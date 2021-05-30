/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.evolutionanalysis;

import algorithm.graph.communitymining.dynamic.auxilaryDS.MetaCommunity;
import algorithm.graph.communitymining.dynamic.auxilaryDS.Snapshot;
import algorithm.graph.evolutionanalysis.eventdetection.EventDetection;
import algorithm.graph.evolutionanalysis.eventdetection.MODEC2EventDetection;
import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * This class, EvolutionAnalyzer, is the only interface for this package. This
 * means other classes in this package should not be used by users.
 *
 * EvolutionAnalyzer is built with an array of matrices which includes the
 * adjacency matrix for each timeframe. i.e. every matrix is a binary matrix,
 * row index shows groups and column index shows nodes.
 *
 * node1 node2 node3 node4 node5 node6 node7
 *
 * group1 0 1 0 0 0 0 1 group2 1 0 0 0 0 0 0
 *
 * group3 0 0 1 0 1 0 0 group4 0 0 0 1 0 1 0
 *
 * the array shows the time window so for example if the array includes 4
 * matrices, EventAnalyzer will assume them as [t0, t1, t2, t3] and will answer
 * all the questions based on this assumption. While those 4 elements in the
 * array could be any subset of the whole time period.
 *
 * e.g.
 *
 * the whole time period" [T0, T1, T2, T3, T4, T5, T6, T7]
 *
 * subset: [T2, T3, T4]
 *
 * construct an EventAnalyzer with subset: myAnalyzer = new EventAnalyzer([T2,
 * T3, T4])
 *
 * internal interpretation of the subset: [t0, t1, t2]
 *
 * IT IS THE USER'S RESPONSIBILITY TO HANDLE THE MAPPING BETWEEN SUBSET AND
 * TOTAL SET
 *
 *
 *
 */
public class EvolutionAnalyzer<V extends IVertex, E extends IEdge<V>> {

    public enum Framework {
        ASUR, GREENE, PALLA, MODEC, MODEC2
    }

    private Framework framework = Framework.MODEC2;

    @SuppressWarnings("unused")
    private IDynamicGraph<V, E> dynaGraph;

    // the array of snapshots
    private List<Snapshot> snapshots = new ArrayList<>();

    // this hash map contains all the meta communities in the observation time.
    // The reason that I used hash map is because I need to find out the meta
    // community for one specific community based on its ID.
    private HashMap<String, MetaCommunity> metaCommunities;

    
    private EventDetection eventDetector;
    
    private Map<Integer, EventSnapshot<V,E>> mapEventSnapshots;

//    public EvolutionAnalyzer(IDynamicGraph<V, E> pdynaGraph, List<Matrix> matrices,
//            Framework matchingAlg, double identityThreshold) {
//        this.framework = matchingAlg;
//        this.dynaGraph = pdynaGraph;
//    }
    
    public EvolutionAnalyzer(IDynamicGraph<V,E> pdynaGraph) {
        this.dynaGraph = pdynaGraph;
    }

    public void run() {
        
        Map<String, MetaCommunity<V>> mapMetaCommunities = new HashMap<>();
        int intTFIndex = 0;
        for (TimeFrame tf : dynaGraph.getAllTimeFrames()) {
            for (V v : dynaGraph.getVertices(tf)) {
                if (v.getSystemAttributer()
                            .getAttributeValue(MeerkatSystem.COMMUNITY, tf) != null) {
                    String[] coms = v.getSystemAttributer()
                            .getAttributeValue(MeerkatSystem.COMMUNITY, tf).split(",");

                    for (String com : coms) {
                        com = com.trim();
                        if (!mapMetaCommunities.containsKey(com)) {
                            MetaCommunity<V> meta = 
                                    new MetaCommunity<>(mapMetaCommunities.keySet().size(), com);
                            mapMetaCommunities.put(com, meta);
                        }

                        mapMetaCommunities.get(com).addVertex(intTFIndex, v);
                    }
                }
            }
            intTFIndex++;
        }
        
        eventDetector = new MODEC2EventDetection(dynaGraph, mapMetaCommunities);
        eventDetector.runEventDetection();
        
        mapEventSnapshots = eventDetector.getEventSnapshots();

    }
    
