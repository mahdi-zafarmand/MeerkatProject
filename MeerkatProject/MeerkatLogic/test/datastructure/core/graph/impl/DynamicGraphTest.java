/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.graph.impl;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IStaticGraph;
import datastructure.core.graph.classinterface.IVertex;
import java.util.ArrayList;
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
public class DynamicGraphTest {
    
    private IDynamicGraph<IVertex,IEdge<IVertex>> dynamicGraphInstance = null;
    private IStaticGraph<IVertex,IEdge<IVertex>> statgraph1;
    private IStaticGraph<IVertex,IEdge<IVertex>> statgraph2;
    private List<IVertex> listVertices;
    private Set<String> setCommunities;
    private List<IVertex> listVerticesTF1;
    private Set<String> setCommunitiesTF1;
    private Set<String> setCommunitiesTF2;
    private List<IEdge<IVertex>> listEdges;
    private List<IEdge<IVertex>> listEdgesTF1;
    private Map<String, String> mapGommunityColorsExpected;
    private int index_MeerkatSystem_ColorCommunities;
    private String strTimeFrame1;
    private String strTimeFrame2;
    private TimeFrame timeFrame1;
    private TimeFrame timeFrame2;
    private int noOfVertices;
    private int noOfEdges;
    private int noOfVerticesTF1;
    private int noOfEdgesTF1;
    private int noOfVerticesTF2;
    private int noOfEdgesTF2;
    double xValue;
    double yValue;
    
    public DynamicGraphTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
       
       dynamicGraphInstance = new DynamicGraph<>(0,0);
       listVertices = new ArrayList<>();
       listEdges = new ArrayList<>();
       mapGommunityColorsExpected = new HashMap<>();
       index_MeerkatSystem_ColorCommunities = 0;
       setCommunities = new HashSet<>();
       
       //TF1
       //vertices = 1,2,3,4,5
       //v1-v2, v2-v4, v1-v5
       //communities = 1,2,3
       prepareTF1();
       
       //TF2
       //vertice in tf 2  = 1,2,4,6
       //edges in tf2 = v1-v2, v4-v6
       //communities = 1,4,5,2
       prepareTF2();
       
