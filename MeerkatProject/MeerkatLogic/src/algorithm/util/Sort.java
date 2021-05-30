package algorithm.util;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author aabnar
 * @param <V>
 */
public class Sort<V> {

    HashMap<V, Double> hmpVScores;

    /**
     *
     * @param phmpVScores
     */
    public Sort(HashMap<V, Double> phmpVScores) {
        this.hmpVScores = phmpVScores;
    }

    /**
     *
     * @return
     */
    public LinkedList<V> callQuickSort() {
        LinkedList<V> arlVertices = new LinkedList<>();
        arlVertices.addAll(hmpVScores.keySet());

        return quickSort(arlVertices);
    }

    /**
     *
     * @param parlVertices
     * @return
     */
    public LinkedList<V> quickSort(LinkedList<V> parlVertices) {
        
        Random rndGen = new Random();
        int index = rndGen.nextInt(parlVertices.size());
//        System.out.println("index: " + index);

        LinkedList<V> arlLowerList = new LinkedList<>();
        LinkedList<V> arlUpperList = new LinkedList<>();
        LinkedList<V> equalList = new LinkedList<>();

        double dblIndexScore = hmpVScores.get(parlVertices.get(index));
        
        for (V v : parlVertices) {
            if (v.equals(parlVertices.get(index))) {
                continue;
            }
            /* else */
            if (hmpVScores.get(v) == dblIndexScore) {
                equalList.add(v);
            } else if (hmpVScores.get(v) < dblIndexScore) {
                arlLowerList.add(v);
            } else {
                arlUpperList.add(v);
            }
        }

        LinkedList<V> arlSorted = new LinkedList<>();
        if (arlLowerList.size() > 1) {
            arlLowerList = quickSort(arlLowerList);
            arlSorted.addAll(quickSort(arlLowerList));
        } else {
            arlSorted.addAll(arlLowerList);
        }
        arlSorted.addAll(equalList);
        arlSorted.add(parlVertices.get(index));
        if (arlUpperList.size() > 1) {
            arlUpperList = quickSort(arlUpperList);
            arlSorted.addAll(arlUpperList);
        } else {
            arlSorted.addAll(arlUpperList);
        }

        return arlSorted;
    }
}
