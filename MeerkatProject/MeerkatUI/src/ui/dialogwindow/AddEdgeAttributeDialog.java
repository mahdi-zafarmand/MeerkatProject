/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
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
 *  Class Name      : AddEdgeAttributeDialog
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
public class AddEdgeAttributeDialog {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strImagePath ;
    private static String strHeader ;
    private static String strInformation ;
    
    private static String strAttributeLabel ;
    
    private static String strEdgeType ;
    private static String strAttributeName ;
    private static String strAttributeValue ;
    
    private static String strMultipleValues ;
    
    private static String strSeparator ;
    
    // Fields to store the values
    private static String strSelectedAttribute ;
    private static boolean blnIsMultipleValues ;
    private static boolean blnIsAttributeValue ;
    private static String strSeparatorValue;


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
     *  @param pstrEdgeType : String
     *  @param pstrAttributeName : String
     *  @param pstrAttributeValue : String
     *  @param pstrMultipleValues : String
     *  @param  pstrSeparator : String
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
            , String pstrEdgeType
            , String pstrAttributeName
            , String pstrAttributeValue
            , String pstrMultipleValues
            , String pstrSeparator
    ) {
        strImagePath = pstrImagePath ;
        strHeader = pstrHeader ;
        strAttributeLabel = pstrAttributeLabel ;
        strEdgeType = pstrEdgeType ;
        strAttributeName = pstrAttributeName ;
        strAttributeValue = pstrAttributeValue ;
        strMultipleValues = pstrMultipleValues ;
        strSeparator = pstrSeparator ;
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
        Stage stgAddEdgeAttribute = new Stage();
        stgAddEdgeAttribute.initModality(Modality.APPLICATION_MODAL);
        
        // Image 
        Image imageIcon = new Image(strImagePath);
        ImageView imgvwIcon = new ImageView(imageIcon);
                 
        // Grid to show all the User Input controls
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(5);
        
        // Column constraints to define the two columns
        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
        column2.setHgrow(Priority.ALWAYS);
        gridpane.getColumnConstraints().addAll(column1, column2);

        
        // ROW 1
        Label lblAttribute = new Label(strAttributeLabel);
        // The observable list is retrieved from the graph API
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
        
        ObservableList<String> lststrAttributeNames =  FXCollections.observableArrayList(
                GraphAPI.getAllAttributesNames_Sorted(UIInstance.getActiveProjectTab().getProjectID(), UIInstance.getActiveProjectTab().getActiveGraphID(), UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex()));        
        final ComboBox cmbAttributes = new ComboBox(lststrAttributeNames);
        
        // ROW 2
        Label lblEdgeType = new Label(strEdgeType);
        
        final ToggleGroup grpEdgeType = new ToggleGroup();
        
        RadioButton rbAttributeName = new RadioButton(strAttributeName);
        rbAttributeName.setToggleGroup(grpEdgeType);
        rbAttributeName.setSelected(true);
        blnIsAttributeValue = false ;
        
        RadioButton rbAttributeValue = new RadioButton(strAttributeValue);
        rbAttributeValue.setToggleGroup(grpEdgeType);        
        
        
        // ROW 3
        Label lblMultipleValues = new Label(strEdgeType);
        
        final ToggleGroup grpMultipleValues = new ToggleGroup();
        
        RadioButton rbTrue = new RadioButton(LangConfig.GENERAL_TRUE);
        rbTrue.setToggleGroup(grpEdgeType);
        
        RadioButton rbFalse = new RadioButton(LangConfig.GENERAL_FALSE);
        rbFalse.setToggleGroup(grpEdgeType);
        rbFalse.setSelected(true);
        blnIsMultipleValues = false ;
        
        // ROW 4 - Separator
        Label lblSeparator = new Label(strSeparator);
        lblSeparator.setVisible(false);
        TextField txtSeparator = new TextField(",");
        txtSeparator.setVisible(false);
        strSeparatorValue = txtSeparator.getText();
        
        
        // Assigning values to the grid
        // Attribute Names
        GridPane.setHalignment(lblAttribute, HPos.LEFT);
        gridpane.add(lblAttribute, 0, 0);
        
        GridPane.setHalignment(cmbAttributes, HPos.CENTER);
        gridpane.add(cmbAttributes, 1, 0);
        
        // EdgeType
        GridPane.setHalignment(lblEdgeType, HPos.LEFT);
        gridpane.add(lblEdgeType, 0, 1);
        
        HBox hboxEdgeType = new HBox(rbAttributeName, rbAttributeValue);
        hboxEdgeType.setSpacing(5);
        hboxEdgeType.setPadding(new Insets(2,2,2,2));
        GridPane.setHalignment(hboxEdgeType, HPos.CENTER);
        gridpane.add(hboxEdgeType, 1, 1);
        
        // Multiple Values
        GridPane.setHalignment(lblMultipleValues, HPos.LEFT);
        gridpane.add(lblMultipleValues, 0, 2);
        
        HBox hboxMultipleValues = new HBox(rbTrue, rbFalse);
        hboxMultipleValues.setSpacing(5);
        hboxMultipleValues.setPadding(new Insets(2,2,2,2));
        GridPane.setHalignment(hboxMultipleValues, HPos.CENTER);
        gridpane.add(hboxMultipleValues, 1, 2);
        
        // Row 4 - Separator
        GridPane.setHalignment(lblSeparator, HPos.LEFT);
        gridpane.add(lblSeparator, 0, 3);
        
        GridPane.setHalignment(cmbAttributes, HPos.CENTER);
        gridpane.add(cmbAttributes, 1, 3);
        
        
        // Adding the OK and Cancel Buttons at the end        
        Button btnOK = new Button (LangConfig.GENERAL_OK);
        Button btnCancel = new Button (LangConfig.GENERAL_CANCEL);
        HBox hboxOKCancel = new HBox(btnOK, btnCancel);
        hboxOKCancel.setSpacing(10);
        hboxOKCancel.setPadding(new Insets(5,10,5,10));
        
        // Adding the GRID and the Button control to a VBOX
        VBox vboxSelectionPanel = new VBox(gridpane, hboxOKCancel);
                
        // HBox to place the Grid and the Image
        HBox hboxPanel = new HBox(imgvwIcon, vboxSelectionPanel);   
        
        
        
        // EVENT HANDLERS
        
        // Toggle Groups        
        grpEdgeType.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) -> {
            RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
            
            // If true, then unhide the separator
            if (chk.getText().equalsIgnoreCase(strAttributeName)){
                System.out.println("DEBUG: AddEdgeAttribute.Display(): Selected Radio Button - Attribute Name Selected");
                blnIsAttributeValue = false ;
            } else {
                System.out.println("DEBUG: AddEdgeAttribute.Display(): Selected Radio Button - Attribute Value Selected");
                blnIsAttributeValue = true;
            }
        });
        
        grpEdgeType.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) -> {
            RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
            
            // If true, then unhide the separator
            if (chk.getText().equalsIgnoreCase(LangConfig.GENERAL_TRUE)){
                System.out.println("DEBUG: AddEdgeAttribute.Display(): Selected Radio Button - True");
                lblSeparator.setVisible(true);
                txtSeparator.setVisible(true);
                blnIsMultipleValues = true ;
            } else {
                System.out.println("DEBUG: AddEdgeAttribute.Display(): Selected Radio Button - False");
                lblSeparator.setVisible(false);
                txtSeparator.setVisible(false);
                blnIsMultipleValues = false ;
            }
        });
        
        // Combo Box Value
        cmbAttributes.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {                
                strSelectedAttribute = t1 ;
            }    
        });
        
        // Button Pressed Events
        btnOK.setOnAction((ActionEvent e) -> {
            
            System.out.println("DEBUG: AddEdgeAttribute.Display(): OK Pressed");
            
            // Call a method with parameters strSelectedAttribute, blnIsAttributeValue, blnIsMultipleValue, strSeparator
            
            // Close the dialog box
            stgAddEdgeAttribute.close();
        });
        btnCancel.setOnAction((ActionEvent e) -> {
            System.out.println("DEBUG: AddEdgeAttribute.Display(): Cancel Pressed");
            
            stgAddEdgeAttribute.close();
        });
        
        Scene scnAddEdgeAttribute = new Scene(hboxPanel);
        scnAddEdgeAttribute.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
                stgAddEdgeAttribute.close();
                pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            }
        });
        
        //stgAddEdgeAttribute.initModality(Modality.WINDOW_MODAL);
        stgAddEdgeAttribute.setTitle(strHeader);        
        stgAddEdgeAttribute.setResizable(false);
        
        stgAddEdgeAttribute.setScene(scnAddEdgeAttribute);
        stgAddEdgeAttribute.show();
    }
}
