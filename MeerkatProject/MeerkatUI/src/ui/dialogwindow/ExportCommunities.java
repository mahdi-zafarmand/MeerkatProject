/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import accordiontab.MetricElements;
import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.analysis.GraphMetricAPI;
import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import java.util.Map;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *  Class Name      : ExportCommunities
 *  Created Date    : 2016-07-25
 *  Description     : Export Community dialog boxes
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class ExportCommunities {
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-07-25
     *  Description     : Displays the Window to select the metrics required to expose
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     * 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Display (AnalysisController pController, int pintProjectID, int pintGraphID) {
        try {
            Stage stgExportCommunities = new Stage();
            GridPane gridMetrics = new GridPane();
            gridMetrics.setVgap(4);
            gridMetrics.setPadding(new Insets(5, 5, 5, 5));
            
            MetricElements mtrElements = MetricElements.getInstance();; // Mapping between the text and the ID
            int intMetricsElements = mtrElements.getMetricsIDTextMapping().size() ;
            boolean [] arrblnWasRunBefore = new boolean[intMetricsElements+1] ;
            
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            int intTotalTimeFrames = UIInstance.getProject(pintProjectID).getGraphTab(pintGraphID).getTotalTimeFrames();
            
            int intIndex = 1 ;            
            for (String strKey : mtrElements.getMetricsIDTextMapping().keySet()) {
                String strMetricID = mtrElements.getMetricID(strKey);
                String [] arrstrMetricParameter = new String [] {mtrElements.getMetricParameter(strMetricID)};
                final int intColumnNumber = intIndex ;
                
                arrblnWasRunBefore[intColumnNumber] = false ;

                CheckBox chbCurrentItem = new CheckBox(strKey);
                chbCurrentItem.setDisable(false);
                chbCurrentItem.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    if (newValue) { // If the Checkbox is checked                                        
                        // System.out.println("StatisticsValue.StatisticsValue(): Was x run?: "+arrblnWasRunBefore[intColumnNumber]);
                        if (!arrblnWasRunBefore[intColumnNumber]) {

                            for (int intTimeFrameIndex=0; intTimeFrameIndex<intTotalTimeFrames; intTimeFrameIndex++) {
                                // Invoke the API
                                GraphMetricAPI.computeCentrality(pintProjectID, pintGraphID, intTimeFrameIndex, strMetricID, arrstrMetricParameter) ;
                                while (!GraphMetricAPI.isDone(pintProjectID, pintGraphID, intTimeFrameIndex, strMetricID, arrstrMetricParameter)) {
                                    
                                }                        
                                Map<Integer, Double> mapResults = GraphMetricAPI.getScores(pintProjectID, pintGraphID, intTimeFrameIndex, strMetricID, arrstrMetricParameter) ;
                            }
                        }
                     
                    }
                });            
                gridMetrics.add(chbCurrentItem, (intIndex+1)%2, (intIndex-1)/2);

                intIndex++;
            }
            
            // Adding the OK and Cancel Buttons at the end        
            Button btnOK = new Button (LangConfig.GENERAL_OK);
            btnOK.setOnAction(e -> {
                
                // Write down the Communities to a file
                
                
                // Close the Stage 
                stgExportCommunities.close();
                pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            });
            Button btnCancel = new Button (LangConfig.GENERAL_CANCEL);
            btnCancel.setOnAction(e -> {                
                stgExportCommunities.close();
                pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            });
            
            HBox hboxButtons = new HBox(btnOK, btnCancel);
            hboxButtons.setSpacing(10);
            hboxButtons.setPadding(new Insets(5,10,5,10));
            hboxButtons.setAlignment(Pos.CENTER);
            
            // The scene consists of the following items
            // 1) Grid for Input Values
            // 2) HBox - buttons
            VBox vboxPanel = new VBox(gridMetrics, hboxButtons) ;
            
            
            Scene scnExportCommunities = new Scene(vboxPanel);
            scnExportCommunities.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                if (evt.getCode().equals(KeyCode.ESCAPE)) {
                    stgExportCommunities.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
                }
            });
        
            stgExportCommunities.initModality(Modality.APPLICATION_MODAL);
            stgExportCommunities.setTitle("Export Metrics");
            stgExportCommunities.setResizable(false);
            stgExportCommunities.setOnCloseRequest(event -> {
                pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            });

            stgExportCommunities.setScene(scnExportCommunities);
            stgExportCommunities.show();
            
            pController.updateStatusBar(true, StatusMsgsConfig.WAITING_USERINPUT);
        } catch (Exception ex) {
            System.out.println("NeighborhoodDegreeDialog.Display(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
    
}
