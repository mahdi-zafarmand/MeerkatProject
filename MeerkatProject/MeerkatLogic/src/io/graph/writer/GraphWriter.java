package io.graph.writer;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Description: The parent class for all graph writers.
 * 
 * @author aabnar
 * @param <V>
 * @param <E> 
 * 
 * Edit history
 * Date         Author          Description
 * 2016-04-05   Afra            added write methods without timeframe that is used by export graph and should be there in all writers because of reflection.
 *                              added write methods with timeframe and also with a list of vertices which are both used by community mining classes.
 */
public abstract class GraphWriter<V extends IVertex, E extends IEdge<V>> {

    /**
     * Description: write method to write the whole graph (static or dynamic) to file(s) at once.
     * @param pIGraph
     * @param pstrPath2FileName
     * @return 
     *      String comma separated complete filePaths along with its extension.
     */
    public abstract String write(IDynamicGraph<V,E> pIGraph,
            String pstrPath2FileName);
    
    	
    /**
     * Description: write method to write a specific timeframe of a dynamic graph to file.
     * @param pIGraph
     * @param tf
     * @param pstrPath2FileName
     * @return 
     */
    public abstract String write(IDynamicGraph<V,E> pIGraph, TimeFrame tf,
            String pstrPath2FileName);
    
    
    /**
     * Description: write method to write a subset of a dynamic graph (described by vertices in a list) at a specific timeframe to file.
     * @param pIGraph
     * @param tf
     * @param plstVertices
     * @param pstrPath2FileName
     * @return 
     */
    public abstract String write(IDynamicGraph<V,E> pIGraph, TimeFrame tf,
            List<V> plstVertices, String pstrPath2FileName);


}
