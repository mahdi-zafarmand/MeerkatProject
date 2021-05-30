/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.graph.impl;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IVertex;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class VertexTest {
    
    static IVertex pvtxSource = null;
    static TimeFrame strTimeFrame = null;
    static List<String> userAttributes = null;
    static List<String> sysAttributes = null;
    static double xValue = 0.9299599162704955;
    static double yValue = 0.4827600466302346;
    
    public VertexTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        //preparing the source vertex.
        pvtxSource = new Vertex();
        String strVertexID = "1";
        strTimeFrame = new TimeFrame("testTF");
        
        //generating test user attributes
        userAttributes = new ArrayList<>();
        userAttributes.add("Attribute1");
        userAttributes.add("Attribute2");
        userAttributes.add("Attribute3");
        userAttributes.add("File_ID");
        
        //adding a File ID
        pvtxSource.getUserAttributer().addAttribute(MeerkatSystem.FILE_ID,
                        strVertexID,
                        strTimeFrame);
        
        //adding some user Attributes
        for(int i=0; i<userAttributes.size(); i++){
            pvtxSource.getUserAttributer().addAttribute(userAttributes.get(i),
                                                userAttributes.get(i)+"Value",
                                                strTimeFrame);
        }
        
        //generating test system attributes
        sysAttributes = new ArrayList<>();
        sysAttributes.add(MeerkatSystem.COLOR);
        sysAttributes.add(MeerkatSystem.X);
        sysAttributes.add(MeerkatSystem.Y);
        
        //adding some system attributes
        pvtxSource.getSystemAttributer().addAttributeValue(
                                        MeerkatSystem.COLOR,
                                        MeerkatSystem.getDefaultVertexColor(),
                                        new Date(),
                                        strTimeFrame);
        
        pvtxSource.getSystemAttributer().addAttributeValue(
                                        MeerkatSystem.X,
                                        String.valueOf(xValue),
                                        new Date(),
                                        strTimeFrame);
        pvtxSource.getSystemAttributer().addAttributeValue(
                                        MeerkatSystem.Y,
                                        String.valueOf(yValue),
                                        new Date(),
                                        strTimeFrame);
    }
    
    @AfterClass
    public static void tearDownClass() {
        pvtxSource = null;
        strTimeFrame = null;
        userAttributes =  null;
        sysAttributes = null;
    }
    
    @Before
    public void setUp() {
        pvtxSource.getSystemAttributer().addAttributeValue(MeerkatSystem.COLOR, MeerkatSystem.getDefaultVertexColor(), new Date(), strTimeFrame);
        pvtxSource.getSystemAttributer().addAttributeValue(MeerkatSystem.X, String.valueOf(xValue), new Date(), strTimeFrame);
        pvtxSource.getSystemAttributer().addAttributeValue(MeerkatSystem.Y, String.valueOf(yValue), new Date(), strTimeFrame);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of updateAttributes method, of class Vertex.
     */
    @Test
    public void testUpdateAttributes() {
        System.out.println("updateAttributes");
 
        //creating another vertex and calling the method to be tested.
        Vertex instance = new Vertex();
        instance.updateAttributes(pvtxSource, strTimeFrame, strTimeFrame);
        
        //testing for user attributes
        for(String attribName : instance.getUserAttributer().getAttributeNames()){
            System.out.println("'Attribute Name: "+ attribName);
            Assert.assertTrue(userAttributes.contains(attribName));
        }
        
        //assert equals values for user and system attributes
        Assert.assertEquals("Attribute1"+"Value", instance.getUserAttributer().getAttributeValue("Attribute1", strTimeFrame));
        Assert.assertEquals("Attribute2"+"Value", instance.getUserAttributer().getAttributeValue("Attribute2", strTimeFrame));
        Assert.assertEquals("Attribute3"+"Value", instance.getUserAttributer().getAttributeValue("Attribute3", strTimeFrame));
        
        for(String attribName : instance.getSystemAttributer().getAttributeNames()){           
            Assert.assertTrue(sysAttributes.contains(attribName));
        }
        
        Assert.assertEquals(MeerkatSystem.getDefaultVertexColor(), instance.getSystemAttributer().getAttributeValue(sysAttributes.get(0), strTimeFrame));
        Assert.assertEquals(String.valueOf(xValue), instance.getSystemAttributer().getAttributeValue(sysAttributes.get(1), strTimeFrame));
        Assert.assertEquals(String.valueOf(yValue), instance.getSystemAttributer().getAttributeValue(sysAttributes.get(2), strTimeFrame));
    }

    /**
     * Test of updateAttributeValue method, of class Vertex.
     */
    @Test
    public void testUpdateAttributeValue() {
        System.out.println("updateAttributeValue");
        
        String strAttrName = userAttributes.get(0);
        String AttrValue = "cValue";

        pvtxSource.updateAttributeValue(strAttrName, AttrValue, strTimeFrame);
        
        Assert.assertEquals(AttrValue, pvtxSource.getUserAttributer().getAttributeValue(strAttrName, strTimeFrame));

    }

    /**
     * Test of updateColor method, of class Vertex.
     */
    @Test
    public void testUpdateColor() {
        System.out.println("updateColor");
        
        String pColor = "#000000";
        pvtxSource.updateColor(pColor, strTimeFrame);
        
        Assert.assertEquals(pColor, pvtxSource.getSystemAttributer().getAttributeValue(MeerkatSystem.COLOR, strTimeFrame));

    }

    /**
     * Test of getColor method, of class Vertex.
     */
    @Test
    public void testGetColor() {
        System.out.println("getColor: "+ pvtxSource.getSystemAttributer().getAttributeValue(MeerkatSystem.COLOR, strTimeFrame));
        
        String color_Black = "#000000";
        pvtxSource.updateColor(color_Black, strTimeFrame);
        
        String expResult = color_Black;
        String result = pvtxSource.getColor(strTimeFrame);
        
        assertEquals(expResult, result);
        
    }

    /**
     * Test of updateXYPosition method, of class Vertex.
     */
    @Test
    public void testUpdateXYPosition() {
        System.out.println("updateXYPosition");
        
        double X = 0.5;
        double Y = 0.4;

        pvtxSource.updateXYPosition(X, Y, strTimeFrame);
        
        Assert.assertEquals(String.valueOf(X), pvtxSource.getSystemAttributer().getAttributeValue(MeerkatSystem.X, strTimeFrame));
        Assert.assertEquals(String.valueOf(Y), pvtxSource.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, strTimeFrame));
    }

    /**
     * Test of getXPosition method, of class Vertex.
     */
    @Test
    public void testGetXPosition() {
        System.out.println("getXPosition");
        
        pvtxSource.updateXYPosition(xValue, yValue, strTimeFrame);
        String expResult = String.valueOf(xValue);
        
        String result = pvtxSource.getXPosition(strTimeFrame);
        assertEquals(expResult, result);
    }

    /**
     * Test of getYPosition method, of class Vertex.
     */
    @Test
    public void testGetYPosition() {
        System.out.println("getYPosition");
        
        double xVal = 0.4;
        double yVal = 0.6;
        
        pvtxSource.updateXYPosition(xVal, yVal, strTimeFrame);
        String expResult = String.valueOf(yVal);
        
        String result = pvtxSource.getYPosition(strTimeFrame);
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Vertex.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        int pintId = 0;
        Vertex instance = new Vertex();
        instance.setId(pintId);
        
        assertEquals(pintId, instance.getId());
    }

    /**
     * Test of getId method, of class Vertex.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Vertex instance = new Vertex();
        
        int expResult = 0;
        instance.setId(expResult);
        
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWeight method, of class Vertex.
     */
    @Test
    public void testSetWeight() {
        System.out.println("setWeight");
        
        double pdblWeight = 0.0;
        Vertex instance = new Vertex();
        
        instance.setWeight(pdblWeight);
        
        assertEquals(Double.valueOf(pdblWeight), Double.valueOf(instance.getWeight()));
    }

    /**
     * Test of getWeight method, of class Vertex.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        
        Vertex instance = new Vertex();
        double expResult = 10.0;
        instance.setWeight(expResult);
        
        double result = instance.getWeight();
        assertEquals(Double.valueOf(expResult), Double.valueOf(result));
    }
    
}
