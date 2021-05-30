/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core;

import config.MeerkatSystem;

/**
 *  Class Name      : TimeFrame
 *  Created Date    : 2016-03-xx
 *  Description     : The Time Frame in a graph
 *  Version         : 1.0
 *  @author         : aabnar
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class TimeFrame {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */

    /**
     *
     */
    
    public static TimeFrame DEFAULT_TIMEFRAME = 
            new TimeFrame(MeerkatSystem.DEFAULT_TIMEFRAME);
    
    private String strTimeFrameName;
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    /**
     *  Constructor Name: TimeFrame()
     *  Created Date    : 2016-03-xx
     *  Description     : Sets the time frame name
     *  Version         : 1.0
     *  @author         : aabnar
     * 
     *  @param pstrTimeFrame : String 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public TimeFrame(String pstrTimeFrame) {
        this.strTimeFrameName = pstrTimeFrame;
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */

    /**
     *
     * @return
     */
    
    
    @Override
    public String toString() {
        return this.strTimeFrameName;
    }
    
    
    /**
     *  Method Name     : getTimeFrameName()
     *  Created Date    : 2016-04-13
     *  Description     : Gets the Name of the TimeFrame
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return String - the TimeFrame Name
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public String getTimeFrameName() {
        return strTimeFrameName ;
    }
}
