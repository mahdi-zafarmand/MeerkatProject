/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.parser;

/**
 *  Class Name      : AnalysisMenu
 *  Created Date    : 2015-07-xx
 *  Description     : Contains the Parser for the Analysis Menu
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class AnalysisMenu {
    
    /**
     *  Method Name     : Parse()
     *  Created Date    : 2015-07-xx
     *  Description     : Parses the XML file to get the MenuBar that would be 
     *                    displayed at the top of the AnalysisScreen
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrFilePath : String
     *  @return MenuBar
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    /*
    public static MenuBar Parse(String pstrFilePath) {        
        String strCallingFunction = "AnalysisMenu.Parse(): ";
        MenuBar menuAnalysis = new MenuBar();
        
        try{
            
            File fXmlFile = new File(pstrFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();		 
            NodeList nList = doc.getElementsByTagName(AppConfig.LANGUAGE_ROOT_TAG);

            for (int temp = 0; temp < nList.getLength(); temp++) {	
                
                Node nNode = nList.item(temp);		 
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {		                             
                    Element eElement = (Element) nNode;

                    NodeList nMenu = doc.getElementsByTagName(AppConfig.MENUBAR_ANALYSIS_TAG);

                    for (int intMenuCounter = 0; intMenuCounter < nMenu.getLength(); intMenuCounter++) {
                        Node nodeMenu = nMenu.item(intMenuCounter);
                        NodeList lstMenuOption = doc.getElementsByTagName(AppConfig.MENUBAR_OPTION_ANALYSIS_TAG);
                        for (int intMenuOptionCounter = 0; intMenuOptionCounter < lstMenuOption.getLength(); intMenuOptionCounter++) {                                        
                            Node nodeMenuOption = lstMenuOption.item(intMenuOptionCounter);
                            String strMenuOption = nodeMenuOption.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_VALUE_TAG).getNodeValue();
                            
                            HashMap<String, MenuItemGeneric> hmapMenuItems = new HashMap<>();                            
                            
                            NodeList childMenuItems = nodeMenuOption.getChildNodes();                                    
                            for (int intMenuItemCounter = 0; intMenuItemCounter < childMenuItems.getLength(); intMenuItemCounter++) {
                                Node nodeMenuItem = childMenuItems.item(intMenuItemCounter);
                                if (nodeMenuItem instanceof Element) {
                                    String strMenuItemText = nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_TEXT_TAG).getNodeValue();
                                    String strClassName = nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_CLASS_TAG).getNodeValue();
                                    String strIconPath = nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_ICON_TAG).getNodeValue();
                                    boolean blnCheckMenuItem = Boolean.parseBoolean(nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_CHECKMENUITEM_TAG).getNodeValue());
                                    boolean blnMnemonic = Boolean.parseBoolean(nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_MNEMONIC_TAG).getNodeValue());
                                    
                                    String strShortcut = "" ;
                                    if (nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_SHORTCUT_TAG) != null) {
                                        strShortcut = nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_SHORTCUT_TAG).getNodeValue() ;
                                    }             
                                    
                                    boolean blnDisabled = false;
                                    if (nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_DISABLED_TAG) != null) {
                                        blnDisabled = Boolean.parseBoolean(nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_DISABLED_TAG).getNodeValue());
                                    } 
                                    hmapMenuItems.put(strMenuItemText, new MenuItemGeneric(strMenuItemText, strClassName, strIconPath, blnMnemonic, blnCheckMenuItem, blnDisabled, strShortcut));
                                }
                            }
                            menuAnalysis.addMenuOption(intMenuOptionCounter, new MenuOption(strMenuOption, hmapMenuItems));
                        }
                    }
                } // End of if
                
            } // End of for   
            
            return menuAnalysis;
        } catch (ParserConfigurationException | SAXException | IOException | DOMException ex) {
                System.out.println(strCallingFunction+"Error in Parsing the XML Language File: "+pstrFilePath);
        }
        return null;
    }
*/
}
