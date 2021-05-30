/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import globalstate.MeerkatUI;
import javafx.scene.control.MenuItem;

/**
 *  Class Name      : MenuItemOpenProject
 *  Created Date    : 2016-xx-xx
 *  Description     : Functionalities when the user chooses Load Project from the MenuBar
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-01-28      Talat           Changed the signature of Click()
 * 
*/
public class MenuItemFishEyeLens extends MenuItemGeneric implements IMenuItem{

    /**
     *  Constructor Name: MenuItemFishEyeLens()
     *  Created Date    : 2016-xx-xx
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
    public MenuItemFishEyeLens(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
    /**
     *  Method Name     : Click
     *  Created Date    : 2015-08-11
     *  Description     : Actions to be taken when the Save option is clicked
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController: AnalysisController
     *  @param pMenuItem: MenuItem
     *  @param pobjParameter : Object
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-01-28      Talat           Changed the parameter from boolean to MenuItem
    */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        MeerkatUI applicationInstance = MeerkatUI.getUIInstance();
        applicationInstance.getActiveProjectTab().getActiveGraphTab().setFisheyeLens(false);
        // pController.setFishEyeLens(pblnSelected);
        // pController.updateMainGraph(applicationInstance.getActiveProjectTab().getActiveGraphTab().getWebEngine());
    }
}
