/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm.graph.communitymining.dynamic.util;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

/**
 * This class provides comparison between two nodes based on their degree
 * 
 */
public class CommunityComparator<V> implements Comparator<Set<V>> {
    Map<V, Double> vertices;

    public CommunityComparator(Map<V, Double> vertices) {
        super();
        this.vertices = vertices;
    }

    @Override
    public int compare(Set<V> community1, Set<V> community2) {
        Double hashcode1 = 0.0;
        for (V v : community1) {
            hashcode1 += vertices.get(v);
        }
        hashcode1 = hashcode1 / community1.size();

        Double hashcode2 = 0.0;
        for (V v : community2) {
            hashcode2 += vertices.get(v).hashCode();
        }
        hashcode2 = hashcode2 / community2.size();

        return hashcode1.compareTo(hashcode2);
    }

}

