/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import javafx.scene.control.MenuItem;

public class MenuItemShowHidePredictedEdges extends MenuItemGeneric implements IMenuItem {
            
    /**
     * Constructor for MenuItemShowHidePredictedEdges class
     * @param pstrMenuItemDisplay
     * @param pstrMenuItemClass
     * @param pstrMenuItemIconPath 
     * 
     * @author Talat
     * @since 2018-04-14
     */
    public MenuItemShowHidePredictedEdges(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
        
    
    /**
     * The action to be taken when the user clicks Show/hide Edges option from the Menu
     * @param pController
     * @param pMenuItem
     * @param pobjParameter 
     * 
     * @author Talat
     * @since 2018-04-14
     */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        if (UIInstance.getActiveProjectTab().getActiveGraphTab().getArePredictedEdgesShown()) {
            UIInstance.getActiveProjectTab().getActiveGraphTab().hidePredictedEdges();
            pController.updateStatusBar(false, StatusMsgsConfig.CANVAS_SHOWPREDICTEDEDGES);
        } else {
            UIInstance.getActiveProjectTab().getActiveGraphTab().showPredictedEdges();
            pController.updateStatusBar(false, StatusMsgsConfig.CANVAS_HIDEPREDICTEDEDGES);
        }
    }
}