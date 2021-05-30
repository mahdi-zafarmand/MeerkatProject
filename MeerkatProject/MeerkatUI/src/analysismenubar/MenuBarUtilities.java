/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import config.AppConfig;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ui.elements.MeerkatVertexImage;

/**
 *  Class Name      : MenuBarUtilities
 *  Created Date    : 2015-09-xx
 *  Description     : List of Utilities for the MenuBar
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-09-11      Talat           Moved Debug_PrintMenu method from Utilities Class
 * 
*/
public class MenuBarUtilities {
     /**
     *  Method Name     : initiateMenuBar()
     *  Created Date    : 2015-07-22
     *  Description     : Initializes the MenuBar of the Analysis Screen
     *  Version         : 1.0
     *  @author Talat
     * 
     *  @param pController: AnalysisController
     *  @return lstMenu: List<Menu>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-01-29      Talat           Added the functionality of mnemonics and shortcuts
     *  2016-01-29      Talat           Removed all the old code. Check previously dated versions for old code
     *  2015-07-28      Talat           Moved it from AnalysisController.java to a sub module in Menubar package
     * 
    */
    public static List<Menu> initiateMenuBar(AnalysisController pController) {
        
        /* Read the parameters from the language file */
        MenuBar mnAnalysisMenuInstance = MenuBar.getInstance(AppConfig.XML_LANGUAGE_FILE);    
        List<Menu> lstMenu = new ArrayList<>();
        
        for(Integer intKey: mnAnalysisMenuInstance.MenuOptions().keySet()){
            MenuOption menuOptionCurrent = mnAnalysisMenuInstance.MenuOptions().get(intKey);            
            
            // List of all Menu that are displayed horizantally
            Menu menuCurrent = new Menu(menuOptionCurrent.getMenuOptionName());
            menuCurrent.setMnemonicParsing(true);
            
            for(Integer intMenuItemKey : menuOptionCurrent.getMenuItems().keySet()) {                
                MenuItemGeneric currentMenuItem = menuOptionCurrent.getMenuItems().get(intMenuItemKey);

                /* If the Menu item is a checked menu item */
                if (currentMenuItem.IsCheckMenuItem()) {
                    
                    CheckMenuItem itemCurrent = new CheckMenuItem(currentMenuItem.getDisplayName());
                    // System.out.println("MenubarUtilities: Check Item: "+currentMenuItem.getDisplayName());
                    
                    if (currentMenuItem.getIconPath() != null && !currentMenuItem.getIconPath().isEmpty()) {
//                        itemCurrent.setGraphic(new ImageView(new Image(currentMenuItem.getIconPath())));
                        
//                        MAHDI: next line is to remove "file: resources/" from the beginning of the url, needs to be fixed.
                        String imageurl = currentMenuItem.getIconPath().substring(15);
                        itemCurrent.setGraphic(new ImageView(new Image(MenuBarUtilities.class.getClassLoader().getResourceAsStream(imageurl))));

//                        InputStream imgMenu = MenuBarUtilities.class.getClassLoader().getResourceAsStream(currentMenuItem.getIconPath().substring(5));
////                        System.out.println("MenubarUtilities img = " + img + ", path = " + currentMenuItem.getIconPath());
//                        if(imgMenu != null){
//                            itemCurrent.setGraphic(new ImageView(new Image(imgMenu)));
//                        }
                    }
                    itemCurrent.setSelected(false); // If the false is set to true, change in all possible places with value as true
                    
                    // Check if the Mnemonic parsing is set in the input file                    
                    if (currentMenuItem.getMnemonicParsing()) {
                        itemCurrent.setMnemonicParsing(true);
                    }
                    
                    // Implement the shortcut functionality by setting the accelerator
                    if (currentMenuItem.getKeyCodeCombination() != null) {
                        itemCurrent.setAccelerator(currentMenuItem.getKeyCodeCombination());
                    }
                                        
                    switch (currentMenuItem.getDisabledLevel()) {
                        case NOTHING :
                            itemCurrent.setDisable(false);
                            break ;
                        case PROJECT_AVAILABLE :
                            itemCurrent.setDisable(true);
                            break ;
                        case GRAPH_AVAILABLE :
                            itemCurrent.setDisable(true);
                            break ;
                        case TEXTUAL_AVAILABLE :
                            itemCurrent.setDisable(true);
                            break ;       
                    } 
                    
                    // Java Refelection on clicking the option
                    itemCurrent.setOnAction(e -> {                    
                        try {
                            String strParam_Display = currentMenuItem.getDisplayName();
                            String strParam_Class = currentMenuItem.getItemClass();
                            String strParam_IconPath = currentMenuItem.getIconPath();
                            String strParam_Parameter = currentMenuItem.getParameter();

                            String strClassName = "analysismenubar."+currentMenuItem.getItemClass();
                            Class clsMenuItem = Class.forName(strClassName);
                            Constructor constructor = clsMenuItem.getConstructor(String.class, String.class, String.class);
                            Object objMenuItem = constructor.newInstance(strParam_Display, strParam_Class, strParam_IconPath);

                            // Call the Click Method
                            Class[] paramTab = new Class[3];
                            paramTab[0] = AnalysisController.class;
                            paramTab[1] = CheckMenuItem.class;
                            paramTab[2] = Object.class ;

                            Method method = clsMenuItem.getDeclaredMethod("Click", paramTab);
                            method.invoke(objMenuItem, pController, itemCurrent, (Object)strParam_Parameter);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException ex) {
                            Logger.getLogger(MenuBar.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                    });
                    menuCurrent.getItems().add(itemCurrent);
                    
                    for (int intLocalI=0; intLocalI < currentMenuItem.getSeparator(); intLocalI++) {
                        menuCurrent.getItems().add(new SeparatorMenuItem());
                    }
                    
                } else {
                    
                    MenuItem itemCurrent = new MenuItem(currentMenuItem.getDisplayName());
                    
                    // Set the image if the image url is available
                    if (currentMenuItem.getIconPath() != null && !currentMenuItem.getIconPath().isEmpty()) {
                        itemCurrent.setGraphic(new ImageView(new Image(currentMenuItem.getIconPath())));
                        
//                        MAHDI: next line is to remove "file: resources/" from the beginning of the url, needs to be fixed.
//                        String imageurl = currentMenuItem.getIconPath().substring(15);
//                        itemCurrent.setGraphic(new ImageView(new Image(MenuBarUtilities.class.getClassLoader().getResourceAsStream(imageurl))));

                        //InputStream img = MenuBarUtilities.class.getClassLoader().getResourceAsStream(currentMenuItem.getIconPath());
                        //System.out.println("MenubarUtilities img = " + img + ", path = " + currentMenuItem.getIconPath());
                        //if(img != null){
                        //    itemCurrent.setGraphic(new ImageView(new Image(img)));
                        //}
                    }
                    
                    // Check if the Mnemonic parsing is set in the input file
                    // System.out.println("MenuBarUtilities.initiateMenuBar(): "+currentMenuItem.getDisplayName()+"\tMnemonics: "+currentMenuItem.getMnemonicParsing());
                    if (currentMenuItem.getMnemonicParsing()) {
                        itemCurrent.setMnemonicParsing(true);
                    }
                    
                    
                    switch (currentMenuItem.getDisabledLevel()) {
                        case NOTHING :
                            itemCurrent.setDisable(false);
                            break ;
                        case PROJECT_AVAILABLE :
                            itemCurrent.setDisable(true);
                            break ;
                        case GRAPH_AVAILABLE :
                            itemCurrent.setDisable(true);
                            break ;
                        case TEXTUAL_AVAILABLE :
                            itemCurrent.setDisable(true);
                            break ;       
                    }
                    
                    // Implement the shortcut functionality by setting the accelerator
                    if (currentMenuItem.getKeyCodeCombination() != null) {
                        itemCurrent.setAccelerator(currentMenuItem.getKeyCodeCombination());
                    }
                    
                    itemCurrent.setOnAction(e -> {
                        try {
                            String strParam_Display = currentMenuItem.getDisplayName();
                            String strParam_Class = currentMenuItem.getItemClass();
                            String strParam_IconPath = currentMenuItem.getIconPath();
                            String strParam_Parameter = currentMenuItem.getParameter();

                            String strClassName = "analysismenubar."+currentMenuItem.getItemClass();
                            Class clsMenuItem = Class.forName(strClassName);
                            Constructor constructor = clsMenuItem.getConstructor(String.class, String.class, String.class);
                            Object objMenuItem = constructor.newInstance(strParam_Display, strParam_Class, strParam_IconPath);

                            
                            // Call the Click Method
                            Class[] paramTab = new Class[3];
                            paramTab[0] = AnalysisController.class;
                            paramTab[1] = MenuItem.class;
                            paramTab[2] = Object.class ;

                            Method method = clsMenuItem.getDeclaredMethod("Click", paramTab);
                            method.invoke(objMenuItem, pController, itemCurrent, (Object)strParam_Parameter);
                            
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException | InstantiationException | NoSuchMethodException | SecurityException ex) {
                            Logger.getLogger(MenuBar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    
                    menuCurrent.getItems().add(itemCurrent);
                    
                    for (int intLocalI=0; intLocalI < currentMenuItem.getSeparator(); intLocalI++) {
                        menuCurrent.getItems().add(new SeparatorMenuItem());
                    }
                    
                } // End of if-else

            } // End of for 
            
            lstMenu.add(menuCurrent);                        
        }    
        return lstMenu;
    }
    
    
    /**
     *  Method Name     : Debug_PrintMenu
     *  Created Date    : 2015-06-xx
     *  Description     : Debug function to print all the Menu Items from  the XML file
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pmnMenu : Menubar
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Debug_PrintMenu(MenuBar pmnMenu) {
        for (MenuOption curMenuOption : pmnMenu.MenuOptions().values()) {
            System.out.println(curMenuOption.getMenuOptionName());
            for (MenuItemGeneric curMenuItem : curMenuOption.getMenuItems().values()) {
                System.out.println("\t"+curMenuItem.getDisplayName()+"\tParsing: "+curMenuItem.getMnemonicParsing());
            }
        }
    }
    
    
    /**
     *  Method Name     : Debug_PrintMenu
     *  Created Date    : 2015-06-xx
     *  Description     : Debug function to print all the Menu Items from  the XML file
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param phmapMenuOptions : HashMap<Integer, MenuOption> 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Debug_Print(HashMap<Integer, MenuOption> phmapMenuOptions) {        
        for(Integer intKey: phmapMenuOptions.keySet()){
            MenuOption moptCurrent = phmapMenuOptions.get(intKey);
            moptCurrent.Print();
        }
    }
}
