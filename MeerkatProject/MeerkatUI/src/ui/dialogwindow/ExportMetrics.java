/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import accordiontab.MetricElements;
import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.analysis.GraphMetricAPI;
import config.ErrorCode;
import globalstate.MeerkatUI;
import graphelements.UIEdge;
import graphelements.UIVertex;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author sankalp
 */
public class ExportMetrics {
    
    
    public static void Display(AnalysisController pController) {        
        
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        
        FileChooser filechooser = new FileChooser();
        filechooser.setInitialDirectory((new File(System.getProperty("user.dir"))));
        filechooser.setTitle("Choose Metrics Filename");
        filechooser.setInitialFileName("metrics.json");
        File file = filechooser.showSaveDialog(dialog);
        
        String filePath;
        
        if(file!= null){
            filePath = file.getAbsolutePath();
        }else{
            filePath=null;
        }
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
        int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
        
        Set<UIVertex> setSelectedVertices = new HashSet<>();        
        setSelectedVertices.addAll(UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getVertices().values());
        
        Set<UIEdge> setSelectedEdges = new HashSet<>();
        setSelectedEdges.addAll(UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getEdges().values());

        int intcurrentTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
        
        if(filePath!=null)
            ExportMetrics.exportMetrics(intProjectID, intGraphID, intcurrentTimeFrameIndex, setSelectedVertices, setSelectedEdges, filePath);
        
    }
    
    public static void exportMetrics(int intProjectID, int intGraphID, int intcurrentTimeFrameIndex, 
            Set<UIVertex> psetSelectedVertices, Set<UIEdge> psetSelectedEdges, String filePath) {
                
        MetricElements mtrElements = MetricElements.getInstance();
        Map<String, String[]> mapMetricParameter = new HashMap<>();        
        
        //get all metrics and parameters
        for (String strKey : mtrElements.getMetricsIDTextMapping().keySet()) {            
            String strMetricID = mtrElements.getMetricID(strKey);
            System.out.println("METRIC Name :"+ strMetricID);
            String [] arrstrMetricParameter = new String [] {mtrElements.getMetricParameter(strMetricID)};
            mapMetricParameter.put(strMetricID, arrstrMetricParameter);
        }
        
        int intErrorCode = GraphMetricAPI.exportMetrics(intProjectID, intGraphID, intcurrentTimeFrameIndex, mapMetricParameter, filePath); 
        
        if(intErrorCode==ErrorCode.ERROR_SAVECOMMUNITY.getId()){
            System.out.println("ExportMetrics exportMetrics(): Save Error!");
            ErrorDialog.Display(intErrorCode);
        }else{
            InfoDialog.Display("Metrics saved successfully", 3);
        }
    }
}
