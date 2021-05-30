/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.edgeshapes;

import config.SceneConfig;
import globalstate.MeerkatUI;
import globalstate.UIComponents.DeleteEdgeMode;
import globalstate.UIComponents.GraphCanvasMode;
import graphelements.EdgeContextMenu;
import graphelements.UIEdge;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import ui.elements.ColorToolBox;
import ui.elements.SizeToolBox;

/**
 *  Class Name      : EdgeLine
 *  Created Date    : 2015-09-xx
 *  Description     : Lines as shapes of the edges
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-07-06      Talat           Added functionalities for selecting/deselecting an Edge
 *  2016-01-21      Talat           Refactored from package vertexshapes to ui.edgesshapes
 * 
*/
public class EdgeLine extends Line implements IEdgeShapes {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    private UIEdge uiEdgeParent ;
    
    // private Color clrFill ;
    private double dblStrokeWidth ;
    private StrokeLineCap capLine;
    
    private double dblHueShift ;
    private double dblSaturationFactor ;
    private double dblBrightnessFactor ;
    private double dblOpacityFactor ;
    
    private double dblDashArrayFillLength ;
    private double dblDashArrayEmptyLength ;
    
    private boolean blnIsEdgeDirected ; // true for Directed
    private boolean blnIsEdgePredicted ; // true for predicted
    
    private Color primaryColor;
    private Color clrSecondaryColor ;
    private Color clrTertiaryColor ;
    
    private BooleanProperty blnprpIsEdgeSelected ;
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    /**
     * Retrieves the Parent of Edge Line
     * @return Parent of type UIEdge that contains some properties
     * @author Talat
     * @since 2018-01-19
     */
    public UIEdge getEdgeParent(){
        return this.uiEdgeParent;
    }
    
    private ObjectProperty<Color> clrprpColor = new SimpleObjectProperty<>(this, "color", Color.valueOf(SceneConfig.EDGE_COLOR_PRIMARYCOLOR));
    public final Color getColor() {
        return clrprpColor.get();
    }

    public final void setColor(Color color) {
        this.primaryColor = color;
        this.clrprpColor.set(color);
        setStroke(this.clrprpColor.get().deriveColor(this.dblHueShift, this.dblSaturationFactor, this.dblBrightnessFactor, this.dblOpacityFactor));
    }

    public ObjectProperty<Color> colorProperty() {
        return clrprpColor;
    }
    
    public Color getSecondaryColor() {
        return this.clrSecondaryColor ;
    }
    public void setSecondaryColor(Color pColor){
        this.clrSecondaryColor = pColor ;
    }
    
    public Color getTertiaryColor() {
        return this.clrTertiaryColor ;
    }
    public void setTertiaryColor(Color pColor) {
        this.clrTertiaryColor = pColor ;
    }
    
    public boolean isEdgePredicted(){
        return this.blnIsEdgePredicted;
    }
    
    public boolean isEdgeDirected(){
        return this.blnIsEdgeDirected;
    }
        
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    /**
     *  Constructor Name: EdgeLine()
     *  Created Date    : 2016-09-xx
     *  Description     : Constructor for EdgeLine
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pblnIsDirected : boolean
     *  @param pdblStartX : DoubleProperty
     *  @param pdblStartY : DoubleProperty
     *  @param pdblEndX : DoubleProperty
     *  @param pdblEndY : DoubleProperty
     *  @param puiEdge : UIEdge
     *  @param pstrColor : Color
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public EdgeLine (
              boolean pblnIsDirected
            , DoubleProperty pdblStartX
            , DoubleProperty pdblStartY
            , DoubleProperty pdblEndX
            , DoubleProperty pdblEndY
            , UIEdge puiEdge
            , Color pstrColor) {
        
        this.uiEdgeParent = puiEdge ;
        blnprpIsEdgeSelected = new SimpleBooleanProperty(false);
        
        this.initialize();
        this.blnIsEdgeDirected = pblnIsDirected;
        this.setColor(pstrColor);
        
        startXProperty().bind(pdblStartX);
        startYProperty().bind(pdblStartY);
        endXProperty().bind(pdblEndX);
        endYProperty().bind(pdblEndY);
        
        draw();
        
        nonDraggable();
    }
    
    /* *************************************************************** */
    /* ****************     OVER-RIDDEN  METHODS     ***************** */
    /* *************************************************************** */
        
    @Override
    public final Double getStartXValue() {
        return startXProperty().getValue();
    }

    @Override
    public Double getStartYValue() {
        return startYProperty().getValue();
    }

