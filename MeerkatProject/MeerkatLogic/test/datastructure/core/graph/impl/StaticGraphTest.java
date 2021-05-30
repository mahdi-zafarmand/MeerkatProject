/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.graph.impl;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IStaticGraph;
import datastructure.core.graph.classinterface.IVertex;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
public class StaticGraphTest {
    
    
    private IStaticGraph<IVertex,IEdge<IVertex>> instancestatgraph;
    private int noOfVertices;
    private int noOfEdges;
    TimeFrame timeFrame;
    List<String> userAttributes = null;
    List<String> sysAttributes = null;
    Set<String> setNumericalAttributes = null;
    Map<String, Boolean> mapAttributeAndType = null;
    double xValue;
    double yValue;
    
    
    public StaticGraphTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        fillListAttributes();
        noOfVertices = 5;
        noOfEdges = 3;
        timeFrame = new  TimeFrame("year1");
        xValue = 0.9299599162704955;
        yValue = 0.4827600466302346;
        
        
        instancestatgraph  = new StaticGraph<>(0 , 0);
        for(int i = 0; i < noOfVertices; i++){ 
            IVertex vertex = new Vertex();
            vertex.setId(i);
            instancestatgraph.addVertex(vertex);
        }
        
        //v1-v2, v2-v4, v1-v5
        IVertex v1 = instancestatgraph.getVertex(0);
        IVertex v2 = instancestatgraph.getVertex(2);
        IVertex v4 = instancestatgraph.getVertex(3);
        IVertex v5 = instancestatgraph.getVertex(4);
        fillVertexAttributesSet1(v1);
        fillVertexAttributesSet2(v2);
        fillVertexAttributesSet1(v5);

