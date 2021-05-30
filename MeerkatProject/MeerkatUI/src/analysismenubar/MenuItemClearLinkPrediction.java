/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import javafx.scene.control.MenuItem;
import linkprediction.ClearLinkPredictionResults;

/**
 *
 * @author talat
 * @since 2018-04-09
 */
public class MenuItemClearLinkPrediction extends MenuItemGeneric implements IMenuItem {

    public MenuItemClearLinkPrediction(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        
        // Display the Parameters dialog box
        ClearLinkPredictionResults.Display(pController, pobjParameter.toString());                
    }    
}