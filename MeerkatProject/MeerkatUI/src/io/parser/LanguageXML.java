/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.parser;

import analysisscreen.AnalysisScreen;
import communitymining.parameters.DynamicCommunityMiningParam;
import communitymining.parameters.FastModularityParam;
import communitymining.parameters.KMeansClusteringParam;
import communitymining.parameters.LocalCommunityParam;
import communitymining.parameters.LocalTParam;
import communitymining.parameters.LocalTopLeadersParam;
import communitymining.parameters.LouvainMiningParam;
import communitymining.parameters.RosvallInfomapParam;
import communitymining.parameters.RosvallInfomodParam;
import communitymining.parameters.SameAttributeValueParam;
import config.AnalysisConfig;
import config.AppConfig;
import config.AttributeFilterBoxConfig;
import config.AttributeFilterBoxConfig.FilterOperatorType;
import config.CanvasContextConfig;
import config.CommunityContextConfig;
import config.CommunityMiningConfig;
import config.LangConfig;
import ui.dialogwindow.NewProjectWizard;
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
import config.EdgeContextConfig;
import config.ErrorMsgsConfig;
import config.GraphEditingToolsConfig;
import config.LinkPredictionConfig;
import config.ModeConfig;
import config.ModeInformationConfig;
import config.VertexContextConfig;
import config.SettingsWindowConfig;
import config.SnapshotToolboxConfig;
import config.StatusMsgsConfig;
import java.util.HashMap;
import java.util.Map;
import ui.dialogwindow.AboutMeerkatDialog;
import ui.dialogwindow.AddEdgeAttributeDialog;
import ui.dialogwindow.AddEdgeAttributeSimiliarityDialog;
import ui.dialogwindow.AppParameterDialog;
import ui.dialogwindow.ColorChooser;
import ui.dialogwindow.DocumentationMeerkat;
import ui.dialogwindow.ExitDialog;
//import ui.dialogwindow.FeedbackSelectionDialog;
import ui.dialogwindow.NeighborhoodDegreeDialog;
import ui.dialogwindow.ProjectCloseConfirmDialog;
import ui.toolsribbon.ToolsRibbonContextMenu;

/**
 *  Class Name      : LanguageXML
 *  Created Date    : 2015-06-xx
 *  Description     : Parser for parsing the various tags in the language specific xml file
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-08-23      Abhi            Added parsing functionality of Algoritms params
 *  2016-01-29      Talat           Removing old code, check previously dated versions for old code
*/

public class LanguageXML {
    
