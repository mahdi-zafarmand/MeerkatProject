/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.evolutionanalysis.eventdetection;

import algorithm.graph.communitymining.dynamic.auxilaryDS.MetaCommunity;
import algorithm.graph.evolutionanalysis.EventSnapshot;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.HashMap;
import java.util.Map;

public abstract class EventDetection<V extends IVertex, E extends IEdge<V>> {

    protected IDynamicGraph<V, E> dynaGraph;
    
    /* this threshold shows the percentage of a community that is important and
     * forms the identity of that community
     * it's in a direct relation with the concept of 'Flag' for the community:
     * it means which portion of a community carries the flag
     */
    protected double identityThreshold;


    /* this hash map contains all the meta communities in the observation time.
     * The reason that I used hash map is because I need to find out the meta
     * community for one specific community based on its ID.
     * Only ourEventDetection have meta community.
     */
    protected Map<String, MetaCommunity<V>> metaCommunities;

    
    /* TimeFrameIndex --> EventSnapshot */
    protected Map<Integer, EventSnapshot<V,E>> mapEventSnapshots = new HashMap<>();
    
    /*
     * This array of matrices includes the adjacency matrix for each timeframe.
     * i.e. every matrix is a binary matrix, row index shows groups and column
     * index shows nodes.
     * 
     * node1 node2 node3 node4 node5 node6 node7
     * 
     * group1 0 1 0 0 0 0 1 group2 1 0 0 0 0 0 0
     * 
     * group3 0 0 1 0 1 0 0 group4 0 0 0 1 0 1 0
     * 
     * the array shows the time window so for example if the array includes 4
     * matrices, EventAnalyzer will assume them as [t0, t1, t2, t3] and will
     * answer all the questions based on this assumption. While those 4 elements
     * in the array could be any subset of the whole time period.
     * 
     * e.g.
     * 
     * the whole time period" [T0, T1, T2, T3, T4, T5, T6, T7]
     * 
     * subset: [T2, T3, T4]
     * 
     * 
     * * IT IS THE USER'S RESPONSIBILITY TO HANDLE THE MAPPING BETWEEN SUBSET
     * AND TOTAL SET
     */
    public EventDetection(IDynamicGraph<V, E> pdynaGraph, 
            Map<String, MetaCommunity<V>> pmpMetaCommunities/*,
     double identityThreshold, 
     List<Matrix> matrices*/) {
//		this.identityThreshold = identityThreshold;
        this.dynaGraph = pdynaGraph;

        metaCommunities = pmpMetaCommunities;
        
        for (int tfIndex = 0 ; tfIndex < pdynaGraph.getAllTimeFrames().size() ; tfIndex++) {
            EventSnapshot<V,E> evSnapshot = new EventSnapshot<>(pdynaGraph,tfIndex);
            mapEventSnapshots.put(tfIndex, evSnapshot);
        }
    }

    /**
     * the main interface of this class. Based on the definition of the events
     * and matching algorithm in each specific paper this method will detect the
     * evolution of communities and in some cases the evolution of individual as
     * well.
     */
    public abstract void runEventDetection();

    public Map<String, MetaCommunity<V>> getMetaCommunities() {
        return metaCommunities;
    }

    protected abstract void detectingEvents();

    /**
     * Only AsurEventDetection and OurEventDetection will override this method.
     * The other ones doesn't have individual events. so they return null for
     * the individual events.
     */
    protected void detectingIndividualEvents() {

    }
    
    public Map<Integer, EventSnapshot<V,E>> getEventSnapshots() {
        return mapEventSnapshots;
    }
}
