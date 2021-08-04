/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.communitymining;


import algorithm.graph.communitymining.louvain.Louvain;
import java.util.List;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Generates communities based on the louvain algorithm
 *
 * @author Abhi
 * @param <V>
 * @param <E>
 *
 */
public class LouvainMiner<V extends IVertex, E extends IEdge<V>>
        extends Miner<V, E> {

    /*************************
     *Variables
     ************************/
    public static final String STR_NAME = "Louvain";


    /**
     * Constructor
     * Version: 1.0
     * @param pIGraph
     *          dynamic graph (not null)
     * @param tf
     * @param parameters
     */
    public LouvainMiner(IDynamicGraph<V, E> pIGraph,
            TimeFrame tf,
            String[] parameters) {
        super(pIGraph, tf);
    }
    
    
    
     /*************************
     *Methods
     ************************/

    /**
     *
     */
    @Override
    public void run() {
        System.out.println("********* LOUVAIN ++++++++");
        mineGraph();
        if(!running){
            return;
        }
        updateDataStructure();
        blnDone = true;
    }

    /**
     * MethodName: mineGraph
     * Description: call Louvain algorithm on graph and convert results to our format
     * Version: 1.0
     * Author: Abhi
     * 
     * EDIT HISTORY
     * DATE         AUTHOR      DESCRIPTION
     */
    public void mineGraph() {  
        /*
        Louvain runs only on undirected graphs
        */
        //1. run louvain
        //2. convert the results to fill hMapCommunities
        
        
        
        // HashMap<Integer, Integer> results = map between vertex to community id
        // Always one vertex belongs to one community only.
        
        // put thread stopping code in louvain 
        HashMap<Integer, Integer> results = Louvain.runLouvain(dynaGraph, tf, this.isThreadRunningProperty);
        if(!running){
            return;
        }
        for(int vertexId : results.keySet()){
            int communityId = results.get(vertexId);
            if(hmpCommunities.containsKey(communityId+"")){
                hmpCommunities.get(communityId+"").add(vertexId);
            }else{
                List<Integer> listOfVertices = new ArrayList<>();
                listOfVertices.add(vertexId);
                hmpCommunities.put(communityId+"", listOfVertices);
            }
            
        }
    }

    
    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        return STR_NAME;
    }
}
