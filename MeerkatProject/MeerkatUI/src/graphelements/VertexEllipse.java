/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphelements;

import config.SceneConfig;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

/**
 *  Class Name      : VertexEllipse
 *  Created Date    : 2015-11-xx
 *  Description     : The Ellipse shape of a Vertex
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class VertexEllipse extends Ellipse implements IVertexShape {
    
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    /**
     *  Constructor Name: VertexEllipse()
     *  Created Date    : 2015-11-xx
     *  Description     : Constructor for VertexEllipse which initiates all the default parameters of the Ellipse
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pdblCentreX : DoubleProperty
     *  @param pdblCentreY : DoubleProperty
     *  @param pdblXLength : DoubleProperty
     *  @param pdblYLength : DoubleProperty
     *  @param  pColor : Color
     *  
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public VertexEllipse(DoubleProperty pdblCentreX, DoubleProperty pdblCentreY, 
            DoubleProperty pdblXLength, DoubleProperty pdblYLength, Color pColor) {
        // The minor axis length is assumed to the 2/3rd in length than that of the length of major axis
        super(pdblCentreX.get(), pdblCentreY.get(), pdblXLength.get()/2, pdblYLength.get()/2*(2.0/3.0));
        
        drawVertex(pColor);
        // System.out.println("VertexEllipse.VertexEllipse(): Radius = "+radiusXProperty().get()+"\t"+radiusYProperty().get());
//        pdblXLength.bind(this.radiusXProperty());
//        pdblYLength.bind(this.radiusYProperty());
        this.radiusXProperty().bind(pdblXLength);
        this.radiusYProperty().bind(pdblYLength);
        
    }
    
    
    /* *************************************************************** */
    /* ****************     OVER-RIDDEN METHODS     ****************** */
    /* *************************************************************** */
    @Override
    public DoubleProperty getXProperty() {
        return centerXProperty();
    }

    @Override
    public DoubleProperty getYProperty() {
        return centerYProperty();
    }

    @Override
    public void drawVertex(Color pColor) {
        setFill(pColor);
        setStroke(pColor);
        setOpacity(SceneConfig.VERTEX_OPACITY);
        setStrokeWidth(SceneConfig.VERTEX_STROKEWIDTH);
        setStrokeType(StrokeType.CENTERED);   
        this.toFront();     
    }

    @Override
    public Node getNode() {
        return this;
    }

    @Override
    public void changeColor(Color pclrNewColor) {
        drawVertex(pclrNewColor);
    }

    @Override
    public void throbAndBlink(int pintTimePerThrobMills, int pintThrobCount, int pintThrobScale, int pintTimeThrobScaleCycle) {
        //System.out.println("VertexCircle.throb : throbbing vertex");
        //System.out.println("stats : "+pintTimePerThrobMills+ " "+ pintThrobCount);
        ScaleTransition st = new ScaleTransition(Duration.millis(pintTimeThrobScaleCycle), this);
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setByX(pintThrobScale);
        st.setByY(pintThrobScale);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
        
        
        FadeTransition fadeStart = new FadeTransition(Duration.millis(pintTimePerThrobMills), this);
        fadeStart.setFromValue(0);
        fadeStart.setToValue(1);        
        fadeStart.setAutoReverse(true);
        fadeStart.setCycleCount(pintThrobCount);
        fadeStart.play();
    }
    
    @Override
    public void throb(int pintThrobCount, int pintTimePerThrobMills) {
        FadeTransition fade = new FadeTransition(Duration.millis(pintTimePerThrobMills), this);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setAutoReverse(true);
        fade.setCycleCount(pintThrobCount);
        fade.play();
    }
    
    @Override
    public void throbback(int pintTimePerThrobMills) {
        
    }
}