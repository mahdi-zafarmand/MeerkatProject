/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.serialize;

import static io.graph.reader.GraphReader.BUFFER_SIZE;
import io.utility.Utilities;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import main.meerkat.MeerkatBIZ;
import main.project.Project;

/**
 *  Class Name      : ProjectDeserialize
 *  Created Date    : 2016-03-01
 *  Description     : Functionalities to read the project files from the disk where they were saved
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class ProjectDeserialize {
    
    //to capture errors while reading graph/project files
    //Map<projectID, Map <GraphTitle, ErrorCode>>
    private static Map<Integer, Map<String, Integer>> projectGraphErrorMap = new HashMap<>();
    
    /**
     *  Method Name     : Read()
     *  Created Date    : 2016-03-03
     *  Description     : Reads the Projectfile which is in a specific format
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pstrProjectFilePath : String
     *  @param pstrProjectFileDelimiter : String
     *  @param pstrGraphFileDelimiter : String
     *  @return int - the project ID
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static int Read(int pintProjectID, String pstrProjectFilePath, String pstrProjectFileDelimiter, String pstrGraphFileDelimiter) {
        int intErrorCode = -1000100 ;
        int graphErrorCode = 101;
        projectGraphErrorMap.put(pintProjectID, new HashMap<>());
        try {
//            System.out.println("ProjectDeserialize.Read(): ProjectID: "+pintProjectID+" File: "+pstrProjectFilePath);
//            InputStreamReader isReader = new InputStreamReader(new FileInputStream(pstrProjectFilePath));
//            BufferedReader bfReader = new BufferedReader(isReader, BUFFER_SIZE);

            InputStream is = ProjectDeserialize.class.getClassLoader().getResourceAsStream(pstrProjectFilePath);
            BufferedReader bfReader = new BufferedReader(new InputStreamReader(is), BUFFER_SIZE);
            String strCurrentLine = null;
            
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication() ;
            Project prjCurrent = BIZInstance.getProject(pintProjectID) ;
            
            while((strCurrentLine = bfReader.readLine()) != null) {
                strCurrentLine = strCurrentLine.trim();
                int intDelimiterIndex = strCurrentLine.indexOf(pstrProjectFileDelimiter);
                if (intDelimiterIndex == -1) {
                    intErrorCode = -1000101 ;
                    return intErrorCode ;
                }
                String strKey = strCurrentLine.substring(0, intDelimiterIndex).trim();
                String strValue = strCurrentLine.substring(intDelimiterIndex+1, strCurrentLine.length()).trim();
                if (strKey.isEmpty() || strKey.length() == strCurrentLine.length()) {
                    intErrorCode = -1000102 ;
                    return intErrorCode ;
                }
                // System.out.println("ProjectDeserialize.Read(): Key: "+strKey+" Value: "+strValue);
                int errorCode = AssignKeyValue(strKey, strValue, pintProjectID, prjCurrent); 
                
                if(errorCode==-201){
                    graphErrorCode=errorCode;
                    projectGraphErrorMap.get(pintProjectID).put(strValue.split(",")[0], graphErrorCode);
                }
            }
            is.close();
            bfReader.close();
            
            // fill mapsMPRJ for graph and textual network - these maps only contain graphs which are in
            // mprj file
            prjCurrent.fillMPRJMaps();
            
            
            System.out.println("ProjectDeserialize.Read(): Return Value Error Code: "+intErrorCode);
            
            if(graphErrorCode==-201)
                return graphErrorCode;
            else
                return pintProjectID;
        } catch (Exception ex) {
            intErrorCode = -1000110 ;
//            System.out.println("ProjectDeserialize.Read(): EXCEPTION");
            ex.printStackTrace();
            return intErrorCode ;
        }
    }

    
    /**
     *  Method Name     : AssignKeyValue
     *  Created Date    : 2015-08-10
     *  Description     : Checks for the Key Value pair and assigns to the corresponding features in ProjectTab
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrKey : String
     *  @param pstrValue : String
     *  @param pintProjectID : int
     *  @param pProjectTab : ProjectTab
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private static int AssignKeyValue (String pstrKey, String pstrValue, int pintProjectID, Project pProject) {
        int ErrorCode = 101;
        try {
            pstrKey = pstrKey.toLowerCase().trim();
            // System.out.println("Key: "+pstrKey+"\tValue: "+pstrValue);
            switch(pstrKey) {
                case "projectname" :
                    pProject.setProjectName(pstrValue);                
                    break;
                case "savedtime" :
                    pProject.setSavedTime(Long.parseLong(pstrValue));
                    break;
                case "projectdirectory" :
                    pProject.setProjectDirectory(pstrValue);
                    break;
                case "rawdatafile" :
                    if (!pstrValue.isEmpty()) {
                        String [] arrstrValues = pstrValue.split(",");
                        pProject.setRawDataFiles(Arrays.asList(arrstrValues));
                    }
                    break;
                case "graph" :
                    if (!pstrValue.isEmpty()) {
                        String [] arrstrValues = pstrValue.split(",");
                        // Graph:GraphTitle2,GraphFile2.x,RawDataFile1
                        String strGraphTitle = arrstrValues[0];
                        String strGraphFile = arrstrValues[1];

                        // Get the reader ID for the class which is the same as extension
                        // String strReaderID = ConfigLoader.getClassMapping(MeerkatSystem.READERS_TAG, Utilities.getFileExtension(strGraphFile)) ;
                        String strReaderID = Utilities.getFileExtension(strGraphFile) ;
                         System.out.println("ProjectDeserialize.Read(): ReaderID: "+strReaderID);
                        
                        // Parse the GraphFile
                        strGraphFile = pProject.getProjectDirectory()+strGraphFile;

                        int intGraphID = pProject.loadFile(strReaderID, strGraphFile, 0) ;
                        
                        System.out.println("ProjectDeserialize.Read(): GraphID"+intGraphID);
                        
                        // Set the title of the Graph Network since the title might not be related to the input data (or graph) file for a valid GraphID
                        if(intGraphID>=0)
                            pProject.getGraph(intGraphID).setGraphTitle(strGraphTitle);
                        else
                            ErrorCode=-201;
                        
                        System.out.println("ProjectDeserialize.AssignKeyValue(): FileName: "+strGraphFile+"\tProjectID: "+pintProjectID+"\tGraphID: "+intGraphID);
                    }
                    break;
                /*    
                case "textual" :
                    if (!pstrValue.isEmpty()) {
                        // Textual:sampleTitle,sample.textual
                        String [] arrstrValues = pstrValue.split(",");
                        String strTextualTitle = arrstrValues[0];
                        String strTextualFile = arrstrValues[1];

                        // Get the reader ID for the class
                        String strReaderID = Utilities.getFileExtension(strTextualFile) ;
                        // String strReaderID = ConfigLoader.getClassMapping(MeerkatSystem.READERS_TAG, Utilities.getFileExtension(strTextualFile)) ;

                        // Parse the GraphFile
                        strTextualFile = pProject.getProjectDirectory() + strTextualFile;

                        int intTextualTabID = pProject.loadFile(strReaderID, strTextualFile, 1) ;
                        
                        // System.out.println("ProjectDeserialize.AssignKeyValue(): "+intTextualTabID);
                        
                        // Set the Title of Textual Network since the tile might not be a part of the input file
                        pProject.getTextualNetwork(pintProjectID).setTitle(strTextualTitle);                        
                    }
                    break;
                */
                default:
                    break;
            }
            
        } catch (Exception ex) {
            System.out.println("ProjectDeserialize.AssignKeyValue(): EXCEPTION ");
            ex.printStackTrace();
        }
        
        return ErrorCode;
    }
    
    /**
     *  Method Name     : getGraphErrorMap
     *  Created Date    : 2017-08-11
     *  Description     : returns all graphs with errors for a given project.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pintProjectID : int
     *  @return 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *   
     * 
    */
    
    public static Map<String, Integer> getGraphErrorMap(int pintProjectID){
        return projectGraphErrorMap.get(pintProjectID);
    }
}
