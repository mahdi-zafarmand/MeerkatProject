/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import graphelements.SaveCommunity;
import javafx.scene.control.MenuItem;

/**
 *
 * @author sankalp
 */
/**
 *  Class Name      : MenuItemSaveCommunities
 *  Created Date    : 2017-07-06
 *  Description     : Functionalities when the user chooses Save Communities from the menu bar
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class MenuItemSaveCommunities extends MenuItemGeneric implements IMenuItem {

    /**
     *  Constructor Name: MenuItemSaveCommunities()
     *  Created Date    : 2016-07-06
     *  Description     : Calls the super constructor to initialize the values
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pstrMenuItemDisplay : String
     *  @param pstrMenuItemClass : String
     *  @param pstrMenuItemIconPath : String
     *  
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public MenuItemSaveCommunities(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
       /**
     *  Method Name     : Click
     *  Created Date    : 2017-07-06
     *  Description     : displays the location for saving the community file.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pController: AnalysisController
     *  @param pMenuItem : MenuItem
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        
        SaveCommunity.Display(pController);                
    }
}
