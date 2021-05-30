package analysismenubar;

import analysisscreen.AnalysisController;
import communitymining.dialogwindow.RosvallInfomod;
import javafx.scene.control.MenuItem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *  Class Name      : MenuItemRosvallInfomod
 *  Created Date    : 2016-04-12
 *  Description     : Functionalities when the user chooses RosvallInfomod Community Mining Algorithm from the MenuBar
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class MenuItemRosvallInfomod extends MenuItemGeneric implements IMenuItem {

    /**
     *  Constructor Name: MenuItemRosvallInfomod()
     *  Created Date    : 2016-04-12
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
    public MenuItemRosvallInfomod(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
       /**
     *  Method Name     : Click
     *  Created Date    : 2016-04-12
     *  Description     : Displays the parameters required for the community mining algorithm 
     *  Version         : 1.0
     *  @author         : Talat
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
        
        // Display the Parameters dialog box
        RosvallInfomod.Display(pController, pobjParameter.toString());                
    }
}