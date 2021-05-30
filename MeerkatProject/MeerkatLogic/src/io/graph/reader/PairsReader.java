package io.graph.reader;

import config.MeerkatSystem;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import datastructure.core.graph.impl.*;
import java.io.File;
import java.util.Date;

/**
 *
 * @author aabnar
 */
public class PairsReader extends GraphReader {

    IVertex[] vertices;
        
    long minId = Integer.MAX_VALUE;
    long maxId = Integer.MIN_VALUE;
    
    int dataStructureTimeV = 0;
    int dataStructureTimeE = 0;
    
    /**
     *
     * @param pstrPath2FileName
     */
    public PairsReader (String pstrPath2FileName) {
        super (pstrPath2FileName);
    }
    
    /**
     *
     * @return
     */
    @Override
    public IDynamicGraph<IVertex,IEdge<IVertex>> loadFile() {

        IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = null;
        IStaticGraph<IVertex,IEdge<IVertex>> igraph = null;

        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(
                            strFilePath));
            File f = new File (strFilePath);
            BufferedReader br =
                    new BufferedReader(reader, customBufferSize(f.length()));
            

            String line;
            line = br.readLine();
            
            int edgeCount = 0;
            while (line != null) {
                if (!line.isEmpty() && line.charAt(0) != '#') {
                    String[] lineItem = line.trim().split("\\s+");
                    long id1 = Long.parseLong(lineItem[0]);
                    long id2 = Long.parseLong(lineItem[1]);
                    
                    
                    if (id1 < id2) {
                       if (id1 < minId) {
                            minId = id1;
                       }
                       if (id2 > maxId) {
                            maxId = id2;
                        }
                    } else {
                        if (id2 < minId) {
                            minId = id2;
                       }
                       if (id1 > maxId) {
                            maxId = id1;
                        }
                    }
                }
                edgeCount++;
                line = br.readLine();
            }
            reader.close();
            br.close();
            
            int arraySize = 0 ;
            if (maxId - minId + 1 < Integer.MAX_VALUE) {
                arraySize = (int) (maxId - minId + 1);
            } else {
                new Exception("Graph is too large to be displayed!");
            }
            
            //vertices_file_id = new long[arraySize];
            TimeFrame tf = TimeFrame.DEFAULT_TIMEFRAME;
            igraph = new StaticGraph<>(arraySize, edgeCount);
            dynaGraph = new DynamicGraph<>(arraySize, edgeCount);
            dynaGraph.addGraph(tf, igraph);
            
            vertices = new Vertex[arraySize];
            
            reader = new InputStreamReader(new FileInputStream(strFilePath));
            br = new BufferedReader(reader, customBufferSize(f.length()));

            line = br.readLine();
            while (line != null) {
                line = line.trim();

                if (!line.isEmpty() && line.charAt(0) != '#') {
                    String[] parts = line.split("\\s+");
                    String strid1 = parts[0];
                    String strid2 = parts[1];

                    long id1 = Long.parseLong(strid1);
                    long id2 = Long.parseLong(strid2);

                    IVertex v1 = null;
                    int index1 = findVertex(id1);
                    //System.out.println(index1);
                    if (index1 > -1) {
                        v1 = vertices[index1];
                    } else {
                        v1 = new Vertex();
                        v1.getUserAttributer().addAttribute(MeerkatSystem.FILE_ID, strid1, tf);
//                        v1.getUserAttributer().addAttribute("id", strid1, tf);
                        addVertex(id1, v1);
                        dynaGraph.addVertex(v1,tf);
                    }

                    IVertex v2 = null;
                    int index2 = findVertex(id2);
                    if (index2 > -1) {
                        v2 = vertices[index2];
                    } else {
                        v2 = new Vertex();
                        v2.getUserAttributer().addAttribute(MeerkatSystem.FILE_ID, strid2, tf);
//                        v2.getUserAttributer().addAttribute("id", strid2, tf);
                        addVertex(id2, v2);
                        dynaGraph.addVertex(v2,tf);
                    }

                    IEdge<IVertex> iedgeNew = new Edge<>(v1, v2, false);

                    // Make the defaults of an Edge to be undirected and not-predicted
                    iedgeNew.getSystemAttributer().addAttributeValue(MeerkatSystem.PREDICTED, "false", new Date(), tf);
                    iedgeNew.getSystemAttributer().addAttributeValue(MeerkatSystem.DIRECTED, "false", new Date(), tf);
                    
                    dynaGraph.addEdge(iedgeNew, tf);

                }
                line = br.readLine();
            }
            
            reader.close();
            br.close();
        } catch (IOException e) {
                throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            
        }

//        System.out.println("returning the graph!");
        
        
        return dynaGraph;
    }

    private int findVertex(long id1) {
        if (vertices[(int)(id1 - minId)] == null ) {
            return -1;
        } else {
            return (int) (id1 - minId);
        }
    }
    
    private void addVertex(long id1, IVertex v1) {
        vertices[(int)(id1 - minId)] = v1;
    }
    
    /**
     * 
     * @param pintFileLength 
     *              length of file in Byte
     * @return 
     */
    private int customBufferSize(long pintFileLength /*length of file in Byte*/) {
        //return (int) (Math.min(pintFileLength, FreeSpace() / 10) + 1024 ) ;
        return (int) pintFileLength / 10;
    }
}
