/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.graph.reader;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IStaticGraph;
import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.graph.impl.DynamicGraph;
import datastructure.core.graph.impl.Edge;
import datastructure.core.graph.impl.StaticGraph;
import datastructure.core.graph.impl.Vertex;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author sankalp
 */
public class MeerkatReaderTest {
    
    /* All different kinds of test cases:
    
    1 - invalid_format_missing_asterisk
    2 - invalid_format_missing_paranthesis
    3 - invalid_SYS:COLOR
    4 - invalid_TYPEICON_URL
    5 - Non-numeric_SYS:XY_values
    6 - validate_graph_elements
    7 - zero_edges_graph
    8 - zero_vertex_graph
    
    */
    
    static final String pstrTestFolderPath = "junit_test_files/reader_test_files/meerkat/";
    static final String pstrValidate_graph_instance = "validate_graph_elements.meerkat";
    static final String pstrinvalid_format_missing_asterisk = "invalid_format_missing_asterisk.meerkat";
    static final String pstrinvalid_SYS_COLOR = "invalid_SYS:COLOR.meerkat";
    static final String pstrinvalid_TYPEICON_URL = "invalid_TYPEICON_URL.meerkat";
    static final String pstrNon_numeric_SYS_XY_values = "Non-numeric_SYS:XY_values.meerkat";
    static final String pstrzero_edges_graph = "zero_edges_graph.meerkat";
    static final String pstrzero_vertex_graph = "zero_vertex_graph.meerkat";
    static final String pstrinvalid_format_spaces_between_vertices = "invalid_format_spaces_between_vertices.meerkat";
    static final String pstrinvalid_format_spaces_between_edges = "invalid_format_spaces_between_edges.meerkat";
    static final String pstrinvalid_format_missing_opening_braces_vertices = "invalid_format_missing_opening_braces_vertices.meerkat";
    static final String pstrinvalid_format_missing_closing_braces_vertices = "invalid_format_missing_closing_braces_vertices.meerkat";
    static final String pstrinvalid_format_missing_semicolon = "invalid_format_missing_semicolon.meerkat";
    
    private IDynamicGraph<IVertex,IEdge<IVertex>> dynamicGraphInstance = null;
    private IStaticGraph<IVertex,IEdge<IVertex>> statgraph1;
    private IStaticGraph<IVertex,IEdge<IVertex>> statgraph2;
    private List<IVertex> listVertices;
    private List<IVertex> listVerticesTF1;
    private Set<String> setCommunitiesTF1;
    private List<IEdge<IVertex>> listEdges;
    private List<IEdge<IVertex>> listEdgesTF1;
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
    
    public MeerkatReaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
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
        IVertex v1 = dynamicGraphInstance.getVertex(0);
        IVertex v2 = dynamicGraphInstance.getVertex(1);
        IVertex v4 = dynamicGraphInstance.getVertex(3);
        IVertex v5 = dynamicGraphInstance.getVertex(4);
        setCommunity(v1, 1+"", timeFrame1);
        setCommunity(v2, 2+"", timeFrame1);
        setCommunity(v4, 3+"", timeFrame1);
        setCommunity(v5, 3+"", timeFrame1);

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
        
        
        statgraph2  = new StaticGraph<>(0 , 0);
        dynamicGraphInstance.addGraph(timeFrame2, statgraph2);
        
        //vertice in tf 2  = 1,2,4,6
        //edges in tf2 = v1-v2, v4-v6
        IVertex v1 = dynamicGraphInstance.getVertex(0);
        dynamicGraphInstance.addVertex(v1, timeFrame2);
        this.setVertexFileId(v1, 1, timeFrame2);
        
        IVertex v2 = dynamicGraphInstance.getVertex(1);
        dynamicGraphInstance.addVertex(v2, timeFrame2);
        this.setVertexFileId(v2, 2, timeFrame2);
        
        IVertex v4 = dynamicGraphInstance.getVertex(3);
        dynamicGraphInstance.addVertex(v4, timeFrame2);
        this.setVertexFileId(v4, 4, timeFrame2);
        
