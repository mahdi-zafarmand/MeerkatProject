/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.net.URL;
import java.security.*;
import java.util.stream.*;

/**
 *  Class Name      : Utilities
 *  Created Date    : 2016-02-29
 *  Description     : A list of utilities that are to manipulates string and filenames
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class Utilities {
    
    /**
     *  Method Name     : getFileNameWithExtension()
  Created Date    : 2016-02-29
  Description     : Gets only the filename in extension
  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrFileName
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static String getFileNameWithExtension(String pstrFileName) {
        int intIndex1 = -1;
        int intIndex2 = -1;
        int intDotIndex = 0;
        if (pstrFileName.contains("/")) {
            intIndex1 = pstrFileName.lastIndexOf("/");
        }
        if (pstrFileName.contains("\\")) {
            intIndex2 = pstrFileName.lastIndexOf("\\");
        }
        
        intDotIndex = pstrFileName.length();
        
        return pstrFileName.substring((intIndex1>intIndex2?intIndex1:intIndex2)+1, intDotIndex);
    }
    
    /**
     *  Method Name     : getFileExtension()
  Created Date    : 2016-03-01
  Description     : Gets only the file extension
  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrFileName
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static String getFileExtension(String pstrFileName) {
        if (pstrFileName.contains(".")) {
            return pstrFileName.substring(pstrFileName.lastIndexOf("."), pstrFileName.length());
        }
        return "";
    }
    
    
    /**
     *  Method Name     : getFileNameWithoutExtension()
  Created Date    : 2016-03-01
  Description     : Gets only the file name without the extension and without the path
  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrFileName
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static String getFileNameWithoutExtension(String pstrFileName) {
        int intIndex1 = -1;
        int intIndex2 = -1;
        int intDotIndex = 0;
        if (pstrFileName.contains("/")) {
            intIndex1 = pstrFileName.lastIndexOf("/");
        }
        if (pstrFileName.contains("\\")) {
            intIndex2 = pstrFileName.lastIndexOf("//");
        }
        if (pstrFileName.contains(".")) {
            intDotIndex = pstrFileName.lastIndexOf(".");
        } else {
            intDotIndex = pstrFileName.length();
        }
        
        return pstrFileName.substring((intIndex1>intIndex2?intIndex1:intIndex2)+1, intDotIndex);
    }
    
    /**
     * Creates/Replaces a directory based on the parameter replaceExisting
     * @param pstrPath
     * @param replaceExisting
     * @return true if the operation is successful, else false
     * @author Talat
     * @since 2018-02-06
     */
    public static boolean createDirectory(String pstrPath, boolean replaceExisting){
        File file = new File(pstrPath);
        try {
            if(replaceExisting){
                if(file.exists()){
                   file.delete();
                }
                file.mkdir();
            } else {
                if(!file.exists()){
                    file.mkdir();
                }
            }
            return true;
        } catch(Exception ex){
            System.out.println("EXCEPTION in Utilities.createDirectory()");
            ex.printStackTrace();
            return false;
        }
    }
    
    /**
     * Retrieves only the files in a directory
     * @param pstrDir
     * @return List<String>
     * @since 2018-02-07
     * @author Sankalp/Talat
     */
    public static List<String> getAllFilesInDirectory(String pstrDir){
//        MAHDI: The old code is commented and the new code is written and activated below that.
//        The number of files added to the "lstFiles" is different, the number for new code matches
//        the real number of files in the corresponding directory, but the number of files in the old
//        code is much more than that! (WHY?)
        
        List<String> lstFiles = new ArrayList<>();
    
//        try{
//            File folder = new File(pstrDir);
//            System.out.println("MAHDI: TEST1: " + folder.getPath());
//            System.out.println("MAHDI: TEST2: " + folder.getAbsolutePath());
//            File[] listOfFiles = folder.listFiles();
//            for (int i = 0; i < listOfFiles.length; i++) {
//                if (listOfFiles[i].isFile()) {
//                    lstFiles.add(listOfFiles[i].getPath());
//                }
//            }
//            lstFiles = lstFiles.stream().sorted().collect(Collectors.toList());
//            for(String s : lstFiles){
//                System.out.println(s);
//            }
//            System.out.println("MAHDI: There are " + lstFiles.size() + " are loaded.");
//            System.out.println("-----------------------------");
//
//        }catch(Exception ex){
//            System.out.println(ex);
//        }
        
//        List<File> filesList = new ArrayList<File>();
//        for(final String path : pstrDir.split(File.pathSeparator)){
//            final File file = new File(pstrDir);
//            System.out.println(file.getPath());
//            if(!file.isDirectory()){
//                filesList.add(file);
//            }
//        }
//        System.out.println("MAHDI: TEST " + lstFiles.size());
        return lstFiles;
    }
}
