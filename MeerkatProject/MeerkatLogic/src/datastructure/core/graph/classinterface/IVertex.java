/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.graph.classinterface;

import datastructure.core.TimeFrame;

/**
 * Last edited by Afra Abnar July 2015.
 * before that edited by Talat Iqbal.
 * 
 * <p>
 * An interface representing a vertex which has an id, weight, attributes,
 * color, location, and is selectable.
 * </p>
 * 
 * @author Matt Gallivan * 
 * 
 * 
 * EDIT HISTORY
 * 2018-01-17   Talat       Added methods updateColor, getColor, updateXYPosition, getXPosition, getYPosition
 */
public interface IVertex extends IGraphElement{  

    public void updateAttributes(IVertex vold, TimeFrame tf, TimeFrame tfCopy);
    public void updateAttributeValue(String strAttrName , String AttrValue, TimeFrame ptfCurrentCopy);
    
    public void updateColor(String pColor, TimeFrame ptf);    
    public String getColor(TimeFrame timeFrame);
    public void updateIconURL(String pstrIconURL, TimeFrame ptf);
    public String getIconURL(TimeFrame ptf);
    public void updateXYPosition(double X, double Y, TimeFrame ptf);
    public String getXPosition(TimeFrame timeFrame);
    public String getYPosition(TimeFrame timeFrame);
	
}
