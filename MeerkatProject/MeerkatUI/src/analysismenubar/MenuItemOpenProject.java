/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import javafx.scene.control.MenuItem;
import ui.dialogwindow.OpenProject;

/**
 *  Class Name      : MenuItemOpenProject
 *  Created Date    : 2015-06-29
 *  Description     : Functionalities when the user chooses Load Project from the MenuBar
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-01-28      Talat           Changed the signature of Click()
 * 
*/
public class MenuItemOpenProject extends MenuItemGeneric implements IMenuItem {

    /**
     *  Constructor Name: MenuItemOpenProject()
     *  Created Date    : 2015-06-29
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
    public MenuItemOpenProject(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
    /**
     *  Method Name     : Click
     *  Created Date    : 2015-07-15
     *  Description     : The sequence of events that should take place on clicking the event
     *                  : 1) Open the file chooser to select the project (.mprj) files
     *                  : 2) Call the IO.Parser - that would return the ProjectTab
     *                  : 3) The project Tab would be used to update the UI components
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController: AnalysisController
     *  @param pMenuItem: MenuItem
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author      Description
     *  2016-01-28      Talat       Changed the parameter from boolean to MenuItem
     *  2018-01-24      Talat       Calling a Display from ui.dialogwindow instead of directly calling the OpenProject from utilities
     * 
    */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        
        try {
            OpenProject.Display(pController);            
        } catch (Exception ex) {
            System.out.println("MenuItemOpenProject.Click(): EXCEPTION") ;
            ex.printStackTrace();
        } 
        
    }
}
