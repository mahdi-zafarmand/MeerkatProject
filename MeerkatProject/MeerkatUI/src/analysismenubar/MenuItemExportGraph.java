/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.ExportAPI;
import config.ExportFileFilters;
import config.FileFilter;
import config.LangConfig;
import config.SceneConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import java.io.File;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import meerkat.Utilities;
import ui.dialogwindow.ErrorDialog;
import ui.dialogwindow.InfoDialog;

/**
 *  Class Name      : MenuItemExportGraph
 *  Created Date    : 2016-03-21
 *  Description     : Functionalities when the user chooses Export Graph from the MenuBar
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
*/
public class MenuItemExportGraph extends MenuItemGeneric implements IMenuItem {

    /**
     *  Constructor Name: MenuItemExportGraph()
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
    public MenuItemExportGraph(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
    /**
     *  Method Name     : Click
     *  Created Date    : 2016-03-21
     *  Description     : The sequence of events that should take place on clicking the event
     *                  : 1) Open the file chooser to select the location and the file format to export the graph
     *                  : 2) Call the Respective writers based on the input
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
            pController.updateStatusBar(true, StatusMsgsConfig.GRAPH_EXPORTING);
            /* Open the Dialog Box to allow the user to select the file */
            FileChooser fileChooser = new FileChooser();

            for (FileFilter currentFilter : ExportFileFilters.getFileFilters()) {
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(currentFilter.getFileDisplay()+"(*"+currentFilter.getFileExtension()+")", "*"+currentFilter.getFileExtension());
                fileChooser.getExtensionFilters().add(extFilter);
            }       
            
            File file = fileChooser.showSaveDialog(null);
            
            // If the file is null, then the Cancel button is pressed. Handle what is to be done when cancelled button is pressed
            if (file == null) {
                pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            }
            else {
                System.out.println("MenuItemOpenProject.Click(): File choosen : "+file.getAbsolutePath()); // #Debug

                // Call the API with the ID and the file path
                MeerkatUI UIInstance = MeerkatUI.getUIInstance();
                int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
                int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
                String strFilePath = file.getAbsolutePath() ;
                
                String [] arrstrParam = ExportFileFilters.getWriterParameters(fileChooser.getSelectedExtensionFilter().getExtensions()) ;
                String strWriterID = ExportFileFilters.getWriterID(fileChooser.getSelectedExtensionFilter().getExtensions()) ;
                
                // Extension is not required to be sent since the respective writers would append the extension
                strFilePath = Utilities.getFilePathWithoutExtention(strFilePath) ; 
                
                String strFilePathReturnedOrError = ExportAPI.ExportGraph(intProjectID, intGraphID, null, strFilePath, strWriterID, arrstrParam) ;
                System.out.println(strFilePathReturnedOrError);
                if (Utilities.tryParseInteger(strFilePathReturnedOrError)) {
                    int intErrorCode = Integer.parseInt(strFilePathReturnedOrError) ;                
                    if (intErrorCode >= 0) {
                        // Display a msg box that the Graph has been exported
                        InfoDialog.Display(LangConfig.INFO_GRAPHEXPORTED + "\n" + strFilePath, SceneConfig.INFO_TIMEOUT);

                        // Set the Status bar
                        pController.updateStatusBar(false, StatusMsgsConfig.GRAPH_EXPORTED);

                    } else if (intErrorCode < 0) {
                        // Display the Error Dialog Box
                        ErrorDialog.Display(intErrorCode);                    
                        pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_FAILED);
                    }
                }
                pController.updateStatusBar(false, StatusMsgsConfig.GRAPH_EXPORTED);
            }
        } catch (Exception exMeerkat) {
            ErrorDialog.Display(exMeerkat.getMessage());
            exMeerkat.printStackTrace();
        } 
        
    }
}
