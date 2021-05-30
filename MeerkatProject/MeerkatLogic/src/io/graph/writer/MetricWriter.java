/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.graph.writer;

import datastructure.core.graph.classinterface.IVertex;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Description: Writes metric results (betweenness, closeness, hub, authority, pagerank , indegree, outdegree, etc.) to file.
 * @author aabnar
 */
public class MetricWriter{
    
    /**
     *
     * @param <V>
     * @param pstrFilename
     * @param phmpScores
     */
    public static <V extends IVertex> void write(String pstrFilename, 
                                            HashMap<V, Double> phmpScores) {

            
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(pstrFilename));
            for (V v : phmpScores.keySet()) {
                out.write(v.getId() +"\t"+
                    phmpScores.get(v) + "\n");
            }
            out.close();

        } catch (IOException ex) {
            Logger.getLogger(MetricWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
            
      
    }
}
