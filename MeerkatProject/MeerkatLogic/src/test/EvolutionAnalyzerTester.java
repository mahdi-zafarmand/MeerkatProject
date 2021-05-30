/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import algorithm.graph.communitymining.DynamicMiner;
import algorithm.graph.communitymining.dynamic.DynamicCommunityMining;
import algorithm.graph.communitymining.dynamic.DynamicCommunityMiningProperties;
import algorithm.graph.evolutionanalysis.EvolutionAnalyzer;
import config.dynamiccommunitymining.DynamicCommunityMiningAlgorithmName;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.*;
import io.graph.reader.MeerkatReader;
import java.util.Properties;

/**
 *
 * @author aabnar
 */
public class EvolutionAnalyzerTester {

    public static void main(String[] args) throws InterruptedException {
        String filepath = "/cshome/aabnar/workspace/MeerkatUI/resources/files/demo.meerkat";
        
        MeerkatReader mreader = new MeerkatReader(filepath);
        IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph = mreader.loadFile();
        
        DynamicMiner<IVertex, IEdge<IVertex>> dynamicMiner = new DynamicMiner<>(dynaGraph, null);
        
        Thread th = new Thread(dynamicMiner);
        th.start();
        th.join();
        
        EvolutionAnalyzer<IVertex, IEdge<IVertex>> evolutionAnalyzer =
                new EvolutionAnalyzer<>(dynaGraph);
        
        evolutionAnalyzer.run();
        
        for (String tfEvents : evolutionAnalyzer.getAllEvents()) {
            System.out.println(tfEvents);
        }
    }
}
