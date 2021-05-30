/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm.graph.communitymining.dynamic.util;

import datastructure.core.graph.classinterface.IVertex;
import java.util.Comparator;
import java.util.Map;

/**
 * This class provides comparison between two nodes based 
 * on their degree
 * 
 * @param <V>
 */
public class VertexComparator<V extends IVertex> implements Comparator<V> {
    Map<V, Double> vertices;

    public VertexComparator(Map<V, Double> vertices) {
        super();
        this.vertices = vertices;
    }

    @Override
    public int compare(V v1, V v2) {
        double doubleV1 = vertices.get(v1);
        double doubleV2 = vertices.get(v2);
        if (doubleV1 < doubleV2)
                return -1;
        else if (doubleV1 == doubleV2)
                return 0;
        else
                return 1;

    }
}
