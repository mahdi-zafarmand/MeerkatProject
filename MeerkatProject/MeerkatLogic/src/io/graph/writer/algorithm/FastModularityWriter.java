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
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aabnar
 */
public class FastModularityWriter {
    public static String strExtension  = ".pairs";
    public static <V extends IVertex, E extends IEdge<V>> String
            write(IDynamicGraph<V, E> pdynaGraph,
                    TimeFrame tf,
                    List<V> plstVerticesOrder,
                    boolean pblnWeighted,
                    String pstrPath2FileName) {

        BufferedWriter out = null;
        try {
            String fn = pstrPath2FileName + strExtension;
            out = new BufferedWriter(new FileWriter(fn));
            
            for (V v : plstVerticesOrder) {
                for (V u : plstVerticesOrder) {
                    if (v.equals(u)) {
                        continue;
                    }
                    E e = pdynaGraph.findEdge(v, u, tf);
                    if (e != null) {
                        int sourceIndex = plstVerticesOrder.indexOf(e.getSource()) + 1;
                        int destIndex = plstVerticesOrder.indexOf(e.getDestination()) + 1;
                        out.write( sourceIndex + "\t" + destIndex);

                        if (pblnWeighted) { 
                            out.write("\t"+e.getWeight());
                        }
                        out.write("\n");
                    }
                }
            }
            for (E e : pdynaGraph.getEdges(tf)) {
                
            }
            
            out.flush();
            out.close();
            return fn;
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
