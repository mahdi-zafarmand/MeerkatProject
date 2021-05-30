/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysismenubar.MenuDisableConfig.MenuDisablityLevel;
import config.AppConfig;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

/**
 *  Class Name      : MenuItemGeneric
 *  Created Date    : 2015-07-xx
 *  Description     : A Generic MenuItem for the MenuBar
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-06-13      Talat           Added Separators
 *  2016-06-06      Talat           Changed the disabled from boolean 
 *  2016-01-29      Talat           Added the functionality of Mnemonics and Shortcuts
 * 
*/
public class MenuItemGeneric {

    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    protected String strMenuItemClass ;
    protected String strDisplayText ;
    protected String strIconPath;
    protected boolean blnMnemonicParsing ;
    protected boolean blnCheckMenuItem ;
    // protected boolean blnDisabled ;
    protected MenuDisablityLevel levelDisabledLevel ;
    protected String strParameter ;
    protected int intSeparators ;
    
    // Used to parse the shortcuts
    protected List<String> lststrKeyCombinations ;
    protected KeyCode chrKeyCode ;
    protected KeyCodeCombination keyCombination ;
    
    private static String strShift = "shift" ;
    private static String strCtrl = "ctrl" ;
    private static String strAlt = "alt" ;
       
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    /**
     *  Constructor Name: MenuItemGeneric()
     *  Created Date    : 2015-07-xx
     *  Description     : Constructor for MenuItemGeneric to initialize all the fields
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrDisplayName : String
     *  @param pstrMenuItemClass : String
     *  @param pstrIconPath : String
     *  
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description     *  
     * 
    */
    public MenuItemGeneric(String pstrDisplayName, String pstrMenuItemClass, String pstrIconPath) {
        this.strDisplayText = pstrDisplayName;
        this.strMenuItemClass = pstrMenuItemClass ;
        this.strIconPath = pstrIconPath;
        this.blnMnemonicParsing = false;
    }
    
    /**
     *  Constructor Name: MenuItemGeneric()
     *  Created Date    : 2015-07-xx
     *  Description     : Constructor for MenuItemGeneric to initialize all the fields
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrDisplayName : String
     *  @param pstrMenuItemClass : String
     *  @param pstrIconPath : String
     *  @param pblnMenmonicParsing : boolean
     *  
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description     *  
     * 
    */
    public MenuItemGeneric(String pstrDisplayName, String pstrMenuItemClass, String pstrIconPath, boolean pblnMenmonicParsing) {
        this(pstrDisplayName, pstrMenuItemClass, pstrIconPath);
        this.blnMnemonicParsing = pblnMenmonicParsing;
    }
    
    /**
     *  Constructor Name: MenuItemGeneric()
     *  Created Date    : 2015-07-xx
     *  Description     : Constructor for MenuItemGeneric to initialize all the fields
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrDisplayName : String
     *  @param pstrMenuItemClass : String
     *  @param pstrIconPath : String
     *  @param pblnMenmonicParsing : boolean
     *  @param pblnCheckMenuItem : boolean     *  
     *  @param pintDisabledLevel : int
     *  @param pstrKeyCombination : String
     *  @param pstrParameter : String
     *  @param pintSeparator : int
     *  
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description     *  
     *  2016-06-13      Talat           Added a separator 
    */
    public MenuItemGeneric(
              String pstrDisplayName
            , String pstrMenuItemClass
            , String pstrIconPath
            , boolean pblnMenmonicParsing
            , boolean pblnCheckMenuItem
            //boolean pblnIsDisabled, 
            , int pintDisabledLevel
            , String pstrKeyCombination
            , String pstrParameter
            , int pintSeparator
    ) {
        this(pstrDisplayName, pstrMenuItemClass, pstrIconPath, pblnMenmonicParsing);
        this.blnCheckMenuItem = pblnCheckMenuItem;        
        this.parseKeyCombinations(pstrKeyCombination);
        this.strParameter = pstrParameter ;
        this.intSeparators = pintSeparator ;
        
        switch (pintDisabledLevel) {
            case 0 : 
                this.levelDisabledLevel = MenuDisablityLevel.NOTHING ;
                break ;
            case 1: 
                this.levelDisabledLevel = MenuDisablityLevel.PROJECT_AVAILABLE ;
                break ;
            case 2 : 
                this.levelDisabledLevel = MenuDisablityLevel.GRAPH_AVAILABLE ;
                break ;
            case 3 : 
                this.levelDisabledLevel = MenuDisablityLevel.TEXTUAL_AVAILABLE ;
                break ;
            default :
                this.levelDisabledLevel = MenuDisablityLevel.NOTHING ;
                break ;
        }
    }
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public String getItemClass() {
        return this.strMenuItemClass;
    }
    public void setItemName(String pstrMenuItemClass) {
        this.strMenuItemClass = pstrMenuItemClass;
    }
    
