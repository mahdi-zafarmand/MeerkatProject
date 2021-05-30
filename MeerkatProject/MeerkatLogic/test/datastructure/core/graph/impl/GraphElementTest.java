/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.graph.impl;

import config.MeerkatSystem;
import datastructure.core.DynamicAttributable;
import datastructure.core.TimeFrame;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sankalp
 */
public class GraphElementTest {
    
    static  Vertex instance = null;
    static TimeFrame strTimeFrame = null;
    static List<String> userAttributes = null;
    static List<String> sysAttributes = null;
    static double xValue = 0.9299599162704955;
    static double yValue = 0.4827600466302346;
    
    public GraphElementTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        instance = new Vertex();
        
        //generating test user attributes
        userAttributes = new ArrayList<>();
        userAttributes.add("Attribute1");
        userAttributes.add("Attribute2");
        userAttributes.add("Attribute3");
        
        //adding some user Attributes
        for(int i=0; i<userAttributes.size(); i++){
            instance.getUserAttributer().addAttribute(userAttributes.get(i),
                                                userAttributes.get(i)+"Value",
                                                strTimeFrame);
        }
        
        //generating test system attributes
        sysAttributes = new ArrayList<>();
        sysAttributes.add(MeerkatSystem.COLOR);
        sysAttributes.add(MeerkatSystem.X);
        sysAttributes.add(MeerkatSystem.Y);
        
        //adding some system attributes
        instance.getSystemAttributer().addAttributeValue(
                                        MeerkatSystem.COLOR,
                                        MeerkatSystem.getDefaultVertexColor(),
                                        new Date(),
                                        strTimeFrame);
        
        instance.getSystemAttributer().addAttributeValue(
                                        MeerkatSystem.X,
                                        String.valueOf(xValue),
                                        new Date(),
                                        strTimeFrame);
        instance.getSystemAttributer().addAttributeValue(
                                        MeerkatSystem.Y,
                                        String.valueOf(yValue),
                                        new Date(),
                                        strTimeFrame);
    }
    
    @AfterClass
    public static void tearDownClass() {
        instance = null;
        strTimeFrame = null;
        userAttributes = null;
        sysAttributes = null;
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getNumericAttributeNames method, of class GraphElement.
     */
    @Test
    public void testGetNumericAttributeNames() {
        System.out.println("getNumericAttributeNames");
    
        Set<String> expResult = new HashSet<>();
        expResult.add(sysAttributes.get(1));
        expResult.add(sysAttributes.get(2));
       
        Set<String> result = instance.getNumericAttributeNames();
        System.out.println("result: "+ result);
        Assert.assertTrue(expResult.equals(result));
    }

    /**
     * Test of getAttributeNames method, of class GraphElement.
     */
    @Test
    public void testGetAttributeNames() {
        System.out.println("getAttributeNames");

        Set<String> expResult = new HashSet<>();
        
        expResult.addAll(sysAttributes);
        expResult.addAll(userAttributes);
        
        Set<String> result = instance.getAttributeNames();
        Assert.assertTrue(expResult.equals(result));
    }

    /**
     * Test of getAttributeNamesWithType method, of class GraphElement.
     */
    @Test
    public void testGetAttributeNamesWithType() {
        System.out.println("getAttributeNamesWithType");
        
        Map<String, Boolean> expResult = new HashMap<>();
        
        //adding SYS attributes
        expResult.put(sysAttributes.get(0), Boolean.FALSE);
        expResult.put(sysAttributes.get(1), Boolean.TRUE);
        expResult.put(sysAttributes.get(2), Boolean.TRUE);
        
        //adding user attributes
        expResult.put(userAttributes.get(0), Boolean.FALSE);
        expResult.put(userAttributes.get(1), Boolean.FALSE);
        expResult.put(userAttributes.get(2), Boolean.FALSE);

        Map<String, Boolean> result = instance.getAttributeNamesWithType();
        Assert.assertTrue(expResult.equals(result));
    }

    /**
     * Test of getUserAttributer method, of class GraphElement.
     */
    @Test
    public void testGetUserAttributer() {
        System.out.println("getUserAttributer");
        
        DynamicAttributable.DynamicAttributer.GenericDynamicAttributer expResult = 
                                                                        new DynamicAttributable.DynamicAttributer.GenericDynamicAttributer();
        
        DynamicAttributable.DynamicAttributer.GenericDynamicAttributer result = instance.getUserAttributer();
        Assert.assertTrue(expResult.getClass().equals(result.getClass()));
    }

    /**
     * Test of getSystemAttributer method, of class GraphElement.
     */
    @Test
    public void testGetSystemAttributer() {
        System.out.println("getSystemAttributer");

        DynamicAttributable.SysDynamicAttributer.SystemDynamicAttributer expResult = 
                                                                        new DynamicAttributable.SysDynamicAttributer.SystemDynamicAttributer();
        
        DynamicAttributable.SysDynamicAttributer.SystemDynamicAttributer result = instance.getSystemAttributer();
        Assert.assertTrue(expResult.getClass().equals(result.getClass()));
    }

    /**
     * Test of getId method, of class GraphElement.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        
        instance.setId(2);
        int expResult = 2;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class GraphElement.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        
        int pintId = 5;
        instance.setId(pintId);
        int expResult = pintId;
        
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWeight method, of class GraphElement.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        
        double expResult = 3.0;
        instance.setWeight(expResult);
        double result = instance.getWeight();
        assertEquals(Double.valueOf(expResult), Double.valueOf(result));
    }

    /**
     * Test of setWeight method, of class GraphElement.
     */
    @Test
    public void testSetWeight() {
        System.out.println("setWeight");
        
        double pdblWeight = 5.0;
        instance.setWeight(pdblWeight);
        double expResult = pdblWeight;
        
        double result = instance.getWeight();
        assertEquals(Double.valueOf(expResult), Double.valueOf(result));
    }
    
}
