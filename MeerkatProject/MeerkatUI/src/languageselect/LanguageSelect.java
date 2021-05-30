/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageselect;

import analysisscreen.AnalysisScreen;
import config.AppConfig;
import io.stateserialization.Language;
import java.net.URL;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author syedtala
 */
public class LanguageSelect {
    
    private static String strSelectedLang ; // Where the default language selected will be stored
    
    /* UI Related Text */
    private static String strWindowTitle ;
    private static String strWindowIcon;
    private static String strLabelText ;
    private static String strCheckboxText ;
    private static String strButtonText;
    
    /* GETTERS AND SETTERS */
    public static String getWindowTitle(){return LanguageSelect.strWindowTitle ;}
    public static void setWindowTitle(String pstrWindowTitle){LanguageSelect.strWindowTitle = pstrWindowTitle;}
    
    public static String getWindowIcon(){return LanguageSelect.strWindowIcon ;}
    public static void setWindowIcon(String pstrWindowIcon){LanguageSelect.strWindowIcon = pstrWindowIcon;}
    
    public static String getLabelText(){return LanguageSelect.strLabelText ;}
    public static void setLabelText(String pstrLabelText){LanguageSelect.strLabelText = pstrLabelText;}
    
    public static String getCheckboxText(){return LanguageSelect.strCheckboxText ;}
    public static void setCheckboxText(String pstrCheckboxText){LanguageSelect.strCheckboxText = pstrCheckboxText;}
    
    public static String getButtonText(){return LanguageSelect.strButtonText ;}
    public static void setButtonText(String pstrButtonText){LanguageSelect.strButtonText = pstrButtonText;}
    
    public static void Display(List<String> plstAvailableOptions, Stage parentWindow) {
        Stage languageWindow = new Stage();
        languageWindow.initModality(Modality.APPLICATION_MODAL);
        languageWindow.setTitle(LanguageSelect.strWindowTitle);
        languageWindow.setMinWidth(250);
        
        //A checkbox without a caption
        CheckBox cbDefaultLang = new CheckBox(LanguageSelect.strCheckboxText);
        cbDefaultLang.setPadding(new Insets(15));
        // Label to show the message
        Label lblMsg = new Label(LanguageSelect.strLabelText);
        
        Button btnSelectLanguage = new Button(LanguageSelect.strButtonText);        
        btnSelectLanguage.setPadding(new Insets(10));
        btnSelectLanguage.setAlignment(Pos.BOTTOM_CENTER);
        btnSelectLanguage.setOnAction(e -> {
            System.out.println("DEBUG: LanguageSelect.Display(): "+strSelectedLang);
            if (cbDefaultLang.selectedProperty().get()) {
                Language.UpdateDefaultLang(strSelectedLang);
            }
            languageWindow.close();
            parentWindow.close();;
            AnalysisScreen.Display(strSelectedLang+AppConfig.EXTENSION_XML);            
        });
        
        Scene scene = new Scene(new Group());
        
        URL url = LanguageSelect.class.getResource("LanguageSelect.css");
        if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }
        String css = url.toExternalForm(); 
        scene.getStylesheets().add(css);
        
        VBox vboxLanguage = new VBox();
        vboxLanguage.getChildren().addAll(lblMsg);
        final ToggleGroup languageToggle = new ToggleGroup();
        for (String strCurrent : plstAvailableOptions) {
            RadioButton rbCurrent = new RadioButton(TrimFileExtension(strCurrent));            
            rbCurrent.setToggleGroup(languageToggle);
            rbCurrent.getStyleClass().add("langselect-radio-button");
            rbCurrent.setPadding(new Insets(10));
            vboxLanguage.getChildren().addAll(rbCurrent);
        }
        
        
        vboxLanguage.getChildren().addAll(cbDefaultLang);
        vboxLanguage.getChildren().addAll(btnSelectLanguage);
        
        
        languageToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
             @Override
             public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                 RadioButton rbtnSelectedLang = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                 strSelectedLang = rbtnSelectedLang.getText();
             }
         });
        

        ((Group) scene.getRoot()).getChildren().add(vboxLanguage);
        languageWindow.setScene(scene);
        languageWindow.show();       
    }
    
    private static String TrimFileExtension(String pstrFileName) {        
        String [] arrstrFile = pstrFileName.split("\\.");
        return arrstrFile[0];
    }
    
}
