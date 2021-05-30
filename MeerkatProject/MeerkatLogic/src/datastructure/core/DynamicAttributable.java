package datastructure.core;

import config.MeerkatSystem;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import datastructure.core.DynamicAttributable.DynamicAttributer.GenericDynamicAttributer;
import datastructure.core.DynamicAttributable.SysDynamicAttributer.SystemDynamicAttributer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * An interface giving an object the ability to store multiple attributes.
 *
 * @author Matt Gallivan

 -------------- Edit history 
 Date             Author          Description
 2017-05-18       sankalp         - added the method removeAttributeValue.
 2015-11-24       Afra            - added Timeframe to generalAttributer.
 November 2015    Afra            - Added timeframe to system attributes.
 2015-09-22       Talat           Using Diamond Interface where possible 
 2015-09-22       Talat           Adding method getAttributeNames() 
 july 14, 2015    aabnar          added SystemDynamicAttributer to include
                                  numerical values computed by meerkat. 
 july 14th,2015   aabnar          change from IAttribute to (attName, attValue).
 */
public interface DynamicAttributable {

    /**
     *
     * @return
     */
    public GenericDynamicAttributer getUserAttributer();

    /**
     *
     * @return
     */
    public SystemDynamicAttributer getSystemAttributer();

    /**
     *
     * @return
     */
    public Set<String> getNumericAttributeNames();

    /**
     *
     * @return
     */
    public Map<String, Boolean> getAttributeNamesWithType();
    
    /**
     *
     * @return
     */
    public Set<String> getAttributeNames();

    /*
     * --------- Interface ---------
     */

    /**
     *
     */
    
    interface DynamicAttributer {

        /**
         *
         * @return
         */
        public Set<String> getAttributeNames();

        /**
         *
         * @param pstrAttName
         * @param tf
         * @return
         */
        public String getAttributeValue(String pstrAttName, TimeFrame tf);

        /**
         *
         * @param pstrAttName
         * @return
         */
        public Map<TimeFrame, String> getAttributeValues(String pstrAttName);

        /**
         *
         * @param pstrAttName
         * @param pAttValue
         * @param tf
         */
        public void addAttribute(String pstrAttName, 
                                        String pAttValue,
                                        TimeFrame tf);
        
        //public void addAttributeValue(String pstrAttName, T pAttValue);

        /**
         *
         * @param pstrAttName
         */
                public void removeAttribute(String pstrAttName);

        /*
         * --------- Implementation ---------
         */

        /**
         *
         */
        
        public class GenericDynamicAttributer implements DynamicAttributer {

            private final Map<String, Map<TimeFrame,String>> mapAttributeValues
                    = new HashMap<>();

            /**
             *
             */
            public GenericDynamicAttributer() {
            }

            /**
             *
             * @return
             */
            @Override
            public Set<String> getAttributeNames() {
                return mapAttributeValues.keySet();
            }
            /**
             *
             * @param tf
             * @return
             */
            public Set<String> getAttributeNames(TimeFrame tf) {
                Set<String> setAttributeNames = new HashSet<>();
                for(String attribute : mapAttributeValues.keySet()){
                    if(mapAttributeValues.get(attribute).containsKey(tf)){
                        setAttributeNames.add(attribute);
                    }
                }
                return setAttributeNames;
            }

            /**
             *
             * @param pstrAttName
             * @param tf
             * @return
             * 
             * EDIT HISTORY 
             * 2018-01-25       Talat       Adding conditions before retrieving
             */
            @Override
            public String getAttributeValue(String pstrAttName, TimeFrame tf) {
                if(mapAttributeValues.containsKey(pstrAttName)){
                    if(mapAttributeValues.get(pstrAttName).containsKey(tf)){
                        return mapAttributeValues.get(pstrAttName).get(tf);
                    }
                }
                return null;
            }
            
            /**
             *
             * @param pstrAttName
             * @return
             
            @Override
            public List<String> getAttributeValues(String pstrAttName) {
                List<String> allValues = new ArrayList<>();
                allValues.addAll(mapAttributeValues.get(pstrAttName).values());
                
                return allValues;
            }
            */
            
