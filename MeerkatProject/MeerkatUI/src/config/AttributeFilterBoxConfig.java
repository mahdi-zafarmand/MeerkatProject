/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Talat-AICML
 */
public class AttributeFilterBoxConfig {
        
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    private static Map<String, String> mapFilterOperators  ;
    private static Map<String, FilterOperatorType> mapFilterOperatorsType  ;
    
    public enum FilterOperatorType {NUMERIC, NONNUMERIC};    
    
    public static final String EQUAL = "= (equal to)" ;
    public static final String NOTEQUAL = "!= (not equal to)" ;
    public static final String LESSERTHAN = "< (lesser than)" ;
    public static final String GREATERTHAN = "> (greater than)" ;
    public static final String LESSERTHANEQUAL = "<= (less than or equal to)" ;
    public static final String GREATERTHANEQUAL = ">= (greater than or equal to)" ;
    public static final String BETWEEN = "between" ;
    
    public static final String EXACTMATCH = "matches exactly" ;
    public static final String CONTAINS = "contains" ;
    public static final String STARTSWITH = "starts with" ;
    public static final String ENDSWITH = "ends with" ;
    //public static final String SIMILAR = "similar" ;
    public static final String EMPTY = "empty" ;
    public static final String NOTEMPTY = "not empty" ;
    
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2016-05-16
     *  Description     : Sets the two maps between the operators, the text and their types
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pmapFilterOperators : Map<String, String> 
     *  @param pmapFilterOperatorsType : Map<String, FilterOperatorType>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters(Map<String, String> pmapFilterOperators, Map<String, FilterOperatorType> pmapFilterOperatorsType) {
        mapFilterOperators = pmapFilterOperators ;
        mapFilterOperatorsType = pmapFilterOperatorsType ;
    }
    
    
    /**
     *  Method Name     : getOperatorText()
     *  Created Date    : 2016-05-16
     *  Description     : Fetches the operator text based on the operator
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrOperator
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static String getOperatorText(String pstrOperator) {
        String strReturnValue = null ;
        try {            
            for (String strOperatorText : mapFilterOperators.keySet()) {
                String strOperator = mapFilterOperators.get(strOperatorText) ;
                if (strOperator.equals(pstrOperator)) {
                    strReturnValue = new String(strOperatorText) ;
                    break ;
                }
            }
        } catch (Exception ex) {
            System.out.println(".(): EXCEPTION") ;
            ex.printStackTrace();
        }
        
        return strReturnValue ;
    }
    
    /**
     *  Method Name     : getOperator()
     *  Created Date    : 2016-05-16
     *  Description     : Gets the Operator based on the operator display text (sent as the parameter)
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrOperatorText : String
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static String getOperator(String pstrOperatorText) {
        String strReturnValue = null ;
        try {
            strReturnValue = mapFilterOperators.get(pstrOperatorText);
        } catch (Exception ex) {
            System.out.println(".(): EXCEPTION") ;
            ex.printStackTrace();
        }
        
        return strReturnValue ;
    }
    
    /**
     *  Method Name     : getNumericExpressions()
     *  Created Date    : 2016-05-16
     *  Description     : Returns the list of observable expressions that will be displayed on the combo 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return ObservableList
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static ObservableList getNumericExpressions() {
       
        List<String> lstExpressions = new ArrayList<>() ;
        
        for (String strOperatorText : mapFilterOperators.keySet()) {
            
            FilterOperatorType currentType = mapFilterOperatorsType.get(strOperatorText) ;
            if (currentType == FilterOperatorType.NUMERIC) {
                lstExpressions.add(strOperatorText) ;
            }
        }
        
        Collections.unmodifiableList(lstExpressions);
        ObservableList oblstExpressions = FXCollections.observableList(lstExpressions);
        return oblstExpressions;
    } 
    
    /**
     *  Method Name     : getStringExpressions()
     *  Created Date    : 2016-05-16
     *  Description     : Returns the list of observable expressions that will be displayed on the combo 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return ObservableList
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static ObservableList getStringExpressions() {
        
        List<String> lstExpressions = new ArrayList<>() ;
        
        for (String strOperatorText : mapFilterOperators.keySet()) {
            
            FilterOperatorType currentType = mapFilterOperatorsType.get(strOperatorText) ;
            if (currentType == FilterOperatorType.NONNUMERIC) {
                lstExpressions.add(strOperatorText) ;
            }
        }
        
        Collections.unmodifiableList(lstExpressions);
        ObservableList oblstExpressions = FXCollections.observableList(lstExpressions);
        return oblstExpressions;
    }
}
