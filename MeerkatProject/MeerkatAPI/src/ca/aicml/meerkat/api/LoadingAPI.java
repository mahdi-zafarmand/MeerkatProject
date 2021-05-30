/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api;

import java.util.ArrayList;
import java.util.List;
import main.meerkat.MeerkatBIZ;



/**
 *  Class Name      : LoadingAPI
 *  Created Date    : 2015-07-16
 *  Description     : API through which the user action of Loading something invoke their respective implementation
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class LoadingAPI {
    /**
     *  Method Name     : LoadProject
     *  Created Date    : 2015-08-07
     *  Description     : Loads an empty project and returns the Project ID
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static int LoadProject() {
        int intProjectID = -1;
        try {
            MeerkatBIZ mktappBusiness = MeerkatBIZ.getMeerkatApplication();            
            intProjectID = mktappBusiness.createNewProject();
            System.out.println("LoadingAPI.LoadProject(): The Project ID created is "+intProjectID);
        } catch (Exception exception) {
            System.out.println("EXCEPTION in LoadingAPI.LoadProject():EXCEPTION: ");
            exception.printStackTrace();
        }            
        return intProjectID;
    }
    
    public static int CreateProject(String pstrProjectName, String pstrProjectDirectory) {
        int intProjectID = -1;
        try {
            MeerkatBIZ mktappBusiness = MeerkatBIZ.getMeerkatApplication();            
            intProjectID = mktappBusiness.createNewProject(pstrProjectName, pstrProjectDirectory);
            System.out.println("LoadingAPI.LoadProject(): The Project ID created is "+intProjectID);
        } catch (Exception exception) {
            System.out.println("EXCEPTION in LoadingAPI.LoadProject():EXCEPTION: ");
            exception.printStackTrace();
        }            
        return intProjectID;
    }
    
    
    public static int LoadGraphFile(int pintProjectID, 
            String pstrReaderClassID, 
            String pstrInputFilePath) {

        int intGraphID = -1;
        try {
            MeerkatBIZ mktappBusiness = MeerkatBIZ.getMeerkatApplication();
            System.out.println("LoadingAPI: "+pintProjectID+"\t"+pstrReaderClassID+"\t\n\t"+pstrInputFilePath);
            intGraphID = mktappBusiness.getProject(pintProjectID)
                    .loadFileFromOutsideMPRJ(pstrReaderClassID, pstrInputFilePath, 0);
        } catch (Exception ex) {
            System.out.println("EXCEPTION in LoadingAPI.LoadGraphFile(): EXCEPTION: ");
            ex.printStackTrace();
        }
        
        return intGraphID ;
    }
    
    public static int LoadTextualFile(int pintProjectID, 
            String pstrReaderClassID, 
            String pstrInputFilePath) {

        int intTextualNetworkId = -101;
        try {
            MeerkatBIZ mktappBusiness = MeerkatBIZ.getMeerkatApplication();
            intTextualNetworkId = mktappBusiness.getProject(pintProjectID)
                    .loadFile(pstrReaderClassID, pstrInputFilePath, 1);
            
        } catch(Exception ex) {
            System.out.println("EXCEPTION in LoadingAPI.LoadTextualFile()"
                    + ": Error Code "+intTextualNetworkId);
            ex.printStackTrace();
        }
        
        return intTextualNetworkId;
    }
}
