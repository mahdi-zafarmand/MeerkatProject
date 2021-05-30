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
import config.GraphConfig;

import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import meerkat.Utilities;
import ui.dialogwindow.InfoDialog;

/**
 *
 * @author AICML Administrator
 */
public class RenameGraph {
    
    
    
    
     public static void rename(AnalysisController pController) {
     
         /*
         1. get active graph is from ui
         
         2. remove it from Logic delete its file from hard disk
         3. remove from Project.java mapGraphs
         4. modify .mprj file
          
         
         5. remove it from graphMap in Project tab
         6. remove it from project tab
         7. remove tree item
         */
        
        try {
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            
            GraphTab activeGraphTab = UIInstance.getActiveProjectTab().getActiveGraphTab();
            if (activeGraphTab != null) {

                showInputWindow(pController);
                
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
     }
     
     public static void rename(AnalysisController pController, GraphTab graphTab) {
     
         /*
         1. get active graph is from ui
         
         2. remove it from Logic delete its file from hard disk
         3. remove from Project.java mapGraphs
         4. modify .mprj file
          
         
         5. remove it from graphMap in Project tab
         6. remove it from project tab
         7. remove tree item
         */
        
        try {
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            
            //GraphTab activeGraphTab = UIInstance.getActiveProjectTab().getActiveGraphTab();
            if (graphTab != null) {

                showInputWindow(pController, graphTab);
                
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
     }
     
     public static void showInputWindow(AnalysisController pController, GraphTab graphTab){
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        Stage stgRenameGraph = new Stage();
        stgRenameGraph.initModality(Modality.APPLICATION_MODAL);
        
        Label lblGraphName = new Label("Please provide new name for Graph");
        Label lblError_GraphNameFound = new Label("Graph already exists with this name");
        Label lblError_GraphNameNotValid = new Label("Graph name is not valid");
        
        Button btnRename = new Button("Rename");
        Button btnCancel = new Button("Cancel");
        
        
        lblError_GraphNameFound.setVisible(false);
        lblError_GraphNameNotValid.setVisible(false);
        
        TextField txtGraphName = new TextField ();
        txtGraphName.setPromptText("Enter new name");
        txtGraphName.setPrefColumnCount(15);
        
        
        txtGraphName.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode() == KeyCode.ENTER) {
                btnRename.fire();
            }
        });

      
        HBox hboxButtons = new HBox(5);
        hboxButtons.setPadding(new Insets(5,5,5,5));
        
        
        btnRename.disableProperty().bind(
            Bindings.isEmpty(txtGraphName.textProperty())
                
        );                
        btnRename.setOnAction(e -> {
            String strGraphNewName = txtGraphName.getText();
            
            //check whether the entered name is a valid file name for windows/linux/mac
            //check whether there is graph already in the project with the same name
            if(checkIfValidFileName(strGraphNewName)){            
      
                if (ProjectAPI.graphExistsWithName(UIInstance.getActiveProjectTab().getProjectID(), strGraphNewName)) {
                    lblError_GraphNameFound.setVisible(true);

                } else {

                    // Rename the graph in logic
                    // if(graph part of mprj file) then
                    // update in mprj file - update the graphFile as well
                    // otherwise just rename the graph title
                    
                    if(! GraphAPI.renameGraph(UIInstance.getActiveProjectTab().getProjectID(), graphTab.getGraphID(), strGraphNewName, AppConfig.EXTENSION_PROJECTFILE, AppConfig.PROJECTFILE_DELIMITER, AppConfig.GRAPHLIST_DELIMITER)){
                        //show message here if false
                        //graph could not be renamed
                        InfoDialog.Display("Graph Could not be renamed, check if file exists. Otherwise try saving the project first.", -1);
                        
                    }
                    
                    
                    
                    // Close the Dialog Box
                    stgRenameGraph.close();


                    // rename graph in ProjectTabpane, TreeView in UI

                    graphTab.renameGraphTab(strGraphNewName);

                    // Update project status
                    // no need to update project status since graph file has been renamed and mprj file rewritten (case1)
                    // Update project status
                    //ProjectStatusTracker.updateProjectModifiedStatus(UIInstance.getActiveProjectID(), ProjectStatusTracker.eventGraphRenamed);

                }
            }else{
                lblError_GraphNameNotValid.setVisible(true);
            }    
        });
        
        btnCancel.setOnAction(e -> {
            stgRenameGraph.close();
        });
        btnCancel.setCancelButton(true);
        
        
        hboxButtons.getChildren().addAll(btnRename, btnCancel);
        hboxButtons.setSpacing(20);
        hboxButtons.setPadding(new Insets(0, 10, 10, 10));
        hboxButtons.setAlignment(Pos.CENTER);
      
        // Build a VBox
        VBox vboxRows = new VBox(5);
        vboxRows.setPadding(new Insets(5,5,5,5));        
        vboxRows.getChildren().addAll(lblGraphName, txtGraphName, lblError_GraphNameFound, lblError_GraphNameNotValid,hboxButtons);
        
        vboxRows.setMinWidth(600);
        Scene scnRenameGraph = new Scene(vboxRows);
        
        scnRenameGraph.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
                stgRenameGraph.close();
                pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            }
        });
        
        stgRenameGraph.setTitle("Rename Graph");        
        stgRenameGraph.setResizable(false);
        
        stgRenameGraph.setScene(scnRenameGraph);
        stgRenameGraph.show();
    }
     
     public static Boolean checkIfValidFileName(String pstrFileName){
         if(pstrFileName!=null){
            String trimmed = pstrFileName.trim();
            String regex = GraphAPI.getGraphNameProjectNameRegex();
            if(trimmed.length()>0 && trimmed.matches(regex)){
                
                return true;
            }
         }
         return false;
     }
     
     
     
     public static void showInputWindow(AnalysisController pController){
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        Stage stgRenameGraph = new Stage();
        stgRenameGraph.initModality(Modality.APPLICATION_MODAL);
        
        Label lblGraphName = new Label("Please provide new name for Graph");
        Label lblError_GraphNameFound = new Label("Graph already exists with this name");
        Label lblError_GraphNameNotValid = new Label("Graph name is not valid");
        
        Button btnRename = new Button("Rename");
        Button btnCancel = new Button("Cancel");
        
        
        lblError_GraphNameFound.setVisible(false);
        lblError_GraphNameNotValid.setVisible(false);
        
        TextField txtGraphName = new TextField ();
        txtGraphName.setPromptText("Enter new name");
        txtGraphName.setPrefColumnCount(15);
        
        
        txtGraphName.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode() == KeyCode.ENTER) {
                btnRename.fire();
            }
        });

      
        HBox hboxButtons = new HBox(5);
        hboxButtons.setPadding(new Insets(5,5,5,5));
        
        
        btnRename.disableProperty().bind(
            Bindings.isEmpty(txtGraphName.textProperty())
                
        );                
        btnRename.setOnAction(e -> {
            String strGraphNewName = txtGraphName.getText();
            
            //check whether the entered name is a valid file name for windows/linux/mac
            //check whether there is graph already in the project with the same name
            if(checkIfValidFileName(strGraphNewName)){            
      
                if (ProjectAPI.graphExistsWithName(UIInstance.getActiveProjectTab().getProjectID(), strGraphNewName)) {
                    lblError_GraphNameFound.setVisible(true);

                } else {

                    // Rename the graph in logic
                    // if(graph part of mprj file) then
                    // update in mprj file - update the graphFile as well
                    // otherwise just rename the graph title
                    
                    if(!GraphAPI.renameGraph(UIInstance.getActiveProjectTab().getProjectID(), UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID(), strGraphNewName, AppConfig.EXTENSION_PROJECTFILE, AppConfig.PROJECTFILE_DELIMITER, AppConfig.GRAPHLIST_DELIMITER)){
                        //show message here if false
                        //graph could not be renamed
                        InfoDialog.Display("Graph Could not be renamed, check if file exists. Otherwise try saving the project first.", -1);
                        
                    }

                    // Close the Dialog Box
                    stgRenameGraph.close();


                    // rename graph in ProjectTabpane, TreeView in UI

                    UIInstance.getActiveProjectTab().getActiveGraphTab().renameGraphTab(strGraphNewName);
                    // no need to update project status since graph file has been renamed and mprj file rewritten (case1)
                    // Update project status
                    //ProjectStatusTracker.updateProjectModifiedStatus(UIInstance.getActiveProjectID(), ProjectStatusTracker.eventGraphRenamed);

                }
            }else{
                lblError_GraphNameNotValid.setVisible(true);
            }    
        });
        
        btnCancel.setOnAction(e -> {
            stgRenameGraph.close();
        });
        btnCancel.setCancelButton(true);
        
        
        hboxButtons.getChildren().addAll(btnRename, btnCancel);
        hboxButtons.setSpacing(20);
        hboxButtons.setPadding(new Insets(0, 10, 10, 10));
        hboxButtons.setAlignment(Pos.CENTER);
      
        // Build a VBox
        VBox vboxRows = new VBox(5);
        vboxRows.setPadding(new Insets(5,5,5,5));        
        vboxRows.getChildren().addAll(lblGraphName, txtGraphName, lblError_GraphNameFound, lblError_GraphNameNotValid,hboxButtons);
        
        vboxRows.setMinWidth(600);
        Scene scnRenameGraph = new Scene(vboxRows);
        
        scnRenameGraph.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
                stgRenameGraph.close();
                pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            }
        });
        
        stgRenameGraph.setTitle("Rename Graph");        
        stgRenameGraph.setResizable(false);
        
        stgRenameGraph.setScene(scnRenameGraph);
        stgRenameGraph.show();
    }
     

        
    
    
}
