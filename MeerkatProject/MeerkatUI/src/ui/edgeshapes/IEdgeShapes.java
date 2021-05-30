/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.edgeshapes;

import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 *
 * @author aicml_adm
 */
public interface IEdgeShapes {
    public Node getShapeNode();
    public void restoreStrokeWidth(double pdblScaleFactor);
    public double getLineStrokeWidth();
    public void selectEdge();
    public void draw();
    public void changeToDefaultColor();
    public void setEdgeColor(Color pclrNewColor);
    public void changePrimaryColor(Color pclrNewColor);
    public void changeColor(String pstrRGBColor);
    public void changeStrokeWidth(double pintStrokeWidth);
    public void clearMiningResult();    
    public void clearListAndSelectEdge();
    public void deselectEdge();
    
    public Double getStartXValue();
    public Double getStartYValue();    
    public Double getEndXValue();
    public Double getEndYValue();
    public Color getColor();
}
