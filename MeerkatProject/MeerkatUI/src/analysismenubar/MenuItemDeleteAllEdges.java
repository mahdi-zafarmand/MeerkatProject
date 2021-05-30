/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import globalstate.MeerkatUI;
import javafx.scene.control.MenuItem;
import ui.dialogwindow.EdgeDeleteConfirmationDialog;

/**
 *
 * @author sankalp
 */

/**
 *  Class Name      : MenuItemDeleteAllEdges
 *  Created Date    : 2017-07-24
 *  Description     : Functionalities when the user chooses to delete all edges from the menu
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class MenuItemDeleteAllEdges extends MenuItemGeneric implements IMenuItem {

    /**
     *  Constructor Name: MenuItemDeleteAllEdges()
     *  Created Date    : 2016-07-24
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
    public MenuItemDeleteAllEdges(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
       /**
     *  Method Name     : Click
     *  Created Date    : 2017-07-24
     *  Description     : call the method to delete all the edges.
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
        
        MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getGraphCanvas().selectAllEdges();
        
        EdgeDeleteConfirmationDialog.Display(pController);
    }
}
