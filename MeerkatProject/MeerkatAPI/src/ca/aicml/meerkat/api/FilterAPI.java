/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api;

import algorithm.graph.filter.GraphFilter;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.meerkat.MeerkatBIZ;

/**
 *
 * @author mahdi
 */
public class FilterAPI {
    public static Set<Integer> getFilteredVertices(int pintProjectID, int pintGraphID,
            int pintTimeFrameIndex, String [][] parrarrstrVertexFilters,
            String [][] parrarrstrEdgeFilters) {
        
        Set<Integer> setintVertexIDs = null ;
        try {            
            setintVertexIDs = new HashSet<>();
            
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
            IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectID).getGraph(pintGraphID);        
            TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
            
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
    
    public static Set<Integer> getFilteredEdges(int pintProjectID, int pintGraphID,
            int pintTimeFrameIndex, String [][] parrarrstrEdgeFilters) {
        
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
