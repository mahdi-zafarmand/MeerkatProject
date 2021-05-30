/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communitymining.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.analysis.CommunityMiningAPI;
import communitymining.parameters.SameAttributeValueParam;
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
import javafx.scene.control.TextField;
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
 *  Class Name      : SameAttributeValue
 *  Created Date    : 2016-04-08
 *  Description     : Class to Display the UI Components to select the parameters of algorithm SameAttributeValue
 *  Version         : 1.0
 *  @author         : Talat
 * 
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class SameAttributeValue {
    
    private static String strAttributeType = "" ;
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-04-08
     *  Description     : Displays the Window to select parameters for algorithm SameAttributeValue
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     *  @param pstrMappingID : String
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
                Stage stgSameAttributeValue = new Stage();
                stgSameAttributeValue.initModality(Modality.APPLICATION_MODAL);

                MeerkatUI UIInstance = MeerkatUI.getUIInstance();
                int intProjectID = UIInstance.getActiveProjectTab().getProjectID();
                int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
                int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();

                Label lblInfo = new Label(SameAttributeValueParam.getMessageLabel());

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

                // Row 1 - ATTRIBUTE NAME
                Label lblAttribute = new Label(SameAttributeValueParam.getAttributeLabel());            
                ComboBox cmbAttributeID = new ComboBox() ;
                cmbAttributeID.getItems().addAll(GraphAPI.getAllAttributesNames_Sorted(intProjectID, intGraphID, intTimeFrameIndex)) ;
                cmbAttributeID.setMaxWidth(100);


                GridPane.setHalignment(lblAttribute, HPos.LEFT);
                gridUserControls.add(lblAttribute, 0, 0);

                GridPane.setHalignment(cmbAttributeID, HPos.LEFT);
                gridUserControls.add(cmbAttributeID, 1, 0);

                // Row 2 - MULTIPLE VALUES ?
                Label lblMultipleValues = new Label(SameAttributeValueParam.getMultipleValuesLabel()) ;
                CheckBox chbMultipleValues = new CheckBox();
                chbMultipleValues.setSelected(CommunityMiningConfig.SAMEVALUEATTR_MULTIPLEVALUES);

                GridPane.setHalignment(lblMultipleValues, HPos.LEFT);
                gridUserControls.add(lblMultipleValues, 0, 1);

                GridPane.setHalignment(chbMultipleValues, HPos.LEFT);
                gridUserControls.add(chbMultipleValues, 1, 1);

                // Row 3 - SEPARATOR
                Label lblSeparator = new Label(SameAttributeValueParam.getSeparatorLabel()) ;
                lblSeparator.setVisible(false);
                TextField txtSeparator = new TextField(CommunityMiningConfig.SAMEVALUEATTR_SEPERATOR);
                txtSeparator.setVisible(false);
                txtSeparator.setMaxWidth(200);
                if(chbMultipleValues.isSelected()){
                    lblSeparator.setVisible(true);
                    txtSeparator.setVisible(true);
                }

                GridPane.setHalignment(lblSeparator, HPos.LEFT);
                gridUserControls.add(lblSeparator, 0, 2);

                GridPane.setHalignment(chbMultipleValues, HPos.LEFT);
                gridUserControls.add(txtSeparator, 1, 2);

                chbMultipleValues.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    if (new_val) {
                        lblSeparator.setVisible(true);
                        txtSeparator.setVisible(true);
                    } else {
                        lblSeparator.setVisible(false);
                        txtSeparator.setVisible(false);
                    }
                });

                // Error Label 
                Label lblError = new Label("") ;
                lblError.setVisible(false);

                // Adding the OK and Cancel Buttons at the end        
                Button btnOK = new Button (LangConfig.GENERAL_OK);
                btnOK.setOnAction(e -> {
                    ClearMiningResults.clearCommunityAssignment();

                    pController.updateStatusBar(true, StatusMsgsConfig.MINING_RESULTSCOMPUTING);
                    stgSameAttributeValue.close();

                    String [] arrstrParameters = new String [3] ;

                    if (strAttributeType != null && !strAttributeType.isEmpty()) {
                        arrstrParameters[0] =  CommunityMiningAPI.getKey_SAV_Attr()+":"+strAttributeType ;
                    } else {
                        lblError.setText(ErrorMsgsConfig.COMMUNITYMINING_SELECTATTRIBUTE);
                        lblError.setVisible(true);
                        return ;
                    }
                    arrstrParameters[1] = CommunityMiningAPI.getKey_SAV_MultipleValue()+":"+chbMultipleValues.isSelected() ;

                    if (chbMultipleValues.isSelected()) {
                        if (txtSeparator.getText().length() > 0) {
                            arrstrParameters[2] = CommunityMiningAPI.getKey_SAV_Separator()+":"+txtSeparator.getText();
                        } else {
                            lblError.setText(ErrorMsgsConfig.COMMUNITYMINING_SEPRATOREMPTY);
                            lblError.setVisible(true);
                            return ;
                        }
                    } else {
                        arrstrParameters[2] = CommunityMiningAPI.getKey_SAV_Separator()+":null";
                    }

                    // #DEBUG - Checking for the parameters
                    for (String strCurrent : arrstrParameters) {
                        System.out.println("SAV(): Parameter : "+strCurrent);
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
                    strAttributeType = "" ;
                    stgSameAttributeValue.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                });
                HBox hboxButtons = new HBox(btnOK, btnCancel);
                hboxButtons.setSpacing(10);
                hboxButtons.setPadding(new Insets(5,10,5,10));
                hboxButtons.setAlignment(Pos.CENTER);

                cmbAttributeID.valueProperty().addListener(new ChangeListener<String>() {
                    @Override 
                    public void changed(ObservableValue ov, String t, String t1) {
                        strAttributeType = t1 ;

                        if (strAttributeType.length() > 0) {
                            lblError.setVisible(false);
                        }
                    }    
                });

                // The scene consists of the following items
                // 1) Label - Info Label
                // 2) Grid for Input Values
                // 3) HBox - buttons
                VBox vboxPanel = new VBox(gridUserControls, lblError, hboxButtons) ;


                Scene scnSameAttributeValue = new Scene(vboxPanel);

                scnSameAttributeValue.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                    if (evt.getCode().equals(KeyCode.ESCAPE)) {
                        stgSameAttributeValue.close();
                        pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                    }
                });

                //stgSameAttributeValue.initModality(Modality.WINDOW_MODAL);
                stgSameAttributeValue.setTitle(SameAttributeValueParam.getTitle());
                stgSameAttributeValue.setResizable(false);

                stgSameAttributeValue.setScene(scnSameAttributeValue);
                stgSameAttributeValue.show();
                stgSameAttributeValue.setOnCloseRequest(e -> {
                    e.consume();
                    stgSameAttributeValue.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                });
            }
        } catch (Exception ex) {
            System.out.println(".Display(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
}
