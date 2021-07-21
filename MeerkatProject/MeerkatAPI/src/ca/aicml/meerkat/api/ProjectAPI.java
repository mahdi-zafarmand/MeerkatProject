/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author mahdi
 */
public class ProjectAPI {
    public static int newProject(String filename) {
        MeerkatBIZ meerkatApp = MeerkatBIZ.getMeerkatApplication();
        int prjId = meerkatApp.createNewProject();
        return prjId;
    }
    
    public static int loadProject(String projectConfig, String filename) {    
        return 1;
    }
    
    public static int saveProject(int pintProjectID, String pstrProjectExtension,
            String pstrProjectDelimiter, String pstrGraphDelimiter) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        int intErrorCode = BIZInstance.getProject(pintProjectID).Save(pstrProjectExtension, pstrProjectDelimiter, pstrGraphDelimiter);        
        return intErrorCode;
    }
    
    public static int saveProject(int pintProjectID, Map<Integer, Map<Integer, Map<Integer, Double[]>>> pmapProjectsAllVerticesLocation,
            String pstrProjectExtension, String pstrProjectDelimiter, String pstrGraphDelimiter) {
        
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        int intErrorCode = BIZInstance.getProject(pintProjectID).Save(pmapProjectsAllVerticesLocation, pstrProjectExtension, pstrProjectDelimiter, pstrGraphDelimiter) ;        
        return intErrorCode;
    }
    
    public static boolean closeProject(String projectConfig, String filename) {
        return true;
    }
    
    public static void closeProject(int pintProjectId) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        BIZInstance.closeProject(pintProjectId);
    }
    
    public static void deleteProject(int ProjectId, String pstrProjectExtension){
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
        BIZInstance.deleteProject(ProjectId, pstrProjectExtension);
    }
    
    public static String getInputFileName (String pstrGraphID) {
        String strInputFileName = new String();
        // TODO
        return strInputFileName;
    }
    
    public static boolean setInputFileName(String pstrGraphID, String pstrInputFileName) {
        boolean blnSuccess = false ;
        // TODO
        return blnSuccess;
    }
    
    public static int parseProjectFile(int pintProjectID, String pstrProjectFilePath, String pstrProjectFileDelimiter, String pstrGraphFileDelimiter) {        
        int intErrorCode = ProjectDeserialize.Read(pintProjectID, pstrProjectFilePath, pstrProjectFileDelimiter, pstrGraphFileDelimiter) ;        
        return intErrorCode;
    }
    
    public static Map<String, Integer> getProjectGraphErrors(int pintProjectID) {        
        return ProjectDeserialize.getGraphErrorMap(pintProjectID);
    }
    
    public static String getProjectName(int pintProjectID) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication() ;
        return BIZInstance.getProject(pintProjectID).getProjectName() ;
    }
    
    public static String getProjectDirectory (int pintProjectID) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication() ;
        return BIZInstance.getProject(pintProjectID).getProjectDirectory() ;
    }
    
    public static List<String> getProjectRawFiles (int pintProjectID) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication() ;
        return BIZInstance.getProject(pintProjectID).getRawDataFiles() ;
    }
    
    public static String getProjectIconPath (int pintProjectID) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication() ;
        return BIZInstance.getProject(pintProjectID).getProjectIconPath() ;
    }
    
    public static long getProjectSavedTime (int pintProjectID) {
        MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication() ;
        return BIZInstance.getProject(pintProjectID).getSavedTime() ;
    }
    
    public static List<String> getTextualNetworkDetails (int pintProjectID, String pstrDelimiter) {
        List<String> lststrTextNetworkDetails = new ArrayList<>() ;
        try {
            // Create an instance of the project to fetch the textual networks
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication() ;        
            Project prjCurrent = BIZInstance.getProject(pintProjectID) ;

            prjCurrent.getAllTextNetworkIDs().forEach((intTextualNetwork) -> {
                ITextualNetwork iTextualNetwork = prjCurrent.getTextualNetwork(intTextualNetwork) ;                
                String strTextualFile = iTextualNetwork.getFile() ;
                lststrTextNetworkDetails.add(pstrDelimiter + intTextualNetwork
                        + pstrDelimiter + strTextualFile + pintProjectID);
            });
        } catch (Exception ex) {
            ex.printStackTrace(); 
        } 
        return lststrTextNetworkDetails;
    }
    
    public static List<String> getGraphDetails (int pintProjectID, String pstrDelimiter) {
        List<String> lststrGraphDetails = null  ;
        try {            
            lststrGraphDetails = new ArrayList<>();
            // Create an instance of the project to fetch the graphs
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication() ;        
            Project prjCurrent = BIZInstance.getProject(pintProjectID) ;

            for (int intGraphID : prjCurrent.getAllGraphIDs()) {
                IDynamicGraph<IVertex, IEdge<IVertex>> grpCurrent = prjCurrent.getGraph(intGraphID) ;
                int intGraphType = 0 ;
                String strGraphTitle = (grpCurrent.getGraphTitle() != null ? grpCurrent.getGraphTitle() : "") ; // Making it an empty string in case the value returned is null
                String strGraphFile = (grpCurrent.getGraphFile() != null ? grpCurrent.getGraphFile() : "") ; // Making it an empty string in case the value returned is null
                
                lststrGraphDetails.add(pintProjectID + pstrDelimiter + intGraphID
                                + pstrDelimiter + intGraphType + pstrDelimiter 
                                + strGraphTitle + pstrDelimiter + strGraphFile
                                + pstrDelimiter + strGraphFile + pstrDelimiter);
            }
            return lststrGraphDetails ;
        } catch (Exception ex) {
            System.out.println("ProjectAPI.getGraphDetails(): ");
            ex.printStackTrace(); 
            return null ;
        }
    }
    
    public static String deleteGraph(int pintProjectId, int pintGraphId, String pstrProjectExtension,
            String pstrProjectDelimiter, String pstrGraphDelimiter){
        
        String messageResturned = "";
        try {
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();        
            Project prj = BIZInstance.getProject(pintProjectId);
            messageResturned = prj.deleteGraph(pintGraphId, pstrProjectExtension, 
                    pstrProjectDelimiter, pstrGraphDelimiter);
            
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
    
    public static String getProjectResourcesDir(){
        return MeerkatSystem.PROJECT_RESOURCES_DIR;
    }
    
    public static String getProjectIconsDir(){
        return MeerkatSystem.PROJECT_ICONS_DIR;
    }
}
