/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import config.AppConfig;
import config.CommunityMiningConfig;
import config.LangConfig;
import config.SceneConfig;
import config.SettingsWindowConfig;
import config.StatusMsgsConfig;
import io.writer.AppSettingsWriter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.utilities.FXUtils;

/**
 *  Class Name      : ApplicationSettings
 *  Created Date    : 2016-07-11
 *  Description     : Application Settings Window that will allow the user to change the settings
 *  Version         : 1.0
 *  @author         : Talat
 * 
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-08-22      Abhi            Added UpdateConfigFiles() method - to update values from the ApplicationSettings window to the static variables in SceneConfig.java and CommunityMiningConfig.java
 *  
 * 
*/
public class ApplicationSettings {
    
    
    /*
    * These 4 instance variables act as helpers to update values in confing files.
    * A way to get values out of eventlisteners from UI elements
    */
    private static String strFastModularityAlgoType;
    private static String strLocalCommunityAlgoType = CommunityMiningConfig.LOCALCOMMUNITY_TYPE_DEFAULT;
    private static String strSelectedDCMiningMetric = CommunityMiningConfig.DCMINING_METRIC_DEFAULT;
    private static String strSelectedDCMiningMethod = CommunityMiningConfig.DCMINING_METHOD_DEFAULT;
    
    
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-07-11
     *  Description     : The Dialog Box to be showed when settings is clicked
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController ; AnalysisController
     *  @param pstrTitle : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 2016-08-16       Abhi            Added functionality to display all Algorithm parameters in Algorithms params tab
     *  
     * 
    */
    public static void Display(AnalysisController pController, String pstrTitle) {
        
        Stage stgApplicationSettings = new Stage();
        stgApplicationSettings.initModality(Modality.APPLICATION_MODAL);
        
        VBox vboxContainer = new VBox(5);
        HBox hboxButtons = new HBox();
        
        GridPane gridTab = new GridPane();
        
        // Column constraints to define the two columns
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        column2.setHgrow(Priority.ALWAYS);
        gridTab.getColumnConstraints().addAll(column1, column2);
        
        Text txtUIHeading = new Text(SettingsWindowConfig.SETTINGSWINDOW_UITABTITLE);
        // txtUIHeading.setStyle("-fx-font-weight: bold;");        
        txtUIHeading.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        
        Text txtlgorithmParameter = new Text(SettingsWindowConfig.SETTINGSWINDOW_ALGORITHMTABTITLE);
        // txtlgorithmParameter.setStyle("-fx-font-weight: regular;");
        txtlgorithmParameter.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        
        AnchorPane anchUITab = new AnchorPane(txtUIHeading);       
        AnchorPane anchAlgParamTab = new AnchorPane(txtlgorithmParameter);
        
        GridPane.setHalignment(anchUITab, HPos.LEFT);
        gridTab.add(anchUITab, 0, 0);
        GridPane.setHalignment(anchAlgParamTab, HPos.LEFT);
        gridTab.add(anchAlgParamTab, 1, 0);
                
        // Design the Items in the UI Settings        
        GridPane gridUI = new GridPane();
        
        // Column constraints to define the two columns
        ColumnConstraints colUI1 = new ColumnConstraints();
        colUI1.setPercentWidth(35);
        ColumnConstraints colUI2 = new ColumnConstraints();
        colUI2.setPercentWidth(35);
        ColumnConstraints colUI3 = new ColumnConstraints();
        colUI3.setPercentWidth(30);
        gridUI.getColumnConstraints().addAll(colUI1, colUI2, colUI3);
        
        // Add the labels and text boxes for the UI settings
        Label lblVertexColorDefault = new Label(SettingsWindowConfig.UITAB_VERTEXCOLORDEFAULT);
        GridPane.setHalignment(lblVertexColorDefault, HPos.LEFT);
        gridUI.add(lblVertexColorDefault, 0, 0);
        TextField txtVertexColorDefault = new TextField(SceneConfig.VERTEX_COLOR_DEFAULT);
        GridPane.setHalignment(txtVertexColorDefault, HPos.LEFT);
        gridUI.add(txtVertexColorDefault, 1, 0);        
        txtVertexColorDefault.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                SceneConfig.VERTEX_COLOR_DEFAULT = txtVertexColorDefault.getText() ;
            }
        });        
        txtVertexColorDefault.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            
        });
        ColorPicker clrVertexColorDefault = new ColorPicker(Color.valueOf(SceneConfig.VERTEX_COLOR_DEFAULT));
        GridPane.setHalignment(clrVertexColorDefault, HPos.LEFT);
        gridUI.add(clrVertexColorDefault, 2, 0);
        clrVertexColorDefault.setOnAction( e -> {
            SceneConfig.VERTEX_COLOR_DEFAULT = FXUtils.ColorToHex(clrVertexColorDefault.getValue());
            txtVertexColorDefault.setText(SceneConfig.VERTEX_COLOR_DEFAULT);
        });
        
        Label lblVertexColorSelected = new Label(SettingsWindowConfig.UITAB_VERTEXTCOLORSELECTED);
        GridPane.setHalignment(lblVertexColorSelected, HPos.LEFT);
        gridUI.add(lblVertexColorSelected, 0, 1);
        TextField txtVertexColorSelected = new TextField(SceneConfig.VERTEX_COLOR_SELECTED);
        GridPane.setHalignment(txtVertexColorSelected, HPos.LEFT);
        gridUI.add(txtVertexColorSelected, 1, 1);
        txtVertexColorSelected.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                SceneConfig.VERTEX_COLOR_SELECTED = txtVertexColorSelected.getText() ;
            }
        });        
        txtVertexColorSelected.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            
        });
        ColorPicker clrVertexColorSelected = new ColorPicker(Color.valueOf(SceneConfig.VERTEX_COLOR_SELECTED));
        GridPane.setHalignment(clrVertexColorSelected, HPos.LEFT);
        gridUI.add(clrVertexColorSelected, 2, 1);
        clrVertexColorSelected.setOnAction( e -> {
            SceneConfig.VERTEX_COLOR_SELECTED = FXUtils.ColorToHex(clrVertexColorSelected.getValue());
            txtVertexColorSelected.setText(SceneConfig.VERTEX_COLOR_SELECTED);
        });
        
        
        Label lblVertexShapeDefault = new Label(SettingsWindowConfig.UITAB_VERTEXSHAPEDEFAULT);
        GridPane.setHalignment(lblVertexShapeDefault, HPos.LEFT);
        gridUI.add(lblVertexShapeDefault, 0, 2);
        TextField txtVertexShapeDefault = new TextField(String.valueOf(SceneConfig.VERTEX_SHAPE_DEFAULT_INT));
        GridPane.setHalignment(txtVertexShapeDefault, HPos.LEFT);
        gridUI.add(txtVertexShapeDefault, 1, 2);
        txtVertexShapeDefault.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                SceneConfig.VERTEX_SHAPE_DEFAULT_INT = Integer.parseInt(txtVertexShapeDefault.getText()) ;
            }
        });        
        txtVertexShapeDefault.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                if (newValue.length() != 0 && !newValue.equals("-") && !newValue.equals(".")) {
                    // double dblValue = Double.parseDouble(newValue);
                }
            } catch (Exception ex) {
                txtVertexShapeDefault.setText(oldValue);
            }
        });
        
        
        Label lblVertexSizeDefault = new Label(SettingsWindowConfig.UITAB_VERTEXSIZEDEFAULT);
        GridPane.setHalignment(lblVertexSizeDefault, HPos.LEFT);
        gridUI.add(lblVertexSizeDefault, 0, 3);
        TextField txtVertexSizeDefault = new TextField(String.valueOf(SceneConfig.VERTEX_SIZE_DEFAULT));
        GridPane.setHalignment(txtVertexSizeDefault, HPos.LEFT);
        gridUI.add(txtVertexSizeDefault, 1, 3);
        txtVertexSizeDefault.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                SceneConfig.VERTEX_SIZE_DEFAULT = Double.parseDouble(txtVertexSizeDefault.getText()) ;
            }
        });        
        txtVertexSizeDefault.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                if (newValue.length() != 0 && !newValue.equals("-") && !newValue.equals(".")) {
                    double dblValue = Double.parseDouble(newValue);
                }
            } catch (Exception ex) {
                txtVertexSizeDefault.setText(oldValue);
            }
        });
        
        
        Label lblVertexOpacityDefault = new Label(SettingsWindowConfig.UITAB_VERTEXOPACITYDEFAULT);
        GridPane.setHalignment(lblVertexOpacityDefault, HPos.LEFT);
        gridUI.add(lblVertexOpacityDefault, 0, 4);
        TextField txtVertexOpacityDefault = new TextField(String.valueOf(SceneConfig.VERTEX_OPACITY));
        GridPane.setHalignment(txtVertexOpacityDefault, HPos.LEFT);
        gridUI.add(txtVertexOpacityDefault, 1, 4);
        txtVertexOpacityDefault.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                SceneConfig.VERTEX_OPACITY = Double.parseDouble(txtVertexOpacityDefault.getText()) ;
            }
        });        
        txtVertexOpacityDefault.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                if (newValue.length() != 0 && !newValue.equals("-") && !newValue.equals(".")) {
                    double dblValue = Double.parseDouble(newValue);
                }
            } catch (Exception ex) {
                txtVertexOpacityDefault.setText(oldValue);
            }
        });
        
        
        Label lblVertexLabelFontSizeDefault = new Label(SettingsWindowConfig.UITAB_VERTEXLABELFONTSIZE);
        GridPane.setHalignment(lblVertexLabelFontSizeDefault, HPos.LEFT);
        gridUI.add(lblVertexLabelFontSizeDefault, 0, 5);
        TextField txtVertexLabelFontSizeDefault = new TextField(String.valueOf(SceneConfig.VERTEX_LABEL_FONTSIZE_DEFAULT));
        GridPane.setHalignment(txtVertexLabelFontSizeDefault, HPos.LEFT);
        gridUI.add(txtVertexLabelFontSizeDefault, 1, 5);
        txtVertexLabelFontSizeDefault.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                SceneConfig.VERTEX_LABEL_FONTSIZE_DEFAULT = Integer.parseInt(txtVertexLabelFontSizeDefault.getText()) ;
            }
        });        
        txtVertexLabelFontSizeDefault.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                if (newValue.length() != 0 && !newValue.equals("-") && !newValue.equals(".")) {
                    int intValue = Integer.parseInt(newValue);
                }
            } catch (Exception ex) {
                txtVertexLabelFontSizeDefault.setText(oldValue);
            }
        });
        
        
        
        
        Label lblEdgeColorPrimary = new Label(SettingsWindowConfig.UITAB_EDGECOLORPRIMARY);
        GridPane.setHalignment(lblEdgeColorPrimary, HPos.LEFT);
        gridUI.add(lblEdgeColorPrimary, 0, 6);
        TextField txtEdgeColorPrimary = new TextField(SceneConfig.EDGE_COLOR_PRIMARYCOLOR);
        GridPane.setHalignment(txtEdgeColorPrimary, HPos.LEFT);
        gridUI.add(txtEdgeColorPrimary, 1, 6);
        txtEdgeColorPrimary.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                SceneConfig.EDGE_COLOR_PRIMARYCOLOR = txtEdgeColorPrimary.getText() ;
            }
        });        
        txtEdgeColorPrimary.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                if (newValue.length() != 0 && !newValue.equals("-") && !newValue.equals(".")) {
                    // double dblValue = Double.parseDouble(newValue);
                }
            } catch (Exception ex) {
                txtEdgeColorPrimary.setText(oldValue);
            }
        });
        ColorPicker clrEdgeColorPrimary = new ColorPicker(Color.valueOf(SceneConfig.EDGE_COLOR_PRIMARYCOLOR));
        GridPane.setHalignment(clrEdgeColorPrimary, HPos.LEFT);
        gridUI.add(clrEdgeColorPrimary, 2, 6);
        clrEdgeColorPrimary.setOnAction( e -> {
            SceneConfig.EDGE_COLOR_PRIMARYCOLOR = FXUtils.ColorToHex(clrEdgeColorPrimary.getValue());
            txtVertexColorSelected.setText(SceneConfig.EDGE_COLOR_PRIMARYCOLOR);
        });
        
        Label lblEdgeColorSecondary = new Label(SettingsWindowConfig.UITAB_EDGECOLORSECONDARY);
        GridPane.setHalignment(lblEdgeColorSecondary, HPos.LEFT);
        gridUI.add(lblEdgeColorSecondary, 0, 7);
        TextField txtEdgeColorSecondary = new TextField(SceneConfig.EDGE_COLOR_SECONDARYCOLOR);
        GridPane.setHalignment(txtEdgeColorSecondary, HPos.LEFT);
        gridUI.add(txtEdgeColorSecondary, 1, 7);
        txtEdgeColorSecondary.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                SceneConfig.EDGE_COLOR_SECONDARYCOLOR = txtEdgeColorSecondary.getText() ;
            }
        });        
        txtEdgeColorSecondary.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            
        });
        ColorPicker clrEdgeColorSecondary = new ColorPicker(Color.valueOf(SceneConfig.EDGE_COLOR_SECONDARYCOLOR));
        GridPane.setHalignment(clrEdgeColorSecondary, HPos.LEFT);
        gridUI.add(clrEdgeColorSecondary, 2, 7);
        clrEdgeColorSecondary.setOnAction( e -> {
            SceneConfig.EDGE_COLOR_SECONDARYCOLOR = FXUtils.ColorToHex(clrEdgeColorSecondary.getValue());
            txtVertexColorSelected.setText(SceneConfig.EDGE_COLOR_SECONDARYCOLOR);
        });
        
        
        Label lblEdgeColorTertiary = new Label(SettingsWindowConfig.UITAB_EDGECOLORTERTIARY);
        GridPane.setHalignment(lblEdgeColorTertiary, HPos.LEFT);
        gridUI.add(lblEdgeColorTertiary, 0, 8);
        TextField txtEdgeColorTertiary = new TextField(SceneConfig.EDGE_COLOR_TERTIARYCOLOR);
        GridPane.setHalignment(txtEdgeColorTertiary, HPos.LEFT);
        gridUI.add(txtEdgeColorTertiary, 1, 8);
        txtEdgeColorTertiary.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                SceneConfig.EDGE_COLOR_TERTIARYCOLOR = txtEdgeColorTertiary.getText() ;
            }
        });        
        txtEdgeColorTertiary.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            
        });
        ColorPicker clrEdgeColorTertiary = new ColorPicker(Color.valueOf(SceneConfig.EDGE_COLOR_TERTIARYCOLOR));
        GridPane.setHalignment(clrEdgeColorTertiary, HPos.LEFT);
        gridUI.add(clrEdgeColorTertiary, 2, 8);
        clrEdgeColorTertiary.setOnAction( e -> {
            SceneConfig.EDGE_COLOR_TERTIARYCOLOR = FXUtils.ColorToHex(clrEdgeColorTertiary.getValue());
            txtVertexColorSelected.setText(SceneConfig.EDGE_COLOR_TERTIARYCOLOR);
        });
        
        
        Label lblEdgeOpacity = new Label(SettingsWindowConfig.UITAB_EDGEOPACITY);
        GridPane.setHalignment(lblEdgeOpacity, HPos.LEFT);
        gridUI.add(lblEdgeOpacity, 0, 9);
        TextField txtEdgeOpacity = new TextField(String.valueOf(SceneConfig.EDGE_OPACITY));
        GridPane.setHalignment(txtEdgeOpacity, HPos.LEFT);
        gridUI.add(txtEdgeOpacity, 1, 9);
        txtEdgeOpacity.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                SceneConfig.EDGE_OPACITY = Double.parseDouble(txtEdgeOpacity.getText()) ;
            }
        });        
        txtEdgeOpacity.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                if (newValue.length() != 0 && !newValue.equals("-") && !newValue.equals(".")) {
                    double dblValue = Double.parseDouble(newValue);
                }
            } catch (Exception ex) {
                txtEdgeOpacity.setText(oldValue);
            }
        });
        
        
        Label lblEdgeStrokeWidth = new Label(SettingsWindowConfig.UITAB_EDGESTROKEWIDTH);
        GridPane.setHalignment(lblEdgeStrokeWidth, HPos.LEFT);
        gridUI.add(lblEdgeStrokeWidth, 0, 10);
        TextField txtEdgeStrokeWidth = new TextField(String.valueOf(SceneConfig.EDGE_STROKEWIDTH));
        GridPane.setHalignment(txtEdgeStrokeWidth, HPos.LEFT);
        gridUI.add(txtEdgeStrokeWidth, 1, 10);
        txtEdgeStrokeWidth.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                SceneConfig.EDGE_STROKEWIDTH = Double.parseDouble(txtEdgeStrokeWidth.getText()) ;
            }
        });        
        txtEdgeStrokeWidth.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                if (newValue.length() != 0 && !newValue.equals("-") && !newValue.equals(".")) {
                   double dblValue = Double.parseDouble(newValue);
                }
            } catch (Exception ex) {
                txtEdgeStrokeWidth.setText(oldValue);
            }
        });
        
        
        
        
        Label lblCanvasBackgroundColorDefault = new Label(SettingsWindowConfig.UITAB_CANVASBGCOLORDEFAULT);
        GridPane.setHalignment(lblCanvasBackgroundColorDefault, HPos.LEFT);
        gridUI.add(lblCanvasBackgroundColorDefault, 0, 11);
        TextField txtCanvasBackgroundColorDefault = new TextField(String.valueOf(SceneConfig.CANVAS_BACKGROUND_COLOR));
        GridPane.setHalignment(txtCanvasBackgroundColorDefault, HPos.LEFT);
        gridUI.add(txtCanvasBackgroundColorDefault, 1, 11);
        txtCanvasBackgroundColorDefault.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                SceneConfig.CANVAS_BACKGROUND_COLOR = txtCanvasBackgroundColorDefault.getText() ;
            }
        });        
        txtCanvasBackgroundColorDefault.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            
        });
        ColorPicker clrCanvasBackgroundColorDefault = new ColorPicker(Color.valueOf(SceneConfig.CANVAS_BACKGROUND_COLOR));
        GridPane.setHalignment(clrCanvasBackgroundColorDefault, HPos.LEFT);
        gridUI.add(clrCanvasBackgroundColorDefault, 2, 11);
        clrCanvasBackgroundColorDefault.setOnAction( e -> {
            SceneConfig.CANVAS_BACKGROUND_COLOR = FXUtils.ColorToHex(clrCanvasBackgroundColorDefault.getValue());
            txtVertexColorSelected.setText(SceneConfig.CANVAS_BACKGROUND_COLOR);
        });
        
        
        Label lblCanvasDragRectColor = new Label(SettingsWindowConfig.UITAB_CANVASDRAGRECTCOLOR);
        GridPane.setHalignment(lblCanvasDragRectColor, HPos.LEFT);
        gridUI.add(lblCanvasDragRectColor, 0, 12);
        TextField txtCanvasDragRectColor = new TextField(String.valueOf(SceneConfig.CANVAS_DRAGRECT_COLOR));
        GridPane.setHalignment(txtCanvasDragRectColor, HPos.LEFT);
        gridUI.add(txtCanvasDragRectColor, 1, 12);
        txtCanvasDragRectColor.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                SceneConfig.CANVAS_DRAGRECT_COLOR = txtCanvasDragRectColor.getText() ;
            }
        });        
        txtCanvasDragRectColor.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            
        });
        ColorPicker clrCanvasDragRectColor = new ColorPicker(Color.valueOf(SceneConfig.CANVAS_DRAGRECT_COLOR));
        GridPane.setHalignment(clrCanvasDragRectColor, HPos.LEFT);
        gridUI.add(clrCanvasDragRectColor, 2, 12);
        clrCanvasDragRectColor.setOnAction( e -> {
            SceneConfig.CANVAS_DRAGRECT_COLOR = FXUtils.ColorToHex(clrCanvasDragRectColor.getValue());
            txtVertexColorSelected.setText(SceneConfig.CANVAS_DRAGRECT_COLOR);
        });
        
        Label lblCanvasDragRectOpacity = new Label(SettingsWindowConfig.UITAB_CANVASDRAGRECTOPACITY);
        GridPane.setHalignment(lblCanvasDragRectOpacity, HPos.LEFT);
        gridUI.add(lblCanvasDragRectOpacity, 0, 13);
        TextField txtCanvasDragRectOpacity = new TextField(String.valueOf(SceneConfig.CANVAS_DRAGRECT_OPACITY));
        GridPane.setHalignment(txtCanvasDragRectOpacity, HPos.LEFT);
        gridUI.add(txtCanvasDragRectOpacity, 1, 13);
        txtCanvasDragRectOpacity.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                SceneConfig.CANVAS_DRAGRECT_OPACITY = Double.parseDouble(txtCanvasDragRectOpacity.getText()) ;
            }
        });        
        txtCanvasDragRectOpacity.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                if (newValue.length() != 0 && !newValue.equals("-") && !newValue.equals(".")) {
                    double dblValue = Double.parseDouble(newValue);
                }
            } catch (Exception ex) {
                txtCanvasDragRectOpacity.setText(oldValue);
            }
        });
        
        
        Label lblCanvasZoomFactor = new Label(SettingsWindowConfig.UITAB_CANVASZOOMFACTOR);
        GridPane.setHalignment(lblCanvasZoomFactor, HPos.LEFT);
        gridUI.add(lblCanvasZoomFactor, 0, 14);
        TextField txtCanvasZoomFactor = new TextField(String.valueOf(SceneConfig.CANVAS_SCROLL_SCALEFACTOR));
        GridPane.setHalignment(txtCanvasZoomFactor, HPos.LEFT);
        gridUI.add(txtCanvasZoomFactor, 1, 14);
        txtCanvasZoomFactor.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                SceneConfig.CANVAS_SCROLL_SCALEFACTOR = Double.parseDouble(txtCanvasZoomFactor.getText());
            }
        });        
        txtCanvasZoomFactor.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                if (newValue.length() != 0 && !newValue.equals("-") && !newValue.equals(".")) {
                    double dblValue = Double.parseDouble(newValue);
                }
            } catch (Exception ex) {
                txtCanvasZoomFactor.setText(oldValue);
            }
        });
                
        /*
        * Design the Items in the Algorithm Parameter Settings tab   
        */
        //TODO start intRowNumber from 0, to start from uiTab        
        int intRowNumber = 13;
        GridPane gridAlgParam = new GridPane();
        // Column constraints to define the two columns
        ColumnConstraints colAlgParam1 = new ColumnConstraints();
        colAlgParam1.setPercentWidth(50);
        ColumnConstraints colAlgParam2 = new ColumnConstraints();
        colAlgParam2.setPercentWidth(50);
        colAlgParam2.setHgrow(Priority.ALWAYS);
        gridAlgParam.getColumnConstraints().addAll(colAlgParam1, colAlgParam2);
        
        intRowNumber = putGroupsSeparator(gridAlgParam, intRowNumber);
        // K Means cluster count param
        Label lblAlgTabKmeansClutserCount = new Label(SettingsWindowConfig.ALGOPARAMTAB_KMEANSCLUSTERCOUNT);
        GridPane.setHalignment(lblAlgTabKmeansClutserCount, HPos.LEFT);
        gridAlgParam.add(lblAlgTabKmeansClutserCount, 0, intRowNumber);
        final Spinner<Integer> spnKMeansClusterCount = new Spinner();
        spnKMeansClusterCount.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                              CommunityMiningConfig.KMEANS_CLUSTERCOUNT_MIN
                            , CommunityMiningConfig.KMEANS_CLUSTERCOUNT_MAX
                            , CommunityMiningConfig.KMEANS_CLUSTERCOUNT_DEFAULT
                            , CommunityMiningConfig.KMEANS_CLUSTERCOUNT_STEP
                    ));
        spnKMeansClusterCount.setEditable(true);
        spnKMeansClusterCount.setMaxWidth(100);
        GridPane.setHalignment(spnKMeansClusterCount, HPos.LEFT);
        gridAlgParam.add(spnKMeansClusterCount, 1, intRowNumber++);
        // TODO check validity of value that user is entering, if not valid- show an alert window

        spnKMeansClusterCount.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                CommunityMiningConfig.KMEANS_CLUSTERCOUNT_DEFAULT = spnKMeansClusterCount.getValue() ;
            }
        });
        
        intRowNumber = putGroupsSeparator(gridAlgParam, intRowNumber);
        
        // Same Attribute Value Multiple Values or Not Parameter
        Label lblAlgTabSameAttrValMultiple = new Label(SettingsWindowConfig.ALGOPARAMTAB_SAMEATTRIBUTEVALUE_MULTIPLEVALUES);
        GridPane.setHalignment(lblAlgTabSameAttrValMultiple, HPos.LEFT);
        gridAlgParam.add(lblAlgTabSameAttrValMultiple, 0, intRowNumber);
        CheckBox chbSameAttrValMultiple = new CheckBox();
        chbSameAttrValMultiple.setSelected(CommunityMiningConfig.SAMEVALUEATTR_MULTIPLEVALUES);
        GridPane.setHalignment(chbSameAttrValMultiple, HPos.LEFT);
        gridAlgParam.add(chbSameAttrValMultiple, 1, intRowNumber++);
        
        intRowNumber = putGroupsSeparator(gridAlgParam, intRowNumber);
        
        // Fast Modularity Algorithm Type Parameter
        Label lblAlgTabFastModularityAlgoType = new Label(SettingsWindowConfig.ALGOPARAMTAB_FASTMODULARITY_ALGORITHM);
        strFastModularityAlgoType = CommunityMiningConfig.FM_METRIC_DEFAULT;
        GridPane.setHalignment(lblAlgTabFastModularityAlgoType, HPos.LEFT);
        gridAlgParam.add(lblAlgTabFastModularityAlgoType, 0, intRowNumber);
        HBox hboxModularity = new HBox();
        hboxModularity.setAlignment(Pos.CENTER_LEFT);
            hboxModularity.setPadding(new Insets(5, 5, 5, 5));
            hboxModularity.setSpacing(30);
            ToggleGroup tgModularity = new ToggleGroup();
            for (String strModularity : CommunityMiningConfig.FM_METRIC) {
                RadioButton rbModularity = new RadioButton(strModularity);
                rbModularity.setToggleGroup(tgModularity);
                // set default selected value of the toggle group
                if(strModularity.equalsIgnoreCase(CommunityMiningConfig.FM_METRIC_DEFAULT)){
                    rbModularity.setSelected(true);
                }
                hboxModularity.getChildren().add(rbModularity);                
            }
          
        tgModularity.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) -> {
                RadioButton rbtnSelectedLang = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button                        
                strFastModularityAlgoType = rbtnSelectedLang.getText() ;
                
            });
        GridPane.setHalignment(hboxModularity, HPos.LEFT);
        gridAlgParam.add(hboxModularity, 1, intRowNumber++);
        // Fast Modularity Weighted? Parameter
        Label lblAlgTabFastModularityWeighted = new Label(SettingsWindowConfig.ALGOPARAMTAB_FASTMODULARITY_WEIGHTED);
        GridPane.setHalignment(lblAlgTabFastModularityWeighted, HPos.LEFT);
        gridAlgParam.add(lblAlgTabFastModularityWeighted, 0, intRowNumber);
        CheckBox chbFastModularityWeighted = new CheckBox();
        chbFastModularityWeighted.setSelected(CommunityMiningConfig.FM_WEIGHTED);
        GridPane.setHalignment(chbFastModularityWeighted, HPos.LEFT);
        gridAlgParam.add(chbFastModularityWeighted, 1, intRowNumber++);
        
        intRowNumber = putGroupsSeparator(gridAlgParam, intRowNumber);
        
        // Local top leader // 
        // Local top leader cluster count parameter
        Label lblAlgTabLocalTopLeaderClusterCount = new Label(SettingsWindowConfig.ALGOPARAMTAB_LOCALTOPLEADERS_CLUSTERCOUNT);
        GridPane.setHalignment(lblAlgTabLocalTopLeaderClusterCount, HPos.LEFT);
        gridAlgParam.add(lblAlgTabLocalTopLeaderClusterCount, 0, intRowNumber);
        final Spinner<Integer> spnrLocalTopLeaderClusterCount = new Spinner();
            spnrLocalTopLeaderClusterCount.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                              CommunityMiningConfig.LOCALTOPLEADERS_CLUSTERCOUNT_MIN
                            , CommunityMiningConfig.LOCALTOPLEADERS_CLUSTERCOUNT_MAX
                            , CommunityMiningConfig.LOCALTOPLEADERS_CLUSTERCOUNT_DEFAULT
                            , CommunityMiningConfig.LOCALTOPLEADERS_CLUSTERCOUNT_STEP
                    ));
            spnrLocalTopLeaderClusterCount.setEditable(true);
            spnrLocalTopLeaderClusterCount.setMaxWidth(60);
        GridPane.setHalignment(spnrLocalTopLeaderClusterCount, HPos.LEFT);
        gridAlgParam.add(spnrLocalTopLeaderClusterCount, 1, intRowNumber++);
        spnrLocalTopLeaderClusterCount.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                CommunityMiningConfig.LOCALTOPLEADERS_CLUSTERCOUNT_DEFAULT = spnrLocalTopLeaderClusterCount.getValue();
            }
        });
        // Local top leader outlier percentage parameter
        Label lblAlgTabLocalTopLeaderOutlierPercentage = new Label(SettingsWindowConfig.ALGOPARAMTAB_LOCALTOPLEADERS_OUTLIERPERCENTAGE);
        GridPane.setHalignment(lblAlgTabLocalTopLeaderOutlierPercentage, HPos.LEFT);
        gridAlgParam.add(lblAlgTabLocalTopLeaderOutlierPercentage, 0, intRowNumber);
        final Spinner<Double> spnrLocalTopLeaderOutlierPercentage = new Spinner();
            spnrLocalTopLeaderOutlierPercentage.setValueFactory(
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(
                              CommunityMiningConfig.LOCALTOPLEADERS_OUTLIERPERCENTAGE_MIN
                            , CommunityMiningConfig.LOCALTOPLEADERS_OUTLIERPERCENTAGE_MAX
                            , CommunityMiningConfig.LOCALTOPLEADERS_OUTLIERPERCENTAGE_DEFAULT
                            , CommunityMiningConfig.LOCALTOPLEADERS_OUTLIERPERCENTAGE_STEP
                    ));
            spnrLocalTopLeaderOutlierPercentage.setEditable(true);
            spnrLocalTopLeaderOutlierPercentage.setMaxWidth(60);
        GridPane.setHalignment(spnrLocalTopLeaderOutlierPercentage, HPos.LEFT);
        gridAlgParam.add(spnrLocalTopLeaderOutlierPercentage, 1, intRowNumber++);
        spnrLocalTopLeaderOutlierPercentage.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                CommunityMiningConfig.LOCALTOPLEADERS_OUTLIERPERCENTAGE_DEFAULT = spnrLocalTopLeaderOutlierPercentage.getValue();
            }
        });
        // Local top leader Hub percentage parameter
        Label lblAlgTabLocalTopLeaderHubPercentage = new Label(SettingsWindowConfig.ALGOPARAMTAB_LOCALTOPLEADERS_HUBPERCENTAGE);
        GridPane.setHalignment(lblAlgTabLocalTopLeaderHubPercentage, HPos.LEFT);
        gridAlgParam.add(lblAlgTabLocalTopLeaderHubPercentage, 0, intRowNumber);
        final Spinner<Double> spnrLocalTopLeaderHubPercentage = new Spinner();
            spnrLocalTopLeaderHubPercentage.setValueFactory(
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(
                              CommunityMiningConfig.LOCALTOPLEADERS_HUBPERCENTAGE_MIN
                            , CommunityMiningConfig.LOCALTOPLEADERS_HUBPERCENTAGE_MAX
                            , CommunityMiningConfig.LOCALTOPLEADERS_HUBPERCENTAGE_DEFAULT
                            , CommunityMiningConfig.LOCALTOPLEADERS_HUBPERCENTAGE_STEP
                    ));
            spnrLocalTopLeaderHubPercentage.setEditable(true);
            spnrLocalTopLeaderHubPercentage.setMaxWidth(60);
        GridPane.setHalignment(spnrLocalTopLeaderHubPercentage, HPos.LEFT);
        gridAlgParam.add(spnrLocalTopLeaderHubPercentage, 1, intRowNumber++);
        spnrLocalTopLeaderHubPercentage.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                CommunityMiningConfig.LOCALTOPLEADERS_HUBPERCENTAGE_DEFAULT = spnrLocalTopLeaderHubPercentage.getValue();
            }
        });
        // Local top leader Center Distance parameter
        Label lblAlgTabLocalTopLeaderCenterDistance = new Label(SettingsWindowConfig.ALGOPARAMTAB_LOCALTOPLEADERS_CENTERDISTANCE);
        GridPane.setHalignment(lblAlgTabLocalTopLeaderCenterDistance, HPos.LEFT);
        gridAlgParam.add(lblAlgTabLocalTopLeaderCenterDistance, 0, intRowNumber);
        final Spinner<Double> spnrLocalTopLeaderCenterDistance = new Spinner();
            spnrLocalTopLeaderCenterDistance.setValueFactory(
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(
                              CommunityMiningConfig.LOCALTOPLEADERS_CENTERDISTANCE_MIN
                            , CommunityMiningConfig.LOCALTOPLEADERS_CENTERDISTANCE_MAX
                            , CommunityMiningConfig.LOCALTOPLEADERS_CENTERDISTANCE_DEFAULT
                            , CommunityMiningConfig.LOCALTOPLEADERS_CENTERDISTANCE_STEP
                    ));
            spnrLocalTopLeaderCenterDistance.setEditable(true);
            spnrLocalTopLeaderCenterDistance.setMaxWidth(60);
        GridPane.setHalignment(spnrLocalTopLeaderCenterDistance, HPos.LEFT);
        gridAlgParam.add(spnrLocalTopLeaderCenterDistance, 1, intRowNumber++);
        spnrLocalTopLeaderCenterDistance.setOnKeyPressed((KeyEvent ke) -> {            
            if (ke.getCode().equals(KeyCode.ENTER)){
                CommunityMiningConfig.LOCALTOPLEADERS_CENTERDISTANCE_DEFAULT = spnrLocalTopLeaderCenterDistance.getValue();
            }
        });
        
        intRowNumber = putGroupsSeparator(gridAlgParam, intRowNumber);
                
        // Local Community Algorithm Type
        Label lblAlgTabLocalCommunityAlgorithm = new Label(SettingsWindowConfig.ALGOPARAMTAB_LOCALCOMMUNITY_ALGORITHM);
        GridPane.setHalignment(lblAlgTabLocalCommunityAlgorithm, HPos.LEFT);
        gridAlgParam.add(lblAlgTabLocalCommunityAlgorithm, 0, intRowNumber);
        ComboBox cmbLocalCommunityAlgorithmType = new ComboBox() ;
            cmbLocalCommunityAlgorithmType.getItems().addAll(CommunityMiningConfig.LOCALCOMMUNITY_TYPE) ;
            cmbLocalCommunityAlgorithmType.setMaxWidth(100);
            cmbLocalCommunityAlgorithmType.setMinWidth(100);
        cmbLocalCommunityAlgorithmType.setValue(CommunityMiningConfig.LOCALCOMMUNITY_TYPE_DEFAULT);
        System.out.println("CommunityMiningConfig.LOCALCOMMUNITY_TYPE_DEFAULT : " + CommunityMiningConfig.LOCALCOMMUNITY_TYPE_DEFAULT);
            cmbLocalCommunityAlgorithmType.valueProperty().addListener(new ChangeListener<String>() {
                @Override 
                public void changed(ObservableValue ov, String t, String t1) {
                    strLocalCommunityAlgoType = t1 ;
                    System.out.println("strLocalCommunityAlgoType : " + strLocalCommunityAlgoType);
                }    
            });
        GridPane.setHalignment(cmbLocalCommunityAlgorithmType, HPos.LEFT);
        gridAlgParam.add(cmbLocalCommunityAlgorithmType, 1, intRowNumber++);
        // Local Community Overlap?
        Label lblAlgTabLocalCommunityOverlap = new Label(SettingsWindowConfig.ALGOPARAMTAB_LOCALCOMMUNITY_OVERLAP);
        GridPane.setHalignment(lblAlgTabLocalCommunityOverlap, HPos.LEFT);
        gridAlgParam.add(lblAlgTabLocalCommunityOverlap, 0, intRowNumber);
        CheckBox chbLocalCommunityOverLap = new CheckBox();
        chbLocalCommunityOverLap.setSelected(CommunityMiningConfig.LOCALCOMMUNITY_OVERLAP);
        GridPane.setHalignment(chbLocalCommunityOverLap, HPos.LEFT);
        gridAlgParam.add(chbLocalCommunityOverLap, 1, intRowNumber++);
        
        intRowNumber = putGroupsSeparator(gridAlgParam, intRowNumber);
        
        // RosvallInfoMap Number of Attempts
        Label lblAlgTabRosvallInfoMapNoOfAttempts = new Label(SettingsWindowConfig.ALGOPARAMTAB_ROSVALLINFOMAP_ATTEMPTS);
        GridPane.setHalignment(lblAlgTabRosvallInfoMapNoOfAttempts, HPos.LEFT);
        gridAlgParam.add(lblAlgTabRosvallInfoMapNoOfAttempts, 0, intRowNumber);
        final Spinner<Double> spnrRosvallInfoMapAttemptsCount = new Spinner();
            spnrRosvallInfoMapAttemptsCount.setValueFactory(
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(
                              CommunityMiningConfig.ROSVALLINFOMAP_ATTEMPTS_MIN
                            , CommunityMiningConfig.ROSVALLINFOMAP_ATTEMPTS_MAX
                            , CommunityMiningConfig.ROSVALLINFOMAP_ATTEMPTS_DEFAULT
                            , CommunityMiningConfig.ROSVALLINFOMAP_ATTEMPTS_STEP
                    ));
            spnrRosvallInfoMapAttemptsCount.setEditable(true);
            spnrRosvallInfoMapAttemptsCount.setMaxWidth(60);
        GridPane.setHalignment(spnrRosvallInfoMapAttemptsCount, HPos.LEFT);
        gridAlgParam.add(spnrRosvallInfoMapAttemptsCount, 1, intRowNumber++);
        // RosvallInfoMap Is Directed?
        Label lblAlgTabRosvallInfoMapIsDirected = new Label(SettingsWindowConfig.ALGOPARAMTAB_ROSVALLINFOMAP_ISDIRECTED);
        GridPane.setHalignment(lblAlgTabRosvallInfoMapIsDirected, HPos.LEFT);
        gridAlgParam.add(lblAlgTabRosvallInfoMapIsDirected, 0, intRowNumber);
        CheckBox chbRosvallInfoMapIsDirected = new CheckBox();
        chbRosvallInfoMapIsDirected.setSelected(CommunityMiningConfig.ROSVALLINFOMAP_ISDIRECTED);
        GridPane.setHalignment(chbRosvallInfoMapIsDirected, HPos.LEFT);
        gridAlgParam.add(chbRosvallInfoMapIsDirected, 1, intRowNumber++);
        
        intRowNumber = putGroupsSeparator(gridAlgParam, intRowNumber);
        
        // RosvallInfoMod Number of Attempts
        Label lblAlgTabRosvallInfoModNoOfAttempts = new Label(SettingsWindowConfig.ALGOPARAMTAB_ROSVALLINFOMOD_ATTEMPTS);
        GridPane.setHalignment(lblAlgTabRosvallInfoModNoOfAttempts, HPos.LEFT);
        gridAlgParam.add(lblAlgTabRosvallInfoModNoOfAttempts, 0, intRowNumber);
        final Spinner<Double> spnrRosvallInfoModAttemptsCount = new Spinner();
            spnrRosvallInfoModAttemptsCount.setValueFactory(
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(
                              CommunityMiningConfig.ROSVALLINFOMOD_ATTEMPTS_MIN
                            , CommunityMiningConfig.ROSVALLINFOMOD_ATTEMPTS_MAX
                            , CommunityMiningConfig.ROSVALLINFOMOD_ATTEMPTS_DEFAULT
                            , CommunityMiningConfig.ROSVALLINFOMOD_ATTEMPTS_STEP
                    ));
            spnrRosvallInfoModAttemptsCount.setEditable(true);
            spnrRosvallInfoModAttemptsCount.setMaxWidth(60);
        GridPane.setHalignment(spnrRosvallInfoModAttemptsCount, HPos.LEFT);
        gridAlgParam.add(spnrRosvallInfoModAttemptsCount, 1, intRowNumber++);
        
        intRowNumber = putGroupsSeparator(gridAlgParam, intRowNumber);
        
        // Dynamic Community Mining
        // DC Mining SImilarity Threshold
        Label lblAlgTabDCMiningSimThreshold = new Label(SettingsWindowConfig.ALGOPARAMTAB_DCMINING_SIMILARITYTHRESHOLD);
        GridPane.setHalignment(lblAlgTabDCMiningSimThreshold, HPos.LEFT);
        gridAlgParam.add(lblAlgTabDCMiningSimThreshold, 0, intRowNumber);
        final Spinner<Double> spnrDCMiningSimilarityThreshold = new Spinner();
            spnrDCMiningSimilarityThreshold.setValueFactory(
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(
                              CommunityMiningConfig.DCMINING_SIMILARITYTHRESHOLD_MIN
                            , CommunityMiningConfig.DCMINING_SIMILARITYTHRESHOLD_MAX
                            , CommunityMiningConfig.DCMINING_SIMILARITYTHRESHOLD_DEFAULT
                            , CommunityMiningConfig.DCMINING_SIMILARITYTHRESHOLD_STEP
                    ));
            spnrDCMiningSimilarityThreshold.setEditable(true);
            spnrDCMiningSimilarityThreshold.setMaxWidth(100);
        GridPane.setHalignment(spnrDCMiningSimilarityThreshold, HPos.LEFT);
        gridAlgParam.add(spnrDCMiningSimilarityThreshold, 1, intRowNumber++);
        // 2.
        Label lblAlgTabDCMiningMetric = new Label(SettingsWindowConfig.ALGOPARAMTAB_DCMINING_METRIC);
        GridPane.setHalignment(lblAlgTabDCMiningMetric, HPos.LEFT);
        gridAlgParam.add(lblAlgTabDCMiningMetric, 0, intRowNumber);
        ComboBox cmbDCMiningMetric = new ComboBox() ;
            cmbDCMiningMetric.getItems().addAll(CommunityMiningConfig.DCMINING_METRIC);
            cmbDCMiningMetric.setValue(CommunityMiningConfig.DCMINING_METRIC_DEFAULT);
            cmbDCMiningMetric.setMaxWidth(100);
        GridPane.setHalignment(cmbDCMiningMetric, HPos.LEFT);
        gridAlgParam.add(cmbDCMiningMetric, 1, intRowNumber++);
        cmbDCMiningMetric.valueProperty().addListener(new ChangeListener<String>() {
                @Override 
                public void changed(ObservableValue ov, String t, String t1) {
                    strSelectedDCMiningMetric = t1 ;
                    
                }    
            });
            
            
        
        //3. 
        Label lblAlgTabDCMiningMethod = new Label(SettingsWindowConfig.ALGOPARAMTAB_DCMINING_METHOD);
        GridPane.setHalignment(lblAlgTabDCMiningMethod, HPos.LEFT);
        gridAlgParam.add(lblAlgTabDCMiningMethod, 0, intRowNumber);
        ComboBox cmbDCMiningMethod = new ComboBox() ;
            cmbDCMiningMethod.getItems().addAll(CommunityMiningConfig.DCMINING_METHOD) ;
            cmbDCMiningMethod.setValue(CommunityMiningConfig.DCMINING_METHOD_DEFAULT);
            cmbDCMiningMethod.setMaxWidth(100);
        GridPane.setHalignment(cmbDCMiningMethod, HPos.LEFT);
        gridAlgParam.add(cmbDCMiningMethod, 1, intRowNumber++);
        cmbDCMiningMethod.valueProperty().addListener(new ChangeListener<String>() {
                @Override 
                public void changed(ObservableValue ov, String t, String t1) {
                    strSelectedDCMiningMethod = t1 ;
                                       
                }    
                
            });
        //4. 
        Label lblAlgTabDCMiningzoverlap = new Label(SettingsWindowConfig.ALGOPARAMTAB_DCMINING_OVERLAP);
        GridPane.setHalignment(lblAlgTabDCMiningzoverlap, HPos.LEFT);
        gridAlgParam.add(lblAlgTabDCMiningzoverlap, 0, intRowNumber);
        CheckBox chbDCMiningOverLap = new CheckBox();
        chbDCMiningOverLap.setSelected(CommunityMiningConfig.DCMINING_OVERLAP_DEFAULT);
        GridPane.setHalignment(chbDCMiningOverLap, HPos.LEFT);
        gridAlgParam.add(chbDCMiningOverLap, 1, intRowNumber++);
        
        //5. 
        Label lblAlgTabDCMiningHubs = new Label(SettingsWindowConfig.ALGOPARAMTAB_DCMINING_HUBS);
        GridPane.setHalignment(lblAlgTabDCMiningHubs, HPos.LEFT);
        gridAlgParam.add(lblAlgTabDCMiningHubs, 0, intRowNumber);
        CheckBox chbDCMiningHubs = new CheckBox();
        chbDCMiningHubs.setSelected(CommunityMiningConfig.DCMINING_HUBS_DEFAULT);
        GridPane.setHalignment(chbDCMiningHubs, HPos.LEFT);
        gridAlgParam.add(chbDCMiningHubs, 1, intRowNumber++);
        //6. 
        Label lblAlgTabDCMiningInstability = new Label(SettingsWindowConfig.ALGOPARAMTAB_DCMINING_INSTABILITY);
        GridPane.setHalignment(lblAlgTabDCMiningInstability, HPos.LEFT);
        gridAlgParam.add(lblAlgTabDCMiningInstability, 0, intRowNumber);
        final Spinner<Double> spnrDCMiningInstability = new Spinner();
            spnrDCMiningInstability.setValueFactory(
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(
                              CommunityMiningConfig.DCMINING_INSTABILITY_MIN
                            , CommunityMiningConfig.DCMINING_INSTABILITY_MAX
                            , CommunityMiningConfig.DCMINING_INSTABILITY_DEFAULT
                            , CommunityMiningConfig.DCMINING_INSTABILITY_STEP
                    ));
            spnrDCMiningInstability.setEditable(true);
            spnrDCMiningInstability.setMaxWidth(100);
        GridPane.setHalignment(spnrDCMiningInstability, HPos.LEFT);
        gridAlgParam.add(spnrDCMiningInstability, 1, intRowNumber++);
        //7. 
        Label lblAlgTabDCMiningHistory = new Label(SettingsWindowConfig.ALGOPARAMTAB_DCMINING_HISTORY);
        GridPane.setHalignment(lblAlgTabDCMiningHistory, HPos.LEFT);
        gridAlgParam.add(lblAlgTabDCMiningHistory, 0, intRowNumber);
        final Spinner<Double> spnrDCMiningHistory = new Spinner();
            spnrDCMiningHistory.setValueFactory(
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(
                              CommunityMiningConfig.DCMINING_HISTORY_MIN
                            , CommunityMiningConfig.DCMINING_HISTORY_MAX
                            , CommunityMiningConfig.DCMINING_HISTORY_DEFAULT
                            , CommunityMiningConfig.DCMINING_HISTORY_STEP
                    ));
            spnrDCMiningHistory.setEditable(true);
            spnrDCMiningHistory.setMaxWidth(100);
        GridPane.setHalignment(spnrDCMiningHistory, HPos.LEFT);
        gridAlgParam.add(spnrDCMiningHistory, 1, intRowNumber++);
        
        intRowNumber = putGroupsSeparator(gridAlgParam, intRowNumber);
        // 
        
        
        
        
        
        
        
        
        /****  Parameters Painting Ends Here  ****/
        AnchorPane anchUIDetails = new AnchorPane(gridUI);
        AnchorPane anchAlgoParamDetails = new AnchorPane(gridAlgParam);
        
        anchUITab.setOnMouseClicked( e -> {
            // txtUIHeading.setStyle("-fx-font-weight: bold;");
            // txtlgorithmParameter.setStyle("-fx-font-weight: regular;");
            txtUIHeading.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
            txtlgorithmParameter.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
            vboxContainer.getChildren().clear();
            vboxContainer.getChildren().addAll(gridTab, anchUIDetails, hboxButtons);
        });
        anchAlgParamTab.setOnMouseClicked( e -> {
            // txtUIHeading.setStyle("-fx-font-weight: regular;");
            // txtlgorithmParameter.setStyle("-fx-font-weight: bold;");
            txtlgorithmParameter.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
            txtUIHeading.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
            vboxContainer.getChildren().clear();
            vboxContainer.getChildren().addAll(gridTab, anchAlgoParamDetails, hboxButtons);
        });
        
                
        Button btnOK = new Button(LangConfig.GENERAL_OK);
        btnOK.setOnAction((event) -> {
            event.consume();
            
            updateConfigFiles(txtVertexColorDefault,
                                          txtVertexColorSelected,
                                          txtVertexShapeDefault,
                                          txtVertexSizeDefault,
                                          txtVertexOpacityDefault,
                                          txtVertexLabelFontSizeDefault,
                                          txtEdgeColorPrimary,
                                          clrEdgeColorPrimary,
                                          txtEdgeColorSecondary,
                                          txtEdgeColorTertiary,
                                          clrEdgeColorTertiary,
                                          txtEdgeOpacity,
                                          txtEdgeStrokeWidth,
                                          txtCanvasBackgroundColorDefault,
                                          clrCanvasBackgroundColorDefault,
                                          txtCanvasDragRectColor,
                                          clrCanvasDragRectColor,
                                          txtCanvasDragRectOpacity,
                                          txtCanvasZoomFactor,
                                          spnKMeansClusterCount,
                                          chbSameAttrValMultiple,
                                          chbFastModularityWeighted,
                                          spnrLocalTopLeaderClusterCount,
                                          spnrLocalTopLeaderOutlierPercentage,
                                        spnrLocalTopLeaderHubPercentage,
                                        spnrLocalTopLeaderCenterDistance,
                                        chbLocalCommunityOverLap,
                                        spnrRosvallInfoMapAttemptsCount,
                                        chbRosvallInfoMapIsDirected,
                                        spnrRosvallInfoModAttemptsCount,
                                        spnrDCMiningSimilarityThreshold,
                                        chbDCMiningOverLap,
                                        chbDCMiningHubs,
                                        spnrDCMiningInstability,
                                        spnrDCMiningHistory
            );
            
            // Write the XML File
            AppSettingsWriter.WriteXML(AppConfig.SETTINGS_XML_PATH_DEFAULT);
            
            //update default color in Logic
            GraphAPI.updateDefaultVertexColorString(SceneConfig.VERTEX_COLOR_DEFAULT);
            
            InfoDialog.Display(LangConfig.INFO_SETTINGS,SceneConfig.INFO_TIMEOUT);
            
            // Close the XML File
            stgApplicationSettings.close();            
            
            pController.updateStatusBar(false, StatusMsgsConfig.SETTINGS_APPLIEDCLOSED);
        });
        
        Button btnApply = new Button(LangConfig.GENERAL_APPLY);
        btnApply.setOnAction((event) -> {
            event.consume();
            updateConfigFiles(txtVertexColorDefault,
                                          txtVertexColorSelected,
                                          txtVertexShapeDefault,
                                          txtVertexSizeDefault,
                                          txtVertexOpacityDefault,
                                          txtVertexLabelFontSizeDefault,
                                          txtEdgeColorPrimary,
                                          clrEdgeColorPrimary,
                                          txtEdgeColorSecondary,
                                          txtEdgeColorTertiary,
                                          clrEdgeColorTertiary,
                                          txtEdgeOpacity,
                                          txtEdgeStrokeWidth,
                                          txtCanvasBackgroundColorDefault,
                                          clrCanvasBackgroundColorDefault,
                                          txtCanvasDragRectColor,
                                          clrCanvasDragRectColor,
                                          txtCanvasDragRectOpacity,
                                          txtCanvasZoomFactor,
                                          spnKMeansClusterCount,
                                          chbSameAttrValMultiple,
                                          chbFastModularityWeighted,
                                          spnrLocalTopLeaderClusterCount,
                                          spnrLocalTopLeaderOutlierPercentage,
                                        spnrLocalTopLeaderHubPercentage,
                                        spnrLocalTopLeaderCenterDistance,
                                        chbLocalCommunityOverLap,
                                        spnrRosvallInfoMapAttemptsCount,
                                        chbRosvallInfoMapIsDirected,
                                        spnrRosvallInfoModAttemptsCount,
                                        spnrDCMiningSimilarityThreshold,
                                        chbDCMiningOverLap,
                                        chbDCMiningHubs,
                                        spnrDCMiningInstability,
                                        spnrDCMiningHistory
            );
            // Write the XML file
            AppSettingsWriter.WriteXML(AppConfig.SETTINGS_XML_PATH_DEFAULT);
            
            pController.updateStatusBar(false, StatusMsgsConfig.SETTINGS_APPLIED);
        });
        
        Button btnCancel = new Button(LangConfig.GENERAL_CANCEL);
        btnCancel.setOnAction((event) -> {
            event.consume();
            stgApplicationSettings.close();
            pController.updateStatusBar(false, StatusMsgsConfig.SETTINGS_CANCELLED);
        });
        
        hboxButtons.getChildren().addAll(btnOK, btnApply, btnCancel);
        hboxButtons.setPadding(new Insets(10));
        hboxButtons.setSpacing(20);
        hboxButtons.setAlignment(Pos.CENTER_RIGHT);
        
        vboxContainer.setPadding(new Insets(20,10,5,10));        
        vboxContainer.getChildren().addAll(gridTab, anchUIDetails, hboxButtons);
        vboxContainer.setAlignment(Pos.TOP_CENTER);
        
        Scene scnApplicationSettings = new Scene(vboxContainer, 700, 600);
        scnApplicationSettings.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
                stgApplicationSettings.close();
                pController.updateStatusBar(false, StatusMsgsConfig.SETTINGS_CANCELLED);
            }
        });
        
        //stgApplicationSettings.initModality(Modality.WINDOW_MODAL);
        stgApplicationSettings.setTitle(pstrTitle);        
        stgApplicationSettings.setResizable(false);
                
        stgApplicationSettings.setScene(scnApplicationSettings);
        stgApplicationSettings.show();
        
        // Update the status bar
        pController.updateStatusBar(false, StatusMsgsConfig.SETTINGS_SHOW);
    }
    
    /*
    * This method updates values of config files from the fields of settings window.
    * Files are sceneconfig, community mining config.java.
    */
    private static void updateConfigFiles(TextField txtVertexColorDefault,
                                          TextField txtVertexColorSelected,
                                          TextField txtVertexShapeDefault,
                                          TextField txtVertexSizeDefault,
                                           TextField txtVertexOpacityDefault,
                                           TextField txtVertexLabelFontSizeDefault,
                                           TextField txtEdgeColorPrimary,
                                           ColorPicker clrEdgeColorPrimary,
                                           TextField txtEdgeColorSecondary,
                                           TextField txtEdgeColorTertiary,
                                           ColorPicker clrEdgeColorTertiary,
                                           TextField txtEdgeOpacity,
                                           TextField txtEdgeStrokeWidth,
                                           TextField txtCanvasBackgroundColorDefault,
                                           ColorPicker clrCanvasBackgroundColorDefault,
                                           TextField txtCanvasDragRectColor,
                                           ColorPicker clrCanvasDragRectColor,
                                           TextField txtCanvasDragRectOpacity,
                                           TextField txtCanvasZoomFactor,
                                           Spinner<Integer> spnrKMeansClusterCount,
                                           CheckBox chbSameAttrValMultiple,
                                           CheckBox chbFastModularityWeighted,
                                           Spinner<Integer> spnrLocalTopLeaderClusterCount,
                                           Spinner<Double> spnrLocalTopLeaderOutlierPercentage,
                                           Spinner<Double> spnrLocalTopLeaderHubPercentage,
                                           Spinner<Double> spnrLocalTopLeaderCenterDistance,
                                           CheckBox  chbLocalCommunityOverLap,
                                           Spinner<Double> spnrRosvallInfoMapAttemptsCount,
                                           CheckBox  chbRosvallInfoMapIsDirected,
                                           Spinner<Double> spnrRosvallInfoModAttemptsCount,
                                           Spinner<Double> spnrDCMiningSimilarityThreshold,
                                           CheckBox chbDCMiningOverLap,
                                           CheckBox chbDCMiningHubs,
                                           Spinner<Double> spnrDCMiningInstability,
                                           Spinner<Double> spnrDCMiningHistory

                                           )
        {
       // -- uitab params -- //
       // -- scenecongig.java static variables -- //
        SceneConfig.VERTEX_COLOR_DEFAULT = txtVertexColorDefault.getText() ;
        SceneConfig.VERTEX_COLOR_SELECTED = txtVertexColorSelected.getText() ;
        SceneConfig.VERTEX_SHAPE_DEFAULT_INT = Integer.parseInt(txtVertexShapeDefault.getText()) ;
        SceneConfig.VERTEX_SIZE_DEFAULT = Double.parseDouble(txtVertexSizeDefault.getText()) ;

        SceneConfig.VERTEX_OPACITY = Double.parseDouble(txtVertexOpacityDefault.getText()) ;

        SceneConfig.VERTEX_LABEL_FONTSIZE_DEFAULT = Integer.parseInt(txtVertexLabelFontSizeDefault.getText()) ;

        SceneConfig.EDGE_COLOR_PRIMARYCOLOR = txtEdgeColorPrimary.getText() ;

        SceneConfig.EDGE_COLOR_PRIMARYCOLOR = FXUtils.ColorToHex(clrEdgeColorPrimary.getValue());
                    txtVertexColorSelected.setText(SceneConfig.EDGE_COLOR_PRIMARYCOLOR);

        SceneConfig.EDGE_COLOR_SECONDARYCOLOR = txtEdgeColorSecondary.getText() ;

        SceneConfig.EDGE_COLOR_TERTIARYCOLOR = txtEdgeColorTertiary.getText() ;
        SceneConfig.EDGE_COLOR_TERTIARYCOLOR = FXUtils.ColorToHex(clrEdgeColorTertiary.getValue());
                    txtVertexColorSelected.setText(SceneConfig.EDGE_COLOR_TERTIARYCOLOR);

        SceneConfig.EDGE_OPACITY = Double.parseDouble(txtEdgeOpacity.getText()) ;

        SceneConfig.EDGE_STROKEWIDTH = Double.parseDouble(txtEdgeStrokeWidth.getText()) ;

        SceneConfig.CANVAS_BACKGROUND_COLOR = txtCanvasBackgroundColorDefault.getText() ;
        SceneConfig.CANVAS_BACKGROUND_COLOR = FXUtils.ColorToHex(clrCanvasBackgroundColorDefault.getValue());
                    txtVertexColorSelected.setText(SceneConfig.CANVAS_BACKGROUND_COLOR);


        SceneConfig.CANVAS_DRAGRECT_COLOR = txtCanvasDragRectColor.getText() ;
        SceneConfig.CANVAS_DRAGRECT_COLOR = FXUtils.ColorToHex(clrCanvasDragRectColor.getValue());
                    txtVertexColorSelected.setText(SceneConfig.CANVAS_DRAGRECT_COLOR);

        SceneConfig.CANVAS_DRAGRECT_OPACITY = Double.parseDouble(txtCanvasDragRectOpacity.getText()) ;


        SceneConfig.CANVAS_SCROLL_SCALEFACTOR = Double.parseDouble(txtCanvasZoomFactor.getText());
        
        // -- algorithms params tab -- //
        // -- communityminingcongif.java static variables -- //
        // K-Means Clustering
        CommunityMiningConfig.KMEANS_CLUSTERCOUNT_DEFAULT = spnrKMeansClusterCount.getValue() ;
        
        // Similar Value Attribute
        CommunityMiningConfig.SAMEVALUEATTR_MULTIPLEVALUES = chbSameAttrValMultiple.isSelected();
        // Fast Modularity
        CommunityMiningConfig.FM_METRIC_DEFAULT = strFastModularityAlgoType;
        CommunityMiningConfig.FM_WEIGHTED = chbFastModularityWeighted.isSelected();
        // Local Top Leader
        CommunityMiningConfig.LOCALTOPLEADERS_CLUSTERCOUNT_DEFAULT = spnrLocalTopLeaderClusterCount.getValue();
        CommunityMiningConfig.LOCALTOPLEADERS_OUTLIERPERCENTAGE_DEFAULT = spnrLocalTopLeaderOutlierPercentage.getValue();
        CommunityMiningConfig.LOCALTOPLEADERS_HUBPERCENTAGE_DEFAULT = spnrLocalTopLeaderHubPercentage.getValue();
        CommunityMiningConfig.LOCALTOPLEADERS_CENTERDISTANCE_DEFAULT = spnrLocalTopLeaderCenterDistance.getValue();
        
        // Local Community
        CommunityMiningConfig.LOCALCOMMUNITY_TYPE_DEFAULT = strLocalCommunityAlgoType;
        CommunityMiningConfig.LOCALCOMMUNITY_OVERLAP = chbLocalCommunityOverLap.isSelected();
        
        // Rosvall InfoMap
        CommunityMiningConfig.ROSVALLINFOMAP_ATTEMPTS_DEFAULT =  spnrRosvallInfoMapAttemptsCount.getValue().intValue();
        CommunityMiningConfig.ROSVALLINFOMAP_ISDIRECTED =  chbRosvallInfoMapIsDirected.isSelected();

        // ROsvall InfoMod
        CommunityMiningConfig.ROSVALLINFOMOD_ATTEMPTS_DEFAULT =  spnrRosvallInfoModAttemptsCount.getValue().intValue();
        // DC Mining
        CommunityMiningConfig.DCMINING_SIMILARITYTHRESHOLD_DEFAULT = spnrDCMiningSimilarityThreshold.getValue();
        //TODO: There is a bug in updating and writing thses values from dropdowns to settings.xml
        //System.out.println("Application Settings.java -> update() : strSelectedDCMiningMetric = " + strSelectedDCMiningMetric);
        CommunityMiningConfig.DCMINING_METRIC_DEFAULT = strSelectedDCMiningMetric;
        CommunityMiningConfig.DCMINING_METHOD_DEFAULT = strSelectedDCMiningMethod;
        CommunityMiningConfig.DCMINING_OVERLAP_DEFAULT = chbDCMiningOverLap.isSelected();
        CommunityMiningConfig.DCMINING_HUBS_DEFAULT = chbDCMiningHubs.isSelected();
        CommunityMiningConfig.DCMINING_INSTABILITY_DEFAULT = spnrDCMiningInstability.getValue();
        CommunityMiningConfig.DCMINING_HISTORY_DEFAULT = spnrDCMiningHistory.getValue();
        
        
        
        
        
        
    }

    private static int putGroupsSeparator(GridPane gridAlgParam, int pintRowNumber) {
        
        Separator sep1 = new Separator();
        GridPane.setHalignment(sep1, HPos.LEFT);
        Separator sep2 = new Separator();
        GridPane.setHalignment(sep2, HPos.LEFT);
        sep1.setMinHeight(5);
        sep2.setMinHeight(5);
        gridAlgParam.add(sep1, 0, pintRowNumber);
        gridAlgParam.add(sep2, 1, pintRowNumber++);
        
        /*Pane  spring1 = new Pane();
        spring1.minHeightProperty().bind(sep1.heightProperty());
        gridAlgParam.add(spring1, 0, pintRowNumber++);
        
        Pane  spring2 = new Pane();
        spring2.minHeightProperty().bind(sep1.heightProperty());
        gridAlgParam.add(spring2, 0, pintRowNumber++);
        */
        
        return pintRowNumber;
    }
}
