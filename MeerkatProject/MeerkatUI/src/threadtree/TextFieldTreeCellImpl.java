/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadtree;

import config.LangConfig;
//import emotionscreen.EmotionController;
import globalstate.MeerkatUI;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
//import polarityscreen.PolarityController;
import threadtree.TreeItemConfig.TreeItemType;


/**
 *  Class Name      : TextFieldTreeCellImpl
 *  Created Date    : 2015-08-xx
 *  Description     : The Context Menus and the implementations
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class TextFieldTreeCellImpl extends TreeCell<String> {
 
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private TextField textField;
    /* The context menus set over here are never used in code.
           The context menu is fetched from MeerkatTreeItem class using getMenu() method.
    private ContextMenu ctxMenuNode = new ContextMenu();        
    private ContextMenu ctxMenuEdge = new ContextMenu();
    private ContextMenu ctxTextualContentContextMenu = new ContextMenu();
    private ContextMenu ctxTextualContextMenu = new ContextMenu();
    private ContextMenu ctxGraphContextMenu = new ContextMenu();
    */

    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    /**
     *  Constructor Name: TextFieldTreeCellImpl()
     *  Created Date    : 2015-8-xx
     *  Description     : The Constructor of the class
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public TextFieldTreeCellImpl() {
        /* The context menus set over here are never used in code.
           The context menu is fetched from MeerkatTreeItem class using getMenu() method.
        
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        TreeView treeView = UIInstance.getController().getTreeView();        
        ThreadTree threadTreeInstance = ThreadTree.getTreeInstance();
        
        // LOAD A GRAPH
        MenuItem loadGraphMenuItem = new MenuItem(LangConfig.OPTIONS_LOADGRAPH);
        loadGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
            }
        });
        
        // ADD A NEW GRAPH
        MenuItem newGraphMenuItem = new MenuItem(LangConfig.OPTIONS_NEWGRAPH);
        loadGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
            }
        });
        
        // REMOVE A GRAPH
        MenuItem removeGraphMenuItem = new MenuItem(LangConfig.OPTIONS_REMOVEGRAPH);
        loadGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
            }
        });
        
        // LOAD A TEXTUALGRAPH
        MenuItem loadTextualGraphMenuItem = new MenuItem(LangConfig.OPTIONS_LOADTEXTUALGRAPH);
        loadTextualGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
            }
        });
        
        // ADD A NEW TEXTUALGRAPH
        MenuItem newTextualGraphMenuItem = new MenuItem(LangConfig.OPTIONS_NEWTEXTUALGRAPH);
        loadTextualGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
            }
        });
        
        // REMOVE A TEXTUALGRAPH
        MenuItem removeTextualGraphMenuItem = new MenuItem(LangConfig.OPTIONS_REMOVETEXTUALGRAPH);
        loadTextualGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
            }
        });
        
        
        // ADD A VERTEX
        MenuItem addVertexMenuItem = new MenuItem(LangConfig.OPTIONS_ADDNODE);  
        int intVertexID =0 ;
        addVertexMenuItem.setOnAction(new EventHandler() { // Get the ID for the Node
            public void handle(Event t) {
                MeerkatTreeItem newNode = new MeerkatTreeItem(LangConfig.OPTIONS_NEWNODE, TreeItemType.VERTICES, intVertexID);
                getTreeItem().getChildren().add(newNode);
            }
        });
        
        // REMOVE A VERTEX
        MenuItem removeVertexMenuItem = new MenuItem(LangConfig.OPTIONS_REMOVENODE);
        removeVertexMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                System.out.println("TextFieldTreeCellImpl.TextFieldTreeCellImpl(): Node to be Removed");
            }
        });
        
        // ADD AN EDGE
        MenuItem addEdgeMenuItem = new MenuItem(LangConfig.OPTIONS_ADDEDGE);        
        int intEdgeID = 0;
        addEdgeMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) { // Add the ID for the Edge
                MeerkatTreeItem newNode = new MeerkatTreeItem(LangConfig.OPTIONS_NEWEDGE, TreeItemType.EDGES, intEdgeID);
                getTreeItem().getChildren().add(newNode);
            }
        });
        
        // REMOVE AN EDGE
        MenuItem removeEdgeMenuItem = new MenuItem(LangConfig.OPTIONS_REMOVEEDGE);            
        removeEdgeMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                System.out.println("TextFieldTreeCellImpl.TextFieldTreeCellImpl(): Edge to be Removed");
            }
        });
        
        // GENERATE GRAPH
        MenuItem generateGraphMenuItem = new MenuItem(LangConfig.OPTIONS_GENERATEGRAPH);        
        generateGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                System.out.println("TextFieldTreeCellImpl.TextFieldTreeCellImpl(): Generate Graph invoked");
            }
        });
        
        // EMOTION ANALYSIS
        MenuItem emotionAnalysisMenuItem = new MenuItem(LangConfig.OPTIONS_EMOTION);
        emotionAnalysisMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                System.out.println("TextFieldTreeCellImpl.TextFieldTreeCellImpl(): Emotion Analysis Invoked");
                final EmotionController emtnController = new EmotionController();                    
                
                MeerkatTreeItem<String> treeitemSelected = (MeerkatTreeItem)treeView.getSelectionModel().getSelectedItem();
                
                int intMsgID = threadTreeInstance.getID(treeitemSelected);
                int intProjectID = UIInstance.getActiveProjectTab().getProjectID();
                int intRootMsgID = UIInstance.getActiveProjectTab().getActiveTextualTab().getID();
                
                System.out.println("TextFieldTreeCellImpl.TextFieldTreeCellImpl(): Message ID sent is "+intMsgID);
                
                emtnController.Display(intProjectID, intMsgID, intRootMsgID);

            }
        });
        
        // POLARITY ANALYSIS
        MenuItem polarityGraphMenuItem = new MenuItem(LangConfig.OPTIONS_POLARITY);        
        polarityGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
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
        MenuItem wordCloudMenuItem = new MenuItem(LangConfig.OPTIONS_WORDCLOUD);        
        wordCloudMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                
            }
        });
        
        // TOPIC MODELLING
        MenuItem topicModellingMenuItem = new MenuItem(LangConfig.OPTIONS_TOPICMODELLING);        
        topicModellingMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {

            }
        });
        
        // ****************   CONTEXT MENU FOR NODES  ****************        
        ctxMenuNode.getItems().add(addVertexMenuItem);
        ctxMenuNode.getItems().add(removeVertexMenuItem);
        
        
        // ****************   CONTEXT MENU FOR EDGES  ****************        
        ctxMenuEdge.getItems().add(addEdgeMenuItem);     
        ctxMenuEdge.getItems().add(removeEdgeMenuItem);
        
        
        // ****************     CONTEXT MENU FOR GRAPH      **************
        ctxGraphContextMenu.getItems().add(loadGraphMenuItem);
        ctxGraphContextMenu.getItems().add(newGraphMenuItem);
        ctxGraphContextMenu.getItems().add(removeGraphMenuItem);
        ctxGraphContextMenu.getItems().add(loadTextualGraphMenuItem);
        ctxGraphContextMenu.getItems().add(newTextualGraphMenuItem);

        
        // ****************   CONTEXT MENU FOR TEXT    ****************
        ctxGraphContextMenu.getItems().add(loadTextualGraphMenuItem);
        ctxGraphContextMenu.getItems().add(newTextualGraphMenuItem);
        ctxGraphContextMenu.getItems().add(removeTextualGraphMenuItem);
        ctxGraphContextMenu.getItems().add(loadGraphMenuItem);
        ctxGraphContextMenu.getItems().add(newGraphMenuItem);
        
        
        // ****************   CONTEXT MENU FOR TEXT CONTENT    ****************
        ctxTextualContentContextMenu.getItems().add(generateGraphMenuItem);        
        ctxTextualContentContextMenu.getItems().add(emotionAnalysisMenuItem);
        ctxTextualContentContextMenu.getItems().add(polarityGraphMenuItem);
        ctxTextualContentContextMenu.getItems().add(wordCloudMenuItem);
        ctxTextualContentContextMenu.getItems().add(topicModellingMenuItem);
        */
    }
 
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText((String) getItem());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(getTreeItem().getGraphic());
                MeerkatTreeItem mtitem = (MeerkatTreeItem<String>)getTreeItem();
                // System.out.println("===========================================TextFieldTreeCellImpl.TreeItem: "+mtitem.getTreeItemType()+"\t"+mtitem.getValue());
                    setContextMenu(((MeerkatTreeItem) getTreeItem()).getMenu());
                    
                if (!getTreeItem().isLeaf() && getTreeItem().getParent()!= null){                   
                    
                    
                    /*
                    if (getString().equalsIgnoreCase(ThreadTreeLangParameters.getVertexLabel())) {
                        
                        setContextMenu(ctxMenuNode);
                    } else if (getString().equalsIgnoreCase(ThreadTreeLangParameters.getEdgeLabel())) {
                        // setContextMenu(ctxMenuEdge);
                    } else {
                        // setContextMenu(ctxTextualContentContextMenu);
                    }
                    // setContextMenu(ctxMenuNode);
*/
                }
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(textField.getText());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}