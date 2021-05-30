package io.graph.reader;

import config.MeerkatSystem;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import datastructure.core.graph.impl.DynamicGraph;
import datastructure.core.graph.impl.Edge;
import datastructure.core.graph.impl.StaticGraph;
import datastructure.core.graph.impl.Vertex;
import java.util.Date;

/**
 *
 * @author aabnar
 */
public class PajekReader extends GraphReader {

    public static final String label = "label";
    public static final String id = "name";
    enum State {

        VERTICES,
        EDGES,
        ARCS,
        EDGESLIST,
        ARCSLIST
    }

    State state;
    HashMap<String, IVertex> hmpVertices = new HashMap<>();

    int intVertexCount = 0;
    int intEdgeCount = 0;

    TimeFrame tf = TimeFrame.DEFAULT_TIMEFRAME;
    
    /**
     *
     * @param pstrPath2FileName
     */
    public PajekReader(String pstrPath2FileName) {
        super(pstrPath2FileName);
    }

    /**
     *
     * @return
     */
    @Override
    public IDynamicGraph<IVertex, IEdge<IVertex>> loadFile() {

        
        firstRead();

        IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph
                = new DynamicGraph<>(intVertexCount, intEdgeCount);
        
        IStaticGraph<IVertex, IEdge<IVertex>> igraph = new StaticGraph<>(intVertexCount,intEdgeCount);
        
        dynaGraph.addGraph(tf, igraph);

        try {
            InputStreamReader reader
                    = new InputStreamReader(new FileInputStream(strFilePath));
            BufferedReader br = new BufferedReader(reader, BUFFER_SIZE);

            String line = br.readLine();
            while (line != null) {
                line = line.trim().toLowerCase();
                if (line.startsWith("%") || line.isEmpty()) {
                    line =  br.readLine();
                    continue;
                }
                if(line.startsWith("*vertices")) {
                    state = State.VERTICES;
                } else if (line.equals("*arcs")) {
                    state = State.ARCS;
                } else if (line.equals("*edges")) {
                    state = State.EDGES;
                } else if (line.equals("*edgeslist")) {
                    state = State.EDGESLIST;
                } else if (line.equals("*arcslist")) {
                    state = State.ARCSLIST;
                }else {
                    switch (state) {
                        case VERTICES:
                            String id = line.substring(0,line.indexOf("\"")).trim();
                            String vertexName
                                    = line.substring(line.indexOf("\"")+1,line.lastIndexOf("\""));

                            IVertex ivtNewV = new Vertex();

                            ivtNewV.getUserAttributer().addAttribute(MeerkatSystem.FILE_ID, id, tf);
                            
                            
                            if (!vertexName.isEmpty()) {
                                ivtNewV.getUserAttributer().addAttribute(label,
                                    vertexName,
                                    igraph.getTimeframe());
                            }

                            hmpVertices.put(id, ivtNewV);
                            dynaGraph.addVertex(ivtNewV, tf);

                            break;
                        case EDGES:
                            String[] vIds = line.split("\\s+");
                            String source = vIds[0];
                            String destination = vIds[1];

                            IEdge<IVertex> iedgeNew = new Edge<>(
                                    hmpVertices.get(source),
                                    hmpVertices.get(destination),
                                    false);
                            
                            // Make the defaults of an Edge to be undirected and not-predicted
                            iedgeNew.getSystemAttributer().addAttributeValue(MeerkatSystem.PREDICTED, "false", new Date(), tf);
                            iedgeNew.getSystemAttributer().addAttributeValue(MeerkatSystem.DIRECTED, "false", new Date(), tf);

                            dynaGraph.addEdge(iedgeNew, tf);
                            
                            break;
                        case ARCS:
                            vIds = line.split("\\s+");
                            source = vIds[0];
                            destination = vIds[1];

                            iedgeNew = new Edge<>(
                                    hmpVertices.get(source),
                                    hmpVertices.get(destination),
                                    true);

                            dynaGraph.addEdge(iedgeNew, tf);

                            break;
                        case ARCSLIST:
                            vIds = line.split("\\s+");
                            source = vIds[0];

                            for (int i = 1; i < vIds.length; i++) {
                                destination = vIds[i];
                                iedgeNew = new Edge<>(
                                        hmpVertices.get(source),
                                        hmpVertices.get(destination),
                                        true);

                                dynaGraph.addEdge(iedgeNew, tf);
                            }
                            break;
                        case EDGESLIST:
                            vIds = line.split("\\s+");
                            source = vIds[0];

                            for (int i = 1; i < vIds.length; i++) {
                                destination = vIds[i];
                                iedgeNew = new Edge<>(
                                        hmpVertices.get(source),
                                        hmpVertices.get(destination),
                                        false);

                                dynaGraph.addEdge(iedgeNew, tf);
                            }
                            break;
                    }
                }
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        dynaGraph.addGraph(TimeFrame.DEFAULT_TIMEFRAME, igraph);
        return dynaGraph;
    }

    private void firstRead() {
        try {
            InputStreamReader reader
                    = new InputStreamReader(new FileInputStream(strFilePath));
            BufferedReader br = new BufferedReader(reader, BUFFER_SIZE);

            String line = br.readLine();
            while (line != null) {
//                System.out.println("PajekReader.firstRun() : Line --> " + line);
                line = line.trim().toLowerCase();
                if (line.startsWith("%") || line.isEmpty()) {
                    line = br.readLine();
                    continue;
                }
                
                if( line.startsWith("*vertices")) {
                    state = State.VERTICES;
                } else if (line.equals("*arcs")) {
                    state = State.ARCS;
                } else if (line.equals("*edges")) {
                    state = State.EDGES;
                } else if(line.equals("*edgeslist")) {
                    state = State.EDGESLIST;
                } else if (line.equals("*arcslist")) {
                    state =State.ARCSLIST;
                } else {
                    switch (state) {
                        case VERTICES:
                            intVertexCount++;
                            break;
                        case EDGES:
                            intEdgeCount++;
                            break;
                        case ARCS:
                            intEdgeCount++;
                            break;
                        case EDGESLIST:
                            String[] adjList = line.split(" ");
                            intEdgeCount += adjList.length;
                            break;
                    }
                }
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }
}
