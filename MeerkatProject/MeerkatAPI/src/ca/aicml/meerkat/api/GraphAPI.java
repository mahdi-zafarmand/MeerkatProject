/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api;

import algorithm.graph.GraphUtil;
import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.graph.impl.DynamicGraph;
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
 * Class Name: GraphAPI
 * Description: API between the UI layer and the Logic layer for Graph related elements.
 * 
 * @author mahdi
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
    
    public static String [] getTimeFrameNames(int pintProjectId, int pintGraphId) {
        int intTotalTimeFrames = -1;
        String [] arrstrTimeFrameNames = null ;
        try {            
            MeerkatBIZ mktBizInstance = MeerkatBIZ.getMeerkatApplication();
            IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph = mktBizInstance.getProject(pintProjectId).getGraph(pintGraphId);
            intTotalTimeFrames = dynaGraph.getAllTimeFrames().size();
                        
            arrstrTimeFrameNames = new String[intTotalTimeFrames];
            int intTimeFrameIndex = 0;
            for(TimeFrame tf : dynaGraph.getAllTimeFrames()){
                arrstrTimeFrameNames[intTimeFrameIndex++] = tf.getTimeFrameName();
            }
        } catch (Exception ex) {
            System.out.println("GraphAPI.getTimeFrameCount(): EXCEPTION") ;
            ex.printStackTrace();
        }
        return arrstrTimeFrameNames ;
    }
    
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
            ex.printStackTrace();
            return intVertexCount ;
        }
    }
    
    public static int getEdgeCount(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        int intEdgeCount = -1;
        MeerkatBIZ mktBizInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph
                = mktBizInstance.getProject(pintProjectId).getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        intEdgeCount = dynaGraph.getGraph(tf).getEdgeCount();
        return intEdgeCount;
    }
    
    public static List<Integer> getAllVertexIds(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ mktappBusiness = MeerkatBIZ.getMeerkatApplication();
        List<Integer> lstReturnValue = new ArrayList<>();
        IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph
                = mktappBusiness.getProject(pintProjectId).getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        lstReturnValue.addAll(dynaGraph.getGraph(tf).getAllVertexIds());

        return lstReturnValue;
    }
    
    public static List<Integer> getAllVertexIds(int pintProjectId, int pintGraphId) {
        MeerkatBIZ mktappBusiness = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph
                = mktappBusiness.getProject(pintProjectId).getGraph(pintGraphId);
        List<Integer> lstReturnValue = dynaGraph.getAllVertexIds();
        return lstReturnValue;
    }
    
    public static List<Integer> getAllEdgeIds(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        List<Integer> setVertexIds = new ArrayList<>();
        MeerkatBIZ mktBizInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph
                = mktBizInstance.getProject(pintProjectId).getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        setVertexIds.addAll(dynaGraph.getGraph(tf).getAllEdgeIds());
        return setVertexIds;
    }
    
    public static List<String> getAllEdgesAsNodeIds(int pintProjectId, int pintGraphId,
            int pintTimeFrameIndex, String pstrDelimiter) {

        /* NOTE: Each String in the list returned is a comma separated value 
                 with a comma between the source and the destination */
        MeerkatBIZ mktappBusiness = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = 
                mktappBusiness.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        List<IEdge<IVertex>> lstEdgeAsNodes = dynaGraph.getEdges(tf);
        List<String> lstEdgesAsStrings = new ArrayList<>();
        lstEdgeAsNodes.stream().map((e) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(e.getId())
                    .append(pstrDelimiter)
                    .append(e.getSource())
                    .append(pstrDelimiter)
                    .append(e.getDestination());
            return sb;            
        }).forEachOrdered((sb) -> {
            lstEdgesAsStrings.add(sb.toString());
        });
        return lstEdgesAsStrings;
    }
    
    public static int [][] getAllEdgesAsNodeIds(int pintProjectId, int pintGraphId, String pstrDelimiter) {

        /* NOTE: Each String in the list returned is a comma separated value 
         with a comma between the source and the destination */
        MeerkatBIZ mktappBusiness = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = 
                mktappBusiness.getProject(pintProjectId).getGraph(pintGraphId);
        
        List<IEdge<IVertex>> lstEdges = dynaGraph.getAllEdges() ;
        int [][] arrstrEdges = new int [lstEdges.size()][3] ; // Source at 0th index and destination at the 1st index
        int intEdgeIndex = 0;
        lstEdges.stream().map((e) -> {
            arrstrEdges[intEdgeIndex][0] = e.getId();
            return e;
        }).map((e) -> {
            arrstrEdges[intEdgeIndex][1] = e.getSource().getId() ;
            return e;
        }).forEachOrdered((e) -> {
            arrstrEdges[intEdgeIndex][2] = e.getDestination().getId() ;
        });
        return arrstrEdges;
    }
    
    public static String getOutputFile(int pintGraphId) {
        String strOutputFilePath = new String();
        // TODO
        return strOutputFilePath;
    }
    
    public static boolean saveGraph(int pintProjectId, int pintGraphId, String pstrGraphFilePath) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        boolean blnSuccess = false;
        IDynamicGraph<IVertex,IEdge<IVertex>> graphCurrent = BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