            /**
             * Retrieves the values of an attribute as a Map between the timeframes and values
             * @param pstrAttName
             * @return A Map containing the timeframes and values in each timeframe
             * @author Talat
             * @since 2018-02-02
             */
            @Override
            public Map<TimeFrame, String> getAttributeValues(String pstrAttName) {
                
                if(mapAttributeValues.containsKey(pstrAttName)) {
                    return mapAttributeValues.get(pstrAttName);
                }
                return null;
            }

            /**
             *
             * @param pstrAttName
             * @param pAttValue
             * @param tf
             */
            @Override
            public void addAttribute(String pstrAttName, 
                                        String pAttValue,
                                        TimeFrame tf) {
                if (!mapAttributeValues.containsKey(pstrAttName)) {
                    mapAttributeValues.put(pstrAttName, new HashMap<>());
                }
                mapAttributeValues.get(pstrAttName).put(tf, pAttValue);
            }

            /**
             *
             * @param pstrAttName
             */
            @Override
            public void removeAttribute(String pstrAttName) {
                mapAttributeValues.remove(pstrAttName);
            }
        }
    }
    
    /*
     * --------- Interface ---------
     */

    /**
     *
     */
    
    interface SysDynamicAttributer {

        /**
         * Replaces the previous value for the attribute, 
         * if there exists one.
         * @param pstrAttName
         * @param pAttValue
         * @param pdtTime
         * @param pTimeframe 
         */
        public void addAttributeValue(String pstrAttName,
                String pAttValue,
                Date pdtTime,
                TimeFrame pTimeframe);
        
        /**
         * Appends the new value  to the existing value.
         * @param pstrAttName
         * @param pAttValue
         * @param pdtTime
         * @param pTimeframe 
         */
        public void appendAttributeValue(String pstrAttName,
                String pAttValue,
                Date pdtTime,
                TimeFrame pTimeframe);
        
        /**
         *
         * @return
         */
        public Set<String> getAttributeNames();

        /**
         *
         * @param pstrAttName
         * @param pTimeframe
         * @return
         */
        public String getAttributeValue(String pstrAttName,
                TimeFrame pTimeframe);
        
        /**
         *
         * @param pstrAttName
         * @return
         */
        public Map<TimeFrame, String> getAttributeValues(String pstrAttName);
        
        /**
         *
         * @param attName
         * @param pTimeframe
         * @return
         */
        public Date getAttributeUpdateTime(String attName,
                TimeFrame pTimeframe);
        
        /**
         *
         * @param pstrAttName
         */
        public void removeAttribute(String pstrAttName);
        
        /**
         *
         * @param pstrAttName
         * @param tf
         */
        public void removeAttributeValue(String pstrAttName, TimeFrame tf);

        /**
         *
         */
        public class SystemDynamicAttributer implements SysDynamicAttributer {

            /* attName --> {timeframe --> attUpdateTime} */

            /**
             *
             */
            
            public static HashMap<String, HashMap<TimeFrame, Date>> hmpAttTime
                    = new HashMap<>();

            /* attName --> {timeframe , attValue} */
            private final HashMap<String, HashMap<TimeFrame, String>> mapAttributes
                    = new HashMap<>();

            /**
             *
             */
            public SystemDynamicAttributer() {
            }

            /**
             *
             * @return
             */
            @Override
            public Set<String> getAttributeNames() {
                return mapAttributes.keySet();
            }
            /**
             *
             * @param tf
             * @return
             */
            public Set<String> getAttributeNames(TimeFrame tf) {
                Set<String> setAttributeNames = new HashSet<>();
                for(String attribute : mapAttributes.keySet()){
                    if(mapAttributes.get(attribute).containsKey(tf)){
                        setAttributeNames.add(attribute);
                    }
                }
                return setAttributeNames;
            }
            /**
             *
             * @param pstrAttName
             * @param pTimeframe
             * @return
             */
            @Override
            public synchronized String getAttributeValue(String pstrAttName,
                    TimeFrame pTimeframe) {
                if(mapAttributes.containsKey(pstrAttName)){
                    if(mapAttributes.get(pstrAttName).containsKey(pTimeframe)){
                        return mapAttributes.get(pstrAttName).get(pTimeframe);
                    }
                }
                return null;
            }
            
