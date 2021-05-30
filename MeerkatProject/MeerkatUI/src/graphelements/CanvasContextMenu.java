/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphelements;

import ca.aicml.meerkat.api.analysis.LinkPredictionAPI;
import config.StatusMsgsConfig;
import config.CanvasContextConfig;
import config.EdgeContextConfig;
import globalstate.MeerkatUI;
import java.io.File;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import ui.dialogwindow.AddVertexDialog;
import ui.dialogwindow.ColorChooser;
import ui.dialogwindow.EdgeDeleteConfirmationDialog;
import ui.dialogwindow.EdgeInformationDialog;
import ui.dialogwindow.LinkPrediction;
import ui.dialogwindow.VertexDeleteConfirmationDialog;
import ui.dialogwindow.VertexInformationDialog;

/**
 *  Class Name      : CanvasContextMenu
 *  Created Date    : 2016-06-23
 *  Description     : The class for Context Menu
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class CanvasContextMenu {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static ContextMenu cmNode;
    private static CanvasContextMenu ncmInstance = null; // To use singleton pattern

    private MenuItem menuPinVertices ;
    private MenuItem menuUnpinVertices ;
    private MenuItem menuShowHideMinimap ;
    private MenuItem menuChangeBGColor ;
    private MenuItem menuChangeBGImage ;
    private MenuItem menuClearBG ;
    private MenuItem menuExtractSubGraph;
    private MenuItem menuAddVertex;
    private MenuItem menuAddVertexMultipleTimeFrames;
    private MenuItem menuAddEdge;
    private MenuItem menuRemoveSelectedNodes;
    private MenuItem menuRemoveSelectedEdges;
    private MenuItem menuVertexInformation;
    private MenuItem menuEdgeInformation;
    //delete ir later
    private MenuItem menuDoLinkPrediction;
    
    private Set<UIVertex> setSelectedVertices;
    private Set<UIEdge> setSelectedEdges;
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public static CanvasContextMenu getInstance() {
        if (ncmInstance == null) {
            ncmInstance = new CanvasContextMenu();
        } 
        return ncmInstance;
    }

    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    private CanvasContextMenu() {
        
        cmNode = new ContextMenu();
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        
        
        /* ADDING THE CONTEXT MENU ITEM */
        menuPinVertices = new MenuItem(CanvasContextConfig.getPinVerticesText());
        menuPinVertices.setOnAction(event -> {
            event.consume();
            UIInstance.getController().updateStatusBar(true, StatusMsgsConfig.VERTEXPINNING_ENABLING);
            UIInstance.getActiveProjectTab().getActiveGraphTab().pinVertexToCanvas();
            UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.VERTEXPINNING_ENABLED);
        });
        
        menuUnpinVertices = new MenuItem(CanvasContextConfig.getUnpinVerticesText());
        menuUnpinVertices.setOnAction(event -> {
            event.consume();
            UIInstance.getController().updateStatusBar(true, StatusMsgsConfig.VERTEXPINNING_DISABLING);
            UIInstance.getActiveProjectTab().getActiveGraphTab().unpinVertexToCanvas();
            UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.VERTEXPINNING_DISABLED);
        });
        
        menuShowHideMinimap = new MenuItem(CanvasContextConfig.getShowHideMinimapText());
        menuShowHideMinimap.setOnAction(event -> {
            event.consume();
            if (UIInstance.getActiveProjectTab().getActiveGraphTab().IsMinimapHidden()) {
                UIInstance.getActiveProjectTab().getActiveGraphTab().showMinimap();
                UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.CANVAS_SHOWMINIMAP);
            } else {
                UIInstance.getActiveProjectTab().getActiveGraphTab().hideMinimap();
                UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.CANVAS_HIDEMINIMAP);
            }
        });
        
        menuChangeBGColor = new MenuItem(CanvasContextConfig.getChangeBGColorText());
        menuChangeBGColor.setOnAction(event -> {
            event.consume();
            ColorChooser.Display(UIInstance.getController());
        });
        
        menuChangeBGImage = new MenuItem(CanvasContextConfig.getChangeBGImageText());
        menuChangeBGImage.setOnAction(event -> {
            event.consume();
            UIInstance.getController().updateStatusBar(true, StatusMsgsConfig.WAITING_USERINPUT);
            
            /* Open the Dialog Box to allow the user to select the file */
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg",
                                        "*.JPEG", "*.jpeg");
            fileChooser.getExtensionFilters().add(extFilter);
            extFilter = new FileChooser.ExtensionFilter("PNG files (.png)", "*png");
            fileChooser.getExtensionFilters().add(extFilter);
            extFilter = new FileChooser.ExtensionFilter("Bitmap Images (.bmp)", "*bmp");
            fileChooser.getExtensionFilters().add(extFilter);
            extFilter = new FileChooser.ExtensionFilter("TIFF Images (.tiff)", "*tiff");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showOpenDialog(null);
            
            // If the file is null, then the Cancel button is pressed. Handle what is to be done when cancelled button is pressed
            if (file == null) {
                UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            } else {
                UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.CANVAS_BGIMAGE_CHANGING);                            
                UIInstance.getActiveProjectTab().getActiveGraphTab().changeBackgroundImage(file.getAbsolutePath());
                UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.CANVAS_BGIMAGE_CHANGED);
            }
        });
        
        menuClearBG = new MenuItem(CanvasContextConfig.getClearBGImageText());
        menuClearBG.setOnAction(event -> {
            event.consume();
            UIInstance.getActiveProjectTab().getActiveGraphTab().changeBackgroundImage(null);
        });
        
        menuExtractSubGraph = new MenuItem(CanvasContextConfig.getExtratcSubGraphText());
        
        menuExtractSubGraph.setOnAction(event -> {
           
                event.consume();
                int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
                int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
                setSelectedVertices = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getSelectedVertices();
                setSelectedEdges = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getSelectedEdges();
                System.out.println("CanvasContextMenu.canvasContextMenu() : activeGraphTabTitle Parent :: " + UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphTabTitle() + 
                        ", its graphid = " + intGraphID);
                String[] timeFrames = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrames();
                int currentTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
                if(setSelectedVertices.size() > 0 || setSelectedEdges.size() > 0){
                    ExtractSubGraph.extractASubgraph(intProjectID, intGraphID, currentTimeFrameIndex,timeFrames, setSelectedVertices, setSelectedEdges);
                }
        });
        
        menuAddVertex = new MenuItem("Add Vertex in this TimeFrame");
        menuAddVertex.setOnAction(event -> {
        
            event.consume();
            AddVertexDialog.Display(UIInstance.getController());
        
        });
        
        
        
        menuAddVertexMultipleTimeFrames = new MenuItem("Add Vertex in Multiple TimeFrames");
        menuAddVertexMultipleTimeFrames.setOnAction(event -> {
        
            event.consume();
            AddVertexDialog.DisplayMultipleTimeFrameOption(UIInstance.getController());
        
        });
        
        menuAddEdge = new MenuItem("Add Edge between two vertices");
        menuAddEdge.setOnAction(event -> {
        
            event.consume();
            //AddEdgeDialog.Display(UIInstance.getController());
        
        });
        
        menuRemoveSelectedNodes = new MenuItem(CanvasContextConfig.getNodeRemoveText());
        menuRemoveSelectedNodes.setOnAction(event -> {
            event.consume();
            VertexDeleteConfirmationDialog.Display(UIInstance.getController());
        });
        
        menuRemoveSelectedEdges = new MenuItem(CanvasContextConfig.getEdgeRemoveText());
        menuRemoveSelectedEdges.setOnAction(event -> {
            event.consume();
            EdgeDeleteConfirmationDialog.Display(UIInstance.getController());
        });
        
        menuVertexInformation = new MenuItem(CanvasContextConfig.getVertexInformationText());
        menuVertexInformation.setOnAction(event -> {
            event.consume();
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
            setSelectedVertices = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getSelectedVertices();
            int currentTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            VertexInformationDialog.Display(UIInstance.getController(), setSelectedVertices, intProjectID, intGraphID, currentTimeFrameIndex);
        });
        
        menuEdgeInformation = new MenuItem(EdgeContextConfig.getEdgeInfoText());
        menuEdgeInformation.setOnAction(event -> {
            event.consume();
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
            setSelectedEdges = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getSelectedEdges();
            int currentTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            EdgeInformationDialog.Display(UIInstance.getController(), setSelectedEdges, intProjectID, intGraphID, currentTimeFrameIndex);
        });
        
        menuDoLinkPrediction = new MenuItem(CanvasContextConfig.getLinkPredictionText());
        menuDoLinkPrediction.setOnAction(event ->{
        
            event.consume();
            LinkPrediction.Display(UIInstance.getController(), null); 
            
            /*
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
            
            int currentTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            
            int noOfTopKEdges = 10;
            String [] arrstrParameters = new String [1] ;
            arrstrParameters[0] = LinkPredictionAPI.getLocalNaiveBayesIndex_TopN()+":"+String.valueOf(noOfTopKEdges) ;
            String pstrCMAlgName = "localNaiveBayesPredictor";
            LinkPredictionAPI.runLPAlgorithm(intProjectID, intGraphID, currentTimeFrameIndex, pstrCMAlgName, arrstrParameters);
            */
        });

        cmNode.getItems().addAll(
                  menuPinVertices
                , menuVertexInformation
                , menuEdgeInformation
                , menuUnpinVertices
                , menuShowHideMinimap
                , menuChangeBGColor
                , menuChangeBGImage
                , menuClearBG
                , menuExtractSubGraph
                , menuAddVertex
                , menuAddVertexMultipleTimeFrames
                //, menuAddEdge
                , menuRemoveSelectedNodes
                , menuRemoveSelectedEdges
                ,menuDoLinkPrediction
            );        
    }
    

    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : Show()
     *  Created Date    : 2016-06-25
     *  Description     : Shows the context menu for the Canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pNode : Node
     *  @param pdblX : double
     *  @param pdblY : double
     * 
     *  EDIT HISTORY (most recent at the top)20
     *  Date            Author          Description
     *  
     * 
    */
    public void Show (Node pNode, double pdblX, double pdblY) {
        this.cmNode.show(pNode, pdblX, pdblY);
    }
    
    /**
     *  Method Name     : Hide()
     *  Created Date    : 2016-06-25
     *  Description     : Shows the context menu for the Canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)20
     *  Date            Author          Description
     *  
     * 
    */
    public void Hide () {
        this.cmNode.hide();
    }
}
