/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communitymining.parameters;

/**
 *  Class Name      : DynamicCommunityMiningParam
 *  Created Date    : 2016-04-07
 *  Description     : Parameters for Dynamic Community Mining
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class DynamicCommunityMiningParam {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    private static String strTitle ;
    
    private static String strSimilarityThresholdLabel ;
    private static double dblSimilarityThreshold ;
    
    private static String strMetricLabel ;
    private static String strMetric ;

    private static String strMethodLabel ;
    private static String strMethod ;
    
    private static String strOverlapLabel ;
    private static boolean blnIsOverlaped ;
    
    private static String strHubsLabel ;
    private static boolean blnIsHubs ;
    
    private static String strInstabilityLabel ;
    private static double dblInstability ;
    
    private static String strHistoryLabel ;
    private static double dblHistory ;    
    

    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public static String getTitle () {
        return strTitle;
    }
    public static String getSimilarityThresholdLabel () {
        return strSimilarityThresholdLabel;
    }
    public static String getMetricLabel () {
        return strMetricLabel;
    }
    public static String getMethodLabel () {
        return strMethodLabel;
    }
    public static String getOverlapLabel () {
        return strOverlapLabel;
    }
    public static String getHubsLabel () {
        return strHubsLabel;
    }
    public static String getInstabilityLabel () {
        return strInstabilityLabel;
    }
    public static String getHistoryLabel () {
        return strHistoryLabel;
    }
    
    public static double getSimilarityThreshold() {
        return dblSimilarityThreshold ;
    }
    public static void setSimilarityThreshold(double pdblValue) {
        dblSimilarityThreshold = pdblValue ;
    }
    
    public static void setMetric(String pstrValue) {
        strMetric = pstrValue ;
    }
    public static String getMetric() {
        return strMetric ;
    }
    
    public static void setMethod(String pstrValue) {
        strMethod = pstrValue ;
    }
    public static String getMethod() {
        return strMethod ;
    }    
    
    public static boolean getIsOverlap() {
        return blnIsOverlaped ;
    }
    public static void setOverlap(boolean pblnValue) {
        blnIsOverlaped = pblnValue ;
    }
    
    public static boolean getIsHubs() {
        return blnIsHubs ;
    }
    public static void setHubs(boolean pblnValue) {
        blnIsHubs = pblnValue ;
    }
    
    public static double getInstability() {
        return dblInstability ;
    }
    public static void setInstability(double pdblValue) {
        dblInstability = pdblValue ;
    }
    
    
    public static double getHistory() {
        return dblHistory ;
    }
    public static void setHistory(double pdblValue) {
        dblHistory = pdblValue ;
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2016-04-07
     *  Description     : Sets the Parameters of the Dynamic Community Mining Algorithm
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrTitle : String
     *  @param pstrSimilarityThresholdLabel : String
     *  @param pstrMetricLabel : String
     *  @param pstrMethodLabel : String
     *  @param pstrOverlapLabel : String
     *  @param pstrHubsLabel : String
     *  @param pstrInstabilityLabel : String
     *  @param pstrHistoryLabel : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters(
              String pstrTitle
            , String pstrSimilarityThresholdLabel
            , String pstrMetricLabel
            , String pstrMethodLabel
            , String pstrOverlapLabel
            , String pstrHubsLabel
            , String pstrInstabilityLabel
            , String pstrHistoryLabel
    ){
        strTitle = pstrTitle ;
        strSimilarityThresholdLabel = pstrSimilarityThresholdLabel ;
        strMetricLabel = pstrMetricLabel ;
        strMethodLabel = pstrMethodLabel ;
        strOverlapLabel = pstrOverlapLabel ;
        strHubsLabel = pstrHubsLabel ;
        strInstabilityLabel = pstrInstabilityLabel ;
        strHistoryLabel = pstrHistoryLabel ;
    }
}