    public String getDisplayName() {
        return this.strDisplayText;
    }
    public void setDisplayName(String pstrDisplayName) {
        this.strDisplayText = pstrDisplayName;
    }   
    
    public String getIconPath() {
        return this.strIconPath;
    }    
    public void setIconPath(String pstrIconPath) {
        this.strIconPath = pstrIconPath;
    }
    
    public boolean getMnemonicParsing(){
        return this.blnMnemonicParsing;
    }
    private void setMnemonicParsing(boolean pblnValue){
        this.blnMnemonicParsing = pblnValue;
    }
    
    public boolean IsCheckMenuItem(){
        return this.blnCheckMenuItem;
    }
    public void setCheckMenuItem(boolean pblnValue){
        this.blnCheckMenuItem = pblnValue;
    }
    
    public KeyCode getKeyCode() {
        return this.chrKeyCode ;
    }
    
    public MenuDisablityLevel getDisabledLevel() {
        return this.levelDisabledLevel ;
    }
    
    public String getParameter() {
        return this.strParameter ;
    }
    
    public int getSeparator() {
        return this.intSeparators ;
    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : getKeyCodeCombination()
     *  Created Date    : 2016-01-29
     *  Description     : Gets the KeyCode combination for the shortcut to be implemented
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return KeyCodeCombination
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public KeyCodeCombination getKeyCodeCombination() {
        if (chrKeyCode == null){
            return null ;
        }
        
        if (lststrKeyCombinations.contains(strShift) && lststrKeyCombinations.contains(strAlt) && lststrKeyCombinations.contains(strCtrl)) {
            keyCombination = new KeyCodeCombination(chrKeyCode, KeyCodeCombination.SHIFT_DOWN, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.ALT_DOWN) ;
        } else if (lststrKeyCombinations.contains(strShift) && lststrKeyCombinations.contains(strAlt)) {
            keyCombination = new KeyCodeCombination(chrKeyCode, KeyCodeCombination.SHIFT_DOWN, KeyCodeCombination.ALT_DOWN) ;
        } else if (lststrKeyCombinations.contains(strShift) && lststrKeyCombinations.contains(strCtrl)) {
            keyCombination = new KeyCodeCombination(chrKeyCode, KeyCodeCombination.SHIFT_DOWN, KeyCodeCombination.CONTROL_DOWN) ;
        } else if (lststrKeyCombinations.contains(strAlt) && lststrKeyCombinations.contains(strCtrl)) {
            keyCombination = new KeyCodeCombination(chrKeyCode, KeyCodeCombination.SHIFT_DOWN, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.ALT_DOWN) ;
        } else if (lststrKeyCombinations.contains(strShift)) {
            keyCombination = new KeyCodeCombination(chrKeyCode, KeyCodeCombination.SHIFT_DOWN);
        } else if (lststrKeyCombinations.contains(strAlt)) {
            keyCombination = new KeyCodeCombination(chrKeyCode, KeyCodeCombination.ALT_DOWN);
        } else if (lststrKeyCombinations.contains(strCtrl)) {
            keyCombination = new KeyCodeCombination(chrKeyCode, KeyCodeCombination.CONTROL_DOWN);
        } 
        
        return keyCombination;
    }
    
    /**
     *  Method Name     : parseKeyCombinations()
     *  Created Date    : 2016-01-29
     *  Description     : Parses the Key combination to select a Menu option based on the input in language xml config file
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrCombinations
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void parseKeyCombinations (String pstrCombinations){
        String [] arrCombinations = pstrCombinations.split(AppConfig.DELIMITER_COMMA) ;
        int intKeyCodeFound = 0;
        lststrKeyCombinations = new ArrayList<>();
        
        for (String strKeyPress : arrCombinations) {
            strKeyPress = strKeyPress.toLowerCase().trim() ;
            
            // If it is one among the key combinations
            if (strKeyPress.equals(strShift) || strKeyPress.equals(strCtrl) || strKeyPress.equals(strAlt)) { 
                lststrKeyCombinations.add(strKeyPress);
            } else {
                // Only one key code is allowed. Will allow in future for multiple keycodes
                // A keycode is any character (aphanumeric or special character including tab and space)
                if (intKeyCodeFound == 0) {
                    intKeyCodeFound ++ ;
                    switch (strKeyPress){
                        case "a" :
                            chrKeyCode = chrKeyCode.A ;
                            break ;
                        case "b" :
                            chrKeyCode = chrKeyCode.B ;
                            break ;
                        case "c" :
                            chrKeyCode = chrKeyCode.C ;
                            break ;
                        case "d" :
                            chrKeyCode = chrKeyCode.D ;
                            break ;
                        case "e" :
                            chrKeyCode = chrKeyCode.E ;
                            break ;
                        case "f" :
                            chrKeyCode = chrKeyCode.F ;
                            break ;
                        case "g" :
                            chrKeyCode = chrKeyCode.G ;
                            break ;
                        case "h" :
                            chrKeyCode = chrKeyCode.H ;
                            break ;
                        case "i" :
                            chrKeyCode = chrKeyCode.I ;
                            break ;
                        case "j" :
                            chrKeyCode = chrKeyCode.J ;
                            break ;
                        case "k" :
                            chrKeyCode = chrKeyCode.K ;
                            break ;
                        case "l" :
                            chrKeyCode = chrKeyCode.L ;
                            break ;
                        case "m" :
                            chrKeyCode = chrKeyCode.M ;
                            break ;
                        case "n" :
                            chrKeyCode = chrKeyCode.N ;
                            break ;
                        case "o" :
                            chrKeyCode = chrKeyCode.O ;
                            break ;
                        case "p" :
                            chrKeyCode = chrKeyCode.P ;
                            break ;
                        case "q" :
                            chrKeyCode = chrKeyCode.Q ;
                            break ;
                        case "r" :
                            chrKeyCode = chrKeyCode.R ;
                            break ;
                        case "s" :
                            chrKeyCode = chrKeyCode.S ;
                            break ;
                        case "t" :
                            chrKeyCode = chrKeyCode.T ;
                            break ;
                        case "u" :
                            chrKeyCode = chrKeyCode.U ;
                            break ;
                        case "v" :
                            chrKeyCode = chrKeyCode.V ;
                            break ;
                        case "w" :
                            chrKeyCode = chrKeyCode.W ;
                            break ;
                        case "x" :
                            chrKeyCode = chrKeyCode.X ;
                            break ;
                        case "y" :
                            chrKeyCode = chrKeyCode.Y ;
                            break ;
                        case "z" :
                            chrKeyCode = chrKeyCode.Z ;
                            break ;
                        
                        case "0" :
                            chrKeyCode = chrKeyCode.DIGIT0 ;
                            break ;
                        case "1" :
                            chrKeyCode = chrKeyCode.DIGIT1 ;
                            break ;
                        case "2" :
                            chrKeyCode = chrKeyCode.DIGIT2 ;
                            break ;
                        case "3" :
                            chrKeyCode = chrKeyCode.DIGIT3 ;
                            break ;
                        case "4" :
                            chrKeyCode = chrKeyCode.DIGIT4 ;
                            break ;
                        case "5" :
                            chrKeyCode = chrKeyCode.DIGIT5 ;
                            break ;
                        case "6" :
                            chrKeyCode = chrKeyCode.DIGIT6 ;
                            break ;
                        case "7" :
                            chrKeyCode = chrKeyCode.DIGIT7 ;
                            break ;
                        case "8" :
                            chrKeyCode = chrKeyCode.DIGIT8 ;
                            break ;
                        case "9" :
                            chrKeyCode = chrKeyCode.DIGIT9 ;
                            break ;
                        
                        
                        case "tab" :
                            chrKeyCode = chrKeyCode.TAB ;
                            break ;
                        case "end" :
                            chrKeyCode = chrKeyCode.END ;
                            break ;
                        case "enter" :
                        case "return":
                            chrKeyCode = chrKeyCode.ENTER ;
                            break ;
                        case "space":
                            chrKeyCode = chrKeyCode.SPACE;
                            break;
                        
                        case "{" :
                            chrKeyCode = chrKeyCode.BRACELEFT ;
                            break ;
                        case "}" :
                            chrKeyCode = chrKeyCode.BRACERIGHT ;
                            break ;
                        case "_" :
                            chrKeyCode = chrKeyCode.UNDERSCORE ;
                            break ;
                        case "=" :
                            chrKeyCode = chrKeyCode.EQUALS ;
                            break ;
                        case "-" :
                            chrKeyCode = chrKeyCode.MINUS ;
                            break ;
                        case "+" :
                            chrKeyCode = chrKeyCode.PLUS ;
                            break ;
                            
                        case "\\" :
                            chrKeyCode = chrKeyCode.BACK_SLASH ;
                            break ;
                        case "/" :
                            chrKeyCode = chrKeyCode.SLASH ;
                            break ;
                        
                        case "f1" :
                            chrKeyCode = chrKeyCode.F1 ;
                            break ;
                        case "f2" :
                            chrKeyCode = chrKeyCode.F2 ;
                            break ;
                        case "f3" :
                            chrKeyCode = chrKeyCode.F3 ;
                            break ;
                        case "f4" :
                            chrKeyCode = chrKeyCode.F4 ;
                            break ;
                        case "f5" :
                            chrKeyCode = chrKeyCode.F5 ;
                            break ;
                        case "f6" :
                            chrKeyCode = chrKeyCode.F6 ;
                            break ;
                        case "f7" :
                            chrKeyCode = chrKeyCode.F7 ;
                            break ;
                        case "f8" :
                            chrKeyCode = chrKeyCode.F8 ;
                            break ;
                        case "f9" :
                            chrKeyCode = chrKeyCode.F9 ;
                            break ;
                        case "f10" :
                            chrKeyCode = chrKeyCode.F10 ;
                            break ;
                        case "f11" :
                            chrKeyCode = chrKeyCode.F11 ;
                            break ;
                        case "f12" :
                            chrKeyCode = chrKeyCode.F12 ;
                            break ;
                            
                        case "'" :
                            chrKeyCode = chrKeyCode.QUOTE ;
                            break ;
                        case "\"" :
                            chrKeyCode = chrKeyCode.QUOTEDBL ;
                            break ;
                        case ":" :
                            chrKeyCode = chrKeyCode.COLON;
                            break ;
                        case ";" :
                            chrKeyCode = chrKeyCode.SEMICOLON;
                            break;
                        default :
                            intKeyCodeFound -- ;
                            break;
                    }
                }
            }
            
        }
    }
}