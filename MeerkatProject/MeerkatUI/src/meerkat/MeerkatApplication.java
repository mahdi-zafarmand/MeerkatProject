/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meerkat;

import analysisscreen.AnalysisScreen;
import ca.aicml.meerkat.api.MeerkatAPI;
import config.AppConfig;
import config.SceneConfig;
import io.parser.ApplicationXML;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import languageselect.LanguageSelect;
import meerkat.registration.MeerkatServerJobs;

/**
 *  Class Name      : MeerkatApplication
 *  Created Date    : 2015-07-02
 *  Description     : The Main MeerkatApplication Class which is the starting point of the application
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class MeerkatApplication extends Application {
    
    Stage parentWindow ;
    
    @Override
    public void start(Stage stage) throws Exception {
        //MeerkatServerJobs.startupRegistrationValidationCheck();
        startMeerkatFx(stage);
        
        //MeerkatServerJobs.checkIfNextVersionReleased();
    
    }
    
    public void startMeerkatFx(Stage stage){
        parentWindow = stage ;
                
        // Read the Application level XML file
        String strAppXMLPath = AppConfig.CONF_XML_PATH;
        ApplicationXML.Parse(strAppXMLPath);

        // Set the icon as the location of this icon is available only after the conf.xml is parsed
        Image imgStageLogo = new Image(AppConfig.getScreenZeroLogoPath());
        parentWindow.getIcons().add(imgStageLogo);

        // If the config file does not contain any default language, list the user with a list of language options available
        List<String> lstLanguages ;

        if (AppConfig.getDefaultLang() == null || AppConfig.getDefaultLang().equalsIgnoreCase("")) {

            lstLanguages = Utilities.AvailableLanguages();

            Scene sceneZero = new Scene(new VBox(), 400, 350);
            parentWindow.setTitle(AppConfig.getScreenZeroTitle());
            // System.out.println(AppConfig.getScreenZeroLogoPath());
            //parentWindow.getIcons().add(new Image(AppConfig.getScreenZeroLogoPath()));
            parentWindow.setScene(sceneZero);
            parentWindow.show();                                    
            LanguageSelect.Display(lstLanguages, parentWindow);

        } else {
            AnalysisScreen.Display(AppConfig.getDefaultLang()+AppConfig.EXTENSION_XML);
        }
        
        /* Bundling of Scene Config variables */
        Map<String, String> mapConfig = new HashMap<>();
        mapConfig.put("VERTEX_COLOR_DEFAULT", SceneConfig.VERTEX_COLOR_DEFAULT);

        /* Initialize the Meerkat BIZ Layer */
        String strOutputDirectoryLocation = new File("").getAbsolutePath()+"/"+AppConfig.DIR_DISPLAY_GRAPH;    
        MeerkatAPI.initializeMeerkat(strOutputDirectoryLocation, mapConfig);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
