/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate;

import ca.aicml.meerkat.api.GraphAPI;
import config.SceneConfig;
import graphelements.UIEdge;
import graphelements.ShapeCentre;
import graphelements.UIVertex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import ui.dialogwindow.InfoDialog;
import ui.edgeshapes.ArrowHead;
import ui.elements.ColorToolBox;
import ui.elements.SizeToolBox;

/**
 *  Class Name      : GraphCanvas
 *  Created Date    : 2015-11-30
 *  Description     : The canvas on which the graph is drawn. It extends the groups that contains all the elements in the Vertices
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-02-25      Talat           Adding the parameters for TimeFrameIndex across required methods
 *  2016-01-28      Talat           Removing the old code. Check previously dated version in case old code is required
 *  2016-01-19      Talat           Changing the HashMap to an array of objects for Vertices, Edges and Positions. HashMaps are too slow for millions of nodes
 *  2017-06-14      sankalp         We changed the UIVertex object holder from array to map to accommodate future vertex additions. Same for UIEdges.
 * 
*/
public class GraphCanvas extends Group{

    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    //private UIVertex [] arrVertices ;  
    //using the mapVertices to store all the UIVertex objects.
    private Map<Integer, UIVertex> mapVertices;
    //private UIEdge [] arrEdges ;
    //using the mapEdges to store all the UIEdge objects.
    private Map<Integer, UIEdge> mapEdges;
    private Set<UIVertex> setSelectedVertices ;
    private Set<UIEdge> setSelectedEdges ;
    //map of UIVertex -> UIEdges , vertex and all edges connected to it.
    private Map<UIVertex, Set<UIEdge>> mapVertexEdges;
    private Map<UIVertex, Set<ArrowHead>> mapVertexArrowHead;
    //to keep track if the canvas was dragged
    private boolean canvasDragged ;
    
    private boolean blnIsVertexClicked = false ;
    private boolean blnIsEdgeClicked = false ;
    
    private double dblMouseX ;
    private double dblMouseY ;    
    
    private int intProjectId;
    private int intGraphId;
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    public void setMouseX(double pdblValue) {
        dblMouseX = pdblValue ;
    }
    public double getMouseX() {
        return dblMouseX ;
    }
    
    public void setMouseY(double pdblValue) {
        dblMouseY = pdblValue ;
    }
    public double getMouseY() {
        return dblMouseY ;
    }
    public Set<UIVertex> getSelectedVertices() {
        return setSelectedVertices ;
    }
    
    public Set<UIEdge> getSelectedEdges() {
        return setSelectedEdges ;
    }
    
    // Is Vertex Clicked
    public void setIsVertexClicked(boolean pblnIsVertexClicked) {
        blnIsVertexClicked = pblnIsVertexClicked ;
    }
    
    public boolean getIsVertexClicked() {
        return blnIsVertexClicked ;
    }
    
    public void setIsEdgeClicked(boolean pblnIsEdgeClicked) {
        blnIsEdgeClicked = pblnIsEdgeClicked ;
    }
    
    public boolean getIsEdgeClicked() {
        return blnIsEdgeClicked ;
    }
    
    public Map<UIVertex,Set<UIEdge>> getVertexEdgesMap() {
        return mapVertexEdges ;
    }
    
    public boolean getDragged(){
        return canvasDragged;
    }
    
    public void setDragged(boolean isCanvasDragged){
        canvasDragged = isCanvasDragged;
    }
    
    public int getNoOfVertices(){
        return this.mapVertices.size();
    }
    
    public int getNoOfEdges(){
        return this.mapEdges.size();
    }
    
    public void setProjectID(int intProjectID){
        this.intProjectId = intProjectID;
    }
    
    public int getProjectID(){
        return this.intProjectId;
    }
    
    public void setGraphID(int intGraphID){
        this.intGraphId = intGraphID;
    }
    
