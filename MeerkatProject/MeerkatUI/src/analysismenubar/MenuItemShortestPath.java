/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import config.ModeConfig.ModeTypes;
import globalstate.MeerkatUI;
import javafx.scene.control.MenuItem;
import ui.elements.EditingToolBox;


/**
 * Functionalities when the user chooses Shortest Path with existing graph
 * 
 * @author talat
 * @since 2018-04-09
 */
public class MenuItemShortestPath extends MenuItemGeneric implements IMenuItem {

    /**
     * Constructor for MenuItemLinkPrediction
     * @param pstrMenuItemDisplay
     * @param pstrMenuItemClass
     * @param pstrMenuItemIconPath 
     * 
     * @author Talat
     * @since 2018-04-09
     */
    public MenuItemShortestPath(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
    /**
     * Actions to be taken when the Shortest Path from Menubar is clicked
     * @param pController
     * @param pMenuItem
     * @param pobjParameter 
     * 
     * @author Talat
     * @since 2018-04-09
     */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        EditingToolBox.getInstance().activateShortestPath();
        UIInstance.getActiveProjectTab().getActiveGraphTab().setGraphCanvasMode(ModeTypes.SHORTESTPATH);
    }
}