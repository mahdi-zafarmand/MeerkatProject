/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communitymining.parameters;

/**
 *  Class Name      : LocalT
 *  Created Date    : 2016-04-07
 *  Description     : Parameters for LocalT Community Mining Algorithm
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class LocalTParam {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strTitle ;


    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public static String getTitle() {
        return strTitle ;
    }

    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2017-08-10
     *  Description     : Sets the Parameters of the Local T Mining Algorithm
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pstrTitle : String
     *  
     * 
    */
    public static void setParameters(
              String pstrTitle
    ) {
        strTitle = pstrTitle ;
    }
    
}
