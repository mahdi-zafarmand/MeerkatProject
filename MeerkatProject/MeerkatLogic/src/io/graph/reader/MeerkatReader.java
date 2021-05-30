/*
 * meerkat@aicml June 2015
 */
package io.graph.reader;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IStaticGraph;
import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.graph.impl.DynamicGraph;
import datastructure.core.graph.impl.Edge;
import datastructure.core.graph.impl.StaticGraph;
import datastructure.core.graph.impl.Vertex;
import datastructure.general.DynamicArray;
import datastructure.general.Pair;
import io.serialize.ProjectDeserialize;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class Name : MeerkatReader Created Date : 2015-06-xx Description : A
 * StaticGraph Reader that specifically reads the graph of the format .meerkat
 * Version : 1.0
 *
 * @author : Talat
 *
 * Edit History Date Author	Description 2015-10-28 Afra - fixed bugs in dynamic
 * reading implementation parts. - added maps to keep track of same vertices &
 * edges throughout different time frames. 2015-10-19 Afra - added
 * implementation for constructing dynamic graphs as well as static graphs.
 * 2015-08-27 Talat Adding Override annotation July 17, 2015 Afra	change
 * stateChecking of the reader from String to Enum. cleaned the code structure
 * by: - avoiding more than two nested loops - code line length no more than 82
 * characters. - adding ID in the file as an attribute called PRIMARY_ID. -
 * adding a hashmap for keeping vertices with their PRIMARY_ID to match edges to
 * vertices. - adding parts for reading timeframes and arcs, later on we need to
 * create dynamicgraphs based on timeframes. - adding implementation for reading
 * arcs.
 */

/* TODO: Talat has made some changes such as "->" to the meerkat file format! 
 We need to check this with Osmar since it is not common 
 in other graph formats.
 */
public class MeerkatReader extends GraphReader {

    private IDynamicGraph<IVertex, IEdge<IVertex>> dynamicGraph;

    private Map<Integer, IVertex> hmpAllVertices;
    private DynamicArray<Map<IVertex,IEdge>> edges;
    
    private Map<Integer, IVertex> arrTFVertices;

    enum State {
        NONE,
        STARTTIMEFRAME,
        VERTEX,
        EDGE,
        ARC
    }

    private State readState = State.NONE;

    private int timeframe = -1;
    private TimeFrame tf;

    Set<String> setSeenVertices = new HashSet<>();
    Set<Pair> setSeenEdges = new HashSet<>();

    int intTimeFrameCount = 0;
    List<Integer> lstVertexCount = new LinkedList<>();
    List<Integer> lstEdgeCount = new LinkedList<>();

    int minVID = Integer.MAX_VALUE;
    int maxVID = 0;
    
    private static final String HEX_PATTERN1 = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
    private static final String HEX_PATTERN2 = "^0x([A-Fa-f0-9]{6}ff)$";
    
    private static final String default_double = "0.5";

    /**
     *
     * @param pstrFilePath
     */
    public MeerkatReader(String pstrFilePath) {
        super(pstrFilePath);
    }

