/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import communitymining.dialogwindow.SiwoPlus;
import javafx.scene.control.MenuItem;

/**
 *
 * @author mahdi
 */
public class MenuItemSiwoPlus extends MenuItemGeneric implements IMenuItem {

    public MenuItemSiwoPlus(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }

    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        
        // Display the Parameters dialog box
        SiwoPlus.Display(pController, pobjParameter.toString());                
    }
    
}
