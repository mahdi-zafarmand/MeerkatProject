/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.linkprediction;

import algorithm.GraphAlgorithm;
import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.graph.impl.Edge;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author AICML Administrator
 * @param <V>
 * @param <E>
 */
public abstract class LinkPredictor<V extends IVertex, E extends IEdge<V>> 
                                                    extends GraphAlgorithm<V,E>{
    
    
    public List<int[]> listEdgesPredicted;
    
    /**
     *
     * @param dynaGraph
     * @param tf
     */
    public LinkPredictor(IDynamicGraph<V,E> dynaGraph, TimeFrame tf){
        
        super(dynaGraph, tf);
        listEdgesPredicted = new ArrayList<>();
        
    }
    
    @Override
    public void writeToFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public synchronized boolean updateDataStructure(){
        
        //edges predicted inserted here
        for(int[] edgePair : listEdgesPredicted){
                
            V v1 = dynaGraph.getVertex(edgePair[0]);

            V v2 = dynaGraph.getVertex(edgePair[1]);
            IEdge edge = new Edge(v1, v2, false);
            edge.getSystemAttributer().addAttributeValue(MeerkatSystem.ISPREDICTED, "true", new Date(), tf);
            //why need to cast edge?
            dynaGraph.addEdge((E)edge, tf);
            
            
        }
        
        System.out.println("LinkPredictor.updateDataStructure()");
        return true;
    }
    
}
