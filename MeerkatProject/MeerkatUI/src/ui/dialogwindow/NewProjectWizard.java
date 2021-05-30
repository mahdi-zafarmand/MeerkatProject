/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.ProjectAPI;
import config.AppConfig;
import config.GraphConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import java.io.File;
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


/**
 *  Class Name      : NewProjectWizard
 *  Created Date    : 2015-08-04
 *  Description     : Some of the Language specific parameters
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class NewProjectWizard {
        
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strNewProjectWizard ;
    private static String strProjectNameLabel; 
    private static String strPromptText;
    private static String strCreateProject ;
    private static String strCancel ;
    private static String strError_FileFound;

    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2015-08-04
     *  Description     : Sets the parameters of the New Project Wizard
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrNewProjectWizard : String
     *  @param pstrCreateProject : String
     *  @param pstrCancel : String
     *  @param pstrProjectNameLabel : String
     *  @param pstrPromptText : String
     *  @param pstrError_FileFound : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters (String pstrNewProjectWizard, String pstrCreateProject, String pstrCancel
            , String pstrProjectNameLabel, String pstrPromptText, String pstrError_FileFound) {
        strNewProjectWizard = pstrNewProjectWizard;
        strCreateProject = pstrCreateProject;
        strCancel = pstrCancel;
        strProjectNameLabel = pstrProjectNameLabel;
        strPromptText = pstrPromptText;
        strError_FileFound = pstrError_FileFound;
    }
    
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2015-08-04
     *  Description     : Displays the new project wizard
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Display (AnalysisController pController) {
        
        Stage stgNewProjectWizard = new Stage();
        stgNewProjectWizard.initModality(Modality.APPLICATION_MODAL);
        
        HBox project = new HBox();
        Label lblProjectName = new Label(strProjectNameLabel);
        Label lblError_FileFound = new Label(strError_FileFound);
        Label lblError_ProjectNameNotValid = new Label("Project name is not valid");
        Button btnCreate = new Button(strCreateProject);
        Button btnCancel = new Button(strCancel);

        
        lblError_FileFound.setVisible(false);
        lblError_ProjectNameNotValid.setVisible(false);
        
        
        TextField txtProjectName = new TextField ();
        txtProjectName.setPromptText(strPromptText);
        txtProjectName.setPrefColumnCount(15);
        project.getChildren().addAll(lblProjectName, txtProjectName);
        project.setPadding(new Insets(5,5,5,5));
        project.setSpacing(10);
        project.setAlignment(Pos.CENTER);
        
        txtProjectName.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode() == KeyCode.ENTER) {
                btnCreate.fire();
            }
        });

      
        HBox hboxButtons = new HBox(5);
        hboxButtons.setPadding(new Insets(5,5,5,5));
        
        // Button btnCreate = new Button(strCreateProject);
        btnCreate.disableProperty().bind(
            Bindings.isEmpty(txtProjectName.textProperty())
                
        );                
        btnCreate.setOnAction(e -> {
            
            if(checkIfValidDirectoryName(txtProjectName.getText())){
                
                String strProjectName = txtProjectName.getText().trim();
                String strProjectRootDirectory = AppConfig.DIR_PROJECT ;       
                String strProjectDirectory = strProjectRootDirectory + strProjectName + File.separator ;
                String strProjectExtension = AppConfig.EXTENSION_PROJECTFILE ;
                String strProjectFileName = strProjectName+strProjectExtension ;


                // System.out.println("NewProjectWizard.Display(): Project Name entered: "+strProjectName+" with length "+strProjectName.length());
                if (Utilities.ProjectExists(strProjectFileName, System.getProperty("user.dir")+File.separator+strProjectRootDirectory)) {
                    lblError_FileFound.setVisible(true);
                } else {
                    // Create a project tab with the existing project name
                    MeerkatUI UIInstance = MeerkatUI.getUIInstance();
                    UIInstance.addNewProject(strProjectName, strProjectDirectory, GraphConfig.GraphType.GRAPH);

                    // Make the Project Tab as Active Project
                    /* Automatically done while creating a new project */

                    // Send this Project Tab to write the project file
                    ProjectAPI.saveProject(UIInstance.getActiveProjectTab().getProjectID(), AppConfig.EXTENSION_PROJECTFILE, AppConfig.PROJECTFILE_DELIMITER, AppConfig.GRAPHLIST_DELIMITER);
                    // ProjectWriter.Save(strProjectDirectory, strProjectName, strProjectExtension, applicationInstance.getActiveProject());

                    // Close the Dialog Box
                    stgNewProjectWizard.close();

                    /* Add the project to UI */
                    pController.addProject(UIInstance.getActiveProjectTab());
                }
            }else{
                
                lblError_ProjectNameNotValid.setVisible(true);
            }
        });
        
        btnCancel.setOnAction(e -> {
            stgNewProjectWizard.close();
        });
        btnCancel.setCancelButton(true);
        
        
        hboxButtons.getChildren().addAll(btnCreate, btnCancel);
        hboxButtons.setSpacing(20);
        hboxButtons.setPadding(new Insets(0, 10, 10, 10));
        hboxButtons.setAlignment(Pos.CENTER);
      
        // Build a VBox
        VBox vboxRows = new VBox(5);
        vboxRows.setPadding(new Insets(5,5,5,5)); 
        vboxRows.setAlignment(Pos.CENTER);
        vboxRows.setSpacing(10);
        vboxRows.getChildren().addAll(project, lblError_ProjectNameNotValid, lblError_FileFound, hboxButtons);
        
        vboxRows.setMinWidth(600);
        Scene scnNewProjectWizard = new Scene(vboxRows);
        
        scnNewProjectWizard.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
                stgNewProjectWizard.close();
                pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            }
        });
        
        //stgNewProjectWizard.initModality(Modality.WINDOW_MODAL);
        stgNewProjectWizard.setTitle(strNewProjectWizard);        
        stgNewProjectWizard.setResizable(false);
        
        stgNewProjectWizard.setScene(scnNewProjectWizard);
        stgNewProjectWizard.show();
    }

    public static Boolean checkIfValidDirectoryName(String pstrFileName){
         if(pstrFileName!=null){
            String trimmed = pstrFileName.trim();
            String regex = ProjectAPI.getGraphNameProjectNameRegex();
            if(trimmed.length()>0 && trimmed.matches(regex)){
                return true;
            }
         }
         return false;
    }
    
}
