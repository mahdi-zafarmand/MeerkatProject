/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core;

import datastructure.core.DynamicAttributable.DynamicAttributer.GenericDynamicAttributer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author talat
 * @since 2018-02-02
 */
public class GenericDynamicAttributerTest {
    
    String strAttrName ;
    String strAttrValue ;
    String strTimeFrameName ;
    TimeFrame tf ;
        
    @Before
    public void setUp() {
        strAttrName = "AttributeName";
        strAttrValue = "AttributeValue";
        strTimeFrameName = "DefaultTimeFrame";
        tf = new TimeFrame(strTimeFrameName);
    }
    
    @Test
    public void testGetAttributeNames_0arg_sametf() {
        
        String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue1";
        
        GenericDynamicAttributer instance = new GenericDynamicAttributer();
        instance.addAttribute(strAttrName, strAttrValue, tf);
        instance.addAttribute(strAttrName1, strAttrValue1, tf);
        
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        expResult.add(strAttrName1);
        
        Set<String> result = instance.getAttributeNames();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetAttributeNames_0arg_difftf() {
        
        String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue1";
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        
        GenericDynamicAttributer instance = new GenericDynamicAttributer();
        instance.addAttribute(strAttrName, strAttrValue, tf);
        instance.addAttribute(strAttrName1, strAttrValue1, tf1);
        
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        expResult.add(strAttrName1);
        
        Set<String> result = instance.getAttributeNames();
        
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAttributeNames_1arg_sametf() {
        
        String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue1";
        
        GenericDynamicAttributer instance = new GenericDynamicAttributer();
        instance.addAttribute(strAttrName, strAttrValue, tf);
        instance.addAttribute(strAttrName1, strAttrValue1, tf);
        
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        expResult.add(strAttrName1);
        
        Set<String> result = instance.getAttributeNames(tf);
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetAttributeNames_1arg_difftf() {
        
        String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue1";
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        
        GenericDynamicAttributer instance = new GenericDynamicAttributer();
        instance.addAttribute(strAttrName, strAttrValue, tf);
        instance.addAttribute(strAttrName1, strAttrValue1, tf1);
        
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        
        Set<String> result = instance.getAttributeNames(tf);
        
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAttributeValue_diffTimeFrame() {
        String strAttrValue1 = "AttributeValue1";
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        
        GenericDynamicAttributer instance = new GenericDynamicAttributer();
        instance.addAttribute(strAttrName, strAttrValue, tf);
        instance.addAttribute(strAttrName, strAttrValue1, tf1);
        
        String result = instance.getAttributeValue(strAttrName, tf);
        String expResult = strAttrValue;
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetAttributeValue_sameTimeFrame() {
        String strAttrValue1 = "AttributeValue1";
        
        GenericDynamicAttributer instance = new GenericDynamicAttributer();
        instance.addAttribute(strAttrName, strAttrValue, tf);
        instance.addAttribute(strAttrName, strAttrValue1, tf);
        
        String result = instance.getAttributeValue(strAttrName, tf);
        String expResult = strAttrValue1;
        
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAttributeValues() {

        String strAttrValue1 = "AttributeValue1";        
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        
        String strAttrValue2 = "AttributeValue2";
        String strTimeFrameName2 = "DefaultTimeFrame2";
        TimeFrame tf2 = new TimeFrame(strTimeFrameName2);
                
        String strTimeFrameName3 = "DefaultTimeFrame3";
        TimeFrame tf3 = new TimeFrame(strTimeFrameName3);
        
        GenericDynamicAttributer instance = new GenericDynamicAttributer();
        
        instance.addAttribute(strAttrName, strAttrValue, tf);
        instance.addAttribute(strAttrName, strAttrValue1, tf1);
        instance.addAttribute(strAttrName, strAttrValue2, tf2);
        instance.addAttribute(strAttrName, strAttrValue2, tf3);
        
        Map<TimeFrame, String> result = instance.getAttributeValues(strAttrName);
        Map<TimeFrame, String> expResult = new HashMap<>();
        expResult.put(tf, strAttrValue);
        expResult.put(tf1, strAttrValue1);
        expResult.put(tf2, strAttrValue2);
        expResult.put(tf3, strAttrValue2);
        
        assertEquals(expResult, result);
    }

    @Test
    public void testAddAttribute() {
        String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue1";
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        
        GenericDynamicAttributer instance = new GenericDynamicAttributer();
        instance.addAttribute(strAttrName, strAttrValue, tf);
        instance.addAttribute(strAttrName1, strAttrValue1, tf1);
        
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        expResult.add(strAttrName1);
        
        Set<String> result = instance.getAttributeNames();
        
        assertEquals(expResult, result);
    }

    @Test
    public void testRemoveAttribute() {
        String strAttrName1 = "AttributeName1";
        String strAttrValue1 = "AttributeValue1";
        String strTimeFrameName1 = "DefaultTimeFrame1";
        TimeFrame tf1 = new TimeFrame(strTimeFrameName1);
        
        GenericDynamicAttributer instance = new GenericDynamicAttributer();
        instance.addAttribute(strAttrName, strAttrValue, tf);
        instance.addAttribute(strAttrName, strAttrValue, tf1);
        instance.addAttribute(strAttrName1, strAttrValue1, tf);
        instance.addAttribute(strAttrName1, strAttrValue1, tf1);
        
        instance.removeAttribute(strAttrName);
        
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName1);
                
        Set<String> result = instance.getAttributeNames();
        
        assertEquals(expResult, result);
    }
    
}

            
