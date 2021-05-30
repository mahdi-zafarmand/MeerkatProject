/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import algorithm.graph.metric.Closeness;
import algorithm.graph.metric.IMetric;
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
public class ClosenessTest {
    
    /**
     *
     * @param args
     */
    public static void main (String[] args) {
        testCloseness("/cshome/aabnar/workspace/MeerkatLogicMVP/resources/files/demo.meerkat");
        
    }

    private static void testCloseness(String pstrFilePath) {
        MeerkatReader reader = new MeerkatReader(pstrFilePath);
        System.out.println(CLASS_NAME+": reads dynamic file...");
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = reader.loadFile();
        
        for (TimeFrame tf : dynaGraph.getAllTimeFrames()) {
            Closeness clAlg = Closeness.getAlgorithm( dynaGraph, tf, null);
            
            Thread th = new Thread(clAlg);
            th.start();
            
            while(th.isAlive());
            
            HashMap<IVertex, Double> clScores = clAlg.getClosenessScores();
            
            System.out.println("Closeness Scores : ");
            for (IVertex v : clScores.keySet()) {
                System.out.println(v.getId() + " : " + clScores.get(v));
            }
            
            
            clAlg = Closeness.getAlgorithm( dynaGraph, tf, null);
            
            th = new Thread(clAlg);
            th.start();
            
            while(th.isAlive());
            
            clScores = clAlg.getClosenessScores();
            
            System.out.println("Closeness Scores : ");
            for (IVertex v : clScores.keySet()) {
                System.out.println(v.getId() + " : " + clScores.get(v));
            }
        }
    }
}
