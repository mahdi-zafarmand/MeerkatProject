/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class Name      : 
 *  Created Date    : 2016-
 *  Description     : 
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class LayoutSet {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private List<LayoutGroup> lstlytGroup;    
    private static LayoutSet layoutsetInstance ;
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public List<LayoutGroup> getAllLayouts() {
        return this.lstlytGroup;
    }
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    private LayoutSet() {
        this.lstlytGroup = new ArrayList<>();
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    public static LayoutSet getInstance() {
        if (layoutsetInstance == null) {
            layoutsetInstance = new LayoutSet();
        }
        return layoutsetInstance ;
    }
    public static void setLayoutSetNull() {
        layoutsetInstance=null;
    }
    
    public void addLayoutGroup(LayoutGroup pGroup) {
        this.lstlytGroup.add(pGroup);
    }
}
