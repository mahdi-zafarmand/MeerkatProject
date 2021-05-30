/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api.analysis;

import algorithm.graph.shortestpath.ShortestPathAbstract;
import algorithm.graph.shortestpath.ShortestPathHandler;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.meerkat.MeerkatBIZ;

/**
 *
 * @author talat
 */
public class ShortestPathAPI {
    
    public static void runShortestPathAlgorithm(int pintProjectId,
            int pintGraphId,
            int pintTimeFrameIndex,
            String pstrShortestPathAlgoName,
            int pintSourceVertexId,            
            int pintDestinationVertexId) {
        
        try {
            MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
            IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                    = meerkatApp.getProject(pintProjectId).getGraph(pintGraphId);

            ShortestPathAbstract shortestPath = null;
            
            if (pintTimeFrameIndex > -1) {
                
                TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
                 
                shortestPath = ShortestPathHandler
                        .getSPAlgorithm(igraph, tf, pstrShortestPathAlgoName, pintSourceVertexId, pintDestinationVertexId);
                 
                ShortestPathHandler.runAlgorithm(igraph, tf, shortestPath, pintSourceVertexId, pintDestinationVertexId);
            }
            
            System.out.println("ShortestPathAPI.runShortestPathAlgorithm() shortest path algoirthm = " + shortestPath.getName());    
        } catch (Exception ex) {
            Logger.getLogger(ShortestPathAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Returns the Shortest Path Results that was last run as a list of edges
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @return edgeIds - List<Integer>
     * 
     * @author Talat
     * @since 2018-05-14
     */
    public static List<Integer> getShortestPathResults_LastRun(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        List<Integer> edgeIds = null;
        try {
            IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                        = meerkatApp.getProject(pintProjectId).getGraph(pintGraphId);
            TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
            edgeIds = ShortestPathHandler.getResults_LastRun(igraph,tf);
        } catch (Exception ex) {
            System.out.print("ShortestPathAPI.getShortestPathResults_LastRun(): EXCEPTION");
            ex.printStackTrace();
            Logger.getLogger(ShortestPathAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return edgeIds;
    }
    
    /**
     * Returns the Shortest Path Results that was all runs as a list of edges
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @return edgeIds - List<Integer>
     * 
     * @author Talat
     * @since 2018-05-14
     */
    public static List<Integer> getShortestPathResults_AllRuns(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        List<Integer> edgeIds = null;
        try {
            IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                        = meerkatApp.getProject(pintProjectId).getGraph(pintGraphId);
            TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
            edgeIds = ShortestPathHandler.getResults_AllRuns(igraph,tf);
        } catch (Exception ex) {
            System.out.print("ShortestPathAPI.getShortestPathResults_LastRun(): EXCEPTION");
            ex.printStackTrace();
            Logger.getLogger(ShortestPathAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return edgeIds;
    }
    
    /**
     * Terminates Shortest Path algorithm
     * @param pintProjectID
     * @param pintGraphID
     * @param pintTimeFrameIndex
     * @param pstrLPAlgName
     */
     public static void stopAlgorithm(int pintProjectID,
             int pintGraphID, int pintTimeFrameIndex, String pstrLPAlgName) {
        
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
            
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = meerkatApp.getProject(pintProjectID).getGraph(pintGraphID);
        
        TimeFrame tf = null;
        if (pintTimeFrameIndex > -1) {
            tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
        }
        ShortestPathHandler.terminateThread(igraph, tf, pstrLPAlgName);
    }
     
 
    
    public static boolean isDone (int pintProjectID,
            int pintGraphID,
            int pintTimeFrameIndex) {
        
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = meerkatApp.getProject(pintProjectID).getGraph(pintGraphID);

        if (pintTimeFrameIndex > -1) {
            TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
            return ShortestPathHandler.isDone(igraph, tf);
        } else {
            return ShortestPathHandler.isDone(igraph);
        }
    }
    
    /**
     * Description: check if the Shortest Path algorithm is done on a graph 
     * checks on time frame index 0
     * @param pintProjectID
     * @param pintGraphID
     * @return 
     */
    public static boolean isDone (int pintProjectID,
            int pintGraphID) {
        return ShortestPathAPI.isDone(pintProjectID, pintGraphID, -1);
    }
    
    /**
     * list of edges as edge Ids
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @return List<Integer>
     *      
     */
    public static List<Integer> getResults_LastRun (int pintProjectId,
            int pintGraphId,
            int pintTimeFrameIndex) {
        
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = meerkatApp.getProject(pintProjectId).getGraph(pintGraphId);

        TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        return ShortestPathHandler.getResults_LastRun(igraph, tf);

    }
    
    /**
     * Clears the shortest path results
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex 
     */
    public static void clearResults_AllRuns(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = meerkatApp.getProject(pintProjectId).getGraph(pintGraphId);

        TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        ShortestPathHandler.clearResults_AllRuns(igraph, tf);
    }
    
    /**
     * Returns the Shortest Path Results that was last run as a list of edges
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * 
     * @author Talat
     * @return double
     * @since 2018-05-14
     */
    public static double getShortestPathDistance(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        double shortestPathDistance = Double.MAX_VALUE;
        try {
            IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                        = meerkatApp.getProject(pintProjectId).getGraph(pintGraphId);
            TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
            shortestPathDistance = ShortestPathHandler.getShortestPathDistance(igraph,tf);
        } catch (Exception ex) {
            System.out.print("ShortestPathAPI.getShortestPathResults_LastRun(): EXCEPTION");
            ex.printStackTrace();
            Logger.getLogger(ShortestPathAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return shortestPathDistance;
    }
}
