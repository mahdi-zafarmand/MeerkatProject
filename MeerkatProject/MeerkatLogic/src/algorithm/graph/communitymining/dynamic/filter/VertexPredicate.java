/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm.graph.communitymining.dynamic.filter;

import java.util.Collection;
import org.apache.commons.collections15.Predicate;


public class VertexPredicate<V> implements Predicate<V> {

    Collection<V> graph;

    public VertexPredicate(Collection<V> graph) {
        super();
        this.graph = graph;
    }

    @Override
    public boolean evaluate(V vertex) {
        if (graph.contains(vertex))
            return true;
        return false;
    }
}
