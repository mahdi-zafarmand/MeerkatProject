/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.graph.writer;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import io.graph.reader.GraphMLReader;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public class GraphMLWriter<V extends IVertex, E extends IEdge<V>>
    extends GraphWriter<V,E> {

    /**
     *
     */
    public static final String extension = ".graphml";
    @Override
    public String write(IDynamicGraph<V, E> pIGraph, String pstrPath2FileName) {
        StringBuilder stbfileNames = new StringBuilder();
        
        if (pIGraph.getAllTimeFrames().size() == 1) {
             String strFilN = write(pIGraph, 
                    pIGraph.getAllTimeFrames().get(0), 
                    pstrPath2FileName );

            stbfileNames.append(strFilN);
        } else {
            for (int i = 0 ; i < pIGraph.getAllTimeFrames().size() ; i++) {
                String strFilN = write(pIGraph, 
                        pIGraph.getAllTimeFrames().get(i),
                        pstrPath2FileName + "_" + i);

                stbfileNames.append(strFilN);

                if (i < pIGraph.getAllTimeFrames().size() - 1) {
                    stbfileNames.append(",");
                }
            }
        }
    
        return stbfileNames.toString();
    }

    @Override
    public String write(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String pstrPath2FileName) {
        
        String strFileName = pstrPath2FileName + extension;
        try {
            Document dom;
            Element e = null;
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            
            DocumentBuilder db = dbf.newDocumentBuilder();
            
            dom = db.newDocument();
        
            Element graphml = dom.createElement("graphml");
            graphml.setAttribute("xmlns", 
                    "http://graphml.graphdrawing.org/xmlns/graphml");
            graphml.setAttribute("xmlns:xsi", 
                    "http://www.w3.org/2001/XMLSchema-instance");
            graphml.setAttribute("xsi:schemaLocation", 
                    "http://graphml.graphdrawing.org/xmlns/graphml");
            Element rootEle = dom.createElement("graph");
            
            graphml.appendChild(rootEle);
            
            rootEle.setAttribute("edgedefault", "undirected");
            
            for (V v : pIGraph.getVertices(tf)) {
                e = dom.createElement("node");
                e.setAttribute(GraphMLReader.id, v.getUserAttributer()
                        .getAttributeValue(MeerkatSystem.FILE_ID, tf));
                for (String strAttName : v.getUserAttributer().getAttributeNames()) {
                    if (!strAttName.equals(MeerkatSystem.FILE_ID) 
                            && !strAttName.equals(MeerkatSystem.MEERKAT_ID)) {
                        Element eAttr = dom.createElement("data");
                        eAttr.setAttribute("key", strAttName);
                        eAttr.appendChild(dom.createTextNode(v.getUserAttributer()
                                .getAttributeValue(strAttName, tf)));
                        e.appendChild(eAttr);
                    }
                }
                
                for (String strAttName : v.getSystemAttributer().getAttributeNames()) {
                    if (!strAttName.equals(MeerkatSystem.FILE_ID)
                            && !strAttName.equals(MeerkatSystem.FILE_ID)) {
                        Element eAttr = dom.createElement("data");
                        eAttr.setAttribute("key", strAttName);
                        eAttr.appendChild(dom.createTextNode(v.getSystemAttributer()
                                .getAttributeValue(strAttName, tf)));
                        e.appendChild(eAttr);
                    }
                }
                rootEle.appendChild(e);
                
                
            }
            
            for (E edge : pIGraph.getEdges(tf)) {
                e = dom.createElement("edge");
                e.setAttribute("directed", edge.isDirected() + "");
                e.setAttribute("source", edge.getSource().getUserAttributer()
                .getAttributeValue(MeerkatSystem.FILE_ID, tf));
                e.setAttribute("target", edge.getDestination().getUserAttributer()
                .getAttributeValue(MeerkatSystem.FILE_ID, tf));
                e.setAttribute("weight", edge.getWeight() + "");
                for (String strAttr : edge.getUserAttributer().getAttributeNames()) {
                    Element eAttr = dom.createElement("data");
                    eAttr.setAttribute("key", strAttr);
                    eAttr.appendChild(dom.createTextNode(edge.getUserAttributer()
                    .getAttributeValue(strAttr, tf)));
                    e.appendChild(eAttr);
                }
                
                for (String strAttr : edge.getSystemAttributer().getAttributeNames()) {
                    Element eAttr = dom.createElement("data");
                    eAttr.setAttribute("key", strAttr);
                    eAttr.appendChild(dom.createTextNode(edge.getSystemAttributer()
                    .getAttributeValue(strAttr, tf)));
                    e.appendChild(eAttr);
                }
                
                rootEle.appendChild(e);
            }
            
            
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(graphml);
            StreamResult result = new StreamResult(new File(strFileName));

            // Output to console for testing
//             StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(GraphMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(GraphMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(GraphMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return strFileName;
    }

    @Override
    public String write(IDynamicGraph<V, E> pIGraph, TimeFrame tf, List<V> plstVertices, String pstrPath2FileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
