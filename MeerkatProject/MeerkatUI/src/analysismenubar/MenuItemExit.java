/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import javafx.scene.control.MenuItem;
import ui.dialogwindow.ErrorDialog;
import ui.dialogwindow.ExitDialog;

/**
 *
 * @author Talat-AICML
 */
public class MenuItemExit extends MenuItemGeneric implements IMenuItem  {
    
    /**
     *  Constructor Name: MenuItemOpenProject()
     *  Created Date    : 2016-03-04
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
    public MenuItemExit(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
    
    /**
     *  Method Name     : Click
     *  Created Date    : 2016-03-04
     *  Description     : Exiting the primary screen of the Application
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController: AnalysisController
     *  @param pMenuItem: MenuItem
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author      Description
     * 
    */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        
        try {
            ExitDialog.Display(pController.getPrimaryStage(), pController);
        } catch (Exception exMeerkat) {
            ErrorDialog.Display(exMeerkat.getMessage());
        } 
        
    }
    
}
