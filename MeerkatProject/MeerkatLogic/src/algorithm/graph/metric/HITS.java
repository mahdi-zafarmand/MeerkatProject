package algorithm.graph.metric;

import config.MeerkatSystem;
import datastructure.core.DynamicAttributable.SysDynamicAttributer.SystemDynamicAttributer;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.writer.MetricWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 *
 * @author aabnar
 *
 * @param <V>
 * @param <E>
 */
/**
 * Class Name : metric.HITS Created Date : 2015-07-xx Description : Computes hub
 * and authority score.
 * 
* Implementation based on the wikipedia page:
 * https://en.wikipedia.org/wiki/HITS_algorithm#Authority_Update_Rule
 * 
 *  algorithm given in Section 3 under the heading An Iterative Algorithm
 * - specifically on page no 9 in the paper - 
 * Kleinberg, J. M., “Authoritative sources in a hyperlinked environment,” 
 * Journal of the ACM, 46(5):604632, 1999.
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
 * Change methods: run() and updateDataStructure.
 * 
*
 */
public class HITS<V extends IVertex, E extends IEdge<V>>
        extends IMetric<V, E> {

    /**
     *
     */
    public static final int STEP = 100;

    /**
     *
     */
    public static final Type DEFAULT_TYPE = Type.Authority;
    
    HashMap<V, Double> hmpHubScore = new HashMap<>();
    HashMap<V, Double> hmpAuthorityScore = new HashMap<>();

    Type strType = DEFAULT_TYPE;

    /**
     *
     * @param parameters
     */
    @Override
    protected void parseParameters(String[] parameters) {

    }
    
    /**
     *
     */
    public enum Type {

        /**
         *
         */
        Authority,

        /**
         *
         */
        Hub
    }
    
    /**
     *
     * @param pIGraph
     * @param tf
     * @param parameters
     */
    public HITS(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String[] parameters) {
        super(pIGraph, tf, parameters);
    }

    /**
     *
     * @param algType
     */
    public void setType(Type algType) {
        strType = algType;
    }
    
    /**
     *
     * @return
     */
    public Type getType() {
        return strType;
    }
    
    /**
     *
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
            initialize();

            for (int i = 0; i < STEP; i++) {
                if(!running)
                    return;
                    
                /* Update Authority Scores */
                double normAuth = 0.0;
                for (int vid : dynaGraph.getGraph(tf).getAllVertexIds()) {
                    V v = dynaGraph.getVertex(vid);
                    if (!Thread.interrupted()) {
                        double auth = 0.0;
                        for (V neigh : dynaGraph.getIncomingNeighbors(v, tf)) {
                            auth += hmpHubScore.get(neigh);
                        }
                        hmpAuthorityScore.put(v, auth);
                        normAuth += Math.pow(auth, 2.0);
                    }else{
                        System.out.println("HITS.run() : thread interupted!");
                        return;
                    }
                }
                normAuth = Math.sqrt(normAuth);
                for (int vid : dynaGraph.getGraph(tf).getAllVertexIds()) {
                    V v = dynaGraph.getVertex(vid);
                    hmpAuthorityScore.put(v, hmpAuthorityScore.get(v) / normAuth);
                }

                /* Update Hub Scores */
                double normHub = 0.0;
                for (int vid : dynaGraph.getGraph(tf).getAllVertexIds()) {
                    V v = dynaGraph.getVertex(vid);
                    if (!Thread.interrupted()) {
                        double hub = 0.0;
                        for (V neigh : dynaGraph.getOutgoingNeighbors(v, tf)) {
                            hub += hmpHubScore.get(neigh);
                        }
                        normHub += Math.pow(hub, 2.0);
                        hmpHubScore.put(v, hub);
                        
                    }else{
                        System.out.println("HITS.run() : thread interupted!");
                        return;
                    }
                }
                normHub = Math.sqrt(normHub);


                /* Normalizing Hubs and Authority Scores */
                for (int vid : dynaGraph.getGraph(tf).getAllVertexIds()) {
                    V v = dynaGraph.getVertex(vid);
                    hmpAuthorityScore.put(v, hmpAuthorityScore.get(v) / normAuth);
                    hmpHubScore.put(v, hmpHubScore.get(v) / normHub);
                }
                
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
            hmpHubScore.put(v, 1.0);
            hmpAuthorityScore.put(v, 1.0);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Hub";
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
     * declaration in interface 2015-10-06 Afra - changed the condition when we
     * need to update the data structure with computed results.
     *
     */
    @Override
    public synchronized boolean updateDataStructure() {
        /*
         * Update the date structure only when the COMMUNITY attribute is not 
         * out-dated. Meaning when it was not added yet or the time is older 
         * than dtLastUpdate. 
         */
        if (dtCallTime.compareTo(dynaGraph.getGraph(tf).getLastChangeTime()) > 0) {

            for (V v : hmpAuthorityScore.keySet()) {
                v.getSystemAttributer().addAttributeValue(
                        MeerkatSystem.AUTHORITY,
                        hmpAuthorityScore.get(v) + "",
                        dtCallTime,
                        tf);
            }
        }
        /* else: our results are out-dated and should not be committed*/

        /*
         * Update the date structure only when the COMMUNITY attribute is not 
         * out-dated. Meaning when it was not added yet or the time is older 
         * than dtLastUpdate. 
         */
        if (dtCallTime.compareTo(dynaGraph.getGraph(tf).getLastChangeTime()) > 0) {

            for (V v : hmpHubScore.keySet()) {
                v.getSystemAttributer().addAttributeValue(
                        MeerkatSystem.HUB,
                        hmpHubScore.get(v) + "",
                        dtCallTime,
                        tf);
            }

            return true;
        }
        /* else: our results are out-dated and should not be committed*/
        return false;
    }

    /**
     * 
     * @return 
     */
    @Override
    public HashMap<V, Double> getScores(){
        switch(strType) {
            case Authority:
                return getAuthorityScores();
            case Hub:
                return getHubScores();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public HashMap<V, Double> getAuthorityScores() {
        List<Integer> lstVertexIDs = dynaGraph.getGraph(tf).getAllVertexIds();
        String strAttr = MeerkatSystem.AUTHORITY;
        hmpAuthorityScore.clear();
        
        for (int intVertexID : lstVertexIDs){
            V vtx = dynaGraph.getGraph(tf).getVertex(intVertexID);
            Double value = 0.0;
            value = Double.valueOf(dynaGraph.getGraph(tf).getVertex(intVertexID).getSystemAttributer().getAttributeValue(strAttr, tf));
            hmpAuthorityScore.put(vtx, value);
        }
        return hmpAuthorityScore;
    }
    
    /**
     *
     * @return
     */
    public HashMap<V, Double> getHubScores() {
        
        List<Integer> lstVertexIDs = dynaGraph.getGraph(tf).getAllVertexIds();
        String strAttr = MeerkatSystem.HUB;
        hmpHubScore.clear();
        
        for (int intVertexID : lstVertexIDs){
            V vtx = dynaGraph.getGraph(tf).getVertex(intVertexID);
            Double value = 0.0;
            value = Double.valueOf(dynaGraph.getGraph(tf).getVertex(intVertexID).getSystemAttributer().getAttributeValue(strAttr, tf));
            hmpHubScore.put(vtx, value);
        }
        return hmpHubScore;
    }
    
    
    @Override
    public boolean isResultValid() {
        boolean HITSComputed
                = SystemDynamicAttributer.hmpAttTime.containsKey(MeerkatSystem.HUB)
                || SystemDynamicAttributer.hmpAttTime.containsKey(MeerkatSystem.AUTHORITY);

        if (HITSComputed) {
            Date dtHubTime = SystemDynamicAttributer.hmpAttTime.get(MeerkatSystem.HUB)
                .get(tf);
            Date dtAuthorityTime
                = SystemDynamicAttributer.hmpAttTime.get(MeerkatSystem.AUTHORITY)
                .get(tf);
        

            return (HITSComputed
                    && dtHubTime!=null
                && (dtHubTime.after(dynaGraph.getGraph(tf).getLastChangeTime())
                    && dtAuthorityTime!=null
                && dtAuthorityTime.after(dynaGraph.getGraph(tf).getLastChangeTime())));
        } else {
            return false;
        }
    }

    private boolean isCallTimeValid() {
        return (dtCallTime.after(dynaGraph.getGraph(tf).getLastChangeTime()));
    }

    /**
     *
     */
    @Override
    public void writeToFile() {
        String filename = MeerkatSystem.OUTPUT_DIRECTORY+
                    "graph"+
                    dynaGraph.getId()+
                    ".authority";
        
        MetricWriter.write(filename, hmpAuthorityScore);
        
        filename = MeerkatSystem.OUTPUT_DIRECTORY+
                    "graph"+
                    dynaGraph.getId()+
                    ".hub";
        
        MetricWriter.write(filename, hmpHubScore);
    }

}