    /**
     *
     * @return
     */
    @Override
    public IDynamicGraph<IVertex, IEdge<IVertex>> loadFile() {

        firstRun();
        
        //create a graph if and only if one or more vertices are present.
        if(setSeenVertices.size()>0){
        
            dynamicGraph =
                    new DynamicGraph<>(setSeenVertices.size(), setSeenEdges.size());
            hmpAllVertices = new HashMap<>();

            edges = new DynamicArray<>(setSeenVertices.size());

            String strCurrentLine = null;

            try {
//                InputStreamReader reader = new InputStreamReader(new FileInputStream(strFilePath));
//                BufferedReader br = new BufferedReader(reader, BUFFER_SIZE);

                InputStream is = MeerkatReader.class.getClassLoader().getResourceAsStream(strFilePath);
                BufferedReader br = new BufferedReader(new InputStreamReader(is), BUFFER_SIZE);


                strCurrentLine = br.readLine().trim();

                while (strCurrentLine != null) {
                    strCurrentLine = strCurrentLine.trim();

                    if (strCurrentLine.isEmpty()) {
                        strCurrentLine = br.readLine();
                        continue;
                    }

                    /* Set the component such that all the consecutive lines read 
                     *the particular component
                     */
                    // Using a temporary variable so that the main tags are case insensitive and the URLs maintian their case
                    String strCurrentLine_Lower = strCurrentLine.toLowerCase(); 
                    switch (strCurrentLine_Lower) {
                        case "*vertices":
                            readState = State.VERTEX;
                            break;
                        case "*edges":
                            readState = State.EDGE;
                            break;
                        case "*timeframe":
                            /* Number of timeframes in the file determines whether the 
                             graph is dynamic or not.
                             */
                            timeframe++;
    //                        System.out.println(timeframe);
                            arrTFVertices = new HashMap<>();
                            readState = State.STARTTIMEFRAME;
                            break;
                        case "*arcs":
                            readState = State.ARC;
                            break;
                        case "*end":
                            break;
                        default:
                            switch (readState) {
                                case STARTTIMEFRAME:
                                    tf = new TimeFrame(strCurrentLine);
                                    IStaticGraph<IVertex,IEdge<IVertex>>graph = 
                                            new StaticGraph<>(lstVertexCount.get(timeframe), 
                                                    lstEdgeCount.get(timeframe));
                                     dynamicGraph.addGraph(tf, graph);
                                    readState = State.NONE;
                                    break;
                                case VERTEX:
                                    if(tf!=null)
                                        readVertex(strCurrentLine);
                                    break;
                                case EDGE:
                                    if(tf!=null)
                                        readEdge(strCurrentLine, false);
                                    break;
                                case ARC:
                                    if(tf!=null)
                                        readEdge(strCurrentLine, true);
                                    break;
                                default:
                                    break;
                            }
                            break;
                    }
                    strCurrentLine = br.readLine();
                }
                is.close();
                br.close();

            } catch (IOException exIO) {
                System.out.println("IO.MeerkatReader.read(): Unable to parse the file " + strFilePath);
            }
        }
        return dynamicGraph;
    }

    private void readVertex(String strCurrentLine) {
        // Example of the format
        // 0  {id=etiquette;d0=34;}
//        System.out.println(strCurrentLine);
        String[] arrstrCurrentVertex = strCurrentLine.split("\\s+");
        String regex = "\\d+";

        /* Since we do not need this strVertexID,
         * we should change the input format to:
         * {att1=val1;att2=val2;att3=val3}
         * */
        String strVertexID = arrstrCurrentVertex[0].trim();
        if(strVertexID.matches(regex)){
            int intVID = Integer.parseInt(strVertexID);

            IVertex ivtNewV = null;
            if (hmpAllVertices.get(intVID)== null) {
                ivtNewV = new Vertex();
                hmpAllVertices.put(intVID,ivtNewV);
                arrTFVertices.put(intVID,ivtNewV);
            } else if (arrTFVertices.get(intVID) == null) {
                arrTFVertices.put(intVID, hmpAllVertices.get(intVID));
                ivtNewV = arrTFVertices.get(intVID);
            } else {
                ivtNewV = hmpAllVertices.get(intVID);
            }

            ivtNewV.getUserAttributer().addAttribute(MeerkatSystem.FILE_ID,
                        strVertexID,
                        tf);
    
            if (strCurrentLine.contains("{") && strCurrentLine.contains("}")) {
                String attributes = strCurrentLine.substring(
                        strCurrentLine.indexOf('{') + 1, strCurrentLine.lastIndexOf('}'));
                String[] arrstrVertexAttributes = attributes
                        .replace("\"", "").split(";");

                for (String strCurrentAttribute : arrstrVertexAttributes) {
                    strCurrentAttribute = strCurrentAttribute.trim();
                    if (!strCurrentAttribute.isEmpty()) {
                        String[] strAttElements = strCurrentAttribute.split("=");
                        String strKey = strAttElements[0].trim();
                        String strValue = "";
                        if (strAttElements.length == 2) {
                            strValue = strAttElements[1].trim();
                        }

                        if (strKey != null) {
                            if (MeerkatSystem.isSystemAttribute(strKey.toUpperCase())) {
                                
                                switch(strKey.toUpperCase()){
                                    case MeerkatSystem.X :
                                        if(validateNumber(strValue)){
                                            ivtNewV.getSystemAttributer().addAttributeValue(
                                                strKey.toUpperCase(),
                                                strValue,
                                                new Date(),
                                                tf);
                                        }else{
                                            ivtNewV.getSystemAttributer().addAttributeValue(
                                            strKey.toUpperCase(),
                                            default_double,
                                            new Date(),
                                            tf);                                        
                                        }
                                        break;
                                    case MeerkatSystem.Y :
                                        if(validateNumber(strValue)){
                                            ivtNewV.getSystemAttributer().addAttributeValue(
                                                strKey.toUpperCase(),
                                                strValue,
                                                new Date(),
                                                tf);
                                        }else{
                                            ivtNewV.getSystemAttributer().addAttributeValue(
                                            strKey.toUpperCase(),
                                            default_double,
                                            new Date(),
                                            tf);                                        
                                        }
                                        break;
                                    case MeerkatSystem.COLOR :
                                        if(strValue.matches(HEX_PATTERN1) || strValue.matches(HEX_PATTERN2)){
                                            ivtNewV.getSystemAttributer().addAttributeValue(
                                                strKey.toUpperCase(),
                                                strValue,
                                                new Date(),
                                                tf);
                                        }else{
                                            ivtNewV.getSystemAttributer().addAttributeValue(
                                            strKey.toUpperCase(),
                                            MeerkatSystem.getDefaultVertexColor(),
                                            new Date(),
                                            tf);                                        
                                        }
                                        break;
                                    default:
                                        ivtNewV.getSystemAttributer().addAttributeValue(
                                                strKey.toUpperCase(),
                                                strValue,
                                                new Date(),
                                                tf);
                                        break;
                                }
                                
                            }else{
                                ivtNewV.getUserAttributer().addAttribute(strKey,
                                        strValue,
                                        tf);
                            }
                        }
                    }
                }
            }

            dynamicGraph.addVertex(ivtNewV, tf);
            edges.add(ivtNewV.getId(), new HashMap<>());
        }
        
    }

