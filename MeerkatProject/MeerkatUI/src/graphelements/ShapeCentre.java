/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphelements;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Node;

/**
 *  Class Name      : ShapeCentre
 *  Created Date    : 2015-11-06
 *  Description     : Centre of any given shape
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class ShapeCentre {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private DoubleProperty dblpCenterX = new SimpleDoubleProperty();
    private DoubleProperty dblpCenterY = new SimpleDoubleProperty();

    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    /**
     *  Constructor Name: ShapeCentre
     *  Created Date    : 2015-11-06
     *  Description     : Initiates the Nodes
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pNode : Node
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public ShapeCentre(Node pNode) {
        calculateCentre(pNode.getBoundsInParent());
        pNode.boundsInParentProperty().addListener((ObservableValue<? extends Bounds> observableValue, Bounds oldBounds, Bounds bounds) -> {
            calculateCentre(bounds);
        });
    }

    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    /**
     *  Method Name     : calculateCentre()
     *  Created Date    : 2015-11-06
     *  Description     : Calculates the centre of the Node. 
     *                      The centre can be retrieved using the properties
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pBounds : Bounds
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void calculateCentre(Bounds pBounds) {
        dblpCenterX.set(pBounds.getMinX() + pBounds.getWidth()  / 2);
        dblpCenterY.set(pBounds.getMinY() + pBounds.getHeight() / 2);
    }

    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public DoubleProperty centerXProperty() {
        return dblpCenterX;
    }

    public DoubleProperty centerYProperty() {
        return dblpCenterY;
    }
}