/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.utilities;

import analysisscreen.AnalysisController;
import config.LangConfig;
import globalstate.MeerkatUI;
import globalstate.ProjectTab;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 *
 * @author AICML Administrator
 */
public class DeleteProject {
    
    public static Boolean confirmationStatus = false;
    
    public static void deleteProject(AnalysisController pController){
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        ProjectTab activeProject = UIInstance.getActiveProjectTab();
        showConfirmationDialogWindow(pController, activeProject.getProjectName());
        if(confirmationStatus){
            
            UIInstance.deleteProject(activeProject.getProjectID());
            
        }
    
    }
    
    public static void showConfirmationDialogWindow(AnalysisController pController, String projectDirectory){
        
        
        Stage stgConfirmationDialog = new Stage();
        
        
        Label lblMessage = new Label("Are you sure you want to delete this Project? ");
        Label lblMessage2 = new Label("All files in directory " + projectDirectory + " will be deleted.");
        Button btnYes = new Button(LangConfig.GENERAL_YES);
        btnYes.setOnAction(e -> {                        
            //close this satge and return back to calling method
            e.consume();
            confirmationStatus = true;
            System.out.println("MeerkatUI DeleteProject.java In conformation dialog");
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
        VBox vbox = new VBox(lblMessage,lblMessage2, hbox);                
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
