/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import config.ErrorMsgsConfig;
import config.LangConfig;
import config.SceneConfig;
import config.StatusMsgsConfig;
import javafx.scene.control.MenuItem;
import toolbox.snapshot.FullGraphSnapshot;
import ui.dialogwindow.ErrorDialog;
import ui.dialogwindow.InfoDialog;

/**
 *  Class Name      : MenuItemSnapshotCurrentGraph
 *  Created Date    : 2016-03-21
 *  Description     : Functionalities when the user chooses to take s snapshot of the application
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
*/
public class MenuItemSnapshotCurrentGraph extends MenuItemGeneric implements IMenuItem {

    /**
     *  Constructor Name: MenuItemSnapshotCurrentGraph()
     *  Created Date    : 2016-03-21
     *  Description     : Calls the super constructor to initialize the values
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrMenuItemDisplay : String
     *  @param pstrMenuItemClass : String
     *  @param pstrMenuItemIconPath : String
     *  
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public MenuItemSnapshotCurrentGraph(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
    /**
     *  Method Name     : Click
     *  Created Date    : 2016-03-21
     *  Description     : Saves the snapshot of the Application
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController: AnalysisController
     *  @param pMenuItem: MenuItem
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author      Description
     * 
    */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        
        try {
            // pController.updateStatusBar(true, StatusMsgsConfig.GRAPH_EXPORTING);
            
            boolean blnIsSucess = FullGraphSnapshot.SaveAsPNG(pController) ;
            
            if (blnIsSucess) {
                    // Display a msg box that the Graph has been exported
                    InfoDialog.Display(LangConfig.INFO_SNAPSHOTSAVED, SceneConfig.INFO_TIMEOUT);
                    
                    // Set the Status bar
                    pController.updateStatusBar(false, StatusMsgsConfig.SNAPSHOT_APPLICATION);
            } else {
                InfoDialog.Display(ErrorMsgsConfig.ERROR_SNAPSHOTFAILED, SceneConfig.INFO_TIMEOUT);
                
                pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_FAILED);
            }             
        } catch (Exception exMeerkat) {
            ErrorDialog.Display(exMeerkat.getMessage());
        } 
        
    }
}