/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import config.AppConfig;

/**
 *  Class Name      : LayoutElements
 *  Created Date    : 2015-07-16
 *  Description     : Used to store the mapping between IDs and LayoutElements
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class LayoutElements {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    // Mapping between the Display Text (key) and the ID (value) 
    // private Map<String, String> hmapElements ;
    private String strID ;
    private String strParameter ;
    private String strText ;
    private String strClass ;
    private String [] arrstrParameters ;
    
    public String getID () {
        return strID ;
    }
    public String getParameter() {
        return strParameter ;
    }
    public String getText() {
        return strText ;
    }
    
    public String getLayoutClass() {
        return this.strClass ;
    }
    
    public String [] getParameters() {
        return this.arrstrParameters ;
    }
    
    public LayoutElements(String pstrID, String pstrText, String pstrClass, String pstrParameter) {
        strID = pstrID ;        
        strText = pstrText ;
        strParameter = pstrParameter;
                
        if (pstrClass != null) {
            strClass = pstrClass ;
        }
        if (pstrParameter != null) {
            arrstrParameters = pstrParameter.split(AppConfig.DELIMITER_COMMA);
        }
    }
    
    
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    /*
    public void setLayoutElements (Map<String, String> phmapElements) {
        hmapElements = new HashMap<>();
        hmapElements.putAll(phmapElements);
    }
    public Map<String, String> getLayoutElements() {
        return hmapElements;
    }
    */
    
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    /*
    public LayoutElements(Map<String, String> phmapElements) {        
        this();
        hmapElements.putAll(phmapElements);
    }
    
    public LayoutElements() {
        hmapElements = new HashMap<>();
    }
    */
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : addElement()
     *  Created Date    : 2015-07-16
     *  Description     : Add a layout element 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrLayoutClassID: String
     *  @param pstrDisplayText: String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    /*
    public void addElement(String pstrLayoutClassID, String pstrDisplayText) {
        if (hmapElements == null) {
            hmapElements = new HashMap<>();  // Initialize the Hashmap if is null       
        }
        hmapElements.put(pstrDisplayText, pstrLayoutClassID);
    }        
    */
    
    /**
     *  Method Name     : getLayoutClass()
     *  Created Date    : 2015-07-16
     *  Description     : Finds the Display Text from the list of available texts and returns the corresponding ID
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrDisplayText: String
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    /*
    public String getLayoutDisplay (String pstrDisplayText) {        
        return hmapElements.get(pstrDisplayText);
    }
*/
    
}
