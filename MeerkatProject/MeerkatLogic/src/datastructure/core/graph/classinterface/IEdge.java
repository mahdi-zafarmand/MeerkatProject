/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.graph.classinterface;

import datastructure.core.TimeFrame;



/**
 * <p>
 * An interface representing a graph's edge. An edge has a source vertex, a
 * destination vertex, and can be directed or undirected.
 * </p>
 * 
 * <p>
 * The source vertex is the beginning-point of the edge, the destination vertex
 * is the end-point. If an edge is directed it means it is one-way (represented
 * with an arrow pointing towards its destination vertex).
 * </p>
 * 
 * @author Matt Gallivan
 * @param <V>
 *  * 
 * * EDIT HISTORY
 * 2018-01-17   Talat       Added methods updateColor, getColor
 */
public interface IEdge<V extends IVertex> extends IGraphElement{
    
    /**
     *
     * @return
     */
    public V getSource();

    /**
     *
     * @return
     */
    public V getDestination();
    
    /**
     *
     * @return
     */
    public boolean isDirected();
    
    /**
     *
     * @param v
     * @return
     */
    public V getOtherEndPoint(V v);
    
    /**
     *
     * @return
     */
    @Override
    public String toString();

    public void updateAttributes(IEdge eold, TimeFrame tf, TimeFrame tfCopy);
    public void updateAttributeValue(String strAttrName , String AttrValue, TimeFrame ptfCurrentCopy);
    
    public void updateColor(String pColor, TimeFrame ptf);
    public String getColor(TimeFrame timeFrame);
    
}
