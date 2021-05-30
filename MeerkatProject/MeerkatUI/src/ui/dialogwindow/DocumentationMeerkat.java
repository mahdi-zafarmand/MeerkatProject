/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import config.LangConfig;
import config.StatusMsgsConfig;
import java.awt.Desktop;
import java.net.URI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 *  Class Name      : DocumentationMeerkat
 *  Created Date    : 2017-09-12
 *  Description     : User doc for meerkat
 *  Version         : 1.0
 *  @author         : Abhi
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class DocumentationMeerkat {
 
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    private static String strDocumentationMeerkat = "" ;
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    public static String getStrDocumentationMeerkat () {
        return strDocumentationMeerkat ;
    }    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2017-09-12
     *  Description     : Sets the parameters of the Documentation of Meerkat that are to be displayed in the dialog box on clicking the menu option
     *  Version         : 1.0
     *  @author         : Abhi
     * 
     *  @param pstrDocumentationMeerkat : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters (
          String pstrDocumentationMeerkat 
    ) {
        strDocumentationMeerkat = pstrDocumentationMeerkat ;  
    }
    
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2017-09-12
     *  Description     : The Dialog Box to be showed when the option is clicked
     *  Version         : 1.0
     *  @author         : Abhi
     * 
     *  @param pController ; AnalysisController
     *  @param pstrTitle : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Display(AnalysisController pController, String pstrTitle) {
        //TODO - display it using webengine - write documentation in html
        Stage stgDocumentationMeerkatDialog = new Stage();
        stgDocumentationMeerkatDialog.initModality(Modality.APPLICATION_MODAL);
        
        //Label lblDocumentationMeerkat = new Label(strDocumentationMeerkat);
        Label lblDocumentationMeerkat = new Label("Check our webpage for documentation : ");
        String docURL = "https://www.amii.ca/meerkat";
        Hyperlink link = new Hyperlink(docURL);
        link.setBorder(Border.EMPTY);
        
        link.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try{
                    if( Desktop.isDesktopSupported() )
                        {
                            new Thread(() -> {
                                   try {
                                       Desktop.getDesktop().browse( new URI( docURL ) );
                                   } catch (Exception et) {
                                       System.out.println("Exception in hyperlink click in DocumentationMeerkat:Disply()");
                                   }
                               }).start();
                        }
                    stgDocumentationMeerkatDialog.close();
                    }catch(Exception exp){
                        System.out.println("Exception in hyperlink click in DocumentationMeerkat:Disply()");
                    }
                }
            });
      
        HBox hbox = new HBox();
        hbox.getChildren().addAll(lblDocumentationMeerkat, link);
        hbox.setAlignment(Pos.CENTER);
        // Build a VBox
        Button btnOK = new Button(LangConfig.GENERAL_OK);
        VBox vboxRows = new VBox(5);
        vboxRows.setPadding(new Insets(5,10,5,10));        
        vboxRows.getChildren().addAll(hbox, btnOK);
        vboxRows.setAlignment(Pos.CENTER);
        
        Scene scnDocumentationMeerkatDialog = new Scene(vboxRows);
        scnDocumentationMeerkatDialog.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
                stgDocumentationMeerkatDialog.close();
                pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            }
        });
        
        //stgDocumentationMeerkatDialog.initModality(Modality.WINDOW_MODAL);
        stgDocumentationMeerkatDialog.setTitle(pstrTitle);        
        stgDocumentationMeerkatDialog.setResizable(false);
        
        
        // Events 
        btnOK.setOnAction((ActionEvent e) -> {
            // Close the dialog box
            stgDocumentationMeerkatDialog.close();
            
            
        });
        
        stgDocumentationMeerkatDialog.setScene(scnDocumentationMeerkatDialog);
        stgDocumentationMeerkatDialog.show();
        
        // Update the status bar
        
    }
}