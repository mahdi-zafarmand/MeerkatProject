package io.utility;

import config.MeerkatClassConfig;
import io.parser.ConfigParser;
import java.util.HashMap;


/**
 *  Class Name      : ConfigLoader
 *  Created Date    : 2015-07-xx
 *  Description     : Loader that loads the config file which contains the mapping between IDs and attributes
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-08-26      Talat           Since the config file is an xml, the xml parser is used instead of parsing it line by line
 *  2015-08-26      Talat           The parsing is moved into a Class called ConfigParser in io.parser
 *  2015-08-26      Talat           ReadElement() is removed as it is not used anymore (Check the commented section)
 * 
*/
public class ConfigLoader{
	
    static HashMap<String, HashMap<String,String>> hmpId2ClassMapping = new HashMap<>();

    /**
     *  Method Name     : loadConfig
     *  Created Date    : 2015-08-26
     *  Description     : Used to load the Configuration File of the Business Layer
     *  Version         : 2.0
     *  @author         : Talat
     * 
     *  @param pstrConfigFilePath
     *  @return MeerkatClassConfig
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static MeerkatClassConfig loadConfig(String pstrConfigFilePath) { 
        hmpId2ClassMapping = ConfigParser.Parse(pstrConfigFilePath);
        return new MeerkatClassConfig(hmpId2ClassMapping);
    }
    
    /**
     *
     * @param pstrCategory
     * @param pstrKey
     * @return
     */
    public static String getClassMapping(String pstrCategory, String pstrKey) {
        return hmpId2ClassMapping.get(pstrCategory).get(pstrKey);
    }
}
