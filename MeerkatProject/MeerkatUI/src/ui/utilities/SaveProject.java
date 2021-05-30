/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.utilities;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.ProjectAPI;
import config.AppConfig;
import config.LangConfig;
import config.SceneConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import globalstate.ProjectTab;
import java.util.ArrayList;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.dialogwindow.InfoDialog;

/**
 *
 * @author AICML Administrator
 */
public class SaveProject {
    
    
    public Boolean checkAndSaveProject(AnalysisController pController, Stage parentStage, int projectId){
            
        Boolean result = false;
            
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            ArrayList<String> listUntitledGraphs = new ArrayList<>();
            
            String strExtractSubGraphTitlePrefix = GraphAPI.getExtractSubGraphTitlePrefix();
            
            ProjectTab projectTab = UIInstance.getProject(projectId);
            if(projectTab!=null){
                
                for(int graphId : projectTab.getAllGraphTabs().keySet()){
                    if(projectTab.getGraphTab(graphId).getGraphTabTitle().contains(strExtractSubGraphTitlePrefix)){
                        listUntitledGraphs.add(projectTab.getGraphTab(graphId).getGraphTabTitle());
                    }
                }

                if(listUntitledGraphs.size()>0){

                    showDialogSaveUnTitledGraphs(listUntitledGraphs, parentStage, projectTab.getProjectName());

                }else{


                    // get its vertices and edges data from UI
                    Map<Integer, Map<Integer, Map<Integer, Double[]>>> mapProjectsAllVerticesLocation = projectTab.getProjectDataFromUI();
                    /* Save the given Project */
                    ProjectAPI.saveProject(projectTab.getProjectID(), mapProjectsAllVerticesLocation, AppConfig.EXTENSION_PROJECTFILE, AppConfig.PROJECTFILE_DELIMITER, AppConfig.GRAPHLIST_DELIMITER);



                    //Update Project Status
                    ProjectStatusTracker.updateProjectModifiedStatus(projectTab.getProjectID(), ProjectStatusTracker.eventProjectSaved);

                    //InfoDialog.Display(LangConfig.INFO_PROJECTSAVED, SceneConfig.INFO_TIMEOUT);

                    pController.updateStatusBar(false, StatusMsgsConfig.PROJECT_SAVED);
                    
                    result = true;
                }
            }
            return result;
    }
    
    private void showDialogSaveUnTitledGraphs(ArrayList<String> plistListUntitledGraphs, Stage pstgparentStage, String projectName){
    
        Stage stgDialogSaveUnTitledGraphs = new Stage();
        
        
        Label lblMessage = new Label("Please rename the following untitled graphs before saving the project "+ projectName +".");
        
        VBox vBoxList = new VBox();                
        vBoxList.setPadding(new Insets(10, 10, 10, 10));
        vBoxList.setSpacing(20);
        
        for(String graphTitle : plistListUntitledGraphs){
            Label labelGraphtitle = new Label(graphTitle);
            vBoxList.getChildren().add(labelGraphtitle);
        }

        
        
        VBox vbox = new VBox(lblMessage, vBoxList);                
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);
        
        Scene scnDialogSaveUnTitledGraphs = new Scene(vbox);
        
        stgDialogSaveUnTitledGraphs.initOwner(pstgparentStage);
        stgDialogSaveUnTitledGraphs.initModality(Modality.WINDOW_MODAL);
        stgDialogSaveUnTitledGraphs.setTitle("Rename graphs in this project"); 
        
        stgDialogSaveUnTitledGraphs.setResizable(false);
        
        stgDialogSaveUnTitledGraphs.setScene(scnDialogSaveUnTitledGraphs);
        stgDialogSaveUnTitledGraphs.showAndWait();
        stgDialogSaveUnTitledGraphs.setAlwaysOnTop(true);
    }
    
    
    
}
