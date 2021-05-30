/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api.analysis;

import algorithm.graph.communitymining.CommunityMiningHandler;
import algorithm.graph.communitymining.Miner;
import config.CommunityMiningParameters;
import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.meerkat.MeerkatBIZ;
import main.project.Project;

/**
 * CommunityMiningAPi has a method handling back-end calls for each
 * Community Mining Algorithms.
 * After running the community mining (CM) algorithm, the graph file on the
 * disk would be updated.
 * If algorithm and file update was done successfully, the method will return
 * a true, otherwise it would throw the proper exception.
 * @author aabnar
 */
public class CommunityMiningAPI {
    
    public static void runCommunityMiningAlgorithm(int pintProjectID,
            int pintGraphID,
            int pintTimeFrameIndex,
            String pstrCMAlgName,
            String[] parameters) {
        
        try {
            MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
            System.out.println("prj id =  " + pintProjectID +
                    " , graph id = " + pintGraphID);
            IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                    = meerkatApp.getProject(pintProjectID).getGraph(pintGraphID);

            Miner miner;
            
            if (pintTimeFrameIndex > -1) {
                
                TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
                 
                miner = CommunityMiningHandler
                        .getCMAlgorithm(igraph, tf, pstrCMAlgName, parameters);
                 
                CommunityMiningHandler.runAlgorithm(igraph, tf, miner);
                 
            } else {
                miner = CommunityMiningHandler
                    .getCMAlgorithm(igraph, pstrCMAlgName, parameters);
            
                CommunityMiningHandler.runAlgorithm(igraph, miner);
            }
        System.out.println("CommunityMiningAPI.runCMAlgorithm() miner = " + miner.getName());    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CommunityMiningAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Description: Terminates community mining algorithm.
     * 
     * @param pintProjectID
     * @param pintGraphID
     * @param pintTimeFrameIndex
     * @param pstrCMAlgName
     */
     public static void stopAlgorithm(int pintProjectID, int pintGraphID, int pintTimeFrameIndex, String pstrCMAlgName) {
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
            
            IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                    = meerkatApp.getProject(pintProjectID).getGraph(pintGraphID);
            TimeFrame tf = null;
            if (pintTimeFrameIndex > -1) {
                tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
            }
         CommunityMiningHandler.terminateThread(igraph, tf, pstrCMAlgName);
    }
     
    public static void stopAlgorithm(int pintProjectID, int pintGraphID, String pstrCMAlgName) {
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
            
            IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                    = meerkatApp.getProject(pintProjectID).getGraph(pintGraphID);
            
        CommunityMiningHandler.terminateThread(igraph, pstrCMAlgName);
    } 
    /**
     * Description: runs community mining on all time frames of a graph.
     * 
     * @param pintProjectID
     * @param pintGraphID
     * @param pstrCMAlgName
     * @param parameters 
     */
    public static void runCMAlgorithm(int pintProjectID,
            int pintGraphID,
            String pstrCMAlgName,
            String[] parameters) {
        CommunityMiningAPI.runCommunityMiningAlgorithm(pintProjectID,pintGraphID, -1 , 
                pstrCMAlgName, parameters);
    }
    
    public static boolean isDone (int pintProjectID,
            int pintGraphID,
            int pintTimeFrameIndex) {
        
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = meerkatApp.getProject(pintProjectID).getGraph(pintGraphID);

        if (pintTimeFrameIndex > -1) {
            TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
            return CommunityMiningHandler.isDone(igraph, tf);
        } else {
            return CommunityMiningHandler.isDone(igraph);
        }
    }
    
    /**
     * Description: check if the community mining algorithm is done on a graph 
     * where we are running the algorithm on all time frames of the graph.
     * @param pintProjectID
     * @param pintGraphID
     * @return 
     */
    public static boolean isDone (int pintProjectID,
            int pintGraphID) {
        return CommunityMiningAPI.isDone(pintProjectID, pintGraphID, -1);
    }
    
    /**
     * 
     * @param pintProjectID
     * @param pintGraphID
     * @param pintTimeFrameIndex
     * @return 
     *      a map from community label to list of vertices is returned.
     */
    public static Map<String, List<Integer>> getResults (int pintProjectID,
            int pintGraphID,
            int pintTimeFrameIndex) {
        
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = meerkatApp.getProject(pintProjectID).getGraph(pintGraphID);

//        if ( pintTimeFrameIndex > -1) {
            TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
            return CommunityMiningHandler.getResults(igraph, tf);

//        } else {
//            return CommunityMiningHandler.getResults(igraph);
//        }
    }
    /**
     * get communities of timeframe where one vertex may belong to more than one community
     * @param pintProjectID
     * @param pintGraphID
     * @param pintTimeFrameIndex
     */
    public static Map<String, List<Integer>> getCommunitiesMultiples (int pintProjectID,
            int pintGraphID,
            int pintTimeFrameIndex) {
    
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex , IEdge<IVertex>> igraph =
                meerkatApp.getProject(pintProjectID).getGraph(pintGraphID);
        
        TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        String attName = MeerkatSystem.COMMUNITY;
        Map<String, List<Integer>> communities = new HashMap<>();
        
        if (igraph.getVertexAllAttributeNames().contains(attName)) {
            for (IVertex v : igraph.getVertices(tf)) {
                if (v.getSystemAttributer()
                        .containsAttributeAtTimeFrame(attName, tf)) {
                    String com = v.getSystemAttributer()
                            .getAttributeValue(attName, tf);
                    //System.out.println("MeerkatAPI:CommunityMiningAPI.getCommunities ::: vertex id = " + v.getId() + ", " + com);
                    String[] singleCom = com.split(",");
                    
                    for (String c : singleCom) {
                        if (!communities.containsKey(c)) {
                            communities.put(c, new LinkedList<>());
                        }
                        communities.get(c).add(v.getId());
                        
//                        System.out.println("CommunityMiningAPI.getCommunities() : "
//                            + c + " : " + v.getId() );
                    }
                    

                }
            }
        }
        return communities;
    }
     /**
     * get communities of timeframe where one vertex belongs to only one community
     * @param pintProjectID
     * @param pintGraphID
     * @param pintTimeFrameIndex
     */
    public static Map<String, List<Integer>> getCommunities (int pintProjectID,
            int pintGraphID,
            int pintTimeFrameIndex) {
    
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex , IEdge<IVertex>> igraph =
                meerkatApp.getProject(pintProjectID).getGraph(pintGraphID);
        
        TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        String attName = MeerkatSystem.COMMUNITY;
        Map<String, List<Integer>> communities = new HashMap<>();
        
        if (igraph.getVertexAllAttributeNames().contains(attName)) {
            for (IVertex v : igraph.getVertices(tf)) {
                if (v.getSystemAttributer()
                        .containsAttributeAtTimeFrame(attName, tf)) {
                    String com = v.getSystemAttributer()
                            .getAttributeValue(attName, tf);
                    //System.out.println("MeerkatAPI:CommunityMiningAPI.getCommunities ::: vertex id = " + v.getId() + ", " + com);
                    String[] singleCom = com.split(",");
                    String finalComm = singleCom[0];
                    
                        if (!communities.containsKey(finalComm)) {
                            communities.put(finalComm, new LinkedList<>());
                        }
                        communities.get(finalComm).add(v.getId());
                        
                        //System.out.println("CommunityMiningAPI.getCommunities() : "
                        //    + c + " : " + v.getId() );
                    
                    

                }
            }
        }
        return communities;
    }
    
    // this method is now only called for DCMining, it removes attribute name for all timeframes.
    public static void clearDynamicCommunities(int activeProjectID, 
            int activeGraphID) {
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        Project prj = meerkatApp.getProject(activeProjectID);
        IDynamicGraph<IVertex, IEdge<IVertex>> graph = 
                prj.getGraph(activeGraphID);
        List<TimeFrame> timeframes = graph.getAllTimeFrames();
        
        for(TimeFrame tf : timeframes){
            for (IVertex v : graph.getVertices(tf)) {
                if (v.getSystemAttributer().getAttributeNames()
                        .contains(MeerkatSystem.COMMUNITY)) {
                    v.getSystemAttributer().removeAttribute(
                            MeerkatSystem.COMMUNITY);
                }
            }
        }
    }
    
    //added this method to clear attribute values for specific timeframes
    public static void clearCommunities(int activeProjectID, 
            int activeGraphID, 
            int timeFrameIndex) {
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        Project prj = meerkatApp.getProject(activeProjectID);
        IDynamicGraph<IVertex, IEdge<IVertex>> graph = 
                prj.getGraph(activeGraphID);
        TimeFrame tf = graph.getAllTimeFrames().get(timeFrameIndex);
        for (IVertex v : graph.getVertices(tf)) {
            if (v.getSystemAttributer().getAttributeNames()
                    .contains(MeerkatSystem.COMMUNITY)) {
                v.getSystemAttributer().removeAttributeValue(MeerkatSystem.COMMUNITY, tf);
            }
        }
    }        
        
    // DYNAMIC COMMUNITY MINING
    public static String getKey_DCMINING_SIMTHRESHOLD() {
        return CommunityMiningParameters.DCMINING_SIMILARITYTHRESHOLD ;
    }
    public static String getKey_DCMINING_METRIC() {
        return CommunityMiningParameters.DCMINING_METRIC ;
    }
    public static String getKey_DCMINING_METHOD() {
        return CommunityMiningParameters.DCMINING_METHOD ;
    }
    public static String getKey_DCMINING_OVERLAP() {
        return CommunityMiningParameters.DCMINING_OVERLAP ;
    }
    public static String getKey_DCMINING_HUBS() {
        return CommunityMiningParameters.DCMINING_HUBS ;
    }
    public static String getKey_DCMINING_INSTABILITY() {
        return CommunityMiningParameters.DCMINING_INSTABILITY ;
    }
    public static String getKey_DCMINING_HISTORY() {
        return CommunityMiningParameters.DCMINING_HISTORY ;
    }
    
    // FAST MODULARITY
    public static String getKey_FM_Weighted() {
        return CommunityMiningParameters.FASTMODULARITY_WEIGHTED ;
    }
    public static String getKey_FM_AlgType() {
        return CommunityMiningParameters.FASTMODULARITY_ALGORITHMTYPE ;
    }
    public static String getValue_FM_FastModularity() {
        return CommunityMiningParameters.FASTMODULARITY_ALGTYPE_FASTMODULARITY ;
    }
    public static String getValue_FM_MaxMin() {
        return CommunityMiningParameters.FASTMODULARITY_ALGTYPE_MAXMINMODULARITY ;
    }
    
    // SAME ATTRIBUTE VALUE
    public static String getKey_SAV_Attr() {
        return CommunityMiningParameters.ATTRIBUTEVALUEMINER_CHOSENATTRIBUTE ;
    }
    public static String getKey_SAV_MultipleValue() {
        return CommunityMiningParameters.ATTRIBUTEVALUEMINER_MULTIPLEVALUE ;
    }
    public static String getKey_SAV_Separator() {
        return CommunityMiningParameters.ATTRIBUTEVALUEMINER_SEPARATOR ;
    }
    
    // KMEANS    
    public static String getKey_KMeans_Attr() {
        return CommunityMiningParameters.KMEANS_ATTRIBUTE ;
    }
    public static String getKey_KMeans_NumClusters() {
        return CommunityMiningParameters.KMEANS_NUMBEROFCLUSTERS ;
    }
    
    // LOCALCM
    public static String getKey_LocalCM_Overlap() {
        return CommunityMiningParameters.LOCALCM_OVERLAP ;
    }
    public static String getKey_LocalCM_AlgType() {
        return CommunityMiningParameters.LOCALCM_ALGORITHMTYPE ;
    }
    public static String getValue_LocalCM_AlgTypeM() {
        return CommunityMiningParameters.LOCALCM_ALGTYPE_M ;
    }
    public static String getValue_LocalCM_AlgTypeL() {
        return CommunityMiningParameters.LOCALCM_ALGTYPE_L ;
    }
    public static String getValue_LocalCM_AlgTypeR() {
        return CommunityMiningParameters.LOCALCM_ALGTYPE_R ;
    }    
    
    // LOCAL T
    public static String getKey_LocalT_StartNodeID() {
        return CommunityMiningParameters.LOCALT_STARTNODEID ;
    }
    public static String getKey_LocalT_NumComm() {
        return CommunityMiningParameters.LOCALT_NUMBEROFCM ;
    }
    
    // LOCAL TOP LEADEERS
    public static String getKey_LocalTopL_NumClusters() {
        return CommunityMiningParameters.LOCALTOPLEADERS_NUMBEROFCLUSTERS ;
    }
    public static String getKey_LocalTopL_OutlierPercent() {
        return CommunityMiningParameters.LOCALTOPLEADERS_OUTLIERPERCENTAGE ;
    }
    public static String getKey_LocalTopL_HubPercent() {
        return CommunityMiningParameters.LOCALTOPLEADERS_HUBPERCENTAGE ;
    }
    public static String getKey_LocalTopL_CenterDist() {
        return CommunityMiningParameters.LOCALTOPLEADERS_CENTERS ;
    }
    
    // ROSVALL INFOMAP
    public static String getKey_RInfomap_Weighted() {
        return CommunityMiningParameters.ROSVALLINFOMAP_DIRECTED ;
    }
    public static String getKey_RInfomap_Attempts() {
        return CommunityMiningParameters.ROSVALLINFOMAP_NUMBEROFATTEMPTS ;
    }
    
    // ROSVALL INFOMOD    
    public static String getKey_RInfomod_Attempts() {
        return CommunityMiningParameters.ROSVALLINFOMOD_NUMBEROFATTEMPTS ;
    }

   

 


}
