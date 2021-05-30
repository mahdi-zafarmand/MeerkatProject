package test;

import algorithm.graph.metric.IMetric;
import java.util.HashMap;

import datastructure.core.graph.classinterface.*;
import datastructure.core.graph.impl.Edge;
import datastructure.core.graph.impl.StaticGraph;
import datastructure.core.graph.impl.Vertex;
import algorithm.graph.metric.PageRank;
import datastructure.core.TimeFrame;
import datastructure.core.graph.impl.DynamicGraph;

/**
 *
 * @author aabnar
 */
public class PageRankTest {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph = new DynamicGraph<>(3,6);
        IStaticGraph<IVertex, IEdge<IVertex>> igraph = new StaticGraph<>(3, 6);
        
        TimeFrame tf = TimeFrame.DEFAULT_TIMEFRAME;
        dynaGraph.addGraph(tf, igraph);
        
        

        IVertex v1 = new Vertex();
        IVertex v2 = new Vertex();
        IVertex v3 = new Vertex();

        IEdge<IVertex> e1 = new Edge<>(v1, v2, true);
        IEdge<IVertex> e2 = new Edge<>(v1, v3, true);
        IEdge<IVertex> e3 = new Edge<>(v2, v3, true);
        IEdge<IVertex> e4 = new Edge<>(v3, v1, true);
        IEdge<IVertex> e5 = new Edge<>(v2, v1, true);
        IEdge<IVertex> e6 = new Edge<>(v3, v2, true);

        dynaGraph.addVertex(v1, tf);
        dynaGraph.addVertex(v2, tf);
        dynaGraph.addVertex(v3, tf);

        PageRank<IVertex, IEdge<IVertex>> prkAlg
                = PageRank.getAlgorithm(dynaGraph, tf, null);
        Thread trdPageRank = new Thread(prkAlg);
        trdPageRank.start();
        
        while(trdPageRank.isAlive());
        
        HashMap<IVertex, Double> hmpVertexPR = prkAlg.getPageRankScore();

        System.out.println(hmpVertexPR);
        System.out.println("NO EDGES");
        System.out.println(hmpVertexPR.get(v1) + " | "
                + hmpVertexPR.get(v2) + " | "
                + hmpVertexPR.get(v3));

        dynaGraph.addEdge(e1,tf);
        //igraph.addEdge(e2);
        dynaGraph.addEdge(e3,tf);
        //igraph.addEdge(e4);
        dynaGraph.addEdge(e5,tf);
        dynaGraph.addEdge(e6,tf);

        prkAlg = PageRank.getAlgorithm( dynaGraph, tf, null);
        trdPageRank = new Thread(prkAlg);
        trdPageRank.start();
        
        while(trdPageRank.isAlive());

        hmpVertexPR = prkAlg.getPageRankScore();
        System.out.println(hmpVertexPR);
        
        System.out.println("ALL EDGES EXCEPT EDGES between V1 and V3");
        System.out.println(hmpVertexPR.get(v1) + " | "
                + hmpVertexPR.get(v2) + " | "
                + hmpVertexPR.get(v3));

        dynaGraph.addEdge(e2, tf);
        dynaGraph.addEdge(e4, tf);

        prkAlg = PageRank.getAlgorithm(dynaGraph, tf, null);
        trdPageRank = new Thread(prkAlg);
        trdPageRank.start();
        
        while(trdPageRank.isAlive());
        
        System.out.println(hmpVertexPR);
        System.out.println("ALL EDGES");
        System.out.println(hmpVertexPR.get(v1) + " | "
                + hmpVertexPR.get(v2) + " | "
                + hmpVertexPR.get(v3));

    }
}
