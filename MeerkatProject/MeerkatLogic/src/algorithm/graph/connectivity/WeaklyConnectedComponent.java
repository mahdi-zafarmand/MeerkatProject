/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.connectivity;

import algorithm.GraphAlgorithm;
import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.List;
import java.util.Set;

/**
 * Finds all weak components in a graph as sets of vertices.  
 * @param <V>
 * @param <E>
 */
public class WeaklyConnectedComponent<V extends IVertex,E extends IEdge<V>> 
                                                    extends GraphAlgorithm<V,E>{

    List<Set<V>> lltAllConnectecComponents;
    Set<V> setVertices;
    
    /**
     *
     * @param pIGraph
     * @param tf
     */
    public WeaklyConnectedComponent(IDynamicGraph<V,E> pIGraph, TimeFrame tf, Set<V> previousCommunity) {
        super(pIGraph, tf);
        this.setVertices = previousCommunity;
    }
    
    /**
     * Extracts the weak components from an dynaGraph.
     * @return the list of weak components
     */
    @Override
    public void run() {
        ConnectedComponents ccAlg = new ConnectedComponents(dynaGraph, tf, setVertices);
        lltAllConnectecComponents = ccAlg.getWeakComponentAsSet();
//        System.out.println("WeaklyConnectedComponent.run() : lstOfComponents --> " + lltAllConnectecComponents);
        blnDone = true;
        updateRunTime();
    }
    
    /**
     *
     * @return
     */
    public List<Set<V>> getConnectedComponents() {
//        System.out.println("WeaklyConnectedComponent.getConnectedComponents() : lstOfComponents --> " + lltAllConnectecComponents);
        return lltAllConnectecComponents;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean updateDataStructure() {
        int clusterIndex = 0;
        for (Set<V> cluster : lltAllConnectecComponents) {
            for (V v : cluster) {
                if (v.getSystemAttributer().getAttributeNames()
                        .contains(MeerkatSystem.WEAKCONNECTEDCOMPONENT)) {
                    if (v.getSystemAttributer().getAttributeUpdateTime(MeerkatSystem.WEAKCONNECTEDCOMPONENT,
                            tf)
                        .before(dtRunTime)) {
                        v.getSystemAttributer().addAttributeValue(MeerkatSystem.WEAKCONNECTEDCOMPONENT, 
                                clusterIndex + "", 
                                dtRunTime,
                                tf);
                    }
                } else {
                    v.getSystemAttributer().addAttributeValue(MeerkatSystem.WEAKCONNECTEDCOMPONENT, 
                            clusterIndex + "", 
                            dtRunTime,
                            tf);
                }
            }
            clusterIndex++;
        }
        return true;
    }
    
    /**
     *
     */
    @Override
    public void writeToFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
