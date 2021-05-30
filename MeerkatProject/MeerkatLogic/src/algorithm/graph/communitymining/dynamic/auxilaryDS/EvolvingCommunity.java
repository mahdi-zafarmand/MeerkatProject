/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.communitymining.dynamic.auxilaryDS;

import algorithm.graph.evolutionanalysis.Event;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ClassName: EvolvingCommunity
 * Description: This class represent the community in the evolution analyzer framework. The
 * community is the group of densely connected individuals at a specific
 * timeframe. Each community may undergoes different events and transitions. A
 * community has one id which defines its meta community (i.e. series of similar
 * community).
 * 
 * @author takaffol
 * 
 */
public class EvolvingCommunity<V> {

    // id of the meta community
    private String id;

    // Snapshot the community appears in
    private int timeframeIndex;

    // index of the community in the snapshot
    private int index;

    /* The backward community match is the closest in time previously similar
     * community
     * to this one
     * which is found by matching algorithm.
     * However, for some of the framework such as Greene and Palla we may have
     * more than one matches. So an array has to be used here.
     */
    
    private ArrayList<EvolvingCommunity<V>> backwardMatches;

    /* The forward community match is the closest in time next similar community
     * to this one
     * which is found by matching algorithm.
     * However, for some of the framework such as Greene and Palla we may have
     * more than one matches. So an array has to be used here.
     */
    private ArrayList<EvolvingCommunity<V>> forwardMatches;

    private Set<V> vertices;

    public EvolvingCommunity(Set<V> vertices, int index, int snapshotIdx) {

        backwardMatches = new ArrayList<>();
        forwardMatches = new ArrayList<>();

        // Link to the other community class, and vice versa.
        this.vertices = vertices;
        this.index = index;
        this.timeframeIndex = snapshotIdx;
        this.id = "invalid";
    }

    public int getTimeFrameIndex() {
        return timeframeIndex;
    }

    public int getSize() {
        return vertices.size();
    }

    public void addBackwardMatch(EvolvingCommunity<V> backwardMatches) {
        this.backwardMatches.add(backwardMatches);
    }

    public void addForwardMatch(EvolvingCommunity<V> forwardMatches) {
        this.forwardMatches.add(forwardMatches);
    }

    @Override
    public String toString() {
        return this.id + ":" + index + "@" + timeframeIndex;
    }

    public String getId() {
        return id;
    }

    public int getIndex() {
        return index;
    }

    public Set<V> getVertices() {
        return vertices;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<EvolvingCommunity<V>> getBackwardMatches() {
        return backwardMatches;
    }
}
