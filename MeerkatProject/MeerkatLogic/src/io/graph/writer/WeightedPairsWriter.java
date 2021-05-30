package io.graph.writer;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import datastructure.core.graph.classinterface.*;
import java.io.File;
import java.util.List;


/**
 * Write a MeerkatGraph to a file using the .wpairs format.
 * 
 * The .wpairs format is simply:
 * 
 * e1.v1\te1.v2\te1.w\n e2.v1\te2.v2\te2.w\n ... en.v1\ten.v2\ten.w\n
 * 
 * where ex is the x'th edge, and v1 and v2 are its source and destination, and
 * w is the weight of the edge.
 * 
 * @author Matt Gallivan
 * @param <V>
 * @param <E>
 * 
 * Edit history
 * Date         Author          Description
 * 2016-04-04   Afra            Added 3 version of write:
 *                              - wihtout timeframe: writes the whole graph to file(s)
 *                              - with timeframe: writes a specific timeframe to a file.
 *                              - with timeframe and list of vertices: writes a subset of a graph at a specific timeframe to a file.  
 * 
 */
public class WeightedPairsWriter<V extends IVertex, E extends IEdge<V>> extends GraphWriter<V,E> {

    /**
     *
     */
    public static final String strExtension = ".wpairs";
    
    /**
     * 
     * @param pIGraph
     * @param fileName
     * @return 
     */
    @Override
    public String write(IDynamicGraph<V,E> pIGraph, String fileName) {
        int intErrorCode = 0;
        StringBuilder strFileNames = new StringBuilder();
        int intNumTF = pIGraph.getAllTimeFrames().size();
        System.out.println("PairsWriter.write(): number of timeframes: " + intNumTF);
        if ( intNumTF > 1) {
            for (int intTfIndex = 0 ; intTfIndex < intNumTF ; intTfIndex++) {
                String fn = fileName + "_" + intTfIndex;
                
                String filepath = write(pIGraph, pIGraph.getAllTimeFrames().get(intTfIndex), fn);
                strFileNames.append(filepath).append(",");
            }
        } else {
            String filepath = write(pIGraph, pIGraph.getAllTimeFrames().get(0), fileName);
            strFileNames.append(filepath);
        }
        return strFileNames.toString();
    }
    
    /**
     * MethodName : write
     * Description : writes the graph in pairs format along with edge weights for
     *              each edge. And nodes are written by their FILE_ID.
     * @param pIGraph : Graph
     * @param tf : TimeFrame
     * @param fileName : file name to be written 
     * @return 
     */
    @Override
    public String write(IDynamicGraph<V,E> pIGraph, TimeFrame tf, String fileName) {
        int intErrorCode = 0;
        String fn = fileName +  strExtension;
        
        //generating all files in the /temp directory.
        String curDir = System.getProperty("user.dir");
        File tempDir = new File (curDir+"/temp");
        if(!tempDir.exists())
            tempDir.mkdirs();
        
        FileWriter fstream;
        try {
            File f = new File(tempDir+"/"+fn);
            fstream = new FileWriter(f);

            BufferedWriter out = new BufferedWriter(fstream);
            for (E e : pIGraph.getEdges(tf)) {
                out.write(e.getSource().getUserAttributer()
                        .getAttributeValue(MeerkatSystem.FILE_ID, tf)
                        + "\t"
                        + e.getDestination().getUserAttributer()
                        .getAttributeValue(MeerkatSystem.FILE_ID, tf)
                        + "\t"
                        + e.getWeight() + "\n");
            }
            out.close();
            return f.getAbsolutePath();
        } catch (IOException e1) {
            intErrorCode = -1000701;
        }
        return null;
    }

    @Override
    public String write(IDynamicGraph<V, E> pIGraph, TimeFrame tf, List<V> plstVertices, String pstrPath2FileName) {
        int intErrorCode = 0;
        String fn = pstrPath2FileName +  strExtension;
        FileWriter fstream;
        try {
            File f = new File(fn);
            fstream = new FileWriter(f);

            BufferedWriter out = new BufferedWriter(fstream);
            for (V v : plstVertices) {
                for (V u : plstVertices) {
                    if (v.equals(u)) {
                        continue;
                    }
                    E e = pIGraph.findEdge(v, u, tf);
                    if (e != null) {
                        out.write(e.getSource().getUserAttributer()
                            .getAttributeValue(MeerkatSystem.FILE_ID, tf)
                            + "\t"
                            + e.getDestination().getUserAttributer()
                            .getAttributeValue(MeerkatSystem.FILE_ID, tf)
                            + "\t"
                            + e.getWeight() + "\n");
                    }
                }
            }
            out.close();
            f.getAbsoluteFile();
        } catch (IOException e1) {
            intErrorCode = -1000701;
        }
        
        return null;
    }
}
