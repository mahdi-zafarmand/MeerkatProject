/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import config.ErrorMsgsConfig;
import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *  Class Name      : ErrorDialog
 *  Created Date    : 2015-08-05
 *  Description     : Error Dialog Box
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class ErrorDialog {
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2015-08-05
     *  Description     : The Error Dialog Box that is to be displayed
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrErrorMessage : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
     public static void Display (String pstrErrorMessage) {
        
        Stage stgErrorDialog = new Stage();
        
        Label lblError = new Label(pstrErrorMessage);
        Button btnOK = new Button(LangConfig.GENERAL_OK);
        btnOK.setOnAction(e -> {
            stgErrorDialog.close();
        });
        btnOK.setCancelButton(true);        
        btnOK.setAlignment(Pos.CENTER);
                
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().add(column1); 

        grid.add(lblError, 0  , 0);
        grid.add(btnOK, 0  , 1);
        
        
        // Build a VBox
        /*
        VBox vboxRows = new VBox(5);        
        vboxRows.setPadding(new Insets(5,5,5,5));
        vboxRows.getChildren().addAll(lblError, btnOK);
                */
        
        //Scene scnNewProjectWizard = new Scene(vboxRows);
        Scene scnErrorDialog = new Scene(grid);
        scnErrorDialog.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
                stgErrorDialog.close();
                MeerkatUI UIInstance = MeerkatUI.getUIInstance();
                UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            }
        });
        
        stgErrorDialog.initModality(Modality.APPLICATION_MODAL);
        stgErrorDialog.setTitle(LangConfig.GENERAL_ERROR); 
        stgErrorDialog.setResizable(false);
        
        stgErrorDialog.setScene(scnErrorDialog);
        stgErrorDialog.show();
    }
     
     
     
     public static void Display (int pintErrorCode) {
        
        String strErrorMsg = "" ;
        
        switch (pintErrorCode) {
            case -1000100 :
                strErrorMsg = ErrorMsgsConfig.PROJECTDESERIALIZE_GENERALERROR;
                break ;
            case -100101:
                strErrorMsg = ErrorMsgsConfig.PROJECTDESERIALIZE_MALFORMEDPROJECTLINE;
                break ;
            case -1000102:
                strErrorMsg = ErrorMsgsConfig.PROJECTDESERIALIZE_NOVALUEFORKEY;
                break ;
            case -1000110:
                strErrorMsg = ErrorMsgsConfig.ERROR_CORRUPTEDFILE;
                break ;
            case -1000200:
                strErrorMsg = ErrorMsgsConfig.PROJECTSERIALIZE_GENERALERROR;
                break ;
            case -1000201:
                strErrorMsg = ErrorMsgsConfig.PROJECTSERIALIZE_REGULARGRAPH;
                break ;
            case -1000202:
                strErrorMsg = ErrorMsgsConfig.PROJECTSERIALIZE_TEXTUALGRAPH;
                break ;
            case -1000203:
                strErrorMsg = ErrorMsgsConfig.PROJECTSERIALIZE_WRITINGPROJECT;
                break ;
            case -201:
                strErrorMsg = ErrorMsgsConfig.ERROR_GRAPHLOAD;
                break ;
            case -101:
                strErrorMsg = ErrorMsgsConfig.ERROR_SAVECOMMUNITY;
                break ;                
        }
        
        ErrorDialog.Display(strErrorMsg);
                
    }
}
