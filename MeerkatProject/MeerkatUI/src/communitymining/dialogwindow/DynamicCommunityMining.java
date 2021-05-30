/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communitymining.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.analysis.CommunityMiningAPI;
import communitymining.parameters.DynamicCommunityMiningParam;
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
 *  Class Name      : DynamicCommunityMining
 *  Created Date    : 2016-04-08
 *  Description     : Class to Display the UI Components to select the parameters of algorithm DynamicCommunityMining
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class DynamicCommunityMining {
    
    private static String strSelectedMetric = "" ;
    private static String strSelectedMethod = "" ;
    private static final boolean isDCMining=true;
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-04-08
     *  Description     : Displays the Window to select parameters for algorithm DynamicCommunityMining
     *  Version         : 1.0
     *  @author         : Talat
     *  
     *  @param pController : AnalysisController
     *  @param pstrIDMapping
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
     *  
     * 
    */
    public static void Display (AnalysisController pController, String pstrIDMapping) {
        try {
            boolean commMiningStatus = ClearMiningResults.checkExistingCommMining(isDCMining);
            if(commMiningStatus){
                Stage stgDynamicCommunityMining = new Stage();
                stgDynamicCommunityMining.initModality(Modality.APPLICATION_MODAL);

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

                // ROW 1 - SIMILARITY THRESHOLD
                Label lblSimilarityThreshold = new Label(DynamicCommunityMiningParam.getSimilarityThresholdLabel()) ;
                final Spinner<Double> spnrSimilarityThreshold = new Spinner();
                spnrSimilarityThreshold.setValueFactory(
                        new SpinnerValueFactory.DoubleSpinnerValueFactory(
                                  CommunityMiningConfig.DCMINING_SIMILARITYTHRESHOLD_MIN
                                , CommunityMiningConfig.DCMINING_SIMILARITYTHRESHOLD_MAX
                                , CommunityMiningConfig.DCMINING_SIMILARITYTHRESHOLD_DEFAULT
                                , CommunityMiningConfig.DCMINING_SIMILARITYTHRESHOLD_STEP
                        ));
                spnrSimilarityThreshold.setEditable(true);
                spnrSimilarityThreshold.setMaxWidth(100);

                GridPane.setHalignment(lblSimilarityThreshold, HPos.LEFT);
                gridUserControls.add(lblSimilarityThreshold, 0, 0);

                GridPane.setHalignment(spnrSimilarityThreshold, HPos.LEFT);
                gridUserControls.add(spnrSimilarityThreshold, 1, 0);

                // ROW 2 - METRIC
                Label lblMetric = new Label(DynamicCommunityMiningParam.getMetricLabel()) ;
                ComboBox<String> cmbMetric = new ComboBox() ;
                cmbMetric.getItems().addAll(CommunityMiningConfig.DCMINING_METRIC);
                cmbMetric.setValue(CommunityMiningConfig.DCMINING_METRIC_DEFAULT);
                cmbMetric.setMaxWidth(100);
                strSelectedMetric = cmbMetric.getValue();

                GridPane.setHalignment(lblMetric, HPos.LEFT);
                gridUserControls.add(lblMetric, 0, 1);

                GridPane.setHalignment(cmbMetric, HPos.LEFT);
                gridUserControls.add(cmbMetric, 1, 1);

                // Row 3 - METHOD
                Label lblMethod = new Label(DynamicCommunityMiningParam.getMethodLabel()) ;
                ComboBox<String> cmbMethod = new ComboBox() ;
                cmbMethod.getItems().addAll(CommunityMiningConfig.DCMINING_METHOD) ;
                cmbMethod.setValue(CommunityMiningConfig.DCMINING_METHOD_DEFAULT);
                cmbMethod.setMaxWidth(100);
                strSelectedMethod = cmbMethod.getValue();


                GridPane.setHalignment(lblMethod, HPos.LEFT);
                gridUserControls.add(lblMethod, 0, 2);

                GridPane.setHalignment(cmbMethod, HPos.LEFT);
                gridUserControls.add(cmbMethod, 1, 2);

                // ROW 4 - OVERLAP
                Label lblOverLap = new Label(DynamicCommunityMiningParam.getOverlapLabel()) ;
                CheckBox chbOverLap = new CheckBox();
                chbOverLap.setSelected(CommunityMiningConfig.DCMINING_OVERLAP_DEFAULT);

                GridPane.setHalignment(lblOverLap, HPos.LEFT);
                gridUserControls.add(lblOverLap, 0, 3);

                GridPane.setHalignment(chbOverLap, HPos.LEFT);
                gridUserControls.add(chbOverLap, 1, 3);

                // RWO 5 - HUBS
                Label lblHubs = new Label(DynamicCommunityMiningParam.getHubsLabel()) ;
                CheckBox chbHubs = new CheckBox();
                chbHubs.setSelected(CommunityMiningConfig.DCMINING_HUBS_DEFAULT);

                GridPane.setHalignment(lblHubs, HPos.LEFT);
                gridUserControls.add(lblHubs, 0, 4);

                GridPane.setHalignment(chbHubs, HPos.LEFT);
                gridUserControls.add(chbHubs, 1, 4);

                // ROW 6 - INSTABILITY
                Label lblInstability = new Label(DynamicCommunityMiningParam.getInstabilityLabel()) ;
                final Spinner<Double> spnrInstability = new Spinner();
                spnrInstability.setValueFactory(
                        new SpinnerValueFactory.DoubleSpinnerValueFactory(
                                  CommunityMiningConfig.DCMINING_INSTABILITY_MIN
                                , CommunityMiningConfig.DCMINING_INSTABILITY_MAX
                                , CommunityMiningConfig.DCMINING_INSTABILITY_DEFAULT
                                , CommunityMiningConfig.DCMINING_INSTABILITY_STEP
                        ));
                spnrInstability.setEditable(true);
                spnrInstability.setMaxWidth(100);

                GridPane.setHalignment(lblInstability, HPos.LEFT);
                gridUserControls.add(lblInstability, 0, 5);

                GridPane.setHalignment(spnrInstability, HPos.LEFT);
                gridUserControls.add(spnrInstability, 1, 5);

                // ROW 7 - HISTORY
                Label lblHistory = new Label(DynamicCommunityMiningParam.getHistoryLabel()) ;
                final Spinner<Double> spnrHistory = new Spinner();
                spnrHistory.setValueFactory(
                        new SpinnerValueFactory.DoubleSpinnerValueFactory(
                                  CommunityMiningConfig.DCMINING_HISTORY_MIN
                                , CommunityMiningConfig.DCMINING_HISTORY_MAX
                                , CommunityMiningConfig.DCMINING_HISTORY_DEFAULT
                                , CommunityMiningConfig.DCMINING_HISTORY_STEP
                        ));
                spnrHistory.setEditable(true);
                spnrHistory.setMaxWidth(100);

                GridPane.setHalignment(lblHistory, HPos.LEFT);
                gridUserControls.add(lblHistory, 0, 6);

                GridPane.setHalignment(spnrHistory, HPos.LEFT);
                gridUserControls.add(spnrHistory, 1, 6);

                // Error Label 
                Label lblError = new Label("") ;
                lblError.setVisible(false);

                // Adding the OK and Cancel Buttons at the end in a Horizontal Box
                Button btnOK = new Button (LangConfig.GENERAL_OK);
                btnOK.setOnAction(e -> {

                    ClearMiningResults.clearDynamicCommunityAssignment();

                    String [] arrstrParameters = new String [7] ;
                    if (spnrSimilarityThreshold.getValue() >= 
                            CommunityMiningConfig.DCMINING_SIMILARITYTHRESHOLD_MIN
                            && spnrSimilarityThreshold.getValue() <= 
                            CommunityMiningConfig.DCMINING_SIMILARITYTHRESHOLD_MAX
                            && Utilities.tryParseDouble(
                                   spnrSimilarityThreshold.getValue().toString())) {

                        arrstrParameters[0] = 
                                CommunityMiningAPI.getKey_DCMINING_SIMTHRESHOLD()+
                                ":"+
                                spnrSimilarityThreshold.getValue() ;
                    } else {
                        // Display Error
                        lblError.setText(ErrorMsgsConfig
                                .COMMUNITYMINING_SIMILARITYTHRESHOLDBETWEEN);
                        lblError.setVisible(true);
    //                    return ;
                    }

                    if (strSelectedMetric != null && !strSelectedMetric.isEmpty()) {
                        arrstrParameters[1] = 
                                CommunityMiningAPI.getKey_DCMINING_METRIC()+
                                ":"+
                                strSelectedMetric ;
                    } else {
                        // Display Error
                        lblError.setText(
                                ErrorMsgsConfig.COMMUNITYMINING_SELECTMETRIC);
                        lblError.setVisible(true);
    //                    return ;
                    }

                    if (strSelectedMethod != null && !strSelectedMethod.isEmpty()) {
                        arrstrParameters[2] = 
                                CommunityMiningAPI.getKey_DCMINING_METHOD()+
                                ":"+
                                strSelectedMethod ;
                    } else {
                        // Display Error
                        lblError.setText(
                                ErrorMsgsConfig.COMMUNITYMINING_SELECTMETHOD);
                        lblError.setVisible(true);
    //                    return ;
                    }

                    arrstrParameters[3] = 
                            CommunityMiningAPI.getKey_DCMINING_OVERLAP()+
                            ":"+
                            chbOverLap.isSelected() ;

                    arrstrParameters[4] = 
                            CommunityMiningAPI.getKey_DCMINING_HUBS()+
                            ":"+
                            chbHubs.isSelected() ;                

                    if (spnrInstability.getValue() >= 
                            CommunityMiningConfig.DCMINING_INSTABILITY_MIN
                            && spnrInstability.getValue() <= 
                            CommunityMiningConfig.DCMINING_INSTABILITY_MAX
                            && Utilities.tryParseDouble(
                                    spnrInstability.getValue().toString())) {

                        arrstrParameters[5] = 
                                CommunityMiningAPI.getKey_DCMINING_INSTABILITY()+
                                ":"+
                                spnrInstability.getValue() ;
                    } else {
                        // Display Error
                        lblError.setText(ErrorMsgsConfig.
                                COMMUNITYMINING_INSTABILITYBETWEEN);
                        lblError.setVisible(true);

    //                    return ;
                    }

                    if (spnrHistory.getValue() >= 
                            CommunityMiningConfig.DCMINING_HISTORY_MIN
                            && spnrHistory.getValue() <= 
                            CommunityMiningConfig.DCMINING_HISTORY_MAX
                            && Utilities.tryParseDouble(
                                    spnrHistory.getValue().toString())) {

                        arrstrParameters[6] = 
                                CommunityMiningAPI.getKey_DCMINING_HISTORY()+
                                ":"+
                                spnrHistory.getValue() ;
                    } else {
                        // Display Error
                        lblError.setText(ErrorMsgsConfig.
                                COMMUNITYMINING_HISTORYBETWEEN);
                        lblError.setVisible(true);

    //                    return ;
                    }                

                    if (lblError.getText().isEmpty()) {

                        stgDynamicCommunityMining.close();
    //                    System.out.println("Dynamic Community Mining Requested!");
                        pController.updateStatusBar(true, 
                            StatusMsgsConfig.MINING_RESULTSCOMPUTING);
                        // Call the Community corresponding Mining API

                        MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
                        int intProjectID = 
                                UIInstance.getActiveProjectTab().getProjectID() ;
                        GraphTab currentGraph = 
                                    UIInstance.getActiveProjectTab()
                                            .getActiveGraphTab();
                        int intGraphID = currentGraph.getGraphID() ;
                        int intTimeFrameIndex = UIInstance.getActiveProjectTab()
                                .getActiveGraphTab().getTimeFrameIndex() ;
                        
                        System.out.println("Calling DynamicCommunityMining Alg.");
                        CommunityMiningAlgorithmDialog cmad = new CommunityMiningAlgorithmDialog();
                        cmad.runDynamicMiningAlgorithm(intProjectID, intGraphID, intTimeFrameIndex, pstrIDMapping, arrstrParameters, pController);

                        /*
    //                    System.out.println("Calling DynamicCommunityMining Alg.");
                        CommunityMiningAPI.runCMAlgorithm(intProjectID, intGraphID,  
                                pstrIDMapping, arrstrParameters) ;
                        while(!CommunityMiningAPI.isDone(intProjectID, intGraphID)){
    //                        System.out.println("DynamicCommunityMining is running");
                        }

                        currentGraph.setDCommMiningStatus(Boolean.TRUE);
                        int intNUMTF = 
                                GraphAPI.getTimeFrameNames(intProjectID, intGraphID)
                                .length;
                        for (int i = 0 ; i < intNUMTF ; i++) {
                            currentGraph.setTimeFrameIndex(i);
                            currentGraph.updateAfterCommunityMining();
                            currentGraph.updateComMiningStatus();
                        }
                        currentGraph.setTimeFrameIndex(intTimeFrameIndex);
                        currentGraph.updateAfterCommunityMining();
                        currentGraph.updateComMiningStatus();
                        currentGraph.updateDCPanelAfterCommunityMining();
                        
                        //Update Project Status
                        ProjectStatusTracker.updateProjectModifiedStatus(intProjectID, ProjectStatusTracker.eventCommunityMiningDone);

                        System.out.println("DynamicCommunityMining.Display() : "
                                + "timeframeindex is " 
                                + currentGraph.getTimeFrameIndex());
                        pController.updateStatusBar(false, 
                                StatusMsgsConfig.MINING_RESULTSCOMPUTED);
                    */            
                    }
                });

                Button btnCancel = new Button (LangConfig.GENERAL_CANCEL);
                btnCancel.setOnAction((e) -> {
    //                strSelectedMethod = "" ;
    //                strSelectedMetric = "" ;
                    stgDynamicCommunityMining.close();
                    pController.updateStatusBar(false, 
                            StatusMsgsConfig.OPERATION_CANCEL);
                });
                HBox hboxButtons = new HBox(btnOK, btnCancel);
                hboxButtons.setSpacing(10);
                hboxButtons.setPadding(new Insets(5,10,5,10));
                hboxButtons.setAlignment(Pos.CENTER);


                cmbMetric.valueProperty().addListener(new ChangeListener<String>() {
                    @Override 
                    public void changed(ObservableValue ov, String t, String t1) {
                        strSelectedMetric = t1 ;

                        if (strSelectedMetric.length() > 0) {
                            lblError.setVisible(false);
                        }
                    }    
                });

                cmbMethod.valueProperty().addListener(new ChangeListener<String>() {
                    @Override 
                    public void changed(ObservableValue ov, String t, String t1) {
                        strSelectedMethod = t1 ;

                        if (strSelectedMethod.length() > 0) {
                            lblError.setVisible(false);
                        }                    
                    }    

                });

                // The scene consists of the following items
                // 1) Grid for Input Values
                // 2) HBox - buttons
                VBox vboxPanel = new VBox(gridUserControls, lblError, hboxButtons) ;

                Scene scnDynamicCommunityMining = new Scene(vboxPanel);
                scnDynamicCommunityMining.addEventFilter(KeyEvent.KEY_PRESSED, 
                        (KeyEvent evt) -> {
                    if (evt.getCode().equals(KeyCode.ESCAPE)) {
                        stgDynamicCommunityMining.close();
                        pController.updateStatusBar(false, 
                                StatusMsgsConfig.OPERATION_CANCEL);
                    }
                });

                //stgDynamicCommunityMining.initModality(Modality.WINDOW_MODAL);
                stgDynamicCommunityMining.setTitle(
                        DynamicCommunityMiningParam.getTitle());
                stgDynamicCommunityMining.setResizable(false);

                stgDynamicCommunityMining.setScene(scnDynamicCommunityMining);
                stgDynamicCommunityMining.show();
                stgDynamicCommunityMining.setOnCloseRequest(e -> {
                    e.consume();
                    stgDynamicCommunityMining.close();
                    pController.updateStatusBar(false, 
                            StatusMsgsConfig.OPERATION_CANCEL);
                });
            }    
        } catch (Exception ex) {
            System.out.println(".Display(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
    
}