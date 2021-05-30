package io.graph.reader;

import config.MeerkatSystem;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import datastructure.core.DynamicAttributable.DynamicAttributer.GenericDynamicAttributer;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import datastructure.core.graph.impl.DynamicGraph;
import datastructure.core.graph.impl.Edge;
import datastructure.core.graph.impl.StaticGraph;
import datastructure.core.graph.impl.Vertex;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Convert files of .gml format to a Meerkat network.
 * .gml format is supposed to be as follows:
 *  graph
    [
        node
        [
            id A
        ]
        node
        [
            id B
        ]
        node
        [
            id C
        ]
        edge
        [
            source B
            target A
        ]
        edge
        [
            source C
            target A
        ]
    ]
 *
 * The format of a .gml file can be found at
 * http://www.fim.uni-passau.de/en/fim/faculty/chairs/theoretische-informatik/
 * projects.html.
 *
 * @author Matt Gallivan
 *
 * 
 * MAHDI: the standard format is different than what is already implemented.
 * There are some cases not covered yet. The link above supposed to show the
 * format of .gml files does not work, but you can refer to this:
 * https://en.wikipedia.org/wiki/Graph_Modelling_Language
 */
public class GMLReader extends GraphReader{

    public static String strId = "id";
    HashMap<String,IVertex> hmpVertices = new HashMap<>();
    Set<IEdge> hmpEdges = new HashSet<>();
    
    private TimeFrame tf = TimeFrame.DEFAULT_TIMEFRAME;
    private int vertexCount = 0;
    private int edgeCount = 0;
	
    /**
     *
     * @param pstrPath2FileName
     */
    public GMLReader(String pstrPath2FileName) {
        super(pstrPath2FileName);
    }
    
