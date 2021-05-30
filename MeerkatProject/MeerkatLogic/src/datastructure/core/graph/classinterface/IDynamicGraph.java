/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.graph.classinterface;

import datastructure.core.TimeFrame;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public interface IDynamicGraph<V extends IVertex, E extends IEdge<V>> {
    
    /**
     *
     * @param pintId
     */
    public void setId(int pintId);

    /**
     *
     * @return
     */
    public int getId();
    
    /**
     *
     * @return
     */
    public String getGraphTitle();

    /**
     *
     * @param pstrGraphTitle
     */
    public void setGraphTitle(String pstrGraphTitle);
    
    /**
     *
     * @return
     */
    public String getGraphFile();

    /**
     *
     * @param pstrGraphFile
     */
    public void setGraphFile(String pstrGraphFile);
    
    /**
     *
     * @param pTimeFrame
     * @param pIGraph
     */
    public void addGraph (TimeFrame pTimeFrame, IStaticGraph<V,E> pIGraph);
    
    /**
     *
     * @param pTimeframe
     * @return
     */
    public IStaticGraph<V,E> getGraph(TimeFrame pTimeframe);
    
    /**
     *
     * @return
     */
    public List<TimeFrame> getAllTimeFrames();
    
    /**
     *
     * @param pintVertexID
     * @return
     */
    public V getVertex(int pintVertexID);
    
    /**
     *
     * @param tf
     * @return
     */
    public V getAnyVertex(TimeFrame tf);
    /**
     *
     * @param tf
     * @return
     */
    public List<V> getVertices(TimeFrame tf);

    /**
     *
     * @return
     */
    public List<Integer> getAllVertexIds();

    /**
     *
     * @return
     */
    public int getVertexCount();
    
    /**
     * 
     * @return
     * 
     * @param tf
     */
    public int getVertexCount(TimeFrame tf);
    
    /**
     *
     * @param pVertex
     * @param plstTimeFrame
     */
    public void addVertex(V pVertex, TimeFrame plstTimeFrame);

    /**
     *
     * @param phmapVertices
     * @param tf
     */
    public void addVertex(List<V> phmapVertices, TimeFrame tf);

    /**
     *
     * @param psetVertices
     * @param tf
     */
    public void addVertex(Set<V> psetVertices, TimeFrame tf);
            
    /**
     *
     * @return
     */
    public List<V> getAllVertices();
   
    /**
     *
     * @param pVertex
     * @param plstTimeFrame
     */
    public void removeVertex(V pVertex, List<TimeFrame> plstTimeFrame);

    /**
     *
     * @param pVertex
     */
    public void removeVertex(V pVertex);

    /**
     *
     * @param plstVertex
     */
    public void removeVertices(List<V> plstVertex);

    /**
     *
     * @param v
     * @param pTimeFrame
     * @return
     */
    public int getDegree(V v, TimeFrame pTimeFrame);

    /**
     *
     * @param v
     * @param pTimeFrame
     * @return
     */
    public List<V> getNeighbors(V v, TimeFrame pTimeFrame);

    /**
     *
     * @param v
     * @param pTimeFrame
     * @return
     */
    public List<V> getIncomingNeighbors(V v, TimeFrame pTimeFrame);

    /**
     *
     * @param v
     * @param pTimeFrame
     * @return
     */
    public List<V> getOutgoingNeighbors(V v, TimeFrame pTimeFrame);

    /**
     *
     * @param v
     * @param pTimeFrame
     * @return
     */
    public List<E> getOutgoingEdges(V v, TimeFrame pTimeFrame);

    /**
     *
     * @param v
     * @param pTimeFrame
     * @return
     */
    public List<E> getIncomingEdges(V v, TimeFrame pTimeFrame);

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

    /**
     *
     * @param pintEdgeID
     * @return
     */
    public E getEdge(int pintEdgeID);

    /**
     *
     * @param v
     * @param pTimeFrame
     * @return
     */
    public List<E> getEdges(V v, TimeFrame pTimeFrame);

    /**
     *
     * @return
     */
    public List<Integer> getAllEdgeIds();

    /**
     *
     * @param e
     * @param tf
     */
    public void addEdge(E e , TimeFrame tf);

    /**
     *
     * @param tf
     * @return
     */
    public List<E> getEdges(TimeFrame tf);

    /**
     *
     * @return
     */
    public List<E> getAllEdges();
    
    /**
     * removes the edge from the specific timeframe.
     * @param pEdge
     * @param pTimeFrame 
     */
    public void removeEdge(E pEdge, TimeFrame pTimeFrame);
    
    /**
     *
     * @param pEdge
     */
    public void removeEdge(E pEdge);

    /**
     *
     * @param plstEdges
     */
    public void removeEdge(List<E> plstEdges);

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
    public Set<String> getEdgeAttributeNames();

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
    
    /**
     *
     * @param source
     * @param destination
     * @return
     */
    public Set<E> findEdge(V source, V destination);

    /**
     *
     * @param source
     * @param destination
     * @param tf
     * @return
     */
    public E findEdge(V source, V destination, TimeFrame tf);
    
    /**
     *
     * @param timeFrame
     * @return Set<int[]>
     */
    public Set<int[]> getNonExistingEdges(TimeFrame timeFrame);
    /**
     *
     * @param u
     * @param v
     * @param tf
     * @return Set<V>
     */
    public Set<V> getCommonNeighbors(V u, V v, TimeFrame tf);
    /**
     *
     * @return
     */
    public int getEdgeCount();
    

    /**
     * Removes all edge in a specific timeframe.
     * @param ptf 
     */
    public void clearEdges(TimeFrame ptf);
    
    /**
     * 
     * @param e
     * @param pTimeFrame
     * @return Neighbors of and edge in a specific timeframe.
     */
    public List<E> getNeighbors(E e, TimeFrame pTimeFrame);
    
    /**
     *
     * @return
     */
    public Date getLastChangeTime();
    
    
     /**
     * updates the SYS:X, SYS:Y values of all vertices in all time frame from the coordinates received from UI
     * 
     * @param pmapGraphTimeFramesVerticesLocation
     * @return
     */
    public void updateVertexLocations(Map<Integer, Map<Integer, Double[]>> pmapGraphTimeFramesVerticesLocation );
    
    
    /**
     * updates the SYS:X, SYS:Y values of all vertices in a time frame from the coordinates received in the map
     * @param timeframe
     * @param pmapVertexLocation
     * @return
     */
    public void updateVertexLocationsInTimeFrame( TimeFrame timeframe, Map<Integer, Double[]> pmapVertexLocation );
    
    public void renameGraph(String pstrgraphNewTitle);
    
    /**
     * The maxFileId of any vertex in this graph
     * @return
     */
    public int getMaxFileId();

    
    
    public E getEdgeIfExists(int pintSourceVertexID, int pintDestinationVertexID);
    
    
    
    public boolean updateVertexColors(TimeFrame ptf, Map<Integer, String> pmapVertexColors);
    public boolean updateEdgeColors(TimeFrame ptf, Map<Integer, String> pmapEdgeColors);
    
    
    
    public boolean updateVertexColors(TimeFrame ptf, String color, List<Integer> plstVertexIDs);
    public boolean updateEdgeColors(TimeFrame ptf, String color, List<Integer> plstEdgeIDs);
    
    public boolean updateVertexIconURLs(TimeFrame ptf, Map<Integer, String> pmapVertexIconURLs);
    public boolean updateVertexIconURLs(TimeFrame ptf, String pstrIconURL, List<Integer> plstVertexIDs);
    
    
    /**
     * Compute the colors of communities for this time frame
     * @param tf
     * @param setCommunities
     */
    public void calculateCommunityColors(Set<String> setCommunities, TimeFrame tf);
    
    public String getCommunityColor(String strCommunity, TimeFrame tf);
    
    public void setCommunityColor(String strCommunity, String strColor, TimeFrame tf);
    
    public Map<String,String> getMapCommunityColors(TimeFrame tf);

    public void resetGlobalCommunityColorMap();

    public void setCommunityColorMap(TimeFrame tfCopy, Map<String, String> mapCommunityColorMapdynaGraph);

    public void setGlobalCommunityColorMap(Map<String, String> mapGloablCommunityColordynaGraph);

    public Map<String, String> getMapGloablCommunityColor();

    public void calculateCommunityColorsOnLoading();
    
    public Set<String> calculateCommunityOnLoading(TimeFrame ptf);
    

}