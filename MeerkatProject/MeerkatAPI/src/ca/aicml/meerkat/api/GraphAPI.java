/*
 * meerkat@aicml june 2015
 */
package ca.aicml.meerkat.api;

import algorithm.graph.GraphUtil;
import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.graph.impl.DynamicGraph;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import main.meerkat.MeerkatBIZ;

/**
 * Class Name : GraphAPI Created Date : Description : API between the UI and the
 * Logic Layer for Graph related elements Version : 1.0
 *
 * @author : Afra Abnar
 *
 * EDIT HISTORY (most recent at the top) Date Author Description 2015-10-26
 * Talat Adding get2DPoistions() & getEdges() 2015-09-01 Talat Implementations
 * for the following methods 1) getVertexCount()
 * 
*/
public class GraphAPI {

    
    public static int addNewGraph(int pintProjectId) {
        
        int intNewGraphId = -1 ;
        try {
            
        } catch (Exception ex) {
            System.out.println("GraphAPI.addNewGraph(): EXCEPTION") ;
            ex.printStackTrace();
        }
        return intNewGraphId ;
    }
    
    public static void addGraphElements(int pintProjectId, int pintGraphId) {
        try {
            
        } catch (Exception ex) {
            System.out.println("GraphAPI.addGraphElements(): EXCEPTION") ;
            ex.printStackTrace();
        }
    }
    
    /**
     *  Method Name     : getTimeFrameCount()
     *  Created Date    : 2016-04-13
     *  Description     : Retrieves the number of time frames available in a given graph
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectId : int
     *  @param pintGraphId : int
     *  @return String [] - All the Time Frames names
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static String [] getTimeFrameNames(int pintProjectId, int pintGraphId) {
        
        int intTotalTimeFrames = -1;
        String [] arrstrTimeFrameNames = null ;
        try {            
            MeerkatBIZ mktBizInstance = MeerkatBIZ.getMeerkatApplication();
            IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph = mktBizInstance.getProject(pintProjectId).getGraph(pintGraphId);
            intTotalTimeFrames = dynaGraph.getAllTimeFrames().size();
            
            System.out.println("GraphAPI.getTimeFrameNames(): intTotalTimeFrames = " + intTotalTimeFrames) ;
            
            arrstrTimeFrameNames = new String[intTotalTimeFrames];
            int intTimeFrameIndex = 0;
            for(TimeFrame tf : dynaGraph.getAllTimeFrames()){
                arrstrTimeFrameNames[intTimeFrameIndex++] = tf.getTimeFrameName();
            }
            /* this does not work if a graph has time frames such as {2,3,5} i.e. not continuous from 0 to n
            for (int intTimeFrameIndex=0; intTimeFrameIndex < intTotalTimeFrames; intTimeFrameIndex++) {
                TimeFrame tf = dynaGraph.getAllTimeFrames().get(intTimeFrameIndex);
                arrstrTimeFrameNames[intTimeFrameIndex] = tf.getTimeFrameName();
            }
            */
        } catch (Exception ex) {
            System.out.println("GraphAPI.getTimeFrameCount(): EXCEPTION") ;
            ex.printStackTrace();
        }
        return arrstrTimeFrameNames ;
    }
    
    
    /**
     *  Method Name : getVertexCount ()
     *  Created Date : 2015-09-01 
     *  Description : Returns the number of Vertices (Nodes) that are there in a graph 
     *  Version : 1.0
     *
     *  @author : Talat
     *
     *  @param pintProjectId : int
     *  @param pintGraphId : int
     *  @param pintTimeFrameIndex : int
     *  @return int
     *
     *  EDIT HISTORY (most recent at the top) 
     *  Date         Author          Description
     *  2016-03-11   Talat           Added parameter pintTimeFrameIndex and try catch block 
     *
     */
    public static int getVertexCount(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        
        int intVertexCount = 0;
        
        try {        
            
            MeerkatBIZ mktBizInstance = MeerkatBIZ.getMeerkatApplication();
            IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph = mktBizInstance.getProject(pintProjectId).getGraph(pintGraphId);
            TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
            intVertexCount = dynaGraph.getGraph(tf).getVertexCount();
            return intVertexCount;
        } catch (Exception ex) {
            intVertexCount = -1 ;
            System.out.println("GraphAPI.getVertexCount(): EXCEPTION - returning "+intVertexCount);
            return intVertexCount ;
        }
    }

    /**
     * Method Name : getEdgeCount Created Date : 2015-09-01 Description :
     * Returns the number of Edges (Links) that are there in a graph Version :
     * 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pintTimeFrameIndex : int
     * @return int
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     *
     */
    public static int getEdgeCount(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        int intEdgeCount = -1;
        MeerkatBIZ mktBizInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph
                = mktBizInstance.getProject(pintProjectId).getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        intEdgeCount = dynaGraph.getGraph(tf).getEdgeCount();
        return intEdgeCount;
    }

    /**
     * Method Name : getAllVertexIds Created Date : 2015-09-01 Description :
     * Returns the list of all the Vertex Ids of Vertices (Nodes) in a graph
     * Version : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pintTimeFrameIndex : int
     * @return List<Integer>
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     *
     */
    public static List<Integer> getAllVertexIds(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {

        MeerkatBIZ mktappBusiness = MeerkatBIZ.getMeerkatApplication();
        List<Integer> lstReturnValue = new ArrayList<>();
        IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph
                = mktappBusiness.getProject(pintProjectId).getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        lstReturnValue.addAll(dynaGraph.getGraph(tf).getAllVertexIds());

        return lstReturnValue;
    }
    
    /**
     * Method Name : getAllVertexIds Created Date : 2015-09-01 Description :
     * Returns the list of all the Vertex Ids of Vertices (Nodes) in a graph
     * Version : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @return List<Integer>
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     *
     */
    public static List<Integer> getAllVertexIds(int pintProjectId, int pintGraphId) {

        MeerkatBIZ mktappBusiness = MeerkatBIZ.getMeerkatApplication();
        List<Integer> lstReturnValue = new ArrayList<>();
        IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph
                = mktappBusiness.getProject(pintProjectId).getGraph(pintGraphId);
        return dynaGraph.getAllVertexIds();
    }

    /**
     * Method Name : getAllEdgeIds Created Date : 2015-09-01 Description :
     * Returns the list of EdgeIds of edges in a graph Version : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @return List<Integer>
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     *
     */
    public static List<Integer> getAllEdgeIds(int pintProjectId,
            int pintGraphId, 
            int pintTimeFrameIndex) {

        List<Integer> setVertexIds = new ArrayList<>();

        MeerkatBIZ mktBizInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph
                = mktBizInstance.getProject(pintProjectId).getGraph(pintGraphId);

        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        setVertexIds.addAll(dynaGraph.getGraph(tf).getAllEdgeIds());

        return setVertexIds;
    }

    /**
     * Method Name : getAllEdgesAsNodeIds Created Date : 2015-09-01 Description
     * : Returns a list of Strings - each String containing a pair of NodeIds in
     * an edge Version : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pintTimeFrameIndex : int
     * @param pstrDelimiter : String
     * @return List<String>
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     *
     */
    public static List<String> getAllEdgesAsNodeIds(int pintProjectId,
            int pintGraphId,
            int pintTimeFrameIndex,
            String pstrDelimiter) {

        /* NOTE: Each String in the list returned is a comma separated value 
         with a comma between the source and the destination */
        MeerkatBIZ mktappBusiness = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = 
                mktappBusiness.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        List<IEdge<IVertex>> lstEdgeAsNodes = dynaGraph.getEdges(tf);

        List<String> lstEdgesAsStrings = new ArrayList<>();
        
        for (IEdge<IVertex> e : lstEdgeAsNodes) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.getId())
                    .append(pstrDelimiter)
                    .append(e.getSource())
                    .append(pstrDelimiter)
                    .append(e.getDestination());
            
            lstEdgesAsStrings.add(sb.toString());
        }

        return lstEdgesAsStrings;
    }
    
    /**
     * Method Name : getAllEdgesAsNodeIds Created Date : 2015-09-01 Description
     * : Returns a list of Strings - each String containing a pair of NodeIds in
     * an edge Version : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pstrDelimiter : String
     * @return int [][]
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     *
     */
    public static int [][] getAllEdgesAsNodeIds(int pintProjectId,
            int pintGraphId,
            String pstrDelimiter) {

        /* NOTE: Each String in the list returned is a comma separated value 
         with a comma between the source and the destination */
        MeerkatBIZ mktappBusiness = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = 
                mktappBusiness.getProject(pintProjectId).getGraph(pintGraphId);
        
        List<IEdge<IVertex>> lstEdges = dynaGraph.getAllEdges() ;
        
        int [][] arrstrEdges = new int [lstEdges.size()][3] ; // Source at 0th index and destination at the 1st index
        
        int intEdgeIndex = 0;
        for (IEdge<IVertex> e : lstEdges) {
            arrstrEdges[intEdgeIndex][0] = e.getId();
            arrstrEdges[intEdgeIndex][1] = e.getSource().getId() ;
            arrstrEdges[intEdgeIndex][2] = e.getDestination().getId() ;
        }

        return arrstrEdges;
    }

    /**
     * #TOBEREMOVED Method Name : getOutputFile() Created Date : 2015-07-24
     * Description : Get the current output file for a graph Version : 1.0
     *
     * @author : Talat
     *
     * @param pintGraphId: int
     * @return String - output file path that is written and displayed on the
     * screen
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     *
     */
    public static String getOutputFile(int pintGraphId) {
        String strOutputFilePath = new String();
        // TODO
        return strOutputFilePath;
    }

    /**
     * Method Name : saveGraph Created Date : 2015-09-10 Description : Given a
     * graphId, saves the graph Version : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pstrGraphFilePath : String - Directory in which the graph is to be
     * saved
     * @return boolean - returns true if the save was successful
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     *
     */
    public static boolean saveGraph(int pintProjectId, int pintGraphId, String pstrGraphFilePath) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        boolean blnSuccess = false;
        IDynamicGraph<IVertex,IEdge<IVertex>> graphCurrent = BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        //String blnSuccess = MeerkatWriter.write(graphCurrent, pstrGraphFilePath);
        return blnSuccess;
    }

    /**
     * Method Name : getMinMaxVertexDegree() Created Date : 2015-09-22
     * Description : Returns the Minimum and the Maximum available Vertex
     * degrees of a Graph (Index 0 = minimum value; Index 1 = maximum value)
     * Version : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @return Integer[]
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     *
     */
    public static Integer[] getMinMaxVertexDegree(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = 
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        int minDegree = Integer.MAX_VALUE;
        int maxDegree = 0;
        
        for (IVertex v : dynaGraph.getVertices(tf)) {
            int vDegree = dynaGraph.getDegree(v, tf);
            if (vDegree <= minDegree) {
                minDegree = vDegree;
            } else {
                if (vDegree > maxDegree ) {
                    maxDegree = vDegree;
                }
            }
        } 
        
        Integer[] minmax = new Integer[2];
        minmax[0] = minDegree;
        minmax[1] = maxDegree;
        return minmax;
    }

    /**
     * Method Name : getVertexAttributeNamesWithType() Created Date : 2015-09-22
     * Description : Returns a map of all Attribute Names of a Vertex and its
     * type (true for numeric and false for non-numeric) Version : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pintTimeFrameIndex : int
     * @return Map<String, Boolean>
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     *
     */
    public static Map<String, Boolean> getVertexAttributeNamesWithType (
            int pintProjectId
            , int pintGraphId
            , int pintTimeFrameIndex
    ) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        return dynaGraph.getGraph(tf).getVertexAttributeNamesWithType();
    }
    
        /**
     *  Method Name     : removeVertices()
     *  Created Date    : 2017-05-31
     *  Description     : removes the list of vertices for a given time frame.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pintProjectId : int 
     *  @param pintGraphId : int
     *  @param pintTimeFrameIndex : int
     *  @param lstVertexIds : List<Integer>
     *  
     * 
    */
    public static void removeVertices(
              int pintProjectId
            , int pintGraphId
            , int pintTimeFrameIndex
            , List<Integer> lstVertexIds) {

        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        List<TimeFrame> lstTF = new ArrayList<>();
        lstTF.add(tf);

        for (IVertex v : dynaGraph.getVertices(tf)) {
            if (lstVertexIds.contains(v.getId())) {
                dynaGraph.removeVertex(v,lstTF);
            }
        }
    }
    
         
    /**
     *  Method Name     : removeEdges()
     *  Created Date    : 2017-05-31
     *  Description     : removes the list of edges corresponding to the list of vertices for a given time frame.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pintProjectId : int 
     *  @param pintGraphId : int
     *  @param pintTimeFrameIndex : int
     *  @param lstVertexIds : List<Integer>
     *  
     * 
    */
    public static void removeEdges(
              int pintProjectId
            , int pintGraphId
            , int pintTimeFrameIndex
            , List<Integer> lstVertexIds) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        for (IVertex v : dynaGraph.getVertices(tf)) {
            if (lstVertexIds.contains(v.getId())) {
                for(IEdge e : dynaGraph.getEdges(v, tf)){
                    dynaGraph.removeEdge(e, tf);
                }
            }
        }  
    }
    
    /**
     *  Method Name     : removeEdges()
     *  Created Date    : 2017-06-11
     *  Description     : removes the list of edges for a given time frame.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pintProjectId : int 
     *  @param pintGraphId : int
     *  @param pintTimeFrameIndex : int
     *  @param lstEdges : Set<Integer>
     * 
    */
    public static void removeEdges(
              int pintProjectId
            , int pintGraphId
            , int pintTimeFrameIndex
            , Set<Integer> lstEdges) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        for (IEdge e : dynaGraph.getEdges(tf)) {
            if (lstEdges.contains(e.getId()))
                dynaGraph.removeEdge(e, tf);
        }
    }
    
    public static Set<String> getAllUserAttributesNames(int pintProjectId, 
            int pintGraphId,
            int pintTimeFrameIndex) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        Set<String> setUserAttributeNames = new HashSet<>();

        for (IVertex v : dynaGraph.getVertices(tf)) {
            setUserAttributeNames.addAll(v.getUserAttributer().getAttributeNames());
        }
         
        return setUserAttributeNames;
    }

    /**
     * Method Name : getEdgeAttributeNamesWithType() Created Date : 2015-09-22
     * Description : Returns a map of all Attribute Names of an Edge and its
     * type (true for numeric and false for non-numeric) Version : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @return Map<String, Boolean>
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     *
     */
    public static Map<String, Boolean> getEdgeAttributeNamesWithType
        (int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = 
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        // #Debug
        // BIZInstance.Debug_ListProject();
        // #EndDebug
                
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        return dynaGraph.getGraph(tf).getEdgeAttributeNamesWithType();
    }

    /**
     * Method Name : getMetric Created Date : 2015-09-21 Description : 
     * Gets a specific Metric values for all Vertices Version : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pstrReader : String
     * @return Map<Integer, Double>
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     *
     */
        
    // What does this method suppose to do?
