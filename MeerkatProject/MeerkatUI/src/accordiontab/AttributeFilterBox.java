/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import ca.aicml.meerkat.api.GraphAPI;
import config.AttributeFilterBoxConfig;
import config.ErrorMsgsConfig;
import config.LangConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.RangeSlider;

/**
 *  Class Name      : AttributeFilterBox
 *  Created Date    : 2015-09-22
 *  Description     : Attribute Filter Box consists of the following UI elements wrapped in an HBox
 *                      1) ComboBox - for listing all the attribute names
 *                      2) ComboBox - expressions for the string or numerical comparisons
 *                      3) TextField - To allow the user to enter the value to be compared with
 *                      4) Remove Filter Button - To remove the filter    
 *  Version         : 
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-08-23      Abhi            Added new constructor, getRemoveButton method to additionally make a remove filter button
 * 
*/
public class AttributeFilterBox {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private VBox vboxCompleteContainer ;
    
    private HBox hboxComboContainer ;
    private ComboBox<String> cmbAttributeName ;
    private ComboBox<String> cmbExpression ;
    
    private Node ndValue ;
        
    // private String[] arrstrValue = new String[10];
    private String strSelectedAttributeName = "" ;
    private String strSelectedExpression = "" ;
    private String strValue1 = "" ;
    private String strValue2 = "" ;
    
    
    private boolean blnIsNumeric;
    private BooleanProperty blnIsNumericProperty ;
    private Button btnAddNewAttributeFilterBoxButton;
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    private ComboBox<String> getExpressionComboBox() {
        return this.cmbExpression;
    }
    private ComboBox<String> getAttributeNameComboBox() {
        return this.cmbAttributeName;
    }
    private void setIsAttributeNumeric(boolean pblnIsNumeric) {
        this.blnIsNumeric = pblnIsNumeric;
        blnIsNumericProperty = new SimpleBooleanProperty(pblnIsNumeric);
    }
    private boolean getIsAttributeNumeric() {
        return this.blnIsNumeric;
    }
    
    public Node getAttributeFilterBox() {
        return this.vboxCompleteContainer;
    }
    
    public Button getBtnAddNewAttributeFilterBoxButton(){
        return this.btnAddNewAttributeFilterBoxButton;
    }
    
    public List<String[]> getValue () {
        List<String[]> lstarrstrValue = new ArrayList<>();
        if (this.strSelectedExpression.equalsIgnoreCase(AttributeFilterBoxConfig.BETWEEN)) {
            
            String [] arrGreaterValue = new String[3];
            arrGreaterValue[0] = this.strSelectedAttributeName;
            arrGreaterValue[1] = AttributeFilterBoxConfig.getOperator(AttributeFilterBoxConfig.GREATERTHAN) ;
            arrGreaterValue[2] = strValue1 ;
            
            String [] arrLesserValue = new String[3];
            arrLesserValue[0] = this.strSelectedAttributeName;
            arrLesserValue[1] = AttributeFilterBoxConfig.getOperator(AttributeFilterBoxConfig.LESSERTHAN) ;
            arrLesserValue[2] = strValue2 ;
            
            lstarrstrValue.add(arrGreaterValue);
            lstarrstrValue.add(arrLesserValue);
        } else {
            String [] arrValue = new String[3] ;
            arrValue[0] = this.strSelectedAttributeName;
            arrValue[1] = this.strSelectedExpression;
            arrValue[2] = this.strValue1;
            lstarrstrValue.add(arrValue);
        }
        return lstarrstrValue ;
    }
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    public AttributeFilterBox (Map<String, Boolean> phmapAttributeNames) {        
        
        setIsAttributeNumeric(false);
        cmbAttributeName = new ComboBox<>();
        cmbExpression = new ComboBox<>();
        cmbExpression.setDisable(true);
        TextField txtDummy = new TextField();
        txtDummy.setDisable(true);
        ndValue = new VBox(txtDummy);
        
        addAttributeNameComboBox(phmapAttributeNames);
        //Button btndeleteFilterButton = getRemoveFilterBUtton();
        hboxComboContainer = new HBox(cmbAttributeName, cmbExpression);        
        hboxComboContainer.setAlignment(Pos.CENTER);
        hboxComboContainer.setPadding(new Insets(5,5,5,5));
        hboxComboContainer.setSpacing(10);
        
        vboxCompleteContainer = new VBox(hboxComboContainer, ndValue);
        vboxCompleteContainer.setAlignment(Pos.CENTER);
        vboxCompleteContainer.setPadding(new Insets(5,5,5,5));
        vboxCompleteContainer.setSpacing(10);
    }
    
