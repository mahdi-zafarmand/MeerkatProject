/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.filter;

import config.FilteringOperators;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.graph.impl.DynamicGraph;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author aabnar
 * EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-08-23      Abhi            Modified method filterByVertexEdgeAttributes() to process VertexFilters and EdgeFilters. Combine Filtered vertices on both type of filters
    
 */
public class GraphFilter {

    /**
     *
     * @param <V>
     * @param <E>
     * @param pIGraph
     * @param tf
     * @param plstVertices
     * @return
     */
    public static <V extends IVertex, E extends IEdge<V>>
            IDynamicGraph<V, E> filterByVList(IDynamicGraph<V, E> pIGraph,
                    TimeFrame tf,
                    List<V> plstVertices) {

        List<E> edges = new LinkedList<>();
        for (int i = 0; i < plstVertices.size() - 1; i++) {
            for (int j = i + 1; j < plstVertices.size(); j++) {
                edges.add(pIGraph.findEdge(plstVertices.get(i),
                        plstVertices.get(j), tf));
            }
        }

        IDynamicGraph<V, E> istatG
                = new DynamicGraph<>(plstVertices.size(), edges.size());
        for (V v : plstVertices) {
            istatG.addVertex(v, tf);
        }

        for (E e : edges) {
            istatG.addEdge(e, tf);
        }

        return istatG;
    }

    /**
     *
     * @param <V>
     * @param <E>
     * @param pIGraph
     * @param tf
     * @param plstEdges
     * @return
     */
    public static <V extends IVertex, E extends IEdge<V>>
            IDynamicGraph<V, E> filterByEList(IDynamicGraph<V, E> pIGraph,
                    TimeFrame tf,
                    List<E> plstEdges) {

        Set<V> setV = new HashSet<>();

        for (E e : plstEdges) {
            setV.add(e.getSource());
            setV.add(e.getDestination());

        }
        IDynamicGraph<V, E> istatG = new DynamicGraph(setV.size(), plstEdges.size());

        for (E e : plstEdges) {
            istatG.addVertex(e.getSource(), tf);
            istatG.addVertex(e.getDestination(), tf);
            istatG.addEdge(e, tf);
        }
        return istatG;
    }

    /**
     * MethodName: filterByVertexAttributes
     * Description: filters graph vertices based on filtering criteria 
     * given by the user
     * Version: 1.0
     * Author: Afra
     * 
     * @param <V> : Vertex type (a class implementing IVertex)
     * @param <E>
     * @param pDynaGraph
     * @param tf
     * @param arrstrVertexFilters: 2D array of String where:
     *          String[i][0] = The ith filter Attribute Name 
     *          String[i][1] = The ith filter Operator
     *          String[i][2] = The ith filter value
     * 
     * @return List<V> : a list of vertices satisfy the filters.
     */
    public static <V extends IVertex, E extends IEdge<V>> List<V> 
        filterByVertexAttributes(
            IDynamicGraph<V,E> pDynaGraph,
            TimeFrame tf,
            String[][] arrstrVertexFilters) {
            
        List<V> lstFiltered = new LinkedList<>();
        
        // Parsing
        
            
        for (V v : pDynaGraph.getVertices(tf)) {
            boolean blnSatisfy = false;
            for (int i = 0 ; i < arrstrVertexFilters.length ; i++) {
                String strAttName = arrstrVertexFilters[i][0];
                String strOperator = arrstrVertexFilters[i][1];
                String strFilterValue = null;
                if (arrstrVertexFilters[i].length == 3) {
                    strFilterValue = arrstrVertexFilters[i][2];
                }
                String strVAttValue = null;
                if (v.getUserAttributer().getAttributeNames()
                        .contains(strAttName)) {
                    strVAttValue = v.getUserAttributer()
                            .getAttributeValue(strAttName, tf);
                } else if (v.getSystemAttributer().getAttributeNames()
                        .contains(strAttName)) {
                    strVAttValue = v.getSystemAttributer()
                            .getAttributeValue(strAttName, tf);
                }
                //System.out.println(v.toString());
                //System.out.println(strOperator + " : "+ strVAttValue +" (strAttName) " + strAttName + ": " + strFilterValue);
                blnSatisfy = FilteringOperators
                        .compare(strOperator, strVAttValue, strFilterValue);
                //System.out.println(blnSatisfy);
                if (!blnSatisfy) {
                    break;
                }
            }
            if (blnSatisfy) {
                lstFiltered.add(v);
            }
        }
        
        return lstFiltered;
    }
    
