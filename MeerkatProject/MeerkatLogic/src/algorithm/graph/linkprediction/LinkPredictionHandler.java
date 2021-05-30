/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.linkprediction;



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
 * @author AICML Administrator
 */
public class LinkPredictionHandler {
    
    
    static Map<IDynamicGraph, Map<TimeFrame, Thread>> mapThreads = new HashMap<>();
    static Map<Thread, LinkPredictor> mapAlgorithms = new HashMap<>();
    
    /**
     * MethodName : getLPAlgorithm
     * Description: creates or loads the static link prediction algorithm.
     * @param pDynaGraph
     * @param ptf
     * @param pstrLPAlgName
     * @param pstrParameters
     * @return
     * @throws ClassNotFoundException
     */
    public static LinkPredictor getLPAlgorithm(IDynamicGraph pDynaGraph,
            TimeFrame ptf,
            String pstrLPAlgName,
            String[] pstrParameters) throws ClassNotFoundException {
        
        
        LinkPredictor<IVertex, IEdge<IVertex>> linkPredictorAlg = null;
        
        try {
            String strClassName = MeerkatBIZ.meerkatConfig 
                    .getClassName(MeerkatClassConfig.LINKPREDICTION_TAG,
                            pstrLPAlgName);
            
            System.out.println("LinkPredictionHandler.getLPAlgorithm(): ClassName = "+strClassName+" with ID "+pstrLPAlgName) ;
            
            String strClassPath = "algorithm.graph.linkprediction." + strClassName;
            
            System.out.println("LinkPredictionHandler.getLPAlgorithm(): classPath = " + strClassPath);
            
            Class clsLinkPrediction = Class.forName(strClassPath);
            Constructor constLP = null;
            
            if (ptf != null) {
                constLP = clsLinkPrediction.getConstructor(
                        IDynamicGraph.class, TimeFrame.class, String[].class);
                linkPredictorAlg = (LinkPredictor) constLP.newInstance(
                        pDynaGraph, ptf, pstrParameters);
            } else {
                constLP = clsLinkPrediction.getConstructor(
                        IDynamicGraph.class, String[].class);
                linkPredictorAlg = (LinkPredictor) constLP.newInstance(
                        pDynaGraph, pstrParameters);
            }
                
            
        } catch (SecurityException ex) {
            Logger.getLogger(LinkPredictionHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(LinkPredictionHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return linkPredictorAlg;
    }
    
    
    /**
     *
     * @param pDynaGraph
     * @param ptf
     * @param pLPAlg
     */
    public static void runAlgorithm(
            IDynamicGraph<IVertex,IEdge<IVertex>> pDynaGraph,
            TimeFrame ptf,
            LinkPredictor<IVertex, IEdge<IVertex>> pLPAlg) {
        
        Thread th = new Thread(pLPAlg);
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
        mapAlgorithms.put(th, pLPAlg);
        
        th.start();
    
        try{
            th.join();
        }catch(InterruptedException ex){
            System.out.println("LinkPredictionHandler.runAlgorithm() - LinkPrediction Thread Interrupted!");
        }
            catch(Exception e){
            System.out.println("LinkPredictionHandler.runAlgorithm() exception in th.join");
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
        
        return LinkPredictionHandler.isDone(pDynaGraph, null);
    }
    
    /**
     *
     * @param pDynaGraph
     * @param ptf
     * @return
     */
    //
    public static List<int[]> getResults (IDynamicGraph pDynaGraph,
            TimeFrame ptf) {
        
        if (!mapThreads.keySet().contains(pDynaGraph) ||
                !mapThreads.get(pDynaGraph).containsKey(ptf)) {
            return new ArrayList<>();
        }
        
        List<int[]> listPredictedEdges;
        if (isDone(pDynaGraph, ptf)) {
            System.out.println("inside loop ");
            Thread th = mapThreads.get(pDynaGraph).get(ptf);
            System.out.println("thread id :"+th.getName());
            LinkPredictor<IVertex, IEdge<IVertex>> linkPredictor = mapAlgorithms.get(th);
            System.out.println("linkPredictor listPredictedEdges Size "+linkPredictor.listEdgesPredicted.size());
            
            listPredictedEdges = linkPredictor.listEdgesPredicted;
            System.out.println("LinkPredictionHandler getresults() : listPredictedEdges size :"+listPredictedEdges.size());
            
            return listPredictedEdges;
        }
        return null;
    } 


    public static void terminateThread(IDynamicGraph<IVertex, IEdge<IVertex>> pDynaGraph, TimeFrame ptf, String pstrLPAlgName) {
        Thread th = mapThreads.get(pDynaGraph).get(ptf);
        if (th.isAlive()) {
            
            LinkPredictor<IVertex, IEdge<IVertex>> linkPredictor = mapAlgorithms.get(th);
            mapAlgorithms.remove(th);
            mapThreads.remove(pDynaGraph);
            linkPredictor.stopThread();
            
            try{
                th.interrupt();
            }catch(Exception e){
                System.err.print("LinkPrecition algorithm thread was interrupted");
            }
            
        }
    }
   
}
