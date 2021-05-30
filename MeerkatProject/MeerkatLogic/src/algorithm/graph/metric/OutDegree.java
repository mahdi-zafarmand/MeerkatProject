package algorithm.graph.metric;

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
*  Class Name     : metric.OutDegree
*  Created Date   : 2015-07-xx
*  Description    : Computes outdegree centrality.
*          
* 
*  Version         : 1
*  @author        : Afra
* 
*  @param <V>       : generic type for vertex. 
*  @param <E>       : generic type for edge.
* 
*  EDIT HISTORY (most recent at the top)
*  Date            Author          Description
*  2015-10-06      Afra            - adding conditions to check whether computation
*                                  is necessary and if the results in the DS are
*                                  updated or not.
*                                  also check whether the results of computation
*                                  are still valid at the time of updating the
*                                  data structure. Change methods: run() and
*                                  updateDataStructure.
*                                  - added method writeToFile. which writes the
*                                  updatedDS values in file named *.outdegree
*                                   
* 
*/
public class OutDegree<V extends IVertex, E extends IEdge<V>> 
                                                    extends IMetric<V,E> {

    /**
     *
     */
    public static String STR_NAME = "OutDegree Metric";

    HashMap<V, Double> hmpOutDegreeScore = new HashMap<>();


    private OutDegree(IDynamicGraph<V,E> pIGraph, TimeFrame tf, String[] parameters) {
            super(pIGraph, tf, parameters);
            this.metricAttribute=MeerkatSystem.OUTDEGREE;
    }

    /**
     *
     * @param pIGraph
     * @param tf
     * @param parameters
     * @return
     */
    public static OutDegree getAlgorithm(IDynamicGraph pIGraph, TimeFrame tf, String[] parameters) {
        OutDegree outdegreeAlg = null;
        try {
//            if (containsAlglortihm(OutDegree.class, pIGraph, tf)) {
//                outdegreeAlg = (OutDegree) GraphAlgorithm.getAlgorithm(OutDegree.class, pIGraph, tf);
//            } else {
                //removing if condition so that every time a new thread of metric is created.
                outdegreeAlg = new OutDegree(pIGraph, tf, parameters);
                addAlgorithm(OutDegree.class, pIGraph, tf, outdegreeAlg);
            //}
        } catch (SecurityException | IllegalArgumentException 
                ex) {
            Logger.getLogger(IMetric.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (outdegreeAlg != null) {
            outdegreeAlg.parseParameters(parameters);
        }
        return outdegreeAlg;
    }

    /**
     *
     */
    @Override
    public void run() {
        updateCallTime();
        blnDone = false;

        /**
         * if we have already computed this measure and
         * the latest time of computation > latest update time on the graph,
         * then we should not run the algorithm again. (we will get same
         * result as we already have saved.)
         * 
         * GUT = graph update time
         * LCT = last computation time
         * CT = call time of the algorithm
         * 
         * run the algorithm only when:
         *          (metric not computed before OR GUT > LCT) AND CT > GUT
         * 
         * or not run the algorithm when:
         *          ![(metric not computed before OR GUT > LCT) AND CT > GUT]
         *          = (metric computed AND GUT less/equal LCT) OR CT less/equal GUT. 
         */
        
        if (!isResultValid() && isCallTimeValid()) {
            
            initialize();
            
            for (int vertexId : dynaGraph.getGraph(tf).getAllVertexIds()) {
                if(!running)
                    return;
                V vertex = dynaGraph.getVertex(vertexId);
                // Calculate the in degree of the vertex.
                double metric = 0;
                for (int edgeId : dynaGraph.getGraph(tf).getAllEdgeIds()) {
                    E edge = dynaGraph.getEdge(edgeId);
                    if (!Thread.interrupted()) {
                        if (edge.isDirected()) {
                            if (edge.getSource().equals(vertex)) {
                                metric++;
                            }
                        } else {
                            if (edge.getDestination().equals(vertex)
                                || edge.getSource().equals(vertex)) {
                                    metric++;
                            }
                        }
                    }else
                        return;
                }
                // Store the metric.
                hmpOutDegreeScore.put(vertex, metric);
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
            hmpOutDegreeScore.put(v,0.0);
        }
    }
    
    /**
     *
     * @return
     */
    public HashMap<V,Double> getOutDegreeScore() {
        return hmpOutDegreeScore;
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
     *  Method Name     : updateDataStructure
     *  Created Date    : 2015-07-xx
     *  Description     : Updates the Data structure based on the results being out-dated or not
     *  Version         : 
     *  @author         : Afra
     * 
     *  @param 
     *  @return 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-08-25      Talat           Returns a boolean - true if updated - to conform to the method declaration in interface
     *  2015-10-06      Afra            - changed conditions when we need to 
     *                                  update the DS with computed results.
    */
    @Override
    public synchronized boolean updateDataStructure() {
        /*
         * Update the date structure only when the COMMUNITY attribute is not out-dated.
         * Meaning when it was not added yet or the time is older than dtLastUpdate. 
         */
        if (dtCallTime.compareTo(dynaGraph.getGraph(tf).getLastChangeTime()) >= 0 ) {
            for (V v : hmpOutDegreeScore.keySet()) {
                v.getSystemAttributer().addAttributeValue(
                        MeerkatSystem.OUTDEGREE, 
                        hmpOutDegreeScore.get(v)+"",
                        dtCallTime,
                        tf);
            }

            return true;
        }
        return false;
        /* else: our results are out-dated and should not be commited. */
    }

    /**
     *
     * @return
     */
    @Override
    public HashMap<V, Double> getScores() {
        List<Integer> lstVertexIDs = dynaGraph.getGraph(tf).getAllVertexIds();
        String strAttr = MeerkatSystem.OUTDEGREE;
        hmpOutDegreeScore.clear();
        
        for (int intVertexID : lstVertexIDs){
            V vtx = dynaGraph.getGraph(tf).getVertex(intVertexID);
            Double value = 0.0;
            value = Double.valueOf(dynaGraph.getGraph(tf).getVertex(intVertexID).getSystemAttributer().getAttributeValue(strAttr, tf));
            hmpOutDegreeScore.put(vtx, value);
        }
        return hmpOutDegreeScore;
    }

    /**
     *
     */
    @Override
    public void writeToFile() {
        String filename = MeerkatSystem.OUTPUT_DIRECTORY+
                    "graph"+
                    dynaGraph.getGraph(tf).getId()+
                    ".outdegree";
        
        MetricWriter.write(filename, hmpOutDegreeScore);
    }

    @Override
     public boolean isResultValid() {
        return (SystemDynamicAttributer.hmpAttTime.containsKey(MeerkatSystem.OUTDEGREE) 
                &&
                SystemDynamicAttributer.hmpAttTime.get(MeerkatSystem.OUTDEGREE)
                .containsKey(tf)
                &&
                SystemDynamicAttributer.hmpAttTime.get(MeerkatSystem.OUTDEGREE)
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
