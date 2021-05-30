/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.ProjectAPI;
import config.AppConfig;
import config.ErrorMsgsConfig;
import config.LangConfig;
import config.SceneConfig;
import config.StatusMsgsConfig;
import globalstate.GraphCanvas;
import globalstate.GraphTab;
import ui.dialogwindow.ErrorDialog;
import ui.dialogwindow.InfoDialog;
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import globalstate.ProjectTab;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.utilities.SaveProject;

/**
 *  Class Name      : Talat
 *  Created Date    : 2015-08-11
 *  Description     : Functionalities when the Menu Item is clicked
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-01-28      Talat           Changed the signature of Click()
 * 
*/
public class MenuItemSaveProject extends MenuItemGeneric implements IMenuItem {
    
    /**
     *  Constructor Name: MenuItemSaveProject()
     *  Created Date    : 2015-08-11
     *  Description     : Calls the super constructor to initialize the values
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrMenuItemDisplay : String
     *  @param pstrMenuItemClass : String
     *  @param pstrMenuItemIconPath : String
     *  
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public MenuItemSaveProject(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
        
    /**
     *  Method Name     : Click
     *  Created Date    : 2015-08-11
     *  Description     : Actions to be taken when the Save option is clicked
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController: AnalysisController
     *  @param pMenuItem: MenuItem
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-01-28      Talat           Changed the parameter from boolean to MenuItem
     *  2015-09-10      Talat           Info Dialog will be displayed
     *  2015-09-10      Talat           ProjectWriter.Save will have 4 parameters 
     *                                  1) Project Directory
     *                                  2) Project Name
     *                                  3) Project Extension
     *                                  4) Current Project Tab
     * 
    */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        
        if (UIInstance.getProjectTabCount() > 0) {
            
            int projectId = UIInstance.getActiveProjectTab().getProjectID();
            
            SaveProject saveProject = new SaveProject();
            saveProject.checkAndSaveProject(pController, pController.getPrimaryStage(), projectId);
            
            
            /*
            pController.updateStatusBar(true, StatusMsgsConfig.PROJECT_SAVING);
            

            ArrayList<String> listUntitledGraphs = new ArrayList<>();
            ProjectTab activeProjectTab = UIInstance.getActiveProjectTab();
            for(int graphId : activeProjectTab.getAllGraphTabs().keySet()){
                if(activeProjectTab.getGraphTab(graphId).getGraphTabTitle().contains("untitled")){
                    listUntitledGraphs.add(activeProjectTab.getGraphTab(graphId).getGraphTabTitle());
                }
            }
            if(listUntitledGraphs.size()>0){
                showDialogSaveUnTitledGraphs(listUntitledGraphs, pController.getPrimaryStage());
            }else{



                Map<Integer, Map<Integer, Map<Integer, Double[]>>> mapProjectsAllVerticesLocation = activeProjectTab.getProjectDataFromUI();
                
                ProjectAPI.saveProject(activeProjectTab.getProjectID(), mapProjectsAllVerticesLocation, AppConfig.EXTENSION_PROJECTFILE, AppConfig.PROJECTFILE_DELIMITER, AppConfig.GRAPHLIST_DELIMITER);

                // Since the save project will now happen at the logic layer, ProjectWriter.Save() will never be called
                // ProjectWriter.Save(strProjectDirectory, UIInstance.getActiveProject().getProjectName(), strProjectExtension, UIInstance.getActiveProject());



                //Update Project Status
                ProjectStatusTracker.updateProjectModifiedStatus(UIInstance.getActiveProjectTab().getProjectID(), ProjectStatusTracker.eventProjectSaved);
                InfoDialog.Display(LangConfig.INFO_PROJECTSAVED, SceneConfig.INFO_TIMEOUT);

                pController.updateStatusBar(false, StatusMsgsConfig.PROJECT_SAVED);
            
            }
            */
        } else {
            ErrorDialog.Display(ErrorMsgsConfig.ERROR_NOPROJECTSTOSAVE);
        }
              
    }
    
    public Boolean checkAndSaveProject(AnalysisController pController, Stage parentStage, int projectId){
            
        Boolean result = false;
            
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            ArrayList<String> listUntitledGraphs = new ArrayList<>();
            
            ProjectTab projectTab = UIInstance.getProject(projectId);
            if(projectTab!=null){
                
                for(int graphId : projectTab.getAllGraphTabs().keySet()){
                    if(projectTab.getGraphTab(graphId).getGraphTabTitle().contains("untitled")){
                        listUntitledGraphs.add(projectTab.getGraphTab(graphId).getGraphTabTitle());
                    }
                }

                if(listUntitledGraphs.size()>0){

                    showDialogSaveUnTitledGraphs(listUntitledGraphs, parentStage);

                }else{


                    // get its vertices and edges data from UI
                    Map<Integer, Map<Integer, Map<Integer, Double[]>>> mapProjectsAllVerticesLocation = projectTab.getProjectDataFromUI();
                    /* Save the given Project */
                    ProjectAPI.saveProject(projectTab.getProjectID(), mapProjectsAllVerticesLocation, AppConfig.EXTENSION_PROJECTFILE, AppConfig.PROJECTFILE_DELIMITER, AppConfig.GRAPHLIST_DELIMITER);



                    //Update Project Status
                    ProjectStatusTracker.updateProjectModifiedStatus(projectTab.getProjectID(), ProjectStatusTracker.eventProjectSaved);

                    InfoDialog.Display(LangConfig.INFO_PROJECTSAVED, SceneConfig.INFO_TIMEOUT);

                    pController.updateStatusBar(false, StatusMsgsConfig.PROJECT_SAVED);
                    
                    result = true;
                }
            }
            return result;
    }
    
    private void showDialogSaveUnTitledGraphs(ArrayList<String> plistListUntitledGraphs, Stage pstgparentStage){
    
        Stage stgDialogSaveUnTitledGraphs = new Stage();
        
        
        Label lblMessage = new Label("Please rename the following untitled graphs before saving the project.");
        
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
        stgDialogSaveUnTitledGraphs.setTitle("Are you sure"); 
        
        stgDialogSaveUnTitledGraphs.setResizable(false);
        
        stgDialogSaveUnTitledGraphs.setScene(scnDialogSaveUnTitledGraphs);
        stgDialogSaveUnTitledGraphs.showAndWait();
        stgDialogSaveUnTitledGraphs.setAlwaysOnTop(true);
    }
}


