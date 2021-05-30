/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.graph.classinterface;

import datastructure.core.TimeFrame;
import datastructure.core.graph.impl.StaticGraph;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Last edited by Afra Abnar july 2015.
 * Edited before by Talat Iqbal.
 * 
 *   An interface representing a graph containing vertices, edges, and communities.
 *
 *   @author Matt Gallivan
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author       Description
 *  2015-10-19       Afra        - Removed useless and UI related methods.
 *                               - Removed algorithm computation such as:
 *                               density, clusteringCoefficient, etc.
 *  2015-09-22       Talat       Addition of methods getVertexAttributeNamesWithType() & getEdgeAttributeNamesWithType()
 *  2015-08-27       Talat       Addition of a method getAllEdgeAsVertexIDs() that would retrieve the vertex IDs as a pair of integers
 *  July 2015        Afra
 * @param <V>
 * @param <E>
 *
 */
public interface IGraph<V extends IVertex,E extends IEdge<V>>{
    
    /**
     *
     * @return
     */
    public int getId();

    /**
     *
     * @param pintId
     */
    public void setId(int pintId);
    /* Vertices */

    /**
     *
     * @param pintVertexID
     * @return
     */
    
    public V getVertex(int pintVertexID); 

    /**
     *
     * @return
     */
    public List<Integer> getAllVertexIds();
        
    /**
     *
     * @param phmapVertices
     */
    public void addVertices(List<V> phmapVertices); // public void addVertex(HashMap<String, IVertex> phmapVertices);

    /**
     *
     * @param phmapVertices
     */
    public void addVertices(Set<V> phmapVertices);

    /**
     *
     * @param pVertex
     */
    public void addVertex(V pVertex);
    
    /**
     *
     * @return
     */
    public List<V> getAllVertices();

    /**
     *
     * @param pVertex
     */
    public void removeVertex(V pVertex);    

    /**
     *
     * @param phmapVertex
     */
    public void removeVertices(List<V> phmapVertex); // public void removeVertex(HashMap<String, IVertex> phmapVertex);
 
    /**
     *
     * @return
     */
    public Set<String> getVertexAllAttributeNames();

    /**
     *
     * @return
     */
    public Set<String> getVertexNumericalAttributeNames();

    /**
     *
     * @return
     */
    public Map<String, Boolean> getVertexAttributeNamesWithType();
    
    /* Edges */

    /**
     *
     * @param pintEdgeID
     * @return
     */
    
    public E getEdge(int pintEdgeID);

    /**
     *
     * @return
     */
    public List<Integer> getAllEdgeIds();
    
    /**
     *
     * @return
     */
    public List<E> getAllEdges();

    /**
     *
     * @param plstEdges
     */
    public void removeEdge(List<E> plstEdges);

    /**
     *
     * @param pEdge
     */
    public void removeEdge(E pEdge);

    /**
     *
     */
    public void clearEdges();

    /**
     *
     * @return
     */
    public int getMaxEdgeId();

    /**
     *
     * @return
     */
    public Set<String> getEdgeUserAttributeNames();

    /**
     *
     * @return
     */
    public Set<String> getEdgeNumericalAttributeNames();

    /**
     *
     * @return
     */
    public Map<String, Boolean> getEdgeAttributeNamesWithType();
    
    
    /* GRAPH PROPERTIES */

    /**
     *
     * @return
     */
    
    public int getVertexCount();          

    /**
     *
     * @return
     */
    public int getEdgeCount();    
    
    /**
     *
     * @return
     */
    public Date getLastChangeTime();

    /**
     *
     * @return
     */
    //public List<Integer[]> getAllEdgeAsVertexIDs();

    /**
     *
     * @param pstrDelimiter
     * @return
     */
    //public List<String> getAllEdgeAsVertexIDString(String pstrDelimiter);
    
    
    
    /* **********************   EDGES ************************  */

    /**
     *
     * @param plstEdges
     */
    
    
    public void addEdges(List<E> plstEdges);

    /**
     * 
     * @param pEdge
     * @return 
     */
    public boolean addEdge(E pEdge);
    
    public TimeFrame getTimeframe();
    
    public void setTimeframe(TimeFrame timeframe);
        
    
    
    

}
