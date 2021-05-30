package io;

import config.MeerkatClassConfig;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.text.HNode;
import datastructure.core.text.impl.TextualNetwork;
import io.graph.reader.GraphReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import main.meerkat.MeerkatBIZ;

/**
 *  Class Name      : DataReader
 *  Created Date    : 2015-07-xx
 *  Description     : A Generic Data Reader that reads the input file based on the ID-Class Mapping
 *  Version         : 1.0
 *  @author         : aabnar
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-01-20      Talat           Edits in loadThreadTree(). See individual method's edit history for changes
 *  2015-10-19      Afra            Split load method into loadGraph and
 *                                  loadThreadTree. Since we know which one to
 *                                  call from "Project.java".
 *  2015-09-09      Talat           Due to the change in packages, the classname is now starts with "io.graph.reader." instead of "io.graph."
 *  2015-08-26      Talat           Adding the condition of returning null in case the class name has not been found
 * 
*/
public abstract class DataReader {
	
    /**
     *
     */
    public static final String STR_NAME = "DataReader";

    /**
     *  Method Name     : loadGraph()
     *  Created Date    : 2015-07-xx
     *  Description     : Loads the Graph by dynamically invoking the reader given an input file
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrReaderID : String
     *  @param pstrPath2FileName : String
     *  @return IDynamicGraph
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-10-19      Afra            Now returns IDynamicGraph
     * 
    */
    public static IDynamicGraph<IVertex,IEdge<IVertex>> loadGraph(String pstrReaderID, 
                                    String pstrPath2FileName) throws InvocationTargetException{
        
        IDynamicGraph igraph = null;
        Class clsReader;
            
        try {
            /* Extract the format of the file from the filename and Find the Reader Class Name */
            String strClassName = MeerkatBIZ.meerkatConfig.getClassName(MeerkatClassConfig.READERS_TAG, pstrReaderID);
            
            if (strClassName == null) {
                return null;
            }
            
            Constructor constructor;
            GraphReader drdObj;
            Method methodRead;
            strClassName = "io.graph.reader." + strClassName ;
            clsReader = Class.forName(strClassName);                                
            constructor = clsReader.getConstructor(String.class);            
            drdObj = (GraphReader) constructor.newInstance(pstrPath2FileName);            
            methodRead = clsReader.getMethod("loadFile");            
            igraph = (IDynamicGraph) methodRead.invoke(drdObj);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException e) {
            System.out.println("DataReader.load() : Exception: "+e.toString());
            e.printStackTrace();
        }

        return igraph;
    }

    /**
     *  Method Name     : loadThreadTree()
     *  Created Date    : 2015-07-xx
     *  Description     : Loads the Text by dynamically invoking the reader given an input file
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrReaderID : String
     *  @param pstrPath2FileName : String
     *  @return IDynamicGraph
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-01-20      Talat           The invoke method was wrong with a wrong constructor, changed it accordingly
     *  2015-10-19      Afra            Now returns IDynamicGraph
     * 
    */
    public static TextualNetwork loadThreadTree(String pstrReaderID, 
                                          String pstrPath2FileName) {
        
        TextualNetwork textualNetwork = null;
        Object obj = null;
        Class clsReader;
            
        try {
            /* Extract the format of the file from the filename and Find the Reader Class Name */
            String strClassName = 
                    MeerkatBIZ.meerkatConfig.getClassName(
                            MeerkatClassConfig.READERS_TAG, pstrReaderID);
            if (strClassName == null) {
                return null;
            }
            
            Constructor constructor;
            DataReader drdObj;
            Method methodRead;
            
            strClassName = "io.text.reader." + strClassName ;
            clsReader = Class.forName(strClassName);
//            MAHDI: removed "null" as the argument of the getConstructor() in the next line.
            constructor = clsReader.getConstructor();
            drdObj = (DataReader) constructor.newInstance();
            methodRead = clsReader.getMethod("loadFile", String.class);
            obj = methodRead.invoke(drdObj, pstrPath2FileName);
            textualNetwork = (TextualNetwork)obj ;
            
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
            System.out.println("DataReader.loadThreadTree() : Exception: "+e.getMessage());
            e.printStackTrace();
        }

        return textualNetwork;
    }
}
