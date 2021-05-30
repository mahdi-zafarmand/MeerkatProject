/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import java.util.HashMap;
import java.util.Map;

/**
 *  Class Name      : MetricElements
 *  Created Date    : 2015-09-25
 *  Description     : A Map between the MetricItem Text and the ID
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-03-24      Talat           Adding mapIDParamter changes to methods setMetricElements() & addElement()
 *  2016-03-24      Talat           Added methods getMetricsIDParameterMapping() & getMetricParameter()
*/
public class MetricElements {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    static MetricElements application = null; // The only instance that would be created for the MetricsElements
    private Map<String, String> mapTextID ; // Map between the Display Text and the IDs
    private Map<String, String> mapIDParameter ; // Map between the IDs and the Parameter
        
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    /**
     *  Method Name     : setMetricElements()
     *  Created Date    : 2015-09-25
     *  Description     : Sets the mapping between Text, ID and Parameter
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pmapTextID : Map<String, String>
     *  @param pmapIDParameter : Map<String, String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-03-24      Talat           Added parameter pmapIDParameter and initializing and adding code
     * 
    */
    public void setMetricElements (Map<String, String> pmapTextID, Map<String, String> pmapIDParameter) {        
        mapTextID = new HashMap<>();
        mapTextID.putAll(pmapTextID);
        
        mapIDParameter = new HashMap<>() ;
        mapIDParameter.putAll(pmapIDParameter);
    }
    public Map<String, String> getMetricsIDTextMapping() {
        return mapTextID;
    }
    public Map<String, String> getMetricsIDParameterMapping() {
        return mapIDParameter ;
    }
    
    
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    /**
     *  Constructor Name: MetricElements
     *  Created Date    : 2015-09-25
     *  Description     : A private Constructor for the MetricItem
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param phmapElements : Map<String, String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private MetricElements(Map<String, String> phmapElements) {        
        this();
        mapTextID.putAll(phmapElements);
    }
    
    private MetricElements() {
        mapTextID = new HashMap<>();
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    public void addElement(String pstrLayoutClassID, String pstrDisplayText, String pstrParameter) {
        if (mapTextID == null) {
            mapTextID = new HashMap<>();  // Initialize the Hashmap if is null       
        }
        mapTextID.put(pstrDisplayText, pstrLayoutClassID);
        
        if (mapIDParameter == null) {
            mapIDParameter = new HashMap<>();  // Initialize the Hashmap if is null       
        }
        mapIDParameter.put(pstrLayoutClassID, pstrParameter);
    } 
    
    public String getMetricID (String pstrMetricText) {
        if (mapTextID.containsKey(pstrMetricText)) {
            return mapTextID.get(pstrMetricText);
        }
        return null;
    }
    
    public String getMetricParameter (String pstrMetricID) {
        if (mapTextID.containsKey(pstrMetricID)) {
            return mapTextID.get(pstrMetricID);
        }
        return null;
    }
    
    public void Debug_Print () {
        for (String strKey : getMetricsIDTextMapping().keySet()) {
            System.out.println("MetricElements.Debug_Print() : Key = "+strKey +"\tValue = " + getMetricsIDTextMapping().get(strKey));
        }
    }
    
    public static MetricElements getInstance() {
        if (application == null) {
            application = new MetricElements();
        }
        return application;
    }    
}
