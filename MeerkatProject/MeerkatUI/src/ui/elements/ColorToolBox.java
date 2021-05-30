/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.elements;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import config.GraphEditingToolsConfig;
import globalstate.GraphCanvas;
import globalstate.MeerkatUI;
import graphelements.UIEdge;
import graphelements.UIVertex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 *  Class Name      : ColorToolBox
 *  Created Date    : 2017-12-15
 *  Description     : Displays the color tool picker for selected vertex and edges
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class ColorToolBox {
    
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private final ColorPicker vertexColorPicker;
    private final ColorPicker edgeColorPicker;
    private final ImageView imgVertex;
    private final ImageView imgEdge;
    
    private static ColorToolBox instance;
    
    private static AnalysisController pAnalysisController ;
    private final HBox hboxColorTools ;
    
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
    
     public HBox getColorTools() {
        return hboxColorTools;
    }   
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */

    /**
     *  Constructor Name: ColorToolBox()
     *  Created Date    : 2017-12-15
     *  Description     : Constructor for ColorToolBox
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */

    private ColorToolBox() {
        
        Image imgVertexImage = new Image(GraphEditingToolsConfig.getVertexColorImageURL());
        imgVertex = new ImageView(imgVertexImage);
        BoxBlur bb = new BoxBlur();
        imgVertex.setEffect(bb);
        Tooltip vertexTooltip = new Tooltip(GraphEditingToolsConfig.getVertexColorImageTooltip());
        vertexColorPicker = new ColorPicker();
        vertexColorPicker.setDisable(true);        
        vertexColorPicker.setTooltip(new Tooltip("Vertex Color Tool"));
        Tooltip.install(imgVertex, vertexTooltip);
        
        Image imgEdgeImage = new Image(GraphEditingToolsConfig.getEdgeColorImageURL());
        imgEdge = new ImageView(imgEdgeImage);
        imgEdge.setEffect(bb);
        Tooltip edgeTooltip = new Tooltip(GraphEditingToolsConfig.getEdgeColorImageTooltip());
        edgeColorPicker = new ColorPicker();
        edgeColorPicker.setDisable(true);        
        edgeColorPicker.setTooltip(new Tooltip("Edge Color Tool"));
        Tooltip.install(imgEdge, edgeTooltip);
                
        hboxColorTools = new HBox(imgVertex, vertexColorPicker, imgEdge, edgeColorPicker);
        hboxColorTools.setAlignment(Pos.CENTER);
        hboxColorTools.setSpacing(2);
        hboxColorTools.setPadding(new Insets(0, 0, 0, 0));
        hboxColorTools.setStyle(style);

    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
   
    /**
     *  Method Name     : getInstance()
     *  Created Date    : 2017-12-15
     *  Description     : Returns the only instance of ColorToolBox Object
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
    public static ColorToolBox getInstance() {
        if (instance == null) {
            instance = new ColorToolBox();
        }
        return instance;
    }
    
    
    /**
     *  Method Name     : getInstance()
     *  Created Date    : 2017-12-15
     *  Description     : Returns the only instance of ColorToolBox Object
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
    public static ColorToolBox getInstance(AnalysisController pController) {
        pAnalysisController = pController;
        if (instance == null) {
            instance = new ColorToolBox();
        }
        return instance;
    }
    
    /**
     *  Method Name     : enableEdgeColorToolbox()
     *  Created Date    : 2017-12-15
     *  Description     : enables the color tool picker for edges.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void enableEdgeColorToolbox(){
        edgeColorPicker.setDisable(false);
        enableColorPickerListeners();
    }
    
    /**
     *  Method Name     : disableEdgeColorToolbox()
     *  Created Date    : 2017-12-15
     *  Description     : disables the color tool picker for edges.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void disableEdgeColorToolbox(){
        edgeColorPicker.setDisable(true);
        edgeColorPicker.setValue(Color.BLACK);
        //edgeSlider.setValue(SceneConfig.EDGE_STROKEWIDTH);
    }
    
    /**
     *  Method Name     : enableVertexColorToolbox()
     *  Created Date    : 2017-12-15
     *  Description     : enables the color tool picker for vertices.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void enableVertexColorToolbox(){
        vertexColorPicker.setDisable(false);
        enableColorPickerListeners();
    }
    
    /**
     *  Method Name     : disableVertexColorToolbox()
     *  Created Date    : 2017-12-15
     *  Description     : disables the color tool picker for vertex.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void disableVertexColorToolbox(){
        vertexColorPicker.setDisable(true);
        vertexColorPicker.setValue(Color.BLACK);
    }
    
    /**
     *  Method Name     : disableColorToolbox()
     *  Created Date    : 2017-12-15
     *  Description     : disables the color tool picker.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void disableColorToolbox(){
        disableVertexColorToolbox();
        disableEdgeColorToolbox();
    }
    
     /**
     *  Method Name     : enableColorPickerListeners()
     *  Created Date    : 2017-12-15
     *  Description     : enables the color tool pickers listeners on graph load.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2018-01-19      Talat           Updating the colors of the UIVertex and Edges that have been added to the list
     * 
    */
    public void enableColorPickerListeners() {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int intProjectId = UIInstance.getActiveProjectTab().getProjectID();
        int intGraphId = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID();
        int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
        GraphCanvas graphCanvas = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas();  
        
        vertexColorPicker.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                List<Integer> lstVertexIDs = new ArrayList<>();
                Color selectedColor = vertexColorPicker.getValue();
                for(UIVertex v : graphCanvas.getSelectedVertices()){
                    v.setColor(selectedColor);
                    lstVertexIDs.add(v.getID());
                }                
                GraphAPI.updateVertexColor(intProjectId, intGraphId, intTimeFrameIndex, selectedColor.toString(), lstVertexIDs);
                UIInstance.getActiveProjectTab().setProjectModifiedStatus(Boolean.TRUE);
            }
        });
        
        edgeColorPicker.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {                
                List<Integer> lstEdgeIDs = new ArrayList<>();
                Color selectedColor = edgeColorPicker.getValue();
                for(UIEdge e : graphCanvas.getSelectedEdges()){
                    e.getEdgeShape().changePrimaryColor(edgeColorPicker.getValue());                    
                    lstEdgeIDs.add(e.getID());
                }                
                GraphAPI.updateEdgeColor(intProjectId, intGraphId, intTimeFrameIndex, selectedColor.toString(), lstEdgeIDs);
                UIInstance.getActiveProjectTab().setProjectModifiedStatus(Boolean.TRUE);
            }
        });
                
    }
    
    /**
     *  Method Name     : disableColorIconBlur()
     *  Created Date    : 2017-12-15
     *  Description     : disables the image blur for color picker tools
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void disableColorIconBlur(){
        imgVertex.setEffect(null);
        imgEdge.setEffect(null);
    }
    
}
