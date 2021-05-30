/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author aabnar
 */
public class QuickSortTest {

    public static void main(String args[]) {
        Set<Integer> setUnordered = new HashSet<>();
        setUnordered.add(12);
        setUnordered.add(3);
        setUnordered.add(5);
        setUnordered.add(15);
        setUnordered.add(17);
        setUnordered.add(21);
        setUnordered.add(100);
        setUnordered.add(1000);
        setUnordered.add(223);
        
        
        List<Integer> lstOrderedTimeFrameIndexes = new LinkedList<>();
        for (int tfIndex : setUnordered) {
            System.out.println(tfIndex);
            if(lstOrderedTimeFrameIndexes.isEmpty()) {
                lstOrderedTimeFrameIndexes.add(tfIndex);
            } else {
                for (int i = 0 ; i < lstOrderedTimeFrameIndexes.size() ; i++) {
                    if (tfIndex < lstOrderedTimeFrameIndexes.get(i)) {
                        lstOrderedTimeFrameIndexes.add(i, tfIndex);
                        break;
                    } else if (i == lstOrderedTimeFrameIndexes.size() - 1) {
                        lstOrderedTimeFrameIndexes.add(tfIndex);
                        break;
                    }
                }
            }
        }
        
        System.out.println(lstOrderedTimeFrameIndexes.size());
        for (int i : lstOrderedTimeFrameIndexes) {
            System.out.print(i + " , ");
        }
    }
}
