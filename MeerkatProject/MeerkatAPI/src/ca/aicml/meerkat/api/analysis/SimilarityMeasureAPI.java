/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  Class Name      : SimilarityMeasureAPI
 *  Created Date    : 2016-01-13
 *  Description     : 
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class SimilarityMeasureAPI {
    
    public static List<String> getSimilarityMeasures() {
        List<String> lststrAttributeNames ;
        
        // #REMOVE
        lststrAttributeNames = new ArrayList<>(Arrays.asList("Measure1", "Measure2", "Measure3"));
        // #END-REMOVE
        
        // returns a list of strings with all the attribute names
        return lststrAttributeNames ;
    }
}
