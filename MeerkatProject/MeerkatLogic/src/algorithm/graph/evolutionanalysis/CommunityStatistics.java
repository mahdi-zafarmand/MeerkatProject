/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm.graph.evolutionanalysis;

import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author aabnar
 */
public class CommunityStatistics {

    public static double average(final List<Double> data) {

        // first pass: read in data, compute sample mean
        double sumx = 0.0;
        for (double x : data)
            sumx += x;
        if (data.isEmpty()) {
            return 0;
        }
        return sumx / data.size();
    }

    public static double average(final List<Double> data, final int size) {

        // first pass: read in data, compute sample mean
        double sumx = 0.0;
        for (double x : data)
            sumx += x;
        if (size == 0) {
            return 0;
        }
        return sumx / size;
    }

    public static double stddev(final List<Double> data) {

        return Math.sqrt(variance(data));

    }

    public static double variance(final List<Double> data) {
        double xbar = average(data);
        // second pass: compute variance
        double xxbar = 0.0;

        for (double x : data)
            xxbar += (x - xbar) * (x - xbar);

        if (data.size() < 2) {
            return 0;
        }
        return xxbar / ((double) data.size() - 1);
    }

    public static double getCentralityScore(final IVertex v,
            final IDynamicGraph<IVertex, IEdge<IVertex>> graph,
            Collection<IVertex> vertices,
            TimeFrame tf) {

        int neighbourCount = 0;
        for (IVertex u : graph.getNeighbors(v, tf)) {
            if (vertices.contains(u)) {
                neighbourCount++;
            }
        }
        return (double) neighbourCount / (double) (vertices.size() - 1);

    }

    public static List<IVertex> getCentral(
            final IDynamicGraph<IVertex, IEdge<IVertex>> graph,
            Collection<IVertex> vertices, 
            TimeFrame tf) {
        List<IVertex> central = new ArrayList<>();
        double maxCentrality = -1;

        if (vertices.size() == 1) {
            for (IVertex v : graph.getVertices(tf))
                central.add(v);
        } else {
            for (IVertex v : vertices) {
                int neighbourCount = 0;
                for (IVertex u : graph.getNeighbors(v, tf)) {
                    if (vertices.contains(u)) {
                        neighbourCount++;
                    }
                }
                double centrality = (double) neighbourCount 
                        / (double) (vertices.size() - 1);
                
                if (centrality > maxCentrality) {
                    maxCentrality = centrality;
                    central = new ArrayList<>();
                    central.add(v);
                } else if (centrality == maxCentrality)
                    central.add(v);
            }
        }
        return central;
    }
}
