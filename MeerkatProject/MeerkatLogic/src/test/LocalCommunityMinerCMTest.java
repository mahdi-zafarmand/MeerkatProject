/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import algorithm.graph.communitymining.LocalCommunityMiner;
import algorithm.graph.communitymining.LocalT;
import config.CommunityMiningParameters;
import config.MeerkatSystem;
import datastructure.core.TimeFrame;
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
public class LocalCommunityMinerCMTest {
    public static void main(String[] args) {
         // load graph
        String basePath = new File("").getAbsolutePath();
        String filename = basePath.concat("/../MeerkatUI/projects/layouttest/Graph (0).meerkat");

        MeerkatReader reader = new MeerkatReader(filename);

        IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph
                = reader.loadFile();

        TimeFrame tf = dynaGraph.getAllTimeFrames().get(0);
        String[] parameter = new String[1];
        parameter[0] = CommunityMiningParameters.LOCALCM_ALGORITHMTYPE + ":" + 
                CommunityMiningParameters.LOCALCM_ALGTYPE_M;
        
        LocalCommunityMiner<IVertex,IEdge<IVertex>> miner = 
                new LocalCommunityMiner<>(dynaGraph,tf,parameter);
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
        
//        for (IVertex v : dynaGraph.getVertices(dynaGraph.getAllTimeFrames().get(0))) {
////            System.out.println(v.getSystemAttributer().getAttributeNames());
//            if (v.getSystemAttributer().getAttributeNames().contains(MeerkatSystem.COMMUNITY)) {
//                System.out.println(v.getId() + " : " + v.getSystemAttributer().getAttributeValue(MeerkatSystem.COMMUNITY, dynaGraph.getAllTimeFrames().get(0)));
//            }
//        }
    }
}
