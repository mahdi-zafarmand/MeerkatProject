/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import javafx.scene.control.MenuItem;
import ui.utilities.LoadGraph;

/**
 *  Class Name      : MenuItemLoadGraphData
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
public class MenuItemLoadGraphData extends MenuItemGeneric implements IMenuItem {
    
    /**
     *  Constructor Name: MenuItemLoadGraphData()
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
    public MenuItemLoadGraphData(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
    /**
     *  Method Name     : Click
     *  Created Date    : 2015-08-05
     *  Description     : The sequence of events that should take place on clicking Load Graph File 
     *                  : 1) Check if there is any project that has been created
     *                  : 2) Show the File Dialog Box - user would select an input file
     *                  : 3) Based on the input file selected, call 
     *                  :   a) The Reader that would read the file and convert it into the graph
     *                      b) Populate the Thread Tree
     *                      c) Display the graph / or not display on the MainGraph Pane
     *                      d) Update the thread tree
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
        
        LoadGraph.load(pController);
    }
}
