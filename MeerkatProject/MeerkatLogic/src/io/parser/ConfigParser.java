/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.parser;

import config.MeerkatSystem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *  Class Name      : ConfigParser
 *  Created Date    : 2015-08-26
 *  Description     : Parser to parse the config file that contains the mapping between the ID and the Class Names
 *                  : The class names will be used for java reflection to invoke various classes based on the user interaction
 *  Version         : 
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class ConfigParser {
    /**
     *  Method Name     : Parse
     *  Created Date    : 2015-08-26
     *  Description     : Parses an XML to retrieve the mapping between the Classes and their respective IDs
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrFilePath : String
     *  @return HashMap<String, HashMap<String, String>>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static HashMap<String, HashMap<String, String>> Parse(String pstrFilePath) {
        String strCallingFunction = "ConfigParser.Parse(): ";
//        System.out.println("ConfigParse.Parse: Started Parsing...");
        
        HashMap<String, HashMap<String, String>> hmapMapping = new HashMap<>();
        List<String> lstTags = asList(
                MeerkatSystem.MEERKAT_TAG, 
                MeerkatSystem.LAYOUTS_TAG,
                MeerkatSystem.COMMUNITYMININGS_TAG,
                MeerkatSystem.LINKPREDICTION_TAG,
                MeerkatSystem.SHORTESTPATH_TAG,
                MeerkatSystem.METRICS_TAG,
                MeerkatSystem.GRAPHS_TAG,
                MeerkatSystem.READERS_TAG,
                MeerkatSystem.WRITERS_TAG
        );
        
        try {           
            
//            File fXmlFile = new File(pstrFilePath);
            InputStream fXmlFile = ConfigParser.class.getClassLoader().getResourceAsStream(pstrFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();		 
            NodeList nList = doc.getElementsByTagName(MeerkatSystem.APPLICATION_ROOT_TAG);

            for (int temp = 0; temp < nList.getLength(); temp++) {		 
                Node nNode = nList.item(temp);		 
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {		                             
                                      
                    for (String strTag : lstTags) {
                        
                        // System.out.println("ConfigParser.Parse(): Current Tag is "+strTag);
                        HashMap<String, String> hmapIDClassMapping = new HashMap<>();
                        NodeList nMeerkat = doc.getElementsByTagName(strTag);
                        for (int intSameTagCounter = 0; intSameTagCounter < nMeerkat.getLength(); intSameTagCounter++) {
                            Node nodeTag = nMeerkat.item(intSameTagCounter);                                                
                            NodeList childMenuItems = nodeTag.getChildNodes();                                    
                            for (int intClassItemCounter = 0; intClassItemCounter < childMenuItems.getLength(); intClassItemCounter++) {
                                Node nodeClass = childMenuItems.item(intClassItemCounter);
                                if (nodeClass instanceof Element) {
                                    String strClassName = nodeClass.getLastChild().getTextContent().trim();
                                    String strID = nodeClass.getAttributes().getNamedItem(MeerkatSystem.ID_ATTRIBUTE_TAG).getNodeValue();
                                    // System.out.println("ConfigParser.Parse(): ID: "+strID+"\tClass: "+strClassName);
                                    hmapIDClassMapping.put(strID, strClassName);
                                    //System.out.println("ConfigParser: Parse : classID --> className: " + strID + "," + strClassName );
                                }
                            }
                        }
                        // System.out.println("ConfigParser.Parse(): Added Tag is "+strTag);
                        hmapMapping.put(strTag, hmapIDClassMapping);
                    }
                }
            }
           
            return hmapMapping;
        } catch (ParserConfigurationException | SAXException | IOException | DOMException ex) {
            System.out.println(strCallingFunction+"Error in Parsing the XML Biz Config File: "+pstrFilePath);
        }
        return null;
    }
}
