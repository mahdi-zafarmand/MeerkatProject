/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import java.io.File;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

/**
 *  Class Name      : MenuItemChangeBGImage
 *  Created Date    : 2016-05-27
 *  Description     : Functionalities when the user chooses Selects to change Background color
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
*/
public class MenuItemChangeBGImage extends MenuItemGeneric implements IMenuItem {

    /**
     *  Constructor Name: MenuItemChangeBGImage()
     *  Created Date    : 2016-05-27
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
    public MenuItemChangeBGImage(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
    
    
    /**
     *  Method Name     : Click
     *  Created Date    : 2015-05-27
     *  Description     : 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController: AnalysisController
     *  @param pMenuItem: MenuItem
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author      Description
     *  2016-01-28      Talat       Changed the parameter from boolean to MenuItem
     * 
    */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        
        try {
            pController.updateStatusBar(true, StatusMsgsConfig.WAITING_USERINPUT);
            
            /* Open the Dialog Box to allow the user to select the file */
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg",
                                        "*.JPEG", "*.jpeg");
            fileChooser.getExtensionFilters().add(extFilter);
            extFilter = new FileChooser.ExtensionFilter("PNG files (.png)", "*png");
            fileChooser.getExtensionFilters().add(extFilter);
            extFilter = new FileChooser.ExtensionFilter("Bitmap Images (.bmp)", "*bmp");
            fileChooser.getExtensionFilters().add(extFilter);
            extFilter = new FileChooser.ExtensionFilter("TIFF Images (.tiff)", "*tiff");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showOpenDialog(null);
            
            // If the file is null, then the Cancel button is pressed. Handle what is to be done when cancelled button is pressed
            if (file == null) {
                pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            } else {
                pController.updateStatusBar(false, StatusMsgsConfig.CANVAS_BGIMAGE_CHANGING);
                MeerkatUI UIInstance = MeerkatUI.getUIInstance();               
                UIInstance.getActiveProjectTab().getActiveGraphTab().changeBackgroundImage(file.getAbsolutePath());
                pController.updateStatusBar(false, StatusMsgsConfig.CANVAS_BGIMAGE_CHANGED);
            }
        } catch (Exception ex) {
            System.out.println("MenuItemBGChange.Click(): EXCEPTION");
            ex.printStackTrace();
        } 
        
    }
    
}
