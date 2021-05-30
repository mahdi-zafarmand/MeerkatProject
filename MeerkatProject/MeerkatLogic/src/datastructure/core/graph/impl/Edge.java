/*
 * meerkat@aicml July 2015
 */
package datastructure.core.graph.impl;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.Date;

/**
 * Class Name : Edge Created Date : Description : Edge Implementation of the
 * IEdge Version : 2.0
 *
 * <p>
 * Edge is a class representing an edge of a graph.
 * </p>
 *
 * @author : Matt Gallivan
 *
 * EDIT HISTORY (most recent at the top) Date Author Description 2015-11-13 Afra
 * added EdgeType for heterogenous graphs.
 * @param <V>
 */
public class Edge<V extends IVertex> extends GraphElement implements IEdge<V>,
        Comparable<IEdge<V>> {

    /**
     *
     */
    public static enum VertexStatus {

        /**
         *
         */
        Source,

        /**
         *
         */
        Destination,

        /**
         *
         */
        EndPoint,

        /**
         *
         */
        NULL
    }
    
    private V vtxSource;
    private V vtxDestination;
    private boolean blnDirected = false;
    
    /**
     * Constructor 1
     *
     * @param pvtxSource : Source Node
     * @param pvtxDestination : Destination Node
     * @param pblnDirected
     */
    public Edge(V pvtxSource, V pvtxDestination, boolean pblnDirected) {
        this.vtxSource = pvtxSource;
        this.vtxDestination = pvtxDestination;
        this.blnDirected = pblnDirected;
    }
   

    /**
     * Constructor 2
     *
     * @param pvtxSource
     * @param pvtxDestination
     * @param directed
     * @param pdblWeight
     */
    public Edge(V pvtxSource, V pvtxDestination, boolean directed,
            double pdblWeight) {
        this(pvtxSource, pvtxDestination, directed);
        this.dblGraphElementWeight = pdblWeight;
    }
    
    /**
     * Constructor 3
     *
     * @param pvtxSource
     * @param pvtxDestination
     * @param pEdge
     * @param ptfCurrent
     */
    public Edge(V pvtxSource, V pvtxDestination, IEdge pEdge, TimeFrame ptfCurrent, TimeFrame ptfCurrentCopy) {
        
        this(pvtxSource, pvtxDestination, pEdge.isDirected(), pEdge.getWeight());
        
        
        // Add the User Attributes
        for (String strAttributeName : pEdge.getUserAttributer().getAttributeNames()) {
            String strAttributeValue = pEdge.getUserAttributer().getAttributeValue(strAttributeName, ptfCurrent) ;
            this.getUserAttributer().addAttribute(strAttributeName, strAttributeValue, ptfCurrentCopy);
        }
        // Add the System Attributes
        for (String strAttributeName : pEdge.getSystemAttributer().getAttributeNames()) {
            String strAttributeValue = pEdge.getSystemAttributer().getAttributeValue(strAttributeName, ptfCurrent) ;
            this.getSystemAttributer().addAttributeValue(strAttributeName, strAttributeValue, new Date(),ptfCurrentCopy);
        }

    }
    public void updateAttributes(IEdge eold, TimeFrame ptfCurrent, TimeFrame ptfCurrentCopy) {
        
        
        // For the time frame tf
        // deep copy attributes from old edge to new edge
        
        // Add the User Attributes
        for (String strAttributeName : eold.getUserAttributer().getAttributeNames()) {
            String strAttributeValue = eold.getUserAttributer().getAttributeValue(strAttributeName, ptfCurrent) ;
            this.getUserAttributer().addAttribute(strAttributeName, strAttributeValue, ptfCurrentCopy);
        }
        
        // Add the System Attributes
        for (String strAttributeName : eold.getSystemAttributer().getAttributeNames()) {
            String strAttributeValue = eold.getSystemAttributer().getAttributeValue(strAttributeName, ptfCurrent) ;
            this.getSystemAttributer().addAttributeValue(strAttributeName, strAttributeValue, new Date(), ptfCurrentCopy);
        }
        
        
    }
    
    @Override
    public void updateAttributeValue(String strAttrName, String AttrValue, TimeFrame ptfCurrentCopy) {
        this.getUserAttributer().addAttribute(strAttrName, AttrValue, ptfCurrentCopy);
    }
    

    /**
     *
     * @param pintId
     */
    @Override
    public void setId (int pintId) {
        this.intGraphElementID = pintId;
    }
    
    
    
    /**
     *
     * @return
     */
    @Override
    public int getId() {
        return this.intGraphElementID;
    }
    
    /**
     *
     * @param pdblWeight
     */
    @Override
    public void setWeight (double pdblWeight) {
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
    
    /**
     *
     * @return
     */
    @Override
    public boolean isDirected() {
        return blnDirected;
    }

    /**
     *
     * @return
     */
    @Override
    public V getSource() {
        return vtxSource;
    }

    /**
     *
     * @return
     */
    @Override
    public V getDestination() {
        return vtxDestination;
    }
    
    /**
     *
     * @param v
     * @return
     */
    public VertexStatus getVStatus(V v) {
        if (blnDirected) {
            if (vtxSource.equals(v)) {
                return VertexStatus.Source;
            } else if (vtxDestination.equals(v)) {
                return VertexStatus.Destination;
            } else {
                return VertexStatus.NULL;
            }
        } else if (vtxSource.equals(v) || vtxDestination.equals(v)) {
            return VertexStatus.EndPoint;
        }
        return VertexStatus.NULL;
    }

    /**
     *
     * @param v
     * @return
     */
    @Override
    public V getOtherEndPoint(V v) {
        if (v.equals(this.vtxSource)) {
            return this.vtxDestination;
        } else if (v.equals(this.vtxDestination)) {
            return this.vtxSource;
        }
        return null;
    }
    
    /**
     * Compares the MeerkatID of this edge with the one 
     * passed in as parameter.
     * MeerkatID shown which edge was added earlier.
     * @param e
     * @return 
     */
    @Override
    public int compareTo(IEdge<V> e) {
        return (this.intGraphElementID - e.getId());
    }

    /**
     * returns the edge in the following format:
     *
     * edgeId : sourceVertexId - destinationVertexId (edgeWeight)
     * @return 
     */
    @Override
    public String toString() {
        StringBuilder strbEdge = new StringBuilder();
        strbEdge.append(this.intGraphElementID);
        strbEdge.append(" : ");
        strbEdge.append(vtxSource.getId()).append(" - ")
                .append(vtxDestination.getId());
        strbEdge.append(" (").append(this.getWeight()).append(") ");

        return strbEdge.toString();
    }

    /**
     * Updates the Color Attribute of an Edge
     * @param pColor
     * @param ptf 
     * @author Talat
     * @since 2018-01-17
     * 
     */
    @Override
    public void updateColor(String pColor, TimeFrame ptf){
        this.getSystemAttributer().addAttributeValue(MeerkatSystem.COLOR, pColor, new Date(), ptf);
    }
    
    /**
     * Retrieves the Color System Attribute of an Edge
     * @param timeFrame     
     * @return Color as Hexadecimal String
     * @author Talat
     * @since 2018-01-17
     * 
     */
    public String getColor(TimeFrame timeFrame){
        return this.getSystemAttributer().getAttributeValue(MeerkatSystem.COLOR, timeFrame);
    }
}
