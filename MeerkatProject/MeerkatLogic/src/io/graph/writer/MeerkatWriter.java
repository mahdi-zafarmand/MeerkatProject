package io.graph.writer;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.utility.Utilities;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

// TODO : Dynamic Graphs
/**
 * Class Name : MeerkatWriter Created Date : 2015-08-xx Description : To write
 * the .meerkat file in a give format Version : 1.0
 *
 * @author : Afra
 *
 * EDIT HISTORY (most recent at the top) 
 * 
 * Date         Author      Description
 * 2018-02-13   Talat       Adding to resources folder
 * 2016 Apr 21  Afra        added implementation for write(graph,timeframe, path): writing only one timeframe to the file.
 * @param <V>
 * @param <E>
 *
 */
public class MeerkatWriter<V extends IVertex, E extends IEdge<V>>
        extends GraphWriter<V, E> {

    /**
     *
     */
    public static final String strExtension = ".meerkat";

    /**
     * Method Name : writeFile Created Date : 2015-08-xx Description : Takes the
     * output file path & graph as input and writes a .meerkat file Version :
     * 1.0
     *
     * @author : Afra
     *
     * @param pIGraph : IGraph<V,E>
     * @param pstrPath2FileName : String
     * @return
     *
     */
    @Override
    public String write(IDynamicGraph<V, E> pIGraph, 
            String pstrPath2FileName) {
        
        System.out.println("MeerkatWriter.write() : writing all timeframes");

        int intErrorCode = 0;
        String strFilePath = pstrPath2FileName + strExtension ;
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(strFilePath));

            File f = new File(strFilePath);
            String strProjectDir = f.getParent();
            // Timeframes
            for (TimeFrame timeframe : pIGraph.getAllTimeFrames()) {
                writeTimeFrame(out, pIGraph, timeframe, strProjectDir);
            }
            

            out.close();
            return strFilePath;
        } catch (IOException ex) {
            Logger.getLogger(MeerkatWriter.class.getName()).log(Level.SEVERE, null, ex);
            strFilePath = null ;
            intErrorCode = -1000401;
        } 
        return null;
    }
    
    /**
     * MethodName: write
     * Description: writes only one tiemframe to file
     * Version: 1.0
     * Author: Afra
     * 
     * @param pIGraph
     *          dynamic graph
     * @param tf
     *          time frame
     * @param pstrPath2FileName
     *          full path (without extension) to the file name
     * @return 
     *          full path (with extension) to the file.
     */
    @Override
    public String write(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String pstrPath2FileName) {
        
        BufferedWriter out = null;
        try {
            String strFilePath = pstrPath2FileName + strExtension;
            File f = new File(strFilePath);
            String strProjectDir = f.getParent();
            out = new BufferedWriter(new FileWriter(strFilePath));
            writeTimeFrame(out, pIGraph, tf, strProjectDir);
            
            return strFilePath;
        } catch (IOException ex) {
            Logger.getLogger(MeerkatWriter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(MeerkatWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
        return null;
    }

    private void writeTimeFrame (BufferedWriter out,IDynamicGraph<V, E> pIGraph, TimeFrame timeframe, String pstrProjectDir) {
        try {
            out.write("*Timeframe\n");
            out.write(timeframe.toString() + "\n");
            
            // Vertices
            out.write("*Vertices\n");
            for (IVertex vertex : pIGraph.getVertices(timeframe)) {
                String id = vertex.getUserAttributer()
                        .getAttributeValue(MeerkatSystem.FILE_ID, timeframe);
                
                
                int intAttSize = vertex.getUserAttributer().getAttributeNames().size() +
                        vertex.getSystemAttributer().getAttributeNames().size();
                
                if (intAttSize > 0) {
                    out.write(id);
                    // out.write(" {");
                    StringBuilder strB = new StringBuilder();
                    strB.append(" {");
                    // Attributes
                    for (String attName : vertex.getUserAttributer().getAttributeNames()) {
                        if (!attName.equals(MeerkatSystem.MEERKAT_ID)
                                && !attName.equals(MeerkatSystem.FILE_ID)) {
                            
                            String strValue = vertex.getUserAttributer()
                                    .getAttributeValue(attName,timeframe);
                            
                            if (strValue != null) {
                                strB.append(attName)
                                        .append("=")
                                        .append(vertex.getUserAttributer()
                                                .getAttributeValue(attName,timeframe))
                                        .append(";");
                            }
                        }
                    }
                    
                    String strIconDir = pstrProjectDir + "/" + MeerkatSystem.PROJECT_RESOURCES_DIR +
                            MeerkatSystem.PROJECT_ICONS_DIR;
                    
                    for (String attName : vertex.getSystemAttributer().getAttributeNames()) {
                        String attValue = vertex.getSystemAttributer().getAttributeValue(attName, timeframe);
                        
                        if(attValue != null){
                                                        
                            if(attName.equalsIgnoreCase(MeerkatSystem.TYPEICONURL)) {
                                if(!attValue.isEmpty()){
                                    String strSource = attValue.substring(attValue.indexOf(":")+1, attValue.length());
                                    String fileName = Utilities.getFileNameWithExtension(attValue);
                                    String strDestPath = strIconDir + fileName;
                                    if(!strDestPath.equalsIgnoreCase(strSource)){
                                        FileUtils.copyFile(new File(strSource), new File(strDestPath));
                                    }
                                    attValue = "file:" + strDestPath;
                                }
                            }
                            
                            strB.append(attName)
                                    .append("=")
                                    .append(attValue)
                                    .append(";");
                        }
                    }
                    
                    strB.append("}");
                    
                    if (!strB.toString().endsWith("{}")) {
                        out.write(strB.toString());
                    }
                }
                
                out.write("\n");
            }
            
            // Edges
            out.write("*Edges\n");
            for (IEdge edge : pIGraph.getEdges(timeframe)) {
//                    System.out.println("MeerkatWriter : write edge for tf " + timeframe);
                if (!edge.isDirected()) {
                    out.write(edge.getSource().getUserAttributer()
                            .getAttributeValue(MeerkatSystem.FILE_ID, timeframe)
                            + "\t"
                            + edge.getDestination().getUserAttributer()
                                    .getAttributeValue(MeerkatSystem.FILE_ID, timeframe));
                    
                    int intAttSize = edge.getUserAttributer().getAttributeNames().size() +
                            edge.getSystemAttributer().getAttributeNames().size();
                    if (intAttSize > 0) {
                        StringBuilder strB = new StringBuilder();
                        strB.append(" {");
                        // Attributes
                        for (String attName : edge.getUserAttributer()
                                .getAttributeNames()) {
                            
                            String strValue = edge.getUserAttributer()
                                    .getAttributeValue(attName,
                                            timeframe);
                            
                            if (strValue != null) {
                                strB.append(attName)
                                        .append("=")
                                        .append(edge.getUserAttributer()
                                                .getAttributeValue(attName,
                                                        timeframe))
                                        .append(";");
                            }
                        }
                        
                        for (String attName : edge.getSystemAttributer()
                                .getAttributeNames()) {
                            
                            String strValue = edge.getSystemAttributer()
                                    .getAttributeValue(attName,
                                            timeframe);
                            if (strValue != null){
                                strB.append(attName)
                                        .append("=")
                                        .append(edge.getSystemAttributer()
                                                .getAttributeValue(attName,
                                                        timeframe))
                                        .append(";");
                            }
                        }
                        
                        strB.append("}");
                        if (!strB.toString().endsWith("{}")) {
                            out.write(strB.toString());
                        }
                        
                    }
                    out.write("\n");
                }
            }
            
            // Arcs
            out.write("*Arcs\n");
            
            for (IEdge edge : pIGraph.getEdges(timeframe)) {
                if (edge.isDirected()) {
                    int eid = edge.getId();
                    out.write(edge.getSource().getUserAttributer()
                            .getAttributeValue(MeerkatSystem.FILE_ID, timeframe)
                            + "\t"
                            + edge.getDestination().getUserAttributer()
                                    .getAttributeValue(MeerkatSystem.FILE_ID, timeframe));
                    
                    int intAttSize = edge.getUserAttributer().getAttributeNames().size() +
                            edge.getSystemAttributer().getAttributeNames().size();
                    if (intAttSize > 0) {
                        StringBuilder strB = new StringBuilder();
                        strB.append(" {");
                        // Attributes
                        for (String attName : edge.getUserAttributer()
                                .getAttributeNames()) {
                            strB.append(attName)
                                    .append("=")
                                    .append(edge.getUserAttributer()
                                            .getAttributeValue(attName,
                                                    timeframe))
                                    .append(";");
                        }
                        
                        for (String attName : edge.getSystemAttributer()
                                .getAttributeNames()) {
                            strB.append(attName)
                                    .append("=")
                                    .append(edge.getSystemAttributer()
                                            .getAttributeValue(attName,
                                                    timeframe))
                                    .append(";");
                        }
                        
                        strB.append("}");
                        
                        if (!strB.toString().endsWith("{}")) {
                            out.write(strB.toString());
                        }
                    }
                    // Weights
                    // double weight = edge.getWeigher().getWeight();
                    // out.write("weight=" + weight + ";");
                    out.write("\n");
                }
            }
            out.write("*End\n");
        } catch (IOException ex) {
            Logger.getLogger(MeerkatWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    @Override
    public String write(IDynamicGraph<V, E> pIGraph, TimeFrame tf, List<V> plstVertices, String pstrPath2FileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
