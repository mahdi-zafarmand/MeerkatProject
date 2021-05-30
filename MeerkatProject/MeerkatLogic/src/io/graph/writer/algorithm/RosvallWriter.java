/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.graph.writer.algorithm;

import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.writer.PajekWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aabnar
 */
public class RosvallWriter {

    public static String strExtension  = ".net";
    public static <V extends IVertex, E extends IEdge<V>> String
            RosvallWriter(IDynamicGraph<V, E> pdynaGraph,
                    TimeFrame tf,
                    List<V> plstVerticesOrder,
                    String pstrPath2FileName) {

        BufferedWriter out = null;
        try {
            String strFileNameWithExtension = pstrPath2FileName + strExtension;
            
            File flNETGraphFile = new File(strFileNameWithExtension);
            FileWriter flWriter = new FileWriter(flNETGraphFile) ;
            out = new BufferedWriter(flWriter);
            // System.out.println("Rosvall Writer(): Path: "+flNETGraphFile.getAbsolutePath());
                    
            out.write("*Vertices " + pdynaGraph.getVertices(tf).size() + "\n");
            for (V v : plstVerticesOrder) {
                int id = plstVerticesOrder.indexOf(v) + 1;
                out.write(id + " \"" + id + "\"" + "\n");
            }
            out.write("*Edges\n");
            for (E e : pdynaGraph.getEdges(tf)) {
                if (!e.isDirected()) {
                    out.write((plstVerticesOrder.indexOf(e.getSource()) + 1)
                            + " "
                            + (plstVerticesOrder.indexOf(e.getDestination()) + 1)
                            + "\n");
                }
            }
            out.write("*Arcs\n");
            for (E e : pdynaGraph.getEdges(tf)) {
                if (e.isDirected()) {
                    out.write((plstVerticesOrder.indexOf(e.getSource()) + 1 )
                            + " "
                            + (plstVerticesOrder.indexOf(e.getDestination()) + 1)
                            + "\n");
                }
            }
            out.write("\n");
            out.flush();
            out.close();
            System.out.println("RosvallWriter.write() : out.close()");
            return flNETGraphFile.getAbsolutePath();
        } catch (IOException ex) {
            Logger.getLogger(PajekWriter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(PajekWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

}
