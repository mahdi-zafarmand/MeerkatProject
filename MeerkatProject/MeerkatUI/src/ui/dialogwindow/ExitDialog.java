/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysismenubar.MenuItemSaveProject;
import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.ProjectAPI;
import config.AppConfig;
import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import globalstate.ProjectTab;
import io.stateserialization.SystemExit;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.utilities.SaveProject;

/**
 *  Class Name      : ExitDialog
 *  Created Date    : 2015-07-24
 *  Description     : All the functionalities for the exit screen
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-09-14      Talat           Changed the ClassName from ExitScreen to ExitDialog
 * 
*/
public class ExitDialog {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strExitTitle;
    private static String strExitMessage ;    
    private static String strIconPath;
    
    private static StringProperty strSelectedProjectName = new SimpleStringProperty();
    private static BooleanProperty blnProjectSelected = new SimpleBooleanProperty(false);
    private static BooleanProperty blnProjectModified = new SimpleBooleanProperty(false);
    private static Boolean confirmationCloseWithoutSaving = false;
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */    
    private static String getTitle() {
        return strExitTitle;
    }
    private static String getMessage() {
        return strExitMessage;
    }
    private static String getIconPath() {
        return strIconPath;
    }
    private static String getSelectedProjectName(){
        //System.out.println(""strSelectedProjectName);
        return strSelectedProjectName.getValue() ;
    }
    private static void setSelectedProjectName(String pstrValue) {
        strSelectedProjectName.set(pstrValue) ;
        blnProjectSelected.set(true);    
    }
    
