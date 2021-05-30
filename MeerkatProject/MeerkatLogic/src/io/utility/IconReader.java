/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.utility;

import config.MeerkatSystem;
import java.util.List;

/**
 *
 * @author talat
 */
public class IconReader {
    
    /**
     * Retrieves all the icons in the resources folder
     * @return List<String>
     * @since 2018-02-07
     * @author Sankalp/Talat
     */
    public static List<String> getAllIcons(){
        String iconPath = MeerkatSystem.PROJECT_RESOURCES_DIR + MeerkatSystem.PROJECT_ICONS_DIR;
        return Utilities.getAllFilesInDirectory(iconPath);
    }
    
}
