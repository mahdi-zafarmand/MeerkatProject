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
public class FastModularityParam {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strTitle ;
    private static String strNote ;
    private static String strAlgorithmTypeLabel ;
    private static String strWeightedLabel ;
    
    // Values
    private static String strAlgorithmType ;
    private static boolean blnIsWeighted ;


    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public static String getTitle() {
        return strTitle ;
    }
    public static String getNote() {
        return strNote ;
    }
    public static String getAlgorithmTypeLabel() {
        return strAlgorithmTypeLabel ;
    }
    public static String getWeightedLabel() {
        return strWeightedLabel ;
    }
    
    public static String getAlgorithmType() {
        return strAlgorithmType ;
    }
    public static void setAlgorithmType(String pstrValue) {
        strAlgorithmType = pstrValue ;
    }
    
    public static boolean getIsWeighted() {
        return blnIsWeighted ;
    }
    public static void setWeighted(boolean pblnValue) {
        blnIsWeighted = pblnValue ;
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2016-04-07
     *  Description     : Sets the Parameters of the Fast Modularity Community Mining Algorithm
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrTitle : String
     *  @param pstrNote : String
     *  @param pstrAlgorithmTypeLabel : String
     *  @param pstrWeightedLabel : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters(
              String pstrTitle
            , String pstrNote
            , String pstrAlgorithmTypeLabel
            , String pstrWeightedLabel
    ) {
        strTitle = pstrTitle ;
        strNote = pstrNote ;
        strAlgorithmTypeLabel = pstrAlgorithmTypeLabel ;
        strWeightedLabel = pstrWeightedLabel ;
    }
}
