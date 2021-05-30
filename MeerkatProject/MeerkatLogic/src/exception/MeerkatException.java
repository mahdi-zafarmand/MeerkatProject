/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *  Class Name      : MeerkatException
 *  Created Date    : 2015-08-26
 *  Description     : A Custom exception that would generate an error
 *  Version         : 
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class MeerkatException extends Exception {

    /**
     *
     */
    public String strMessage;

    /**
     *  Constructor Name: MeerkatException
     *  Created Date    : 2015-08-26
     *  Description     : Constructor for MeerkatException
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrMessage : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public MeerkatException(String pstrMessage){
        super(pstrMessage);
        this.strMessage = pstrMessage;        
    }

    /**
     *  Method Name     : getMessage
     *  Created Date    : 2015-08-26
     *  Description     : Overridden Method that returns the message
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */

    @Override
    public String getMessage(){
        return this.strMessage;
    }
}