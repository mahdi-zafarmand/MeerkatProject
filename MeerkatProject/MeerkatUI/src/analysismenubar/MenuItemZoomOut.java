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
 *  Class Name      : MenuItemZoomOut
 *  Created Date    : 2017-07-24
 *  Description     : Functionalities when the user chooses to Zoom Out
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
*/
public class MenuItemZoomOut extends MenuItemGeneric implements IMenuItem {

    /**
     *  Constructor Name: MenuItemZoomOut()
     *  Created Date    : 2017-07-24
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
    public MenuItemZoomOut(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
   
    /**
     *  Method Name     : Click
     *  Created Date    : 2017-07-24
     *  Description     : executes Zoom Out
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
        
        //call the zoom utility with negative deltaY
        ZoomCanvas.zoomCanvas(-1.0);
    } 
    
}
