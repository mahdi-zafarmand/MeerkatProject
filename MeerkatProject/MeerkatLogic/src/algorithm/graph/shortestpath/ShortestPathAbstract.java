/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.shortestpath;

/**
 *
 * @author sankalp
 */

import datastructure.core.graph.classinterface.*;
import algorithm.GraphAlgorithm;
import datastructure.core.TimeFrame;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public abstract class ShortestPathAbstract<V extends IVertex, E extends IEdge<V>> 
                                                    extends GraphAlgorithm<V,E>{

    
    public List<Integer> listEdgesPredicted;
    double shortestPathSrcToDest = Double.MAX_VALUE;
    /**
     *
     * @param pIGraph
     * @param tf
     */
    public ShortestPathAbstract(IDynamicGraph<V,E> pIGraph, TimeFrame tf) {
        super(pIGraph, tf);
        listEdgesPredicted = new ArrayList<>();
    }
    
    abstract void computeWeightedShortestPathEdges(V source, V destination);
}

