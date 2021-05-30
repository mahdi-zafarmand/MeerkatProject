/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.snapshot;

import analysisscreen.AnalysisController;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *  Class Name      : FullSnapshot
 *  Created Date    : 2016-01-08
 *  Description     : Take the Snapshot of the Complete Application (Including all panes)
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-01-14      Talat           The updateStatusBar is invoked to change the status of the status bar
 *  
 * 
*/
public class ApplicationSnapshot {
    
    
    /**
     *  Method Name     : SaveAsPNG()
     *  Created Date    : 2016-01-07
     *  Description     : Takes a screen shot of the current graph (entire graph) and saves it as a .png file
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     *  @return boolean : true for a successful save
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static boolean SaveAsPNG (AnalysisController pController) {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        boolean blnIsSuccess = false ;
                
        try {
            // WritableImage image = UIInstance.getActiveProject().getActiveGraphTab().getDrawingPane().snapshot(new SnapshotParameters(), null);
            
            WritableImage image = pController.getApplicationNode().snapshot(new SnapshotParameters(), null);
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose save location");
        
            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);
            
            pController.updateStatusBar(true, StatusMsgsConfig.WAITING_USERINPUT);

            //Show save file dialog
            File file = fileChooser.showSaveDialog(pController.getPrimaryStage());

            if(file != null){
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                blnIsSuccess = true;
            } else {
                pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            }    
                        
            pController.updateStatusBar(false, StatusMsgsConfig.SNAPSHOT_APPLICATION);
            
        } catch (IOException e) {
            // TODO: handle exception here
        }
        
        return blnIsSuccess;
    }
}
