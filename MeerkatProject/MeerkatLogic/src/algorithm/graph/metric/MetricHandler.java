/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.metric;

import algorithm.graph.layout.algorithms.LayoutHandler;
import config.MeerkatClassConfig;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IVertex;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.meerkat.MeerkatBIZ;

/**
 *
 * @author aabnar
 */
public class MetricHandler {
    
    /**
     * At each time only one metric can be run on one IDynamicGraph TimeFrame
     */
    static Map<Class, Map<IDynamicGraph, Map<TimeFrame,Thread>>> mapThreads = new HashMap<>();
    static Map<Thread, IMetric> mapTh2Alg = new HashMap<>();
    
    /**
     * 
     * @param pdynaGraph: IDynamicGraph
     * @param tf : TimeFrame
     * @param pstrCentralityClassId : String
     * @param parameters : String[]
     * @return 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 2016-03-23       Afra            changed the if-else statements to reflection for getting the appropriate metric algorithm.
     */
    public static IMetric getMetricAlgorithm(IDynamicGraph pdynaGraph, 
            TimeFrame tf, 
            String pstrCentralityClassId,
            String[] parameters) {
        
        IMetric imetric = null;
        
        try {
            Class clsMetric;
            Method method;
            
            String strLayoutClassName = MeerkatBIZ.meerkatConfig
                .getClassName(MeerkatClassConfig.METRICS_TAG, pstrCentralityClassId);
            
            String strClassPath = "algorithm.graph.metric." + strLayoutClassName;
            
//            System.out.print("MetricHandler.");
            clsMetric = Class.forName(strClassPath);                                
            method = clsMetric.getMethod("getAlgorithm",IDynamicGraph.class, TimeFrame.class, String[].class);            
            imetric = (IMetric) method.invoke(null, pdynaGraph,tf, parameters);            

        } catch (ClassNotFoundException | NoSuchMethodException 
                | SecurityException | IllegalAccessException 
                | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(LayoutHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return imetric;
    }
    
    /**
     *
     * @param pimtrcAlgorithm
     * @param pdynaGraph
     * @param tf
     */
    public static void RunAlgorithm(IMetric pimtrcAlgorithm, IDynamicGraph pdynaGraph, TimeFrame tf) {
        
        Thread th = new Thread(pimtrcAlgorithm);
        
        if (!mapThreads.containsKey(pimtrcAlgorithm.getClass())) {
            mapThreads.put(pimtrcAlgorithm.getClass(), new HashMap<>());
        }
        if (!mapThreads.get(pimtrcAlgorithm.getClass()).containsKey(pdynaGraph)) {
            mapThreads.get(pimtrcAlgorithm.getClass()).put(pdynaGraph, new HashMap<>());
        }
        
        mapThreads.get(pimtrcAlgorithm.getClass()).get(pdynaGraph).put(tf, th);
        mapTh2Alg.put(th, pimtrcAlgorithm);
        
        th.start();
    }
    
    /**
     *
     * @param pclassName
     * @param pdynaGraph
     * @param tf
     * @return
     */
    public static boolean isResultsReady(Class pclassName, IDynamicGraph pdynaGraph, TimeFrame tf) {
        Thread th = mapThreads.get(pclassName).get(pdynaGraph).get(tf);
        if (th.isAlive()) {
            return false;
        }
        return true;
    }
    
    /**
     *
     * @param pclassName
     * @param pdynaGraph
     * @param tf
     * @return
     */
    public static Map<Integer,Double> getResults(Class pclassName, IDynamicGraph pdynaGraph, TimeFrame tf) {
        if (isResultsReady(pclassName, pdynaGraph, tf)) {
            Thread th = mapThreads.get(pclassName).get(pdynaGraph).get(tf);
            IMetric metric = mapTh2Alg.get(th);
            mapThreads.get(pclassName).remove(pdynaGraph);
            Map<IVertex, Double> scores = new HashMap<>();
            
            
            scores = metric.getScores();
                  
            
            Map<Integer, Double> resultScores = new HashMap<>();
            for (IVertex v : scores.keySet()) {
                resultScores.put(v.getId(), scores.get(v));
            }
            
            return resultScores;
        }
        return null;
    }
}