    public int getGraphID(){
        return this.intGraphId;
    }
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    /**
     *  Constructor Name: GraphCanvas ()
     *  Created Date    : 2016-02-01
     *  Description     : The3 Constructor of the GraphCanvas which initiates the drawing area
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pintTimeFrameIndex : int
     *  @param pstrVertexLabelAttr : String
     *  @param pstrVertexToolTipAttr : String
     *  @param pstrEdgeAttr : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-02-25      Talat           Added parameters for the constructor for strVertexLabelAttr, strVertexToolTipAttr, strEdgeAttr
     *  2016-02-25      Talat           Changing all the references to Point2D to x and y
     * 
    */
    public GraphCanvas(int pintProjectID, 
            int pintGraphID, 
            int pintTimeFrameIndex, 
            String pstrVertexLabelAttr, 
            String pstrVertexToolTipAttr, 
            String pstrEdgeAttr) {
        
        long time1 = System.currentTimeMillis();
        
        //setting graph and project ID
        this.setProjectID(pintProjectID);
        this.setGraphID(pintGraphID);
        
        
        // Initialize the data structures for Selected Vertices and Edges
        setSelectedVertices = new HashSet<>();
        setSelectedEdges = new HashSet<>();
        mapVertices = new HashMap<>();
        
        Map<Integer, Double[]> mapVertexPositions = 
                GraphAPI.getVertex2DPoistions(pintProjectID, 
                        pintGraphID, 
                        pintTimeFrameIndex, 
                        SceneConfig.GRAPHCANVAS_WIDTH, 
                        SceneConfig.GRAPHCANVAS_HEIGHT);
        //System.out.println("\t\t\t\t\t GraphCanvas : mapVertexPositions, its size = " + mapVertexPositions.size());
        Map<Integer, String[]> mapstrLabelTooltips = 
                GraphAPI.getVertexLabelsTooltips(pintProjectID, 
                        pintGraphID, 
                        pintTimeFrameIndex, 
                        pstrVertexLabelAttr, 
                        pstrVertexToolTipAttr);
        
        //getting the colors of all the vertices from logic.
        Map<Integer, String> mapVertexColors = 
                            GraphAPI.getAllVertexColors(pintProjectID, 
                                                        pintGraphID, 
                                                        pintTimeFrameIndex);
        
        //getting the vertex URL of all the vertices from logic.
        Map<Integer, String> mapVertexIconURLs = 
                            GraphAPI.getAllVertexIconURLs(pintProjectID, 
                                                        pintGraphID, 
                                                        pintTimeFrameIndex);
                
        // ADDING UI VERTICES TO THE GRAPH CANVAS
        // Loop through all the nodes and add it to the drawing area
        mapVertexEdges = new HashMap<>();
        mapVertexArrowHead = new HashMap<>();
        double start_vertex = System.currentTimeMillis();
        for (int intVertexID : mapVertexPositions.keySet()) {
            // Create a new UIVertex
            DoubleProperty dblXProp = new SimpleDoubleProperty(
                    mapVertexPositions.get(intVertexID)[0]);
            DoubleProperty dblYProp = new SimpleDoubleProperty(
                    mapVertexPositions.get(intVertexID)[1]);
            
            String vColorString = mapVertexColors.get(intVertexID);
            
            Color vColor;
            
            if(vColorString!=null)
                vColor = Color.valueOf(vColorString);
            else
                vColor = Color.valueOf(SceneConfig.VERTEX_COLOR_DEFAULT);
            
            String vIconURL = mapVertexIconURLs.get(intVertexID) ;
            
            UIVertex vtxCurrent = new UIVertex(this, 
                    intVertexID,
                    mapVertexPositions.get(intVertexID)[0], 
                    mapVertexPositions.get(intVertexID)[1], 
                    dblXProp, 
                    dblYProp, 
                    mapstrLabelTooltips.get(intVertexID)[0], 
                    mapstrLabelTooltips.get(intVertexID)[1],
                    vColor,
                    vIconURL); 
            // Add the vertex to the list of UI Vertices
            //arrVertices[intVertexID] = vtxCurrent ;
            mapVertices.put(intVertexID, vtxCurrent);
            mapVertexEdges.put(vtxCurrent, new HashSet<UIEdge>());
            mapVertexArrowHead.put(vtxCurrent, new HashSet<ArrowHead>());
            // Add the Vertex Holder to the graph canvas
            this.getChildren().add(vtxCurrent.getVertexHolder().getNode());
            this.getChildren().add(vtxCurrent.getVertexHolder().getLabelHolder()) ;
        }
        double end_vertex = System.currentTimeMillis();


        // The array arrarrintEdges store just the source and destination
        Map<Integer, Integer[]> mapEdgesInfo = GraphAPI.getEdges(pintProjectID, pintGraphID, pintTimeFrameIndex);
        
        //getting the colors of all the edges from logic.
        Map<Integer, String> mapEdgeColors = 
                            GraphAPI.getAllEdgeColors(pintProjectID, pintGraphID, pintTimeFrameIndex);
        
        Map<Integer, Boolean> mapEdgeDirected = GraphAPI.getAllEdgeDirValues(pintProjectID, pintGraphID, pintTimeFrameIndex);
        Map<Integer, Boolean> mapEdgePredicted = GraphAPI.getAllEdgePredValues(pintProjectID, pintGraphID, pintTimeFrameIndex);
        
        // The array arrEdges stores the references to all the Edges
        //TODO - arrEdges being initialised twice - check it
        //arrEdges = new UIEdge[arrarrintEdges.length] ;
        mapEdges = new HashMap<>();
        
        //for (int intEdgeID=0; intEdgeID<arrarrintEdges.length; intEdgeID++){
        double start_edge = System.currentTimeMillis();
        for (Integer edgeId : mapEdgesInfo.keySet()) {  
            
            int intSourceVertexID = mapEdgesInfo.get(edgeId)[0] ;
            int intDestinationVertexID = mapEdgesInfo.get(edgeId)[1] ;           
            int intEdgeType = mapEdgesInfo.get(edgeId)[2] ;
            boolean blnIsEdgeDirected = mapEdgeDirected.get(edgeId);
            boolean blnIsEdgePredicted = mapEdgePredicted.get(edgeId);
            UIVertex vtxSource = mapVertices.get(intSourceVertexID);
            UIVertex vtxDestination = mapVertices.get(intDestinationVertexID);
           
            ShapeCentre ctrSource = new ShapeCentre(mapVertices.get(intSourceVertexID).getVertexHolder());
            ShapeCentre ctrDestination = new ShapeCentre(mapVertices.get(intDestinationVertexID).getVertexHolder());
            
            String eColorString = mapEdgeColors.get(edgeId);
            Color eColor;
            if(eColorString!=null){
                eColor = Color.valueOf(eColorString);
            } else {
                eColor = Color.valueOf(SceneConfig.EDGE_COLOR_PRIMARYCOLOR);
            }
            
            blnIsEdgeDirected = false ;
            
            // blnIsPredicted = true ;
            UIEdge edgeCurrent = new UIEdge(
                      edgeId
                    , intSourceVertexID
                    , intDestinationVertexID
                    , blnIsEdgeDirected
                    , blnIsEdgePredicted
                    , ctrSource.centerXProperty()
                    , ctrSource.centerYProperty()
                    , ctrDestination.centerXProperty()
                    , ctrDestination.centerYProperty()
                    , this
                    , eColor
            );

            if(mapVertexEdges.containsKey(mapVertices.get(intSourceVertexID))) {
                mapVertexEdges.get(mapVertices.get(intSourceVertexID)).add(edgeCurrent);
            }
            
            if(mapVertexEdges.containsKey(mapVertices.get(intDestinationVertexID))) {
                mapVertexEdges.get(mapVertices.get(intDestinationVertexID)).add(edgeCurrent);
            }
            
            mapEdges.put(edgeId, edgeCurrent);
            
            this.getChildren().add(mapEdges.get(edgeId).getEdgeShape().getShapeNode());
            edgeCurrent.getEdgeShape().getShapeNode().toBack();
            
            blnIsEdgeDirected = false;
            if (blnIsEdgeDirected) {
                ArrowHead arrowHead = new ArrowHead(vtxSource, vtxDestination, edgeCurrent.getEdgeShape());
                this.getChildren().add(arrowHead);
                mapVertexArrowHead.get(vtxDestination).add(arrowHead);
            }
           
        }
        double end_edge = System.currentTimeMillis();
        System.out.println("Edge Plot time :"+ (end_edge-start_edge));
        System.out.println("Total Plot Time :"+ ((end_vertex-start_vertex) + (end_edge-start_edge)));
        
        long time2 = System.currentTimeMillis();
    }    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : updateVertexLabels()
     *  Created Date    : 2016-05-25
     *  Description     : Updates all the Vertex Labels
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param parrstrLabels : String []
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateVertexLabels(String [] parrstrLabels) {
        
        // Loop through all the nodes and add it to the drawing area
        for (UIVertex currentVertex : this.mapVertices.values()) {
            try {
                if (parrstrLabels[currentVertex.getVertexHolder().getID()] != null) {
                    currentVertex.getVertexHolder().updateLabel(parrstrLabels[currentVertex.getVertexHolder().getID()]);
                }                
            } catch (Exception ex) {
                continue ;
            }
        }
    }
    
