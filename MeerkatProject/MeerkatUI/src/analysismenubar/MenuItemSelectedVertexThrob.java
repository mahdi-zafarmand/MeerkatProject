/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import config.SceneConfig;
import globalstate.MeerkatUI;
import javafx.scene.control.MenuItem;

/**
 *  Class Name      : MenuItemSelectedVertexThrob
 *  Created Date    : 2016-07-08
 *  Description     : Class to implement the throb functionality for all the selected nodes
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
*/


public class MenuItemSelectedVertexThrob extends MenuItemGeneric implements IMenuItem {
    
    /**
     *  Constructor Name: MenuItemAppParameters()
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
    public MenuItemSelectedVertexThrob(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
    /**
     *  Method Name     : Click
     *  Created Date    : 2016-01-18
     *  Description     : The functionalities to be executed when App Parameters is clickes
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController: AnalysisController
     *  @param pMenuItem: MenuItem
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-01-28      Talat           Changed the parameter from boolean to MenuItem
    */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
        UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().ThrobAndBlinkSelectedVertices();
        
    }
}