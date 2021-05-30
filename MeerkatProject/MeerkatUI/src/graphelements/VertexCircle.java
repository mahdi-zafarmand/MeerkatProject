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
 *  Class Name      : VertexCircle
 *  Created Date    : 2015-11-xx
 *  Description     : The Circle shape of a Vertex
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class VertexCircle extends Ellipse implements IVertexShape {
        
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    /**
     *  Constructor Name: VertexCircle()
     *  Created Date    : 2015-11-xx
     *  Description     : Constructor for VertexCircle
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
    public VertexCircle(DoubleProperty pdblCentreX, DoubleProperty pdblCentreY, 
            DoubleProperty pdblXLength, DoubleProperty pdblYLength, Color pColor) {
        
        super(pdblCentreX.get(), pdblCentreY.get(), pdblXLength.get()/2, pdblYLength.get()/2);
        drawVertex(pColor);
        // System.out.println("VertexCircle.VertexCircle(): Radius = "+radiusXProperty().get()+"\t"+radiusYProperty().get());
        this.radiusXProperty().bind(pdblXLength.divide(2));
        this.radiusYProperty().bind(pdblYLength.divide(2));
    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
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
    public void throb(int pintTimePerThrobMills, int pintThrobCount) {
        //System.out.println("VertexCircle.throb : throbbing vertex");
        //System.out.println("stats : "+pintTimePerThrobMills+ " "+ pintThrobCount);
        FadeTransition fadeStart = new FadeTransition(Duration.millis(pintTimePerThrobMills), this);
        fadeStart.setFromValue(0);
        fadeStart.setToValue(1);        
        fadeStart.setAutoReverse(true);
        fadeStart.setCycleCount(pintThrobCount);
        fadeStart.play();
    }
    
    @Override
    public void throbback(int pintTimePerThrobMills) {
        // The second fade is to bring it back to the originial place since the fade will end at fading away the circle
        FadeTransition fadeEnd = new FadeTransition(Duration.millis(pintTimePerThrobMills), this);
        fadeEnd.setFromValue(0);
        fadeEnd.setToValue(1);        
        fadeEnd.setAutoReverse(false);
        fadeEnd.setCycleCount(1);
        fadeEnd.play();
    }
}