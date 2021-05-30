/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.parser;


import ca.aicml.meerkat.api.LoadingAPI;
import config.ImportFileFilters;
import config.AppConfig;
import config.ErrorMsgsConfig;
import config.GraphConfig.GraphType;
import config.LangConfig;
import ui.dialogwindow.ErrorDialog;
import exception.MeerkatException;
import globalstate.GraphTab;
import globalstate.ProjectTab;
import globalstate.TextualTab;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 *  Class Name      : ProjectParser
 *  Created Date    : 2015-08-10
 *  Description     : To parse the Project file. T
 *                    The project file will be in the format
 *                  : --------------- START ----------------
 *                      ProjectName:Project1
 *                      LastSavedTime:1234567890
 *                      RawDataFiles:DataFile1,DataFile2,DataFile3
 *                      Graph:GraphTitle1,GraphFile1.x,RawDataFile1
 *                      Graph:GraphTitle2,GraphFile2.x,RawDataFile1
 *                      Graph:GraphTitle3,GraphFile3.x,RawDataFile2 
 *                  : ---------------- END -----------------
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-030-1      Talat           Moving the Project Parsing to MeerkatLogic Project
 *                                  Moved AssignKeyValue() method to MeerkatLogic
 * 
*/
public class ProjectParser {
    
    public static int BUFFER_SIZE = 99999999; 
    /**
     *  Method Name     : Parse
     *  Created Date    : 2015-08-10
     *  Description     : Parses the Meerkat Project File and returns a Project Tab
     *  Notes           : Reasons for parsing the UI layer and not in the Biz layer
     *                      a)  The UI layer is the layer which deals with attributes such as Project/Graph Name so 
     *                          that it can be displayed on the tabs
     *                      b)  The logic layer (or business layer) operates only based on the Project ID and does 
     *                          not maintain the Project / Graph Names
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pstrProjectFilePath : String
     *  @return ProjectTab
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-10-15      Talat           Adding the parsing of the TextualTabs
     * 
    */
    public static ProjectTab Parse (int pintProjectID, String pstrProjectFilePath) {        
        ProjectTab currentProject = null;
        try {            
            currentProject = new ProjectTab(pintProjectID);
            currentProject.setProjectDirectory(pstrProjectFilePath);
            
            InputStreamReader isReader = new InputStreamReader(new FileInputStream(pstrProjectFilePath));        	
            BufferedReader bfReader = new BufferedReader(isReader, BUFFER_SIZE);            
            String strCurrentLine = null;
            
            while((strCurrentLine = bfReader.readLine()) != null) {    
                strCurrentLine = strCurrentLine.trim();
                
                int intDelimiterIndex = strCurrentLine.indexOf(AppConfig.PROJECTFILE_DELIMITER);
                if (intDelimiterIndex == -1) {
                    throw new MeerkatException (ErrorMsgsConfig.ERROR_CORRUPTEDFILE);
                }
                String strKey = strCurrentLine.substring(0, intDelimiterIndex).trim();
                String strValue = strCurrentLine.substring(intDelimiterIndex+1, strCurrentLine.length()).trim();
                if (strKey.isEmpty() || strKey.length() == strCurrentLine.length()) {
                    throw new MeerkatException (ErrorMsgsConfig.ERROR_CORRUPTEDFILE);
                }
                AssignKeyValue(strKey, strValue, pintProjectID, currentProject); 
            }
            isReader.close();
            bfReader.close();
        } catch(IOException ex) {
            ex.printStackTrace();;
        } catch (MeerkatException ex) {
            ErrorDialog.Display(ex.getMessage());
        }
        
        return new ProjectTab(currentProject);                
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
    private static void AssignKeyValue (String pstrKey, String pstrValue, int pintProjectID, ProjectTab pProjectTab) {
        pstrKey = pstrKey.toLowerCase().trim();
        // System.out.println("Key: "+pstrKey+"\tValue: "+pstrValue);
        switch(pstrKey) {
            case "projectname" :
                pProjectTab.setProjectName(pstrValue);                
                break;
            case "savedtime" :
                pProjectTab.setSavedTime(Long.parseLong(pstrValue));
                break;
            case "projectdirectory" :
                pProjectTab.setProjectDirectory(pstrValue);
                break;
            case "rawdatafile" :
                if (!pstrValue.isEmpty()) {
                    String [] arrstrValues = pstrValue.split(",");
                    pProjectTab.setInputFilePath(Arrays.asList(arrstrValues));
                }
                break;
            case "graph" :
                if (!pstrValue.isEmpty()) {
                    String [] arrstrValues = pstrValue.split(",");
                    // Graph:GraphTitle2,GraphFile2.x,RawDataFile1
                    String strGraphTitle = arrstrValues[0];
                    String strGraphFile = arrstrValues[1];
                    String strRawDataFileForGraph = arrstrValues[2];
                    
                    // Get the reader ID for the class
                    String strReaderID = ImportFileFilters.getReaderID(strGraphFile);
                    GraphType enmGraphType = ImportFileFilters.getGraphType(strGraphFile);
                    
                    // Parse the GraphFile
                    strGraphFile = pProjectTab.getProjectDirectory()+strGraphFile;
                    int intGraphID = LoadingAPI.LoadGraphFile(pintProjectID, strReaderID, strGraphFile);
                    GraphTab graphTab = new GraphTab(pintProjectID, intGraphID, enmGraphType, strGraphTitle, strGraphFile, strRawDataFileForGraph);
                    pProjectTab.addGraphTab(graphTab);
                    
                    System.out.println("ProjectParser.AssignKeyValue(): FileName: "+strGraphFile+"\tProjectID: "+pintProjectID+"\tGraph: "+intGraphID);
                }
                break;
            
            case "textual" :
                if (!pstrValue.isEmpty()) {
                    // Textual:sampleTitle,sample.textual
                    String [] arrstrValues = pstrValue.split(",");
                    String strTextualTitle = arrstrValues[0];
                    String strTextualFile = arrstrValues[1];
                    
                    // Get the reader ID for the class
                    String strReaderID = ImportFileFilters.getReaderID(strTextualFile);
                    GraphType enmGraphType = ImportFileFilters.getGraphType(strTextualFile);
                                        
                    // Parse the GraphFile
                    strTextualFile = pProjectTab.getProjectDirectory()+strTextualFile;
                    
                    int intTextualTabID = LoadingAPI.LoadTextualFile(pintProjectID, strReaderID, strTextualFile);
                    
                    // #DEBUG
                    System.out.println("ProjectParser.AssignKeyValue(): FileName: "+strTextualFile
                            +"\tReader"+strReaderID+" with GraphType: "+enmGraphType+"\tProjectID: "
                            +pintProjectID+"\tTextTabID: "+intTextualTabID);
                    // #ENDDEBUG
                    
                    TextualTab txtTab = new TextualTab(pintProjectID,intTextualTabID, strTextualFile);
                    pProjectTab.addTextualTab(txtTab);
                    pProjectTab.setActiveTextualID(intTextualTabID);
                    
                    System.out.println("ProjectParser.AssignKeyValue(): Number of text tabs in Project ("+pProjectTab.getProjectID()+")"+pProjectTab.getTextualCount()); 
                }
                break;
        }
    }
}
