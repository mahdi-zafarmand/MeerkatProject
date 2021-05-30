/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import config.GraphConfig.GraphType;
import java.util.ArrayList;
import java.util.List;

   /**
     *  Class Name      : ImportFileFilters
     *  Created Date    : 2015-07-15
     *  Description     : Contains the methods to process the file filters
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *   
     * 
    */
public class ImportFileFilters {
    
    private static List<FileFilter> lstFilters ;
    
    /**
     *  Method Name     : getFileFilters
     *  Created Date    : 2015-07-15
     *  Description     : Returns all the file filters that are available in the configuration file
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return List<FileFilter>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */        
    public static List<FileFilter> getFileFilters () {
        return lstFilters;
    }
    
    /**
     *  Method Name     : setFileFilters
     *  Created Date    : 2015-07-15
     *  Description     : Sets the file Filters - Currently not used anywhere (hence private) - 
     *                  : change the access modifier based on the usage
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param List<FileFilter>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */ 
    private static void setFileFilters(List<FileFilter> plstFilters) { 
        lstFilters = new ArrayList<>();
        lstFilters.addAll(plstFilters);
    }
    
    
    /**
     *  Method Name     : addFileFilter
     *  Created Date    : 2015-07-15
     *  Description     : Adding a file filter to the list of file filters available
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pFilter: FileFilter
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */ 
    public static void addFileFilter(FileFilter pFilter) {
        if (lstFilters == null) {
            lstFilters = new ArrayList<>();
        }
        lstFilters.add(pFilter);
    }
    
    
    /**
     *  Method Name     : getGraphType
     *  Created Date    : 2015-08-06
     *  Description     : Retrieves the type of the graph using the filename
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrFileName : String
     *  @return GraphType
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static GraphType getGraphType (String pstrFileName) {
        String strExtension = pstrFileName.substring(pstrFileName.lastIndexOf("."),pstrFileName.length());
        for (FileFilter flFilter : lstFilters) {
            if (strExtension.equalsIgnoreCase(flFilter.getFileExtension())) {
                return flFilter.getGraphType();
            }
        }
        return null;
    }
        
    /**
     *  Method Name     : getReaderID
     *  Created Date    : 2015-08-24
     *  Description     : Retrieves the ID of the graph using the FileName (by mapping with the file extension)
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
    public static String getReaderID (String pstrFileName) {
        String strExtension = pstrFileName.substring(pstrFileName.lastIndexOf("."),pstrFileName.length());
        for (FileFilter flFilter : lstFilters) {
            if (strExtension.equalsIgnoreCase(flFilter.getFileExtension())) {
                return flFilter.getReaderID();
            }
        }
        return null;
    }
    
}
