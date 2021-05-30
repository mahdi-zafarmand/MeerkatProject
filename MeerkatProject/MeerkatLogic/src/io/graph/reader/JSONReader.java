package io.graph.reader;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IStaticGraph;
import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.graph.impl.DynamicGraph;
import datastructure.core.graph.impl.Edge;
import datastructure.core.graph.impl.StaticGraph;
import datastructure.core.graph.impl.Vertex;
import datastructure.general.DynamicArray;
import static io.graph.reader.GraphReader.BUFFER_SIZE;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * JSONReader created on Date 2017-02-xx. A static graph reader that 
 * specifically reads nodes and edges from a .JSON file.
 * @version: 1.0
 * @author sankalp
 */

public class JSONReader extends GraphReader{
    
    private int vertexCount = 0;
    private int edgeCount = 0;
    
    int minVID = Integer.MAX_VALUE;
    int maxVID = 0;
    private IVertex[] hmpAllVertices;
    
    @SuppressWarnings("FieldMayBeFinal")
    private TimeFrame tf = TimeFrame.DEFAULT_TIMEFRAME;
    
    private DynamicGraph<IVertex, IEdge<IVertex>> dynaGraph;
    private DynamicArray<Map<IVertex,IEdge>> edges;
    
    /**
     * @param pstrPath2FileName
     */
    public JSONReader(String pstrPath2FileName){
        super(pstrPath2FileName);
    }
    
    /**
     * MethodName: loadFile()
     * Description: prepares the dynamic graph , uses method calls to read
     * vertices and edges from .JSON file.
     * @version: 1.0
     * @author: sankalp
     */
    
    @Override
    public DynamicGraph<IVertex,IEdge<IVertex>> loadFile() {
        System.out.println("path to file: " + strFilePath);
        firstRun();
        
        dynaGraph 
               = new DynamicGraph<>(vertexCount , edgeCount);

        IStaticGraph<IVertex,IEdge<IVertex>> igraph 
            = new StaticGraph<>(vertexCount , edgeCount);
        
        edges = new DynamicArray<>(edgeCount);

        dynaGraph.addGraph(tf, igraph);
        hmpAllVertices = new IVertex[maxVID - minVID + 1];
        System.out.println("min, max vertex id :" + minVID + " "+maxVID);

        readVertex();
        readEdge(false);
 
        return dynaGraph;
    }
    
     /**
     * MethodName: readVertex()
     * Description: reads the vertices from .JSON file
     * @version: 1.0
     * @author: sankalp
     */
    private void readVertex(){
        try{
            InputStreamReader reader = new InputStreamReader(new FileInputStream(strFilePath));
            BufferedReader br = new BufferedReader(reader, BUFFER_SIZE);
            
            StringBuilder sb  = new StringBuilder();
            String strCurrentLine = br.readLine().trim();            
            
            while (strCurrentLine != null) {
                strCurrentLine = strCurrentLine.toLowerCase().trim();

                if (strCurrentLine.isEmpty()) {
                    strCurrentLine = br.readLine();
                    continue;
                }
                
                sb.append(strCurrentLine);
                strCurrentLine = br.readLine();
            }   
             
            
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(sb.toString());
            System.out.println("check2: " + json);
            
            JSONArray jarray_nodes =  (JSONArray) json.get("nodes");
            System.out.println("array is "+ jarray_nodes);
            
            for(int i =0; i<jarray_nodes.size();i++){
                JSONObject ob = (JSONObject) jarray_nodes.get(i);
                
                String strVertexID = ob.get("id").toString();
                int intVID = Integer.parseInt(strVertexID);

                IVertex ivtNewV = null;
                if (hmpAllVertices[intVID - minVID] == null) {
                    ivtNewV = new Vertex();
                    hmpAllVertices[intVID - minVID] = ivtNewV;
                }


                ivtNewV.getUserAttributer().addAttribute(MeerkatSystem.FILE_ID,
                            strVertexID,
                            tf);


                for (Object key : ob.keySet()) {
                    String strKey = (String)key;
                    String strValue = ob.get(strKey).toString();

                        if (strKey != null) {
                            if (MeerkatSystem.isSystemAttribute(strKey.toUpperCase())) {
                                ivtNewV.getSystemAttributer().addAttributeValue(
                                    strKey.toUpperCase(),
                                    strValue,
                                    new Date(),
                                    tf);

                            } else {

                                ivtNewV.getUserAttributer().addAttribute(strKey,
                                        strValue,
                                        tf);
                            }
                        }
                    
                }
                
                dynaGraph.addVertex(ivtNewV, tf);
                edges.add(ivtNewV.getId(), new HashMap<>());
            }
            }catch(IOException | NumberFormatException | ParseException e){
                System.out.println("JSONException "+e.getMessage());

            }
    }
    
