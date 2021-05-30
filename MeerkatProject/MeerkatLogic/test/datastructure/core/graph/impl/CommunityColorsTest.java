/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.graph.impl;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author AICML Administrator
 */
public class CommunityColorsTest {
    
    private CommunityColors communityColorsInstance;
    private Map<TimeFrame, Map<String, String>> mapCommunityColorTimeFrames;
    private Map<String, String> mapGlobalCommunityColor = new HashMap<>();
    private TimeFrame tf1;
    private TimeFrame tf2;
    private Set<String> setCommunitiesTF1;
    private Set<String> setCommunitiesTF2;
    
    public CommunityColorsTest() {
    }
    
    
    
    @Before
    public void setUp() {
        communityColorsInstance = new CommunityColors();
        tf1 = new TimeFrame("year1");
        tf2 = new TimeFrame("year2");
        communityColorsInstance.addTimeFrame(tf1);
        communityColorsInstance.addTimeFrame(tf2);
        
        mapCommunityColorTimeFrames = new HashMap<>();
        mapGlobalCommunityColor = new HashMap<>();
        
        setCommunitiesTF1 = new HashSet<>();
        setCommunitiesTF2 = new HashSet<>();
        
        
        fillGlobalCommunityColorMap();
        fillMapCommunityCOlorTimeFrames();
    }
    
    private void fillGlobalCommunityColorMap(){
        /**
         * 1:   #FFB300" // Vivid Yellow
           2:   "#803E75" // Strong Purple
           3:   "#FF6800" // Vivid Orange
           4:   "#A6BDD7" // Very Light Blue
           5:   "#C10020" // Vivid Red
              
         */
        
        
        for(int i = 0; i<5;i++){
            mapGlobalCommunityColor.put(Integer.toString(i), MeerkatSystem.getValidColorsCommunities().get(i));
        }
        
        //communityColorsInstance.setGlobalCommunityColorMap(mapGlobalCommunityColor);
        
    }
    
    private void fillMapCommunityCOlorTimeFrames(){
        
        
        //fill for timeframe1
        setCommunitiesTF1.add("0");
        setCommunitiesTF1.add("1");
        setCommunitiesTF1.add("2");
        communityColorsInstance.calculateCommunityColor(tf1, setCommunitiesTF1);
        
        Map<String, String> mapCommunityColorTF1 = new HashMap<>();
        mapCommunityColorTF1.put("0", "#FFB300");
        mapCommunityColorTF1.put("1", "#803E75");
        mapCommunityColorTF1.put("2", "#FF6800");
        mapCommunityColorTimeFrames.put(tf1, mapCommunityColorTF1);
        
        //fill for timeframe2
        setCommunitiesTF2.add("1");
        setCommunitiesTF2.add("3");
        setCommunitiesTF2.add("4");
        communityColorsInstance.calculateCommunityColor(tf2, setCommunitiesTF2);
        
        Map<String, String> mapCommunityColorTF2 = new HashMap<>();
        mapCommunityColorTF2.put("1", "#803E75");
        mapCommunityColorTF2.put("3", "#A6BDD7");
        mapCommunityColorTF2.put("4", "#C10020");
        mapCommunityColorTimeFrames.put(tf2, mapCommunityColorTF2);
        
    
    }
    
    @After
    public void tearDown() {
        
        communityColorsInstance = null;
        mapCommunityColorTimeFrames = null;
        mapGlobalCommunityColor = null;
        setCommunitiesTF1 = null;
        setCommunitiesTF2 = null;
    }

    @Test
    public void testGetMapCommunityColors_MapGloablCommunityColor_FilledBeforehand() {
        System.out.println("getMapCommunityColors");
        TimeFrame tf = tf1;
        
        Map<String, String> result = communityColorsInstance.getMapCommunityColors(tf);
        Map<String, String> expected = mapCommunityColorTimeFrames.get(tf1);
        
        assertEquals(result, expected);
        //TODO test for the case when MapGloablCommunityColor is not already built
    }
    
    @Test
    public void testGetMapGloablCommunityColor() {
        System.out.println("getMapGloablCommunityColor");
        
        Map<String, String> expResult = mapGlobalCommunityColor;
        Map<String, String> result = communityColorsInstance.getMapGloablCommunityColor();
        assertEquals(expResult, result);
        
    }

    @Test
    public void testCalculateCommunityColor() {
        System.out.println("calculateCommunityColor");
        
        TimeFrame tf = tf1;
        Set<String> setCommunitiesTF = setCommunitiesTF1;
        setCommunitiesTF.add("3");
        setCommunitiesTF.add("5");
        
        
        
        Map<String, String> mapCommunityColorTF = mapCommunityColorTimeFrames.get(tf);
        mapCommunityColorTF.put("3", "#A6BDD7");
        mapCommunityColorTF.put("5", "#CEA262");
        mapCommunityColorTimeFrames.put(tf, mapCommunityColorTF);
        
        communityColorsInstance.calculateCommunityColor(tf, setCommunitiesTF);
        
        Map<String, String> expResult = mapCommunityColorTF;
        Map<String, String> result = communityColorsInstance.getMapCommunityColors(tf1);
        assertEquals(expResult, result);
        
        
    }

    @Test
    public void testGetCommunityColor() {
        System.out.println("getCommunityColor");
        String strCommunity = "2";
        TimeFrame tf = tf1;
        
        String expResult = "#FF6800";
        String result = communityColorsInstance.getCommunityColor(strCommunity, tf);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testGetCommunityColor_CommunityNotExist() {
        System.out.println("getCommunityColor");
        String strCommunity = "20";
        TimeFrame tf = tf1;
        
        
        String result = communityColorsInstance.getCommunityColor(strCommunity, tf);
        String expResult = null;
        assertEquals(expResult, result);
        
    }

    @Test
    public void testSetCommunityColor() {
        System.out.println("setCommunityColor");
        String strCommunity = "7";
        TimeFrame tf = tf1;
        
        String strColor = "#007D34";
        String expResult = strColor;
        
        
        communityColorsInstance.setCommunityColor(strCommunity, strColor, tf);
        String result = communityColorsInstance.getCommunityColor(strCommunity, tf);
        assertEquals(expResult, result);
    }

    @Test
    public void testAddTimeFrame() {
        System.out.println("addTimeFrame");
        TimeFrame newTF = new TimeFrame("year3");
        
        communityColorsInstance.addTimeFrame(newTF);
        
        assertNotNull(communityColorsInstance.getMapCommunityColors(newTF));
        
    }

    @Test
    public void testResetGlobalCommunityColorMap() {
        System.out.println("resetGlobalCommunityColorMap");
        
        communityColorsInstance.resetGlobalCommunityColorMap();
        
        assertEquals(0, communityColorsInstance.getMapGloablCommunityColor().size());
    }

 
    @Ignore
    @Test
    public void testSetCommunityColorMap() {
        System.out.println("setCommunityColorMap");
        TimeFrame tf = null;
        Map<String, String> mapCommunityColorMap = null;
        CommunityColors instance = new CommunityColors();
        instance.setCommunityColorMap(tf, mapCommunityColorMap);
        fail("The test case is a prototype.");
    }
    
    @Ignore
    @Test
    public void testSetGlobalCommunityColorMap() {
        System.out.println("setGlobalCommunityColorMap");
        Map<String, String> pmapGloablCommunityColor = null;
        CommunityColors instance = new CommunityColors();
        instance.setGlobalCommunityColorMap(pmapGloablCommunityColor);
        fail("The test case is a prototype.");
    }
    
}
