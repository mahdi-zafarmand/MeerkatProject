package algorithm.graph.metric;

import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: which centrality measure is this class supposed to implement?
 * @author aabnar
 *
 * @param <V>
 * @param <E>
 */
public class NodePositionMetric <V extends IVertex, E extends IEdge<V>> 
                                                        extends IMetric<V,E> {

    /**
     *
     */
    public static final String STR_NAME = "Node Position";

    private final double ALPHA = 0.15;


    private NodePositionMetric(IDynamicGraph<V,E> pIGraph, TimeFrame tf, String[] parameters) {
        super(pIGraph, tf, parameters);
    }

    /**
     *
     */
    @Override
    public void run() {
        blnDone = false;

        PageRank<V, E> pageRank = PageRank.getAlgorithm(dynaGraph, tf, null);
        pageRank.run(); // Was changed from pageRank.compute();
        Map<V, Double> vertexToMetric = pageRank.getPageRankScore();
        for (V vertex : dynaGraph.getAllVertices()) {
                vertexToMetric.put(vertex, pageRank.getPageRankScore().get(vertex));
        }
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
     *  @param 
     *  @return 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-08-25      Talat           Returns a boolean - true if updated - to conform to the method declaration in interface
     * 
    */
    @Override
    public boolean updateDataStructure() {
        dtCallTime = new Date();
        // TODO: update datastructure!
        blnDone = true;
        
        return blnDone;
    }

    /**
     *
     * @return
     */
    @Override
    public HashMap<V, Double> getScores() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     */
    @Override
    public void writeToFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param parameters
     */
    @Override
    protected void parseParameters(String[] parameters) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isResultValid() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
