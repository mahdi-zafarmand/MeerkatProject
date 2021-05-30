/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IStaticGraph;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.reader.MeerkatReader;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 *  Class Name      : MeerkatGraphReaderTest
  Created Date    : 2015-10-xx
  Description     : 
  Version         : 1.0
 *  @author         : Afra 
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-13-10      Afra            checks if MeerkarReader is can read a .meerkat
 *                                  file that contains multiple snapshots.
 *                                  & checks whether the references of same
 *                                  vertices & edge throughout all time frames
 *                                  are the same.
 * 
*/
public class MeerkatGraphReaderTest {
    
    /**
     *
     */
    public static final String CLASS_NAME = "test.MeerkatGraphReaderTest";
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        String basePath = new File("").getAbsolutePath();
//        String graphPath = basePath.concat("/../MeerkatUI/projects/layouttest/Graph (2).meerkat");
        String graphPath = basePath.concat("/../MeerkatUI/resources/files/demo.meerkat");

        //testDynamicGraph(graphPath);
        testStaticGraph(graphPath);
        
    }

    private static void testDynamicGraph(String pstrFilePath) {
        MeerkatReader reader = new MeerkatReader(pstrFilePath);
        System.out.println(CLASS_NAME+": reads dynamic file...");
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = reader.loadFile();
        
        Set<Integer> allVertices = new HashSet<>();
        int sumVertices = 0;
        for (TimeFrame t : dynaGraph.getAllTimeFrames()) {
            allVertices.addAll(dynaGraph.getGraph(t).getAllVertexIds());
            sumVertices += dynaGraph.getGraph(t).getAllVertexIds().size();
        }
        
        System.out.println("Graph ID : " + dynaGraph.getId());
        System.out.println("Number of timeframes : " + 
                dynaGraph.getAllTimeFrames().size());
        System.out.println(CLASS_NAME+": Sum of all graph vertices: " + sumVertices);
        System.out.println(CLASS_NAME+": Total number of vertices in the dynaGraph: " + 
                allVertices.size());
        
        
        Set<Integer> allEdges = new HashSet<>();
        int sumEdges = 0;
        for (TimeFrame t : dynaGraph.getAllTimeFrames()) {
            allEdges.addAll(dynaGraph.getGraph(t).getAllEdgeIds());
            sumEdges += dynaGraph.getGraph(t).getAllEdgeIds().size();
        }
        
        System.out.println(CLASS_NAME+": Sum of all graph edges: " + sumEdges);
        System.out.println(CLASS_NAME+": Total number of edges in the dynaGraph: " + 
                allEdges.size());
    }

    private static void testStaticGraph(String pstrFilePath) {
        MeerkatReader reader = new MeerkatReader(pstrFilePath);
        System.out.println(CLASS_NAME+": reads static file...");
        IDynamicGraph<IVertex,IEdge<IVertex>> igraph = reader.loadFile();
        
        Set<IVertex> allVertices = new HashSet<>();
        allVertices.addAll(igraph.getAllVertices());
        
        System.out.println(CLASS_NAME+": Total number of vertices in the static graph: " + 
                allVertices.size());
        
        
        Set<IEdge> allEdges = new HashSet<>();
        allEdges.addAll(igraph.getAllEdges());
        
        System.out.println(CLASS_NAME+": Total number of edges in the static graph: " + 
                allEdges.size());
        
        for (TimeFrame t : igraph.getAllTimeFrames()) {
            igraph.getGraph(t).getAllEdgeIds();
            
        }
        TimeFrame t = igraph.getAllTimeFrames().get(0);
        IStaticGraph graphAtT0 = igraph.getGraph(t);
        
        
        
        
        
    }
}
