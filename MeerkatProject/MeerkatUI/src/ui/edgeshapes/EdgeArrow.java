/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.edgeshapes;

import javafx.scene.shape.Polygon;

/**
 *
 * @author talat
 */
public class EdgeArrow extends Polygon {
    
    private EdgeLine line;
    double radius;
    static double[] points = {0.0, 5.0, -5.0, -5.0, 5.0, -5.0};

    public EdgeArrow(EdgeLine line, double AnchorRadius) {        
        super(points);
        this.line = line;
        this.radius = AnchorRadius * 2;
        initialize();

    }

    private void initialize() {
        double angle = Math.atan2(line.getEndY() - line.getStartY(), line.getEndX() - line.getStartX()) * 180 / 3.14;

        double height = line.getEndY() - line.getStartY();
        double width = line.getEndX() - line.getStartX();
        double length = Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2));

        double subtractWidth = radius * width / length;
        double subtractHeight = radius * height / length;
        
        // System.out.println("X Value : " + line.getStartXValue());
        // System.out.println("Y Value : " + line.getStartYValue());

        setRotate(angle - 90);
        setTranslateX(line.getEndX()/2);
        setTranslateY(line.getEndY()/2);
        // setTranslateX(line.getEndX());
        // setTranslateY(line.getEndY());
        // setTranslateX(line.getEndX() - subtractWidth);
        // setTranslateY(line.getEndY() - subtractHeight);
    }

    public void update(){
        initialize();
    }
}
