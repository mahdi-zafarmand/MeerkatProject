/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import javafx.scene.control.MenuItem;
import ui.dialogwindow.AddEdgeAttributeDialog;

/**
 *  Class Name      : MenuItemAddEdgesAttributes
 *  Created Date    : 2016-01-12
 *  Description     : Functionalities when the user chooses Add Edges based on Attributes from the Menubar
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-01-28      Talat           Changed the signature of Click()
 * 
*/
public class MenuItemAddEdgesAttributes extends MenuItemGeneric implements IMenuItem {

    /**
     *  Constructor Name: MenuItemAddEdgesAttributes()
     *  Created Date    : 2016-01-12
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
    public MenuItemAddEdgesAttributes(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
       /**
     *  Method Name     : Click
     *  Created Date    : 2016-01-12
     *  Description     : Opens up the Add Edge Attribute Dialog Box 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController: AnalysisController
     *  @param pMenuItem: MenuItem
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author      Description
     *  2016-01-28      Talat       Changed the parameter from boolean to MenuItem
     * 
    */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        
        // Display the New Project dialog box
        AddEdgeAttributeDialog.Display(pController);                
    }
}