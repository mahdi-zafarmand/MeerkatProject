/*
 * To change this license header, choose License Headers in ProjectTab Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate;

import accordiontab.AccordionTabContents;
import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.LoadingAPI;
import ca.aicml.meerkat.api.ProjectAPI;
import config.AppConfig;
import config.GraphConfig.GraphType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import meerkat.UIUtilities;
import meerkat.Utilities;
import threadtree.MeerkatTreeItem;
import threadtree.ThreadTree;
import threadtree.TreeItemConfig.TreeItemType;
import ui.dialogwindow.InfoDialog;
import ui.dialogwindow.ProjectCloseConfirmDialog;
import ui.elements.EditingToolBox;

/**
 *  Class Name      : ProjectTab
 *  Created Date    : 2015-07-21
 *  Description     : Details about a ProjectTab shown in the UI
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-10-13      Talat           Added the map mapTextualTab and the implementations of
 *                                      1) getAllTextualTabs()
 *                                      2) getActiveTextualTab()
 *                                      3) getTextualTab()
 *                                      4) getTextualCount()    
 *                                      5) addTextualTab()
 *  2015-07-31      Talat           Added different constructors for different ways a ProjectTab can be initialized
 * 
*/
public class ProjectTab {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    private int intProjectID; // ID that is assigned to a project
    private String strProjectName; // The Name of the Project - will be used to display the Project Tab title and saving the Project
    private Map<Integer, GraphTab> mapGraphTabs; // Set of Graph Tabs that belong to the project
    private int intActiveGraphID; // The current GraphID
    private int intActiveTextualTabID ; // The current TextualTab ID
    // private GraphType enmProjectType; // The type of Project // #TOBEREMOVED
    
        
    private String strIconPath; // The path of the Icon that would be used to display the project at various places
    private List<String> lststrRawDataFilePath; // The file path to the raw data file
    private String strProjectDirectory ;
    
    private long lngSavedTime; // Timestamp when the graph was saved    
        
    /* UI RELATED COMPONENTS */
    private Tab uitabProjectTab; // The Tab for this Project
    private AnchorPane anchGraphPane; // Anchor Pane that would hold the 
    private TabPane tabpaneGraph; // The TabPane for all the graphs
    private MeerkatTreeItem<String> treeProject ; // The Tree Item for this project
    
    // Adding the TextualTab that can contain multiple GraphTabs
    private Map<Integer, TextualTab> mapTextualTabs ;
    private Boolean ProjectModifiedStatus; // Status whether the project has been modifed after last save event
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    // ProjectID
    public int getProjectID() {
        return this.intProjectID;
    }
    public void setProjectID(int pintProjectID) {
        this.intProjectID = pintProjectID;
    }
    public String getProjectIDString() {
        return Integer.toString(intProjectID);
    }
    
    // ProjectName
    public String getProjectName() {
        return this.strProjectName;
    }
    public void setProjectName(String pstrProjectName) {
        this.strProjectName = pstrProjectName;
    }    
    
    // ProjectFilePath
    public String getProjectDirectory() {
        return this.strProjectDirectory;
    }
    public void setProjectDirectory(String pstrFileProjectPath) {
        this.strProjectDirectory = pstrFileProjectPath;
    }
    
    // ProjectIconPath
    public String getProjectIconPath () {
        return this.strIconPath;
    }
    public void setProjectIconPath (String pstrIconPath) {
        this.strIconPath = pstrIconPath;
    }
    
    // ActiveGraphID
    public int getActiveGraphID () {
        return this.intActiveGraphID;
    }
    
    public void setActiveGraphID (int pintActiveGraphID) {
        this.intActiveGraphID = pintActiveGraphID;
    }
    
    // ActiveTextualID
    public void setActiveTextualID(int pintActiveTextualID) {
        this.intActiveTextualTabID = pintActiveTextualID;
    }
    public int getActiveTextualID () {
        return this.intActiveTextualTabID;
    }
    
    // RawDataFilePath
    public List<String> getInputFilePath() {
        return this.lststrRawDataFilePath;
    }
    public void setInputFilePath(List<String> plststrRawDataFile) {
        this.lststrRawDataFilePath.addAll(plststrRawDataFile);
    }
    public void addInputFilePath(String pstrFilePath)  {
        this.lststrRawDataFilePath.add(pstrFilePath) ;
    }
    

