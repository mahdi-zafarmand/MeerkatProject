/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core;

import datastructure.core.StaticAttributable.SysStaticAttributer.SystemStaticAttributer;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author talat
 */
public class SystemStaticAttributerTest {
        
    @Test
    public void testGetAttributeNames_1Attr() {
        SystemStaticAttributer instance = new SystemStaticAttributer();
        
        String strAttrName = "Attribute";
        String strAttrValue = "Value";
        Date dtTime = new Date();
        instance.addAttribute(strAttrName, strAttrValue, dtTime);
        
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        
        Set<String> result = instance.getAttributeNames();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetAttributeNames_NoAttr() {
        SystemStaticAttributer instance = new SystemStaticAttributer();
        
        Set<String> expResult = new HashSet<>();        
        Set<String> result = instance.getAttributeNames();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetAttributeValue() {
        SystemStaticAttributer instance = new SystemStaticAttributer();
        
        String strAttrName = "Attribute";
        String strAttrValue = "Value";
        Date dtTime = new Date();
        instance.addAttribute(strAttrName, strAttrValue, dtTime);
        
        String result = instance.getAttributeValue(strAttrName);
        
        assertEquals(strAttrValue, result);
    }

    @Test
    public void testAddAttribute() {
        SystemStaticAttributer instance = new SystemStaticAttributer();
        
        String strAttrName = "Attribute";
        String strAttrValue = "Value";
        Date dtTime = new Date();
        instance.addAttribute(strAttrName, strAttrValue, dtTime);
        
        int result = instance.getAttributeNames().size();
        int expResult = 1;
        
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAttributeUpdateTime() {
        SystemStaticAttributer instance = new SystemStaticAttributer();
        
        String strAttrName = "Attribute";
        String strAttrValue = "Value";
        Date dtExpResult = new Date();
        instance.addAttribute(strAttrName, strAttrValue, dtExpResult);
        
        Date dtResult = instance.getAttributeUpdateTime(strAttrName);
        
        assertEquals(dtExpResult, dtResult);
    }

    @Test
    public void testRemoveAttribute() {
        SystemStaticAttributer instance = new SystemStaticAttributer();
        
        String strAttrName = "Attribute1";
        String strAttrValue = "Value";
        Date dtExpResult = new Date();
        instance.addAttribute(strAttrName, strAttrValue, dtExpResult);
                
        String strAttrName2 = "Attribute2";
        String strAttrValue2 = "Value2";
        Date dtExpResult2 = new Date();
        instance.addAttribute(strAttrName2, strAttrValue2, dtExpResult2);
        
        instance.removeAttribute(strAttrName2);
        
        int expResult = 1;
        int result = instance.getAttributeNames().size();        
        assertEquals(expResult, result);
    }
}
