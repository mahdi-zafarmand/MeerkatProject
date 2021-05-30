/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import javafx.scene.control.MenuItem;
import ui.toolsribbon.ToolsRibbonContextMenu;

/**
 *  Class Name      : MenuItemShowHideToolsRibbon
 *  Created Date    : 2016-01-18
 *  Description     : The Menu Item and the functionalities to be executed on clicking this option
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-01-28      Talat           Changed the signature of Click()
 * 
*/
public class MenuItemShowHideToolsRibbon extends MenuItemGeneric implements IMenuItem {
            
    /**
     *  Constructor Name: MenuItemShowHideToolsRibbon()
     *  Created Date    : 2016-01-18
     *  Description     : Calls the super constructor to initialize the values
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrMenuItemDisplay : String
     *  @param pstrMenuItemClass : String
     *  @param pstrMenuItemIconPath : String
     *  
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public MenuItemShowHideToolsRibbon(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
        
    /**
     *  Method Name     : Click
     *  Created Date    : 2016-01-18
     *  Description     : Actions to be taken when the Sho Hide is clicked option is clicked
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     *  @param pMenuItem : MenuItem
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author      Description
     *  2016-01-28      Talat       Changed the parameter from boolean to MenuItem
     * 
    */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        
        System.out.println("MenuItemShowHideToolsRibbon.Click(): State of the tool: "+ToolsRibbonContextMenu.getIsShown());
        if (ToolsRibbonContextMenu.getIsShown()) {
            System.out.println("MenuItemShowHideToolsRibbon.Click(): Hiding the tool bar");
            pController.hideToolsRibbon();
        } else {
            System.out.println("MenuItemShowHideToolsRibbon.Click(): Showing the tool bar");
            pController.showToolsRibbon();
        }
    }
}
