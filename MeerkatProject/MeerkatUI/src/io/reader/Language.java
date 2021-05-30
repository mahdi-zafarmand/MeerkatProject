/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.reader;

import config.AppConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aicml_adm
 */
public class Language {
    public static List<String> AvailableLanguages() {
        List<String> lstLanguages = new ArrayList<>();
        File folder = new File(AppConfig.LANGUAGES_PATH);
        File[] listOfFiles = folder.listFiles();			
        for (File file : listOfFiles) {
            if (file.isFile()) {
                lstLanguages.add(file.getName());
            }
        }
        
        return lstLanguages;
    }
}
