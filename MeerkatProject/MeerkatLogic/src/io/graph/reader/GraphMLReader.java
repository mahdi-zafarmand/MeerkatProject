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
import datastructure.general.Pair;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
public class GraphMLReader<T> extends GraphReader {

    /**
     * <graphml xmlns="http://graphml.graphdrawing.org/xmlns"  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     * xsi:schemaLocation="http://graphml.graphdrawing.org/xmlns http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd">
     * <key id="d0" for="node" attr.name="color" attr.type="string">yellow</key>
     * <key id="d1" for="edge" attr.name="weight" attr.type="double"/>
     * <graph id="G" edgedefault="undirected">
     * <node id="n0">
     * <data key="d0">green</data>
     * </node>
     * <node id="n1"/>
     * <node id="n2">
     * <data key="d0">blue</data>
     * </node>
     * <node id="n3">
     * <data key="d0">red</data>
     * </node>
     * <node id="n4"/>
     * <node id="n5">
     * <data key="d0">turquoise</data>
     * </node>
     * <edge id="e0" source="n0" target="n2">
     * <data key="d1">1.0</data>
     * </edge>
     * <edge id="e1" source="n0" target="n1">
     * <data key="d1">1.0</data>
     * </edge>
     * <edge id="e2" source="n1" target="n3">
     * <data key="d1">2.0</data>
     * </edge>
     * <edge id="e3" source="n3" target="n2"/>
     * <edge id="e4" source="n2" target="n4"/>
     * <edge id="e5" source="n3" target="n5"/>
     * <edge id="e6" source="n5" target="n4">
     * <data key="d1">1.1</data>
     * </edge>
     * </graph>
     * </graphml>
     */
    public static final String vertex = "node";

    /**
     *
     */
    public static final String edge = "edge";

    /**
     *
     */
    public static final String graph = "graph";

    /**
     *
     */
    public static final String id = "id";

    /**
     *
     */
    public static final String edgeDefault = "edgedefault";

    Map<String, IVertex> mapAllVertices = new HashMap<>();
    Map<IVertex, List<TimeFrame>> mapVertexTFs = new HashMap<>();
    
    Set<IEdge> mapAllEdges = new HashSet<>();
    Map<IEdge, List<TimeFrame>> mapEdgeTFs = new HashMap<>();
    
    Map<TimeFrame, IStaticGraph> mapAllGraphs = new HashMap<>();

    Map<String, Pair<String, String>> mapFor2AttName2AttType
            = new HashMap<>();

    Map<String, Pair<String, String>> mapFor2AttName2AttDefaultVal
            = new HashMap<>();

    /**
     *
     * @param pstrFilePath
     */
    public GraphMLReader(String pstrFilePath) {
        super(pstrFilePath);
    }

