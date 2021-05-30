package datastructure.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import datastructure.core.StaticAttributable.StaticAttributer.GenericStaticAttributer;
import datastructure.core.StaticAttributable.SysStaticAttributer.SystemStaticAttributer;
import java.util.Date;

/**
 * An interface giving an object the ability to store multiple attributes.
 *
 * @author Matt Gallivan
 *
 * -------------- Edit history 
 * Date             Author          Description 
 * 2015-11-24       Afra            - added Timeframe to generalAttributer.
 * November 2015    Afra            - Added timeframe to system attributes.
 * 2015-09-22       Talat           Using Diamond Interface where possible 
 * 2015-09-22       Talat           Adding method getAttributeNames() 
 * july 14, 2015    aabnar          added SystemAttributer to include
 *                                  numerical values computed by meerkat. 
 * july 14th,2015   aabnar          change from IAttribute to (attName, attValue).
 */
public interface StaticAttributable {

    /**
     *
     * @return
     */
    public GenericStaticAttributer getUserAttributer();

    /**
     *
     * @return
     */
    public SystemStaticAttributer getSystemAttributer();

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
    
    interface StaticAttributer {

        /**
         *
         * @return
         */
        public Set<String> getAttributeNames();

        /**
         *
         * @param pstrAttName
         * @return
         */
        public String getAttributeValue(String pstrAttName);

        /**
         *
         * @param pstrAttName
         * @param pAttValue
         */
        public void addAttribute(String pstrAttName, String pAttValue);
        
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
        
        public class GenericStaticAttributer implements StaticAttributer {

            private final Map<String, String> mapAttributeValues
                    = new HashMap<>();

            /**
             *
             */
            public GenericStaticAttributer() {
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
             * @param pstrAttName
             * @return
             */
            @Override
            public String getAttributeValue(String pstrAttName) {
                return mapAttributeValues.get(pstrAttName);
            }
            
            /**
             *
             * @param pstrAttName
             * @param pAttValue
             */
            @Override
            public void addAttribute(String pstrAttName, String pAttValue) {
                mapAttributeValues.put(pstrAttName, pAttValue);
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
    
    interface SysStaticAttributer {

        /**
         *
         * @param pstrAttName
         * @param pAttValue
         * @param pdtTime
         */
        public void addAttribute(String pstrAttName,
                String pAttValue,
                Date pdtTime);
        
        /**
         *
         * @return
         */
        public Set<String> getAttributeNames();

        /**
         *
         * @param pstrAttName
         * @return
         */
        public String getAttributeValue(String pstrAttName);
        
        /**
         *
         * @param attName
         * @return
         */
        public Date getAttributeUpdateTime(String attName);
        
        /**
         *
         * @param pstrAttName
         */
        public void removeAttribute(String pstrAttName);

        /**
         *
         */
        public class SystemStaticAttributer implements SysStaticAttributer {

            /* attName --> attUpdateTime */

            /**
             *
             */
            
            public static HashMap<String, Date> hmpAttTime = new HashMap<>();

            /* attName --> attValue */
            private final HashMap<String, String> mapAttributes
                    = new HashMap<>();

            /**
             *
             */
            public SystemStaticAttributer() {
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
             * @param pstrAttName
             * @return
             */
            @Override
            public synchronized String getAttributeValue(String pstrAttName) {
                return mapAttributes.get(pstrAttName);
            }
            
            /**
             *
             * @param pstrAttName
             * @param pAttValue
             * @param pdtTime
             */
            @Override
            public synchronized void addAttribute(String pstrAttName,
                    String pAttValue,
                    Date pdtTime) {

                
                hmpAttTime.put(pstrAttName, pdtTime);
                mapAttributes.put(pstrAttName, pAttValue);
            }

            /**
             *
             * @param attName
             * @return
             */
            @Override
            public Date getAttributeUpdateTime(String attName) {
                return hmpAttTime.get(attName);
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
        }
    }

}
