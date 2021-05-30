/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api.analysis;

import algorithm.graph.metric.IMetric;
import algorithm.graph.metric.MetricHandler;
import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.attributes.writer.AttributeWriter;
import io.metric.writer.MetricWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import main.meerkat.MeerkatBIZ;

/**
 *  Class Name      : GraphMetricAPI
 *  Created Date    : 2016-03-xx
 *  Description     : 
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class GraphMetricAPI {
    
    /**
     *  Method Name     : computeCentrality()
     *  Created Date    : 2016-03-xx
     *  Description     : 
     *  Version         : 1.0
     *  @author         : Afra
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pintTimeFrameIndex : int
     *  @param pstrCentralityClassName : String
     *  @param parrstrParameters : String[]
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-03-24      Talat           Adding parameter parrstrParameters and passing 
     *                                  it to MetricHandler.getMetricAlgorithm() instead of null
     * 
    */
    public static void computeCentrality(int pintProjectID,
            int pintGraphID,
            int pintTimeFrameIndex,
            String pstrCentralityClassName,
            String [] parrstrParameters) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = BIZInstance.getProject(pintProjectID).getGraph(pintGraphID);

        TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        IMetric metricAlg = MetricHandler
                .getMetricAlgorithm(igraph, tf, pstrCentralityClassName, parrstrParameters);
        
        MetricHandler.RunAlgorithm(metricAlg, igraph, tf);
    }
    
    
    public static boolean isDone(int pintProjectID,
            int pintGraphID,
            int pintTimeFrameIndex,
            String pstrCentralityClassName,
            String [] parrstrParameters) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = BIZInstance.getProject(pintProjectID).getGraph(pintGraphID);

        TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        IMetric metricAlg = MetricHandler
                .getMetricAlgorithm(igraph, tf, pstrCentralityClassName, parrstrParameters);
        
        return MetricHandler.isResultsReady(metricAlg.getClass(), igraph, tf);
        
    }
    
    
    public static Map<Integer, Double> getScores(int pintProjectID,
            int pintGraphID,
            int pintTimeFrameIndex,
            String pstrCentralityClassName,
            String [] parrstrParameters ) {
         
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = meerkatApp.getProject(pintProjectID).getGraph(pintGraphID);

        TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        Map<Integer, Double> mapVScores ; // = new HashMap<>();
        
        IMetric metricAlg = MetricHandler
                .getMetricAlgorithm(igraph, tf, pstrCentralityClassName, parrstrParameters);
        
        mapVScores = MetricHandler.getResults(metricAlg.getClass(), igraph, tf);
        
        return mapVScores;
    }
    
    /**
     * This is a temporary solution. Report to talat@amii.ca if you see this
     * @param pintProjectID
     * @param pintGraphID
     * @param pintTimeFrameIndex
     * @param plstSystemAttr
     * @param plstUserAttr
     * @return 
     * @since 2018-01-23
     * @author Talat
     */
    public static Map<Integer, String> getSpecificAttributes(
            int pintProjectID, 
            int pintGraphID, 
            int pintTimeFrameIndex, 
            List<String> plstSystemAttr, 
            List<String> plstUserAttr){
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = meerkatApp.getProject(pintProjectID).getGraph(pintGraphID);

        TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        Map<Integer, String> mapReturnAttrs = new HashMap<>();
        
        List<Integer> lstVertexIDs = igraph.getGraph(tf).getAllVertexIds();        
        plstSystemAttr.add(MeerkatSystem.INDEGREE);        
        plstSystemAttr.add(MeerkatSystem.AUTHORITY);
        plstSystemAttr.add(MeerkatSystem.BETWEENNESS);
        plstSystemAttr.add(MeerkatSystem.CLOSENESS);
        
        // Write the header
        String strHeader = "";
        for (String strAttr : plstUserAttr){                
                strHeader += strAttr + ",";
        }

        for (String strAttr : plstSystemAttr){                
            strHeader += strAttr + ",";
        }
        mapReturnAttrs.put(-1, strHeader);
        
        // Write the actual Data
        for (int intVertexID : lstVertexIDs){            
            IVertex vtx = igraph.getGraph(tf).getVertex(intVertexID);
            String totalString = "";
            
            for (String strAttr : plstUserAttr){                
                totalString += igraph.getGraph(tf).getVertex(intVertexID).getUserAttributer().getAttributeValue(strAttr, tf) + ",";
            }
            
            for (String strAttr : plstSystemAttr){                
                totalString += igraph.getGraph(tf).getVertex(intVertexID).getSystemAttributer().getAttributeValue(strAttr, tf) + ",";
            }
            
            mapReturnAttrs.put(intVertexID, totalString);
        }
        return mapReturnAttrs;
    }
    
    public static Map<Integer, Double> getVertexMetricScores(int pintProjectID,
            int pintGraphID,
            int pintTimeFrameIndex,
            String pstrCentralityClassName,
            String [] parrstrParameters ) {
         
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = meerkatApp.getProject(pintProjectID).getGraph(pintGraphID);

        TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        Map<Integer, Double> mapVScores ; // = new HashMap<>();
        
        IMetric metricAlg = MetricHandler
                .getMetricAlgorithm(igraph, tf, pstrCentralityClassName, parrstrParameters);
        
        //setting the metricAttribute of authority and hub here
        // since they're run via HITS.
        if(pstrCentralityClassName.equals("Authority"))
            metricAlg.metricAttribute=MeerkatSystem.AUTHORITY;
        else if(pstrCentralityClassName.equals("Hub"))
            metricAlg.metricAttribute=MeerkatSystem.HUB;
        
        mapVScores = computeVertexMetricScore(igraph, tf, metricAlg.metricAttribute);
        
        return mapVScores;
    }

    public static int exportMetrics(int intProjectID, int intGraphID, int intcurrentTimeFrameIndex, 
            Map<String, String[]> mapMetricParameter, String filePath) {
        
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = meerkatApp.getProject(intProjectID).getGraph(intGraphID);

        Map<String, Map<Integer, Double>> mapMetrics = new HashMap<>();

        for(Map.Entry<String, String[]> entry : mapMetricParameter.entrySet()){
            
            computeCentrality(intProjectID, intGraphID, intcurrentTimeFrameIndex, entry.getKey(), entry.getValue()) ;
            while (!GraphMetricAPI.isDone(intProjectID, intGraphID, intcurrentTimeFrameIndex, entry.getKey(), entry.getValue())) {
                // Do Nothing
                // System.out.println("StatisticsValue.StatisticsValue(): Inside while loop - Column Number: "+intColumnNumber);
            }
            
            Map<Integer, Double> mapResults = GraphMetricAPI.getScores(intProjectID, intGraphID, intcurrentTimeFrameIndex, entry.getKey(), entry.getValue()) ;
            
            mapMetrics.put(entry.getKey(), mapResults);
        }
        
        int intError = MetricWriter.Write(mapMetrics, filePath);
        
        return intError;        
    }
    
    /**
     * Exporting the System Attributes
     * @param pintProjectID
     * @param pintGraphID
     * @param pintTimeFrameIndex
     * @param psetUserAttributes
     * @param psetSystemAttributes
     * @param pstrFilePath
     * @return Error Code
     */
    public static int exportAttributes(
            int pintProjectID, 
            int pintGraphID, 
            int pintTimeFrameIndex,
            Set<String> psetUserAttributes, 
            Set<String> psetSystemAttributes, 
            String pstrFilePath) {
        
        int intError = 0;
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = meerkatApp.getProject(pintProjectID).getGraph(pintGraphID);
        
        AttributeWriter<IVertex, IEdge<IVertex>> writer = new AttributeWriter();
        intError = writer.WriteVertices(igraph, igraph.getAllTimeFrames().get(pintTimeFrameIndex), psetUserAttributes, psetSystemAttributes, pstrFilePath);
        
        return intError;        
    }

    public static Map<Integer, Double> computeVertexMetricScore(IDynamicGraph<IVertex, IEdge<IVertex>> igraph, TimeFrame tf, String metricAttribute) {
        Map<Integer, Double> vertexMetricValues = new HashMap<>();

        if(igraph.getGraph(tf).getVertexNumericalAttributeNames().contains(metricAttribute)){
            
            for(IVertex v : igraph.getVertices(tf)){
                vertexMetricValues.put(v.getId(), Double.parseDouble(v.getSystemAttributer().getAttributeValue(metricAttribute, tf)));
            }
        }
        
        return vertexMetricValues;
    }
}
