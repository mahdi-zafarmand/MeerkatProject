/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api;

import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import main.meerkat.MeerkatBIZ;


/**
 *
 * @author mahdi
 */
public class VertexAPI {
    public static void updateIconURL(int pintProjectID, int pintGraphID, 
            int pintTimeFrameIndex, String pstrIconURL) {
        
        MeerkatBIZ mktappBusiness = MeerkatBIZ.getMeerkatApplication();
        IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph
                = mktappBusiness.getProject(pintProjectID).getGraph(pintGraphID);
        
        TimeFrame tf = dynaGraph.getAllTimeFrames().get(pintTimeFrameIndex);
    }
}
