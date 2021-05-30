/*
 * meerkat@aicml june 2015
 */
package ca.aicml.meerkat.api;


import config.MeerkatSystem;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.text.classinterface.ITextualNetwork;
import io.serialize.ProjectDeserialize;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import main.meerkat.MeerkatBIZ;
import main.project.Project;

/**
 *  Class Name      : ProjectAPI
 *  Created Date    : 2015-08-xx
 *  Description     : 
 *  Version         : 1.0
 *  @author         : Afra Abnar
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class ProjectAPI {
    
    /**
     * newProject creates a new Meerkat project from a graph 
     * (Network) file (.pajek, .csv, .graphml, .gml, .json) or 
     * a TextualNetwork from a text-based file (including the content
     * of a discussion thread, a set of tweets, etc.).
     * In case of a Network the graph stored in the file will immediately
     * be visualized, however in case of a TextualNetwork, the Three Tree of the
     * textual content will be shown but no graph will be there. The graph will
     * be generated and thus visualized upon users request for it.
     * @param filename 
     * 
     * @return boolean 
     */
    public static int newProject(String filename) {
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        int prjId = meerkatApp.createNewProject();
        
        return prjId;
    }
    /**
     * loadProject loads a previously stored Meerkat project from 
     * file.
     * @param projectConfig
     * @param filename 
     * 
     * @return boolean 
     */
    public static int loadProject(String projectConfig, String filename) {
        
        return 1;
    }
    
    /**
     * saveProject save the active project as a whole (all graphs) 
     * or a subset of it (some of the generated graphs) upon users
     * choice.
     * @param projectConfig
     *          includes the reference to the project, which parts to save, ...)
     * @param filename 
     * 
     * @return boolean 
     */
    
    /**
     *  Method Name     : saveProject()
     *  Created Date    : 2016-03-04
     *  Description     : saveProject save the active project as a whole (all graphs) 
                            or a subset of it (some of the generated graphs)
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pstrProjectExtension : String
     *  @param pstrProjectDelimiter : String
     *  @param pstrGraphDelimiter : String
     *  @return int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static int saveProject(
              int pintProjectID
            , String pstrProjectExtension
            , String pstrProjectDelimiter
            , String pstrGraphDelimiter) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        // System.out.println("ProjectAPI.saveProject(): ProjectID: "+pintProjectID+" Extension: "+pstrProjectExtension+" Delimiter: "+pstrProjectDelimiter+" "+pstrGraphDelimiter);
        int intErrorCode = BIZInstance.getProject(pintProjectID).Save(pstrProjectExtension, pstrProjectDelimiter, pstrGraphDelimiter) ;
        
        return intErrorCode;
    }
    
        /**
     *  Method Name     : saveProject()
     *  Created Date    : 2017-04-13
     *  Description     : saveProject save the active project as a whole (all graphs), writes graphs according to new location of vertices 
                            
     *  Version         : 1.0
     *  @author         : Abhi
     * 
     *  @param pintProjectID : int
     *  @param pmapProjectsAllVerticesLocation : Map<Integer, Map<Integer, Map<Integer, Double[]>>>
     *  @param pstrProjectExtension : String
     *  @param pstrProjectDelimiter : String
     *  @param pstrGraphDelimiter : String
     *  @return intErrorCode
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static int saveProject(
              int pintProjectID
            , Map<Integer, Map<Integer, Map<Integer, Double[]>>> pmapProjectsAllVerticesLocation
            , String pstrProjectExtension
            , String pstrProjectDelimiter
            , String pstrGraphDelimiter) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        // System.out.println("ProjectAPI.saveProject(): ProjectID: "+pintProjectID+" Extension: "+pstrProjectExtension+" Delimiter: "+pstrProjectDelimiter+" "+pstrGraphDelimiter);
        int intErrorCode = BIZInstance.getProject(pintProjectID).Save(pmapProjectsAllVerticesLocation, pstrProjectExtension, pstrProjectDelimiter, pstrGraphDelimiter) ;
        
        return intErrorCode;
    }
    
    
    
    /**
     * closeProject closes the referenced project associated with the
     * project filename on the hard disk (only when the project is
     * previously saved)
     * @param projectConfig
     * @param filename 
     * 
     * @return boolean 
     */
    public static boolean closeProject(String projectConfig, String filename) {
        
        return true;
    }
    
        /**
     * removeProject removes the referenced project associated with the
     * projectId
     * @param pintProjectId
     *  
     * 
     * @return boolean 
     */
    public static void closeProject(int pintProjectId) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        BIZInstance.closeProject(pintProjectId);
        
    }
    
    public static void deleteProject(int ProjectId, String pstrProjectExtension){
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        BIZInstance.deleteProject(ProjectId, pstrProjectExtension);
        
    }
    
    
    /**
     *  Method Name     : getInputFileName()
     *  Created Date    : 2015-07-20
     *  Description     : Returns the input file of the current graph that is being displayed
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrGraphID: String
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */

    public static String getInputFileName (String pstrGraphID) {
        String strInputFileName = new String();
        // TODO
        return strInputFileName;
    }
    
    /**
     *  Method Name     : ()
     *  Created Date    : 2016-03-03
     *  Description     : 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param 
     *  @param 
     *  @return 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static boolean setInputFileName(String pstrGraphID, String pstrInputFileName) {
        boolean blnSuccess = false ;
        // TODO
        return blnSuccess;
    }
    
    
    /** 
     *  Method Name     : parseProjectFile
     *  Created Date    : 2016-03-02
     *  Description     : Parsing the project file and returns the ProjectID of the Project that is added
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pstrProjectFilePath : String
     *  @param pstrProjectFileDelimiter : String
     *  @param pstrGraphFileDelimiter : String
     *  @return int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static int parseProjectFile(int pintProjectID, String pstrProjectFilePath, String pstrProjectFileDelimiter, String pstrGraphFileDelimiter) {        
        int intErrorCode = ProjectDeserialize.Read(pintProjectID, pstrProjectFilePath, pstrProjectFileDelimiter, pstrGraphFileDelimiter) ;        
        System.out.println("ProjectAPI.parseProjectFile(): "+intErrorCode);
        return intErrorCode;
    }
    
    /** 
     *  Method Name     : getProjectGraphErrors
     *  Created Date    : 2017-10-11
     *  Description     : gets all graphs with error codes for a given project.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pintProjectID : int
     *  @return 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description 
     * 
     * 
    */
    public static Map<String, Integer> getProjectGraphErrors(int pintProjectID) {        
        return ProjectDeserialize.getGraphErrorMap(pintProjectID);
    }
    
    /**
     *  Method Name     : getProjectName()
     *  Created Date    : 2016-03-03
     *  Description     : Retrieves the Project Name
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static String getProjectName(int pintProjectID) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication() ;
        return BIZInstance.getProject(pintProjectID).getProjectName() ;
    }
    
    /**
     *  Method Name     : getProjectDirectory()
     *  Created Date    : 2016-03-03
     *  Description     : Retrieves the list of Project Directory of the Project
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static String getProjectDirectory (int pintProjectID) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication() ;
        return BIZInstance.getProject(pintProjectID).getProjectDirectory() ;
    }
    
    /**
     *  Method Name     : getProjectRawFiles()
     *  Created Date    : 2016-03-03
     *  Description     : Retrieves the list of Raw data files that were used to build this project
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID
     *  @return List<String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static List<String> getProjectRawFiles (int pintProjectID) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication() ;
        return BIZInstance.getProject(pintProjectID).getRawDataFiles() ;
    }
    
    /**
     *  Method Name     : getProjectIconPath()
     *  Created Date    : 2016-03-03
     *  Description     : Retrieves the Icon Path of the Project
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static String getProjectIconPath (int pintProjectID) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication() ;
        return BIZInstance.getProject(pintProjectID).getProjectIconPath() ;
    }
    
    /**
     *  Method Name     : getProjectSavedTime()
     *  Created Date    : 2016-03-03
     *  Description     : Retrieves the Last Time the Project was saved in milliseconds
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @return long
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static long getProjectSavedTime (int pintProjectID) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication() ;
        return BIZInstance.getProject(pintProjectID).getSavedTime() ;
    }
    
    /**
     *  Method Name     : getTextualNetworkDetails()
     *  Created Date    : 2016-03-03
     *  Description     : Retrieves the Textual Network Details of all the graphs available in the project
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pstrDelimiter : String
     *  @return List<String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static List<String> getTextualNetworkDetails (int pintProjectID, String pstrDelimiter) {
        
        List<String> lststrTextNetworkDetails = new ArrayList<>() ;
        
        try {
            // Create an instance of the project to fetch the textual networks
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication() ;        
            Project prjCurrent = BIZInstance.getProject(pintProjectID) ;

            // For all the Textual Networks Available in the current project
            for (int intTextualNetwork : prjCurrent.getAllTextNetworkIDs()) {
                ITextualNetwork iTextualNetwork = prjCurrent.getTextualNetwork(intTextualNetwork) ;                
                String strTextualFile = iTextualNetwork.getFile() ;
                lststrTextNetworkDetails.add(
                        pintProjectID + pstrDelimiter
                        + intTextualNetwork + pstrDelimiter
                        + strTextualFile
                );
            }
        } catch (Exception ex) {
            // System.out.println("ProjectAPI.getTextualNetworkDetails(): ");
            ex.printStackTrace(); 
        } 
        return lststrTextNetworkDetails;
    }
    
    /**
     *  Method Name     : getGraphDetails()
     *  Created Date    : 2016-03-03
     *  Description     : Retrieves the Graph Details of all the graphs available in the project
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pstrDelimiter : String
     *  @return List<String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static List<String> getGraphDetails (int pintProjectID, String pstrDelimiter) {
        
        List<String> lststrGraphDetails = null  ;
        
        try {
            
            lststrGraphDetails = new ArrayList<>();
            // Create an instance of the project to fetch the graphs
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication() ;        
            Project prjCurrent = BIZInstance.getProject(pintProjectID) ;

            // For all the Graphs Available in the current project
            for (int intGraphID : prjCurrent.getAllGraphIDs()) {
                IDynamicGraph<IVertex, IEdge<IVertex>> grpCurrent = prjCurrent.getGraph(intGraphID) ;


                // (pintProjectID, intGraphID, enmGraphType, strGraphTitle, strGraphFile, strRawDataFileForGraph);
                int intGraphType = 0 ;
                String strGraphTitle = (grpCurrent.getGraphTitle() != null ? grpCurrent.getGraphTitle() : "") ; // Making it an empty string in case the value returned is null
                String strGraphFile = (grpCurrent.getGraphFile() != null ? grpCurrent.getGraphFile() : "") ; // Making it an empty string in case the value returned is null
                
                // #DEBUG
                /*
                System.out.println("ProjectAPI.getGraphDetails(): pintProjectID: "+pintProjectID);
                System.out.println("ProjectAPI.getGraphDetails(): intGraphID: "+intGraphID);
                System.out.println("ProjectAPI.getGraphDetails(): intGraphType: "+intGraphType);
                System.out.println("ProjectAPI.getGraphDetails(): strGraphTitle: "+strGraphTitle);
                System.out.println("ProjectAPI.getGraphDetails(): strGraphFile: "+strGraphFile);
                */
                // #ENDDEBUG
                lststrGraphDetails.add(
                                  pintProjectID + pstrDelimiter 
                                + intGraphID + pstrDelimiter 
                                + intGraphType + pstrDelimiter 
                                + strGraphTitle + pstrDelimiter
                                + strGraphFile + pstrDelimiter 
                                + strGraphFile + pstrDelimiter
                );

            }
            return lststrGraphDetails ;
        } catch (Exception ex) {
            System.out.println("ProjectAPI.getGraphDetails(): ");
            ex.printStackTrace(); 
            return null ;
        } 
    }
    
    public static String deleteGraph(int pintProjectId 
            , int pintGraphId
            , String pstrProjectExtension
            , String pstrProjectDelimiter
            , String pstrGraphDelimiter){
        
        String messageResturned = "";
        try {
            
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();        
            Project prj = BIZInstance.getProject(pintProjectId);
            messageResturned = prj.deleteGraph(pintGraphId, pstrProjectExtension, pstrProjectDelimiter, pstrGraphDelimiter);
            
        }catch(Exception e){
            e.printStackTrace();
            messageResturned = e.toString();
        }
        return messageResturned;
        
    }
    
    public static Boolean graphExistsWithName(int pintProjectId, String pstrGraphName){
        try {
            
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();        
            Project prj = BIZInstance.getProject(pintProjectId);
            if(!prj.containsGraphName(pstrGraphName)){
                return false;
            }
            
        }catch(Exception e){
            e.printStackTrace();
            return true;
        }
        return true;
    }
    
    public static String getGraphNameProjectNameRegex(){
  
      MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
      return BIZInstance.getGraphNameProjectNameRegex();
      
    }
    
    /**
     * Returns the Resources Directory in a Project
     * @return String
     * @author Talat
     * @since 2018-02-07
     */
    public static String getProjectResourcesDir(){
        return MeerkatSystem.PROJECT_RESOURCES_DIR;
    }
    
    /**
     * Returns the Icons Directory in a Project
     * @return String
     * @author Talat
     * @since 2018-02-07
     */
    public static String getProjectIconsDir(){
        return MeerkatSystem.PROJECT_ICONS_DIR;
    }

}
