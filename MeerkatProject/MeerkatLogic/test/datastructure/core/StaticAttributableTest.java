/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author talat
 */
public class StaticAttributableTest {
    
    public StaticAttributableTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getUserAttributer method, of class StaticAttributable.
     */
    @Test
    public void testGetUserAttributer() {
        StaticAttributable instance = new StaticAttributableImpl();
        String strAttrName = "Attribute";
        String strAttrValue = "Value";
        instance.getUserAttributer().addAttribute(strAttrName, strAttrValue);
        
        Set<String> result = instance.getUserAttributer().getAttributeNames();
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getSystemAttributer method, of class StaticAttributable.
     */
    @Test
    public void testGetSystemAttributer() {        
        StaticAttributable instance = new StaticAttributableImpl();
        String strAttrName = "Attribute";
        String strAttrValue = "Value";
        Date dtTime = new Date();
        instance.getSystemAttributer().addAttribute(strAttrName, strAttrValue, dtTime); 
       
        Set<String> result = instance.getSystemAttributer().getAttributeNames();
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumericAttributeNames method, of class StaticAttributable.
     */
    @Test
    public void testGetNumericAttributeNames() {
        
    }

    /**
     * Test of getAttributeNamesWithType method, of class StaticAttributable.
     */
    @Test
    public void testGetAttributeNamesWithType() {
        
    }

    /**
     * Test of getAttributeNames method, of class StaticAttributable.
     */
    @Test
    public void testGetAttributeNames() {
        StaticAttributable instance = new StaticAttributableImpl();
        String strAttrName = "Attribute";
        String strAttrValue = "Value";
        Date dtTime = new Date();
        instance.getSystemAttributer().addAttribute(strAttrName, strAttrValue, dtTime); 
       
        Set<String> result = instance.getSystemAttributer().getAttributeNames();
        Set<String> expResult = new HashSet<>();
        expResult.add(strAttrName);
        
        assertEquals(expResult, result);        
    }

    public class StaticAttributableImpl implements StaticAttributable {

        public StaticAttributer.GenericStaticAttributer getUserAttributer() {
            return null;
        }

        public SysStaticAttributer.SystemStaticAttributer getSystemAttributer() {
            return null;
        }

        public Set<String> getNumericAttributeNames() {
            return null;
        }

        public Map<String, Boolean> getAttributeNamesWithType() {
            return null;
        }

        public Set<String> getAttributeNames() {
            return null;
        }
    }
    
}
