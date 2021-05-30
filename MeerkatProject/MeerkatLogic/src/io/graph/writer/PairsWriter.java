package io.graph.writer;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import datastructure.core.graph.classinterface.*;
import java.util.List;

/**
 * Write a MeerkatGraph to a file using the .pairs format.
 *
 * The .pairs format is simply:
 *
 * e1.v1\te1.v2\n e2.v1\te2.v2\n ... en.v1\ten.v2\n
 *
 * where ex is the x'th edge, and v1 and v2 are its source and destination.
 *
 * @author Matt Gallivan
 *
 * Edit history
 * Date         Author          Description
 * 2016 Apr 21  Afra            changed write(Graph,fileName) to call the other write(Graph,tf,fileName) 
 *                              for each timeframe (removed the duplicate code)
 * 2016-04-04   Afra            Added 3 version of write:
 *                              - wihtout timeframe: writes the whole graph to file(s)
 *                              - with timeframe: writes a specific timeframe to a file.
 *                              - with timeframe and list of vertices: writes a subset of a graph at a specific timeframe to a file.    
 * @param <V>    
 * @param <E>    
 */
public class PairsWriter<V extends IVertex, E extends IEdge<V>> 
    extends GraphWriter<V, E> {

    /**
     *
     */
    public static String strExtension = ".pairs";

    /**
     * MethodName: Write
     * Description: writes all timeframes of the graph each in a separate file
     * Version: 2.0
     * Author: Afra
     * 
     * @param pIGraph
     * @param fileName
     *          fileName without extension
     * @return 
     *          list of file(s) paths [each for one timeframe] in a comma separated string.
     * 
     * EDIT HISTORY
     * DATE         AUTHOR      DESCRIPTION
     * 2015 Apr 25  Afra        removed the duplicate code by calling write(graph, timeframe, filePath)
     */
    @Override
    public String write(IDynamicGraph<V, E> pIGraph, String fileName) {
        StringBuilder strFileNames = new StringBuilder();
        int intNumTF = pIGraph.getAllTimeFrames().size();
//        System.out.println("PairsWriter.write(): number of timeframes: " + intNumTF);
        
        for (TimeFrame tf : pIGraph.getAllTimeFrames()) {
            FileWriter fstream;
            String fn = fileName + "_" + pIGraph.getAllTimeFrames().indexOf(tf);
            String result = write(pIGraph,tf, fn);
            if (result != null) {
                if ( intNumTF > 1 || pIGraph.getAllTimeFrames().indexOf(tf) < intNumTF -1) {
                    strFileNames.append(result).append(",");
                } else {
                    strFileNames.append(result);
                }
            }
        }
        return strFileNames.toString();
    }
    
    /**
     * MethodName : write
     * Descrition: writes only one timeframe of the graph to file
     * Version: 1.0
     * Author: Afra
     * 
     * @param pIGraph
     *          dynamicGraph
     * @param tf
     *          Timeframe
     * @param fileName
     *          full file path without extension
     * @return 
     *          full file path with extension
     * 
     */
    public String write(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String fileName) {
        int intErrorCode = 0;
        String strFileName;
        FileWriter fstream;
        try {
            String fn = fileName  + strExtension;
            strFileName = fn;
            fstream = new FileWriter(fn);

            BufferedWriter out = new BufferedWriter(fstream);
            for (E e : pIGraph.getEdges(tf)) {
                out.write(e.getSource().getUserAttributer()
                        .getAttributeValue(MeerkatSystem.FILE_ID, tf)
                        + "\t"
                        + (e.getDestination().getUserAttributer()
                                .getAttributeValue(MeerkatSystem.FILE_ID, tf)
                                + "\n"));
            }
            out.close();
            fstream.close();
            return strFileName;
        } catch (IOException e1) {
            e1.printStackTrace();
            intErrorCode = -1000301;
        }
        
        return null;
    }

    /**
     * Method Name: write
     * Description: writes a subgraph (described by set of vertices) of a specific timeframe of the graph
     * @param pIGraph
     *          dynamic graph
     * @param tf
     *          timeframe
     * @param plstVertices
     *          subset of graph described by set of vertices
     * @param pstrPath2FileName
     *          full file path without extension
     * @return 
     *          full file path with extension
     */
    @Override
    public String write(IDynamicGraph<V, E> pIGraph, TimeFrame tf, 
            List<V> plstVertices, String pstrPath2FileName) {
        int intErrorCode = 0;
        String strFileName;
        FileWriter fstream;
        try {
            String fn = pstrPath2FileName  + strExtension;
            strFileName = fn;
            fstream = new FileWriter(fn);

            BufferedWriter out = new BufferedWriter(fstream);
            for (V v : plstVertices) {
                for (V u : plstVertices) {
                    if (v.equals(u)) {
                        continue;
                    }
                    IEdge e = pIGraph.findEdge(v,u,tf);
                    if (e != null) {
                        out.write(e.getSource().getUserAttributer()
                                .getAttributeValue(MeerkatSystem.FILE_ID, tf)
                                + "\t"
                            + (e.getDestination().getUserAttributer()
                                    .getAttributeValue(MeerkatSystem.FILE_ID, tf)
                                    + "\n"));
                    }
                }
            }
            out.close();
            fstream.close();
            return strFileName;
        } catch (IOException e1) {
            e1.printStackTrace();
            intErrorCode = -1000301;
        }
        
        return null;
    }

}
