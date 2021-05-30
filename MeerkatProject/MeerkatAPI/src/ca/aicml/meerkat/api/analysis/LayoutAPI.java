/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api.analysis;

import algorithm.graph.layout.algorithms.Layout;
import algorithm.graph.layout.algorithms.LayoutHandler;
import config.LayoutParameters;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.List;
import java.util.Map;
import main.meerkat.MeerkatBIZ;

/**
 *
 * @author aabnar
 */
public class LayoutAPI {
    
    public static void runLayout(int pintProjectID,
            int pintGraphID,
            int pintTimeFrameIndex,
            String pstrLayoutAlgName,
            String[] parameters) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = BIZInstance.getProject(pintProjectID).getGraph(pintGraphID);

        TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        Layout layout = LayoutHandler.getLayoutAlgorithm(igraph,tf,pstrLayoutAlgName, parameters);
        
        List<IVertex> lstV = igraph.getVertices(tf) ;
//        for (IVertex iv : lstV) {
//            System.out.println(""+iv.getId()+"\tX: "+iv.getSystemAttributer().getAttributeValue(MeerkatSystem.X, tf)+"\tY: "+iv.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, tf));
//        }
        
        LayoutHandler.runLayoutAlgorithm(layout, igraph, tf);
    }
    
    public static boolean isDone(int pintProjectID,
            int pintGraphID,
            int pintTimeFrameIndex,
            String pstrLayoutAlgName,
            String[] parameters) {
        
        boolean blnIsDone = false ;
        
        try {
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
            IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                    = BIZInstance.getProject(pintProjectID).getGraph(pintGraphID);

            TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);

            blnIsDone = LayoutHandler.isDone(igraph, tf);
        } catch (Exception ex) {
            
        }        
        return blnIsDone ;
        
    }
    
    public static Map<Integer, Double[]> getResults (int pintProjectID,
            int pintGraphID,
            int pintTimeFrameIndex,
            String pstrLayoutAlgName,
            String[] parameters) {
        
        System.out.println("LayoutAPI.getResults(): Project: "+pintProjectID+"\tGraph: "+pintGraphID+"\tTimeFrame: "+pintTimeFrameIndex+"\tLayout: "+pstrLayoutAlgName);
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = BIZInstance.getProject(pintProjectID).getGraph(pintGraphID);

        TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);
        
        Layout layout = LayoutHandler
                .getLayoutAlgorithm(igraph,tf,pstrLayoutAlgName, parameters);
        
        Map<Integer, Double[]> map = LayoutHandler.getResults(igraph, tf) ;
        for (Integer intID : map.keySet()) {
            System.out.println("LayoutAPI.getResults(): ID: "+intID+"\tX: "+map.get(intID)[0]+"\tY: "+map.get(intID)[1]); 
        }
        
        return LayoutHandler.getResults(igraph, tf);
    }
    public static int stopAlgorithm (
            int pintProjectID,
            int pintGraphID,
            int pintTimeFrameIndex) {
        
        try {
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
            IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                    = BIZInstance.getProject(pintProjectID).getGraph(pintGraphID);

            TimeFrame tf = igraph.getAllTimeFrames().get(pintTimeFrameIndex);

            LayoutHandler.terminateAlgorithm(igraph, tf);
            
            System.out.println("LayoutAPI.stopAlgorithm() : LayoutAlgorithm has stopped!");

            return 1 ;
        } catch (Exception ex) {
            System.out.println("LayoutAPI.stopAlgorithm(): EXCEPTION") ;
            return -401 ;            
        }
    }
    
    /**
     *  Method Name     : getLayoutParameters_Lattitude()
     *  Created Date    : 2016-07-14
     *  Description     : Returns the Key for appending the Latitude Parameter
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static String getLayoutParameters_Latitude() {
        return LayoutParameters.SPATIALLAYOUT_LATITUDE ;        
    }
    
    /**
     *  Method Name     : getLayoutParameters_Longitude()
     *  Created Date    : 2016-07-14
     *  Description     : Returns the Key for appending the Longitude Parameter
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static String getLayoutParameters_Longitude() {
        return LayoutParameters.SPATIALLAYOUT_LONGTITUDE ;        
    }
}
