/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.text.reader;

import datastructure.core.text.HNode;
import datastructure.core.text.impl.TextualNetwork;
import io.utility.Utilities;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import main.meerkat.MeerkatBIZ;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *  Class Name      : TextualXMLReader
 *  Created Date    : 2015-10-05
 *  Description     : Reads the XML file that contains the textual data
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class TextualXMLReader extends MsgReader {

    TextualNetwork textualNetwork;

    
    /**
     *  Method Name     : load()
     *  Created Date    : 2015-10-05
     *  Description     : Overridden method that loads a file
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrFileName : String
     *  @return HNode
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-11-02      Talat           Added the functionality to store the feedback
     * 
    */
    @Override
    public TextualNetwork loadFile(String pstrFileName) {
        
        try {
            textualNetwork = new TextualNetwork(
                    Utilities.getFileNameWithoutExtension(pstrFileName), 
                    pstrFileName);
            File fXmlFile = new File(pstrFileName);
            
            DocumentBuilderFactory dbFactory = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document xmlDoc = dBuilder.parse(fXmlFile);
            xmlDoc.getDocumentElement().normalize();            
            
            Node ndCurrentNode = xmlDoc.getFirstChild();
                                    
            parseXML(ndCurrentNode, null, 0, null);
            
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println("EXCEPTION in XMLReader.read()");
            ex.printStackTrace();
        }
//        return hndRoot;
        return textualNetwork;
    }    
    
    /**
     *  Method Name     : parseXML()
     *  Created Date    : 2015-10-05
     *  Description     : Parses an XML Node and recursively parses its children
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pndCurrentNode : Node
     *  @param pndParent : Node
     *  @param pintDepth : int
     *  @param phnCurrentHNode : HNode 
     *  @param phnParentHNode : HNode
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * @return 
     *  
     * 
    */
    public void parseXML(Node pndCurrentNode, 
            Node pndParent, 
            int pintDepth,  
            HNode phnParentHNode) {
        
        HNode hnodeCurrent;
        /* strNodeName contains the tag name for the xmlNode */
        String strNodeName = pndCurrentNode.getNodeName();
        String strTitle = "";
        Double dblFeedback = null;
        
        NamedNodeMap nmAttributes = pndCurrentNode.getAttributes();
        if (nmAttributes != null) {
            for (int intAttrCount=0; 
                    intAttrCount < nmAttributes.getLength(); 
                    intAttrCount++) {
//                System.out.println("Attribute Name : " +
//                        nmAttributes.item(intAttrCount));
                if (nmAttributes.item(intAttrCount).getNodeName()
                        .equalsIgnoreCase("title")) {
                    strTitle = nmAttributes.item(intAttrCount).getNodeValue();
                } else if (nmAttributes.item(intAttrCount).getNodeName()
                        .equalsIgnoreCase("feedback")) {
                    dblFeedback = Double.parseDouble(nmAttributes
                            .item(intAttrCount).getNodeValue());
                }
            }
        }
        
        String strNodeValue = "";
        
        if (pndCurrentNode.hasChildNodes()) {                                    
                       
            NodeList lstndChildren = pndCurrentNode.getChildNodes(); 
            
            for (int i = 0; i < lstndChildren.getLength(); i++) {                
                
                Node ndChildNode = lstndChildren.item(i);        
                if (ndChildNode.getNodeName().equalsIgnoreCase("#text")) {

                    strNodeValue += ndChildNode.getTextContent();
                }
            }            
        }
        
        strNodeValue = strNodeValue.trim();
        strNodeValue = strNodeValue.replaceAll("\\p{C}", "");
        if (!strNodeValue.isEmpty()) {
            
            hnodeCurrent = textualNetwork.createHNode(strTitle,
                                    phnParentHNode,
                                    strNodeValue);
            
//            System.out.println("TextualXMLReader.parseXML(): node = " +
//                    strNodeName);
//            System.out.println("TextualXMLReader.parseXML() : content = " +
//                    strNodeValue);
        
        } else {
            hnodeCurrent = textualNetwork.createHNode(strTitle, phnParentHNode);
            
//            System.out.println("TextualXMLReader.parseXML(): node = " +
//                    strNodeName);
//            System.out.println("HNODE");
        
        }
        
        
        if (pndCurrentNode.hasChildNodes()) {                                    
                       
            /* For each non-#text child, recurse */
            NodeList lstndChildren = pndCurrentNode.getChildNodes(); 
            
            for (int i = 0; i < lstndChildren.getLength(); i++) {                
                
                Node ndChildNode = lstndChildren.item(i);        
                if (! ndChildNode.getNodeName().equalsIgnoreCase("#text")) {                    
                    
                    parseXML(ndChildNode, pndCurrentNode, pintDepth+1, 
                            hnodeCurrent);
                }
            }            
        }
        
        if (/*phnParentHNode == null && */dblFeedback != null) {
            MeerkatBIZ BizInstance = MeerkatBIZ.getMeerkatApplication();
            BizInstance.addFeedback(hnodeCurrent.getId(), dblFeedback);
        }
        
    }   
    
    /**
     *
     * @param pRoot
     */
    public static void Debug_PrintNodes(HNode pRoot) {
        // System.out.println("TextualXMLReader.Debug_PrintNodes(): Total number of children of "+pRoot.getTitle()+" : "+pRoot.getChildrenCount());
        if (pRoot.getChildrenCount() > 0) {
            for (HNode currentNode : pRoot.getChildren()) {
                Debug_PrintNodes(currentNode);
            }
        }
    }
}
