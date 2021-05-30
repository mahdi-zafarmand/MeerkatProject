/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.parser;

import config.AppConfig;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import threadtree.ThreadTreeConfig;
import threadtree.ThreadTreeLangParameters;

/**
 *  Class Name      : ThreadTree
 *  Created Date    : 2015-07-15
 *  Description     : Responsible for any IO associated with the ThreadTree in the UI layer
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class ThreadTree {
    
    /**
     *  Method Name     : Parse
     *  Created Date    : 2015-07-15
     *  Description     : Reads the ThreadTreeParameters from the xml file (language dependent parameters)
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrFilePath
     *  @return ThreadTreeParameters
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static ThreadTreeLangParameters Parse (String pstrFilePath) {      
        String strCallingFunction = "Utilities.ReadThreadTreeParameters: ";
        
        ThreadTreeLangParameters treeParam = null ;
        
        try{
//            File fXmlFile = new File(pstrFilePath);
            InputStream fXmlFile = ThreadTree.class.getClassLoader().getResourceAsStream(pstrFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();		 
            NodeList nlstRoot = doc.getElementsByTagName(AppConfig.LANGUAGE_ROOT_TAG);

            for (int temp = 0; temp < nlstRoot.getLength(); temp++) {		 
                Node nRoot = nlstRoot.item(temp);		 
                
                NodeList nThreadParameters = doc.getElementsByTagName(ThreadTreeConfig.THREADTREEPARAMETERS_TAG);

                for (int intMenuCounter = 0; intMenuCounter < nThreadParameters.getLength(); intMenuCounter++) {
                    Node nodeThreadParameter = nThreadParameters.item(intMenuCounter);

                    if (nodeThreadParameter.getNodeType() == Node.ELEMENT_NODE) {		                             
                        Element eElement = (Element) nodeThreadParameter;                                
                        
                        treeParam = new ThreadTreeLangParameters(
                                eElement.getElementsByTagName(ThreadTreeConfig.PROJECT_TAG).item(0).getTextContent(),     
                                
                                eElement.getElementsByTagName(ThreadTreeConfig.NODE_TAG).item(0).getTextContent(), 
                                eElement.getElementsByTagName(ThreadTreeConfig.EDGE_TAG).item(0).getTextContent(),
                                
                                eElement.getElementsByTagName(ThreadTreeConfig.USER_TAG).item(0).getTextContent(),
                                eElement.getElementsByTagName(ThreadTreeConfig.TOPIC_TAG).item(0).getTextContent(),
                                eElement.getElementsByTagName(ThreadTreeConfig.HASHTAG_TAG).item(0).getTextContent(),
                                
                                eElement.getElementsByTagName(ThreadTreeConfig.AUTHOR_TAG).item(0).getTextContent(),
                                eElement.getElementsByTagName(ThreadTreeConfig.TERM_TAG).item(0).getTextContent(),
                                eElement.getElementsByTagName(ThreadTreeConfig.TERMCLOUD_TAG).item(0).getTextContent()                                
                        );
                    }                        
                } // End of if
            } // End of for             
            return treeParam;
        } catch (ParserConfigurationException | SAXException | IOException | DOMException ex) {
                System.out.println(strCallingFunction+"Error in Parsing the XML Language File: "+pstrFilePath);
        }
        return null;
    }
}