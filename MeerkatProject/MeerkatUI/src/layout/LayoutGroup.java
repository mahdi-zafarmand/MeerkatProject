/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import config.AppConfig;
import config.LayoutGroupConfig.LayoutGroupType;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class Name      : LayoutGroup
 *  Created Date    : 2015-07-17
 *  Description     : For each of the group in the LayoutPane, an instance of this class will hold their respective values
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class LayoutGroup {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
//    private String strTitle ;    
//    private LayoutElements lytElements ;
    
    private String strTitle ;
    private List<LayoutElements> lstLayoutGroup ; // Mapping between the Title and the LayoutElements
    private LayoutGroupType lgtLayoutGroupType ;
    
    public LayoutElements getLayoutElementID(String pstrID) {
        for (LayoutElements currntLayoutElements : lstLayoutGroup) {
            if (currntLayoutElements.getID().equals(pstrID)) {
                return currntLayoutElements ;
            }
        }
        return null ;       
    }
        
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */  
//    public void setLayoutElements (LayoutElements plytItems) {
//        this.lytElements.setLayoutElements(plytItems.getLayoutElements());
//    }
    public List<LayoutElements> getAllLayoutElements () {
        return this.lstLayoutGroup;
    }
//    
//    public void setTitle(String pstrText) {
//        this.strTitle = pstrText;
//    }
    public String getTitle() {
        return this.strTitle;
    }
    
    public LayoutGroupType getLayoutGroupType () {
        return lgtLayoutGroupType ;
    }
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
//    public LayoutGroup() {
//        mapLayoutGroup = new HashMap<>();
//        lytElements = new LayoutElements();
//    }
//    
    public LayoutGroup(String pstrTitle, String pstrLayoutGroupType) {
        lstLayoutGroup = new ArrayList<>();
        this.strTitle = pstrTitle;  
        
        switch (pstrLayoutGroupType) {
            case AppConfig.LAYOUTGROUPTYPE_STANDARD :
                lgtLayoutGroupType = LayoutGroupType.STANDARD;
                break ;
            case AppConfig.LAYOUTGROUPTYPE_METRIC :
                lgtLayoutGroupType = LayoutGroupType.METRIC;
                break ;
            case AppConfig.LAYOUTGROUPTYPE_COMMUNITY :
                lgtLayoutGroupType = LayoutGroupType.COMMUNITY;
                break ;
        }
    }
//    public LayoutGroup(String pstrTitle, LayoutElements plytItems) {
//        this(pstrTitle);
//        this.lytElements.setLayoutElements(plytItems.getLayoutElements());
//    }
        
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
     /**
     *  Method Name     : addLayoutItem
     *  Created Date    : 2015-07-17
     *  Description     : Adds an element to the list of available layouts
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrID: String
     *  @param pstrText: String
     *  @param pstrClass : String
     *  @param pstrParameter : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void addLayoutElement(String pstrID, String pstrText, String pstrClass, String pstrParameter) {
        LayoutElements currentLayout = new LayoutElements(pstrID, pstrText, pstrClass, pstrParameter) ;
        lstLayoutGroup.add(currentLayout) ;
    }
    
    
}
