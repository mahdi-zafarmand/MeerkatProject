/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphelements;

import config.SceneConfig;
import config.StatusMsgsConfig;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import globalstate.UIComponents.DeleteVertexMode;
import globalstate.UIComponents.GraphCanvasMode;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import ui.elements.ColorToolBox;
import ui.elements.IconToolBox;
import ui.elements.SizeToolBox;

/**
 *  Class Name      : VertexHolder
 *  Created Date    : 2015-11-28
 *  Description     : An invisible holder that holds the shape of the Vertex
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-080-08     Talat           Made changes to unpinVertexToCanvas() and pinVertexToCanvas()
 *  2016-05-04      Talat           Adding the functionality of selecting the vertices
 *  2016-02-01      Talat           Made changes to changeColor()
 *  2016-01-27      Talat           Added the tooltipVertex and LabelHolder
 *  2016-07-27      Talat           Made changes to the constructor VertexHolder()
 *  2016-01-27      Talat           Adding two fields, strLabel & strToolTip to store respective fields
 *  2016-01-27      Talat           Making changes to the dragProperties() 
 *  2016-01-22      Talat           Added a label Vertex lblVertex which will by just below the VertexShape
 * 
*/
public class VertexHolder extends StackPane {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private UIVertex uiVertexParent ;
    private int intVertexID ;
    // private String strLabel ;
    private String strToolTip ;
    private IVertexShape vShape ; // Instance where the shape is stored
    private Color clrVertex ; // The color of the shape is stored
    private Circle crcPinned ;
    private ImageView imageView;
    
    // Canter Properties
    private DoubleProperty dblCentreX ; 
    private DoubleProperty dblCentreY ;
    
    // Width and Height Property
    private DoubleProperty dblXLength ; // Also defines the size of the vertex
    private DoubleProperty dblYLength ;
    
    private BooleanProperty blnprpIsVertexSelected ;
    private boolean blnIsMouseReleased ;
    
    private double dblXYLengthRatio ;
    
    private VertexLabelHolder vtxLabelHolder ;
    
    private Tooltip tooltipVertex ;
    private boolean blnVertexTooltip ;
    private boolean blnIsPinned = false; // Says if the Vertex is actually pinned to the canvas
    
    // private double dblSceneX ;
    // private double dblSceneY ;
    
    Delta deltaDragHolder = new Delta();
    
    //private Node node;
    private double dblTranslateAnchorX;
    private double dblTranslateAnchorY;
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public int getID () {
        return this.intVertexID;
    }
    public DoubleProperty getXLengthProperty() {
        return dblXLength;
    }
    
    public DoubleProperty getYLengthProperty() {
        return dblYLength;
    }    
    
    public DoubleProperty getXCentreProperty() {
        return dblCentreX;
    }
    
    public DoubleProperty getYCentreProperty() {
        return dblCentreY;
    }    
    
    public DoubleProperty getXProperty() {
        return layoutXProperty();
    }
    
    public  DoubleProperty getYProperty() {
        return layoutYProperty();
    }
    
    public Node getNode() {
        return this;
    }
    
    public IVertexShape getVertexShape() {
        return vShape ;
    }
    
    public Color getColor() {
        return this.clrVertex;
    }
    
    public void setColor(Color pColor) {
        this.clrVertex = pColor ;
        this.vShape.changeColor(pColor);
        if (this.imageView != null) {
            this.imageView.toFront();
        }
    }
    
    public VertexLabelHolder getLabelHolder () {
        return vtxLabelHolder ;
    }
    
