package globalstate;

import accordiontab.AccordionTabContents;
import analysismenubar.MenuBar;
import analysismenubar.MenuItemGeneric;
import analysismenubar.MenuOption;
import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.LoadingAPI;
import ca.aicml.meerkat.api.ProjectAPI;
import ca.aicml.meerkat.api.TextualAPI;
import config.AppConfig;
import config.GraphConfig.GraphType;
import java.util.HashMap;
import java.util.List;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import layout.LayoutSet;
import meerkat.Utilities;
import ui.elements.ColorToolBox;
import ui.elements.EditingToolBox;
import ui.elements.IconToolBox;
import ui.elements.SizeToolBox;
import ui.elements.SnapshotToolBox;

/**
 *  Class Name      : Meerkat
 *  Created Date    : 2015-07-21
 *  Description     : Stores the information about the Opened Projects and other related attributes of them
 *                  : Uses the singleton project
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class MeerkatUI {
		
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    private AnalysisController analysisController ; // Just a reference to the Analysis Controller so that any updates could be applied in a straightforward way
    private HashMap<Integer, ProjectTab> hmapOpenedProjects = new HashMap<>(); // Mapping between a Meerkat Application generated ID and the ProjectTab
    private int intActiveProjectID ; // ID of the current Active ProjectTab that appears on the screen
    private int intMaxProjectID; // The Max ID for the list of projects that have been opened

    private static MeerkatUI application = null; // The only instance that would be created
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public static MeerkatUI getUIInstance () {
        if (application == null) {
               application = new MeerkatUI();
        }
        return application;
    }

    public ProjectTab getProject(int pintMeerkatProjectID) {
        return hmapOpenedProjects.get(pintMeerkatProjectID);
    }

    public ProjectTab getActiveProjectTab() {
        return hmapOpenedProjects.get(intActiveProjectID);
    }
    
    public ProjectTab getProjectTabByName(String pstrProjectName) {
        for (ProjectTab project : hmapOpenedProjects.values()) {
            if (project.getProjectName().equalsIgnoreCase(pstrProjectName)) {
                return project ;
            }
        }
        return null ;
    }
    
    public int getActiveProjectID () {
        return intActiveProjectID ;
    }
    
    public void setActiveProject(int pintProjectID) {
        intActiveProjectID = pintProjectID;
    }
    
    public void setController(AnalysisController pController) {
        this.analysisController = pController;
    }
    public AnalysisController getController() {
        return this.analysisController;
    }

    /**
    *  Method Name     : Meerkat()
    *  Method Type     : Constructor - private
    *  Created Date    : 2015-07-21
    *  Description     : Private Constructor to enforce the Singleton pattern - An object can be created only in this class
    *  Version         : 1.0
    *  @author         : Talat
    * 
    *  EDIT HISTORY (most recent at the top)
    *  Date            Author          Description
    * 
   */
    private MeerkatUI() {
        // TODO: initialize bunch of application wide attributes.		
        intMaxProjectID = -1; /* Starts with a value of 0 and is incremented when a new project is loaded */
        
    }

    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : [addProject WILL BE DEPRECATED SOON - Since each project will contain multiple RawDataFiles]
     *  Created Date    : 2015-07-xx
     *  Description     : Adding a project to the list of project when one of the actions by the user leads to a new project
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrProjectName : String
     *  @param penmProjectType : GraphType
     *  @param pstrRawInputFilePath : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    /*
    public void addProject(String pstrProjectName, GraphType penmProjectType, String pstrRawInputFilePath) {
        String strFileName = pstrRawInputFilePath.substring(pstrRawInputFilePath.lastIndexOf("/")+1).trim()
                .substring(pstrRawInputFilePath.lastIndexOf("\\")+1).trim();
        
        addProject(pstrProjectName, penmProjectType);
    }
    */
    
    
    /**
     *  Method Name     : addProject
     *  Created Date    : 2015-07-xx
     *  Description     : Adding a project to the list of project when one of the actions by the user leads to a new project
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrProjectName : String
     *  @param penmProjectType : GraphType
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void addProject(String pstrProjectName, GraphType penmProjectType) {
        
        int intProjectID = LoadingAPI.LoadProject();
        ProjectTab prjNewProject = new ProjectTab(intProjectID, pstrProjectName);
        /* The the current project to the list of ProjectTabs available */
        this.hmapOpenedProjects.put(prjNewProject.getProjectID(), prjNewProject);
        /* Make the current Project as Active Project */
        this.intActiveProjectID = intProjectID;
        /* Invoke the tools activation */
        UpdateUI();
    }
    
    public void addNewProject(String pstrProjectName, String pstrProjectDirectory, GraphType penmProjectType) {
        int intProjectID = LoadingAPI.CreateProject(pstrProjectName, pstrProjectDirectory);
        
        System.out.println("MeerkatUI.addNewProject(): ProjectID loaded is "+intProjectID);
        ProjectTab prjNewProject = new ProjectTab(intProjectID, pstrProjectName);
        /* The the current project to the list of ProjectTabs available */
        this.hmapOpenedProjects.put(prjNewProject.getProjectID(), prjNewProject);
        /* Make the current Project as Active Project */
        this.intActiveProjectID = intProjectID;
        /* Invoke the tools activation */
        UpdateUI();
    }
    
    public void addProject(ProjectTab pProjectTab) {
        hmapOpenedProjects.put(pProjectTab.getProjectID(), pProjectTab);
        this.intActiveProjectID = pProjectTab.getProjectID();
        
        /* Invoke the tools activation */
        UpdateUI();
    }
    
    
    public boolean isProjectOpen(String pstrProjectFileName) {
        String strProjectName = Utilities.getFileNameWithoutExtention(pstrProjectFileName);
        System.out.println("Meerkat.IsProjectOpen(): Project Name: "+strProjectName);
        for (ProjectTab currentProject : this.hmapOpenedProjects.values()) {
            System.out.println("\tMeerkat.IsProjectOpen(): "+currentProject.getProjectName());
            if (strProjectName.equalsIgnoreCase(currentProject.getProjectName())) {
                return true;
            }
        }
        return false;
    }
   
    public void closeProject() {
        /* Invoke the tools activation */
        UpdateUI();
    }
    
    public int getProjectTabCount() {
        return this.hmapOpenedProjects.size();
    }
    
    public HashMap<Integer, ProjectTab> getAllProject() {
        return this.hmapOpenedProjects;
    }
    
    
    /**
     *  Method Name     : ToolsActivation()
     *  Created Date    : 2016-01-07
     *  Description     : A method that would activate or deactivate all the tools based on the number of projects
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void ToolsActivation () {
        
        boolean blnActivate = false ;
        
        // EDITING TOOL BOX
        // If there is at least one project that is OPEN, activate the graph editing tool box
        EditingToolBox editToolBox = EditingToolBox.getInstance();
        
        // SNAPSHOT TOOL BOX
        // If here is at least one project that is OPEN, activate the snapshot tool box
        SnapshotToolBox snapshotToolBox = SnapshotToolBox.getInstance();
        
        
        if (getProjectTabCount() > 0) {
            if (getActiveProjectTab().getGraphCount() > 0) {
                blnActivate = true ;
            }
            else {
                blnActivate = false ;
                SizeToolBox.getInstance().disableSizeIconBlur();
                ColorToolBox.getInstance().disableColorIconBlur();
                IconToolBox.getInstance().disableIconToolbox();
            }
        }
        
        editToolBox.activateEditingToolBox(blnActivate);        
        snapshotToolBox.activateSnapshotToolBox(blnActivate);
        
    }
    
    /**
     *  Method Name     : ActivateMenubar()
     *  Created Date    : 2016-06-07
     *  Description     : Activates the Menubar based on the open projects/graphs/textual
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void ActivateMenubar() {
        
        ProjectTab currentProject = getActiveProjectTab();
        
        MenuBar mnAnalysisMenuInstance = MenuBar.getInstance();   
        List<Menu> lstmnAllMenus = this.analysisController.getMenuBar().getMenus() ;
        
        if (currentProject == null) {
            for (Menu menuCurrent : lstmnAllMenus) { // Loop through the Menus
                MenuOption mnCurrentOption = mnAnalysisMenuInstance.getMenuOptionByName(menuCurrent.getText()) ;
                if (mnCurrentOption != null) {
                   // mnCurrentOption.Print(); // Only for Debug
                    for (MenuItem mnItem : menuCurrent.getItems()) {                        
                        MenuItemGeneric iMenuItem = mnCurrentOption.getMenuItem(mnItem.getText()) ;
                        // System.out.println("MeerkatUI.MenuActivation(): Project Null = MenuItems: "+iMenuItem.getDisplayName()+"\tLevel"+iMenuItem.getDisabledLevel());
                        // If the accessed value is true, disable the things that were disabled before
                        if (iMenuItem != null) {
                            switch (iMenuItem.getDisabledLevel()) {
                                case NOTHING :
                                    mnItem.setDisable(false);
                                    break ;
                                case PROJECT_AVAILABLE :
                                    mnItem.setDisable(true);
                                    break ;
                                case GRAPH_AVAILABLE :
                                    mnItem.setDisable(true);
                                    break ;
                                case TEXTUAL_AVAILABLE :
                                    mnItem.setDisable(true);
                                    break ;       
                            }
                        }
                    }
                }
            }            
            return ;
        } else {
            for (Menu menuCurrent : lstmnAllMenus) { // Loop through the Menus
               MenuOption mnCurrentOption = mnAnalysisMenuInstance.getMenuOptionByName(menuCurrent.getText()) ;
               if (mnCurrentOption != null) {
                   // mnCurrentOption.Print(); // Only for Debug
                   for (MenuItem mnItem : menuCurrent.getItems()) {                        
                       MenuItemGeneric iMenuItem = mnCurrentOption.getMenuItem(mnItem.getText()) ;
                       // System.out.println("MeerkatUI.MenuActivation(): Project Available = MenuItems: "+iMenuItem.getDisplayName()+"\tLevel"+iMenuItem.getDisabledLevel());
                       
                        // If the accessed value is true, disable the things that were disabled before
                        if (iMenuItem != null) {
                            switch (iMenuItem.getDisabledLevel()) {
                                case NOTHING :
                                    mnItem.setDisable(false);
                                    break ;
                                case PROJECT_AVAILABLE :
                                    mnItem.setDisable(false);
                                    break ;
                                case GRAPH_AVAILABLE :
                                    mnItem.setDisable(true);
                                    break ;
                                case TEXTUAL_AVAILABLE :
                                    mnItem.setDisable(true);
                                    break ;       
                            }
                        }
                   }
               }
           }
        }
        
        // If there exists a graph in the current project
        if (currentProject.getGraphCount() > 0) {
            for (Menu menuCurrent : lstmnAllMenus) { // Loop through the Menus
                MenuOption mnCurrentOption = mnAnalysisMenuInstance.getMenuOptionByName(menuCurrent.getText()) ;
                if (mnCurrentOption != null) {
                   // mnCurrentOption.Print(); // Only for Debug
                    for (MenuItem mnItem : menuCurrent.getItems()) {                        
                        MenuItemGeneric iMenuItem = mnCurrentOption.getMenuItem(mnItem.getText()) ;
                        // System.out.println("MeerkatUI.MenuActivation(): Graph Count ("+currentProject.getGraphCount()+") MenuItems: "+iMenuItem.getDisplayName()+"\tLevel"+iMenuItem.getDisabledLevel());
                        // If the accessed value is true, disable the things that were disabled before
                        if (iMenuItem != null) {
                            switch (iMenuItem.getDisabledLevel()) {
                                case NOTHING :
                                    mnItem.setDisable(false);
                                    break ;
                                case GRAPH_AVAILABLE :
                                    mnItem.setDisable(false);
                                    break ;     
                            }
                        }
                    }
                }
            }
        }
        
        // If there exists in the current Project
        if (currentProject.getTextualCount() > 0) {
            
            for (Menu menuCurrent : lstmnAllMenus) { // Loop through the Menus
                MenuOption mnCurrentOption = mnAnalysisMenuInstance.getMenuOptionByName(menuCurrent.getText()) ;
                if (mnCurrentOption != null) {
                   // mnCurrentOption.Print(); // Only for Debug
                    for (MenuItem mnItem : menuCurrent.getItems()) {                        
                        MenuItemGeneric iMenuItem = mnCurrentOption.getMenuItem(mnItem.getText()) ;
                        // System.out.println("MeerkatUI.MenuActivation(): Graph Count ("+currentProject.getTextualCount()+") MenuItems: "+iMenuItem.getDisplayName()+"\tLevel"+iMenuItem.getDisabledLevel());
                        
                        // If the accessed value is true, disable the things that were disabled before
                        if (iMenuItem != null) {
                            switch (iMenuItem.getDisabledLevel()) {
                                case NOTHING :
                                    mnItem.setDisable(false);
                                    break ;
                                case PROJECT_AVAILABLE :
                                    mnItem.setDisable(false);
                                    break ;
                                case TEXTUAL_AVAILABLE :
                                    mnItem.setDisable(false);
                                    break ;       
                            }
                        }
                    }
                }
            }
        } 
        
    }
    
    /**
     *  Method Name     : UpdateUI()
     *  Created Date    : 2016-05-20
     *  Description     : Updates the UI elements
     *  Version         : 1.0
     *  @author         : Talat
     *
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void UpdateUI() {
        ToolsActivation() ;
        ActivateMenubar() ;
        getController().updateDCMiningPanel();
    }
    
    /**
     *  Method Name     : StopAllThreads()
     *  Created Date    : 2016-06-20
     *  Description     : Stops all the Threads of the Graphs/TextualNW of all the Projects
     *  Version         : 1.0
     *  @author         : Talat
     *
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void StopAllThreads() {
        for (ProjectTab currentProject : this.hmapOpenedProjects.values()) {
            int intProjectID = currentProject.getProjectID() ;
            for (GraphTab currentGraph : currentProject.getAllGraphTabs().values()) {
                int intGraphID = currentGraph.getGraphID();                
                // Close the graph
                GraphAPI.stopAlgorithms(intProjectID, intGraphID);                
            }
            
            for (TextualTab currentTextualNW : currentProject.getAllTextualTabs().values()) {
                int intTextualID = currentTextualNW.getID();
                // Close the TedxtualTab
                TextualAPI.stopAlgorithms(intProjectID, intTextualID);                
            }
        }
    }
    
    public void ProjectTabClose(int pintProjectId) {

        // remove tree item
        System.out.println("MeerkatUI ProjectTabClose() : removing project tree item");
        this.getAllProject().get(pintProjectId).removeTreeItem();
        
        // Remove the Tab itself
        System.out.println("MeerkatUI ProjectTabClose() : removing project tab");
        getController().getProjectTabPane().getTabs().remove(this.getAllProject().get(pintProjectId).getProjectUITab());
        

        // Remove from the list of Project Tabs that are available in Project 
        this.getAllProject().remove(pintProjectId) ;
        
        //if no projects open then clear everything from accordian tabs and disable sze toolbox
        if(this.getAllProject().isEmpty()){
            AccordionTabContents.setAccordionNull();
            LayoutSet.setLayoutSetNull(); // setting layoutset object null as it would be re-initialized again using next line.
            this.getController().initiateAccordionTabs();
            SizeToolBox.getInstance().disableSizeToolbox();
        }
        // remove this project from MeerkatLogic
        ProjectAPI.closeProject(pintProjectId);
        UpdateUI();
    }
    
    public void deleteProject(int pintProjectId){
    
        // delete project from logic
        // logic will delete its graph files and mprj file from the hard disk
        ProjectAPI.deleteProject(pintProjectId, AppConfig.EXTENSION_PROJECTFILE);
        
        //remove this project tab from ui and clear the reference in logic
        this.ProjectTabClose(pintProjectId);
        
    
    }
	
}
