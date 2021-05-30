/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import javafx.scene.control.MenuItem;
import ui.dialogwindow.DocumentationMeerkat;


/**
 *  Class Name      : MenuItemAppParameters
 *  Created Date    : 2016-01-18
 *  Description     : The class for implementing the functionalities of documentation Option from the Menu
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-01-28      Talat           Changed the signature of Click()
 * 
*/

public class MenuItemDocumentation extends MenuItemGeneric implements IMenuItem {
    
    /**
     *  Constructor Name: MenuItemDocumentation()
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
    public MenuItemDocumentation(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
    /**
     *  Method Name     : Click
     *  Created Date    : 2016-01-18
     *  Description     : The functionalities to be executed when App Parameters is clicks
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController: AnalysisController
     *  @param pMenuItem: MenuItem
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author      Description
     *  2016-01-28      Talat       Changed the parameter from boolean to MenuItem
    */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
                DocumentationMeerkat.Display(pController, strDisplayText);
    }
}