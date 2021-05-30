/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api;

import config.MeerkatSystem;

/**
 *  Class Name      : GeneralAPI
 *  Created Date    : 2016-05-27
 *  Description     : Set of General APIs
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/

public class GeneralAPI {
    
    public static String getSystemAttributeToken() {
        return MeerkatSystem.SYSTEMATTRIBUTE ;
    }
}
