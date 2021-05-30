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
public class LocalCommunityParam {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strTitle ;
    private static String strSelectAlgorithmLabel ;
    private static String strOverlapLabel ;
    
    // Values
    private static String strAlgorithmType ;
    private static boolean blnIsOverlap ;


    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public static String getTitle() {
        return strTitle ;
    }
    public static String getAlgorithmLabel() {
        return strSelectAlgorithmLabel ;
    }
    public static String getOverlapLabel() {
        return strOverlapLabel ;
    }
    
    public static String getAlgorithmType() {
        return strAlgorithmType ;
    }
    public static void setAlgorithmType(String pstrValue) {
        strAlgorithmType = pstrValue ;
    }
    
    public static boolean getIsOverlap() {
        return blnIsOverlap ;
    }
    public static void setOverlap(boolean pblnValue) {
        blnIsOverlap = pblnValue ;
    }

    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2016-04-07
     *  Description     : Sets the Parameters of the Local Community Mining Algorithm
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrTitle : String
     *  @param pstrSelectAlgorithmLabel : String
     *  @param pstrOverlapLabel : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters(
              String pstrTitle
            , String pstrSelectAlgorithmLabel
            , String pstrOverlapLabel
    ) {
        strTitle = pstrTitle ;
        strSelectAlgorithmLabel = pstrSelectAlgorithmLabel ;
        strOverlapLabel = pstrOverlapLabel ;
    }
}
