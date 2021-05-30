/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate.UIComponents;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author AICML Administrator
 */
public class PanAndZoomPane extends Pane {
    
     

        public static final double DEFAULT_DELTA = 1.3d;
        DoubleProperty myScale = new SimpleDoubleProperty(1.0);
        public DoubleProperty deltaY = new SimpleDoubleProperty(0.0);
        private Timeline timeline;
        
        private Double clickX = 0.0;
        private Double clickY = 0.0;

        public PanAndZoomPane() {

            this.timeline = new Timeline(60);

            // add scale transform
            scaleXProperty().bind(myScale);
            scaleYProperty().bind(myScale);
            //this.setStyle("-fx-border-color: red;");
            
        }
        
        public void setMouseClickX(Double x){
            clickX = x;
        }
        
        public void setMouseClickY(Double y){
            clickY = y;
        }
        
        public Double getMouseClickX(){ 
            return clickX;
        }
        
        public Double getMouseClickY(){     
            return clickY;
        }


        public double getScale() {
            return myScale.get();
        }

        public void setScale( double scale) {
            myScale.set(scale);
        }

        public void setPivot( double x, double y, double scale) {
            // note: pivot value must be untransformed, i. e. without scaling
            // timeline that scales and moves the node
            timeline.getKeyFrames().clear();
            timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(200), new KeyValue(translateXProperty(), getTranslateX() - x)),
                new KeyFrame(Duration.millis(200), new KeyValue(translateYProperty(), getTranslateY() - y)),
                new KeyFrame(Duration.millis(200), new KeyValue(myScale, scale))
            );
            timeline.play();

        }

    /** 
     * 
     * 
     * 
     *   
     */
        public void fitWidth () {
            double scale = getParent().getLayoutBounds().getMaxX()/getLayoutBounds().getMaxX();
            double oldScale = getScale();

            double f = (scale - oldScale);

            double dx = getTranslateX() - getBoundsInParent().getMinX() - getBoundsInParent().getWidth()/2;
            double dy = getTranslateY() - getBoundsInParent().getMinY() - getBoundsInParent().getHeight()/2;

            double newX = f*dx + getBoundsInParent().getMinX();
            double newY = f*dy + getBoundsInParent().getMinY();

            setPivot(newX, newY, scale);

        }

        public void resetZoom () {
            double scale = 1.0d;
            /*
             * Translation happens to (0,0) of the PanAndZoomPane.
             * But if graph has been moved out of the pane in any direction, then after translation 
             * we will not see the graph.
             * See if the logic can be changed to achieve that.
             */
            double x = getTranslateX();
            double y = getTranslateY();
            setPivot(x, y, scale);
            //double minX = this.getBoundsInLocal().getMinX();
            //double minY = this.getBoundsInLocal().getMinY();
            //setPivot(x+minX, y+minY, scale);
        }

        public double getDeltaY() {
            return deltaY.get();
        }
        public void setDeltaY( double dY) {
            deltaY.set(dY);
        }
}
    

/*    
        public static final double DEFAULT_DELTA = 1.3d;
        DoubleProperty myScale = new SimpleDoubleProperty(1.0);
        public DoubleProperty deltaY = new SimpleDoubleProperty(0.0);
        private Timeline timeline;
        //private double widthPanAndZoomPaneFixed;
        //private double heightPanAndZoomPaneFixed;

        public PanAndZoomPane() {
            //Fix the pane size, it will never be changed. It can zoom in/out but pane size relative to itself always remains fixed.
            //Added 1 rectangle of size(0,0) in top-left corner, and one in bottom right corner to ensure
            //that the pane size is fixed
            this.timeline = new Timeline(60);

            // add scale transform
            scaleXProperty().bind(myScale);
            scaleYProperty().bind(myScale);
            this.setStyle("-fx-border-color: blue;");
            //int offset = 20;
            //this.setMaxSize(SceneConfig.GRAPHCANVAS_WIDTH + offset, SceneConfig.GRAPHCANVAS_HEIGHT + offset);
            //this.setMinSize(SceneConfig.GRAPHCANVAS_WIDTH -offset, SceneConfig.GRAPHCANVAS_HEIGHT -offset);
            
            //Rectangle rectLowerTop = new Rectangle(0,0,0,0);
           
            //Rectangle rectRightBottom = new Rectangle(SceneConfig.GRAPHCANVAS_WIDTH, SceneConfig.GRAPHCANVAS_WIDTH, 0,0);
            
            //this.getChildren().addAll(rectLowerTop, rectRightBottom);
            
        }


        public double getScale() {
            return myScale.get();
        }

        public void setScale( double scale) {
            myScale.set(scale);
        }

        public void setPivot( double x, double y, double scale) {
            // note: pivot value must be untransformed, i. e. without scaling
            // timeline that scales and moves the node
            timeline.getKeyFrames().clear();
            timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(200), new KeyValue(translateXProperty(), getTranslateX() - x)),
                new KeyFrame(Duration.millis(200), new KeyValue(translateYProperty(), getTranslateY() - y)),
                new KeyFrame(Duration.millis(200), new KeyValue(myScale, scale))
            );
            timeline.play();

        }

        public void fitWidth () {
            double scale = getParent().getLayoutBounds().getMaxX()/getLayoutBounds().getMaxX();
            double oldScale = getScale();

            double f = (scale - oldScale);

            double dx = getTranslateX() - getBoundsInParent().getMinX() - getBoundsInParent().getWidth()/2;
            double dy = getTranslateY() - getBoundsInParent().getMinY() - getBoundsInParent().getHeight()/2;

            double newX = f*dx + getBoundsInParent().getMinX();
            double newY = f*dy + getBoundsInParent().getMinY();

            setPivot(newX, newY, scale);

        }

        public void resetZoom () {
            double scale = 1.0d;

            double x = getTranslateX();
            double y = getTranslateY();

            setPivot(x, y, scale);
        }

        public double getDeltaY() {
            return deltaY.get();
        }
        public void setDeltaY( double dY) {
            deltaY.set(dY);
        }
*/