    public boolean IsVertexPinned() {
        return blnIsPinned ;
    }
    public void setIsVertexPinned(boolean pblnValue) {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
        
        if (pblnValue) {
            UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.VERTEXPINNING_ENABLED);
        } else {
            UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.VERTEXPINNING_DISABLED);
        }
        this.blnIsPinned = pblnValue ;
    }
        
    public boolean IsVertexTooltipEnabled() {
        return this.blnVertexTooltip ;
    }
    
    public void setIsVertexTooltipEnabled(boolean pblnValue) {
        this.blnVertexTooltip = pblnValue;
    }
    
    public BooleanProperty getVertexSelectBooleanProperty(){
        return blnprpIsVertexSelected;
    }
        
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    /**
     *  Constructor Name: VertexHolder()
     *  Created Date    : 2015-11-28
     *  Description     : Constructor
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pUIVertex : UIVertex
     *  @param pintVertexID : int
     *  @param pdblCentreX : DoubleProperty
     *  @param pdblCentreY : DoubleProperty
     *  @param pdblXLength : DoubleProperty
     *  @param pdblYLength : DoubleProperty
     *  @param pclrColor : Color
     *  @param pstrLabel : String
     *  @param pstrTooltip : String 
     *  @param pstrIconURL : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-01-27      Talat           Added two parameters (pstrLabel, pstrTooltip)
     * 
    */
    public VertexHolder (
               UIVertex pUIVertex
             , int pintVertexID
             , DoubleProperty pdblCentreX
             , DoubleProperty pdblCentreY
             , DoubleProperty pdblXLength
             , DoubleProperty pdblYLength
             , Color pclrColor
             , String pstrLabel
             , String pstrTooltip
             , String pstrIconURL
              ) {
        
        try {
            
            blnprpIsVertexSelected = new SimpleBooleanProperty(false);
                                
            this.uiVertexParent = pUIVertex ;
            this.intVertexID = pintVertexID ;
            this.dblCentreX = pdblCentreX ;
            this.dblCentreY = pdblCentreY ;
            this.dblXLength = pdblXLength ;
            this.dblYLength = pdblYLength ;               
            this.dblXYLengthRatio = this.dblXLength.get() / this.dblYLength.get();
            
            //this.strLabel = pstrLabel ;
            this.strToolTip = pstrTooltip ;
            //commenting below line as we do not want to create tooltip on graph load
            //this.tooltipVertex = new Tooltip(strToolTip);

            this.clrVertex = pclrColor ;

            // Functionalities for Vertex Label
            vtxLabelHolder = new VertexLabelHolder(dblCentreX, dblCentreY, dblXLength, dblYLength, pstrLabel) ;

            // Draw the holder - ideally this holder should be invisible so that only the vertex is visible
            this.drawHolder(pclrColor, pdblCentreX, pdblCentreY, pdblXLength, pdblYLength);

            // Add the dragproperties to the Vertex Holder -
            // Actions to be taken on mouse hovering, dragging, clicking and releasing the mouse
            this.dragProperties();

            // For the first time, set the visibility of the Label to be the values in AppConfig
            // setLabelVisibility(AppConfig.IsLabelEnabled);

            // Add listeners to the Length parameters
            dblXLength.addListener((ObservableValue<? extends Number> observable, 
                    Number dblOldValue, 
                    Number dblNewValue) -> {
                setWidth(dblNewValue.doubleValue());            
            });
            dblYLength.addListener((ObservableValue<? extends Number> observable, 
                    Number dblOldValue, 
                    Number dblNewValue) -> {
                setHeight(dblNewValue.doubleValue());
            });

            // Creating the default shape
            switch (SceneConfig.VERTEX_SHAPE_DEFAULT) {
                case CIRCLE :
                    vShape = new VertexCircle(dblCentreX, dblCentreY, dblXLength, dblYLength, pclrColor);
                    break;
                case RECTANGLE :
                    vShape = new VertexRectangle(dblCentreX, dblCentreY, dblXLength, dblYLength, pclrColor);
                    break ;
                case ELLIPSE :
                    vShape = new VertexEllipse(dblCentreX, dblCentreY, dblXLength, dblYLength, pclrColor);
                    break ;
                case SQUARE :
                    vShape = new VertexSquare(dblCentreX, dblCentreY, dblXLength, dblYLength, pclrColor);
                    break ;
                default :
                    vShape = new VertexCircle(dblCentreX, dblCentreY, dblXLength, dblYLength, pclrColor);
                    break ;
            }           
            
            this.crcPinned = new Circle(SceneConfig.VERTEX_PINNING_SIZE);
            this.crcPinned.setFill(Color.BLACK);
            this.crcPinned.setOpacity(0);
                        
            this.getChildren().add(vShape.getNode());            
            this.getChildren().add(crcPinned);
            
            imageView = new ImageView();
            if (pstrIconURL != null){
                try{
                    imageView.setImage(new Image(pstrIconURL));
                    imageView.toFront();
                }catch(Exception eg){
                    System.out.println("VertexHolder() - Invalid URL for ICON file");
                }                
            }
            this.getChildren().add(imageView);
            
            this.setAlignment(Pos.CENTER);            
            
        } catch (Exception ex) {
            System.out.println("VertexHolder.VertexHolder(): EXCEPTION");
            ex.printStackTrace();
        }
                
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */    
    
    /**
     * 
     * @param pstrIconURL 
     */
    public void updateIcon(String pstrIconURL){
        if (pstrIconURL != null){
            try{
            imageView.setImage(new Image(pstrIconURL));
            imageView.toFront();
            }catch(Exception et){
                System.out.println("VertexHolder:UpdateIcon() - Invalid URL for ICON file");
            }
        }else{
            imageView.setImage(null);
        } 
    }
    /**
     *  Method Name     : updateLabel()
     *  Created Date    : 2016-05-27
     *  Description     : Updates the Label
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrLabel : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateLabel(String pstrLabel) {
        this.vtxLabelHolder.updateLabel(pstrLabel);
    }
    
    /**
     *  Method Name     : updateTooltip()
     *  Created Date    : 2016-05-27
     *  Description     : Updates the Tooltip
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrTooltip : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateTooltip(String pstrTooltip) {
        this.strToolTip = pstrTooltip;
        tooltipVertex.setText(strToolTip);
    }
    
    /**
     *  Method Name     : setLabelVisibility()
     *  Created Date    : 2016-01-28
     *  Description     : Changes the visibility of the Label of this Vertex
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pblnVisibility : boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void setLabelVisibility(boolean pblnVisibility){
        this.vtxLabelHolder.setTextVisibility(pblnVisibility);
    }
    
    
    /**
     *  Method Name     : setTooltipVisibility()
     *  Created Date    : 2016-05-27
     *  Description     : Changes the visibility of the Tooltip of this Vertex
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pblnVisibility : boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void setTooltipVisibility(boolean pblnVisibility){
        // System.out.println("VertexHolder(): Tool tip selection");
        this.setIsVertexTooltipEnabled(pblnVisibility);
    }
    
    
    /**
     *  Method Name     : increaseVertexLabelSize()
     *  Created Date    : 2016-05-25
     *  Description     : Increases the size of the label
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void increaseVertexLabelSize(){
        this.vtxLabelHolder.increaseLabelSize();
    }
    
    /**
     *  Method Name     : decreaseVertexLabelSize()
     *  Created Date    : 2016-05-26
     *  Description     : Decreases the size of the label
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void decreaseVertexLabelSize(){
        this.vtxLabelHolder.decreaseLabelSize();
    }
    
    
    /**
     *  Method Name     : pinVertexToCanvas()
     *  Created Date    : 2016-05-26
     *  Description     : Pins the Vertex to Canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-08-08      Talat           Adding the pinning circle to the StackPane when pinned
     * 
    */
    public void pinVertexToCanvas(){        
        try {
            this.blnIsPinned = true ;
            if (this.getChildren().contains(this.crcPinned)) {
                this.getChildren().remove(this.crcPinned);
            }
            this.getChildren().add(this.crcPinned);
        } catch (Exception ex) {
            System.out.println("VertexHolder.pinVertexToCanvas(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    /**
     *  Method Name     : pinVertexToCanvas()
     *  Created Date    : 2016-05-26
     *  Description     : Pins the Vertex to Canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-08-08      Talat           Removing the pinning circle from the StackPane when unpinned
     * 
    */
    public void unpinVertexToCanvas(){
        try {
            this.blnIsPinned = false ;
            this.getChildren().remove(this.crcPinned);
        } catch (Exception ex) {
            System.out.println("VertexHolder.unpinVertexToCanvas(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
        
    /* METHODS TO CHANGE THE SHAPE */
    
    /**
     *  Method Name     : addEllipseShape()
     *  Created Date    : 2015-11-28
     *  Description     : Changes the shape of the Vertex to Ellipse
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void addEllipseShape() {
        System.out.println("VertexHolder.addEllipseShape(): Number of children in StackPane = "+this.getChildren().size());
        this.getChildren().remove(vShape.getNode());
        vShape = new VertexEllipse(dblCentreX, dblCentreY, dblXLength, dblYLength, clrVertex);
        this.getChildren().add(vShape.getNode());
    }
    
    
    /**
     *  Method Name     : addCircleShape()
     *  Created Date    : 2015-11-28
     *  Description     : Changes the Shape of the Vertex to a Circle
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void addCircleShape() {
        System.out.println("VertexHolder.addCircleShape(): Number of children in StackPane = "+this.getChildren().size());
        
        this.getChildren().remove(vShape.getNode());
        vShape = new VertexCircle(dblCentreX, dblCentreY, dblXLength, dblYLength, clrVertex);
        this.getChildren().add(vShape.getNode());
    }
    
    /**
     *  Method Name     : addRectangleShape()
     *  Created Date    : 2015-11-28
     *  Description     : Changes the shape of the Vertex to a Rectangle
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void addRectangleShape() {
        System.out.println("VertexHolder.addRectangleShape(): Number of children in StackPane = "+this.getChildren().size());
        this.getChildren().remove(vShape.getNode());
        vShape = new VertexRectangle(dblCentreX, dblCentreY, dblXLength, dblYLength, clrVertex);
        this.getChildren().add(vShape.getNode());
    }
    
    /**
     *  Method Name     : addRectangleShape()
     *  Created Date    : 2015-11-28
     *  Description     : Changes the shape of the Vertex to a Rectangle
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void addSquareShape() {
        this.getChildren().remove(vShape.getNode());
        vShape = new VertexSquare(dblCentreX, dblCentreY, dblXLength, dblYLength, clrVertex);
        this.getChildren().add(vShape.getNode());
    }
    
    
    /* METHOD TO CHANGE THE SIZE OF THE VERTEX */
    
    /**
     *  Method Name     : changeHolderSize()
     *  Created Date    : 2015-11-28
     *  Description     : Changes the holder Size of the Vertex Holder
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pdblSize : double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void changeHolderSize(double pdblSize) {
        // System.out.println("changeHolderSize(): Current Length: "+this.dblXLength.get()+"\tSize increased to: "+pdblSize);        
        dblXLength.set(SceneConfig.VERTEX_SIZE_DEFAULT * pdblSize);
        dblYLength.set(SceneConfig.VERTEX_SIZE_DEFAULT * this.dblXYLengthRatio * pdblSize);
    }
    
    /**
     *  Method Name     : updatePosition()
     *  Created Date    : 2016-03-13
     *  Description     : Updates the X and Y position of the vertex
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pdblX : double
     *  @param pdblY : double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updatePosition (double pdblX, double pdblY) {
        //vertex selection by left click or making a rectangle is based on (dblCentreX, dblCentreY). Update them when move a vertex
        dblCentreX.set(pdblX);
        dblCentreY.set(pdblY);
        this.setTranslateX(0);
        this.setTranslateY(0);
        setLayoutX(pdblX); 
        setLayoutY(pdblY);
        
        this.vtxLabelHolder.setTranslateX(0);
        this.vtxLabelHolder.setTranslateY(0);
        this.vtxLabelHolder.setLayoutX(pdblX + + this.dblXLength.getValue());
        // xLabelHolder = xVertex + dblXLength of Vertex, yLabelHolder = yVertex + dblYLength of Vertex
        this.vtxLabelHolder.setLayoutY(pdblY + this.dblYLength.getValue());
        
        // System.out.println("\t\t\tUpdated: ("+vtxLabelHolder.getLayoutX()+","+vtxLabelHolder.getLayoutY()+")");
    }
    
    /* METHOD TO CHANGE THE VCOLOR OF THE VERTEX */
    
    /**
     *  Method Name     : changeColor()
     *  Created Date    : 2015-11-28
     *  Description     : Changes the color of the Vertex Holder
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pNewColor : Color
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-02-01      Talat           Removed the background color of the Holder as transparent
     * 
    */
    public void changeColor(Color pNewColor) {
        this.vShape.changeColor(pNewColor);
        if (blnIsPinned) {
            if (this.getChildren().contains(this.crcPinned)) {
                this.getChildren().remove(this.crcPinned);
            }
            this.getChildren().add(this.crcPinned);
        }
    }
    
    /**
     *  Method Name     : changeToDefaultColor()
     *  Created Date    : 2016-05-05
     *  Description     : Changes the color of the vertex to default color
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void changeToDefaultColor() {
        // System.out.println("VertexHolder.changeColorDefault(): "+clrVertex);
        // this.vShape.changeColor(clrVertex);
        this.vShape.changeColor(Color.valueOf(SceneConfig.VERTEX_COLOR_DEFAULT));
    }
    
    
    /* DRAWING THE HOLDER */
    /**
     *  Method Name     : drawHolder()
     *  Created Date    : 2015-11-28
     *  Description     : To draw the holder of the vertex
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pPaintColor : Paint
     *  @param pdblCentreXProp : DoubleProperty
     *  @param pdblCentreYProp : DoubleProperty
     *  @param pdblXLengthProp : DoubleProperty
     *  @param pdblYLengthProp : DoubleProperty
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void drawHolder(Paint pPaintColor, DoubleProperty pdblCentreXProp, DoubleProperty pdblCentreYProp
            , DoubleProperty pdblXLengthProp, DoubleProperty pdblYLengthProp) {
        
        this.setWidth(pdblXLengthProp.get());
        this.setHeight(pdblYLengthProp.get());
        
        this.setStyle("-fx-text-fill: black; "); // this.setStyle("-fx-background-color: #ffffff; "+ "-fx-text-fill: black; "); // Previously
        
        setLayoutX(pdblCentreXProp.get());
        setLayoutY(pdblCentreYProp.get());
    }
    
    /**
     *  Method Name     : clearListAndSelectVertex()
     *  Created Date    : 2016-05-05
     *  Description     : Clears the currently selected list and add the current node to the list
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param 
     *  @param 
     *  @return 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void clearListAndSelectVertex() {
        try {
            // Clear the List
            this.uiVertexParent.clearSelectedVertexList();
            
            // Add the current vertex to the list of selected vertices
            this.selectVertex();
        } catch (Exception ex) {
            System.out.println("VertexHolder.clearListAndSelectVertex(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    private void clearListSelectedVertices() {
        try {
            // Clear the List
            this.uiVertexParent.clearSelectedVertexList();
            this.uiVertexParent.getParentCanvas().clearSelectedEdgeList();
            
        } catch (Exception ex) {
            System.out.println("VertexHolder.clearListAndSelectVertex(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    /**
     *  Method Name     : selectVertex()
     *  Created Date    : 2016-05-05
     *  Description     : Selects the Vertex in the Graph Canvas, updates Minimap and process this event using GraphCanvas Mode
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void selectVertex () {
        try {
            
            blnprpIsVertexSelected.set(true);
            
            //System.out.println("CVhanging the color to "+SceneConfig.VERTEX_COLOR_SELECTED);
            // Change the color of the vertex
            changeColor(Color.valueOf(SceneConfig.VERTEX_COLOR_SELECTED));
            //this.vShape.throb(SceneConfig.VERTEX_TIMEPERTHROB_MILLIS, SceneConfig.VERTEX_THROB_COUNT);
            this.vShape.throbAndBlink(SceneConfig.VERTEX_TIMEPERTHROB_MILLIS, SceneConfig.VERTEX_THROB_COUNT, SceneConfig.VERTEX_THROB_SCALE, SceneConfig.VERTEX_TIMESCALE_MILLIS);
            
            // this.vShape.throbback(SceneConfig.VERTEX_TIMEPERTHROB_MILLIS);
            
            // Add this vertex to the list of selected vertices
            this.uiVertexParent.addVertexToSelectedList();
            this.dblTranslateAnchorX = this.getTranslateX();
            this.dblTranslateAnchorY = this.getTranslateY();
            
            // The statistics pane should also be reflected
            MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
            UIInstance.getActiveProjectTab().getActiveGraphTab().getAccordionTabValues().getStatisticsValues().focusARow(this.getID());
            
            // Process this event via graphCanvasMode factory
             GraphCanvasMode gMode = MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getCurrentGraphCanvasMode();
            
            if(!(gMode instanceof DeleteVertexMode) ||
                    (gMode instanceof DeleteVertexMode && MeerkatUI.getUIInstance().
                            getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getIsVertexClicked())){
                
                GraphTab currentGraph = UIInstance.getActiveProjectTab().getActiveGraphTab();
                currentGraph.getCurrentGraphCanvasMode().primaryMouseReleasedOnVertex(this, currentGraph.getModeInformationUI());
            }
            //enable Vertex Size Tool
            SizeToolBox.getInstance().enableVertexSizeToolbox();
            ColorToolBox.getInstance().enableVertexColorToolbox();
            IconToolBox.getInstance().enableIconToolbox();
            
//            UIInstance.getActiveProjectTab().getActiveGraphTab().getMinimapDelegator().updateMinimap();
//            UIInstance.getActiveProjectTab().getActiveGraphTab().getMinimapDelegator().changeMinimapMasking();
            if (this.imageView != null) {
                this.imageView.toFront();
            }

            
        } catch (Exception ex) {
            System.out.println("VertexHolder.selectVertex(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    /**
     *  Method Name     : deselectVertex()
     *  Created Date    : 2016-05-05
     *  Description     : Deselects the current Vertex in the Graph Canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void deselectVertex() {
        try {
            
            blnprpIsVertexSelected.set(false);
            
            // Change the color of the vertex from the Selected Color to the previous color
            changeColor(clrVertex);
            if (this.imageView != null) {
                this.imageView.toFront();
            }
                        
            // Remove the vertex from the list of selected vertices
            this.uiVertexParent.removeVertexFromSelectedList();
            
            
        } catch (Exception ex) {
            System.out.println("VertexHolder.selectVertex(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    /**
     *  Method Name     : clearMiningResult()
     *  Created Date    : 2016-05-17
     *  Description     : Clears the mining results by setting the color of the node to the default value
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void clearMiningResult() {
        try {
            
            blnprpIsVertexSelected.set(false);
            clrVertex = Color.valueOf(SceneConfig.VERTEX_COLOR_DEFAULT);
            
            // Change the color of the vertex from the Selected Color to the previous color
            changeColor(clrVertex);
                        
            // Remove the vertex from the list of selected vertices
            this.uiVertexParent.removeVertexFromSelectedList();
            
            
        } catch (Exception ex) {
            System.out.println("VertexHolder.selectVertex(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    public void displayVertexContextMenu(double pdblScreenX, double pdblScreenY){
        VertexContextMenu ncmInstance = VertexContextMenu.getInstance(this);
        ncmInstance.Show(this, pdblScreenX, pdblScreenY);
    }
     
    
    /**
     *  Method Name     : dragProperties()
     *  Created Date    : 2015-11-28
     *  Description     : Mouse listeners and events for dragging a vertex
     *  Version         : 1.0
     *  @author         : Talat
     *
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-01-27      Talat           Changed the way that the drag is experienced using the scenes data. Look for old versions before this data for old functionality
     *  2016-01-22      Talat           Added the code to invoke zoom in and out when mouse enters or exits a vertex
     * 
    */
    public void dragProperties() { 
               
        
        // final Delta deltaDragLabel = new Delta();
      
        // ON PRESSING ONE OF THE MOUSE BUTTONS
        setOnMousePressed((MouseEvent mouseEvent) -> {
            
            // Call the method on the Graph Tab to send the signal that the vertex has been clicked
            this.uiVertexParent.vertexClicked() ;
            
            // RIGHT CLICK OF THE MOUSE
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                
                // Add the nodes to the List of selected vertices
//                if (mouseEvent.isControlDown()) {
//                    System.out.println("VertexHolder.DragProperties(): Ctrl is pressed down");
//                    // Only add the vertex
//                    this.selectVertex();
//                } else {
//                    System.out.println("VertexHolder.DragProperties(): Ctrl is NOT pressed down");
//                    // Clear the list and add the selected vertex
//                    clearListAndSelectVertex();
//                }
                clearListAndSelectVertex();
                // Display the Context Menu on right click
                VertexContextMenu ncmInstance = VertexContextMenu.getInstance(this);
                ncmInstance.Show(this, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                System.out.println("VertexHolder.DragProperties: Click on Vertex");
                
            } else if (mouseEvent.getButton() == MouseButton.PRIMARY) { // LEFT CLICK OF THE MOUSE
                
                // Records the drag position relative to the scene's X & Y co ordinates                
                deltaDragHolder.dblX = mouseEvent.getSceneX() - getLayoutX();
                deltaDragHolder.dblY = mouseEvent.getSceneY() - getLayoutY();
                
                System.out.println("VHOlder: "+deltaDragHolder.dblX+"\t"+deltaDragHolder.dblY);
                
                // this.dblSceneX = mouseEvent.getSceneX() ;
                // this.dblSceneY = mouseEvent.getSceneY() ;
                
                deltaDragHolder.dblSceneX = mouseEvent.getSceneX() ;
                deltaDragHolder.dblSceneY = mouseEvent.getSceneY() ;
                
                //////////////////////////////////////////////////////////////
                //node = (Node) mouseEvent.getSource();

                deltaDragHolder.translateAnchorX = getTranslateX();
                deltaDragHolder.translateAnchorY = getTranslateY();
                ////////////////////////////////////////////////////////////////
                
                System.out.println("VHolder.Click(): delta = "+deltaDragHolder.dblX+","+deltaDragHolder.dblY
                        +"\tScene: "+mouseEvent.getSceneX()+","+mouseEvent.getSceneY()
                        +"\tLayout "+getLayoutX()+","+getLayoutY());
                
                // deltaDragLabel.dblX = this.vtxLabelHolder.getLayoutX()-mouseEvent.getSceneX();
                // deltaDragLabel.dblY = this.vtxLabelHolder.getLayoutY()-mouseEvent.getSceneY();
                
                getScene().setCursor(Cursor.MOVE);
                
                // Add the nodes to the List of selected vertices
                if (mouseEvent.isControlDown()) {                    
                    // Only add the vertex
                    this.selectVertex();
                    
                } else {
                    
                    // If it is already selected, then do not deselect all the other nodes
                    if (this.blnprpIsVertexSelected.get()) {
                        
                    } else {
                        // Clear the list and add the selected vertex
                        clearListAndSelectVertex();
                    }
                }
            }
            
        });
      
        // ON MOUSE BUTTON RELEASE
        setOnMouseReleased((MouseEvent mouseEvent) -> {
            getScene().setCursor(Cursor.DEFAULT);
            vtxLabelHolder.zoomoutLabel();
            // if left click released : unselect the selected vertices. 
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                this.clearListSelectedVertices();
            }
            
            if (this.imageView != null) {
                this.imageView.toFront();
            }
            
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            UIInstance.getActiveProjectTab().getActiveGraphTab().getMinimapDelegator().updateMinimap();
        });
        
        // ON MOUSE BUTTON DRAGGING
        setOnMouseDragged((MouseEvent mouseEvent) -> {            
            /*
            double dblNewX = mouseEvent.getSceneX() + dragDelta.dblX;                
            setLayoutX(dblNewX);
            double dblNewY = mouseEvent.getSceneY() + dragDelta.dblY;            
            setLayoutY(dblNewY);
            */
            
            // To move only if the primary button has been pressed and dragged
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                
                /*
                System.out.println("VHolder.Drag(): delta = "+deltaDragHolder.dblX+","+deltaDragHolder.dblY
                        +"\tScene: "+mouseEvent.getSceneX()+","+mouseEvent.getSceneY()
                        +"\tLayout "+getLayoutX()+","+getLayoutY());
                */
                
                double dblDiffX = mouseEvent.getSceneX() - deltaDragHolder.dblSceneX ;
                double dblDiffY = mouseEvent.getSceneY() - deltaDragHolder.dblSceneY ;
                
                // deltaDragHolder.dblX = mouseEvent.getSceneX() - getLayoutX();
                // deltaDragHolder.dblY = mouseEvent.getSceneY() - getLayoutY();
                // setLayoutX(mouseEvent.getSceneX() - deltaDragHolder.dblX);
                // setLayoutY(mouseEvent.getSceneY() - deltaDragHolder.dblY);
                
                // translate selected vertices and their vertex label holders
                this.uiVertexParent.getParentCanvas().translateSelectedVertices(dblDiffX, dblDiffY);
                
                // FOR LABEL HOLDER                
                deltaDragHolder.dblDiffX = deltaDragHolder.dblSceneX - mouseEvent.getSceneX() ;
                deltaDragHolder.dblDiffY = deltaDragHolder.dblSceneY - mouseEvent.getSceneY() ;
                            
                //this.vtxLabelHolder.translateXY(deltaDragHolder.dblDiffX, deltaDragHolder.dblDiffY);

//                setLayoutX(getCanvasX());
//                setLayoutY(getCanvasY());
                //Update Project status
                MeerkatUI UIInstance = MeerkatUI.getUIInstance();
                ProjectStatusTracker.updateProjectModifiedStatus(UIInstance.getActiveProjectID(), ProjectStatusTracker.eventVertexMoved);
            }
        });        
        
        // ON HOVERING THE MOUSE ON THE MOUSE HOLDER
        setOnMouseEntered((MouseEvent mouseEvent) -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.HAND); 
                vtxLabelHolder.zoominLabel();
                MeerkatUI UIInstance = MeerkatUI.getUIInstance();
                if (this.IsVertexTooltipEnabled()) {
                    tooltipVertex.install(this, tooltipVertex);
                } else {
                    tooltipVertex.uninstall(this, tooltipVertex);
                }
            }
        });
        
        // ON MOVING OUT OF THE VERTEX HOLDER AREA
        setOnMouseExited((MouseEvent mouseEvent) -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.DEFAULT);                
                vtxLabelHolder.zoomoutLabel();
            }
        });      
        
    }
    /*
    ** Dummy method just used for testing
    */
    private void translateXY2(double pdblXShiftOriginal, double pdblYShiftOriginal) { 
        if (!blnIsPinned) {
            
            
                //double newLocationX = getLayoutX() + pdblXShiftScaled;
                //double newLocationY = getLayoutY() + pdblYShiftScaled;
            
                //System.out.println("Shift "+pdblXShiftScaled+"\t"+pdblYShiftScaled);
                //System.out.println("Scene "+deltaDragHolder.dblSceneX+"\t"+deltaDragHolder.dblSceneY);
                //System.out.println("XY "+deltaDragHolder.dblX+"\t"+deltaDragHolder.dblY);

                

                //System.out.println("VHolder: Before Layout: "+getLayoutX()+","+getLayoutY());

                 //setLayoutX(deltaDragHolder.dblSceneX + pdblXShiftOriginal - deltaDragHolder.dblX);
                 //setLayoutY(deltaDragHolder.dblSceneY + pdblYShiftOriginal - deltaDragHolder.dblY);
                //double scale = this.uiVertexParent.getParentCanvas().getScale();
                double scale = this.uiVertexParent.getParentCanvas().getParent().getScaleX();
                double newPositionX = deltaDragHolder.translateAnchorX + pdblXShiftOriginal/scale;
                double newPositionY = deltaDragHolder.translateAnchorY + pdblYShiftOriginal/scale;        
                setTranslateX(newPositionX);
                setTranslateY(newPositionY);
                // Update vertex location properties when vertex was dragged
                Bounds vertexBounds = this.getBoundsInParent();
                this.dblCentreX.set(vertexBounds.getMinX() + vertexBounds.getWidth()/2);
                this.dblCentreY.set(vertexBounds.getMinY() + vertexBounds.getHeight()/2);
                
                //this.dblCentreX.set(deltaDragHolder.translateAnchorX + pdblXShiftOriginal/scale);
                //System.out.println("VertexHolder.translateXY2 : layout :: " + this.getXProperty().getValue() +","+ this.getYProperty().getValue());

                //System.out.println("VHolder: After Layout: "+getLayoutX()+","+getLayoutY());
                //System.out.println();

                // The new scene co ordinates should change
                //deltaDragHolder.dblSceneX += pdblXShiftOriginal;
                //deltaDragHolder.dblSceneY += pdblYShiftOriginal;
            
        }
    }
    
    /**
     *  Method Name     : getCanvasX()
     *  Created Date    : 2016-06-15
     *  Description     : Returns the X coordinate of the canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private double getCanvasX() {
        return uiVertexParent.getMouseX() ;
    }
    
    /**
     *  Method Name     : getCanvasY()
     *  Created Date    : 2016-06-15
     *  Description     : Returns the Y coordinate of the canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private double getCanvasY() {
        return uiVertexParent.getMouseY() ;
    }
    
    /**
     *  Method Name     : translateXY()
     *  Created Date    : 2016-06-15
     *  Description     : Moves the Vertices(that are selected but not pinned to the canvas) based on the movement of the mouse on the canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pdblXShiftOriginal : double
     *  @param pdblYShiftOriginal : double
     *  @param pdblScale          : double
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2017-03-22      Abhi            Added scale param. Changed logic of translation. Update the dblCenterX,dblCenterX properties 
     * 
    */
    public void translateXY(double pdblXShiftOriginal, double pdblYShiftOriginal, double pdblScale) { 
        if (!blnIsPinned) {
            
                //double scale = this.uiVertexParent.getParentCanvas().getParent().getScaleX();
                //double newPositionX = deltaDragHolder.translateAnchorX + pdblXShiftOriginal/pdblScale;
                //double newPositionY = deltaDragHolder.translateAnchorY + pdblYShiftOriginal/pdblScale;        
                double newPositionX = dblTranslateAnchorX + pdblXShiftOriginal/pdblScale;
                double newPositionY = dblTranslateAnchorY + pdblYShiftOriginal/pdblScale;        
               
                
                setTranslateX(newPositionX);
                setTranslateY(newPositionY);
                // Update vertex location properties when vertex was dragged
                //vertex selection by left click or making a rectangle is based on (dblCentreX, dblCentreY). Update them when move a vertex
                Bounds vertexBounds = this.getBoundsInParent();
                this.dblCentreX.set(vertexBounds.getMinX() + vertexBounds.getWidth()/2);
                this.dblCentreY.set(vertexBounds.getMinY() + vertexBounds.getHeight()/2);
                
                // translate its VertexLableHolder
                this.vtxLabelHolder.translateXY(newPositionX, newPositionY);
            
            /* old logic not working correctly
            // #DEBUG
            // System.out.println("Vertex: "+this.uiVertexParent);
            double newLocationX = getLayoutX() + pdblXShiftScaled;
            double newLocationY = getLayoutY() + pdblYShiftScaled;
            //if(newLocationX > 0 && newLocationX < SceneConfig.GRAPHCANVAS_WIDTH -5 && newLocationY > 0 && newLocationY < SceneConfig.GRAPHCANVAS_HEIGHT-5){
                System.out.println("Shift "+pdblXShiftScaled+"\t"+pdblYShiftScaled);
                System.out.println("Scene "+deltaDragHolder.dblSceneX+"\t"+deltaDragHolder.dblSceneY);
                System.out.println("XY "+deltaDragHolder.dblX+"\t"+deltaDragHolder.dblY);

                // #ENDDEBUG

                System.out.println("VHolder: Before Layout: "+getLayoutX()+","+getLayoutY());

                 //setLayoutX(deltaDragHolder.dblSceneX + pdblXShiftOriginal - deltaDragHolder.dblX);
                 //setLayoutY(deltaDragHolder.dblSceneY + pdblYShiftOriginal - deltaDragHolder.dblY);

                node.setTranslateX(deltaDragHolder.translateAnchorX + pdblXShiftScaled);
                node.setTranslateY(deltaDragHolder.translateAnchorY + pdblYShiftScaled);
                //relocate(getLayoutX() + pdblXShift, getLayoutY() + pdblYShift);

                System.out.println("VHolder: After Layout: "+getLayoutX()+","+getLayoutY());
                System.out.println();

                // The new scene co ordinates should change
                //deltaDragHolder.dblSceneX += pdblXShiftOriginal;
                //deltaDragHolder.dblSceneY += pdblYShiftOriginal;
            //}
            */
        }
    }
    
    /**
        *  Class Name      : createTooltip
        *  Created Date    : 2017-08-30
        *  Description     : A class creates the Tooltip object
        *  Version         : 1.0
        *  @author         : sankalp
        * 
        * 
    */
    
    public void createTooltip() {
        this.tooltipVertex = new Tooltip(strToolTip);
    }

    /**
     * Changes the color of the vShape to the color stored in clrVertex
     * @since 2018-01-26
     * @author Sankalp / Talat
     */
    public void changeToVertexColor() {
        this.vShape.changeColor(clrVertex);
    }
    
    /**
        *  Class Name      : Delta
        *  Created Date    : 2015-11-28
        *  Description     : A class which has changes in the X & Y Axis
        *  Version         : 1.0
        *  @author         : Talat
        * 
        *  EDIT HISTORY (most recent at the top)
        *  Date            Author          Description
        *  
        * 
    */
    private class Delta { 
        double dblX ;
        double dblY; 
        
        double dblDiffX ;
        double dblDiffY ;
        
        double dblSceneX ;
        double dblSceneY ;
        
        double translateAnchorX;
        double translateAnchorY;
    } 
}
