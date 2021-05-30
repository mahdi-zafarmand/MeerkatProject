/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import config.ErrorMsgsConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import java.io.File;
import javafx.stage.FileChooser;
/**
 *
 * @author Talat
 * @since 2018-01-24
 */
public class OpenProject {
    
    /**
     * Provides an option to select the .mprj files and invokes the functions to parse the project/graph files
     * @param pController 
     * @since 2018-01-24
     * @author Talat
     */
    public static void Display(AnalysisController pController) {        
        
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("Meerkat Project (.mprj)", "*.mprj");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("All Files (*.*)", "*.*");
        fileChooser.getExtensionFilters().add(extFilter1);
        fileChooser.getExtensionFilters().add(extFilter2);
        fileChooser.setInitialDirectory((new File(System.getProperty("user.dir"))));
        File file = fileChooser.showOpenDialog(pController.getPrimaryStage());
        
        String strProjectFilePath;
        
        if(file!= null){
            strProjectFilePath = file.getAbsolutePath();
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            if (UIInstance.isProjectOpen(file.getName())) {
                ErrorDialog.Display(ErrorMsgsConfig.ERROR_PROJECTALREADYOPEN);
                return;
            }
            ui.utilities.OpenProject.load(pController, strProjectFilePath);
        } else {
            pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
        }
        
    }
}