        IVertex v6 = new Vertex();
        dynamicGraphInstance.addVertex(v6, timeFrame2);
        this.setVertexFileId(v6, 6, timeFrame2);
        listVertices.add(v6);

        IEdge edge1 = dynamicGraphInstance.getEdge(0);
        IEdge edge2 = new Edge(v4, v6, false, 0);//id = 4;
        
        dynamicGraphInstance.addEdge(edge1, timeFrame2);
        dynamicGraphInstance.addEdge(edge2, timeFrame2);
        listEdges.add(edge2);
        
    
    }
    
    private void setCommunity(IVertex vertex,String community, TimeFrame tf){
        vertex.getSystemAttributer().addAttributeValue(MeerkatSystem.COMMUNITY, community, new Date(), tf);
        setCommunitiesTF1.add(community);
    }
    
    private void setVertexFileId(IVertex vertex,int fileId, TimeFrame tf){ 
        vertex.getUserAttributer().addAttribute(MeerkatSystem.FILE_ID, fileId+"", tf);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {

        dynamicGraphInstance = new DynamicGraph<>(0,0);
        listVertices = new ArrayList<>();
        listEdges = new ArrayList<>();

        //TF1
        //vertices = 1,2,3,4,5
        //v1-v2, v2-v4, v1-v5
        prepareTF1();

        //TF2
        //vertice in tf 2  = 1,2,4,6
        //edges in tf2 = v1-v2, v4-v6
        prepareTF2();

        noOfVertices = 6;
        noOfEdges = 4;
    }
    
    @After
    public void tearDown() {
        
        dynamicGraphInstance = null;
        statgraph1 = null;
        statgraph2 = null;
        setCommunitiesTF1 = null;
        listVertices = null;
        listVerticesTF1 = null;
        listEdges = null;
        listEdgesTF1 = null;
    
        noOfVertices = 0;
        noOfEdges = 0;
        noOfVerticesTF1 = 0;
        noOfEdgesTF1 = 0;
        noOfVerticesTF2 = 0;
        noOfEdgesTF2 = 0;
    }
    
    /**
     * Method to validate loading of the graph file
     */
    @Test
    public void ValidateGraphInstance() {
        
        MeerkatReader instance = new MeerkatReader(pstrTestFolderPath+pstrValidate_graph_instance);
        //testing for graph elements
        IDynamicGraph<IVertex,IEdge<IVertex>> actualDynaGraph = instance.loadFile();
        
        //counting total vertices
        Assert.assertEquals(noOfVertices, actualDynaGraph.getVertexCount());
        //counting total edges
        Assert.assertEquals(noOfEdges, actualDynaGraph.getEdgeCount());        
        //counting total time frames
        Assert.assertEquals(2, actualDynaGraph.getAllTimeFrames().size());
        
        List<TimeFrame> alltimeFrames = actualDynaGraph.getAllTimeFrames();
        
        //checking number of edges and vertices for each time frame
        for(TimeFrame tf : alltimeFrames){
            if(tf.getTimeFrameName().equalsIgnoreCase(timeFrame1.getTimeFrameName())){
                Assert.assertEquals(3, actualDynaGraph.getEdges(tf).size());
                Assert.assertEquals(5, actualDynaGraph.getVertexCount(tf));
            }else{
                Assert.assertEquals(2, actualDynaGraph.getEdges(tf).size());
                Assert.assertEquals(4, actualDynaGraph.getVertexCount(tf));
            }
        }
    }
    
    /**
     * Method to validate loading of the graph file with invalid format (missing asterisk)
     */
    @Test
    public void ValidateInvalidFormatMissingAsterisk() {
        MeerkatReader instance = new MeerkatReader(pstrTestFolderPath+pstrinvalid_format_missing_asterisk);
        
        IDynamicGraph<IVertex,IEdge<IVertex>> actualDynaGraph = instance.loadFile();
        
        //graph will not be loaded, null value returned
        Assert.assertNull(actualDynaGraph);
    }
    
    /**
     * Method to validate loading of the graph file with invalid color
     */
    @Test
    public void ValidateInvalidSysColor() {
        MeerkatReader instance = new MeerkatReader(pstrTestFolderPath+pstrinvalid_SYS_COLOR);
        
        IDynamicGraph<IVertex,IEdge<IVertex>> actualDynaGraph = instance.loadFile();
        
        //graph will be loaded, with default color
        Assert.assertNotNull(actualDynaGraph);
        
        List<TimeFrame> timeFrames = actualDynaGraph.getAllTimeFrames();
        
        // vertexID 0 and 4 have invalid SYS:COLOR value, check if it is set to dafault vertex color
        Assert.assertEquals(actualDynaGraph.getVertex(0).getColor(timeFrames.get(0)), MeerkatSystem.getDefaultVertexColor());
        Assert.assertEquals(actualDynaGraph.getVertex(4).getColor(timeFrames.get(0)), MeerkatSystem.getDefaultVertexColor());
        
        //negative test case --> confirming that a vertex with a valid color should not be assigned the default color.
        Assert.assertNotEquals(actualDynaGraph.getVertex(7).getColor(timeFrames.get(0)), MeerkatSystem.getDefaultVertexColor());
    }
    
    /**
     * Method to validate loading of the graph file with invalid Type ICON URL
     */
    @Test
    public void ValidateInvalidTypeIconURL() {
        MeerkatReader instance = new MeerkatReader(pstrTestFolderPath+pstrinvalid_TYPEICON_URL);
        
        IDynamicGraph<IVertex,IEdge<IVertex>> actualDynaGraph = instance.loadFile();
        
        //check if the graph is Not Null
        Assert.assertNotNull(actualDynaGraph);
        
        //check if all vertices all loaded (even the ones with invalid TYPE ICON URL)
        Assert.assertEquals(12, actualDynaGraph.getVertexCount());   
    }
    
    /**
     * Method to validate loading of the graph file with Non Numeric SYS values
     */
    @Test
    public void ValidateNonNumericSysValues() {
        MeerkatReader instance = new MeerkatReader(pstrTestFolderPath+pstrNon_numeric_SYS_XY_values);
        
        IDynamicGraph<IVertex,IEdge<IVertex>> actualDynaGraph = instance.loadFile();
        
        //check if the graph is Not Null
        Assert.assertNotNull(actualDynaGraph);
        
        //check if all vertices all loaded (even the ones with invalid TYPE ICON URL)
        Assert.assertEquals(12, actualDynaGraph.getVertexCount()); 
        
        List<TimeFrame> timeFrames = actualDynaGraph.getAllTimeFrames();
        
        //check if the vertex with non-numeric SYS:X or SYS:Y value is plotted in the center.
        Assert.assertEquals(String.valueOf(0.5), actualDynaGraph.getVertex(0).getSystemAttributer().getAttributeValue("SYS:X", timeFrames.get(0)));
        Assert.assertEquals(String.valueOf(0.5), actualDynaGraph.getVertex(11).getSystemAttributer().getAttributeValue("SYS:Y", timeFrames.get(0)));
    }
    
    /**
     * Method to validate loading of the graph file with no edges
     */
    @Test
    public void ValidateGraphWithNoEdge() {
        MeerkatReader instance = new MeerkatReader(pstrTestFolderPath+pstrzero_edges_graph);
        
        IDynamicGraph<IVertex,IEdge<IVertex>> actualDynaGraph = instance.loadFile();
        
        //check if the graph is Not Null
        Assert.assertNotNull(actualDynaGraph);
        
        //check for the number of vertices
        Assert.assertEquals(12, actualDynaGraph.getVertexCount());
        
        //check for the number of edges
        Assert.assertEquals(0, actualDynaGraph.getEdgeCount());
        
    }
    
    /**
     * Method to validate loading of the graph file with no vertices
     */
    @Test
    public void ValidateGraphWithNoVertices() {
        MeerkatReader instance = new MeerkatReader(pstrTestFolderPath+pstrzero_vertex_graph);
        
        IDynamicGraph<IVertex,IEdge<IVertex>> actualDynaGraph = instance.loadFile();
        
        //check if the graph is Null as we cannot have a graph with no vertices
        Assert.assertNull(actualDynaGraph);

    }
    
    /**
     * Method to validate loading of the graph file with invalid format (spaces between vertices)
     */
    @Test
    public void ValidateInvalidFormatSpacesBetweenVertices() {
        MeerkatReader instance = new MeerkatReader(pstrTestFolderPath+pstrinvalid_format_spaces_between_vertices);
        
        IDynamicGraph<IVertex,IEdge<IVertex>> actualDynaGraph = instance.loadFile();
        
        //check if the graph is not Null
        Assert.assertNotNull(actualDynaGraph);
        
        //check for the number of vertices
        Assert.assertEquals(12, actualDynaGraph.getVertexCount());
        
        //check for the number of edges
        Assert.assertEquals(9, actualDynaGraph.getEdgeCount());

    }
    
    /**
     * Method to validate loading of the graph file with invalid format (spaces between edges)
     */
    @Test
    public void ValidateInvalidFormatSpacesBetweenEdges() {
        MeerkatReader instance = new MeerkatReader(pstrTestFolderPath+pstrinvalid_format_spaces_between_edges);
        
        IDynamicGraph<IVertex,IEdge<IVertex>> actualDynaGraph = instance.loadFile();
        
        //check if the graph is not Null
        Assert.assertNotNull(actualDynaGraph);
        
        //check for the number of vertices
        Assert.assertEquals(12, actualDynaGraph.getVertexCount());
        
        //check for the number of edges
        Assert.assertEquals(9, actualDynaGraph.getEdgeCount());

    }
    
    /**
     * Method to validate loading of the graph file with invalid format (missing opening braces for vertices)
     */
    @Test
    public void ValidateInvalidFormatMissingOpeningBracesVertices() {
        MeerkatReader instance = new MeerkatReader(pstrTestFolderPath+pstrinvalid_format_missing_opening_braces_vertices);
        
        IDynamicGraph<IVertex,IEdge<IVertex>> actualDynaGraph = instance.loadFile();
        
        //check if the graph is not Null
        Assert.assertNotNull(actualDynaGraph);
        
        //check for the number of vertices
        Assert.assertEquals(12, actualDynaGraph.getVertexCount());
        
        //check for the number of edges
        Assert.assertEquals(9, actualDynaGraph.getEdgeCount());
        
        //check if the vertex if no opening braces has still been created
        Assert.assertEquals(0, actualDynaGraph.getVertex(0).getId());

    }
    
    /**
     * Method to validate loading of the graph file with invalid format (missing closing braces for vertices)
     */
    @Test
    public void ValidateInvalidFormatMissingClosingBracesVertices() {
        MeerkatReader instance = new MeerkatReader(pstrTestFolderPath+pstrinvalid_format_missing_closing_braces_vertices);
        
        IDynamicGraph<IVertex,IEdge<IVertex>> actualDynaGraph = instance.loadFile();
        
        //check if the graph is not Null
        Assert.assertNotNull(actualDynaGraph);
        
        //check for the number of vertices
        Assert.assertEquals(12, actualDynaGraph.getVertexCount());
        
        //check for the number of edges
        Assert.assertEquals(9, actualDynaGraph.getEdgeCount());
        
        //check if the vertex if no closing braces has still been created
        Assert.assertEquals(0, actualDynaGraph.getVertex(0).getId());

    }
    
     /**
     * Method to validate loading of the graph file with invalid format (missing closing braces for vertices)
     */
    @Test
    public void ValidateInvalidFormatMissingSemiColon() {
        MeerkatReader instance = new MeerkatReader(pstrTestFolderPath+pstrinvalid_format_missing_semicolon);
        
        IDynamicGraph<IVertex,IEdge<IVertex>> actualDynaGraph = instance.loadFile();
        
        //check if the graph is not Null
        Assert.assertNotNull(actualDynaGraph);
        
        //check for the number of vertices
        Assert.assertEquals(12, actualDynaGraph.getVertexCount());
        
        //check for the number of edges
        Assert.assertEquals(9, actualDynaGraph.getEdgeCount());
    }
    
}
