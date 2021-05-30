/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import algorithm.graph.communitymining.RosvallInfomodMiner;
import config.CommunityMiningParameters;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.reader.GMLReader;
import io.graph.reader.MeerkatReader;
import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author aabnar
 */
public class RosvallInfomodCMTest {
    
    public static void main (String[] args) {
        // load graph
        String basePath = new File("").getAbsolutePath();
        String filename = basePath.concat("/../MeerkatUI/projects/layouttest/Graph (2).meerkat");
        
        MeerkatReader reader = new MeerkatReader(filename);
        
        IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph =
                reader.loadFile();
        
        // System.out.println("Reader is completed");
        
        String[] parameter = new String[1];
        parameter[0] = CommunityMiningParameters.ROSVALLINFOMOD_NUMBEROFATTEMPTS
                + ":10";
        RosvallInfomodMiner<IVertex,IEdge<IVertex>> miner = 
                new RosvallInfomodMiner<>(dynaGraph,
                        dynaGraph.getAllTimeFrames().get(0),parameter);
        
        Thread th = new Thread(miner);
        th.start();
        
        while(th.isAlive()){
            // System.out.println("Thread is running");
        }
        
        System.out.println("Mining is done!");
        
        HashMap<String, List<Integer>> mapComs = miner.hmpCommunities;
        
        for (String com : mapComs.keySet()) {
            System.out.println(com + ":");
            for (int vid : mapComs.get(com)) {
                System.out.print(vid + ",");
            }
            System.out.println();
        }
    }
}
