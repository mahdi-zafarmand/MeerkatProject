/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import algorithm.graph.shortestpath.DijkstraShortestPath;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.reader.MeerkatReader;
import java.util.HashMap;
import java.util.Map;
import static test.KKLayoutTest.layoutGraph;
import static test.MeerkatGraphReaderTest.CLASS_NAME;

/**
 *
 * @author aabnar
 */
public class DijsktraSPTest {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                loadFile("/cshome/aabnar/workspace/MeerkatLogicMVP/"
                                + "testSP.meerkat");
        
        algorithmTest(dynaGraph);
    }
    
    /**
     *
     * @param pstrFilePath
     * @return
     */
    public static IDynamicGraph<IVertex,IEdge<IVertex>> loadFile(String pstrFilePath) {
        MeerkatReader reader = new MeerkatReader(pstrFilePath);
        System.out.println(CLASS_NAME+": reads dynamic file...");
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = reader.loadFile();
        
        return dynaGraph;
    }

    private static void algorithmTest(IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph) {
        for (TimeFrame tf : dynaGraph.getAllTimeFrames()) {
            DijkstraShortestPath djkstra = new DijkstraShortestPath(dynaGraph, tf);
            
            HashMap<IVertex,Map<IVertex,Double>> results = djkstra.computeAllPairsSPWeighted();
            for (IVertex v1 : results.keySet()) {
                for (IVertex v2 : results.get(v1).keySet()) {
                    System.out.println("DijkstraSPTest : algorithmTest : "
                            + "VId1, VId2, SP --> " 
                            + v1.getId() + ","+v2.getId() + ","
                            + results.get(v1).get(v2));
                }
            }
        }
    }
    
    
}
