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


public class ExportAPI {
 
    /**
     * 
     * @param pintProjectID
     * @param pintGraphID
     * @param pintTimeFrameindex
     *          timeframe index. can be null. if null all timeframes will be exported to file.
     * @param pstrFilePathNoExt
     *          complete path to file name (without extension)
     * @param pstrWriterID
     * @param parrstrParamters
     * @return 
     *      string filename(s) [incase of multiple files (for several timeframes in static based file formats), the return would be comma separated]
     */
    public static String ExportGraph(int pintProjectID, 
            int pintGraphID, 
            Integer pintTimeFrameindex,
            String pstrFilePathNoExt, 
            String pstrWriterID, 
            String [] parrstrParamters) {
        int intErrorCode = -1 ;
        
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
            // intErrorCode = DataWriter.writeGraph(igraph, pstrWriterID, pstrFilePathNoExt) ;
            
            return filename;
        } catch (Exception ex) {
            System.out.println("ExportAPI.ExportGraph(): EXCEPTION");
            ex.printStackTrace();

            intErrorCode = -1 ;
        }
        
        return null;
    }
}
