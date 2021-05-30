/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.attributes.writer;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Talat
 * @since 2018-01-24
 * @param <V>
 * @param <E> 
 */
public class AttributeWriter<V extends IVertex, E extends IEdge<V>> {
 
    public AttributeWriter(){
        
    }
    
    /**
     * Writes the Vertices Along with the Selected Attributes to the file
     * @param pIGraph
     * @param ptf
     * @param psetUserAttributes
     * @param psetSystemAttributes
     * @param pstrFilePath
     * @return 
     */
    public int WriteVertices(
            IDynamicGraph<V, E> pIGraph, 
            TimeFrame ptf,
            Set<String> psetUserAttributes, 
            Set<String> psetSystemAttributes,
            String pstrFilePath) {
        
        int intError= 0;
        try {
            FileWriter file = new FileWriter(pstrFilePath);
            
            List<String> lstUserAttr = new ArrayList<>();
            lstUserAttr.addAll(psetUserAttributes);
            Collections.sort(lstUserAttr, String.CASE_INSENSITIVE_ORDER);
            
            List<String> lstSystemAttr = new ArrayList<>();
            lstSystemAttr.addAll(psetSystemAttributes);
            Collections.sort(lstSystemAttr, String.CASE_INSENSITIVE_ORDER);
            
            // Writing the Header
            String strHeader = MeerkatSystem.ID_ATTRIBUTE_TAG + MeerkatSystem.DELIMITER_CSV;
            strHeader += String.join(MeerkatSystem.DELIMITER_CSV, lstUserAttr) + MeerkatSystem.DELIMITER_CSV;
            strHeader += String.join(MeerkatSystem.DELIMITER_CSV, lstSystemAttr);
            file.write(strHeader + "\n");
                        
            List<Integer> lstVertexIDs = pIGraph.getAllVertexIds();            
            for (int intVertexID : lstVertexIDs) {                
                
                List<String> lstAttrValues = new ArrayList<>();
                lstAttrValues.add(intVertexID + "");
                
                for (String strAttr : lstUserAttr){
                    String strValue = pIGraph.getGraph(ptf).getVertex(intVertexID).getUserAttributer().getAttributeValue(strAttr, ptf);
                    lstAttrValues.add(strValue == null ? "" : strValue);
                }
                
                for (String strAttr : lstSystemAttr){
                    String strValue = pIGraph.getGraph(ptf).getVertex(intVertexID).getSystemAttributer().getAttributeValue(strAttr, ptf);
                    lstAttrValues.add(strValue == null ? "" : strValue);
                }
                
                file.write(String.join(MeerkatSystem.DELIMITER_CSV, lstAttrValues) + "\n");
            }
            file.close();
        } catch (IOException ex){
            System.out.println("EXCEPTION in ExportMetrics.exportMetrics(): TEMP Solution");
            return intError;
        }
        
        return intError;
    }
}
