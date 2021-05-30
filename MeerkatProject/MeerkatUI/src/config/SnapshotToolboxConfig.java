/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *  Class Name      : SnapshotToolboxConfig
 *  Created Date    : 2016-01-07
 *  Description     : The config of the tool tips or the snapshots to be taken
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class SnapshotToolboxConfig {
    
    
    private static String strHeader ;
    
    private static String strAppSnapshot_tooltip ;
    private static String strAppSnapshot_imageURL ;
    
    private static String strAllOpenGraphsActiveProject_ToolTip ;
    private static String strAllOpenGraphActiveProject_ImageURL ;
    
    private static String strAllOpenGraphsAllProjects_ToolTip ;
    private static String strAllOpenGraphsAllProjects_ImageURL ;
    
    private static String strCurrentGraphComplete_ToolTip ;
    private static String strCurrentGraphComplete_ImageURL ;
    
    private static String strCurrentGraphViewOnly_ToolTip ;
    private static String strCurrentGraphViewOnly_ImageURL ;
    
    private static String strAllProjectsLabel ;

    /**
     * @return the strHeader
     */
    public static String getHeader() {
        return strHeader;
    }
    
    /**
     * @return the strAllProjectsLabel
     */
    public static String getLabelAllProjects() {
        return strAllProjectsLabel;
    }

    /**
     * @return the strAppSnapshot_tooltip
     */
    public static String getAppSnapshot_tooltip() {
        return strAppSnapshot_tooltip;
    }

    /**
     * @return the strAppSnapshot_imageURL
     */
    public static String getAppSnapshot_imageURL() {
        return strAppSnapshot_imageURL;
    }

    /**
     * @return the strAllOpenGraphsActiveProject_ToolTip
     */
    public static String getAllOpenGraphsActiveProject_ToolTip() {
        return strAllOpenGraphsActiveProject_ToolTip;
    }

    /**
     * @return the strAllOpenGraphActiveProject_ImageURL
     */
    public static String getAllOpenGraphActiveProject_ImageURL() {
        return strAllOpenGraphActiveProject_ImageURL;
    }

    /**
     * @return the strAllOpenGraphsAllProjects_ToolTip
     */
    public static String getAllOpenGraphsAllProjects_ToolTip() {
        return strAllOpenGraphsAllProjects_ToolTip;
    }

    /**
     * @return the strAllOpenGraphsAllProjects_ImageURL
     */
    public static String getAllOpenGraphsAllProjects_ImageURL() {
        return strAllOpenGraphsAllProjects_ImageURL;
    }

    /**
     * @return the strCurrentGraphComplete_ToolTip
     */
    public static String getCurrentGraphComplete_ToolTip() {
        return strCurrentGraphComplete_ToolTip;
    }

    /**
     * @return the strCurrentGraphComplete_ImageURL
     */
    public static String getCurrentGraphComplete_ImageURL() {
        return strCurrentGraphComplete_ImageURL;
    }

    /**
     * @return the strCurrentGraphViewOnly_ToolTip
     */
    public static String getCurrentGraphViewOnly_ToolTip() {
        return strCurrentGraphViewOnly_ToolTip;
    }

    /**
     * @return the strCurrentGraphViewOnly_ImageURL
     */
    public static String getCurrentGraphViewOnly_ImageURL() {
        return strCurrentGraphViewOnly_ImageURL;
    }
    
    
    
    public static void Instantiate(
          String pstrHeader
        , String pstrLabelAllProjects
    
        , String pstrAppSnapshot_tooltip
        , String pstrAppSnapshot_imageURL

        , String pstrAllOpenGraphsActiveProject_ToolTip
        , String pstrAllOpenGraphActiveProject_ImageURL

        , String pstrAllOpenGraphsAllProjects_ToolTip
        , String pstrAllOpenGraphsAllProjects_ImageURL

        , String pstrCurrentGraphComplete_ToolTip
        , String pstrCurrentGraphComplete_ImageURL

        , String pstrCurrentGraphViewOnly_ToolTip
        , String pstrCurrentGraphViewOnly_ImageURL
    ) {
        
        strHeader = pstrHeader ;
        strAllProjectsLabel = pstrLabelAllProjects ;
    
        strAppSnapshot_tooltip = pstrAppSnapshot_tooltip ;
        strAppSnapshot_imageURL = pstrAppSnapshot_imageURL ;

        strAllOpenGraphsActiveProject_ToolTip = pstrAllOpenGraphsActiveProject_ToolTip ;
        strAllOpenGraphActiveProject_ImageURL = pstrAllOpenGraphActiveProject_ImageURL ;

        strAllOpenGraphsAllProjects_ToolTip = pstrAllOpenGraphsAllProjects_ToolTip ; 
        strAllOpenGraphsAllProjects_ImageURL = pstrAllOpenGraphsAllProjects_ImageURL ;

        strCurrentGraphComplete_ToolTip = pstrCurrentGraphComplete_ToolTip ;
        strCurrentGraphComplete_ImageURL = pstrCurrentGraphComplete_ImageURL ;

        strCurrentGraphViewOnly_ToolTip = pstrCurrentGraphViewOnly_ToolTip ;
        strCurrentGraphViewOnly_ImageURL = pstrCurrentGraphViewOnly_ImageURL ;
    }
    
}
