/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *  Class Name      : AppConfig
 *  Created Date    : 2015-07-xx
 *  Description     : List of all the configuration tags, attributes and the xml file path
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-08-26      Talat           Added BIZCONF_XML_PATH (file path)
 *  2018-01-24      Talat           Added Default Project
 * 
*/
public class AppConfig {

    /* Path for the configuration XML file */
    public static String CONF_XML_PATH = "conf/conf.xml";
    public static String SETTINGS_XML_PATH_DEFAULT = "conf/settings.xml" ;
    public static String SETTINGS_XML_PATH_USER = "userConf/settings.xml";
    public static String BIZCONF_XML_PATH = "conf/bizconf.xml";
    public static String LANGUAGES_PATH = "lang/";
    public static String DIR_HTMLFILES = "/analysisscreen/html/";
    
    /* PATHS */
    public static String DIR_DISPLAY_GRAPH = "src/analysisscreen/html/output/";
    public static String DIR_DISPLAYGRAPH_HTML = "output/";
    public static String DIR_PROJECT = "projects" + File.separator;
    
    /* FILE EXTENSIONS */
    public static String EXTENSION_DISPLAY_OUTPUT = ".json";
    public static String EXTENSION_XML = ".xml";
    public static String EXTENSION_PROJECTFILE = ".mprj";
    public static String EXTENSION_GRAPHFILE = ".meerkat";
    
    // Time Intervals
    // public static int INFO_TIMEOUT = 2;
    
    public static String DELIMITER_COMMA = ",";
    public static String DELIMITER_COLON = ";";
    public static String DELIMITER_SEMICOLON = ":";
    public static String DELIMITER_SPACE = " ";
    public static String DELIMITER_TAB = "\t";
    public static String DELIMITER_NEWLINE = "\n";
    
    
    /* Strings that are available in the conf xml */  
    public static String DEFAULT_LANG_TAG = "defaultlang";
    public static String DEFAULT_PROJECT_TAG = "defaultproject";
    public static String APPLICATION_ROOT_TAG = "app";
    public static String DEBUG_TAG = "debug";
        
    public static String SCREENZERO_TITLE_TAG = "screenzerotitle";
    public static String SCREENZERO_LOGOPATH_TAG = "screenzerologopath";
    
    public static String LANGUAGE_ROOT_TAG = "Lang";
    // public static String SETTINGS_ROOT_TAG = "Settings";
    public static String MENU_TAG = "Menu";
    public static String MENUOPTION_TAG = "MenuOption";
    public static String MENUITEM_TAG = "MenuItem";
    
    public static String MENUBAR_ANALYSIS_TAG = "MenuBar";
    public static String MENUBAR_OPTION_ANALYSIS_TAG = "MenuBarOption";
    

    public static String PROJECT_TITLE_TAG = "ProjectScreenTitle";
    
    public static String ATTRIBUTE_VALUE_TAG = "value";    
    public static String ATTRIBUTE_CLASS_TAG = "class";
    public static String ATTRIBUTE_ICON_TAG = "icon";
    public static String ATTRIBUTE_TEXT_TAG = "text";
    public static String ATTRIBUTE_MNEMONIC_TAG = "mnemonicParsing";
    public static String ATTRIBUTE_CHECKMENUITEM_TAG = "check";
    public static String ATTRIBUTE_DISABLED_TAG = "disabled";
    public static String ATTRIBUTE_ID_TAG = "id";
    public static String ATTRIBUTE_TYPE_TAG = "type";
    public static String ATTRIBUTE_SHORTCUT_TAG = "shortcut";
    public static String ATTRIBUTE_PARAMETER_TAG = "parameter";
    public static String ATTRIBUTE_IDMAPPINGPARAMETER_TAG = "idmappingparameter" ;
    public static String ATTRIBUTE_SEPARATORCOUNT_TAG = "SeparatorCount" ;
    
    public final static String LAYOUTGROUPTYPE_STANDARD = "Standard" ;
    public final static String LAYOUTGROUPTYPE_METRIC = "Metric" ;
    public final static String LAYOUTGROUPTYPE_COMMUNITY = "Community" ;
    
    
    
    
    public static String XML_LANGUAGE_FILE = "";
    
    public static String PROJECTFILE_DELIMITER = ":";    
    public static String GRAPHLIST_DELIMITER = ",";
    
    // Supported Files
    public static String SUPPORTEDFILES_TAG = "supportedfiles";
    public static String FILETYPE_TAG = "filetype";
    public static String ATTRIBUTE_EXTENSION_TAG = "extension";
    
    public static String SUPPORTEDEXPORTEDFILES_TAG = "exportformats"  ;
    
    // Default Settings 
    // public static double DEFAULT_VERTEX_SIZE = 5;
    
    
    /* VARIABLES */    
    //TODO change default lang to null later on!
    private static String strDefaultLang = "English";
    private static String strDefaultProject ;
    private static boolean blnDebug ;
    private static String strScreenZeroTitle ;
    private static String strScreenZeroLogoPath;
    
    // Only for the initialization
    public static boolean IsLabelEnabled = false ;
    public static boolean IsTooltipEnabled = false ;
    
    
    
    /* GETTERS AND SETTERS */    
    public static String getDefaultLang(){
        return AppConfig.strDefaultLang;
    }
    public static void setDefaultLang(String pstrDefaultLang){
        AppConfig.strDefaultLang=pstrDefaultLang;
    }
    
    public static String getDefaultProject(){
        return AppConfig.strDefaultProject;
    }
    public static void setDefaultProject(String pstrDefaultProject){
        AppConfig.strDefaultProject=pstrDefaultProject;
    }
    
    public static boolean getDebug(){
        return AppConfig.blnDebug;
    }
    public static void setDebug(boolean pblnDebug){
        AppConfig.blnDebug = pblnDebug;
    }
    
    public static String getScreenZeroTitle(){
        return AppConfig.strScreenZeroTitle;
    }
    public static void setScreenZeroTitle(String pstrScreenZeroTitle){
        AppConfig.strScreenZeroTitle=pstrScreenZeroTitle;
    }
    
    public static String getScreenZeroLogoPath(){
        return AppConfig.strScreenZeroLogoPath;
    }
    public static void setScreenZeroLogoPath(String pstrScreenZeroLogoPath){
        AppConfig.strScreenZeroLogoPath=pstrScreenZeroLogoPath;
    }
    
    public static String getSETTINGS_XML_PATH(){
        File fileSettinsUser = new File(SETTINGS_XML_PATH_USER);
        if(!fileSettinsUser.exists()){
            try {
                //create the file and copy the contents from default settings file in conf/settings.xml to this file
                fileSettinsUser.createNewFile();
                copyFileFromJar(fileSettinsUser, SETTINGS_XML_PATH_DEFAULT);
                return SETTINGS_XML_PATH_USER;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
            return SETTINGS_XML_PATH_USER;
        }
        return SETTINGS_XML_PATH_DEFAULT;
    }
    
    private static void copyFileFromJar(File newFile, String pathJarFile){
        InputStream inputStream = null;
	OutputStream outputStream = null;
        

	try {
		// read this file into InputStream
		inputStream = AppConfig.class.getResourceAsStream(pathJarFile);

		// write the inputStream to a FileOutputStream
		outputStream =
                    new FileOutputStream(newFile);

		int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}

	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (outputStream != null) {
			try {
				// outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
    }
    
}
