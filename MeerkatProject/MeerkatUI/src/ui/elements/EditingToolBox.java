/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.elements;

import analysisscreen.AnalysisController;
import config.GraphEditingToolsConfig;
import config.ModeConfig.ModeTypes;
import globalstate.MeerkatUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *  Class Name      : EditingToolBox
 *  Created Date    : 2016-01-04
 *  Description     : Displays the Editing Tool Box for editing the graph
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class EditingToolBox {
    
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private ToggleImageButton tbAddEdge ;
    private ToggleImageButton tbAddVertex ;
    private ToggleImageButton tbDeleteVertex ;
    private ToggleImageButton tbDeleteEdge ;
    private ToggleImageButton tbAddVertexMultipleTimeFrames ;
    private ToggleImageButton tbNormalSelection ;
    private ToggleImageButton tbShortestPath ;
   
    private final ToggleGroup grpEditingTools ;
    
    private final Label lblEditingTools ;
    private final HBox hboxEditingTools ;
    private final VBox vboxEditingTools ;
    
    // STATES OF THE EDITING TOOLS - Set to ture if they are selected
    private boolean blnAddVertex ;
    private boolean blnAddEdge ;
    private boolean blnDeleteVertex ;
    private boolean blnDeleteEdge;
    private boolean blnAddVertexMultipleTimeFrames ;
    private boolean blnNormalSelection ;
    private boolean blnShortestPath ;
    
    
    private static EditingToolBox instance;
    
    private static AnalysisController pAnalysisController ;
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public VBox getEditingToolsWithLabel () {
        return vboxEditingTools;
    }
    
    public HBox getEditingTools() {
        return hboxEditingTools;
    }
    
    
    public boolean IsAddVertex () {
        return this.blnAddVertex ;
    }
    
    public boolean IsEdgeVertex () {
        return this.blnAddEdge ;
    }
    
    public boolean IsDeleteVertex () {
        return this.blnDeleteVertex ;
    }
    
    public boolean IsSelect () {
        return this.blnAddVertexMultipleTimeFrames ;
    }
    
    public boolean IsSelectAll () {
        return this.blnNormalSelection ;
    }
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */

    /**
     *  Constructor Name: EditingToolBox()
     *  Created Date    : 2016-01-04
     *  Description     : Constructor for EditingToolBoox - Initiates all the components and their behaviour within the editing tool box
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */

    private EditingToolBox() {
        
        grpEditingTools = new ToggleGroup();        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        blnAddEdge = false ;
        blnAddVertex = false ;
        blnDeleteVertex = false ;
        blnDeleteEdge = false;
        blnAddVertexMultipleTimeFrames = false ;
        blnNormalSelection = false ;
        
        // Adding the toggle buttons to the toggle group
        tbAddEdge = new ToggleImageButton(GraphEditingToolsConfig.getAddEdgeImageURL());
//        Image imgAddEdge = new Image(GraphEditingToolsConfig.getAddEdgeImageURL());
//        tbAddEdge = new ToggleImageButton("A", new ImageView(imgAddEdge));
//        tbAddEdge = new ToggleImageButton(new ImageView(imgAddEdge));
        tbAddEdge.setTooltip(new Tooltip(GraphEditingToolsConfig.getAddEdgeToolTip()));
        tbAddEdge.setToggleGroup(grpEditingTools);        
        tbAddEdge.setOnAction(a -> {
            //TODO correct all these bln flags in all modes, they are wrong right now
            blnAddEdge = flip(blnAddEdge) ;
            if (blnAddEdge) {
                blnAddVertex = false ;
                blnDeleteVertex = false ;
                blnDeleteEdge = false;
                blnAddVertexMultipleTimeFrames = false ;
                blnNormalSelection = false ;            
            }
            UIInstance.getActiveProjectTab().getActiveGraphTab().setGraphCanvasMode(ModeTypes.EDGEADD);
            System.out.println("EditingToolBox.EditingToolBox(): AddEdgeMode Activated");
        });
        
        tbAddVertex = new ToggleImageButton(GraphEditingToolsConfig.getAddVertexImageURL());
//        Image imgAddVertex = new Image(GraphEditingToolsConfig.getAddVertexImageURL());
//        tbAddVertex = new ToggleImageButton("B", new ImageView(imgAddVertex));
//        tbAddVertex = new ToggleImageButton(new ImageView(imgAddVertex));
        tbAddVertex.setTooltip(new Tooltip(GraphEditingToolsConfig.getAddVertexToolTip()));
        tbAddVertex.setToggleGroup(grpEditingTools);
        tbAddVertex.setOnAction(a -> {
            blnAddVertex = flip(blnAddVertex) ;
            if (blnAddVertex) {
                blnAddEdge = false ;
                blnDeleteVertex = false ;
                blnDeleteEdge = false;
                blnAddVertexMultipleTimeFrames = false ;
                blnNormalSelection = false ;            
            }
            UIInstance.getActiveProjectTab().getActiveGraphTab().setGraphCanvasMode(ModeTypes.VERTEXADD);
            System.out.println("EditingToolBox.EditingToolBox(): Add Vertex Mode Activated");
        });
        
        
        tbAddVertexMultipleTimeFrames = new ToggleImageButton(GraphEditingToolsConfig.getAddVertexMultipleTimeFramesImageURL());
//       Image imgAddVertexMultipleTimeFrames = new Image(GraphEditingToolsConfig.getAddVertexMultipleTimeFramesImageURL());
//        tbSelect = new ToggleImageButton("D", new ImageView(imgSelect));
//        tbAddVertexMultipleTimeFrames = new ToggleImageButton(new ImageView(imgAddVertexMultipleTimeFrames));
        tbAddVertexMultipleTimeFrames.setTooltip(new Tooltip(GraphEditingToolsConfig.getAddVertexMultipleTimeFramesToolTip()));
        tbAddVertexMultipleTimeFrames.setToggleGroup(grpEditingTools);
        tbAddVertexMultipleTimeFrames.setOnAction(a -> {
            blnAddVertexMultipleTimeFrames = flip(blnAddVertexMultipleTimeFrames) ;
            if (blnAddVertex) {
                blnAddEdge = false ;
                blnDeleteVertex = false ;
                blnDeleteEdge = false;
                blnAddVertexMultipleTimeFrames = false ;
                blnNormalSelection = false ;            
            }
            UIInstance.getActiveProjectTab().getActiveGraphTab().setGraphCanvasMode(ModeTypes.VERTEXMULTIFRAMEADD);
            System.out.println("EditingToolBox.EditingToolBox():  AddVertexMultipleTimeFrames Mode ACtivated");
        }); 
        
    
        tbDeleteVertex = new ToggleImageButton(GraphEditingToolsConfig.getDeleteVertexImageURL());
//        Image imgDeleteVertices = new Image(GraphEditingToolsConfig.getDeleteVertexImageURL());
//        tbDelete = new ToggleImageButton("C", new ImageView(imgDelete));
//        tbDeleteVertex = new ToggleImageButton(new ImageView(imgDeleteVertices));
        tbDeleteVertex.setTooltip(new Tooltip(GraphEditingToolsConfig.getDeleteVertexToolTip()));
        tbDeleteVertex.setToggleGroup(grpEditingTools);
        tbDeleteVertex.setOnAction(a -> {
            blnDeleteVertex = flip(blnDeleteVertex) ;
            if (blnAddVertex) {
                blnAddEdge = false ;
                blnDeleteEdge = false;
                blnAddVertexMultipleTimeFrames = false ;
                blnNormalSelection = false ;            
            }
            UIInstance.getActiveProjectTab().getActiveGraphTab().setGraphCanvasMode(ModeTypes.VERTEXDELETE);
            System.out.println("EditingToolBox.EditingToolBox(): DeleteVERTEXMode Pressed");
        });
         
        
        tbDeleteEdge = new ToggleImageButton(GraphEditingToolsConfig.getDeleteEdgeImageURL());
//        Image imgDeleteEdges = new Image(GraphEditingToolsConfig.getDeleteEdgeImageURL());
//        tbDelete = new ToggleImageButton("C", new ImageView(imgDelete));
//        tbDeleteEdge = new ToggleImageButton(new ImageView(imgDeleteEdges));
        tbDeleteEdge.setTooltip(new Tooltip(GraphEditingToolsConfig.getDeleteEdgeToolTip()));
        tbDeleteEdge.setToggleGroup(grpEditingTools);
        tbDeleteEdge.setOnAction(a -> {
            blnDeleteVertex = flip(blnDeleteVertex) ;
            if (blnAddVertex) {
                blnAddEdge = false ;
                blnDeleteVertex = false ;
                blnAddVertexMultipleTimeFrames = false ;
                blnNormalSelection = false ;            
            }
            UIInstance.getActiveProjectTab().getActiveGraphTab().setGraphCanvasMode(ModeTypes.EDGEDELETE);
            System.out.println("EditingToolBox.EditingToolBox(): DeleteEDGEMode ACTIVATED");
        }); 
        
        
        tbNormalSelection = new ToggleImageButton(GraphEditingToolsConfig.getSelectMultiImageURL());
//        Image imgNormalSelection = new Image(GraphEditingToolsConfig.getSelectMultiImageURL());
//        tbSelectMulti = new ToggleImageButton("E", new ImageView(imgMultiSelect));
//        tbNormalSelection = new ToggleImageButton(new ImageView(imgNormalSelection));
        tbNormalSelection.setTooltip(new Tooltip(GraphEditingToolsConfig.getSelectMultiToolTip()));
        tbNormalSelection.setToggleGroup(grpEditingTools);
        tbNormalSelection.setOnAction(a -> {
            blnNormalSelection = flip(blnNormalSelection) ;
            if (blnAddVertex) {
                blnAddEdge = false ;
                blnDeleteVertex = false ;
                blnAddVertexMultipleTimeFrames = false ;
                blnNormalSelection = false ;            
            }
            UIInstance.getActiveProjectTab().getActiveGraphTab().setGraphCanvasMode(ModeTypes.SELECT);
            System.out.println("EditingToolBox.EditingToolBox(): Normal Selection Mode activated");
        });
        
        
        tbShortestPath = new ToggleImageButton(GraphEditingToolsConfig.getShortestPathImageURL());
//        Image imgShortestPath = new Image(GraphEditingToolsConfig.getShortestPathImageURL());
//        tbShortestPath = new ToggleImageButton(new ImageView(imgShortestPath));
        tbShortestPath.setTooltip(new Tooltip(GraphEditingToolsConfig.getShortestPathToolTip()));
        tbShortestPath.setToggleGroup(grpEditingTools);
        tbShortestPath.setOnAction(a -> {
            blnShortestPath = flip(blnShortestPath) ;
            if (blnShortestPath) {
                blnAddVertex = false;
                blnAddEdge = false ;
                blnDeleteVertex = false ;
                blnAddVertexMultipleTimeFrames = false ;
                blnShortestPath = false ;            
            }
            UIInstance.getActiveProjectTab().getActiveGraphTab().setGraphCanvasMode(ModeTypes.SHORTESTPATH);
            System.out.println("EditingToolBox.EditingToolBox(): Shortest Path Mode activated");
        });
        
        hboxEditingTools = new HBox(tbNormalSelection, tbAddVertex, tbAddVertexMultipleTimeFrames,tbDeleteVertex, tbAddEdge, tbDeleteEdge, tbShortestPath);
        hboxEditingTools.setAlignment(Pos.CENTER);
        hboxEditingTools.setSpacing(2);
        hboxEditingTools.setPadding(new Insets(0, 2, 0, 2));
        hboxEditingTools.setStyle(
                          "-fx-padding: 0;"
                        + "-fx-border-style: solid outside;"
                        + "-fx-border-width: 1;"
                        + "-fx-border-insets: 0;"
                        + "-fx-border-radius: 0;"
                        + "-fx-border-style: dashed;"
                        + "-fx-border-color: #AAAAAA;"
        );
        
        // Initializing the label to be displayed at the top of the editing tool box
        lblEditingTools = new Label();
        
        // Adding the label and the toggle group of buttons in a VBox to be displayed on the tool ribbon
        vboxEditingTools = new VBox(lblEditingTools, hboxEditingTools);
        vboxEditingTools.setAlignment(Pos.CENTER);
        vboxEditingTools.setStyle(
                          "-fx-padding: 0;"
                        + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 1;"
                        + "-fx-border-insets: 1;"
                        + "-fx-border-radius: 1;"
                        + "-fx-border-style: dashed;"
                        + "-fx-border-color: black;"
        );
    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : activateEditingToolBox()
     *  Created Date    : 2016-01-07
     *  Description     : Activates / Deactivates all the toggle buttons in the toggle group
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pblnActiveState : boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void activateEditingToolBox(boolean pblnActiveState) {
                
        for (Toggle t : grpEditingTools.getToggles()) {  
            if (t instanceof ToggleImageButton) {  
                ((ToggleImageButton) t).setDisable(!pblnActiveState);  
            }  
        } 
    }
    
    
    /**
     *  Method Name     : getInstance()
     *  Created Date    : 2016-01-07
     *  Description     : Returns the only instance of EditingToolBox Object
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return EditingToolBox
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static EditingToolBox getInstance() {
        if (instance == null) {
            instance = new EditingToolBox();
        }
        return instance;
    }
    
    
    /**
     *  Method Name     : getInstance()
     *  Created Date    : 2016-01-07
     *  Description     : Returns the only instance of EditingToolBox Object
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     *  @return EditingToolBox
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static EditingToolBox getInstance(AnalysisController pController) {
        pAnalysisController = pController;
        if (instance == null) {
            instance = new EditingToolBox();
        }
        return instance;
    }
    
    
    public boolean flip (boolean pblnInput) {
        return !pblnInput ;
    }
    
    /**
     * Activate the specific Mode
     * @param modeType 
     */
    public void activateMode(ModeTypes modeType) {
        switch(modeType) {
            case SELECT:
                grpEditingTools.selectToggle(tbNormalSelection);
                break;
            case VERTEXADD:
                grpEditingTools.selectToggle(tbAddVertex);
                break;
            case VERTEXMULTIFRAMEADD:
                grpEditingTools.selectToggle(tbAddVertexMultipleTimeFrames);
                break;
            case VERTEXDELETE:
                grpEditingTools.selectToggle(tbDeleteVertex);
                break;
            case EDGEADD:
                grpEditingTools.selectToggle(tbAddEdge);
                break;
            case EDGEDELETE:
                grpEditingTools.selectToggle(tbDeleteEdge);
                break;
            case SHORTESTPATH:
                grpEditingTools.selectToggle(tbShortestPath);
                break;
            default:
                grpEditingTools.selectToggle(tbNormalSelection);
                break;
        }
    }
    
    public void activateShortestPath() {
        grpEditingTools.selectToggle(tbShortestPath);
    }
    
    public void activateAddEdge(){
        grpEditingTools.selectToggle(tbAddEdge);
    }
    
    public void activateAddVertex(){
        grpEditingTools.selectToggle(tbAddVertex);
    }
    
    public void activateAddMultiFrameVertex(){
        grpEditingTools.selectToggle(tbAddVertexMultipleTimeFrames);
    }
    
    public void activateDeleteVertex(){
        grpEditingTools.selectToggle(tbDeleteVertex);
    }
    
    public void activateDeleteEdge(){
        grpEditingTools.selectToggle(tbDeleteEdge);
    }
    
    public void activateSelectionMode() {
        grpEditingTools.selectToggle(tbNormalSelection);
    }
}
