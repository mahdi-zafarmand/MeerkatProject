/*
 * meerkat@aicml june 2015
 */
package ca.aicml.meerkat.api;

import java.util.Map;
import main.meerkat.MeerkatBIZ;

/**
 * MeerkatAPI is responsible to diverting the general calls regarding 
 * the whole Meerkat application from Meerkat UI to Meerkat Back-end.
 * 
 * @author aabnar
 */
public class MeerkatAPI {
    
    /**
     * loadMeerkat will run the meerkat back-end code either on the 
     * local machine or will establish connection with Meerkat Server
     * depending on the parameter values.
     * @param serverProcessing
     *              the boolean parameter that determines if meerkat back-end
     *              will run locally or on meerkat server.
     * @param dataServer 
     *              the boolean parameter that determines if data structure
     *              will be kept locally or on Meerkat server.
     * 
     * @return boolean 
     * 
     */
    public static boolean loadMeerkat(boolean serverProcessing, boolean dataServer) {
        
        
        return true;
    }
    
    public static boolean initializeMeerkat(String pstrOutputDirectory, Map<String, String> pmapConfig) {
        MeerkatBIZ applicationInstance = MeerkatBIZ.getMeerkatApplication();
        applicationInstance.updateConfig(pmapConfig);
        applicationInstance.setOutputDirectory(pstrOutputDirectory);
        return true;
    }
    
    /**
     * closeMeerkat will be called upon close of the application to 
     * handle memory, data structure, processing associated with the 
     * application as well as updating log files.
     * 
     * @return boolean 
     */
    public static boolean closeMeerkat() {
        
        return true;
    }
}
