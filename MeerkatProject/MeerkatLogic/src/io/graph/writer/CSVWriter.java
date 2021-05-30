/*Licenced at Meerkat@ualberta*/
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
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName : CSVWriter
 * Description : Writes the graph in the csv format (all attribute names in the first row and each row show the attribute values for each vertex)
 * Version : 1.0
 * Author: Afra
 * 
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public class CSVWriter<V extends IVertex, E extends IEdge<V>> extends GraphWriter<V,E>{

    /**
     *
     */
    public static final String extension = ".csv";
    
    /**
     * MethodName: write
     * Description: write the whole dynamic graph to file(s) each time frame in a different file
     * Version: 1.0
     * Author: Afra
     * 
     * @param pIGraph
     *          DynamicGraph
     * @param pstrPath2FileName
     *          full path (without extension) to the file and file name (no extension)
     * @return 
     *          The complete path (with extension) to file(s) [comma separated in terms of multiple files].
     */
    @Override
    public String write(IDynamicGraph<V, E> pIGraph, String pstrPath2FileName) {
        StringBuilder strFileNames = new StringBuilder();
        for (TimeFrame tf : pIGraph.getAllTimeFrames()) {
            String strFileName;
            if (pIGraph.getAllTimeFrames().size() > 1) {
                strFileName = pstrPath2FileName + "_" + pIGraph.getAllTimeFrames().indexOf(tf);
            } else {
                strFileName = pstrPath2FileName;
            }
            
            String returnFN = write(pIGraph, tf, strFileName);
            
            if (pIGraph.getAllTimeFrames().size() > 1 || pIGraph.getAllTimeFrames().indexOf(tf) < pIGraph.getAllTimeFrames().size() -1) {
                strFileNames.append(returnFN).append(",");
            } else {
                strFileNames.append(returnFN);
            }
            
        }
        return strFileNames.toString();
    }

    /**
     * MethodName: write
     * Description: writes a specific timeframe of the graph to file
     * Version: 1.0
     * Author: Afra
     * 
     * @param pIGraph
     *          DynamicGraph
     * @param tf
     *          TimeFrame
     * @param pstrPath2FileName
     *          full path (without extension) with filename.
     * @return 
     *          full path(with extension) to the file.
     */
    @Override
    public String write(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String pstrPath2FileName) {
        BufferedWriter out = null;
        String strFileName = pstrPath2FileName + extension;
        try {
            
            out = new BufferedWriter(new FileWriter(strFileName));
            // writing att names (first row)
            Set<String> lstAttNames = new HashSet<>();

            for (V v : pIGraph.getVertices(tf)) {
                for (String str : v.getAttributeNames()) {
                    if (!lstAttNames.contains(str)) {
                        if (str.equals(MeerkatSystem.FILE_ID)) {
                            lstAttNames.add(MeerkatSystem.FILE_ID);
                            out.write("id" + "\t");
                        } else if (!str.equals(MeerkatSystem.MEERKAT_ID)) {
                            lstAttNames.add(str);
                            out.write(str + "\t");
                        }
                    }
                }
            }
            out.write("\n");

            for (V v : pIGraph.getVertices(tf)) {
                for (String strAtt : lstAttNames) {
                    if (v.getUserAttributer().getAttributeNames().contains(strAtt)) {
                        out.write(v.getUserAttributer().getAttributeValue(strAtt, tf) + "\t");
                    } else if (v.getSystemAttributer().getAttributeNames().contains(strAtt)) {
                        out.write(v.getSystemAttributer().getAttributeValue(strAtt, tf) + "\t");                    
                    }else {
                        out.write(" \t");
                    }
                }
                out.write("\n");
            }
            out.flush();
            out.close();
            return strFileName;
        } catch (IOException ex) {
            Logger.getLogger(CSVWriter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(CSVWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public String write(IDynamicGraph<V, E> pIGraph, TimeFrame tf, List<V> plstVertices, String pstrPath2FileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
