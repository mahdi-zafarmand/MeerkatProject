/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communitymining.parameters;

/**
 *  Class Name      : FastModularityParam
 *  Created Date    : 2016-04-07
 *  Description     : Parameters for Fast Modularity Community Mining Algorithm
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class LocalTopLeadersParam {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strTitle ;
    private static String strClusterNumberLabel ;
    private static String strOutlierPercentLabel ;
    private static String strHubPercentLabel ;
    private static String strCenterDistanceLabel ;
    
    // Values
    private static int intNumberOfClusters ;
    private static double dblOutlierPercentage ;
    private static double dblHubPercentage ;
    private static double dblCenterDistance ;


    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public static String getTitle() {
        return strTitle ;
    }
    public static String getClusterNumberLabel() {
        return strClusterNumberLabel ;
    }
    public static String getOutlierPercentLabel() {
        return strOutlierPercentLabel ;
    }
    public static String getHubPercentLabel() {
        return strHubPercentLabel ;
    }
    public static String getCenterDistanceLabel() {
        return strCenterDistanceLabel ;
    }
    
    public static int getNumberOfClusters () {
        return intNumberOfClusters ;
    }
    public static void setNumberOfClusters (int pintValue) {
        intNumberOfClusters = pintValue ;
    }
    
    public static double getOutlierPercentage () {
        return dblOutlierPercentage ;
    }
    public static void setOutlierPercentage (double pdblValue) {
        dblOutlierPercentage = pdblValue ;
    }
    
    public static double getHubPercentage () {
        return dblHubPercentage ;
    }
    public static void setHubPercentage (double pdblValue) {
        dblHubPercentage = pdblValue ;
    }
    
    public static double getCenterDistance () {
        return dblCenterDistance ;
    }
    public static void setCenterDistance (double pdblValue) {
        dblCenterDistance = pdblValue ;
    }   
            

    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2016-04-07
     *  Description     : Sets the Parameters of the Local Top Leaders Community Mining Algorithm
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrTitle : String
     *  @param pstrClusterNumberLabel : String
     *  @param pstrOutlierPercentLabel : String
     *  @param pstrHubPercentLabel : String
     *  @param pstrCenterDistanceLabel : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters(
              String pstrTitle
            , String pstrClusterNumberLabel
            , String pstrOutlierPercentLabel
            , String pstrHubPercentLabel
            , String pstrCenterDistanceLabel
                    
    ) {
        strTitle = pstrTitle ;
        strClusterNumberLabel = pstrClusterNumberLabel ;
        strOutlierPercentLabel = pstrOutlierPercentLabel ;
        strHubPercentLabel = pstrHubPercentLabel ;
        strCenterDistanceLabel = pstrCenterDistanceLabel ;
    }
}
