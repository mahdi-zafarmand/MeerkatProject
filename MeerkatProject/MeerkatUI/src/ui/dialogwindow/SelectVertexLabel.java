/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import config.LangConfig;
import config.SceneConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.utilities.VertexLabelShowHide;

/**
 *  Class Name      : SelectVertexLabel
 *  Created Date    : 2016-05-25
 *  Description     : Selects a Vertex Label 
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2018-01-19      Talat           Making the ComboBox with an editable Textfield for easy search 
 * 
*/
public class SelectVertexLabel {
    
    private static String strSelectedAttribute ;
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-05-25
     *  Description     : Displays the attributes to select the Vertex Label Attribute
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
    public static void Display (AnalysisController pController) {
        try {
            Stage stgSelectVertexLabel = new Stage();
            stgSelectVertexLabel.initModality(Modality.APPLICATION_MODAL);
            
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID();
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
            int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
                                    
            // Grid to show all the User Input controls
            GridPane gridUserControls = new GridPane();
            gridUserControls.setPadding(new Insets(5));
            gridUserControls.setHgap(5);
            gridUserControls.setVgap(5);

            // Column constraints to define the two columns
            ColumnConstraints column1 = new ColumnConstraints();
            //column1.setPercentWidth(50);
            ColumnConstraints column2 = new ColumnConstraints();
            //column2.setPercentWidth(50);
            column2.setHgrow(Priority.ALWAYS);
            gridUserControls.getColumnConstraints().addAll(column1, column2);
                       
            
            // ROW 1 - ATTRIBUTE
            Label lblAttribute = new Label(LangConfig.VERTEXLABEL_MSG);            
            ComboBox<String> cmbAttributeID = new ComboBox<>() ;
            cmbAttributeID.setEditable(true);

            // Create a list with some dummy values.
            ObservableList<String> items = FXCollections.observableArrayList(
                    GraphAPI.getAllAttributesNames_Sorted(intProjectID, intGraphID, intTimeFrameIndex));

            // Create a FilteredList wrapping the ObservableList.
            FilteredList<String> filteredItems = new FilteredList<>(items, p -> true);

            // Add a listener to the textProperty of the combobox editor. The
            // listener will simply filter the list every time the input is changed
            // as long as the user hasn't selected an item in the list.
            cmbAttributeID.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                final TextField editor = cmbAttributeID.getEditor();
                final String selected = cmbAttributeID.getSelectionModel().getSelectedItem();

                // This needs run on the GUI thread to avoid the error described
                // here: https://bugs.openjdk.java.net/browse/JDK-8081700.
                Platform.runLater(() -> {
                    // If the no item in the list is selected or the selected item
                    // isn't equal to the current input, we refilter the list.
                    if (selected == null || !selected.equals(editor.getText())) {
                        filteredItems.setPredicate(item -> {
                            // We return true for any items that starts with the same letters 
                            // as the input. We use toUpperCase to avoid case sensitivity.
                            if (item.toUpperCase().startsWith(newValue.toUpperCase())) {
                                return true;
                            } else {
                                return false;
                            }
                        });
                    }
                });
            });

            cmbAttributeID.setItems(filteredItems);
            cmbAttributeID.setMaxWidth(100);
            // removing the system attributes: SYS:X and SYS:Y
            // cmbAttributeID.getItems().remove(GraphAPI.getMeerkatSystemXAttribute());
            // cmbAttributeID.getItems().remove(GraphAPI.getMeerkatSystemYAttribute());
                        
            GridPane.setHalignment(lblAttribute, HPos.LEFT);
            gridUserControls.add(lblAttribute, 0, 0);

            GridPane.setHalignment(cmbAttributeID, HPos.LEFT);
            gridUserControls.add(cmbAttributeID, 1, 0);
        
            
            // Error Label 
            Label lblError = new Label("") ;
            lblError.setVisible(false);
            
            // Adding the OK and Cancel Buttons at the end        
            Button btnOK = new Button (LangConfig.GENERAL_OK);
            btnOK.setOnAction(e -> {
                
                boolean blnIsVertexLabelShown = UIInstance.getActiveProjectTab().getActiveGraphTab().IsVertexLabelShown();
                
                if (blnIsVertexLabelShown) {
                    pController.updateStatusBar(true, StatusMsgsConfig.VERTEXLABEL_DISABLING);
                } else {
                    pController.updateStatusBar(true, StatusMsgsConfig.VERTEXLABEL_ENABLING);
                }
                SceneConfig.VERTEX_LABEL_SELECTED_ATTR = strSelectedAttribute ;
                
                String [] arrstrLabels = GraphAPI.getVertexAttributeValues(intProjectID, intGraphID, intTimeFrameIndex, strSelectedAttribute);
                UIInstance.getActiveProjectTab().getActiveGraphTab().setVertexLabelAttr(strSelectedAttribute);
                UIInstance.getActiveProjectTab().getActiveGraphTab().setIsVertexLabelShown(true);
                UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().updateVertexLabels(arrstrLabels);
                                
                VertexLabelShowHide.Execute(pController);
                
                pController.updateStatusBar(false, StatusMsgsConfig.MINING_RESULTSCOMPUTED);
                
                stgSelectVertexLabel.close();
                
            });
            Button btnCancel = new Button (LangConfig.GENERAL_CANCEL);
            btnCancel.setOnAction(e -> {
                strSelectedAttribute = "" ;
                stgSelectVertexLabel.close();
                pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            });
            HBox hboxButtons = new HBox(btnOK, btnCancel);
            hboxButtons.setSpacing(10);
            hboxButtons.setPadding(new Insets(5,10,5,10));
            hboxButtons.setAlignment(Pos.CENTER);
            
            
            cmbAttributeID.valueProperty().addListener(new ChangeListener<String>() {
                @Override 
                public void changed(ObservableValue ov, String t, String t1) {
                    strSelectedAttribute = t1 ;
                    
                    if (strSelectedAttribute.length() > 0) {
                        lblError.setVisible(false);
                    }
                }    
            });
            
            // The scene consists of the following items
            // 1) Grid for Input Values
            // 2) HBox - buttons
            VBox vboxPanel = new VBox(gridUserControls,lblError, hboxButtons) ;
            
            
            Scene scnSelectVertexLabel = new Scene(vboxPanel);
            scnSelectVertexLabel.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                if (evt.getCode().equals(KeyCode.ESCAPE)) {
                    stgSelectVertexLabel.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
                }
            });
        
            //stgSelectVertexLabel.initModality(Modality.WINDOW_MODAL);
            stgSelectVertexLabel.setTitle(LangConfig.VERTEXLABEL_TITLE);
            stgSelectVertexLabel.setResizable(false);
            pController.updateStatusBar(true, StatusMsgsConfig.WAITING_USERINPUT);

            stgSelectVertexLabel.setScene(scnSelectVertexLabel);
            stgSelectVertexLabel.show();
            stgSelectVertexLabel.setOnCloseRequest(e -> {
                e.consume();
                stgSelectVertexLabel.close();
                pController.updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            });
        } catch (Exception ex) {
            System.out.println("SelectVertexLabel.Display(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
}