//    public static Map<Integer, Double> getMetric(int pintProjectId, 
//            int pintGraphId, 
//            int pintTimeFrameIndex,
//            String pstrReader) {
//        
//        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
//        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = 
//                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
//        
//        
//
//        // Remove this part
//        return (new HashMap<>());
//    }

    /**
     * Method Name : get2DPoistions() Created Date : 2015-09-21 Description :
     * Returns the 2D positions of the Vertices Version : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pintTimeFrameIndex : int
     * @param pdblCanvasWidth : double
     * @param pdblCanvasHeight : double
     * @return Point2D []
     *
     * EDIT HISTORY (most recent at the top) 
     * Date         Author      Description 
     * 2016-03-31   Talat       Making the normalization using the minimum of the height and width
     * 2016-02-26   Talat       Adding the de-Normalization part to convert the positions of vertices to normalized
     * 2016-02-25   Talat       Converting the Point2D array list to 2D array (0th index will have the x and 1st index will have the y)
     * 2016-01-19   Talat       Changed the name from get2DPositions to getVertex2DPositions
     * 2016-01-19   Talat       Changed the return value from a Map of Point2D to an array of Point2D
     *
     */
        
        // Change it to double[][] with 0 = Id, 1 = X, 2 = Y; asap
    public static Map<Integer, Double[]> getVertex2DPoistions(
            int pintProjectId, 
            int pintGraphId,
            int pintTimeFrameIndex,
            double pdblCanvasWidth,
            double pdblCanvasHeight) {
        //System.out.println("GraphAPI.getVertex2Dpositions: " + pdblCanvasWidth + ", " + pdblCanvasHeight);
        double dblMinLength = pdblCanvasHeight > pdblCanvasWidth ? pdblCanvasWidth : pdblCanvasHeight ;
        double dblMaxLength = pdblCanvasHeight <= pdblCanvasWidth ? pdblCanvasWidth : pdblCanvasHeight ;
        
        // To accommodate the size of the vertices
        dblMaxLength -= 15 ;
        dblMinLength -= 15 ;
        
        double dblXPadding = 0.0 ;
        double dblYPadding = 0.0 ;
        
        if (pdblCanvasHeight > pdblCanvasWidth) {
            dblYPadding = (dblMaxLength - dblMinLength) / 2 ;
        } else {
            dblXPadding = (dblMaxLength - dblMinLength) / 2 ;
        }
        
        
        Map<Integer, Double[]> mapVertex2dPositions = new HashMap<>();

        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();      
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        TimeFrame tfTimeFrame = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        // double [][] arrarrdbl2DPositions = new double[dynaGraph.getVertexCount()][3];
        System.out.println("\t\t\t\t\tGraphAPI().getVertex2DPoistions : " + dynaGraph.getVertices(tfTimeFrame).size() + " in timeFrame = " + tfTimeFrame.getTimeFrameName());
        for (IVertex v : dynaGraph.getVertices(tfTimeFrame)) {            
           
            // arrarrdbl2DPositions[v.getId()][0] = Double.parseDouble(v.getSystemAttributer().getAttributeValue(MeerkatSystem.X, tfTimeFrame)) * dblMinLength;
            // arrarrdbl2DPositions[v.getId()][1] = Double.parseDouble(v.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, tfTimeFrame)) * dblMinLength;
            //System.out.println("\t\t\t\t GraphAPI().getVertex2DPoistions all Ids: " + v.getId());
            Double [] arrdblPositions = new Double[2]; // 0th Index stores X and 1st Index stores Y
            if(v.getSystemAttributer().getAttributeValue(MeerkatSystem.X, tfTimeFrame)!=null && v.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, tfTimeFrame)!=null){
                arrdblPositions[0] = Double.parseDouble(v.getSystemAttributer().getAttributeValue(MeerkatSystem.X, tfTimeFrame)) * dblMinLength + dblXPadding;
                arrdblPositions[1] = Double.parseDouble(v.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, tfTimeFrame)) * dblMinLength + dblYPadding;
                //System.out.println(v.getId() + ", " + v.getSystemAttributer().getAttributeValue(MeerkatSystem.X, tfTimeFrame) + ", "+ v.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, tfTimeFrame) );
            }else{
                //for cases where SYS:X and SYS:Y are not present for a few vertices in the graph file
                arrdblPositions[0] = Double.parseDouble(String.valueOf(Math.random())) * dblMinLength + dblXPadding;
                arrdblPositions[1] = Double.parseDouble(String.valueOf(Math.random())) * dblMinLength + dblYPadding;
            }
            mapVertex2dPositions.put(v.getId(), arrdblPositions) ;
        }
        return mapVertex2dPositions ;
    }

    /**
     * Method Name : getVertexLabels() Created Date : 2016-01-27 Description :
     * Returns the Labels of all the vertices Version : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pintTimeFrameIndex : int
     * @param pstrVertexAttrName
     * @return String []
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     */
    public static String[] getVertexLabels(int pintProjectId, 
            int pintGraphId,
            int pintTimeFrameIndex,
            String pstrVertexAttrName) {

        return getVertexAttributeValues(pintProjectId, pintGraphId, pintTimeFrameIndex, pstrVertexAttrName);
    }

    /**
     * Method Name : getVertexTooltips() Created Date : 2016-01-27 Description :
     * Returns the Tooltips of all the vertices Version : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pintTimeFrameIndex : int
     * @param pstrVertexAttrName : String
     * @return String []
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     */
    public static String[] getVertexTooltips(int pintProjectId, 
            int pintGraphId,
            int pintTimeFrameIndex,
            String pstrVertexAttrName) {

        return getVertexAttributeValues(pintProjectId, pintGraphId, pintTimeFrameIndex, pstrVertexAttrName);
    }

    
    public static String[] getVertexAttributeValues(int pintProjectId, 
            int pintGraphId,
            int pintTimeFrameIndex,
            String pstrVAttName) {

        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);

        String[] arrstrLabels = new String[dynaGraph.getGraph(tf).getVertexCount()];

        int i = 0;
        for (IVertex v : dynaGraph.getVertices(tf)) {
            if (v.getUserAttributer().getAttributeNames().contains(pstrVAttName)) {
                arrstrLabels[i] = v.getUserAttributer().getAttributeValue(pstrVAttName, tf);
            } else if (v.getSystemAttributer().getAttributeNames().contains(pstrVAttName)) {
                arrstrLabels[i] = v.getSystemAttributer().getAttributeValue(pstrVAttName, tf);
            } else {
                arrstrLabels[i] = "";
            }
            i++;
        }

        return arrstrLabels;
    }
    
    
    /**
     * Method Name : getallVertices() Created Date : 2017-03-10 Description :
     * Returns a map of all vertices along with their attribute_names and
     * attribute_values.
     * e.g : 1 --> ["name" : "vertex1", "class" : "level1"]
     *
     * @author : Sankalp
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pintTimeFrameIndex : int
     * @return Map<Integer, Map<String,String>>
     *
     *
     */
    public static Map<Integer, Map<String,String>> getallVertices(int pintProjectId, 
            int pintGraphId,
            int pintTimeFrameIndex) {

        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        Map<Integer, Map<String,String>> mapVertices = new HashMap<>();

        for (IVertex v : dynaGraph.getVertices(tf)) {
            Integer vId = v.getId();
            Map<String,String> tempMap = new HashMap<>();
            Set<String> attributes = new HashSet<>();
            attributes.addAll(v.getUserAttributer().getAttributeNames());
            attributes.addAll(v.getSystemAttributer().getAttributeNames());
            
            for(String s : attributes) {
                tempMap.put("Id",vId.toString());
                if (v.getUserAttributer().getAttributeNames().contains(s))
                    tempMap.put(s,v.getUserAttributer().getAttributeValue(s, tf));
                else if (v.getSystemAttributer().getAttributeNames().contains(s))
                    tempMap.put(s,v.getSystemAttributer().getAttributeValue(s,tf));
                else
                    tempMap.put(s,"");
            }
            mapVertices.put(vId,tempMap);
        }
        return mapVertices;
    }
    
    
    /**
     *  Method Name     : getVertexAttributeValues()
     *  Created Date    : 2016-05-10
     *  Description     : Returns the values of the specific attribute for all the Ids sent as parameter
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectId : int 
     *  @param pintGraphId : int
     *  @param pintTimeFrameIndex : int
     *  @param pstrVAttName : String
     *  @param lstVertexIds : List<Integer>
     *  @return Map<Integer, String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static Map<Integer, String> getVertexAttributeValues(
              int pintProjectId
            , int pintGraphId
            , int pintTimeFrameIndex
            , String pstrVAttName
            , List<Integer> lstVertexIds) {

        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);

        Map<Integer, String> mapIdAttrValue = new HashMap<>();

        int i = 0;
        for (IVertex v : dynaGraph.getVertices(tf)) {
            if (lstVertexIds.contains(v.getId())) {
                if (v.getUserAttributer().getAttributeNames().contains(pstrVAttName)) {
                    mapIdAttrValue.put(v.getId(), v.getUserAttributer().getAttributeValue(pstrVAttName, tf));
                } else if (v.getSystemAttributer().getAttributeNames().contains(pstrVAttName)) {
                    mapIdAttrValue.put(v.getId(), v.getSystemAttributer().getAttributeValue(pstrVAttName, tf));
                } else {
                    mapIdAttrValue.put(v.getId(), "");
                }
            }
            i++;
        }

        return mapIdAttrValue;
    }
    
    /**
     * Method Name : getEdgeAttributeValues() 
     * Created Date : 2017-06-09
     * Description : Returns a map of all Edge Ids along with their attribute values
     * for a given attribute name.
     *
     * @author : sankalp
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pintTimeFrameIndex
     * @param pstrVAttName
     * @param lstEdgeIds
     * @return Map<String, Boolean>
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     *
     */
     public static Map<Integer, String> getEdgeAttributeValues(
              int pintProjectId
            , int pintGraphId
            , int pintTimeFrameIndex
            , String pstrVAttName
            , List<Integer> lstEdgeIds) {

        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);

        Map<Integer, String> mapIdAttrValue = new HashMap<>();

        int i = 0;
        for (IEdge e : dynaGraph.getEdges(tf)) {
            if (lstEdgeIds.contains(e.getId())) {
                if (e.getUserAttributer().getAttributeNames().contains(pstrVAttName)) {
                    mapIdAttrValue.put(e.getId(), e.getUserAttributer().getAttributeValue(pstrVAttName, tf));
                } else if (e.getSystemAttributer().getAttributeNames().contains(pstrVAttName)) {
                    mapIdAttrValue.put(e.getId(), e.getSystemAttributer().getAttributeValue(pstrVAttName, tf));
                } else {
                    mapIdAttrValue.put(e.getId(), "");
                }
            }
            i++;
        }

        return mapIdAttrValue;
    }

    /**
     * Method Name : getVertexLabelsTooltips() Created Date : 2016-01-27
     * Description : Returns the Labels and Tooltips of all the vertices Version
     * : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @return String [][]
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     */
    public static Map<Integer, String[]> getVertexLabelsTooltips(int pintProjectId, 
            int pintGraphId,
            int pintTimeFrameIndex,
            String pstrVAttLabelName,
            String pstrVAttTooltipName) {

        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);

        Map<Integer, String[]> mapstrLabelsTooltips = new HashMap<>();

        
        for (IVertex v : dynaGraph.getVertices(tf)) {
            
            String [] arrstrLabelsTooltips = new String[2];
            
            if (v.getUserAttributer().getAttributeNames().contains(pstrVAttLabelName)) {
                arrstrLabelsTooltips[0] = v.getUserAttributer().getAttributeValue(pstrVAttLabelName, tf);
            } else if (v.getSystemAttributer().getAttributeNames().contains(pstrVAttLabelName)) {
                arrstrLabelsTooltips[0] = v.getSystemAttributer().getAttributeValue(pstrVAttLabelName, tf);
            } else {
                arrstrLabelsTooltips[0] = "";
            }
            
            if (v.getUserAttributer().getAttributeNames().contains(pstrVAttTooltipName)) {
                arrstrLabelsTooltips[1] = v.getUserAttributer().getAttributeValue(pstrVAttTooltipName, tf);
            } else if (v.getSystemAttributer().getAttributeNames().contains(pstrVAttTooltipName)) {
                arrstrLabelsTooltips[1] = v.getSystemAttributer().getAttributeValue(pstrVAttTooltipName, tf);
            } else {
                arrstrLabelsTooltips[1] = "";
            }
            
            mapstrLabelsTooltips.put(v.getId(), arrstrLabelsTooltips) ;
        }

        return mapstrLabelsTooltips;
    }
    
    
    
    
    
    

    /**
     * Method Name : getEdges() Created Date : 2015-09-21 Description : Returns
     * the list of Edges with source and destinations Version : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pintTimeFrameIndex : int
     * @return int[][]: each row is representing one edge (sourdeId, destinationId, directed(1)/undirected(0))
     *
     * EDIT HISTORY (most recent at the top) Date Author Description 2016-01-19
     * Talat Changed the return value from a Map of Integer Array to an array of
     * Point2D
     *
     */
    public static Map<Integer, Integer[]> getEdges(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        //int[][] edges = new int [dynaGraph.getGraph(tf).getEdgeCount()][3];
        int noOfEdges = dynaGraph.getGraph(tf).getEdgeCount();
        Map<Integer, Integer[]> mapEdges = new HashMap<>( (int) Math.ceil(noOfEdges/ 0.75) );
        /**
         * Efficient map initialization if size is known.
         * http://blog.scottlogic.com/2012/08/14/efficient-map-initialization-in-java.html
         * 
         * int initSize = (int) Math.ceil(listOfCustomObjs.size() / 0.75);
           Map<String, CustomObj> objectById = new HashMap<String, CustomObj>(initSize);
         */
        
        
        for (IEdge e : dynaGraph.getEdges(tf)) {
            Integer[] arrEdgeInfo = new Integer[3];
            int sourceId = e.getSource().getId();
            int destinationId = e.getDestination().getId();
            boolean directed = e.isDirected();
            arrEdgeInfo[0] = sourceId;
            arrEdgeInfo[1] = destinationId;
            arrEdgeInfo[2] = (directed ? 1 : 0);
            
            mapEdges.put(e.getId(), arrEdgeInfo);
        }

        return mapEdges;
    }
    public static void addVertex(int pintProjectId, int pintGraphId,double pdblX, double pdblY){
        //TODO
    }
    public static int addVertexMultipleTimeFrames(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, List<Integer> plistTimeFrames,double pdblX, double pdblY){
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        List<TimeFrame> listTimeFrames = new ArrayList<>();
        for(int timeFrameIndex : plistTimeFrames){
            TimeFrame tf = dynaGraph.getAllTimeFrames().get(timeFrameIndex);
            listTimeFrames.add(tf);
        }
        
        
        
        
        int intVNewId = GraphUtil.createAndAddNewVertexMultipleTimeFrames(dynaGraph ,listTimeFrames, pdblX, pdblY);
        return intVNewId;
    }
    public static int addVertex(int pintProjectId, int pintGraphId, int pintTimeFrameIndex,double pdblX, double pdblY){
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        
        int intVNewId = GraphUtil.createAndAddNewVertex(dynaGraph ,tf, pdblX, pdblY);
        return intVNewId;
    }
    
    public static Double [] getVertex2DPosition(int pintProjectId, 
            int pintGraphId,
            int pintTimeFrameIndex,
            int pintVertexId,
            double pdblCanvasWidth,
            double pdblCanvasHeight){
        
        double dblMinLength = pdblCanvasHeight > pdblCanvasWidth ? pdblCanvasWidth : pdblCanvasHeight ;
        double dblMaxLength = pdblCanvasHeight <= pdblCanvasWidth ? pdblCanvasWidth : pdblCanvasHeight ;
        
        // To accommodate the size of the vertices
        dblMaxLength -= 15 ;
        dblMinLength -= 15 ;
        
        double dblXPadding = 0.0 ;
        double dblYPadding = 0.0 ;
        
        if (pdblCanvasHeight > pdblCanvasWidth) {
            dblYPadding = (dblMaxLength - dblMinLength) / 2 ;
        } else {
            dblXPadding = (dblMaxLength - dblMinLength) / 2 ;
        }
        

        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();      
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        TimeFrame tfTimeFrame = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);

        IVertex v = dynaGraph.getVertex(pintVertexId);           

        Double [] arrdblPositions = new Double[2]; // 0th Index stores X and 1st Index stores Y
        arrdblPositions[0] = Double.parseDouble(v.getSystemAttributer().getAttributeValue(MeerkatSystem.X, tfTimeFrame)) * dblMinLength + dblXPadding;
        arrdblPositions[1] = Double.parseDouble(v.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, tfTimeFrame)) * dblMinLength + dblYPadding;
 
        return arrdblPositions ;
        

    } 
    
    public static Double[] getConvertedPositionFromUIToLogic(double pdblXUI,
            double pdblYUI,
            double pdblCanvasWidth,
            double pdblCanvasHeight){
    
        // check(getVertex2DPosition, getVertex2DPositions() methods)
        // same logic here
        double dblMinLength = pdblCanvasHeight > pdblCanvasWidth ? pdblCanvasWidth : pdblCanvasHeight ;
        double dblMaxLength = pdblCanvasHeight <= pdblCanvasWidth ? pdblCanvasWidth : pdblCanvasHeight ;
        
        // To accommodate the size of the vertices
        dblMaxLength -= 15 ;
        dblMinLength -= 15 ;
        
        double dblXPadding = 0.0 ;
        double dblYPadding = 0.0 ;
        
        if (pdblCanvasHeight > pdblCanvasWidth) {
            dblYPadding = (dblMaxLength - dblMinLength) / 2 ;
        } else {
            dblXPadding = (dblMaxLength - dblMinLength) / 2 ;
        }
        /**
         * X(UI) = X(logic)*canvasSize + padding; padding = 15 
         * 
         */
        Double[] arrPositions = new Double[2];
        
        arrPositions[0] = (pdblXUI - dblXPadding)/dblMinLength;
        
        arrPositions[1] = (pdblYUI - dblYPadding)/dblMinLength;
        
        //System.out.println("GraphAPI.getCovertedPositionFromUIToLogic x,y in logic = " + arrPositions[0] + ", " + arrPositions[1]);
        
        return arrPositions;
    
    }

    public static String[] getVertexLabelTooltips(int pintProjectId, 
            int pintGraphId,
            int pintTimeFrameIndex,
            int pintVertexId,
            String pstrVAttLabelName,
            String pstrVAttTooltipName) {

        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);

        String[] arrstrLabelsTooltips = new String[2];

        
        IVertex v = dynaGraph.getVertex(pintVertexId);      
        
            if (v.getUserAttributer().getAttributeNames().contains(pstrVAttLabelName)) {
                arrstrLabelsTooltips[0] = v.getUserAttributer().getAttributeValue(pstrVAttLabelName, tf);
            } else if (v.getSystemAttributer().getAttributeNames().contains(pstrVAttLabelName)) {
                arrstrLabelsTooltips[0] = v.getSystemAttributer().getAttributeValue(pstrVAttLabelName, tf);
            } else {
                arrstrLabelsTooltips[0] = "";
            }
            
            if (v.getUserAttributer().getAttributeNames().contains(pstrVAttTooltipName)) {
                arrstrLabelsTooltips[1] = v.getUserAttributer().getAttributeValue(pstrVAttTooltipName, tf);
            } else if (v.getSystemAttributer().getAttributeNames().contains(pstrVAttTooltipName)) {
                arrstrLabelsTooltips[1] = v.getSystemAttributer().getAttributeValue(pstrVAttTooltipName, tf);
            } else {
                arrstrLabelsTooltips[1] = "";
            }
            
        

        return arrstrLabelsTooltips;
    }
    

    /**
     * Method Name : addEdge() Created Date : 2017-05-07 Description : Adding a
     * new Edge to a given graph in a Project for the given source and
     * destination node Version : 1.0
     *
     * 
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pintSourceVertexId : int
     * @param pintDestinationVertexId : int
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     *
     */
    public static int addEdge(int pintProjectId, int pintGraphId, int timeFrameIndex,int pintSourceVertexId, int pintDestinationVertexId) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(timeFrameIndex);
        
        return GraphUtil.createAndAddNewEdge(dynaGraph ,tf, pintSourceVertexId, pintDestinationVertexId);
    }
    public static Boolean edgeExists(int pintProjectId, int pintGraphId, int timeFrameIndex,int pintSourceVertexId, int pintDestinationVertexId) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(timeFrameIndex);
        
        return GraphUtil.edgeExists(dynaGraph ,tf, pintSourceVertexId, pintDestinationVertexId);
    }
    
    /**
     * Retrieves a sorted list of System Attributes of a Vertex in a Graph
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @return Sorted List of System Attribute Names
     * @author Talat
     * @since 2018-01-24
     */
    public static List<String> getVertexSystemAttributes_Sorted(int pintProjectId, int pintGraphId, int pintTimeFrameIndex){
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        Set<String> setstrSystemAttributes = new HashSet<>();
        for (IVertex v : dynaGraph.getVertices(tf)) {
            setstrSystemAttributes.addAll(v.getSystemAttributer().getAttributeNames());
        }
        List<String> lststrSystemAttributes = new ArrayList<>();
        lststrSystemAttributes.addAll(setstrSystemAttributes);
        Collections.sort(lststrSystemAttributes, String.CASE_INSENSITIVE_ORDER) ;        
        
        return lststrSystemAttributes;
    }
    
    /**
     * Retrieves a sorted list of User Attributes of a Vertex in a Graph
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @return Sorted List of User Attribute Names
     * @author Talat
     * @since 2018-01-24
     */
    public static List<String> getVertexUserAttributes_Sorted(int pintProjectId, int pintGraphId, int pintTimeFrameIndex){
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
                
        Set<String> setstrUserAttributes = new HashSet<>();
        for (IVertex v : dynaGraph.getVertices(tf)) {
            setstrUserAttributes.addAll(v.getUserAttributer().getAttributeNames());
        }
        List<String> lststrUserAttributes = new ArrayList<>();
        lststrUserAttributes.addAll(setstrUserAttributes);
        Collections.sort(lststrUserAttributes, String.CASE_INSENSITIVE_ORDER) ;        
        
        return lststrUserAttributes;
    }
        
    /**
     * Retrieves a sorted list of System Attributes of an Edge in a Graph
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @return Sorted List of System Attribute Names
     * @author Talat
     * @since 2018-01-24
     */
    public static List<String> getEdgeSystemAttributes_Sorted(int pintProjectId, int pintGraphId, int pintTimeFrameIndex){
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        Set<String> setstrSystemAttributes = new HashSet<>();        
        for (IEdge e : dynaGraph.getEdges(tf)) {
            setstrSystemAttributes.addAll(e.getSystemAttributer().getAttributeNames());
        }
        
        List<String> lststrSystemAttributes = new ArrayList<>();
        lststrSystemAttributes.addAll(setstrSystemAttributes);
        Collections.sort(lststrSystemAttributes, String.CASE_INSENSITIVE_ORDER) ;        
        
        return lststrSystemAttributes;
    }
    
    /**
     * Retrieves a sorted list of User Attributes of an Edge in a Graph
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @return Sorted List of User Attribute Names
     * @author Talat
     * @since 2018-01-24
     */
    public static List<String> getEdgeUserAttributes_Sorted(int pintProjectId, int pintGraphId, int pintTimeFrameIndex){
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        Set<String> setstrUserAttributes = new HashSet<>();
        
        for (IEdge e : dynaGraph.getEdges(tf)) {
            setstrUserAttributes.addAll(e.getUserAttributer().getAttributeNames());
            break;
        }
        List<String> lststrUserAttributes = new ArrayList<>();
        lststrUserAttributes.addAll(setstrUserAttributes);
        Collections.sort(lststrUserAttributes, String.CASE_INSENSITIVE_ORDER) ;        
        
        return lststrUserAttributes;
    }
    
    /**
     * 
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @return 
     * 
     * EDIT HISTORY
     * 
     */
    public static List<String> getAllAttributesNames_Sorted(int pintProjectId, 
            int pintGraphId,
            int pintTimeFrameIndex) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        Set<String> setUserAttr = new HashSet<>();
        Set<String> setSystemAttr = new HashSet<>();

        for (IVertex v : dynaGraph.getVertices(tf)) {
            setUserAttr.addAll(v.getUserAttributer().getAttributeNames());
            setSystemAttr.addAll(v.getSystemAttributer().getAttributeNames());
        }
        
        // User Attributes should be first followed by System Attributes
        List<String> lststrUserAttributes = new ArrayList<>();
        lststrUserAttributes.addAll(setUserAttr);
        Collections.sort(lststrUserAttributes, String.CASE_INSENSITIVE_ORDER);
        
        List<String> lststrSystemAttributes = new ArrayList<>();
        lststrSystemAttributes.addAll(setSystemAttr);
        Collections.sort(lststrSystemAttributes, String.CASE_INSENSITIVE_ORDER);
        
        List<String> lstSortedAttr = new ArrayList<>();
        lstSortedAttr.addAll(lststrUserAttributes);
        lstSortedAttr.addAll(lststrSystemAttributes);
        
        return lstSortedAttr;
    }
    
    

    /**
     * Method Name : getMaxVertexCount() Created Date : 2016-01-19 Description :
     * Returns the maximum number of vertices that can be created for this graph
     * Version : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pintTimeFrameIndex : int
     * @return int
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     *
     */
    public static int getMaxVertexCount(int pintProjectId, 
            int pintGraphId,
            int pintTimeFrameIndex) {

        
        /* Note: since we are using dynamicArrays, there is not constraint
         * coming from logic on maxVertex numbers. If there is a constraint.
         * it is from UI and because of memory (because for UI everything should
         * reside in memory. So we are not sure where to put and how to calculate
         * the return value for this function.
         * 18 Feb, 2016
         */
        
        int intMaxVertexCount = 200;

        // TO DO
        // invoke the logic layer to fetch the maximum vertex count
        return intMaxVertexCount;
    }

    /**
     * Method Name : getMaxEdgeCount() Created Date : 2016-01-19 Description :
     * Returns the maximum number of edges that can be created for this graph
     * Version : 1.0
     *
     * @author : Talat
     *
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pintTimeFrameIndex : int
     * @return int
     *
     * EDIT HISTORY (most recent at the top) 
     * Date     Author      Description
     *
     *
     */
    public static int getMaxEdgeCount(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {

        
        /* Note: since we are using dynamicArrays, there is not constraint
         * coming from logic on maxVertex numbers. If there is a constraint.
         * it is from UI and because of memory (because for UI everything should
         * reside in memory. So we are not sure where to put and how to calculate
         * the return value for this function.
         * 18 Feb, 2016
         */
        
        int intMaxEdgeCount = 200;

        // TO DO
        // invoke the logic layer to fetch the maximum vertex count
        return intMaxEdgeCount;
    }   
    
    
    /**
     *  Method Name     : getVertexNeighborhood()
     *  Created Date    : 2016-05-11
     *  Description     : Returns a set of Neighbors for the list of Vertices sent as the parameter
     *  Version         : 1.0
     *  @author         : Talat
     * 
     * @param pintProjectId : int
     * @param pintGraphId : int
     * @param pintTimeFrameIndex : int
     * @param plstintVertices : List<Integer>
     * @return Set<Integer>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static Set<Integer> getVertexNeighborhood(int pintProjectId, 
            int pintGraphId, 
            int pintTimeFrameIndex, 
            List<Integer> plstintVertices, 
            int pintNeighborDegree) {
        
        Set<Integer> setNeighbors = null;
        try {
            setNeighbors = new HashSet<>();
            
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

            IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
            TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
            
            for (Integer intCurrentVertexId: plstintVertices) {
                IVertex vtxCurrent = dynaGraph.getVertex(intCurrentVertexId);
                Set<IVertex> lstNeighbors = GraphUtil.getNeighborAtLevelith(
                        dynaGraph, vtxCurrent, tf, pintNeighborDegree);
                System.out.println("GraphAPI.getVertexNeighborhood(): "
                        + "Number of Neighbors for Vertex "+vtxCurrent.getId()+
                        " is "+lstNeighbors.size()+"\t["+lstNeighbors.toString()+"]");
                
                for (IVertex vtxCurrentNeighbor : lstNeighbors) {
                    setNeighbors.add(vtxCurrentNeighbor.getId());
                }
            }
            
        } catch (Exception ex) {
            System.out.println("GraphAPI.getVertexNeighborhood(): EXCEPTION") ;
            ex.printStackTrace();
        }
        return setNeighbors ;
    }
    
    /**
     *  Method Name     : stopAlgorithms()
     *  Created Date    : 2016-06-20
     *  Description     : Stops all the algorithms of the graphs
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectId : int
     *  @param pintGraphId : int
     *  @return boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static boolean stopAlgorithms(int pintProjectId, int pintGraphId) {
        
        boolean blnValue = true ;
        
        return blnValue ;
    }
    
    /**
     *  Method Name     : extractSubGraph()
     *  Created Date    : 2017-05-31
     *  Description     : create a new graph from the selected vertices and edges of parent graph. Add the new graph to the parent graph's project
     *  Version         : 1.0
     *  @author         : Abhi
     * 
     *  @param pintProjectId : int
     *  @param pintGraphId : int
     *  @return boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
     
    public static int extractSubGraph(int pintProjectId, int pintGraphId, String[] ptimeFrames, List<Integer> plistSelectedVerticesIds, List<Integer> plistSelectedEdgesIds){
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        // make subgraph
        int intNoOfVerticesSubGraph = plistSelectedVerticesIds.size();
        int intNoOfEdgesSubGraph = plistSelectedEdgesIds.size();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaSubGraph = new DynamicGraph(intNoOfVerticesSubGraph, intNoOfEdgesSubGraph);
        
        // add it to destination project
        int intSubGraphId = BIZInstance.getProject(pintProjectId).addGraph(dynaSubGraph);
        
        System.out.println("GraphAPI.:: subGraphId from project map(id, graph) : "+dynaSubGraph.getId() + ", : project path : " + BIZInstance.getProject(pintProjectId).getProjectDirectory());
        
        //set file name, filepath, graphtitle
        //TODO : Change logic of graph title here
        dynaSubGraph.setGraphTitle(getExtractSubGraphTitlePrefix() + " ("+dynaSubGraph.getId()+")");
        
        
        //Get project direcotry path
        String dynaGraphFile = dynaGraph.getGraphFile();   
        Path dynaGraphFilePath = Paths.get(dynaGraphFile);
        Path directoryDynaGraph = dynaGraphFilePath.getParent();
        String subGraphFilePath =  directoryDynaGraph+File.separator+dynaSubGraph.getGraphTitle()+".meerkat";
        //
        dynaSubGraph.setGraphFile(BIZInstance.getProject(pintProjectId).getProjectDirectory()+dynaSubGraph.getGraphTitle()+".meerkat"); // Set the file name
        //System.out.println("GraphAPI.extractSubgraph: subGraph's file path = " + subGraphFilePath);
        //dynaSubGraph.setGraphFile(subGraphFilePath); // Set the file name
        //pass the control to MeerkatLogic to  add vertices and edges in dynaSubGraph
        GraphUtil.makeDynaGraph(dynaGraph, dynaSubGraph, plistSelectedVerticesIds, plistSelectedEdgesIds);
                
        
        return intSubGraphId;    
            
    }
    
    /**
     *  Method Name     : saveCommunity()
     *  Created Date    : 2017-07-05
     *  Description     : saves the community along with its vertices and edges.                            
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pintProjectId : int
     *  @param intGraphId : int
     *  @param intcurrentTimeFrameIndex : int
     *  @param lstintSelectedVertexIds : List<Integer>
     *  @param lstintSelectedEdgeIds : List<Integer>
     *  @param filePath : String
     *  @return intErrorCode : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static int saveCommunity(
              int pintProjectId
            , int intGraphId
            , int intcurrentTimeFrameIndex
            , List<Integer> lstintSelectedVertexIds
            , List<Integer> lstintSelectedEdgeIds
            , String filePath ) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(intGraphId);
        
        List<Integer[]> edgeSourceAndTarget = new ArrayList<>();
        
        for(int edgeId : lstintSelectedEdgeIds){
            Integer[] edgeSrcTarget = new Integer[2];
            IEdge e  = dynaGraph.getEdge(edgeId);
            edgeSrcTarget[0] = e.getSource().getId();
            edgeSrcTarget[1] = e.getDestination().getId();
            edgeSourceAndTarget.add(edgeSrcTarget);
        }
        
        Set<String> communities = new HashSet<>();
        
        Map<Integer, String> communityMap = getVertexAttributeValues(pintProjectId, intGraphId, intcurrentTimeFrameIndex, MeerkatSystem.COMMUNITY, lstintSelectedVertexIds);
        
        for(Map.Entry<Integer,String> entry : communityMap.entrySet()){
            if(entry.getValue()!=null)
                communities.add(entry.getValue());
        }
        
        int intErrorCode = BIZInstance.getProject(pintProjectId).saveCommunity(intGraphId, communities, lstintSelectedVertexIds, edgeSourceAndTarget, filePath);
        
        return intErrorCode;
    }
    
    
    public static String getGraphTitle(int pintProjectId, int pintGraphId){
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        if(dynaGraph != null){
            return dynaGraph.getGraphTitle();
        }
        
        return null;
    
    }
    
  public static Boolean renameGraph(int pintProjectId, int pintGraphId, String pstrgraphNewTitle, String pstrProjectExtension
            , String pstrProjectDelimiter
            , String pstrGraphDelimiter){
  
      
        try{
            
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
            
            IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
            if(BIZInstance.getProject(pintProjectId).checkIfGraphInMPRJFile(pintGraphId)){
                //rename dynagraph
                //modify mprj file
                //rename the  graph file on disk
                if(BIZInstance.getProject(pintProjectId).renameGraphFile(pintGraphId, pstrgraphNewTitle)){
                    dynaGraph.renameGraph(pstrgraphNewTitle);
                    BIZInstance.getProject(pintProjectId).writeMPRJFile(pstrProjectExtension, pstrProjectDelimiter, pstrGraphDelimiter);
                }else{
                    //there was an error in renaming the file
                    return false;
                }
                
            }else{
                dynaGraph.renameGraph(pstrgraphNewTitle);
            }      
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
  }
  
  public static String getExtractSubGraphTitlePrefix(){
  
      MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
      return BIZInstance.getExtractSubGraphTitlePrefix();
      
  }
  
  public static String getMeerkatSystemXAttribute(){
      return MeerkatSystem.X;
  }
  
  public static String getMeerkatSystemYAttribute(){
      return MeerkatSystem.Y;
  }
  
  public static String getMeerkatSystemCommunityAttribute(){
      return MeerkatSystem.COMMUNITY;
  }
  
  /**
   * 
   * @return Type Tag
   * @since 2018-01-25
   */
  public static String getMeerkatSystemTypeAttribute(){
      return MeerkatSystem.TYPE;
  }
  
  public static String getMeerkatSystemColorAttribute(){
      return MeerkatSystem.COLOR;
  }
  
  public static String getGraphNameProjectNameRegex(){
  
      MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
      return BIZInstance.getGraphNameProjectNameRegex();
      
  }
  
  public static void updateVertexLocationsInLogic(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, Map<Integer, Double[]> pmapVertexUILocationsTF){
  
      MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
      
      IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
      TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
      
      dynaGraph.updateVertexLocationsInTimeFrame(tf, pmapVertexUILocationsTF);
  
  }
  
    /**
     *  Method Name     : updateVertexAttributeValue()
     *  Created Date    : 2017-07-20
     *  Description     : updates the attribute value for a given attribute name, for given set of vertices                            
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pintProjectId : int
     *  @param pintGraphId : int
     *  @param pintTimeFrameIndex : int
     *  @param editedRows : List<String[]>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void updateVertexAttributeValue(int pintProjectId, int pintGraphId, 
            int pintTimeFrameIndex, List<String[]> editedRows){
        System.out.println("GraphAPI updateVertexAttributeValue(): Updating Vertex Attribute Value...");
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);

        for(String[] row : editedRows){
            IVertex vertexNode = dynaGraph.getVertex(Integer.parseInt(row[0]));
            vertexNode.updateAttributeValue(row[1], row[2], tf);
        }
    }
    
    /**
     * Updates the color for a list of vertices
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param pmapVertexColor 
     * @return true if the update was successful / else false
     */
    public static boolean updateVertexColor(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, Map<Integer, String> pmapVertexColor) {
        
        System.out.println("GraphAPI updateVertexAttributeValue(): Updating Vertex Attribute Value...");
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        dynaGraph.updateVertexColors(tf, pmapVertexColor);
        
        return false;
    }
    
    /**
     * Updates the color for a list of vertices
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param pstrColor
     * @param plstVertexIds
     * @return true if the update was successful / else false
     */
    public static boolean updateVertexColor(int pintProjectId, int pintGraphId,
            int pintTimeFrameIndex, String pstrColor, List<Integer> plstVertexIds) {
        
        System.out.println("GraphAPI updateVertexAttributeValue(): Updating Vertex Attribute Value...");
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        dynaGraph.updateVertexColors(tf, pstrColor, plstVertexIds);
        
        return false;
    }
    
    /**
     * removes the sys color attribute of the vertices in given time frame.
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     */
    public static void removeSysColorVerticesAttribute(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        for (IVertex v : dynaGraph.getVertices(tf)) {
            if (v.getSystemAttributer().getAttributeNames()
                    .contains(MeerkatSystem.COLOR)) {
                v.getSystemAttributer().removeAttribute(MeerkatSystem.COLOR);
            }
        }
    }
    
    /**
     * removes the sys color attribute of the edges in given time frame.
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     */
    public static void removeSysColorEdgesAttribute(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        for (IEdge e : dynaGraph.getEdges(tf)) {
            if (e.getSystemAttributer().getAttributeNames()
                    .contains(MeerkatSystem.COLOR)) {
                e.getSystemAttributer().removeAttribute(MeerkatSystem.COLOR);
            }
        }
    }
    
    /**
     * gets the color for all the vertices
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex 
     * @return Map<Integer, String>
     */
    public static  Map<Integer, String> getAllVertexColors(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        
        //System.out.println("GraphAPI getAllVertexColors(): getting colors of all the vertices...");
        Map<Integer, String> mapAllVerticesColor = new HashMap<>();
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        dynaGraph.getVertices(tf).forEach((v) -> {
            mapAllVerticesColor.put(v.getId(), getVertexColor(pintProjectId, pintGraphId, pintTimeFrameIndex, v.getId()));
        });
        
        return mapAllVerticesColor;
    }
    
    /**
     * gets the color for all the edges
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex 
     * @return Map<Integer, String>
     */
    public static  Map<Integer, String> getAllEdgeColors(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        
        //System.out.println("GraphAPI getAllEdgeColors(): getting colors of all the edges...");
        Map<Integer, String> mapAllEdgesColor = new HashMap<>();
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        dynaGraph.getEdges(tf).forEach((e) -> {
            String[] eColor = null;
            IEdge edge = dynaGraph.getEdge(e.getId());
            if(edge.getSystemAttributer().containsAttributeAtTimeFrame(MeerkatSystem.COLOR, tf)){
                if(edge.getSystemAttributer().getAttributeValue(MeerkatSystem.COLOR, tf)!=null){               
                    eColor = edge.getSystemAttributer().getAttributeValue(MeerkatSystem.COLOR, tf).split(",");
                }
            }
            String color = null;
            if (eColor == null){
                color = null;
            } else {
                color = eColor[0];
            }
            mapAllEdgesColor.put(e.getId(), color);
        });
        
        return mapAllEdgesColor;
    }
    
    /**
     * Returns whether each of the Edges is Predicted or not
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @return Map between the EdgeId and if that edge is predicted or not
     * 
     * @author Talat
     * @since 2018-03-23
     */
    public static  Map<Integer, Boolean> getAllEdgePredValues(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        
        Map<Integer, Boolean> mapAllEdgePredValues = new HashMap<>();
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        dynaGraph.getEdges(tf).forEach((e) -> {
            String strPredicted = null;
            IEdge edge = dynaGraph.getEdge(e.getId());
            if(edge.getSystemAttributer().containsAttributeAtTimeFrame(MeerkatSystem.PREDICTED, tf)){
                if(edge.getSystemAttributer().getAttributeValue(MeerkatSystem.PREDICTED, tf)!=null){               
                    strPredicted = edge.getSystemAttributer().getAttributeValue(MeerkatSystem.PREDICTED, tf);
                }
            }
            
            boolean isPredicted = Boolean.parseBoolean(strPredicted);
            mapAllEdgePredValues.put(e.getId(), isPredicted);
        });
        
        return mapAllEdgePredValues;
    }
    
    /**
     * Returns a list of predicted edges for a timeframe
     * The returned value is a map of Edge Id with an array of size 2
     * 0th index contains the source vertex Id
     * 1st index contains the destination vertex Id
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @return Predicted Edges as a Map
     * 
     * @author Talat
     * @since 2018-04-09
     */
    public static Map<Integer, Integer[]> getPredictedEdges(int pintProjectId, 
            int pintGraphId, int pintTimeFrameIndex) {
        
        Map<Integer, Integer[]> mapPredictedEdges = new HashMap<>();
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        dynaGraph.getEdges(tf).forEach((e) -> {
            String strPredicted = null;
            IEdge edge = dynaGraph.getEdge(e.getId());
            if(edge.getSystemAttributer().containsAttributeAtTimeFrame(MeerkatSystem.PREDICTED, tf)){
                if(edge.getSystemAttributer().getAttributeValue(MeerkatSystem.PREDICTED, tf) != null){               
                    strPredicted = edge.getSystemAttributer().getAttributeValue(MeerkatSystem.PREDICTED, tf);
                    if (Boolean.parseBoolean(strPredicted)) {
                        mapPredictedEdges.put(edge.getId(), new Integer[] {edge.getSource().getId(), edge.getDestination().getId()});
                    }
                }
            }
        });
        return mapPredictedEdges;
    }
    
    /**
     * Returns a list of non-predicted edges for a timeframe
     * The returned value is a map of Edge Id with an array of size 2
     * 0th index contains the source vertex Id
     * 1st index contains the destination vertex Id
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @return Predicted Edges as a Map
     * 
     * @author Talat
     * @since 2018-05-30
     */
    public static Map<Integer, Integer[]> getNonPredictedEdges(int pintProjectId, 
            int pintGraphId, int pintTimeFrameIndex) {
        
        Map<Integer, Integer[]> mapPredictedEdges = new HashMap<>();
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        dynaGraph.getEdges(tf).forEach((e) -> {
            String strPredicted = null;
            IEdge edge = dynaGraph.getEdge(e.getId());
            if(edge.getSystemAttributer().containsAttributeAtTimeFrame(MeerkatSystem.PREDICTED, tf)){
                if(edge.getSystemAttributer().getAttributeValue(MeerkatSystem.PREDICTED, tf) != null){               
                    strPredicted = edge.getSystemAttributer().getAttributeValue(MeerkatSystem.PREDICTED, tf);
                    if (!Boolean.parseBoolean(strPredicted)) {
                        mapPredictedEdges.put(edge.getId(), new Integer[] {edge.getSource().getId(), edge.getDestination().getId()});
                    }
                }
            }
        });
        return mapPredictedEdges;
    }
    
    /**
     * Removes the predicted edges in the graph and returns the ids as a map 
     * to the UI layer
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @return Map of Predicted Edge IDs
     * 
     * @author Talat
     * @since 2018-04-07
     */
    public static Map<Integer, Integer[]> removePredictedEdgesInGraph(
            int pintProjectId,
            int pintGraphId,
            int pintTimeFrameIndex) {
        Map<Integer, Integer[]> mapPredictedEdges = new HashMap<>();
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        dynaGraph.getEdges(tf).forEach((e) -> {
            String strPredicted = null;
            IEdge edge = dynaGraph.getEdge(e.getId());
            if(edge.getSystemAttributer().containsAttributeAtTimeFrame(MeerkatSystem.PREDICTED, tf)){
                if(edge.getSystemAttributer().getAttributeValue(MeerkatSystem.PREDICTED, tf) != null){
                    strPredicted = edge.getSystemAttributer().getAttributeValue(MeerkatSystem.PREDICTED, tf);
                    if (Boolean.parseBoolean(strPredicted)) {
                        mapPredictedEdges.put(edge.getId(), new Integer[] {edge.getSource().getId(), edge.getDestination().getId()});
                        dynaGraph.removeEdge(edge);
                    }
                }
            }
        });
        return mapPredictedEdges;
    }
    
    /**
     * Returns whether each of the Edges is Directed or not
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @return Map between the EdgeId and if that edge is directed or not
     * 
     * @author Talat
     * @since 2018-03-23
     */
    public static  Map<Integer, Boolean> getAllEdgeDirValues(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        
        Map<Integer, Boolean> mapAllEdgeDirValues = new HashMap<>();
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        dynaGraph.getEdges(tf).forEach((e) -> {
            String strDirected = null;
            IEdge edge = dynaGraph.getEdge(e.getId());
            if(edge.getSystemAttributer().containsAttributeAtTimeFrame(MeerkatSystem.DIRECTED, tf)){
                if(edge.getSystemAttributer().getAttributeValue(MeerkatSystem.DIRECTED, tf)!=null){               
                    strDirected = edge.getSystemAttributer().getAttributeValue(MeerkatSystem.DIRECTED, tf);
                }
            }
            /* TO BE REMOVED AFTER THE LOGIC LAYER IS IMPLEMENTED */
            if (strDirected == null){
                if (edge.getId() % 2 == 0) {
                    strDirected = "true";
                } else {
                    strDirected = "false";
                }
            }
            // -----
            
            boolean isDirected = Boolean.parseBoolean(strDirected);
            mapAllEdgeDirValues.put(e.getId(), isDirected);
        });
        
        return mapAllEdgeDirValues;
    }
    
    /**
     * gets the color for all the vertices
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex 
     * @return Map<Integer, String>
     * @author Talat
     * @since 2018-01-25
     */
    public static  Map<Integer, String> getAllVertexIconURLs(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        
        //System.out.println("GraphAPI getAllVertexIconURLs(): getting colors of all the vertices...");
        Map<Integer, String> mapAllVerticesIconURLs = new HashMap<>();
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        dynaGraph.getVertices(tf).forEach((v) -> {            
            mapAllVerticesIconURLs.put(v.getId(), v.getSystemAttributer().getAttributeValue(MeerkatSystem.TYPEICONURL, tf));
        });
        
        return mapAllVerticesIconURLs;
    }
    
    /**
     * gets the color for a given vertex
     * @author : sankalp
     * @since : 2018-01-25
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex 
     * @param pintVertexId
     * @return String
     */
    public static  String getVertexColor(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, int pintVertexId) {
        
        // System.out.println("GraphAPI getVertexColor(): getting color of given vertex...");
        String[] vColor = null;
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        IVertex v = dynaGraph.getVertex(pintVertexId);            
            
        if(v.getSystemAttributer().containsAttributeAtTimeFrame(MeerkatSystem.COLOR, tf)){
            if(v.getSystemAttributer().getAttributeValue(MeerkatSystem.COLOR, tf)!=null){               
                vColor = v.getSystemAttributer().getAttributeValue(MeerkatSystem.COLOR, tf).split(",");
            }
        }else
            return null;
        
        //just return the first color value if a vertex is associated with multiple colors
        return vColor[0];
    }
    /**
     * gets the color for a given edge
     * @author : sankalp
     * @since : 2018-01-25
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex 
     * @param pintEdgeId
     * @return String
     */
    
    public static String getEdgeColor(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, int pintEdgeId) {
        
        String[] eColor = null;
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        IEdge edge = dynaGraph.getEdge(pintEdgeId);            
            
        if(edge.getSystemAttributer().containsAttributeAtTimeFrame(MeerkatSystem.COLOR, tf)){
            if(edge.getSystemAttributer().getAttributeValue(MeerkatSystem.COLOR, tf)!=null){               
                eColor = edge.getSystemAttributer().getAttributeValue(MeerkatSystem.COLOR, tf).split(",");
            }
        } else {
            return null;
        }
        
        //just return the first color value if a vertex is associated with multiple colors
        return eColor[0];
    }
    
    /**
     * Gets if the Edge is directed or not
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param pintEdgeId
     * @return true is the Edge is directed
     * 
     * @author Talat
     * @since 2018-03-23
     */
    public static Boolean getIsEdgeDirected(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, int pintEdgeId) {
        
        String[] eDirected = null;
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        IEdge edge = dynaGraph.getEdge(pintEdgeId);   
        boolean isDirected ;
            
        if(edge.getSystemAttributer().containsAttributeAtTimeFrame(MeerkatSystem.DIRECTED, tf)){
            if(edge.getSystemAttributer().getAttributeValue(MeerkatSystem.DIRECTED, tf)!=null){               
                eDirected = edge.getSystemAttributer().getAttributeValue(MeerkatSystem.DIRECTED, tf).split(",");
            }
            isDirected = Boolean.parseBoolean(eDirected[0]);
            
        } else {
            return null;
        }
        
        return isDirected;
    }
    
    /**
     * Gets if the Edge is predicted or not
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param pintEdgeId
     * @return true is the Edge is directed
     * 
     * @author Talat
     * @since 2018-03-23
     */
    public static Boolean getIsEdgePredicted(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, int pintEdgeId) {
        
        String[] ePredicted = null;
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        IEdge edge = dynaGraph.getEdge(pintEdgeId);         
        boolean isPredicted ;
            
        if(edge.getSystemAttributer().containsAttributeAtTimeFrame(MeerkatSystem.PREDICTED, tf)){
            if(edge.getSystemAttributer().getAttributeValue(MeerkatSystem.PREDICTED, tf)!=null){               
                ePredicted = edge.getSystemAttributer().getAttributeValue(MeerkatSystem.PREDICTED, tf).split(",");
            }
            isPredicted = Boolean.parseBoolean(ePredicted[0]);
        } else {
            return null;
        }
        
        return isPredicted;
    }
        
     /**
     *  Method Name     : updateEdgeAttributeValue()
     *  Created Date    : 2017-07-21
     *  Description     : updates the attribute value for a given attribute name, for given set of edges                            
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pintProjectId : int
     *  @param pintGraphId : int
     *  @param pintTimeFrameIndex : int
     *  @param editedRows : List<String[]>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void updateEdgeAttributeValue(int pintProjectId, int pintGraphId, 
            int pintTimeFrameIndex, List<String[]> editedRows){
        //System.out.println("GraphAPI updateEdgeAttributeValue(): Updating Edge Attribute Value...");
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);

        for(String[] row : editedRows){
            IEdge edgeNode = dynaGraph.getEdge(Integer.parseInt(row[0]));
            edgeNode.updateAttributeValue(row[1], row[2], tf);
        }

    }
    
    /**
     *  Updates the Edge System Attribute of Predicted
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param pintEdgeId
     * @param pblnIsPredicted 
     * 
     * @author Talat
     * @since 2018-04-08
     */
    public static void updateEdgeAttribute_Predicted (
            int pintProjectId, 
            int pintGraphId, 
            int pintTimeFrameIndex, 
            int pintEdgeId,
            boolean pblnIsPredicted) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        IEdge edge = dynaGraph.getEdge(pintEdgeId);  
        
        if (edge != null) {
            edge.getSystemAttributer().addAttributeValue(MeerkatSystem.PREDICTED, 
                    String.valueOf(pblnIsPredicted), new Date(), tf);
        }
    }
    
    /**
     * Updates the Edge System Attribute of Directed
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param pintEdgeId
     * @param pblnIsDirected 
     * 
     * @author Talat
     * @since 2018-04-08
     */
    public static void updateEdgeAttribute_Directed (
            int pintProjectId, 
            int pintGraphId, 
            int pintTimeFrameIndex, 
            int pintEdgeId,
            boolean pblnIsDirected) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        IEdge edge = dynaGraph.getEdge(pintEdgeId);  
        
        if (edge != null) {
            edge.getSystemAttributer().addAttributeValue(MeerkatSystem.DIRECTED, 
                    String.valueOf(pblnIsDirected), new Date(), tf);
        }
    }
    
    
    /**
     * Updates the color for a list of Edges
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param pstrColor
     * @param plstEdgeIds
     * @return true if the update was successful / else false
     */
    public static boolean updateEdgeColor(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, String pstrColor, List<Integer> plstEdgeIds) {
        
        //System.out.println("GraphAPI.updateEdgeColor(): Updating Vertex Attribute Value...");
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        dynaGraph.updateEdgeColors(tf, pstrColor, plstEdgeIds);
        
        return false;
    }
    
    /**
     * Updates the color for a list of Edges
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param pmapEdgeColor 
     * @return true if the update was successful / else false
     */
    public static boolean updateEdgeColor(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, Map<Integer, String> pmapEdgeColor) {
        
        //System.out.println("GraphAPI.updateEdgeColor(): Updating Vertex Attribute Value...");
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        dynaGraph.updateEdgeColors(tf, pmapEdgeColor);
        
        return true;
    }
    
    /**
     * Updates the URL for the list of Vertices
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param pmapIconURLs 
     * @return true if the update was successful / else false
     */
    public static boolean updateVertexIconURLs(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, Map<Integer, String> pmapIconURLs){
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        dynaGraph.updateVertexIconURLs(tf, pmapIconURLs);
        
        return true;
    }
    
    /**
     * Updates the URL for the list of Vertices
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param pstrIconURL
     * @param plstVertexIds 
     * @return true if the update was successful / else false
     */
    public static boolean updateVertexIconURLs(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, String pstrIconURL, List<Integer> plstVertexIds){
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        dynaGraph.updateVertexIconURLs(tf, pstrIconURL, plstVertexIds);
        
        return true;
    }

    /**
     * Updates the default color string value in MeerkatSystem
     * @param vertex_color_default
     */
    public static void updateDefaultVertexColorString(String vertex_color_default) {
        MeerkatSystem.setDefaultVertexColor(vertex_color_default);
    }
   
    public static void setCommunityColor(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, String strCommunity, String strColor) {

        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        dynaGraph.setCommunityColor(strCommunity, strColor, tf);
      
    }
    
    public static Map<String, String> getMapCommunityColors(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        return dynaGraph.getMapCommunityColors(tf);
      
    }

    public static void resetGlobalCommunityColorMap(int pintProjectId, int pintGraphId) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        dynaGraph.resetGlobalCommunityColorMap();
        
    }

    public static String getVertexIconURL(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, int pintVertexId) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        return dynaGraph.getVertex(pintVertexId).getSystemAttributer().getAttributeValue(MeerkatSystem.TYPEICONURL, dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex));
    }

    
    /**
     * checks if the given set of vertices in the current time frame
       have a certain sys attribute present or not.
     * @author : sankalp
     * @since : 2018-01-25
     * @param pintProjectId
     * @param pintGraphId
     * @param attrname 
     * @param pVertices
     * @return true if sys attribute is present / else false
     */
    public static Map<Integer, Boolean> checkVertexSysAttributePresent(int pintProjectId, int pintGraphId, 
                                                                String attrname, List<Integer> pVertices) {
        
        //System.out.println("GraphAPI.getAttributePresent():");
        
        Map<Integer, Boolean> mapAttrCheck = new HashMap<>();
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        for(int vertexId : pVertices){
            IVertex v = dynaGraph.getVertex(vertexId);
            if(v.getSystemAttributer().getAttributeNames().contains(attrname))
                mapAttrCheck.put(vertexId, Boolean.TRUE);
            else
                mapAttrCheck.put(vertexId, Boolean.FALSE);
        }
        
        return mapAttrCheck;
    }
    
    /**
     * checks if the given set of edges in the current time frame
       have a certain sys attribute present or not.
     * @author : sankalp
     * @since : 2018-01-25
     * @param pintProjectId
     * @param pintGraphId
     * @param attrname 
     * @param edgeIds
     * @return true if sys attribute is present / else false
     */
    public static Map<Integer, Boolean> checkEdgeSysAttributePresent(int pintProjectId, int pintGraphId, 
                                                                    String attrname, List<Integer> edgeIds) {
        //System.out.println("GraphAPI.checkEdgeSysAttributePresent():");
        
        Map<Integer, Boolean> mapAttrCheck = new HashMap<>();
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        for(int edgeId : edgeIds){
            IEdge e = dynaGraph.getEdge(edgeId);
            if(e.getSystemAttributer().getAttributeNames().contains(attrname))
                mapAttrCheck.put(edgeId, Boolean.TRUE);
            else
                mapAttrCheck.put(edgeId, Boolean.FALSE);
        }
        
        return mapAttrCheck;
    }
    
    /**
     * Returns the list of Edge Ids that constitute the shortest path between two vertices
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param pintSourceVertexId
     * @param pintDestinationVertexId
     * @return list of Edges Ids as Integers
     * 
     * @author Talat
     * @since 2018-05-10
     */
    public static List<Integer> getShortestPath(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, 
            int pintSourceVertexId, int pintDestinationVertexId) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        // Call the method
        // List<Integer> lstEdgeIds = 
        
        // START DELETE 
        List<Integer> lstEdgeIds = new ArrayList<>();
        lstEdgeIds.add(1);
        lstEdgeIds.add(3);
        lstEdgeIds.add(4);
        // END DELETE
        
        return lstEdgeIds;
    }
}
