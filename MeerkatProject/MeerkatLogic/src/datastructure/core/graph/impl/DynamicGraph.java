package datastructure.core.graph.impl;

import config.MeerkatSystem;
import datastructure.core.IDGenerator;
import datastructure.core.TimeFrame;
import java.util.HashMap;

import datastructure.core.graph.classinterface.*;
import datastructure.general.DynamicArray;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author aabnar
 *
 * @param <V>
 * @param <E>
 */
public class DynamicGraph<V extends IVertex, E extends IEdge<V>> 
                                        implements IDynamicGraph<V,E>{

    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */

    /**
     *
     */
    
    public String className = this.getClass().getCanonicalName();
    
    /*String indicating graph title*/
    private String strGraphTitle ;
    
    /*String indicating full path of graphFile including extenstion*/
    private String strGraphFilePathFull ;
    
    /*graph MeerkatId*/
    private int intDynaGraphId;
    
    /*ordered list of timeframes*/
    private final List<TimeFrame> lstTimeFrameOrdering = new ArrayList<>();

    /*map associating staticGraphs to timeframes*/
    private final HashMap<TimeFrame,IStaticGraph<V,E>> hmpGraphsAtTimeframes;
    
    /*list of all vertices*/
    private DynamicArray<V> mapAllVertices;
    
    /*list of all edges*/
    private DynamicArray<E> mapAllEdges;
    
    /*adjacency list for all timeframes: from vertexId to map of timeframe to edgeList*/
    private DynamicArray<HashMap<TimeFrame,LinkedList<E>>> darrGlobalAdjList;
    
    /*Id generator for vertices*/
    private IDGenerator vertexIdGen = new IDGenerator();
    
    /*Id generator for edges*/
    private IDGenerator edgeIdGen = new IDGenerator();
    
    /*Color of Communities*/
    private ICommunityColors icommunityColors;
   
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */

    /**
     *
     * @param pintVCount
     * @param pintEdgeCount
     */
    
    public DynamicGraph(int pintVCount, int pintEdgeCount) {
        this.hmpGraphsAtTimeframes = new HashMap<>();
        mapAllVertices = new DynamicArray<>(pintVCount);
        mapAllEdges = new DynamicArray<>(pintEdgeCount);
        darrGlobalAdjList = new DynamicArray<>(pintVCount);
        
        icommunityColors = new CommunityColors();
    }
    
    /* *************************************************************** */
    /* ****************     OVER-RIDDEN  METHODS     ***************** */
    /* *************************************************************** */
    
    /**
     * Compute the colors of communities for this time frame
     * @param tf
     * @param setCommunities
     */
    @Override
    public void calculateCommunityColors(Set<String> setCommunities, TimeFrame tf){
        
        icommunityColors.calculateCommunityColor(tf, setCommunities);

    }
    /**
     * get Color of a community in a timeframe
     * @param strCommunity
     * @param tf
     * @return String
     */
    @Override
    public String getCommunityColor(String strCommunity, TimeFrame tf){
        return icommunityColors.getCommunityColor(strCommunity, tf);
    }
    
    
    @Override
    public Map<String,String> getMapCommunityColors(TimeFrame tf){
        return icommunityColors.getMapCommunityColors(tf);
    }
    
    /**
     * set Color of a community in a timeframe
     * @param strCommunity
     * @param strColor
     * @param tf
     */
    @Override
    public void setCommunityColor(String strCommunity, String strColor, TimeFrame tf){
        icommunityColors.setCommunityColor(strCommunity, strColor, tf);
    }
    
    @Override
    public void resetGlobalCommunityColorMap() {
        icommunityColors.resetGlobalCommunityColorMap();
    }
    /**
     * update the mapCommunityColor timeframe with given map
     * @param tf
     * @param mapCommunityColorMap
     */
    @Override
    public void setCommunityColorMap(TimeFrame tf, Map<String, String> mapCommunityColorMap) {
        icommunityColors.setCommunityColorMap(tf, mapCommunityColorMap);
    }
    
    
    /**
     * update the mapGlobalCommunityColor of graph with given map
     * @param mapGloablCommunityColor 
     */
    @Override
    public void setGlobalCommunityColorMap(Map<String, String> mapGloablCommunityColor) {
        icommunityColors.setGlobalCommunityColorMap(mapGloablCommunityColor);
    }
    
    /**
     * returns a copy of mapGlobalCommunityColors
     * @return Map<String,String>
     */
    @Override
    public Map<String,String> getMapGloablCommunityColor(){
        return icommunityColors.getMapGloablCommunityColor();
    }
    
    /**
     * The maxFileId of any vertex in this graph
     * @return 
     */
    
    @Override
    public int getMaxFileId(){
    int maxFileId = -1;
    for(int id : mapAllVertices.getIds()){
        V v = mapAllVertices.get(id);
        // get File ids for all time frames if exist, take for one ans break since file id will be same for all time frames for a vertex
        List<String> listFileId = 
                new ArrayList<>(v.getUserAttributer().getAttributeValues(MeerkatSystem.FILE_ID).values());
        for(String strfileId : listFileId){
            //System.out.println("File ID : " + strfileId);
            int fileId = Integer.parseInt(strfileId);
            if(fileId > maxFileId){
                maxFileId = fileId;
            }
            break;
        }
    }
    return maxFileId;

}
    /**
     * The id of the dynamicGraph is being assigned by the Project object.
     * @param pintId
     */
    
    @Override
    public void setId(int pintId) {
        this.intDynaGraphId = pintId;
    }
    
    /**
     *
     * @return
     */
    @Override
    public int getId() {
        return this.intDynaGraphId;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String getGraphTitle() {
        return this.strGraphTitle ;        
    }

    /**
     *
     * @param pstrGraphTitle
     */
    @Override
    public void setGraphTitle(String pstrGraphTitle) {
        this.strGraphTitle = pstrGraphTitle ;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String getGraphFile() {
        return this.strGraphFilePathFull ;
    }

    /**
     *
     * @param pstrGraphFile
     */
    @Override
    public void setGraphFile(String pstrGraphFile) {
        this.strGraphFilePathFull = pstrGraphFile ;
    }
    /**
     *
     * @param pstrgraphNewTitle
     */
    @Override
    public void renameGraph(String pstrgraphNewTitle) {
            //in dynamic graph
            //rename graphTitle
            //reset graphFile
            this.setGraphTitle(pstrgraphNewTitle);
            
            String oldFilePath = this.getGraphFile();
            
            Path oldPath = Paths.get(oldFilePath);
            Path directoryPath = oldPath.getParent();
            String fileName = oldPath.getFileName().toString();
            
            
            
            String title = pstrgraphNewTitle;
            String extension = "";
            if (fileName.contains(".")) {
             extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            }
            System.out.println("DynamicGraph.renameGraph() : directoryPath of graph = " + directoryPath.toString());
            String newFilePath = directoryPath.toString() + File.separator + title + extension;
            this.setGraphFile(newFilePath);
    }
    
    /**
     * Adding staticGraph along with its timeframe to the dynamicGraph
     * @param pTimeFrame
     * @param pIGraph
     */
    @Override
    public void addGraph (TimeFrame pTimeFrame, IStaticGraph<V,E> pIGraph) {
        hmpGraphsAtTimeframes.put(pTimeFrame, pIGraph);
        lstTimeFrameOrdering.add(pTimeFrame);
        icommunityColors.addTimeFrame(pTimeFrame);
    }

    /**
     *
     * @param pTimeframe
     * @return
     */
    @Override
    public IStaticGraph<V,E> getGraph(TimeFrame pTimeframe) {
        if (hmpGraphsAtTimeframes.containsKey(pTimeframe)) {
            return hmpGraphsAtTimeframes.get(pTimeframe);
        }
        /**/
        System.err.println(className + ": Timeframe does not exist!");
        return null;
    }
    
    /**
     *
     * @return the ordered list of timeframes 
     */
    @Override
    public List<TimeFrame> getAllTimeFrames() {
        return lstTimeFrameOrdering;
    }
    
    /**
     * 
     * @param tf
     * @return all vertices associated with the timeframe
     */
    @Override
    public List<V> getVertices(TimeFrame tf) {
        List<V> lstVertices = new ArrayList<>();
        /*
        System.out.println("Vertex List Size Time Frame tf = " + tf.toString() + " , " +hmpGraphsAtTimeframes.get(tf) + " , " + hmpGraphsAtTimeframes.get(tf).getAllVertexIds());
        */for (int i : hmpGraphsAtTimeframes.get(tf).getAllVertexIds()) { 
            lstVertices.add(mapAllVertices.get(i));
            /*
            String fileId = mapAllVertices.get(i).getUserAttributer().getAttributeValue(MeerkatSystem.FILE_ID, tf);
            String x = mapAllVertices.get(i).getSystemAttributer().getAttributeValue(MeerkatSystem.X, tf);
            String y = mapAllVertices.get(i).getSystemAttributer().getAttributeValue(MeerkatSystem.Y, tf);
            System.out.println(mapAllVertices.get(i).getId() + " : " + fileId + " :: " + x + ", " + y);
            */
        }
        
        return lstVertices;
    }
    
    /**
     *
     * @param pintVertexID
     * @return
     */
    @Override
    public V getVertex(int pintVertexID) {
        return mapAllVertices.get(pintVertexID);
    }
    
    /**
     *
     * @param tf
     * @return
     */
    @Override
    public V getAnyVertex(TimeFrame tf) {
        
        V v = null;
        //return the first vertex in the hmpGraphsAtTimeframes of the timeframe tf
        for (int i : hmpGraphsAtTimeframes.get(tf).getAllVertexIds()) {
            v = mapAllVertices.get(i);
            break;
        }
        
        return v;
 
    }

    /**
     *
     * @return all vertices in the dynamicGraph
     */
    @Override
    public List<V> getAllVertices() {
        List<V> lst = new ArrayList<>();
        for (int i = 0 ; i < mapAllVertices.size() ; i++) {
            if(mapAllVertices.get(i)!=null)
                lst.add(mapAllVertices.get(i));
        }
        return lst;
    }

    /**
     *
     * @return ids of all vertices in the dynamicGraph
     * TODO : This method returns wrong results now after implementing "remove Vertex, Add Vertex" Feature on 12/06/2017
     */
    @Override
    public List<Integer> getAllVertexIds() {
        List<Integer> lst = new ArrayList<>();
        for (int i = 0 ; i < mapAllVertices.size() ; i++) {
            if(mapAllVertices.get(i)!=null)
                lst.add(i);
        }
        return lst;
    }
        
    /**
     * 
     * @return vertex count over all time frames.
     */
    @Override
    public int getVertexCount() {
        int vertexSize=0;
        for(int i=0;i<mapAllVertices.size();i++)
            if(mapAllVertices.get(i)!=null)
                vertexSize+=1;
        return vertexSize;
    }
    
    /**
     * 
     * @return vertex count in a timeframe.
     * 
     * @param tf
     */
    @Override
    public int getVertexCount(TimeFrame tf) {

        return hmpGraphsAtTimeframes.get(tf).getVertexCount();
        
    }
       
        
    
    
    /**
     * Adds all Vertices in the list to all TimeFrames. 
     * @param phmapVertices
     * @param tf
     */
    @Override
    public void addVertex(List<V> phmapVertices, TimeFrame tf) {
        for (V v : phmapVertices) {
            addVertex(v,tf);
        }
    }

    /**
     * Adds all Vertices in the set to all TimeFrames. 
     * @param psetVertices
     * @param tf 
     */
    @Override
    public void addVertex(Set<V> psetVertices, TimeFrame tf) {
        for (V v : psetVertices) {
            addVertex(v,tf);
        }
    }

    /**
     * Adds pVertex to the specific TimeFrames and adds it to the staticGraph as well.
     * @param pVertex 
     * @param tf 
     */
    @Override
    public void addVertex(V pVertex, TimeFrame tf) {
        if (pVertex.getId() < 0) {
            int id = vertexIdGen.getNextAvailableID();
            //System.out.println(" :: vertex new id = " + id);
            pVertex.setId(id);
            mapAllVertices.add(id, pVertex);
            if (darrGlobalAdjList.get(id) == null) {
               darrGlobalAdjList.add(id, new HashMap<>());
            }
        }
        //System.out.println(" darrGlobalAdjList.get(pVertex.getId()) : " + darrGlobalAdjList.get(pVertex.getId()));
        if (!darrGlobalAdjList.get(pVertex.getId()).containsKey(tf)) {
            darrGlobalAdjList.get((pVertex.getId())).put(tf, new LinkedList<>());
        }
        hmpGraphsAtTimeframes.get(tf).addVertex(pVertex);
        //System.out.println(" \t\t\t\t\t DynamicGraph.addVertex tf = " + tf.getTimeFrameName() + " :: " +this.getGraphTitle());
        //System.out.println(" \t\t\t\t\t DynamicGraph.addVertex mapAllVertices = " + mapAllVertices.getIds());
        //System.out.println(" \t\t\t\t\t DynamicGraph.addVertex tf = " + hmpGraphsAtTimeframes.get(tf));
        //System.out.println(" \t\t\t\t\t DynamicGraph.addVertex hmpGraphsAtTimeframes.get(tf) all vertexIds = " + hmpGraphsAtTimeframes.get(tf).getAllVertexIds());
    }

    /**
     * Remove vertex from all timeframes and also to all staticGraphs.
     * @param pVertex 
     */
    @Override
    public void removeVertex(V pVertex) {
        for (TimeFrame tf : darrGlobalAdjList.get(pVertex.getId()).keySet()) {
            hmpGraphsAtTimeframes.get(tf).removeVertex(pVertex);
        }
        darrGlobalAdjList.remove(pVertex.getId());
        mapAllVertices.remove(pVertex.getId());
    }
    
    /**
     * removes vertex from the specified list of timeframes and 
     * their associated staticGraphs.
     * @param pVertex
     * @param plstTimeFrame
     */
    @Override
    public void removeVertex(V pVertex, List<TimeFrame> plstTimeFrame) {
        for (TimeFrame tf : plstTimeFrame) {
            hmpGraphsAtTimeframes.get(tf).removeVertex(pVertex);
            darrGlobalAdjList.get(pVertex.getId()).remove(tf);
        }
        if (darrGlobalAdjList.get(pVertex.getId()).isEmpty()) {
            mapAllVertices.remove(pVertex.getId());
        }
    }

    /**
     * remove list of vertices from all timeframes and 
     * all associated staticGraphs as well.
     * @param plstVertex
     */
    @Override
    public void removeVertices(List<V> plstVertex) {
        for (V v : plstVertex) {
            removeVertex(v);
        }
    }

    /**
     * returns degree of a node in a specific timeframe.
     * @param v
     * @param pTimeFrame
     * @return
     */
    @Override
    public int getDegree(V v, TimeFrame pTimeFrame) {
        return darrGlobalAdjList.get(v.getId()).get(pTimeFrame).size();
    }
    
    /**
     *
     * @param v
     * @param tf
     * @return
     */
    public int getIndegree(V v, TimeFrame tf) {
        int indegree = 0;
        for (E e : darrGlobalAdjList.get(v.getId()).get(tf)) {
            if (e.getDestination().equals(v)) {
                indegree++;
            }
        }
        return indegree;
    }
    
    /**
     *
     * @param v
     * @param tf
     * @return
     */
    public int getOutDegree(V v, TimeFrame tf) {
        int outdegree = 0;
        for (E e : darrGlobalAdjList.get(v.getId()).get(tf)) {
            if (e.getSource().equals(v)) {
                outdegree++;
            }
        }
        return outdegree;
    }

    /**
     * returns neighbors of a node in a specific timeframe.
     * @param v
     * @param pTimeFrame
     * @return
     */
    @Override
    public List<V> getNeighbors(V v, TimeFrame pTimeFrame) {
        List<V> lstNeighbors = new ArrayList<>();
//        System.out.println("DynamicGraph.getNeighbor() : " + darrGlobalAdjList.get(v.getId()).get(pTimeFrame));
        if (darrGlobalAdjList.get(v.getId()).containsKey(pTimeFrame)) {
            for (E e : darrGlobalAdjList.get(v.getId()).get(pTimeFrame)){
                if (e.getOtherEndPoint(v) != null) {
                    lstNeighbors.add(e.getOtherEndPoint(v));
                }
            }
        }
        return lstNeighbors;
    }
    
    /**
     *
     * @param v
     * @param pTimeFrame
     * @return
     */
    @Override
    public List<V> getIncomingNeighbors(V v, TimeFrame pTimeFrame) {
        List<V> lstInComingNeighs = new ArrayList<>();
        for (E e : darrGlobalAdjList.get(v.getId()).get(pTimeFrame)) {
            if (e.getDestination().equals(v)) {
                lstInComingNeighs.add(e.getSource());
            }
            if (!e.isDirected()) {
                if(e.getSource().equals(v))
                lstInComingNeighs.add(e.getDestination());
            }
        }
        return lstInComingNeighs;
    }

    /**
     *
     * @param v
     * @param pTimeFrame
     * @return
     */
    @Override
    public List<V> getOutgoingNeighbors(V v, TimeFrame pTimeFrame) {
        List<V> lstOutComingNeighs = new ArrayList<>();
        for (E e : darrGlobalAdjList.get(v.getId()).get(pTimeFrame)) {
            if (e.getSource().equals(v)) {
                lstOutComingNeighs.add(e.getDestination());
            }
            if (!e.isDirected()) {
                if(e.getDestination().equals(v))
                lstOutComingNeighs.add(e.getSource());
            }
        }
        return lstOutComingNeighs;
    }

    /**
     *
     * @param v
     * @param pTimeFrame
     * @return
     */
    @Override
    public List<E> getOutgoingEdges(V v, TimeFrame pTimeFrame) {
        List<E> lstOutComingEdges = new ArrayList<>();
        for (E e : darrGlobalAdjList.get(v.getId()).get(pTimeFrame)) {
            if (e.getSource().equals(v)) {
                lstOutComingEdges.add(e);
            }
            if (!e.isDirected()) {
                if(e.getDestination().equals(v))
                lstOutComingEdges.add(e);
            }
        }
        return lstOutComingEdges;
    }

    /**
     *
     * @param v
     * @param pTimeFrame
     * @return
     */
    @Override
    public List<E> getIncomingEdges(V v, TimeFrame pTimeFrame) {
        List<E> lstInComingEdges = new ArrayList<>();
        for (E e : darrGlobalAdjList.get(v.getId()).get(pTimeFrame)) {
            if (e.getDestination().equals(v)) {
                lstInComingEdges.add(e);
            }
            if (!e.isDirected()) {
                if(e.getSource().equals(v))
                lstInComingEdges.add(e);
            }
        }
        return lstInComingEdges;
    }

    /**
     * return all edges of a vertex in a specific timeframe.
     * @param v
     * @param pTimeFrame
     * @return
     */
    @Override
    public List<E> getEdges(V v, TimeFrame pTimeFrame) {
        List<E> lstEdges = new ArrayList<>();
        for (E e : darrGlobalAdjList.get(v.getId()).get(pTimeFrame)) {
            lstEdges.add(e);
        }
        return lstEdges;
    }
    
    
    /**
     *
     * @return the names of all user and system attributes of all vertices.
     */
    @Override
    public Set<String> getVertexAllAttributeNames() {
        Set<String> setAllAttNames = new HashSet<>();
        for (IGraph<V,E> g : hmpGraphsAtTimeframes.values()) {
            setAllAttNames.addAll(g.getVertexAllAttributeNames());
        } 
        return setAllAttNames;
    }

    /**
     *
     * @return only those attributes of a vertex where they only have numeric values.
     */
    @Override
    public Set<String> getVertexNumericalAttributeNames() {
        Set<String> setAllNumAttNames = new HashSet<>();
        for (IGraph<V,E> g : hmpGraphsAtTimeframes.values()) {
            setAllNumAttNames.addAll(g.getVertexNumericalAttributeNames());
        } 
        return setAllNumAttNames;
    }

    /**
     *
     * @return attribute names along with whether it is numeric (true) 
     * or not (false)
     */
    @Override
    public Map<String, Boolean> getVertexAttributeNamesWithType() {
        Map<String, Boolean> mapAllAttsTypes = new HashMap<>();
        for (int i = 0 ; i < mapAllVertices.size() ; i++) {
            mapAllAttsTypes.putAll(mapAllVertices.get(i).getAttributeNamesWithType());
        }
        return mapAllAttsTypes;
    }

    /**
     *
     * @param pintEdgeID
     * @return
     */
    @Override
    public E getEdge(int pintEdgeID) {
        return mapAllEdges.get(pintEdgeID);
    }
    
    /**
     *
     * @param tf
     * @return all edges in the specified timeframe.
     */
    @Override
    public List<E> getEdges(TimeFrame tf) {
        
        List<E> lstEdges = new ArrayList<>();
        for (int i  : hmpGraphsAtTimeframes.get(tf).getAllEdgeIds()) {
            //System.out.println("DynamicGraph.getEdges() : " + i + "(" + mapAllEdges.get(i) + ")");
            lstEdges.add(mapAllEdges.get(i));
        }
        return lstEdges;
        
        
    }

    /**
     * 
     * @return all edges in all the timeframes.
     */
    @Override
    public List<E> getAllEdges() {
        List<E> lstReturn = new ArrayList<>() ;
        for ( int i = 0 ; i < mapAllEdges.size() ; i++) {
            if(mapAllEdges.get(i)!=null)
                lstReturn.add(mapAllEdges.get(i));
        }
        return lstReturn;
    }

    /**
     *
     * @return the MeerkatId of all edges in all the timeframes
     */
    @Override
    public List<Integer> getAllEdgeIds() {
        List<Integer> lstEdgeIds = new ArrayList<>();
        for (int i  = 0 ; i < mapAllEdges.size() ; i++) {
            if(mapAllEdges.get(i)!=null)
                lstEdgeIds.add(i);
        }
        return lstEdgeIds;
    }

    /**
     * Add edge in the specified timeframes.
     * @param pEdge
     * @param tf 
     */
    @Override
    public void addEdge(E pEdge, TimeFrame tf) {
        
        // If The Edge ID is not initialized yet, initalialize it
        if (pEdge.getId() < 0) {
            int id = edgeIdGen.getNextAvailableID();
            pEdge.setId(id);
            mapAllEdges.add(id, pEdge);
            //System.out.println("\t\t\t\t\t\t====***************** DynamicGraph.addEdge() edge (id, mapAllEdges.size) = " + id + ", " +  mapAllEdges.size());
        }
        // Adding to the adjacent list
        if (!darrGlobalAdjList.get(pEdge.getSource().getId()).get(tf).contains(pEdge)) {
            darrGlobalAdjList.get(pEdge.getSource().getId()).get(tf).add(pEdge);
            //System.out.println("\t\t\t\t\t\t====***************** DynamicGraph.addEdge() ading to darrGlobalAdjList ");
            if(!pEdge.isDirected() &&
                    !darrGlobalAdjList.get(pEdge.getDestination().getId()).get(tf).contains(pEdge)) {
                darrGlobalAdjList.get(pEdge.getDestination().getId()).get(tf).add(pEdge);
            }
        }
        
        hmpGraphsAtTimeframes.get(tf).addEdge(pEdge);
        
    }

    /**
     * Remove an edge from all timeframes.
     * @param pEdge 
     */
    @Override
    public void removeEdge(E pEdge) {
        for (TimeFrame tf : lstTimeFrameOrdering) {
            if (darrGlobalAdjList.get(pEdge.getSource().getId()).get(tf).contains(pEdge)) {
                hmpGraphsAtTimeframes.get(tf).removeEdge(pEdge);
                darrGlobalAdjList.get(pEdge.getSource().getId()).get(tf).remove(pEdge);
                darrGlobalAdjList.get(pEdge.getDestination().getId()).get(tf).remove(pEdge);
            }
        }
        mapAllEdges.remove(pEdge.getId());
    }
    
    /**
     * Remove all edges in the list from all timeframes if present.
     * @param plstEdges 
     */
    @Override
    public void removeEdge(List<E> plstEdges) {
        for (E e : plstEdges) {
            removeEdge(e);
        }
    }

    /**
     * removes the edge from the specific timeframe.
     * @param pEdge
     * @param pTimeFrame 
     */
    @Override
    public void removeEdge(E pEdge, TimeFrame pTimeFrame) {
        hmpGraphsAtTimeframes.get(pTimeFrame).removeEdge(pEdge);

        darrGlobalAdjList.get(pEdge.getSource().getId()).get(pTimeFrame).remove(pEdge);
        darrGlobalAdjList.get(pEdge.getDestination().getId()).get(pTimeFrame).remove(pEdge);
        
        boolean otherInstances = false;
        for (TimeFrame tf : darrGlobalAdjList.get(pEdge.getSource().getId()).keySet()) {
            if (darrGlobalAdjList.get(pEdge.getSource().getId()).get(tf).contains(pEdge)) {
                otherInstances = true;
                break;
            }
        }
        
        if (!otherInstances) {
            mapAllEdges.remove(pEdge.getId());
        }
    }

    /**
     * Removes all edges from all timeframes. the graph will have no edge after
     * calling this method.
     */
    @Override
    public void clearEdges() {
        /* remove edges from all graphs */
        for (TimeFrame tf : lstTimeFrameOrdering) {
            hmpGraphsAtTimeframes.get(tf).clearEdges();
        }
        
        /* empty adjacency list */
        for (int i = 0 ; i < darrGlobalAdjList.size() ; i++) {
            for (TimeFrame tf : darrGlobalAdjList.get(i).keySet()) {
                darrGlobalAdjList.get(i).get(tf).clear();
            }
        }
        /* delete all Edge Objects */
        mapAllEdges.clear();
    }

    /**
     * Removes all edge in a specific timeframe.
     * @param ptf 
     */
    @Override
    public void clearEdges(TimeFrame ptf) {
        List<Integer> lstEdgeIds = hmpGraphsAtTimeframes.get(ptf).getAllEdgeIds();
        /* remove edges from all graphs */
        hmpGraphsAtTimeframes.get(ptf).clearEdges();
        
        /* empty adjacency list */
        for (int i = 0 ; i < darrGlobalAdjList.size() ; i++) {
            darrGlobalAdjList.get(i).get(ptf).clear();
        }
        /* delete all Edge Objects */
        for (int id : lstEdgeIds) {
            mapAllEdges.remove(id);
        }
    }
    
    /**
     * 
     * @param e
     * @param pTimeFrame
     * @return Neighbors of and edge in a specific timeframe.
     */
    @Override
    public List<E> getNeighbors(E e, TimeFrame pTimeFrame) {
        List<E> lstNeighEdges = new ArrayList<>();
        for (E ne : darrGlobalAdjList.get(e.getSource().getId()).get(pTimeFrame)) {
            lstNeighEdges.add(ne);
        }
        return lstNeighEdges;
    }

    /**
     *
     * @return the maximum MeerkatId of edges in all the timeframes.
     */
    @Override
    public int getMaxEdgeId() {
        return mapAllEdges.getMaxIndex();
    }

    /**
     * 
     * @return all attribute of all edges over all timeframes.
     */
    @Override
    public Set<String> getEdgeAttributeNames() {
        Set<String> setEAttNames = new HashSet<>();
        for (int i = 0 ; i < mapAllEdges.size() ; i++) {
            setEAttNames.addAll(mapAllEdges.get(i).getAttributeNames());
        }
        return setEAttNames;
    }

    /**
     * 
     * @return all numerical attributes of edge over all timeframes.
     */
    @Override
    public Set<String> getEdgeNumericalAttributeNames() {
        Set<String> setENumAttNames = new HashSet<>();
        for (int i = 0 ; i < mapAllEdges.size() ; i++) {
            setENumAttNames.addAll(mapAllEdges.get(i).getNumericAttributeNames());
        }
        return setENumAttNames;
    }

    /**
     * 
     * @return all edge attributes in all timeframes along with a boolean
     *          that shows if the attribute is Numerical (TRUE) or not (FALSE).
     */
    @Override
    public Map<String, Boolean> getEdgeAttributeNamesWithType() {
        Map<String, Boolean> mapEAttTypes = new HashMap<>();
        for (int i = 0 ; i < mapAllEdges.size() ; i++) {
            mapEAttTypes.putAll(mapAllEdges.get(i).getAttributeNamesWithType());
        }
        return mapEAttTypes;
    }

    /**
     * 
     * @param source
     * @param destination
     * @return edges between source & destination of all types and
     *          over all timeframes.
     */
    @Override
    public Set<E> findEdge(V source, V destination) {
        Set<E> setEdges = new HashSet<>();
        for(TimeFrame tf : darrGlobalAdjList.get(source.getId()).keySet()) {
            for (E e : darrGlobalAdjList.get(source.getId()).get(tf)) {
                if (e.getDestination().equals(destination)) {
                    setEdges.add(e);
                }
            }
        }
        return setEdges;
    }
    
    @Override
     public Set<int[]> getNonExistingEdges(TimeFrame timeFrame) {
        Set<int[]> setEdgePairs = new HashSet<>();
        
        List<V> listVertices = this.getVertices(timeFrame);
        Object[] verticesArray =  listVertices.toArray();
        //int edgecount = 0;
        for(int i = 0; i < verticesArray.length; i++){
            for(int j = i; j < verticesArray.length; j++){
                V v1 = (V)verticesArray[i];
                V v2 = (V)verticesArray[j];
                E edge = this.findEdge(v1, v2, timeFrame);
                if(i!=j && edge == null){
                    //edgecount++;
                    //System.out.println("i,j " + i + ", " + j);
                    
                    int[] edgePair = new int[2];
                    if(v1.getId() < v2.getId()){
                        edgePair[0] = v1.getId();
                        edgePair[1] = v2.getId();
                    }else{
                        edgePair[0] = v2.getId();
                        edgePair[1] = v1.getId();
                    }
                    
                    setEdgePairs.add(edgePair);
                    
                }
            }
        }
        //System.out.println("edge count = " + edgecount);
        return setEdgePairs;
    }
     
     @Override
     public Set<V> getCommonNeighbors(V u, V v, TimeFrame tf){
     
         List<V> uNeighbors = this.getNeighbors(u, tf);
         List<V> vNeighbors = this.getNeighbors(v, tf);
         
         Set<V> uNeighborSet = new HashSet<>(uNeighbors);
         Set<V> vNeighborSet = new HashSet<>(vNeighbors);
         
         uNeighborSet.retainAll(vNeighborSet);
         
         return uNeighborSet;
         
     }
    
    /**
     *
     * @param source
     * @param destination
     * @param tf
     * @return returns edges between source and destination in a specific time frame.
     */
    @Override
    public E findEdge(V source, V destination, TimeFrame tf) {
//        System.out.println(destination.getId());
        for (E e : darrGlobalAdjList.get(source.getId()).get(tf)) {
//            System.out.println("---> " + e.getOtherEndPoint(source).getId());
            if (e.getOtherEndPoint(source).equals(destination)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 
     * @return  Edge count over all timeframes.
     */
    @Override
    public int getEdgeCount() {
        int edgeSize=0;
        for(int i=0;i<mapAllEdges.size();i++)
            if(mapAllEdges.get(i)!=null)
                edgeSize+=1;
        return edgeSize;
    }

    /**
     * 
     * @return  the latest time user has altered the graph.
     */
    @Override
    public Date getLastChangeTime() {
        Date dtMax = hmpGraphsAtTimeframes.get(lstTimeFrameOrdering.get(0)).getLastChangeTime();
        for (TimeFrame tf : lstTimeFrameOrdering) {
            if (dtMax.before(hmpGraphsAtTimeframes.get(tf).getLastChangeTime())) {
                dtMax = hmpGraphsAtTimeframes.get(tf).getLastChangeTime();
            }
        }
        return dtMax;
    }

    /**
     *
     * @return the list of timeframes in the reverse order.
     */
    public List<TimeFrame> getAllTimeFramesInReverseOrder() {
        List<TimeFrame> lstLargeToSmall = new ArrayList<>();
        
        for (int i = lstTimeFrameOrdering.size() - 1 ; i >= 0 ; i--) {
            lstLargeToSmall.add(lstTimeFrameOrdering.get(i));
        }
        return lstLargeToSmall;
    }
    

    /**
     *
     * @param snapshotIdx
     * @return the list of timeframes greater than the specified timeframe.
     */
    public List<TimeFrame> getGreaterTimeFrames(TimeFrame snapshotIdx) {
        List<TimeFrame> lstGreater = new ArrayList<>();
        
        
        boolean greater = false;
        for (TimeFrame tf : lstTimeFrameOrdering) {
            if (greater) {
                lstGreater.add(tf);
            }
            if (tf.equals(snapshotIdx)) {
                greater = true;
            }
        }
        return lstGreater;
    }

    /**
     *
     * @param snapshotIdx
     * @return the list of timeframes smaller than the specified timeframes.
     */
    public Iterable<TimeFrame> getSmallerTimeFrames(TimeFrame snapshotIdx) {
        List<TimeFrame> lstSmaller = new ArrayList<>();
        
        
        boolean greater = false;
        for (TimeFrame tf : lstTimeFrameOrdering) {
            if (tf.equals(snapshotIdx)) {
                greater = true;
            }
            if (!greater) {
                lstSmaller.add(tf);
            }
        }
        return lstSmaller;
    }

    /**
     * checks if the specified timeframe is the first timeframe or not.
     * @param snapshotIndex
     * @return 
     */
    public boolean isFirstTimeFrame(TimeFrame snapshotIndex) {
        if (snapshotIndex.equals(lstTimeFrameOrdering.get(0))) {
            return true;
        }
        return false;
    }

    /**
     * checks if the specified timeframe is the last timeframe or not.
     * @param snapshotIdx
     * @return
     */
    public boolean isLastTimeFrame(TimeFrame snapshotIdx) {
        if (snapshotIdx.equals(lstTimeFrameOrdering
                .get(lstTimeFrameOrdering.size() -1))) {
            return true;
        }
        return false;
    }
    /**
     * updates the SYS:X, SYS:Y values of all vertices in all time frame from the coordinates received from UI
     * 
     * @param pmapGraphTimeFramesVerticesLocation
     * 
     */
    @Override
    public void updateVertexLocations(Map<Integer, Map<Integer, Double[]>> pmapGraphTimeFramesVerticesLocation ){
        int tf = 0;
        for (TimeFrame timeframe : getAllTimeFrames()) {
                Map<Integer, Double[]> mapVertexLocation = pmapGraphTimeFramesVerticesLocation.get(tf++);
                updateVertexLocationsInTimeFrame(timeframe, mapVertexLocation);
            }   
    }
    
    /**
     * Updates the color for a list of Vertices in a graph
     * @param ptf
     * @param pmapVertexColors 
     * @return true if successful
     */
    @Override
    public boolean updateVertexColors(TimeFrame ptf, Map<Integer, String> pmapVertexColors){
        
        for(int vertexId : pmapVertexColors.keySet()){
            this.getGraph(ptf).getVertex(vertexId).updateColor(pmapVertexColors.get(vertexId), ptf);
        }
        
        return true;
    }
    
    /**
     * Updates the color for a list of Vertices in a graph
     * @param ptf
     * @param pstrColor
     * @param plstVertexIDs
     * @return true if successful
     */
    @Override
    public boolean updateVertexColors(TimeFrame ptf, String pstrColor, List<Integer> plstVertexIDs){
        
        for(int vertexId : plstVertexIDs){
            this.getGraph(ptf).getVertex(vertexId).updateColor(pstrColor, ptf);
        }
        
        return true;
    }
    
    
    /**
     * Updates the color for a list of Edges in a graph
     * @param ptf
     * @param pmapEdgeColors 
     * @return true if successful
     */
    @Override
    public boolean updateEdgeColors(TimeFrame ptf, Map<Integer, String> pmapEdgeColors){
        
        for(int edgeID : pmapEdgeColors.keySet()){
            this.getGraph(ptf).getEdge(edgeID).updateColor(pmapEdgeColors.get(edgeID), ptf);
        }
        
        return true;
    }
    
    /**
     * Updates the color for a list of Edges in a graph
     * @param ptf
     * @param pstrColor
     * @param plstEdgeIDs
     * @return true if successful
     */
    @Override
    public boolean updateEdgeColors(TimeFrame ptf, String pstrColor, List<Integer> plstEdgeIDs){
        
        for(int edgeID : plstEdgeIDs){
            this.getGraph(ptf).getEdge(edgeID).updateColor(pstrColor, ptf);
        }
        
        return true;
    }
    
    /**
     * Updates the color for a list of Edges in a graph
     * @param ptf
     * @param pmapVertexIconURLs 
     * @return true if successful
     */
    @Override
    public boolean updateVertexIconURLs(TimeFrame ptf, Map<Integer, String> pmapVertexIconURLs){
        for(int vertexId : pmapVertexIconURLs.keySet()){
            this.getGraph(ptf).getVertex(vertexId).updateIconURL(pmapVertexIconURLs.get(vertexId), ptf);
        }
        return true;
    }
    
    /**
     * Updates the list of Vertices in a graph with the same type icon url
     * @param ptf
     * @param pstrIconURL
     * @param plstVertexIDs
     * @return true if successful
     */
    @Override
    public boolean updateVertexIconURLs(TimeFrame ptf, String pstrIconURL, List<Integer> plstVertexIDs){
        for(int vertexId : plstVertexIDs){
            this.getGraph(ptf).getVertex(vertexId).updateIconURL(pstrIconURL, ptf);
        }
        return true;
    }
    
    /**
     * updates the SYS:X, SYS:Y values of all vertices in a time frame from the coordinates received in the map
     * @param timeframe
     * @param pmapVertexLocation
     */
    @Override
    public void updateVertexLocationsInTimeFrame(TimeFrame timeframe, Map<Integer, Double[]> pmapVertexLocation ){
        
        // find minX,maxX and minY, maxY of the co-ordinates supplied from UI 
        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        // System.out.println("MeerkatLogic.DynamicGraph.updateVertexLocationsInTimeFrame() : updating timeframe = " + timeframe);
        Iterator<Map.Entry<Integer, Double[]>> entries = pmapVertexLocation.entrySet().iterator();
        
        while (entries.hasNext()) {
            
            Map.Entry<Integer, Double[]> entry = entries.next();
            
            double xCoordinate = entry.getValue()[0];
            double yCoordinate = entry.getValue()[1];
            if(xCoordinate < minX){
                minX = xCoordinate;
            }
            if(xCoordinate > maxX){
                maxX = xCoordinate;
            }
            if(yCoordinate < minY){
                minY = yCoordinate;
            }
            if(yCoordinate > maxY){
                maxY = yCoordinate;
            }
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        // do normalization and translation of coordinates here
        double width = maxX - minX;
        double height = maxY - minY;
        
        entries = pmapVertexLocation.entrySet().iterator();
        
        while (entries.hasNext()) {
            Map.Entry<Integer, Double[]> entry = entries.next();
            double xCoordinate = entry.getValue()[0];
            double yCoordinate = entry.getValue()[1];
            if(width!=0.0)
                xCoordinate = (xCoordinate - minX)/width;
            else
                xCoordinate = 0.5; //temporary fix if the graph has a single vertex
            
            if(height!=0.0)
                yCoordinate = (yCoordinate - minY)/height;
            else
                yCoordinate = 0.5; //temporary fix if the graph has a single vertex
            
            int id = entry.getKey();
            
            getVertex(id).updateXYPosition(xCoordinate, yCoordinate, timeframe);
            // getVertex(id).getSystemAttributer().addAttributeValue(MeerkatSystem.X, xCoordinate+"", new Date(), timeframe);
            // getVertex(id).getSystemAttributer().addAttributeValue(MeerkatSystem.Y, yCoordinate+"", new Date(), timeframe);
        }
    }

    /**
     * returns an edge if edge exists between sourceVertex and destinationVertex in any time frame of dynamic graph
     * @param pintSourceVertexID
     * @param pintDestinationVertexID
     */
    @Override
    public E getEdgeIfExists(int pintSourceVertexID, int pintDestinationVertexID) {
        V vertexFrom = getVertex(pintSourceVertexID);
        V vertexTo = getVertex(pintDestinationVertexID);
        for(TimeFrame tf : darrGlobalAdjList.get(pintSourceVertexID).keySet()) {
           for (E e : darrGlobalAdjList.get(pintSourceVertexID).get(tf)) {          
                if (e.getOtherEndPoint(vertexFrom).equals(vertexTo)) {
                    return e;
                }
            }   
        }
        return null;
    }
    
    /**
     * Updates the color of all the communities on loading.
     * @author : sankalp
     * @since : 2018-01-25
     * 
     */
    @Override
    public void calculateCommunityColorsOnLoading() {
        if (getVertexAllAttributeNames().contains(MeerkatSystem.COMMUNITY)){
            for(TimeFrame tf : getAllTimeFrames()){
                Set<String> communities = calculateCommunityOnLoading(tf);
                if(communities.size()>0)
                    calculateCommunityColors(communities, tf);
            }
        }
    }
    
    /**
     * calculates different communities on graph load.
     * @author : sankalp
     * @since : 2018-01-25
     * @param ptf 
     * @return Set<String> communities
     */
    @Override
    public Set<String> calculateCommunityOnLoading(TimeFrame ptf) {
        Set<String> communities = new HashSet<>();
        
        for (IVertex v : getAllVertices()) {
            if (v.getSystemAttributer().containsAttributeAtTimeFrame(MeerkatSystem.COMMUNITY, ptf)) {
                String com = v.getSystemAttributer()
                        .getAttributeValue(MeerkatSystem.COMMUNITY, ptf);

                String[] singleCom = com.split(",");
                String finalComm = singleCom[0];

                communities.add(finalComm);

            }
        }
        
        return communities;        
    }
    

}