/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import accordiontab.LayoutValues;
import ca.aicml.meerkat.api.GraphAPI;
import config.ErrorMsgsConfig;
import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *  Class Name      : LayoutSpatial
 *  Created Date    : 2016-07-13
 *  Description     : Class that would be invoked when Spatial Layout is selected
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class LayoutSpatial {
    
    private static String strLatAttr = "" ;
    private static String strLongAttr = "" ;
    
    
    public static String getLattitudeAttribute() {
        return strLatAttr ;
    }
    
    public static String getLongitudeAttribute() {
        return strLongAttr ;
    }
    
    public static String [] getParameters() {
        System.out.println("LayoutSpatial.getParameters(): Returning rresults");
        String [] arrstrReturn = {strLatAttr, strLongAttr} ;
        return arrstrReturn ;
    }
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-06-13
     *  Description     : Displays the Window for selecting the Attribute
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : Integer
     *  @param pintGraphID : Integer
     *  @param pintTimeFrameIndex : Integer
     *  @param pstrSelectedLayoutID : String
     *  @param plytValues : LayoutValues
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Display(Integer pintProjectID, Integer pintGraphID, Integer pintTimeFrameIndex, String pstrSelectedLayoutID, LayoutValues plytValues) {
        try {
            Stage stgLatLong = new Stage();
            
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
            column1.setPercentWidth(50);
            ColumnConstraints column2 = new ColumnConstraints();
            column2.setPercentWidth(50);
            column2.setHgrow(Priority.ALWAYS);
            gridUserControls.getColumnConstraints().addAll(column1, column2);
            
            List<String> lststrAttributes = GraphAPI.getAllAttributesNames_Sorted(intProjectID, intGraphID, intTimeFrameIndex) ;
                       
            
            // ROW 1 - ATTRIBUTE
            Label lblLatAttr = new Label(LangConfig.VERTEXLABEL_MSG);            
            ComboBox cmbLatAttr = new ComboBox() ;
            cmbLatAttr.getItems().addAll(lststrAttributes) ;
            cmbLatAttr.setMaxWidth(100);
            cmbLatAttr.valueProperty().addListener((ov, t, t1) -> {
                if (t1 != null) {
                    strLatAttr = t1.toString();
                }
            });
                        
            GridPane.setHalignment(lblLatAttr, HPos.LEFT);
            gridUserControls.add(lblLatAttr, 0, 0);

            GridPane.setHalignment(cmbLatAttr, HPos.LEFT);
            gridUserControls.add(cmbLatAttr, 1, 0);
            
            
            Label lblLongAttr = new Label(LangConfig.VERTEXLABEL_MSG);            
            ComboBox cmbLongAttr = new ComboBox() ;
            cmbLongAttr.getItems().addAll(lststrAttributes) ;
            cmbLongAttr.setMaxWidth(100);
            cmbLongAttr.valueProperty().addListener((ov, t, t1) -> {
                if (t1 != null) {
                    strLongAttr = t1.toString();
                }
            });
                        
            GridPane.setHalignment(lblLongAttr, HPos.LEFT);
            gridUserControls.add(lblLongAttr, 0, 1);

            GridPane.setHalignment(cmbLongAttr, HPos.LEFT);
            gridUserControls.add(cmbLongAttr, 1, 1);
        
            
            // Error Label             
            Label lblError = new Label(ErrorMsgsConfig.ERROR_SAMELATLONGATTR) ;
            lblError.setVisible(false);
            GridPane.setHalignment(lblError, HPos.CENTER);
            gridUserControls.add(lblError, 1, 2, 2, 1);
            
            // Adding the OK and Cancel Buttons at the end        
            Button btnOK = new Button (LangConfig.GENERAL_OK);
            btnOK.setOnAction(e -> {
                if (strLatAttr.isEmpty() || strLongAttr.isEmpty()) {
                    lblError.setText(ErrorMsgsConfig.ERROR_BOTHINPUT);
                    lblError.setVisible(true);
                } else if (strLatAttr.equals(strLongAttr)) {
                    lblError.setText(ErrorMsgsConfig.ERROR_SAMELATLONGATTR);
                    lblError.setVisible(true);
                } else {
                    stgLatLong.close();
                    plytValues.runLayoutAlgorithm(pintProjectID, pintGraphID, pintTimeFrameIndex, getParameters());
                }                
            });
            Button btnCancel = new Button (LangConfig.GENERAL_CANCEL);
            btnCancel.setOnAction(e -> {
                strLatAttr = "" ;
                strLongAttr = "" ;
                stgLatLong.close();
                UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            });
            HBox hboxButtons = new HBox(btnOK, btnCancel);
            hboxButtons.setSpacing(10);
            hboxButtons.setPadding(new Insets(5,10,5,10));
            hboxButtons.setAlignment(Pos.CENTER);
            
            
            
            // The scene consists of the following items
            // 1) Grid for Input Values
            // 2) HBox - buttons
            VBox vboxPanel = new VBox(gridUserControls, hboxButtons) ;
            
            
            Scene scnSelectVertexLabel = new Scene(vboxPanel);
        
            stgLatLong.initModality(Modality.APPLICATION_MODAL);
            stgLatLong.setTitle(LangConfig.VERTEXLABEL_TITLE);
            stgLatLong.setResizable(false);
            UIInstance.getController().updateStatusBar(true, StatusMsgsConfig.WAITING_USERINPUT);

            stgLatLong.setScene(scnSelectVertexLabel);
            stgLatLong.show();
            stgLatLong.setOnCloseRequest(e -> {
                e.consume();
                stgLatLong.close();
                UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            });
        } catch (Exception ex) {
            System.out.println(".Display(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
}
