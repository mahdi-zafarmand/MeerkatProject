package datastructure.core.graph.impl;

import config.MeerkatSystem;
import java.util.HashSet;
import java.util.Set;

import datastructure.core.DynamicAttributable.DynamicAttributer.GenericDynamicAttributer;
import datastructure.core.DynamicAttributable.SysDynamicAttributer.SystemDynamicAttributer;
import datastructure.core.graph.classinterface.IGraphElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  Class Name      : GraphElement
 *  Created Date    : 2015-xx-xx
 *  Description     : An element of a graph - can be a vertex or an edge
 *  Version         : 1.0
 *  @author         : Afra
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-09-22      Talat           Added the implementation of getAttributeNamesWithType()
 * 
*/
public class GraphElement implements IGraphElement {
    
    /**
     *
     */
    public static double DEFAULT_WEIGHT = 1.0;
    
    int intGraphElementID = -1;
    double dblGraphElementWeight = DEFAULT_WEIGHT;

    private GenericDynamicAttributer userAttributer = new GenericDynamicAttributer();
    private SystemDynamicAttributer systemAttributer = new SystemDynamicAttributer();


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
            List<String> listFileId = 
                new ArrayList<>(getUserAttributer().getAttributeValues(attName).values());
            for (String value : listFileId){
                if (value!=null && value.matches("[-+]?\\d*\\.?\\d+")) {
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
        }
        
        for(String attName : getSystemAttributer().getAttributeNames()) {
            
            List<String> listFileId = 
                new ArrayList<>(getSystemAttributer().getAttributeValues(attName).values());
            for (String value : listFileId){
                if (value!=null && value.matches("[-+]?\\d*\\.?\\d+")) {
                    if (!nonNumericAttNames.contains(attName)) {
                        numericAttNames.add(attName);
                    }
                } else if (numericAttNames.contains(attName)) {
                    numericAttNames.remove(attName);
                    nonNumericAttNames.add(attName);
                }
            } 
        }


        //numericAttNames.addAll(systemAttributer.getAttributeNames());

        return numericAttNames;
    }
    
    /**
     *
     * @return
     */
    @Override
    public Set<String> getAttributeNames() {
        Set<String> setAttributeNames = new HashSet<>();
        
        // The key is the attribute name and the value is boolean (true for numeric and false for non-numeric)
        for(String att : getUserAttributer().getAttributeNames()) {
            setAttributeNames.add(att);
        }
        
        for(String att : getSystemAttributer().getAttributeNames()) {
            setAttributeNames.add(att);
        }
        return setAttributeNames;
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
        for(String strCurrentAttribute : 
                getUserAttributer().getAttributeNames()) {
            
             List<String> listFileId = 
                new ArrayList<>(getUserAttributer().getAttributeValues(strCurrentAttribute).values());
            
            for (String value : listFileId) {
                if (value!=null && value.matches("[-+]?\\d*\\.?\\d+")) {
                    if (!hmapAttributeNames.containsKey(strCurrentAttribute)) {
                        hmapAttributeNames.put(strCurrentAttribute, true);
                    }
                } else {
                    if (hmapAttributeNames.containsKey(strCurrentAttribute)) {
                        hmapAttributeNames.remove(strCurrentAttribute);
                        hmapAttributeNames.put(strCurrentAttribute, false);
                    }else{
                         hmapAttributeNames.put(strCurrentAttribute, false);
                    }
                }
            }
        }
        
        for(String strCurrentAttribute : 
                getSystemAttributer().getAttributeNames()) {
            List<String> listFileId = 
                new ArrayList<>(getSystemAttributer().getAttributeValues(strCurrentAttribute).values());
            for (String value : listFileId){
                if (value!=null && value.matches("[-+]?\\d*\\.?\\d+")) {
                    if (!hmapAttributeNames.containsKey(strCurrentAttribute)) {
                        hmapAttributeNames.put(strCurrentAttribute, true);
                    }
                } else {
                    if (hmapAttributeNames.containsKey(strCurrentAttribute)) {
                        hmapAttributeNames.remove(strCurrentAttribute);
                        hmapAttributeNames.put(strCurrentAttribute, false);
                    }else{
                        hmapAttributeNames.put(strCurrentAttribute, false);
                    }
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
    public GenericDynamicAttributer getUserAttributer() {
        return userAttributer;
    }

    /**
     *
     * @return
     */
    @Override
    public SystemDynamicAttributer getSystemAttributer() {
        return systemAttributer;
    }

    /**
     *
     * @return
     */
    @Override
    public int getId() {
        return this.intGraphElementID;
    }

    /**
     *
     * @param pintId
     */
    @Override
    public void setId(int pintId) {
        this.intGraphElementID = pintId;
    }

    /**
     *
     * @return
     */
    @Override
    public double getWeight() {
        return this.dblGraphElementWeight;
    }

    /**
     *
     * @param pdblWeight
     */
    @Override
    public void setWeight(double pdblWeight) {
        this.dblGraphElementWeight = pdblWeight;
    }

}
