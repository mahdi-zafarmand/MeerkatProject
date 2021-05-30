/*
 * meerkat@aicml july 2015
 */
package datastructure.core.text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import datastructure.core.StaticAttributable.StaticAttributer.GenericStaticAttributer;
import datastructure.core.StaticAttributable.SysStaticAttributer.SystemStaticAttributer;
import datastructure.core.StaticAttributable;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * Classname: HNode (Hierarchical Node)
 * Version Information: 1
 * Created Date: July 13, 2015
 * @author: aabnar
 * Copyright Notice: @meerkat
 * Description:
 * HNode or Hierarchy Node is abstraction of all nodes that the developer
 * wants to show in a thread tree panel. Nodes with no content would simply
 * be only an HNode.
 * -------------------------------------------
 * EDIT HISTORY (latest on top)
 * Date             Author	Description
 * 2015-10-04       Talat       Changed the access modifiers to protected
 * 2015-09-22       Talat       Added implementation of getAttributeNamesWithType()
 * July 13,2015     aabnar	created
 * 
 */
public class HNode implements StaticAttributable {
	
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */

    protected int id;
    protected GenericStaticAttributer usrAttributer = 
            new GenericStaticAttributer();
    protected SystemStaticAttributer sysAttributer = 
            new SystemStaticAttributer();
    protected String strTitle;
    protected HNode hndParent;
    protected List<HNode> lhndChildren = new ArrayList<>();
    protected String strIconFilePath;
    protected Date dtDate ;
    protected Calendar calCalendar;

    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */    
    public HNode(int pintId, String pstrTitle, HNode pParent) {
        this.id = pintId;
        this.strTitle = pstrTitle;
        this.hndParent = pParent;
        if (pParent != null) {
            pParent.addChild(this);
//            System.out.println("HNode.constructor() : parent = "+
//                    pParent.getTitle() +
//                    " , child = " +
//                    this.getTitle());
        }
    }

    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */

    public int getId() {
        return this.id;
    }
    public String getTitle() {
        return strTitle;
    }    
    public void setTitle(String pstrTitle) {
        this.strTitle = pstrTitle;
    }

    public void setDate(Date pdtDate) {
        this.dtDate = pdtDate ;
        this.calCalendar.setTime(this.dtDate);
    }
    public Date getDate() {
        return dtDate ;
    }
    
    public HNode getParent() {
        return hndParent;
    }
    public boolean isRoot() {
        if (hndParent != null) {
            return false;
        }
        return true;
    }

    
    public List<HNode> getChildren() {
        return lhndChildren;
    }
    public void setMsgIcon(String pstrFilePath) {
        this.strIconFilePath = pstrFilePath;
    }
    
    /* *************************************************************** */
    /* ****************     OVER-RIDDEN  METHODS     ***************** */
    /* *************************************************************** */
    @Override
    public GenericStaticAttributer getUserAttributer() {
        return usrAttributer;
    }

    @Override
    public SystemStaticAttributer getSystemAttributer() {
        return sysAttributer;
    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */

    public String getDateString() {
//        MAHDI: there was a warning about using depricated methods. 
//        I added this.calcCalendar, added the second line in setDate(), and added four lines in this method.
//        return this.dtDate.getYear()+"-"+this.dtDate.getMonth()+"-"+this.dtDate.getDay();
        int year = this.calCalendar.get(Calendar.YEAR);
        int month = this.calCalendar.get(Calendar.MONTH) + 1; // because the original value is between {0-11}.
        int day = this.calCalendar.get(Calendar.DAY_OF_MONTH);
        return Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);
    }
    /**
     *
     * @param pChild
     */
    protected void addChild(HNode pChild) {
        lhndChildren.add(pChild);
    }

    /**
     *
     * @param pChildren
     */
    public void addChildren(List<HNode> pChildren) {
        /*
        for (HNode ch : pChildren) {
            lhndChildren.add(ch);
        }
        */
        this.lhndChildren = pChildren;
    }

    /**
     *
     * @param pChild
     */
    public void removeChild(HNode pChild) {
        /*
        // #DEBUG        
        System.out.println("Child : "+pChild.getTitle()+"\t Addr "+pChild);
        
        System.out.println("Size of the list: "+lhndChildren.size());
        for (HNode current : this.lhndChildren) {
            System.out.println("Title: "+current.getTitle()+"\t Addr "+current);
            if (current == pChild) {
                System.out.println("Match found");
            }
        }
        // #ENDDEBUG
        */
        if (pChild != null) {
            if (lhndChildren.contains(pChild)) {
                // System.out.println("HNode.removeChild(): Child Removed");
                lhndChildren.remove(pChild);
            }
        }
    }

    /**
     *
     * @param pChildren
     */
    public void removeChildren(List<HNode> pChildren) {
        for (HNode ch : pChildren) {
            if (lhndChildren.contains(ch)) {
                lhndChildren.remove(ch);
            }
        }
    }
    
    /**
     *
     * @return
     */
    public int getChildrenCount() {
        return this.lhndChildren.size();
    }

    
    /**
     *  Method Name     : getNumericAttributeNames()
     *  Created Date    : 2015-0x-xx
     *  Description     : Retrieves the list of all numeric attribute names
     *  Version         : 1.0
     *  @author         : Afra
     * 
     *  @return Set<String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-09-22      Talat           Changed the name of the method from getNumericAttNames() to getNumericAttributeNames()
     * 
    */
    @Override
    public Set<String> getNumericAttributeNames() {
        HashSet<String> numericAttNames = new HashSet<>();
        HashSet<String> nonNumericAttNames = new HashSet<>();


        for(String attName : getUserAttributer().getAttributeNames()) {
            if (getUserAttributer().getAttributeValue(attName)
                    .matches("[-+]?\\d*\\.?\\d+")) {
                if (!nonNumericAttNames.contains(attName)) {
                    numericAttNames.add(attName);
                }
            } else {
                if (numericAttNames.contains(attName)) {
                    numericAttNames.remove(attName);
                    nonNumericAttNames.add(attName);
                }
            }
        }

        numericAttNames.addAll(sysAttributer.getAttributeNames());

        return numericAttNames;
    }
    
        /**
     *  Method Name     : getAttributeNamesWithType()
     *  Created Date    : 2015-09-22
     *  Description     : Returns a map of all the attributes available for this 
     *                      graph element and its type (true for numeric and false 
     *                      for non-numberic)
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return Map<String, Boolean>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    @Override
    public Map<String, Boolean> getAttributeNamesWithType() {
           
        Map<String, Boolean> hmapAttributeNames = new HashMap<>();
        
        // The key is the attribute name and the value is boolean (true for numeric and false for non-numeric)
        for(String strAttributeName : getUserAttributer().getAttributeNames()) {
            if (getUserAttributer().getAttributeValue(strAttributeName)
                    .matches("[-+]?\\d*\\.?\\d+")) {
                if (!hmapAttributeNames.containsKey(strAttributeName)) {
                    hmapAttributeNames.put(strAttributeName, true);
                }
            } else {
                if (hmapAttributeNames.containsKey(strAttributeName)) {
                    // hmapAttributeNames.remove(strAttributeName);
                    hmapAttributeNames.put(strAttributeName, false);
                }
            }
        }
        
        return hmapAttributeNames;
    }

    /**
     *
     * @return
     */
    @Override
    public Set<String> getAttributeNames() {
        Set<String> setAttNames = new HashSet<>();
        setAttNames.addAll(getUserAttributer().getAttributeNames());
        setAttNames.addAll(getSystemAttributer().getAttributeNames());
        return setAttNames;
    }

}
