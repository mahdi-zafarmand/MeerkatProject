/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import javafx.scene.control.MenuItem;
import ui.dialogwindow.GenerateGraph;

/**
 *
 * @author sankalp
 */
public class MenuItemGenerateGraph extends MenuItemGeneric implements IMenuItem {
    
    public MenuItemGenerateGraph(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }

    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        GenerateGraph.Display(pController, strDisplayText);
    }
    
}