        IEdge edge1 = new Edge(v1,v2, false, 0);
        edge1.setId(0);
        IEdge edge2 = new Edge(v2,v4, false, 0);
        edge2.setId(1);
        IEdge edge3 = new Edge(v1,v5, false, 0);
        edge3.setId(2);
        fillEdgeAttributesSet1(edge1);
        fillEdgeAttributesSet2(edge2);
        
        
        
        
        instancestatgraph.addEdge(edge1);
        instancestatgraph.addEdge(edge2);
        instancestatgraph.addEdge(edge3);
        
    }
    private void fillListAttributes(){
       //generating test user attributes
       userAttributes = new ArrayList<>();
       userAttributes.add("Attribute1");
       userAttributes.add("Attribute2");
       userAttributes.add("Attribute3");
       userAttributes.add("NumericalAttribute");
       //generating test system attributes
       sysAttributes = new ArrayList<>();
       sysAttributes.add(MeerkatSystem.COLOR);
       sysAttributes.add(MeerkatSystem.X);
       sysAttributes.add(MeerkatSystem.Y);
       sysAttributes.add(MeerkatSystem.TYPE);
       
       setNumericalAttributes = new HashSet();
       setNumericalAttributes.add("NumericalAttribute");
       setNumericalAttributes.add(MeerkatSystem.X);
       setNumericalAttributes.add(MeerkatSystem.Y);
       
       
       mapAttributeAndType = new HashMap();
       mapAttributeAndType.put("Attribute1", false);
       mapAttributeAndType.put("Attribute2", false);
       mapAttributeAndType.put("Attribute3", false);
       mapAttributeAndType.put("NumericalAttribute", true);
       mapAttributeAndType.put(MeerkatSystem.X, true);
       mapAttributeAndType.put(MeerkatSystem.COLOR, false);
       mapAttributeAndType.put(MeerkatSystem.Y, true);
       mapAttributeAndType.put(MeerkatSystem.TYPE, false);
       
    }
    private void fillVertexAttributesSet2(IVertex v){
    
       
       
       //adding some user Attributes
       for(int i=0; i<userAttributes.size(); i++){
           if(!userAttributes.get(i).equals("NumericalAttribute")){
                v.getUserAttributer().addAttribute(userAttributes.get(i),
                                               userAttributes.get(i)+"Value",
                                               timeFrame);
                }
       }
       

       
       //adding some system attributes
       v.getSystemAttributer().addAttributeValue(
                                       MeerkatSystem.TYPE,
                                       "Person",
                                       new Date(),
                                       timeFrame);
       
       v.getSystemAttributer().addAttributeValue(
                                       MeerkatSystem.X,
                                       String.valueOf(xValue),
                                       new Date(),
                                       timeFrame);
       v.getSystemAttributer().addAttributeValue(
                                       MeerkatSystem.Y,
                                       String.valueOf(yValue),
                                       new Date(),
                                       timeFrame);
    }
    
    private void fillVertexAttributesSet1(IVertex v){
    
       
       
       //adding some user Attributes
       
       for(int i=0; i<userAttributes.size(); i++){
           if(!userAttributes.get(i).equals("NumericalAttribute"))
                v.getUserAttributer().addAttribute(userAttributes.get(i),
                                               userAttributes.get(i)+"Value",
                                               timeFrame);
       }
       
       v.getUserAttributer().addAttribute("NumericalAttribute",
                                               Integer.toString(42),
                                               timeFrame);

       
       //adding some system attributes
       v.getSystemAttributer().addAttributeValue(
                                       MeerkatSystem.COLOR,
                                       MeerkatSystem.getDefaultVertexColor(),
                                       new Date(),
                                       timeFrame);
       
       v.getSystemAttributer().addAttributeValue(
                                       MeerkatSystem.X,
                                       String.valueOf(xValue),
                                       new Date(),
                                       timeFrame);
       v.getSystemAttributer().addAttributeValue(
                                       MeerkatSystem.Y,
                                       String.valueOf(yValue),
                                       new Date(),
                                       timeFrame);
    }
    
    private void fillEdgeAttributesSet1(IEdge e){
    
       
       
       //adding some user Attributes
       
       for(int i=0; i<userAttributes.size(); i++){
           if(!userAttributes.get(i).equals("NumericalAttribute"))
                e.getUserAttributer().addAttribute(userAttributes.get(i),
                                               userAttributes.get(i)+"Value",
                                               timeFrame);
       }
       
       e.getUserAttributer().addAttribute("NumericalAttribute",
                                               Integer.toString(42),
                                               timeFrame);

       
       //adding some system attributes
       e.getSystemAttributer().addAttributeValue(
                                       MeerkatSystem.COLOR,
                                       MeerkatSystem.getDefaultVertexColor(),
                                       new Date(),
                                       timeFrame);
       
       e.getSystemAttributer().addAttributeValue(
                                       MeerkatSystem.X,
                                       String.valueOf(xValue),
                                       new Date(),
                                       timeFrame);
       e.getSystemAttributer().addAttributeValue(
                                       MeerkatSystem.Y,
                                       String.valueOf(yValue),
                                       new Date(),
                                       timeFrame);
    }
        
    private void fillEdgeAttributesSet2(IEdge e){
    
       
       
       //adding some user Attributes
       for(int i=0; i<userAttributes.size(); i++){
           if(!userAttributes.get(i).equals("NumericalAttribute")){
                e.getUserAttributer().addAttribute(userAttributes.get(i),
                                               userAttributes.get(i)+"Value",
                                               timeFrame);
                }
       }
       

       
       //adding some system attributes
       e.getSystemAttributer().addAttributeValue(
                                       MeerkatSystem.TYPE,
                                       "Person",
                                       new Date(),
                                       timeFrame);
       
       e.getSystemAttributer().addAttributeValue(
                                       MeerkatSystem.X,
                                       String.valueOf(xValue),
                                       new Date(),
                                       timeFrame);
       e.getSystemAttributer().addAttributeValue(
                                       MeerkatSystem.Y,
                                       String.valueOf(yValue),
                                       new Date(),
                                       timeFrame);
    }    
    
    @After
    public void tearDown() {
        
        instancestatgraph = null;
        timeFrame = null;
        userAttributes = null;
        sysAttributes = null;
        setNumericalAttributes = null;
        mapAttributeAndType = null;
    }

    /**
     * Test of setId method, of class StaticGraph.
     */
    @Ignore
    @Test
    public void testSetId() {
        System.out.println("setId");
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class StaticGraph.
     */
    @Ignore
    @Test
    public void testGetId() {
        System.out.println("getId");
       
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVertex method, of class StaticGraph.
     */
    @Test
    public void testGetVertex() {
        System.out.println("getVertex");
        
        Random rand = new Random();
        int  vertexId = rand.nextInt(this.noOfVertices);
        int expResult = vertexId;
        
        int result = instancestatgraph.getVertex(vertexId).getId();
        
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getAllVertices method, of class StaticGraph.
     */
    @Test
    public void testGetAllVertices() {
        System.out.println("getAllVertices");
        
        int expResult = noOfVertices;
        List listVertices = instancestatgraph.getAllVertices();
        int result = listVertices.size();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getAllVertexIds method, of class StaticGraph.
     */
    @Test
    public void testGetAllVertexIds() {
        System.out.println("getAllVertexIds");
        
        List<Integer> expResult =  new ArrayList();
        for(int i = 0; i < noOfVertices; i++){
            expResult.add(i);
        }
        List<Integer> result = instancestatgraph.getAllVertexIds();
        assertEquals(expResult, result);
        
        //TODO - add cases after removing few vertices

    }

    /**
     * Test of addVertex method, of class StaticGraph.
     */
    @Test
    public void testAddVertex() {
        System.out.println("addVertex");
        //check no of vertices after adding the new vertex
        IVertex vertex = new Vertex();
        vertex.setId(++noOfVertices);
        instancestatgraph.addVertex(vertex);
        
        int noOfVerticesExpected = noOfVertices;
        int resultNoOfVertices = instancestatgraph.getVertexCount();
        assertEquals(noOfVerticesExpected, resultNoOfVertices);
    }

    /**
     * Test of addVertices method, of class StaticGraph.
     */
    @Test
    public void testAddVertices_List() {
        System.out.println("addVertices from a list");
        
        List<IVertex> lstVertices = new ArrayList();
        IVertex v1 = new Vertex();
        v1.setId(++noOfVertices);
        IVertex v2 = new Vertex();
        v2.setId(++noOfVertices);
        lstVertices.add(v1);
        lstVertices.add(v2);
        
        instancestatgraph.addVertices(lstVertices);
        
        int noOfVerticesExpected = noOfVertices;
        int noOfVerticesResult = instancestatgraph.getVertexCount();
        
        assertEquals(noOfVerticesExpected, noOfVerticesResult);
        
        
    }

    /**
     * Test of addVertices method, of class StaticGraph.
     */
    @Test
    public void testAddVertices_Set() {
        System.out.println("addVertices from a set");
        Set<IVertex> setVertices = new HashSet();
        IVertex v1 = new Vertex();
        v1.setId(++noOfVertices);
        IVertex v2 = new Vertex();
        v2.setId(++noOfVertices);
        setVertices.add(v1);
        setVertices.add(v2);
        
        instancestatgraph.addVertices(setVertices);
        int noOfVerticesExpected = noOfVertices;
        int noOfVerticesResult = instancestatgraph.getVertexCount();
        assertEquals(noOfVerticesExpected, noOfVerticesResult);
    }

    /**
     * Test of removeVertex method, of class StaticGraph.
     */
    @Test
    public void testRemoveVertex() {
        System.out.println("removeVertex");
        //check no of vertices after removing the new vertex
        Random rand = new Random();
        int  idVertexToRemove = rand.nextInt(this.noOfVertices);
        
        IVertex vertex = instancestatgraph.getVertex(idVertexToRemove);
        
        instancestatgraph.removeVertex(vertex);
        
        int noOfVerticesExpected = --noOfVertices;
        int resultNoOfVertices = instancestatgraph.getVertexCount();
        assertEquals(noOfVerticesExpected, resultNoOfVertices);
    }

    /**
     * Test of removeVertices method, of class StaticGraph.
     */
    @Test
    public void testRemoveVertices() {
        //method has no usages
    }

    /**
     * Test of getVertexAllAttributeNames method, of class StaticGraph.
     */
    @Test
    public void testGetVertexAllAttributeNames() {
        System.out.println("getVertexAllAttributeNames");
        
        Set<String> expResult = new HashSet();
        for(String userAttribute : userAttributes){
            expResult.add(userAttribute);
        }
        for(String userAttribute : sysAttributes){
            expResult.add(userAttribute);
        }
        Set<String> result = instancestatgraph.getVertexAllAttributeNames();
        assertTrue(expResult.equals(result));
        
    }

    /**
     * Test of getVertexNumericalAttributeNames method, of class StaticGraph.
     */
    @Test
    public void testGetVertexNumericalAttributeNames() {
        System.out.println("getVertexNumericalAttributeNames");
        Set<String> expResult = setNumericalAttributes;
        
        Set<String> result = instancestatgraph.getVertexNumericalAttributeNames();
        
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getVertexAttributeNamesWithType method, of class StaticGraph.
     */
    @Test
    public void testGetVertexAttributeNamesWithType() {
        System.out.println("getVertexAttributeNamesWithType");
        
        Map<String, Boolean> expResult = mapAttributeAndType;
        Map<String, Boolean> result = instancestatgraph.getVertexAttributeNamesWithType();
        
        //System.out.println("   " + expResult);
        //System.out.println("   " + result);
        assertTrue(expResult.equals(result));
        
    }

    /**
     * Test of getAllEdgeIds method, of class StaticGraph.
     */
    @Test
    public void testGetAllEdgeIds() {
        System.out.println("getAllEdgeIds");
        
        
        List<Integer> expResult =  new ArrayList();
        for(int i = 0; i < noOfEdges; i++){
            expResult.add(i);
        }
        List<Integer> result = instancestatgraph.getAllEdgeIds();
        assertEquals(expResult, result);
        
        //TODO - add cases after removing few edges
        
        
    }

    /**
     * Test of getAllEdges method, of class StaticGraph.
     */
    @Test
    public void testGetAllEdges() {
        System.out.println("getAllEdges");
        
        int noOfEdgesExpected = noOfEdges;
        
        List<IEdge<IVertex>> result = instancestatgraph.getAllEdges();
        int noOfEdgesResult = result.size();
        assertEquals(noOfEdgesExpected, noOfEdgesResult);
        
        //TODO - add cases after removing few edges
    }

    /**
     * Test of addEdge method, of class StaticGraph.
     */
    @Test
    public void testAddEdge() {
        System.out.println("addEdge");

        //check no of edges after adding the new edge
        IVertex v1 = new Vertex();
        v1.setId(++noOfVertices);
        IVertex v2 = new Vertex();
        v2.setId(++noOfVertices);
        instancestatgraph.addVertex(v1);
        instancestatgraph.addVertex(v2);
        IEdge edge = new Edge(v1,v2, false, 0);
        edge.setId(++noOfEdges);
        instancestatgraph.addEdge(edge);
        
        int noOfEdgesExpected = noOfEdges;
        int resultNoOfEdges = instancestatgraph.getEdgeCount();
        assertEquals(noOfEdgesExpected, resultNoOfEdges);
        
        //TODO add edge directed case
    }

    /**
     * Test of addEdges method, of class StaticGraph.
     */
    @Test
    public void testAddEdges() {
        System.out.println("addEdges");
        
        
        //check no of edges after adding the new edge
        IVertex v1 = new Vertex();
        v1.setId(++noOfVertices);
        IVertex v2 = new Vertex();
        v2.setId(++noOfVertices);
        IVertex v3 = new Vertex();
        v3.setId(++noOfVertices);
        instancestatgraph.addVertex(v1);
        instancestatgraph.addVertex(v2);
        instancestatgraph.addVertex(v3);
        IEdge edge1 = new Edge(v1,v2, false, 0);
        edge1.setId(++noOfEdges);
        IEdge edge2 = new Edge(v2,v3, false, 0);
        edge2.setId(++noOfEdges);
        
        List<IEdge<IVertex>> listEdges = new ArrayList();
        listEdges.add(edge1);
        listEdges.add(edge2);
        instancestatgraph.addEdges(listEdges);
        
        int noOfEdgesExpected = noOfEdges;
        int resultNoOfEdges = instancestatgraph.getEdgeCount();
        assertEquals(noOfEdgesExpected, resultNoOfEdges);
        
    }

    /**
     * Test of getEdge method, of class StaticGraph.
     */
    @Test
    public void testGetEdge() {
        System.out.println("getEdge");
        
        int edgeId = noOfEdges-1;
        int expectedResult = edgeId;
        IEdge result = instancestatgraph.getEdge(edgeId);
        int resultId = result.getId();
        assertEquals(expectedResult, resultId);
        
        //TODO get edgeId which does not exist
        
       
    }

    /**
     * Test of removeEdge method, of class StaticGraph.
     */
    @Test
    public void testRemoveEdge_GenericType() {
        System.out.println("removeEdge");
        
        IVertex v1 = new Vertex();
        v1.setId(++noOfVertices);
        IVertex v2 = new Vertex();
        v2.setId(++noOfVertices);
        instancestatgraph.addVertex(v1);
        instancestatgraph.addVertex(v2);
        IEdge edge = new Edge(v1,v2, false, 0);
        edge.setId(++noOfEdges);
        instancestatgraph.addEdge(edge);
        
        instancestatgraph.removeEdge(edge);
        noOfEdges--;
        
        int noOfEdgesExpected = noOfEdges;
        int resultNoOfEdges = instancestatgraph.getEdgeCount();
        assertEquals(noOfEdgesExpected, resultNoOfEdges);
    }

    /**
     * Test of removeEdge method, of class StaticGraph.
     */
    @Test
    public void testRemoveEdge_List() {
        System.out.println("removeEdge");
        
        IVertex v1 = new Vertex();
        v1.setId(++noOfVertices);
        IVertex v2 = new Vertex();
        v2.setId(++noOfVertices);
        IVertex v3 = new Vertex();
        v3.setId(++noOfVertices);
        instancestatgraph.addVertex(v1);
        instancestatgraph.addVertex(v2);
        instancestatgraph.addVertex(v3);
        IEdge edge1 = new Edge(v1,v2, false, 0);
        edge1.setId(++noOfEdges);
        IEdge edge2 = new Edge(v2,v3, false, 0);
        edge2.setId(++noOfEdges);
        
        List<IEdge<IVertex>> listEdges = new ArrayList();
        listEdges.add(edge1);
        listEdges.add(edge2);
        instancestatgraph.addEdges(listEdges);
        
        instancestatgraph.removeEdge(listEdges);
        
        noOfEdges = noOfEdges - listEdges.size();
        
        int noOfEdgesExpected = noOfEdges;
        int resultNoOfEdges = instancestatgraph.getEdgeCount();
        assertEquals(noOfEdgesExpected, resultNoOfEdges);
           
    }

    /**
     * Test of clearEdges method, of class StaticGraph.
     */
    @Test
    public void testClearEdges() {
        System.out.println("clearEdges");
        
        int noOfEdgesExpected = 0;
        
        instancestatgraph.clearEdges();
        int resultNoOfEdges = instancestatgraph.getEdgeCount();
        assertEquals(noOfEdgesExpected, resultNoOfEdges);
    }

    /**
     * Test of getMaxEdgeId method, of class StaticGraph.
     */
    @Test
    public void testGetMaxEdgeId() {
        System.out.println("getMaxEdgeId");
        
        int expResult = noOfEdges-1;
        int result = instancestatgraph.getMaxEdgeId();
        assertEquals(expResult, result);
    
    }

    /**
     * Test of getEdgeAttributeNames method, of class StaticGraph.
     */
    @Ignore
    @Test
    public void testGetEdgeUserAttributeNames() {
        // Error caused in a check-in by somebody. Commenting to avoid errors
        // in the repositorys
        /*
        System.out.println("getEdgeUserAttributeNames");
        Set<String> expResult = new HashSet();
        for(String userAttribute : userAttributes){
            expResult.add(userAttribute);
        }
        
        Set<String> result = instancestatgraph.getEdgeUserAttributeNames();
        //System.out.println(" " + expResult);
        //System.out.println(" " + result);
        assertTrue(expResult.equals(result));
        */
    }

    /**
     * Test of getEdgeNumericalAttributeNames method, of class StaticGraph.
     */
    @Test
    public void testGetEdgeNumericalAttributeNames() {
        System.out.println("getEdgeNumericalAttributeNames");
        Set<String> expResult = setNumericalAttributes;
        
        Set<String> result = instancestatgraph.getEdgeNumericalAttributeNames();
        
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getEdgeAttributeNamesWithType method, of class StaticGraph.
     */
    @Test
    public void testGetEdgeAttributeNamesWithType() {
        System.out.println("getEdgeAttributeNamesWithType");
        Map<String, Boolean> expResult = mapAttributeAndType;
        
        Map<String, Boolean> result = instancestatgraph.getEdgeAttributeNamesWithType();
        
        System.out.println("-------------- " + expResult);
        System.out.println("-------------- " + result);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getVertexCount method, of class StaticGraph.
     */
    @Test
    public void testGetVertexCount() {
        System.out.println("getVertexCount");
        
        int expResult = noOfVertices;
        int result = instancestatgraph.getVertexCount();
        assertEquals(expResult, result);

    }

    /**
     * Test of getEdgeCount method, of class StaticGraph.
     */
    @Test
    public void testGetEdgeCount() {
        System.out.println("getEdgeCount");
        
        int expResult = noOfEdges;
        int result = instancestatgraph.getEdgeCount();
        assertEquals(expResult, result);
    }



    /**
     * Test of getLastChangeTime method, of class StaticGraph.
     */
    @Test
    public void testGetLastChangeTime() {
        System.out.println("getLastChangeTime");
        
        IVertex v1 = new Vertex();
        v1.setId(++noOfVertices);
        instancestatgraph.addVertex(v1);
        IVertex v2 = new Vertex();
        v2.setId(++noOfVertices);
        instancestatgraph.addVertex(v2);
        IEdge edge = new Edge(v1,v2, false, 0);
        edge.setId(++noOfEdges);
        
        Date expResult = new Date();
        instancestatgraph.addEdge(edge);
        
        
        Date result = instancestatgraph.getLastChangeTime();
        assertTrue(expResult.compareTo(result)==0 || expResult.compareTo(result)==-1);

    }
    
}
