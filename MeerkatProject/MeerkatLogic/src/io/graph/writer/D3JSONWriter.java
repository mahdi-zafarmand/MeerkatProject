/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.graph.writer;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *  Class Name      : JSONRewriter
 *  Created Date    : 2015-06-xx
 *  Description     : Used to writing of the output Json file
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-04-04      Afra            added 3 version of write method: write the whole graph all timeframe in file(s)
 *                                                                   write a specific timeframe (used by community mining algorithms)
 *                                                                   write a subset of a graph (defined by a list of vertices) from a specific timeframe to a file. (used by community mining algorithms)
 * @param <V>
 * @param <E>
 * 
*/
public class D3JSONWriter<V extends IVertex,E extends IEdge<V>> extends GraphWriter<V,E>{
    
    /**
     *
     */
    public static final String strExtension = ".json";

    /**
     *  Method Name     : write
     *  Created Date    : 2015-06-xx
     *  Description     : Converting a graph to a Json file on disk
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pGraph : IGraph<Vertex, Edge>
     *  @param pblnCustomLayout : boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-08-31      Talat           Moved to a io.graph.reader package from io.graph and implements the IWriter interface
     *  2015-08-27      Talat           Changed the string concatenation to '.append()' instead of '+'
     * @return 
     * 
    */
    public String write(IDynamicGraph<V,E> pGraph, 
            String pstrPath2OutputFile ) {
             
        int intErrorCode = 0;
        StringBuilder strFileNames = new StringBuilder();
               
        int intNumTF = pGraph.getAllTimeFrames().size();
//        System.out.println("PairsWriter.write(): number of timeframes: " + intNumTF);
        
        for (int intTfIndex = 0 ; intTfIndex < intNumTF ; intTfIndex++) {
            String fn = null;
            if ( intNumTF > 1) {
                fn = pstrPath2OutputFile + ("_" + intTfIndex + strExtension); // Appending the extension to the filepath
            } else {
                fn = pstrPath2OutputFile + strExtension;
            }
            strFileNames.append(fn);

//            System.out.println("D3JSONWriter.Convert(): Output File Path: "+ fn);
            /* Conver the graph to the following format 
            {
                "nodes":[
                    {
                        "name":"HR",
                        "community":1, // MANDATORY - If there is no community mining, alll nodes will be in the same community
                    },
                    {
                        "name":"Receptionist", // MANDATORY 
                        "community":1, // MANDATORY - If there is no community mining, alll nodes will be in the same community
                        "x":"10", // MANDATORY FOR CUSTOM LAYOUTS
                        "y":"10", // MANDATORY FOR CUSTOM LAYOUTS
                        "fixed":true // MANDATORY FOR CUSTOM LAYOUTS        
                    }
                ],
                "links":[
                    {
                        "source":1, // MANDATORY
                        "target":0, // MANDATORY 
                        "weight":10
                    },
                    {
                        "source":2, // MANDATORY 
                        "target":0, // MANDATORY 
                        "weight":8
                    }
                ]
            }
            */        

            StringBuilder strJSONNodes = new StringBuilder();        
            strJSONNodes.append("{\n\"nodes\":\n[\n");
            // for (IVertex vtxCurrent : pGraph.getVertices().values()) {
            for (V vtxCurrent : pGraph.getVertices(pGraph.getAllTimeFrames().get(intTfIndex))) {
                strJSONNodes.append("{");

                for (String strAttrName : vtxCurrent.getUserAttributer().getAttributeNames()) {
                    
                    if (strAttrName.equals(MeerkatSystem.FILE_ID)) {
                        String attVal = vtxCurrent.getUserAttributer()
                                .getAttributeValue(strAttrName,pGraph.getAllTimeFrames().get(intTfIndex));
                        
                        if (attVal.startsWith("\"")) {
                            attVal = attVal.substring(1,attVal.length());
                        }
                        if (attVal.endsWith("\"")) {
                            attVal = attVal.substring(0,attVal.length()-1);
                        }
                        strJSONNodes.append("\"").append("id").append("\":\"")
                            .append(attVal)
                            .append("\",");  
                    } else if(!strAttrName.equals(MeerkatSystem.MEERKAT_ID)){
                       
                        String attVal = vtxCurrent.getUserAttributer()
                                .getAttributeValue(strAttrName,pGraph.getAllTimeFrames().get(intTfIndex));
                        
                        if (attVal.startsWith("\"")) {
                            attVal = attVal.substring(1,attVal.length());
                        }
                        if (attVal.endsWith("\"")) {
                            attVal = attVal.substring(0,attVal.length()-1);
                        }
                        
                        strJSONNodes.append("\"").append(strAttrName).append("\":\"")
                            .append(attVal)
                            .append("\",");  
                    }
                }

                for (String strAttrName : vtxCurrent.getSystemAttributer().getAttributeNames()) {
                    // System.out.println("D3JSONWriter.write(): System Attributes: "+strAttrName+"\t"+vtxCurrent.getUserAttributer().getAttributeValue(strAttrName));
                    
                    if (strAttrName.equals(MeerkatSystem.X)) {
                        strJSONNodes.append("\"").append("x").append("\":\"")
                            .append(vtxCurrent.getSystemAttributer()
                                .getAttributeValue(strAttrName,pGraph.getAllTimeFrames().get(intTfIndex)))
                            .append("\",");       
                    } else if (strAttrName.equals(MeerkatSystem.Y)) {
                        strJSONNodes.append("\"").append("y").append("\":\"")
                            .append(vtxCurrent.getSystemAttributer()
                                .getAttributeValue(strAttrName,pGraph.getAllTimeFrames().get(intTfIndex)))
                            .append("\",");  
                        strJSONNodes.append("\"fixed\":").append(true).append(",");
                    } else if(!strAttrName.equals(MeerkatSystem.MEERKAT_ID)){ 
                        strJSONNodes.append("\"").append(strAttrName).append("\":\"")
                            .append(vtxCurrent.getSystemAttributer()
                            .getAttributeValue(strAttrName,pGraph.getAllTimeFrames().get(intTfIndex)))
                            .append("\",");      
                    }
                }

                /* TODO: TOBEREMOVED - Adding the community - Need to find a better way of representing communities via the vertex itself */
                // -- START --
//                strJSONNodes.append("\"community\":").append("\"1\"").append(",");
                // -- END -- 

                strJSONNodes.deleteCharAt(strJSONNodes.length()-1);
                strJSONNodes.append("},\n");
            }

            // Remove the last character
            strJSONNodes.delete(strJSONNodes.length()-2, strJSONNodes.length());
            strJSONNodes.append("\n],\n");

            // Building the Links (Edges)
            StringBuilder strJSONLinks = new StringBuilder();
            strJSONLinks.append("\"links\":\n[\n");
            for (E edgCurrent : pGraph.getEdges(pGraph.getAllTimeFrames().get(intTfIndex))) {
                strJSONLinks.append("{\"source\":").append(edgCurrent.getSource()
                        .getUserAttributer().getAttributeValue(MeerkatSystem.FILE_ID, 
                                pGraph.getAllTimeFrames().get(intTfIndex)));
                strJSONLinks.append(",\"target\":").append(edgCurrent.getDestination()
                .getUserAttributer().getAttributeValue(MeerkatSystem.FILE_ID, 
                        pGraph.getAllTimeFrames().get(intTfIndex)));

                strJSONLinks.append(",\"directed\":").append(edgCurrent.isDirected());
                for (String attrCurrent : edgCurrent.getUserAttributer().getAttributeNames()) {
                    strJSONLinks.append(",\"").append(attrCurrent)
                        .append("\":\"")
                        .append(edgCurrent.getUserAttributer().
                            getAttributeValue(attrCurrent,pGraph.getAllTimeFrames().get(intTfIndex)))
                        .append("\"");               
                }

                for (String attrCurrent : edgCurrent.getSystemAttributer().getAttributeNames()) {
                    strJSONLinks.append(",\"")
                        .append(attrCurrent)
                        .append("\":\"").append(edgCurrent.getSystemAttributer().
                            getAttributeValue(attrCurrent,pGraph.getAllTimeFrames().get(intTfIndex)))
                        .append("\"");               
                }

                strJSONLinks.append("},\n");
            }

            if (pGraph.getEdges(pGraph.getAllTimeFrames().get(intTfIndex)).size() > 0) {
                strJSONLinks.delete(strJSONLinks.length()-2, strJSONLinks.length());
            }
            strJSONLinks.append("\n]\n}");


            // Write the output to the file
            try {
                /* Write the updated conf */
                File file = new File(fn);
                // if file doesnt exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(strJSONNodes.toString());
                bw.write(strJSONLinks.toString());
                bw.close();
            } catch (IOException exIO) {
                intErrorCode = -1000601;
            }
        }
        return strFileNames.toString();
    }

    
    /**
     * Description: write method to write a specific timeframe of a dynamic graph to file.
     *                It does not have any implementation for D3JSONWriter as it has never used yet.
     * @param pIGraph
     * @param tf
     * @param pstrPath2FileName
     * @return 
     */
    @Override
    public String write(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String pstrPath2FileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Description: write method to write a subset of a dynamic graph (described by vertices in a list) at a specific time frame to file.
     *                It does not have any implementation for D3JSONWriter as it has never used yet.
     * @param pIGraph
     * @param tf
     * @param plstVertices
     * @param pstrPath2FileName
     * @return 
     */
    @Override
    public String write(IDynamicGraph<V, E> pIGraph, TimeFrame tf, List<V> plstVertices, String pstrPath2FileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
