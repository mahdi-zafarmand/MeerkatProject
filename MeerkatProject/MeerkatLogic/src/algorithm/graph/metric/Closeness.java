package algorithm.graph.metric;

import algorithm.GraphAlgorithm;
import algorithm.graph.shortestpath.DijkstraShortestPath;
import config.MeerkatSystem;
import datastructure.core.DynamicAttributable.SysDynamicAttributer.SystemDynamicAttributer;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.writer.MetricWriter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class Name : metric.Closeness 
 * Created Date : 2015-07-xx 
 * Description : Computes closeness centrality
 * Version : 1
 *
 * @author : Afra
 * 
 * @param <V> : generic type for vertex.
 * @param <E> : generic type for edge.
 * 
 * EDIT HISTORY (most recent at the top) 
 * Date         Author      Description 
 * 2015-10-06   Afra        - adding conditions to check whether computation is necessary and if the
 *                          results in the DS are updated or not. also check whether the results of
 *                          computation are still valid at the time of updating the data structure.
 *                          Change methods: run() and updateDataStructure. - added method writeToFile.
 *                          which writes the updatedDS values in file named *.closeness
 */
public class Closeness<V extends IVertex, E extends IEdge<V>>
        extends IMetric<V, E> {

    /**
     *
     */
    public static String STR_NAME = "Closeness Centrality";

    HashMap<V, Double> hmpClosenessScore = new HashMap<>();

    private Closeness(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String[] parameters) {
        super(pIGraph, tf, parameters);
        this.metricAttribute=MeerkatSystem.CLOSENESS;
    }

    /**
     *
     * @param pIGraph
     * @param tf
     * @param parameters
     * @return
     */
    public static Closeness getAlgorithm(IDynamicGraph pIGraph, TimeFrame tf, String[] parameters) {
        Closeness closenessAlg = null;
        try {
//            if (containsAlglortihm(Closeness.class, pIGraph, tf)) {
//                closenessAlg = (Closeness) GraphAlgorithm.getAlgorithm(Closeness.class, pIGraph, tf);
//            } else 
//            {
            //removing if condition so that every time a new thread of metric is created.
                closenessAlg = new Closeness(pIGraph, tf, parameters);
                addAlgorithm(Closeness.class, pIGraph, tf, closenessAlg);
        } catch (SecurityException | IllegalArgumentException 
                ex) {
            Logger.getLogger(IMetric.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (closenessAlg != null) {
            closenessAlg.parseParameters(parameters);
        }
        return closenessAlg;
    }
     
    /**
     * 
     * @param pIGraph
     * @param parameters
     */
//    public static Closeness getAlgorithm(IDynamicGraph pIGraph, TimeFrame tf, String[] parameters) {
//        if (containsAlglortihm(Closeness.class, pIGraph, tf)) {
//            Closeness clnAlg = (Closeness) GraphAlgorithm.getAlgorithm(Closeness.class, pIGraph, tf);
//            clnAlg.updateCallTime();
//            return (Closeness) GraphAlgorithm.getAlgorithm(Closeness.class, pIGraph, tf);
//        } else {
//            Closeness clnAlg = new Closeness(pIGraph, tf, parameters);
//            addAlgorithm(Closeness.class, pIGraph, tf, clnAlg);
//            return clnAlg;
//        }
//    }

    public void run() {
        updateCallTime();
        blnDone = false;

        /**
         * if we have already computed this measure and the latest time of
         * computation > latest update time on the graph, then we should not run
         * the algorithm again. (we will get same result as we already have
         * saved.)
         *
         * GUT = graph update time LCT = last computation time CT = call time of
         * the algorithm
         *
         * run the algorithm only when: (metric not computed before OR GUT >
         * LCT) AND CT > GUT
         *
         * or not run the algorithm when: ![(metric not computed before OR GUT >
         * LCT) AND CT > GUT] = (metric computed AND GUT less/equal LCT) OR CT
         * less/equal GUT.
         */
        if (!isResultValid() && isCallTimeValid()) {
            
            initialize();

            DijkstraShortestPath<V, E> shortestPaths
                    = new DijkstraShortestPath<>(dynaGraph, tf);
            for (int vid : dynaGraph.getGraph(tf).getAllVertexIds()) {
                if(!running)
                    return;
                V v = dynaGraph.getVertex(vid);
                HashMap<V, Double> distances
                        = shortestPaths.computeAllPairsSPUnWeighted(v);
                int totalDistance = 0;
                for (V vv : distances.keySet()) {
                    if (!Thread.interrupted()) {
                        if (distances.get(vv) < dynaGraph.getGraph(tf).getVertexCount()) {
                            totalDistance += distances.get(vv);
                        }
                    }else
                        return;
                }
                if(totalDistance>0)
                    hmpClosenessScore.put(v, 1.0 / totalDistance);
                else
                    hmpClosenessScore.put(v, 0.0);
            }

            if (updateDataStructure()) {
                writeToFile();
            }
        }
        blnDone = true;
    }

    private void initialize() {
        updateRunTime();

        for (int vid : dynaGraph.getGraph(tf).getAllVertexIds()) {
            V v = dynaGraph.getVertex(vid);
            hmpClosenessScore.put(v, 0.0);
        }
    }

    public HashMap<V, Double> getClosenessScores() {
        return hmpClosenessScore;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return STR_NAME;
    }

    /**
     * Method Name : updateDataStructure Created Date : 2015-07-xx Description :
     * Updates the Data structure based on the results being out-dated or not
     * Version :
     *
     * @author : Afra
     *
     * @param
     * @return
     *
     * EDIT HISTORY (most recent at the top) Date Author Description 2015-08-25
     * Talat Returns a boolean - true if updated - to conform to the method
     * declaration in interface 2015-10-06 Afra - changed condition when we
     * should update data structure with computed results.
     *
     */
    @Override
    public synchronized boolean updateDataStructure() {
        /*
         * Update the date structure only when the COMMUNITY attribute is not out-dated.
         * Meaning when it was not added yet or the time is older than dtLastUpdate. 
         */
        if (dtCallTime.compareTo(dynaGraph.getGraph(tf).getLastChangeTime()) > 0) {

            for (V v : hmpClosenessScore.keySet()) {
                v.getSystemAttributer().addAttributeValue(MeerkatSystem.CLOSENESS,
                        hmpClosenessScore.get(v) + "",
                        dtCallTime,
                        tf);
            }
            return true;
        }
        return false;
    }

    @Override
    public HashMap<V, Double> getScores() {
        List<Integer> lstVertexIDs = dynaGraph.getGraph(tf).getAllVertexIds();
        String strAttr = MeerkatSystem.CLOSENESS;
        hmpClosenessScore.clear();
        
        for (int intVertexID : lstVertexIDs){
            V vtx = dynaGraph.getGraph(tf).getVertex(intVertexID);
            Double value = 0.0;
            value = Double.valueOf(dynaGraph.getGraph(tf).getVertex(intVertexID).getSystemAttributer().getAttributeValue(strAttr, tf));
            hmpClosenessScore.put(vtx, value);
        }
        return hmpClosenessScore;
    }

    /**
     *
     */
    @Override
    public void writeToFile() {
        String filename = MeerkatSystem.OUTPUT_DIRECTORY
                + "graph"
                + dynaGraph.getGraph(tf).getId()
                + ".closeness";

        MetricWriter.write(filename, hmpClosenessScore);
    }

    @Override
    public boolean isResultValid() {

        return (SystemDynamicAttributer.hmpAttTime.containsKey(MeerkatSystem.CLOSENESS)
                && SystemDynamicAttributer.hmpAttTime.get(MeerkatSystem.CLOSENESS)
                .containsKey(tf)
                &&
                SystemDynamicAttributer.hmpAttTime.get(MeerkatSystem.CLOSENESS)
                .get(tf)
                .after(dynaGraph.getGraph(tf).getLastChangeTime()));

        
    }

    private boolean isCallTimeValid() {
        return (dtCallTime.after(dynaGraph.getGraph(tf).getLastChangeTime()));
    }

    /**
     *
     * @param parameters
     */
    @Override
    protected void parseParameters(String[] parameters) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
