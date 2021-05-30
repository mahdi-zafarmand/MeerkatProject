/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api;

import algorithm.graph.filter.GraphFilter;
import static algorithm.graph.filter.GraphFilter.filterByEdgeAttributes;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.meerkat.MeerkatBIZ;

/**
 *  Class Name      : FilterAPI
 *  Created Date    : 2016-05-16
 *  Description     : Filtering the Graph using some criteria
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class FilterAPI {
 
    
    /**
     *  Method Name     : getFilteredVertices()
     *  Created Date    : 2016-05-16
     *  Description     : Filters the Vertices Based on Some Criteria
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pintTimeFrameIndex : int
     *  @param parrarrstrVertexFilters : String [][]
     *  @param parrarrstrEdgeFilters : String [][]
     *  @return List<Integer>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-08-23      Abhi            Modified call to GraphFilter by passing VertexFilterValues and EdgeFilterValues.
     * 
    */    
    public static Set<Integer> getFilteredVertices(
              int pintProjectID
            , int pintGraphID
            , int pintTimeFrameIndex
            , String [][] parrarrstrVertexFilters
            , String [][] parrarrstrEdgeFilters
            ) {
        
        Set<Integer> setintVertexIDs = null ;
        try {            
            setintVertexIDs = new HashSet<>();
            
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
            IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectID).getGraph(pintGraphID);        
            TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
            
            //List<IVertex> lstFilteredVertices = GraphFilter.filterByVertexAttributes(dynaGraph, tf, parrarrstrVertexFilters);
            List<IVertex> lstFilteredVertices = GraphFilter.filterByVertexEdgeAttributes(dynaGraph, tf, parrarrstrVertexFilters, parrarrstrEdgeFilters);
            
            for (IVertex vtxCurrent : lstFilteredVertices) {
                setintVertexIDs.add(vtxCurrent.getId()) ;
            }
            
        } catch (Exception ex) {
            System.out.println("FilterAPI.getFilteredVertices(): EXCEPTION") ;
            ex.printStackTrace();
        }
        
        return setintVertexIDs ;
    }
    
    /**
     *  Method Name     : getFilteredEdges()
     *  Created Date    : 2017-07-14
     *  Description     : Filters the Edges Based on Some Criteria
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pintTimeFrameIndex : int
     *  @param parrarrstrEdgeFilters : String [][]
     *  @return Set<Integer>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */    
    public static Set<Integer> getFilteredEdges(
              int pintProjectID
            , int pintGraphID
            , int pintTimeFrameIndex
            , String [][] parrarrstrEdgeFilters
            ) {
        
        Set<Integer> setintEdgeIDs = null ;
        try {            
            setintEdgeIDs = new HashSet<>();
            
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
            IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectID).getGraph(pintGraphID);        
            TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);

            List<IEdge<IVertex>> lstEdges  = GraphFilter.filterByEdgeAttributes(dynaGraph, tf, parrarrstrEdgeFilters);
            
            for (IEdge currentEdge : lstEdges) {
                setintEdgeIDs.add(currentEdge.getId()) ;
            }
            
        } catch (Exception ex) {
            System.out.println("FilterAPI.getFilteredEdges(): EXCEPTION") ;
            ex.printStackTrace();
        }
        
        return setintEdgeIDs ;
    }
    
    
}
