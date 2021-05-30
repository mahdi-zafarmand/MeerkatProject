/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm.graph.communitymining.dynamic.util;

import java.util.Comparator;
import java.util.Set;

public class SetComparator<V> implements Comparator<Set<V>> {

	@Override
	public int compare(Set<V> set1, Set<V> set2) {
		if (set1.size() < set2.size())
			return -1;
		else
			return 1;
	}

}
