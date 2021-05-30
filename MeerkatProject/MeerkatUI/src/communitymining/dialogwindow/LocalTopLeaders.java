/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communitymining.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.analysis.CommunityMiningAPI;
import communitymining.parameters.LocalTopLeadersParam;
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
 *  Class Name      : LocalTopLeaders
 *  Created Date    : 2016-04-08
 *  Description     : Class to Display the UI Components to select the parameters of algorithm LocalTopLeaders
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class LocalTopLeaders {
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-04-08
     *  Description     : Displays the Window to select parameters for algorithm LocalTopLeaders
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
                Stage stgLocalTopLeaders = new Stage();
                stgLocalTopLeaders.initModality(Modality.APPLICATION_MODAL);

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

                // ROW 1 - CLUSTER COUNT
                Label lblClusterCount = new Label(LocalTopLeadersParam.getClusterNumberLabel()); ;
                final Spinner<Integer> spnrClusterCount = new Spinner();
                spnrClusterCount.setValueFactory(
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(
                                  CommunityMiningConfig.LOCALTOPLEADERS_CLUSTERCOUNT_MIN
                                , CommunityMiningConfig.LOCALTOPLEADERS_CLUSTERCOUNT_MAX
                                , CommunityMiningConfig.LOCALTOPLEADERS_CLUSTERCOUNT_DEFAULT
                                , CommunityMiningConfig.LOCALTOPLEADERS_CLUSTERCOUNT_STEP
                        ));
                spnrClusterCount.setEditable(true);
                spnrClusterCount.setMaxWidth(60);

                GridPane.setHalignment(lblClusterCount, HPos.LEFT);
                gridUserControls.add(lblClusterCount, 0, 0);

                GridPane.setHalignment(spnrClusterCount, HPos.LEFT);
                gridUserControls.add(spnrClusterCount, 1, 0);

                // ROW 2 - OUTLIER PERCENTAGE
                Label lblOutlierPercentage = new Label(LocalTopLeadersParam.getOutlierPercentLabel()); ;
                final Spinner<Double> spnrOutlierPercentage = new Spinner();
                spnrOutlierPercentage.setValueFactory(
                        new SpinnerValueFactory.DoubleSpinnerValueFactory(
                                  CommunityMiningConfig.LOCALTOPLEADERS_OUTLIERPERCENTAGE_MIN
                                , CommunityMiningConfig.LOCALTOPLEADERS_OUTLIERPERCENTAGE_MAX
                                , CommunityMiningConfig.LOCALTOPLEADERS_OUTLIERPERCENTAGE_DEFAULT
                                , CommunityMiningConfig.LOCALTOPLEADERS_OUTLIERPERCENTAGE_STEP
                        ));
                spnrOutlierPercentage.setEditable(true);
                spnrOutlierPercentage.setMaxWidth(60);

                GridPane.setHalignment(lblOutlierPercentage, HPos.LEFT);
                gridUserControls.add(lblOutlierPercentage, 0, 1);

                GridPane.setHalignment(spnrOutlierPercentage, HPos.LEFT);
                gridUserControls.add(spnrOutlierPercentage, 1, 1);


                // ROW 3 - HUB PERCENTAGE
                Label lblHubercentage = new Label(LocalTopLeadersParam.getHubPercentLabel()); ;
                final Spinner<Double> spnrHubPercentage = new Spinner();
                spnrHubPercentage.setValueFactory(
                        new SpinnerValueFactory.DoubleSpinnerValueFactory(
                                  CommunityMiningConfig.LOCALTOPLEADERS_HUBPERCENTAGE_MIN
                                , CommunityMiningConfig.LOCALTOPLEADERS_HUBPERCENTAGE_MAX
                                , CommunityMiningConfig.LOCALTOPLEADERS_HUBPERCENTAGE_DEFAULT
                                , CommunityMiningConfig.LOCALTOPLEADERS_HUBPERCENTAGE_STEP
                        ));
                spnrHubPercentage.setEditable(true);
                spnrHubPercentage.setMaxWidth(60);

                GridPane.setHalignment(lblHubercentage, HPos.LEFT);
                gridUserControls.add(lblHubercentage, 0, 2);

                GridPane.setHalignment(spnrHubPercentage, HPos.LEFT);
                gridUserControls.add(spnrHubPercentage, 1, 2);


                // ROW 4 - CENTER DISTANCE
                Label lblCenterDistance = new Label(LocalTopLeadersParam.getCenterDistanceLabel()); ;
                final Spinner<Double> spnrCenterDistance = new Spinner();
                spnrCenterDistance.setValueFactory(
                        new SpinnerValueFactory.DoubleSpinnerValueFactory(
                                  CommunityMiningConfig.LOCALTOPLEADERS_CENTERDISTANCE_MIN
                                , CommunityMiningConfig.LOCALTOPLEADERS_CENTERDISTANCE_MAX
                                , CommunityMiningConfig.LOCALTOPLEADERS_CENTERDISTANCE_DEFAULT
                                , CommunityMiningConfig.LOCALTOPLEADERS_CENTERDISTANCE_STEP
                        ));
                spnrCenterDistance.setEditable(true);
                spnrCenterDistance.setMaxWidth(60);

                GridPane.setHalignment(lblCenterDistance, HPos.LEFT);
                gridUserControls.add(lblCenterDistance, 0, 3);

                GridPane.setHalignment(spnrCenterDistance, HPos.LEFT);
                gridUserControls.add(spnrCenterDistance, 1, 3);

                // Error Label 
                Label lblError = new Label("") ;
                lblError.setVisible(false);

                // Adding the OK and Cancel Buttons at the end        
                Button btnOK = new Button (LangConfig.GENERAL_OK);
                btnOK.setOnAction(e -> {
                    ClearMiningResults.clearCommunityAssignment();

                    pController.updateStatusBar(true, StatusMsgsConfig.MINING_RESULTSCOMPUTING);
                    stgLocalTopLeaders.close();

                    String [] arrstrParameters = new String [4] ;
                    if (spnrClusterCount.getValue() >= CommunityMiningConfig.LOCALTOPLEADERS_CLUSTERCOUNT_MIN 
                            && spnrClusterCount.getValue() <= CommunityMiningConfig.LOCALTOPLEADERS_CLUSTERCOUNT_MAX
                            && Utilities.tryParseInteger(spnrClusterCount.getValue().toString())) {

                        arrstrParameters[0] = CommunityMiningAPI.getKey_LocalTopL_NumClusters()+":"+spnrClusterCount.getValue() ;
                    } else {
                        // Display Error
                        lblError.setText(ErrorMsgsConfig.COMMUNITYMINING_NUMBERCLUSTERSBETWEEN);
                        lblError.setVisible(true);
                        return ;
                    }

                    if (spnrOutlierPercentage.getValue() >= CommunityMiningConfig.LOCALTOPLEADERS_OUTLIERPERCENTAGE_MIN
                            && spnrOutlierPercentage.getValue() <= CommunityMiningConfig.LOCALTOPLEADERS_OUTLIERPERCENTAGE_MAX
                            && Utilities.tryParseDouble(spnrOutlierPercentage.getValue().toString())) {

                        arrstrParameters[1] = CommunityMiningAPI.getKey_LocalTopL_OutlierPercent()+":"+spnrOutlierPercentage.getValue() ;
                    } else {
                        // Display Error
                        lblError.setText(ErrorMsgsConfig.COMMUNITYMINING_OUTLIERPERCENTAGEBETWEEN);
                        lblError.setVisible(true);

                        return ;
                    }

                    if (spnrHubPercentage.getValue() >= CommunityMiningConfig.LOCALTOPLEADERS_HUBPERCENTAGE_MIN 
                            && spnrHubPercentage.getValue() <= CommunityMiningConfig.LOCALTOPLEADERS_HUBPERCENTAGE_MAX
                            && Utilities.tryParseDouble(spnrHubPercentage.getValue().toString())) {

                        arrstrParameters[2] = CommunityMiningAPI.getKey_LocalTopL_HubPercent()+":"+spnrHubPercentage.getValue() ;
                    } else {
                        // Display Error
                        lblError.setText(ErrorMsgsConfig.COMMUNITYMINING_HUBPERCENTAGEBETWEEN);
                        lblError.setVisible(true);

                        return ;
                    }

                    if (spnrCenterDistance.getValue() >= CommunityMiningConfig.LOCALTOPLEADERS_CENTERDISTANCE_MIN
                            && spnrCenterDistance.getValue() <= CommunityMiningConfig.LOCALTOPLEADERS_CENTERDISTANCE_MAX
                            && Utilities.tryParseDouble(spnrCenterDistance.getValue().toString())) {

                        arrstrParameters[3] = CommunityMiningAPI.getKey_LocalTopL_CenterDist()+":"+spnrCenterDistance.getValue() ;
                    } else {
                        // Display Error
                        lblError.setText(ErrorMsgsConfig.COMMUNITYMINING_CENTERDISTANCEBETWEEN);
                        lblError.setVisible(true);

                        return ;
                    }

                    // #DEBUG - Checking for the parameters
                    for (String strCurrent : arrstrParameters) {
                        System.out.println("LocalTop(): Parameter : "+strCurrent);
                    }                
                    // #ENDDEBUG

                    // Call the Community corresponding Mining API
                    MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
                    int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
                    int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID() ;
                    int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex() ;
                    
                    CommunityMiningAlgorithmDialog cmad = new CommunityMiningAlgorithmDialog();
                    cmad.runAlgorithm(intProjectID, intGraphID, intTimeFrameIndex, pstrMappingID, arrstrParameters, pController);

                /*    
                    // Call the Community corresponding Mining API                
                    CommunityMiningAPI.runCMAlgorithm(intProjectID, intGraphID, intTimeFrameIndex, pstrMappingID, arrstrParameters) ;

                    // Waiting for the Community Mining API to run
                    while(!CommunityMiningAPI.isDone(intProjectID, intGraphID, intTimeFrameIndex)) {
                        System.out.println("LocalTop(): Is The Algorithm Done ? "+CommunityMiningAPI.isDone(intProjectID, intGraphID, intTimeFrameIndex)) ;
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
                    stgLocalTopLeaders.close();
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


                Scene scnLocalTopLeaders = new Scene(vboxPanel);

                scnLocalTopLeaders.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                    if (evt.getCode().equals(KeyCode.ESCAPE)) {
                        stgLocalTopLeaders.close();
                        pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                    }
                });

                //stgLocalTopLeaders.initModality(Modality.WINDOW_MODAL);
                stgLocalTopLeaders.setTitle(LocalTopLeadersParam.getTitle());
                stgLocalTopLeaders.setResizable(false);

                stgLocalTopLeaders.setScene(scnLocalTopLeaders);
                stgLocalTopLeaders.show();
                stgLocalTopLeaders.setOnCloseRequest(e -> {
                    e.consume();
                    stgLocalTopLeaders.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                });
            }
        } catch (Exception ex) {
            System.out.println(".Display(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
}
