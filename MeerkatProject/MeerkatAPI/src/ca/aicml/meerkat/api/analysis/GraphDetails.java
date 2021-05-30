/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api.analysis;

import algorithm.graph.GeneralStatistics;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import main.meerkat.MeerkatBIZ;
import main.project.Project;

/**
 * Description: API to Calculates statistics about the graph (timeframe)
 *  
 * @author aabnar
 */
public class GraphDetails {
 
    public static double getAvgClusteringCoefficient(int pintProjectId,
            int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ mk = MeerkatBIZ.getMeerkatApplication();
        Project pj = mk.getProject(pintProjectId);
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = pj.getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        double dblAvgCC = GeneralStatistics.computeAvgClusteringCoefficient(dynaGraph, tf);
        
        return dblAvgCC;
    }
    
    public static double getTransitivity(int pintProjectId,
            int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ mk = MeerkatBIZ.getMeerkatApplication();
        Project pj = mk.getProject(pintProjectId);
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = pj.getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        double dblAvgTr = GeneralStatistics.computeTransitivity(dynaGraph, tf);
        
        return dblAvgTr;
    }
    
    public static double getAverageAssortativity(int pintProjectId,
            int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ mk = MeerkatBIZ.getMeerkatApplication();
        Project pj = mk.getProject(pintProjectId);
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = pj.getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        double dblAvgAssort = GeneralStatistics.computeAssortativity(dynaGraph, tf);
        
        return dblAvgAssort;
        
    }
    
    public static double getAvgShortestPath(int pintProjectId,
            int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ mk = MeerkatBIZ.getMeerkatApplication();
        Project pj = mk.getProject(pintProjectId);
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = pj.getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        //run time of computeAvgShortestPath = O(V.(V+E)+V*V)
        double dblAvgSP = GeneralStatistics.computeAvgShortestPath(dynaGraph, tf);
        
        return dblAvgSP;
        
    }
    
    public static double getAvgNumberOfConnections(int pintProjectId,
            int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ mk = MeerkatBIZ.getMeerkatApplication();
        Project pj = mk.getProject(pintProjectId);
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = pj.getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        double dblAvgNCon = GeneralStatistics.computeAvgNumberOfConnections(dynaGraph, tf);
        
        return dblAvgNCon;
    }
    
    public static double getDensity(int pintProjectId,
            int pintGraphId,
            int pintTimeFrameIndex) {
        
        MeerkatBIZ mk = MeerkatBIZ.getMeerkatApplication();
        Project pj = mk.getProject(pintProjectId);
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = pj.getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        double numberOfEdges = dynaGraph.getEdges(tf).size();
        double numberofNodes = dynaGraph.getVertices(tf).size();
        
        double density = 2 * numberOfEdges / (numberofNodes * (numberofNodes - 1));
        
        return density;
                
    }
}
