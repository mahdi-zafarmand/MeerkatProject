/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphelements;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import config.ErrorCode;
import globalstate.MeerkatUI;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.dialogwindow.ErrorDialog;
import ui.dialogwindow.InfoDialog;

/**
 *
 * @author sankalp
 */
public class SaveCommunity {
    
    public static void saveCommunity(int intProjectID, int intGraphID, int intcurrentTimeFrameIndex, 
            Set<UIVertex> psetSelectedVertices, Set<UIEdge> psetSelectedEdges, String filePath){
        
        List<Integer> lstintSelectedVertexIDs = new ArrayList<>();        
        for (UIVertex uiVertex : psetSelectedVertices) {
                lstintSelectedVertexIDs.add(uiVertex.getVertexHolder().getID()) ;
        }
        
        List<Integer> lstintSelectedEdgeIDs = new ArrayList<>();        
        for (UIEdge uiEdge : psetSelectedEdges) {
                lstintSelectedEdgeIDs.add(uiEdge.getID()) ;
        }
        
        int intErrorCode = GraphAPI.saveCommunity(intProjectID, intGraphID, intcurrentTimeFrameIndex, lstintSelectedVertexIDs, lstintSelectedEdgeIDs, filePath); 
        
        if(intErrorCode==ErrorCode.ERROR_SAVECOMMUNITY.getId()){
            System.out.println("SaveCommunity saveCommunity(): Save Error!");
            ErrorDialog.Display(intErrorCode);
        }else{
            InfoDialog.Display("Comunities saved successfully", 3);
        }
        
    }

    public static void Display(AnalysisController pController) {        
        
        //TODO add file chooser dialog
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        
        FileChooser filechooser = new FileChooser();
        filechooser.setInitialDirectory((new File(System.getProperty("user.dir"))));
        filechooser.setTitle("choose file name");
        filechooser.setInitialFileName("communities.json");
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
            SaveCommunity.saveCommunity(intProjectID, intGraphID, intcurrentTimeFrameIndex, setSelectedVertices, setSelectedEdges, filePath);
        
    }
    
}
