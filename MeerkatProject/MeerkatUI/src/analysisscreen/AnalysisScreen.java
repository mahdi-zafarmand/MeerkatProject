/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysisscreen;

import config.AnalysisConfig;
import config.AppConfig;
import config.SceneConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import io.parser.AppSettingsXML;
import io.parser.LanguageXML;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ui.utilities.OpenProject;
import java.io.InputStream;
import javafx.fxml.JavaFXBuilderFactory;


/**
 *  Class Name      : AnalysisScreen
 *  Created Date    : 2015-07-22
 *  Description     : The functionalities with respect to the Analysis Screen
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-03-17      Talat           Removed the Overloaded Display method Display(Stage, ProjectTab)
 *                                  Check previously dated versions for old code
 * 
*/
public class AnalysisScreen {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strAnalysisScreenTitle;
    private static String strAnalysisScreenLogoPath ;
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    public static String getTitle() {
        return strAnalysisScreenTitle;
    }
    public static String getIconPath() {
        return strAnalysisScreenLogoPath;
    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2015-08-05
     *  Description     : Sets the parameters of the Analysis Screen such as Title and LogoPath
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrAnalysisScreenTitle : String 
     *  @param pstrAnalysisScreenLogoPath : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
    */
    public static void setParameters(String pstrAnalysisScreenTitle, String pstrAnalysisScreenLogoPath) {
        strAnalysisScreenTitle = pstrAnalysisScreenTitle;
        strAnalysisScreenLogoPath = pstrAnalysisScreenLogoPath;
    }
        
    /**
     *  Method Name     : Display()
     *  Created Date    : 2015-08-05
     *  Description     : Closing of the Welcome screen and creating a new Analysis Screen
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrSelectedLangFile : String - The path of the Language file
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description     *       * 
    */
    public static void Display(String pstrSelectedLangFile) {
        try {
            Stage stgAnalysisWindow = new Stage();            

            String strXMLLangFile = AppConfig.LANGUAGES_PATH+pstrSelectedLangFile;
            AppConfig.XML_LANGUAGE_FILE = strXMLLangFile;
            
            LanguageXML.Parse(strXMLLangFile);
            AppSettingsXML.Parse(AppConfig.SETTINGS_XML_PATH_DEFAULT);

            /* Loading of the FXML file for the Analysis Window - This would also invoke the initalize method of AnalysisController */
            FXMLLoader loader = new FXMLLoader(AnalysisScreen.class.getResource(AnalysisConfig.ANALYSISSCREEN_FXML));

//            System.out.println("AnalysisScreen.Display(): "+loader.getLocation());
            Scene scene = new Scene(loader.load(), 1250, 850);
            stgAnalysisWindow.setScene(scene);
            stgAnalysisWindow.setHeight(850);
            stgAnalysisWindow.setWidth(1250);
//            stgAnalysisWindow.setScene(new Scene(loader.load(), SceneConfig.EVENTCANVAS_WIDTH, SceneConfig.EVENTCANVAS_HEIGHT));

//            stgAnalysisWindow.setWidth(SceneConfig.EVENTCANVAS_WIDTH);
//            stgAnalysisWindow.setHeight(SceneConfig.EVENTCANVAS_HEIGHT);
            
            // Just load and assign the height & width parameters of the tabpane to that statis fields in AppConfig
            AnalysisController aController = loader.getController();
            SceneConfig.TABPANE_WIDTH = aController.getProjectTabPane().getWidth();
            SceneConfig.TABPANE_HEIGHT = aController.getProjectTabPane().getHeight();

            /* Setting Analysis Window Parameters */
            stgAnalysisWindow.setTitle(getTitle());
            stgAnalysisWindow.getIcons().add(new Image(getIconPath()));
            stgAnalysisWindow.setResizable(true);

            AnalysisController controller =  loader.<AnalysisController>getController();             
            // System.out.println("AnalysisScreen.Display(): All the initializations are completed");            

            //activate KeyEvent listener
            controller.ActivateKeyEventListener();

            stgAnalysisWindow.show();

            if (!AppConfig.getDefaultProject().isEmpty()){
                OpenProject.load(controller,AppConfig.getDefaultProject());
            }

            stgAnalysisWindow.setOnCloseRequest((e) -> {
                e.consume();
                controller.updateStatusBar(true, StatusMsgsConfig.APPLICATION_CLOSING);
                                
                MeerkatUI UIInstance = MeerkatUI.getUIInstance();

                if (UIInstance.getProjectTabCount() > 0) {                
                    // ExitDialog.Display(stgAnalysisWindow, controller); // Uncomment this line and check in
                    stgAnalysisWindow.close(); // This line should not be checked in
                } else {
                    stgAnalysisWindow.close();
                }
            });                                    
        } catch (IOException ex) {
            Logger.getLogger(AnalysisScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
