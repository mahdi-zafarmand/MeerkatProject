/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import config.LangConfig;
import config.SnapshotToolboxConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import toolbox.snapshot.AllProjectsAllGraphs;
import toolbox.snapshot.CurrentProjectAllGraphs;
import toolbox.snapshot.FullGraphSnapshot;
import toolbox.snapshot.VisibleGraphArea;

/**
 *  Class Name      : SnapshotOptionsDialog
 *  Created Date    : 2016-06-09
 *  Description     : 
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class SnapshotOptionsDialog {
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-06-09
     *  Description     : Displays the Options for the snapshot tool
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Display() {
        try {
            
            Stage stgSnapshotsOptions = new Stage();
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            BooleanProperty blnprpSelected = new SimpleBooleanProperty(false);
            
            GridPane grid = new GridPane();            
            grid.setVgap(4);
            grid.setPadding(new Insets(5, 5, 5, 5));
            
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(15);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(85);
            grid.getColumnConstraints().addAll(col1,col2);
        
            final ToggleGroup layoutToggle = new ToggleGroup();
            
            Label lblCurrentProject = new Label(SnapshotToolboxConfig.getLabelAllProjects());
            
            RadioButton rbAllProject = new RadioButton(SnapshotToolboxConfig.getAllOpenGraphsAllProjects_ToolTip());
            rbAllProject.setOnAction(e -> {
                AllProjectsAllGraphs.SaveAsPNG(UIInstance.getController());
            });
            RadioButton rbCurrentProjectAllGraphs = new RadioButton(SnapshotToolboxConfig.getAllOpenGraphsActiveProject_ToolTip());
            rbCurrentProjectAllGraphs.setOnAction(e -> {
                CurrentProjectAllGraphs.SaveAsPNG(UIInstance.getController());
            });
            RadioButton rbCurrentProjectCurrentGraph = new RadioButton(SnapshotToolboxConfig.getCurrentGraphComplete_ToolTip());
            rbCurrentProjectCurrentGraph.setOnAction(e -> {
                FullGraphSnapshot.SaveAsPNG(UIInstance.getController());
            });
            RadioButton rbCurrentProjectCurrentView = new RadioButton(SnapshotToolboxConfig.getCurrentGraphViewOnly_ToolTip());
            rbCurrentProjectCurrentView.setOnAction(e -> {
                VisibleGraphArea.SaveAsPNG(UIInstance.getController());
            });
            
            // Set the radio buttons to the current Toggle Group
            rbAllProject.setToggleGroup(layoutToggle);
            rbCurrentProjectAllGraphs.setToggleGroup(layoutToggle);
            rbCurrentProjectCurrentGraph.setToggleGroup(layoutToggle);
            rbCurrentProjectCurrentView.setToggleGroup(layoutToggle);
            
            
            // If toggiling is required, use the following code
            /*
            layoutToggle.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) -> {
                RadioButton rbtnSelectedSnapshot = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button    
                // IModule localModule = null ;
                if (rbtnSelectedSnapshot.getText().equalsIgnoreCase(rbAllProject.getText())) {

                } else if (rbtnSelectedSnapshot.getText().equalsIgnoreCase(rbCurrentProjectAllGraphs.getText())) {

                } else if (rbtnSelectedSnapshot.getText().equalsIgnoreCase(rbCurrentProjectCurrentGraph.getText())) {

                } else if (rbtnSelectedSnapshot.getText().equalsIgnoreCase(rbCurrentProjectCurrentView.getText())) {

                }
            });
            */
            
            // Adding in the Grid
            grid.add(rbAllProject, 0, 0, 2, 1);
            grid.add(lblCurrentProject, 0, 1, 2, 1);
            grid.add(rbCurrentProjectAllGraphs, 1, 2, 1, 1);
            grid.add(rbCurrentProjectCurrentGraph, 1, 3, 1, 1);
            grid.add(rbCurrentProjectCurrentView, 1, 4);
        
            Button btnOK = new Button(LangConfig.GENERAL_OK);
            btnOK.disableProperty().bind(blnprpSelected); 
            btnOK.setOnAction(e -> {

            });
            
            Button btnCancel = new Button(LangConfig.GENERAL_CANCEL);
            btnCancel.setOnAction(e -> {
                stgSnapshotsOptions.close();
                UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            });
            


            HBox hboxButtons = new HBox(5);
            hboxButtons.setPadding(new Insets(5,5,5,5));
            hboxButtons.getChildren().addAll(btnOK, btnCancel);
            hboxButtons.setSpacing(20);
            hboxButtons.setPadding(new Insets(0, 10, 10, 10));
            hboxButtons.setAlignment(Pos.CENTER);

            // Build a VBox
            VBox vboxRows = new VBox(5);
            vboxRows.setPadding(new Insets(5,5,5,5));        
            vboxRows.getChildren().addAll(grid);

            Scene scnSnapshotOptions = new Scene(vboxRows);
            scnSnapshotOptions.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                if (evt.getCode().equals(KeyCode.ESCAPE)) {
                    stgSnapshotsOptions.close();
                    UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                }
            });

            stgSnapshotsOptions.initModality(Modality.APPLICATION_MODAL);
            stgSnapshotsOptions.setTitle(SnapshotToolboxConfig.getHeader());        
            stgSnapshotsOptions.setResizable(false);

            stgSnapshotsOptions.setScene(scnSnapshotOptions);
            stgSnapshotsOptions.show();
            
            UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.WAITING_USERINPUT);
        } catch (Exception ex) {
            System.out.println(".(): EXCEPTION") ;
            ex.printStackTrace();
        }
    }
    
}