    /**
     * MethodName: filterByEdgeAttributes
     * Description: selected edges based filtering criteria given by 
     * the user
     * Version: 1.0
     * Author : Afra
     * @param <V>
     *          Vertex Type (subclass of IVertex)
     * @param <E>
     *          Edge Type (subclass of IEdge)
     * @param pDynaGraph
     *          DynamicGraph
     * @param tf
     *          TimeFrame
     * @param arrstrEdgeFilters
     *          2D String array of filtering criteria:
     *          String[i][0] = ith filter attribute name
     *          String[i][1] = ith filter operator
     *          String[i][2] = ith filter filtering value
     * @return 
     *          List<E> : list of edges that match with all given filtering 
     * criteria.
     */
    public static <V extends IVertex, E extends IEdge<V>> 
        List<E> filterByEdgeAttributes(IDynamicGraph<V,E> pDynaGraph,
                TimeFrame tf,
                String[][] arrstrEdgeFilters) {
        
            List<E> lstEdges = new LinkedList<>();
            
            for (E e : pDynaGraph.getEdges(tf)) {
                boolean blnSatisfy = false;
                for (int i = 0 ; i < arrstrEdgeFilters.length; i++) {
                    String strAttName = arrstrEdgeFilters[i][0];
                    String strOperator = arrstrEdgeFilters[i][1];
                    String strFilterValue = null;
                    if (arrstrEdgeFilters[i].length == 3) {
                        strFilterValue = arrstrEdgeFilters[i][2];
                    }
                    
                    String strEAttValue = null;
                    if (e.getUserAttributer().getAttributeNames()
                            .contains(strAttName)) {
                        strEAttValue = e.getUserAttributer()
                                .getAttributeValue(strAttName, tf);
                    } else if (e.getSystemAttributer().getAttributeNames()
                            .contains(strAttName)) {
                        strEAttValue = e.getSystemAttributer()
                                .getAttributeValue(strAttName, tf);
                    }
                    //System.out.println(e.toString());
                   // System.out.println(strOperator + " : "+ strEAttValue +" (strEAttName) " + strAttName + ": " + strFilterValue);
                    blnSatisfy = FilteringOperators
                            .compare(strOperator, strEAttValue, strFilterValue);
                    //System.out.println(blnSatisfy);
                    if (!blnSatisfy) {
                        break;
                    }
                }
                if (blnSatisfy) {
                    lstEdges.add(e);
                }
            }
            
            return lstEdges;
    }
        
    /**
     * MethodName: filterByVertexEdgeAttributes
     * Description: selected a subset of the vertices of the graph, 
     * where all vertices and edges satisfy the filtering conditions.
     * @param <V>
     *          Vertex Type (subclass of IVertex)
     * @param <E>
     *          Edge Type (subclass of IEdge)
     * @param pDynaGraph
     *          DynamicGraph
     * @param tf
     *          TimeFrame
     * @param arrstrVertexFilters
     *          2D String array of filtering criteria for vertices:
     *          String[i][0] = ith filter attribute name
     *          String[i][1] = ith filter operator
     *          String[i][2] = ith filter filtering value
     * @param arrstrEdgeFilters
     *          2D String array of filtering criteria for edges:
     *          String[i][0] = ith filter attribute name
     *          String[i][1] = ith filter operator
     *          String[i][2] = ith filter filtering value
     * @return 
     *          List<V> : list of vertices which are the intersection of
     * filtering results by vertices filtering and edge filtering.
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-08-23      Abhi            Modified to process VertexFilters and EdgeFilters. Combine Filtered vertices on both type of filters
     */
    public static <V extends IVertex, E extends IEdge<V>> List<V> 
            filterByVertexEdgeAttributes(IDynamicGraph<V,E> pDynaGraph,
                    TimeFrame tf,
                    String[][] arrstrVertexFilters,
                    String[][] arrstrEdgeFilters) {
        List<V> lstInter = new LinkedList<>();
        //System.out.println("arrstrVertexFilters length: " + arrstrVertexFilters.length);
        //System.out.println("arrstrEdgeFilters length: " + arrstrEdgeFilters.length);
        
        if(arrstrVertexFilters.length>0 && arrstrEdgeFilters.length>0){   // both types of filters present 
            
            List<V> lstV = filterByVertexAttributes(pDynaGraph, tf, arrstrVertexFilters);
            List<E> lstE = filterByEdgeAttributes(pDynaGraph, tf, arrstrEdgeFilters);

            Set<V> setV = new HashSet<>();
            setV.addAll(lstV);
            // find an intersection of filtered vertices and vertices of filtered edgess
            for (E e : lstE) {
                if (setV.contains(e.getSource())) {
                    lstInter.add(e.getSource());
                }
                if (setV.contains(e.getDestination())) {
                    lstInter.add(e.getDestination());
                }
            }
            //System.out.println("FilteredVertexListSize: " + lstV.size());
            //System.out.println("FilteredEdgeListSize: " + lstE.size());
            //System.out.println("FilteredVertexListAfterBothOperations: " + lstInter.size());
            
        }else if(arrstrVertexFilters.length>0 && arrstrEdgeFilters.length<=0){  // only vertex filters present
            
            List<V> lstV = filterByVertexAttributes(pDynaGraph, tf, arrstrVertexFilters);
            lstInter = lstV;
            
        }else if(arrstrVertexFilters.length<=0 && arrstrEdgeFilters.length>0){    // only edge filters present
            
            List<E> lstE = filterByEdgeAttributes(pDynaGraph, tf, arrstrEdgeFilters);
            for (E e : lstE) { 
                    lstInter.add(e.getSource());
                    lstInter.add(e.getDestination());
            }
        }
           
        return lstInter;
    }
}
