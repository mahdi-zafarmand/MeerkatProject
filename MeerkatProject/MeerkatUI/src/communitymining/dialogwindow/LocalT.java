/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communitymining.dialogwindow;

import analysisscreen.AnalysisController;
import communitymining.parameters.LocalTParam;
import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *  Class Name      : LocalT
 *  Created Date    : 2016-04-08
 *  Description     : Class to Display the UI Components to select the parameters of algorithm LocalT
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class LocalT {
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-04-08
     *  Description     : Displays the Window to select parameters for algorithm LocalT
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Display (AnalysisController pController, String pstrMappingID) {
        try {
            boolean commMiningStatus = ClearMiningResults.checkExistingCommMining(Boolean.FALSE);
            if(commMiningStatus){
                Stage stgLocalT = new Stage();
                stgLocalT.initModality(Modality.APPLICATION_MODAL);

                // Grid to show all the User Input controls
                GridPane gridUserControls = new GridPane();
                gridUserControls.setPadding(new Insets(5));
                gridUserControls.setHgap(5);
                gridUserControls.setVgap(5);

                // Column constraints to define the two columns
                ColumnConstraints column1 = new ColumnConstraints();
                //column1.setPercentWidth(40);
                ColumnConstraints column2 = new ColumnConstraints();
                //column2.setPercentWidth(60);
                column2.setHgrow(Priority.ALWAYS);
                gridUserControls.getColumnConstraints().addAll(column1, column2);


                Label lblAttribute = new Label(" Confirm if you want to run Local T? ") ;
                GridPane.setHalignment(lblAttribute, HPos.LEFT);
                gridUserControls.add(lblAttribute, 0, 0);
                /*final Spinner<Double> spinner = new Spinner();
                double dblInitialValue = 0.5 ;
                spinner.setValueFactory(
                        new SpinnerValueFactory.DoubleSpinnerValueFactory(
                                  0
                                , 1
                                , dblInitialValue
                        )
                );
                spinner.setEditable(true);
                spinner.setMaxWidth(60);

                GridPane.setHalignment(lblAttribute, HPos.LEFT);
                gridUserControls.add(lblAttribute, 0, 0);

                GridPane.setHalignment(spinner, HPos.CENTER);
                gridUserControls.add(spinner, 1, 0);
                */
                // Adding the OK and Cancel Buttons at the end        
                Button btnOK = new Button (LangConfig.GENERAL_OK);
                btnOK.setOnAction(e -> {
                    ClearMiningResults.clearCommunityAssignment();

                    pController.updateStatusBar(true, StatusMsgsConfig.MINING_RESULTSCOMPUTING);

                    stgLocalT.close();

                    // Call the Community corresponding Mining API
                    MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
                    int intProjectID = UIInstance.getActiveProjectTab()
                            .getProjectID() ;
                    int intGraphID = UIInstance.getActiveProjectTab()
                            .getActiveGraphTab().getGraphID() ;
                    int intTimeFrameIndex = UIInstance.getActiveProjectTab()
                            .getActiveGraphTab().getTimeFrameIndex() ;
                    
                    CommunityMiningAlgorithmDialog cmad = new CommunityMiningAlgorithmDialog();
                    cmad.runAlgorithm(intProjectID, intGraphID, intTimeFrameIndex, pstrMappingID, null, pController);

                    
                /*    
                    CommunityMiningAPI.runCMAlgorithm(intProjectID, intGraphID, 
                            intTimeFrameIndex, pstrMappingID, null) ;

                    pController.updateStatusBar(false, StatusMsgsConfig.MINING_RESULTSCOMPUTED);

                    while(!CommunityMiningAPI
                            .isDone(intProjectID, intGraphID, intTimeFrameIndex)) {
                        System.out.println("MeerkatUI.LocalT:"
                                + "Comuunity Mining in progress...");
                    }

                    // Update the UI
                    // 1) Update the UI Panel                
                    GraphTab currentGraph = UIInstance.getActiveProjectTab().getActiveGraphTab();
                    currentGraph.updateAfterCommunityMining();
                    currentGraph.updateComMiningStatus();
                    
                    //Update Project Status
                    ProjectStatusTracker.updateProjectModifiedStatus(intProjectID, ProjectStatusTracker.eventCommunityMiningDone);

                    
                    pController.updateStatusBar(false, StatusMsgsConfig.MINING_RESULTSCOMPUTED);
                */
                });

                Button btnCancel = new Button (LangConfig.GENERAL_CANCEL);
                btnCancel.setOnAction(e ->{
                    stgLocalT.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                });
                HBox hboxButtons = new HBox(btnOK, btnCancel);
                hboxButtons.setSpacing(10);
                hboxButtons.setPadding(new Insets(5,10,5,10));
                hboxButtons.setAlignment(Pos.CENTER);

                // The scene consists of the following items
                // 1) Grid for Input Values
                // 2) HBox - buttons
                VBox vboxPanel = new VBox(gridUserControls, hboxButtons) ;


                Scene scnLocalT = new Scene(vboxPanel);

                scnLocalT.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                    if (evt.getCode().equals(KeyCode.ESCAPE)) {
                        stgLocalT.close();
                        pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                    }
                });

                //stgLocalT.initModality(Modality.WINDOW_MODAL);
                stgLocalT.setTitle(LocalTParam.getTitle());
                stgLocalT.setResizable(false);

                stgLocalT.setScene(scnLocalT);
                stgLocalT.show();
                stgLocalT.setOnCloseRequest(e -> {
                    e.consume();
                    stgLocalT.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                });
            }
        } catch (Exception ex) {
            System.out.println(".Display(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
}