    /**
     *  Method Name     : Parse
     *  Created Date    : 2015-06-XX
     *  Description     : Parsing the language XML file
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrFilePath : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-08-23      Abhi            Added parsing functionality of Algorithms params
     *  2016-03-14      Talat           Removing the parsing of Welcome Screen and return type changed from MenuBar to void
     *  2016-02-01      Talat           Added the parsing functionality for Background Color Change of Canvas
     *  2016-01-29      Talat           Removing old code, check previously dated versions for old code
     *  2016-01-18      Talat           Added the parsing functionality for Tools Ribbon, Application Parameters and About
     *  2016-01-04      Talat           Added the parsing functionality for Graph Editing Tools (Tool Tip and Image URLs from the XML)
     *  2015-10-29      Talat           Added the parsing functionality for Emotion and Polarity Analysis Tag
     * 
    */
    public static void Parse(String pstrFilePath) {        
        String strCallingFunction = "meerkat.Utilities.ReadLanguageXML: ";
        // MenuBar mnAnalysisMenuBar = new MenuBar();
        
        try{
//            File fXmlFile = new File(pstrFilePath);          
            InputStream fXmlFile = LanguageXML.class.getClassLoader().getResourceAsStream(pstrFilePath);            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();		 
            NodeList nList = doc.getElementsByTagName(AppConfig.LANGUAGE_ROOT_TAG);

            for (int temp = 0; temp < nList.getLength(); temp++) {		 
                Node nNode = nList.item(temp);		 
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {		                             
                    Element eElement = (Element) nNode;
                    
                    /* Analysis Screen */
                    AnalysisScreen.setParameters(
                            eElement.getElementsByTagName(AnalysisConfig.ANALYSISSCREENTITLE_TAG).item(0).getTextContent(), 
                            eElement.getElementsByTagName(AnalysisConfig.ANALYSISSCREENICON_TAG).item(0).getTextContent()
                    );
                    // System.out.println("LanguageXML.Parse(): "+AnalysisScreen.getIconPath());
                    
                    /* Exit Screen */
                    ExitDialog.setParameters(
                            eElement.getElementsByTagName(LangConfig.EXITTITLE_TAG).item(0).getTextContent(), 
                            eElement.getElementsByTagName(LangConfig.EXITMESSAGE_TAG).item(0).getTextContent(),
                            eElement.getElementsByTagName(LangConfig.EXITICONPATH_TAG).item(0).getTextContent()
                    );
                    
                    
                    /* Exit Screen */
                    /*FeedbackSelectionDialog.setParameters(
                            eElement.getElementsByTagName(LangConfig.FEEDBACKSELECTIONTITLE_TAG).item(0).getTextContent(), 
                            eElement.getElementsByTagName(LangConfig.FEEDBACKSELECTIONMESSAGE_TAG).item(0).getTextContent(),
                            eElement.getElementsByTagName(LangConfig.FEEDBACKSELECTIONICONPATH_TAG).item(0).getTextContent()
                    );
                    */
                    NewProjectWizard.setParameters (
                            eElement.getElementsByTagName(LangConfig.NEWPROJECTWIZARD_TAG).item(0).getTextContent(), 
                            eElement.getElementsByTagName(LangConfig.CREATEPROJECTBUTTON_TAG).item(0).getTextContent(), 
                            eElement.getElementsByTagName(LangConfig.CANCEL_TAG).item(0).getTextContent(),
                            eElement.getElementsByTagName(LangConfig.NEWPROJECTLABEL_TAG).item(0).getTextContent(), 
                            eElement.getElementsByTagName(LangConfig.NEWPROJECTPROMPTTEXT_TAG).item(0).getTextContent(),
                            eElement.getElementsByTagName(LangConfig.NEWPROJECTFILEFOUNDERROR_TAG).item(0).getTextContent()
                    );
                    
                    NeighborhoodDegreeDialog.setParameters (
                            eElement.getElementsByTagName(LangConfig.NEIGHBORDEGREE_TITLE_TAG).item(0).getTextContent(), 
                            eElement.getElementsByTagName(LangConfig.NEIGHBORDEGREE_PROMPT_TAG).item(0).getTextContent(), 
                            eElement.getElementsByTagName(LangConfig.NEIGHBORDEGREE_ERROR_TAG).item(0).getTextContent()
                    );
                    
                    /* *************************    ERROR TAGS  ************************** */
                    NodeList ndlstProjectClose = doc.getElementsByTagName(LangConfig.PROJECTCLOSED_TAG);
                    for (int intProjectCloseCounter = 0; intProjectCloseCounter < ndlstProjectClose.getLength(); intProjectCloseCounter++) {
                        Node ndProjectClose = ndlstProjectClose.item(intProjectCloseCounter);
                        if (ndProjectClose.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndProjectClose;  
                            
                            ProjectCloseConfirmDialog.setParameters(
                                      eElement.getElementsByTagName(LangConfig.PROJECTCLOSEDTITLE_TAG).item(0).getTextContent()
                                    , eElement.getElementsByTagName(LangConfig.PROJECTCLOSEDMESSAGE_TAG).item(0).getTextContent()
                                    , eElement.getElementsByTagName(LangConfig.PROJECTCLOSEDNOTSAVEMESSAGE_TAG).item(0).getTextContent()
                                    , eElement.getElementsByTagName(LangConfig.PROJECTCLOSEDICONPATH_TAG).item(0).getTextContent()                            
                            );
                        }
                    }
                    
                    /* *************************    ERROR TAGS  ************************** */
                    NodeList ndlstErrorMsgs = doc.getElementsByTagName(ErrorMsgsConfig.ERRORMSG_TAG);
                    for (int intErrorMsgsCounter = 0; intErrorMsgsCounter < ndlstErrorMsgs.getLength(); intErrorMsgsCounter++) {
                        Node ndErrorMsg = ndlstErrorMsgs.item(intErrorMsgsCounter);

                        if (ndErrorMsg.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndErrorMsg;  
                            ErrorMsgsConfig.ERROR_ATLEASTONEPROJECT = eElement.getElementsByTagName(ErrorMsgsConfig.ERROR_ATLEASTONEPROJECT_TAG).item(0).getTextContent();
                            ErrorMsgsConfig.ERROR_CORRUPTEDFILE = eElement.getElementsByTagName(ErrorMsgsConfig.ERROR_CORRUPTEDFILE_TAG).item(0).getTextContent();
                            ErrorMsgsConfig.ERROR_WRITINGGRAPHFILE = eElement.getElementsByTagName(ErrorMsgsConfig.ERROR_WRITINGGRAPHFILE_TAG).item(0).getTextContent();
                            ErrorMsgsConfig.ERROR_NOPROJECTSTOSAVE = eElement.getElementsByTagName(ErrorMsgsConfig.ERROR_NOPROJECTSTOSAVE_TAG).item(0).getTextContent();
                            ErrorMsgsConfig.ERROR_PROJECTALREADYOPEN = eElement.getElementsByTagName(ErrorMsgsConfig.ERROR_PROJECTALREADYOPEN_TAG).item(0).getTextContent();
                            ErrorMsgsConfig.ERROR_GRAPHALREADYOPEN = eElement.getElementsByTagName(ErrorMsgsConfig.ERROR_GRAPHALREADYOPEN_TAG).item(0).getTextContent();
                            ErrorMsgsConfig.ERROR_GRAPHLOAD = eElement.getElementsByTagName(ErrorMsgsConfig.ERROR_GRAPHLOAD_TAG).item(0).getTextContent();
                            ErrorMsgsConfig.ERROR_SAVECOMMUNITY = eElement.getElementsByTagName(ErrorMsgsConfig.ERROR_SAVECOMMUNITY_TAG).item(0).getTextContent();
                            ErrorMsgsConfig.ERROR_NUMERICVALUEONLY = eElement.getElementsByTagName(ErrorMsgsConfig.ERROR_NUMERICVALUEONLY_TAG).item(0).getTextContent();
                            ErrorMsgsConfig.ERROR_SMALLERLEFTVALUE = eElement.getElementsByTagName(ErrorMsgsConfig.ERROR_SMALLERLEFTVALUE_TAG).item(0).getTextContent();
                            ErrorMsgsConfig.ERROR_SNAPSHOTFAILED = eElement.getElementsByTagName(ErrorMsgsConfig.ERROR_SNAPSHOTFAILED_TAG).item(0).getTextContent();
                            ErrorMsgsConfig.ERROR_SAMELATLONGATTR = eElement.getElementsByTagName(ErrorMsgsConfig.ERROR_SAMELATLONGATTR_TAG).item(0).getTextContent();
                            ErrorMsgsConfig.ERROR_BOTHINPUT = eElement.getElementsByTagName(ErrorMsgsConfig.ERROR_BOTHINPUT_TAG).item(0).getTextContent();
                        }
                    }
                    
                    /* *************************    INFO TAGS  ************************** */
                    LangConfig.INFO_PROJECTSAVED = eElement.getElementsByTagName(LangConfig.INFO_PROJECTSAVED_TAG).item(0).getTextContent();
                    LangConfig.INFO_GRAPHEXPORTED = eElement.getElementsByTagName(LangConfig.INFO_GRAPHEXPORTED_TAG).item(0).getTextContent();
                    LangConfig.INFO_SNAPSHOTSAVED = eElement.getElementsByTagName(LangConfig.INFO_SNAPSHOTSAVED_TAG).item(0).getTextContent();
                    LangConfig.INFO_MININGRESULTSCLEARED = eElement.getElementsByTagName(LangConfig.INFO_MININGRESULTSCLEARED_TAG).item(0).getTextContent();
                    LangConfig.INFO_LINKPREDICTIONRESULTSCLEARED = eElement.getElementsByTagName(LangConfig.INFO_LINKPREDICTIONRESULTSCLEARED_TAG).item(0).getTextContent();
                    LangConfig.INFO_SELECTVERTEX = eElement.getElementsByTagName(LangConfig.INFO_SELECTVERTEX_TAG).item(0).getTextContent();
                    LangConfig.INFO_SELECTEDGE = eElement.getElementsByTagName(LangConfig.INFO_SELECTEDGE_TAG).item(0).getTextContent();
                    LangConfig.INFO_SETTINGS = eElement.getElementsByTagName(LangConfig.INFO_SETTINGS_TAG).item(0).getTextContent();
                    LangConfig.INFO_MININGRESULTSCOMPUTED = eElement.getElementsByTagName(LangConfig.INFO_MININGRESULTSCOMPUTED_TAG).item(0).getTextContent();
                    LangConfig.INFO_CANVAS_VISUALALREADYENABLED = eElement.getElementsByTagName(LangConfig.INFO_CANVAS_VISUALALREADYENABLED_TAG).item(0).getTextContent();
                    LangConfig.INFO_CANVAS_VISUALALREADYDISABLED = eElement.getElementsByTagName(LangConfig.INFO_CANVAS_VISUALALREADYDISABLED_TAG).item(0).getTextContent();
                    
                    LangConfig.INFO_NODENEIGHBORHOOD_WARNING = eElement.getElementsByTagName(LangConfig.INFO_NODENEIGHBORHOOD_WARNING_TAG).item(0).getTextContent();

                    /* *************************    GENERIC TAGS  ************************** */
                    LangConfig.GENERAL_OK = eElement.getElementsByTagName(LangConfig.GENERAL_OK_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_CANCEL = eElement.getElementsByTagName(LangConfig.GENERAL_CANCEL_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_CLOSE = eElement.getElementsByTagName(LangConfig.GENERAL_CLOSE_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_ERROR = eElement.getElementsByTagName(LangConfig.GENERAL_ERROR_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_EXPORT = eElement.getElementsByTagName(LangConfig.GENERAL_EXPORT_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_SAVE = eElement.getElementsByTagName(LangConfig.GENERAL_SAVE_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_SAVEALL = eElement.getElementsByTagName(LangConfig.GENERAL_SAVEALL_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_DONTSAVE = eElement.getElementsByTagName(LangConfig.GENERAL_DONTSAVE_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_APPLY = eElement.getElementsByTagName(LangConfig.GENERAL_APPLY_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_TRUE = eElement.getElementsByTagName(LangConfig.GENERAL_TRUE_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_FALSE = eElement.getElementsByTagName(LangConfig.GENERAL_FALSE_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_START = eElement.getElementsByTagName(LangConfig.GENERAL_START_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_STOP = eElement.getElementsByTagName(LangConfig.GENERAL_STOP_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_RUN = eElement.getElementsByTagName(LangConfig.GENERAL_RUN_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_YES = eElement.getElementsByTagName(LangConfig.GENERAL_YES_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_NO = eElement.getElementsByTagName(LangConfig.GENERAL_NO_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_AND = eElement.getElementsByTagName(LangConfig.GENERAL_AND_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_COMPUTE = eElement.getElementsByTagName(LangConfig.GENERAL_COMPUTE_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_INFORMATION = eElement.getElementsByTagName(LangConfig.GENERAL_INFORMATION_TAG).item(0).getTextContent();
                    
                    
                    LangConfig.GENERAL_ORPHANSINGULAR = eElement.getElementsByTagName(LangConfig.GENERAL_ORPHANSINGULAR_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_ORPHANPLURAL = eElement.getElementsByTagName(LangConfig.GENERAL_ORPHANPLURAL_TAG).item(0).getTextContent();
                    
                    /* *************************    GENERIC GRAPH RELATED TAGS  ************************** */
                    LangConfig.GENERAL_EDGESINGULAR = eElement.getElementsByTagName(LangConfig.GENERAL_EDGESINGULAR_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_EDGEPLURAL = eElement.getElementsByTagName(LangConfig.GENERAL_EDGEPLURAL_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_EDGEID = eElement.getElementsByTagName(LangConfig.GENERAL_EDGEID_TAG).item(0).getTextContent();
                    
                    LangConfig.GENERAL_VERTEXSINGULAR = eElement.getElementsByTagName(LangConfig.GENERAL_VERTEXSINGULAR_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_VERTEXPLURAL = eElement.getElementsByTagName(LangConfig.GENERAL_VERTEXPLURAL_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_VERTEXID = eElement.getElementsByTagName(LangConfig.GENERAL_VERTEXID_TAG).item(0).getTextContent();
                    
                    LangConfig.GENERAL_COMMUNITYSINGULAR = eElement.getElementsByTagName(LangConfig.GENERAL_COMMUNITYSINGULAR_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_COMMUNITYPLURAL = eElement.getElementsByTagName(LangConfig.GENERAL_COMMUNITYPLURAL_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_COMMUNITYID = eElement.getElementsByTagName(LangConfig.GENERAL_COMMUNITYID_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_COMMUNITYSUB = eElement.getElementsByTagName(LangConfig.GENERAL_COMMUNITYSUB_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_FILTER = eElement.getElementsByTagName(LangConfig.GENERAL_FILTER_TAG).item(0).getTextContent();
                    
                    LangConfig.GENERAL_ENABLEVISUALIZATION = eElement.getElementsByTagName(LangConfig.GENERAL_ENABLEVISUALIZATION_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_DISABLEVISUALIZATION = eElement.getElementsByTagName(LangConfig.GENERAL_DISABLEVISUALIZATION_TAG).item(0).getTextContent();
                    
                    LangConfig.GENERAL_AREYOUSURE = eElement.getElementsByTagName(LangConfig.GENERAL_AREYOUSURE_TAG).item(0).getTextContent();
                    LangConfig.GENERAL_AREYOUSURE_EDGE = eElement.getElementsByTagName(LangConfig.GENERAL_AREYOUSURE_EDGE_TAG).item(0).getTextContent();
                    
                    
                    
                    /* *************************    PARSING MENU   ************************** */
                    /*
                    NodeList nMenu = doc.getElementsByTagName(AppConfig.MENU_TAG);
                    
                    for (int intMenuCounter = 0; intMenuCounter < nMenu.getLength(); intMenuCounter++) {
                        Node nodeMenu = nMenu.item(intMenuCounter);
                        NodeList lstMenuOption = doc.getElementsByTagName(AppConfig.MENUOPTION_TAG);
                        for (int intMenuOptionCounter = 0; intMenuOptionCounter < lstMenuOption.getLength(); intMenuOptionCounter++) {                                        
                            Node nodeMenuOption = lstMenuOption.item(intMenuOptionCounter);
                            String strMenuOption = nodeMenuOption.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_VALUE_TAG).getNodeValue();
                            
                            HashMap<String, MenuItemGeneric> hmapMenuItems = new HashMap<>();                            
                            
                            NodeList childMenuItems = nodeMenuOption.getChildNodes();                                    
                            for (int intMenuItemCounter = 0; intMenuItemCounter < childMenuItems.getLength(); intMenuItemCounter++) {
                                Node nodeMenuItem = childMenuItems.item(intMenuItemCounter);
                                if (nodeMenuItem instanceof Element) {
                                    String strMenuItemDisplay = nodeMenuItem.getLastChild().getTextContent().trim();
                                    String strClassName = nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_CLASS_TAG).getNodeValue();
                                    String strIconPath = nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_ICON_TAG).getNodeValue();

                                    hmapMenuItems.put(strMenuItemDisplay, new MenuItemGeneric(strMenuItemDisplay, strClassName, strIconPath));                                    
                                }
                            }
                            mnAnalysisMenuBar.addMenuOption(intMenuOptionCounter, new MenuOption(strMenuOption, hmapMenuItems));
                        }
                    }
                    */
                    
                    /* *************************    PARSING OPTIONS   ************************** */
                    NodeList nOptions = doc.getElementsByTagName(LangConfig.OPTIONS_TAG);
                    for (int intCounter = 0; intCounter < nOptions.getLength(); intCounter++) {
                        Node nodeThreadParameter = nOptions.item(intCounter);

                        if (nodeThreadParameter.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) nodeThreadParameter;                                

                            LangConfig.OPTIONS_ADDNODE = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_ADDNODE_TAG).item(0).getTextContent() ;
                            LangConfig.OPTIONS_NEWNODE = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_NEWNODE_TAG).item(0).getTextContent() ;
                            LangConfig.OPTIONS_REMOVENODE = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_REMOVENODE_TAG).item(0).getTextContent() ;
                            
                            LangConfig.OPTIONS_ADDEDGE = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_ADDEDGE_TAG).item(0).getTextContent() ;
                            LangConfig.OPTIONS_NEWEDGE = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_NEWEDGE_TAG).item(0).getTextContent() ;
                            LangConfig.OPTIONS_REMOVEEDGE = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_REMOVEEDGE_TAG).item(0).getTextContent() ;
                                                        
                            LangConfig.OPTIONS_GENERATEGRAPH = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_GENERATEGRAPH_TAG).item(0).getTextContent() ;
                            //Removed from version 1.0
                            //LangConfig.OPTIONS_POLARITY = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_POLARITY_TAG).item(0).getTextContent() ;
                            //LangConfig.OPTIONS_EMOTION = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_EMOTION_TAG).item(0).getTextContent() ;
                            //LangConfig.OPTIONS_WORDCLOUD = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_WORDCLOUD_TAG).item(0).getTextContent() ;
                            //LangConfig.OPTIONS_TOPICMODELLING = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_TOPICMODELLING_TAG).item(0).getTextContent() ;
                            
                            LangConfig.OPTIONS_LOADGRAPH = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_LOADGRAPH_TAG).item(0).getTextContent() ;
                            LangConfig.OPTIONS_NEWGRAPH = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_NEWGRAPH_TAG).item(0).getTextContent() ;
                            LangConfig.OPTIONS_REMOVEGRAPH = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_REMOVEGRAPH_TAG).item(0).getTextContent() ;
                            LangConfig.OPTIONS_RENAMEGRAPH = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_RENAMEGRAPH_TAG).item(0).getTextContent() ;
                            
                            //LangConfig.OPTIONS_LOADTEXTUALGRAPH = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_LOADTEXTUALGRAPH_TAG).item(0).getTextContent() ;
                            //LangConfig.OPTIONS_NEWTEXTUALGRAPH = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_NEWTEXTUALGRAPH_TAG).item(0).getTextContent() ;
                            //LangConfig.OPTIONS_REMOVETEXTUALGRAPH = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_REMOVETEXTUALGRAPH_TAG).item(0).getTextContent() ;
                            //LangConfig.OPTIONS_RENAMETEXTUALGRAPH = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_RENAMETEXTUALGRAPH_TAG).item(0).getTextContent() ;
                            
                            LangConfig.OPTIONS_LOADPROJECT = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_LOADPROJECT_TAG).item(0).getTextContent() ;
                            LangConfig.OPTIONS_NEWPROJECT = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_NEWPROJECT_TAG).item(0).getTextContent() ;
                            LangConfig.OPTIONS_REMOVEPROJECT = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_REMOVEPROJECT_TAG).item(0).getTextContent() ;
                            LangConfig.OPTIONS_RENAMEPROJECT = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_RENAMEPROJECT_TAG).item(0).getTextContent() ;
                            //Removed from version 1.0
                            //LangConfig.OPTIONS_ADDSUBTHREADMENU = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_ADDSUBTHREADMENU_TAG).item(0).getTextContent() ;
                            //LangConfig.OPTIONS_ADDCONTENT = eOptionsElement.getElementsByTagName(LangConfig.OPTIONS_ADDCONTENT_TAG).item(0).getTextContent() ;
                        }    
                    }
                    
                    /* *************************    PARSING EMOTION   ************************** */                    
                    /* Removed from version 1.0
                    NodeList ndlstEmotion = doc.getElementsByTagName(LangConfig.EMOTION_CONTENT_TAG);
                    for (int intEmotionCounter = 0; intEmotionCounter < ndlstEmotion.getLength(); intEmotionCounter++) {
                        Node ndEmotion = ndlstEmotion.item(intEmotionCounter);
                         
                        if (ndEmotion.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndEmotion;                                

                            EmotionConfig.Instantiate(
                                      eOptionsElement.getElementsByTagName(LangConfig.EMOTION_TITLE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.EMOTION_SURPRISE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.EMOTION_ANGER_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.EMOTION_SADNESS_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.EMOTION_THANK_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.EMOTION_FEAR_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.EMOTION_JOY_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.EMOTION_LOVE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.EMOTION_DISGUST_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.EMOTION_GUILT_TAG).item(0).getTextContent()                                    
                            );                            
                        }    
                    }
                   
                     *************************    PARSING POLARITY   ************************** 
                    NodeList ndlstPolarity = doc.getElementsByTagName(LangConfig.POLARITY_CONTENT_TAG);
                    for (int intPolarityCounter = 0; intPolarityCounter < ndlstPolarity.getLength(); intPolarityCounter++) {
                        Node ndPolarity = ndlstPolarity.item(intPolarityCounter);

                        if (ndPolarity.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndPolarity;                                

                            PolarityConfig.Instantiate(
                                      eOptionsElement.getElementsByTagName(LangConfig.POLARITY_FEEDBACK_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.POLARITY_TITLE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.POLARITY_SENTENCE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.POLARITY_WORD_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.POLARITY_STRONGPOSITIVE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.POLARITY_POSITIVE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.POLARITY_NEUTRAL_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.POLARITY_NEGATIVE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.POLARITY_STRONGNEGATIVE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.POLARITY_TOTAL_TAG).item(0).getTextContent()                                    
                            ); 
                        }    
                    }
                    
                    */
                    /* *************************    PARSING VERTEX CONTEXT OPTIONS   ************************** */                    
                    NodeList ndlstVertexContext = doc.getElementsByTagName(LangConfig.NODECTX_MENU_TAG);
                    for (int intVertexContextCounter = 0; intVertexContextCounter < ndlstVertexContext.getLength(); intVertexContextCounter++) {
                        Node ndVertexContext = ndlstVertexContext.item(intVertexContextCounter);

                        if (ndVertexContext.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndVertexContext;                                

                            VertexContextConfig.Instantiate(
                                      eOptionsElement.getElementsByTagName(LangConfig.NODECTX_INFO_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.NODECTX_NEIGHBOR_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.NODECTX_STYLE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.NODECTX_SHAPE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.NODECTX_SIZE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.NODECTX_LABELSIZE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.NODECTX_COLOR_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.NODECTX_DELETE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.NODECTX_PIN_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.NODECTX_UNPIN_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.NODECTX_EXTRACT_TAG).item(0).getTextContent()
                            ); 
                        }    
                    }
                    
                    
                    /* *************************    PARSING EDGE CONTEXT OPTIONS   ************************** */                    
                    NodeList ndlstEdgeContext = doc.getElementsByTagName(LangConfig.EDGECTX_MENU_TAG);
                    for (int intEdgeContextCounter = 0; intEdgeContextCounter < ndlstEdgeContext.getLength(); intEdgeContextCounter++) {
                        Node ndEdgeContext = ndlstEdgeContext.item(intEdgeContextCounter);

                        if (ndEdgeContext.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndEdgeContext;                                

                            EdgeContextConfig.Instantiate(
                                      eOptionsElement.getElementsByTagName(LangConfig.EDGECTX_INFO_TAG).item(0).getTextContent()                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.EDGECTX_STYLE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.EDGECTX_LINE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.EDGECTX_WIDTH_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.EDGECTX_COLOR_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.EDGECTX_DELETE_TAG).item(0).getTextContent()
                            ); 
                        }    
                    }
                    
                    /* *************************    PARSING CANVAS CONTEXT OPTIONS   ************************** */                    
                    NodeList ndlstCanvasCTX = doc.getElementsByTagName(LangConfig.CANVASCTX_MENU_TAG);
                    for (int intCanvasContextCounter = 0; intCanvasContextCounter < ndlstCanvasCTX.getLength(); intCanvasContextCounter++) {
                        Node ndCanvasContext = ndlstCanvasCTX.item(intCanvasContextCounter);

                        if (ndCanvasContext.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndCanvasContext;                                

                            CanvasContextConfig.Instantiate(
                                      eOptionsElement.getElementsByTagName(LangConfig.CANVASCTX_PINVERTICES_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CANVASCTX_UNPINVERTICES_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CANVASCTX_SHOWHIDEMINIMAP_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CANVASCTX_CHANGEBGCOLOR_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CANVASCTX_CHANGEBGIMAGE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CANVASCTX_CLEARBG_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CANVASCTX_EXTRACTSUBGRAPH_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CANVASCTX_LINKPREDICTION_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CANVASCTX_REMOVENODE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CANVASCTX_REMOVEEDGE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CANVASCTX_VERTEXINFO_TAG).item(0).getTextContent()
                            ); 
                        }    
                    }
                    
                    /* *************************    PARSING COMMUNITY CONTEXT OPTIONS   ************************** */      
                    
                    NodeList ndlstCommunityCTX = doc.getElementsByTagName(LangConfig.CMNTCTX_MENU_TAG);
                    for (int intCommunityContextCounter = 0; intCommunityContextCounter < ndlstCommunityCTX.getLength(); intCommunityContextCounter++) {
                        Node ndCommunityContext = ndlstCommunityCTX.item(intCommunityContextCounter);

                        if (ndCommunityContext.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndCommunityContext;                                

                            CommunityContextConfig.Instantiate(
                                      eOptionsElement.getElementsByTagName(LangConfig.CMNTCTX_PINVERTICES_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CMNTCTX_UNPINVERTICES_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CMNTCTX_EXTRACTGRAPH_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CMNTCTX_SAVECOMMUNITY_TAG).item(0).getTextContent()
                            ); 
                        }    
                    }
                    
                    /* *************************    PARSING COMMUNITIES CONTEXT OPTIONS   ************************** */
                    /*
                    NodeList ndlstCommunitiesCTX = doc.getElementsByTagName(LangConfig.CMNTSCTX_MENU_TAG);
                    for (int intCommunitiesContextCounter = 0; intCommunitiesContextCounter < ndlstCommunitiesCTX.getLength(); intCommunitiesContextCounter++) {
                        Node ndCommunitiesContext = ndlstCommunitiesCTX.item(intCommunitiesContextCounter);

                        if (ndCommunitiesContext.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndCommunitiesContext;                                

                            CommunitiesContextConfig.Instantiate(
                                      eOptionsElement.getElementsByTagName(LangConfig.CMNTSCTX_SAVECOMMUNITIES_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CMNTSCTX_EXTRACTGRAPH_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CMNTSCTX_MININGALGORITHM_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CMNTSCTX_CLEARMININGRESULTS_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CMNTSCTX_KMEANS_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CMNTSCTX_FASTMODULARITY_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CMNTSCTX_SAMEATTRIBUTEVALUE_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CMNTSCTX_LOCALT_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CMNTSCTX_LOCALTOP_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CMNTSCTX_LOCALCOMMUNITY_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CMNTSCTX_ROSVALLINFOMAP_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CMNTSCTX_ROSVALLINFOMOD_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CMNTSCTX_DYNAMICCOMMUNITY_TAG).item(0).getTextContent()
                            ); 
                        }    
                    }
                    */
                    
                    /* *************************    PARSING EDITING TOOL TIPS   ************************** */
                    NodeList ndlstGraphEditingTools = doc.getElementsByTagName(LangConfig.GRAPHEDITINGTOOLS_TAG);
                    for (int intEditingCounter = 0; intEditingCounter < ndlstGraphEditingTools.getLength(); intEditingCounter++) {
                        Node ndEditingTool = ndlstGraphEditingTools.item(intEditingCounter);

                        if (ndEditingTool.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndEditingTool;                                

                            GraphEditingToolsConfig.Instantiate(
                                      eOptionsElement.getElementsByTagName(LangConfig.GRAPHEDITINGTOOLS_HEADER).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDEDGE_TOOLTIP_TAG).item(0).getTextContent()                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDEDGE_IMAGEURL_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDVERTEX_TOOLTIP_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDVERTEX_IMAGEURL_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDVERTEXMULTIPLETIMEFRAMES_TOOLTIP_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDVERTEXMULTIPLETIMEFRAMES_IMAGEURL_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.SHORTESTPATH_TOOLTIP_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.SHORTESTPATH_IMAGEURL_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.DELETEVERTEX_TOOLTIP_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.DELETEVERTEX_IMAGEURL_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.DELETEEDGE_TOOLTIP_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.DELETEEDGE_IMAGEURL_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.SELECT_TOOLTIP_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.SELECT_IMAGEURL_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.SELECTMULTI_TOOLTIP_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.SELECTMULTI_IMAGEURL_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.EDGESIZE_IMAGEURL_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.EDGESIZE_TOOLTIP_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.VERTEXSIZE_IMAGEURL_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.VERTEXSIZE_TOOLTIP_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.VERTEXCOLOR_IMAGEURL_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.VERTEXCOLOR_TOOLTIP_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.EDGECOLOR_IMAGEURL_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.EDGECOLOR_TOOLTIP_TAG).item(0).getTextContent()
                            );                             
                        }    
                    }
                    
                    
                    /* ****************************    SNAPSHOT TOOL TIPS   ****************************** */
                    NodeList ndlstSnapshotTool = doc.getElementsByTagName(LangConfig.SNAPSHOTTOOLS_TAG);
                    for (int intSnapshotToolCounter = 0; intSnapshotToolCounter < ndlstSnapshotTool.getLength(); intSnapshotToolCounter++) {
                        Node ndSnapshotTool = ndlstSnapshotTool.item(intSnapshotToolCounter);

                        if (ndSnapshotTool.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndSnapshotTool;                                

                            SnapshotToolboxConfig.Instantiate(
                                      eOptionsElement.getElementsByTagName(LangConfig.SNAPSHOTTOOLS_HEADER).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.ALLPROJECTS_LABEL_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.APPSNAPSHOT_TOOLTIP_TAG).item(0).getTextContent()                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.APPSNAPSHOT_IMAGEURL_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.ALLGRAPHONEPROJECT_TOOLTIP_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.ALLGRAPHONEPROJECT_IMAGEURL_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.ALLGRAPHALLPROJECTS_TOOLTIP_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.ALLGRAPHALLPROJECTS_IMAGEURL_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.CURRENTGRAPHCOMPLETE_TOOLTIP_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CURRENTGRAPHCOMPLETE_IMAGEURL_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.CURRENTGRAPHVISIBLE_TOOLTIP_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.CURRENTGRAPHVISIBLE_IMAGEURL_TAG).item(0).getTextContent()
                            ); 
                        }    
                    } // End of snapshot tool
                    
                    
                    /* ****************************    ADD EDGE ATTRIBUTE DIALOG BOX   ****************************** */
                    NodeList ndlstAddEdgeAttr = doc.getElementsByTagName(LangConfig.ADDEDGEATTRIBUTE_TAG);
                    for (int intAddEdgeAttrCounter = 0; intAddEdgeAttrCounter < ndlstAddEdgeAttr.getLength(); intAddEdgeAttrCounter++) {
                        Node ndAddEdgeAttr = ndlstAddEdgeAttr.item(intAddEdgeAttrCounter);

                        if (ndAddEdgeAttr.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndAddEdgeAttr;                                

                            AddEdgeAttributeDialog.setParameters(
                                      eOptionsElement.getElementsByTagName(LangConfig.ADDEDGEATTRIBUTE_HEADER_TAG).item(0).getTextContent() 
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDEDGEATTRIBUTE_INFO_TAG).item(0).getTextContent() 
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDEDGEATTRIBUTE_IMAGEURL_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDEDGEATTRIBUTE_ATTRIBUTE_TAG).item(0).getTextContent()                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDEDGEATTRIBUTE_EDGETYPE_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDEDGEATTRIBUTE_ATTRIBUTENAME_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDEDGEATTRIBUTE_ATTRIBUTEVALUE_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDEDGEATTRIBUTE_MULTIPLEVALUES_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDEDGEATTRIBUTE_SEPARATOR_TAG).item(0).getTextContent()
                            ); 
                        } 
                    }
                    
                    /* ****************************    BACKGROUND COLOR BOX   ****************************** */
                    NodeList ndlstBackgroundColorChange = doc.getElementsByTagName(LangConfig.BGCOLORCHANGE_TAG);
                    for (int intBGCounter = 0; intBGCounter < ndlstBackgroundColorChange.getLength(); intBGCounter++) {
                        Node ndBackgroundColorChange = ndlstBackgroundColorChange.item(intBGCounter);

                        if (ndBackgroundColorChange.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndBackgroundColorChange;                                

                            ColorChooser.setParameters(
                                      eOptionsElement.getElementsByTagName(LangConfig.BGCOLORCHANGE_TITLE_TAG).item(0).getTextContent() 
                                    , eOptionsElement.getElementsByTagName(LangConfig.BGCOLORCHANGE_INFO_TAG).item(0).getTextContent() 
                                    , eOptionsElement.getElementsByTagName(LangConfig.BGCOLORCHANGE_ERROR_TAG).item(0).getTextContent()
                            ); 
                        } 
                    }                   
                    
                    
                    /* ****************************    ADD EDGE ATTRIBUTE SIMILARITY DIALOG BOX   ****************************** */
                    NodeList ndlstAddEdgeAttrSim = doc.getElementsByTagName(LangConfig.ADDEDGEATTRSIMILAIRTY_TAG);
                    for (int intAddEdgeAttrSimCounter = 0; intAddEdgeAttrSimCounter < ndlstAddEdgeAttrSim.getLength(); intAddEdgeAttrSimCounter++) {
                        Node ndAddEdgeAttrSim = ndlstAddEdgeAttrSim.item(intAddEdgeAttrSimCounter);

                        if (ndAddEdgeAttrSim.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndAddEdgeAttrSim;                                

                            AddEdgeAttributeSimiliarityDialog.setParameters(
                                      eOptionsElement.getElementsByTagName(LangConfig.ADDEDGEATTRSIMILAIRTY_HEADER_TAG).item(0).getTextContent() 
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDEDGEATTRSIMILAIRTY_INFO_TAG).item(0).getTextContent() 
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDEDGEATTRSIMILAIRTY_IMAGEURL_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDEDGEATTRSIMILAIRTY_ATTRIBUTE_TAG).item(0).getTextContent()                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDEDGEATTRSIMILAIRTY_SIMILARITY_TAG).item(0).getTextContent()                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDEDGEATTRSIMILAIRTY_THRESHOLD_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.ADDEDGEATTRSIMILARITY_THRESHOLDERROR_TAG).item(0).getTextContent()
                            ); 
                        } 
                    }
                    
                    
                    /* ****************************    TOOLS RIBBON   ****************************** */
                    NodeList ndlstToolsRibbon = doc.getElementsByTagName(LangConfig.TOOLSRIBBONMENU_TAG);
                    for (int intToolsRibbonCounter = 0; intToolsRibbonCounter < ndlstToolsRibbon.getLength(); intToolsRibbonCounter++) {
                        Node ndToolsRibbon = ndlstToolsRibbon.item(intToolsRibbonCounter);

                        if (ndToolsRibbon.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndToolsRibbon;                                
                            ToolsRibbonContextMenu.setHideMenu(eOptionsElement.getElementsByTagName(LangConfig.TOOLSRIBBONMENU_HIDE_TAG).item(0).getTextContent());
                        } 
                    }
                    
                    
                    /* ****************************    VERTEX LABEL SELECT ATTRIBUTE DIALOG    ****************************** */
                    NodeList ndlstVertexLabelSelectAttr = doc.getElementsByTagName(LangConfig.VERTEXLABEL_TAG);
                    for (int intVertexLabelSelectAttrCounter = 0; intVertexLabelSelectAttrCounter < ndlstVertexLabelSelectAttr.getLength(); intVertexLabelSelectAttrCounter++) {
                        Node ndVertexLabelSelectAttr = ndlstVertexLabelSelectAttr.item(intVertexLabelSelectAttrCounter);

                        if (ndVertexLabelSelectAttr.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndVertexLabelSelectAttr;                                
                            LangConfig.VERTEXLABEL_TITLE = eOptionsElement.getElementsByTagName(LangConfig.VERTEXLABEL_TITLE_TAG).item(0).getTextContent() ;
                            LangConfig.VERTEXLABEL_MSG = eOptionsElement.getElementsByTagName(LangConfig.VERTEXLABEL_MSG_TAG).item(0).getTextContent() ;
                        } 
                    }
                    
                    
                    /* ****************************    VERTEX TOOLTIP SELECT ATTRIBUTE DIALOG    ****************************** */
                    NodeList ndlstVertexTooltipSelectAttr = doc.getElementsByTagName(LangConfig.VERTEXTOOLTIP_TAG);
                    for (int intVertexTooltipSelectAttrCounter = 0; intVertexTooltipSelectAttrCounter < ndlstVertexTooltipSelectAttr.getLength(); intVertexTooltipSelectAttrCounter++) {
                        Node ndVertexTooltipSelectAttr = ndlstVertexTooltipSelectAttr.item(intVertexTooltipSelectAttrCounter);

                        if (ndVertexTooltipSelectAttr.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndVertexTooltipSelectAttr;                                
                            LangConfig.VERTEXTOOLTIP_TITLE = eOptionsElement.getElementsByTagName(LangConfig.VERTEXTOOLTIP_TITLE_TAG).item(0).getTextContent() ;
                            LangConfig.VERTEXTOOLTIP_MSG = eOptionsElement.getElementsByTagName(LangConfig.VERTEXTOOLTIP_MSG_TAG).item(0).getTextContent() ;
                        } 
                    }
                    
                    /* ****************************    FILTER OPERATORS ****************************** */
                    NodeList ndlstFilterOperators = doc.getElementsByTagName(LangConfig.FILTEROPERATORS_TAG);
                    
                    for (int intFilterOperatorCounter = 0; intFilterOperatorCounter < ndlstFilterOperators.getLength(); intFilterOperatorCounter++) {                                        
                        Node ndFilterOperator = ndlstFilterOperators.item(intFilterOperatorCounter);

                        Map<String, String> mapFilterOperator = new HashMap<>();
                        Map<String, FilterOperatorType> mapFilterOperatorType = new HashMap<>();

                        NodeList childMenuItems = ndFilterOperator.getChildNodes();
                        for (int intOperatorCounter = 0; intOperatorCounter < childMenuItems.getLength(); intOperatorCounter++) {
                            Node ndOperator = childMenuItems.item(intOperatorCounter);

                            if (ndOperator instanceof Element) {

                                String strOperator = ndOperator.getAttributes().getNamedItem(LangConfig.FILTEROPERATORS_ATTR_VALUE).getNodeValue();
                                String strOperatorText = ndOperator.getAttributes().getNamedItem(LangConfig.FILTEROPERATORS_ATTR_TEXT).getNodeValue();
                                
                                String strOperatorType = ndOperator.getAttributes().getNamedItem(LangConfig.FILTEROPERATORS_ATTR_TYPE).getNodeValue();
                                FilterOperatorType curFilterType = null ;
                                if (strOperatorType.equalsIgnoreCase(LangConfig.FILTEROPERATORS_VALUE_NUMERIC)) {
                                    curFilterType = FilterOperatorType.NUMERIC ;
                                } else if (strOperatorType.equalsIgnoreCase(LangConfig.FILTEROPERATORS_VALUE_NONNUMERIC)) {
                                    curFilterType = FilterOperatorType.NONNUMERIC ;
                                }

                                mapFilterOperator.put(strOperatorText, strOperator);
                                mapFilterOperatorType.put(strOperatorText, curFilterType);
                            }
                        }
                        
                        AttributeFilterBoxConfig.setParameters(mapFilterOperator, mapFilterOperatorType);
                    }
                    
                    /* ****************************    APPLICATION PARAMETERS   ****************************** */
                    NodeList ndlstAppParameters = doc.getElementsByTagName(LangConfig.APPPARAMETERS_TAG);
                    for (int intAppParametersCounter = 0; intAppParametersCounter < ndlstAppParameters.getLength(); intAppParametersCounter++) {
                        Node ndAppParameters = ndlstAppParameters.item(intAppParametersCounter);

                        if (ndAppParameters.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndAppParameters;                                

                            AppParameterDialog.setParameters(
                                      eOptionsElement.getElementsByTagName(LangConfig.APPPARAMETERS_OS_TAG).item(0).getTextContent() 
                                    , eOptionsElement.getElementsByTagName(LangConfig.APPPARAMETERS_PROCESSORS_TAG).item(0).getTextContent() 
                                    , eOptionsElement.getElementsByTagName(LangConfig.APPPARAMETERS_TOTALMEMORY_TAG).item(0).getTextContent()
                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.APPPARAMETERS_JVMVERSION_TAG).item(0).getTextContent()                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.APPPARAMETERS_TOTALJVMMEMORY_TAG).item(0).getTextContent()                                    
                                    , eOptionsElement.getElementsByTagName(LangConfig.APPPARAMETERS_USEDJVMMEMORY_TAG).item(0).getTextContent()
                                    , eOptionsElement.getElementsByTagName(LangConfig.APPPARAMETERS_FREEJVMMEMORY_TAG).item(0).getTextContent()
                            ); 
                        } 
                    }
                    
                    /* ****************************    APPLICATION PARAMETERS   ****************************** */
                    NodeList ndlstSettingsWindow = doc.getElementsByTagName(LangConfig.SETTINGSWINDOW_TAG);
                    for (int intSettingsWindowCounter = 0; intSettingsWindowCounter < ndlstSettingsWindow.getLength(); intSettingsWindowCounter++) {
                        Node ndSettingsWindow = ndlstSettingsWindow.item(intSettingsWindowCounter);

                        if (ndSettingsWindow.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndSettingsWindow;      
                            
                            SettingsWindowConfig.SETTINGSWINDOW_TITLE = eOptionsElement.getElementsByTagName(LangConfig.SETTINGSWINDOW_TITLE_TAG).item(0).getTextContent();                            
                            SettingsWindowConfig.SETTINGSWINDOW_UITABTITLE = eOptionsElement.getElementsByTagName(LangConfig.SETTINGSWINDOW_UITABTITLE_TAG).item(0).getTextContent();
                            SettingsWindowConfig.SETTINGSWINDOW_ALGORITHMTABTITLE = eOptionsElement.getElementsByTagName(LangConfig.SETTINGSWINDOW_ALGORITHMTABTITLE_TAG).item(0).getTextContent();
                            
                            // Vertex Parameters
                            SettingsWindowConfig.UITAB_VERTEXCOLORDEFAULT = eOptionsElement.getElementsByTagName(LangConfig.UITAB_VERTEXCOLORDEFAULT_TAG).item(0).getTextContent();
                            SettingsWindowConfig.UITAB_VERTEXTCOLORSELECTED = eOptionsElement.getElementsByTagName(LangConfig.UITAB_VERTEXTCOLORSELECTED_TAG).item(0).getTextContent();
                            SettingsWindowConfig.UITAB_VERTEXSHAPEDEFAULT = eOptionsElement.getElementsByTagName(LangConfig.UITAB_VERTEXSHAPEDEFAULT_TAG).item(0).getTextContent();
                            SettingsWindowConfig.UITAB_VERTEXSIZEDEFAULT = eOptionsElement.getElementsByTagName(LangConfig.UITAB_VERTEXSIZEDEFAULT_TAG).item(0).getTextContent();
                            SettingsWindowConfig.UITAB_VERTEXOPACITYDEFAULT = eOptionsElement.getElementsByTagName(LangConfig.UITAB_VERTEXOPACITYDEFAULT_TAG).item(0).getTextContent();
                            SettingsWindowConfig.UITAB_VERTEXLABELFONTSIZE = eOptionsElement.getElementsByTagName(LangConfig.UITAB_VERTEXLABELFONTSIZE_TAG).item(0).getTextContent();

                            // Edge Parameters
                            SettingsWindowConfig.UITAB_EDGECOLORPRIMARY = eOptionsElement.getElementsByTagName(LangConfig.UITAB_EDGECOLORPRIMARY_TAG).item(0).getTextContent();
                            SettingsWindowConfig.UITAB_EDGECOLORSECONDARY = eOptionsElement.getElementsByTagName(LangConfig.UITAB_EDGECOLORSECONDARY_TAG).item(0).getTextContent();
                            SettingsWindowConfig.UITAB_EDGECOLORTERTIARY = eOptionsElement.getElementsByTagName(LangConfig.UITAB_EDGECOLORTERTIARY_TAG).item(0).getTextContent();
                            SettingsWindowConfig.UITAB_EDGEOPACITY = eOptionsElement.getElementsByTagName(LangConfig.UITAB_EDGEOPACITY_TAG).item(0).getTextContent();
                            SettingsWindowConfig.UITAB_EDGESTROKEWIDTH = eOptionsElement.getElementsByTagName(LangConfig.UITAB_EDGESTROKEWIDTH_TAG).item(0).getTextContent();

                            // Canvas Parameters
                            SettingsWindowConfig.UITAB_CANVASBGCOLORDEFAULT = eOptionsElement.getElementsByTagName(LangConfig.UITAB_CANVASBGCOLORDEFAULT_TAG).item(0).getTextContent();
                            SettingsWindowConfig.UITAB_CANVASDRAGRECTCOLOR = eOptionsElement.getElementsByTagName(LangConfig.UITAB_CANVASDRAGRECTCOLOR_TAG).item(0).getTextContent();
                            SettingsWindowConfig.UITAB_CANVASDRAGRECTOPACITY = eOptionsElement.getElementsByTagName(LangConfig.UITAB_CANVASDRAGRECTOPACITY_TAG).item(0).getTextContent();
                            SettingsWindowConfig.UITAB_CANVASZOOMFACTOR = eOptionsElement.getElementsByTagName(LangConfig.UITAB_CANVASZOOMFACTOR_TAG).item(0).getTextContent();


                            // Algorithm Tab
                            // k-means clustering params
                            SettingsWindowConfig.ALGOPARAMTAB_KMEANSCLUSTERCOUNT = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_KMEANSCLUSTERCOUNT_TAG).item(0).getTextContent();
                            // Same Attribute value
                            SettingsWindowConfig.ALGOPARAMTAB_SAMEATTRIBUTEVALUE_MULTIPLEVALUES = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_SAMEATTRIBUTEVALUE_MULTIPLEVALUES_TAG).item(0).getTextContent();
                            // Fast Modularity
                            SettingsWindowConfig.ALGOPARAMTAB_FASTMODULARITY_ALGORITHM = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_FASTMODULARITY_ALGORITHM_TAG).item(0).getTextContent();
                            SettingsWindowConfig.ALGOPARAMTAB_FASTMODULARITY_WEIGHTED = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_FASTMODULARITY_WEIGHTED_TAG).item(0).getTextContent();
                            // Local Top Leaders Params
                            SettingsWindowConfig.ALGOPARAMTAB_LOCALTOPLEADERS_CLUSTERCOUNT = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_LOCALTOPLEADERS_CLUSTERCOUNT_TAG).item(0).getTextContent();
                            SettingsWindowConfig.ALGOPARAMTAB_LOCALTOPLEADERS_OUTLIERPERCENTAGE = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_LOCALTOPLEADERS_OUTLIERPERCENTAGE_TAG).item(0).getTextContent();
                            SettingsWindowConfig.ALGOPARAMTAB_LOCALTOPLEADERS_HUBPERCENTAGE = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_LOCALTOPLEADERS_HUBPERCENTAGE_TAG).item(0).getTextContent();
                            SettingsWindowConfig.ALGOPARAMTAB_LOCALTOPLEADERS_CENTERDISTANCE = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_LOCALTOPLEADERS_CENTERDISTANCE_TAG).item(0).getTextContent();
                            // Local Commmunity
                            SettingsWindowConfig.ALGOPARAMTAB_LOCALCOMMUNITY_ALGORITHM = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_LOCALCOMMUNITY_ALGORITHM_TAG).item(0).getTextContent();
                            SettingsWindowConfig.ALGOPARAMTAB_LOCALCOMMUNITY_OVERLAP = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_LOCALCOMMUNITY_OVERLAP_TAG).item(0).getTextContent();
                            // RosvallInfoMap
                            SettingsWindowConfig.ALGOPARAMTAB_ROSVALLINFOMAP_ATTEMPTS = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_ROSVALLINFOMAP_ATTEMPTS_TAG).item(0).getTextContent();
                            SettingsWindowConfig.ALGOPARAMTAB_ROSVALLINFOMAP_ISDIRECTED = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_ROSVALLINFOMAP_ISDIRECTED_TAG).item(0).getTextContent();
                            // RosvallInfoMod
                            SettingsWindowConfig.ALGOPARAMTAB_ROSVALLINFOMOD_ATTEMPTS = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_ROSVALLINFOMOD_ATTEMPTS_TAG).item(0).getTextContent();
                            // Dynamic Community Mining
                            SettingsWindowConfig.ALGOPARAMTAB_DCMINING_SIMILARITYTHRESHOLD = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_DCMINING_SIMILARITYTHRESHOLD_TAG).item(0).getTextContent();
                            SettingsWindowConfig.ALGOPARAMTAB_DCMINING_METRIC = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_DCMINING_METRIC_TAG).item(0).getTextContent();
                            SettingsWindowConfig.ALGOPARAMTAB_DCMINING_METHOD = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_DCMINING_METHOD_TAG).item(0).getTextContent();
                            SettingsWindowConfig.ALGOPARAMTAB_DCMINING_INSTABILITY = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_DCMINING_INSTABILITY_TAG).item(0).getTextContent();
                            SettingsWindowConfig.ALGOPARAMTAB_DCMINING_HISTORY = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_DCMINING_HISTORY_TAG).item(0).getTextContent();
                            SettingsWindowConfig.ALGOPARAMTAB_DCMINING_OVERLAP = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_DCMINING_OVERLAP_TAG).item(0).getTextContent();
                            SettingsWindowConfig.ALGOPARAMTAB_DCMINING_HUBS = eOptionsElement.getElementsByTagName(LangConfig.ALGOPARAMTAB_DCMINING_HUBS_TAG).item(0).getTextContent();
                        } 
                    }
                    
                    AboutMeerkatDialog.setParameters(eElement.getElementsByTagName(LangConfig.ABOUTMEERKAT_TAG).item(0).getTextContent());
                    
                    DocumentationMeerkat.setParameters(eElement.getElementsByTagName(LangConfig.DOCUMENTATIONMEERKAT_TAG).item(0).getTextContent());
                    
                    
                    /* ****************************    PARSING OF THE STATUS MESSAGES   ****************************** */
                    NodeList ndlstStatusMsgs = doc.getElementsByTagName(StatusMsgsConfig.STATUSMSGS_TAG);
                    for (int intStatusMsgsCounter = 0; intStatusMsgsCounter < ndlstStatusMsgs.getLength(); intStatusMsgsCounter++) {
                        Node ndStatusMsg = ndlstStatusMsgs.item(intStatusMsgsCounter);

                        if (ndStatusMsg.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndStatusMsg;                                

                            // CANCEL OPERATIONS
                            StatusMsgsConfig.OPERATION_CANCEL = eOptionsElement.getElementsByTagName(StatusMsgsConfig.OPERATION_CANCEL_TAG).item(0).getTextContent();
                            StatusMsgsConfig.OPERATION_FAILED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.OPERATION_FAILED_TAG).item(0).getTextContent();
                            
                            // WINDOW OPERATIONS
                            StatusMsgsConfig.WINDOW_OPENED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.WINDOW_OPENED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.WINDOW_OPENING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.WINDOW_OPENING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.WINDOW_CLOSED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.WINDOW_CLOSED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.WINDOW_CLOSING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.WINDOW_CLOSING_TAG).item(0).getTextContent();
                            
                            // GENERAL - CHANGES OPERATION
                            StatusMsgsConfig.CHANGES_SAVING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.CHANGES_SAVED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.CHANGES_SAVED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.CHANGES_SAVING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.CHANGES_DISCARDING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.CHANGES_DISCARDED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.CHANGES_DISCARDED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.CHANGES_DISCARDING_TAG).item(0).getTextContent();
                            
                            // APPLICATION OPERATIONS
                            StatusMsgsConfig.APPLICATION_OPENING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.APPLICATION_OPENING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.APPLICATION_OPENED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.APPLICATION_OPENED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.APPLICATION_CLOSING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.APPLICATION_CLOSING_TAG).item(0).getTextContent();
                            
                            // WAITING OPERATIONS
                            StatusMsgsConfig.WAITING_USERINPUT = eOptionsElement.getElementsByTagName(StatusMsgsConfig.WAITING_USERINPUT_TAG).item(0).getTextContent();
                            
                            // PROJECTS
                            StatusMsgsConfig.PROJECT_OPENED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.PROJECT_OPENED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.PROJECT_OPENING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.PROJECT_OPENING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.PROJECT_CLOSED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.PROJECT_CLOSED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.PROJECT_CLOSING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.PROJECT_CLOSING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.PROJECT_ADDED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.PROJECT_ADDED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.PROJECT_ADDING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.PROJECT_ADDING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.PROJECT_SAVED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.PROJECT_SAVED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.PROJECT_SAVING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.PROJECT_SAVING_TAG).item(0).getTextContent();
                            
                            // GRAPHS
                            StatusMsgsConfig.GRAPH_OPENED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.GRAPH_OPENED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.GRAPH_OPENING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.GRAPH_OPENING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.GRAPH_CLOSED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.GRAPH_CLOSED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.GRAPH_CLOSING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.GRAPH_CLOSING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.GRAPH_ADDED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.GRAPH_ADDED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.GRAPH_ADDING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.GRAPH_ADDING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.GRAPH_SAVED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.GRAPH_SAVED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.GRAPH_SAVING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.GRAPH_SAVING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.GRAPH_EXPORTED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.GRAPH_EXPORTED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.GRAPH_EXPORTING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.GRAPH_EXPORTING_TAG).item(0).getTextContent();
                            
                            StatusMsgsConfig.LAYOUT_RUNNING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.LAYOUT_RUNNING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.LAYOUT_COMPLETED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.LAYOUT_COMPLETED_TAG).item(0).getTextContent();
                            
                            // EDITING TOOLS
                            StatusMsgsConfig.EDITING_VERTEXADDED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.EDITING_VERTEXADDED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.EDITING_VERTICESADDED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.EDITING_VERTICESADDED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.EDITING_VERTEXDELETED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.EDITING_VERTEXDELETED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.EDITING_VERTICESDELETED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.EDITING_VERTICESDELETED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.EDITING_VERTEXSELECTED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.EDITING_VERTEXSELECTED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.EDITING_VERTICESSELECTED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.EDITING_VERTICESSELECTED_TAG).item(0).getTextContent();
                            
                            StatusMsgsConfig.EDITING_EDGEADDED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.EDITING_EDGEADDED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.EDITING_EDGESADDED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.EDITING_EDGESADDED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.EDITING_EDGEDELETED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.EDITING_EDGEDELETED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.EDITING_EDGESDELETED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.EDITING_EDGESDELETED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.EDITING_EDGESELECTED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.EDITING_EDGESELECTED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.EDITING_EDGESSELECTED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.EDITING_EDGESSELECTED_TAG).item(0).getTextContent();
                            
                            StatusMsgsConfig.EDITING_SELECTIONTOOLSELECTED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.EDITING_SELECTIONTOOLSELECTED_TAG).item(0).getTextContent();
                            
                            // SNAPSHOT
                            StatusMsgsConfig.SNAPSHOT_APPLICATION = eOptionsElement.getElementsByTagName(StatusMsgsConfig.SNAPSHOT_APPLICATION_TAG).item(0).getTextContent();
                            StatusMsgsConfig.SNAPSHOT_ALLPROJECTSALLGRAPHS = eOptionsElement.getElementsByTagName(StatusMsgsConfig.SNAPSHOT_ALLPROJECTSALLGRAPHS_TAG).item(0).getTextContent();
                            StatusMsgsConfig.SNAPSHOT_CURRENTPROJECTALLGRAPH = eOptionsElement.getElementsByTagName(StatusMsgsConfig.SNAPSHOT_CURRENTPROJECTALLGRAPH_TAG).item(0).getTextContent();
                            StatusMsgsConfig.SNAPSHOT_CURRENTGRAPH = eOptionsElement.getElementsByTagName(StatusMsgsConfig.SNAPSHOT_CURRENTGRAPH_TAG).item(0).getTextContent();
                            StatusMsgsConfig.SNAPSHOT_CURRENTVIEW = eOptionsElement.getElementsByTagName(StatusMsgsConfig.SNAPSHOT_CURRENTVIEW_TAG).item(0).getTextContent();
                            
                            // TOOLS RIBBON                            
                            StatusMsgsConfig.TOOLSRIBBON_SHOW = eOptionsElement.getElementsByTagName(StatusMsgsConfig.TOOLSRIBBON_SHOW_TAG).item(0).getTextContent();
                            StatusMsgsConfig.TOOLSRIBBON_HIDE = eOptionsElement.getElementsByTagName(StatusMsgsConfig.TOOLSRIBBON_HIDE_TAG).item(0).getTextContent();                            
                            StatusMsgsConfig.TOOLSRIBBON_WANTTOHIDE = eOptionsElement.getElementsByTagName(StatusMsgsConfig.TOOLSRIBBON_WANTTOHIDE_TAG).item(0).getTextContent();
                            
                            //APPLICATION PARAMETERS
                            StatusMsgsConfig.APPPARAMETERS_SHOW = eOptionsElement.getElementsByTagName(StatusMsgsConfig.APPPARAMETERS_SHOW_TAG).item(0).getTextContent();
                            StatusMsgsConfig.APPPARAMETERS_CLOSED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.APPPARAMETERS_CLOSED_TAG).item(0).getTextContent();                            
                            
                            // ABOUT MEERKAT
                            StatusMsgsConfig.ABOUT_SHOW = eOptionsElement.getElementsByTagName(StatusMsgsConfig.ABOUT_SHOW_TAG).item(0).getTextContent();
                            StatusMsgsConfig.ABOUT_CLOSED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.ABOUT_CLOSED_TAG).item(0).getTextContent(); 
                            
                            // APPLICATION SETTINGS WINDOW
                            StatusMsgsConfig.SETTINGS_SHOW = eOptionsElement.getElementsByTagName(StatusMsgsConfig.SETTINGS_SHOW_TAG).item(0).getTextContent();
                            StatusMsgsConfig.SETTINGS_CLOSED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.SETTINGS_CLOSED_TAG).item(0).getTextContent(); 
                            StatusMsgsConfig.SETTINGS_APPLIED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.SETTINGS_APPLIED_TAG).item(0).getTextContent(); 
                            StatusMsgsConfig.SETTINGS_APPLIEDCLOSED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.SETTINGS_APPLIEDCLOSED_TAG).item(0).getTextContent(); 
                            StatusMsgsConfig.SETTINGS_CANCELLED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.SETTINGS_CANCELLED_TAG).item(0).getTextContent(); 
                            
                            // DOCUMENTATION
                            StatusMsgsConfig.DOCUMENTATION_SHOW = eOptionsElement.getElementsByTagName(StatusMsgsConfig.DOCUMENTATION_SHOW_TAG).item(0).getTextContent();
                            StatusMsgsConfig.DOCUMENTATION_CLOSED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.DOCUMENTATION_CLOSED_TAG).item(0).getTextContent(); 
                            
                            // VERTEX LABEL
                            StatusMsgsConfig.VERTEXLABEL_ENABLED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.VERTEXLABEL_ENABLED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.VERTEXLABEL_ENABLING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.VERTEXLABEL_ENABLING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.VERTEXLABEL_DISABLED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.VERTEXLABEL_DISABLED_TAG).item(0).getTextContent(); 
                            StatusMsgsConfig.VERTEXLABEL_DISABLING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.VERTEXLABEL_DISABLING_TAG).item(0).getTextContent(); 
                            
                            // VERTEX TOOLTIP
                            StatusMsgsConfig.VERTEXTOOLTIP_ENABLED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.VERTEXTOOLTIP_ENABLED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.VERTEXTOOLTIP_ENABLING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.VERTEXTOOLTIP_ENABLING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.VERTEXTOOLTIP_DISABLED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.VERTEXTOOLTIP_DISABLED_TAG).item(0).getTextContent(); 
                            StatusMsgsConfig.VERTEXTOOLTIP_DISABLING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.VERTEXTOOLTIP_DISABLING_TAG).item(0).getTextContent(); 
                            
                            // VERTEX PINNING
                            StatusMsgsConfig.VERTEXPINNING_ENABLED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.VERTEXPINNING_ENABLED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.VERTEXPINNING_ENABLING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.VERTEXPINNING_ENABLING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.VERTEXPINNING_DISABLED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.VERTEXPINNING_DISABLED_TAG).item(0).getTextContent(); 
                            StatusMsgsConfig.VERTEXPINNING_DISABLING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.VERTEXPINNING_DISABLING_TAG).item(0).getTextContent(); 
                            
                            // VERTEX DELETION
                            StatusMsgsConfig.VERTEX_DELETED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.VERTEX_DELETED_TAG).item(0).getTextContent(); 
                            StatusMsgsConfig.VERTEX_DELETING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.VERTEX_DELETING_TAG).item(0).getTextContent(); 
                            
                            //EDGE INFO
                            StatusMsgsConfig.Edge_INFORMATION = eOptionsElement.getElementsByTagName(StatusMsgsConfig.Edge_INFORMATION_TAG).item(0).getTextContent();
                            StatusMsgsConfig.Edge_DELETE = eOptionsElement.getElementsByTagName(StatusMsgsConfig.Edge_DELETE_TAG).item(0).getTextContent();
                            
                            // MINING RESULTS COMPUTED
                            StatusMsgsConfig.MINING_RESULTSCOMPUTED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.MINING_RESULTSCOMPUTED_TAG).item(0).getTextContent(); 
                            StatusMsgsConfig.MINING_RESULTSCOMPUTING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.MINING_RESULTSCOMPUTING_TAG).item(0).getTextContent(); 
                            
                            // MINING RESULTS CLEARING
                            StatusMsgsConfig.MINING_RESULTSCLEARED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.MINING_RESULTSCLEARED_TAG).item(0).getTextContent(); 
                            StatusMsgsConfig.MINING_RESULTSCLEARING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.MINING_RESULTSCLEARING_TAG).item(0).getTextContent(); 
                            
                            // LINK PREDICTION COMPUTED/COMPUTING
                            StatusMsgsConfig.LINKPREDICTION_RESULTSCOMPUTED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.LINKPREDICTION_RESULTSCOMPUTED_TAG).item(0).getTextContent(); 
                            StatusMsgsConfig.LINKPREDICTION_RESULTSCOMPUTING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.LINKPREDICTION_RESULTSCOMPUTING_TAG).item(0).getTextContent(); 
                            
                            // LINK PREDICTION RESULTS CLEARING
                            StatusMsgsConfig.LINKPREDICTION_RESULTSCLEARED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.LINKPREDICTION_RESULTSCLEARED_TAG).item(0).getTextContent(); 
                            StatusMsgsConfig.LINKPREDICTION_RESULTSCLEARING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.LINKPREDICTION_RESULTSCLEARING_TAG).item(0).getTextContent(); 
                            
                            // SHORTEST PATH COMPUTED/COMPUTING
                            StatusMsgsConfig.SHORTESTPATH_COMPUTING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.SHORTESTPATH_COMPUTED_TAG).item(0).getTextContent(); 
                            StatusMsgsConfig.SHORTESTPATH_COMPUTING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.SHORTESTPATH_COMPUTING_TAG).item(0).getTextContent(); 
                            
                            // SHORTEST PATH RESULTS CLEARING/CLEARED
                            StatusMsgsConfig.SHORTESTPATH_RESULTSCLEARING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.SHORTESTPATH_RESULTSCLEARING_TAG).item(0).getTextContent(); 
                            StatusMsgsConfig.SHORTESTPATH_RESULTSCLEARED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.SHORTESTPATH_RESULTSCLEARED_TAG).item(0).getTextContent(); 
                                                        
                            // FILTERS 
                            StatusMsgsConfig.FILTERS_APPLYING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.FILTERS_APPLYING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.FILTERS_APPLIED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.FILTERS_APPLIED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.FILTERS_ADDING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.FILTERS_ADDING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.FILTERS_ADDED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.FILTERS_ADDED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.FILTERS_REMOVING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.FILTERS_REMOVING_TAG).item(0).getTextContent();
                            StatusMsgsConfig.FILTERS_REMOVED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.FILTERS_REMOVED_TAG).item(0).getTextContent();
                            
                            // BACKGROUND COLOR
                            StatusMsgsConfig.CANVAS_BGCOLOR_CHANGING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.CANVAS_BGCOLOR_CHANGED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.CANVAS_BGCOLOR_CHANGED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.CANVAS_BGCOLOR_CHANGING_TAG).item(0).getTextContent();
                            
                            // BACKGROUND IMAGE
                            StatusMsgsConfig.CANVAS_BGIMAGE_CHANGING = eOptionsElement.getElementsByTagName(StatusMsgsConfig.CANVAS_BGIMAGE_CHANGED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.CANVAS_BGIMAGE_CHANGED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.CANVAS_BGIMAGE_CHANGING_TAG).item(0).getTextContent();
                            
                            // MINIMAP SHOW / HIDE
                            StatusMsgsConfig.CANVAS_SHOWMINIMAP = eOptionsElement.getElementsByTagName(StatusMsgsConfig.CANVAS_SHOWMINIMAP_TAG).item(0).getTextContent();
                            StatusMsgsConfig.CANVAS_HIDEMINIMAP = eOptionsElement.getElementsByTagName(StatusMsgsConfig.CANVAS_HIDEMINIMAP_TAG).item(0).getTextContent();
                            
                            // EDGES SHOW/HIDE
                            StatusMsgsConfig.CANVAS_SHOWEDGES = eOptionsElement.getElementsByTagName(StatusMsgsConfig.CANVAS_SHOWEDGES_TAG).item(0).getTextContent();
                            StatusMsgsConfig.CANVAS_HIDEEDGES = eOptionsElement.getElementsByTagName(StatusMsgsConfig.CANVAS_HIDEEDGES_TAG).item(0).getTextContent();
                            
                            // PREDICTED EDGES SHOW/HIDE
                            StatusMsgsConfig.CANVAS_SHOWPREDICTEDEDGES = eOptionsElement.getElementsByTagName(StatusMsgsConfig.CANVAS_SHOWPREDICTEDEDGES_TAG).item(0).getTextContent();
                            StatusMsgsConfig.CANVAS_HIDEPREDICTEDEDGES = eOptionsElement.getElementsByTagName(StatusMsgsConfig.CANVAS_HIDEPREDICTEDEDGES_TAG).item(0).getTextContent();
                            
                            // ENABLING / DISABLING VISUALIZATION
                            StatusMsgsConfig.CANVAS_VISUALIZATION_ENABLED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.CANVAS_VISUALIZATION_ENABLED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.CANVAS_VISUALIZATION_DISABLED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.CANVAS_VISUALIZATION_DISABLED_TAG).item(0).getTextContent();
                            
                            // GRAPH EXPORT
                            StatusMsgsConfig.WINDOW_CLEARED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.WINDOW_CLEARED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.EXPORT_CANCELLED = eOptionsElement.getElementsByTagName(StatusMsgsConfig.EXPORT_CANCELLED_TAG).item(0).getTextContent();
                            StatusMsgsConfig.EXPORTING_GRAPH = eOptionsElement.getElementsByTagName(StatusMsgsConfig.EXPORTING_GRAPH_TAG).item(0).getTextContent();
                        } 
                    }
                    
                    /* ****************************    PARSING OF THE MODES ****************************** */
                    NodeList ndlstModes = doc.getElementsByTagName(ModeConfig.MODES_TAG);
                    for (int intModeCounter = 0; intModeCounter < ndlstModes.getLength(); intModeCounter++) {
                        Node ndMode = ndlstModes.item(intModeCounter);

                        if (ndMode.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndMode;                                

                            ModeConfig.SELECT_MODE = eOptionsElement.getElementsByTagName(ModeConfig.SELECT_MODE_TAG).item(0).getTextContent();
                            ModeConfig.VERTEXADD_MODE = eOptionsElement.getElementsByTagName(ModeConfig.VERTEXADD_MODE_TAG).item(0).getTextContent();
                            ModeConfig.VERTEXADD_MULTIFRAME_MODE = eOptionsElement.getElementsByTagName(ModeConfig.VERTEXADD_MULTIFRAME_MODE_TAG).item(0).getTextContent();
                            ModeConfig.VERTEXDELETE_MODE = eOptionsElement.getElementsByTagName(ModeConfig.VERTEXDELETE_MODE_TAG).item(0).getTextContent();
                            ModeConfig.EDGEADD_MODE = eOptionsElement.getElementsByTagName(ModeConfig.EDGEADD_MODE_TAG).item(0).getTextContent();
                            ModeConfig.EDGEDELETE_MODE = eOptionsElement.getElementsByTagName(ModeConfig.EDGEDELETE_MODE_TAG).item(0).getTextContent();
                            ModeConfig.SHORTESTPATH_MODE = eOptionsElement.getElementsByTagName(ModeConfig.SHORTESTPATH_MODE_TAG).item(0).getTextContent();
                            
                        }
                    }
                    
                    /* ****************************    PARSING OF THE MODE INFORMATION ****************************** */
                    NodeList ndlstModeInfo = doc.getElementsByTagName(ModeInformationConfig.MODEINFO_TAG);
                    for (int intModeInfoCounter = 0; intModeInfoCounter < ndlstModeInfo.getLength(); intModeInfoCounter++) {
                        Node ndModeInfo = ndlstModeInfo.item(intModeInfoCounter);

                        if (ndModeInfo.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndModeInfo;                                

                            ModeInformationConfig.SELECTMODEINFO = eOptionsElement.getElementsByTagName(ModeInformationConfig.SELECTMODEINFO_TAG).item(0).getTextContent();
                            ModeInformationConfig.SELECTVERTEX1INFO = eOptionsElement.getElementsByTagName(ModeInformationConfig.SELECTVERTEX1INFO_TAG).item(0).getTextContent();
                            ModeInformationConfig.SELECTVERTEX2INFO = eOptionsElement.getElementsByTagName(ModeInformationConfig.SELECTVERTEX2INFO_TAG).item(0).getTextContent();
                            ModeInformationConfig.SELECTEDGE_DELETE = eOptionsElement.getElementsByTagName(ModeInformationConfig.SELECTEDGE_DELETE_TAG).item(0).getTextContent();
                            ModeInformationConfig.SELECTVERTEX_ADD = eOptionsElement.getElementsByTagName(ModeInformationConfig.SELECTVERTEX_ADD_TAG).item(0).getTextContent();
                            ModeInformationConfig.SELECTVERTEX_DELETE = eOptionsElement.getElementsByTagName(ModeInformationConfig.SELECTVERTEX_DELETE_TAG).item(0).getTextContent();
                        }
                    }
                    
                    
                    /* ****************************    PARSING OF THE VERTEX SHAPES' NAMES   ****************************** */
                    NodeList ndlstVertexShapes = doc.getElementsByTagName(LangConfig.VERTEXSHAPES_TAG);
                    for (int intVertexShapesCounter = 0; intVertexShapesCounter < ndlstVertexShapes.getLength(); intVertexShapesCounter++) {
                        Node ndVertexShape = ndlstVertexShapes.item(intVertexShapesCounter);

                        if (ndVertexShape.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndVertexShape;                                
                            
                            LangConfig.VERTEXSHAPES_SQUARE = eOptionsElement.getElementsByTagName(LangConfig.VERTEXSHAPES_SQUARE_TAG).item(0).getTextContent();
                            LangConfig.VERTEXSHAPES_RECTANGLE = eOptionsElement.getElementsByTagName(LangConfig.VERTEXSHAPES_RECTANGLE_TAG).item(0).getTextContent();
                            LangConfig.VERTEXSHAPES_ELLIPSE = eOptionsElement.getElementsByTagName(LangConfig.VERTEXSHAPES_ELLIPSE_TAG).item(0).getTextContent();
                            LangConfig.VERTEXSHAPES_CIRCLE = eOptionsElement.getElementsByTagName(LangConfig.VERTEXSHAPES_CIRCLE_TAG).item(0).getTextContent();
                            
                            LangConfig.VERTEXSHAPES_SPHERE = eOptionsElement.getElementsByTagName(LangConfig.VERTEXSHAPES_SPHERE_TAG).item(0).getTextContent();
                            LangConfig.VERTEXSHAPES_CUBOID = eOptionsElement.getElementsByTagName(LangConfig.VERTEXSHAPES_CUBOID_TAG).item(0).getTextContent();
                            LangConfig.VERTEXSHAPES_CUBE = eOptionsElement.getElementsByTagName(LangConfig.VERTEXSHAPES_CUBE_TAG).item(0).getTextContent();
                            
                            // #Debug
                            //System.out.println("LanguageXML(): "+LangConfig.VERTEXSHAPES_SQUARE+"\t"+LangConfig.VERTEXSHAPES_RECTANGLE+"\t"+LangConfig.VERTEXSHAPES_ELLIPSE+"\t"+LangConfig.VERTEXSHAPES_CIRCLE);
                            // #EndDebug
                        } 
                    }
                    
                    /* ****************************    PARSING OF THE LINK PREDICTION   ****************************** */
                    NodeList ndlstLinkPrediction = doc.getElementsByTagName(LangConfig.LINKPREDICTION_TAG);
                    for (int intLinkPredCounter = 0; intLinkPredCounter < ndlstLinkPrediction.getLength(); intLinkPredCounter++) {
                        Node ndLinkPred = ndlstLinkPrediction.item(intLinkPredCounter);

                        if (ndLinkPred.getNodeType() == Node.ELEMENT_NODE) {		                             
                            Element eOptionsElement = (Element) ndLinkPred;
                            
                            LinkPredictionConfig.setParameters(
                                    eOptionsElement.getElementsByTagName(LangConfig.LINKPREDICTION_TITLE_TAG).item(0).getTextContent(),
                                    eOptionsElement.getElementsByTagName(LangConfig.LINKPREDICTION_TOPN_TAG).item(0).getTextContent(),
                                    eOptionsElement.getElementsByTagName(LangConfig.LINKPREDICTION_METRIC_TAG).item(0).getTextContent(),
                                    eOptionsElement.getElementsByTagName(LangConfig.LINKPREDICTION_TOPN_ERROR_TAG).item(0).getTextContent(),
                                    eOptionsElement.getElementsByTagName(LangConfig.LINKPREDICTION_METRIC_ERROR_TAG).item(0).getTextContent(),
                                    eOptionsElement.getElementsByTagName(LangConfig.LINKPREDICTION_TOPN_TOOLTIP_TAG).item(0).getTextContent(),
                                    eOptionsElement.getElementsByTagName(LangConfig.LINKPREDICTION_METRIC_TOOLTIP_TAG).item(0).getTextContent()
                            );
                        } 
                    }
                    
                    /* ************************* COMMUNITY MINING **************************** */
                    NodeList ndlstCommunityMining = doc.getElementsByTagName(CommunityMiningConfig.COMMUNITYMINING_TAG);
                    
                    for (int intCommunityAlgoCounter = 0; intCommunityAlgoCounter < ndlstCommunityMining.getLength(); intCommunityAlgoCounter++) {
                        
                        // Extract the Dynamic Community Mining 
                        NodeList ndlstDCMining = doc.getElementsByTagName(CommunityMiningConfig.DCMINING_TAG);
                        
                        for (int intDCMining = 0; intDCMining < ndlstDCMining.getLength(); intDCMining++) {                                        
                            Node ndDCMining = ndlstDCMining.item(intDCMining);
                            
                            if (ndDCMining.getNodeType() == Node.ELEMENT_NODE) {		                             
                                Element eOptionsElement = (Element) ndDCMining;  

                                DynamicCommunityMiningParam.setParameters(
                                          eOptionsElement.getElementsByTagName(CommunityMiningConfig.DCMINING_TITLE_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.DCMINING_SIMILARITY_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.DCMINING_METRIC_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.DCMINING_METHOD_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.DCMINING_OVERLAP_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.DCMINING_HUBS_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.DCMINING_INSTABILITY_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.DCMINING_HISTORY_TAG).item(0).getTextContent()
                                );
                            }
                        }                            
                        
                        
                        // Extract the Fast Modularity Community Mining 
                        NodeList ndFastModularityMining = doc.getElementsByTagName(CommunityMiningConfig.FASTMODULARITY_TAG);
                        
                        for (int intFMMining = 0; intFMMining < ndFastModularityMining.getLength(); intFMMining++) {                                        
                            Node ndFMMining = ndFastModularityMining.item(intFMMining);
                            
                            if (ndFMMining.getNodeType() == Node.ELEMENT_NODE) {		                             
                                Element eOptionsElement = (Element) ndFMMining; 
                                
                                FastModularityParam.setParameters(
                                          eOptionsElement.getElementsByTagName(CommunityMiningConfig.FASTMODULARITY_TITLE_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.FASTMODULARITY_NOTE_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.FASTMODULARITY_ALGORITHM_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.FASTMODULARITY_WEIGHTED_TAG).item(0).getTextContent()
                                );
                            }
                        }
                        
                        
                        // Extract the Louvain Community Mining 
                        NodeList ndLouvainMining = doc.getElementsByTagName(CommunityMiningConfig.Louvain_TAG);
                        
                        for (int intLVNMining = 0; intLVNMining < ndLouvainMining.getLength(); intLVNMining++) {                                        
                            Node ndLVNMining = ndLouvainMining.item(intLVNMining);
                            
                            if (ndLVNMining.getNodeType() == Node.ELEMENT_NODE) {		                             
                                Element eOptionsElement = (Element) ndLVNMining; 
                                
                                LouvainMiningParam.setParameters(
                                          eOptionsElement.getElementsByTagName(CommunityMiningConfig.LOUVAIN_TITLE_TAG).item(0).getTextContent()
                                        
                                );
                            }
                        }
                        
                        
                        // Extract the KMeans Clustering
                        NodeList ndlstKMeans = doc.getElementsByTagName(CommunityMiningConfig.KMEANS_TAG);
                        
                        for (int intKMeansMining = 0; intKMeansMining < ndlstKMeans.getLength(); intKMeansMining++) {                                        
                            Node ndKMeansMining = ndlstKMeans.item(intKMeansMining);
                            
                            if (ndKMeansMining.getNodeType() == Node.ELEMENT_NODE) {		                             
                                Element eOptionsElement = (Element) ndKMeansMining;  
                            
                                KMeansClusteringParam.setParameters(
                                          eOptionsElement.getElementsByTagName(CommunityMiningConfig.KMEANS_TITLE_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.KMEANS_CLUSTERCOUNT_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.KMEANS_ATTRIBUTE_TAG).item(0).getTextContent()
                                );
                            }
                        }
                        
                        
                        // Extract the Local Community Mining 
                        NodeList ndlstLocalCommMining = doc.getElementsByTagName(CommunityMiningConfig.LOCALCOMMUNITY_TAG);
                        
                        for (int intLocalCommMining = 0; intLocalCommMining < ndlstLocalCommMining.getLength(); intLocalCommMining++) {                                        
                            Node ndLocalCommMining = ndlstLocalCommMining.item(intLocalCommMining);
                            
                            if (ndLocalCommMining.getNodeType() == Node.ELEMENT_NODE) {		                             
                                Element eOptionsElement = (Element) ndLocalCommMining;  
                            
                                LocalCommunityParam.setParameters(
                                          eOptionsElement.getElementsByTagName(CommunityMiningConfig.LOCALCOMMUNITY_TITLE_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.LOCALCOMMUNITY_ALGORITHM_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.LOCALCOMMUNITY_OVERLAP_TAG).item(0).getTextContent()
                                );
                            }
                        }
                        
                        // Extract the Local T Community Mining 
                        NodeList ndlstLocalTCommMining = doc.getElementsByTagName(CommunityMiningConfig.LOCALTCOMMUNITY_TAG);
                        
                        for (int intLocalTCommMining = 0; intLocalTCommMining < ndlstLocalTCommMining.getLength(); intLocalTCommMining++) {                                        
                            Node ndLocalTCommMining = ndlstLocalTCommMining.item(intLocalTCommMining);
                            
                            if (ndLocalTCommMining.getNodeType() == Node.ELEMENT_NODE) {		                             
                                Element eOptionsElement = (Element) ndLocalTCommMining;  
                            
                                LocalTParam.setParameters(
                                          eOptionsElement.getElementsByTagName(CommunityMiningConfig.LOCALTCOMMUNITY_TITLE_TAG).item(0).getTextContent()
                                );
                            }
                        }
                        
                        
                        // Extract the Local Top Leaders Community Mining 
                        NodeList ndlstTopLeadersMining = doc.getElementsByTagName(CommunityMiningConfig.LOCALTOPLEADERS_TAG);
                        
                        for (int intTopLeadersMining = 0; intTopLeadersMining < ndlstTopLeadersMining.getLength(); intTopLeadersMining++) {                                        
                            Node ndTopLeadersMining = ndlstTopLeadersMining.item(intTopLeadersMining);
                            
                            if (ndTopLeadersMining.getNodeType() == Node.ELEMENT_NODE) {		                             
                                Element eOptionsElement = (Element) ndTopLeadersMining;  
                            
                                LocalTopLeadersParam.setParameters(
                                          eOptionsElement.getElementsByTagName(CommunityMiningConfig.LOCALTOPLEADERS_TITLE_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.LOCALTOPLEADERS_NUMBEROFCLUSTERS_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.LOCALTOPLEADERS_OUTLIERPERCENT_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.LOCALTOPLEADERS_HUBPERCENT_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.LOCALTOPLEADERS_CENTERDISTANCE_TAG).item(0).getTextContent()
                                );
                            }
                        }
                        
                        
                        // Extract the Rosvall Infomap Communing Mining Algorithm
                        NodeList ndlstRosvallInfomapMining = doc.getElementsByTagName(CommunityMiningConfig.ROSVALLINFOMAP_TAG);
                        
                        for (int intRosvallInfomapMining = 0; intRosvallInfomapMining < ndlstRosvallInfomapMining.getLength(); intRosvallInfomapMining++) {                                        
                            Node ndRosvallInfomapMining = ndlstRosvallInfomapMining.item(intRosvallInfomapMining);
                            
                            if (ndRosvallInfomapMining.getNodeType() == Node.ELEMENT_NODE) {		                             
                                Element eOptionsElement = (Element) ndRosvallInfomapMining;  

                                RosvallInfomapParam.setParameters(
                                          eOptionsElement.getElementsByTagName(CommunityMiningConfig.ROSVALLINFOMAP_TITLE_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.ROSVALLINFOMAP_NUMBEROFATTEMPTS_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.ROSVALLINFOMAP_DIRECTED_TAG).item(0).getTextContent()
                                );
                            }
                        }                        
                        
                        // Extract the Rosvall Infomod Community Mining 
                        NodeList ndlstRosvallInfomodMining = doc.getElementsByTagName(CommunityMiningConfig.ROSVALLINFOMOD_TAG);
                        
                        for (int intRosvallInfomodMining = 0; intRosvallInfomodMining < ndlstRosvallInfomodMining.getLength(); intRosvallInfomodMining++) {                                        
                            Node ndRosvallInfomodMining = ndlstRosvallInfomodMining.item(intRosvallInfomodMining);
                            
                            if (ndRosvallInfomodMining.getNodeType() == Node.ELEMENT_NODE) {		                             
                                Element eOptionsElement = (Element) ndRosvallInfomodMining;  
                            
                                RosvallInfomodParam.setParameters(
                                          eOptionsElement.getElementsByTagName(CommunityMiningConfig.ROSVALLINFOMOD_TITLE_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.ROSVALLINFOMOD_NUMBEROFATTEMPTS_TAG).item(0).getTextContent()
                                );
                            }
                        }
                        
                        
                        // Extract the Save Value Attribute Community Mining 
                        NodeList ndlstSameValueAttrMining = doc.getElementsByTagName(CommunityMiningConfig.SAMEATTRIBUTEVALUE_TAG);
                        
                        for (int intSameValueAttrMining = 0; intSameValueAttrMining < ndlstSameValueAttrMining.getLength(); intSameValueAttrMining++) {                                        
                            Node ndSameValueAttrMining = ndlstSameValueAttrMining.item(intSameValueAttrMining);
                            
                            if (ndSameValueAttrMining.getNodeType() == Node.ELEMENT_NODE) {		                             
                                Element eOptionsElement = (Element) ndSameValueAttrMining;  
                            
                                SameAttributeValueParam.setParameters(
                                          eOptionsElement.getElementsByTagName(CommunityMiningConfig.SAMEATTRIBUTEVALUE_TITLE_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.SAMEATTRIBUTEVALUE_MESSAGE_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.SAMEATTRIBUTEVALUE_ATTRIBUTE_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.SAMEATTRIBUTEVALUE_MULTIPLEVALUES_TAG).item(0).getTextContent()
                                        , eOptionsElement.getElementsByTagName(CommunityMiningConfig.SAMEATTRIBUTEVALUE_SEPERATOR_TAG).item(0).getTextContent()
                                );
                            }
                        }
                    }
                    
                } // End of if
            } // End of for             
            // return mnAnalysisMenuBar;// return mnAnalysisMenuBar;
        } catch (ParserConfigurationException | SAXException | IOException | DOMException ex) {
            System.out.println(strCallingFunction+"Error in Parsing the XML Language File: "+pstrFilePath);
            ex.printStackTrace();
        }
    }
}
