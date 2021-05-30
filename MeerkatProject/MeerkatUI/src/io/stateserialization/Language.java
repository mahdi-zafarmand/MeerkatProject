/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.stateserialization;

import config.AppConfig;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author syedtala
 */
public class Language {
    
    public static void UpdateDefaultLang(String strDefaultLanguage) {

        BufferedReader br = null;
        String strCurrentLine ;
        
        try {
            String strOutput = new String();            
            br = new BufferedReader(new FileReader(AppConfig.CONF_XML_PATH));
            while ((strCurrentLine = br.readLine()) != null) {
                if (strCurrentLine.contains(AppConfig.DEFAULT_LANG_TAG)) {
                    strOutput += "<"+AppConfig.DEFAULT_LANG_TAG+">"+strDefaultLanguage+"</"+AppConfig.DEFAULT_LANG_TAG+">\n";                    
                } else {
                    strOutput += strCurrentLine+"\n" ;
                }
            }
            br.close();
            
            /* Write the updated conf */
            File file = new File(AppConfig.CONF_XML_PATH);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(strOutput);
            bw.close();
        } catch (IOException exIO) {
            System.out.println("Not able to update the Default Language");
        }
    }
    
}