    /**
     *
     * @return
     */
    @Override
    public final DynamicGraph<IVertex,IEdge<IVertex>> loadFile() {
//        firstRun();
        
        DynamicGraph<IVertex,IEdge<IVertex>> dynaGraph = 
                new DynamicGraph<>(vertexCount, edgeCount);
    	IStaticGraph<IVertex,IEdge<IVertex>> igraph = 
    			new StaticGraph<>(vertexCount, edgeCount);
        dynaGraph.addGraph(tf, igraph);
        
        try {
            InputStreamReader reader = new InputStreamReader(
                            new FileInputStream(strFilePath));

            BufferedReader br = new BufferedReader(reader, BUFFER_SIZE);
        	
            String line;
            line = br.readLine();
            while (line != null) {
                line = line.trim();
                // Comments
                if (line.startsWith("#")) {
                	// DO NOTHING
                } else if (line.startsWith("graph")) {
                	// DO NOTHING
                } else if (line.startsWith("node")) {
                    // Node Declaration
                    vertexCount++;                    
                    IVertex ivtNewV = readNode(line,br);
                    dynaGraph.addVertex(ivtNewV, tf);
                } else if (line.startsWith("edge")) {
                	// Edge Declaration
                    edgeCount++;                    
                    IEdge<IVertex> edge = readEdge(line,br);
                    dynaGraph.addEdge(edge, tf);
                }
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return dynaGraph;
    }

    private IVertex readNode(String line, BufferedReader br) throws IOException {
        IVertex ivtNewV = new Vertex();

        boolean blnHasFILEID = false;
        
        String id = null;
        line = br.readLine();
        while (line != null) {
            line = line.trim();

            // Start of node (vertex) declaration.
            if (line.startsWith("[")){
                // DO NOTHING                
            } else if (line.startsWith("]")){
                break;
            } else {
                String name = line.split("\\s+")[0].trim();
                String value = line.split("\\s+")[1];
//                System.out.println("GMLReader.readNode(): line --> "+line+", name --> " + name + ", value --> " + value);
                if (name.equals(MeerkatSystem.FILE_ID)) {
                    id = value;
                    ivtNewV.getUserAttributer().addAttribute(MeerkatSystem.FILE_ID, 
                        value, 
                        TimeFrame.DEFAULT_TIMEFRAME);
                    
                    blnHasFILEID = true;
                } else if (!blnHasFILEID && name.equals(strId)) {
                    id = value;
//                    MAHDI: commented the next line of code and uncommented the line after that.

//                    ivtNewV.getUserAttributer().addAttribute(MeerkatSystem.FILE_ID, 
//                        value, 
//                        TimeFrame.DEFAULT_TIMEFRAME);
                    
                    ivtNewV.getUserAttributer().addAttribute(strId, 
                        value, 
                        TimeFrame.DEFAULT_TIMEFRAME);
                } else if (MeerkatSystem.isSystemAttribute(name.toUpperCase())) {
                    ivtNewV.getSystemAttributer().addAttributeValue(name.toUpperCase(), 
                            value, 
                            new Date(), 
                            tf);
                } else {
                    ivtNewV.getUserAttributer().addAttribute(name, 
                        value, 
                        TimeFrame.DEFAULT_TIMEFRAME);
                }
            }

            line = br.readLine();
        }

        hmpVertices.put(id, ivtNewV);
        return ivtNewV;
    }
	
    private IEdge<IVertex> readEdge(String line, BufferedReader br) throws 
                   IOException {
        GenericDynamicAttributer attributer = new GenericDynamicAttributer();
        IEdge<IVertex> iedgeNew = null;
        IVertex source = null;
        IVertex destination = null;
        boolean directed = false;
        
        line = br.readLine();
        while (line != null) {
            line = line.trim();

            if (source !=null && destination != null) {
                iedgeNew = new Edge<>(source,destination,directed);
            }
            // Start of node (vertex) declaration.
            if (line.startsWith("[")) {
                // DO NOTHING
            } else if (line.startsWith("]")) {
               
                return iedgeNew;
            } else {
                String name = line.split("\\s+")[0];
                String value = line.split("\\s+")[1].replace("\"", "");
//                System.out.println("GMLReader.readEdge(): line --> "+line+", name --> " + name + ", value --> " + value);

                if (name.trim().equals("source")) {
                    source = hmpVertices.get(value);
                } else if (name.trim().equals("target")) {
                    destination = hmpVertices.get(value);
                } else if (name.trim().equals("directed")) {
                    directed = Boolean.parseBoolean(name.trim());
                } else if (MeerkatSystem.isSystemAttribute(name.toUpperCase())) {
                    iedgeNew.getSystemAttributer().addAttributeValue(name.toUpperCase(), 
                            value, 
                            new Date(), 
                            tf);
                } else {
                    iedgeNew.getUserAttributer().addAttribute(name, 
                            value, 
                            TimeFrame.DEFAULT_TIMEFRAME);
                }
                
//                MAHDI: at this point iedgeNew is null and you cannot call any method for it.
//                // Make the defaults of an Edge to be undirected and not-predicted
//                iedgeNew.getSystemAttributer().addAttributeValue(MeerkatSystem.PREDICTED, "false", new Date(), tf);
//                iedgeNew.getSystemAttributer().addAttributeValue(MeerkatSystem.DIRECTED, "false", new Date(), tf);
            }

            line = br.readLine();
        }
        return iedgeNew;
    }
    
//    MAHDI: the next function only counts the number of vertices and edges, this can easily be done when nodes and edges are created.
//    private void firstRun() {
//        try {
//            InputStreamReader reader = new InputStreamReader(
//                            new FileInputStream(strFilePath));
//
//            BufferedReader br = new BufferedReader(reader, BUFFER_SIZE);
//        	
//            String line;
//            line = br.readLine();
//            while (line != null) {
//                line = line.trim();
//
//                // Comments
//                if (line.startsWith("#")) {
//                	// DO NOTHING
//                } else if (line.startsWith("graph")) {
//                	// DO NOTHING
//                } else if (line.startsWith("node")) {
//                    // Node Declaration
//                    vertexCount++;
//                } else if (line.startsWith("edge")) {
//                	// Edge Declaration
//                    edgeCount++;
//                }
//                line = br.readLine();
//            }
//            br.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
