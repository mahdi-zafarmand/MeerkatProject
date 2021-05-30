/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.writer;

import ca.aicml.meerkat.api.GraphAPI;
import config.AppConfig;
import config.ErrorMsgsConfig;
import ui.dialogwindow.ErrorDialog;
import globalstate.GraphTab;
import globalstate.ProjectTab;
import globalstate.TextualTab;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import meerkat.Utilities;
import org.apache.commons.io.FileUtils;

/**
 *  Class Name      : ProjectWriter
 *  Created Date    : 2015-08-06
 *  Description     : Class to save the projects writing a .mprj file
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-09-10      Talat           strProjectFilePath is not divided into 3 parameters
 *                                  1) pstrProjectDirectory
 *                                  2) pstrProjectName
 *                                  3) pstrProjectExtension
 * 
*/
public class ProjectWriter {
    
    /**
     *  Method Name     : Save
     *  Created Date    : 2015-08-10
     *  Description     : Saves a project File in the specified format
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrProjectDirectory : String
     *  @param pstrProjectName : String
     *  @param pstrProjectExtension : String
     *  @param pProjectTab : ProjectTab
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-02-29      Talat           Moving the project functionalities to logic in io.serialize package
     *  2015-10-15      Talat           Added the Saving of Textual Files
     *  2015-10-13      Talat           Changed the declaration from its usage (hmapGraphs)
     * 
    */
    public static void Save(String pstrProjectDirectory, String pstrProjectName, String pstrProjectExtension, ProjectTab pProjectTab) {
        BufferedWriter bw = null;
        // System.out.println("ProjectWriter.Save(): File Path : "+pstrProjectDirectory+pstrProjectName+pstrProjectExtension);
        try {                        
            
            // Create a file
            String strProjectFilePath = pstrProjectDirectory+pstrProjectName+pstrProjectExtension;
            File file = new File(strProjectFilePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            pProjectTab.setProjectDirectory(pstrProjectDirectory+pstrProjectName+"/");
            
            // Create a Directory
            String strDirectory = pstrProjectDirectory+pstrProjectName+"/";
            File dirProject = new File(strDirectory);
            if (!dirProject.exists()) {
                dirProject.mkdir();
            }
            
            FileWriter fw = new FileWriter(file);             
            bw = new BufferedWriter(fw);             
             
            /* Writing Project Name */
            bw.write("ProjectName"+AppConfig.PROJECTFILE_DELIMITER+pProjectTab.getProjectName()+"\n");
            
            /* Writing the Project Directory */
            bw.write("ProjectDirectory"+AppConfig.PROJECTFILE_DELIMITER+pProjectTab.getProjectDirectory()+"\n");
             
            /* Writing Saved Time */
            bw.write("SavedTime"+AppConfig.PROJECTFILE_DELIMITER+System.currentTimeMillis()+"\n");

            /* Writing the list of Raw Data Files that were used to generate Graphs */
            String strContent = "RawDataFile"+AppConfig.PROJECTFILE_DELIMITER+" ";
            for(String strRawFile : pProjectTab.getInputFilePath()) {
                strContent += strRawFile+",";
            }
            bw.write(strContent.substring(0, strContent.length()-1));
            
            String strProjectDirectory = pstrProjectDirectory + pstrProjectName + "/" ;
            
            // Writing the list of graph files that are included in the project
            Map<Integer,GraphTab> hmapGraphs = pProjectTab.getAllGraphTabs();
            for (GraphTab currentGraph : hmapGraphs.values()) {
                
                bw.write("\nGraph"+AppConfig.PROJECTFILE_DELIMITER
                        + currentGraph.getGraphTabTitle()+AppConfig.GRAPHLIST_DELIMITER
                        + currentGraph.getGraphTabTitle()+AppConfig.EXTENSION_GRAPHFILE+AppConfig.GRAPHLIST_DELIMITER
                        + currentGraph.getRawDataFile());
                
                String strGraphFilePath = strProjectDirectory + currentGraph.getGraphTabTitle()+AppConfig.EXTENSION_GRAPHFILE;
                boolean blnSuccess = GraphAPI.saveGraph(pProjectTab.getProjectID(), currentGraph.getGraphID(), strGraphFilePath);
                
                if (!blnSuccess) {
                    ErrorDialog.Display(ErrorMsgsConfig.ERROR_WRITINGGRAPHFILE);
                }
            }
            
            
            // Writing the list of textual files that are included in the project
            Map<Integer,TextualTab> hmapText = pProjectTab.getAllTextualTabs();
            
            for (TextualTab currentText : hmapText.values()) {
                // First Save the Text Files
                
                String strFileName = Utilities.getFileNameWithExtention(currentText.getFilePath());
                File flSource = new File(currentText.getFilePath());
                File flDestination = new File(strProjectDirectory+strFileName);
                FileUtils.copyFile(flSource, flDestination);
                currentText.setFilePath(strFileName);
                
                bw.write("\nTextual"+AppConfig.PROJECTFILE_DELIMITER
                            + currentText.getTitle() + AppConfig.GRAPHLIST_DELIMITER
                            + currentText.getFilePath());
                
                // Save the Graphs within this textual tab
                
                for (GraphTab currentGraph : currentText.getAllGraphs().values()) {
                    bw.write("\nGraph"+AppConfig.PROJECTFILE_DELIMITER
                            + currentGraph.getGraphTabTitle()+AppConfig.GRAPHLIST_DELIMITER
                            + currentGraph.getGraphTabTitle()+AppConfig.EXTENSION_GRAPHFILE+AppConfig.GRAPHLIST_DELIMITER
                            + currentGraph.getRawDataFile());

                    String strGraphFilePath = pstrProjectDirectory + pstrProjectName + "/" + currentGraph.getGraphTabTitle()+AppConfig.EXTENSION_GRAPHFILE;
                    boolean blnSuccess = GraphAPI.saveGraph(pProjectTab.getProjectID(), currentGraph.getGraphID(), strGraphFilePath);

                    if (!blnSuccess) {
                        ErrorDialog.Display(ErrorMsgsConfig.ERROR_WRITINGGRAPHFILE);
                    }
                }
            }
            
            
            // Write all the existing graphs 

            // System.out.println("ProjectWriter.Save(): Project File "+pstrProjectName+"written Successfully"); // #Debug
             
            bw.close();
            fw.close();

        } catch (IOException ioe) {
              ioe.printStackTrace();
        }
    }
}
