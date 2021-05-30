/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.evolutionanalysis;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represent one snapshot (i.e. specific timepoint) in the
 * evolutionary graph.
 *
 * @author takaffol
 *
 */
public class EventSnapshot<V extends IVertex, E extends IEdge<V>> {

    int timeframeIndex;
    TimeFrame timeframe;
    List<Event> lstForm = new LinkedList<>();
    List<Event> lstSurvive = new LinkedList<>();
    List<Event> lstDissolve = new LinkedList<>();
    List<Event> lstSplit = new LinkedList<>();
    List<Event> lstMerge = new LinkedList<>();
    List<V> lstAppear = new LinkedList<>();
    List<V> lstDisappear = new LinkedList<>();
    List<V> lstJoin = new LinkedList<>();
    List<V> lstLeft = new LinkedList<>();
    Map<V, ArrayList<String>> mapAppear = new HashMap<>();
    Map<V, ArrayList<String>> mapDisappear = new HashMap<>();
    Map<V, ArrayList<String>> mapJoin = new HashMap<>();
    Map<V, ArrayList<String>> mapLeft = new HashMap<>();
    
    Map<String,List<V>> mapCommunities = new HashMap<>();

    public EventSnapshot(IDynamicGraph<V,E> pdynaGraph, int pinttfIndex) {
        this.timeframeIndex = pinttfIndex;
        this.timeframe = pdynaGraph.getAllTimeFrames().get(timeframeIndex);
        
        for(V v : pdynaGraph.getVertices(timeframe)) {
            if (v.getSystemAttributer().getAttributeValue(
                    MeerkatSystem.COMMUNITY, timeframe) != null) {
                String[] vertexComs = v.getSystemAttributer()
                        .getAttributeValue(MeerkatSystem.COMMUNITY, timeframe)
                        .split(",");

                for (String com : vertexComs) {
                    com = com.trim();
                    if (!mapCommunities.containsKey(com)) {
                        mapCommunities.put(com, new LinkedList<>());
                    }
                    mapCommunities.get(com).add(v);
                }
            }
        }
        
        System.out.println("EventSnapshot.Constructor() : " + timeframe.toString());
        for (String str : mapCommunities.keySet()) {
            System.out.print(str + ": ");
            for (V v : mapCommunities.get(str)) {
                System.out.print( v.getId() + " , ");
            }
            System.out.println("");
        }
    }
    
    public TimeFrame getTimeFrame() {
        return this.timeframe;
    }
    
    public int getTimeFrameIndex() {
        return this.timeframeIndex;
    }

    public List<Event> getForms() {
        return this.lstForm;
    }
    
    public List<Event> getSurvives() {
        return this.lstSurvive;
    }
    
    public List<Event> getDissolves() {
        return this.lstDissolve;
    }
    
    public List<Event> getSplits() {
        return this.lstSplit;
    }
    
    public List<Event> getMerges() {
        return this.lstMerge;
    }
    
    public Map<V, ArrayList<String>> getAppear() {
        return this.mapAppear;
    }
     
    public Map<V, ArrayList<String>> getDisappear() {
        return this.mapDisappear;
    }
      
    public Map<V, ArrayList<String>> getJoin() {
        return this.mapJoin;
    }
       
    public Map<V, ArrayList<String>> getLeft() {
        return this.mapLeft;
    }
    
    public void addFormEvent(Event pEvForm){
        lstForm.add(pEvForm);
    }
    
    public void addSurviveEvent(Event pEvSurvive) {
        lstSurvive.add(pEvSurvive);
    }
    
    public void addDissolveEvent (Event pEvDissolve) {
        lstDissolve.add(pEvDissolve);
    }
    
    public void addSplitEvent (Event pEvSplit) {
        lstSplit.add(pEvSplit);
    }
    
    public void addMergeEvent (Event pEvMerge) {
        lstMerge.add(pEvMerge);
    }
    
    public void addAppearIndividual(V v, ArrayList<String> comm){
        mapAppear.put(v, comm);
    }
     
    public void addDisappearIndividual(V v, ArrayList<String> comm){
        mapDisappear.put(v, comm);
    }
    
    public void addJoinIndividual(V v, ArrayList<String> comm){
        mapJoin.put(v, comm);
    }
    
    public void addLeftIndividual(V v, ArrayList<String> comm){
        mapLeft.put(v, comm);
    }

    public Set<String> getCommunityIds() {
        return mapCommunities.keySet();
    }
    
    public List<V> getCommunityVertices(String pstrComId) {
        return mapCommunities.get(pstrComId);
    }
    
    public Collection<List<V>> getCommunities() {
        return mapCommunities.values();
    }
    
    public ArrayList<String> getVertexCommunity(V v) {
        ArrayList<String> VertexComm = new ArrayList<>();
        
        for (String comm: mapCommunities.keySet()){
            if(mapCommunities.get(comm).contains(v))
                VertexComm.add(comm);
        }
        
        return VertexComm;
    }
    
    public boolean hasVertex(V v) {
        boolean hasVertex = false;
        for (String comm: mapCommunities.keySet()){
            if(mapCommunities.get(comm).contains(v)){
                hasVertex =true;
                break;
            }
        }
        return hasVertex;
    }
}
