/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.parser;

import config.ImportFileFilters;
import config.AppConfig;
import config.ExportFileFilters;
import config.FileFilter;
import config.LangConfig;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
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
 *
 * @author aicml_adm
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2018-01-24      Talat           Added Default Project
 */
public class ApplicationXML {
    public static void Parse(String pstrFilePath) {        
        String strCallingFunction = "ApplicationXML.Parse()";
        try{
//                File fXmlFile = new File(pstrFilePath);
                InputStream fXmlFile = ApplicationXML.class.getClassLoader().getResourceAsStream(pstrFilePath);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);

                //optional, but recommended
                //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                doc.getDocumentElement().normalize();		 
                NodeList nList = doc.getElementsByTagName(AppConfig.APPLICATION_ROOT_TAG);

                for (int temp = 0; temp < nList.getLength(); temp++) {		 
                    Node nNode = nList.item(temp);		 
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {		                             
                        Element eElement = (Element) nNode;
                        AppConfig.setDefaultLang(eElement.getElementsByTagName(AppConfig.DEFAULT_LANG_TAG).item(0).getTextContent());
                        AppConfig.setDefaultProject(eElement.getElementsByTagName(AppConfig.DEFAULT_PROJECT_TAG).item(0).getTextContent());
                        AppConfig.setDebug(Boolean.parseBoolean(eElement.getElementsByTagName(AppConfig.DEBUG_TAG).item(0).getTextContent()));

                        AppConfig.setScreenZeroTitle(eElement.getElementsByTagName(AppConfig.SCREENZERO_TITLE_TAG).item(0).getTextContent());
                        AppConfig.setScreenZeroLogoPath(eElement.getElementsByTagName(AppConfig.SCREENZERO_LOGOPATH_TAG).item(0).getTextContent());
                        
                        languageselect.LanguageSelect.setWindowTitle(eElement.getElementsByTagName(LangConfig.LANGSELECTWINDOWTITLE_TAG).item(0).getTextContent());
                        languageselect.LanguageSelect.setWindowIcon(eElement.getElementsByTagName(LangConfig.LANGSELECTWINDOWICON_TAG).item(0).getTextContent());
                        languageselect.LanguageSelect.setLabelText(eElement.getElementsByTagName(LangConfig.LANGSELECTWINDOWLABEL_TAG).item(0).getTextContent());
                        languageselect.LanguageSelect.setCheckboxText(eElement.getElementsByTagName(LangConfig.LANGSELECTWINDOWCHECKBOX_TAG).item(0).getTextContent());
                        languageselect.LanguageSelect.setButtonText(eElement.getElementsByTagName(LangConfig.LANGSELECTWINDOWBUTTON_TAG).item(0).getTextContent());
                    } // End of if

                    
                    NodeList lstSupportedFiles = doc.getElementsByTagName(AppConfig.SUPPORTEDFILES_TAG);
                    for (int intSupportedFilesCounter = 0; intSupportedFilesCounter < lstSupportedFiles.getLength(); intSupportedFilesCounter++) {                                        
                        Node nodeMenuOption = lstSupportedFiles.item(intSupportedFilesCounter);
                        NodeList childMenuItems = nodeMenuOption.getChildNodes();                                    
                        for (int intMenuItemCounter = 0; intMenuItemCounter < childMenuItems.getLength(); intMenuItemCounter++) {
                            Node nodeMenuItem = childMenuItems.item(intMenuItemCounter);
                            if (nodeMenuItem instanceof Element) {
                                String strFilterDisplay = nodeMenuItem.getLastChild().getTextContent().trim();
                                String strExtension = nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_EXTENSION_TAG).getNodeValue();
                                String strReaderID = nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_ID_TAG).getNodeValue();
                                int intGraphType = Integer.parseInt(nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_TYPE_TAG).getNodeValue());

                                ImportFileFilters.addFileFilter(new FileFilter(strFilterDisplay, strExtension, strReaderID, intGraphType));
                            }
                        }
                    }
                    
                    
                    NodeList lstSupportedExportFiles = doc.getElementsByTagName(AppConfig.SUPPORTEDEXPORTEDFILES_TAG);
                    for (int intSupportedFilesCounter = 0; intSupportedFilesCounter < lstSupportedExportFiles.getLength(); intSupportedFilesCounter++) {                                        
                        Node nodeMenuOption = lstSupportedExportFiles.item(intSupportedFilesCounter);
                        NodeList childMenuItems = nodeMenuOption.getChildNodes();                                    
                        for (int intMenuItemCounter = 0; intMenuItemCounter < childMenuItems.getLength(); intMenuItemCounter++) {
                            Node nodeMenuItem = childMenuItems.item(intMenuItemCounter);
                            if (nodeMenuItem instanceof Element) {
                                String strFilterDisplay = nodeMenuItem.getLastChild().getTextContent().trim();
                                String strExtension = nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_EXTENSION_TAG).getNodeValue();
                                String strReaderID = nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_ID_TAG).getNodeValue();
                                int intGraphType = Integer.parseInt(nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_TYPE_TAG).getNodeValue());

                                ExportFileFilters.addFileFilter(new FileFilter(strFilterDisplay, strExtension, strReaderID, intGraphType));
                            }
                        }
                    }
                } // End of for 
        } catch (ParserConfigurationException | SAXException | IOException | DOMException ex) {
            System.out.println(strCallingFunction+"Error in Parsing the XML File: "+pstrFilePath);
        }
    }
}
