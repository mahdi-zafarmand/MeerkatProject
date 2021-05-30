/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import algorithm.graph.communitymining.CommunityMiningHandler;
import algorithm.graph.communitymining.Miner;
import config.CommunityMiningParameters;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.reader.MeerkatReader;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.meerkat.MeerkatBIZ;

/**
 *
 * @author aabnar
 */
public class CommunityMiningHandlerTest {

    public static void main(String[] args) {
        
        MeerkatBIZ app = MeerkatBIZ.getMeerkatApplication();
        String filename = "/cshome/aabnar/workspace/MeerkatLogicMerge/Graph (2).meerkat";
        
        MeerkatReader reader = new MeerkatReader(filename);
        
        IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph =
                reader.loadFile();
        
        String[] parameter = new String[1];
        parameter[0] = CommunityMiningParameters.ROSVALLINFOMOD_NUMBEROFATTEMPTS + ":10";
        
        
        try {
            Miner<IVertex, IEdge<IVertex>> miner = 
                    CommunityMiningHandler.getCMAlgorithm(dynaGraph, dynaGraph.getAllTimeFrames().get(0), "rosvallinfomod", parameter);
            
            CommunityMiningHandler.runAlgorithm(dynaGraph, dynaGraph.getAllTimeFrames().get(0), miner);
            
            while(!CommunityMiningHandler.isDone(dynaGraph, dynaGraph.getAllTimeFrames().get(0)));
            
            Map<String,List<Integer>> mapResults = 
                    CommunityMiningHandler.getResults(dynaGraph, dynaGraph.getAllTimeFrames().get(0));
            
            System.out.println ("Results are ready!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CommunityMiningHandlerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
