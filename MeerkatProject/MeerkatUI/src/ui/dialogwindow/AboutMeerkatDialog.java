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
import meerkat.registration.MeerkatSoftwareValidator;

/**
 *  Class Name      : AboutMeerkatDialog
 *  Created Date    : 2016-01-18
 *  Description     : Information about Meerkat
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class AboutMeerkatDialog {
 
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    private static String strAboutMeerkat = "" ;
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    public static String getAboutMeerkat () {
        return strAboutMeerkat ;
    }    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2016-01-18
     *  Description     : Sets the parameters of the About Meerkat that are to be displayed in the dialog box on clicking the menu option
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrAboutMeerkat : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters (
          String pstrAboutMeerkat 
    ) {
        strAboutMeerkat = pstrAboutMeerkat ;  
    }
    
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-01-18
     *  Description     : The Dialog Box to be showed when the option is clicked
     *  Version         : 1.0
     *  @author         : Talat
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
        
        Stage stgAboutMeerkatDialog = new Stage();
        stgAboutMeerkatDialog.initModality(Modality.APPLICATION_MODAL);
        
        //Label lblAboutMeerkat = new Label(strAboutMeerkat);
        
        String aboutMeerkat = "Meerkat is a social network analysis tool developed at AMII, Department of Computing Science, University of Alberta, "
                                + "under the leadership of Dr. Osmar Zaïane.";
        
        String meerkatURL = "https://www.amii.ca/meerkat";
        
        Hyperlink link = new Hyperlink(meerkatURL);
        link.setBorder(Border.EMPTY);
        link.setPadding(new Insets(0, 0, 0, 0));
        link.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try{
                    if( Desktop.isDesktopSupported() )
                        {
                            new Thread(() -> {
                                   try {
                                       Desktop.getDesktop().browse( new URI( meerkatURL ) );
                                   } catch (Exception et) {
                                       System.out.println("Exception in hyperlink click in AboutMeerkat:Disply()");
                                   }
                               }).start();
                        }
                    stgAboutMeerkatDialog.close();
                    }catch(Exception exp){
                        System.out.println("Exception in hyperlink click in AboutMeerkat:Disply()");
                    }
                }
            });
        
        
        
        String meerkatEmail = "meerkat@cs.ualberta.ca ";

        String details = "For more details please visit us online at: ";
        String emailDetails = " or email us at: meerkat@cs.ualberta.ca";
        
        String meerkatVersion = "Meerkat Version: "+MeerkatSoftwareValidator.getMeerkatVersion();
        String meerkatReleaseDate = "Version Release Date: 2018 February 02";

        String meerkatcurrentDevs = "Current Developers : Sankalp Prabhakar, Talat Iqbal Syed";
        
        String meerkatPastDevs = "Past and Present Team Members (alphabetically): Abhimanyu Panwar, Ali Yadollahi, Afra Abnar, Amin Travelsi, Eric Verbeek, Farzad Sangi, Justin Fagnan, \n" +
                                "Jiyang Chen, Matt Gallivan, Mansoureh Takaffoli, Reihaneh Rabbany, Sankalp Prabhakar, Shiva Zamani Gharaghooshi, "
                                + "Talat Iqbal Syed, Xiaoxiao Li.";

        
        VBox labels = new VBox();
        Label label1 = new Label(aboutMeerkat);
        Label label2 = new Label(details);
        Label label3 = new Label(emailDetails);
        
        HBox hbox = new HBox();
        hbox.getChildren().addAll(label2, link, label3);
        
        Label version = new Label(meerkatVersion);
        Label releaseDate = new Label(meerkatReleaseDate);
        Label currDevs = new Label(meerkatcurrentDevs);
        Label pastDevs = new Label(meerkatPastDevs);
        
        labels.getChildren().addAll(label1,hbox,version,releaseDate,currDevs,pastDevs);
        labels.setPadding(new Insets(5, 10, 5, 10));
        labels.setSpacing(10);
      
        // Build a VBox
        Button btnOK = new Button(LangConfig.GENERAL_OK);
        VBox vboxRows = new VBox(5);
        vboxRows.setPadding(new Insets(5,10,5,10));        
        vboxRows.getChildren().addAll(labels, btnOK);
        vboxRows.setAlignment(Pos.CENTER);
        
        Scene scnAboutMeerkatDialog = new Scene(vboxRows);
        scnAboutMeerkatDialog.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
                stgAboutMeerkatDialog.close();
                pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            }
        });
        
        //stgAboutMeerkatDialog.initModality(Modality.WINDOW_MODAL);
        stgAboutMeerkatDialog.setTitle(pstrTitle);        
        stgAboutMeerkatDialog.setResizable(false);
        
        
        // Events 
        btnOK.setOnAction((ActionEvent e) -> {
            // Close the dialog box
            stgAboutMeerkatDialog.close();
            
            // Update the Status bar
            pController.updateStatusBar(false, StatusMsgsConfig.ABOUT_CLOSED);
        });
        
        stgAboutMeerkatDialog.setScene(scnAboutMeerkatDialog);
        stgAboutMeerkatDialog.show();
        
        // Update the status bar
        pController.updateStatusBar(true, StatusMsgsConfig.ABOUT_SHOW);
    }
}