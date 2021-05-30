/*
 * To updateTime this license header, choose License Headers in Project Properties.
 * To updateTime this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.graph.impl;

import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import datastructure.general.DynamicArray;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Matt Gallivan
 *
 */
/**
 *  Class Name      : StaticGraph
  Created Date    : 
  Description     : StaticGraph Implementation of the IGraph
  Version         : 2.0
 *  @author         : Matt Gallivan (Version 2.0 by Afra)
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-11-24       Afra           - Added the static attribute TimeFrame for
 *                                  recording the timeframe of the staticgraph.
 *                                  - Added two new constructors to set the 
 *                                  TimeFrame as well. If the constructors 
 *                                  without timeframes are being used the
 *                                  timeframe of the graph is set to the DEFAULT.
 *  2015-11-13       Afra           - Changed the structure of adjacency list
 *                                  to contains vertex relations based on type
 *                                  of the edges.
 *                                  - added clearEdges() method
 *                                  - updated removeEdge and addEdge because when
 *                                  updating adjacency lists,we need to take
 *                                  edge type into account.
 *  2015-10-19       Afra           - Removed useless and UI related methods.
 *                                  - Removed attribute Name from constructors &
 *                                  class.
 *                                  - Removed algorithm computation such as:
 *  2015-09-18      Talat           Adding implementation of getMinMaxVertexDegree()
 *  2015-08-27      Talat           Adding implementation of getAllEdgeAsVertexIDs()
 * @param <V>
 * @param <E>
 * 
*/
public class StaticGraph<V extends IVertex, E extends IEdge<V>> 
                                    implements IStaticGraph<V,E>{

    private int intGraphId;
    

    /* not using it since it is result of an algorithm not related to DS. */
    /*
    private final Map<Integer, IGraph<V,E>> communities = 
                    new HashMap<Integer, IGraph<V,E>>();
    */
    
    private DynamicArray<E> darrayEdges;
    private DynamicArray<V> darrayVertices;
    
    private Date changeTime;

    /**
     *
     */
    public static TimeFrame timeframe = TimeFrame.DEFAULT_TIMEFRAME;

    @Override
    public TimeFrame getTimeframe() {
        return this.timeframe;
    }

    @Override
    public void setTimeframe(TimeFrame timeframe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    


    /**
     *
     */
    public enum Type {

        /**
         *
         */
        DIRECTED,

        /**
         *
         */
        UNDIRECTED,

        /**
         *
         */
        MIXED
    }
    
    //private Type typeGraph;

    /**
     *
     * @param pintVertexCount
     * @param pintEdgeCount
     */
    public StaticGraph(int pintVertexCount, int pintEdgeCount) {
        darrayVertices = new DynamicArray<>(pintVertexCount);
        darrayEdges = new DynamicArray<>(pintEdgeCount);
        updateTime();
    }
    
     /**
    *
    * @param pintVertexCount
    * @param pintEdgeCount
    * @param ptf
    */
   public StaticGraph(int pintVertexCount, int pintEdgeCount, TimeFrame ptf) {
       
       this.timeframe = ptf;      
       updateTime();
   }
    

    /**
     *
     * @param pintId
     */
    @Override
    public void setId(int pintId) {
        this.intGraphId = pintId;
    }
    
    /**
     *
     * @return
     */
    @Override
    public int getId() {
        return this.intGraphId;
    }

    /**
     *
     * @param pintVertexId
     * @return
     */
    @Override
    public V getVertex(int pintVertexId) {
        return darrayVertices.get(pintVertexId);
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<V> getAllVertices() {
        return darrayVertices.getObjects();
    }

    /**
     *
     * @return
     */
    @Override
    public List<Integer> getAllVertexIds() {
        return darrayVertices.getIds();
    }

    /**
     *
     * @param pVertex
     */
    @Override
    public void addVertex(V pVertex) {
        darrayVertices.add(pVertex.getId(), pVertex);
        //System.out.println("  \t\t\t\t\t   StaticGraph.addvertex() tf = " + " vertedAdded = " + pVertex.getId());
        updateTime();
    }

    /**
     *
     * @param plstVertices
     */
    @Override
    public void addVertices(List<V> plstVertices) {
        for (V v : plstVertices) {
                addVertex(v);
        }
    }

    /**
     *
     * @param phmapVertices
     */
    @Override
    public void addVertices(Set<V> phmapVertices) {
        for (V v : phmapVertices) {
                addVertex(v);
        }
    }	
    
    /**
     *
     * @param pVertex
     */
    @Override
    public void removeVertex(V pVertex) {
        darrayVertices.remove(pVertex.getId());
        updateTime();
    }

    /**
     *
     * @param plstVertex
     */
    @Override
    public void removeVertices(List<V> plstVertex) {
        for (V v : plstVertex) {
            removeVertex(v);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Set<String> getVertexAllAttributeNames() {
        Set<String> vertexAttNames = new HashSet<>();
        
        for (int i = 0 ; i < darrayVertices.size() ; i++) {
            V v = darrayVertices.get(i);
            if (v != null) {
                vertexAttNames.addAll(v.getUserAttributer().getAttributeNames());
                vertexAttNames.addAll(v.getSystemAttributer().getAttributeNames());

                
            }
        }
        return vertexAttNames;
    }

    /**
     *
     * @return
     */
    @Override
    public Set<String> getVertexNumericalAttributeNames() {
        HashSet<String> numericalAtts = new HashSet<>();
        for (int i = 0 ; i < darrayVertices.size(); i++) {
            V v = darrayVertices.get(i);
            if (v != null) {
                numericalAtts.addAll(v.getNumericAttributeNames());
            }
        }
        return numericalAtts;
    }
    
    
    /**
     *  Method Name     : getVertexAttributeNamesWithType
     *  Created Date    : 2015-09-22
     *  Description     : Returns a map of all Attribute Names of a Vertex and 
     *                      its type (true for numeric and false for non-numeric)
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return Map<String, Boolean>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    @Override
    public Map<String, Boolean> getVertexAttributeNamesWithType() {
        Map<String, Boolean> hmapAttributeNameWithType = new HashMap<>();
        for (int i = 0 ; i < darrayVertices.size(); i++) {
            V v = darrayVertices.get(i);
            if (v != null) {
                hmapAttributeNameWithType.putAll(v.getAttributeNamesWithType());
            }
        }
        return hmapAttributeNameWithType;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Integer> getAllEdgeIds() {
        return darrayEdges.getIds();
        /*
        List<Integer> lst = new ArrayList<>();
        for ( int i = 0 ; i < darrayEdges.size(); i++ ) {
//            System.out.println("StaticGraph.getAllEdgeIds() : darray element i = " + darrayEdges.get(i));
            if (darrayEdges.get(i) != null) {
                lst.add(i);
            }
        }
        return lst;
        */
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<E> getAllEdges() {
        return darrayEdges.getObjects();
    }

    /**
     * add an edge if its source vertex and destination vertex exist in static graph
     * @param pEdge
     */
    @Override
    public boolean addEdge(E pEdge) {
        
        int vertex1Id; 
        int vertex2Id;
        try{
            vertex1Id = pEdge.getSource().getId();
            vertex2Id = pEdge.getDestination().getId();
        }catch(Exception e){
            return false;
        }
        if(this.getAllVertexIds().contains(vertex1Id) && this.getAllVertexIds().contains(vertex2Id)){
            darrayEdges.add(pEdge.getId(), pEdge);
            //System.out.println("\t\t\t\t\t\t\t\t\t *****_______________________________________StaticGraph.addEdge() : " +"NoOfedges " + getEdgeCount() + "  ---  list of nodes = " + this.getAllEdgeIds());
            updateTime();
            return true;
        }else{
            return false;
        }
    }
    
    /**
     *
     * @param plstEdges
     */
    @Override
    public void addEdges(List<E> plstEdges) {
        plstEdges.stream().forEach((e) -> {
            addEdge(e);
        });
    }

    /**
     *
     * @param pintEdgeID
     * @return
     */
    @Override
    public E getEdge(int pintEdgeID) {
        return darrayEdges.get(pintEdgeID);
    }

    /**
     *
     * @param pEdge
     */
    @Override
    public void removeEdge(E pEdge) {
        darrayEdges.remove(pEdge.getId());
        updateTime();
    }

    /**
     *
     * @param plstEdges
     */
    @Override
    public void removeEdge(List<E> plstEdges) {
        plstEdges.stream().forEach((e) -> {
            removeEdge(e);
        });
    }

    /**
     *
     */
    @Override
    public void clearEdges() {
        darrayEdges.clear();
        updateTime();
    }
    
    /**
     *
     * @return
     */
    @Override
    public int getMaxEdgeId() {
        return darrayEdges.getMaxIndex();
    }

    /**
     *
     * @return
     */
    @Override
    public Set<String> getEdgeUserAttributeNames() {
        Set<String> edgeAttNames = new HashSet<>();
        for (int i = 0 ; i < darrayEdges.size() ; i ++) {
            E e = darrayEdges.get(i);
            edgeAttNames.addAll(e.getUserAttributer().getAttributeNames());
        }
        return edgeAttNames;
    }

    /**
     *
     * @return
     */
    @Override
    public Set<String> getEdgeNumericalAttributeNames() {
        HashSet<String> numericalAtts = new HashSet<>();
        for (int i = 0 ; i < darrayEdges.size() ; i ++) {
            E e = darrayEdges.get(i);
            numericalAtts.addAll(e.getNumericAttributeNames());
        }
        return numericalAtts;
    }
    
    /**
     *  Method Name     : getEdgeAttributeNamesWithType
     *  Created Date    : 2015-09-22
     *  Description     : Returns a map of all Attribute Names of an Edge and 
     *                      its type (true for numeric and false for non-numeric)
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return Map<String, Boolean>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    @Override
    public Map<String, Boolean> getEdgeAttributeNamesWithType() {
        Map<String, Boolean> hmapAttributeNameWithType = new HashMap<>();
        for (int i = 0 ; i < darrayEdges.size() ; i ++) {
            E e = darrayEdges.get(i);
            
            if (e != null) {
                hmapAttributeNameWithType.putAll(e.getAttributeNamesWithType());
            }
        }
        return hmapAttributeNameWithType;
    }

    /*
    @Override
    public List<Integer[]> getAllEdgeAsVertexIDs() {
        List<Integer[]> lstEdgesAsVertexIDs = new ArrayList<>();
        for (int i = 0 ; i < darrayEdges.size() ; i ++) {
            E e = darrayEdges.get(i);
            lstEdgesAsVertexIDs.add(new Integer[] 
            {e.getSource().getId(), 
                e.getDestination().getId()});
        }
        return lstEdgesAsVertexIDs;
    }
    
   
    @Override
    public List<String> getAllEdgeAsVertexIDString(String pstrDelimiter) {
        List<String> lstEdgesAsStrings = new ArrayList<>();
        for (int i = 0 ; i < darrayEdges.size() ; i ++) {
            E e = darrayEdges.get(i);
            lstEdgesAsStrings.add(e.getSource().getId()+
                pstrDelimiter+e.getDestination().getId());
        }
        return lstEdgesAsStrings;
    }
    */
    /**
     *
     * @return
     */
    @Override
    public int getVertexCount() {
        
        return getAllVertexIds().size(); 
        
        
    }

    /**
     *
     * @return
     */
    @Override
    public int getEdgeCount() {
        return getAllEdgeIds().size();
    }



    /**
     *
     * @return
     */
    @Override
    public Date getLastChangeTime() {
        return changeTime;
    }

    private void updateTime() {
        changeTime = new Date();
    }
    
    
}
