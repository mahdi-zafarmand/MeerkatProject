/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *  Class Name      : LinkPredictionConfig
 *  Created Date    : 2018-03-21
 *  Description     : Parameters for Fast Modularity Community Mining Algorithm
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class LinkPredictionConfig {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strTitle ;
    private static String strLinkMeasure_TopN ;
    private static String strLinkMeasure_Metric ;
    private static String strError_TopN ;
    private static String strError_Metric ;
    private static boolean blnMeasureType ; // 1 for TopN, 0 for Metric
    private static String strTooltip_TopN ;
    private static String strTooltip_Metric ;
    
    // Values
    private static double dblLinkMeasureValue ;


    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public static String getTitle() {
        return strTitle ;
    }
    
    public static String getTopN(){
        return strLinkMeasure_TopN;
    }
    
    public static String getMetric() {
        return strLinkMeasure_Metric;
    }
    
    public static String getError_TopN(){
        return strError_TopN;
    }
    
    public static String getError_Metric(){
        return strError_Metric;
    }
    
    public static boolean getMetricType(){
        return blnMeasureType;
    }
    
    public static void setMeasureValue(double dblValue){
        dblLinkMeasureValue = dblValue;
    }
    
    public static double getMeasureValue(){
        return dblLinkMeasureValue;
    }
    
    public static String getTooltip_TopN(){
        return strTooltip_TopN;
    }
    
    public static String getTooltip_Metric(){
        return strTooltip_Metric;
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     * Sets the strings required to be displayed in the Link Prediction Dialog
     * @param pstrTitle
     * @param pstrLinkMeasure_TopN
     * @param pstrLinkMeasure_Metric
     * @param pstrError_TopN
     * @param pstrError_Metric
     * @param pstrTooltip_TopN
     * @param pstrTooltip_Metric 
     * 
     * @author Talat
     * @since 2018-03-21
     */
    public static void setParameters(
              String pstrTitle, 
            String pstrLinkMeasure_TopN, 
            String pstrLinkMeasure_Metric,
            String pstrError_TopN, 
            String pstrError_Metric,
            String pstrTooltip_TopN,
            String pstrTooltip_Metric
            
    ) {
        strTitle = pstrTitle ;
        strLinkMeasure_TopN = pstrLinkMeasure_TopN;
        strLinkMeasure_Metric = pstrLinkMeasure_Metric;
        strError_TopN = pstrError_TopN ;
        strError_Metric = pstrError_Metric ;
        strTooltip_TopN = pstrTooltip_TopN ;
        strTooltip_Metric = pstrTooltip_Metric ;
    }
}
