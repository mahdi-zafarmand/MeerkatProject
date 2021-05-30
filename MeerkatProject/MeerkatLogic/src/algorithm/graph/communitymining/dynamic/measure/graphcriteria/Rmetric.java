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
import datastructure.core.graph.classinterface.IStaticGraph;
import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.graph.impl.StaticGraph;
import java.util.Set;
import java.util.Vector;
import org.apache.commons.collections15.Transformer;

public class Rmetric<V extends IVertex, E extends IEdge<V>> 
                                extends RelativeCommunityCriteria<V, E> {

    private boolean normalized;

    public Rmetric(IDynamicGraph<V, E> graph, 
            TimeFrame ptf,
            boolean normalized) {

        super(graph, ptf);
        this.normalized = normalized;
    }

    public Rmetric() {
        super(null, null);
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
        double r = region.getBoundary2RegionWeight()
                        / (region.getBoundary2RegionWeight() + region
                                        .getBoundary2ShellWeight());

        return r;
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
