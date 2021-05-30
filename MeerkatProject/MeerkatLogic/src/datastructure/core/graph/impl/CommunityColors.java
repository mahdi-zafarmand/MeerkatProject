/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.graph.impl;

import datastructure.core.graph.classinterface.ICommunityColors;
import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author AICML Administrator
 */
public class CommunityColors implements ICommunityColors {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    /* Map(TimeFrame -> Map(Community, Color))*/
    private Map<TimeFrame, Map<String, String>> mapCommunityColorTimeFrames = new HashMap<>();
    /*
    mapGlobalCommunityColor maintains a map for this graph's community names to color. The invariant is that
    if with in a graph, a community with same name, irrespective of time frames, will have same color.
    Once the list of colors is assigned, the list is cycled again to assign colors.
    Earlier, the logic was to maintain this Map as global for entire Meerkat application.
    */
    /*Map<Community, Color>*/
    private Map<String, String> mapGlobalCommunityColor = new HashMap<>();
    private int nextColorIndex;
    
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    public CommunityColors(){
        mapCommunityColorTimeFrames = new HashMap<>();
        mapGlobalCommunityColor = new HashMap<>();
        nextColorIndex = 0;
    }
    
    
    
    /* *************************************************************** */
    /* *****************       Methods     *************************** */
    /* *************************************************************** */
    
    /**
     * returns a copy of mapCommunityColors for the timeframe
     * @param tf
     * @return Map<String,String>
     */
    @Override
    public Map<String,String> getMapCommunityColors(TimeFrame tf){

        Map<String, String> mapCommunityColorsCopy = new HashMap();
        for(String strCommunity : mapCommunityColorTimeFrames.get(tf).keySet()){
            mapCommunityColorsCopy.put(strCommunity, mapCommunityColorTimeFrames.get(tf).get(strCommunity));
        }
        
        return mapCommunityColorsCopy;
    }
    
    /**
     * returns a copy of mapGlobalCommunityColors
     * @return Map<String,String>
     */
    @Override
    public Map<String,String> getMapGloablCommunityColor(){

        Map<String, String> mapGlobalCommunityColorCopy = new HashMap();
        for(String strCommunity : mapGlobalCommunityColor.keySet()){
            mapGlobalCommunityColorCopy.put(strCommunity, mapGlobalCommunityColor.get(strCommunity));
        }
        
        return mapGlobalCommunityColorCopy;
    }
    
    
    
    @Override
    public void calculateCommunityColor(TimeFrame tf, Set<String> setCommunities){
        //everytime minig is done - clear the communityColorTimeFrames.get(tf)
        //then decide the color of a community
        mapCommunityColorTimeFrames.get(tf).clear();
        
        String strCommunityColor;
        for(String strCommunity : setCommunities){
            if (mapGlobalCommunityColor.containsKey(strCommunity)) {

                strCommunityColor = mapGlobalCommunityColor.get(strCommunity);
                mapCommunityColorTimeFrames.get(tf).put(strCommunity, strCommunityColor);
            } else {

                strCommunityColor = MeerkatSystem.getValidColorsCommunities().get(nextColorIndex);
                
                mapGlobalCommunityColor.put(strCommunity, strCommunityColor);
                mapCommunityColorTimeFrames.get(tf).put(strCommunity, strCommunityColor);

                nextColorIndex++;

                if (nextColorIndex >= MeerkatSystem.getValidColorsCommunities().size()) {
                    nextColorIndex = 0;
                }
            } 
        }   
    }

    @Override
    public String getCommunityColor(String strCommunity, TimeFrame tf){
            return mapCommunityColorTimeFrames.get(tf).get(strCommunity);
        
    }

    @Override
    public void setCommunityColor(String strCommunity, String strColor, TimeFrame tf) {
        mapCommunityColorTimeFrames.get(tf).put(strCommunity, strColor);
        mapGlobalCommunityColor.put(strCommunity, strColor);
    }

    /**
     * add key, value :timeframe, Map<StrCommunity,strColor> when timefame is created in dynamic graph
     * @param pTimeFrame 
     */
    @Override
    public void addTimeFrame(TimeFrame pTimeFrame) {
        this.mapCommunityColorTimeFrames.put(pTimeFrame, new HashMap<>());
    }

    @Override
    public void resetGlobalCommunityColorMap() {
        mapGlobalCommunityColor.clear();
    }
    
    /**
     * update the mapCommunityColor of the time frame with given map
     * @param tf
     * @param mapCommunityColorMap 
     */
    @Override
    public void setCommunityColorMap(TimeFrame tf, Map<String, String> mapCommunityColorMap) {
        mapCommunityColorTimeFrames.put(tf,mapCommunityColorMap);
    }
    
    /**
     * update the mapGlobalCommunityColor of graph with given map
     * @param pmapGloablCommunityColor 
     */
    @Override
    public void setGlobalCommunityColorMap(Map<String, String> pmapGloablCommunityColor) {
        mapGlobalCommunityColor = pmapGloablCommunityColor;
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
