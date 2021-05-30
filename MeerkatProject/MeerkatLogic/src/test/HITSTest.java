/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import algorithm.graph.metric.Authority;
import algorithm.graph.metric.HITS;
import algorithm.graph.metric.Hub;
import algorithm.graph.metric.IMetric;
import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.reader.MeerkatReader;
import java.util.HashMap;
import static test.MeerkatGraphReaderTest.CLASS_NAME;

/**
 *
 * @author aabnar
 */
public class HITSTest {
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        testHITS("graph.meerkat");
        
    }

    private static void testHITS(String pstrFilePath) {
        MeerkatReader reader = new MeerkatReader(pstrFilePath);
        System.out.println(CLASS_NAME+": reads dynamic file...");
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = reader.loadFile();
        
        for (TimeFrame tf : dynaGraph.getAllTimeFrames()) {
            String[] parameter = new String[1];
            HITS hitsAlg = Authority.getAlgorithm(dynaGraph, tf, parameter);
            System.out.println("HITSTest.testHITS: algorithm type -->" + hitsAlg.getType());
            Thread th = new Thread(hitsAlg);
            th.start();
            
            while(th.isAlive());
            
            HashMap<IVertex, Double> authScores = hitsAlg.getScores();
            
            System.out.println("Authority Scores : ");
            for (IVertex v : authScores.keySet()) {
                System.out.println(v.getId() + " : " + authScores.get(v));
            }
            
            parameter[0] = MeerkatSystem.HUB;
            hitsAlg = Hub.getAlgorithm(dynaGraph, tf, parameter);
            System.out.println("HITSTest.testHITS: algorithm type -->" + hitsAlg.getType());            
            HashMap<IVertex, Double>  hubScores = hitsAlg.getScores();
            
            System.out.println("Hub Scores : ");
            for (IVertex v : hubScores.keySet()) {
                System.out.println(v.getId() + " : " + hubScores.get(v));
            }
            
        }
    }
}
