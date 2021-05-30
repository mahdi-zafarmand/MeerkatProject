/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import globalstate.MeerkatUI;
import javafx.scene.control.MenuItem;
import ui.dialogwindow.NeighborhoodDegreeDialog;

/**
 *  Class Name      : MenuItemViewVertexNeighbourhood
 *  Created Date    : 2016-08-24
 *  Description     : Functionalities when the user chooses View Vertex Neighbors from the MenuBar
 *  Version         : 1.0
 *  @author         : Abhi
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class MenuItemViewVertexNeighbourhood extends MenuItemGeneric implements IMenuItem{
    
    
    /**
     *  Constructor Name: MenuItemViewVertexNeighbourhood()
     *  Created Date    : 2016-08-24
     *  Description     : Calls the super constructor to initialize the values
     *  Version         : 1.0
     *  @author         : Abhi
     * 
     *  @param pstrMenuItemDisplay : String
     *  @param pstrMenuItemClass : String
     *  @param pstrMenuItemIconPath : String
     *  
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public MenuItemViewVertexNeighbourhood(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
       /**
     *  Method Name     : Click
     *  Created Date    : 2016-08-24
     *  Description     : Displays the parameters required for getting vertex neighbors
     *  Version         : 1.0
     *  @author         : Abhi
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
        
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
        int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
        int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex() ;
        NeighborhoodDegreeDialog.Display(pController, intProjectID, intGraphID, intTimeFrameIndex);
    }
    
}
