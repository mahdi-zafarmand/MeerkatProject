/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meerkat;

import config.AppConfig;
import config.GraphConfig.GraphType;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class Name      : Utilities
 *  Created Date    : 2015-06-xx
 *  Description     : General Utilities required
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-09-11      Talat           Moved Debug_PrintMenu() to MenuBarUtilities
 * 
*/
public class Utilities {
    
    
    /**
     *  Method Name     : ProjectExists
     *  Created Date    : 2015-06-xx
     *  Description     : Returns true if the project with the same name exists
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrProjectNameWithExtension : String
     *  @param pstrProjectDirectory : String
     *  @return boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static boolean ProjectExists(String pstrProjectNameWithExtension, String pstrProjectDirectory) {        
        File folder = new File(pstrProjectDirectory);
        File[] listOfFiles = folder.listFiles();
        for (File currentFile : listOfFiles) {
            // System.out.println("Utilities.ProjectExists(): Project: "+pstrProjectNameWithExtension+" Found: "+currentFile.getName()); // #DEBUG
            if (pstrProjectNameWithExtension.equalsIgnoreCase(currentFile.getName())) {
                return true;
            }
        }
        return false;
    }
        
    /**
     *  Method Name     : AvailableLanguages
     *  Created Date    : 2015-06-xx
     *  Description     : Returns a list of available languages available for the Meerkat application
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return List<String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static List<String> AvailableLanguages() {
        List<String> lstLanguages = new ArrayList<>();
        File folder = new File(AppConfig.LANGUAGES_PATH);
        //File folder = new File( Utilities.getClass().getResourceAsStream(AppConfig.LANGUAGES_PATH));
        File[] listOfFiles = folder.listFiles();			
        for (File file : listOfFiles) {
            if (file.isFile()) {
                lstLanguages.add(file.getName());
            }
        }        
        return lstLanguages;
    }   
    
    
    /**
     *  Method Name     : getFileNameWithoutExtention()
     *  Created Date    : 2016-01-17
     *  Description     : Extracts the file name from a full path without its extension
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrFileName : String
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static String getFileNameWithoutExtention(String pstrFileName) {
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
     *  Method Name     : getFilePathWithoutExtention()
     *  Created Date    : 2016-05-18
     *  Description     : Extracts the file path from a full path without its extension
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrFileName : String
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static String getFilePathWithoutExtention(String pstrFileName) {
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
        
        return pstrFileName.substring(0, intDotIndex);
    }
    
    /**
     *  Method Name     : getFileNameWithExtention()
     *  Created Date    : 2016-05-17
     *  Description     : Extracts only the File Name from the full path
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrFileName : String
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static String getFileNameWithExtention(String pstrFileName) {
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
     *  Method Name     : getFileExtention()
     *  Created Date    : 2016-01-17
     *  Description     : Extracts only the file extension from a file name or a full path
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrFileName : String
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */

    public static String getFileExtention(String pstrFileName) {
        if (pstrFileName.contains(".")) {
            return pstrFileName.substring(pstrFileName.lastIndexOf("."), pstrFileName.length());
        }
        return "";        
    }
    
    
    /**
     *  Method Name     : tryParseBoolean()
     *  Created Date    : 2016-01-17
     *  Description     : Checks if the string is a boolean or not
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrInput : String
     *  @return boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static boolean tryParseBoolean (String pstrInput) {
        try {
            Boolean.parseBoolean(pstrInput);
            return true ;
        } catch (Exception ex) {
            return false;
        }
    }
    
    /**
     *  Method Name     : tryParseDouble()
     *  Created Date    : 2016-01-17
     *  Description     : Checks if the string is a double or not
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrInput : String
     *  @return boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static boolean tryParseDouble (String pstrInput) {
        try {
            Double.parseDouble(pstrInput);
            return true ;
        } catch (Exception ex) {
            return false;
        }
    }
    
    /**
     *  Method Name     : tryParseLong()
     *  Created Date    : 2016-01-17
     *  Description     : Checks if the string is a long integer or not
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrInput : String
     *  @return boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static boolean tryParseLong (String pstrInput) {
        try {
            Long.parseLong(pstrInput);
            return true ;
        } catch (Exception ex) {
            return false;
        }
    }
    
    /**
     *  Method Name     : tryParseInteger()
     *  Created Date    : 2016-01-17
     *  Description     : Checks if the string is an integer or not
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrInput : String
     *  @return boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static boolean tryParseInteger (String pstrInput) {
        try {
            Integer.parseInt(pstrInput);
            return true ;
        } catch (Exception ex) {
            return false;
        }
    }
    
    
    /**
     *  Method Name     : getGraphType()
     *  Created Date    : 2016-01-17
     *  Description     : Gets the Graph Type
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintGraphType : int
     *  @return GraphType
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static GraphType getGraphType (int pintGraphType) {
        
        GraphType enmGraphType  = null;
        
        switch(pintGraphType) {
            case 0 :
                enmGraphType = enmGraphType.GRAPH;
                break;
            case 1: 
                enmGraphType = enmGraphType.TEXTUALGRAPH;
                break;
            case 2: 
                enmGraphType = enmGraphType.TWITTERGRAPH;
                break;
            default : 
                enmGraphType = enmGraphType.NOSUCHGRAPH ;
                break ;
        }
        
        return enmGraphType ;
    }
}