       noOfVertices = 6;
       noOfEdges = 4;
        
    }
    private void prepareTF1(){
        
        noOfVerticesTF1 = 5;
        noOfEdgesTF1 = 3;
        
        timeFrame1 = new  TimeFrame("year1");
        xValue = 0.9299599162704955;
        yValue = 0.4827600466302346;
        listVerticesTF1 = new ArrayList<>();
        listEdgesTF1 = new ArrayList<>();
        statgraph1  = new StaticGraph<>(0 , 0);
        setCommunitiesTF1 = new HashSet<>();
        
        
        dynamicGraphInstance.addGraph(timeFrame1, statgraph1);
        
        for(int i = 0; i < noOfVerticesTF1; i++){ 
            IVertex vertex = new Vertex();
            dynamicGraphInstance.addVertex(vertex, timeFrame1);
            this.setVertexFileId(vertex, i+1, timeFrame1);
            listVerticesTF1.add(vertex);
            listVertices.add(vertex);
        }
        //vertices = 1,2,3,4,5
        //v1-v2, v2-v4, v1-v5
        //communities = 1,2,3
        IVertex v1 = dynamicGraphInstance.getVertex(0);
        IVertex v2 = dynamicGraphInstance.getVertex(1);
        IVertex v4 = dynamicGraphInstance.getVertex(3);
        IVertex v5 = dynamicGraphInstance.getVertex(4);
        
        setCommunity(v1, 1+"", timeFrame1);
        setCommunitiesTF1.add(1+"");
        
        setCommunity(v2, 2+"", timeFrame1);
        setCommunitiesTF1.add(2+"");
        
        setCommunity(v4, 3+"", timeFrame1);
        setCommunitiesTF1.add(3+"");
        
        setCommunity(v5, 3+"", timeFrame1);
        setCommunitiesTF1.add(3+"");

        IEdge edge1 = new Edge(v1,v2, false, 0);//id = 0
        
        IEdge edge2 = new Edge(v2,v4, false, 0);//id = 1
        
        IEdge edge3 = new Edge(v1,v5, false, 0);//id = 2
        
        
        dynamicGraphInstance.addEdge(edge1, timeFrame1);
        dynamicGraphInstance.addEdge(edge2, timeFrame1);
        dynamicGraphInstance.addEdge(edge3, timeFrame1);
        listEdges.add(edge1);
        listEdges.add(edge2);
        listEdges.add(edge3);
        listEdgesTF1.add(edge1);
        listEdgesTF1.add(edge2);
        listEdgesTF1.add(edge3);
    
    }
    private void prepareTF2(){
        
        noOfVerticesTF2 = 4;
        noOfEdgesTF2 = 2;
        
        timeFrame2 = new  TimeFrame("year2");
        xValue = 0.9299599162704955;
        yValue = 0.4827600466302346;
        
        
        setCommunitiesTF2 = new HashSet<>();
        statgraph2  = new StaticGraph<>(0 , 0);
        dynamicGraphInstance.addGraph(timeFrame2, statgraph2);
        
        //vertice in tf 2  = 1,2,4,6
        //edges in tf2 = v1-v2, v4-v6
        //communities = 1,4,5,2
        IVertex v1 = dynamicGraphInstance.getVertex(0);
        dynamicGraphInstance.addVertex(v1, timeFrame2);
        this.setVertexFileId(v1, 1, timeFrame2);
        setCommunity(v1, 1+"", timeFrame2);
        setCommunitiesTF2.add(1+"");
        
        IVertex v2 = dynamicGraphInstance.getVertex(1);
        dynamicGraphInstance.addVertex(v2, timeFrame2);
        this.setVertexFileId(v2, 2, timeFrame2);
        setCommunity(v2, 4+"", timeFrame2);
        setCommunitiesTF2.add(4+"");
        
        IVertex v4 = dynamicGraphInstance.getVertex(3);
        dynamicGraphInstance.addVertex(v4, timeFrame2);
        this.setVertexFileId(v4, 4, timeFrame2);
        setCommunity(v4, 5+"", timeFrame2);
        setCommunitiesTF2.add(5+"");
        
        IVertex v6 = new Vertex();
        dynamicGraphInstance.addVertex(v6, timeFrame2);
        this.setVertexFileId(v6, 6, timeFrame2);
        listVertices.add(v6);
        setCommunity(v6, 2+"", timeFrame2);
        setCommunitiesTF2.add(2+"");
        
        IEdge edge1 = dynamicGraphInstance.getEdge(0);
        IEdge edge2 = new Edge(v4, v6, false, 0);//id = 4;
        
        dynamicGraphInstance.addEdge(edge1, timeFrame2);
        dynamicGraphInstance.addEdge(edge2, timeFrame2);
        listEdges.add(edge2);
        
    
    }
    
    private void setCommunity(IVertex vertex,String community, TimeFrame tf){
        vertex.getSystemAttributer().addAttributeValue(MeerkatSystem.COMMUNITY, community, new Date(), tf);
        setCommunities.add(community);
        
        if(!mapGommunityColorsExpected.containsKey(community)){
        
            if(!(index_MeerkatSystem_ColorCommunities < MeerkatSystem.getValidColorsCommunities().size())){
                index_MeerkatSystem_ColorCommunities = 0;
            }
            String color = MeerkatSystem.getValidColorsCommunities().get(index_MeerkatSystem_ColorCommunities++);
            mapGommunityColorsExpected.put(community, color);
        }
    }
    
    private void setVertexFileId(IVertex vertex,int fileId, TimeFrame tf){ 
        vertex.getUserAttributer().addAttribute(MeerkatSystem.FILE_ID, fileId+"", tf);
    }
    
    @After
    public void tearDown() {
        
        dynamicGraphInstance = null;
        statgraph1 = null;
        statgraph2 = null;
        setCommunities = null;
        setCommunitiesTF1 = null;
        setCommunitiesTF2 = null;
        listVertices = null;
        listVerticesTF1 = null;
        listEdges = null;
        listEdgesTF1 = null;
        mapGommunityColorsExpected = null;
        index_MeerkatSystem_ColorCommunities = 0;
        noOfVertices = 0;
        noOfEdges = 0;
        noOfVerticesTF1 = 0;
        noOfEdgesTF1 = 0;
        noOfVerticesTF2 = 0;
        noOfEdgesTF2 = 0;
    }

    /**
     * Test of calculateCommunityColors method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testCalculateCommunityColors() {
        System.out.println("calculateCommunityColors");
        Set<String> setCommunities = setCommunitiesTF1;
        TimeFrame tf = timeFrame1;
        dynamicGraphInstance.calculateCommunityColors(setCommunities, tf);
        
    }

    /**
     * Test of getCommunityColor method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetCommunityColor() {
        System.out.println("getCommunityColor");
        String strCommunity = "";
        TimeFrame tf = null;
        DynamicGraph instance = null;
        String expResult = "";
        String result = instance.getCommunityColor(strCommunity, tf);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMapCommunityColors method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetMapCommunityColors() {
        System.out.println("getMapCommunityColors");
        TimeFrame tf = null;
        DynamicGraph instance = null;
        Map<String, String> expResult = null;
        Map<String, String> result = instance.getMapCommunityColors(tf);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCommunityColor method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testSetCommunityColor() {
        System.out.println("setCommunityColor");
        String strCommunity = "";
        String strColor = "";
        TimeFrame tf = null;
        DynamicGraph instance = null;
        instance.setCommunityColor(strCommunity, strColor, tf);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resetGlobalCommunityColorMap method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testResetGlobalCommunityColorMap() {
        System.out.println("resetGlobalCommunityColorMap");
        DynamicGraph instance = null;
        instance.resetGlobalCommunityColorMap();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCommunityColorMap method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testSetCommunityColorMap() {
        System.out.println("setCommunityColorMap");
        TimeFrame tf = null;
        Map<String, String> mapCommunityColorMap = null;
        DynamicGraph instance = null;
        instance.setCommunityColorMap(tf, mapCommunityColorMap);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGlobalCommunityColorMap method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testSetGlobalCommunityColorMap() {
        System.out.println("setGlobalCommunityColorMap");
        Map<String, String> mapGloablCommunityColor = null;
        DynamicGraph instance = null;
        instance.setGlobalCommunityColorMap(mapGloablCommunityColor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMapGloablCommunityColor method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetMapGloablCommunityColor() {
        System.out.println("getMapGloablCommunityColor");
        DynamicGraph instance = null;
        Map<String, String> expResult = null;
        Map<String, String> result = instance.getMapGloablCommunityColor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxFileId method, of class DynamicGraph.
     */
    @Test
    public void testGetMaxFileId() {
        System.out.println("getMaxFileId");
        
        int expResult = 6;//noOfVertices
        int result = dynamicGraphInstance.getMaxFileId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testSetId() {
        System.out.println("setId");
        int pintId = 0;
        DynamicGraph instance = null;
        instance.setId(pintId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetId() {
        System.out.println("getId");
        DynamicGraph instance = null;
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGraphTitle method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetGraphTitle() {
        System.out.println("getGraphTitle");
        DynamicGraph instance = null;
        String expResult = "";
        String result = instance.getGraphTitle();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGraphTitle method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testSetGraphTitle() {
        System.out.println("setGraphTitle");
        String pstrGraphTitle = "";
        DynamicGraph instance = null;
        instance.setGraphTitle(pstrGraphTitle);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGraphFile method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetGraphFile() {
        System.out.println("getGraphFile");
        DynamicGraph instance = null;
        String expResult = "";
        String result = instance.getGraphFile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGraphFile method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testSetGraphFile() {
        System.out.println("setGraphFile");
        String pstrGraphFile = "";
        DynamicGraph instance = null;
        instance.setGraphFile(pstrGraphFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of renameGraph method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testRenameGraph() {
        System.out.println("renameGraph");
        String pstrgraphNewTitle = "";
        
        dynamicGraphInstance.renameGraph(pstrgraphNewTitle);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addGraph method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testAddGraph() {
        System.out.println("addGraph");
        
        IStaticGraph<IVertex, IEdge<IVertex>> newStaticGraph = new StaticGraph(0,0);
        TimeFrame newTF = new TimeFrame("new TF");
        
        dynamicGraphInstance.addGraph(newTF, newStaticGraph);
        IStaticGraph<IVertex, IEdge<IVertex>> result = dynamicGraphInstance.getGraph(newTF);
        
        assertNotNull(result);
        
        
    }

    /**
     * Test of getGraph method, of class DynamicGraph.
     */
    @Test
    public void testGetGraph() {
        System.out.println("getGraph");

        
        IStaticGraph result = dynamicGraphInstance.getGraph(timeFrame1);
        
        assertNotNull(result);
    }

    /**
     * Test of getAllTimeFrames method, of class DynamicGraph.
     */
    @Test
    public void testGetAllTimeFrames() {
        System.out.println("getAllTimeFrames");
        
        List<TimeFrame> expResult = new ArrayList();
        expResult.add(timeFrame1);
        expResult.add(timeFrame2);
        
        List<TimeFrame> result = dynamicGraphInstance.getAllTimeFrames();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getVertices method, of class DynamicGraph.
     */
    @Test
    public void testGetVertices() {
        System.out.println("getVertices");
        
        TimeFrame tf = timeFrame1;
        List expResult = listVerticesTF1;
        
        List result = dynamicGraphInstance.getVertices(tf);
        
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getVertex method, of class DynamicGraph.
     */
    @Test
    public void testGetVertex() {
        System.out.println("getVertex");
        
        Random rand = new Random();
        int  vertexId = rand.nextInt(this.noOfVertices);
        int expResult = vertexId;
        
        int result = dynamicGraphInstance.getVertex(vertexId).getId();
        
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getAnyVertex method, of class DynamicGraph.
     */
    @Test
    public void testGetAnyVertex() {
        System.out.println("getAnyVertex");
        TimeFrame tf = timeFrame1;
        
        int resultId = dynamicGraphInstance.getAnyVertex(tf).getId();
        Boolean result = resultId >= 0 && resultId < noOfVerticesTF1; 
        assertTrue(result);
        
    }

    /**
     * Test of getAllVertices method, of class DynamicGraph.
     */
    @Test
    public void testGetAllVertices() {
        System.out.println("getAllVertices");
        
        List expResult = listVertices;
        List result = dynamicGraphInstance.getAllVertices();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getAllVertexIds method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetAllVertexIds() {
        System.out.println("getAllVertexIds");
        DynamicGraph instance = null;
        List<Integer> expResult = null;
        List<Integer> result = instance.getAllVertexIds();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVertexCount method, of class DynamicGraph.
     */
    @Test
    public void testGetVertexCount_0args() {
        System.out.println("getVertexCount");
        
        int expResult = noOfVertices;
        int result = dynamicGraphInstance.getVertexCount();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getVertexCount method, of class DynamicGraph.
     */
    @Test
    public void testGetVertexCount_TimeFrame() {
        System.out.println("getVertexCount");
        
        TimeFrame tf = timeFrame1;
        
        int expResult = noOfVerticesTF1;
        
        int result = dynamicGraphInstance.getVertexCount(tf);
        assertEquals(expResult, result);
    }

    /**
     * Test of addVertex method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testAddVertex_List_TimeFrame() {
        System.out.println("addVertex");
        
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addVertex method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testAddVertex_Set_TimeFrame() {
        System.out.println("addVertex");
        DynamicGraph instance = null;
        //instance.addVertex(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addVertex method, of class DynamicGraph.
     */
    @Test
    public void testAddVertex_GenericType_TimeFrame() {
        System.out.println("addVertex new Vertex");
        IVertex vertex = new Vertex();
        TimeFrame tf = timeFrame1;
        
        dynamicGraphInstance.addVertex(vertex, tf);
        listVertices.add(vertex);
        listVerticesTF1.add(vertex);
        noOfVertices++;
        noOfVerticesTF1++;
        
        int vertexIdExpected = noOfVertices-1;
        
        int resultId = dynamicGraphInstance.getVertex(vertexIdExpected).getId();
        
        assertEquals(vertexIdExpected, resultId);
        
        

    }
    
    /**
     * Test of addVertex method, of class DynamicGraph.
     */
    @Test
    public void testAddVertex_GenericType_TimeFrame_VertexAlreadyExistsInGraph() {
        System.out.println("addVertex VertexAlreadyExistsInGraph");
        
        //this is not present in tf2, lets add it it in tf2
        int vertexId = 5;
        IVertex vertex = dynamicGraphInstance.getVertex(vertexId);
        
        TimeFrame tf = timeFrame2;
        dynamicGraphInstance.addVertex(vertex, tf);
        
        
        int vertexIdExpected = vertexId;
        
        int resultId = dynamicGraphInstance.getVertex(vertexId).getId();
        
        
        Boolean vertexAddedSuccess = false;
        List<IVertex> listOfVertices = dynamicGraphInstance.getVertices(tf);
        for(IVertex v : listOfVertices){
            if(v.getId() == vertexId){
                vertexAddedSuccess = true;
            }
        }
        
        assertEquals(vertexIdExpected, resultId);
        assertTrue(vertexAddedSuccess);
        
    }

    /**
     * Test of removeVertex method, of class DynamicGraph.
     */
    @Test
    public void testRemoveVertex_GenericType() {
        System.out.println("removeVertex");
        
        int vertexId = 0;
        IVertex vertex = dynamicGraphInstance.getVertex(vertexId);
        dynamicGraphInstance.removeVertex(vertex);
        
        //should return a null object
        assertNull(dynamicGraphInstance.getVertex(vertexId));
        
    }
    
    @Test(expected = NullPointerException.class)
    public void testRemoveVertex_VertexNotInGraph() {
        System.out.println("removeVertex");
        
        //this vertex does not exist in the dynamic graph
        int vertexId = 100;
        IVertex vertex = new Vertex();
        vertex.setId(vertexId);
        dynamicGraphInstance.removeVertex(vertex);
        
    }

    /**
     * Test of removeVertex method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testRemoveVertex_GenericType_List() {
        System.out.println("removeVertex");
        Object pVertex = null;
        List<TimeFrame> plstTimeFrame = null;
        DynamicGraph instance = null;
        //instance.removeVertex(pVertex, plstTimeFrame);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeVertices method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testRemoveVertices() {
        System.out.println("removeVertices");
        DynamicGraph instance = null;
        instance.removeVertices(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDegree method, of class DynamicGraph.
     */
    @Test
    public void testGetDegree() {
        System.out.println("getDegree");
        
        int vertexId = 0;
        IVertex vertex = dynamicGraphInstance.getVertex(vertexId);
        TimeFrame timeFrame = timeFrame1;
        
        int expResult = 2;
        int result = dynamicGraphInstance.getDegree(vertex, timeFrame);
        assertEquals(expResult, result);

    }

    /**
     * Test of getIndegree method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetIndegree() {
        System.out.println("getIndegree");
        
        int vertexId = 0;
        IVertex vertex = dynamicGraphInstance.getVertex(vertexId);
        TimeFrame timeFrame = timeFrame1;
        
        int expResult = 2;
        //int result = dynamicGrahInstance.getIndegree(vertex, timeFrame);
        //assertEquals(expResult, result);
        
    }

    /**
     * Test of getOutDegree method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetOutDegree() {
        System.out.println("getOutDegree");
        
        int vertexId = 0;
        IVertex vertex = dynamicGraphInstance.getVertex(vertexId);
        TimeFrame timeFrame = timeFrame1;
        
        int expResult = 2;
        //int result = dynamicGrahInstance.getOutdegree(vertex, timeFrame);
        //assertEquals(expResult, result);
    }

    /**
     * Test of getNeighbors method, of class DynamicGraph.
     */
    @Test
    public void testGetNeighbors_2args_1() {
        System.out.println("getNeighbors");
        
        int vertexId = 0;
        IVertex vertex = dynamicGraphInstance.getVertex(vertexId);
        TimeFrame timeFrame = timeFrame1;
        
        List<IVertex> expResult = new ArrayList<>();
        IVertex v2 = dynamicGraphInstance.getVertex(1);
        IVertex v5 = dynamicGraphInstance.getVertex(4);
        expResult.add(v2);
        expResult.add(v5);
               
                
        List result = dynamicGraphInstance.getNeighbors(vertex, timeFrame);
        assertEquals(expResult, result);

    }

    /**
     * Test of getIncomingNeighbors method, of class DynamicGraph.
     */
    @Test
    public void testGetIncomingNeighbors() {
        System.out.println("getIncomingNeighbors");
        
        int vertexId = 0;
        IVertex vertex = dynamicGraphInstance.getVertex(vertexId);
        TimeFrame timeFrame = timeFrame1;
        
        List<IVertex> expResult = new ArrayList<>();
        IVertex v2 = dynamicGraphInstance.getVertex(1);
        IVertex v5 = dynamicGraphInstance.getVertex(4);
        expResult.add(v2);
        expResult.add(v5);
               
                
        List result = dynamicGraphInstance.getIncomingNeighbors(vertex, timeFrame);
        assertEquals(expResult, result);
    }

    /**
     * Test of getOutgoingNeighbors method, of class DynamicGraph.
     */
    @Test
    public void testGetOutgoingNeighbors() {
        System.out.println("getOutgoingNeighbors");
        
        int vertexId = 0;
        IVertex vertex = dynamicGraphInstance.getVertex(vertexId);
        TimeFrame timeFrame = timeFrame1;
        
        List<IVertex> expResult = new ArrayList<>();
        IVertex v2 = dynamicGraphInstance.getVertex(1);
        IVertex v5 = dynamicGraphInstance.getVertex(4);
        expResult.add(v2);
        expResult.add(v5);
               
                
        List result = dynamicGraphInstance.getNeighbors(vertex, timeFrame);
        assertEquals(expResult, result);
    }

    /**
     * Test of getOutgoingEdges method, of class DynamicGraph.
     */
    @Test
    public void testGetOutgoingEdges() {
        System.out.println("getOutgoingEdges");
        
        int vertexId = 0;
        IVertex vertex = dynamicGraphInstance.getVertex(vertexId);
        TimeFrame timeFrame = timeFrame1;
        
        List<IEdge> expResult = new ArrayList<>();
        IEdge e1 = dynamicGraphInstance.getEdge(0);
        IEdge e3 = dynamicGraphInstance.getEdge(2);
        expResult.add(e1);
        expResult.add(e3);
               

        List result = dynamicGraphInstance.getOutgoingEdges(vertex, timeFrame);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getIncomingEdges method, of class DynamicGraph.
     */
    @Test
    public void testGetIncomingEdges() {
        System.out.println("getIncomingEdges");
        int vertexId = 0;
        IVertex vertex = dynamicGraphInstance.getVertex(vertexId);
        TimeFrame timeFrame = timeFrame1;
        
        List<IEdge> expResult = new ArrayList<>();
        IEdge e1 = dynamicGraphInstance.getEdge(0);
        IEdge e3 = dynamicGraphInstance.getEdge(2);
        expResult.add(e1);
        expResult.add(e3);
               

        List result = dynamicGraphInstance.getIncomingEdges(vertex, timeFrame);
        assertEquals(expResult, result);
    }

    /**
     * Test of getEdges method, of class DynamicGraph.
     */
    @Test
    public void testGetEdges_GenericType_TimeFrame() {
        System.out.println("getEdges");
        
        
        TimeFrame timeFrame = timeFrame1;
        
        List<IEdge<IVertex>> expResult = listEdgesTF1;
        
        List result = dynamicGraphInstance.getEdges(timeFrame);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getVertexAllAttributeNames method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetVertexAllAttributeNames() {
        System.out.println("getVertexAllAttributeNames");
        DynamicGraph instance = null;
        Set<String> expResult = null;
        Set<String> result = instance.getVertexAllAttributeNames();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVertexNumericalAttributeNames method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetVertexNumericalAttributeNames() {
        System.out.println("getVertexNumericalAttributeNames");
        DynamicGraph instance = null;
        Set<String> expResult = null;
        Set<String> result = instance.getVertexNumericalAttributeNames();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVertexAttributeNamesWithType method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetVertexAttributeNamesWithType() {
        System.out.println("getVertexAttributeNamesWithType");
        DynamicGraph instance = null;
        Map<String, Boolean> expResult = null;
        Map<String, Boolean> result = instance.getVertexAttributeNamesWithType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEdge method, of class DynamicGraph.
     */
    @Test
    public void testGetEdge() {
        System.out.println("getEdge");
        
        Random rand = new Random();
        int  edgeId = rand.nextInt(this.noOfEdges);
        int expResult = edgeId;
        
        int result = dynamicGraphInstance.getEdge(edgeId).getId();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getEdges method, of class DynamicGraph.
     */
    @Test
    public void testGetEdges_TimeFrame() {
        System.out.println("getEdges");
        
        TimeFrame tf1 = timeFrame1;
        List expResult = listEdgesTF1;
        
        List result = dynamicGraphInstance.getEdges(tf1);
        
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getAllEdges method, of class DynamicGraph.
     */
    @Test
    public void testGetAllEdges() {
        System.out.println("getAllEdges");
        
        List expResult = listEdges;
        List result = dynamicGraphInstance.getAllEdges();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllEdgeIds method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetAllEdgeIds() {
        System.out.println("getAllEdgeIds");
//        DynamicGraph instance = null;
//        List<Integer> expResult = null;
//        List<Integer> result = instance.getAllEdgeIds();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of addEdge method, of class DynamicGraph.
     */
    @Test
    public void testAddEdge() {
        System.out.println("addEdge");
        
        TimeFrame tf1 = timeFrame1;
        
        IVertex v4 = dynamicGraphInstance.getVertex(3);
        IVertex v5 = dynamicGraphInstance.getVertex(4);
        IEdge<IVertex> edge = new Edge(v4, v5, false, 0);
        
        
        //edge id will be = noOfEdges
        dynamicGraphInstance.addEdge(edge, tf1);
        listEdges.add(edge);
        listEdgesTF1.add(edge);
        noOfEdges++;
        noOfEdgesTF1++;
        
        int edgeIdExpected = noOfEdges-1;
        
        int resultId = dynamicGraphInstance.getEdge(edgeIdExpected).getId();
        
        assertEquals(edgeIdExpected, resultId);
    }

    /**
     * Test of removeEdge method, of class DynamicGraph.
     */
    @Test
    public void testRemoveEdge_GenericType() {
        System.out.println("removeEdge");
        
        int edgeId = 0;
        IEdge<IVertex> edge = dynamicGraphInstance.getEdge(edgeId);
        
        dynamicGraphInstance.removeEdge(edge);
        //should throw a null pointer
        assertNull(dynamicGraphInstance.getEdge(edgeId));
    }

    /**
     * Test of removeEdge method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testRemoveEdge_List() {
        System.out.println("removeEdge");
        DynamicGraph instance = null;
        //instance.removeEdge(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeEdge method, of class DynamicGraph.
     */
    @Test
    public void testRemoveEdge_GenericType_TimeFrame() {
        System.out.println("removeEdge");
        
        //this edge is in tf1 and tf2
        //remove it from tf1
        TimeFrame tf1 = timeFrame1;
                
        int edgeId = 0;
        IEdge<IVertex> edge = dynamicGraphInstance.getEdge(edgeId);
        dynamicGraphInstance.removeEdge(edge, tf1);
        noOfEdges--;
        noOfEdgesTF1--;
        //should throw a null pointer
        int expected = noOfEdgesTF1;
        int noOfEdgesTF1REsult = dynamicGraphInstance.getEdges(tf1).size();
        assertEquals(expected, noOfEdgesTF1REsult);
    }

    /**
     * Test of clearEdges method, of class DynamicGraph.
     */
    @Test
    public void testClearEdges_0args() {
        System.out.println("clearEdges");
        
        TimeFrame tf1 = timeFrame1;
        
        int expected = 0;
        
        dynamicGraphInstance.clearEdges();
        int resultNoOfEdgesTF1 = dynamicGraphInstance.getEdges(tf1).size();
        int resultNoOfEdges = dynamicGraphInstance.getEdgeCount();
        
        assertEquals(expected, resultNoOfEdgesTF1);
        assertEquals(expected, resultNoOfEdges);
               
    }

    /**
     * Test of clearEdges method, of class DynamicGraph.
     */
    @Test
    public void testClearEdges_TimeFrame() {
        System.out.println("clearEdges");
        
        TimeFrame tf1 = timeFrame1;
        
        int expected = 0;
        
        dynamicGraphInstance.clearEdges(tf1);
        int resultNoOfEdgesTF1 = dynamicGraphInstance.getEdges(tf1).size();
        
        assertEquals(expected, resultNoOfEdgesTF1);
    }

    /**
     * Test of getNeighbors method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetNeighbors_2args_2() {
        System.out.println("getNeighbors");
        Object e = null;
        TimeFrame pTimeFrame = null;
        DynamicGraph instance = null;
        List expResult = null;
        //List result = instance.getNeighbors(e, pTimeFrame);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxEdgeId method, of class DynamicGraph.
     */
    @Test
    public void testGetMaxEdgeId() {
        System.out.println("getMaxEdgeId");
        
        int expResult = noOfEdges-1;
        int result = dynamicGraphInstance.getMaxEdgeId();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getEdgeAttributeNames method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetEdgeAttributeNames() {
        System.out.println("getEdgeAttributeNames");
        DynamicGraph instance = null;
        Set<String> expResult = null;
        Set<String> result = instance.getEdgeAttributeNames();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEdgeNumericalAttributeNames method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetEdgeNumericalAttributeNames() {
        System.out.println("getEdgeNumericalAttributeNames");
        DynamicGraph instance = null;
        Set<String> expResult = null;
        Set<String> result = instance.getEdgeNumericalAttributeNames();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEdgeAttributeNamesWithType method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetEdgeAttributeNamesWithType() {
        System.out.println("getEdgeAttributeNamesWithType");
        DynamicGraph instance = null;
        Map<String, Boolean> expResult = null;
        Map<String, Boolean> result = instance.getEdgeAttributeNamesWithType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findEdge method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testFindEdge_GenericType_GenericType() {
        System.out.println("findEdge");
        Object source = null;
        Object destination = null;
        DynamicGraph instance = null;
        Set expResult = null;
        //Set result = instance.findEdge(source, destination);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findEdge method, of class DynamicGraph.
     */
    @Test
    public void testFindEdge_3args() {
        System.out.println("findEdge");
        
        TimeFrame tf1 = timeFrame1;
        IVertex v1 = dynamicGraphInstance.getVertex(0);
        IVertex v2 = dynamicGraphInstance.getVertex(1);
        
        //v1-v2
        IEdge<IVertex> expectedEdge = dynamicGraphInstance.getEdge(0);
        IEdge<IVertex> result = dynamicGraphInstance.findEdge(v1, v2, tf1);
        assertEquals(expectedEdge.getId(), result.getId());
    }
    
    @Test
    public void testFindEdge_edge_not_exist() {
        System.out.println("findEdge");
        
        TimeFrame tf1 = timeFrame1;
        IVertex v1 = dynamicGraphInstance.getVertex(0);
        
        IVertex v3 = dynamicGraphInstance.getVertex(2);
        
        //v1-v3 - no such edge - return null object
        assertNull(dynamicGraphInstance.findEdge(v1, v3, tf1));
        
    }

    /**
     * Test of getEdgeCount method, of class DynamicGraph.
     */
    @Test
    public void testGetEdgeCount() {
        System.out.println("getEdgeCount");
        
        int expResult = noOfEdges;
        int result = dynamicGraphInstance.getEdgeCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLastChangeTime method, of class DynamicGraph.
     */
    @Test
    public void testGetLastChangeTime() {
        System.out.println("getLastChangeTime");
        
        
        TimeFrame tf1 = timeFrame1;
        IVertex v = new Vertex();
        Date expResult = new Date();
        dynamicGraphInstance.addVertex(v, tf1);
        
        
        Date result = dynamicGraphInstance.getLastChangeTime();
        assertTrue(expResult.compareTo(result)==0 || expResult.compareTo(result)==-1);
        
    }

    /**
     * Test of getAllTimeFramesInReverseOrder method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetAllTimeFramesInReverseOrder() {
        System.out.println("getAllTimeFramesInReverseOrder");
        DynamicGraph instance = null;
        List<TimeFrame> expResult = null;
        List<TimeFrame> result = instance.getAllTimeFramesInReverseOrder();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGreaterTimeFrames method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetGreaterTimeFrames() {
        System.out.println("getGreaterTimeFrames");
        TimeFrame snapshotIdx = null;
        DynamicGraph instance = null;
        List<TimeFrame> expResult = null;
        List<TimeFrame> result = instance.getGreaterTimeFrames(snapshotIdx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSmallerTimeFrames method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testGetSmallerTimeFrames() {
        System.out.println("getSmallerTimeFrames");
        TimeFrame snapshotIdx = null;
        DynamicGraph instance = null;
        Iterable<TimeFrame> expResult = null;
        Iterable<TimeFrame> result = instance.getSmallerTimeFrames(snapshotIdx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isFirstTimeFrame method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testIsFirstTimeFrame() {
        System.out.println("isFirstTimeFrame");
        TimeFrame snapshotIndex = null;
        DynamicGraph instance = null;
        boolean expResult = false;
        boolean result = instance.isFirstTimeFrame(snapshotIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isLastTimeFrame method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testIsLastTimeFrame() {
        System.out.println("isLastTimeFrame");
        TimeFrame snapshotIdx = null;
        DynamicGraph instance = null;
        boolean expResult = false;
        boolean result = instance.isLastTimeFrame(snapshotIdx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateVertexLocations method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testUpdateVertexLocations() {
        System.out.println("updateVertexLocations");
        Map<Integer, Map<Integer, Double[]>> pmapGraphTimeFramesVerticesLocation = null;
        DynamicGraph instance = null;
        instance.updateVertexLocations(pmapGraphTimeFramesVerticesLocation);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateVertexColors method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testUpdateVertexColors_TimeFrame_Map() {
        System.out.println("updateVertexColors");
        TimeFrame ptf = null;
        Map<Integer, String> pmapVertexColors = null;
        DynamicGraph instance = null;
        boolean expResult = false;
        boolean result = instance.updateVertexColors(ptf, pmapVertexColors);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateVertexColors method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testUpdateVertexColors_3args() {
        System.out.println("updateVertexColors");
        TimeFrame ptf = null;
        String pstrColor = "";
        List<Integer> plstVertexIDs = null;
        DynamicGraph instance = null;
        boolean expResult = false;
        boolean result = instance.updateVertexColors(ptf, pstrColor, plstVertexIDs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateEdgeColors method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testUpdateEdgeColors_TimeFrame_Map() {
        System.out.println("updateEdgeColors");
        TimeFrame ptf = null;
        Map<Integer, String> pmapEdgeColors = null;
        DynamicGraph instance = null;
        boolean expResult = false;
        boolean result = instance.updateEdgeColors(ptf, pmapEdgeColors);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateEdgeColors method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testUpdateEdgeColors_3args() {
        System.out.println("updateEdgeColors");
        TimeFrame ptf = null;
        String pstrColor = "";
        List<Integer> plstEdgeIDs = null;
        DynamicGraph instance = null;
        boolean expResult = false;
        boolean result = instance.updateEdgeColors(ptf, pstrColor, plstEdgeIDs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateVertexIconURLs method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testUpdateVertexIconURLs_TimeFrame_Map() {
        System.out.println("updateVertexIconURLs");
        TimeFrame ptf = null;
        Map<Integer, String> pmapVertexIconURLs = null;
        DynamicGraph instance = null;
        boolean expResult = false;
        boolean result = instance.updateVertexIconURLs(ptf, pmapVertexIconURLs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateVertexIconURLs method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testUpdateVertexIconURLs_3args() {
        System.out.println("updateVertexIconURLs");
        TimeFrame ptf = null;
        String pstrIconURL = "";
        List<Integer> plstVertexIDs = null;
        DynamicGraph instance = null;
        boolean expResult = false;
        boolean result = instance.updateVertexIconURLs(ptf, pstrIconURL, plstVertexIDs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateVertexLocationsInTimeFrame method, of class DynamicGraph.
     */
    @Ignore
    @Test
    public void testUpdateVertexLocationsInTimeFrame() {
        System.out.println("updateVertexLocationsInTimeFrame");
        
        // TODO write it later -
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEdgeIfExists method, of class DynamicGraph.
     */
    @Test
    public void testGetEdgeIfExists() {
        System.out.println("getEdgeIfExists");
        
        int sourceVertexID = 0;
        int destinationVertexID = 1;
        
        IEdge<IVertex> expResult = dynamicGraphInstance.getEdge(0);
        IEdge<IVertex> result = dynamicGraphInstance.getEdgeIfExists(sourceVertexID, destinationVertexID);
        assertEquals(expResult.getId(), result.getId());
        
    }
    
    @Test
    public void testGetEdgeIfExists_nullResult() {
        System.out.println("getEdgeIfExists");
        
        int sourceVertexID = 1;
        int destinationVertexID = 2;
        
        //v2-v3 - does not exist
        
        
        assertNull(dynamicGraphInstance.getEdgeIfExists(sourceVertexID, destinationVertexID));
   
    }

    /**
     * Test of calculateCommunityColorsOnLoading method, of class DynamicGraph.
     */
    @Test
    public void testCalculateCommunityColorsOnLoading() {
        System.out.println("calculateCommunityColorsOnLoading");
        
        Map<String, String> expected = mapGommunityColorsExpected;
        
        dynamicGraphInstance.calculateCommunityColorsOnLoading();
        Map<String, String> mapGommunityColorsResult = dynamicGraphInstance.getMapGloablCommunityColor();
        
        assertEquals(expected, mapGommunityColorsResult);
    }

    /**
     * Test of calculateCommunityOnLoading method, of class DynamicGraph.
     */
    @Test
    public void testCalculateCommunityOnLoading() {
        System.out.println("calculateCommunityOnLoading");
        
        TimeFrame tf1 = timeFrame1;
        
        Set<String> expResult = setCommunitiesTF1;
        Set<String> result = dynamicGraphInstance.calculateCommunityOnLoading(tf1);
        assertEquals(expResult, result);
        
    }
    
}
