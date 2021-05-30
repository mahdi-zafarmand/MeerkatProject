package algorithm.graph.communitymining.dynamic.util;

import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;


public class Cloning<V extends IVertex, E extends IEdge<V>> {

    public IDynamicGraph<V, E> clone(IDynamicGraph<V, E> graph) {

//        for (V vertex : graph.getAllVertices()) {
//            clonedGraph.addVertex(vertex);
//        }
//        for (V vertex1 : graph.getAllVertices()) {
//            for (V vertex2 : graph.getAllVertices()) {
//                for (E e : graph.findEdge(vertex1, vertex2, eType)) {
//                    if (e != null && !clonedGraph.getNeighbors(vertex1).contains(vertex2)) {
//                        clonedGraph.addEdge(e);
//                    }
//                }
//            }
//        }
//
//        return clonedGraph;
        return null;
    }

    public Vector<Set<V>> clone(Vector<Set<V>> partitioning) {
        Vector<Set<V>> clonedPartitioning = new Vector<>();

        for (Set<V> cluster : partitioning) {
            Set<V> clonedCluster = new HashSet<>(cluster);
            clonedPartitioning.add(clonedCluster);
        }

        return clonedPartitioning;
    }
}
