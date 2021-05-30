/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communitymining.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.analysis.CommunityMiningAPI;
import communitymining.parameters.KMeansClusteringParam;
import config.CommunityMiningConfig;
import config.ErrorMsgsConfig;
import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

/**
 *  Class Name      : KMeansClustering
 *  Created Date    : 2016-04-12
 *  Description     : Class for KMeans Parameter window
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/

public class KMeansClustering {
    
    private static String strSelectedAttribute ;
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-04-12
     *  Description     : Displays the Window to select parameters for algorithm KMeansClustering
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
                Stage stgKMeansClustering = new Stage();
                stgKMeansClustering.initModality(Modality.APPLICATION_MODAL);

                MeerkatUI UIInstance = MeerkatUI.getUIInstance();
                int intProjectID = UIInstance.getActiveProjectTab().getProjectID();
                int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
                int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();

                // Grid to show all the User Input controls
                GridPane gridUserControls = new GridPane();
                gridUserControls.setPadding(new Insets(5));
                gridUserControls.setHgap(5);
                gridUserControls.setVgap(5);

                // Column constraints to define the two columns
                ColumnConstraints column1 = new ColumnConstraints();
                //column1.setPercentWidth(50);
                ColumnConstraints column2 = new ColumnConstraints();
                //column2.setPercentWidth(50);
                column2.setHgrow(Priority.ALWAYS);
                gridUserControls.getColumnConstraints().addAll(column1, column2);

                // ROW 1 - NUMBER OF CLUSTERS
                Label lblClusterCount = new Label(KMeansClusteringParam.getClusterCountLabel()) ;
                final Spinner<Integer> spnClusterCount = new Spinner();
                spnClusterCount.setValueFactory(
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(
                                  CommunityMiningConfig.KMEANS_CLUSTERCOUNT_MIN
                                , CommunityMiningConfig.KMEANS_CLUSTERCOUNT_MAX
                                , CommunityMiningConfig.KMEANS_CLUSTERCOUNT_DEFAULT
                                , CommunityMiningConfig.KMEANS_CLUSTERCOUNT_STEP
                        ));
                spnClusterCount.setEditable(true);
                spnClusterCount.setMaxWidth(100);

                GridPane.setHalignment(lblClusterCount, HPos.LEFT);
                gridUserControls.add(lblClusterCount, 0, 0);

                GridPane.setHalignment(spnClusterCount, HPos.LEFT);
                gridUserControls.add(spnClusterCount, 1, 0);

                // ROW 2 - ATTRIBUTE
                Label lblAttribute = new Label(KMeansClusteringParam.getAttributeLabel());            
                ComboBox cmbAttributeID = new ComboBox() ;
                cmbAttributeID.getItems().addAll(GraphAPI.getAllAttributesNames_Sorted(intProjectID, intGraphID, intTimeFrameIndex)) ;
                cmbAttributeID.setMaxWidth(100);

                GridPane.setHalignment(lblAttribute, HPos.LEFT);
                gridUserControls.add(lblAttribute, 0, 1);

                GridPane.setHalignment(cmbAttributeID, HPos.LEFT);
                gridUserControls.add(cmbAttributeID, 1, 1);


                // Error Label 
                Label lblError = new Label("") ;
                lblError.setVisible(false);

                // Adding the OK and Cancel Buttons at the end        
                Button btnOK = new Button (LangConfig.GENERAL_OK);
                btnOK.setOnAction(e -> {
                    ClearMiningResults.clearCommunityAssignment();

                    pController.updateStatusBar(true, StatusMsgsConfig.MINING_RESULTSCOMPUTING);
                    stgKMeansClustering.close();

                    String [] arrstrParameters = new String [2] ;
                    if (spnClusterCount.getValue() >= CommunityMiningConfig.KMEANS_CLUSTERCOUNT_MIN 
                            && spnClusterCount.getValue() <= CommunityMiningConfig.KMEANS_CLUSTERCOUNT_MAX) {
                        arrstrParameters[0] = CommunityMiningAPI.getKey_KMeans_NumClusters()+":"+spnClusterCount.getValue() ;

                        System.out.println("KMeans(): Cluster Count parameter is fine "+arrstrParameters[0]);
                    } else {
                        // Show Error Label Here
                        lblError.setText(ErrorMsgsConfig.COMMUNITYMINING_NUMBERCLUSTERSBETWEEN);
                        lblError.setVisible(true);
                        return ;
                    }

                    if (!cmbAttributeID.getSelectionModel().isEmpty()) {
                        arrstrParameters[1] = CommunityMiningAPI.getKey_KMeans_Attr()+":"+cmbAttributeID.getValue().toString() ;

                        System.out.println("KMeans(): AttributeID is"+arrstrParameters[1]);
                    } else {
                        lblError.setText(ErrorMsgsConfig.COMMUNITYMINING_SELECTATTRIBUTE);
                        lblError.setVisible(true);
                        return ;
                    }

                    // #DEBUG - Checking for the parameters
                    for (String strCurrent : arrstrParameters) {
                        System.out.println("KMeans(): Parameter : "+strCurrent);
                    }                
                    // #ENDDEBUG
                    
                    CommunityMiningAlgorithmDialog cmad = new CommunityMiningAlgorithmDialog();
                    cmad.runAlgorithm(intProjectID, intGraphID, intTimeFrameIndex, pstrMappingID, arrstrParameters, pController);

                    
                    
                /*    
                    // Call the Community corresponding Mining API                
                    CommunityMiningAPI.runCMAlgorithm(intProjectID, intGraphID, intTimeFrameIndex, pstrMappingID, arrstrParameters) ;

                    // Waiting for the Community Mining API to run
                    while(!CommunityMiningAPI.isDone(intProjectID, intGraphID, intTimeFrameIndex)) {
                        System.out.println("KMeans(): Is The Algorithm Done ? "+CommunityMiningAPI.isDone(intProjectID, intGraphID, intTimeFrameIndex)) ;
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
                    strSelectedAttribute = "" ;
                    stgKMeansClustering.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                });
                HBox hboxButtons = new HBox(btnOK, btnCancel);
                hboxButtons.setSpacing(10);
                hboxButtons.setPadding(new Insets(5,10,5,10));
                hboxButtons.setAlignment(Pos.CENTER);


                cmbAttributeID.valueProperty().addListener(new ChangeListener<String>() {
                    @Override 
                    public void changed(ObservableValue ov, String t, String t1) {
                        strSelectedAttribute = t1 ;

                        if (strSelectedAttribute.length() > 0) {
                            lblError.setVisible(false);
                        }
                    }    
                });

                // The scene consists of the following items
                // 1) Grid for Input Values
                // 2) HBox - buttons
                VBox vboxPanel = new VBox(gridUserControls,lblError, hboxButtons) ;


                Scene scnKMeansClustering = new Scene(vboxPanel);

                scnKMeansClustering.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                    if (evt.getCode().equals(KeyCode.ESCAPE)) {
                        stgKMeansClustering.close();
                        pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                    }
                });

                //stgKMeansClustering.initModality(Modality.WINDOW_MODAL);
                stgKMeansClustering.setTitle(KMeansClusteringParam.getTitle());
                stgKMeansClustering.setResizable(false);

                stgKMeansClustering.setScene(scnKMeansClustering);
                stgKMeansClustering.show();
                stgKMeansClustering.setOnCloseRequest(e -> {
                    e.consume();
                    stgKMeansClustering.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                });
            }
        } catch (Exception ex) {
            System.out.println(".Display(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
}