    // GraphTabs
    public Map<Integer, GraphTab> getAllGraphTabs() {
        return this.mapGraphTabs;
    }
    
    public void removeGraphTabFromMapGraphs(int pintGraphId) {
        if(this.mapGraphTabs.remove(pintGraphId)!=null){
            this.mapGraphTabs.remove(pintGraphId);
        }
    }
    
    // TextualTab
    public Map<Integer, TextualTab> getAllTextualTabs() {
        return this.mapTextualTabs;
    }
    
    // Graph TabPane
    public TabPane getGraphTabPane () {
        return this.tabpaneGraph;
    }
    
    public void removeGraphTabFromTabPane (GraphTab pGraphTab) {
        this.tabpaneGraph.getTabs().remove(pGraphTab.getUITab());
    }
    
    // AnchorPane
    private AnchorPane getGraphAnchorPane() {
        return this.anchGraphPane;
    }
    
    // Active Graph
    public GraphTab getActiveGraphTab() {
        return this.mapGraphTabs.get(this.intActiveGraphID);
    }
    
    public TextualTab getActiveTextualTab() {
        // #DEBUG
        /*
        System.out.println("ProjectTab.getActiveTextualTab(): Active ID is "+this.intActiveTextualTabID);
        System.out.println("ProjectTab.getActiveTextualTab(): Size: "+mapTextualTabs.size()); 
        if (mapTextualTabs.containsKey(this.intActiveTextualTabID)) {
            System.out.println("ProjectTab.getActiveTextualTab(): Found");
        }
        */
        // #ENDDEBUG
        
        return this.mapTextualTabs.get(this.intActiveTextualTabID);
    }
    
    // A Specific Graph Tab
    public GraphTab getGraphTab(int pintGraphID){
        return this.mapGraphTabs.get(pintGraphID);
    }
    
    public TextualTab getTextualTab(int pintTextualID) {
        return this.mapTextualTabs.get(pintTextualID);
    }
    
    // GraphCount
    public int getGraphCount() {
        return this.mapGraphTabs.size();
    }
    
    public int getTextualCount() {
        return this.mapTextualTabs.size();
    }
            
    // ProjectTab
    public Tab getProjectUITab() {
        return this.uitabProjectTab;
    }
    
    // TreeItem - Project
    public MeerkatTreeItem<String> getTreeItemProject() {
        return this.treeProject;
    }
    
    // SavedTime
    public long getSavedTime() {
        return this.lngSavedTime;
    }
    public void setSavedTime(long plngSavedTime) {
        this.lngSavedTime = plngSavedTime;
    }
    
