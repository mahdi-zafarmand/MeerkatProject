/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.analysis.ShortestPathAPI;
import config.StatusMsgsConfig;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import java.util.List;
import javafx.scene.control.MenuItem;

/**
 * Functionalities when the user chooses to clear the Shortest Path
 * 
 * @author talat
 * @since 2018-05-25
 */
public class MenuItemClearShortestPath extends MenuItemGeneric implements IMenuItem {

    /**
     * Constructor for MenuItemClearShortestPath
     * @param pstrMenuItemDisplay
     * @param pstrMenuItemClass
     * @param pstrMenuItemIconPath 
     * 
     * @author Talat
     * @since 2018-05-25
     */
    public MenuItemClearShortestPath(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
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
        
        pController.updateStatusBar(false, StatusMsgsConfig.SHORTESTPATH_RESULTSCLEARING);
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int intProjectId = UIInstance.getActiveProjectID();
        int intGraphId = UIInstance.getActiveProjectTab().getActiveGraphID();
        int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
        GraphTab currentGraph = UIInstance.getActiveProjectTab().getActiveGraphTab();
        
        List<Integer> lstEdgeIds = ShortestPathAPI.getShortestPathResults_AllRuns(intProjectId, intGraphId, intTimeFrameIndex);
        currentGraph.updateAfterClearingShortestPath(intProjectId, intGraphId, intTimeFrameIndex, lstEdgeIds);
        pController.updateStatusBar(false, StatusMsgsConfig.SHORTESTPATH_RESULTSCLEARED);
    }
}