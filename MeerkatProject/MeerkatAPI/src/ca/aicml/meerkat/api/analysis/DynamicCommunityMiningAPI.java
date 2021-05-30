/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.aicml.meerkat.api.analysis;

import algorithm.graph.communitymining.CommunityMiningHandler;
import algorithm.graph.communitymining.DynamicMiner;
import algorithm.graph.communitymining.Miner;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import main.meerkat.MeerkatBIZ;

/**
 *
 * @author aabnar
 */
public class DynamicCommunityMiningAPI {
    
    public Map<Integer, Map<String, List<Integer>>> runDynamicCommunityMiningAlg(int pintProjectID,
            int pintGraphID,
            String[] parameters) {
        
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = meerkatApp.getProject(pintProjectID).getGraph(pintGraphID);

        DynamicMiner<IVertex, IEdge<IVertex>> miner = new DynamicMiner<>(igraph, parameters);
        
        Thread th = new Thread(miner);
        th.start();
        while(th.isAlive());

        Map<TimeFrame, Map<String, List<IVertex>>> result = miner.getResults();
        Map<Integer, Map<String, List<Integer>>> resultforUI = new HashMap<>();
        for (TimeFrame tf : result.keySet()) {
            int tfindex = igraph.getAllTimeFrames().indexOf(tf);
            resultforUI.put(tfindex, new HashMap<>());
            for (String com : result.get(tf).keySet()) {
                resultforUI.get(tfindex).put(com, new LinkedList<>());
                for (IVertex v : result.get(tf).get(com)) {
                    resultforUI.get(tfindex).get(com).add(v.getId());
                }
            }
        }
        
        return resultforUI;
    }
    
}