    /**
     * 
     * @return 
     * return structure: timeframe1:timeframe2;eventname:sourcecommunities:resultcommunities;...
     * 
     * if timeframe1 or timeframe2 does not exist it is "-1"
     * if source or result community does not exist it is "null"
     */
    public List<String> getAllEvents() {
        Map<Integer,Map<Integer,List<Event>>> mapEvents = new HashMap<>();
        
        for (int tfindex : mapEventSnapshots.keySet()) {
            // StringBuilder strb = new StringBuilder();
            /* getting all events*/
            List<Event> eventForms = mapEventSnapshots.get(tfindex).getForms();
            List<Event> eventDissolves = mapEventSnapshots.get(tfindex).getDissolves();
            List<Event> eventSplits = mapEventSnapshots.get(tfindex).getSplits();
            List<Event> eventMerges = mapEventSnapshots.get(tfindex).getMerges();
            List<Event> eventSurvives = mapEventSnapshots.get(tfindex).getSurvives();
            
            /* sorting events based on their source and result timeframes*/
            for (Event eventForm : eventForms) {
                int intSourceTimeFrame = eventForm.getSourceCommunityTimeFrameIndex();
                int intResultTimeFrame = eventForm.getResultCommunityTimeFrameIndex();
                if (!mapEvents.containsKey(intSourceTimeFrame)) {
                    mapEvents.put(intSourceTimeFrame, new HashMap<>());
                }
                if(!mapEvents.get(intSourceTimeFrame).containsKey(intResultTimeFrame)) {
                    mapEvents.get(intSourceTimeFrame).put(intResultTimeFrame, new LinkedList<>());
                }

                mapEvents.get(intSourceTimeFrame).get(intResultTimeFrame).add(eventForm);
            }
            
            for (Event dis : eventDissolves ) {
                int intSourceTimeFrame = dis.getSourceCommunityTimeFrameIndex();
                int intResultTimeFrame = dis.getResultCommunityTimeFrameIndex();
                
                if (!mapEvents.containsKey(intSourceTimeFrame)) {
                    mapEvents.put(intSourceTimeFrame, new HashMap<>());
                }
                if(!mapEvents.get(intSourceTimeFrame).containsKey(intResultTimeFrame)) {
                    mapEvents.get(intSourceTimeFrame).put(intResultTimeFrame, new LinkedList<>());
                }
                
                mapEvents.get(intSourceTimeFrame).get(intResultTimeFrame).add(dis);
                
            }
            
            for (Event split : eventSplits) {
                int intSourceTimeFrame = split.getSourceCommunityTimeFrameIndex();
                int intResultTimeFrame = split.getResultCommunityTimeFrameIndex();
                
                if (!mapEvents.containsKey(intSourceTimeFrame)) {
                    mapEvents.put(intSourceTimeFrame, new HashMap<>());
                }
                if(!mapEvents.get(intSourceTimeFrame).containsKey(intResultTimeFrame)) {
                    mapEvents.get(intSourceTimeFrame).put(intResultTimeFrame, new LinkedList<>());
                }
                
                mapEvents.get(intSourceTimeFrame).get(intResultTimeFrame).add(split);
            }
            
            for (Event merge : eventMerges) {
                int intSourceTimeFrame = merge.getSourceCommunityTimeFrameIndex();
                int intResultTimeFrame = merge.getResultCommunityTimeFrameIndex();
                
                if (!mapEvents.containsKey(intSourceTimeFrame)) {
                    mapEvents.put(intSourceTimeFrame, new HashMap<>());
                }
                if(!mapEvents.get(intSourceTimeFrame).containsKey(intResultTimeFrame)) {
                    mapEvents.get(intSourceTimeFrame).put(intResultTimeFrame, new LinkedList<>());
                }
                
                mapEvents.get(intSourceTimeFrame).get(intResultTimeFrame).add(merge);
            }
            
            for (Event sur : eventSurvives) {
                int intSourceTimeFrame = sur.getSourceCommunityTimeFrameIndex();
                int intResultTimeFrame = sur.getResultCommunityTimeFrameIndex();
                
                if (!mapEvents.containsKey(intSourceTimeFrame)) {
                    mapEvents.put(intSourceTimeFrame, new HashMap<>());
                }
                if(!mapEvents.get(intSourceTimeFrame).containsKey(intResultTimeFrame)) {
                    mapEvents.get(intSourceTimeFrame).put(intResultTimeFrame, new LinkedList<>());
                }
                
                mapEvents.get(intSourceTimeFrame).get(intResultTimeFrame).add(sur);
            }
        }
        
        /* appending all events for the same tf1:tf2 together in the same string*/
        List<String> lstTimeframeEvents = new LinkedList<>();
        
        // For each Source Time Frame
        for (int intSourceTimeFrame : mapEvents.keySet()) {
            
            // For each Result Time Frame that has events with source Time Frame
            for (int intResultTimeFrame : mapEvents.get(intSourceTimeFrame).keySet()) {
                
                StringBuilder strTimeFrameXtoY = new StringBuilder(); // Builds the Strings
                strTimeFrameXtoY.append(intSourceTimeFrame).append(":").append(intResultTimeFrame).append(";");
                
                for (Event eventsBetweenTimeFrameXY : mapEvents.get(intSourceTimeFrame).get(intResultTimeFrame)) {
                    
                    strTimeFrameXtoY.append(eventsBetweenTimeFrameXY.getEventName().toString()).append(":");
                    
                    if (eventsBetweenTimeFrameXY.getSourceCommunityIds().isEmpty()) {
                        strTimeFrameXtoY.append("null");
                    } else {
                        for (String com : eventsBetweenTimeFrameXY.getSourceCommunityIds()) {
                            strTimeFrameXtoY.append(com).append(",");
                            //System.err.println(com);
                        }
                        strTimeFrameXtoY.deleteCharAt(strTimeFrameXtoY.length()-1);
                    }
                    strTimeFrameXtoY.append(":");
                    
                    if (eventsBetweenTimeFrameXY.getResultCommunityIds().isEmpty()) {
                        strTimeFrameXtoY.append("null");
                    } else {
                        for (String com : eventsBetweenTimeFrameXY.getResultCommunityIds()) {
                            strTimeFrameXtoY.append(com).append(",");
                            //System.err.println(com);
                        }
                        strTimeFrameXtoY.deleteCharAt(strTimeFrameXtoY.length()-1);
                    }
                    strTimeFrameXtoY.append(";");
                }
                strTimeFrameXtoY.deleteCharAt(strTimeFrameXtoY.length()-1);
                lstTimeframeEvents.add(strTimeFrameXtoY.toString());
            }
        }
        return lstTimeframeEvents;
    }
    /**
     *  Method Name     : getAllIndividualEvents()
     *  Created Date    : 2017-04-20
     *  Description     : generates individual event vertices and their communities.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description 
     * 
    */
    public ArrayList<Map<String, Map<String, ArrayList<String>>>> getAllIndividualEvents(){
        
        //contains map of individual events to its VertexIDs and Communities.
        ArrayList<Map<String, Map<String, ArrayList<String>>>> individualEvents = new ArrayList<>();
        
        for( int tfindex : mapEventSnapshots.keySet()){
            Map<V,ArrayList<String>> joinVertex = mapEventSnapshots.get(tfindex).getJoin();
            Map<V,ArrayList<String>> appearVertex = mapEventSnapshots.get(tfindex).getAppear();
            Map<V,ArrayList<String>> disappearVertex = mapEventSnapshots.get(tfindex).getDisappear();
            Map<V,ArrayList<String>> leftVertex = mapEventSnapshots.get(tfindex).getLeft();
            
            Map<String, Map<String, ArrayList<String>>> eventTempMap = new HashMap<>();
            
            Map<String, ArrayList<String>> tempJoinMap = new HashMap<>();            
            for(Map.Entry<V,ArrayList<String>> entry : joinVertex.entrySet()){
                tempJoinMap.put(String.valueOf(entry.getKey().getId()) , entry.getValue());
            }
            eventTempMap.put("JOIN", tempJoinMap);
            
            Map<String, ArrayList<String>> tempLeftMap = new HashMap<>(); 
            for(Map.Entry<V,ArrayList<String>> entry : leftVertex.entrySet()){
                tempLeftMap.put(String.valueOf(entry.getKey().getId()) , entry.getValue());
            }
            eventTempMap.put("LEFT", tempLeftMap);
            
            Map<String, ArrayList<String>> tempAppearMap = new HashMap<>(); 
            for(Map.Entry<V,ArrayList<String>> entry : appearVertex.entrySet()){
                tempAppearMap.put(String.valueOf(entry.getKey().getId()) , entry.getValue());
            }
            eventTempMap.put("APPEAR", tempAppearMap);
            
            Map<String, ArrayList<String>> tempDisappearMap = new HashMap<>(); 
            for(Map.Entry<V,ArrayList<String>> entry : disappearVertex.entrySet()){
                tempDisappearMap.put(String.valueOf(entry.getKey().getId()) , entry.getValue());
            }
            eventTempMap.put("DISAPPEAR", tempDisappearMap);
            
            individualEvents.add(tfindex, eventTempMap);
        }       
        return individualEvents;
    }
}
