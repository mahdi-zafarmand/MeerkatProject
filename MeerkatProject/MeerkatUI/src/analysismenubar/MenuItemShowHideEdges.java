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

/**
 * Functionalities when the user chooses Show/Hides Edges
 * 
 * @author talat
 * @since 2018-04-14
 */
public class MenuItemShowHideEdges extends MenuItemGeneric implements ICheckMenuItem {
            
    /**
     * Constructor for MenuItemShowHideEdges class
     * @param pstrMenuItemDisplay
     * @param pstrMenuItemClass
     * @param pstrMenuItemIconPath 
     * 
     * @author Talat
     * @since 2018-04-14
     */
    public MenuItemShowHideEdges(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
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
    public void Click(AnalysisController pController, CheckMenuItem pMenuItem, Object pobjParameter) {
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        if (UIInstance.getActiveProjectTab().getActiveGraphTab().getAreEdgesShown()) {
            UIInstance.getActiveProjectTab().getActiveGraphTab().hideEdges();
            pController.updateStatusBar(false, StatusMsgsConfig.CANVAS_SHOWEDGES);
        } else {
            UIInstance.getActiveProjectTab().getActiveGraphTab().showEdges();
            pController.updateStatusBar(false, StatusMsgsConfig.CANVAS_HIDEEDGES);
        }
    }
}