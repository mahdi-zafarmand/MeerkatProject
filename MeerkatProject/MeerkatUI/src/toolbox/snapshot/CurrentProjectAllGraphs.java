/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.snapshot;

import analysisscreen.AnalysisController;
import config.StatusMsgsConfig;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.stage.DirectoryChooser;
import javax.imageio.ImageIO;

/**
 *  Class Name      : CurrentProjectAllGraphs
 *  Created Date    : 2016-01-08
 *  Description     : Takes the snapshot of all the graphs in a Project
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-01-14      Talat           The updateStatusBar is invoked to change the status of the status bar
 *  
 * 
*/
public class CurrentProjectAllGraphs {
    
    /**
     *  Method Name     : SaveAsPNG()
     *  Created Date    : 2016-01-07
     *  Description     : Takes a screenshot of the all the graphs in the current active project and saves it as a .png image files
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
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Choose save location ");            
            /*                
            // If a default directory is to be set, uncomment this block comment        
            File defaultDirectory = new File("c:/dev/javafx");
            chooser.setInitialDirectory(defaultDirectory);
            */            
            
            pController.updateStatusBar(true, StatusMsgsConfig.WAITING_USERINPUT);
            
            File selectedDirectory = dirChooser.showDialog(pController.getPrimaryStage());
            
            if(selectedDirectory != null){                                
                for (GraphTab currentGraph : UIInstance.getActiveProjectTab().getAllGraphTabs().values()) {
                    WritableImage image = currentGraph.getZoomPane().snapshot(new SnapshotParameters(), null);
                    File flName = new File(selectedDirectory.getAbsolutePath()+"/"+currentGraph.getGraphTabTitle());
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", flName);
                    blnIsSuccess = true;
                }
            } else {
                pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            } 
            
            pController.updateStatusBar(false, StatusMsgsConfig.SNAPSHOT_CURRENTPROJECTALLGRAPH);
        } catch (IOException e) {
            // TODO: handle exception here
        }
        
        return blnIsSuccess;
    }
    
    
    // JPG SAVING IS NOT COMPLETED
    /**
     *  Method Name     : SaveAsJPG()
     *  Created Date    : 2016-01-07
     *  Description     : Takes a screenshot of the current graph (entire graph) and saves it as a .png file
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
    public static boolean SaveAsJPG (AnalysisController pController) {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        boolean blnIsSuccess = false ;
                
        try {
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Save Complete Project ");            
            /*                
            // If a default directory is to be set, uncomment this block comment        
            File defaultDirectory = new File("c:/dev/javafx");
            chooser.setInitialDirectory(defaultDirectory);
            */            
            File selectedDirectory = dirChooser.showDialog(pController.getPrimaryStage());
            
            if(selectedDirectory != null){                                
                for (GraphTab currentGraph : UIInstance.getActiveProjectTab().getAllGraphTabs().values()) {
                    WritableImage image = currentGraph.getDrawingPane().snapshot(new SnapshotParameters(), null);
                    File flName = new File(selectedDirectory.getAbsolutePath()+"/"+currentGraph.getGraphTabTitle());
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg", flName);
                }
            }     
            
            blnIsSuccess = true;
        } catch (IOException e) {
            // TODO: handle exception here
        }
        
        return blnIsSuccess;
    }
}
