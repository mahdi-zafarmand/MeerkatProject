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
public class SameAttributeValueParam {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strTitle ;
    
    // Labels
    private static String strMessageLabel ;
    private static String strAttributeLabel ;
    private static String strMultipleValuesLabel ;
    private static String strSeparatorLabel ;
    
    // Values 
    private static String strAttribute ;
    private static boolean blnIsMultipleValues ;
    private static String strSeparator ;
    

    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public static String getTitle() {
        return strTitle ;
    }
    public static String getMessageLabel() {
        return strMessageLabel ;
    }
    public static String getAttributeLabel() {
        return strAttributeLabel ;
    }
    public static String getMultipleValuesLabel() {
        return strMultipleValuesLabel ;
    }
    public static String getSeparatorLabel() {
        return strSeparatorLabel ;
    }
    
    public static void setAttribute(String pstrValue) {
        strAttribute = pstrValue ;
    }
    public static String getAttribute() {
        return strAttribute ;
    }
    
    public static void setMultipleValues(boolean pblnValue) {
        blnIsMultipleValues = pblnValue ;
    }
    public static boolean getIsMultipleValues() {
        return blnIsMultipleValues ;
    }
    
    public static void setSeparator(String pstrSeperator) {
        strSeparator = pstrSeperator ;
    }
    public static String getSeparator() {
        return strSeparator ;
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2016-04-07
     *  Description     : Sets the Parameters of the Community Mining Algorithms
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrTitle : String
     *  @param pstrMessageLabel : String
     *  @param pstrAttributeIDLabel : String
     *  @param pstrMultipleValuesLabel : String
     *  @param pstrSeparatorLabel : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters(
              String pstrTitle
            , String pstrMessageLabel
            , String pstrAttributeIDLabel
            , String pstrMultipleValuesLabel
            , String pstrSeparatorLabel
    ) {
        strTitle = pstrTitle ;
        strMessageLabel = pstrMessageLabel ;
        strAttributeLabel = pstrAttributeIDLabel ;
        strMultipleValuesLabel = pstrMultipleValuesLabel ;
        strSeparatorLabel = pstrSeparatorLabel ;
    }
}
