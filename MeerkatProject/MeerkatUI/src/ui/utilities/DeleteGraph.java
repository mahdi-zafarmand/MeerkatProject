/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.utilities;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.ProjectAPI;
import config.AppConfig;

import config.LangConfig;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.dialogwindow.InfoDialog;

/**
 *
 * @author AICML Administrator
 */
public class DeleteGraph {
    
    public static Boolean confirmationStatus = false;
    
    
     public static void delete(AnalysisController pController) {
     
         /*
         1. get active graph from ui
         
         2. remove it from Logic delete its file from hard disk
         3. remove from Project.java mapGraphs
         4. modify .mprj file
          
         
         5. remove it from graphMap in Project tab
         6. remove it from project tab
         7. remove tree item
         */
        String message = ""; 
        try {
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            
            //delete from logic first - then UI
            GraphTab activeGraphTab = UIInstance.getActiveProjectTab().getActiveGraphTab();
            if (activeGraphTab != null) {
                
                //show confirmation dialog window here
                showConfirmationDialogWindow(pController);
                if(confirmationStatus){
                    UIInstance.getActiveProjectTab().deleteActiveGraph();
                    
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
     }
     
     
     public static void delete(AnalysisController pController, GraphTab graphTab) {
     
         /*
         1. get active graph from ui
         
         2. remove it from Logic delete its file from hard disk
         3. remove from Project.java mapGraphs
         4. modify .mprj file
          
         
         5. remove it from graphMap in Project tab
         6. remove it from project tab
         7. remove tree item
         */
        String message = ""; 
        try {
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            
            //delete from logic first - then UI
            //GraphTab activeGraphTab = UIInstance.getActiveProjectTab().getActiveGraphTab();
            if (graphTab != null) {
                
                //show confirmation dialog window here
                showConfirmationDialogWindow(pController);
                if(confirmationStatus){
                    UIInstance.getActiveProjectTab().deleteGraph(graphTab);
                    
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
     }
     
     public static void showConfirmationDialogWindow(AnalysisController pController){
        
        
        Stage stgConfirmationDialog = new Stage();
        
        
        Label lblMessage = new Label("Are you sure you want to delete this graph");
        Button btnYes = new Button(LangConfig.GENERAL_YES);
        btnYes.setOnAction(e -> {                        
            //close this satge and return back to calling method
            e.consume();
            confirmationStatus = true;
            System.out.println("MeerkatUI DeleteGraph.java In conformation dialog");
            stgConfirmationDialog.close();
            
            
            
            
   
        });
        btnYes.setCancelButton(false);        
        btnYes.setAlignment(Pos.CENTER);
        
        Button btnNo = new Button(LangConfig.GENERAL_NO);
        btnNo.setOnAction(e -> {   
            // Close the current dialog box and clo
            e.consume();
            confirmationStatus = false;
            stgConfirmationDialog.close();
        });
        btnNo.setCancelButton(true);        
        btnNo.setAlignment(Pos.CENTER);
  

        
        HBox hbox = new HBox(btnYes, btnNo);
        hbox.setPadding(new Insets(0, 10, 0, 10));
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(40);
        VBox vbox = new VBox(lblMessage, hbox);                
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);
        
        Scene scnConfirmationDialog = new Scene(vbox);
        
        //stgConfirmationDialog.initOwner(pstgExitDialog);
        stgConfirmationDialog.initModality(Modality.APPLICATION_MODAL);
        stgConfirmationDialog.setTitle("Are you sure"); 
        //stgConfirmationDialog.getIcons().add(new Image(getIconPath()));
        stgConfirmationDialog.setResizable(false);
        
        stgConfirmationDialog.setScene(scnConfirmationDialog);
        stgConfirmationDialog.showAndWait();
        stgConfirmationDialog.setAlwaysOnTop(true);
        
    }
    
}
