/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communitymining.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.analysis.CommunityMiningAPI;
import communitymining.parameters.LocalCommunityParam;
import config.CommunityMiningConfig;
import config.ErrorMsgsConfig;
import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
 *  Class Name      : LocalCommunity
 *  Created Date    : 2016-04-08
 *  Description     : Class to Display the UI Components to select the parameters of algorithm 
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class LocalCommunity {
    
    private static String strSelectedValue = "" ;
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-04-08
     *  Description     : Displays the Window to select parameters for algorithm LocalCommunity
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
                Stage stgLocalCommunity = new Stage();
                stgLocalCommunity.initModality(Modality.APPLICATION_MODAL);

                // Grid to show all the User Input controls
                GridPane gridUserControls = new GridPane();
                gridUserControls.setPadding(new Insets(5));
                gridUserControls.setHgap(5);
                gridUserControls.setVgap(5);

                // Column constraints to define the two columns
                ColumnConstraints column1 = new ColumnConstraints();
                column1.setPercentWidth(60);
                ColumnConstraints column2 = new ColumnConstraints();
                column2.setPercentWidth(40);
                column2.setHgrow(Priority.ALWAYS);
                gridUserControls.getColumnConstraints().addAll(column1, column2);

                // ROW 1 - ALGORITHM TYPE
                Label lblAlgorithmType = new Label(LocalCommunityParam.getAlgorithmLabel()) ;
                ComboBox cmbAlgorithmType = new ComboBox() ;
                cmbAlgorithmType.getItems().addAll(CommunityMiningConfig.LOCALCOMMUNITY_TYPE) ;
                cmbAlgorithmType.setMaxWidth(100);
                cmbAlgorithmType.setMinWidth(100);
                cmbAlgorithmType.setValue(CommunityMiningConfig.LOCALCOMMUNITY_TYPE_DEFAULT);
                strSelectedValue = CommunityMiningConfig.LOCALCOMMUNITY_TYPE_DEFAULT;
                cmbAlgorithmType.valueProperty().addListener(new ChangeListener<String>() {
                    @Override 
                    public void changed(ObservableValue ov, String t, String t1) {
                        strSelectedValue = t1 ;
                    }    
                });

                GridPane.setHalignment(lblAlgorithmType, HPos.LEFT);
                gridUserControls.add(lblAlgorithmType, 0, 0);

                GridPane.setHalignment(cmbAlgorithmType, HPos.CENTER);
                gridUserControls.add(cmbAlgorithmType, 1, 0);

                // ROW 2 - OVERLAP?
                Label lblOverLap = new Label(LocalCommunityParam.getOverlapLabel()) ;
                CheckBox chbOverLap = new CheckBox();
                chbOverLap.setSelected(CommunityMiningConfig.LOCALCOMMUNITY_OVERLAP);
                GridPane.setHalignment(lblOverLap, HPos.LEFT);
                gridUserControls.add(lblOverLap, 0, 1);

                GridPane.setHalignment(chbOverLap, HPos.LEFT);
                gridUserControls.add(chbOverLap, 1, 1);

                // Error Label 
                Label lblError = new Label("") ;
                lblError.setVisible(false);

                // Adding the OK and Cancel Buttons at the end        
                Button btnOK = new Button (LangConfig.GENERAL_OK);
                btnOK.setOnAction(e -> {
                    ClearMiningResults.clearCommunityAssignment();

                    pController.updateStatusBar(true, StatusMsgsConfig.MINING_RESULTSCOMPUTING);
                    stgLocalCommunity.close();

                    String [] arrstrParameters = new String [2] ;

                    if (strSelectedValue != null && !strSelectedValue.isEmpty()) {
                        arrstrParameters[0] = CommunityMiningAPI.getKey_LocalCM_AlgType()+":"+strSelectedValue ;
                    } else {
                        // Display Error
                        lblError.setText(ErrorMsgsConfig.COMMUNITYMINING_SELECTALGORITHM);
                        lblError.setVisible(true);

                        return ;
                    }

                    arrstrParameters[1] = CommunityMiningAPI.getKey_LocalCM_Overlap()+":"+chbOverLap.isSelected() ;

                    // #DEBUG - Checking for the parameters
                    for (String strCurrent : arrstrParameters) {
                        System.out.println("LocalComm(): Parameter : "+strCurrent);
                    }                
                    // #ENDDEBUG

                    // Call the Community corresponding Mining API
                    MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
                    int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
                    int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID() ;
                    int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex() ;

                    // Call the Community corresponding Mining API                
                    System.out.println("MeerkatUI.LocalCommunity: pstrMappingID : " + pstrMappingID);
                
                    CommunityMiningAlgorithmDialog cmad = new CommunityMiningAlgorithmDialog();
                    cmad.runAlgorithm(intProjectID, intGraphID, intTimeFrameIndex, pstrMappingID, arrstrParameters, pController);

                    
                /*    
                    CommunityMiningAPI.runCMAlgorithm(intProjectID, intGraphID, intTimeFrameIndex, pstrMappingID, arrstrParameters) ;

                    // Waiting for the Community Mining API to run
                    while(!CommunityMiningAPI.isDone(intProjectID, intGraphID, intTimeFrameIndex)) {
                        System.out.println("LocalComm(): Is The Algorithm Done ? "+CommunityMiningAPI.isDone(intProjectID, intGraphID, intTimeFrameIndex)) ;
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
                btnCancel.setOnAction(e -> {
                    stgLocalCommunity.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                });
                HBox hboxButtons = new HBox(btnOK, btnCancel);
                hboxButtons.setSpacing(10);
                hboxButtons.setPadding(new Insets(5,10,5,10));
                hboxButtons.setAlignment(Pos.CENTER);

                // The scene consists of the following items
                // 1) Grid for Input Values
                // 2) HBox - buttons
                VBox vboxPanel = new VBox(gridUserControls, lblError, hboxButtons) ;


                Scene scnLocalCommunity = new Scene(vboxPanel);

                scnLocalCommunity.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                    if (evt.getCode().equals(KeyCode.ESCAPE)) {
                        stgLocalCommunity.close();
                        pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                    }
                });

                //stgLocalCommunity.initModality(Modality.WINDOW_MODAL);
                stgLocalCommunity.setTitle(LocalCommunityParam.getTitle());
                stgLocalCommunity.setResizable(false);

                stgLocalCommunity.setScene(scnLocalCommunity);
                stgLocalCommunity.show();
                stgLocalCommunity.setOnCloseRequest(e -> {
                    e.consume();
                    stgLocalCommunity.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                });
            }
        } catch (Exception ex) {
            System.out.println(".Display(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
}
