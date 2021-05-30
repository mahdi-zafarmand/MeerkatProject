/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communitymining.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.analysis.CommunityMiningAPI;
import communitymining.parameters.FastModularityParam;
import config.CommunityMiningConfig;
import config.ErrorMsgsConfig;
import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *  Class Name      : FastModularity
 *  Created Date    : 2016-04-08
 *  Description     : Class to Display the UI Components to select the parameters of algorithm FastModularity
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class FastModularity {
    
    private static String strSelectedValue = "" ;
    private static BooleanProperty blnIsAlgorithmNotSelected = new SimpleBooleanProperty(false);
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-04-08
     *  Description     : Displays the Window to select parameters for algorithm FastModularity
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
                Stage stgFastModularity = new Stage();
                stgFastModularity.initModality(Modality.APPLICATION_MODAL);


                Label lblNote = new Label(FastModularityParam.getNote()) ;

                Label lblAlgorithmType = new Label(FastModularityParam.getAlgorithmTypeLabel());

                HBox hboxModularity = new HBox();
                hboxModularity.setAlignment(Pos.CENTER_LEFT);
                hboxModularity.setPadding(new Insets(5, 5, 5, 5));
                hboxModularity.setSpacing(30);
                ToggleGroup tgModularity = new ToggleGroup();
                for (String strModularity : CommunityMiningConfig.FM_METRIC) {
                    RadioButton rbModularity = new RadioButton(strModularity);
                    // set default selected value of the toggle group
                    if(strModularity.equalsIgnoreCase(CommunityMiningConfig.FM_METRIC_DEFAULT)){
                        rbModularity.setSelected(true);
                    }
                    rbModularity.setToggleGroup(tgModularity);
                    hboxModularity.getChildren().add(rbModularity);                
                }



                CheckBox chbWeighted = new CheckBox();
                chbWeighted.setSelected(CommunityMiningConfig.FM_WEIGHTED);
                Label lblWeighted = new Label(FastModularityParam.getWeightedLabel());
                HBox hboxWeighted = new HBox(chbWeighted, lblWeighted);
                hboxWeighted.setPadding(new Insets(5, 5, 5, 5));
                hboxWeighted.setSpacing(5);

                strSelectedValue = CommunityMiningConfig.FM_METRIC_DEFAULT;

                // Error Label 
                Label lblError = new Label("") ;
                lblError.setVisible(false);

                // Adding the OK and Cancel Buttons at the end        
                Button btnOK = new Button (LangConfig.GENERAL_OK);
                btnOK.disableProperty().bind(blnIsAlgorithmNotSelected);
                btnOK.setOnAction(e -> {
                    // Fast Modularity Close the dialog box
                    stgFastModularity.close();
                    
                    ClearMiningResults.clearCommunityAssignment();
                    // Update the status bar
                    pController.updateStatusBar(true, StatusMsgsConfig.MINING_RESULTSCOMPUTING);                           

                    

                    String [] arrstrParameters = new String [2] ;

                    if (!strSelectedValue.isEmpty() && strSelectedValue.length() != 0) {
                        arrstrParameters[0] = CommunityMiningAPI.getKey_FM_AlgType()+":"+strSelectedValue ;
                    } else {
                        // Display Error
                        lblError.setText(ErrorMsgsConfig.COMMUNITYMINING_SELECTALGORITHM);
                        lblError.setVisible(true);

                        return ;
                    }

                    arrstrParameters[1] = CommunityMiningAPI.getKey_FM_Weighted()+":"+String.valueOf(chbWeighted.isSelected()) ;

                    // Call the Community corresponding Mining API
                    MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
                    int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
                    int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID() ;
                    int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex() ;

                    // #DEBUG - Checking for the parameters
                    for (String strCurrent : arrstrParameters) {
                        System.out.println("FM(): Parameter : "+strCurrent);
                    }                
                    // #ENDDEBUG

                    
                    
                    CommunityMiningAlgorithmDialog cmad = new CommunityMiningAlgorithmDialog();
                    cmad.runAlgorithm(intProjectID, intGraphID, intTimeFrameIndex, pstrMappingID, arrstrParameters, pController);
                    
                    /*
                    // Call the Community corresponding Mining API       
                    
                    CommunityMiningAPI.runCMAlgorithm(intProjectID, intGraphID, 
                            intTimeFrameIndex, pstrMappingID, arrstrParameters) ;

                    

                    // Waiting for the Community Mining API to run
                    while(!CommunityMiningAPI.isDone(intProjectID, intGraphID, intTimeFrameIndex)) {
                        System.out.println("FM(): Is The Algorithm Done ? "+CommunityMiningAPI.isDone(intProjectID, intGraphID, intTimeFrameIndex)) ;
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
                    strSelectedValue = "" ;
                    blnIsAlgorithmNotSelected.set(true);
                    stgFastModularity.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                });
                HBox hboxButtons = new HBox(btnOK, btnCancel);
                hboxButtons.setSpacing(10);
                hboxButtons.setPadding(new Insets(5,10,5,10));
                hboxButtons.setAlignment(Pos.CENTER);


                tgModularity.selectedToggleProperty().addListener(
                        (ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) -> {
                    RadioButton rbtnSelectedLang = (RadioButton)t1.getToggleGroup()
                            .getSelectedToggle(); // Cast object to radio button                        
                    strSelectedValue = rbtnSelectedLang.getText() ;

                    if (strSelectedValue.length() > 0) {
                        lblError.setVisible(false);
                        blnIsAlgorithmNotSelected.set(false);
                    }
                });

                // The scene consists of the following items
                // 1) Label - Note
                // 2) Label - Algorithm Type
                // 3) HBox - Radio Button
                // 4) HBox - Checkbox and Question for Weight
                // 5) HBox - Buttons
                VBox vboxPanel = new VBox(lblNote, lblAlgorithmType, hboxModularity, hboxWeighted, lblError, hboxButtons) ;
                vboxPanel.setSpacing(20);
                vboxPanel.setPadding(new Insets(10, 20, 10, 20));


                Scene scnFastModularity = new Scene(vboxPanel);
                scnFastModularity.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                    if (evt.getCode().equals(KeyCode.ESCAPE)) {
                        stgFastModularity.close();
                        pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
                    }
                });

                //stgFastModularity.initModality(Modality.WINDOW_MODAL);
                stgFastModularity.setTitle(FastModularityParam.getTitle());
                stgFastModularity.setResizable(false);

                stgFastModularity.setScene(scnFastModularity);
                stgFastModularity.show();
                stgFastModularity.setOnCloseRequest(e -> {
                    e.consume();
                    stgFastModularity.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                });
            }
        } catch (Exception ex) {
            System.out.println(".Display(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
}
