/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import algorithm.graph.metric.IMetric;
import algorithm.graph.metric.InDegree;
import algorithm.graph.metric.OutDegree;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IStaticGraph;
import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.graph.impl.DynamicGraph;
import datastructure.core.graph.impl.Edge;
import datastructure.core.graph.impl.StaticGraph;
import datastructure.core.graph.impl.Vertex;
import java.util.HashMap;

/**
 *
 * @author aabnar
 */
public class InOutDegreeTest {
    /** 
         * Adjacency Matrix:
         *     v1 v2 v3 v4 v5 v6 v7 v8 v9 v10
         * v1  0  1  0  1  0  0  0  0  0  0
         * v2  1  0  1  0  1  0  0  0  0  0
         * v3  0  1  0  1  0  0  0  0  0  0
         * v4  1  0  1  0  0  0  0  0  0  0
         * v5  0  0  0  0  0  0  0  0  0  0
         * v6  0  0  0  0  0  0  0  0  0  0
         * v7  0  0  0  0  0  0  0  1  0  0
         * v8  0  0  0  0  0  0  1  0  0  0
         * v9  0  0  0  0  0  0  0  0  0  1
         * v10 0  0  0  0  0  0  0  0  0  0
     * @param args
         */
    
    public static void main(String[] args) {
        IVertex v1 = new Vertex();
        IVertex v2 = new Vertex();
        IVertex v3 = new Vertex();
        IVertex v4 = new Vertex();
        IVertex v5 = new Vertex();
        IVertex v6 = new Vertex();
        IVertex v7 = new Vertex();
        IVertex v8 = new Vertex();
        IVertex v9 = new Vertex();
        IVertex v10 = new Vertex();
        
        IEdge<IVertex> e1 = new Edge(v1, v2, true);
        IEdge<IVertex> e2 = new Edge(v1, v4, true);
        IEdge<IVertex> e3 = new Edge(v2, v1, true);
        IEdge<IVertex> e4 = new Edge(v2, v3, true);
        IEdge<IVertex> e5 = new Edge(v2, v5, true);
        IEdge<IVertex> e6 = new Edge(v3, v2, true);
        IEdge<IVertex> e7 = new Edge(v3, v4, true);
        IEdge<IVertex> e8 = new Edge(v4, v1, true);
        IEdge<IVertex> e9 = new Edge(v4, v3, true);
        IEdge<IVertex> e10 = new Edge(v7, v8, true);
        IEdge<IVertex> e11 = new Edge(v8, v7, true);
        IEdge<IVertex> e12 = new Edge(v9, v10, true);
        
        TimeFrame tf =TimeFrame.DEFAULT_TIMEFRAME; 
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = new DynamicGraph<>(10,12);
        IStaticGraph<IVertex,IEdge<IVertex>> graph = new StaticGraph<>(10,12);
        
        dynaGraph.addGraph(tf, graph);
        
        dynaGraph.addVertex(v1,tf);
        dynaGraph.addVertex(v2, tf);
        dynaGraph.addVertex(v3, tf);
        dynaGraph.addVertex(v4, tf);
        dynaGraph.addVertex(v5, tf);
        dynaGraph.addVertex(v6, tf);
        dynaGraph.addVertex(v7, tf);
        dynaGraph.addVertex(v8, tf);
        dynaGraph.addVertex(v9, tf);
        dynaGraph.addVertex(v10, tf);
        
        dynaGraph.addEdge(e1, tf);
        dynaGraph.addEdge(e2, tf);
        dynaGraph.addEdge(e3, tf);
        dynaGraph.addEdge(e4, tf);
        dynaGraph.addEdge(e5, tf);
        dynaGraph.addEdge(e6, tf);
        dynaGraph.addEdge(e7, tf);
        dynaGraph.addEdge(e8, tf);
        dynaGraph.addEdge(e9, tf);
        dynaGraph.addEdge(e10, tf);
        dynaGraph.addEdge(e11, tf);
        dynaGraph.addEdge(e12, tf);
        
        InDegree<IVertex,IEdge<IVertex>> indegreeAlg = InDegree.getAlgorithm(dynaGraph, tf, null);
        
        Thread the = new Thread(indegreeAlg);
        the.start();
        
        while (the.isAlive());
        
        HashMap<IVertex,Double> indegreeScores = indegreeAlg.getScores();
        
        System.out.println("InDegree Scores: ");
        System.out.println("-----------------");
        for (IVertex v : indegreeScores.keySet()) {
            System.out.println(v.getId() + " : " + indegreeScores.get(v));
        }
        
        OutDegree<IVertex,IEdge<IVertex>> outdegreeAlg = OutDegree.getAlgorithm(dynaGraph, tf, null);
        
        the = new Thread(outdegreeAlg);
        the.start();
        
        while(the.isAlive());
        
        HashMap<IVertex,Double> outdegreeScores = outdegreeAlg.getScores();

        System.out.println("OutDegree Scores: ");
        System.out.println("------------------");
        for (IVertex v : outdegreeScores.keySet()) {
            System.out.println(v.getId() + " : " + outdegreeScores.get(v));
        }
        
    }
    
}
