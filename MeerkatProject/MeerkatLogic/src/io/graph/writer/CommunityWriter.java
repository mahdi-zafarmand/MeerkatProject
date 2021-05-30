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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aabnar
 */
public class CommunityWriter {
    
    /**
     * Writes the file as such
     * 
     * Com1ID\tV1Id,V2Id,V3Id,V4Id,V5Id,V6Id,V7Id,V8Id, so forth
     * Com2ID\tV1Id,V2Id,V3Id,V4Id,V5Id,V6Id,V7Id,V8Id, so forth
     * ...
     * 
     * @param <V>
     * @param pstrFilename
     * @param phmpCommunity 
     */
    public static <V extends IVertex> void write(String pstrFilename,
                                HashMap<String, List<Integer>> phmpCommunity) {

            
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(pstrFilename));
            for (String comId : phmpCommunity.keySet()) {
                out.write(comId+"\t");
                
                for (int i = 0 ; i < phmpCommunity.get(comId).size(); i++) {
                    if (i > 0) {
                        out.write(",");
                    }
                    out.write(phmpCommunity.get(comId).get(i));
                }
                out.write("\n");
            }
            out.close();

        } catch (IOException ex) {
            Logger.getLogger(MetricWriter.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
            
      
    }
}
