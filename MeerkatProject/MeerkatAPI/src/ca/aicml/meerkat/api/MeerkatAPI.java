/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api;

import java.util.Map;
import main.meerkat.MeerkatBIZ;

/**
 *
 * @author mahdi
 */
public class MeerkatAPI {
    public static boolean loadMeerkat(boolean serverProcessing, boolean dataServer) {
        return true;
    }
    
    public static boolean initializeMeerkat(String pstrOutputDirectory, Map<String, String> pmapConfig) {
        MeerkatBIZ applicationInstance = MeerkatBIZ.getMeerkatApplication();
        applicationInstance.updateConfig(pmapConfig);
        applicationInstance.setOutputDirectory(pstrOutputDirectory);
        return true;
    }
    
    public static boolean closeMeerkat() {   
        return true;
    }
}
