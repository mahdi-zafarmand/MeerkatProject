/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.utilities;

import analysisscreen.AnalysisController;
import config.ErrorCode;
import config.ErrorMsgsConfig;
import config.FileFilter;
import config.GraphConfig;
import config.ImportFileFilters;
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.dialogwindow.ErrorDialog;

/**
 *
 * @author Talat-AICML
 */
public class LoadGraph {
    
    static int graphID;
    
    public static void load(AnalysisController pController) {
        try {
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        if (UIInstance.getActiveProjectTab() == null) {
            System.out.println("LoadGraph.load(): There is no active project yet");
            ErrorDialog.Display(ErrorMsgsConfig.ERROR_ATLEASTONEPROJECT);
        } else {
            
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID();
            
            System.out.println("LoadGraph.load(): ProjectID : "+intProjectID);
        
            /* Open the Dialog Box to allow the user to select the file */
            FileChooser fileChooser = new FileChooser();

            for (FileFilter currentFilter : ImportFileFilters.getFileFilters()) {
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(currentFilter.getFileDisplay()+"(*"+currentFilter.getFileExtension()+")", "*"+currentFilter.getFileExtension());
                fileChooser.getExtensionFilters().add(extFilter);
            }       
            File file = fileChooser.showOpenDialog(pController.getPrimaryStage());
            
            if(file!=null){
                
                //check if the current active project already has the graph with same name
//                MAHDI: there is a problem here, the application should be able to open two files 
//              with the same name (even with the same address, meaning the exact same file) and manage to
//              act based on that. For example treat them as filename and filename(1).
                if (UIInstance.getActiveProjectTab().isGraphOpen(file.getName())) {
                    System.out.println("MAHDI: test -> two graphs with the same name: " + file.getName());
                    ErrorDialog.Display(ErrorMsgsConfig.ERROR_GRAPHALREADYOPEN);
                    return;
                }
                
                Stage projectLoad = new Stage();
                ProgressBar progressbar = new ProgressBar(0.7);
                CallGraphLoadDialogue(projectLoad, progressbar);
                
                String strReaderID = ImportFileFilters.getReaderID(file.getAbsolutePath());
                GraphConfig.GraphType graphType = ImportFileFilters.getGraphType(file.getName());
                graphID = UIInstance.getActiveProjectTab().addFileToProject(file.getAbsolutePath(), strReaderID, graphType);
                
                progressbar.setProgress(1.0);
                projectLoad.close();
                
                //Update Project Status
                ProjectStatusTracker.updateProjectModifiedStatus(UIInstance.getActiveProjectTab().getProjectID(), ProjectStatusTracker.eventNewGraphLoaded);

                /* Update the UI components in the Project Tab */
                pController.updateProjectUI(UIInstance.getActiveProjectTab());
                
                if(graphID==ErrorCode.ERROR_GRAPHLOAD.getId()){
                    System.out.println("OpenProject load(): Read Error!");
                    ErrorDialog.Display(ErrorMsgsConfig.ERROR_GRAPHLOAD+file.getAbsolutePath());
                }
            }
           
            
        } 
        } catch (Exception ex) {
            System.out.println("LoadGraph.load(): EXCEPTION") ;
            ex.printStackTrace();
        }
    }
    
    private static void CallGraphLoadDialogue(Stage projectLoad, ProgressBar progressbar) {
        
        projectLoad.centerOnScreen();
        StackPane progressPane = new StackPane();
        
        Label lblError = new Label("Graph Loading...");
        lblError.setAlignment(Pos.CENTER);
        
        VBox vb = new VBox(progressbar, lblError);
        vb.setAlignment(Pos.CENTER);
        progressPane.getChildren().add(vb);
        progressPane.setAlignment(Pos.CENTER);
                
        projectLoad.initModality(Modality.APPLICATION_MODAL);
        projectLoad.setTitle("Load Graph");
        projectLoad.setResizable(false);
        
        Scene scnErrorDialog = new Scene(progressPane, 200, 100);
        projectLoad.setScene(scnErrorDialog);
        projectLoad.show();        
    }
}