    @Override
    public Double getEndXValue() {
        return endXProperty().getValue();
    }

    @Override
    public Double getEndYValue() {
        return endYProperty().getValue();
    }
    
    @Override
    public void draw() {
        setStrokeWidth(this.dblStrokeWidth);
        setStroke(this.clrprpColor.get().deriveColor(this.dblHueShift, this.dblSaturationFactor, this.dblBrightnessFactor, this.dblOpacityFactor));
        setStrokeLineCap(this.capLine);
        if (this.uiEdgeParent.isEdgePredicted()){
            getStrokeDashArray().addAll(20d, 10d);
        }
        this.toBack();
    }
    
    @Override
    public void setEdgeColor(Color pclrNewColor) {
        this.clrprpColor.set(pclrNewColor);
        draw();
    }
    
    @Override
    public void changePrimaryColor(Color pclrNewColor) {
        this.primaryColor = pclrNewColor;
        this.clrprpColor.set(pclrNewColor);
        draw();
    }
    
    @Override
    public void changeColor(String pstrRGBColor) {
        this.clrprpColor.set(Color.valueOf(pstrRGBColor));
        draw();
    }
    
    @Override
    public void restoreStrokeWidth(double pdblScaleFactor) {
        // While scaling, the stroke width would increase, therefore it has to be restored to the normal
        setStrokeWidth(this.dblStrokeWidth*pdblScaleFactor);
    }
    
    @Override
    public void changeToDefaultColor() {
        this.clrprpColor.set(this.primaryColor);
        draw();
    }
    
    @Override
    public void changeStrokeWidth(double pdblStrokeWidth) {
        setStrokeWidth(pdblStrokeWidth);
    }
    
