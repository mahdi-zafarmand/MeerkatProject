/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.graph.impl;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IEdge;
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
public class EdgeTest {
    
    static IEdge edge = null;
    static IVertex pvtxSource = null;
    static IVertex pvtxDestination = null;
    static TimeFrame strTimeFrame = null;
    static List<String> userAttributes = null;
    static List<String> sysAttributes = null;
    
    public EdgeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        pvtxSource = new Vertex();
        pvtxDestination = new Vertex();
        strTimeFrame = new TimeFrame("testTF");
   
        edge = new Edge(pvtxSource, pvtxDestination, true);
        
        //generating test user attributes
        userAttributes = new ArrayList<>();
        userAttributes.add("Attribute1");
        userAttributes.add("Attribute2");
        userAttributes.add("Attribute3");
        
        //adding some user Attributes
        for(int i=0; i<userAttributes.size(); i++){
            edge.getUserAttributer().addAttribute(userAttributes.get(i),
                                                userAttributes.get(i)+"Value",
                                                strTimeFrame);
        }
        
        //generating test system attributes
        sysAttributes = new ArrayList<>();
        sysAttributes.add(MeerkatSystem.COLOR);
        sysAttributes.add(MeerkatSystem.X);
        sysAttributes.add(MeerkatSystem.Y);
        
