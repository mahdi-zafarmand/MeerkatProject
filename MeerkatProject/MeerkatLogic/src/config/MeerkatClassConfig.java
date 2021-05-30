package config;

import java.util.HashMap;

/**
 *
 * @author aabnar
 */
public class MeerkatClassConfig {
	
    /**
     *
     */
    public static String MEERKAT_TAG = MeerkatSystem.MEERKAT_TAG;

    /**
     *
     */
    public static String LAYOUTS_TAG = MeerkatSystem.LAYOUTS_TAG;

    /**
     *
     */
    public static String COMMUNITYMINING_TAG = MeerkatSystem.COMMUNITYMININGS_TAG;
    
    /**
     *
     */
    public static String SHORTESTPATH_TAG = MeerkatSystem.SHORTESTPATH_TAG;
    
    /**
     *
     */
    public static String LINKPREDICTION_TAG = MeerkatSystem.LINKPREDICTION_TAG;

    /**
     *
     */
    public static String METRICS_TAG = MeerkatSystem.METRICS_TAG;

    /**
     *
     */
    public static String GRAPHS_TAG = MeerkatSystem.GRAPHS_TAG;

    /**
     *
     */
    public static String READERS_TAG = MeerkatSystem.READERS_TAG;

    /**
     *
     */
    public static String WRITERS_TAG = MeerkatSystem.WRITERS_TAG;
	
    HashMap<String, HashMap<String,String>> hmpCatId2ClassMapping = new HashMap<>();
//    HashMap<String, String> hmpClsId2ClsName = new HashMap<>();
	
    /**
     *
     * @param phmpId2ClassMapping
     */
    public MeerkatClassConfig(HashMap<String, HashMap<String,String>> phmpId2ClassMapping) {
            this.hmpCatId2ClassMapping = phmpId2ClassMapping;
    }
	
    /**
     *
     * @param pstrCategory
     * @param pstrId
     * @return
     */
    public String getClassName(String pstrCategory, String pstrId) {
        // Debug(MeerkatClassConfig.getClassName(): ); // #Debug
        System.out.println("MeerkatClassConfig.getClassName(): Category passed is "+pstrCategory+" with ID "+pstrId);
        if (hmpCatId2ClassMapping.containsKey(pstrCategory)) {
//            System.out.println("--------------------------------    MeerkatClassConfig.getClassName(): Found the Category: "+pstrCategory);
            if (hmpCatId2ClassMapping.get(pstrCategory).containsKey(pstrId)) {
//                System.out.println("-----------------------------   MeerkatClassConfig.getClassName(): Found the Key: "+pstrId);
//                System.out.println("MeerkatClassConfig.getClassName(): Value: "+hmpCatId2ClassMapping.get(pstrCategory).get(pstrId));
                return hmpCatId2ClassMapping.get(pstrCategory).get(pstrId);
            } else {
                // TODO: throw function Id does not exist.
            } 
        } else {
            // TODO : throw category does not exist.
        }
        return null;
    }
    
    private void Debug(String pstrCallingMethod) {    
        System.out.println("Debug funciton called in "+pstrCallingMethod);
        for(String strKeyCategory: hmpCatId2ClassMapping.keySet()) {
            System.out.println("Category: "+strKeyCategory);            
            for(String strKeyID : hmpCatId2ClassMapping.get(strKeyCategory).keySet()) {
                System.out.println("\tID: "+strKeyID+"\tValue: "+hmpCatId2ClassMapping.get(strKeyCategory).get(strKeyID));
            }
        }
    }
}
