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

public class Mmetric<V extends IVertex, E extends IEdge<V>> 
                                extends RelativeCommunityCriteria<V, E> {

    public Mmetric(IDynamicGraph<V, E> graph/*, final Map<E, ? extends Number> edgeWeights*/,
            TimeFrame ptf) {

        super(graph, ptf);
    }



    @Override
    public double evaluateCommunities(Vector<Set<V>> communities) {

        if (communities.isEmpty())
            return 0;

        double mmetric = 0.0;

        for (Set<V> community : communities) {
            mmetric += evaluateCommunity(community);
        }
        return mmetric / communities.size();
    }

    public double evaluateRegion(Region<V,E> region) {
        double m = region.getRegion2RegionWeight()
                        / region.getBoundary2ShellWeight();

        return m;
    }

    //TODO
    public double evaluateCommunity(Set<V> cluster) {
        return 0;
    }

    @Override
    public String toString() {
        return "Mmetric";
    }

    public String getName() {
        return "M";
    }
}