    private void readEdge(String strCurrentLine, boolean blnDirected) {

        // System.out.println("MeerkatReader.readVertex(): Edge: "+strCurrentLine);
        // Example of the format
        // 9	21 {d1=3.49169435216;weight=100}
        String[] arrstrCurrentEdge = strCurrentLine.split("\\s+");

        if (arrstrCurrentEdge.length < 2) {
            return;
        }
        String regex = "\\d+";
        // ids within file
        String strFileSrc = arrstrCurrentEdge[0].trim();
        String strFileDest = arrstrCurrentEdge[1].trim();
        
        if(strFileSrc.matches(regex) && strFileDest.matches(regex)){
            
            int intFileSrc = Integer.parseInt(strFileSrc);
            int intFileDest = Integer.parseInt(strFileDest);
               
            IVertex vtxSource = hmpAllVertices.get(intFileSrc);
            IVertex vtxDestination = hmpAllVertices.get(intFileDest);
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

            if (strCurrentLine.contains("{") && strCurrentLine.contains("}")) {
                String attributes = strCurrentLine.substring(
                        (strCurrentLine.indexOf('{') + 1), strCurrentLine.lastIndexOf('}')
                );

                String[] arrstrEdgeAttributes
                        = attributes.replace("\"", "").split(";");

                for (String strCurrentAttribute : arrstrEdgeAttributes) {
                    strCurrentAttribute = strCurrentAttribute.trim();
                    if (!strCurrentAttribute.isEmpty()) {
                        String[] arrstrAttElement = strCurrentAttribute.split("=");

                        String strKey = arrstrAttElement[0].trim();
                        String strValue = "";
                        if (arrstrAttElement.length == 2) {
                            strValue = arrstrAttElement[1].trim();
                        }



                        if (strKey != null) {
                            boolean blnSysAtt = false;
                            for (String s : MeerkatSystem.SystemAttributes) {
                                if (strKey.toUpperCase().equals(s)) {
                                    if(strKey.equalsIgnoreCase(MeerkatSystem.COLOR)){
                                        if(strValue.matches(HEX_PATTERN1) || strValue.matches(HEX_PATTERN2)){
                                            iedgeNew.getSystemAttributer().addAttributeValue(
                                                s,
                                                strValue,
                                                new Date(),
                                                tf);

                                            blnSysAtt = true;
                                            break;
                                        }else{
                                            iedgeNew.getSystemAttributer().addAttributeValue(
                                                s,
                                                MeerkatSystem.getDefaultEdgeColor(),
                                                new Date(),
                                                tf);

                                            blnSysAtt = true;
                                            break;
                                        }
                                    }else{
                                        iedgeNew.getSystemAttributer().addAttributeValue(
                                            s,
                                            strValue,
                                            new Date(),
                                            tf);

                                        blnSysAtt = true;
                                        break;                                        
                                    }

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

                }
            }
            // System.out.println("Edge Attributes: "+iedgNewE.getUserAttributer().getAttributeNames().size());
            //System.out.println("\t\t\t\t\t\t====***************** ______________________________________________________MeerkatReader.readEdge() : Adding edge to timeframe " + vtxSource.getId() + "-" + vtxDestination.getId()+ "  tf = " + strTimeFrame);
            dynamicGraph.addEdge(iedgeNew, tf);
    //        System.err.println("MeerkatReader.readEdge() : number of edges in this timeframe = " + dynamicGraph.getEdges(strTimeFrame).size());
        }
    }

    private void firstRun() {
        int vCount = 0;
        int eCount = 0;
        

        try {
            
//            InputStreamReader reader = new InputStreamReader(new FileInputStream(strFilePath));
//            BufferedReader br = new BufferedReader(reader, BUFFER_SIZE);

            InputStream is = MeerkatReader.class.getClassLoader().getResourceAsStream(strFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is), BUFFER_SIZE);

            String strCurrentLine = br.readLine();
            while (strCurrentLine != null) {
                strCurrentLine = strCurrentLine.toLowerCase().trim();
                
                if (strCurrentLine.length() == 0) {
                    strCurrentLine = br.readLine();
                    continue;
                }

                /* Set the component such that all the consecutive lines read 
                 *the particular component
                 */
                switch (strCurrentLine) {
                    case "*vertices":
                        readState = State.VERTEX;
                        break;
                    case "*edges":
                        readState = State.EDGE;
                        break;
                    case "*timeframe":
                        /* Number of timeframes in the file determines
                         * whether the graph is dynamic or not.
                         */
                        //Do not allow empty time frame in which there is no vertices
                        if (vCount > 0) {
                            lstVertexCount.add(vCount);
                            lstEdgeCount.add(eCount);
                            vCount = 0;
                            eCount = 0;
                        }
                        intTimeFrameCount++;
                        readState = State.STARTTIMEFRAME;
                        break;
                    case "*arcs":
                        readState = State.ARC;
                        break;
                    case "*end":
                        break;
                    default:
                        switch (readState) {
                            case STARTTIMEFRAME:
                                break;
                            case VERTEX:
                                String regex = "\\d+";
                                vCount++;
                                String strId = "";
                                if (strCurrentLine.contains(" ")) {
                                    strId = strCurrentLine.substring(0,
                                        strCurrentLine.indexOf(" ")).trim();
                                } else {
                                    strId = strCurrentLine.trim();
                                }
                                if(strId.matches(regex)){       
                                    int intId = Integer.parseInt(strId);
                                    if (minVID > intId) {
                                        minVID = intId;
                                    } else if (maxVID < intId) {
                                        maxVID = intId;
                                    }

                                    setSeenVertices.add(strId);
                                }
                                break;
                            case EDGE:
                                eCount++;
                                String[] tokens = strCurrentLine.split("\\s+");
                                String src = tokens[0];
                                String des = tokens[1];
                                Pair<String, String> pair = new Pair<>(src, des);
                                setSeenEdges.add(pair);
                                break;
                            case ARC:
                                eCount++;
                                tokens = strCurrentLine.split("\\s+");
                                src = tokens[0];
                                des = tokens[1];
                                pair = new Pair<>(src, des);
                                setSeenEdges.add(pair);
                                break;
                            default:
                                break;
                        }
                        break;
                }
                strCurrentLine = br.readLine();
            }
            lstVertexCount.add(vCount);
            lstEdgeCount.add(eCount);
            
            /* Adding the last graph to the dynamicGraph */
            is.close();
            br.close();

        } catch (IOException exIO) {
            System.out.println("IO.MeerkatReader.read(): Unable to parse the file "+ strFilePath);
        }
    }
    
    private boolean validateNumber(String strValue) {
         try{
             Double.parseDouble(strValue);
         }catch(NumberFormatException ex){
             System.out.println("MeerkatReader:readVertex() -  NumberFormat Exception for string :"+ strValue);
             return false;
         }
        
        return true;
    }

}
