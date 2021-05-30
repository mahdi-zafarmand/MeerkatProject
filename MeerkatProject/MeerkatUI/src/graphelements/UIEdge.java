/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphelements;

import config.AppConfig;
import config.SceneConfig;
import globalstate.GraphCanvas;
import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import ui.edgeshapes.ArrowHead;
import ui.edgeshapes.ArrowLine;
import ui.edgeshapes.EdgeLine;
import ui.edgeshapes.IEdgeShapes;

/**
 *  Class Name      : Edge
 *  Created Date    : 2015-07-xx
 *  Description     : The Data Structure to implement the UI Edge
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class UIEdge {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private int intID ;
    private IEdgeShapes eShape ;
    private ArrowHead arrowHead ;
    private ArrowLine arrowLeft ;
    private ArrowLine arrowRight ;
    private GraphCanvas grphCanvas ;
    private int intSourceVertexID;
    private int intDestinationVertexID;
    private boolean blnIsPredicted ;
    private boolean blnIsDirected ;
    
            
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    public int getID() {
        return this.intID ;
    }
    
    public IEdgeShapes getEdgeShape() {
        return this.eShape;
    }
    
    public ArrowLine getLeftArrow(){
        return this.arrowLeft;
    }
    
    public ArrowLine getRightArrow(){
        return this.arrowRight;
    }
    
    public int getSourceVertexID() {
        return intSourceVertexID ; 
    }
    
    public int getDestinationVertexID() {
        return intDestinationVertexID ;
    }
    
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    /**
     *  Constructor Name: UIEdge()
     *  Created Date    : 2016-07-06
     *  Description     : Constructor for the UIEdge
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintID : int
     *  @param pintSourceVertexID : int
     *  @param pintDestinationVertexID : int
     *  @param pblnIsDirected : boolean
     *  @param pblnIsPredicted : boolean
     *  @param pdblStartX : DoubleProperty
     *  @param pdblStartY : DoubleProperty
     *  @param pdblEndX : DoubleProperty
     *  @param pdblEndY : DoubleProperty
     *  @param pgrphCanvas : GraphCanvas
     *  @param pstrColor    : Color
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public UIEdge (
              int pintID
            , int pintSourceVertexID
            , int pintDestinationVertexID
            , boolean pblnIsDirected
            , boolean pblnIsPredicted
            , DoubleProperty pdblStartX
            , DoubleProperty pdblStartY
            , DoubleProperty pdblEndX
            , DoubleProperty pdblEndY
            , GraphCanvas pgrphCanvas
            , Color pstrColor) {
        
        this.grphCanvas = pgrphCanvas ;
        this.intID = pintID ;        
        this.intSourceVertexID = pintSourceVertexID ;
        this.intDestinationVertexID = pintDestinationVertexID ;
        this.blnIsDirected = pblnIsDirected;
        this.blnIsPredicted = pblnIsPredicted;
        eShape = new EdgeLine(true, pdblStartX, pdblStartY, pdblEndX, pdblEndY, this, pstrColor);
        
        /*
        arrowHead = new Polygon();
        
        // Drawing the arrow        
        double x1 = pdblEndX.doubleValue();
        double y1 = pdblEndY.doubleValue();
        double x2 = pdblStartX.doubleValue();
        double y2 = pdblStartY.doubleValue();
        
        double dblSlopeInDegrees = Math.toDegrees(Math.atan((pdblEndY.doubleValue()-pdblStartY.doubleValue())/(pdblEndX.doubleValue()-pdblStartX.doubleValue())));
        double dblDistance = 5;
                
        //System.out.println(x1 + "\t" + x2 + "\t" + y1 + "\t" + y2);
        //System.out.println("Slope in Degree: " + pintID + ":\t" + dblSlopeInDegrees);
        
        double X2 = x1 + dblDistance * Math.cos(Math.toRadians(dblSlopeInDegrees+30));
        double Y2 = x1 + dblDistance * Math.sin(Math.toRadians(dblSlopeInDegrees+30));
        
        double X3 = x1 + dblDistance * Math.cos(Math.toRadians(dblSlopeInDegrees-30));
        double Y3 = x1 + dblDistance * Math.sin(Math.toRadians(dblSlopeInDegrees-30));
        arrowHead.getPoints().addAll(new Double[]{x1, y1, X2, Y2, X3, Y3});
        //this.grphCanvas.getChildren().add(arrowHead);
        */
        // this.arrowRight = new ArrowLine(true, pdblStartX, pdblStartY, pdblEndX, pdblEndY, this, pstrColor);
        // this.arrowLeft = new ArrowLine(false, pdblStartX, pdblStartY, pdblEndX, pdblEndY, this, pstrColor);
        
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : ()
     *  Created Date    : 2016-07-06
     *  Description     : 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void selectEdge() {
        this.getEdgeShape().selectEdge();
    }
    
    public void deselectEdge() {
        this.getEdgeShape().deselectEdge();
    }
    
    public void addEdgeToSelectedEdgesList() {
        this.grphCanvas.addEdgeToSelectedList(this);
    }
    
    public void clearSelectedEdgeList() {
        this.grphCanvas.clearSelectedEdgeList();
    }
    
    public void removeEdgeFromSelectedEdgesList() {
        this.grphCanvas.removeEdgeFromSelectedList(this);
    }
    
    /**
     * Retrieves the primary color of the Edge in a hexadecimal string format of their RGB values
     * @return hexadecimal notation of RGB values
     * @author Talat
     * @since 2018-01-19
     */
    public String getColor(){
        return this.eShape.getColor().toString();
    }
    
    public boolean isEdgeDirected(){
        return this.blnIsDirected;
    }
    
    public boolean isEdgePredicted(){
        return this.blnIsPredicted;
    }
    
    /**
     * Changes the color of the Edge
     * @param pColor 
     * @author Talat
     * @since 2018-01-19
     */    
    public void setColor(Color pColor){
        this.eShape.setEdgeColor(pColor);
    }
    
    public void changeColorDefault() {
        try {
            this.eShape.changeToDefaultColor();
        } catch (Exception ex) {
            System.out.println("UIEdge.changeToDefaultColor(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    /**
     * Changes the thickness of the edge to that of the shortest path defined in SceneConfig
     * 
     * @author Talat
     * @since 2018-05-17
     */
    public void changeToShortestPathThickness() {
        try {
            this.eShape.changeStrokeWidth(SceneConfig.EDGE_SHORTESTPATH_THICKNESS);
        } catch (Exception ex) {
            System.out.println("UIEdge.changeToShortestPathThickness(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    /**
     * Changes the thickness of the edge to the default stroke width defined in SceneConfig
     * 
     * @suthor Talat
     * @since 2018-05-25
     */
    public void changeToRegularEdgeThickness() {
        try {
            this.eShape.changeStrokeWidth(SceneConfig.EDGE_STROKEWIDTH);
        } catch (Exception ex) {
            System.out.println("UIEdge.changeToRegularEdgeThickness(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    public void edgeClicked() {
        try {
            this.grphCanvas.setIsEdgeClicked(true);
        } catch (Exception ex) {
            System.out.println("UIEdge.vertexClicked(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    public void clearMiningResult() {
        try {
            this.eShape.clearMiningResult();
        } catch (Exception ex) {
            System.out.println("UIEdge.clearMiningResult(): EXCEPTION");
            ex.printStackTrace();
        }
    }
}
