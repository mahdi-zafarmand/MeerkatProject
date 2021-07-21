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
import java.util.LinkedList;
import java.util.List;
import main.meerkat.MeerkatBIZ;
import main.project.Project;

/**
 *
 * @author mahdi
 */
public class FilteringAPI {
    /*
    Method Name: filterVertices
    Description: Applies a set of filters on the vertices and return the vertices which meet all the conditions.
    */
    public static int[] filterVertices(int pintProjectId, int pintGraphId,
            int pintTimeframeIndex, String[][] parrstrFilteringParams) {
        
        Project prj = MeerkatBIZ.getMeerkatApplication().getProject(pintProjectId);
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = prj.getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeframeIndex);
        
        List<IVertex> lstVertices = GraphFilter.filterByVertexAttributes(dynaGraph, tf, parrstrFilteringParams);
        
        int[] arrintVertexIds = new int[lstVertices.size()];
        
        for (int i = 0 ; i < arrintVertexIds.length; i++) {
            arrintVertexIds[i] = lstVertices.get(i).getId();
        }
        
        return arrintVertexIds;
    }
    
    /*
    Method Name: filterEdges
    Description: Applies a set of filters on the edges and return the edges which meet all the conditions.
    */
    public static int[] filterEdges(int pintProjectId, int pintGraphId,
            int pintTimeFrameIndex, String[][] parrstrFilteringParams) {
        
        Project prj = MeerkatBIZ.getMeerkatApplication().getProject(pintProjectId);
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = prj.getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        List<IEdge<IVertex>> lstEdges = GraphFilter.filterByEdgeAttributes(dynaGraph, tf, parrstrFilteringParams);
        
        int[] arrintEdgeIds = new int[lstEdges.size()];
        
        for (int i = 0 ; i < arrintEdgeIds.length ; i++) {
            arrintEdgeIds[i] = lstEdges.get(i).getId();
        }
        
        return arrintEdgeIds;
    }
    
    /*
    MethodName: filterGraphByVList
    Description: Extract a new dynamicGraph from the selected vertices, adds it to the same project
                 and return the new graphId.
    */
    public static int filterGraphByVList(int pintProjectId, int pintGraphId,
            int pintTimeFrameIndex, int[] parrintVertexIds) {
        
        Project prj = MeerkatBIZ.getMeerkatApplication().getProject(pintProjectId);
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = prj.getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        List<IVertex> lstVertices = new LinkedList<>();
        for (int i = 0 ; i < parrintVertexIds.length; i++) {
            lstVertices.add(dynaGraph.getVertex(parrintVertexIds[i]));
        }
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraphFiltered = 
                GraphFilter.filterByVList(dynaGraph, tf, lstVertices);
        
        int intFilteredGId = prj.addGraph(dynaGraphFiltered);
        return intFilteredGId;
    }
    
    /*
    Method Name: filterGraphByEList
    Description: Extract a new dynamicGraph considering the selected edges, adds it to the same project
                 and return the new graphId.
    */
    public static int filterGraphByEList(int pintProjectId, int pintGraphId,
            int pintTimeFrameIndex, int[] parrintEdgeIds) {
        
        Project prj = MeerkatBIZ.getMeerkatApplication().getProject(pintProjectId);
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = prj.getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        List<IEdge<IVertex>> lstEdges = new LinkedList<>();
        for (int i = 0 ; i < parrintEdgeIds.length ; i++) {
            lstEdges.add(dynaGraph.getEdge(parrintEdgeIds[i]));
        }
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraphFiltered =
                GraphFilter.filterByEList(dynaGraph, tf, lstEdges);
        
        int intFilteredGraphId = prj.addGraph(dynaGraphFiltered);
        return intFilteredGraphId;
    }
}
