/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.parser;

import config.AppSettingsConfig;
import config.CommunityMiningConfig;
import config.SceneConfig;
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

/**
 *  Class Name      : Talat
 *  Created Date    : 2016-07-13
 *  Description     : AppSettings
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-08-22      Abhi            Modified Parse() function to include parameters for algorithms and save the parsed values in CommunityMiningConfig static variables
 *  
 * 
*/
public class AppSettingsXML {
    
    /**
     *  Method Name     : Parse
     *  Created Date    : 2016-07-13
     *  Description     : Parsing the app Settings XML file
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrFilePath : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public static void Parse(String pstrFilePath) {        
        try{
//            File fXmlFile = new File(pstrFilePath);
            InputStream fXmlFile = AppSettingsXML.class.getClassLoader().getResourceAsStream(pstrFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();		 
            NodeList nList = doc.getElementsByTagName(AppSettingsConfig.SETTINGS_ROOT_TAG);

            for (int temp = 0; temp < nList.getLength(); temp++) {		 
                Node nNode = nList.item(temp);		 
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {	
                    
                    /* *************************    UI TAB  ************************** */
                    NodeList ndlstUITabItems = doc.getElementsByTagName(AppSettingsConfig.UITAB_TAG);
                    for (int intCounter = 0; intCounter < ndlstUITabItems.getLength(); intCounter++) {
                        Node ndGeneral = ndlstUITabItems.item(intCounter);

                        if (ndGeneral.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eElement = (Element) ndGeneral;  
                            SceneConfig.VERTEX_COLOR_DEFAULT = eElement.getElementsByTagName(AppSettingsConfig.UITAB_VERTEXCOLORDEFAULT_TAG).item(0).getTextContent();
                            SceneConfig.VERTEX_COLOR_SELECTED = eElement.getElementsByTagName(AppSettingsConfig.UITAB_VERTEXTCOLORSELECTED_TAG).item(0).getTextContent();
                            SceneConfig.VERTEX_SHAPE_DEFAULT_INT = Integer.parseInt(eElement.getElementsByTagName(AppSettingsConfig.UITAB_VERTEXSHAPEDEFAULT_TAG).item(0).getTextContent());
                            SceneConfig.VERTEX_SIZE_DEFAULT = Double.parseDouble(eElement.getElementsByTagName(AppSettingsConfig.UITAB_VERTEXSIZEDEFAULT_TAG).item(0).getTextContent());
                            SceneConfig.VERTEX_OPACITY = Double.parseDouble(eElement.getElementsByTagName(AppSettingsConfig.UITAB_VERTEXOPACITYDEFAULT_TAG).item(0).getTextContent());
                            SceneConfig.VERTEX_LABEL_FONTSIZE_DEFAULT = Integer.parseInt(eElement.getElementsByTagName(AppSettingsConfig.UITAB_VERTEXLABLEFONTSIZE_TAG).item(0).getTextContent());
                            
                            SceneConfig.EDGE_COLOR_PRIMARYCOLOR = eElement.getElementsByTagName(AppSettingsConfig.UITAB_EDGECOLORPRIMARY_TAG).item(0).getTextContent();
                            SceneConfig.EDGE_COLOR_SECONDARYCOLOR = eElement.getElementsByTagName(AppSettingsConfig.UITAB_EDGECOLORSECONDARY_TAG).item(0).getTextContent();
                            SceneConfig.EDGE_COLOR_TERTIARYCOLOR = eElement.getElementsByTagName(AppSettingsConfig.UITAB_EDGECOLORTERTIARY_TAG).item(0).getTextContent();
                            SceneConfig.EDGE_OPACITY = Double.parseDouble(eElement.getElementsByTagName(AppSettingsConfig.UITAB_EDGEOPACITY_TAG).item(0).getTextContent());
                            SceneConfig.EDGE_STROKEWIDTH = Double.parseDouble(eElement.getElementsByTagName(AppSettingsConfig.UITAB_EDGESTROKEWIDTH_TAG).item(0).getTextContent());
                            
                            SceneConfig.CANVAS_BACKGROUND_COLOR = eElement.getElementsByTagName(AppSettingsConfig.UITAB_CANVASBGCOLLORDEFAULT_TAG).item(0).getTextContent();
                            SceneConfig.CANVAS_DRAGRECT_COLOR = eElement.getElementsByTagName(AppSettingsConfig.UITAB_CANVASDRAGRECTCOLOR_TAG).item(0).getTextContent();
                            SceneConfig.CANVAS_SCROLL_SCALEFACTOR = Double.parseDouble(eElement.getElementsByTagName(AppSettingsConfig.UITAB_CANVASDRAGRECTOPACITY_TAG).item(0).getTextContent());
                            SceneConfig.CANVAS_SCROLL_SCALEFACTOR = Double.parseDouble(eElement.getElementsByTagName(AppSettingsConfig.UITAB_CANVASZOOMFACTOR_TAG).item(0).getTextContent());
                        }
                    }
    
