/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import java.util.HashMap;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public abstract class GraphAlgorithm<V extends IVertex, E extends IEdge<V>> 
                                                            extends Algorithm{
    
    /**
     * on 1/8/2012, Currently we start a new thread of Imetric class for every new request for a metric algorithm from bulls eye class
     * 
     */
    protected static HashMap<Class, HashMap<IDynamicGraph, HashMap<TimeFrame,GraphAlgorithm>>> hmpalgorithm = 
                                                            new HashMap<>();
    
    /**
     *
     */
    protected IDynamicGraph<V,E> dynaGraph;

    /**
     *
     */
    protected TimeFrame tf;
    
    /**
     *
     * @param pIGraph
     * @param tf
     */
    protected GraphAlgorithm(IDynamicGraph<V,E> pIGraph, TimeFrame tf) {
        super();
        this.dynaGraph = pIGraph;
        this.tf = tf;
    }
    
    /**
     *
     * @return
     */
    public IDynamicGraph getGraph() {
        return dynaGraph;
    }
    
    /**
     *
     * @param pClassName
     * @param pIGraph
     * @param tf
     * @return
     */
    protected static boolean containsAlglortihm(Class pClassName, IDynamicGraph pIGraph, TimeFrame tf) {
        return (hmpalgorithm.containsKey(pClassName) &&
                hmpalgorithm.get(pClassName).containsKey(pIGraph) && 
                hmpalgorithm.get(pClassName).get(pIGraph).containsKey(tf));
    }
    
    /**
     *
     * @param pClassName
     * @param pIGraph
     * @param tf
     * @return
     */
    protected static GraphAlgorithm getAlgorithm(Class pClassName, IDynamicGraph pIGraph, TimeFrame tf) {
        return hmpalgorithm.get(pClassName).get(pIGraph).get(tf);
    }
    
    /**
     *
     * @param pClassName
     * @param pIGraph
     * @param tf
     * @param pGraphAlg
     */
    protected static void addAlgorithm(Class pClassName, IDynamicGraph pIGraph, TimeFrame tf, GraphAlgorithm pGraphAlg) {
        if (!hmpalgorithm.containsKey(pClassName)) {
            hmpalgorithm.put(pClassName, new HashMap<>());
        }
        if (!hmpalgorithm.get(pClassName).containsKey(pIGraph)) {
            hmpalgorithm.get(pClassName).put(pIGraph, new HashMap<>());
        }
        
        hmpalgorithm.get(pClassName).get(pIGraph).put(tf, pGraphAlg);
    }
    
    /**
     *
     */
    public abstract void writeToFile();
    
    /** This method is no where used
     *
     * @param pClassName
     * @param pIGraph
     * @param tf
     * @return
    */
    /*
    public static boolean terminateAlgorithm(Class pClassName, IDynamicGraph pIGraph, TimeFrame tf) {
        //TODO: should we remove the algorithm from the map as well when terminated?
        GraphAlgorithm gagRequest = hmpalgorithm.get(pClassName).get(pIGraph).get(tf);
        gagRequest.interrupt();
       
        return gagRequest.isTerminated();
    }
    */
}
