/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.stateserialization;

//import ca.aicml.meerkat.api.analysis.PolarityAnalysis;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *  Class Name      : SystemExit
 *  Created Date    : 2015-09-15
 *  Description     : Contains all the utilities that are to be executed when the system exits
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class SystemExit {
    
    
    /**
     *  Method Name     : RemoveAllFiles()
     *  Created Date    : 2015-09-14
     *  Description     : Removes all the files in a directory
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrDirectory
     *  @return boolean - true if all the files have been deleted
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private static boolean RemoveAllFiles(String pstrDirectory) {
        try {
            File flFolder = new File(pstrDirectory);
            File[] arrflAllFiles = flFolder.listFiles();        
            for (int i = 0; i < arrflAllFiles.length; i++) {
                if (arrflAllFiles[i].isFile()) {
                    Files.delete(arrflAllFiles[i].toPath());
                } 
            }
            return true;
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return false;
    }
    
    
    
    /**
     *  Method Name     : PolarityAnalysisFeedback()
     *  Created Date    : 2015-11-05
     *  Description     : Writes the feedback file before the application exits
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return boolean - true if the saving was successful
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    /*private static boolean PolarityAnalysisFeedback() {
        boolean blnReturnStatus = false ;
        try {
            blnReturnStatus = PolarityAnalysis.saveLexicon();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return blnReturnStatus;
    }

    */
    public static boolean Cleanup (String pstrRemoveFilesDir) {
        
        RemoveAllFiles(pstrRemoveFilesDir);
        //PolarityAnalysisFeedback();
        return false ;
    }
   
    
}
