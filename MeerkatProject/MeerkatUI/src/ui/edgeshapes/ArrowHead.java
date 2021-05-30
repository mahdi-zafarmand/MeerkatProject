/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.edgeshapes;

import graphelements.UIVertex;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Polygon;

/**
 *
 * @author talat
 */
public class ArrowHead extends Polygon {

    private IEdgeShapes line;
    private UIVertex vtxSource ;
    private UIVertex vtxDestination ;
    private double dblXRadius;
    private double dblYRadius ;
    private DoubleProperty dblXProp ;
    private DoubleProperty dblYProp ;
    
    private static double dblWidth = 5;
    // private static double dblHeight = Math.sqrt(Math.pow(dblWidth, 2) - (Math.pow(dblWidth/2.0, 2)));
     private static double dblHeight = 10;
    // private static double[] points = {0.0, dblPolLength, -dblPolLength, -dblPolLength, dblPolLength, -dblPolLength};
    private static double[] points = {0.0, 0.0, dblWidth, 0, dblWidth/2, dblHeight};

    public ArrowHead(UIVertex pvtxSource, UIVertex pvtxDestination, IEdgeShapes line) {
        super(points);
        
        setOpacity(1);
        setFill(line.getColor());
        
        this.line = line;
        this.vtxSource = pvtxSource ;
        this.vtxDestination = pvtxDestination ;
        
        dblXProp = new SimpleDoubleProperty();
        dblXProp.bind(vtxDestination.getXProperty());
        dblYProp = new SimpleDoubleProperty();
        dblYProp.bind(vtxDestination.getYProperty());
        
        this.dblXRadius = vtxDestination.getXLengthProperty().getValue();
        this.dblYRadius = vtxDestination.getYLengthProperty().getValue();
        
        initialize();
    }

    private void initialize() {
        
        /*
        double angle = Math.atan2(line.getEndYValue()- line.getStartYValue(), 
                line.getEndXValue() - line.getStartXValue()) * 180 / 3.14;
        double height = line.getEndYValue() - line.getStartYValue();
        double width = line.getEndXValue() - line.getStartXValue();
        double length = Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2));
        */
        
        double height = vtxDestination.getYProperty().getValue()- vtxSource.getYProperty().getValue();
        double width = vtxDestination.getXProperty().getValue() - vtxSource.getXProperty().getValue();
        double length = Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2));
        double angle = Math.atan2(height,width) * 180 / 3.14;

        double subtractWidth = dblXRadius * width / length;
        double subtractHeight = dblYRadius * height / length;

        setRotate(angle - 90);
        setTranslateX(dblXProp.getValue() - dblWidth/2.0 + this.dblXRadius/2 - subtractWidth);
        setTranslateY(dblYProp.getValue() - dblHeight/2.0 + this.dblYRadius/2 - subtractHeight);
        
    }

    public void update(){
        initialize();
    }
}