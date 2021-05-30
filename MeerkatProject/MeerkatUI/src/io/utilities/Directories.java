/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.utilities;

import java.io.File;

/**
 *  Class Name      : Directories
 *  Created Date    : 2016-01-11
 *  Description     : All the IO functionalities associated with Directories
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class Directories {
    
    
    /**
     *  Method Name     : Create()
     *  Created Date    : 2016-01-11
     *  Description     : 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrDirectoryPath : String
     *  @return boolean - true if the creation was successful
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static boolean Create (String pstrDirectoryPath) {
        
        // To store the return result - true if the directory was created successfully
        boolean blnResult = false;
        
        // Create a new directory
        File flNewDirectory = new File(pstrDirectoryPath);

        // if the directory does not exist, create it
        if (!flNewDirectory.exists()) {
                        
            try{
                flNewDirectory.mkdir();
                blnResult = true;
            } 
            catch(SecurityException se){
                //handle it
            }
        }
        
        return blnResult;
    }
}
