/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.graph.impl;


import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IVertex;
import java.util.Date;

/**
 * Edited by Afra Abnar July 2015.
 * Edited by Talat Iqbal.
 * <p>
 * Vertex is a class representing the vertices (or nodes) of a graph.
 * </p>
 * <p>
 * <b>Notifies On:</b><br>
 * 
 * <pre>
 * setLocation
 * setShape
 * </pre>
 * 
 * </p>
 * 
 * 
 * @author Matt Gallivan
 * 
 */
public class Vertex extends GraphElement implements IVertex {
	
    
    /* CONSTRUCTORS */

    /**
     *
     */
    
    public Vertex() {
        
    }
    
    public Vertex(IVertex pvtxSource, int pintID, TimeFrame ptfCurrent, TimeFrame ptfCurrentCopy) {
        this();
        
        // Assign the GraphElement ID and the Weight
        this.intGraphElementID = pintID;
        this.dblGraphElementWeight = pvtxSource.getWeight();
        // Add the User Attributes
        for (String strAttributeName : pvtxSource.getUserAttributer().getAttributeNames(ptfCurrent)) {
            String strAttributeValue = pvtxSource.getUserAttributer().getAttributeValue(strAttributeName, ptfCurrent) ;
            this.getUserAttributer().addAttribute(strAttributeName, strAttributeValue, ptfCurrentCopy);
        }
        
        // Add the System Attributes
        for (String strAttributeName : pvtxSource.getSystemAttributer().getAttributeNames(ptfCurrent)) {
            String strAttributeValue = pvtxSource.getSystemAttributer().getAttributeValue(strAttributeName, ptfCurrent) ;
            this.getSystemAttributer().addAttributeValue(strAttributeName, strAttributeValue, new Date(), ptfCurrentCopy);
        }
        
        
                        
        
    }
            
    public void updateAttributes(IVertex pvtxSource, TimeFrame ptfCurrent, TimeFrame ptfCurrentCopy) {
        
        
        // For the time frame tf
        // deep copy attributes from old vertex to new vertex
        // Add the User Attributes
        for (String strAttributeName : pvtxSource.getUserAttributer().getAttributeNames(ptfCurrent)) {
            String strAttributeValue = pvtxSource.getUserAttributer().getAttributeValue(strAttributeName, ptfCurrent) ;
            this.getUserAttributer().addAttribute(strAttributeName, strAttributeValue, ptfCurrentCopy); 
        }
        
        // Add the System Attributes
        for (String strAttributeName : pvtxSource.getSystemAttributer().getAttributeNames(ptfCurrent)) {
            String strAttributeValue = pvtxSource.getSystemAttributer().getAttributeValue(strAttributeName, ptfCurrent) ;
            this.getSystemAttributer().addAttributeValue(strAttributeName, strAttributeValue, new Date(), ptfCurrentCopy);    
        }
        //file id = vertex id +1;
        // update the file_id of new vertex as vertex.id+1. In the constructor, it just copied it from the old vertex.
        //updateFileIdAttributeValue(ptfCurrentCopy);
        
        
    }
    
    @Override
    public void updateAttributeValue(String strAttrName , String AttrValue, TimeFrame ptf){
        this.getUserAttributer().addAttribute(strAttrName, AttrValue,ptf);
    }
    
    /**
     * Updates the System Attribute of Color
     * @param pColor
     * @param ptf 
     * @since 2018-01-17
     * @author Talat
     */
    @Override
    public void updateColor(String pColor, TimeFrame ptf){    
        this.getSystemAttributer().addAttributeValue(MeerkatSystem.COLOR, pColor, new Date(), ptf);
    }
    
    /**
     * Retrieves the Color System Attribute as a Hexadecimal String of RGB values
     * @param timeFrame
     * @return Color as Hexadecimal String
     * @since 2018-01-17
     * @author Talat
     */
    @Override
    public String getColor(TimeFrame timeFrame){
        return this.getSystemAttributer().getAttributeValue(MeerkatSystem.COLOR, timeFrame);
    }
    
    /**
     * Updates the System Attribute of TypeIconURL
     * @param pstrIconURL
     * @param ptf 
     * @since 2018-02-08
     * @author Talat
     */
    @Override
    public void updateIconURL(String pstrIconURL, TimeFrame ptf){
        this.getSystemAttributer().addAttributeValue(MeerkatSystem.TYPEICONURL, pstrIconURL, new Date(), ptf);
    }
    
    /**
     * Retrieves the Type Icon URL System Attribute 
     * @param timeFrame
     * @return Icon URL
     * @since 2018-02-08
     * @author Talat
     */
    @Override
    public String getIconURL(TimeFrame timeFrame){
        return this.getSystemAttributer().getAttributeValue(MeerkatSystem.TYPEICONURL, timeFrame);
    }
    
     /**
     * Updates the System Attribute of X & Y Positions
     * @param X
     * @param Y
     * @param ptf 
     * @since 2018-01-17
     * @author Talat
     */
    @Override
    public void updateXYPosition(double X, double Y, TimeFrame ptf){    
        this.getSystemAttributer().addAttributeValue(MeerkatSystem.X, Double.toString(X), new Date(), ptf);
        this.getSystemAttributer().addAttributeValue(MeerkatSystem.Y, Double.toString(Y), new Date(), ptf);
    }
    
    /**
     * Retrieves the X Position System Attribute 
     * @param timeFrame
     * @return X Position of the Vertex
     * @since 2018-01-17
     * @author Talat
     */
    @Override
    public String getXPosition(TimeFrame timeFrame){
        return this.getSystemAttributer().getAttributeValue(MeerkatSystem.X, timeFrame);
    }
    
    /**
     * Retrieves the Y Position System Attribute 
     * @param timeFrame
     * @return Y Position of the Vertex
     * @since 2018-01-17
     * @author Talat
     */
    @Override
    public String getYPosition(TimeFrame timeFrame){
        return this.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, timeFrame);
    }
    
        
    /**
     *
     * @param pdblWeight
     */
    public Vertex(double pdblWeight) {
        this();
        this.dblGraphElementWeight = pdblWeight;
    }
   
    /**
     *
     * @param pintId
     */
    @Override
    //TODO make this method private - a new vertex object (not in graph) with same id as that of a vertex inside the graph
    //can be removed from graph because graph works based on id of the vertex. id must be set only by graph class when its added or deleted from a graph
    public void setId(int pintId) {
        this.intGraphElementID = pintId;
    }
    
    /**
     *
     * @return
     */
    @Override
    public int getId(){
        return this.intGraphElementID;
    }
    
    /**
     *
     * @param pdblWeight
     */
    @Override
    public void setWeight(double pdblWeight) {
        this.dblGraphElementWeight = pdblWeight;
    }
    
    /**
     *
     * @return
     */
    @Override
    public double getWeight() {
        return this.dblGraphElementWeight;
    }
}
