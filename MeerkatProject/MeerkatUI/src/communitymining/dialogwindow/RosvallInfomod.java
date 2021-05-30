/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communitymining.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.analysis.CommunityMiningAPI;
import communitymining.parameters.RosvallInfomodParam;
import config.CommunityMiningConfig;
import config.ErrorMsgsConfig;
import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import meerkat.Utilities;

/**
 *  Class Name      : RosvallInfomod
 *  Created Date    : 2016-04-08
 *  Description     : Class to Display the UI Components to select the parameters of algorithm RosvallInfomod
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class RosvallInfomod {
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-04-08
     *  Description     : Displays the Window to select parameters for algorithm RosvallInfomod
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
                Stage stgRosvallInfomod = new Stage();
                stgRosvallInfomod.initModality(Modality.APPLICATION_MODAL);

                // Grid to show all the User Input controls
                GridPane gridUserControls = new GridPane();
                gridUserControls.setPadding(new Insets(5));
                gridUserControls.setHgap(5);
                gridUserControls.setVgap(5);

                // Column constraints to define the two columns
                ColumnConstraints column1 = new ColumnConstraints();
                column1.setPercentWidth(70);
                ColumnConstraints column2 = new ColumnConstraints();
                column2.setPercentWidth(30);
                column2.setHgrow(Priority.ALWAYS);
                gridUserControls.getColumnConstraints().addAll(column1, column2);

                // ROW 1 - ATTEMPTS COUNT
                Label lblAttemptsCount = new Label(RosvallInfomodParam.getAtttemptsNumberLabel()) ;
                final Spinner<Integer> spnrAttemptsCount = new Spinner();
                spnrAttemptsCount.setValueFactory(
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(
                                  CommunityMiningConfig.ROSVALLINFOMOD_ATTEMPTS_MIN
                                , CommunityMiningConfig.ROSVALLINFOMOD_ATTEMPTS_MAX
                                , CommunityMiningConfig.ROSVALLINFOMOD_ATTEMPTS_DEFAULT
                                , CommunityMiningConfig.ROSVALLINFOMOD_ATTEMPTS_STEP
                        ));
                spnrAttemptsCount.valueProperty().addListener((obv, oldValue, newValue) -> {
                    // System.out.println("OldValue: "+oldValue+"\tNewValue: "+newValue);
                    SpinnerValueFactory<Integer> valueFactory = spnrAttemptsCount.getValueFactory();
                    if (Utilities.tryParseInteger(String.valueOf(newValue))) {                    
                        valueFactory.setValue(newValue);
                    } else {
                        valueFactory.setValue(oldValue);
                    }
                });
                spnrAttemptsCount.setEditable(true);
                spnrAttemptsCount.setMinWidth(60);
                spnrAttemptsCount.setMaxWidth(60);

                GridPane.setHalignment(lblAttemptsCount, HPos.LEFT);
                gridUserControls.add(lblAttemptsCount, 0, 0);

                GridPane.setHalignment(spnrAttemptsCount, HPos.LEFT);
                gridUserControls.add(spnrAttemptsCount, 1, 0);

                // Error Label 
                Label lblError = new Label("") ;
                lblError.setVisible(false);

                // Adding the OK and Cancel Buttons at the end        
                Button btnOK = new Button (LangConfig.GENERAL_OK);
                btnOK.setOnAction(e -> {
                    ClearMiningResults.clearCommunityAssignment();

                    pController.updateStatusBar(true, StatusMsgsConfig.MINING_RESULTSCOMPUTING);
                    stgRosvallInfomod.close();
                    /// System.out.println("RosvallInfomod: Button OK pressed");

                    String [] arrstrParameters = new String [1] ;
                    if (spnrAttemptsCount.getValue() >= CommunityMiningConfig.ROSVALLINFOMOD_ATTEMPTS_MIN 
                            && spnrAttemptsCount.getValue() <= CommunityMiningConfig.ROSVALLINFOMOD_ATTEMPTS_MAX
                            && Utilities.tryParseInteger(spnrAttemptsCount.getValue().toString())) {
                        arrstrParameters[0] = CommunityMiningAPI.getKey_RInfomod_Attempts()+":"+spnrAttemptsCount.getValue() ;

                        // Run the algorithm

                    } else {
                        // Display Error
                        lblError.setText(ErrorMsgsConfig.COMMUNITYMINING_NUMBEROFATTEMPTSBETWEEN);
                        lblError.setVisible(true);

                        System.out.println("RosvallInfomod: "+ErrorMsgsConfig.COMMUNITYMINING_NUMBEROFATTEMPTSBETWEEN);

                        return ;
                    }

                    // #DEBUG - Checking for the parameters
                    /*
                    for (String strCurrent : arrstrParameters) {
                        System.out.println("Infomod(): Parameter : "+strCurrent);
                    } 
                    */
                    // #ENDDEBUG

                    // Call the Community corresponding Mining API
                    MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
                    int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
                    int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID() ;
                    int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex() ;

                    CommunityMiningAlgorithmDialog cmad = new CommunityMiningAlgorithmDialog();
                    cmad.runAlgorithm(intProjectID, intGraphID, intTimeFrameIndex, pstrMappingID, arrstrParameters, pController);

                    /*
                    CommunityMiningAPI.runCMAlgorithm(intProjectID, intGraphID, intTimeFrameIndex, pstrMappingID, arrstrParameters) ;

                    // Waiting for the Community Mining API to run
                    while(!CommunityMiningAPI.isDone(intProjectID, intGraphID, intTimeFrameIndex)) {
                        try {
                            // System.out.println("RosvallInfomod.Click(): Is The Algorithm Done ? "+CommunityMiningAPI.isDone(intProjectID, intGraphID, intTimeFrameIndex)) ;                        
                            // Thread.sleep(1000);
                            // break ;
                        } catch (Exception ex) {
                            System.out.println("RosvallInfomod.Click(): EXCEPTION");
                            ex.printStackTrace();
                        }
                    }

                    // Update the UI                
                    GraphTab currentGraph = UIInstance.getActiveProjectTab().getActiveGraphTab();
                    currentGraph.updateAfterCommunityMining();
                    currentGraph.updateComMiningStatus();
                    //Update Project Status
                    ProjectStatusTracker.updateProjectModifiedStatus(intProjectID, ProjectStatusTracker.eventCommunityMiningDone);


                    pController.updateStatusBar(false, StatusMsgsConfig.MINING_RESULTSCOMPUTED);
                    */
                });
                Button btnCancel = new Button (LangConfig.GENERAL_CANCEL);
                btnCancel.setOnAction(e -> {                
                    stgRosvallInfomod.close(); ;
                    pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                });
                HBox hboxButtons = new HBox(btnOK, btnCancel);
                hboxButtons.setSpacing(10);
                hboxButtons.setPadding(new Insets(5,10,5,10));
                hboxButtons.setAlignment(Pos.CENTER);

                // The scene consists of the following items
                // 1) Grid for Input Values
                // 2) HBox - buttons
                // 3) Label - to Display Error Msg
                VBox vboxPanel = new VBox(gridUserControls, lblError, hboxButtons) ;


                Scene scnRosvallInfomod = new Scene(vboxPanel);

                scnRosvallInfomod.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                    if (evt.getCode().equals(KeyCode.ESCAPE)) {
                        stgRosvallInfomod.close();
                        pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                    }
                });

                //stgRosvallInfomod.initModality(Modality.WINDOW_MODAL);
                stgRosvallInfomod.setTitle(RosvallInfomodParam.getTitle());
                stgRosvallInfomod.setResizable(false);

                stgRosvallInfomod.setScene(scnRosvallInfomod);
                stgRosvallInfomod.show();
                stgRosvallInfomod.setOnCloseRequest(e -> {
                    e.consume();
                    stgRosvallInfomod.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                });
            }
        } catch (Exception ex) {
            System.out.println("RosvallInfomod.Display(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
}
