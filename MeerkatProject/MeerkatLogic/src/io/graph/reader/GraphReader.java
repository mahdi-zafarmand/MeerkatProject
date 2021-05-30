package io.graph.reader;

import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;

import io.DataReader;


/**
 * Class Name : Afra Abnar Created Date : 2015-07-xx Description : Version : 1.0
 *
 * @author : Talat
 *
 * EDIT HISTORY (most recent at the top) Date Author Description 2015-13-10
 * Talat Changed the input to load file to strInputFilePath 2015-13-11 Afra
 * added default VType and EType.
 * @param <T>
 * 
*/
public abstract class GraphReader<T> extends DataReader {
    
    /**
     *
     */
    public static int BUFFER_SIZE = 99999999;  // number of characters ~= 95MB

    /**
     *
     */
    protected String strFilePath;
    
    /**
     *
     * @param pstrFilePath
     */
    public GraphReader(String pstrFilePath) {
        this.strFilePath = pstrFilePath;
    }

    /**
     *
     * @return
     */
    public abstract IDynamicGraph<IVertex, IEdge<IVertex>> loadFile();
        

}