      /**
     * MethodName: readEdge()
     * Description: reads the edges from .JSON file
     * @version: 1.0
     * @author: sankalp
     */
    private void readEdge(boolean blnDirected){
        
        try{
            InputStreamReader reader = new InputStreamReader(new FileInputStream(strFilePath));
            BufferedReader br = new BufferedReader(reader, BUFFER_SIZE);
            
            StringBuilder sb  = new StringBuilder();
            String strCurrentLine = br.readLine().trim();            
            
            while (strCurrentLine != null) {
                strCurrentLine = strCurrentLine.toLowerCase().trim();

                if (strCurrentLine.isEmpty()) {
                    strCurrentLine = br.readLine();
                    continue;
                }
                
                sb.append(strCurrentLine);
                strCurrentLine = br.readLine();
            }   
             
            
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(sb.toString());
            System.out.println("checkedge: " + json);
            
            JSONArray jarray_links =  (JSONArray) json.get("links");
            System.out.println("array is "+ jarray_links);
            
            for(int i =0; i<jarray_links.size();i++){
                JSONObject ob = (JSONObject) jarray_links.get(i);
            
                String strFileSrc = ob.get("source").toString();
                int intFileSrc = Integer.parseInt(strFileSrc);

                String strFileDest = ob.get("target").toString();
                int intFileDest = Integer.parseInt(strFileDest);

                IVertex vtxSource = hmpAllVertices[intFileSrc - minVID];
                IVertex vtxDestination = hmpAllVertices[intFileDest - minVID];
                if (vtxSource == null || vtxDestination == null) {
                    return;
                }
                
                double weight = 1.0;

                IEdge<IVertex> iedgeNew = null;

                if (edges.get(vtxSource.getId()).containsKey(vtxDestination)) {
                    iedgeNew = edges.get(vtxSource.getId()).get(vtxDestination);
                } else {
                    iedgeNew = new Edge<>(vtxSource,
                            vtxDestination,
                            blnDirected,
                            weight);
                    edges.get(vtxSource.getId()).put(vtxDestination, iedgeNew);
                }
                
                // Make the defaults of an Edge to be undirected and not-predicted
                iedgeNew.getSystemAttributer().addAttributeValue(MeerkatSystem.PREDICTED, "false", new Date(), tf);
                iedgeNew.getSystemAttributer().addAttributeValue(MeerkatSystem.DIRECTED, "false", new Date(), tf);
                
                for (Object key : ob.keySet()) {
                    String strKey = null;
                    if(key=="source" || key=="target"){
                        continue;
                    }
                    
                    strKey = (String)key;
                    String strValue = ob.get(strKey).toString();                  

                    if (strKey != null) {
                        boolean blnSysAtt = false;
                        for (String s : MeerkatSystem.SystemAttributes) {
                            if (strKey.toUpperCase().equals(s)) {
                                iedgeNew.getSystemAttributer().addAttributeValue(
                                    s,
                                    strValue,
                                    new Date(),
                                    tf);

                                blnSysAtt = true;
                                break;
                            }
                        }
                        if (strKey.equalsIgnoreCase("weight")) {
                            weight = Double.parseDouble(strValue);
                            iedgeNew.setWeight(weight);
                        } 
                        if (!blnSysAtt) {
                            iedgeNew.getUserAttributer().addAttribute(strKey,
                                    strValue,
                                    tf);
                        }
                    }
                }
                dynaGraph.addEdge(iedgeNew, tf);
            }
        }catch(IOException | NumberFormatException | ParseException e){
            System.out.println("JSONException "+e.getMessage());
            
        }
        
    }
    
    /**
     * MethodName: firstRun()
     * Description: counts the number of vertices and edges from .JSON file.
     * @version: 1.0
     * @author: sankalp
     */   
    private void firstRun(){
        System.out.println("FIRST RUN");
        
        try{
            InputStreamReader reader = new InputStreamReader(new FileInputStream(strFilePath));
            BufferedReader br = new BufferedReader(reader, BUFFER_SIZE);
            
            StringBuilder sb  = new StringBuilder();
            String strCurrentLine = br.readLine().trim();            
            
            while (strCurrentLine != null) {
                strCurrentLine = strCurrentLine.toLowerCase().trim();

                if (strCurrentLine.isEmpty()) {
                    strCurrentLine = br.readLine();
                    continue;
                }
                
                sb.append(strCurrentLine);
                strCurrentLine = br.readLine();
            }   
             
            
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(sb.toString());
            System.out.println("check2: " + json);
            
            JSONArray jarray_nodes =  (JSONArray) json.get("nodes");
            System.out.println("array is "+ jarray_nodes);
            
            for(int i=0;i<jarray_nodes.size();i++){
                vertexCount++;
                String strId = ((JSONObject) jarray_nodes.get(i)).get("id").toString();
                int intId = Integer.parseInt(strId);
                if (minVID > intId) {
                    minVID = intId;
                } else if (maxVID < intId) {
                    maxVID = intId;
                }
            }
            
            
            System.out.println("Vertex Count :"+vertexCount);
            
            JSONArray jarray_links =  (JSONArray) json.get("links");
            System.out.println("array is "+ jarray_links);
            
            edgeCount=jarray_links.size();
            System.out.println("Edge Count :"+edgeCount);       
            
        }catch(IOException | NumberFormatException | ParseException e){
            System.out.println("JSONException "+e.getMessage());
            
        }
        
        
        
    }
    
    
}
