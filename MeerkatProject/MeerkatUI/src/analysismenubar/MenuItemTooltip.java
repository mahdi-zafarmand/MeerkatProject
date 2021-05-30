
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import javafx.scene.control.CheckMenuItem;
import ui.dialogwindow.SelectVertexLabel;
import ui.dialogwindow.SelectVertexTooltip;
import ui.utilities.VertexLabelShowHide;
import ui.utilities.VertexTooltipShowHide;

/**
 *  Class Name      : MenuItemTooltip
 *  Created Date    : 2016-01-28
 *  Description     : Functionalities when the user selects the Tool Tip option 
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class MenuItemTooltip extends MenuItemGeneric implements ICheckMenuItem {
        
    private static boolean blnIsSelected = false ;
    
    /**
     *  Constructor Name: MenuItemVertexLabel()
     *  Created Date    : 2016-01-28
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
    public MenuItemTooltip(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);        
    }
    
       /**
     *  Method Name     : Click
     *  Created Date    : 2016-01-28
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
        String strVertexTooltipAttribute=  UIInstance.getActiveProjectTab().getActiveGraphTab().getVertexTooltipAttr();
        
        if (strVertexTooltipAttribute.isEmpty() || strVertexTooltipAttribute == null) {
            System.out.println("MenuItemTooltip(): Tool tip selection");
            SelectVertexTooltip.Display(pController);            
            return ;
        } else {
            UIInstance.getActiveProjectTab().getActiveGraphTab().setIsVertexTooltipShown(!UIInstance.getActiveProjectTab().getActiveGraphTab().IsVertexTooltipShown());
        }
        
        VertexTooltipShowHide.Execute(pController);
        
    }
}
