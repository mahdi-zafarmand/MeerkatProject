/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import algorithm.graph.metric.Betweenness;
import algorithm.graph.metric.IMetric;
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
public class BetweennessTest {

    /**
     *   1 2 3 4 5
     * 1 0 1 1 0 0
     * 2 1 0 1 0 0
     * 3 1 1 0 1 1
     * 4 0 0 1 0 1
     * 5 0 0 1 1 0
     * @param args
     */
    public static void main(String[] args) {
        
        IVertex v1 = new Vertex();
        IVertex v2 = new Vertex();
        IVertex v3 = new Vertex();
        IVertex v4 = new Vertex();
        IVertex v5 = new Vertex();
        
        IEdge<IVertex> e1 = new Edge<>(v1, v2, false);
        IEdge<IVertex> e2 = new Edge<>(v1, v3, false);
        IEdge<IVertex> e3 = new Edge<>(v4, v5, false);
        IEdge<IVertex> e4 = new Edge<>(v4, v3, false);
        IEdge<IVertex> e5 = new Edge<>(v3, v2, false);
        IEdge<IVertex> e6 = new Edge<>(v3, v5, false);
        
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = new DynamicGraph<>(5,6);
        IStaticGraph<IVertex,IEdge<IVertex>> stGraph = new StaticGraph<>(5,6);
        
        TimeFrame tf = TimeFrame.DEFAULT_TIMEFRAME;
        dynaGraph.addGraph(tf, stGraph);
        
        dynaGraph.addVertex(v1, tf);
        dynaGraph.addVertex(v2, tf);
        dynaGraph.addVertex(v3, tf);
        dynaGraph.addVertex(v4, tf);
        dynaGraph.addVertex(v5, tf);
        
        dynaGraph.addEdge(e1, tf);
        dynaGraph.addEdge(e2, tf);
        dynaGraph.addEdge(e3, tf);
        dynaGraph.addEdge(e4, tf);
        dynaGraph.addEdge(e5, tf);
        dynaGraph.addEdge(e6, tf);
        
        Betweenness<IVertex,IEdge<IVertex>> betAlg = Betweenness.getAlgorithm(dynaGraph, tf, null);
        
        Thread th = new Thread(betAlg);
        th.start();
        
        while (th.isAlive());
        System.out.println(betAlg.isDone());
        
        HashMap<IVertex,Double> betScores = betAlg.getScores();
        
        System.out.println("Betweenness Scores : ");
        System.out.println("---------------------");
        for(IVertex v : betScores.keySet()) {
            System.out.println(v.getId() + " : " + betScores.get(v));
        }
        
        
        System.out.println(Betweenness.class.getSimpleName());
    }
}
