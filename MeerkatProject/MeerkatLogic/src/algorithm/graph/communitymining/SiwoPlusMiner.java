/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.communitymining;

import algorithm.graph.communitymining.siwoplus.SiwoPlus;
import java.util.List;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mahdi
 * @param <V>
 * @param <E>
 */
public class SiwoPlusMiner<V extends IVertex, E extends IEdge<V>>
        extends Miner<V, E> {

    public static final String STR_NAME = "SiwoPlus";
    
    public SiwoPlusMiner(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String[] parameters) {
        super(pIGraph, tf);
    }

    @Override
    public void run() {
        System.out.println("********* SiwoPlus ++++++++");
        mineGraph();
        if(!running){
            return;
        }
        updateDataStructure();
        blnDone = true;
    }
    
    public void mineGraph() {
//        HashMap<Integer, Integer> results = new HashMap<>();
        HashMap<Integer, Integer> results = SiwoPlus.runSiwoPlus(dynaGraph, tf, this.isThreadRunningProperty);
        if (!running) {
            return;
        }
        results.keySet().forEach((vertexId) -> {
            int communityId = results.get(vertexId);
            if (hmpCommunities.containsKey(communityId+"")) {
                hmpCommunities.get(communityId+"").add(vertexId);
            } else {
                List<Integer> listofVertices = new ArrayList<>();
                listofVertices.add(vertexId);
                hmpCommunities.put(communityId+"", listofVertices);
            }
        });
    }
    
    @Override
    public String toString() {
        return STR_NAME;
    }
}
