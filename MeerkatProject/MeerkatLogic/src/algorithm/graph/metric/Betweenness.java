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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
*  Class Name     : metric.Betweenness
*  Created Date   : 2015-07-xx
*  Description    : Computes betweenness centrality. 
*  The shortest path algorithm is better to be replaced by Johnson;s Algorithm.
*  Cause it is more efficient for sparce graphs. 
* 
*  https://en.wikipedia.org/wiki/Johnson%27s_algorithm
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
*                                  updatedDS values in file named *.betweenness
*                                   
* 
*/
public class Betweenness<V extends IVertex,E extends IEdge<V>> 
                                                extends IMetric<V,E> {

    HashMap<V,Double> hmpBetweennessScore;

    private Betweenness(IDynamicGraph<V,E> pIGraph, TimeFrame tf, String[] parameters) {
        super(pIGraph,tf, parameters);
        this.hmpBetweennessScore = new HashMap<>();
        this.metricAttribute=MeerkatSystem.BETWEENNESS;
    }
    
    /**
     *
     * @param pIGraph
     * @param tf
     * @param parameters
     * @return
     */
    public static Betweenness getAlgorithm(IDynamicGraph pIGraph, TimeFrame tf, String[] parameters) {
        Betweenness btwAlg = null;
        try {

//            if (containsAlglortihm(Betweenness.class, pIGraph, tf)) {
//                btwAlg = (Betweenness) GraphAlgorithm.getAlgorithm(Betweenness.class, pIGraph, tf);
//            } else {
                
                //removing if condition so that every time a new thread of metric is created.
                btwAlg = new Betweenness(pIGraph, tf, parameters);
                addAlgorithm(Betweenness.class, pIGraph, tf, btwAlg);
            //}
        } catch (SecurityException | IllegalArgumentException 
                ex) {
            Logger.getLogger(IMetric.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (btwAlg != null) {
            btwAlg.parseParameters(parameters);
        }
        return btwAlg;
    }

    /**
     *
     */
    @Override
    public void run() {
        //TODO put if(!running){ return;} in all the alogorith,.graph.metric files to add facility to 
        //stop the tread at any time - right now we can not stop all these metric threads - once they start
        // they will run until completion
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

            computeAllShortestPathCount();
            if(!running)
                return;
            
            normalize();
            
            if(!running)
                return;
            if (updateDataStructure()) {
                writeToFile();
            }
        }
        
//        System.out.println(blnDone);

        // removing all pointers
        // TODO
        
        // #DEBUG
        /*
        for  (V currentV : hmpBetweennessScore.keySet()) {
            System.out.println("Logic.Betweenness(): VID : "+currentV.getId()+"\tValue: "+hmpBetweennessScore.get(currentV));
        }
        */
        // #ENDDEBUG
        blnDone = true;
    }

    private void initialize() {
        updateRunTime();
        
        for (int vid : dynaGraph.getGraph(tf).getAllVertexIds()) {
            V v = dynaGraph.getVertex(vid);
            hmpBetweennessScore.put(v, 0.0);
        }
    }

    /**
     *
     * @return
     */
    public HashMap<V,Double> getBetweennessScore() {
        return hmpBetweennessScore;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Betweenness";
    }

    /**
     *  Method Name     : updateDataStructure
     *  Created Date    : 2015-07-xx
     *  Description     : Updates the Data structure based on the 
     *                      results being out-dated or not
     *  Version         : 
     *  @author         : Afra 
     *  @return 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-08-25      Talat           Returns a boolean - true if updated - 
     *                                  to conform to the method declaration 
     *                                  in interface
     *  2015-10-06      Afra            - changed the condition when we update
     *                                  the data structure with current computed
     *                                  results.
     * 
    */
    @Override
    public synchronized boolean updateDataStructure() {
        /*
         * Update the date structure only when the COMMUNITY attribute is not 
         * out-dated.
         * Meaning when it was not added yet or the time is older than 
         * dtLastUpdate. 
         */
        if (dtCallTime.compareTo(dynaGraph.getGraph(tf).getLastChangeTime()) > 0) {
            for (V v : hmpBetweennessScore.keySet()) {
                v.getSystemAttributer().addAttributeValue(
                        MeerkatSystem.BETWEENNESS, 
                        hmpBetweennessScore.get(v) + "", 
                        dtCallTime,
                        tf);
            }
            return true;
        }   
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public HashMap<V, Double> getScores() {
        List<Integer> lstVertexIDs = dynaGraph.getGraph(tf).getAllVertexIds();
        String strAttr = MeerkatSystem.BETWEENNESS;
        hmpBetweennessScore.clear();
        
        for (int intVertexID : lstVertexIDs){
            V vtx = dynaGraph.getGraph(tf).getVertex(intVertexID);
            Double value = 0.0;
            value = Double.valueOf(dynaGraph.getGraph(tf).getVertex(intVertexID).getSystemAttributer().getAttributeValue(strAttr, tf));
            hmpBetweennessScore.put(vtx, value);
        }
        return hmpBetweennessScore;
    }
    
    /**
     *
     */
    @Override
    public synchronized void writeToFile() {

        String filename = MeerkatSystem.OUTPUT_DIRECTORY+
                    "graph"+
                    dynaGraph.getGraph(tf).getId()+
                    ".betweenness";
        
        MetricWriter.write(filename, hmpBetweennessScore);
    }
    
    
    /**
     * Normalization of the betweenness scores is done as follows:
     * betweenness score of node v = g(v)
     * normal(g(v)) = (g(v) - min(g)) / (max(g) - min(g)).
    */
    private void normalize(){
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        
        for (double d : hmpBetweennessScore.values()) {
            if(!running)
                return;
            if (min > d) {
                min = d;
            }
            if (max < d) {
                max = d;
            }
        }
        
        for (V v : hmpBetweennessScore.keySet()) {
            if(!running)
                return;
            double normalizedScore = 
                    (hmpBetweennessScore.get(v) - min) / (max - min);
            hmpBetweennessScore.put(v, normalizedScore);
        }
    }
    
    @Override
    public boolean isResultValid() {
        return (SystemDynamicAttributer.hmpAttTime
                .containsKey(MeerkatSystem.BETWEENNESS) 
                &&
                SystemDynamicAttributer.hmpAttTime.get(MeerkatSystem.BETWEENNESS)
                .containsKey(tf)
                &&
                SystemDynamicAttributer.hmpAttTime
                        .get(MeerkatSystem.BETWEENNESS)
                        .get(tf)
                        .after(dynaGraph.getGraph(tf).getLastChangeTime()));
    }

    private boolean isCallTimeValid() {
        return (dtCallTime.after(dynaGraph.getGraph(tf).getLastChangeTime()));
    }

    private void computeAllShortestPathCount() {
        for (int vid : dynaGraph.getGraph(tf).getAllVertexIds()) {
            if(!running)
                return;
            BFS(dynaGraph.getVertex(vid));
        }
    }
    
    private void BFS(V pVRoot) {
        Set<V> visited = new HashSet<>();
        HashMap<V, Integer> level = new HashMap<>();
        LinkedList<V> queue = new LinkedList<>();
        
        queue.add(pVRoot);
        level.put(pVRoot, 0);
        
//        System.out.println("Root ID : " + pVRoot.getId());
        while(!queue.isEmpty()) {
            V current = queue.poll();
//            System.out.println("Vertex ID : " + current.getId()+ ", Level : " + level.get(current));
            if (visited.contains(current)) {
                continue;
            }
            for (V neigh : dynaGraph.getOutgoingNeighbors(current, tf)) {
                if (visited.contains(neigh) ||
                        (level.containsKey(neigh) && 
                        level.get(neigh) <= level.get(current))) {
                    continue;
                }
                
                if (!current.equals(pVRoot)) {
                    hmpBetweennessScore.put(current, hmpBetweennessScore.get(current) + 1);
                }
                level.put(neigh, level.get(current) + 1);
                queue.add(neigh);
            }
            visited.add(current);
        }
    }

    /**
     *
     * @param parameters
     */
    @Override
    protected void parseParameters(String[] parameters) {
        // Do nothin
    }

}
