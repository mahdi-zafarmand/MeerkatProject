/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.layout.algorithms;

import config.MeerkatClassConfig;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.meerkat.MeerkatBIZ;

/**
 * Class Name : LayoutHandler 
 * Created Date : 2016 January
 * Description : Handle Layout algorithm threads. Runs the algorithms in 
 *                 a thread and keeps them in a map, checks if the algorithm
 *                 is done running. Terminates the threads and returns the results
 *                 of successfully completed algorithms.
 * Version : 1.0
 *
 * @author : Afra
 *
 * EDIT HISTORY (most recent at the top) 
 * Date         Author      Description 
 * 2016-03-16   Afra        changed runLayoutAlgorithm from if statement to
 *                          reflection from the classID.
 * 2016-01      Afra        Added runLayoutAlgorithm, isDone, getResults
 * @param <E> type of edges
 */
public class LayoutHandler {

    private static Map<IDynamicGraph, Map<TimeFrame, Thread>> mapThreads
            = new HashMap<>();

    private static Map<Thread, Layout> mapTh2Layout = new HashMap<>();

    /**
     * Method Name : getLayoutAlgorithm
     * Description : initializes the layout algorithm through reflection.
     * Created Date : 2016 January 
     * Description : Version : 1.0
     *
     * @author : Afra
     *
     * @param igraph
     * @param tf
     * @param pstrLayoutAlgName
     * @param parameters
     * 
     * @return 
     *      the layout algorithm
     *
     * EDIT HISTORY (most recent at the top) 
     * Date         Author  Description 
     * 2016-03-16   Afra    replaced if statements with reflection.
     *
     */
    public static Layout getLayoutAlgorithm(
            IDynamicGraph<IVertex, IEdge<IVertex>> igraph,
            TimeFrame tf,
            String pstrLayoutAlgName,
            String[] parameters) {

        Layout layout = null;
        String strLayoutClassName = MeerkatBIZ.meerkatConfig
                .getClassName(MeerkatClassConfig.LAYOUTS_TAG, pstrLayoutAlgName);
        
        System.out.println("LayoutHandler: getLayoutAlgorithm: ClassID: " + pstrLayoutAlgName);
        System.out.println("LayoutHandler: getLayoutAlgorithm: ClassName: " + strLayoutClassName);
        
        try {
            Class clsLayout;
            Constructor constructor;
//            Layout drdObj;
//            Method methodRead;
            String strClassPath = "algorithm.graph.layout.algorithms." + strLayoutClassName;
            clsLayout = Class.forName(strClassPath);                                
            constructor = clsLayout.getConstructor(IDynamicGraph.class, TimeFrame.class, String[].class);            
            layout = (Layout) constructor.newInstance(igraph,tf,parameters);            
//            methodRead = clsLayout.getMethod("run");            
//            methodRead.invoke(drdObj);

        } catch (ClassNotFoundException | NoSuchMethodException 
                | SecurityException | IllegalAccessException 
                | IllegalArgumentException | InvocationTargetException 
                | InstantiationException ex) {
            Logger.getLogger(LayoutHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return layout;
    }

    
    /**
     * Method Name : runLayoutAlgorithm
     * Description : runs the already initialized layoutAlgorithm in a thread.
     * Created Date : 2016 January 
     * Description : Version : 1.0
     *
     * @author : Afra
     *
     * @param pLayout
     * @param igraph
     * @param tf 
     *
     * EDIT HISTORY (most recent at the top) 
     * Date         Author  Description 
     *
     */
    public static void runLayoutAlgorithm(Layout pLayout,
            IDynamicGraph<IVertex, IEdge<IVertex>> igraph,
            TimeFrame tf) {

        Thread th = new Thread(pLayout);
        if (!mapThreads.containsKey(igraph)) {
            mapThreads.put(igraph, new HashMap<>());
        }
        mapThreads.get(igraph).put(tf, th);
        mapTh2Layout.put(th, pLayout);
        
        th.setDaemon(true);
        th.start();
    }

    
    /**
     * Method Name : isDone
     * Description : checks if the algorithm is done running successfully.
     * Created Date : 2016 January 
     * Description : Version : 1.0
     *
     * @author : Afra
     *
     * @param igraph
     * @param tf 
     *
     * EDIT HISTORY (most recent at the top) 
     * Date         Author  Description 
     * @return  
     *
     */
    public static boolean isDone(IDynamicGraph<IVertex, IEdge<IVertex>> igraph,
            TimeFrame tf) {

        Thread th = mapThreads.get(igraph).get(tf);
//        System.out.println("isAlive : " + th.isAlive());
//        System.out.println("isDone : " + mapTh2Layout.get(th).isDone());
        if (th.isAlive() && !mapTh2Layout.get(th).isDone()) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param igraph dynamic graph
     * @param tf time frame / timestamp
     * @return Map from Vertex Id to an array of Double, where Double[0] = x &
     * Double[1] = y;
     */
    
    /**
     * Method Name : getResults
     * Description : returns the positions of the vertices if the 
     *                  layout algorithm is done running successfully.
     * Created Date : 2016 January 
     * Description : Version : 1.0
     *
     * @author : Afra
     *
     * @param igraph
     * @param tf 
     * 
     * @return 
     *      map from vertex Id to a double[] where : double[0] = x and double[1] = y.
     *
     * EDIT HISTORY (most recent at the top) 
     * Date         Author  Description 
     *
     */
    public static Map<Integer, Double[]> getResults(
            IDynamicGraph<IVertex, IEdge<IVertex>> igraph,
            TimeFrame tf) {

        if (isDone(igraph, tf)) {
            Thread th = mapThreads.get(igraph).get(tf);
            Layout layout = mapTh2Layout.get(th);

            return layout.getResults();
        }

        return null;
    }

    
    /**
     * Method Name : terminateAlgorithm
     * Description : terminates the thread which is running the algorithm.
     * Created Date : 2016 January 
     * Description : Version : 1.0
     *
     * @author : Afra
     * @param igraph
     * @param pLayout
     *
     * @param Layout
     * @param tf
     * @param TimeFrame
     *
     * @return
     *      whether the algorithm was done before termination or not.
     * 
     * EDIT HISTORY (most recent at the top) 
     * Date         Author  Description 
     *
     */
    public static boolean terminateAlgorithm(
            IDynamicGraph<IVertex, IEdge<IVertex>> igraph,
            TimeFrame tf
        ) {
        try {
            if (!isDone(igraph, tf)) {
                Thread th = mapThreads.get(igraph).get(tf);
                Layout layoutAlg = mapTh2Layout.get(th);
                layoutAlg.stopThread();
                
                mapTh2Layout.remove(th);
                mapThreads.remove(igraph);
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
        return false ;
    }
}
