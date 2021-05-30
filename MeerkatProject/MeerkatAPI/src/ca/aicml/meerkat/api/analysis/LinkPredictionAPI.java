/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api.analysis;

import algorithm.graph.linkprediction.LinkPredictionHandler;
import algorithm.graph.linkprediction.LinkPredictor;
import config.LinkPredictionParameters;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.meerkat.MeerkatBIZ;
import main.project.Project;


/**
 * LinkPredictionAPI has a method handling back-end calls for each
 * Link Prediction Algorithms.
 * After running the link prediction (LP) algorithm, the graph file will be 
 * inserted with newly predicted edges. Each predicted edge with have attribute
 * sys:PREDICTED.
 * If algorithm and graph update was done successfully, the method will return
 * a true, otherwise it would throw the proper exception.
 * 
 */
public class LinkPredictionAPI {
    
    public static void runLinkPredictionAlgorithm(int pintProjectID,
            int pintGraphID,
            int pintTimeFrameIndex,
            String pstrLinkPredAlgoName,
            String[] parameters) {
        
        try {
            MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
            IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                    = meerkatApp.getProject(pintProjectID).getGraph(pintGraphID);

            LinkPredictor linkPredictor = null;
            
            if (pintTimeFrameIndex > -1) {
                
                TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
                 
                linkPredictor = LinkPredictionHandler
                        .getLPAlgorithm(igraph, tf, pstrLinkPredAlgoName, parameters);
                 
                LinkPredictionHandler.runAlgorithm(igraph, tf, linkPredictor);
                 
//            } else {
//                linkPredictor = LinkPredictionHandler
//                    .getLPAlgorithm(igraph, pstrCMAlgName, parameters);
//            
//                LinkPredictionHandler.runAlgorithm(igraph, linkPredictor);
            }
        System.out.println("LinkPredictionAPI.runLPAlgorithm() linkPredictor = " + linkPredictor.getName());    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LinkPredictionAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Description: Terminates link prediction algorithm.
     * 
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
         LinkPredictionHandler.terminateThread(igraph, tf, pstrLPAlgName);
    }
     
 
    
    public static boolean isDone (int pintProjectID,
            int pintGraphID,
            int pintTimeFrameIndex) {
        
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = meerkatApp.getProject(pintProjectID).getGraph(pintGraphID);

        if (pintTimeFrameIndex > -1) {
            TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
            return LinkPredictionHandler.isDone(igraph, tf);
        } else {
            return LinkPredictionHandler.isDone(igraph);
        }
    }
    
    /**
     * Description: check if the link prediction algorithm is done on a graph 
     * checks on time frame index 0
     * @param pintProjectID
     * @param pintGraphID
     * @return 
     */
    public static boolean isDone (int pintProjectID,
            int pintGraphID) {
        return LinkPredictionAPI.isDone(pintProjectID, pintGraphID, -1);
    }
    
    /**
     * list of edges as pair of vertex ids
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @return List<int[]>
     *      
     */
    public static List<int[]> getResults (int pintProjectId,
            int pintGraphId,
            int pintTimeFrameIndex) {
        
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = meerkatApp.getProject(pintProjectId).getGraph(pintGraphId);

        TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        return LinkPredictionHandler.getResults(igraph, tf);

    }
    
    /**
     * delete the predicted edges from data structure
     * @param activeProjectID
     * @param activeGraphID
     * @param timeFrameIndex 
     */
    public static void deleteLinkPredictions(int activeProjectID, 
            int activeGraphID, 
            int timeFrameIndex) {
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        Project prj = meerkatApp.getProject(activeProjectID);
        IDynamicGraph<IVertex, IEdge<IVertex>> graph = 
                prj.getGraph(activeGraphID);
        TimeFrame tf = graph.getAllTimeFrames().get(timeFrameIndex);
        
        
        //TODO
    }        
    

    // LOCAL NAIVE BAYES
    public static String getLocalNaiveBayesIndex_TopN() {
        return LinkPredictionParameters.LOCALNAIVEBAYESINDEX_TOPNEDGES;
    }
    
    public static String getLocalNaiveBayesIndex_TopMetric() {
        return LinkPredictionParameters.LOCALNAIVEBAYESINDEX_TOPMETRIC;
    }

}