        //adding some system attributes
        edge.getSystemAttributer().addAttributeValue(
                                        MeerkatSystem.COLOR,
                                        MeerkatSystem.getDefaultEdgeColor(),
                                        new Date(),
                                        strTimeFrame);      
    }
    
    @AfterClass
    public static void tearDownClass() {
        
        edge = null;
        pvtxSource = null;
        pvtxDestination = null;
        strTimeFrame = null;
        userAttributes =  null;
        sysAttributes = null;
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of updateAttributes method, of class Edge.
     */
    @Test
    public void testUpdateAttributes() {
        System.out.println("updateAttributes");
        
        IVertex vSource = new Vertex();
        IVertex vDestination = new Vertex();
        
        Edge instance = new Edge(vSource, vDestination, true);
        
        instance.updateAttributes(edge, strTimeFrame, strTimeFrame);
        
        //testing for user attributes
        for(String attribName : instance.getUserAttributer().getAttributeNames()){
            Assert.assertTrue(userAttributes.contains(attribName));
        }
        
        //assert equals values for user and system attributes
        Assert.assertEquals("Attribute1"+"Value", instance.getUserAttributer().getAttributeValue("Attribute1", strTimeFrame));
        Assert.assertEquals("Attribute2"+"Value", instance.getUserAttributer().getAttributeValue("Attribute2", strTimeFrame));
        Assert.assertEquals("Attribute3"+"Value", instance.getUserAttributer().getAttributeValue("Attribute3", strTimeFrame));
        
        for(String attribName : instance.getSystemAttributer().getAttributeNames()){           
            Assert.assertTrue(sysAttributes.contains(attribName));
        }
        
        Assert.assertEquals(MeerkatSystem.getDefaultEdgeColor(), instance.getSystemAttributer().getAttributeValue(sysAttributes.get(0), strTimeFrame));   
        
    }

    /**
     * Test of updateAttributeValue method, of class Edge.
     */
    @Test
    public void testUpdateAttributeValue() {
        System.out.println("updateAttributeValue");
        
        String strAttrName = userAttributes.get(2);
        String AttrValue = "cValue";

        edge.updateAttributeValue(strAttrName, AttrValue, strTimeFrame);
        
        Assert.assertEquals(AttrValue, edge.getUserAttributer().getAttributeValue(strAttrName, strTimeFrame));
        
    }

    /**
     * Test of setId method, of class Edge.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        int pintId = 0;
        
        edge.setId(pintId);
        
        Assert.assertEquals(pintId, edge.getId());
    }

    /**
     * Test of getId method, of class Edge.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        
        int expResult = 1;
        edge.setId(expResult);
        
        int result = edge.getId();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setWeight method, of class Edge.
     */
    @Test
    public void testSetWeight() {
        System.out.println("setWeight");
        double pdblWeight = 3.0;
        
        edge.setWeight(pdblWeight);
        
        Assert.assertEquals(Double.valueOf(pdblWeight), Double.valueOf(edge.getWeight()));
    }

    /**
     * Test of getWeight method, of class Edge.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        
        
        double expResult = 2.0;
        edge.setWeight(expResult);
        
        double result = edge.getWeight();
        assertEquals(Double.valueOf(expResult), Double.valueOf(result));
    }

    /**
     * Test of isDirected method, of class Edge.
     */
    @Test
    public void testIsDirected() {
        System.out.println("isDirected");

        boolean result = edge.isDirected();
        assertEquals(true, result);
    }

    /**
     * Test of getSource method, of class Edge.
     */
    @Test
    public void testGetSource() {
        System.out.println("getSource");
        
        Object expResult = pvtxSource;
        Object result = edge.getSource();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getDestination method, of class Edge.
     */
    @Test
    public void testGetDestination() {
        System.out.println("getDestination");
        
        Object expResult = pvtxDestination;
        Object result = edge.getDestination();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getVStatus method, of class Edge.
     */
    @Test
    public void testGetVStatus() {
        System.out.println("getVStatus");
        
        Edge instance1 = new Edge(pvtxSource, pvtxDestination, true);  
        Edge.VertexStatus expResult = Edge.VertexStatus.Source;
        Edge.VertexStatus result = instance1.getVStatus(pvtxSource);
        
        Edge instance2 = new Edge(pvtxSource, pvtxDestination, true);
        Edge.VertexStatus expResult2 = Edge.VertexStatus.Destination;
        Edge.VertexStatus result2 = instance2.getVStatus(pvtxDestination);
        
        IVertex vertexInstance = new Vertex();
        Edge instance3 = new Edge(vertexInstance, pvtxDestination, true); 
        Edge.VertexStatus expResult3 = Edge.VertexStatus.NULL;
        Edge.VertexStatus result3 = instance3.getVStatus(pvtxSource);
        
        Edge instance4 = new Edge(pvtxSource, pvtxDestination, false);
        Edge.VertexStatus expResult4 = Edge.VertexStatus.EndPoint;
        Edge.VertexStatus result4 = instance4.getVStatus(pvtxDestination);
        
        assertEquals(expResult, result);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
    }

    /**
     * Test of getOtherEndPoint method, of class Edge.
     */
    @Test
    public void testGetOtherEndPoint() {
        System.out.println("getOtherEndPoint");
        
        Edge instance = new Edge(pvtxSource, pvtxDestination, true);  
        
        Object expResult = pvtxDestination;
        Object result = instance.getOtherEndPoint(pvtxSource);
        assertEquals(expResult, result);
        
        Object expResult2 = pvtxSource;
        Object result2 = instance.getOtherEndPoint(pvtxDestination);
        assertEquals(expResult2, result2);
        
        IVertex vertexInstance = new Vertex();
        Edge instance3 = new Edge(vertexInstance, pvtxDestination, true); 
        Edge.VertexStatus expResult3 = Edge.VertexStatus.NULL;
        Edge.VertexStatus result3 = instance3.getVStatus(pvtxSource);
        assertEquals(expResult3, result3);
    }

    /**
     * Test of compareTo method, of class Edge.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        
        IVertex sourceVertex = new Vertex();
        IVertex destiVertex = new Vertex();
        Edge instance = new Edge(sourceVertex, destiVertex, true); 
        instance.setId(10);
        edge.setId(5);
        
        int expResult = instance.getId() - edge.getId();
        int result = instance.compareTo(edge);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Edge.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        
        IVertex sourceVertex = new Vertex();
        sourceVertex.setId(2);
        
        IVertex destiVertex = new Vertex();
        destiVertex.setId(5);
        Edge instance = new Edge(sourceVertex, destiVertex, true); 
        instance.setWeight(2.0);
        instance.setId(4);
        
        String expResult = "4 : 2 - 5 (2.0) ";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of updateColor method, of class Edge.
     */
    @Test
    public void testUpdateColor() {
        System.out.println("updateColor");
        String pColor = "#000000";

        edge.updateColor(pColor, strTimeFrame);
        
        Assert.assertEquals(pColor, edge.getColor(strTimeFrame));
    }

    /**
     * Test of getColor method, of class Edge.
     */
    @Test
    public void testGetColor() {
        System.out.println("getColor");
        
        String pColor = "#000fff";
        edge.updateColor(pColor, strTimeFrame);
        String expResult = pColor;
        
        String result = edge.getColor(strTimeFrame);
        assertEquals(expResult, result);
    }
    
}
