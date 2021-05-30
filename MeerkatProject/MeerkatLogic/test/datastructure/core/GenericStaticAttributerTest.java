/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core;

import datastructure.core.StaticAttributable.StaticAttributer.GenericStaticAttributer;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author talat
 */
public class GenericStaticAttributerTest {
        
    @Test
    public void testGetAttributeNames_1Attr() {
        GenericStaticAttributer instance = new GenericStaticAttributer();
        
        String strAttrName = "Attribute";
        String strAttrValue = "Value";
        instance.addAttribute(strAttrName, strAttrValue);
        
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        
        Set<String> result = instance.getAttributeNames();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetAttributeNames_NoAttr() {
        GenericStaticAttributer instance = new GenericStaticAttributer();
                
        Set<String> expResult = new HashSet<>();
        Set<String> result = instance.getAttributeNames();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetAttributeValue() {
        GenericStaticAttributer instance = new GenericStaticAttributer();
        
        String strAttrName = "Attribute";
        String strAttrValue = "Value";
        instance.addAttribute(strAttrName, strAttrValue);
        
        String result = instance.getAttributeValue(strAttrName);
        
        assertEquals(strAttrValue, result);
    }

    @Test
    public void testAddAttribute() {
        GenericStaticAttributer instance = new GenericStaticAttributer();
        
        String strAttrName = "Attribute";
        String strAttrValue = "Value";
        instance.addAttribute(strAttrName, strAttrValue);
        
        int result = instance.getAttributeNames().size();
        int expResult = 1;
        
        assertEquals(expResult, result);
    }

    @Test
    public void testRemoveAttribute() {
        GenericStaticAttributer instance = new GenericStaticAttributer();
        
        String strAttrName = "Attribute1";
        String strAttrValue = "Value";
        instance.addAttribute(strAttrName, strAttrValue);
                
        String strAttrName2 = "Attribute2";
        String strAttrValue2 = "Value2";
        instance.addAttribute(strAttrName2, strAttrValue2);
        
        instance.removeAttribute(strAttrName2);
        
        int expResult = 1;
        int result = instance.getAttributeNames().size();        
        assertEquals(expResult, result);
    }
}