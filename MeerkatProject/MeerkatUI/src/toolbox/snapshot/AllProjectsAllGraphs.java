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
import globalstate.ProjectTab;
import io.utilities.Directories;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.stage.DirectoryChooser;
import javax.imageio.ImageIO;

/**
 *  Class Name      : AllProjectsAllGraphs
 *  Created Date    : 2016-01-08
 *  Description     : Takes the snapshot of all the graphs in all the open Projects
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class AllProjectsAllGraphs {
    
    
    /**
     *  Method Name     : SaveAsPNG()
     *  Created Date    : 2016-01-07
     *  Description     : Takes a screenshot of the all the graphs in all the open projects and saves it as a .png image files
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     *  @return boolean : true for a successful save
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-01-14      Talat           The updateStatusBar is invoked to change the status of the status bar
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
                
                for (ProjectTab currentProject : UIInstance.getAllProject().values()) {
                                       
                    // Create a directory for the project in the selected directory
                    Directories.Create(selectedDirectory.getAbsolutePath()+"/"+currentProject.getProjectName());
                    
                    System.out.println("AllProjectsAllGraphs.SaveAsPNG(): Directories Created: "+selectedDirectory.getAbsolutePath()+"/"+currentProject.getProjectName());
                        
                    for (GraphTab currentGraph : currentProject.getAllGraphTabs().values()) {                        
                        WritableImage image = currentGraph.getZoomPane().snapshot(new SnapshotParameters(), null);
                                                
                        // Write the graph files in the directory
                        File flName = new File(selectedDirectory.getAbsolutePath()+"/"+currentProject.getProjectName()+"/"+currentGraph.getGraphTabTitle());
                        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", flName);
                    }
                    blnIsSuccess = true; 
                }
            } else {
                pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            }   
                       
            pController.updateStatusBar(false, StatusMsgsConfig.SNAPSHOT_ALLPROJECTSALLGRAPHS);
            
        } catch (IOException e) {
            // TODO: handle exception here
        }
        
        return blnIsSuccess;
    }
}