                    /* *************************    ALGORITHM PARAMETERS TAB  ************************** */
                    NodeList ndlstAlgoPramaTab = doc.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_TAG);
                    for (int intCounter = 0; intCounter < ndlstAlgoPramaTab.getLength(); intCounter++) {
                        Node ndGeneral = ndlstAlgoPramaTab.item(intCounter);

                        if (ndGeneral.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eElement = (Element) ndGeneral;
                            // K meanes clustering
                            CommunityMiningConfig.KMEANS_CLUSTERCOUNT_DEFAULT = Integer.parseInt(eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_KMEANSCLUSTERCOUNT_TAG).item(0).getTextContent());
                            // Same Attribute Value
                            CommunityMiningConfig.SAMEVALUEATTR_MULTIPLEVALUES = Boolean.parseBoolean(eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_SAMEATTRIBUTEVALUE_MULTIPLEVALUES_TAG).item(0).getTextContent());
                            // Fast Modularity
                            CommunityMiningConfig.FM_METRIC_DEFAULT = (eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_FASTMODULARITY_ALGORITHM_TAG).item(0).getTextContent());
                            CommunityMiningConfig.FM_WEIGHTED = Boolean.parseBoolean(eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_FASTMODULARITY_WEIGHTED_TAG).item(0).getTextContent());
                            // Local Top Leader
                            CommunityMiningConfig.LOCALTOPLEADERS_CLUSTERCOUNT_DEFAULT = Integer.parseInt(eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_LOCALTOPLEADERS_CLUSTERCOUNT_TAG).item(0).getTextContent());
                            CommunityMiningConfig.LOCALTOPLEADERS_OUTLIERPERCENTAGE_DEFAULT = Double.parseDouble(eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_LOCALTOPLEADERS_OUTLIERPERCENTAGE_TAG).item(0).getTextContent());
                            CommunityMiningConfig.LOCALTOPLEADERS_HUBPERCENTAGE_DEFAULT = Double.parseDouble(eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_LOCALTOPLEADERS_HUBPERCENTAGE_TAG).item(0).getTextContent());
                            CommunityMiningConfig.LOCALTOPLEADERS_CENTERDISTANCE_DEFAULT = Double.parseDouble(eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_LOCALTOPLEADERS_CENTERDISTANCE_TAG).item(0).getTextContent());
                            // Local Community
                            CommunityMiningConfig.LOCALCOMMUNITY_TYPE_DEFAULT = (eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_LOCALCOMMUNITY_ALGORITHM_TAG).item(0).getTextContent());
                            CommunityMiningConfig.LOCALCOMMUNITY_OVERLAP = Boolean.parseBoolean(eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_LOCALCOMMUNITY_OVERLAP_TAG).item(0).getTextContent());
                            // Rosvall InfoMap
                            CommunityMiningConfig.ROSVALLINFOMAP_ATTEMPTS_DEFAULT = Integer.parseInt(eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_ROSVALLINFOMAP_ATTEMPTS_TAG).item(0).getTextContent());
                            CommunityMiningConfig.ROSVALLINFOMAP_ISDIRECTED = Boolean.parseBoolean(eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_ROSVALLINFOMAP_ISDIRECTED_TAG).item(0).getTextContent());
                            // Rosvall InfoMod
                            CommunityMiningConfig.ROSVALLINFOMOD_ATTEMPTS_DEFAULT = Integer.parseInt(eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_ROSVALLINFOMOD_ATTEMPTS_TAG).item(0).getTextContent());
                            // Dynamic Community
                            CommunityMiningConfig.DCMINING_SIMILARITYTHRESHOLD_DEFAULT = Double.parseDouble(eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_DCMINING_SIMILARITYTHRESHOLD_TAG).item(0).getTextContent());
                            CommunityMiningConfig.DCMINING_METRIC_DEFAULT = (eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_DCMINING_METRIC_TAG).item(0).getTextContent());
                            CommunityMiningConfig.DCMINING_METHOD_DEFAULT = (eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_DCMINING_METHOD_TAG).item(0).getTextContent());
                            
                            CommunityMiningConfig.DCMINING_OVERLAP_DEFAULT = Boolean.parseBoolean(eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_DCMINING_OVERLAP_TAG).item(0).getTextContent());
                            CommunityMiningConfig.DCMINING_HUBS_DEFAULT = Boolean.parseBoolean(eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_DCMINING_HUBS_TAG).item(0).getTextContent());
                      
                            
                            CommunityMiningConfig.DCMINING_INSTABILITY_DEFAULT = Double.parseDouble(eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_DCMINING_INSTABILITY_TAG).item(0).getTextContent());
                            CommunityMiningConfig.DCMINING_HISTORY_DEFAULT = Double.parseDouble(eElement.getElementsByTagName(AppSettingsConfig.ALGOPARAMTAB_DCMINING_HISTORY_TAG).item(0).getTextContent());
                                  
}
                    }
                    
                } // End of if
            } // End of for             
            // return mnAnalysisMenuBar;// return mnAnalysisMenuBar;
        } catch (ParserConfigurationException | SAXException | IOException | DOMException ex) {
            System.out.println("AppSettingsXML.Parse(): Error in Parsing the XML Language File: "+pstrFilePath);
            ex.printStackTrace();
        }
    }
}
