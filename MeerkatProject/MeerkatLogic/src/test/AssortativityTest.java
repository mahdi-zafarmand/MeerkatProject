/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import algorithm.graph.GeneralStatistics;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.reader.MeerkatReader;
import io.graph.reader.PairsReader;
import io.graph.reader.PajekReader;
import static test.MeerkatGraphReaderTest.CLASS_NAME;

/**
 *
 * @author aabnar
 */
public class AssortativityTest {
    
    /**
     *
     * @param args
     */
    public static void main (String[] args) {
        PairsReader reader = new PairsReader("/cshome/aabnar/workspace/MeerkatLogic/facebook_combined.pairs");
        System.out.println(CLASS_NAME+": reads dynamic file...");
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = reader.loadFile();
        
        for (TimeFrame tf : dynaGraph.getAllTimeFrames()) {
            double dblAssort = 
                    GeneralStatistics.computeAssortativity(dynaGraph, tf);
            
            System.out.println("AssortativityTest.main : " + dblAssort);
        }
    }
}