    private static void setSelectedProjectModifiedStatus(){
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        blnProjectModified.setValue(UIInstance.getProjectTabByName(strSelectedProjectName.getValue()).getProjectModifiedStatus());
        System.out.println("ExitDialog.setSelectedProjectModifiedStatus : selectedProjectName = " + strSelectedProjectName.getValue() + ", modifiedStatus = " + blnProjectModified.getValue());
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2015-11-04
     *  Description     : Sets the parameters of the feedback dialog box
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrExitTitle : String
     *  @param pstrExitMessage : String
     *  @param pstrExitIconPath : String    
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters(String pstrExitTitle, String pstrExitMessage, String pstrExitIconPath) {
        strExitTitle = pstrExitTitle;
        strExitMessage = pstrExitMessage;
        strIconPath = pstrExitIconPath;
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
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-01-14      Talat           The parameter of pController is added and the updateStatusBar is invoked to change the status of the status bar
     *  2015-09-14      Talat           The Exit screen now uses a Stage/Scene instead of an alert boz
     *  2015-09-14      Talat           Changed the Method name from closeWindow() to Display()
     * 
    */

    public static void Display(Stage pWindow, AnalysisController pController) {
       
        Stage stgExitDialog = new Stage();
        pController.updateStatusBar(true, StatusMsgsConfig.WAITING_USERINPUT);
        
        ListView<String> lstviewOpenProjects = new ListView<>();
        lstviewOpenProjects.setMinWidth(100);
        lstviewOpenProjects.setMinHeight(60);
        
        ObservableList<String> olststrOpenProjectsNames = FXCollections.observableArrayList();
        
                
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        for (ProjectTab currentProjectTab : UIInstance.getAllProject().values()) {
            olststrOpenProjectsNames.add(currentProjectTab.getProjectName()) ;
        }        
        lstviewOpenProjects.setItems(olststrOpenProjectsNames);
        lstviewOpenProjects.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> oVObservable, String strOldValue, String strNewValue) -> {
            if(strNewValue!=null){
                System.out.println("ExitDialog.setSelectedProjectModifiedStatus : Listener value = " + strNewValue);
                setSelectedProjectName(strNewValue);
                setSelectedProjectModifiedStatus();
            }
            
            
        });
        
        
        Button btnSave = new Button(LangConfig.GENERAL_SAVE) ;
        BooleanBinding saveBTnEnable = Bindings.and(blnProjectSelected, blnProjectModified);
        btnSave.disableProperty().bind(saveBTnEnable.not());
        btnSave.setOnAction(e -> {
            // Get the ProjectTab using the item selected
            ProjectTab currentProject = UIInstance.getProjectTabByName(getSelectedProjectName()) ;
            if(SaveAndCloseProject(pController, currentProject, stgExitDialog)){
            
                // Remove the project name from the list, listener is called after this line is executed. 
                // Inside listener, these properties - strSelectedProjectName, blnProjectSelected, blnProjectModified
                // will be set
                olststrOpenProjectsNames.remove(getSelectedProjectName());
                System.out.println("ExitDialog.display() btn close and save : After remving from list");
                //setSelectedProjectName(null); // Set the Project Name to null once the old project has been removed from the observable list
                //blnProjectSelected.set(false);
                //blnProjectModified.set(false);
            }
            if (UIInstance.getProjectTabCount() == 0) {
                stgExitDialog.close();
                pWindow.close();
            }
        }); 
        
        ////////////////
        Button btnClose = new Button(LangConfig.GENERAL_CLOSE) ;
        btnClose.disableProperty().bind(blnProjectSelected.not());
        btnClose.setOnAction(e -> {
            // Get the ProjectTab using the item selected and close it
            ProjectTab currentProject = UIInstance.getProjectTabByName(getSelectedProjectName()) ;
            System.out.println("ExitDialog.display() btnClose : projectName = " + getSelectedProjectName() + " , projectObject = " + currentProject);
            System.out.println("ExitDialog.display() btnClose : projectName =  " + currentProject.getProjectName() + ", id =  " + currentProject.getProjectID());
            UIInstance.ProjectTabClose(currentProject.getProjectID());
            // Remove the project name from the list, listener is called after this line is executed. 
            // Inside listener, these properties - strSelectedProjectName, blnProjectSelected, blnProjectModified
            // will be set
            olststrOpenProjectsNames.remove(getSelectedProjectName());
            
            //setSelectedProjectName(null); // Set the Project Name to null once the old project has been removed from the observable list
            //blnProjectSelected.set(false);
           // blnProjectModified.set(false);
            if (UIInstance.getProjectTabCount() == 0) {
                stgExitDialog.close();
                pWindow.close();
            }
        });
        /////////////
        Button btnSaveAll = new Button(LangConfig.GENERAL_SAVEALL) ;
        btnSaveAll.setOnAction(e -> {
            pController.updateStatusBar(false, StatusMsgsConfig.PROJECT_SAVING);
            // Make a copy of list olststrOpenProjectsNames
            ObservableList<String> olststrOpenProjectsNamesCopy = FXCollections.observableArrayList();
            for(String strCurrentProjectName : olststrOpenProjectsNames) {
                String strCurrentProjectNameCopy = new String(strCurrentProjectName);
                olststrOpenProjectsNamesCopy.add(strCurrentProjectNameCopy);
            }
            // For each of the items in the listview, save and close the project
            
            for(String strCurrentProjectName : olststrOpenProjectsNamesCopy) {
                // Get the ProjectTab using the item selected
                ProjectTab currentProject = UIInstance.getProjectTabByName(strCurrentProjectName) ;
                // if this project has been modified then try to save it
                // else just close it
                if(currentProject.getProjectModifiedStatus()){
                    //if project can not be saved - then do not remove it from list view
                    if(SaveAndCloseProject(pController, currentProject, stgExitDialog)){
                        olststrOpenProjectsNames.remove(strCurrentProjectName);
                        pController.updateStatusBar(false, StatusMsgsConfig.PROJECT_SAVED);
                        
                    }
                    else{
                        //To exit the loop if any unsavable project is encountered
                        break;
                    }
                }else{

                    // Remove the project name from the list
                    olststrOpenProjectsNames.remove(strCurrentProjectName);
                    
                    
                    // CLOSING THE PROJECT            
                    // Close and Clean the Project Tab 
                    pController.updateStatusBar(true, StatusMsgsConfig.PROJECT_CLOSING);
                    ////pprjCurrent.ProjectTabClose();
                    UIInstance.ProjectTabClose(currentProject.getProjectID());

                    // Update the UI Instance
                    UIInstance.UpdateUI();
                    pController.updateStatusBar(false, StatusMsgsConfig.PROJECT_CLOSED);
                    
                }
            }
            
            stgExitDialog.close();
            
            // do system exit only if there are no more unsaved projects
            if(olststrOpenProjectsNames.isEmpty()){
                SystemExit.Cleanup(AppConfig.DIR_DISPLAY_GRAPH);
                pWindow.close();
            }
            
        });
        
        
        Button btnDontSave=  new Button(LangConfig.GENERAL_DONTSAVE);
        btnDontSave.setOnAction(e -> {
            //show confirmation dialog window here
            showConfirmationDialogWindow(stgExitDialog);
            if(confirmationCloseWithoutSaving){
                SystemExit.Cleanup(AppConfig.DIR_DISPLAY_GRAPH);
                stgExitDialog.close();
                pWindow.close();
            }
            //
            
        });
        btnDontSave.setCancelButton(false);        
        btnDontSave.setAlignment(Pos.CENTER);
        
        Button btnCancel = new Button(LangConfig.GENERAL_CANCEL);
        btnCancel.setOnAction(e -> {
            stgExitDialog.close();
            pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
        });
        btnCancel.setCancelButton(true);        
        btnCancel.setAlignment(Pos.CENTER);
        
        VBox vboxButtons = new VBox(btnSave, btnClose);
        vboxButtons.setPadding(new Insets(0, 10, 0, 10));
        vboxButtons.setAlignment(Pos.CENTER);
        vboxButtons.setSpacing(40);
        btnSave.setMinWidth(50);
        btnSaveAll.setMinWidth(50);
        btnDontSave.setMinWidth(50);
        btnCancel.setMinWidth(50);
        
        HBox hboxControls = new HBox(lstviewOpenProjects, vboxButtons);                
        hboxControls.setPadding(new Insets(10, 10, 10, 10));
        hboxControls.setSpacing(20);
        
        HBox hboxCommonControls = new HBox(btnSaveAll, btnDontSave, btnCancel); 
        hboxCommonControls.setPadding(new Insets(10, 20, 10, 20));
        hboxCommonControls.setSpacing(10);
        
        Label lblMessage = new Label(getMessage());
        VBox vboxContainer = new VBox(lblMessage, hboxControls, hboxCommonControls);
        vboxContainer.setPadding(new Insets(10, 10, 10, 10));
        vboxContainer.setSpacing(20);
        
        Scene scnExitDialog = new Scene(vboxContainer);     
        scnExitDialog.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
                stgExitDialog.close();
                pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            }
        });
        
        stgExitDialog.initModality(Modality.APPLICATION_MODAL);
        stgExitDialog.setTitle(getTitle()); 
        stgExitDialog.getIcons().add(new Image(getIconPath()));
        stgExitDialog.setResizable(false);
        
        stgExitDialog.setScene(scnExitDialog);
        stgExitDialog.show();
    }
    
    public static Boolean SaveAndCloseProject(AnalysisController pController, ProjectTab pprjCurrent, Stage pstgparentStage) {
        Boolean result = false;
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        SaveProject saveProject = new SaveProject();
        if(saveProject.checkAndSaveProject(pController, pstgparentStage, pprjCurrent.getProjectID())){
            /*    
            // SAVING THE PROJECT
            pController.updateStatusBar(false, StatusMsgsConfig.PROJECT_SAVING);

            // Get the data of active project
            Map<Integer, Map<Integer, Map<Integer, Double[]>>> mapProjectsAllVerticesLocation = pprjCurrent.getProjectDataFromUI();
            // Save the Active Project 
            ProjectAPI.saveProject(pprjCurrent.getProjectID(), mapProjectsAllVerticesLocation, AppConfig.EXTENSION_PROJECTFILE, AppConfig.PROJECTFILE_DELIMITER, AppConfig.GRAPHLIST_DELIMITER);


            // Old method - Save the Active Project 
            ProjectAPI.saveProject(pprjCurrent.getProjectID(), AppConfig.EXTENSION_PROJECTFILE, AppConfig.PROJECTFILE_DELIMITER, AppConfig.GRAPHLIST_DELIMITER);            

            pController.updateStatusBar(false, StatusMsgsConfig.PROJECT_SAVED);
            */



            // CLOSING THE PROJECT            
            // Close and Clean the Project Tab 
            pController.updateStatusBar(true, StatusMsgsConfig.PROJECT_CLOSING);
            ////pprjCurrent.ProjectTabClose();
            UIInstance.ProjectTabClose(pprjCurrent.getProjectID());

            // Update the UI Instance
            UIInstance.UpdateUI();
            pController.updateStatusBar(false, StatusMsgsConfig.PROJECT_CLOSED);
            
            result = true;
        }
        return result;
    }
    
    public static void showConfirmationDialogWindow(Stage pstgExitDialog){
        
        
        Stage stgConfirmationDialog = new Stage();
        
        
        Label lblMessage = new Label("Are you sure you want to close without saving?");
        Button btnYes = new Button(LangConfig.GENERAL_YES);
        btnYes.setOnAction(e -> {                        
            //close this satge and return back to calling method
            e.consume();
            confirmationCloseWithoutSaving = true;
            System.out.println("MeerkatUI ExitDialog In conformation dialog");
            stgConfirmationDialog.close();
            
            
            
            
   
        });
        btnYes.setCancelButton(false);        
        btnYes.setAlignment(Pos.CENTER);
        
        Button btnNo = new Button(LangConfig.GENERAL_NO);
        btnNo.setOnAction(e -> {   
            // Close the current dialog box and clo
            e.consume();
            confirmationCloseWithoutSaving = false;
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
        
        stgConfirmationDialog.initOwner(pstgExitDialog);
        stgConfirmationDialog.initModality(Modality.WINDOW_MODAL);
        stgConfirmationDialog.setTitle("Are you sure?"); 
        stgConfirmationDialog.getIcons().add(new Image(getIconPath()));
        stgConfirmationDialog.setResizable(false);
        
        stgConfirmationDialog.setScene(scnConfirmationDialog);
        stgConfirmationDialog.showAndWait();
        stgConfirmationDialog.setAlwaysOnTop(true);
        
    }
}
