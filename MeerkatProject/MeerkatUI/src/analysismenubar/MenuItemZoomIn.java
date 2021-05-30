/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import globalstate.UIComponents.ZoomCanvas;
import javafx.scene.control.MenuItem;

/**
 *  Class Name      : MenuItemZoomIn
 *  Created Date    : 2017-07-21
 *  Description     : Functionalities when the user chooses to Zoom In
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
*/
public class MenuItemZoomIn extends MenuItemGeneric implements IMenuItem {

    /**
     *  Constructor Name: MenuItemZoomIn()
     *  Created Date    : 2017-07-21
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
    public MenuItemZoomIn(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
    /**
     *  Method Name     : Click
     *  Created Date    : 2017-07-21
     *  Description     : executes Zoom In
     *  Version         : 1.0
     *  @author         : sankalp
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
        
        //call the zoom utility with positive deltaY
        ZoomCanvas.zoomCanvas(1.0);
    }
    
}