    public Boolean getProjectModifiedStatus(){
        return ProjectModifiedStatus;
    }
    public void setProjectModifiedStatus(Boolean pBooleanProjectModifiedStatus){
        ProjectModifiedStatus = pBooleanProjectModifiedStatus;
    }
       
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    /**
     *  Method Name     : Constructor
     *  Created Date    : 2015-07-31
     *  Description     : Initializes some of the components of the ProjectTab
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public ProjectTab () {
        this.mapGraphTabs = new HashMap<>(); // Initializing the list of GraphIDs for a ProjectTab
        this.mapTextualTabs = new HashMap<>();
        this.treeProject = new MeerkatTreeItem(TreeItemType.PROJECTTITLE, intProjectID);
        
        this.lststrRawDataFilePath = new ArrayList<>();
        
        initiateGraphTabPane();
        
        this.anchGraphPane = new AnchorPane();
        
        this.uitabProjectTab = new Tab();
        this.uitabProjectTab.setId(getProjectIDString());        
        this.uitabProjectTab.setText(getProjectName());   
        this.uitabProjectTab.setClosable(true);  
        this.uitabProjectTab.setOnCloseRequest(e -> {
            e.consume();
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            System.out.println("ProjectTab.ProjectTab(): On Closing called");
            ProjectCloseConfirmDialog.Display(UIInstance.getController().getPrimaryStage(), UIInstance.getController(), this.ProjectModifiedStatus);
            UIInstance.UpdateUI(); 
        });
        
        this.ProjectModifiedStatus = false; // false when a project is loaded initially
    }
    
    
           
    /**
     *  Method Name     : Constructor
     *  Created Date    : 2015-08-06
     *  Description     : Initializing a new Project by the user and it will not contain any graphs yet, in it
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public ProjectTab(int pintProjectID) {
        this();
        this.intProjectID = pintProjectID;
        this.ProjectModifiedStatus = false; // false when a project is loaded initially        
        // Initiate All the UI components
        initiateUIComponents();
    }
    
    /**
     *  Method Name     : Constructor
     *  Created Date    : 2015-08-06
     *  Description     : Initializing a new Project by the user and it will not contain any graphs yet, in it
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pstrProjectName : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public ProjectTab(int pintProjectID, String pstrProjectName) {
        this();
        this.intProjectID = pintProjectID;
        this.strProjectName = pstrProjectName;        
        this.ProjectModifiedStatus = false; // false when a project is loaded initially        
        // Initiate All the UI components
        initiateUIComponents();
    }
    
    
        
    /**
     *  Method Name     : Copy Constructor
     *  Created Date    : 2015-09-11
     *  Description     : Initializing a new Project by the user and it will contain 
     *                    all the graphs of the existing ProjectTab
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pProjectTab : ProjectTab
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public ProjectTab(ProjectTab pProjectTab){
        
        this();
        this.intProjectID = pProjectTab.getProjectID() ;
        this.strProjectName = pProjectTab.getProjectName() ;
        
        this.mapGraphTabs.putAll(pProjectTab.getAllGraphTabs());
        this.intActiveGraphID = pProjectTab.getActiveGraphID();
        
        this.mapTextualTabs.putAll(pProjectTab.getAllTextualTabs());        
        this.intActiveTextualTabID = pProjectTab.getActiveTextualID();
        this.ProjectModifiedStatus = false; // false when a project is loaded initially
        // Initiate All the UI components
        initiateUIComponents();
    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : initiateUIComponents
     *  Created Date    : 2015-07-31
     *  Description     : Initializing the UI components of the ProjectTab
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void initiateUIComponents() {
        // Initialize the UI components        
        this.anchGraphPane.getChildren().add(getGraphTabPane()); // Making the GraphPane of the project to be the child of the AnchorPane
        UIUtilities.StretchToParent(getGraphTabPane());
        
        uitabProjectTab.setId(getProjectIDString());
        uitabProjectTab.setText(getProjectName());
        uitabProjectTab.setContent(getGraphAnchorPane());     
        uitabProjectTab.setClosable(true);
        
        // Initiate the UI Tree View
        this.treeProject = new MeerkatTreeItem(getProjectName(), TreeItemType.PROJECTTITLE, intProjectID);
        
        ThreadTree treeInstance = ThreadTree.getTreeInstance();
        // System.out.println("ProjectTab.initiateUIComponents(): Adding Project "+intProjectID+" for "+this.treeProject);
        treeInstance.addItem(this.treeProject, this.intProjectID);
        
        // For each of the Graphs, that are available, append them to the tabpaneGraph
        for (GraphTab currentGraphTab : mapGraphTabs.values()) {
            tabpaneGraph.getTabs().add(currentGraphTab.getUITab());
            tabpaneGraph.getSelectionModel().select(currentGraphTab.getUITab());
            tabpaneGraph.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);  
            
            treeProject.getChildren().add(currentGraphTab.getTreeItemGraph());
        }
        
        
        // System.out.println("ProjecTab.initiateUIComponents(): Number of Textual Tabs in Project ("+this.intProjectID+") "+this.mapTextualTabs.size());
        for (TextualTab currentTextualTab : this.mapTextualTabs.values()) {
            treeProject.getChildren().add(currentTextualTab.getTreeItemGraph());
        }
        
    }
    
    /**
     *  Method Name     : initiateGraphTabPane
     *  Created Date    : 2015-08-30
     *  Description     : The components of the visualization pane are initialized
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void initiateGraphTabPane () {
        this.tabpaneGraph = new TabPane(); // Initializing the GraphTabPane
        UIUtilities.StretchToParent(getGraphTabPane());
        addTabChangeListeners();
    }
    
    
    /********************** METHODS ******************************/    
    /**
     *  Method Name     : addDataFileToProject
     *  Created Date    : 2015-08-12
     *  Description     : Adds a data file to the project (while loading datafile) 
     *                      and creates a graph(or a list of graph)
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrInputFilePath : String
     *  @param pstrReaderID : String
     *  @param pGraphType : GraphType
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-10-13      Talat           Added the implementation of the textual graph case
     * 
    */
    public void addDataFileToProject(String pstrInputFilePath, String pstrReaderID, GraphType pGraphType) {
        
        int intGraphID ;
                
        switch(pGraphType) {
            case GRAPH :
                /* If it is a general graph, add the graph tab and display it. Sequence of Steps are
                    1) Parse the Input File. Should return a list of GraphIDs
                    2) Create GraphTabs for each GraphID and add the GraphTab to this ProjectTab
                    3) Reflect the changes in the UI
                */
                
                intGraphID = LoadingAPI.LoadGraphFile(
                        this.intProjectID, pstrReaderID, pstrInputFilePath);
                
                this.addGraphTab(new GraphTab(
                        this.intProjectID, intGraphID, pGraphType));
                this.intActiveGraphID = intGraphID; // Setting the current graph to be active                      
                
                break;
            case TEXTUALGRAPH:
                // If it is a textual graph, then just add the elements to the Thread Tree View 
                // and no special graphs would be created                
                int intTextualNetworkId = LoadingAPI.LoadTextualFile(this.intProjectID, pstrReaderID, pstrInputFilePath) ;
                if (intTextualNetworkId > -1) {
                    // List<String> lstContent = ThreadTreeAPI.getThreadTree(intProjectID, intRootMsgID);
                    // Create a new TextualTab
                    // String strFileName = Utilities.getFileNameWithoutExtention(pstrInputFilePath);
                    this.addTextualTab(new TextualTab(intProjectID, intTextualNetworkId, pstrInputFilePath));
                    this.intActiveTextualTabID = intTextualNetworkId ;
                }
                this.intActiveTextualTabID = intTextualNetworkId;
                // If there are no Graphs to be added, then the project add the Thread tree to the Project               
                
                break;    
        }
    }
    
    
    public int addFileToProject(String pstrInputFilePath, String pstrReaderID, GraphType pGraphType) {
        
        int intGraphID = 0 ;
                
        switch(pGraphType) {
            case GRAPH :
                /* If it is a general graph, add the graph tab and display it. Sequence of Steps are
                    1) Parse the Input File. Should return a list of GraphIDs
                    2) Create GraphTabs for each GraphID and add the GraphTab to this ProjectTab
                    3) Reflect the changes in the UI
                */
                
                intGraphID = LoadingAPI.LoadGraphFile(this.intProjectID, pstrReaderID, pstrInputFilePath);
                
                if(intGraphID>=0)
                    this.addGraphTab(new GraphTab(this.intProjectID, intGraphID, pGraphType));
                // this.intActiveGraphID = intGraphID;
                // this.intActiveGraphID = lstintGraphIDs.get(0); // Setting the current graph to be active                      
                
                break;
            
            /* REMOVED FROM VERSION 1.0
            case TEXTUALGRAPH:
                // If it is a textual graph, then just add the elements to the Thread Tree View 
                // and no special graphs would be created                
                int intTextualNetworkId = LoadingAPI.LoadTextualFile(this.intProjectID, pstrReaderID, pstrInputFilePath) ;
                if (intTextualNetworkId > -1) {
                    // List<String> lstContent = ThreadTreeAPI.getThreadTree(intProjectID, intRootMsgID);
                    // Create a new TextualTab
                    // String strFileName = Utilities.getFileNameWithoutExtention(pstrInputFilePath);
                    this.addTextualTab(new TextualTab(intProjectID, intTextualNetworkId, pstrInputFilePath));
                    this.intActiveTextualTabID = intTextualNetworkId ;
                }
                this.intActiveTextualTabID = intTextualNetworkId;
                // If there are no Graphs to be added, then the project add the Thread tree to the Project               
                
                break; 
            */
        }
        
        return intGraphID;
    }
    
    
    /**
     *  Method Name     : addGraphTab
     *  Created Date    : 2015-08-07
     *  Description     : Updating the UI components, by adding a GraphTab to the hashmap of GraphTabs
     *                  : and then updating the corresponding UI elements
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pGraphTab : GraphTab
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void addGraphTab (GraphTab pGraphTab) {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance(); 
       
        this.mapGraphTabs.put(pGraphTab.getGraphID(), pGraphTab);
        
        // Updating the Canvas
        tabpaneGraph.getTabs().add(pGraphTab.getUITab());
        tabpaneGraph.getSelectionModel().select(pGraphTab.getUITab());
        /* Make the graph tab unclosable. The graph tab can be closed when its project is closed  */
        //tabpaneGraph.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tabpaneGraph.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);         
        
