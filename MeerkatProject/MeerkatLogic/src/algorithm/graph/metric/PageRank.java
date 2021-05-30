package algorithm.graph.metric;

import algorithm.GraphAlgorithm;
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
 * Class Name : metric.PageRank Created Date : 2015-07-xx Description : Computes
 * pagerank centrality.
 * 
*
 * Version : 1
 *
 * @author : Afra
 * 
* @param <V> : generic type for vertex.
 * @param <E> : generic type for edge.
 * 
* EDIT HISTORY (most recent at the top) Date Author Description 2015-10-06 Afra
 * - adding conditions to check whether computation is necessary and if the
 * results in the DS are updated or not. also check whether the results of
 * computation are still valid at the time of updating the data structure.
 * Change methods: run() and updateDataStructure. - added method writeToFile.
 * which writes the updatedDS values in file named *.pagerank
 * 
*
 */
public class PageRank<V extends IVertex, E extends IEdge<V>>
        extends IMetric<V, E> {

    /**
     *
     */
    public static final String STR_NAME = "Page Rank";

    private final double Damping_Factor = 0.85;

    private final HashMap<Integer, Double> hmpPageRankScore = new HashMap<>();
    HashMap<V, Double> hmpVertexToPRScore = new HashMap<>();

    private PageRank(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String[] parameters) {
        super(pIGraph, tf, parameters);
        this.metricAttribute=MeerkatSystem.PAGERANK;
    }
    
    /**
     *
     * @param pIGraph
     * @param tf
     * @param parameters
     * @return
     */
    public static PageRank getAlgorithm(IDynamicGraph pIGraph, TimeFrame tf, String[] parameters) {
        PageRank pgrnkAlg = null;
        try {
//            if (containsAlglortihm(PageRank.class, pIGraph, tf)) {
//                pgrnkAlg = (PageRank) GraphAlgorithm.getAlgorithm(PageRank.class, pIGraph, tf);
//            } else {
                //removing if condition so that every time a new thread of metric is created.
                pgrnkAlg = new PageRank(pIGraph, tf, parameters);
                addAlgorithm(PageRank.class, pIGraph, tf, pgrnkAlg);
            //}
        } catch (SecurityException | IllegalArgumentException 
                ex) {
            Logger.getLogger(IMetric.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (pgrnkAlg != null) {
            pgrnkAlg.parseParameters(parameters);
        }
        return pgrnkAlg;
    }

    /**
     * this method computes PageRank based on the original formula:
     *
     * P(A) = (1-d) + d*(sum_{v \in N(A)}(P(v)/|N(v)|)
     */
    @Override
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

            System.out.println("Calculating PR started!");
            initialize();

            HashMap<Integer, Double> newVertexToMetric = new HashMap<>();

            // set iterations to 100. as in the original paper for web pages.
            for (int i = 0; i < 100; i++) {
                for (int vId : dynaGraph.getGraph(tf).getAllVertexIds()) {
                    if(!running)
                        return;
                    V v = dynaGraph.getVertex(vId);
                    if (!Thread.interrupted()) {
                        double dblNewScore = 0.0;

                        dblNewScore = (1 - Damping_Factor)
                                + Damping_Factor * calculateNeighborScore(v);

                        newVertexToMetric.put(vId, dblNewScore);
                    }else
                        return;
                }

                for (int vId : newVertexToMetric.keySet()) {
                    hmpPageRankScore.put(vId, newVertexToMetric.get(vId));
                }
            }

            for (int vid : dynaGraph.getGraph(tf).getAllVertexIds()) {
                V v = dynaGraph.getVertex(vid);
                hmpVertexToPRScore.put(v, hmpPageRankScore.get(
                        v.getId()));
            }

            if (updateDataStructure()) {
                writeToFile();
            }
        }
        blnDone = true;
    }

    private void initialize() {
        updateRunTime();

        double initialValue = 1.0;  // initial score of nodes
        for (int vid : dynaGraph.getGraph(tf).getAllVertexIds()) {
            V v = dynaGraph.getVertex(vid);
            hmpPageRankScore.put(v.getId(), initialValue);
        }

    }

    private double calculateNeighborScore(V v) {
        double neighborScore = 0.0;
        for (V neigh : dynaGraph.getNeighbors(v, tf)) {
            neighborScore += hmpPageRankScore.get(neigh.getId())
                    / dynaGraph.getNeighbors(neigh, tf).size();
        }

        return neighborScore;
    }

    /**
     *
     * @return
     */
    public HashMap<V, Double> getPageRankScore() {
        return hmpVertexToPRScore;
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
     * @param
     * @return
     *
     * EDIT HISTORY (most recent at the top) Date Author Description 2015-08-25
     * Talat Returns a boolean - true if updated - to conform to the method
     * declaration in interface 2015-10-06 Afra - changed conditions when we
     * need to update the DS with computed results.
     */
    @Override
    public synchronized boolean updateDataStructure() {
        /*
         * Update the date structure only when the COMMUNITY attribute is not out-dated.
         * Meaning when it was not added yet or the time is older than dtLastUpdate. 
         */
        if (dtCallTime.compareTo(dynaGraph.getGraph(tf).getLastChangeTime()) >= 0) {

            for (V v : hmpVertexToPRScore.keySet()) {
                v.getSystemAttributer().addAttributeValue(
                        MeerkatSystem.PAGERANK,
                        hmpVertexToPRScore.get(v) + "",
                        dtCallTime,
                        tf);
            }
            return true;
        }
        /* else: our result is out-dated and should not be commited.*/
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public HashMap<V, Double> getScores() {
        List<Integer> lstVertexIDs = dynaGraph.getGraph(tf).getAllVertexIds();
        String strAttr = MeerkatSystem.PAGERANK;
        hmpVertexToPRScore.clear();
        
        for (int intVertexID : lstVertexIDs){
            V vtx = dynaGraph.getGraph(tf).getVertex(intVertexID);
            Double value = 0.0;
            value = Double.valueOf(dynaGraph.getGraph(tf).getVertex(intVertexID).getSystemAttributer().getAttributeValue(strAttr, tf));
            hmpVertexToPRScore.put(vtx, value);
        }
        return hmpVertexToPRScore;
    }

    /**
     *
     */
    @Override
    public void writeToFile() {
        String filename = MeerkatSystem.OUTPUT_DIRECTORY
                + "graph"
                + dynaGraph.getGraph(tf).getId()
                + ".pagerank";

        MetricWriter.write(filename, hmpVertexToPRScore);
    }

    @Override
    public boolean isResultValid() {
        return (SystemDynamicAttributer.hmpAttTime.containsKey(MeerkatSystem.PAGERANK)
                &&
                SystemDynamicAttributer.hmpAttTime.get(MeerkatSystem.PAGERANK)
                .containsKey(tf)
                && SystemDynamicAttributer.hmpAttTime.get(MeerkatSystem.PAGERANK)
                .get(tf)
                .after(dynaGraph.getGraph(tf).getLastChangeTime()));
    }

    private boolean isCallTimeValid() {
        return (dtCallTime.after(dynaGraph.getGraph(tf).getLastChangeTime())
                || dtCallTime.equals(dynaGraph.getGraph(tf).getLastChangeTime()));
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
