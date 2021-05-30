/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import config.SettingsWindowConfig;
import javafx.scene.control.MenuItem;
import ui.dialogwindow.ExportAttributes;

/**
 * Class defining the events for the MenuItem of Exporting Data
 * @author Talat
 * @since 2018-01-24
 */
public class MenuItemExportData extends MenuItemGeneric implements IMenuItem {
    
    /**
     * Calls the super constructor to initialize the values
     * @param pstrMenuItemDisplay
     * @param pstrMenuItemClass
     * @param pstrMenuItemIconPath 
     * @author Talat
     * @since 2018-01-24
     */
    public MenuItemExportData(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
    /**
     * The dialog window that is to be shown when this MenuItem is clicked
     * @param pController
     * @param pMenuItem
     * @param pobjParameter 
     * @author Talat
     * @since 2018-01-24
     */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        ExportAttributes.Display(pController, "Export Data Attributes");
    }
}