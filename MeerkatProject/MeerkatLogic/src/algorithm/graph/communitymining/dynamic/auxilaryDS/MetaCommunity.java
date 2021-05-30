/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.communitymining.dynamic.auxilaryDS;

import algorithm.graph.evolutionanalysis.Event;
import data.Item;
import data.ItemSet;
import data.Sequence;
import datastructure.core.graph.classinterface.IVertex;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Meta community is collection of communities with the same ID
 *
 * This class is mostly required for pattern mining. So the events and
 * transitions are saved in the sequence of itemsets.
 *
 * @author takaffol
 *
 */
public class MetaCommunity<V extends IVertex> {

    private String id;
    
    // Timeframe index --> set of vertices
    private Map<Integer, Set<V>> communities = new HashMap<>();

    // the snapshot index of the first instance of this meta community
    private int startTimeIndex = Integer.MAX_VALUE;

    // the snapshot index of the last instance of this meta community
    private int endTimeIndex = Integer.MIN_VALUE;

    List<Integer> lstOrderedTimeframeIndexes = new LinkedList<>();
    /*
     * sequence of events and transitions occurred for this meta community. This
     * sequence is needed to mine sequential patterns
     */
    private Sequence sequence;

    private final HashSet<Event> events;

    public class ItemsetSortByTime implements Comparator<ItemSet> {

        @Override
        public int compare(ItemSet o1, ItemSet o2) {
            if (o1.getTimestamp() < o2.getTimestamp()) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public MetaCommunity(int index, String id) {
        this.id = id;
        sequence = new Sequence(index);
        events = new HashSet<>();
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public Collection<Set<V>> getCommunities() {
        return communities.values();
    }

    /**
     * 
     * @return  
     */
    public List<Integer> getOrderedTimeFrameIndexes() {
        return lstOrderedTimeframeIndexes;
    }
    
    public void addVertex(int pintTimeFrameIndex, V pVertex) {
        
        if (!communities.containsKey(pintTimeFrameIndex)) {
            communities.put(pintTimeFrameIndex, new HashSet<>());
        }
        communities.get(pintTimeFrameIndex).add(pVertex);
        
        if (pintTimeFrameIndex < startTimeIndex) {
            startTimeIndex = pintTimeFrameIndex;
        }

        if (pintTimeFrameIndex > endTimeIndex) {
            endTimeIndex = pintTimeFrameIndex;
        }

        int lstpointer = 0;
        if (lstOrderedTimeframeIndexes.size() > 0) {
            int tfpointer = lstOrderedTimeframeIndexes.get(lstpointer);
            while (pintTimeFrameIndex > tfpointer) {
                lstpointer++;
                if (lstpointer == lstOrderedTimeframeIndexes.size()) {
                    break;
                }
                if(tfpointer<lstOrderedTimeframeIndexes.size())
                    tfpointer = lstOrderedTimeframeIndexes.get(tfpointer);
            }
        }
        //System.out.println("MetaCommunity.addVertex() : lstOrderedTFIndexSize = " + lstOrderedTimeframeIndexes.size());
        if (lstOrderedTimeframeIndexes.size() == lstpointer ) { 
            lstOrderedTimeframeIndexes.add(pintTimeFrameIndex);
        } else if (lstpointer == 0) {
            lstOrderedTimeframeIndexes.add(0, pintTimeFrameIndex);
        } else {
            lstOrderedTimeframeIndexes.add(lstpointer-1, pintTimeFrameIndex);
        }
    }
    
    public Set<V> getCommunity(int pintTimeFrameIndex) {
        return communities.get(pintTimeFrameIndex);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    // add one event or transition to one specific itemset (itemset is
    // identifies by its snapshot)
    public void addItem(String event, int snapshotIndex) {
        boolean found = false;
        Item item = new Item(event);
        for (ItemSet is : sequence.getItemSets()) {
            if (is.getTimestamp() == snapshotIndex) {
                is.addItem(item);
                found = true;
                break;
            }
        }
        if (found == false) {
            ItemSet is = new ItemSet(item, snapshotIndex);
            sequence.addItemSet(is);
            Collections.sort(sequence.getItemSets(), new ItemsetSortByTime());
        }
    }

    @Override
    public String toString() {
        return id + ": " + communities;
    }

    public int getStartTimeIndex() {
        return startTimeIndex;
    }

    public void setStartTimeIndex(int startTimeIndex) {
        this.startTimeIndex = startTimeIndex;
    }

    public int getEndTimeIndex() {
        return endTimeIndex;
    }

    public void setEndTimeIndex(int endTimeIndex) {
        this.endTimeIndex = endTimeIndex;
    }

    public HashSet<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {

        events.add(event);
        addItem(Event.EventName.SURVIVE.toString(),
                event.getResultCommunityTimeFrameIndex());
    }

}
