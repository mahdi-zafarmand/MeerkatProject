/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import algorithm.graph.communitymining.RosvallInfomapMiner;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.reader.MeerkatReader;
import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author aabnar
 */
public class RosvallInfomapCMTest {
    public static void main(String[] args) {
        // load graph
        String basePath = new File("").getAbsolutePath();
        String filename = 
                basePath.concat("/../MeerkatUI/projects/layouttest/Graph (2).meerkat");

        MeerkatReader reader = new MeerkatReader(filename);

        IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph
                = reader.loadFile();

        String[] parameter = new String[2];
        parameter[0] = "undirected";
        parameter[1] = "10";
        RosvallInfomapMiner<IVertex,IEdge<IVertex>> miner =
                new RosvallInfomapMiner<>(dynaGraph,dynaGraph.getAllTimeFrames().get(3),parameter);

        Thread th = new Thread(miner);
        th.start();

        while(th.isAlive());

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
