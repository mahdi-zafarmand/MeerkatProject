/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.utilities;


import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.LoadingAPI;
import ca.aicml.meerkat.api.ProjectAPI;
import config.AppConfig;
import config.ErrorMsgsConfig;
import config.StatusMsgsConfig;
import exception.MeerkatException;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import globalstate.ProjectTab;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import meerkat.Utilities;
import ui.dialogwindow.ErrorDialog;



/**
 *
 * @author Talat
 * @since 2016
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2018-01-24      Talat           The file chooser has been pushed to ui.dialogwindow.OpenProject
 */
public class OpenProject {
    
    public static void load(AnalysisController pController, String pstrProjectFilePath) {

        try { 
            /* Add the Project to the list of Projects in the Logic Layer and retrieve the Project ID */
            int intProjectID = LoadingAPI.LoadProject();
            if (intProjectID < 0) {
                throw new MeerkatException (ErrorMsgsConfig.ERROR_CORRUPTEDFILE);
            }

            // Call the method in Logic that will Parse the Project (call through API)
            int intReturnValue = ProjectAPI.parseProjectFile(intProjectID, pstrProjectFilePath, AppConfig.PROJECTFILE_DELIMITER, AppConfig.GRAPHLIST_DELIMITER) ;
            // On successfull return, the project ID would be returned
//                if (intProjectID != intReturnValue) {
//                    ErrorDialog.Display(intReturnValue);
//                    return ; 
//               }

            Stage projectLoad = new Stage();
            ProgressBar progressbar = new ProgressBar(0.7);
            CallProjectLoadDialogue(projectLoad, progressbar);

            Task task = new Task() {
                @Override
                protected Object call() throws Exception {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
            
            ProjectTab currentProjectTab = new ProjectTab(intProjectID, ProjectAPI.getProjectName(intProjectID));
            currentProjectTab.setProjectDirectory(ProjectAPI.getProjectDirectory(intProjectID));
            currentProjectTab.setInputFilePath(ProjectAPI.getProjectRawFiles(intProjectID));
            currentProjectTab.setProjectIconPath(ProjectAPI.getProjectIconPath(intProjectID));
            currentProjectTab.setSavedTime(ProjectAPI.getProjectSavedTime(intProjectID));

            /*
            Following Line added here from bottom of this method
            Added this line from below as we need to have an active projectID before we do the following work.
            */
            
            // set this project as the active project
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            UIInstance.addProject(currentProjectTab);
            
            // Retrieve all the Graphs in the current project
            List<String> lststrGraphs = ProjectAPI.getGraphDetails(intProjectID, AppConfig.DELIMITER_COMMA);

            for (String strGraphDetail : lststrGraphs) {
                String [] arrstrGraphDetails = strGraphDetail.split(AppConfig.DELIMITER_COMMA) ;
                // System.out.println("=====********************************============ MeerkatUI: OpenProject.load() : GraphTab Object in UI  starting now");
                Long time1 = System.currentTimeMillis();

                GraphTab graphTab = new GraphTab(
                          Integer.parseInt(arrstrGraphDetails[0])
                        , Integer.parseInt(arrstrGraphDetails[1])
                        , Utilities.getGraphType(Integer.parseInt(arrstrGraphDetails[2]))
                        , arrstrGraphDetails[3]
                        , arrstrGraphDetails[4]
                        , arrstrGraphDetails[5]);

                Long time2 = System.currentTimeMillis();
                // System.out.println("=====****************************============= MeerkatUI: OpenProject.load() : graph plotting in UI finished now  --  time taken = " + (time2-time1));



                currentProjectTab.addGraphTab(graphTab);
                //currentProjectTab.setActiveGraphID(Integer.parseInt(arrstrGraphDetails[1])) ;// Set the current graph to be the active graph in the project;
                //setting the comMining Status if the loaded graph already has existing communities.
                for(int tf =0;tf<graphTab.getTotalTimeFrames();tf++){
                    if(graphTab.getAccordionTabValues(tf).getCommunitiesValues().getCommunityVertexMapping().size()>0)
                        graphTab.setComMiningStatus(Boolean.TRUE, tf);
                }

                //update the minimap
                MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getMinimapDelegator().updateMinimap();
                MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getMinimapDelegator().changeMinimapMasking();
            }


            // Retrieve the list of all the Textual Networks
            /* REMOVED FROM VERSION 1.0
            List<String> lststrTextuals = ProjectAPI.getTextualNetworkDetails(intProjectID, AppConfig.DELIMITER_COMMA);
            for (String strTextualNetworkDetail : lststrTextuals) {
                String [] arrstrTextualNetworkDetails = strTextualNetworkDetail.split(AppConfig.DELIMITER_COMMA) ;
                TextualTab txtTab = new TextualTab(
                          Integer.parseInt(arrstrTextualNetworkDetails[0])
                        , Integer.parseInt(arrstrTextualNetworkDetails[1])
                        , arrstrTextualNetworkDetails[2]);
                currentProjectTab.addTextualTab(txtTab);
                currentProjectTab.setActiveTextualID(Integer.parseInt(arrstrTextualNetworkDetails[1]));
            }
            */


            // Adding the Project as a ProjectTab
            // Adding the Project as a ProjectTab, THIS LINE HAS NOW MOVED ABOVE (PLS SEE CHANGES)
            //UIInstance.addProject(currentProjectTab);


            // Updating the UI, add currentprojectTab to tabpaneProject defined in fxml file
            
            pController.addProject(UIInstance.getActiveProjectTab());
                            }
            });
                    return null;
                            }
            };
            Thread th = new Thread(task);
            th.start();
            try{
            th.join();
                    }catch(Exception e){
                        
                    }
            progressbar.setProgress(1.0);
            projectLoad.close();
            // Once the UI is updated, a second update is required to add the MiniMaps to the Graphs. 
            // The reason is to avoid a throwaway scene that would fetch the dimensions of the GraphCanvas
            // int intMiniMapReturn = UIInstance.getActiveProject().addMiniMap();                
            // System.out.println("MenuItemOpenProject.Click(): MiniMaps added: "+intMiniMapReturn);

            // Thread.sleep(10000);

            pController.updateStatusBar(false, StatusMsgsConfig.PROJECT_OPENED);

            //fetching any graphs with load error:

            Map<String, Integer> graphErrorMap = ProjectAPI.getProjectGraphErrors(intProjectID);

            if(graphErrorMap.size()>0)
                ErrorDialog.Display(ErrorMsgsConfig.ERROR_GRAPHLOAD+graphErrorMap.keySet().toString());

//                if(intReturnValue==ErrorCode.ERROR_GRAPHLOAD.getId()){
//                    System.out.println("OpenProject load(): Read Error!");
//                    ErrorDialog.Display(intReturnValue);
//                }
        } catch (MeerkatException exMeerkat) {
            ErrorDialog.Display(exMeerkat.getMessage());
        } 
    }

    private static void CallProjectLoadDialogue(Stage projectLoad, ProgressBar progressbar) {
          
        projectLoad.centerOnScreen();
        StackPane progressPane = new StackPane();

        Label lblError = new Label("Project Loading...");
        lblError.setAlignment(Pos.CENTER);

        VBox vb = new VBox(progressbar, lblError);
        vb.setAlignment(Pos.CENTER);
        progressPane.getChildren().add(vb);
        progressPane.setAlignment(Pos.CENTER);

        projectLoad.initModality(Modality.APPLICATION_MODAL);
        projectLoad.setTitle("Load Project");
        projectLoad.setResizable(false);

        Scene scnErrorDialog = new Scene(progressPane, 200, 100);
        projectLoad.setScene(scnErrorDialog);
        projectLoad.show();

    }
}
