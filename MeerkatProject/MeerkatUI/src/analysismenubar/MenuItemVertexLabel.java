/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import globalstate.MeerkatUI;
import javafx.scene.control.CheckMenuItem;
import ui.dialogwindow.SelectVertexLabel;
import ui.utilities.VertexLabelShowHide;

/**
 *  Class Name      : MenuItemNewProject
 *  Created Date    : 2016-05-25
 *  Description     : Functionalities when the user selects the Vertex Label 
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class MenuItemVertexLabel extends MenuItemGeneric implements ICheckMenuItem {
        
    /**
     *  Constructor Name: MenuItemVertexLabel()
     *  Created Date    : 2016-05-25
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
    public MenuItemVertexLabel(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
       /**
     *  Method Name     : Click
     *  Created Date    : 2016-05-25
     *  Description     : On click, toggle the property
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController: AnalysisController
     *  @param pMenuItem : MenuItem
     *  @param pobjParameter : Object
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    @Override
    public void Click(AnalysisController pController, CheckMenuItem pMenuItem, Object pobjParameter) {    
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        String strVertexLabelAttribute = UIInstance.getActiveProjectTab().getActiveGraphTab().getVertexLabelAttr();        
        
        if (strVertexLabelAttribute.isEmpty() || strVertexLabelAttribute == null) {
            SelectVertexLabel.Display(pController);
        } else {
            UIInstance.getActiveProjectTab().getActiveGraphTab().setIsVertexLabelShown(!UIInstance.getActiveProjectTab().getActiveGraphTab().IsVertexLabelShown());
        }
        
        VertexLabelShowHide.Execute(pController);
    }
}
