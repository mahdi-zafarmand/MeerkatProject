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
public class RosvallInfomodParam {
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strTitle ;
    private static String strAttemptsNumberLabel ;
    private static int intNumberOfAttempts ;
    

    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public static String getTitle() {
        return strTitle ;
    }
    public static String getAtttemptsNumberLabel() {
        return strAttemptsNumberLabel ;
    }
    
    public static int getNumberOfAttempts () {
        return intNumberOfAttempts ;
    }
    public static void setNumberOfAttempts(int pintValue) {
        intNumberOfAttempts = pintValue ;
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2016-04-07
     *  Description     : Sets the Parameters of the Rosvall Infomod Community Mining Algorithms
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrTitle : String
     *  @param pstrAttemptsNumberLabel : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters(
              String pstrTitle
            , String pstrAttemptsNumberLabel            
    ) {
        strTitle = pstrTitle ;
        strAttemptsNumberLabel = pstrAttemptsNumberLabel ;
    }
}
