/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import javafx.scene.control.MenuItem;
import ui.dialogwindow.LinkPrediction;


/**
 * Functionalities when the user chooses Link Prediction with existing graph
 * 
 * @author talat
 * @since 2018-04-09
 */
public class MenuItemLinkPrediction extends MenuItemGeneric implements IMenuItem {

    /**
     * Constructor for MenuItemLinkPrediction
     * @param pstrMenuItemDisplay
     * @param pstrMenuItemClass
     * @param pstrMenuItemIconPath 
     * 
     * @author Talat
     * @since 2018-04-09
     */
    public MenuItemLinkPrediction(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
    /**
     * Actions to be taken when the Link Prediction from Menubar is clicked
     * @param pController
     * @param pMenuItem
     * @param pobjParameter 
     * 
     * @author Talat
     * @since 2081-04-09
     */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {        
        // Display the Link Prediction Display dialog box
        LinkPrediction.Display(pController, pobjParameter.toString());                
    }
}