    /**
     *  Method Name     : clearMiningResult()
     *  Created Date    : 2016-07-06
     *  Description     : Clears the mining results by setting the color of the edge to the default value
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    @Override
    public void clearMiningResult() {
        try {
            
            blnprpIsEdgeSelected.set(false);
            
            // Change the color of the edges from the given community color to the default edge color
            setColor(Color.valueOf(SceneConfig.EDGE_COLOR_PRIMARYCOLOR));
                        
            // Remove the vertex from the list of selected vertices
            this.uiEdgeParent.removeEdgeFromSelectedEdgesList();            
            
        } catch (Exception ex) {
            System.out.println("EdgeHolder.selectEdge(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    private void initialize() {
        
        this.setColor(Color.valueOf(SceneConfig.EDGE_COLOR_PRIMARYCOLOR));
        this.setSecondaryColor(Color.valueOf(SceneConfig.EDGE_COLOR_SECONDARYCOLOR));
        this.setTertiaryColor(Color.valueOf(SceneConfig.EDGE_COLOR_TERTIARYCOLOR));
        
        this.dblStrokeWidth = SceneConfig.EDGE_STROKEWIDTH;
        this.capLine = StrokeLineCap.ROUND;
    
        this.dblHueShift = SceneConfig.EDGE_HUESHIFT ;
        this.dblSaturationFactor = SceneConfig.EDGE_SATURATIONFACTOR ;
        this.dblBrightnessFactor = SceneConfig.EDGE_BRIGHTNESSFACTOR ;
        this.dblOpacityFactor = SceneConfig.EDGE_OPACITYFACTOR;
    
        this.dblDashArrayFillLength = SceneConfig.EDGE_DASHARRAYFILLLENGTH ;
        this.dblDashArrayEmptyLength = SceneConfig.EDGE_DASHARRAYEMPTYLENGTH ;
                
    }
    
    // make a node movable by dragging it around with the mouse.
    private void nonDraggable() {
        setOnMousePressed((MouseEvent mouseEvent) -> {
            this.uiEdgeParent.edgeClicked();            
            
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                
                // Add the edges to the List of selected edges
//                if (mouseEvent.isControlDown()) {
//                    System.out.println("EdgeLine.nonDraggable(): Ctrl is pressed down");
//                    // Only add the edge
//                    this.selectEdge();
//                } else {
//                    System.out.println("EdgeLine.nonDraggable(): Ctrl is NOT pressed down");
//                    // Clear the list and add the selected edge
//                    clearListAndSelectEdge();
//                }
                clearListAndSelectEdge();
                //show the edge context menu
                System.out.println("EdgeLine.nonDraggable(): Right Click on the Edge");
                EdgeContextMenu ecmInstance = EdgeContextMenu.getInstance(this);
                ecmInstance.Show(this, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            } else {
                // Select the Edge that is clicked
                // if the CTRL button is down, then add this edge
                if (mouseEvent.isControlDown()) {
                    this.selectEdge();
                } else {
                    // If the control is not down, then
                     // If it is already selected, then do not deselect all the other edges
                    if (this.blnprpIsEdgeSelected.get()) {
                        
                    } else {
                        // Clear the list and add the selected edge
                        clearListAndSelectEdge();
                    }
                }
                System.out.println("EdgeLine.nonDraggable(): Left Click on the Edge");
            }
        });
        
        setOnMouseReleased((MouseEvent mouseEvent) -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                this.uiEdgeParent.clearSelectedEdgeList();
            }
        });
        
        setOnMouseEntered((MouseEvent mouseEvent) -> {
            
            getScene().setCursor(Cursor.HAND);
            // setColor(Color.valueOf(SceneConfig.EDGE_MOUSEHOVER_COLOR));
            // draw();
                
            // System.out.println("Mouse Enter");
//            if (!mouseEvent.isPrimaryButtonDown()) {
//            }
        });
            
        setOnMouseExited((MouseEvent mouseEvent) -> {
            getScene().setCursor(Cursor.DEFAULT);
            // setColor(clrprpColor.get());
            // draw();
            // System.out.println("Mouse Leave");
//            if (!mouseEvent.isPrimaryButtonDown()) {            
//            }
        });
    }

    /* *************************************************************** */
    /* ****************     OVER-RIDDEN  METHODS     ***************** */
    /* *************************************************************** */
    /**
     *  Method Name     : selectEdge()
     *  Created Date    : 2016-07-06
     *  Description     : Selects the Edge in the Graph Canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    @Override
    public void selectEdge () {
        try {
            
            blnprpIsEdgeSelected.set(true);
            
            // Change the color of the Edge
            setEdgeColor(Color.valueOf(SceneConfig.EDGE_SELECTED_COLOR));
            
            // Add this vertex to the list of selected vertices
            this.uiEdgeParent.addEdgeToSelectedEdgesList();
            
            // Process this event via graphCanvasMode factory
             GraphCanvasMode gMode = MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getCurrentGraphCanvasMode();
             MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            
            if(!(gMode instanceof DeleteEdgeMode) ||
                    (gMode instanceof DeleteEdgeMode && MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getIsEdgeClicked())){
                UIInstance.getActiveProjectTab().getActiveGraphTab().getCurrentGraphCanvasMode().primaryMouseReleasedOnEdge(this.uiEdgeParent);
            }
            
            //UIInstance.getActiveProjectTab().getActiveGraphTab().getCurrentGraphCanvasMode().primaryMouseReleasedOnEdge(this.uiEdgeParent);
            
            //enable Edge Size Tool
            SizeToolBox.getInstance().enableEdgeSizeToolbox();
            ColorToolBox.getInstance().enableEdgeColorToolbox();
                        
        } catch (Exception ex) {
            System.out.println("EdgeHolder.selectEdge(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    @Override
    public Node getShapeNode() {
        return this;
    }
    
    
    
    /**
     *  Method Name     : clearListAndSelectEdge()
     *  Created Date    : 2016-05-05
     *  Description     : Clears the currently selected list and add the current node to the list
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    @Override
    public void clearListAndSelectEdge() {
        try {
            // Clear the List
            this.uiEdgeParent.clearSelectedEdgeList();
            
            // Add the current vertex to the list of selected vertices
            this.selectEdge();
        } catch (Exception ex) {
            System.out.println("EdgeHolder.clearListAndSelectEdge(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    
    
    /**
     *  Method Name     : deselectEdge()
     *  Created Date    : 2016-05-05
     *  Description     : Deselects the current Edge in the Graph Canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    @Override
    public void deselectEdge() {
        try {
            
            blnprpIsEdgeSelected.set(false);
            
            // Change the color of the edge from the Selected Color to the previous color
            this.changeToPrimaryColor();
                        
            // Remove the vertex from the list of selected vertices
            this.uiEdgeParent.removeEdgeFromSelectedEdgesList();
            
            
        } catch (Exception ex) {
            System.out.println("EdgeHolder.selectEdge(): EXCEPTION");
            ex.printStackTrace();
        }
    }    

    @Override
    public double getLineStrokeWidth() {
        return this.getStrokeWidth();
    }

    private void changeToPrimaryColor() {
        this.clrprpColor.set(this.primaryColor);
        draw();
    }
}
