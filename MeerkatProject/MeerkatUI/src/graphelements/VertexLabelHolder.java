/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphelements;

import config.SceneConfig;
import globalstate.MeerkatUI;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *  Class Name      : VertexLabelHolder
 *  Created Date    : 2016-01-22
 *  Description     : The Label that is to be displayed along with the Vertex
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class VertexLabelHolder extends StackPane {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private int intFontSize  ;
    private double dblScalingFactor  ;
        
    // Text that will display the label of the vertex
    private Text txtVertexLabel  ;
    
    // Canter Properties
    private DoubleProperty dblCentreX ; 
    private DoubleProperty dblCentreY ;
    
    // Width and Height Property
    private DoubleProperty dblXLength ; // Also defines the size of the vertex
    private DoubleProperty dblYLength ;
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public int getFontSize() {
        return this.intFontSize ;
    }
    public void setFontSize(int pintFontSize){
        this.intFontSize = pintFontSize ;
    }
    
    public DoubleProperty getXLengthProperty() {
        return dblXLength;
    }
    
    public DoubleProperty getYLengthProperty() {
        return dblYLength;
    }    
    
    // This property gives the X co ordinate of top left point of the Pane
    public DoubleProperty getXProperty() {
        return layoutXProperty();
    }
    
    // This property gives the Y co ordinate of top left point of the Pane
    public  DoubleProperty getYProperty() {
        return layoutYProperty();
    }
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    /**
     *  Constructor Name: VertexLabelHolder()
     *  Created Date    : 2016-01-22
     *  Description     : Constructor for UIVertexLabel - initializes the properties for Centre and Dimensions
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pdblHolderCentreX : DoubleProperty
     *  @param pdblHolderCentreY : DoubleProperty
     *  @param pdblHolderXLength : DoubleProperty
     *  @param pdblHolderYLength : DoubleProperty
     *  @param pstrLabel ; String
     *  
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */

    public VertexLabelHolder (
               DoubleProperty pdblHolderCentreX
             , DoubleProperty pdblHolderCentreY
             , DoubleProperty pdblHolderXLength
             , DoubleProperty pdblHolderYLength
             , String pstrLabel
    ) {
        
        // Initilalize the label with the passed string
        this.intFontSize = SceneConfig.VERTEX_LABEL_FONTSIZE_DEFAULT ;
        this.dblScalingFactor = SceneConfig.VERTEXLABEL_SCALINGFACTOR ;
        
        txtVertexLabel = new Text(pstrLabel) ;
        txtVertexLabel.setFont(Font.font(txtVertexLabel.getFont().getFamily(), intFontSize)); // Set the font of the table
        txtVertexLabel.setVisible(SceneConfig.VERTEX_LABEL_VISIBLE_DEFAULT);
        
        this.getChildren().add(txtVertexLabel) ; // Add the label to UIVertexLabel 
        new Scene(new Group(this)); // Use a throwaway scene so that the layout bounds (height and width) for the text can be extracted
        txtVertexLabel.applyCss(); // Apply the CSS        
        
        
        // System.out.println("VertexLabelHolder.VertexLabelHolder(): W: "+txtVertexLabel.getLayoutBounds().getWidth()+"\tH: "+txtVertexLabel.getLayoutBounds().getHeight());
        
        this.setWidth(txtVertexLabel.getLayoutBounds().getWidth());
        this.setHeight(txtVertexLabel.getLayoutBounds().getHeight());
        
        // System.out.println("VertexLabelHolder.VertexLabelHolder(): Pane: "+widthProperty().get()+": "+heightProperty().get());
        
        // Initialize all the properties        
        dblXLength = new SimpleDoubleProperty(txtVertexLabel.getLayoutBounds().getWidth());
        dblYLength = new SimpleDoubleProperty(txtVertexLabel.getLayoutBounds().getHeight());
        dblCentreX = new SimpleDoubleProperty(pdblHolderCentreX.get()+(pdblHolderXLength.get())-(txtVertexLabel.getLayoutBounds().getWidth()/2)); 
        dblCentreY = new SimpleDoubleProperty(pdblHolderCentreY.get() + (pdblHolderYLength.get()*2) + SceneConfig.VERTEX_LABEL_PADDING);
        
        // System.out.println("UIVertexLabel.UIVertexLabel(): Lbl: "+dblCentreX.get()+": "+dblCentreY.get()+"\tHolder: "+pdblHolderCentreX.get()+","+pdblHolderCentreY.get());
                
        
        // Binding of the XLength and YLength properties to the length and width of the UIVertexLabel pane
        dblXLength.bind(this.widthProperty()); 
        dblYLength.bind(this.heightProperty());        
        
               
        // Binding the Centre properties
        // XCentre is bound to the X of the Vertex Holder since the Labels will be placed below the vertex, the X would remain the same
        // YCentre is bound according to the following equation : VertexYCentre + (VertexYLength+VertexLabelYLength)/2
        //  dblCentreX.bind(pdblHolderCentreX);
        dblCentreY.bind(pdblHolderCentreY.add(pdblHolderYLength.multiply(2)).add(SceneConfig.VERTEX_LABEL_PADDING));
        
        // System.out.println("UIVertexLabel.UIVertexLabel(): After Binding = "+dblCentreX.get()+":"+dblCentreY.get()+"\t"+pdblHolderCentreX.get()+":"+pdblHolderCentreY.get());
        
        drawVertexLabel(dblCentreX, dblCentreY, dblXLength, dblYLength);
    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : setTextVisibility()
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
    public void setTextVisibility(boolean pblnVisibility){  
        this.txtVertexLabel.setVisible(pblnVisibility);
    }
    
    public boolean getIsLabelVisible() {
        return this.txtVertexLabel.visibleProperty().get();
    }
    
    public void updateLabel(String pstrLabel) {
        this.txtVertexLabel.setText(pstrLabel);
    }
        
    /**
     *  Method Name     : drawVertexLabel()
     *  Created Date    : 2016-01-22
     *  Description     : Draws the Vertex Label
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pdblCentreX : DoubleProperty
     *  @param pdblCentreY : DoubleProperty
     *  @param pdblXLength : DoubleProperty
     *  @param pdblYLength : DoubleProperty
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void drawVertexLabel (
              DoubleProperty pdblCentreX
            , DoubleProperty pdblCentreY
            , DoubleProperty pdblXLength
            , DoubleProperty pdblYLength
    ) {
        this.setWidth(pdblXLength.get());
        this.setHeight(pdblYLength.get());
        this.setStyle("-fx-text-fill: black; ");
        // this.setLayoutX(pdblCentreX.get()-(pdblXLength.get()/2));
        // this.setLayoutY(pdblCentreY.get()-(pdblYLength.get()/2));
                
        this.setLayoutX(pdblCentreX.get());
        this.setLayoutY(pdblCentreY.get());        
    }
    
    
    /**
     *  Method Name     : increaseLabelSize()
     *  Created Date    : 2016-01-22
     *  Description     : Increases the size of the Label by increasing the font size by 1
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void increaseLabelSize(){
        intFontSize++ ;
        txtVertexLabel.setFont(Font.font(txtVertexLabel.getFont().getFamily(), intFontSize)); // Set the font of the table
        txtVertexLabel.setLayoutX(txtVertexLabel.getLayoutX()-1);
        txtVertexLabel.setLayoutY(txtVertexLabel.getLayoutY()+1);
    }
    
    /**
     *  Method Name     : decreaseLabelSize()
     *  Created Date    : 2016-01-22
     *  Description     : Decreases the size of the Label by increasing the font size by 1
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void decreaseLabelSize(){
        intFontSize-- ;
        txtVertexLabel.setFont(Font.font(txtVertexLabel.getFont().getFamily(), intFontSize)); // Set the font of the table
        txtVertexLabel.setLayoutX(txtVertexLabel.getLayoutX()+1);
        txtVertexLabel.setLayoutY(txtVertexLabel.getLayoutY()-1);
    }
    
    /**
     *  Method Name     : changeLabelSize()
     *  Created Date    : 2016-06-01
     *  Description     : Change the size of the Label
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void changeLabelSize(int pintLabelSize){
        System.out.println("VertexLabelHolder.changeLabelSize(): Before = "+intFontSize);
        int intFontSizeDiff = pintLabelSize - intFontSize ;
        intFontSize = pintLabelSize ;        
        System.out.println("VertexLabelHolder.changeLabelSize(): After = "+intFontSize);
        txtVertexLabel.setFont(Font.font(txtVertexLabel.getFont().getFamily(), intFontSize)); // Set the font of the table
        txtVertexLabel.setLayoutX(txtVertexLabel.getLayoutX()-intFontSizeDiff);
        txtVertexLabel.setLayoutY(txtVertexLabel.getLayoutY()+intFontSizeDiff);
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        //UIInstance.getActiveProjectTab().getActiveGraphTab().updateMiniMap();
        UIInstance.getActiveProjectTab().getActiveGraphTab().getMinimapDelegator().updateMinimap();
    }
    
    /**
     *  Method Name     : zoominLabel()
     *  Created Date    : 2016-01-22
     *  Description     : Scales both the X-Axis and Y-Axis by a factor of dblScaling Factor (default 1.5)
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void zoominLabel() {
        // txtVertexLabel.setScaleX(dblScalingFactor);
        // txtVertexLabel.setScaleY(dblScalingFactor);
        
        this.setScaleX(dblScalingFactor);
        this.setScaleY(dblScalingFactor);
    }
    
    /**
     *  Method Name     : zoomoutLabel()
     *  Created Date    : 2016-01-22
     *  Description     : Scales both the X-Axis and Y-Axis back to a factor of 1
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void zoomoutLabel() {
        // txtVertexLabel.setScaleX(1);
        // txtVertexLabel.setScaleY(1);
        
        this.setScaleX(1);
        this.setScaleY(1);
    }
    
    
    /**
     *  Method Name     : debugPrintPaneDimensions()
     *  Created Date    : 2016-01-26
     *  Description     : Prints the width and height of the stack pane that consists of the label
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void debugPrintPaneDimensions() {
        System.out.println("UIVertexLabel.debugPrintPaneDimensions(): Width = "+this.getWidth()+"\tHeight = "+this.getHeight());
    }
    
    /**
     *  Method Name     : debugPrintPaneDimensions()
     *  Created Date    : 2016-01-26
     *  Description     : Prints the width and height of the stack pane that consists of the label
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrAdditionalInfo : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void debugPrintPaneDimensions(String pstrAdditionalInfo) {
        System.out.println(pstrAdditionalInfo+"UIVertexLabel.debugPrintPaneDimensions(): Width = "+this.getWidth()+"\tHeight = "+this.getHeight());
    }
    
    public void translateXY(double pdblnewPositionX, double pdblnewPositionY){
        
        setTranslateX(pdblnewPositionX);
        setTranslateY(pdblnewPositionY + SceneConfig.VERTEX_LABEL_PADDING);
        /*
        // System.out.println("VertexLabelHolder.transateXY(): X: "+pdblX+" Y: "+pdblY) ;
        // System.out.println("VertexLabelHolder.transateXY(): Before LayoutX: "+getLayoutX()+" LayoutY: "+getLayoutY()) ;
        setLayoutX(getLayoutX()-pdblX);
        setLayoutY(getLayoutY()-pdblY);
        // System.out.println("VertexLabelHolder.transateXY(): After LayoutX: "+getLayoutX()+" LayoutY: "+getLayoutY()) ;
        */
}
    
}
