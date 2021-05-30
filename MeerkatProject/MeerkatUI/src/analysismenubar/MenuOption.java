/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import java.util.Map;
import java.util.TreeMap;

/**
 *  Class Name      : MenuOption
 *  Created Date    : 2015-07-xx
 *  Description     : Each MenuOption will contain the main items in the Menubar
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-05-27      Talat           Changed the Hashmap<String, MenuItemGeneric> to TreeMap<Integer, MenuItemGeneric>
 *                                  to allow the ordering of the Menu based on the order in XML
 * 
*/

public class MenuOption {
    private String strMenuOptionName ;
    private Map<Integer, MenuItemGeneric> mapMenuItems ;
    
    public MenuOption() {
        this.mapMenuItems = new TreeMap<>();
    }
    public MenuOption(String pstrMenuOptionName, Map<Integer, MenuItemGeneric> plststrMenuItems) {
        this.strMenuOptionName = pstrMenuOptionName;
        this.mapMenuItems = new TreeMap<>();
        this.mapMenuItems = plststrMenuItems;
    }
    
    
    public void setMenuOptionName(String pstrMenuOptionName) {
        this.strMenuOptionName = pstrMenuOptionName;
    }
    public String getMenuOptionName(){
        return this.strMenuOptionName;
    }
    public void setMenuItems(Map<Integer, MenuItemGeneric> plststrMenuItems) {
        this.mapMenuItems = plststrMenuItems;
    }
    public Map<Integer, MenuItemGeneric> getMenuItems() {
        return this.mapMenuItems;
    }
    
    public MenuItemGeneric getMenuItem(String pstrDisplayText) {
        for (MenuItemGeneric currentMenuItemGeneric : this.mapMenuItems.values()) {
            if (currentMenuItemGeneric.getDisplayName().equals(pstrDisplayText)) {
                return currentMenuItemGeneric ;
            }
        }
        return null;
    }
    
    
    // #DEBUG FUNCTIONS
    public void Print() {
        System.out.println(this.strMenuOptionName);
        mapMenuItems.keySet().stream().forEach((strKey) -> {            
            System.out.println(strKey  +" :: "+ mapMenuItems.get(strKey).getDisplayName());
        });
    }
}
