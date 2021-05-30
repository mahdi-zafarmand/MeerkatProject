/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import config.ModeConfig;
import globalstate.MeerkatUI;
import javafx.scene.control.MenuItem;
import ui.elements.EditingToolBox;

/**
 * Functionalities when the user chooses to clear the Shortest Path
 * 
 * @author talat
 * @since 2018-05-25
 */
public class MenuItemModeAddVerticesTimeFrames extends MenuItemGeneric implements IMenuItem {

    /**
     * Constructor for MenuItemModeAddVertices
     * @param pstrMenuItemDisplay
     * @param pstrMenuItemClass
     * @param pstrMenuItemIconPath 
     * 
     * @author Talat
     * @since 2018-05-25
     */
    public MenuItemModeAddVerticesTimeFrames(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
    /**
     * Actions to be taken when the Clear Shortest Path from Menubar is clicked
     * @param pController
     * @param pMenuItem
     * @param pobjParameter 
     * 
     * @author Talat
     * @since 2018-05-25
     */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        EditingToolBox.getInstance().activateAddMultiFrameVertex();
        UIInstance.getActiveProjectTab().getActiveGraphTab().setGraphCanvasMode(ModeConfig.ModeTypes.VERTEXMULTIFRAMEADD);
    }
}