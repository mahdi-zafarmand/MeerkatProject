/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import javafx.scene.control.MenuItem;
import ui.dialogwindow.ExportMetrics;

/**
 *
 * @author sankalp
 */
/**
 *  Class Name      : MenuItemExportMetrics
 *  Created Date    : 2017-07-13
 *  Description     : Functionalities when the user chooses Export Metrics from the menu bar
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class MenuItemExportMetrics extends MenuItemGeneric implements IMenuItem {

    /**
     *  Constructor Name: MenuItemExportMetrics()
     *  Created Date    : 2016-07-13
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
    public MenuItemExportMetrics(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
       /**
     *  Method Name     : Click
     *  Created Date    : 2017-07-13
     *  Description     : displays the location for exporting the metrics file.
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
        
        ExportMetrics.Display(pController);                
    }
}
