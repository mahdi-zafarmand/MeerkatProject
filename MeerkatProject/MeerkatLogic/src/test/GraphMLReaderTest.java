/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.reader.GMLReader;
import java.util.HashSet;
import java.util.Set;
import static test.MeerkatGraphReaderTest.CLASS_NAME;

/**
 *
 * @author aabnar
 */
public class GraphMLReaderTest {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        
        testGraph("/cshome/aabnar/workspace/MeerkatLogic/"
                                + "resources/files/test.graphml");
        
    }

    private static void testGraph(String pstrFilePath) {
        GMLReader reader = new GMLReader(pstrFilePath);
        
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
    }
}