        // Updating the Thread Tree View
        this.treeProject.getChildren().add(pGraphTab.getTreeItemGraph());        
        
        // Updating the Menu and Tools bar
        UIInstance.UpdateUI();
        
        // Updating the Options Pane
    }
    
    public void addTextualTab (TextualTab pTextTab) {
        this.mapTextualTabs.put(pTextTab.getID(), pTextTab);
        System.out.println("ProjectTab.addTextualTab(): Size (ProjectID: "+this.intProjectID+" & Tab ID: "+pTextTab.getID()+") "+this.mapTextualTabs.size());
        this.intActiveTextualTabID = pTextTab.getID();
        // Updating the ThreadTree View
        this.treeProject.getChildren().add(pTextTab.getTreeItemGraph());
    }
    
    /**
     *  Method Name     : getFileNameFromPath
     *  Created Date    : 2015-08-12
     *  Description     : Takes the file path as input and returns only the file name (along with the file extension)
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrInputFilePath : String
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-08-24      Talat           Added the Check condition of non-occurrence of "/"
     * 
    */
    private String getFileNameFromPath(String pstrInputFilePath) {
        int intIndex = pstrInputFilePath.lastIndexOf("/")+1;
        if (intIndex == -1) {
            intIndex = 0;
        }
        return pstrInputFilePath.substring(intIndex).trim();
    }
    
    
    /**
     *  Method Name     : initiateTabbedPane
     *  Created Date    : 2015-08-28
     *  Description     : Initiating the Listener Properties for the TabPane
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void addTabChangeListeners() {        
        // System.out.println("ProjectTab.initateTabbedPane(): Invoked");
        getGraphTabPane().getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Tab>() {
                @Override
                public void changed(ObservableValue<? extends Tab> ov, Tab tabSource, Tab tabDestination) {
                    if (tabDestination != null) {
                        OnTabChange(tabDestination);
                        // System.out.println("ProjectTab.addTabChangeListeners() for GraphTabs: Listener Called for changing graph tabs");
                    }
                }
            }
        );        
        /*
        // Above function as Lambda Expression
        tabpaneGraph.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> ov, Tab tabSource, Tab tabDestination) -> {
            OnTabChange(tabDestination);
        });
        */
    }
    
    /**
     *  Method Name     : OnTabChange
     *  Created Date    : 2015-08-28
     *  Description     : Set of Actions to be taken when the Graph tab has been changed by the user
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param ptabDestination : Tab
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-09-10      Talat           Setting the Active Graph ID - updating at this place would reflect the active graph ID at all locations
     * 
    */
    private void OnTabChange(Tab ptabDestination) {
        System.out.println();
        // System.out.println("ENTRY ProjectTab.OntabChange");
        int intGraphID = Integer.parseInt(ptabDestination.getId());
        int intTimeFrameIndex = getGraphTab(intGraphID).getTimeFrameIndex();
        int intTotalTimeFrames = getGraphTab(intGraphID).getTotalTimeFrames() ;
        
        // Set the Active GraphID to the changed id
        // System.out.println("From ProjectTab. OnTabChange CCCCCCCCCCCCalling setActiveGraphID with param = " + intGraphID);
        setActiveGraphID(intGraphID);
                
        // Create the UI instance to get the handles to all the UI components
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        // Update the Tree
        UIInstance.getController().getTreeView().getSelectionModel()
                .select(getAllGraphTabs().get(intGraphID).getTreeItemGraph());
        

        // Update the Details pane         
        AccordionTabContents accordionTab =
                AccordionTabContents.getAccordionTabInstance();
        accordionTab.updateAccordionTabs(
                  UIInstance.getController().getAccordion()
                , UIInstance.getController().getDetails()
                , UIInstance.getController().getLayouts()
                , UIInstance.getController().getFilters()
                , UIInstance.getController().getStatistics()
                , UIInstance.getController().getCommunities()
                , this.intProjectID
                , intGraphID
                , intTimeFrameIndex
                , getGraphTab(intGraphID).getAccordionTabValues());
        
//        MAHDI: I uncommented these two lines.
        //Update the DC Mining Component 
        Boolean DCMiningStatus = UIInstance.getActiveProjectTab().getActiveGraphTab().getDCommMiningStatus();
        UIInstance.getController().updateDCMiningPanel(intProjectID, intGraphID, intTimeFrameIndex, intTotalTimeFrames, DCMiningStatus);
        
        // Update other UI elements
        getGraphTab(intGraphID).getMinimapDelegator().updateMinimap();
        UIInstance.UpdateUI();
        UpdateEditingTools_ModeInformation();
        // System.out.println("EXIT ProjectTab.OntabChange");
    }
    
    /**
     * Updates the Editing Toolbox from the Mode Type
     * 
     * @author Talat
     * @since 2018-05-30
     */
    public void UpdateEditingTools_ModeInformation(){
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        GraphTab currentGraph = UIInstance.getActiveProjectTab().getActiveGraphTab();
        EditingToolBox editToolBox = EditingToolBox.getInstance();
        if (editToolBox != null) {
            editToolBox.activateMode(currentGraph.getModeInformationUI().getModeType());
        }
    }
    
    /**
     *  Method Name     : removeTreeItem
     *  Created Date    : 2015-09-10
     *  Description     : Removes the tree view for the specific Project whose tab is closed
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void removeTreeItem() {        
        treeProject.getParent().getChildren().remove(treeProject);        
    }
    
    /**
     *  Method Name     : addMiniMap()
     *  Created Date    : 2016-02-24
     *  Description     : Adds a MiniMap to all the graphs that are available and returns the count of graphs for which the minimap was added
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    
    /*
    public int addMiniMap() {
        int intReturn = 0 ; // -1 would be returned in case of any errors        
        try {
            // Loop through each GraphTab and add a minimap to it
            for (GraphTab graphCurrent : this.getAllGraphTabs().values()) {
                intReturn += graphCurrent.addMiniMap() ;
            }
        } catch (Exception ex) {
            intReturn = -1 ;
        } finally {
            return intReturn ;
        }       
    }
    */
    /**
     *  Method Name     : getProjectDataFromUI()
     *  Created Date    : 2017-04-18
     *  Description     : returns a map of location of vertices relative to the PanAndZoomPane (UI) for all graphs of this project
     *  Version         : 1.0
     *  @author         : Abhi
     *  
     *  @return Map<Integer, Map<Integer, Map<Integer, Double[]>>>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public Map<Integer, Map<Integer, Map<Integer, Double[]>>> getProjectDataFromUI(){
    
            
            // For this project, get its graphs. 
            // for each graph - get its timeframes. 
            // for each timeframe get its graph canvas and get the location of each vertex in the PanAndZoomPane.
            
            // Map<graphId, Map<timeFrame, Map<VertexId, arrXY[2]>>> mapProjectAllVerticesLocation;
            Map<Integer, Map<Integer, Map<Integer, Double[]>>> mapProjectsAllVerticesLocation = new HashMap();
            
            //Map<Integer, GraphTab> mapGraphTabs = UIInstance.getActiveProjectTab().getAllGraphTabs();
            Map<Integer, GraphTab> mapGraphTabs = this.getAllGraphTabs();
            for (Integer graphId : mapGraphTabs.keySet()){
                
                Map<Integer, Map<Integer, Double[]>> mapGraphTimeFramesVerticesLocation = new HashMap();
                mapProjectsAllVerticesLocation.put(graphId, mapGraphTimeFramesVerticesLocation);
                
                GraphTab graphTab = mapGraphTabs.get(graphId);
                Map<Integer, GraphCanvas> graphCanvas = graphTab.getGraphCanvasMap();
                
                for(int timeFrame : graphCanvas.keySet()){
                    
                    Map<Integer, Double[]> mapVertexLocation =  graphCanvas.get(timeFrame).getVerticesCoordinatesAbsolute();
                    mapGraphTimeFramesVerticesLocation.put(timeFrame, mapVertexLocation);
                
                }
            }
            return mapProjectsAllVerticesLocation;
    }
    
    
    public void deleteActiveGraph() {
     
         /*
         1. get active graph is from ui
         
         2. remove it from Logic delete its file from hard disk
         3. remove from Project.java mapGraphs
         4. modify .mprj file
          
         
         5. remove it from graphMap in Project tab
         6. remove it from project tab
        
         7. delete its TreeItem
         */
        String message = ""; 
        try {
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            
            //delete from logic first - then UI
            GraphTab activeGraphTab = UIInstance.getActiveProjectTab().getActiveGraphTab();
            if (activeGraphTab != null) {

                message = message + ProjectAPI.deleteGraph(UIInstance.getActiveProjectTab().getProjectID(), activeGraphTab.getGraphID(), AppConfig.EXTENSION_PROJECTFILE, AppConfig.PROJECTFILE_DELIMITER, AppConfig.GRAPHLIST_DELIMITER);
            
                this.removeGraphTabFromMapGraphs(activeGraphTab.getGraphID());
                this.treeProject.getChildren().remove(activeGraphTab.getTreeItemGraph());
                this.removeGraphTabFromTabPane(activeGraphTab);
                //TODO - Check whether its needed or not to update the flag
                //In logic, mprj file is updated and the graph file is deleted
                //update project status
                //ProjectStatusTracker.updateProjectModifiedStatus(this.getProjectID(), ProjectStatusTracker.eventGraphDeleted);
            
            } else {
                
                System.out.println("Error in deleting graph - Graph Tab does not exist");
                message = "Error in deleting graph - Graph Tab does not exist";
            }
            
        }catch(Exception e){
         message = message + e.toString();
        }
        
        
        //display this message
        InfoDialog.Display(message, -1); 
    }
    
    public void deleteGraph(GraphTab graphTab) {
     
         /*
         1. 
         
         2. remove it from Logic delete its file from hard disk
         3. remove from Project.java mapGraphs
         4. modify .mprj file
          
         
         5. remove it from graphMap in Project tab
         6. remove it from project tab
        
         7. delete its TreeItem
         */
        String message = ""; 
        try {
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            
            //delete from logic first - then UI
            //GraphTab graphTab = UIInstance.getActiveProjectTab().getActiveGraphTab();
            if (graphTab != null) {

                message = message + ProjectAPI.deleteGraph(UIInstance.getActiveProjectTab().getProjectID(), graphTab.getGraphID(), AppConfig.EXTENSION_PROJECTFILE, AppConfig.PROJECTFILE_DELIMITER, AppConfig.GRAPHLIST_DELIMITER);
            
                this.removeGraphTabFromMapGraphs(graphTab.getGraphID());
                this.treeProject.getChildren().remove(graphTab.getTreeItemGraph());
                this.removeGraphTabFromTabPane(graphTab);
                //TODO - Check whether its needed or not to update the flag
                //In logic, mprj file is updated and the graph file is deleted
                //update project status
                //ProjectStatusTracker.updateProjectModifiedStatus(this.getProjectID(), ProjectStatusTracker.eventGraphDeleted);
            
            } else {
                
                System.out.println("Error in deleting graph - Graph Tab does not exist");
                message = "Error in deleting graph - Graph Tab does not exist";
            }
            
        }catch(Exception e){
         message = message + e.toString();
        }
        
        
        //display this message
        InfoDialog.Display(message, -1); 
    }
    
    /**
     *  Method Name     : isGraphOpen()
     *  Created Date    : 2017-08-25
     *  Description     : checks if the graph file is already existing in the current active project.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pstrGraphFileName
     *  @return : boolean
     * 
    */
    
    public boolean isGraphOpen(String pstrGraphFileName) {
        String strGraphName = Utilities.getFileNameWithoutExtention(pstrGraphFileName);

        for (GraphTab currentGraph : this.getAllGraphTabs().values()) {
            if (strGraphName.equalsIgnoreCase(currentGraph.getGraphTabTitle())) {
                return true;
            }
        }
        return false;
    }
    
  
}
