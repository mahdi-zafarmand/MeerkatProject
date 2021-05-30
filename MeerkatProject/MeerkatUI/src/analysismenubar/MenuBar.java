/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import config.AppConfig;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
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
 *  Class Name      : MenuBar
 *  Created Date    : 2015-06-xx
 *  Description     : To hold the Menubar items
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-09-11      Talat           Moved the Print() method to MenuBarUtilities
 * 
*/
public class MenuBar {
    
    private HashMap<Integer, MenuOption> hmapMenuOptions ;
    
    private static MenuBar mnMenuBarInstance = null ;
    
    public static MenuBar getInstance(){
        return mnMenuBarInstance ;
    }
    
    public static MenuBar getInstance(String pstrFilePath){
        if (mnMenuBarInstance == null) {
            mnMenuBarInstance = new MenuBar();
            Parse(pstrFilePath);
        } 
        return mnMenuBarInstance ;
    }
    
    private MenuBar() {
        this.hmapMenuOptions = new HashMap<>();
    }
    
    public HashMap<Integer, MenuOption> MenuOptions() {
        return this.hmapMenuOptions ;        
    }    
    public void setMenuBar(HashMap<Integer, MenuOption> phmapMenuOptions) {
        this.hmapMenuOptions = phmapMenuOptions;
    }
    
    public void addMenuOption(Integer pintKey, MenuOption pmenoptValue) {
        this.hmapMenuOptions.put(pintKey, pmenoptValue);
    }
    
    public MenuOption getMenuOptionByName(String pstrName) {
        for (MenuOption mnCurrent : hmapMenuOptions.values()) {
            if (mnCurrent.getMenuOptionName().equals(pstrName)) {
                return mnCurrent ;
            }
        }
        return null ;
    }

    
    private static void Parse(String pstrFilePath) {

        String strCallingFunction = "MenuBar.Parse(): ";
        
        try{
//            File fXmlFile = new File(pstrFilePath);
            InputStream fXmlFile = MenuBar.class.getClassLoader().getResourceAsStream(pstrFilePath);
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
                            
                            Map<Integer, MenuItemGeneric> mapMenuItems = new TreeMap<>();                            
                            
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
                                    
                                    int intDisabledLevel = -1 ;
                                    if (nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_DISABLED_TAG) != null) {
                                        intDisabledLevel = Integer.parseInt(nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_DISABLED_TAG).getNodeValue());
                                    }
                                    
                                    int intSeparatorCount = 0 ;
                                    if (nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_SEPARATORCOUNT_TAG) != null) {
                                        intSeparatorCount = Integer.parseInt(nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_SEPARATORCOUNT_TAG).getNodeValue());
                                    }
                                    
                                    String strParameter = "" ;
                                    if (nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_IDMAPPINGPARAMETER_TAG) != null) {
                                        strParameter = nodeMenuItem.getAttributes().getNamedItem(AppConfig.ATTRIBUTE_IDMAPPINGPARAMETER_TAG).getNodeValue() ;
                                    }
                                    
                                    mapMenuItems.put(intMenuItemCounter, new MenuItemGeneric(strMenuItemText, strClassName, strIconPath, blnMnemonic
                                            , blnCheckMenuItem, intDisabledLevel, strShortcut, strParameter, intSeparatorCount));
                                }
                            }
                            mnMenuBarInstance.addMenuOption(intMenuOptionCounter, new MenuOption(strMenuOption, mapMenuItems));
                        }
                    }
                } // End of if
                
            } // End of for   
        } catch (ParserConfigurationException | SAXException | IOException | DOMException ex) {
            System.out.println(strCallingFunction+"Error in Parsing the XML Language File: "+pstrFilePath);
            ex.printStackTrace();
        }
    }
}
