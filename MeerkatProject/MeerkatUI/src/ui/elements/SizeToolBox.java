/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.elements;

import analysisscreen.AnalysisController;
import config.GraphEditingToolsConfig;
import globalstate.GraphCanvas;
import globalstate.MeerkatUI;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *  Class Name      : SizeToolBox
 *  Created Date    : 2017-06-15
 *  Description     : Displays the size tool slider for selected vertex and edges
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class SizeToolBox {
    
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private final Slider vertexSlider;
    private final Slider edgeSlider;
    private final ImageView imgVertex;
    private final ImageView imgEdge;
    
    private static SizeToolBox instance;
    
    private static AnalysisController pAnalysisController ;
    private final HBox hboxSizeTools ;
    
    private final String style = 
                          "-fx-padding: 0;"
                        + "-fx-border-style: solid outside;"
                        + "-fx-border-width: 1;"
                        + "-fx-border-insets: 0;"
                        + "-fx-border-radius: 0;"
                        + "-fx-border-style: dashed;"
                        + "-fx-border-color: #AAAAAA;";
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
     public HBox getSizeTools() {
        return hboxSizeTools;
    }   
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */

    /**
     *  Constructor Name: SizeToolBox()
     *  Created Date    : 2017-06-15
     *  Description     : Constructor for SizeToolBox
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */

    private SizeToolBox() {
        
        Image imgVertexImage = new Image(GraphEditingToolsConfig.getVertexImageURL());
        imgVertex = new ImageView(imgVertexImage);
        BoxBlur bb = new BoxBlur();
        imgVertex.setEffect(bb);
        Tooltip vertexTooltip = new Tooltip(GraphEditingToolsConfig.getVertexImageTooltip());
        vertexSlider = new Slider(1, 10, 5.5);
//        vertexSlider.setMin(1);
//        vertexSlider.setMax(10);
        vertexSlider.setBlockIncrement(1);
        vertexSlider.setShowTickMarks(false);
        vertexSlider.setDisable(true);        
        vertexSlider.setTooltip(new Tooltip("Vertex Size Tool"));
        Tooltip.install(imgVertex, vertexTooltip);
        
        Image imgEdgeImage = new Image(GraphEditingToolsConfig.getEdgeImageURL());
        imgEdge = new ImageView(imgEdgeImage);
        imgEdge.setEffect(bb);
        Tooltip edgeTooltip = new Tooltip(GraphEditingToolsConfig.getEdgeImageTooltip());
        edgeSlider = new Slider(1, 10, 5.5);
//        edgeSlider.setMin(1);
//        edgeSlider.setMax(10);
        edgeSlider.setBlockIncrement(1);
        edgeSlider.setShowTickMarks(false);
        edgeSlider.setDisable(true);        
        edgeSlider.setTooltip(new Tooltip("Edge Size Tool"));
        Tooltip.install(imgEdge, edgeTooltip);
                
        hboxSizeTools = new HBox(imgVertex, vertexSlider, imgEdge, edgeSlider);
        hboxSizeTools.setAlignment(Pos.CENTER);
        hboxSizeTools.setSpacing(2);
        hboxSizeTools.setPadding(new Insets(0, 0, 0, 0));
        hboxSizeTools.setStyle(style);

    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
   
    /**
     *  Method Name     : getInstance()
     *  Created Date    : 2017-06-15
     *  Description     : Returns the only instance of SizeToolBox Object
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @return EditingToolBox
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static SizeToolBox getInstance() {
        if (instance == null) {
            instance = new SizeToolBox();
        }
        return instance;
    }
    
    
    /**
     *  Method Name     : getInstance()
     *  Created Date    : 2017-06-15
     *  Description     : Returns the only instance of SizeToolBox Object
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pController : AnalysisController
     *  @return EditingToolBox
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static SizeToolBox getInstance(AnalysisController pController) {
        pAnalysisController = pController;
        if (instance == null) {
            instance = new SizeToolBox();
        }
        return instance;
    }
    
    /**
     *  Method Name     : enableEdgeSizeToolbox()
     *  Created Date    : 2017-06-15
     *  Description     : enables the size tool slider for edges.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void enableEdgeSizeToolbox(){
        edgeSlider.setDisable(false);
//        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
//        GraphCanvas graphCanvas = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas();
//        if(graphCanvas.getSelectedEdges().size()==1){
//            for(UIEdge uiEdge : graphCanvas.getSelectedEdges())
//                edgeSlider.setValue(uiEdge.getEdgeShape().getLineStrokeWidth());
//        }
        enableSliderListeners();
    }
    
    /**
     *  Method Name     : disableEdgeSizeToolbox()
     *  Created Date    : 2017-06-15
     *  Description     : disables the size tool slider for edges.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void disableEdgeSizeToolbox(){
        edgeSlider.setDisable(true);
        //edgeSlider.setValue(SceneConfig.EDGE_STROKEWIDTH);
    }
    
    /**
     *  Method Name     : enableVertexSizeToolbox()
     *  Created Date    : 2017-06-15
     *  Description     : enables the size tool slider for vertices.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void enableVertexSizeToolbox(){
        vertexSlider.setDisable(false);
//        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
//        GraphCanvas graphCanvas = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas();
//        if(graphCanvas.getSelectedVertices().size()==1){
//            for(UIVertex uIVertex : graphCanvas.getSelectedVertices())
//                vertexSlider.setValue(uIVertex.getVertexHolder().getXLengthProperty().get() / SceneConfig.VERTEX_SIZE_DEFAULT);
//        }
        enableSliderListeners();
    }
    
    /**
     *  Method Name     : disableVertexSizeToolbox()
     *  Created Date    : 2017-06-15
     *  Description     : disables the size tool slider for vertex.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void disableVertexSizeToolbox(){
        vertexSlider.setDisable(true);
        //vertexSlider.setValue(SceneConfig.VERTEX_SIZE_DEFAULT);
    }
    
    /**
     *  Method Name     : disableSizeToolbox()
     *  Created Date    : 2017-06-15
     *  Description     : disables the size tool sliders.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void disableSizeToolbox(){
        vertexSlider.setValue(5.5);
        vertexSlider.setDisable(true);
        edgeSlider.setValue(5.5);
        edgeSlider.setDisable(true);
    }
    
     /**
     *  Method Name     : enableSliderListeners()
     *  Created Date    : 2017-06-15
     *  Description     : enables the size tool sliders listeners on graph load.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void enableSliderListeners() {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        GraphCanvas graphCanvas = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas();
            
        vertexSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number numOldValue, Number numNewValue) -> { 
            graphCanvas.getSelectedVertices().forEach((uiVertex) -> {
                uiVertex.getVertexHolder().changeHolderSize(vertexSlider.getValue());
            });
        });
        
        
        edgeSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number numOldValue, Number numNewValue) -> {
            graphCanvas.getSelectedEdges().forEach((uiEdge) -> {
                uiEdge.getEdgeShape().restoreStrokeWidth(edgeSlider.getValue());
            });
        });   
                
    }
    
    /**
     *  Method Name     : disableSizeIconBlur()
     *  Created Date    : 2017-07-05
     *  Description     : disables the image blur for size tools
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void disableSizeIconBlur(){
        imgVertex.setEffect(null);
        imgEdge.setEffect(null);
    }
    
}
