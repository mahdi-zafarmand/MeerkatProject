/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.parser;

import accordiontab.AccordionTabContents;
import accordiontab.CommunitiesContent;
import accordiontab.DetailsContent;
import accordiontab.FilterContent;
import accordiontab.LayoutContent;
import accordiontab.MetricElements;
import layout.LayoutGroup;
import accordiontab.StatisticsContent;
import config.AnalysisConfig;
import config.AppConfig;
import config.LangConfig;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import layout.LayoutDS;
import layout.LayoutSet;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *  Class Name      : AccordionTabs
 *  Created Date    : 2015-10-xx
 *  Description     : The Accordion Tabs that are present
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-08-23      Abhi            Added parsing facility to get text for Remove FIlter Buttons
 *  2016-06-07      Talat           Changed the addLayoutGroup() access from LayoutContent to LayoutSet
 *  
 * 
*/
public class AccordionTabs {
    
    /**
     *  Method Name     : Parse()
     *  Created Date    : 2015-10-xx
     *  Description     : Parses the Accordion Tabs from the xml file
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrFilePath : String
     *  @return AccordionTabContents
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static AccordionTabContents Parse(String pstrFilePath) {        
        String strCallingFunction = "AccordionTabs.Parse(): ";
        AccordionTabContents accAccordionTab = AccordionTabContents.getAccordionTabInstance();
        try{
//            File fXmlFile = new File(pstrFilePath);
            InputStream fXmlFile = AccordionTabs.class.getClassLoader().getResourceAsStream(pstrFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();		 
            NodeList nList = doc.getElementsByTagName(AppConfig.LANGUAGE_ROOT_TAG);
            
            LayoutSet lytSet = LayoutSet.getInstance();

            for (int temp = 0; temp < nList.getLength(); temp++) {		 
                Node nNode = nList.item(temp);		 
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {		                             
                    Element eElement = (Element) nNode;

                    NodeList nAccordionTab = doc.getElementsByTagName(AnalysisConfig.ACOORDIANTABS_TAG);

                    for (int intAccordionCounter = 0; intAccordionCounter < nAccordionTab.getLength(); intAccordionCounter++) {
                        // Node nodeMenu = nAccordionTab.item(intAccordionCounter);                        
                        
                        /* LAYOUT - Extracting all the nodes wrt the layouts */
                        LayoutDS layoutDS = new LayoutDS() ;
                        NodeList lstLayout = doc.getElementsByTagName(AnalysisConfig.LAYOUTTAB_TAG);
                        for (int intLayoutCounter = 0; intLayoutCounter < lstLayout.getLength(); intLayoutCounter++) {                                        
                            Node nodeLayout = lstLayout.item(intLayoutCounter);
                            String strLayoutTitle = nodeLayout.getAttributes().getNamedItem(AnalysisConfig.TITLE_TAG).getNodeValue();
                            
                            LayoutContent lytLayout = new LayoutContent(strLayoutTitle);
                            
                            // Each iteration of the loop will be a LayoutGroup 
                            NodeList lstLayoutGroup = doc.getElementsByTagName(AnalysisConfig.LAYOUTGROUP_TAG);
                            for (int intLayoutGroupCounter = 0; intLayoutGroupCounter < lstLayoutGroup.getLength(); intLayoutGroupCounter++) {                                        
                                Node nodeLayoutGroup = lstLayoutGroup.item(intLayoutGroupCounter);
                                String strLayoutGroupTitle = nodeLayoutGroup.getAttributes().getNamedItem(AnalysisConfig.TITLE_TAG).getNodeValue(); 
                                // System.out.println(strLayoutGroupTitle);
                                String strLayoutGroupType = nodeLayoutGroup.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_TYPE_TAG).getNodeValue(); 
                                // System.out.println(strLayoutGroupType);

                                LayoutGroup currentGroup = new LayoutGroup(strLayoutGroupTitle, strLayoutGroupType);
                                
                                NodeList childMenuItems = nodeLayoutGroup.getChildNodes();                                    
                                for (int intLayoutItemCounter = 0; intLayoutItemCounter < childMenuItems.getLength(); intLayoutItemCounter++) {
                                    Node nodeLayoutItem = childMenuItems.item(intLayoutItemCounter);
                                    if (nodeLayoutItem instanceof Element) {
                                        String strLayoutItemText = nodeLayoutItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_TEXT_TAG).getNodeValue();
                                        String strLayoutClassID = nodeLayoutItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_ID_TAG).getNodeValue();
                                        
                                        String strClass = "" ;
                                        if (nodeLayoutItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_CLASS_TAG) != null) {
                                            strClass = nodeLayoutItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_CLASS_TAG).getNodeValue();
                                        }
                                        
                                        String strParameter = "";                                        
                                        if (nodeLayoutItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_PARAMETER_TAG) != null) {
                                            strParameter = nodeLayoutItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_PARAMETER_TAG).getNodeValue();
                                        }
                                                                                
                                        currentGroup.addLayoutElement(strLayoutClassID, strLayoutItemText, strClass, strParameter);
                                        // System.out.println("\t\t"+strLayoutItemText+"\t"+strClassName); // #Debug
                                    }
                                }                                
                                lytSet.addLayoutGroup(currentGroup);
                            }
                            accAccordionTab.setLayout(lytLayout);
                        }
                        
                        /* DETAILS - Extracting all the nodes wrt the details */
                        NodeList lstDetails = doc.getElementsByTagName(AnalysisConfig.DETAILSTAB_TAG);
                        for (int intDetailsCounter = 0; intDetailsCounter < lstDetails.getLength(); intDetailsCounter++) {                                        
                            Node nodeDetails = lstDetails.item(intDetailsCounter);
                            String strDetailsTitle = nodeDetails.getAttributes().getNamedItem(AnalysisConfig.TITLE_TAG).getNodeValue();
                                                        
                            String strVertexCount = nodeDetails.getAttributes().getNamedItem(AnalysisConfig.VERTEXTEXT_TAG).getNodeValue();
                            String strEdgeCount = nodeDetails.getAttributes().getNamedItem(AnalysisConfig.EDGETEXT_TAG).getNodeValue();
                            
                            String strDensity = nodeDetails.getAttributes().getNamedItem(AnalysisConfig.DENSITY_TAG).getNodeValue();
                            String strAvgConnections = nodeDetails.getAttributes().getNamedItem(AnalysisConfig.CONNECTIONS_TAG).getNodeValue();
                            String strAvgCoefficient = nodeDetails.getAttributes().getNamedItem(AnalysisConfig.COEFFICIENT_TAG).getNodeValue();
                            String strAvgAssortavity = nodeDetails.getAttributes().getNamedItem(AnalysisConfig.ASSORTAVITY_TAG).getNodeValue();
                            String strAvgShortestPath = nodeDetails.getAttributes().getNamedItem(AnalysisConfig.SHORTESTPATH_TAG).getNodeValue();
                            
                            accAccordionTab.setDetails(new DetailsContent(strDetailsTitle, strVertexCount, strEdgeCount, strDensity, 
                                    strAvgConnections, strAvgCoefficient, strAvgAssortavity, strAvgShortestPath));    
                            
                        /* // #Debug
                            System.out.println("Title: "+strDetailsTitle);
                            System.out.println("\tstrVertexCount\t"+strVertexCount);
                            System.out.println("\tstrEdgeCount\t"+strEdgeCount);
                            System.out.println("\tstrDensity\t"+strDensity);
                            System.out.println("\tstrAvgConnections\t"+strAvgConnections);
                            System.out.println("\tstrAvgCoefficient\t"+strAvgCoefficient);
                            System.out.println("\tstrAvgAssortavity\t"+strAvgAssortavity);
                            System.out.println("\tstrAvgShortestPath\t"+strAvgShortestPath);
                        */
                        }
                        
                        // STATISTICS
                        
                        NodeList lstStatistics = doc.getElementsByTagName(AnalysisConfig.STATISTICSTAB_TAG);
                        for (int intStatisticsCounter = 0; intStatisticsCounter < lstStatistics.getLength(); intStatisticsCounter++) {                                        
                            Node nodeStatistics = lstStatistics.item(intStatisticsCounter);
                            
                            String strStatisticsTitle = nodeStatistics.getAttributes().getNamedItem(AnalysisConfig.TITLE_TAG).getNodeValue();
                            Map<String, String> mapMetricTextID = new HashMap<>();
                            Map<String, String> mapMetricIDParameter = new HashMap<>();
                            
                            NodeList childMenuItems = nodeStatistics.getChildNodes();
                            for (int intLayoutItemCounter = 0; intLayoutItemCounter < childMenuItems.getLength(); intLayoutItemCounter++) {
                                    Node nodeLayoutItem = childMenuItems.item(intLayoutItemCounter);
                                    
                                    if (nodeLayoutItem instanceof Element) {
                                        
                                        String strMetricItemText = nodeLayoutItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_TEXT_TAG).getNodeValue();
                                        String strMetricClassID = nodeLayoutItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_ID_TAG).getNodeValue();
                                        String strMetricParameter = nodeLayoutItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_PARAMETER_TAG).getNodeValue();
                                        
                                        mapMetricTextID.put(strMetricItemText, strMetricClassID);
                                        mapMetricIDParameter.put(strMetricClassID, strMetricParameter);
                                        // System.out.println("\t\t"+strLayoutItemText+"\t"+strClassName); // #Debug
                                    }
                                }  
                            MetricElements metricStatistics = MetricElements.getInstance();
                            metricStatistics.setMetricElements(mapMetricTextID, mapMetricIDParameter);
                            accAccordionTab.setStatistics(new StatisticsContent(strStatisticsTitle));    
                            // System.out.println("Title: "+strStatisticsTitle);
                        }
                        
                        
                        
                        // FILTERS
                        NodeList lstFilters = doc.getElementsByTagName(AnalysisConfig.FILTERTAB_TAG);
                        for (int intFiltersCounter = 0; intFiltersCounter < lstFilters.getLength(); intFiltersCounter++) {                                        
                            Node nodeFilter = lstFilters.item(intFiltersCounter);
                            String strFiltersTitle = nodeFilter.getAttributes().getNamedItem(AnalysisConfig.TITLE_TAG).getNodeValue();
                                                        
                            String strAddVertexButtonText = nodeFilter.getAttributes().getNamedItem(LangConfig.BTNTXT_FILTER_VERTEXATTRIBUTE_TAG).getNodeValue();
                            String strAddEdgeButtonText = nodeFilter.getAttributes().getNamedItem(LangConfig.BTNTXT_FILTER_EDGEATTRIBUTE_TAG).getNodeValue();
                            String strRemoveVertexButtonText = nodeFilter.getAttributes().getNamedItem(LangConfig.BTNTXT_FILTER_REMOVE_VERTEXATTRIBUTE_TAG).getNodeValue();
                            String strRemoveAddEdgeButtonText = nodeFilter.getAttributes().getNamedItem(LangConfig.BTNTXT_FILTER_REMOVE_EDGEATTRIBUTE_TAG).getNodeValue();
                            String strApplyFilterButtonText = nodeFilter.getAttributes().getNamedItem(LangConfig.BTNTXT_APPLYFILTER_ATTRTAG).getNodeValue();
                            
                            /*
                            accAccordionTab.setFilters(new FilterContent_V1(
                                      strFiltersTitle
                                    , strLabelVertexAttribute
                                    , strLabelEdgeAttribute
                                    , strButtonVertexAttribute
                                    , strButtonEdgeAttribute
                            ));
                            */
                            accAccordionTab.setFilters(new FilterContent(strFiltersTitle, strAddVertexButtonText, strAddEdgeButtonText, strRemoveVertexButtonText, strRemoveAddEdgeButtonText,  strApplyFilterButtonText));
                            // System.out.println("Title: "+strFiltersTitle);
                        }
                    
                        // COMMUNITIES
                        NodeList lstCommunities = doc.getElementsByTagName(AnalysisConfig.COMMUNITYTAB_TAG);
                        for (int intCommunitiesCounter = 0; intCommunitiesCounter < lstCommunities.getLength(); intCommunitiesCounter++) {                                        
                            Node nodeCommunities = lstCommunities.item(intCommunitiesCounter);
                            String strCommunitiesTitle = nodeCommunities.getAttributes().getNamedItem(AnalysisConfig.TITLE_TAG).getNodeValue();
                            
                            accAccordionTab.setCommunities(new CommunitiesContent(strCommunitiesTitle)); 
                            // System.out.println("Title: "+strCommunitiesTitle);
                        }
                    }                    
                } // End of if
            } // End of for     
            
            return accAccordionTab;
        } catch (ParserConfigurationException | SAXException | IOException | DOMException ex) {
                System.out.println(strCallingFunction+"Error in Parsing the XML Language File: "+pstrFilePath);
        }
        return null;
    }
}
