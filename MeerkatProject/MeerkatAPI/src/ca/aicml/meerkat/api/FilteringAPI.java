/*Licenced at Meerkat@ualberta*/
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
 * @author aabnar
 */
public class FilteringAPI {

    /**
     * MethodName: filterVertices
     * Description: applies a set of filters on the vertices and return
     * the vertices than meet all the conditions
     * 
     * Version: 1.0
     * Author: Afra
     * 
     * @param pintProjectId
     *          project id
     * @param pintGraphId
     *          dynamicgraph id
     * @param pintTimeframeIndex
     *          timeframe index
     * @param parrstrFilteringParams
     *          two dimensional list of vertex filters:
     *          parrstrFilteringParams[i][0] = ith filter attribute_name
     *          parrstrFilteringParams[i][1] = ith filter operator
     *          parrstrFilteringParams[i][2] = ith filter attribute value
     * @return 
     *      int[] : array of the selected vertices id's.
     */
    public static int[] filterVertices(int pintProjectId,
            int pintGraphId,
            int pintTimeframeIndex,
            String[][] parrstrFilteringParams) {
        
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
    
    /**
     * MethodName: filterEdges
     * Description: return edges that meet all the filtering conditions.
     * Version: 1.0
     * Author: Afra
     * 
     * @param pintProjectId
     *          project Id
     * @param pintGraphId
     *          graph Id
     * @param pintTimeFrameIndex
     *          timeframe index
     * @param parrstrFilteringParams
     *          two dimensional list of vertex filters:
     *          parrstrFilteringParams[i][0] = ith filter attribute_name
     *          parrstrFilteringParams[i][1] = ith filter operator
     *          parrstrFilteringParams[i][2] = ith filter attribute value
     * @return 
     *          int[] : array of the selected edges id's.
     */
    public static int[] filterEdges(int pintProjectId,
            int pintGraphId,
            int pintTimeFrameIndex,
            String[][] parrstrFilteringParams) {
        
        Project prj = MeerkatBIZ.getMeerkatApplication().getProject(pintProjectId);
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = prj.getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        List<IEdge<IVertex>> lstEdges = GraphFilter.filterByEdgeAttributes(dynaGraph, 
                tf, 
                parrstrFilteringParams);
        
        int[] arrintEdgeIds = new int[lstEdges.size()];
        
        for (int i = 0 ; i < arrintEdgeIds.length ; i++) {
            arrintEdgeIds[i] = lstEdges.get(i).getId();
        }
        
        return arrintEdgeIds;
    }
    
    /**
     * MethodName: filterGraphByVList
     * Description: extract a new dynamicgraph from the selected vertices, adds it to the same project and return the new graph id.
     * Version: 1.0
     * Author: Afra
     * 
     * @param pintProjectId
     *          project id
     * @param pintGraphId
     *          dynamicgraph id
     * @param pintTimeFrameIndex
     *          timeframe index
     * @param parrintVertexIds
     *          int[]: array of selected vertices ids.
     * @return 
     *          id of the newly extracted and added graph.
     */
    public static int filterGraphByVList(int pintProjectId,
            int pintGraphId,
            int pintTimeFrameIndex,
            int[] parrintVertexIds) {
        
        Project prj = MeerkatBIZ.getMeerkatApplication().getProject(pintProjectId);
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                prj.getGraph(pintGraphId);
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
    
    /**
     * MethodName: filterGraphByEList
     * Description: extracts a new dynamicgraph by considering list of selected edges and adds it to the same project.
     * Version: 1.0
     * Author: Afra
     * 
     * @param pintProjectId
     *          project id
     * @param pintGraphId
     *          dynamicgraph id
     * @param pintTimeFrameIndex
     *          timeframe index
     * @param parrintEdgeIds
     *          int[] : array of selected edges ids.
     * @return 
     *          id of the newly extracted dynamicgraph.
     */
    public static int filterGraphByEList(int pintProjectId,
            int pintGraphId,
            int pintTimeFrameIndex,
            int[] parrintEdgeIds) {
        
        Project prj = MeerkatBIZ.getMeerkatApplication().getProject(pintProjectId);
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                prj.getGraph(pintGraphId);
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
