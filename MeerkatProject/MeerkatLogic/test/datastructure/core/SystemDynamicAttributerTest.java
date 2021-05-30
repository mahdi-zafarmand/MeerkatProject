/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core;

import config.MeerkatSystem;
import datastructure.core.DynamicAttributable.SysDynamicAttributer.SystemDynamicAttributer;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Talat
 * @since 2018-02-05
 */
public class SystemDynamicAttributerTest {
    
    String strAttrName ;
    String strAttrValue ;
    String strTimeFrameName ;
    TimeFrame tf ;
 
    /**
     * Setting the AttributeNames, Values and Timeframe for the first 
     * value to be inserted
     * @author Talat
     * @since 2018-02-05
     */
    @Before
    public void setUp() {
        strAttrName = "AttributeName";
        strAttrValue = "AttributeValue";
        strTimeFrameName = "DefaultTimeFrame";
        tf = new TimeFrame(strTimeFrameName);
    }
    
    /**
     * Test for getAttributeNames with no argument and value added to the same 
     * timeframe
     * @author Talat
     * @since 2018-02-02
     */
    @Test    
    public void testGetAttributeNames_0arg_sametf() {
        String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue1";
        
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        Date dtTime = new Date();
        
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        instance.addAttributeValue(strAttrName1, strAttrValue1, dtTime, tf);
        
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        expResult.add(strAttrName1);
        
        Set<String> result = instance.getAttributeNames();
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test for getAttributeNames with no argument and value added to different
     * timeframes
     * @author Talat
     * @since 2018-02-02
     */
    @Test
    public void testGetAttributeNames_0arg_difftf() {
        
        String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue1";
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        Date dtTime = new Date();
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        instance.addAttributeValue(strAttrName1, strAttrValue1, dtTime, tf1);
        
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        expResult.add(strAttrName1);
        
        Set<String> result = instance.getAttributeNames();
        
        assertEquals(expResult, result);
    }

    /**
     * Test for getAttributeNames with 1 argument and values added to the 
     * same timeframe
     * @author Talat
     * @since 2018-02-03
     */
    @Test
    public void testGetAttributeNames_1arg_sametf() {
        
        String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue1";
        
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        Date dtTime = new Date();
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        instance.addAttributeValue(strAttrName1, strAttrValue1, dtTime, tf);
        
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        expResult.add(strAttrName1);
        
        Set<String> result = instance.getAttributeNames(tf);
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test for getAttributeNames with 1 arguments and different timeframes
     * @author Talat
     * @since 2018-02-03
     */
    @Test
    public void testGetAttributeNames_1arg_difftf() {
        
        String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue1";
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        Date dtTime = new Date();
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        instance.addAttributeValue(strAttrName1, strAttrValue1, dtTime, tf1);
        
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        
        Set<String> result = instance.getAttributeNames(tf);
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test for getAttributeValue with values added to different timeframes
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testGetAttributeValue_diffTimeFrame() {
        String strAttrValue1 = "AttributeValue1";
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        Date dtTime = new Date();
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        instance.addAttributeValue(strAttrName, strAttrValue1, dtTime, tf1);
        
        String result = instance.getAttributeValue(strAttrName, tf);
        String expResult = strAttrValue;
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test for getAttributeValue with values added to the same timeframe
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testGetAttributeValue_sameTimeFrame() {
        String strAttrValue1 = "AttributeValue1";
        
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        Date dtTime = new Date();
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        instance.addAttributeValue(strAttrName, strAttrValue1, dtTime, tf);
        
        String result = instance.getAttributeValue(strAttrName, tf);
        String expResult = strAttrValue1;
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test for getAttributeValues
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testGetAttributeValues(){
        String strAttrValue1 = "AttributeValue1";        
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        
        String strAttrValue2 = "AttributeValue2";
        String strTimeFrameName2 = "DefaultTimeFrame2";
        TimeFrame tf2 = new TimeFrame(strTimeFrameName2);
                
        String strTimeFrameName3 = "DefaultTimeFrame3";
        TimeFrame tf3 = new TimeFrame(strTimeFrameName3);
        
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        Date dtTime = new Date();
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        instance.addAttributeValue(strAttrName, strAttrValue1, dtTime, tf1);
        instance.addAttributeValue(strAttrName, strAttrValue2, dtTime, tf2);
        instance.addAttributeValue(strAttrName, strAttrValue2, dtTime, tf3);
        
        Map<TimeFrame, String> result = instance.getAttributeValues(strAttrName);
        Map<TimeFrame, String> expResult = new HashMap<>();
        expResult.put(tf, strAttrValue);
        expResult.put(tf1, strAttrValue1);
        expResult.put(tf2, strAttrValue2);
        expResult.put(tf3, strAttrValue2);
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test for addAttributeValue for non existent attribute and 
     * different timeframe
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testAddAttributeValue_nonexist(){
        String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue1";
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        Date dtTime = new Date();        
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        instance.addAttributeValue(strAttrName1, strAttrValue1, dtTime, tf1);
        
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        expResult.add(strAttrName1);
        
        Set<String> result = instance.getAttributeNames();
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test for addAttributeValue for non existent attribute and 
     * different timeframe
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testAddAttributeValue_nonexist_sameTf(){
        String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue1";
                
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        Date dtTime = new Date();        
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        instance.addAttributeValue(strAttrName1, strAttrValue1, dtTime, tf);
        
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        expResult.add(strAttrName1);
        
        Set<String> result = instance.getAttributeNames();
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test for addAttributeValue for an existing attribute and same Timeframe
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testAddAttributeValue_existingAttr_sameTf(){
        // String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue1";
        // String strTimeFrameName1 = "DefaultTimeFrame1";
        // TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        
        Date dtTime = new Date();
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        instance.addAttributeValue(strAttrName, strAttrValue1, dtTime, tf);
        
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        
        Set<String> result = instance.getAttributeNames();
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test for addAttributeValue for an existing attribute and same Timeframe
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testAddAttributeValue_existingAttr_diffTf(){
        
        String strAttrValue1 = "AttributeValue1";
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        
        Date dtTime = new Date();
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        instance.addAttributeValue(strAttrName, strAttrValue1, dtTime, tf1);
        
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        
        Set<String> result = instance.getAttributeNames();
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test for Appending Attribute Values for an existing Attribute 
     * and existing timeframe
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testAppendAttributeValue_existingAttr_oldTf(){
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        
        Date dtTime = new Date();        
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        
        // String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue2";
        // String strTimeFrameName1 = "DefaultTimeFrame1";
        // TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        Date dtTime1 = new Date();
        instance.appendAttributeValue(strAttrName, strAttrValue1, dtTime1, tf);
        
        String result = instance.getAttributeValue(strAttrName, tf);
        String expResult = strAttrValue + MeerkatSystem.DELIMITER_CSV + strAttrValue1;
        
        assertEquals(result, expResult);
    }
    
    /**
     * Test for Appending Attribute Values for an existing Attribute 
     * and non-existent timeframe
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testAppendAttributeValue_existingAttr_newTf(){
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        
        Date dtTime = new Date();        
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        
        String strAttrValue1 = "AttributeValue2";
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        Date dtTime1 = new Date();
        instance.appendAttributeValue(strAttrName, strAttrValue1, dtTime1, tf1);
        
        String result = instance.getAttributeValue(strAttrName, tf1);
        String expResult = strAttrValue1;
        
        assertEquals(result, expResult);
    }
    
    /**
     * Test for Appending Attribute Values for an new Attribute 
     * and existing timeframe
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testAppendAttributeValue_newAttr_oldTf(){
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        
        Date dtTime = new Date();        
        instance.appendAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        
        String result = instance.getAttributeValue(strAttrName, tf);
        String expResult = strAttrValue ;
        
        assertEquals(result, expResult);
    }
    
    /**
     * Test for Appending Attribute Values for an new Attribute      
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testGetAttributeUpdateTime_newAttr(){
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        Date dtTime = new Date();        
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        
        Date result = instance.getAttributeUpdateTime(strAttrName, tf);
        Date expResult = dtTime;
        assertEquals(expResult, result);
    }
    
    /**
     * Test for getAttributeUpdateTime for an existing Attribute on an
     * existing TimeFrame
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testGetAttributeUpdateTime_existingAttr_sameTf(){
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        Date dtTime = new Date();        
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        
        String strAttrValue1 = "AttributeValue2";
        Date dtTime1 = new Date();
        instance.addAttributeValue(strAttrName, strAttrValue1, dtTime1, tf);
        
        Date result = instance.getAttributeUpdateTime(strAttrName, tf);
        Date expResult = dtTime1;
        assertEquals(expResult, result);
    }
    
    /**
     * Test for getAttributeUpdateTime for an existing Attribute on a
     * non-existing timeframe
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testGetAttributeUpdateTime_existingAttr_twoTf(){
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        
        Date dtTime = new Date();        
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        
        String strAttrValue1 = "AttributeValue2";
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        Date dtTime1 = new Date();
        instance.addAttributeValue(strAttrName, strAttrValue1, dtTime1, tf1); 
       
        Date result = instance.getAttributeUpdateTime(strAttrName, tf);
        Date expResult = dtTime;
        assertEquals(expResult, result);
    }
    
    /**
     * Test for getAttributeUpdateTime for a non-existing Attribute 
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testRemoveAttribute_diffAttrs() {
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        
        Date dtTime = new Date();        
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        
        String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue2";
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        Date dtTime1 = new Date();
        instance.addAttributeValue(strAttrName1, strAttrValue1, dtTime1, tf1); 
       
        instance.removeAttribute(strAttrName);
        
        Set<String> result = instance.getAttributeNames();
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName1);
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test for removeAttribute when the attribute is not already added
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testRemoveAttribute_notAdded() {
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        
        instance.removeAttribute(strAttrName);
        
        Set<String> result = instance.getAttributeNames();
        Set<String> expResult = new HashSet<>();        
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test for removeAttribute when the attribute is same but different 
     * timeframe
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testRemoveAttribute_sameAttrs_diffTf() {
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        
        Date dtTime = new Date();        
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        
        String strAttrValue1 = "AttributeValue2";
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        Date dtTime1 = new Date();
        instance.addAttributeValue(strAttrName, strAttrValue1, dtTime1, tf1); 
       
        instance.removeAttribute(strAttrName);
        
        Set<String> result = instance.getAttributeNames();
        Set<String> expResult = new HashSet<>();        
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test for removeAttribute when the attribute is same and the timeframe is 
     * also the same
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testRemoveAttribute_sameAttrs_sameTf() {
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        
        Date dtTime = new Date();        
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        
        String strAttrValue1 = "AttributeValue2";
        TimeFrame tf = new TimeFrame(strTimeFrameName);
        Date dtTime1 = new Date();
        instance.addAttributeValue(strAttrName, strAttrValue1, dtTime1, tf); 
       
        instance.removeAttribute(strAttrName);
        
        Set<String> result = instance.getAttributeNames();
        Set<String> expResult = new HashSet<>();        
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test for removeAttributeValue
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testRemoveAttributeValue() {
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        
        Date dtTime = new Date();        
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        
        String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue2";
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        Date dtTime1 = new Date();
        instance.addAttributeValue(strAttrName1, strAttrValue1, dtTime1, tf1); 
       
        instance.removeAttributeValue(strAttrName, tf);
        
        String result = instance.getAttributeValue(strAttrName, tf);
        String expResult = null;        
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test for removeAttributeValue with the attribute is not added
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testRemoveAttributeValue_notAdded() {
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
               
        instance.removeAttributeValue(strAttrName, tf);
        
        String result = instance.getAttributeValue(strAttrName, tf);
        String expResult = null;        
        
        assertEquals(expResult, result);
    }

    /**
     * Test for containsAttributeAtTimeFrame when there is an exisiting value
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testContainsAttributeAtTimeFrame() {
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        
        Date dtTime = new Date();        
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        
        String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue2";
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        Date dtTime1 = new Date();
        instance.addAttributeValue(strAttrName1, strAttrValue1, dtTime1, tf1); 
       
        boolean result = instance.containsAttributeAtTimeFrame(strAttrName, tf);
        boolean expResult = true;
    }
    
    /**
     * Test for containsAttributeAtTimeFrame when the attribute has been removed
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testContainsAttributeAtTimeFrame_removed() {
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
        
        Date dtTime = new Date();        
        instance.addAttributeValue(strAttrName, strAttrValue, dtTime, tf);
        instance.removeAttribute(strAttrName);
               
        boolean result = instance.containsAttributeAtTimeFrame(strAttrName, tf);
        boolean expResult = false;
    }
    
    /**
     * Test for containsAttributeAtTimeFrame when there exists no such attribute
     * @author Talat
     * @since 2018-02-05
     */
    @Test
    public void testContainsAttributeAtTimeFrame_notAdded() {
        SystemDynamicAttributer instance = new SystemDynamicAttributer();
                       
        boolean result = instance.containsAttributeAtTimeFrame(strAttrName, tf);
        boolean expResult = false;
    }
}
