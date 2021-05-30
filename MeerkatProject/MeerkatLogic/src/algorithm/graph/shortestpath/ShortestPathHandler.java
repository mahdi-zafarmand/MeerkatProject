/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.shortestpath;

import static algorithm.graph.shortestpath.ShortestPathHandler.lstLastRunShortestPath;
import config.MeerkatClassConfig;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.meerkat.MeerkatBIZ;

/**
 *
 * @author sankalp
 */
public class ShortestPathHandler {
    
    
    static Map<IDynamicGraph, Map<TimeFrame, Thread>> mapThreads = new HashMap<>();
    static Map<Thread, ShortestPathAbstract> mapAlgorithms = new HashMap<>();
    static List<Integer> lstLastRunShortestPath = new ArrayList<>();
    static List<Integer> lstAllRunsShortestPath = new ArrayList<>();
    static double shortestPathDistance;
    
    /**
     * MethodName : getCMAlgorithm
     * Description: creates or loads the static community mining algorithm.
     * @param pDynaGraph
     * @param ptf
     * @param pstrSPAlgName
     * @param pintSourceVertexId
     * @param pintDestinationVertexId
     * @return
     * @throws java.lang.ClassNotFoundException
     */
    public static ShortestPathAbstract getSPAlgorithm(IDynamicGraph pDynaGraph,
            TimeFrame ptf,
            String pstrSPAlgName,
            int pintSourceVertexId,            
            int pintDestinationVertexId) throws ClassNotFoundException {
        
        ShortestPathAbstract<IVertex, IEdge<IVertex>> shortestPathAlg = null;
        
        try {
            String strClassName = MeerkatBIZ.meerkatConfig 
                    .getClassName(MeerkatClassConfig.SHORTESTPATH_TAG,
                            pstrSPAlgName);
            
            System.out.println("ShortestPathHandler.getSPAlgorithm(): ClassName = "+strClassName+" with ID "+pstrSPAlgName) ;
            
            String strClassPath = "algorithm.graph.shortestpath." + strClassName;
            
            System.out.println("ShortestPathHandler.getSPAlgorithm: classPath = " + strClassPath);
            
            Class clsShortestPath = Class.forName(strClassPath);
            Constructor constSP = null;
            
            if (ptf != null) {  
                constSP = clsShortestPath.getConstructor(
                        IDynamicGraph.class, TimeFrame.class);
                shortestPathAlg = (ShortestPathAbstract) constSP.newInstance(
                        pDynaGraph, ptf);
            } else {
                constSP = clsShortestPath.getConstructor(
                        IDynamicGraph.class, String[].class);
                shortestPathAlg = (ShortestPathAbstract) constSP.newInstance(
                        pDynaGraph);
            }
                
            
        } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(ShortestPathHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return shortestPathAlg;
    }
    
     /**
     *
     * @param pDynaGraph
     * @param ptf
     * @param pstrShortestPathAlgoName
     * @param pintSourceVertexId
     * @param pintDestinationVertexId
     */
    public static void runAlgorithm(
            IDynamicGraph<IVertex,IEdge<IVertex>> pDynaGraph,
            TimeFrame ptf, ShortestPathAbstract pstrShortestPathAlgoName, int pintSourceVertexId, int pintDestinationVertexId) {
        
        IVertex source = pDynaGraph.getGraph(ptf).getVertex(pintSourceVertexId);
        IVertex destination = pDynaGraph.getGraph(ptf).getVertex(pintDestinationVertexId);
        
        Thread th = new Thread(() -> {
            pstrShortestPathAlgoName.computeWeightedShortestPathEdges(source, destination);
        });
        
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
        mapAlgorithms.put(th, pstrShortestPathAlgoName);
        
        th.start();
    
        try{
            th.join();
        }catch(InterruptedException ex){
            System.out.println("ShortestPath.runAlgorithm() - ShortestPath Interrupted!");
        }
            catch(Exception e){
            System.out.println("ShortestPath.runAlgorithm() exception in th.join");
            e.printStackTrace();
        }
        
    }
    
    /**
     *
     * @param pDynaGraph
     * @param ptf
     * @return
     */
    public static boolean isDone (
        IDynamicGraph<IVertex,IEdge<IVertex>> pDynaGraph, TimeFrame ptf) {
        
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
        
        return ShortestPathHandler.isDone(pDynaGraph, null);
    }
    
    /**
     * Returns the list of Edges Ids that are a part of the shortest path
     * @param pDynaGraph
     * @param ptf
     * @return
     */
    public static List<Integer> getResults_LastRun (IDynamicGraph pDynaGraph,
            TimeFrame ptf) {
        
        if (isDone(pDynaGraph, ptf)) {
            
            Thread th = mapThreads.get(pDynaGraph).get(ptf);
            System.out.println("thread id :"+th.getName());
            
            lstLastRunShortestPath = mapAlgorithms.get(th).listEdgesPredicted;
            lstAllRunsShortestPath.addAll(lstLastRunShortestPath);
        }
        
        return lstLastRunShortestPath;
    } 
    
    
    public static List<Integer> getResults_AllRuns (IDynamicGraph pDynaGraph, TimeFrame ptf) {
        return lstAllRunsShortestPath;
    }
    
    /**
     * Clears the shortest path results
     * @param pDynaGraph
     * @param ptf
     * @return true if the results have been cleared, else false
     */
    public static boolean clearResults_AllRuns(IDynamicGraph pDynaGraph,
            TimeFrame ptf) {
        try {
            lstLastRunShortestPath.clear();
            lstAllRunsShortestPath.clear();
            return true;
        } catch (Exception ex) {
            System.err.print("ShortestPathHandler.clearResults_AllRuns(): EXCEPTION");
            ex.printStackTrace();
            return false;
        }
    }
    
    
    /**
     * Clears the shortest path results
     * @param pDynaGraph
     * @param ptf
     * @return true if the results have been cleared, else false
     */
    public static boolean clearResults_LastRun(IDynamicGraph pDynaGraph,
            TimeFrame ptf) {
        try {
            lstLastRunShortestPath.clear();
            return true;
        } catch (Exception ex) {
            System.err.print("ShortestPathHandler.clearResults_LastRun(): EXCEPTION");
            ex.printStackTrace();
            return false;
        }
    }
    
    public static void terminateThread(IDynamicGraph<IVertex, IEdge<IVertex>> pDynaGraph, TimeFrame ptf, String pstrCMAlgName) {
        Thread th = mapThreads.get(pDynaGraph).get(ptf);
        if (th.isAlive()) {
            
            ShortestPathAbstract<IVertex, IEdge<IVertex>> shortestPath = mapAlgorithms.get(th);
            mapAlgorithms.remove(th);
            mapThreads.remove(pDynaGraph);
            shortestPath.stopThread();
            
            try{
                th.interrupt();
            } catch(Exception e){
                System.err.print("ShortestPath algorithm thread was interrupted");
            }
            
        }
    }

    public static double getShortestPathDistance(IDynamicGraph<IVertex, IEdge<IVertex>> igraph, TimeFrame tf) {
        
         if (isDone(igraph, tf)) {
            
            Thread th = mapThreads.get(igraph).get(tf);
            System.out.println("thread id :"+th.getName());
            
            shortestPathDistance = mapAlgorithms.get(th).shortestPathSrcToDest;
        }
         
         return shortestPathDistance;
        
    }
    
}
