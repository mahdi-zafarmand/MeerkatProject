/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core;

import java.util.LinkedList;

/**
 *
 * @author aabnar
 */
public class IDGenerator {
    private int max_used_id = -1;
            
    LinkedList<Integer> available_ids = new LinkedList<>();

    /**
     *
     * @return
     */
    public int getNextAvailableID() {
        if (available_ids.isEmpty()) {
            max_used_id++;
            return max_used_id;
        } else {
            return available_ids.pop();
        }
    }

    /**
     *
     * @param pintId
     */
    public void addAvailableID (int pintId) {
        available_ids.push(pintId);
    }
}
