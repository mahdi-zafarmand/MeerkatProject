/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.serialize;

import config.MeerkatSystem;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.text.classinterface.ITextualNetwork;
import io.graph.writer.MeerkatWriter;
import io.utility.Utilities;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import main.meerkat.MeerkatBIZ;
import org.apache.commons.io.FileUtils;

/**
 *  Class Name      : ProjectSerialization
 *  Created Date    : 2016-02-29
 *  Description     : Serializes the Project file
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class ProjectSerialization {
    
    /**
     *  Method Name     : Save()
     *  Created Date    : 2016-02-29
     *  Description     : Serializes the Project
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrProjectDirectory : String
     *  @param pstrProjectName : String
     *  @param pstrProjectExtension : String
     *  @param pintProjectID : int
     *  @param psetGraphIDs : Set<Integer>
     *  @param psetTextualIDs : Set<Integer>
     *  @param plststrInputRawFiles : List<String>
     *  @param pstrProjectFileDelimiter : String
     *  @param pstrGraphListDelimiter : String
     *  @return int 
     * 
     *  NOTE: The functionality is moved from ProjectWriter.java in MeerkatUI to LogicLayer
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static int Write(
              String pstrProjectDirectory
            , String pstrProjectName
            , String pstrProjectExtension
            , int pintProjectID
            
            , Set<Integer> psetGraphIDs            
            , Set<Integer> psetTextualIDs
            
            , List<String> plststrInputRawFiles
            , String pstrProjectFileDelimiter
            , String pstrGraphListDelimiter) {
        
        BufferedWriter bw = null;
        
        int intErrorCode = -1000200 ;
        // System.out.println("ProjectWriter.Save(): File Path : "+pstrProjectDirectory+pstrProjectName+pstrProjectExtension);
        try {       
            
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
            
            String strRootDirectory = pstrProjectDirectory + "../";
            
            // Create a file for writing .mprj file, file = .mprj file
            //String strProjectFilePath = strRootDirectory + pstrProjectName + pstrProjectExtension;
            
            Path projectFolderPath = Paths.get(pstrProjectDirectory);
            Path mprjDirectoryPath = projectFolderPath.getParent();

            String strProjectFilePath = mprjDirectoryPath.getFileName().toString() + File.separator + pstrProjectName + pstrProjectExtension;

            System.out.println("ProjectSerialization.Write(): Project Directory: "+pstrProjectDirectory + "  :: mprjfilepath = " + strProjectFilePath);            
            
            File file = new File(strProjectFilePath);
            if (file.exists()) {
                file.delete(); // Delete for an existing file to overwrite the contents
            }
            System.out.println("ProjectSerialization.write() : FilePath " + strProjectFilePath);
            file.createNewFile();
                        
            // Create a Directory - do not delete the entire project directory - it may have other non graph files as well
            String strCurrentProjectSaveDirectory = pstrProjectDirectory ;//+ File.separator;
                        
            // WRITING THE PROJECT FILE
            FileWriter fw = new FileWriter(file);             
            bw = new BufferedWriter(fw);             
             
            bw.write("ProjectName"+pstrProjectFileDelimiter + pstrProjectName + "\n"); // Writing Project Name
            bw.write("ProjectDirectory"+pstrProjectFileDelimiter + pstrProjectDirectory + "\n"); //Writing the Project Directory
            bw.write("SavedTime" + pstrProjectFileDelimiter + System.currentTimeMillis()+"\n"); //Writing Saved Time 
            /*
            System.out.println("ProjectSerialization.Write(): ProjectName"+pstrProjectFileDelimiter + pstrProjectName);
            System.out.println("ProjectSerialization.Write(): ProjectDirectory"+pstrProjectFileDelimiter + pstrProjectDirectory);
            System.out.println("ProjectSerialization.Write(): SavedTime" + pstrProjectFileDelimiter + System.currentTimeMillis());
            */
            /* Writing the list of Raw Data Files that were used to generate Graphs */
            String strContent = "RawDataFile" + pstrProjectFileDelimiter + " ";
            for(String strRawFile : plststrInputRawFiles) {
                strContent += strRawFile+",";
            }
            bw.write(strContent.substring(0, strContent.length()-1));   
            System.out.println("ProjectSerialization.Write(): "+strContent.substring(0, strContent.length()-1));

            
            // System.out.println("ProjectSerialization.Write(): Number of graphs in Project = "+psetGraphIDs.size());
            // Create a directory for resources
            String strResourceDir = projectFolderPath + "/" + MeerkatSystem.PROJECT_RESOURCES_DIR;
            Utilities.createDirectory(strResourceDir, false);
            
            String strIconDir = strResourceDir + MeerkatSystem.PROJECT_ICONS_DIR;
            Utilities.createDirectory(strIconDir, false);
            
            // Writing the list of graph files that are included in the project            
            for (int intCurrentGraphID : psetGraphIDs) {
                
                IDynamicGraph<IVertex, IEdge<IVertex>> grpCurrent = BIZInstance.getProject(pintProjectID).getGraph(intCurrentGraphID);
                String strGraphTitle = grpCurrent.getGraphTitle();
                String strGraphFileFullPath = grpCurrent.getGraphFile();
                String extension = Utilities.getFileExtension(strGraphFileFullPath); // extension = .meerkat, .gml etc
                
                String strGraphFilePath = strCurrentProjectSaveDirectory + strGraphTitle ; // The output file will not have the extension of the file as its determined by the graph
                String strGraphRawInputFile = grpCurrent.getGraphFile();
                
                // System.out.println("ProjectSerialization.Write(): GraphID : "+intCurrentGraphID+" GraphFile: "+strGraphFilePath+" Title: "+strGraphTitle);
                // Write the Graph File
                try{
                    FileUtils.forceDelete(new File(strGraphFilePath+extension));
                }catch(Exception e){
                    System.out.println("ProjectSerialization.Write() - No Existing File with current file name, saving the file : "+strGraphTitle);
                    //e.printStackTrace();
                    int ErrorCode = -1000201;
                    //TODO deal with error codes and exception - show appropraite message to user in UI
                }
                MeerkatWriter mWriter = new MeerkatWriter();
                String strReturnGraphFile  = mWriter.write(grpCurrent, strGraphFilePath);
                
                if (strReturnGraphFile == null) {
                    intErrorCode =  -1000201 ;
                    // System.out.println("ProjectSerialization.Write(): Graph File is null: "+intErrorCode);
                    return intErrorCode ;
                } else {
                    bw.write("\nGraph"+pstrProjectFileDelimiter
                        + strGraphTitle + pstrGraphListDelimiter
                        + Utilities.getFileNameWithExtension(strReturnGraphFile) + pstrGraphListDelimiter
                        + strGraphRawInputFile);
                }
            }
            
            // Writing the list of textual files that are included in the project            
            for (Integer intTextualID : psetTextualIDs) {
                
                try {
                    
                    ITextualNetwork currentTextualNetwork = BIZInstance.getProject(pintProjectID).getTextualNetwork(intTextualID);
                    
                    // First Save the Text Files                
                    String strFileName = Utilities.getFileNameWithExtension(currentTextualNetwork.getFile());
                    System.out.println("ProjectSerialization.Write(): FileName: "+strFileName);
                    File flSource = new File(currentTextualNetwork.getFile());
                    System.out.println("ProjectSerialization.Write(): Directory & FileName: "+strCurrentProjectSaveDirectory+strFileName);
                    File flDestination = new File(strCurrentProjectSaveDirectory+strFileName);
                    System.out.println("ProjectSerialization.Write(): Source: "+flSource.getAbsolutePath()+"\n\tDestination: "+flDestination.getAbsolutePath());
                    FileUtils.copyFile(flSource, flDestination);

                    bw.write("\nTextual" + pstrProjectFileDelimiter
                                + strFileName + pstrProjectFileDelimiter
                                + currentTextualNetwork.getFile());
                    
                    
                } catch (Exception ex) {
                    System.out.println("ProjectSerialization.Save(): EXCEPTION: Not able to write the Textual Graph");
                    ex.printStackTrace();
                    intErrorCode = -1000202 ;
                    //return intErrorCode ;
                }
            }
                        
            // Write all the existing graphs 
            // System.out.println("ProjectSerialization.Save(): Project File "+pstrProjectName+"written Successfully"); // #Debug
             
            bw.close();
            fw.close();
                     
            return pintProjectID ;

        } catch (IOException ioe) {
            System.out.println("ProjectSerialization.Save(): EXCEPTION: ");
             ioe.printStackTrace();
             intErrorCode = -1000203 ;
             return intErrorCode ;
        }         
    }
    
    
    
    
    public static int WriteMPRJFileOnly(
              String pstrProjectDirectory
            , String pstrProjectName
            , String pstrProjectExtension
            , int pintProjectID
            
            , Set<Integer> psetGraphIDs            
            , Set<Integer> psetTextualIDs
            
            , List<String> plststrInputRawFiles
            , String pstrProjectFileDelimiter
            , String pstrGraphListDelimiter) {
        
        BufferedWriter bw = null;
        
        int intErrorCode = -1000200 ;
        // System.out.println("ProjectWriter.Save(): File Path : "+pstrProjectDirectory+pstrProjectName+pstrProjectExtension);
        try {       
            
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
            
            String strRootDirectory = pstrProjectDirectory + "../";
            
            // Create a file
            //String strProjectFilePath = strRootDirectory + pstrProjectName + pstrProjectExtension;

            Path projectFolderPath = Paths.get(pstrProjectDirectory);

            Path mprjDirectoryPath = projectFolderPath.getParent();


            String strProjectFilePath = mprjDirectoryPath.getFileName().toString() + File.separator + pstrProjectName + pstrProjectExtension;


            System.out.println("ProjectSerialization.WriteMPRJFileOnly(): Project Directory: "+pstrProjectDirectory + "  :: mprjfilepath = " + strProjectFilePath);

            
            
            
            File file = new File(strProjectFilePath);
            if (file.exists()) {
                file.delete(); // Delete for an existing file to overwrite the contents
            }
            System.out.println("ProjectSerialization.writeMPRJFILEONLY() : FilePath " + strProjectFilePath);
            file.createNewFile();
            
            // WRITING THE PROJECT FILE
            FileWriter fw = new FileWriter(file);             
            bw = new BufferedWriter(fw);             
             
            bw.write("ProjectName"+pstrProjectFileDelimiter + pstrProjectName + "\n"); // Writing Project Name
            bw.write("ProjectDirectory"+pstrProjectFileDelimiter + pstrProjectDirectory + "\n"); //Writing the Project Directory
            bw.write("SavedTime" + pstrProjectFileDelimiter + System.currentTimeMillis()+"\n"); //Writing Saved Time 
            /*
            System.out.println("ProjectSerialization.Write(): ProjectName"+pstrProjectFileDelimiter + pstrProjectName);
            System.out.println("ProjectSerialization.Write(): ProjectDirectory"+pstrProjectFileDelimiter + pstrProjectDirectory);
            System.out.println("ProjectSerialization.Write(): SavedTime" + pstrProjectFileDelimiter + System.currentTimeMillis());
            */
            /* Writing the list of Raw Data Files that were used to generate Graphs */
            String strContent = "RawDataFile" + pstrProjectFileDelimiter + " ";
            for(String strRawFile : plststrInputRawFiles) {
                strContent += strRawFile+",";
            }
            bw.write(strContent.substring(0, strContent.length()-1));   
            System.out.println("ProjectSerialization.WriteMPRJOnly(): "+strContent.substring(0, strContent.length()-1));

            
            // System.out.println("ProjectSerialization.Write(): Number of graphs in Project = "+psetGraphIDs.size());
            // Writing the list of graph files that are included in the project            
            for (int intCurrentGraphID : psetGraphIDs) {
                
                IDynamicGraph<IVertex, IEdge<IVertex>> grpCurrent = BIZInstance.getProject(pintProjectID).getGraph(intCurrentGraphID);
                String strGraphTitle = grpCurrent.getGraphTitle();
                
                String strGraphRawInputFile = grpCurrent.getGraphFile();
   
                bw.write("\nGraph"+pstrProjectFileDelimiter
                        + strGraphTitle + pstrGraphListDelimiter
                        + Utilities.getFileNameWithExtension(strGraphRawInputFile) + pstrGraphListDelimiter
                        + strGraphRawInputFile);
                /*System.out.println(" " + "\nGraph"+pstrProjectFileDelimiter
                        + strGraphTitle + pstrGraphListDelimiter
                        + Utilities.getFileNameWithExtension(strGraphRawInputFile) + pstrGraphListDelimiter
                        + strGraphRawInputFile);
                */
            }
            
            // Writing the list of textual files that are included in the project            
            for (Integer intTextualID : psetTextualIDs) { 
                try {
                    ITextualNetwork currentTextualNetwork = BIZInstance.getProject(pintProjectID).getTextualNetwork(intTextualID);
                    // Get the textFile name and write it in mprj file
                    String strFileName = Utilities.getFileNameWithExtension(currentTextualNetwork.getFile());
                    bw.write("\nTextual" + pstrProjectFileDelimiter
                                + strFileName + pstrProjectFileDelimiter
                                + currentTextualNetwork.getFile());
                        
                } catch (Exception ex) {
                    System.out.println("ProjectSerialization.WriteMPRJ(): EXCEPTION: Not able to write TextualGraph line in mprj file");
                    ex.printStackTrace();
                    intErrorCode = -1000203 ;
                    return intErrorCode ;
                }
            }         
            bw.close();
            fw.close();
                     
            return pintProjectID ;

        } catch (IOException ioe) {
            System.out.println("ProjectSerialization.WriteMPRJOnly(): EXCEPTION: ");
             ioe.printStackTrace();
             intErrorCode = -1000204 ;
             return intErrorCode ;
        }         
    }
}