    /**
     *  Method Name     : updateVertexTooltips()
     *  Created Date    : 2016-05-25
     *  Description     : Updates all the Vertex Tooltips
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param parrstrTooltips : String []
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateVertexTooltips(String [] parrstrTooltips) {
        
        // Loop through all the nodes and add it to the drawing area
        for (UIVertex currentVertex : this.mapVertices.values()) {
            try {
                if (parrstrTooltips[currentVertex.getVertexHolder().getID()] != null) {
                    currentVertex.getVertexHolder().updateTooltip(parrstrTooltips[currentVertex.getVertexHolder().getID()]);
                }                
            } catch (Exception ex) {
                continue ;
            }
        }
    }
    
    /**
     *  Method Name     : addAllVertices()
     *  Created Date    : 2016-05-04
     *  Description     : Adds all the vertices as selected vertices
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void addAllVerticesToSelectedList() {
        try {
            if (setSelectedVertices == null) {
                setSelectedVertices = new HashSet<>();
            }

            setSelectedVertices.clear();
            
            //setSelectedVertices.addAll(Arrays.asList(arrVertices));
            setSelectedVertices.addAll(mapVertices.values());
        } catch (Exception ex) {
            System.out.println("GraphCanvas.addVertexToSelectedList(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    /**
     *  Method Name     : addAllEdgesToSelectedList()
     *  Created Date    : 2016-07-06
     *  Description     : Adds all the vertices as selected vertices
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void addAllEdgesToSelectedList() {
        try {
            if (setSelectedEdges == null) {
                setSelectedEdges = new HashSet<>();
            }
            
            setSelectedEdges.clear();            
            //setSelectedEdges.addAll(Arrays.asList(arrEdges));
            setSelectedEdges.addAll(mapEdges.values());
        } catch (Exception ex) {
            System.out.println("GraphCanvas.addAllEdgesToSelectedList(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    
    /**
     *  Method Name     : addVertexToSelectedList()
     *  Created Date    : 2016-05-04
     *  Description     : Adds a single vertices as selected vertices
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pVertex : UIVertex
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void addVertexToSelectedList(UIVertex pVertex) {
        try {
            if (setSelectedVertices == null) {
                setSelectedVertices = new HashSet<>();
            }

            setSelectedVertices.add(pVertex) ;
        } catch (Exception ex) {
            System.out.println("GraphCanvas.addVertexToSelectedList(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    /**
     *  Method Name     : addEdgeToSelectedList()
     *  Created Date    : 2016-07-06
     *  Description     : Adds a single vertices as selected vertices
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pUIEdge : UIEdge
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void addEdgeToSelectedList(UIEdge pUIEdge) {
        try {
            if (setSelectedEdges == null) {
                setSelectedEdges = new HashSet<>();
            }
            //System.out.println("GraphCanvas : addEdgeToSelectedList " + pUIEdge.getSourceVertexID() + " , " + pUIEdge.getDestinationVertexID());
            setSelectedEdges.add(pUIEdge) ;
        } catch (Exception ex) {
            System.out.println("GraphCanvas.addEdgeToSelectedList(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    
    /**
     *  Method Name     : selectVertex()
     *  Created Date    : 2016-05-11
     *  Description     : Selects a vertex on the canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param puiVertex : UIVertex
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void selectVertex(UIVertex puiVertex) {
        try {
            int intID = puiVertex.getVertexHolder().getID() ;
            UIVertex currentVertex = this.mapVertices.get(intID) ;
            if (currentVertex != null) {
                currentVertex.getVertexHolder().selectVertex();
            } 
        } catch (Exception ex) {
            System.out.println("GraphCanvas.selectVertex(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    /**
     *  Method Name     : selectEdge()
     *  Created Date    : 2016-05-11
     *  Description     : Selects a vertex on the canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param puiEdge : UIEdge
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void selectEdge(UIEdge puiEdge) {
        try {
            int intID = puiEdge.getID() ;
            UIEdge currentEdge = this.mapEdges.get(intID) ;
            if (currentEdge != null) {
                currentEdge.selectEdge();
            } 
        } catch (Exception ex) {
            System.out.println("GraphCanvas.selectEdge(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    
    /**
     *  Method Name     : selectVertex()
     *  Created Date    : 2016-05-11
     *  Description     : Selects a vertex on the canvas (only one vertex, rest will be deselected)
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintVertexID : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void selectVertex(int pintVertexID) {
        try {
            UIVertex currentVertex = this.mapVertices.get(pintVertexID);
            if (currentVertex != null) {
                clearSelectedVertexList();
                currentVertex.getVertexHolder().selectVertex();
            }
        } catch (Exception ex) {
            System.out.println("GraphCanvas.selectVertex(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
        /**
     *  Method Name     : getSelectVertex()
     *  Created Date    : 2017-03-30
     *  Description     : get the UIVertex with given id, clear the selection list
     *  Version         : 1.0
     *  @author         : Abhi
     * 
     *  @param pintVertexID : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public UIVertex getSelectVertex(int pintVertexID) {
        UIVertex currentVertex = null;
        try {
            currentVertex = this.mapVertices.get(pintVertexID) ;
            if (currentVertex != null) {
                clearSelectedVertexList();
                currentVertex.getVertexHolder().selectVertex();
                
            }
        } catch (Exception ex) {
            System.out.println("GraphCanvas.selectVertex(): EXCEPTION !");
            ex.printStackTrace();
        }
        return currentVertex;
    }
    
    /**
     *  Method Name     : selectEdge()
     *  Created Date    : 2016-07-06
     *  Description     : Selects a Edge on the canvas (only one vertex, rest will be deselected)
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintEdgeID : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void selectEdge(int pintEdgeID) {
        try {
            UIEdge currentEdge = this.mapEdges.get(pintEdgeID) ;
            if (currentEdge != null) {
                clearSelectedEdgeList();
                currentEdge.selectEdge();
            }
        } catch (Exception ex) {
            System.out.println("GraphCanvas.selectEdge(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    /**
     *  Method Name     : selectVertex()
     *  Created Date    : 2016-05-11
     *  Description     : Selects a Vertex on the canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param plstUIVertex : List<UIVertex>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void selectVertex(List<UIVertex> plstUIVertex) {
        try {
            for (UIVertex vtxCurrent : plstUIVertex) {
                int intID = vtxCurrent.getVertexHolder().getID() ;
                UIVertex currentVertex = this.mapVertices.get(intID) ;
                if (currentVertex != null) {
                    currentVertex.getVertexHolder().selectVertex();
                }
            }
        } catch (Exception ex) {
            System.out.println("GraphCanvas.selectVertex(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    /**
     *  Method Name     : selectEdge()
     *  Created Date    : 2016-07-06
     *  Description     : Selects a Edge on the canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param plstUIEdge : List<UIEdge>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void selectEdge(List<UIEdge> plstUIEdge) {
        try {
            for (UIEdge edgCurrent : plstUIEdge) {
                int intID = edgCurrent.getID() ;
                UIEdge currentEdge = this.mapEdges.get(intID) ;
                if (currentEdge != null) {
                    currentEdge.selectEdge();
                }
            }
        } catch (Exception ex) {
            System.out.println("GraphCanvas.selectEdge(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    
    /**
     *  Method Name     : selectVertex()
     *  Created Date    : 2016-05-11
     *  Description     : Selects a vertex on the canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param psetintVertexID : Set<Integer>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void selectVertex(Set<Integer> psetintVertexID) {
        try {
            for (int intCurrentVertexID : psetintVertexID) {
                UIVertex currentVertex = this.mapVertices.get(intCurrentVertexID) ;
                if (currentVertex != null) {
                    currentVertex.getVertexHolder().selectVertex();
                } 
            }
        } catch (Exception ex) {
            System.out.println("GraphCanvas.selectVertex(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    
    /**
     *  Method Name     : selectEdge()
     *  Created Date    : 2016-07-06
     *  Description     : Selects a vertex on the canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param psetintEdgeID : Set<Integer>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void selectEdge(Set<Integer> psetintEdgeID) {
        try {
            for (int intCurrentEdgeID : psetintEdgeID) {
                UIEdge currentEdge = this.mapEdges.get(intCurrentEdgeID) ;
                if (currentEdge != null) {
                    currentEdge.selectEdge();
                } 
            }
        } catch (Exception ex) {
            System.out.println("GraphCanvas.selectEdge(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    
    /**
     *  Method Name     : selectAllVertices()
     *  Created Date    : 2016-05-11
     *  Description     : Selects a vertex on the canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void selectAllVertices() {
        try {
            for (UIVertex vtxCurrent : this.mapVertices.values()) {
                int intID = vtxCurrent.getVertexHolder().getID() ;
                UIVertex currentVertex = this.mapVertices.get(intID) ;
                if (currentVertex != null) {
                    currentVertex.getVertexHolder().selectVertex();
                }
            }
        } catch (Exception ex) {
            System.out.println("GraphCanvas.selectAllVertices(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    /**
     *  Method Name     : selectAllEdges()
     *  Created Date    : 2016-05-11
     *  Description     : Selects a vertex on the canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void selectAllEdges() {
        try {
            for (UIEdge vtxCurrent : this.mapEdges.values()) {
                int intID = vtxCurrent.getID() ;
                UIEdge currentEdge = this.mapEdges.get(intID) ;
                if (currentEdge != null) {
                    currentEdge.selectEdge();
                }
            }
        } catch (Exception ex) {
            System.out.println("GraphCanvas.selectAllEdges(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    /**
     *  Method Name     : clearMiningResults()
     *  Created Date    : 2016-05-17
     *  Description     : Clears the Mining results
     *  Version         : 1.0
     *  @author         : Talat
     * 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void clearMiningResults() {
        try {
            for (UIVertex vtxCurrent : this.mapVertices.values()) {
                int intID = vtxCurrent.getVertexHolder().getID() ;
                UIVertex currentVertex = this.mapVertices.get(intID) ;
                if (currentVertex != null) {
                    currentVertex.getVertexHolder().clearMiningResult();
                }
            }
            
//            //Note: removing this block of code for now as community mining doesn't affect edges in any way.

            for (UIEdge edgeCurrent : this.mapEdges.values()) {
                int intID = edgeCurrent.getID() ;
                UIEdge currentEdge = this.mapEdges.get(intID) ;
                if (currentEdge != null) {
                    currentEdge.clearMiningResult();
                }
            }
                
                //clearing any selected edges.
                this.setSelectedEdges.clear();
        } catch (Exception ex) {
            System.out.println("GraphCanvas.clearMiningResults(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    /**
     * Clears the Link Prediction by removing the predicted edges
     * @param pintTimeFrameIndex 
     * 
     * @author Talat
     * @since 2018-04-07
     */
    public void clearLinkPredictionResultsUI(int pintTimeFrameIndex) {
        try {
            Map<Integer, UIEdge> vertexEdgesMap = this.getEdges();
            Map<Integer, Integer[]> mapValues = GraphAPI.removePredictedEdgesInGraph(intProjectId, intGraphId, pintTimeFrameIndex);
            for(int edgeId : mapValues.keySet()) {
                UIEdge edge = vertexEdgesMap.get(edgeId);
                if (edge != null) {
                    this.getChildren().remove(edge.getEdgeShape().getShapeNode());
                    if(setSelectedEdges.contains(mapEdges.get(edgeId))) {
                        mapEdges.get(edgeId).removeEdgeFromSelectedEdgesList();
                    }
                    mapEdges.remove(edgeId);
                }
            }
        } catch (Exception ex) {
            System.out.println("GraphCanvas.clearLinkPredictionResults(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    /**
     * Hides the Regular Edges in UI by removing it only from the Canvas
     * @param pintTimeFrameIndex 
     * 
     * @author Talat
     * @since 2018-04-14
     */
    public void hideNonPredictedEdgesInUI(int pintTimeFrameIndex) {
        try {
            Map<Integer, UIEdge> vertexEdgesMap = this.getEdges();
            Map<Integer, Integer[]> mapValues = GraphAPI.getNonPredictedEdges(intProjectId, intGraphId, pintTimeFrameIndex);
            for(int edgeId : mapValues.keySet()) {
                UIEdge edge = vertexEdgesMap.get(edgeId);
                if (edge != null) {
                    this.getChildren().remove(edge.getEdgeShape().getShapeNode());
                    if(setSelectedEdges.contains(mapEdges.get(edgeId))) {
                        mapEdges.get(edgeId).removeEdgeFromSelectedEdgesList();
                    }
                    mapEdges.remove(edgeId);
                }
            }
        } catch (Exception ex) {
            System.out.println("GraphCanvas.hideEdgesInUI(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    /**
     * Shows the Regular Edges in UI by adding it to the Canvas
     * @param pintTimeFrameIndex 
     * 
     * @author Talat
     * @since 2018-04-14
     */
    public void showNonPredictedEdgesInUI(int pintTimeFrameIndex) {
        try {
            Map<Integer, Integer[]> mapValues = GraphAPI.getNonPredictedEdges(intProjectId, intGraphId, pintTimeFrameIndex);
    
            for (Integer intEdgeId : mapValues.keySet()) {
                int intSourceVertexId = mapValues.get(intEdgeId)[0];
                int intDestinationVertexId = mapValues.get(intEdgeId)[1];
                updateCanvasAfterAddEdge(intProjectId, intGraphId, pintTimeFrameIndex, intEdgeId, 
                        intSourceVertexId, intDestinationVertexId);
            }
        } catch (Exception ex) {
            System.out.println("GraphCanvas.showEdgesInUI(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    /**
     * Hides the Predicted Edges in UI by removing it only from the Canvas
     * @param pintTimeFrameIndex 
     * 
     * @author Talat
     * @since 2018-04-14
     */
    public void hidePredictedEdgesInUI(int pintTimeFrameIndex) {
        try {
            Map<Integer, UIEdge> vertexEdgesMap = this.getEdges();
            Map<Integer, Integer[]> mapValues = GraphAPI.getPredictedEdges(intProjectId, intGraphId, pintTimeFrameIndex);
            for(int edgeId : mapValues.keySet()) {
                UIEdge edge = vertexEdgesMap.get(edgeId);
                if (edge != null) {
                    this.getChildren().remove(edge.getEdgeShape().getShapeNode());
                    if(setSelectedEdges.contains(mapEdges.get(edgeId))) {
                        mapEdges.get(edgeId).removeEdgeFromSelectedEdgesList();
                    }
                    mapEdges.remove(edgeId);
                }
            }
        } catch (Exception ex) {
            System.out.println("GraphCanvas.hidePredictedEdgesInUI(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    /**
     * Shows the Predicted Edges in UI by adding it to the Canvas
     * @param pintTimeFrameIndex 
     * 
     * @author Talat
     * @since 2018-04-14
     */
    public void showPredictedEdgesInUI(int pintTimeFrameIndex) {
        try {
            Map<Integer, Integer[]> mapValues = GraphAPI.getPredictedEdges(intProjectId, intGraphId, pintTimeFrameIndex);
    
            for (Integer intEdgeId : mapValues.keySet()) {
                int intSourceVertexId = mapValues.get(intEdgeId)[0];
                int intDestinationVertexId = mapValues.get(intEdgeId)[1];
                updateCanvasAfterAddEdge(intProjectId, intGraphId, pintTimeFrameIndex, intEdgeId, 
                        intSourceVertexId, intDestinationVertexId);
            }
        } catch (Exception ex) {
            System.out.println("GraphCanvas.showPredictedEdgesInUI(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    
    /**
     *  Method Name     : clearSelectedVertexList()
     *  Created Date    : 2016-05-04
     *  Description     : Clears all the selected list selected vertices
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void clearSelectedVertexList() {        
        try {
            for (UIVertex currentVertex : getSelectedVertices()) {
                currentVertex.changeToVertexColor();
                currentVertex.getVertexHolder().getVertexSelectBooleanProperty().setValue(false);
            }
            
            setSelectedVertices.clear();
            
            //disable the vertex SizeToolBox
            SizeToolBox.getInstance().disableVertexSizeToolbox();
            ColorToolBox.getInstance().disableColorToolbox();
        } catch (Exception ex) {
            System.out.println("GraphCanvas.clearSelectedVertexList(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    
    /**
     *  Method Name     : clearSelectedVertexList()
     *  Created Date    : 2016-05-04
     *  Description     : Clears all the selected list selected vertices
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void clearSelectedEdgeList() {        
        try {
            for (UIEdge currentEdge : getSelectedEdges()) {
                currentEdge.changeColorDefault();
            }
            
            setSelectedEdges.clear();
            
            //disable the vertex SizeToolBox
            SizeToolBox.getInstance().disableEdgeSizeToolbox();
        } catch (Exception ex) {
            System.out.println("GraphCanvas.clearSelectedVertexList(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    
    /**
     *  Method Name     : removeVertexFromSelectedList()
     *  Created Date    : 2016-05-04
     *  Description     : Removes the selected vertex to the list
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pVertex : UIVertex
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void removeVertexFromSelectedList(UIVertex pVertex) {
        try {
            setSelectedVertices.remove(pVertex) ;
        } catch (Exception ex) {
            System.out.println("GraphCanvas.removeVertexFromSelectedList(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    /**
     *  Method Name     : removeEdgeFromSelectedList()
     *  Created Date    : 2016-05-04
     *  Description     : Removes the selected vertex to the list
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pEdge : UIEdge
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void removeEdgeFromSelectedList(UIEdge pEdge) {
        try {
            setSelectedEdges.remove(pEdge) ;
        } catch (Exception ex) {
            System.out.println("GraphCanvas.removeEdgeFromSelectedList(): EXCEPTION !");
            ex.printStackTrace();
        }
    }
    
    
    
    /**
     *  Method Name     : updateCanvas()
     *  Created Date    : 2016-03-11
     *  Description     : Updates the Graph on canvas based on the new positions
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pintTimeFrameIndex : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateCanvas(
              int pintProjectID
            , int pintGraphID
            , int pintTimeFrameIndex
    ) {
        
        // double [][] arrarrVerticesPositions = GraphAPI.getVertex2DPoistions(pintProjectID, pintGraphID, pintTimeFrameIndex, SceneConfig.GRAPHCANVAS_WIDTH, SceneConfig.GRAPHCANVAS_HEIGHT);
        
        // Map<Integer, Double []> map2DPositions = LayoutAPI.getResults(pintProjectID, pintGraphID, pintTimeFrameIndex, "LayoutCircle", null);
        Map<Integer, Double []> map2DPositions = GraphAPI.getVertex2DPoistions(pintProjectID, pintGraphID, pintTimeFrameIndex, SceneConfig.GRAPHCANVAS_WIDTH, SceneConfig.GRAPHCANVAS_HEIGHT);
        
        System.out.println("GraphCanvas.updateCanvas(): Total Size: "+mapVertices.size()) ;
        for (UIVertex currentVertex : mapVertices.values()) {
            
            int intVertexID = currentVertex.getVertexHolder().getID() ;
            double dblNewX = map2DPositions.get(intVertexID)[0] ;
            double dblNewY = map2DPositions.get(intVertexID)[1] ;

            // System.out.println("GraphCanvas.updateCanvas(): VertexID: " + intVertexID + "\t(" + dblNewX + " , " + dblNewY + ")") ;
            currentVertex.getVertexHolder().updatePosition(dblNewX, dblNewY);
        }  
    }    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    /**
     *  Method Name     : getChildren()
     *  Created Date    : 2016-01-28
     *  Description     : Gets all the children (vertices and edges) attached to the GraphCanvas group
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return ObservableList<Node>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public final ObservableList<Node> getChildren() {
        return super.getChildren();
    }
    
    /**
     *  Method Name     : ScaleX()
     *  Created Date    : 2015-07-xx
     *  Description     : Scales the X axis based on the parameter sent as the scalefactor
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pdblScaleFactor : double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-01-20      Talat           Changes the map into an array due to the change in the list of vertices and edges data structure
     * 
    */
    public void ScaleX(double pdblScaleFactor) {
        super.setScaleX(pdblScaleFactor);         
        for (UIVertex vertex : mapVertices.values()) {
            if (vertex != null) {
                vertex.getVertexHolder().setScaleX(1/pdblScaleFactor);
            }
        }
        for (UIEdge edge : mapEdges.values()) {
            if (edge != null) {
                edge.getEdgeShape().restoreStrokeWidth(1/pdblScaleFactor);
            }
        }
    }
    
    /**
     *  Method Name     : ScaleY()
     *  Created Date    : 2015-07-xx
     *  Description     : Scales the Y axis based on the parameter sent as the scale factor
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pdblScaleFactor : double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-01-20      Talat           Changes the map into an array due to the change in the list of vertices and edges data structure
     * 
    */
    public void ScaleY(double pdblScaleFactor) {
        super.setScaleY(pdblScaleFactor);
        for (UIVertex vertex : mapVertices.values()) {
            if (vertex != null) {
                vertex.getVertexHolder().setScaleY(1/pdblScaleFactor);
            }
        }
        for (UIEdge edge : mapEdges.values()) {
            if (edge != null) {
                edge.getEdgeShape().restoreStrokeWidth(pdblScaleFactor);
            }
        }
    }
    
    
    /**
     *  Method Name     : ScaleX()
     *  Created Date    : 2015-07-xx
     *  Description     : Scales the X and Y axes based on the parameter sent as the scalefactor
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pdblScaleFactor : double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-01-20      Talat           Changes the map into an array due to the change in the list of vertices and edges data structure
     * 
    */
    public void ScaleXY(double pdblScaleFactor) {
        
        super.setScaleX(pdblScaleFactor);
        super.setScaleY(pdblScaleFactor);
        
        for (UIVertex vertex : mapVertices.values()) {
            if (vertex != null) {
                vertex.getVertexHolder().setScaleX(1/pdblScaleFactor);
                vertex.getVertexHolder().setScaleY(1/pdblScaleFactor);
                
                vertex.getVertexHolder().getLabelHolder().setScaleX(1/pdblScaleFactor);
                vertex.getVertexHolder().getLabelHolder().setScaleY(1/pdblScaleFactor);
            }
        }
        
        for (UIEdge edge : mapEdges.values()) {
            if (edge != null) {
                edge.getEdgeShape().restoreStrokeWidth(1/pdblScaleFactor);
            }
        }
    }
    
    /**
     *  Method Name     : getVertices()
     *  Created Date    : 2016-01-28
     *  Description     : Gets all the vertices attached to the GraphCanvas group
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return UIVertex[]
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public Map<Integer, UIVertex> getVertices() {
        return mapVertices;
    }
    
    /**
     *  Method Name     : getEdges()
     *  Created Date    : 2016-01-28
     *  Description     : Gets all the edges attached to the GraphCanvas group
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return UIEdge []
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public Map<Integer, UIEdge> getEdges() {
        return mapEdges;
    }
    
    /**
     *  Method Name     : updateVerticesColorCommunitiesAfterMining()
     *  Created Date    : 2016-04-29
     *  Description     : Updates the Vertices with the Communities after mining.
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pmapCommunities : Map<Integer, List<Integer>>
     *  @param pmapCommunitiesColor : Map<Integer, Color>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateVerticesColorCommunitiesAfterMining(Map<String, 
            List<Integer>> pmapCommunities, 
            Map<String, Color> pmapCommunitiesColor) {
        
        for (String strCommunityID : pmapCommunities.keySet()) {
            List<Integer> lstVertices = pmapCommunities.get(strCommunityID) ;
            
            Color clrCommunityColor = pmapCommunitiesColor.get(strCommunityID) ;
            
            for (int intVertexID : lstVertices) {
                    mapVertices.get(intVertexID).setColor(clrCommunityColor);
            }
        }
    }
    
    /**
     *  Method Name     : updateVerticesColorCommunitiesOnLoading()
     *  Created Date    : 2018-01-25
     *  Description     : Updates the Vertices with the Communities on graph load.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pmapCommunities : Map<Integer, List<Integer>>
     *  @param pmapCommunitiesColor : Map<Integer, Color>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateVerticesColorCommunitiesOnLoading(Map<String, 
            List<Integer>> pmapCommunities, 
            Map<String, Color> pmapCommunitiesColor) {
        
        for (String strCommunityID : pmapCommunities.keySet()) {
            List<Integer> lstVertices = pmapCommunities.get(strCommunityID) ;
            
            Color clrCommunityColor = pmapCommunitiesColor.get(strCommunityID) ;
            
            Map<Integer, Boolean> mapCheckSysColor = checkVertexSysColorPresent(lstVertices);
            
            //only color those vertices with community color which do not have SYS:COLOR attribute
            for (int intVertexID : lstVertices) {
                if(!mapCheckSysColor.get(intVertexID))
                    mapVertices.get(intVertexID).setColor(clrCommunityColor);
            }
        }
    }
    
    /**
     *  Method Name     : updateEdgesColorCommunities()
     *  Created Date    : 2017-12-15
     *  Description     : Updates the edges with the Community colors
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pmapCommunities : Map<Integer, List<Integer>>
     *  @param pmapCommunitiesColor : Map<Integer, Color>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateEdgesColorCommunitiesAfterMining(Map<String, 
            List<Integer>> pmapCommunities, 
            Map<String, Color> pmapCommunitiesColor) {
        
        for (String strCommunityID : pmapCommunities.keySet()) {
            List<Integer> lstVertices = pmapCommunities.get(strCommunityID) ;

            Color clrCommunityColor = pmapCommunitiesColor.get(strCommunityID) ;
            
            //color the edges with community color but only for those which belong to the same community.
            for (int intVertexID : lstVertices) {
                Set<UIEdge> setofEdges = mapVertexEdges.get(mapVertices.get(intVertexID));
                
                for(UIEdge e : setofEdges){
                    if(lstVertices.contains(e.getSourceVertexID()) && lstVertices.contains(e.getDestinationVertexID()))
                        e.getEdgeShape().changePrimaryColor(clrCommunityColor);                    
                }
            }
        }
    }
    /**
     *  Method Name     : updateEdgesColorCommunitiesOnLoading()
     *  Created Date    : 2018-01-24
     *  Description     : Updates the edges with the Community colors
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pmapCommunities : Map<Integer, List<Integer>>
     *  @param pmapCommunitiesColor : Map<Integer, Color>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateEdgesColorCommunitiesOnLoading(Map<String, 
            List<Integer>> pmapCommunities, 
            Map<String, Color> pmapCommunitiesColor) {
        
        for (String strCommunityID : pmapCommunities.keySet()) {
            List<Integer> lstVertices = pmapCommunities.get(strCommunityID) ;

            Color clrCommunityColor = pmapCommunitiesColor.get(strCommunityID) ;
            
            //color the edges with community color but only for those which belong to the same community.
            for (int intVertexID : lstVertices) {
                Set<UIEdge> setofEdges = mapVertexEdges.get(mapVertices.get(intVertexID));
                
                for(UIEdge e : setofEdges){
                    List<Integer> edgeList = new ArrayList<>();
                    edgeList.add(e.getID());
                    if(lstVertices.contains(e.getSourceVertexID()) && lstVertices.contains(e.getDestinationVertexID()) &&
                            !checkEdgeSysColorPresent(edgeList).get(e.getID())){
                        e.getEdgeShape().changePrimaryColor(clrCommunityColor); 
                    }
                }
            }
        }
    }
    
    
        /**
     *  Method Name     : updateColorEdgesAmongVertices()
     *  Created Date    : 2017-12-13
     *  Description     : Updates the edges with the Community colors
     *  Version         : 1.0
     *  @author         : Abhi
     * 
     *  @param pSetVertexIds : Set<Integer>
     *  @param newColor : Color
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateColorEdgesAmongVertices(Set<Integer> pSetVertexIds, Color newColor) {

            //color the edges among the setOfVertices with the given color
            for (int intVertexID : pSetVertexIds) {
                Set<UIEdge> setofEdges = mapVertexEdges.get(mapVertices.get(intVertexID));
                
                for(UIEdge e : setofEdges){
                    if(pSetVertexIds.contains(e.getSourceVertexID()) && pSetVertexIds.contains(e.getDestinationVertexID()))
                        e.getEdgeShape().changePrimaryColor(newColor);                    
                }
            }
        
    }
        /**
     *  Method Name     : updateColorVertices()
     *  Created Date    : 2017-12-12
     *  Description     : sets new color of the set of Vertices
     *  Version         : 1.0
     *  @author         : Abhi
     * 
     *  @param pSetVertexIds : Set<Integer>
     *  @param newColor : Color
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateColorVertices(Set<Integer> pSetVertexIds, Color newColor) {            
        for (int intVertexID : pSetVertexIds) {
            mapVertices.get(intVertexID).setColor(newColor);
        }
    }
    
    public void updateColorEdges(Set<Integer> psetEdgeIDs, Color newColor) {            
        for (int intEdgeID : psetEdgeIDs) {
            mapEdges.get(intEdgeID).setColor(newColor);
        }
    }
    
    
    /**
     *  Method Name     : translateSelectedVertices()
     *  Created Date    : 2016-
     *  Description     : Shifts the Vertices
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pdblXShift : double
     *  @param pdblYShift : double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void translateSelectedVertices(double pdblXShift, double pdblYShift) {
        
        double scaleXParentPanAndZoomPane = this.getParent().getScaleX();

        for (UIVertex currentVertex : this.setSelectedVertices) {
            currentVertex.getVertexHolder().translateXY(pdblXShift, pdblYShift, scaleXParentPanAndZoomPane);
            for (ArrowHead arrow : mapVertexArrowHead.get(currentVertex)){
                arrow.update();
            }
        }
    }
    
    /**
     *  Method Name     : throbSelectedVertices()
     *  Created Date    : 2016-07-11
     *  Description     : Throbs the Selected Vertices 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void ThrobAndBlinkSelectedVertices() {
        if(setSelectedVertices.size()>0){
            for (UIVertex currentVertex : setSelectedVertices) {
                currentVertex.getVertexHolder().getVertexShape().throbAndBlink(SceneConfig.VERTEX_TIMEPERTHROB_MILLIS, 
                        SceneConfig.VERTEX_THROB_COUNT, SceneConfig.VERTEX_THROB_SCALE, SceneConfig.VERTEX_TIMESCALE_MILLIS);
            }
        }else{
            InfoDialog.Display("Please select vertices to throb!", 3);
        }
    }
    
    /**
     *  Method Name     : getVerticesCoordinatesAbsolute()
     *  Created Date    : 2017-04-12
     *  Description     : Returns a map of all vertex ids and their absolute location in PanAndZoomPane
     *  Version         : 1.0
     *  @author         : Abhi
     *  @return Map<Integer, Double[]>
     * 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public Map<Integer, Double[]> getVerticesCoordinatesAbsolute() {
        Map<Integer, Double[]> mapVertexLocation = new HashMap<>();
        
        try {
            for (UIVertex vtxCurrent : this.mapVertices.values()) {
                Double[] arrXY = new Double[2];
                arrXY[0] = vtxCurrent.getVertexHolder().getXCentreProperty().getValue();
                arrXY[1] = vtxCurrent.getVertexHolder().getYCentreProperty().getValue();
                mapVertexLocation.put(vtxCurrent.getVertexHolder().getID(), arrXY);
            }
            
        } catch (Exception ex) {
            System.out.println("GraphCanvas.VerticesCoordinatesAbsolute(): EXCEPTION !");
            ex.printStackTrace();
        }
        return mapVertexLocation;
    }
    
     /**
     *  Method Name     : updateCanvasAfterVertexRemove()
     *  Created Date    : 2017-06-15
     *  Description     : updates the vertex map after vertices are deleted.
     *  Version         : 1.0
     *  @author         : sankalp
     *  @param vertexIDs: List<Integer>
     *  @param edgeIDs  : List<Integer>
     *  
     * 
    */
    public void updateCanvasAfterVertexRemove(List<Integer> vertexIDs, List<Integer> edgeIDs) {
        System.out.println("GraphCanvas:updateCanvasAfterVertexRemove() -> Updating data structures after vertices are deleted");
        for(Integer vertexID : vertexIDs){
            //remove UIVertex and UIEdges entry from the map.
            if(mapVertexEdges.containsKey(mapVertices.get(vertexID)))
                mapVertexEdges.remove(mapVertices.get(vertexID));
            
            //deselect removed vertex from the selected list.
            mapVertices.get(vertexID).removeVertexFromSelectedList();
            
            //remove UIVertex object from the map
            mapVertices.remove(vertexID);
        }
        
        //deselect and remove UIEdges which were removed when the corresponding vertices were deleted.
        for(Integer edgeID : edgeIDs){
            if(setSelectedEdges.contains(mapEdges.get(edgeID)))
                mapEdges.get(edgeID).removeEdgeFromSelectedEdgesList();
            mapEdges.remove(edgeID);
        }
        
        System.out.println("Size of Vertex Map after Vertex removal :"+ mapVertices.size());
    }
    
    /**
     *  Method Name     : updateCanvasAfterEdgeRemove()
     *  Created Date    : 2017-06-15
     *  Description     : updates the Edge map after the edges are deleted
     *  Version         : 1.0
     *  @author         : sankalp
     *  @param vertexIDs: Set<Integer>
     *  @param edgeIDs  : Set<Integer>
     * 
    */

    public void updateCanvasAfterEdgeRemove(Set<Integer> vertexIDs, Set<Integer> edgeIDs) {
        System.out.println("GraphCanvas:updateCanvasAfterEdgeRemove() -> Updating data structures after edges are deleted");
        //remove UIVertex and UIEdges entry from the map.
        for(Integer vertexID : vertexIDs){
            if(mapVertexEdges.containsKey(mapVertices.get(vertexID))){
                for(Integer edgeID : edgeIDs){
                    if(mapVertexEdges.get(mapVertices.get(vertexID)).contains(mapEdges.get(edgeID)))
                        mapVertexEdges.get(mapVertices.get(vertexID)).remove(mapEdges.get(edgeID));
                }
            }   
        }
        
        //deselect and remove UIEdge object from the map
        for(Integer edgeID : edgeIDs){
            if(setSelectedEdges.contains(mapEdges.get(edgeID))){
                mapEdges.get(edgeID).removeEdgeFromSelectedEdgesList();
                this.setIsEdgeClicked(false);
            }
            mapEdges.remove(edgeID);
        }
        
        System.out.println("Size of Edge Map after edge removal :"+ mapEdges.size());
    }
    
    /**
     *  Method Name     : updateCanvasAfterAddingVertex()
     *  Created Date    : 2017-06-15
     *  Description     : updates the canvas after adding a vertex in logic
     *  Version         : 1.0
     *  @author         : Abhi
     *  @param pintVertexId: int
     *  @param pintProjectId  : int
     *  @param pintGraphId  : int
     *  @param pintTimeFrameIndex  : int
     *  @param pstrVertexLabelAttr : String
     *  @param pstrVertexTooltipAttr : String
     * 
    */
    public void updateCanvasAfterAddingVertex(
            int pintVertexId, 
            int pintProjectId, 
            int pintGraphId, 
            int pintTimeFrameIndex,
            String pstrVertexLabelAttr,
            String pstrVertexTooltipAttr){
        //get vertex attributes from logic
        Double[] arrVertexPositions = 
                GraphAPI.getVertex2DPosition(pintProjectId, 
                        pintGraphId, 
                        pintTimeFrameIndex,
                        pintVertexId,
                        SceneConfig.GRAPHCANVAS_WIDTH, 
                        SceneConfig.GRAPHCANVAS_HEIGHT);
        
        String [] arrstrLabelTooltips = 
                GraphAPI.getVertexLabelTooltips(pintProjectId, 
                        pintGraphId, 
                        pintTimeFrameIndex,
                        pintVertexId,
                        pstrVertexLabelAttr, 
                        pstrVertexTooltipAttr);
        
        String vColorString = GraphAPI.getVertexColor(pintProjectId, pintGraphId, pintTimeFrameIndex, pintVertexId);
        Color vColor;
            
        if(vColorString!=null)
            vColor = Color.valueOf(vColorString);
        else
            vColor = Color.valueOf(SceneConfig.VERTEX_COLOR_DEFAULT);
        
        String strIconURL = GraphAPI.getVertexIconURL(pintProjectId, pintGraphId, pintTimeFrameIndex, pintVertexId);
        
        // Create a new UIVertex
        DoubleProperty dblXProp = new SimpleDoubleProperty(
                arrVertexPositions[0]);
        DoubleProperty dblYProp = new SimpleDoubleProperty(
                arrVertexPositions[1]);
        
        UIVertex vtxCurrent = new UIVertex(this, 
                pintVertexId, 
                arrVertexPositions[0], 
                arrVertexPositions[1], 
                dblXProp, 
                dblYProp, 
                arrstrLabelTooltips[0], 
                arrstrLabelTooltips[1],
                vColor,
                strIconURL); 

        
        // Add the vertex to the list of UI Vertices
        mapVertices.put(pintVertexId, vtxCurrent);
        mapVertexEdges.put(vtxCurrent, new HashSet<UIEdge>());

        // Add the Vertex Holder to the graph canvas
        this.getChildren().add(vtxCurrent.getVertexHolder().getNode());
        this.getChildren().add(vtxCurrent.getVertexHolder().getLabelHolder()) ;

        vtxCurrent.getVertexHolder().selectVertex();
        
    }
    
    
    /**
     *  Method Name     : updateCanvasAfterAddingVertexOutsideGraphBoundary()
     *  Created Date    : 2017-06-15
     *  Description     : updates the canvas after adding a vertex in logic with given x,y coordinates
     *  Version         : 1.0
     *  @author         : Abhi
     *  @param pintVertexId: int
     *  @param pintProjectId  : int
     *  @param pintGraphId  : int
     *  @param pintTimeFrameIndex  : int
     *  @param x : int
     *  @param y : int
     *  @param verteXLabelAttr
     *  @param vertexTooltipAttr
     * 
    */
    public void updateCanvasAfterAddingVertexOutsideGraphBoundary(int pintVertexId, int pintProjectId, 
            int pintGraphId, 
            int pintTimeFrameIndex,
            Double x,
            Double y,
            String verteXLabelAttr,
            String vertexTooltipAttr){
        // it takes location from mouse click coordinates in UI
        String [] arrstrLabelTooltips = 
                GraphAPI.getVertexLabelTooltips(pintProjectId, 
                        pintGraphId, 
                        pintTimeFrameIndex,
                        pintVertexId,
                        verteXLabelAttr, 
                        vertexTooltipAttr);
        
        // Create a new UIVertex
        DoubleProperty dblXProp = new SimpleDoubleProperty(
                x);
        DoubleProperty dblYProp = new SimpleDoubleProperty(
                y);
        
        String vColorString = GraphAPI.getVertexColor(pintProjectId, pintGraphId, pintTimeFrameIndex, pintVertexId);
        Color vColor;
            
        if(vColorString!=null)
            vColor = Color.valueOf(vColorString);
        else
            vColor = Color.valueOf(SceneConfig.VERTEX_COLOR_DEFAULT);
        
        String strIconURL = GraphAPI.getVertexIconURL(pintProjectId, pintGraphId, pintTimeFrameIndex, pintVertexId);


        UIVertex vtxCurrent = new UIVertex(this, 
                                        pintVertexId, 
                                        x, 
                                        y, 
                                        dblXProp, 
                                        dblYProp, 
                                        arrstrLabelTooltips[0], 
                                        arrstrLabelTooltips[1],
                                        vColor,
                                        strIconURL);
        
        
        // Add the vertex to the list of UI Vertices
        mapVertices.put(pintVertexId, vtxCurrent);
        mapVertexEdges.put(vtxCurrent, new HashSet<UIEdge>());

        // Add the Vertex Holder to the graph canvas
        this.getChildren().add(vtxCurrent.getVertexHolder().getNode());
        this.getChildren().add(vtxCurrent.getVertexHolder().getLabelHolder()) ;

        vtxCurrent.getVertexHolder().selectVertex();
    
    }
    
    /**
     *  Updates the Canvas after adding edge
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param pintEdgeId
     * @param pintSourceVertexId
     * @param pintDestinationVertexId 
     */
    public void updateCanvasAfterAddEdge(
            int pintProjectId, 
            int pintGraphId, 
            int pintTimeFrameIndex, 
            int pintEdgeId, 
            int pintSourceVertexId, 
            int pintDestinationVertexId) {
            
            ShapeCentre ctrSource = new ShapeCentre(mapVertices.get(pintSourceVertexId).getVertexHolder());
            ShapeCentre ctrDestination = new ShapeCentre(mapVertices.get(pintDestinationVertexId).getVertexHolder());
            
            String eEdgeColor = GraphAPI.getEdgeColor(pintProjectId, pintGraphId, pintTimeFrameIndex, pintEdgeId);
            Color eColor;
            if(eEdgeColor!=null) {
                eColor = Color.valueOf(eEdgeColor);
            } else {
                eColor = Color.valueOf(SceneConfig.EDGE_COLOR_PRIMARYCOLOR);
            }
            
            Boolean blnIsEdgePredicted = GraphAPI.getIsEdgePredicted(pintProjectId, pintGraphId, pintTimeFrameIndex, pintEdgeId);
            if (blnIsEdgePredicted == null) {
                blnIsEdgePredicted = SceneConfig.EDGE_PREDICTED_DEFAULT ;
                GraphAPI.updateEdgeAttribute_Predicted(pintProjectId, pintGraphId, pintTimeFrameIndex, pintEdgeId, blnIsEdgePredicted);
            }
            
            Boolean blnIsEdgeDirected = GraphAPI.getIsEdgeDirected(pintProjectId, pintGraphId, pintTimeFrameIndex, pintEdgeId);
            if (blnIsEdgeDirected == null) {
                blnIsEdgeDirected = SceneConfig.EDGE_DIRECTED_DEAFULT ;
                GraphAPI.updateEdgeAttribute_Directed(pintProjectId, pintGraphId, pintTimeFrameIndex, pintEdgeId, blnIsEdgeDirected);
            }
            blnIsEdgeDirected = false ;
            
            UIEdge edgeCurrent = new UIEdge(
                      pintEdgeId
                    , pintSourceVertexId
                    , pintDestinationVertexId
                    , blnIsEdgeDirected
                    , blnIsEdgePredicted
                    , ctrSource.centerXProperty()
                    , ctrSource.centerYProperty()
                    , ctrDestination.centerXProperty()
                    , ctrDestination.centerYProperty()
                    , this
                    , eColor
            );

            if(mapVertexEdges.containsKey(mapVertices.get(pintSourceVertexId)))
                mapVertexEdges.get(mapVertices.get(pintSourceVertexId)).add(edgeCurrent);
            
            if(mapVertexEdges.containsKey(mapVertices.get(pintDestinationVertexId)))
                mapVertexEdges.get(mapVertices.get(pintDestinationVertexId)).add(edgeCurrent);

            mapEdges.put(pintEdgeId, edgeCurrent);
            
            this.getChildren().add(mapEdges.get(pintEdgeId).getEdgeShape().getShapeNode());
            edgeCurrent.getEdgeShape().getShapeNode().toBack();
            
            // System.out.println("GraphCanvas Edge is added between " + pintSourceVertexId + " and " + pintDestinationVertexId);
    
    }
    
    /**
     * Updates the Graph Canvas after the Shortest Path has been computed
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param pintEdgeId 
     * 
     * @author Talat
     * @since 2018-05-17
     */
    public void updateCanvasAfterShortestPath(
            int pintProjectId, 
            int pintGraphId, 
            int pintTimeFrameIndex, 
            int pintEdgeId) {
        if (mapEdges.containsKey(pintEdgeId)){
            mapEdges.get(pintEdgeId).changeToShortestPathThickness();
        }
    }
    
    
    /**
     * Updates the Graph Canvas after Clearing any existing Shortest Path
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param pintEdgeId 
     * 
     * @author Talat
     * @since 2018-05-25
     */
    public void updateCanvasAfterClearingShortestPath(
            int pintProjectId, 
            int pintGraphId, 
            int pintTimeFrameIndex, 
            int pintEdgeId) {
        if (mapEdges.containsKey(pintEdgeId)){
            mapEdges.get(pintEdgeId).changeToRegularEdgeThickness();
        }
    }

    /**
     *  Method Name     : checkVertexSysColorPresent()
     *  Created Date    : 2018-01-25
     *  Description     : checks if the SYS:COLOR attribute is present for a given set of vertices
     *  Version         : 1.0
     *  @author         : sankalp
     *  @param          : List<Integer> 
     * 
    */
    private Map<Integer, Boolean> checkVertexSysColorPresent(List<Integer> lstVertices) {

        Map<Integer, Boolean> checkSysColorAttribute = GraphAPI.checkVertexSysAttributePresent(
                                                                    this.intProjectId, this.intGraphId, 
                                                                    GraphAPI.getMeerkatSystemColorAttribute(), lstVertices);
        
        return checkSysColorAttribute;
    }
    
    /**
     *  Method Name     : checkEdgeSysColorPresent()
     *  Created Date    : 2018-01-25
     *  Description     : checks if the SYS:COLOR attribute is present for a given set of edges
     *  Version         : 1.0
     *  @author         : sankalp
     *  @param          : List<Integer> 
     * 
    */

    private Map<Integer, Boolean> checkEdgeSysColorPresent(List<Integer> edgeIDs) {
        
        Map<Integer, Boolean> checkSysColorAttribute = GraphAPI.checkEdgeSysAttributePresent(
                                                                    this.intProjectId, this.intGraphId, 
                                                                    GraphAPI.getMeerkatSystemColorAttribute(), edgeIDs);
        
        return checkSysColorAttribute;
    }
    
}
