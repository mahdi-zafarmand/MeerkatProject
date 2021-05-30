/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.utilities.FXUtils;


/**
 *  Class Name      : ColorChooser
 *  Created Date    : 2016-02-01
 *  Description     : Shows the dialog box to choose a color from
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class ColorChooser {
     
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strTitle ;
    private static String strMsg ;
    private static String strNoProjectError ;
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2016-02-01
     *  Description     : Sets the parameters of the ColorChooser
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrTitle : String
     *  @param pstrMsg : String
     *  @param pstrNoProjectError : String 
     *  
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters (String pstrTitle, String pstrMsg, String pstrNoProjectError) {
        // System.out.println("ColorChooser.setParameters(): "+pstrTitle+"\t"+pstrMsg+"\t"+pstrNoProjectError);
        strTitle = pstrTitle ;
        strMsg = pstrMsg;
        strNoProjectError = pstrNoProjectError ;
    }
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2015-08-04
     *  Description     : Displays the new project wizard
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Display (AnalysisController pController) {
                
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        // Only if there is a valid graph, show the dialog to pick a color, else show the error dialog
        if (UIInstance.getActiveProjectTab() != null && UIInstance.getActiveProjectTab().getActiveGraphTab() != null) {
            
            Stage stgColorChooser = new Stage();
            stgColorChooser.initModality(Modality.APPLICATION_MODAL);
            
            Label lblProjectName = new Label(strMsg);
        
            Button btnOK = new Button(LangConfig.GENERAL_OK);
            Button btnCancel = new Button(LangConfig.GENERAL_CANCEL);
            final ColorPicker clrPicker = new ColorPicker();
            clrPicker.setValue(Color.valueOf(UIInstance.getActiveProjectTab().getActiveGraphTab().getColorString()));
            
            HBox colorBoxes = new HBox(5);
            colorBoxes.setPadding(new Insets(5,5,5,5));
            colorBoxes.getChildren().addAll(lblProjectName, clrPicker);
            colorBoxes.setSpacing(20);
            colorBoxes.setPadding(new Insets(10, 10, 10, 10));
            colorBoxes.setAlignment(Pos.CENTER);

            btnOK.setOnAction(e -> {

                pController.updateStatusBar(true, StatusMsgsConfig.CANVAS_BGCOLOR_CHANGING);
                
                // Invoke the changeColor method in the GraphTab with the select color
                UIInstance.getActiveProjectTab().getActiveGraphTab().changeBGColor(FXUtils.ColorToHex(clrPicker.getValue()));

                // Close the Dialog Box once OK is pressed
                stgColorChooser.close();
                
                pController.updateStatusBar(true, StatusMsgsConfig.CANVAS_BGCOLOR_CHANGED);
            });

            btnCancel.setOnAction(e -> {
                stgColorChooser.close();
                pController.updateStatusBar(true, StatusMsgsConfig.OPERATION_CANCEL);
            });
            btnCancel.setCancelButton(true);

            HBox hboxButtons = new HBox(5);
            hboxButtons.setPadding(new Insets(5,5,5,5));
            hboxButtons.getChildren().addAll(btnOK, btnCancel);
            hboxButtons.setSpacing(20);
            hboxButtons.setPadding(new Insets(10, 10, 10, 10));
            hboxButtons.setAlignment(Pos.CENTER);

            // Build a VBox
            VBox vboxRows = new VBox(5);
            vboxRows.setPadding(new Insets(5,5,5,5));        
            vboxRows.getChildren().addAll(colorBoxes, hboxButtons);

            Scene scnColorChooser = new Scene(vboxRows);
            scnColorChooser.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                if (evt.getCode().equals(KeyCode.ESCAPE)) {
                    stgColorChooser.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
                }
            });
        
            //stgColorChooser.initModality(Modality.WINDOW_MODAL);
            stgColorChooser.setTitle(strTitle);        
            stgColorChooser.setResizable(false);
            pController.updateStatusBar(true, StatusMsgsConfig.WAITING_USERINPUT);

            stgColorChooser.setScene(scnColorChooser);
            stgColorChooser.show();
            stgColorChooser.setOnCloseRequest(e -> {
                e.consume();
                stgColorChooser.close();
                pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            });
            
        } else {
            ErrorDialog.Display(strNoProjectError);
        }
        
    }    
    
}
