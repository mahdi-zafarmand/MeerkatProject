/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadtree;

import analysisscreen.AnalysisController;
import config.LangConfig;
//import emotionscreen.EmotionController;
import globalstate.MeerkatUI;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
//import polarityscreen.PolarityController;
import threadtree.TreeItemConfig.TreeItemType;
//import topicModelScreen.TopicModelControllerLDA;
import ui.dialogwindow.NewProjectWizard;
import ui.dialogwindow.OpenProject;
import ui.utilities.DeleteGraph;
import ui.utilities.DeleteProject;
import ui.utilities.LoadGraph;
import ui.utilities.RenameGraph;

/**
 *  Class Name      : MeerkatTreeItem<V>
 *  Created Date    : 2016-03-28
 *  Description     : TreeItem with ID and the type of the TreeItem it represents (Check TreeItemConfig for types of TreeItems)
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class MeerkatTreeItem<V> extends TreeItem<V>{
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private TreeItemType ticTreeItemType ;
    private int intID ;
    
    // MenuItems of teh Context
    MenuItem newProjectMenuItem ; // ADD A NEW PROJECT
    MenuItem loadProjectMenuItem ; // LOAD AN EXISTING PROJECT
    MenuItem removeProjectMenuItem ; // DELETE AN EXISTING PROJECT
    MenuItem renameProjectMenuItem ; // RENAME AN EXISTING PROJECT
    
    MenuItem loadGraphMenuItem; // LOAD AN EXISTING GRAPH        
    MenuItem newGraphMenuItem ; // ADD A NEW GRAPH        
    MenuItem removeGraphMenuItem ; // REMOVE AN EXISTING GRAPH        
    MenuItem renameGraphMenuItem ; // RENAME AN EXISTING GRAPH
    
    MenuItem loadTextualGraphMenuItem ; // LOAD AN EXISTING TEXTUALGRAPH        
    MenuItem newTextualGraphMenuItem ; // ADD A NEW TEXTUALGRAPH        
    MenuItem removeTextualGraphMenuItem ; // REMOVE AN EXISTING TEXTUALGRAPH      
    MenuItem renameTextualGraphMenuItem ; // RENAME AN EXISTING TEXTUAL GRAPH
    
    MenuItem addVertexMenuItem ; // ADD A NEW VERTEX        
    MenuItem removeVertexMenuItem ; // REMOVE AN EXISTING VERTEX        
    
    MenuItem addEdgeMenuItem ; // ADD A NEW EDGE        
    MenuItem removeEdgeMenuItem ; // REMOVE AN EXISTING EDGE        
    
    MenuItem addSubThreadMenuItem ; // ADD A SUBTHREAD TO THE HIERARCHY
    MenuItem addContentMenuItem ; // ADD A CONTENT TO THE MENU ITEM
    MenuItem generateGraphMenuItem ; // GENERATE GRAPH        
    MenuItem emotionAnalysisMenuItem ; // EMOTION ANALYSIS        
    MenuItem polarityGraphMenuItem ; // POLARITY ANALYSIS        
    MenuItem wordCloudMenuItem ; // WORD CLOUD        
    MenuItem topicModellingMenuItem ; // TOPIC MODELLING
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public TreeItemType getTreeItemType(){
        return ticTreeItemType ;
    }    
    protected void setTreeItemType(TreeItemType pticTreeItemType) {
        this.ticTreeItemType = pticTreeItemType;
    }
    
    public int getID () {
        return this.intID ;
    }
    public void setID (int pintID) {
        this.intID = pintID ;
    }
    
    public void setTreeItemValue(V pvContent){
        this.setValue(pvContent);
    }
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    public MeerkatTreeItem(TreeItemType pticType, int pintID) {
        super();
        this.intID = pintID ;
        this.ticTreeItemType = pticType ;
        initiateContextMenus();
    }
    
    public MeerkatTreeItem(V pVContent, TreeItemType pticType, int pintID) {
        super(pVContent);
        this.intID = pintID ;
        this.ticTreeItemType = pticType;
        initiateContextMenus();
    }    
    
    public void initiateContextMenus() {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        AnalysisController pController = UIInstance.getController();
        TreeView treeView = UIInstance.getController().getTreeView();  
        
        // ThreadTree threadTreeInstance = ThreadTree.getTreeInstance();
        
        // ADD A NEW PROJECT
        newProjectMenuItem = new MenuItem(LangConfig.OPTIONS_NEWPROJECT);
        newProjectMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                NewProjectWizard.Display(pController);
            }
        });
        
        // LOAD AN EXISTING PROJECT
        loadProjectMenuItem = new MenuItem(LangConfig.OPTIONS_LOADPROJECT);
        loadProjectMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                OpenProject.Display(pController);
                        
            }
        });
        
        // DELETE AN EXISTING PROJECT
        removeProjectMenuItem = new MenuItem(LangConfig.OPTIONS_REMOVEPROJECT);
        removeProjectMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                DeleteProject.deleteProject(pController);
            }
        });
        
        // RENAME AN EXISTING PROJECT
        renameProjectMenuItem = new MenuItem(LangConfig.OPTIONS_RENAMEPROJECT);
        renameProjectMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
            }
        });
        
        // LOAD A GRAPH
        loadGraphMenuItem = new MenuItem(LangConfig.OPTIONS_LOADGRAPH);
        loadGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LoadGraph.load(pController);
            }
        });
        
        // ADD A NEW GRAPH
        newGraphMenuItem = new MenuItem(LangConfig.OPTIONS_NEWGRAPH);
        newGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
            }
        });
        
        // REMOVE A GRAPH
        removeGraphMenuItem = new MenuItem(LangConfig.OPTIONS_REMOVEGRAPH);
        removeGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                DeleteGraph.delete(pController);
                //UIInstance.getActiveProjectTab().deleteActiveGraph();
            }
        });
        
        // RENAME A GRAPH
        renameGraphMenuItem = new MenuItem(LangConfig.OPTIONS_RENAMEGRAPH);
        renameGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                RenameGraph.rename(pController);
            }
        });
        
        // LOAD A TEXTUALGRAPH
        loadTextualGraphMenuItem = new MenuItem(LangConfig.OPTIONS_LOADTEXTUALGRAPH);
        loadTextualGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LoadGraph.load(pController);
            }
        });
        
        // ADD A NEW TEXTUALGRAPH
        newTextualGraphMenuItem = new MenuItem(LangConfig.OPTIONS_NEWTEXTUALGRAPH);
        newTextualGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
            }
        });
        
        // REMOVE A TEXTUALGRAPH
        removeTextualGraphMenuItem = new MenuItem(LangConfig.OPTIONS_REMOVETEXTUALGRAPH);
        removeTextualGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
            }
        });        
        
        // RENAME A TEXTUALGRAPH
        renameTextualGraphMenuItem = new MenuItem(LangConfig.OPTIONS_RENAMETEXTUALGRAPH);
        renameTextualGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
            }
        });
        
        // ADD A SUBTHREAD MENU
        addSubThreadMenuItem = new MenuItem(LangConfig.OPTIONS_ADDSUBTHREADMENU);
        addSubThreadMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
            }
        });
        
        // ADD A CONTENT TO
        addContentMenuItem = new MenuItem(LangConfig.OPTIONS_ADDCONTENT);
        addContentMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
            }
        });
        
        // ADD A VERTEX
        addVertexMenuItem = new MenuItem(LangConfig.OPTIONS_ADDNODE);  
        int intVertexID =0 ;
        addVertexMenuItem.setOnAction(new EventHandler() { // Get the ID for the Node
            public void handle(Event t) {
                // MeerkatTreeItem newNode = new MeerkatTreeItem(LangConfig.OPTIONS_NEWNODE, TreeItemType.VERTICES, intVertexID);
                // getTreeItem().getChildren().add(newNode);
            }
        });
        
        // REMOVE A VERTEX
        removeVertexMenuItem = new MenuItem(LangConfig.OPTIONS_REMOVENODE);
        removeVertexMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // System.out.println("TextFieldTreeCellImpl.TextFieldTreeCellImpl(): Node to be Removed");
            }
        });
        
        // ADD AN EDGE
        addEdgeMenuItem = new MenuItem(LangConfig.OPTIONS_ADDEDGE);        
        int intEdgeID = 0;
        addEdgeMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) { // Add the ID for the Edge
                // MeerkatTreeItem newNode = new MeerkatTreeItem(LangConfig.OPTIONS_NEWEDGE, TreeItemType.EDGES, intEdgeID);
                // getTreeItem().getChildren().add(newNode);
            }
        });
        
        // REMOVE AN EDGE
        removeEdgeMenuItem = new MenuItem(LangConfig.OPTIONS_REMOVEEDGE);            
        removeEdgeMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // System.out.println("TextFieldTreeCellImpl.TextFieldTreeCellImpl(): Edge to be Removed");
            }
        });
        
        // GENERATE GRAPH
        generateGraphMenuItem = new MenuItem(LangConfig.OPTIONS_GENERATEGRAPH);        
        generateGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // System.out.println("TextFieldTreeCellImpl.TextFieldTreeCellImpl(): Generate Graph invoked");
            }
        });
        /*
        // EMOTION ANALYSIS
        emotionAnalysisMenuItem = new MenuItem(LangConfig.OPTIONS_EMOTION);
        emotionAnalysisMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
                System.out.println("TextFieldTreeCellImpl.TextFieldTreeCellImpl(): Emotion Analysis Invoked");
                final EmotionController emtnController = new EmotionController();   
                       
                ThreadTree threadTreeInstance = ThreadTree.getTreeInstance();
                
                MeerkatTreeItem<String> treeitemSelected = (MeerkatTreeItem)treeView.getSelectionModel().getSelectedItem();
                                
                int intMsgID = threadTreeInstance.getID(treeitemSelected);
                int intProjectID = UIInstance.getActiveProjectTab().getProjectID();
                int intRootMsgID = UIInstance.getActiveProjectTab().getActiveTextualTab().getID();
                
                System.out.println("TextFieldTreeCellImpl.TextFieldTreeCellImpl(): Message ID sent is "+intMsgID);
                System.out.println("MeerkatTreeItem:EMOTIONcontroller : " + " treeitemselected: " + treeitemSelected.toString());

                emtnController.Display(intProjectID, intMsgID, intRootMsgID);


            }
        });
        
        // POLARITY ANALYSIS
        polarityGraphMenuItem = new MenuItem(LangConfig.OPTIONS_POLARITY);        
        polarityGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                ThreadTree threadTreeInstance = ThreadTree.getTreeInstance();
                
                System.out.println("TextFieldTreeCellImpl.TextFieldTreeCellImpl(): Polarity Analysis Invoked");
                final PolarityController plrController = new PolarityController();
                               
                MeerkatTreeItem<String> treeitemSelected = (MeerkatTreeItem)treeView.getSelectionModel().getSelectedItem();
                
                int intMsgID = threadTreeInstance.getID(treeitemSelected);
                int intProjectID = UIInstance.getActiveProjectTab().getProjectID();
                int intRootMsgID = UIInstance.getActiveProjectTab().getActiveTextualTab().getID();
                        
                System.out.println("TextFieldTreeCellImpl.TextFieldTreeCellImpl(): Message ID sent is "+intMsgID);
                
                plrController.Display(intProjectID, intMsgID, intRootMsgID);
            }
        });
        
        // WORD CLOUD
        wordCloudMenuItem = new MenuItem(LangConfig.OPTIONS_WORDCLOUD);        
        wordCloudMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
                ThreadTree threadTreeInstance = ThreadTree.getTreeInstance();
                
                System.out.println("TextFieldTreeCellImpl.TextFieldTreeCellImpl(): Polarity Analysis Invoked");
                                               
                MeerkatTreeItem<String> treeitemSelected = (MeerkatTreeItem)treeView.getSelectionModel().getSelectedItem();
                
                int intMsgID = threadTreeInstance.getID(treeitemSelected);
                int intProjectID = UIInstance.getActiveProjectTab().getProjectID();
                int intRootMsgID = UIInstance.getActiveProjectTab().getActiveTextualTab().getID();
                
                // Get the list of Msgs from the MsgID
                
                
            }
        });
        
        // TOPIC MODELLING
        topicModellingMenuItem = new MenuItem(LangConfig.OPTIONS_TOPICMODELLING);        
        topicModellingMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
                System.out.println("TextFieldTreeCellImpl.TextFieldTreeCellImpl(): TopicModellingLDA Invoked");
                final TopicModelControllerLDA  tpcLDAController = new TopicModelControllerLDA();   
                       
                ThreadTree threadTreeInstance = ThreadTree.getTreeInstance();
                
                MeerkatTreeItem<String> treeitemSelected = (MeerkatTreeItem)treeView.getSelectionModel().getSelectedItem();
                                
                int intMsgID = threadTreeInstance.getID(treeitemSelected);
                int intProjectID = UIInstance.getActiveProjectTab().getProjectID();
                int intRootMsgID = UIInstance.getActiveProjectTab().getActiveTextualTab().getID();
                
                System.out.println("TextFieldTreeCellImpl.TextFieldTreeCellImpl(): Message ID sent is "+intMsgID);
                System.out.println("tpcmodellingcontroller : "+intMsgID + " , " + intRootMsgID + ", treeitem: " + treeitemSelected.toString());
                tpcLDAController.Display(intProjectID, intMsgID, intRootMsgID);


            }
        });
*/
    }

    
    public ContextMenu getMenu(){
        
        ContextMenu ctxMenu = new ContextMenu() ;
        // System.out.println("--------------------------------MeerkatTreeItems.getMenu(): TreeItemType: "+ticTreeItemType);
        switch (ticTreeItemType) {
            case ROOTNODE:
                ctxMenu.getItems().add(loadProjectMenuItem);
                ctxMenu.getItems().add(newProjectMenuItem);
                break ;
            case PROJECTTITLE :
                // this feature not supported in this version
                //ctxMenu.getItems().add(newGraphMenuItem);                
                ctxMenu.getItems().add(loadGraphMenuItem);
                
                //removing the text related context items for version 1.0 of meerkat.
                //ctxMenu.getItems().add(newTextualGraphMenuItem);                
                //ctxMenu.getItems().add(loadTextualGraphMenuItem);
                
                ctxMenu.getItems().add(removeProjectMenuItem);
                break ;
            case GRAPHTITLE :
                // ****************     CONTEXT MENU FOR GRAPH      **************                
                ctxMenu.getItems().add(renameGraphMenuItem);
                ctxMenu.getItems().add(removeGraphMenuItem);
 
                break ;
            case VERTEXTITLE :
                ctxMenu.getItems().add(addVertexMenuItem);
                break ;
            case VERTICES :
                // ****************   CONTEXT MENU FOR NODES  ****************                        
                ctxMenu.getItems().add(removeVertexMenuItem);
                break ;
            case EDGETITLE :
                ctxMenu.getItems().add(addEdgeMenuItem);
                break ;
            case EDGES :
                // ****************   CONTEXT MENU FOR EDGES  ****************                        
                ctxMenu.getItems().add(removeEdgeMenuItem);
                break ;
            case TEXTTITLE :
                // ****************   CONTEXT MENU FOR TEXT    ****************                
                ctxMenu.getItems().add(removeTextualGraphMenuItem);
                break ;
            case TEXTCONTENT :
                // ****************   CONTEXT MENU FOR TEXT CONTENT    ****************
                ctxMenu.getItems().add(addSubThreadMenuItem);        
                ctxMenu.getItems().add(addContentMenuItem);
                ctxMenu.getItems().add(generateGraphMenuItem);        
                ctxMenu.getItems().add(emotionAnalysisMenuItem);
                ctxMenu.getItems().add(polarityGraphMenuItem);
                ctxMenu.getItems().add(wordCloudMenuItem);
                ctxMenu.getItems().add(topicModellingMenuItem);
                break ;
        }
        
        return ctxMenu ;
    }
}
