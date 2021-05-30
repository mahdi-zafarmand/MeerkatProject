/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.utilities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *  Class Name      : KeyFunctionality
 *  Created Date    : 2016-06-02
 *  Description     : Different functionalities for Key Press
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class KeyPressFunctionality {
    
    /**
     *  Method Name     : CloseOnESC()
     *  Created Date    : 2016-06-02
     *  Description     : Closes the dialog window on pressing the ESCAPE key
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstgWindowStage : Stage
     *  @param pscnWindowScene : Scene
     *  @param pKeyCode : KeyCode
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void CloseWindowOnKeyPress(Stage pstgWindowStage, Scene pscnWindowScene, KeyCode pKeyCode) {
        try {
            pscnWindowScene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent keyEvent) -> {
                if (keyEvent.getCode().equals(pKeyCode)) {
                    pstgWindowStage.close();
                }
            });
        } catch (Exception ex) {
            System.out.println("CloseonEsc.AddESCFunctionality(): ");
            ex.printStackTrace();
        }
    }
}
