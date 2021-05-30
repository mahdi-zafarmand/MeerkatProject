/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import config.MeerkatClassConfig;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import io.graph.writer.GraphWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import main.meerkat.MeerkatBIZ;

/**
 *
 * @author aabnar
 */
public class DataWriter {
    
    /**
     * Description: Calls the appropriate graph writer class by reflection 
     * (using the id of the writer class from bizconf.xml file to map it to the class name. 
     * (the mapping is stored in meerkatBIZ.meerkatConfig)
     * Creation: 2016, 04, 03
     * 
     * @param pDynaGraph
     * @param tf
     * @param pstrWriterClassId
     * @param pstrPathToFileName 
     * @return  
     */
    public static String writeGraph (IDynamicGraph pDynaGraph, 
            TimeFrame tf,
            String pstrWriterClassId, 
            String pstrPathToFileName)  {
     
        System.out.println("DataWriter.writGraph: " + pstrPathToFileName);
        
        int intErrorCode = 0;
        try {
            /* Extract the format of the file from the filename and Find the Reader Class Name */
            String strClassName = MeerkatBIZ.meerkatConfig.getClassName(MeerkatClassConfig.WRITERS_TAG, pstrWriterClassId);
            
            if (strClassName == null) {
                intErrorCode = -1000801;
            } else {
                Class clsWriter;
                Constructor constructor;
                GraphWriter drdObj;
                Method methodRead;
                strClassName = "io.graph.writer." + strClassName ;
                clsWriter = Class.forName(strClassName);                                
                constructor = clsWriter.getConstructor();            
                drdObj = (GraphWriter) constructor.newInstance();     
                System.err.println("DataWriter.writeGraph() : " + drdObj.getClass().getSimpleName());
                String strFileCompletePath;
                if (tf == null) {
                    methodRead = clsWriter.getMethod("write", IDynamicGraph.class, String.class);
                    strFileCompletePath = 
                        (String) methodRead.invoke(drdObj, pDynaGraph, pstrPathToFileName);
                } else {
                    methodRead = clsWriter.getMethod("write", IDynamicGraph.class, TimeFrame.class, String.class); 
                    strFileCompletePath = 
                        (String) methodRead.invoke(drdObj, pDynaGraph, tf, pstrPathToFileName);
                }
                
                return strFileCompletePath;
            }
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
            System.out.println("DataWriter.load() : Exception: "+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
