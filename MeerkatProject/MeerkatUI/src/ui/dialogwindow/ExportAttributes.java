/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.analysis.GraphMetricAPI;
import config.ErrorCode;
import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Exporting Graph Data based on the attributes sent
 * @author Talat
 * @since 2018-01-24
 */
public class ExportAttributes {
    
    
    public static void Display(AnalysisController pController, String pstrTitle) {
        
        Stage stgExportData = new Stage();
        stgExportData.initModality(Modality.APPLICATION_MODAL);
        
        VBox vboxContainer = new VBox(5);
        HBox hboxButtons = new HBox();
        
        GridPane gridAttributes = new GridPane();
        
        // Column constraints to define the two columns
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        column2.setHgrow(Priority.ALWAYS);
        gridAttributes.getColumnConstraints().addAll(column1, column2);
        
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int intProjectID = UIInstance.getActiveProjectID();
        int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID();
        int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
        List<String> lstSystemAttributes = GraphAPI.getVertexSystemAttributes_Sorted(intProjectID, intGraphID, intTimeFrameIndex);
        List<String> lstUserAttributes = GraphAPI.getVertexUserAttributes_Sorted(intProjectID, intGraphID, intTimeFrameIndex);
                
        Set<String> setSelectedSystemAttr = new HashSet<>();        
        VBox vboxSystemAttr = new VBox(5);
        Map<String, CheckBox> mapchbSystemAttributes = new HashMap<>();
        for(String strAttr : lstSystemAttributes){
            CheckBox chbCurrentItem = new CheckBox(strAttr);
            chbCurrentItem.setDisable(false);
            chbCurrentItem.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if(chbCurrentItem.isSelected()){
                    setSelectedSystemAttr.add(strAttr);
                } else {
                    setSelectedSystemAttr.remove(strAttr);
                }
            });
            mapchbSystemAttributes.put(strAttr, chbCurrentItem);
            vboxSystemAttr.getChildren().add(chbCurrentItem);
        }
        
        Set<String> setSelectedUserAttr = new HashSet<>();
        Map<String, CheckBox> mapchbUserAttributes = new HashMap<>();
        VBox vboxUserAttr = new VBox(5);
        for(String strAttr : lstUserAttributes){
            CheckBox chbCurrentItem = new CheckBox(strAttr);
            chbCurrentItem.setDisable(false);
            chbCurrentItem.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if(chbCurrentItem.isSelected()){
                    setSelectedUserAttr.add(strAttr);
                } else {
                    setSelectedUserAttr.remove(strAttr);
                }
            });
            mapchbUserAttributes.put(strAttr, chbCurrentItem);
            vboxUserAttr.getChildren().add(chbCurrentItem);
        }
        
        // Add a pane for System Attributes
        ScrollPane scrSystemAttr = new ScrollPane();
        scrSystemAttr.setFitToHeight(true);
        scrSystemAttr.setFitToWidth(true);
        scrSystemAttr.setContent(vboxSystemAttr);
        
        // Add a pane for User Attributes
        ScrollPane scrUserAttr = new ScrollPane();
        scrUserAttr.setFitToHeight(true);
        scrUserAttr.setFitToWidth(true);
        scrUserAttr.setContent(vboxUserAttr);
        
        Label lblUserAttr = new Label("User Attributes");
        Label lblSystemAttr = new Label("System Attributes");
        
        
        gridAttributes.setVgap(4);
        gridAttributes.setPadding(new Insets(5, 5, 5, 5));
        gridAttributes.add(lblUserAttr, 0, 0);
        gridAttributes.add(lblSystemAttr, 1, 0);
        gridAttributes.add(scrUserAttr, 0, 1);
        gridAttributes.add(scrSystemAttr, 1, 1);
                        
        Text txtUIHeading = new Text("Export Data");        
        txtUIHeading.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
                
        // BUTTONS
        Button btnExportAttr = new Button(LangConfig.GENERAL_EXPORT);
        btnExportAttr.setOnAction((event) -> {
            event.consume();
            
            stgExportData.close();            
            
            // Open the File Chooser
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);

            FileChooser filechooser = new FileChooser();
            filechooser.setInitialDirectory((new File(System.getProperty("user.dir"))));
            filechooser.setTitle("Choose Export Data Filename");
            filechooser.setInitialFileName("data.csv");
            File file = filechooser.showSaveDialog(dialog);

            String strFilePath ;
            int intErrorCode = 0;

            if(file!= null){
                strFilePath = file.getAbsolutePath();
                intErrorCode = GraphMetricAPI.exportAttributes(intProjectID, intGraphID, intTimeFrameIndex, setSelectedUserAttr, setSelectedSystemAttr, strFilePath);
            }
            
            if (intErrorCode==ErrorCode.ERROR_SAVECOMMUNITY.getId()){
                ErrorDialog.Display(intErrorCode);
            } else {
                InfoDialog.Display("Attributes exported successfully", 3);
            }
            pController.updateStatusBar(false, StatusMsgsConfig.SETTINGS_APPLIEDCLOSED);
        });
        
        Button btnCancel = new Button(LangConfig.GENERAL_CANCEL);
        btnCancel.setOnAction((event) -> {
            event.consume();
            stgExportData.close();
            pController.updateStatusBar(false, StatusMsgsConfig.SETTINGS_CANCELLED);
        });
        
        hboxButtons.getChildren().addAll(btnExportAttr, btnCancel);
        hboxButtons.setPadding(new Insets(10));
        hboxButtons.setSpacing(20);
        hboxButtons.setAlignment(Pos.CENTER_RIGHT);
        
        VBox vboxSelectAllNone = new VBox();
        CheckBox chbSelectAll = new CheckBox("Select All / None");
        chbSelectAll.setDisable(false);
        chbSelectAll.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(chbSelectAll.isSelected()){
                for(String strAttr : mapchbUserAttributes.keySet()){
                    setSelectedUserAttr.add(strAttr);
                    mapchbUserAttributes.get(strAttr).setSelected(true);
                }
                
                for(String strAttr : mapchbSystemAttributes.keySet()){
                    setSelectedSystemAttr.add(strAttr);
                    mapchbSystemAttributes.get(strAttr).setSelected(true);
                }
            } else {
                for(String strAttr : mapchbUserAttributes.keySet()){
                    setSelectedUserAttr.remove(strAttr);
                    mapchbUserAttributes.get(strAttr).setSelected(false);
                }
                
                for(String strAttr : mapchbSystemAttributes.keySet()){
                    setSelectedSystemAttr.remove(strAttr);
                    mapchbSystemAttributes.get(strAttr).setSelected(false);
                }
            }
        });
        vboxSelectAllNone.getChildren().add(chbSelectAll);
        vboxSelectAllNone.setAlignment(Pos.CENTER_LEFT);
        
        HBox hboxBottomContainer = new HBox(5);
        hboxBottomContainer.getChildren().addAll(vboxSelectAllNone, hboxButtons);
        hboxBottomContainer.setAlignment(Pos.CENTER);
        
        vboxContainer.setPadding(new Insets(20,10,5,10));        
        vboxContainer.getChildren().addAll(gridAttributes, hboxBottomContainer);
        vboxContainer.setAlignment(Pos.TOP_CENTER);
        
        Scene scnExportAttributes = new Scene(vboxContainer, 700, 600);
        scnExportAttributes.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
                stgExportData.close();
                pController.updateStatusBar(false, StatusMsgsConfig.SETTINGS_CANCELLED);
            }
        });
        
        //stgApplicationSettings.initModality(Modality.WINDOW_MODAL);
        stgExportData.setTitle(pstrTitle);        
        stgExportData.setResizable(false);
                
        stgExportData.setScene(scnExportAttributes);
        stgExportData.show();
        
        // Update the status bar
        pController.updateStatusBar(false, StatusMsgsConfig.SETTINGS_SHOW);
    }
   
}
