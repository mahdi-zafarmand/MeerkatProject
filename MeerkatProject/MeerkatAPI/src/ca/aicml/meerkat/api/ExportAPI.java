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
import io.DataWriter;
import main.meerkat.MeerkatBIZ; 

/**
 *
 * @author mahdi
 */
public class ExportAPI {
    public static String ExportGraph(int pintProjectID, int pintGraphID, 
            Integer pintTimeFrameindex, String pstrFilePathNoExt, 
            String pstrWriterID, String [] parrstrParamters) {
        
        try {
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
            IDynamicGraph<IVertex, IEdge<IVertex>> igraph
                = BIZInstance.getProject(pintProjectID).getGraph(pintGraphID);

            // Call the Data Writer
            TimeFrame tf = null;
            if (pintTimeFrameindex != null) {
                tf = igraph.getAllTimeFrames().get(pintTimeFrameindex);
            }
            String filename = DataWriter.writeGraph(igraph, tf, pstrWriterID, pstrFilePathNoExt);
            
            return filename;
        } catch (Exception ex) {
            System.out.println("ExportAPI.ExportGraph(): EXCEPTION");
            ex.printStackTrace();
        }
        
        return null;
    }
}
