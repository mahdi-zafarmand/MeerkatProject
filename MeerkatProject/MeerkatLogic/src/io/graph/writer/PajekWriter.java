package io.graph.writer;



import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.reader.PajekReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Write a MeerkatGraph to a file using the Pajek .net format.
 * 
 * More on the .net format can be found here:
 * http://www.ccsr.ac.uk/methods/publications/snacourse/netdata.html
 * 
 * @author Afra 
 * @param <V>
 * @param <E>
 * 
 * EDIT HISTORY
 * DATE         AUTHOR      DESCRIPTION
 * 2016 Apr 21  Afra        remove duplicate code from write(graph,filepath) by makeing it calling write(graph,timeframe,filepath) for each tiemframe.
 * 
 */


public class PajekWriter<V extends IVertex, E extends IEdge<V>> extends GraphWriter<V,E>{

    /**
     *
     */
    public static final String strExtension = ".net";
    
    /**
     * MethodName: write
     * Description: write each time frame to a separate file
     * Version: 2.0
     * Author: Afra
     * 
     * @param pIGraph
     *          dynamic graph
     * @param pstrPath2FileName
     *          full path(without extension) to file name;
     * @return
     *      full path (with extention) to file(s) name [comma separated in terms of multiple timeframes in the graph]
     * 
     * EDIT HISTORY
     * DATE         AUTHOR          DESCRIPTION
     * 2016 Apr 21  Afra            removed the duplicate code and made the function to call write(graph,tf, filename) for each timeframe and append the results to the list of files.
     */
    @Override
    public String write(IDynamicGraph<V,E> pIGraph, String pstrPath2FileName) {
        int intErrorCode = 0;
        StringBuilder strFileNames = null;
        strFileNames = new StringBuilder();
        int intNumTF = pIGraph.getAllTimeFrames().size();
//        System.out.println("PairsWriter.write(): number of timeframes: " + intNumTF);

        for (int intTfIndex = 0 ; intTfIndex < intNumTF ; intTfIndex++) {
            String fn = pstrPath2FileName+ "_" + intTfIndex;
            String result = write(pIGraph, pIGraph.getAllTimeFrames().get(intTfIndex),fn);
            if (result !=null) {
                strFileNames.append(result);

                if ( intNumTF > 1 && intTfIndex < intNumTF -1 ) {
                 strFileNames.append(",");
                }
            }
        }
            
        return strFileNames.toString();
    }

    /**
     * Method Name: write
     * Description: writes a specific timeframe to a file
     * Version: 1.0
     * Author: Afra
     * 
     * @param pIGraph
     *          dynamicgraph
     * @param tf
     *          timeframe
     * @param pstrPath2FileName
     *          full path (without extension) to file
     * @return 
     *          full path (with extension) to file.
     */
    @Override
    public String write(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String pstrPath2FileName) {
        
        BufferedWriter out = null;
        try {
            String fn = pstrPath2FileName + strExtension;
            out = new BufferedWriter(new FileWriter(fn));
            out.write("*Vertices "+pIGraph.getVertices(tf).size()+"\n");
            for (V v : pIGraph.getVertices(tf)) {
                out.write(v.getUserAttributer()
                        .getAttributeValue(MeerkatSystem.FILE_ID, tf));
                
                if (v.getUserAttributer().getAttributeNames().contains(PajekReader.label)) {
                    out.write(" " +
                            v.getUserAttributer().getAttributeValue(PajekReader.label, tf));
                }
                out.write("\n");
            }   
            out.write("*Edges\n");
            for (E e : pIGraph.getEdges(tf)) {
                if (!e.isDirected()) {
                    out.write(e.getSource().getUserAttributer()
                        .getAttributeValue(MeerkatSystem.FILE_ID, tf)
                        +"\t" +
                        e.getDestination().getUserAttributer()
                                .getAttributeValue(MeerkatSystem.FILE_ID, tf)
                        +"\n");
                }
            }
            out.write("*Arcs\n");
            for (E e : pIGraph.getEdges(tf)) {
                if (e.isDirected()) {
                    out.write(e.getSource().getUserAttributer()
                        .getAttributeValue(MeerkatSystem.FILE_ID, tf)
                        +"\t" +
                        e.getDestination().getUserAttributer()
                                .getAttributeValue(MeerkatSystem.FILE_ID, tf)
                        +"\n");
                }
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

    @Override
    public String write(IDynamicGraph<V, E> pIGraph, TimeFrame tf, List<V> plstVertices, String pstrPath2FileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}