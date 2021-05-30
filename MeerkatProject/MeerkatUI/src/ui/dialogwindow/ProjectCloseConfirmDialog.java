/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.ProjectAPI;
import config.AppConfig;
import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.utilities.SaveProject;

/**
 *
 * @author Talat-AICML
 */
public class ProjectCloseConfirmDialog {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strTitle;
    
    private static String strMessage ;
    private static String strMessageNotSave;
    private static String strIconPath;
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */    
    private static String getTitle() {
        return strTitle;
    }
    private static String getMessage() {
        return strMessage;
    }
    private static String getMessageNotSave() {
        return strMessageNotSave;
    }
    private static String getIconPath() {
        return strIconPath;
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2016-03-10
     *  Description     : Sets the parameters of the project close dialog box
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrTitle : String
     *  @param pstrMessage : String
     *  @param pstrIconPath : String    
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters(String pstrTitle, String pstrMessage, String pstrMessageNotSave,String pstrIconPath) {
        strTitle = pstrTitle;
        strMessage = pstrMessage;
        strMessageNotSave = pstrMessageNotSave;
        strIconPath = pstrIconPath;
    }
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2015-07-24
     *  Description     : Closes the Welcome Screen Window
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pWindow: Stage
     *  @param pController : AnalysisController
     *  @param pBooleanProjectModifiedStatus : Boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-01-14      Talat           The parameter of pController is added and the updateStatusBar is invoked to change the status of the status bar
     *  2015-09-14      Talat           The Exit screen now uses a Stage/Scene instead of an alert boz
     *  2015-09-14      Talat           Changed the Method name from closeWindow() to Display()
     * 
    */

    public static void Display(Stage pWindow, AnalysisController pController, Boolean pBooleanProjectModifiedStatus) {
        
        if(pBooleanProjectModifiedStatus){
            displayScreenProjctModified(pWindow, pController);
        }else{
            displayScreenProjctNotModified(pWindow, pController);
        }
        
    }
    
    public static void displayScreenProjctModified(Stage pWindow, AnalysisController pController){
    
        Stage stgProjectCloseDialog = new Stage();
        pController.updateStatusBar(true, StatusMsgsConfig.WAITING_USERINPUT);
        
        Label lblMessage = new Label(getMessage());
        Button btnYes = new Button(LangConfig.GENERAL_YES);
        btnYes.setOnAction(e -> { 
            
            MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
            
            SaveProject saveProject = new SaveProject();
            if(saveProject.checkAndSaveProject(pController, stgProjectCloseDialog, UIInstance.getActiveProjectTab().getProjectID())){
                /*
                // Save the Project
                pController.updateStatusBar(true, StatusMsgsConfig.PROJECT_SAVING);


                // Get the data of active project
                Map<Integer, Map<Integer, Map<Integer, Double[]>>> mapProjectsAllVerticesLocation = UIInstance.getActiveProjectTab().getProjectDataFromUI();
                // Save the Active Project 
                ProjectAPI.saveProject(UIInstance.getActiveProjectTab().getProjectID(), mapProjectsAllVerticesLocation, AppConfig.EXTENSION_PROJECTFILE, AppConfig.PROJECTFILE_DELIMITER, AppConfig.GRAPHLIST_DELIMITER);


                // Old method to save a project - Save the Active Project 
                ProjectAPI.saveProject(UIInstance.getActiveProjectTab().getProjectID(), AppConfig.EXTENSION_PROJECTFILE, AppConfig.PROJECTFILE_DELIMITER, AppConfig.GRAPHLIST_DELIMITER);


                pController.updateStatusBar(false, StatusMsgsConfig.PROJECT_SAVED);
                */
                stgProjectCloseDialog.close();
                // Close and Clean the Project Tab 
                pController.updateStatusBar(true, StatusMsgsConfig.PROJECT_CLOSING);
                ///////////UIInstance.getActiveProjectTab().ProjectTabClose();
                UIInstance.ProjectTabClose(UIInstance.getActiveProjectTab().getProjectID());


                UIInstance.UpdateUI();
                pController.updateStatusBar(false, StatusMsgsConfig.PROJECT_CLOSED);
                
            }else{
                
                stgProjectCloseDialog.close();
            }
        });
        btnYes.setCancelButton(false);        
        btnYes.setAlignment(Pos.CENTER);
        
        Button btnNo = new Button(LangConfig.GENERAL_NO);
        btnNo.setOnAction(e -> {   
            // Close the current dialog box
            stgProjectCloseDialog.close();
            
            // Close and Clean the Project Tab 
            MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;           
            //////////////UIInstance.getActiveProjectTab().ProjectTabClose();
            UIInstance.ProjectTabClose(UIInstance.getActiveProjectTab().getProjectID());
            UIInstance.UpdateUI();
            pController.updateStatusBar(false, StatusMsgsConfig.PROJECT_CLOSED);
        });
        btnNo.setCancelButton(true);        
        btnNo.setAlignment(Pos.CENTER);
        
        Button btnCancel = new Button(LangConfig.GENERAL_CANCEL);
        btnCancel.setOnAction(e -> {
            stgProjectCloseDialog.close();
            pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
        });
        btnCancel.setCancelButton(true);        
        btnCancel.setAlignment(Pos.CENTER);
        
        HBox hbox = new HBox(btnYes, btnNo, btnCancel);
        hbox.setPadding(new Insets(0, 10, 0, 10));
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(40);
        VBox vbox = new VBox(lblMessage, hbox);                
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);
        
        Scene scnProjectCloseConfirmation = new Scene(vbox);
        scnProjectCloseConfirmation.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
                stgProjectCloseDialog.close();
                pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            }
        });
        
        stgProjectCloseDialog.initModality(Modality.APPLICATION_MODAL);
        stgProjectCloseDialog.setTitle(getTitle()); 
        stgProjectCloseDialog.getIcons().add(new Image(getIconPath()));
        stgProjectCloseDialog.setResizable(false);
        
        stgProjectCloseDialog.setScene(scnProjectCloseConfirmation);
        stgProjectCloseDialog.show();
        stgProjectCloseDialog.setAlwaysOnTop(true);
    }
    
    
    public static void displayScreenProjctNotModified(Stage pWindow, AnalysisController pController){
    
        Stage stgProjectCloseDialog = new Stage();
        pController.updateStatusBar(true, StatusMsgsConfig.WAITING_USERINPUT);
        
        Label lblMessage = new Label(getMessageNotSave());
       
        
        Button btnYes = new Button(LangConfig.GENERAL_YES);
        btnYes.setOnAction(e -> {   
            // Close the current dialog box
            stgProjectCloseDialog.close();
            
            // Close and Clean the Project Tab 
            MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;           
            //////////////UIInstance.getActiveProjectTab().ProjectTabClose();
            UIInstance.ProjectTabClose(UIInstance.getActiveProjectTab().getProjectID());
            UIInstance.UpdateUI();
            pController.updateStatusBar(false, StatusMsgsConfig.PROJECT_CLOSED);
        });
        btnYes.setCancelButton(true);        
        btnYes.setAlignment(Pos.CENTER);
        
        Button btnCancel = new Button(LangConfig.GENERAL_CANCEL);
        btnCancel.setOnAction(e -> {
            stgProjectCloseDialog.close();
            pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
        });
        btnCancel.setCancelButton(true);        
        btnCancel.setAlignment(Pos.CENTER);
        
        HBox hbox = new HBox(btnYes, btnCancel);
        hbox.setPadding(new Insets(0, 10, 0, 10));
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(40);
        VBox vbox = new VBox(lblMessage, hbox);                
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);
        
        Scene scnProjectCloseConfirmation = new Scene(vbox);
        scnProjectCloseConfirmation.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
                stgProjectCloseDialog.close();
                pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            }
        });
        
        stgProjectCloseDialog.initModality(Modality.APPLICATION_MODAL);
        stgProjectCloseDialog.setTitle(getTitle()); 
        stgProjectCloseDialog.getIcons().add(new Image(getIconPath()));
        stgProjectCloseDialog.setResizable(false);
        
        stgProjectCloseDialog.setScene(scnProjectCloseConfirmation);
        stgProjectCloseDialog.show();
        stgProjectCloseDialog.setAlwaysOnTop(true);
    }
    
}

