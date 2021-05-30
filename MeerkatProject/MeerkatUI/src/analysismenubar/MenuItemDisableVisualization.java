
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import javafx.scene.control.MenuItem;


/**
 *  Class Name      : MenuItemDisableVisualization
 *  Created Date    : 2016-08-08
 *  Description     : The Menu Item and the functionalities to be executed on clicking this option
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
 * 
*/
public class MenuItemDisableVisualization extends MenuItemGeneric implements IMenuItem {
            
    /**
     *  Constructor Name: MenuItemDisableVisualization()
     *  Created Date    : 2016-08-08
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
    public MenuItemDisableVisualization(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
        
    /**
     *  Method Name     : Click
     *  Created Date    : 2016-08-08
     *  Description     : Actions to be taken when the Sho Hide is clicked option is clicked
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     *  @param pMenuItem : MenuItem
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author      Description
     * 
    */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        UIInstance.getActiveProjectTab().getActiveGraphTab().disableVisualization();
        pController.updateStatusBar(false, StatusMsgsConfig.CANVAS_VISUALIZATION_DISABLED);        
    }
}