    /**
     *
     * @return
     */
    @Override
    public IDynamicGraph<IVertex, IEdge<IVertex>> loadFile() {

        try {

            File flXml = new File(strFilePath);
            DocumentBuilderFactory docbfact
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder docbld = docbfact.newDocumentBuilder();
            Document doc = docbld.parse(flXml);

            doc.getDocumentElement().normalize();
            NodeList nKeyList = doc.getElementsByTagName("key");

            readKeys(nKeyList);

            NodeList nGrpahList = doc.getElementsByTagName(this.graph);
            boolean dynamic = nGrpahList.getLength() > 1;

            for (int i = 0; i < nGrpahList.getLength(); i++) {
                TimeFrame tf;
                boolean default_edge_directed = false;

                Element g = (Element) nGrpahList.item(i);

                boolean hasID = g.hasAttribute(id);

                boolean hasEdgeDefault = g.hasAttribute(edgeDefault);

                boolean hasTF = g.hasAttribute("timeframe");

                String pstrId;
                String pstrEdgeDefault;
                String pstrTimeFrame;

                if (hasID) {
                    pstrId = g.getAttribute(id);
                }

                if (hasEdgeDefault) {
                    pstrEdgeDefault = g.getAttribute(edgeDefault);
                    if (pstrEdgeDefault.toLowerCase().equals("directed")) {
                        default_edge_directed = true;
                    } else {
                        default_edge_directed = false;
                    }
                }

                if (hasTF) {
                    pstrTimeFrame = g.getAttribute("timeframe");
                    tf = new TimeFrame(pstrTimeFrame);
                } else if (dynamic) {
                    tf = new TimeFrame(i + "");
                } else {
                    tf = TimeFrame.DEFAULT_TIMEFRAME;
                }


                NodeList vertexList = g.getElementsByTagName(vertex);
                for (int j = 0; j < vertexList.getLength(); j++) {
                    Element vertexElement = (Element) vertexList.item(j);

                    String strId = vertexElement.getAttribute(id);

                    if (vertexElement.hasAttribute(MeerkatSystem.FILE_ID)) {
                        strId = vertexElement.getAttribute(MeerkatSystem.FILE_ID);
                    }

                    IVertex v;
                    if (mapAllVertices.containsKey(strId)) {
                        v = mapAllVertices.get(strId);
                    } else {
                        v = new Vertex();
                        v.getUserAttributer().addAttribute(MeerkatSystem.FILE_ID, strId, tf);
//                        v.getUserAttributer().addAttribute(id, strId, tf);
                        mapAllVertices.put(strId, v);
                        mapVertexTFs.put(v, new LinkedList<>());
                    }
                    
                    mapVertexTFs.get(v).add(tf);

                    NodeList dataList
                            = vertexElement.getElementsByTagName("data");
                    for (int w = 0; w < dataList.getLength(); w++) {
                        Element data = (Element) dataList.item(w);
//                        System.out.println("GraphMLReader data --> " +data.getAttribute("key") );
                        String data_key = data.getAttribute("key");
                        String data_value = data.getTextContent();

                        if (MeerkatSystem.isSystemAttribute(data_key.toUpperCase())) {
                            v.getSystemAttributer().addAttributeValue(
                                    data_key.toUpperCase(), 
                                    data_value, 
                                    new Date(), 
                                    tf);
                        } else if (!data_key.equals(id)) {
                            v.getUserAttributer()
                                .addAttribute(
                                    data_key,
                                    data_value,
                                    tf);
                        }
                    }
                }

                NodeList edgeList = g.getElementsByTagName(edge);
                
                for (int k = 0; k < edgeList.getLength(); k++) {
                    Element edgeElement = (Element) edgeList.item(k);

                    // String strId = edgeElement.getAttribute("id");
                    String strSourceId = edgeElement.getAttribute("source");
                    String strTargetId = edgeElement.getAttribute("target");

                    boolean hasDirection = edgeElement.hasAttribute("directed");
                    
                    if (hasDirection) {
                        default_edge_directed = Boolean.parseBoolean(edgeElement
                                .getAttribute("directed"));
                    }
                    IEdge iedgeNew; 

                    
                    iedgeNew = new Edge(mapAllVertices.get(strSourceId),
                            mapAllVertices.get(strTargetId),
                            default_edge_directed);


                    mapAllEdges.add(iedgeNew);
                    mapEdgeTFs.put(iedgeNew, new LinkedList<>());
                    
                    mapEdgeTFs.get(iedgeNew).add(tf);
                    
                    // Make the defaults of an Edge to be undirected and not-predicted
                    iedgeNew.getSystemAttributer().addAttributeValue(MeerkatSystem.PREDICTED, "false", new Date(), tf);
                    iedgeNew.getSystemAttributer().addAttributeValue(MeerkatSystem.DIRECTED, "false", new Date(), tf);

                    NodeList dataList = edgeElement.getElementsByTagName("data");
                    for (int w = 0; w < dataList.getLength(); w++) {
                        Element data = (Element) dataList.item(w);
                        String data_key = data.getAttribute("key");
                        String data_value = data.getTextContent();

                        if (MeerkatSystem.isSystemAttribute(data_key.toUpperCase())) {
                            iedgeNew.getSystemAttributer().addAttributeValue(
                                    data_key.toUpperCase(), 
                                    data_value, 
                                    new Date(), 
                                    tf);
                        } else { 
                            iedgeNew.getUserAttributer()
                                .addAttribute(
                                        mapFor2AttName2AttType.get(data_key).getKey(),
                                        data_value,
                                        tf);
                        }
                    }
                }

                IStaticGraph<IVertex, IEdge<IVertex>> staticGraph
                        = new StaticGraph<>(vertexList.getLength(),
                                edgeList.getLength());

                mapAllGraphs.put(tf, staticGraph);

            }

            IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph =
                    new DynamicGraph<>(mapAllVertices.size(), 
                            mapAllEdges.size());
            
            mapAllGraphs.keySet().stream().forEach((tf) -> {
                dynaGraph.addGraph(tf, mapAllGraphs.get(tf));
            });
            
            for (IVertex v : mapVertexTFs.keySet()) {
                mapVertexTFs.get(v).stream().forEach((tf) -> {
                    dynaGraph.addVertex(v, tf);
                });
            } 
            
            
            for (IEdge<IVertex> e : mapEdgeTFs.keySet()) {
                for (TimeFrame tf : mapEdgeTFs.get(e)) {
                    dynaGraph.addEdge(e, tf);
                }
            }
            
            return dynaGraph;

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(GraphReader.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return null;

    }

    private void readKeys(NodeList nKeyList) {
        for (int i = 0; i < nKeyList.getLength(); i++) {
            Element n = (Element) nKeyList.item(i);
            String id = n.getAttribute("id");

            boolean blnfor = n.hasAttribute("for");
            String strFor = n.getAttribute("for");

            boolean blnAttName = n.hasAttribute("attr.name");
            String strAttName = n.getAttribute("attr.name");

            boolean blnAttType = n.hasAttribute("attr.type");
            String strAttType = n.getAttribute("attr.type");

            String strDefaultValue = n.getTextContent();

            mapFor2AttName2AttType.put(id,
                    new Pair<>(strAttName, strAttType));

            mapFor2AttName2AttDefaultVal.put(id,
                    new Pair<>(strAttName, strDefaultValue));
        }
    }
}
