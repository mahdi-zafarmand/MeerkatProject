/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

/**
 *  Class Name      : InfoDialog
 *  Created Date    : 2015-09-10
 *  Description     : Info Dialog Box
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class InfoDialog {
    
    /**
     *  Method Name     : Display
     *  Created Date    : 2015-09-10
     *  Description     : Displays the Information Dialog Box
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrInfoMessage : String
     *  @param pintTimeOutInSeconds : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Display (String pstrInfoMessage, int pintTimeOutInSeconds) {
        
        Stage stgInfoDialog = new Stage();
        
        Label lblInfo = new Label(pstrInfoMessage);
        Button btnOK = new Button(LangConfig.GENERAL_OK);
        btnOK.setOnAction(e -> {
            stgInfoDialog.close();
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

        grid.add(lblInfo, 0  , 0);
        grid.add(btnOK, 0  , 1);
        
        
        // Build a VBox
        /*
        VBox vboxRows = new VBox(5);        
        vboxRows.setPadding(new Insets(5,5,5,5));
        vboxRows.getChildren().addAll(lblInfo, btnOK);
                */
        
        //Scene scnNewProjectWizard = new Scene(vboxRows);
        Scene scnInfoDialog = new Scene(grid);
        scnInfoDialog.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
                stgInfoDialog.close();
                MeerkatUI UIInstance = MeerkatUI.getUIInstance();
                UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            }
        });
        
        stgInfoDialog.initModality(Modality.APPLICATION_MODAL);
        stgInfoDialog.setTitle(LangConfig.GENERAL_INFORMATION); 
        stgInfoDialog.setResizable(false);
        //stgInfoDialog.setMaxWidth(200);
        
        stgInfoDialog.setScene(scnInfoDialog);
        stgInfoDialog.show();
        
        if (pintTimeOutInSeconds > 0) {
            // Close the dialog box after few seconds
            PauseTransition delay = new PauseTransition(Duration.millis(pintTimeOutInSeconds*1000));
            delay.setOnFinished( event -> stgInfoDialog.close() );
            delay.play();
        }
    }
}
