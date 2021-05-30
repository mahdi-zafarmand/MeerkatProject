/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphelements;

import config.SceneConfig;
import globalstate.GraphCanvas;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;

/**
 *  Class Name      : UIVertex
 *  Created Date    : 2015-11-29
 *  Description     : Vertex that contains a box/rectangle VertexHolder which has different shapes within it
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-02-25      Talat           Changing the Point2D to x and y
 *  2016-01-27      Talat           Made changes to the constructor UIVertex()
 *  2016-01-19      Talat           Changed the name of the Class from Vertex to UIVertex
 * 
*/
public class UIVertex {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private int intID;
    // private Point2D v2DLocation ;
    
    private double dblX ;
    private double dblY ;
    
    private VertexHolder vtxHolder ;
    
    // private Color vColor ;
    
    private DoubleProperty dblXProp ;
    private DoubleProperty dblYProp ;
    
    private DoubleProperty dblXLengthProp ;
    private DoubleProperty dblYLengthProp ;
    
    private GraphCanvas parentCanvas ;
     
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    public Color getColor() {
        return this.vtxHolder.getColor();
    }
    public void setColor(Color pColor) {
        if (this.vtxHolder != null) {
            vtxHolder.setColor(pColor);
        }
    }
    
    
    public VertexHolder getVertexHolder() {
        return this.vtxHolder;
    }
    
    public GraphCanvas getParentCanvas() {
        return this.parentCanvas;
    }

    /**
     * Retrieves the id of the UIVertex
     * @return id of the vertex
     * @author Talat
     * @since 2018-01-18
     */
    public int getID(){
        return this.intID;
    }
    
    public DoubleProperty getXProperty(){
        return this.dblXProp;
    }
    
    public DoubleProperty getYProperty(){
        return this.dblYProp;
    }
    
    public DoubleProperty getXLengthProperty(){
        return this.dblXLengthProp;
    }
    
    public DoubleProperty getYLengthProperty(){
        return this.dblYLengthProp;
    }
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    /**
     *  Constructor Name: Vertex()
     *  Created Date    : 2015-11-29
     *  Description     : The constructor of the vertex that initiates with the 
     *                      default properties sich as size, color and shape of the vertex
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pParentCanvas : GraphCanvas
     *  @param pintID : int
     *  @param pdblX : double
     *  @param pdblY : double
     *  @param pdblXProp : DoubleProperty
     *  @param pdblYProp : DoubleProperty
     *  @param pstrLabel : String 
     *  @param pstrTooltip : String
     *  @param pclrColor : Color
     *  @param pstrIconURL : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-02-25      Talat           Changing the Point2D to x and y
     *  2016-01-27      Talat           Added two more parameters (pstrLabel, pstrTooltip)
     *  2017-03-22      Abhi            Removed binding on pdblXProp, pdblYProp.Now these are set when a vertex is dragged in VertexHolder class
    */
    public UIVertex(GraphCanvas pParentCanvas, 
            int pintID, 
            double pdblX, 
            double pdblY, 
            DoubleProperty pdblXProp, 
            DoubleProperty pdblYProp, 
            String pstrLabel, 
            String pstrTooltip, 
            Color pclrColor,
            String pstrIconURL){
        
        this.parentCanvas = pParentCanvas ;
        this.intID = pintID ;
        this.dblXLengthProp = new SimpleDoubleProperty(SceneConfig.VERTEX_SIZE_DEFAULT * 2);
        this.dblYLengthProp = new SimpleDoubleProperty(SceneConfig.VERTEX_SIZE_DEFAULT * 2);
                        
        this.dblX = pdblX ;
        this.dblY = pdblY ;
        
        this.dblXProp = pdblXProp ;
        this.dblYProp = pdblYProp ;
        
        vtxHolder = new VertexHolder(this, intID, dblXProp, dblYProp, dblXLengthProp, dblYLengthProp, pclrColor, pstrLabel, pstrTooltip, pstrIconURL);
        
        //pdblXProp.bind(this.vtxHolder.getXProperty());
        //pdblYProp.bind(this.vtxHolder.getYProperty());
        // correct co-ordinates will be = this.translateXProperty().add( this.layoutXProperty())
        // but that may differ depending on the shape of the vertex i.e. circle and rectangle.
        // For rectangle the co-ordiates will be of left-top corner
        
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    
    public void updateLabel(String pstrLabel) {
        this.vtxHolder.updateLabel(pstrLabel);
    }
    
    /**
     *  Method Name     : addVertexToSelectedList()
     *  Created Date    : 2016-05-05
     *  Description     : Adds the Vertex to the list of selected Vertices
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void addVertexToSelectedList () {
        try {
            this.parentCanvas.addVertexToSelectedList(this);
            //System.out.println("UIVertex.UIVertex():: no of selected vertices = "+ this.parentCanvas.getSelectedVertices().size());
        } catch (Exception ex) {
            System.out.println("UIVertex.UIVertex(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    /**
     *  Method Name     : removeVertexFromSelectedList()
     *  Created Date    : 2016-05-05
     *  Description     : Removes the Vertex from the list of selected vertices
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void removeVertexFromSelectedList() {
        try {
            this.parentCanvas.removeVertexFromSelectedList(this);
        } catch (Exception ex) {
            System.out.println("VertexHolder.selectVertex(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    public void clearSelectedVertexList() {
        try {
            this.parentCanvas.clearSelectedVertexList();
        } catch (Exception ex) {
            System.out.println("VertexHolder.clearSelectedVertexList(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    public void changeToDefaultColor() {
        try {
            this.vtxHolder.changeToDefaultColor();
        } catch (Exception ex) {
            System.out.println("VertexHolder.changeToDefaultColor(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    public void vertexClicked() {
        try {
            parentCanvas.setIsVertexClicked(true);
            // System.out.println("UIVertex.IsVertexClicked ? "+parentCanvas.getIsVertexClicked());
        } catch (Exception ex) {
            System.out.println("VertexHolder.vertexClicked(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    public double getMouseX() {
        return parentCanvas.getMouseX() ;
    }
    
    public double getMouseY() {
        return parentCanvas.getMouseY() ;
    }

    /**
     * Changes the Color of vertex to the color stored in vertex holder.
     */
    public void changeToVertexColor() {
        this.vtxHolder.changeToVertexColor();
    }
    
    public void updateIcon(String pstrIconURL){
        this.vtxHolder.updateIcon(pstrIconURL);
    }    
    
}

