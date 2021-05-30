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
 *  Class Name      : MenuItemPinVertices
 *  Created Date    : 2016-05-25
 *  Description     : The Menu Item and the functionalities to be executed on clicking this option
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-01-28      Talat           Changed the signature of Click()
 * 
*/
public class MenuItemPinVertices extends MenuItemGeneric implements IMenuItem {
            
    /**
     *  Constructor Name: MenuItemPinVertices()
     *  Created Date    : 2016-05-25
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
    public MenuItemPinVertices(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
        
    /**
     *  Method Name     : Click
     *  Created Date    : 2016-05-25
     *  Description     : Actions to be taken when the Show/Hide is clicked option is clicked
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
        
        pController.updateStatusBar(true, StatusMsgsConfig.VERTEXPINNING_ENABLING);
        UIInstance.getActiveProjectTab().getActiveGraphTab().pinVertexToCanvas();
        pController.updateStatusBar(false, StatusMsgsConfig.VERTEXPINNING_ENABLED);
    }
}