//        String blnSuccess = MeerkatWriter.write(graphCurrent, pstrGraphFilePath);
        return blnSuccess;
    }
    
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
    
    public static Map<String, Boolean> getVertexAttributeNamesWithType(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        return dynaGraph.getGraph(tf).getVertexAttributeNamesWithType();
    }
    
    public static void removeVertices(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, List<Integer> lstVertexIds) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        List<TimeFrame> lstTF = new ArrayList<>();
        lstTF.add(tf);
        dynaGraph.getVertices(tf).stream().filter((v) -> (lstVertexIds.contains(v.getId()))).forEachOrdered((v) -> {
            dynaGraph.removeVertex(v,lstTF);
        });
    }
    
    public static void removeEdges(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, List<Integer> lstVertexIds) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        dynaGraph.getVertices(tf).stream().filter((v) -> (lstVertexIds.contains(v.getId()))).forEachOrdered((v) -> {
            dynaGraph.getEdges(v, tf).forEach((e) -> {
                dynaGraph.removeEdge(e, tf);
            });
        });  
    }
    
    public static void removeEdges(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, Set<Integer> lstEdges) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        dynaGraph.getEdges(tf).stream().filter((e) -> (lstEdges.contains(e.getId()))).forEachOrdered((e) -> {
            dynaGraph.removeEdge(e, tf);
        });
    }
    
    public static Set<String> getAllUserAttributesNames(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        Set<String> setUserAttributeNames = new HashSet<>();
        dynaGraph.getVertices(tf).forEach((v) -> {
            setUserAttributeNames.addAll(v.getUserAttributer().getAttributeNames());
        });
        return setUserAttributeNames;
    }
    
    public static Map<String, Boolean> getEdgeAttributeNamesWithType
        (int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = 
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
                        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        return dynaGraph.getGraph(tf).getEdgeAttributeNamesWithType();
    }
        
    public static Map<Integer, Double[]> getVertex2DPoistions(int pintProjectId, 
            int pintGraphId, int pintTimeFrameIndex, double pdblCanvasWidth, double pdblCanvasHeight) {
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
        
        for (IVertex v : dynaGraph.getVertices(tfTimeFrame)) {            
            Double [] arrdblPositions = new Double[2]; // 0th Index stores X and 1st Index stores Y
            if(v.getSystemAttributer().getAttributeValue(MeerkatSystem.X, tfTimeFrame)!=null && v.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, tfTimeFrame)!=null){
                arrdblPositions[0] = Double.parseDouble(v.getSystemAttributer().getAttributeValue(MeerkatSystem.X, tfTimeFrame)) * dblMinLength + dblXPadding;
                arrdblPositions[1] = Double.parseDouble(v.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, tfTimeFrame)) * dblMinLength + dblYPadding;
            }else{
                arrdblPositions[0] = Double.parseDouble(String.valueOf(Math.random())) * dblMinLength + dblXPadding;
                arrdblPositions[1] = Double.parseDouble(String.valueOf(Math.random())) * dblMinLength + dblYPadding;
            }
            mapVertex2dPositions.put(v.getId(), arrdblPositions) ;
        }
        return mapVertex2dPositions ;
    }
    
    public static String[] getVertexLabels(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, String pstrVertexAttrName) {
        return getVertexAttributeValues(pintProjectId, pintGraphId, pintTimeFrameIndex, pstrVertexAttrName);
    }
    
    public static String[] getVertexTooltips(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, String pstrVertexAttrName) {
        return getVertexAttributeValues(pintProjectId, pintGraphId, pintTimeFrameIndex, pstrVertexAttrName);
    }
    
    public static String[] getVertexAttributeValues(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, String pstrVAttName) {
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
    
    public static Map<Integer, Map<String,String>> getallVertices(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        Map<Integer, Map<String,String>> mapVertices = new HashMap<>();
        dynaGraph.getVertices(tf).forEach((v) -> {
            Integer vId = v.getId();
            Map<String,String> tempMap = new HashMap<>();
            Set<String> attributes = new HashSet<>();
            attributes.addAll(v.getUserAttributer().getAttributeNames());
            attributes.addAll(v.getSystemAttributer().getAttributeNames());
            attributes.forEach((s) -> {
                tempMap.put("Id",vId.toString());
                if (v.getUserAttributer().getAttributeNames().contains(s))
                    tempMap.put(s,v.getUserAttributer().getAttributeValue(s, tf));
                else if (v.getSystemAttributer().getAttributeNames().contains(s))
                    tempMap.put(s,v.getSystemAttributer().getAttributeValue(s,tf));
                else
                    tempMap.put(s,"");
            });
            mapVertices.put(vId,tempMap);
        });
        return mapVertices;
    }
    
    public static Map<Integer, String> getVertexAttributeValues(int pintProjectId, int pintGraphId,
            int pintTimeFrameIndex, String pstrVAttName, List<Integer> lstVertexIds) {

        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        Map<Integer, String> mapIdAttrValue = new HashMap<>();

        dynaGraph.getVertices(tf).stream().filter((v) -> (lstVertexIds.contains(v.getId()))).forEachOrdered((v) -> {
            if (v.getUserAttributer().getAttributeNames().contains(pstrVAttName)) {
                mapIdAttrValue.put(v.getId(), v.getUserAttributer().getAttributeValue(pstrVAttName, tf));
            } else if (v.getSystemAttributer().getAttributeNames().contains(pstrVAttName)) {
                mapIdAttrValue.put(v.getId(), v.getSystemAttributer().getAttributeValue(pstrVAttName, tf));
            } else {
                mapIdAttrValue.put(v.getId(), "");
            }
        });

        return mapIdAttrValue;
    }
    
    public static Map<Integer, String> getEdgeAttributeValues(int pintProjectId,
            int pintGraphId, int pintTimeFrameIndex, String pstrVAttName, List<Integer> lstEdgeIds) {

        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        Map<Integer, String> mapIdAttrValue = new HashMap<>();

        dynaGraph.getEdges(tf).stream().filter((e) -> (lstEdgeIds.contains(e.getId()))).forEachOrdered((e) -> {
            if (e.getUserAttributer().getAttributeNames().contains(pstrVAttName)) {
                mapIdAttrValue.put(e.getId(), e.getUserAttributer().getAttributeValue(pstrVAttName, tf));
            } else if (e.getSystemAttributer().getAttributeNames().contains(pstrVAttName)) {
                mapIdAttrValue.put(e.getId(), e.getSystemAttributer().getAttributeValue(pstrVAttName, tf));
            } else {
                mapIdAttrValue.put(e.getId(), "");
            }
        });

        return mapIdAttrValue;
    }
    
    public static Map<Integer, String[]> getVertexLabelsTooltips(int pintProjectId, 
            int pintGraphId, int pintTimeFrameIndex, String pstrVAttLabelName, String pstrVAttTooltipName) {

        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        Map<Integer, String[]> mapstrLabelsTooltips = new HashMap<>();
        
        dynaGraph.getVertices(tf).forEach((v) -> {
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
        });

        return mapstrLabelsTooltips;
    }
    
    public static Map<Integer, Integer[]> getEdges(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        int noOfEdges = dynaGraph.getGraph(tf).getEdgeCount();
        Map<Integer, Integer[]> mapEdges = new HashMap<>( (int) Math.ceil(noOfEdges/ 0.75) );
        
        dynaGraph.getEdges(tf).forEach((e) -> {
            Integer[] arrEdgeInfo = new Integer[3];
            int sourceId = e.getSource().getId();
            int destinationId = e.getDestination().getId();
            boolean directed = e.isDirected();
            arrEdgeInfo[0] = sourceId;
            arrEdgeInfo[1] = destinationId;
            arrEdgeInfo[2] = (directed ? 1 : 0);
            
            mapEdges.put(e.getId(), arrEdgeInfo);
        });

        return mapEdges;
    }
    
    public static void addVertex(int pintProjectId, int pintGraphId,double pdblX, double pdblY){
        //TODO
    }
    
    public static int addVertexMultipleTimeFrames(int pintProjectId, int pintGraphId,
            int pintTimeFrameIndex, List<Integer> plistTimeFrames,double pdblX, double pdblY){
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        List<TimeFrame> listTimeFrames = new ArrayList<>();
        plistTimeFrames.stream().map((timeFrameIndex) -> dynaGraph.getAllTimeFrames().get(timeFrameIndex)).forEachOrdered((tf) -> {
            listTimeFrames.add(tf);
        });
        
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
    
    public static Double [] getVertex2DPosition(int pintProjectId, int pintGraphId,
            int pintTimeFrameIndex, int pintVertexId, double pdblCanvasWidth, double pdblCanvasHeight){
        
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
            double pdblYUI, double pdblCanvasWidth, double pdblCanvasHeight){
    
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
        
        Double[] arrPositions = new Double[2];
        arrPositions[0] = (pdblXUI - dblXPadding)/dblMinLength;
        arrPositions[1] = (pdblYUI - dblYPadding)/dblMinLength;
                
        return arrPositions;
    }
    
    public static String[] getVertexLabelTooltips(int pintProjectId, int pintGraphId,
            int pintTimeFrameIndex, int pintVertexId, String pstrVAttLabelName, String pstrVAttTooltipName) {

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
    
    public static int addEdge(int pintProjectId, int pintGraphId, int timeFrameIndex,
            int pintSourceVertexId, int pintDestinationVertexId) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(timeFrameIndex);
        return GraphUtil.createAndAddNewEdge(dynaGraph ,tf, pintSourceVertexId, pintDestinationVertexId);
    }
    
    public static Boolean edgeExists(int pintProjectId, int pintGraphId, int timeFrameIndex,
            int pintSourceVertexId, int pintDestinationVertexId) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(timeFrameIndex);
        return GraphUtil.edgeExists(dynaGraph ,tf, pintSourceVertexId, pintDestinationVertexId);
    }
    
    public static List<String> getVertexSystemAttributes_Sorted(int pintProjectId, int pintGraphId, int pintTimeFrameIndex){
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        Set<String> setstrSystemAttributes = new HashSet<>();
        dynaGraph.getVertices(tf).forEach((v) -> {
            setstrSystemAttributes.addAll(v.getSystemAttributer().getAttributeNames());
        });
        List<String> lststrSystemAttributes = new ArrayList<>();
        lststrSystemAttributes.addAll(setstrSystemAttributes);
        Collections.sort(lststrSystemAttributes, String.CASE_INSENSITIVE_ORDER);        
        return lststrSystemAttributes;
    }
    
    public static List<String> getVertexUserAttributes_Sorted(int pintProjectId, int pintGraphId, int pintTimeFrameIndex){
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
                
        Set<String> setstrUserAttributes = new HashSet<>();
        dynaGraph.getVertices(tf).forEach((v) -> {
            setstrUserAttributes.addAll(v.getUserAttributer().getAttributeNames());
        });
        List<String> lststrUserAttributes = new ArrayList<>();
        lststrUserAttributes.addAll(setstrUserAttributes);
        Collections.sort(lststrUserAttributes, String.CASE_INSENSITIVE_ORDER);        
        return lststrUserAttributes;
    }
    
    public static List<String> getEdgeSystemAttributes_Sorted(int pintProjectId, int pintGraphId, int pintTimeFrameIndex){
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        Set<String> setstrSystemAttributes = new HashSet<>();        
        dynaGraph.getEdges(tf).forEach((e) -> {
            setstrSystemAttributes.addAll(e.getSystemAttributer().getAttributeNames());
        });
        
        List<String> lststrSystemAttributes = new ArrayList<>();
        lststrSystemAttributes.addAll(setstrSystemAttributes);
        Collections.sort(lststrSystemAttributes, String.CASE_INSENSITIVE_ORDER);        
        return lststrSystemAttributes;
    }
    
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
        Collections.sort(lststrUserAttributes, String.CASE_INSENSITIVE_ORDER);        
        return lststrUserAttributes;
    }
    
    public static List<String> getAllAttributesNames_Sorted(int pintProjectId, 
            int pintGraphId, int pintTimeFrameIndex) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        Set<String> setUserAttr = new HashSet<>();
        Set<String> setSystemAttr = new HashSet<>();

        dynaGraph.getVertices(tf).stream().map((v) -> {
            setUserAttr.addAll(v.getUserAttributer().getAttributeNames());
            return v;
        }).forEachOrdered((v) -> {
            setSystemAttr.addAll(v.getSystemAttributer().getAttributeNames());
        });
        
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
    
    public static int getMaxVertexCount(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {

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
    
    public static Set<Integer> getVertexNeighborhood(int pintProjectId, int pintGraphId, 
            int pintTimeFrameIndex, List<Integer> plstintVertices, int pintNeighborDegree) {
        
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
    
    public static boolean stopAlgorithms(int pintProjectId, int pintGraphId) {
        boolean blnValue = true ;
        return blnValue ;
    }
    
    public static int extractSubGraph(int pintProjectId, int pintGraphId, String[] ptimeFrames,
            List<Integer> plistSelectedVerticesIds, List<Integer> plistSelectedEdgesIds){
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        // make subgraph
        int intNoOfVerticesSubGraph = plistSelectedVerticesIds.size();
        int intNoOfEdgesSubGraph = plistSelectedEdgesIds.size();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaSubGraph = new DynamicGraph(intNoOfVerticesSubGraph, intNoOfEdgesSubGraph);
        
        // add it to destination project
        int intSubGraphId = BIZInstance.getProject(pintProjectId).addGraph(dynaSubGraph);
                
        //set file name, filepath, graphtitle
        //TODO : Change logic of graph title here
        dynaSubGraph.setGraphTitle(getExtractSubGraphTitlePrefix() + " ("+dynaSubGraph.getId()+")");
        
        //Get project direcotry path
        String dynaGraphFile = dynaGraph.getGraphFile();   
        Path dynaGraphFilePath = Paths.get(dynaGraphFile);
        Path directoryDynaGraph = dynaGraphFilePath.getParent();
//        String subGraphFilePath =  directoryDynaGraph+File.separator+dynaSubGraph.getGraphTitle()+".meerkat";
        dynaSubGraph.setGraphFile(BIZInstance.getProject(pintProjectId).getProjectDirectory()+dynaSubGraph.getGraphTitle()+".meerkat"); // Set the file name
        GraphUtil.makeDynaGraph(dynaGraph, dynaSubGraph, plistSelectedVerticesIds, plistSelectedEdgesIds);
        return intSubGraphId;    
    }
    
    public static int saveCommunity(int pintProjectId, int intGraphId, int intcurrentTimeFrameIndex,
            List<Integer> lstintSelectedVertexIds, List<Integer> lstintSelectedEdgeIds, String filePath ) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(intGraphId);
        
        List<Integer[]> edgeSourceAndTarget = new ArrayList<>();
        lstintSelectedEdgeIds.stream().map((edgeId) -> {
            Integer[] edgeSrcTarget = new Integer[2];
            IEdge e  = dynaGraph.getEdge(edgeId);
            edgeSrcTarget[0] = e.getSource().getId();
            edgeSrcTarget[1] = e.getDestination().getId();
            return edgeSrcTarget;
        }).forEachOrdered((edgeSrcTarget) -> {
            edgeSourceAndTarget.add(edgeSrcTarget);
        });
        
        Set<String> communities = new HashSet<>();
        Map<Integer, String> communityMap = getVertexAttributeValues(pintProjectId,
                intGraphId, intcurrentTimeFrameIndex, MeerkatSystem.COMMUNITY, lstintSelectedVertexIds);
        
        communityMap.entrySet().stream().filter((entry) -> (entry.getValue()!=null)).forEachOrdered((entry) -> {
            communities.add(entry.getValue());
        });
        
        int intErrorCode = BIZInstance.getProject(pintProjectId).saveCommunity(intGraphId, 
                communities, lstintSelectedVertexIds, edgeSourceAndTarget, filePath);
        
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
    
    public static Boolean renameGraph(int pintProjectId, int pintGraphId, String pstrgraphNewTitle,
            String pstrProjectExtension, String pstrProjectDelimiter, String pstrGraphDelimiter){
      
        try{
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
            IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
            if(BIZInstance.getProject(pintProjectId).checkIfGraphInMPRJFile(pintGraphId)){
                if(BIZInstance.getProject(pintProjectId).renameGraphFile(pintGraphId, pstrgraphNewTitle)){
                    dynaGraph.renameGraph(pstrgraphNewTitle);
                    BIZInstance.getProject(pintProjectId).writeMPRJFile(pstrProjectExtension, pstrProjectDelimiter, pstrGraphDelimiter);
                }else{
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
    
    public static void updateVertexLocationsInLogic(int pintProjectId, int pintGraphId,
            int pintTimeFrameIndex, Map<Integer, Double[]> pmapVertexUILocationsTF){
  
      MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
      IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
      TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
      dynaGraph.updateVertexLocationsInTimeFrame(tf, pmapVertexUILocationsTF);
    }
    
    public static void updateVertexAttributeValue(int pintProjectId, int pintGraphId, 
            int pintTimeFrameIndex, List<String[]> editedRows){

        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);

        editedRows.forEach((row) -> {
            IVertex vertexNode = dynaGraph.getVertex(Integer.parseInt(row[0]));
            vertexNode.updateAttributeValue(row[1], row[2], tf);
        });
    }
    
    public static boolean updateVertexColor(int pintProjectId, int pintGraphId, 
            int pintTimeFrameIndex, Map<Integer, String> pmapVertexColor) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        dynaGraph.updateVertexColors(tf, pmapVertexColor);
        return false;
    }
    
    public static boolean updateVertexColor(int pintProjectId, int pintGraphId,
            int pintTimeFrameIndex, String pstrColor, List<Integer> plstVertexIds) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);        

        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        dynaGraph.updateVertexColors(tf, pstrColor, plstVertexIds);
        return false;
    }

    public static void removeSysColorVerticesAttribute(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        dynaGraph.getVertices(tf).stream().filter((v) -> (v.getSystemAttributer().getAttributeNames()
                .contains(MeerkatSystem.COLOR))).forEachOrdered((v) -> {
                    v.getSystemAttributer().removeAttribute(MeerkatSystem.COLOR);
        });
    }
    
    public static void removeSysColorEdgesAttribute(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        dynaGraph.getEdges(tf).stream().filter((e) -> (e.getSystemAttributer().getAttributeNames()
                .contains(MeerkatSystem.COLOR))).forEachOrdered((e) -> {
                    e.getSystemAttributer().removeAttribute(MeerkatSystem.COLOR);
        });
    }
    
    public static  Map<Integer, String> getAllVertexColors(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
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
    
    public static  Map<Integer, String> getAllEdgeColors(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {        
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
    
    public static  Map<Integer, Boolean> getAllEdgePredValues(int pintProjectId,
            int pintGraphId, int pintTimeFrameIndex) {
        
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
    
    public static Map<Integer, Integer[]> removePredictedEdgesInGraph(int pintProjectId,
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
                        dynaGraph.removeEdge(edge);
                    }
                }
            }
        });
        return mapPredictedEdges;
    }
    
    public static  Map<Integer, Boolean> getAllEdgeDirValues(int pintProjectId, 
            int pintGraphId, int pintTimeFrameIndex) {
        
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
            boolean isDirected = Boolean.parseBoolean(strDirected);
            mapAllEdgeDirValues.put(e.getId(), isDirected);
        });
        
        return mapAllEdgeDirValues;
    }
    
    public static  Map<Integer, String> getAllVertexIconURLs(int pintProjectId, 
            int pintGraphId, int pintTimeFrameIndex) {
        
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
    
    public static  String getVertexColor(int pintProjectId, int pintGraphId, 
            int pintTimeFrameIndex, int pintVertexId) {
        
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
        } else {
            return null;            
        }
        return vColor[0];
    }
    
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
        return eColor[0];
    }
    
    public static Boolean getIsEdgeDirected(int pintProjectId, int pintGraphId, 
            int pintTimeFrameIndex, int pintEdgeId) {
        
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
    
    public static Boolean getIsEdgePredicted(int pintProjectId, int pintGraphId, 
            int pintTimeFrameIndex, int pintEdgeId) {
        
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
    
    public static void updateEdgeAttributeValue(int pintProjectId, int pintGraphId, 
            int pintTimeFrameIndex, List<String[]> editedRows){

        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        editedRows.forEach((row) -> {
            IEdge edgeNode = dynaGraph.getEdge(Integer.parseInt(row[0]));
            edgeNode.updateAttributeValue(row[1], row[2], tf);
        });
    }
    
    public static void updateEdgeAttribute_Predicted (int pintProjectId, int pintGraphId, 
            int pintTimeFrameIndex, int pintEdgeId, boolean pblnIsPredicted) {
        
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
    
    public static void updateEdgeAttribute_Directed (int pintProjectId, int pintGraphId,
            int pintTimeFrameIndex, int pintEdgeId, boolean pblnIsDirected) {
        
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
    
    public static boolean updateEdgeColor(int pintProjectId, int pintGraphId, 
            int pintTimeFrameIndex, String pstrColor, List<Integer> plstEdgeIds) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        dynaGraph.updateEdgeColors(tf, pstrColor, plstEdgeIds);
        return false;
    }
    
    public static boolean updateEdgeColor(int pintProjectId, int pintGraphId, 
            int pintTimeFrameIndex, Map<Integer, String> pmapEdgeColor) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        dynaGraph.updateEdgeColors(tf, pmapEdgeColor);
        return true;
    }
    
    public static boolean updateVertexIconURLs(int pintProjectId, int pintGraphId, 
            int pintTimeFrameIndex, Map<Integer, String> pmapIconURLs) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        dynaGraph.updateVertexIconURLs(tf, pmapIconURLs);
        return true;
    }
    
    public static boolean updateVertexIconURLs(int pintProjectId, int pintGraphId, 
            int pintTimeFrameIndex, String pstrIconURL, List<Integer> plstVertexIds) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
        dynaGraph.updateVertexIconURLs(tf, pstrIconURL, plstVertexIds);
        return true;
    }
    
    public static void updateDefaultVertexColorString(String vertex_color_default) {
        MeerkatSystem.setDefaultVertexColor(vertex_color_default);
    }

    public static void setCommunityColor(int pintProjectId, int pintGraphId, 
            int pintTimeFrameIndex, String strCommunity, String strColor) {

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

    public static String getVertexIconURL(int pintProjectId, int pintGraphId, 
            int pintTimeFrameIndex, int pintVertexId) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        return dynaGraph.getVertex(pintVertexId).getSystemAttributer().
                getAttributeValue(MeerkatSystem.TYPEICONURL, dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex));
    }
    
    public static Map<Integer, Boolean> checkVertexSysAttributePresent(int pintProjectId, int pintGraphId, 
                                                                String attrname, List<Integer> pVertices) {
                
        Map<Integer, Boolean> mapAttrCheck = new HashMap<>();
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        pVertices.forEach((vertexId) -> {
            IVertex v = dynaGraph.getVertex(vertexId);
            if(v.getSystemAttributer().getAttributeNames().contains(attrname))
                mapAttrCheck.put(vertexId, Boolean.TRUE);
            else
                mapAttrCheck.put(vertexId, Boolean.FALSE);
        });
        return mapAttrCheck;
    }
    
    public static Map<Integer, Boolean> checkEdgeSysAttributePresent(int pintProjectId, int pintGraphId, 
                                                                    String attrname, List<Integer> edgeIds) {
        
        Map<Integer, Boolean> mapAttrCheck = new HashMap<>();
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                  BIZInstance.getProject(pintProjectId).getGraph(pintGraphId);
        
        edgeIds.forEach((edgeId) -> {
            IEdge e = dynaGraph.getEdge(edgeId);
            if(e.getSystemAttributer().getAttributeNames().contains(attrname))
                mapAttrCheck.put(edgeId, Boolean.TRUE);
            else
                mapAttrCheck.put(edgeId, Boolean.FALSE);
        });
        return mapAttrCheck;
    }
    
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
