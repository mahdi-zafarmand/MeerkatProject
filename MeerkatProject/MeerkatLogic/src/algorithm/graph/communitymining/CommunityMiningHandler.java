/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.communitymining;


import config.MeerkatClassConfig;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.meerkat.MeerkatBIZ;

/**
 *
 * @author aabnar
 */
public class CommunityMiningHandler {
    
    static Map<IDynamicGraph, Map<TimeFrame, Thread>> mapThreads = new HashMap<>();
    static Map<Thread, Miner> mapAlgorithms = new HashMap<>();
    
    /**
     * MethodName : getCMAlgorithm
     * Description: creates or loads the static community mining algorithm.
     * @param pDynaGraph
     * @param ptf
     * @param pstrCMAlgName
     * @param pstrParameters
     * @return
     * @throws ClassNotFoundException
     */
    public static Miner getCMAlgorithm(IDynamicGraph pDynaGraph,
            TimeFrame ptf,
            String pstrCMAlgName,
            String[] pstrParameters) throws ClassNotFoundException {
        Miner<IVertex, IEdge<IVertex>> minerAlg = null;
        
        try {
            String strClassName = MeerkatBIZ.meerkatConfig 
                    .getClassName(MeerkatClassConfig.COMMUNITYMINING_TAG,
                            pstrCMAlgName);
            
            System.out.println("CommunityMiningHandler.getCMAlgorithm(): ClassName = "+strClassName+" with ID "+pstrCMAlgName) ;
            
            String strClassPath = "algorithm.graph.communitymining." + strClassName;
            
            System.out.println("CommunityMiningHandler.getCMAlgorithm(): classPath = " + strClassPath);
            
            Class clsCommunityMining = Class.forName(strClassPath);
            Constructor constCM = null;
            
            if (ptf != null) {
                constCM = clsCommunityMining.getConstructor(
                        IDynamicGraph.class, TimeFrame.class, String[].class);
                minerAlg = (Miner) constCM.newInstance(
                        pDynaGraph, ptf, pstrParameters);
            } else {
                constCM = clsCommunityMining.getConstructor(
                        IDynamicGraph.class, String[].class);
                minerAlg = (Miner) constCM.newInstance(
                        pDynaGraph, pstrParameters);
            }
                
            
        } catch (SecurityException ex) {
            Logger.getLogger(CommunityMiningHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(CommunityMiningHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return minerAlg;
    }
    
    /**
     * MethodName : getCMAlgorithm
     * Description: creates or loads the dynamic community mining algorithm.
     *              as it does not need the time frame, it is running cm algs that
     *              are ran on all time frames rather than one time frame.
     * @param pDynaGraph
     * @param pstrCMAlgName
     * @param pstrParameters
     * @return
     * @throws ClassNotFoundException
     */
    public static Miner getCMAlgorithm(IDynamicGraph pDynaGraph,
            String pstrCMAlgName,
            String[] pstrParameters) throws ClassNotFoundException {
        
        return CommunityMiningHandler.getCMAlgorithm(
                pDynaGraph, null, pstrCMAlgName, pstrParameters);
    }
    
    /**
     *
     * @param pDynaGraph
     * @param ptf
     * @param pCMAlg
     */
    public static void runAlgorithm(
            IDynamicGraph<IVertex,IEdge<IVertex>> pDynaGraph,
            TimeFrame ptf,
            Miner<IVertex, IEdge<IVertex>> pCMAlg) {
        
        Thread th = new Thread(pCMAlg);
        if (!mapThreads.containsKey(pDynaGraph)) {
            mapThreads.put(pDynaGraph, new HashMap<>());
        }
        
        if (ptf != null) {
            mapThreads.get(pDynaGraph).put(ptf, th);
        } else {
            for (TimeFrame tf : pDynaGraph.getAllTimeFrames()) {
                mapThreads.get(pDynaGraph).put(tf,th);
            }
        }
        mapAlgorithms.put(th, pCMAlg);
        
        th.start();
    
        try{
            th.join();
        }catch(InterruptedException ex){
            System.out.println("CommunityMiningHandler.runAlgorithm() - Mining Thread Interrupted!");
        }
            catch(Exception e){
            System.out.println("CommunityMiningHandler.runAlgorithm() exception in th.join");
            e.printStackTrace();
        }
        
    }
    
    /**
     *  Description: used for dynamic graphs or when it is going to run on all
     *  time frames.
     * @param pDynaGraph
     * @param pCMAlg
     */
    public static void runAlgorithm(IDynamicGraph<IVertex,IEdge<IVertex>> pDynaGraph,
            Miner<IVertex, IEdge<IVertex>> pCMAlg) {
        
        CommunityMiningHandler.runAlgorithm(pDynaGraph, null, pCMAlg);
    }
    
    /**
     *
     * @param pDynaGraph
     * @param ptf
     * @return
     */
    public static boolean isDone (
            IDynamicGraph<IVertex,IEdge<IVertex>> pDynaGraph, TimeFrame ptf) {
//        System.out.print("graph id = " + pDynaGraph.getId());
        if (ptf != null) {
            
            if (mapThreads.get(pDynaGraph).get(ptf).isAlive()) {
                return false;
            }
        } else {
            if (mapThreads.get(pDynaGraph).get(
                    pDynaGraph.getAllTimeFrames().get(0)).isAlive()) {
               return false;
            }
        }
        return true;
    }
    
    
    public static boolean isDone (IDynamicGraph<IVertex,IEdge<IVertex>> pDynaGraph) {
        
        return CommunityMiningHandler.isDone(pDynaGraph, null);
    }
    
    /**
     *
     * @param pDynaGraph
     * @param ptf
     * @return
     */
    public static Map<String, List<Integer>> getResults (IDynamicGraph pDynaGraph,
            TimeFrame ptf) {
        
        if (!mapThreads.keySet().contains(pDynaGraph) ||
                !mapThreads.get(pDynaGraph).containsKey(ptf)) {
            return new HashMap<>();
        }
        
        Map<String, List<Integer>> communities;
        if (isDone(pDynaGraph, ptf)) {
            System.out.println("inside loop ");
            Thread th = mapThreads.get(pDynaGraph).get(ptf);
            System.out.println("thread id :"+th.getName());
            Miner<IVertex, IEdge<IVertex>> miner = mapAlgorithms.get(th);
            System.out.println("miner hmComm Size "+miner.hmpCommunities.size());
            
            communities = miner.hmpCommunities;
            System.out.println("CommunityMiningHandler getresults() : community size :"+communities.size());
            
            return communities;
        }
        return null;
    } 


    public static void terminateThread(IDynamicGraph<IVertex, IEdge<IVertex>> pDynaGraph, TimeFrame ptf, String pstrCMAlgName) {
        Thread th = mapThreads.get(pDynaGraph).get(ptf);
        if (th.isAlive()) {
            
            Miner<IVertex, IEdge<IVertex>> miner = mapAlgorithms.get(th);
            mapAlgorithms.remove(th);
            mapThreads.remove(pDynaGraph);
            miner.stopThread();
            
            try{
                th.interrupt();
            }catch(Exception e){
                System.err.print("CommunityMining algorithm thread was interrupted");
            }
            
        }
    }
    
    
    //for Dynamic Community Mining algorithm
    public static void terminateThread(IDynamicGraph<IVertex, IEdge<IVertex>> pDynaGraph, String pstrCMAlgName) {
        Thread th = null;
        for (TimeFrame tf : pDynaGraph.getAllTimeFrames()) {
                //mapThreads.get(pDynaGraph).put(tf,th);
                th = mapThreads.get(pDynaGraph).get(tf);
                break;
            }
        
        
        if (!th.isAlive()) {
        } else {
            Miner<IVertex, IEdge<IVertex>> miner = mapAlgorithms.get(th);
            mapAlgorithms.remove(th);
            mapThreads.remove(pDynaGraph);
            miner.stopThread();
            
            try{
                th.interrupt();
            }catch(Exception e){
                System.err.print("CommunityMining algorithm thread was interrupted");
            }
        }
    }
    
}
