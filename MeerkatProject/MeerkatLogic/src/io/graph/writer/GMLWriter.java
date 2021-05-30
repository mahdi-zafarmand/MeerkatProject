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
import io.graph.reader.GMLReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public class GMLWriter<V extends IVertex, E extends IEdge<V>> 
                        extends GraphWriter<V,E> {

    /**
     *
     */
    public static final String extension = ".gml";
    
    @Override
    public String write(IDynamicGraph<V, E> pIGraph, String pstrPath2FileName) {
        StringBuilder stbFileNames = new StringBuilder();
        if(pIGraph.getAllTimeFrames().size() == 1 ) {
            String fn = pstrPath2FileName;
            String strCmplFN = write(pIGraph, pIGraph.getAllTimeFrames().get(0),fn);
            stbFileNames.append(strCmplFN);
        } else {
            for (int i = 0 ; i < pIGraph.getAllTimeFrames().size(); i++) {
                String fn = pstrPath2FileName + "_" + i;
                String strCmplFN = write(pIGraph, pIGraph.getAllTimeFrames().get(i),fn);
                stbFileNames.append(strCmplFN);
                if (i < pIGraph.getAllTimeFrames().size()-1) {
                    stbFileNames.append(",");
                }
            }
        }
        return stbFileNames.toString();
    }

    @Override
    public String write(IDynamicGraph<V, E> pIGraph, 
            TimeFrame tf, 
            String pstrPath2FileName) {
        
        BufferedWriter out = null;
        try {
            String fn = pstrPath2FileName + extension;
            out = new BufferedWriter(new FileWriter(fn));
            
            out.write("graph\n[\n");
            for (V v : pIGraph.getVertices(tf)) {
                out.write("  node\n  [\n");
                for (String strAttName : v.getUserAttributer()
                        .getAttributeNames()) {
                    
                    if (!strAttName.equals(MeerkatSystem.FILE_ID) &&
                            !strAttName.equals(MeerkatSystem.MEERKAT_ID)) {
                        out.write("    "+strAttName+ " " + v.getUserAttributer()
                            .getAttributeValue(strAttName, tf) + "\n");
                    } else if (strAttName.equals(MeerkatSystem.FILE_ID)) {
                        out.write("    " + GMLReader.strId + " " + v.getUserAttributer()
                            .getAttributeValue(strAttName, tf) + "\n");
                    }
                }
                
                for (String strAttName : v.getSystemAttributer()
                        .getAttributeNames()) {
                    out.write("    "+strAttName+ " " + v.getSystemAttributer()
                            .getAttributeValue(strAttName, tf) + "\n");
                }
                out.write("  ]\n");
            }
            
            for (E e : pIGraph.getEdges(tf)) {
                out.write("  edge\n  [\n");
                out.write("    source " + e.getSource().getUserAttributer()
                        .getAttributeValue(MeerkatSystem.FILE_ID, tf) + "\n");
                out.write("    target " + e.getDestination().getUserAttributer()
                        .getAttributeValue(MeerkatSystem.FILE_ID, tf) + "\n");
                out.write("    directed " + e.isDirected() + "" + "\n");
                for (String strAttName : e.getUserAttributer()
                        .getAttributeNames()) {
                    out.write("    " + strAttName + " " + e.getUserAttributer()
                    .getAttributeValue(strAttName, tf) + "\n");
                }
                
                for (String strAttName : e.getSystemAttributer()
                        .getAttributeNames()) {
                    out.write("    " + strAttName + " " + e.getSystemAttributer()
                    .getAttributeValue(strAttName, tf) + "\n");
                }
                out.write("  ]\n");
            }
            out.write("]");
            
            out.close();
            return fn;
        } catch (IOException ex) {
            Logger.getLogger(GMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(GMLWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public String write(IDynamicGraph<V, E> pIGraph, TimeFrame tf, List<V> plstVertices, String pstrPath2FileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
