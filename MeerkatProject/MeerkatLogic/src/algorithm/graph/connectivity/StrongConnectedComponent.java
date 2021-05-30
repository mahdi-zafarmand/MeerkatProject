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
 * Finds all strong components in a graph as sets of vertex sets.
 * @param <V>
 * @param <E>
 */
public class StrongConnectedComponent<V extends IVertex,E extends IEdge<V>> 
                                                    extends GraphAlgorithm<V,E>{

    List<Set<V>> clusterSet;
    
    Set<V> setVertices;
    
    public StrongConnectedComponent(IDynamicGraph<V,E> pIGraph, TimeFrame tf, Set<V> psetVertices) {
        super(pIGraph, tf);
        this.setVertices = psetVertices;
    }
    
    /**
     * Extracts the weak components from an dynaGraph.
     * @return the list of weak components
     */
    @Override
    public void run() {
        ConnectedComponents ccAlg = new ConnectedComponents(dynaGraph, tf, setVertices);
        clusterSet = ccAlg.getStrongComponentAsSet();
        blnDone = true;
        updateRunTime();
    }
    
    /**
     *
     * @return
     */
    public List<Set<V>> getConnectedComponents() {
        return clusterSet;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean updateDataStructure() {
        int clusterIndex = 0;

        for (Set<V> cluster : clusterSet) {
            for (V v : cluster) {
                if (v.getSystemAttributer().getAttributeNames()
                        .contains(MeerkatSystem.STRONGCONNECTEDCOMPONENT)) {
                    if (v.getSystemAttributer().getAttributeUpdateTime(MeerkatSystem.STRONGCONNECTEDCOMPONENT,
                            tf)
                            .before(dtRunTime)) {
                        v.getSystemAttributer().addAttributeValue(MeerkatSystem.STRONGCONNECTEDCOMPONENT, 
                                clusterIndex + "", 
                                dtRunTime,
                                tf);
                    }
                } else {
                    v.getSystemAttributer().addAttributeValue(MeerkatSystem.STRONGCONNECTEDCOMPONENT, 
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