    public AttributeFilterBox (Map<String, Boolean> phmapAttributeNames, int intFilterIDCalling, Map<Integer, AttributeFilterBox>  mapFilterBoxCalling, String strRemoveFilterButtonText, FilterValues filterValuesInstance, Button pbtnAddVertexFilter) {        

        setIsAttributeNumeric(false);
        cmbAttributeName = new ComboBox<>();
        cmbExpression = new ComboBox<>();
        cmbExpression.setDisable(true);
        TextField txtDummy = new TextField();
        txtDummy.setDisable(true);
        ndValue = new VBox(txtDummy);

        // the calling + button for this box
        btnAddNewAttributeFilterBoxButton =  pbtnAddVertexFilter;
        btnAddNewAttributeFilterBoxButton.setDisable(true);

        addAttributeNameComboBox(phmapAttributeNames);
        Button btndeleteFilterButton = getRemoveFilterBUtton(intFilterIDCalling, mapFilterBoxCalling, strRemoveFilterButtonText, filterValuesInstance);
        hboxComboContainer = new HBox(btndeleteFilterButton, cmbAttributeName, cmbExpression);        
        hboxComboContainer.setAlignment(Pos.CENTER);
        hboxComboContainer.setPadding(new Insets(5,5,5,5));
        hboxComboContainer.setSpacing(10);

        vboxCompleteContainer = new VBox(hboxComboContainer, ndValue);
        vboxCompleteContainer.setAlignment(Pos.CENTER);
        vboxCompleteContainer.setPadding(new Insets(5,5,5,5));
        vboxCompleteContainer.setSpacing(10);
    }
   
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : getRemoveFilterBUtton()
     *  Created Date    : 2016-08-23
     *  Description     : Makes a Remove Filter button
     *  Version         : 1.0
     *  @author         : Abhi
     * 
     *  @param intFilterIDCalling Integer
     *  @param mapFilterBoxCalling Map<Integer, AttributeFilterBox>
     *  @param strRemoveFilterButtonText String
     *  @param filterValuesInstance FilterValues
     *  @return removeFilterButton Button
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private Button getRemoveFilterBUtton(int intFilterIDCalling, Map<Integer, AttributeFilterBox>  mapFilterBoxCalling, String  strRemoveFilterButtonText, FilterValues filterValuesInstance){
    
        Button removeFilterButton = new Button();
        removeFilterButton.setText(strRemoveFilterButtonText);
        
        removeFilterButton.setOnMouseClicked(e ->{
            //remove filter 
            //unpaint filter
            e.consume();
            this.vboxCompleteContainer.getChildren().clear();
            // send control back to the calling object in FilterValues class
            filterValuesInstance.removeAttributeFilterBoxMapEntry(mapFilterBoxCalling, intFilterIDCalling, btnAddNewAttributeFilterBoxButton);
        });
        return removeFilterButton;
    }
    
    /**
     *  Method Name     : addAttributeNameComboBox()
     *  Created Date    : 2016-05-13
     *  Description     : Adds an attribute Name to the ComboBox
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param phmapAttributeName Map<String, Boolean>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void addAttributeNameComboBox(Map<String, Boolean> phmapAttributeName) {
        
        cmbAttributeName.setEditable(true);
        ObservableList<String> items = FXCollections.observableArrayList(phmapAttributeName.keySet());
        FilteredList<String> filteredItems = new FilteredList<>(items, p -> true);
        cmbAttributeName.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = cmbAttributeName.getEditor();
            final String selected = cmbAttributeName.getSelectionModel().getSelectedItem();
            Platform.runLater(() -> {
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredItems.setPredicate(item -> {
                        if (item.toUpperCase().startsWith(newValue.toUpperCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                }
            });
        });

        cmbAttributeName.setItems(filteredItems);
        cmbAttributeName.setMaxWidth(100);
        /* 
        for (String strCurrent : phmapAttributeName.keySet()) {
            cmbAttributeName.getItems().add(strCurrent);
        }*/
        
