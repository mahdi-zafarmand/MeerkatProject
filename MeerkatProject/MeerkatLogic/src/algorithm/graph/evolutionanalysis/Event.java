/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.evolutionanalysis;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents an event that occur between two sets of communities.
 * The first set that is mostly earlier in time (the left hand side) is called
 * the source communities. The second set that is mostly later in time (the
 * right hand side) is called the result communities.
 *
 * Form and Dissolve events only have the source part.
 *
 * Survive, split, and merge events have both source and result part.
 *
 * @author takaffol
 */
public class Event {

    public enum EventName {

        SURVIVE, DISSOLVE, SPLIT, MERGE, FORM, NONE

    }

    private EventName name;

    /* The id of the left hand side (the earlier) communities in their snapshot
    */
    private List<String> sourceCommunityIds;
    
    /* The index of the left hand side snapshot (time) where the event occurred
    */
    private int sourceSnapshotTimeFrameIndex;

    /* The id of the right hand side (the later) communities in their
     * snapshot.
     * This id is only used for survive, merge, split events
     */
    private List<String> resultCommunityIds = new LinkedList<>();
    
    /* The index of the right hand side snapshot (time).
     * This index is only used for survive, merge, split events
     */
    private int resultSnapshotTimeFrameIndex;

    /* These transitions are the set of transitions occurred for the survived
     * community.
     */
    private List<Transition> transitions;

    public Event(EventName pEvName) {
        this.name = pEvName;

        resultCommunityIds = new LinkedList<>();
        sourceCommunityIds = new LinkedList<>();
        transitions = new LinkedList<>();
    }

    public EventName getEventName() {
        return this.name;
    }
    
    public void setSourceCommunityIds(Collection<String> plstSourceCommunityIds) {
        this.sourceCommunityIds.clear();
        this.sourceCommunityIds.addAll(plstSourceCommunityIds);
    }
    
    public void setSourceCommunityIds(String pstrComId) {
        this.sourceCommunityIds.clear();
        this.sourceCommunityIds.add(pstrComId);
    }
    
    public List<String> getSourceCommunityIds () {
        return this.sourceCommunityIds;
    }
    
    public void setResultCommunityIds(Collection<String> plstResultCommunityIds) {
        this.resultCommunityIds.clear();
        this.resultCommunityIds.addAll(plstResultCommunityIds);
    }
    
    public void setResultCommunityIds(String pResultCommunityIds) {
        this.resultCommunityIds.clear();
        this.resultCommunityIds.add(pResultCommunityIds);
    }
    
    public List<String> getResultCommunityIds() {
        return this.resultCommunityIds;
    }
    
    public void setSourceCommunityTimeFrameIndex (int tfIndex) {
        this.sourceSnapshotTimeFrameIndex = tfIndex;
    }
    
    public int getSourceCommunityTimeFrameIndex() {
        return this.sourceSnapshotTimeFrameIndex;
    }
    
    public void setResultCommunityTimeFrameIndex (int tfIndex) {
        this.resultSnapshotTimeFrameIndex = tfIndex;
    }
    
    public int getResultCommunityTimeFrameIndex() {
        return this.resultSnapshotTimeFrameIndex;
    }
    
    public void addTransition(Transition transition) {
        transitions.add(transition);
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    @Override
    public boolean equals(Object aThat) {
        if (this == aThat) {
            return true;
        }
        if (!(aThat instanceof Event)) {
            return false;
        }
        Event that = (Event) aThat;
        return this.name.equals(that.name)
                && this.sourceSnapshotTimeFrameIndex == that.sourceSnapshotTimeFrameIndex
                && Arrays.equals(this.sourceCommunityIds.toArray(),
                        that.sourceCommunityIds.toArray())
                && this.resultSnapshotTimeFrameIndex == that.resultSnapshotTimeFrameIndex
                && Arrays.equals(this.resultCommunityIds.toArray(),
                        that.resultCommunityIds.toArray());
    }

    @Override
    public String toString() {

        String string = "";
        if (name.equals(EventName.DISSOLVE) || name.equals(EventName.FORM)) {
            string = "Community " + sourceCommunityIds + " "
                    + name.toString();
        } else {
            string = "Communities " + sourceCommunityIds + " "
                    + name.toString() + " to Communities"
                    + resultCommunityIds + " at " + resultSnapshotTimeFrameIndex
                    + "th snapshot";
        }

        return string;
    }
}
