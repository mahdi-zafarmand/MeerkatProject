/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.analysis.LinkPredictionAPI;
import linkprediction.LinkPredictionDialog;
import communitymining.parameters.FastModularityParam;
import config.LangConfig;
import config.LinkPredictionConfig;
import config.StatusMsgsConfig;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import linkprediction.ClearLinkPredictionResults;

/**
 *  Class Name      : LinkPrediction
 *  Created Date    : 2018-03-21
 *  Description     : Class to Display the UI Components to select the parameters of Link Prediction
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class LinkPrediction {
    
    private static String strSelectedValue = "" ;
    private static BooleanProperty blnIsAlgorithmNotSelected = new SimpleBooleanProperty(false);
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2018-03-21
     *  Description     : Displays the Window to select parameters for algorithm LinkPrediction
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
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            GraphTab currentGraph = UIInstance.getActiveProjectTab().getActiveGraphTab();
            int inttimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            
            boolean linkPredictionStatus = ClearLinkPredictionResults.checkLinkPredictionStatus();
            if(linkPredictionStatus){
                Stage stgLinkPrediction = new Stage();
                stgLinkPrediction.initModality(Modality.APPLICATION_MODAL);
                String [] arrstrParameters = new String[1] ;

                int intProjectId = UIInstance.getActiveProjectTab().getProjectID();
                int intGraphId = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID();
                int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
                int intVertexCount = GraphAPI.getVertexCount(intProjectId, intGraphId, intTimeFrameIndex);
                int intEdgeCount = GraphAPI.getEdgeCount(intProjectId, intGraphId, intTimeFrameIndex);
                int intMaxPredEdges = (intVertexCount * (intVertexCount - 1))/2 - intEdgeCount ;
                
                double dblMaxPredMetric = 100.0;
                double dblMinPredMetric = 0.0;
                
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

                final Spinner<Integer> spnTopN = new Spinner();
                spnTopN.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, intMaxPredEdges, 1, 1));
                spnTopN.setEditable(true);
                spnTopN.setMaxWidth(100);
                spnTopN.setDisable(false);
                spnTopN.setTooltip(new Tooltip(LinkPredictionConfig.getTooltip_TopN()));
                
                final Spinner<Double> spnMetric = new Spinner();
                spnMetric.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(dblMinPredMetric, dblMaxPredMetric, 0.0, 0.1));
                spnMetric.setEditable(true);
                spnMetric.setMaxWidth(100);
                spnMetric.setDisable(true);
                spnMetric.setTooltip(new Tooltip(LinkPredictionConfig.getTooltip_Metric()));
                
                Button btnOK = new Button (LangConfig.GENERAL_OK);
                Button btnCancel = new Button (LangConfig.GENERAL_CANCEL);
                
                Label lblError = new Label("This is an error msg");
                lblError.setVisible(false);
                
                ToggleGroup tgMeasure = new ToggleGroup();
                
                RadioButton rbTopN = new RadioButton(LinkPredictionConfig.getTopN());
                rbTopN.setSelected(true);
                arrstrParameters[0] = LinkPredictionAPI.getLocalNaiveBayesIndex_TopN()+":"+String.valueOf(spnTopN.getValue()) ;
                rbTopN.setToggleGroup(tgMeasure);
                rbTopN.setOnAction(e -> {
                    spnTopN.setDisable(false); // Enable the Spinner & disable the other spinner
                    spnMetric.setDisable(true);
                    int value = spnTopN.getValue(); // Do the sanity Check on the spinner
                });
                
                RadioButton rbMetric = new RadioButton(LinkPredictionConfig.getMetric());
                rbMetric.setOnAction(e -> {
                    spnMetric.setDisable(false); // Enable the Spinner & disable the other spinner
                    spnTopN.setDisable(true);
                    double value = spnMetric.getValue(); // Do the sanity Check on the spinner
                });
                
                rbMetric.setToggleGroup(tgMeasure);
                
                spnTopN.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                    
                    String strError = LinkPredictionConfig.getError_TopN() + Integer.toString(1) 
                                + LangConfig.GENERAL_AND + Integer.toString(intMaxPredEdges);    
                    try {
                        int val = Integer.parseInt(newValue.toString());
                        if(val >= 1 && val <= intMaxPredEdges){
                            lblError.setVisible(false);
                            // btnOK.setDisable(false);
                            arrstrParameters[0] = LinkPredictionAPI.getLocalNaiveBayesIndex_TopN()+":"+String.valueOf(val) ;
                        } else {
                            lblError.setText(strError);
                            lblError.setVisible(true);
                            // btnOK.setDisable(true);
                        }
                    } catch (Exception ex){
                        lblError.setText(strError);
                        lblError.setVisible(true);
                        // btnOK.setDisable(true);
                    }
                });
                
                spnMetric.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                    
                    String strError = LinkPredictionConfig.getError_Metric() + Double.toString(dblMinPredMetric) 
                                + LangConfig.GENERAL_AND + Double.toString(dblMaxPredMetric); 
                    try {
                        double val = Double.parseDouble(newValue.toString());
                        if(val >= dblMinPredMetric && val <= dblMaxPredMetric){
                            lblError.setVisible(false);
                            btnOK.setDisable(false);
                            arrstrParameters[0] = LinkPredictionAPI.getLocalNaiveBayesIndex_TopMetric()+":"+String.valueOf(val) ;
                        } else {
                            lblError.setText(strError);
                            lblError.setVisible(true);
                            btnOK.setDisable(true);
                        }
                    } catch (Exception ex){
                        lblError.setText(strError);
                        lblError.setVisible(true);
                        btnOK.setDisable(true);
                    }
                });
                
                // Row 1
                GridPane.setHalignment(rbTopN, HPos.LEFT);
                gridUserControls.add(rbTopN, 0, 0);
                GridPane.setHalignment(rbMetric, HPos.LEFT);
                gridUserControls.add(rbMetric, 1, 0);
                
                // Row 2
                GridPane.setHalignment(spnTopN, HPos.LEFT);
                gridUserControls.add(spnTopN, 0, 1);
                GridPane.setHalignment(spnMetric, HPos.LEFT);
                gridUserControls.add(spnMetric, 1, 1);
                
                // Adding the OK and Cancel Buttons at the end
                btnOK.disableProperty().bind(blnIsAlgorithmNotSelected);
                btnOK.setOnAction(e -> {
                    
                    e.consume();
                    stgLinkPrediction.close();
                    
                    int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
                    int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
                    int currentTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
                    
                    String pstrCMAlgName = "localNaiveBayesPredictor";
                    LinkPredictionDialog linkPredictionAlgo = new LinkPredictionDialog();
                    linkPredictionAlgo.runAlgorithm(intProjectID, intGraphID, intTimeFrameIndex, pstrCMAlgName, arrstrParameters, pController);
                    
                    /*
                    LinkPredictionAPI.runLinkPredictionAlgorithm(intProjectID, intGraphID, currentTimeFrameIndex, pstrCMAlgName, arrstrParameters);
                    */
                });
                
                btnCancel.setOnAction(e -> {
                    stgLinkPrediction.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                });
                
                HBox hboxButtons = new HBox(btnOK, btnCancel);
                hboxButtons.setSpacing(10);
                hboxButtons.setPadding(new Insets(5,10,5,10));
                hboxButtons.setAlignment(Pos.CENTER);


                tgMeasure.selectedToggleProperty().addListener(
                        (ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) -> {
                    RadioButton rbtnSelectedLang = (RadioButton)t1.getToggleGroup()
                            .getSelectedToggle(); // Cast object to radio button                        
                    strSelectedValue = rbtnSelectedLang.getText() ;

                    if (strSelectedValue.length() > 0) {
                        lblError.setVisible(false);
                        blnIsAlgorithmNotSelected.set(false);
                    }
                });

                VBox vboxPanel = new VBox(gridUserControls, lblError, hboxButtons) ;
                vboxPanel.setSpacing(20);
                vboxPanel.setPadding(new Insets(10, 20, 10, 20));


                Scene scnLinkPrediction = new Scene(vboxPanel);
                scnLinkPrediction.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                    if (evt.getCode().equals(KeyCode.ESCAPE)) {
                        stgLinkPrediction.close();
                        pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
                    }
                });

                //stgFastModularity.initModality(Modality.WINDOW_MODAL);
                stgLinkPrediction.setTitle(FastModularityParam.getTitle());
                stgLinkPrediction.setResizable(false);

                stgLinkPrediction.setScene(scnLinkPrediction);
                stgLinkPrediction.show();
                stgLinkPrediction.setOnCloseRequest(e -> {
                    e.consume();
                    stgLinkPrediction.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                });
            }
        } catch (Exception ex) {
            System.out.println(".Display(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
}