        // Add a listener to the value property of the combo box when the attribute is changed
        cmbAttributeName.valueProperty().addListener((ov, t, t1) -> {
            
            if (t1 != null) {
                String strSelectedAttributeName = t1.toString();                
                this.hboxComboContainer.getChildren().remove(cmbExpression);
                
                if (phmapAttributeName.get(strSelectedAttributeName)) {
                    setIsAttributeNumeric(true);                    
                    this.cmbExpression = addExpressionComboBox(AttributeFilterBoxConfig.getNumericExpressions());
                } else {
                    setIsAttributeNumeric(false);
                    this.cmbExpression = addExpressionComboBox(AttributeFilterBoxConfig.getStringExpressions());                    
                }
                
                this.strSelectedAttributeName = strSelectedAttributeName ;
                this.hboxComboContainer.getChildren().add(cmbExpression);
            }
        });
    }
    
    
    /**
     *  Method Name     : addExpressionComboBox()
     *  Created Date    : 2016-05-13
     *  Description     : Adds the expression combo box based on the type of input selected by the user
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param plstExpression : ObservableList
     *  @return ComboBox
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
     private ComboBox addExpressionComboBox(ObservableList plstExpression) {         
        
        final ComboBox<String> cmbExpression_local = new ComboBox();        
        try {
            cmbExpression_local.setItems(plstExpression);

            cmbExpression_local.valueProperty().addListener((ov, t, t1) -> {

                if (t1 != null) {
                    String strSelectedExpression = t1.toString();
                    this.vboxCompleteContainer.getChildren().remove(this.ndValue);
                    this.ndValue = addValueNode(strSelectedExpression); 
                    this.strSelectedExpression = AttributeFilterBoxConfig.getOperator(strSelectedExpression);

                    if (this.ndValue != null) {
                        this.vboxCompleteContainer.getChildren().add(this.ndValue);
                    }
                }
            });
        } catch (Exception ex){
            System.out.println("AttributeFilterBox.addExpressionComboBox(): EXCEPTION");
            ex.printStackTrace();
        }
        return cmbExpression_local;
    }
     
    
     
     
     /**
     *  Method Name     : addValueNode()
     *  Created Date    : 2016-05-13
     *  Description     : Adding a visible node to the UI panel based on the selected Filter
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrSelectedExpression : String
     *  @return Node
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private Node addValueNode (String pstrSelectedExpression) {        
        
        Node ndLocalNode = null ;
        
        switch (pstrSelectedExpression) {
            
            case AttributeFilterBoxConfig.EQUAL :
                ndLocalNode = addValue_NumericTextField();
                break ;
            case AttributeFilterBoxConfig.NOTEQUAL :
                ndLocalNode = addValue_NumericTextField();
                break ;
            case AttributeFilterBoxConfig.LESSERTHAN :
                ndLocalNode = addValue_NumericTextField();
                break ;
            case AttributeFilterBoxConfig.LESSERTHANEQUAL :
                ndLocalNode = addValue_NumericTextField();
                break ;
            case AttributeFilterBoxConfig.GREATERTHAN :
                ndLocalNode = addValue_NumericTextField();
                break ;
            case AttributeFilterBoxConfig.GREATERTHANEQUAL :
                ndLocalNode = addValue_NumericTextField();
                break ;
            case AttributeFilterBoxConfig.BETWEEN :
                 ndLocalNode = addValue_Between();
                break ;
                
            case AttributeFilterBoxConfig.EXACTMATCH :
                ndLocalNode = addValue_TextField();
                break ;
            case AttributeFilterBoxConfig.CONTAINS :
                ndLocalNode = addValue_TextField();
                break ;
            case AttributeFilterBoxConfig.STARTSWITH :
                ndLocalNode = addValue_TextField();
                break ;
            case AttributeFilterBoxConfig.ENDSWITH :
                ndLocalNode = addValue_TextField();
                break ;
            /*    
            case AttributeFilterBoxConfig.SIMILAR :
                ndLocalNode = addValue_TextField();
                break ;
            */
            case AttributeFilterBoxConfig.EMPTY :
                //ndLocalNode = addValue_TextField();
                break ;
            case AttributeFilterBoxConfig.NOTEMPTY :
                //ndLocalNode = addValue_TextField();
                break ;
        }
        
        return ndLocalNode ;
    }
    
    /**
     *  Method Name     : addValue_NumericTextField()
     *  Created Date    : 2016-05-13
     *  Description     : Adds a NumericTextField to the UI panel where only real numbers are allowed
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return VBox
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private VBox addValue_NumericTextField() {
        
        Label lblError = new Label(ErrorMsgsConfig.ERROR_NUMERICVALUEONLY);
        
        TextField txtAttributeValue = new TextField();
        txtAttributeValue.textProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {                
                try {
                    if (newValue.length() != 0 && !newValue.equals("-") && !newValue.equals(".")) {
                        double dblValue = Double.parseDouble(newValue);
                        lblError.setVisible(false); 
                        FilterContent.enableFilterButton();
                        //FilterContent.enableAddVertexFilterButton();
                        btnAddNewAttributeFilterBoxButton.setDisable(false);
                    }
                } catch (Exception ex) {
                    txtAttributeValue.setText(oldValue);
                    lblError.setVisible(true);
                    FilterContent.disableFilterButton();
                    //FilterContent.disableAddVertexFilterButton();
                    btnAddNewAttributeFilterBoxButton.setDisable(true);
                }
                strValue1 = txtAttributeValue.getText();
                //System.out.println("AttributeFilterBox.addValue_NumericTextField : strValue1" + strValue1);
            }
        });        
        VBox vboxValueContainer = new VBox(txtAttributeValue, lblError);
        
        return vboxValueContainer;
    }
    
    /**
     *  Method Name     : addValue_TextField()
     *  Created Date    : 2016-05-13
     *  Description     : Adds a TextField to the UI panel where free text can be typed
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return VBox
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private VBox addValue_TextField() {        
        TextField txtAttributeValue = new TextField();
        txtAttributeValue.textProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {                
                strValue1 = txtAttributeValue.getText();
                FilterContent.enableFilterButton();
                btnAddNewAttributeFilterBoxButton.setDisable(false);
            }
        });   
        VBox vboxValueContainer = new VBox(txtAttributeValue);        
        return vboxValueContainer;
    }
    
    /**
     *  Method Name     : addValue_Between()
     *  Created Date    : 2016-05-13
     *  Description     : Adds a value when the between is selected
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param 
     *  @param 
     *  @return 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    /*
    private VBox addValue_Between() {
        
        RangeSlider rsSlider = addRangeSlider();
        
        Spinner<Integer> spinMin = addSpinner();
        spinMin.setMaxWidth(70);
        spinMin.getValueFactory().setValue((int)rsSlider.getLowValue());
        
        Spinner<Integer> spinMax = addSpinner();        
        spinMax.setMaxWidth(70);
        spinMax.getValueFactory().setValue((int)rsSlider.getHighValue());
        
        // Adding the listener to the spinners
        spinMin.valueProperty().addListener((obs, intOldValue, intNewValue) -> {
            // System.out.println("FiltersContet.initialize(): New value: "+intNewValue);
            if (intNewValue > spinMax.getValue()) {
                intNewValue = spinMax.getValue();
            }
            rsSlider.adjustLowValue(intNewValue);         
        });        
        
        spinMax.valueProperty().addListener((obs, intOldValue, intNewValue) -> {
            // System.out.println("FiltersContet.initialize(): New value: "+intNewValue);
            if (intNewValue < spinMin.getValue()) {
                intNewValue = spinMin.getValue();               
            }
            rsSlider.adjustHighValue(intNewValue);
        });
        
        // Adding the listener to the RangeSlider
        rsSlider.lowValueProperty().addListener((obs, dblOldValue, dblNewValue) -> {                        
            spinMin.getValueFactory().setValue(dblNewValue.intValue());
        });        
        rsSlider.highValueProperty().addListener((obs, dblOldValue, dblNewValue) -> {
            spinMax.getValueFactory().setValue(dblNewValue.intValue());
        });
        
        strSelectedAttributeName = String.valueOf(rsSlider.getLowValue());
        strSelectedExpression = String.valueOf(rsSlider.getHighValue());
        
        HBox hboxSpinner = new HBox(spinMin, spinMax);
        
        VBox vboxLocalContainer = new VBox (hboxSpinner, rsSlider);
        
        return vboxLocalContainer;
    }
    */
    
    private VBox addValue_Between() {
        
        VBox vboxBetween = null ;
        String [] arrstrLocalValue = new String[2] ;
        try {
            Label lblError = new Label(ErrorMsgsConfig.ERROR_NUMERICVALUEONLY);
            // Label lblSanityError = new Label(ErrorMsgsConfig.ERROR_SMALLERLEFTVALUE);
            Label lblAnd = new Label(LangConfig.GENERAL_AND);
            
            System.out.println("AttributeFilterBox.addValue_Between(): ");
            
            arrstrLocalValue[0] = "0.0" ;
            arrstrLocalValue[1] = "0.0" ;
            
            TextField txtAttributeMinValue = new TextField(arrstrLocalValue[0]);
            TextField txtAttributeMaxValue = new TextField(arrstrLocalValue[1]);
                        
            
            txtAttributeMinValue.textProperty().addListener(new ChangeListener<String>() {
                @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {                
                    try {
                        if (newValue.length() != 0 && !newValue.equals("-") && !newValue.equals(".")) {
                            double dblValue = Double.parseDouble(newValue);
                            strValue1 = String.valueOf(dblValue);
                            lblError.setVisible(false);
                        }
                    } catch (Exception ex) {
                        txtAttributeMinValue.setText(oldValue);
                        lblError.setText(ErrorMsgsConfig.ERROR_NUMERICVALUEONLY);
                        lblError.setVisible(true);
                    }
                    arrstrLocalValue[0] = txtAttributeMinValue.getText();
                }
            });  
            txtAttributeMinValue.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                    if (newPropertyValue) {
                        // Events are written when the focus is shifted from Min Value Text box to another control
                    } else {
                        double dblMinValue = Double.parseDouble(txtAttributeMinValue.getText());
                        double dblMaxValue = Double.parseDouble(txtAttributeMaxValue.getText());
                        
                        if (dblMinValue > dblMaxValue) {
                            lblError.setText(ErrorMsgsConfig.ERROR_SMALLERLEFTVALUE);
                            lblError.setVisible(true);
                            FilterContent.disableFilterButton();
                            //FilterContent.disableAddVertexFilterButton();
                            btnAddNewAttributeFilterBoxButton.setDisable(true);
                        } else {
                            lblError.setVisible(false);
                            strValue1 = String.valueOf(dblMinValue);
                            strValue2 = String.valueOf(dblMaxValue);
                            FilterContent.enableFilterButton();
                            //FilterContent.enableAddVertexFilterButton();
                            btnAddNewAttributeFilterBoxButton.setDisable(false);
                        }
                    }
                }
            });
            
            
            txtAttributeMaxValue.textProperty().addListener(new ChangeListener<String>() {
                @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {                
                    try {
                        if (newValue.length() != 0 && !newValue.equals("-") && !newValue.equals(".")) {
                            double dblValue = Double.parseDouble(newValue);
                            strValue2 = String.valueOf(dblValue);
                            lblError.setVisible(false); 
                        }
                    } catch (Exception ex) {
                        txtAttributeMaxValue.setText(oldValue);
                        lblError.setText(ErrorMsgsConfig.ERROR_NUMERICVALUEONLY);
                        lblError.setVisible(true);
                    }
                    arrstrLocalValue[1] = txtAttributeMaxValue.getText();
                }
            });
            txtAttributeMaxValue.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                    if (newPropertyValue) {
                        // Events are written when the focus is shifted from Max Value Text box to another control
                    } else {
                        double dblMinValue = Double.parseDouble(txtAttributeMinValue.getText());
                        double dblMaxValue = Double.parseDouble(txtAttributeMaxValue.getText());
                        
                        if (dblMinValue > dblMaxValue) {
                            lblError.setText(ErrorMsgsConfig.ERROR_SMALLERLEFTVALUE);
                            lblError.setVisible(true);
                            FilterContent.disableFilterButton();
                            //FilterContent.disableAddVertexFilterButton();
                            btnAddNewAttributeFilterBoxButton.setDisable(true);
                        } else {
                            lblError.setVisible(false);
                            strValue1 = String.valueOf(dblMinValue);
                            strValue2 = String.valueOf(dblMaxValue);
                            FilterContent.enableFilterButton();
                            //FilterContent.enableAddVertexFilterButton();
                            btnAddNewAttributeFilterBoxButton.setDisable(false);
                        }
                    }
                }
            });
            
            
            HBox hboxTextBoxes = new HBox(txtAttributeMinValue, lblAnd, txtAttributeMaxValue) ;
            hboxTextBoxes.setPadding(new Insets(5,5,5,5));
            hboxTextBoxes.setSpacing(5);
            
            vboxBetween = new VBox(hboxTextBoxes, lblError);
            vboxBetween.setPadding(new Insets(5,5,5,5));
            vboxBetween.setSpacing(5);
            
        } catch (Exception ex) {
            System.out.println("AttributeFilterBox.addValue_Between(): EXCEPTION") ;
            ex.printStackTrace();
        }   
        return vboxBetween ;
    }
    
    public Boolean isFilledWithValues(){
        
        return !strValue1.equals("");
    }
            
    
    
    /**
     *  Method Name     : addRangeSlider()
     *  Created Date    : 2016-05-13
     *  Description     : Adds a range slider to the UI panel
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return RangeSlider
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private RangeSlider addRangeSlider() {
        RangeSlider hSlider = new RangeSlider(0, 99, 10, 90);
        hSlider.setShowTickMarks(true);
        hSlider.setShowTickLabels(true);
        hSlider.setBlockIncrement(10);
        
        return hSlider;
    }
    
    /**
     *  Method Name     : addSpinner()
     *  Created Date    : 2016-05-13
     *  Description     : Adds a spinner to the UI panel
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return Spinner<Integer>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private Spinner<Integer> addSpinner() {
        final Spinner<Integer> spinner = new Spinner();
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
        spinner.setEditable(true);
        return spinner;
    }
}
