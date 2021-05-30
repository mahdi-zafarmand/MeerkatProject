/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.metric;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public class Authority<V extends IVertex,E extends IEdge<V>> 
                                                extends IMetric<V,E> {
    static HITS hitsAlg;
    /**
     *
     * @param pIGraph
     * @param tf
     * @param parameters
     * @return
     */
    public static HITS getAlgorithm(IDynamicGraph pIGraph, TimeFrame tf, String[] parameters) {
        
        hitsAlg = null;
        try {

//            if (containsAlglortihm(HITS.class, pIGraph, tf)) {
//                hitsAlg = (HITS) GraphAlgorithm.getAlgorithm(HITS.class, pIGraph, tf);
//            } else 
            //removing if condition so that every time a new thread of metric is created.
                hitsAlg = new HITS(pIGraph, tf, parameters);
                addAlgorithm(HITS.class, pIGraph, tf, hitsAlg);
        } catch (SecurityException | IllegalArgumentException 
                ex) {
            Logger.getLogger(IMetric.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (hitsAlg != null) {
            hitsAlg.parseParameters(parameters);
            hitsAlg.setType(HITS.Type.Authority);
        }
        return hitsAlg;
    }

    private Authority(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String[] parameters) {
        super(pIGraph, tf, parameters);
        this.metricAttribute=MeerkatSystem.AUTHORITY;
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
     * @param parameters
     */
    @Override
    protected void parseParameters(String[] parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     */
    @Override
    public void writeToFile() {
        String filename = MeerkatSystem.OUTPUT_DIRECTORY+
                    "graph"+
                    dynaGraph.getGraph(tf).getId()+
                    ".authority";
        
        // MetricWriter.write(filename, hmpBetweennessScore);
    }

    @Override
    public boolean isResultValid() {
        return (hitsAlg.isResultValid());
    }
}
