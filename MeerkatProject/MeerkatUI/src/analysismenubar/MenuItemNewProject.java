/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import javafx.scene.control.MenuItem;
import ui.dialogwindow.NewProjectWizard;

/**
 *  Class Name      : MenuItemNewProject
 *  Created Date    : 2015-08-04
 *  Description     : Functionalities when the user chooses New Project from the MenuBar
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-01-28      Talat           Changed the signature of Click()
 *  
 * 
*/
public class MenuItemNewProject extends MenuItemGeneric implements IMenuItem {

    /**
     *  Constructor Name: MenuItemNewProject()
     *  Created Date    : 2015-08-04
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
    public MenuItemNewProject(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
       /**
     *  Method Name     : Click
     *  Created Date    : 2015-07-15
     *  Description     : The sequence of events that should take place on clicking the New Project Item 
     *                  : 1) Open a Dialog box with a text box
     *                  : 2) Request for the Project Name
     *                          a) Check if the Project name already exists
     *                  : 3) Create a Project with the entered Project Name
     *                  : 4) Display a blank graph in the Project
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController: AnalysisController
     *  @param pMenuItem : MenuItem
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-01-28      Talat           Changed the parameter to boolean to MenuItem
     *  2015-07-15      Talat           Created the class
     * 
    */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        
        // Display the New Project dialog box
        NewProjectWizard.Display(pController);
    }
}