            /**
             *
             * @param pstrAttName
             * @return
             */
            @Override
            public synchronized Map<TimeFrame, String> getAttributeValues(String pstrAttName) {
                return mapAttributes.get(pstrAttName) ;
            }

            @Override
            public synchronized void addAttributeValue(String pstrAttName,
                    String pAttValue,
                    Date pdtTime,
                    TimeFrame pTimeframe) {

                if (!hmpAttTime.containsKey(pstrAttName)) {
                    hmpAttTime.put(pstrAttName, new HashMap<>());
                    mapAttributes.put(pstrAttName, new HashMap<>());
                }
                if (!mapAttributes.containsKey(pstrAttName)) {
                    mapAttributes.put(pstrAttName, new HashMap<>());
                }
                if (!hmpAttTime.get(pstrAttName).containsKey(pTimeframe)
                        || (hmpAttTime.get(pstrAttName).containsKey(pTimeframe) &&
                        pdtTime != null &&
                        !hmpAttTime.get(pstrAttName).get(pTimeframe)
                        .after(pdtTime))) {

                    hmpAttTime.get(pstrAttName).put(pTimeframe, pdtTime);
                    
                    mapAttributes.get(pstrAttName).put(pTimeframe, pAttValue);
//                    System.out.println("1. " + mapAttributes);
//                    System.out.println("2. " + mapAttributes.get(pstrAttName));
                }
            }
            /**
             * add multiple values of an attribute e.g. sys:com:"com1, com2"
             * 
             */
                
            @Override
            public synchronized void appendAttributeValue(String pstrAttName,
                    String pAttValue,
                    Date pdtTime,
                    TimeFrame pTimeframe) {

                if (!hmpAttTime.containsKey(pstrAttName) ||
                        !hmpAttTime.get(pstrAttName).containsKey(pTimeframe) ||
                        !mapAttributes.containsKey(pstrAttName) ||
                        !mapAttributes.get(pstrAttName).containsKey(pTimeframe)) {
                    
                    addAttributeValue(pstrAttName, 
                            pAttValue, 
                            pdtTime, 
                            pTimeframe);
                    
                } else {
                    StringBuilder newAttValue = new StringBuilder(
                            mapAttributes.get(pstrAttName).get(pTimeframe));
                    newAttValue.append(MeerkatSystem.DELIMITER_CSV).append(pAttValue);
                    mapAttributes.get(pstrAttName)
                            .put(pTimeframe, newAttValue.toString());
                    hmpAttTime.get(pstrAttName).put(pTimeframe, pdtTime);
                }
            }
            
            /**
             *
             * @param attName
             * @param pTimeframe
             * @return
             */
            @Override
            public Date getAttributeUpdateTime(String attName,
                    TimeFrame pTimeframe) {
                return hmpAttTime.get(attName).get(pTimeframe);
            }

            /**
             *
             * @param pstrAttName
             */
            @Override
            public void removeAttribute(String pstrAttName) {
                mapAttributes.remove(pstrAttName);
                hmpAttTime.remove(pstrAttName);
            }
            
            /**
             *
             * @param pstrAttName
             * @param tf
             */
            @Override
            public void removeAttributeValue(String pstrAttName, TimeFrame tf) {
                //added this method to remove the attribute values specific to timeframe
                // happens when the mining results are cleared for a specific timeframe
                if (mapAttributes.containsKey(pstrAttName)){
                    if(mapAttributes.get(pstrAttName).containsKey(tf)){
                        mapAttributes.get(pstrAttName).remove(tf);
                        hmpAttTime.get(pstrAttName).remove(tf);
                    }
                }                
            }

            /**
             *
             * @param attName
             * @param tf
             * @return
             */
            public boolean containsAttributeAtTimeFrame(String attName, TimeFrame tf) {
                return (mapAttributes.containsKey(attName) &&
                        mapAttributes.get(attName).containsKey(tf));
            }
        }
    }

}
