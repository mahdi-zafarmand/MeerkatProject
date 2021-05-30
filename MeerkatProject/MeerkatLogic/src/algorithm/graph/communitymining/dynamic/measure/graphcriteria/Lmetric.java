/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm.graph.communitymining.dynamic.measure.graphcriteria;

import algorithm.graph.communitymining.dynamic.auxilaryDS.Region;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.Set;
import java.util.Vector;

public class Lmetric<V extends IVertex, E extends IEdge<V>>
                                    extends RelativeCommunityCriteria<V, E> {

    private boolean normalized;

    public Lmetric(IDynamicGraph<V, E> graph, TimeFrame tf, boolean normalized) {

        super(graph, tf);
        this.normalized = normalized;
    }

    @Override
    public double evaluateCommunities(Vector<Set<V>> communities) {

        if (communities.isEmpty())
            return 0;

        double lmetric = 0.0;

        for (Set<V> community : communities) {
            lmetric += evaluateCommunity(community);
        }
        return lmetric / communities.size();
    }

    public double evaluateRegion(Region<V,E> region) {
        double l = 2
            * (region.getRegion2RegionWeight() / region.getRegionSize())
            / (region.getBoundary2ShellWeight() / region.getBoundarySize());

        return l;
    }

    public double evaluateCommunity(Set<V> cluster) {
        
        double lmetric = 0.0;

        int boudarySize = 0;
        double lin = 0;
        double lex = 0;

        if (cluster.size() > 1) {

            for (V v : cluster) {
                lin += calcuteInnerDegree(v, cluster);
                
                double outerDegree = calcuteOuterDegree(v, cluster);
                if (outerDegree != 0) {
                    boudarySize++;
                    lex += outerDegree;
                }

            }
            lin = lin / cluster.size();

            double upperBound;

            if (boudarySize != 0) {
                lex = lex / boudarySize;
                lmetric = lin / lex;

            } else {
                lmetric = lin;
            }

            if (normalized) {
                double maxW = maxW();
                double minW = minW();
                if (boudarySize != 0) {
                    upperBound = (cluster.size() - 1) * maxW / minW;
                } else {
                    upperBound = (cluster.size() - 1) * maxW;
                }
                lmetric = lmetric / upperBound;
            }

        }
        return lmetric;
    }

    private double minW() {
        double minWeight = Double.MAX_VALUE;
        for (E e : dynaGraph.getEdges(tf)) {
            double w = e.getWeight();
            if (w < minWeight) {
                minWeight = w;
            }
        }
        return minWeight;
    }

    private double maxW() {
        double maxWeight = 0;
        for (E e : dynaGraph.getEdges(tf)) {
            double w = e.getWeight();
            if (w > maxWeight)
                maxWeight = w;
        }
        return maxWeight;
    }

    private double calcuteOuterDegree(V v, Set<V> community) {
        double lex = 0;
        if (community.contains(v)) {
            for (V adjV : dynaGraph.getNeighbors(v,tf)) {
                if (!community.contains(adjV)) {
                    E e = dynaGraph.findEdge(v, adjV, tf);
                    lex += e.getWeight();
                }
            }
        }
        return lex;
    }

    private double calcuteInnerDegree(V v, Set<V> community) {
        double degree = 0.0;
        if (community.contains(v)) {
            for (V u : dynaGraph.getNeighbors(v,tf)) {
                if (community.contains(u)) {
                    degree += dynaGraph.findEdge(v, u, tf).getWeight();
                }
            }
        }
        return degree;
    }

    private double W(V source, V target) {

        E e = (E) dynaGraph.findEdge(source, target, tf);
        if (e == null)
            return 0;
        return e.getWeight();
    }

    @Override
    public String toString() {
        return "Lmetric";
    }

    public String getName() {
        return "L";
    }

}
