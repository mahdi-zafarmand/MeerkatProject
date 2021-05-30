/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphelements;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 *  Interface Name  : IVertexShape
 *  Created Date    : 2015-11 
 *  Description     : The Vertex Shape of the 
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public interface IVertexShape {
    public DoubleProperty getXProperty();
    public DoubleProperty getYProperty();
    public void drawVertex(Color pColor);
    public Node getNode();
    public void changeColor(Color pclrNewColor);
    public void throb(int pintThrobCount, int pintTimePerThrobMills);
    public void throbAndBlink(int pintTimePerThrobMills, int pintThrobCount, int pintThrobScale, int pintTimeThrobScaleCycle);
    public void throbback(int pintTimePerThrobMills) ;
}
