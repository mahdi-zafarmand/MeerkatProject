/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.analysis.SimilarityMeasureAPI;
import config.LangConfig;
import globalstate.MeerkatUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 *  Class Name      : AddEdgeAttributeSimiliarityDialog
 *  Created Date    : 2016-01-12
 *  Description     : The Dialog Window for adding an Edge base on the Attribute Value
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class AddEdgeAttributeSimiliarityDialog {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strImagePath ;
    private static String strHeader ;
    private static String strInformation ;
    
    private static String strAttributeLabel ;    
    private static String strSimilarityLabel ;
    private static String strThresholdLabel ;
    private static String strThresholdErrorLabel ;
    
    // Fields to store the values
    private static String strSelectedAttribute ;
    private static String strSelectedSimilarityMeasure ;
    private static double dblSelectedThreshold ;


    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */


    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2016-01-12
     *  Description     : Sets the parameters of all the values that are displayed in AddAttribute Dialog Window
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrHeader : String
     *  @param pstrInformation : String
     *  @param pstrImagePath : String
     *  @param pstrAttributeLabel : String
     *  @param pstrSimilarityLabel : String
     *  @param pstrThresholdLabel : String
     *  @param pstrThresholdErrorLabel : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters (
              String pstrHeader
            , String pstrInformation
            , String pstrImagePath
            , String pstrAttributeLabel
            , String pstrSimilarityLabel
            , String pstrThresholdLabel
            , String pstrThresholdErrorLabel
    ) {
        strImagePath = pstrImagePath ;
        strInformation = pstrInformation ;
        strHeader = pstrHeader ;
        strAttributeLabel = pstrAttributeLabel ;
        strSimilarityLabel = pstrSimilarityLabel ;
        strThresholdLabel = pstrThresholdLabel ;
        strThresholdErrorLabel = pstrThresholdErrorLabel ;
    }
    
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-01-14
     *  Description     : Displays the dialog box of the AddEdgeAttribute Dialog Box
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
    public static void Display(AnalysisController pController) {
        
        Stage stgNewProjectWizard = new Stage();
        MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
        
        // Image 
        Image imageIcon = new Image(strImagePath);
        ImageView imgvwIcon = new ImageView(imageIcon);
        
        Label lblInfo = new Label(strInformation);
        
        ColumnConstraints clmImageInfo1 = new ColumnConstraints(100);
        clmImageInfo1.setPercentWidth(30);
        ColumnConstraints clmImageInfo2 = new ColumnConstraints();
        clmImageInfo2.setPercentWidth(70);
        clmImageInfo2.setHgrow(Priority.ALWAYS);
        
        GridPane gridImageInfo = new GridPane();
        gridImageInfo.getColumnConstraints().addAll(clmImageInfo1, clmImageInfo2);
        
        GridPane.setHalignment(imgvwIcon, HPos.LEFT);
        gridImageInfo.add(imgvwIcon, 0, 0);
        
        GridPane.setHalignment(lblInfo, HPos.CENTER);
        gridImageInfo.add(lblInfo, 1, 0);
                 
        // Grid to show all the User Input controls
        GridPane gridUserControls = new GridPane();
        gridUserControls.setPadding(new Insets(5));
        gridUserControls.setHgap(5);
        gridUserControls.setVgap(5);
        
        // Column constraints to define the two columns
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(40);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(60);
        column2.setHgrow(Priority.ALWAYS);
        gridUserControls.getColumnConstraints().addAll(column1, column2);

        
        // ROW 1 - Attribute
        Label lblAttribute = new Label(strAttributeLabel);
        // The observable list is retrieved from the graph API        
        ObservableList<String> lststrAttributeNames =  FXCollections.observableArrayList(
                GraphAPI.getAllAttributesNames_Sorted(UIInstance.getActiveProjectTab().getProjectID()
                        , UIInstance.getActiveProjectTab().getActiveGraphID()
                        , UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex()));
        
        final ComboBox cmbAttributes = new ComboBox(lststrAttributeNames);
        
        // ROW 2 - Similarity
        Label lblSimilarity = new Label(strSimilarityLabel);
        // The observable list is retrieved from the graph API        
        ObservableList<String> lststrSimilarityMeasures =  FXCollections.observableArrayList(SimilarityMeasureAPI.getSimilarityMeasures());    
        final ComboBox cmbSimilarity = new ComboBox(lststrSimilarityMeasures);
                   
        
        // ROW 3
        Label lblThreshold = new Label(strThresholdLabel);
        
        final Spinner<Double> spinner = new Spinner();
        double dblInitialValue = 0.5 ;
        spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, dblInitialValue));        
        spinner.setEditable(true);
            
        // Assigning values to the grid
        // Attribute Names
        GridPane.setHalignment(lblAttribute, HPos.LEFT);
        gridUserControls.add(lblAttribute, 0, 0);
        
        GridPane.setHalignment(cmbAttributes, HPos.CENTER);
        gridUserControls.add(cmbAttributes, 1, 0);
        
        // EdgeType
        GridPane.setHalignment(lblSimilarity, HPos.LEFT);
        gridUserControls.add(lblSimilarity, 0, 1);
        
        GridPane.setHalignment(cmbSimilarity, HPos.CENTER);
        gridUserControls.add(cmbSimilarity, 1, 1);
        
        // Multiple Values
        GridPane.setHalignment(lblThreshold, HPos.LEFT);
        gridUserControls.add(lblThreshold, 0, 2);
        
        GridPane.setHalignment(spinner, HPos.CENTER);
        gridUserControls.add(spinner, 1, 2);
        
        
        Label lblThresholdError = new Label(strThresholdErrorLabel) ;
        lblThresholdError.setVisible(false);
        
        
        // Adding the OK and Cancel Buttons at the end        
        Button btnOK = new Button (LangConfig.GENERAL_OK);
        Button btnCancel = new Button (LangConfig.GENERAL_CANCEL);
        HBox hboxOKCancel = new HBox(btnOK, btnCancel);
        hboxOKCancel.setSpacing(10);
        hboxOKCancel.setPadding(new Insets(5,10,5,10));
        
        
        // Adding the two Grid Panes and the OK Cancel button in a vbox
        VBox vboxPanel = new VBox(gridImageInfo, gridUserControls, hboxOKCancel);
        
        // EVENT HANDLERS
        
        // Key press Event
        EventHandler<KeyEvent> enterKeyEventHandler = (KeyEvent event) -> {
            // handle users "enter key event"
            if (event.getCode() == KeyCode.ENTER) {                
                try {
                    // yes, using exception for control is a bad solution ;-)
                    Double.parseDouble(spinner.getEditor().textProperty().get());                
                    lblThresholdError.setVisible(false);
                }
                catch (NumberFormatException e) {                    
                    // show message to user: "only numbers allowed"                   
                    // reset editor to INITAL_VALUE
                    spinner.getEditor().textProperty().set(String.valueOf(dblInitialValue));
                    lblThresholdError.setVisible(true);
                }
            }
        };
        
        // Combo Box Value
        cmbAttributes.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {                
                strSelectedAttribute = t1 ;
            }    
        });
        
        cmbSimilarity.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {                
                strSelectedSimilarityMeasure = t1 ;
            }    
        });
        
        // Button Pressed Events
        btnOK.setOnAction((ActionEvent e) -> {
            
            System.out.println("DEBUG: AddEdgeAttribute.Display(): OK Pressed");
            
            try {
                // yes, using exception for control is a bad solution ;-)
                Double.parseDouble(spinner.getEditor().textProperty().get());                
                lblThresholdError.setVisible(false);
                
                // Call a method with parameters strSelectedAttribute, blnIsAttributeValue, blnIsMultipleValue, strSeparator
                
                // Close the dialog box
                stgNewProjectWizard.close();
            } catch (NumberFormatException ex) {                    
                // show message to user: "only numbers allowed"                   
                // reset editor to INITAL_VALUE
                spinner.getEditor().textProperty().set(String.valueOf(dblInitialValue));
                lblThresholdError.setVisible(true);
            }
        });
        btnCancel.setOnAction((ActionEvent e) -> {
            System.out.println("DEBUG: AddEdgeAttribute.Display(): Cancel Pressed");
            
            stgNewProjectWizard.close();
        });
        
        Scene scnNewProjectWizard = new Scene(vboxPanel);
        
        stgNewProjectWizard.initModality(Modality.APPLICATION_MODAL);
        stgNewProjectWizard.setTitle(strHeader);        
        stgNewProjectWizard.setResizable(false);
        
        stgNewProjectWizard.setScene(scnNewProjectWizard);
        stgNewProjectWizard.show();
    }
}
