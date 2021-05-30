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
import globalstate.ProjectStatusTracker;
import graphelements.UIEdge;
import graphelements.UIVertex;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import ui.utilities.KeyPressFunctionality;

/**
 *  Class Name      : EdgeInformationDialog
 *  Created Date    : 2017-06-09
 *  Description     : Edge Information Dialog that is to be displayed
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class EdgeInformationDialog {
    
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strTitle;
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */    
    private static String getTitle() {
        return strTitle;
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2017-06-09
     *  Description     : Sets the parameters of the Edge Delete Confirmation Dialog Box
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pstrTitle : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters(String pstrTitle) {
        strTitle = pstrTitle;
    }
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2017-06-09
     *  Description     : Closes the Edge Delete Confirmation Dialog Box
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pController : AnalysisController
     *  @param psetUIEdge  : Set of edges
     *  @param pintProjectID : integer
     *  @param pintGraphID : integer
     *  @param pintTimeFrameIndex : integer
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */

    public static void Display(
              AnalysisController pController
            , Set<UIEdge> psetUIEdge
            , int pintProjectID
            , int pintGraphID
            , int pintTimeFrameIndex ) {
              
        Stage stgEdgeInformation = new Stage();
        
        ScrollPane scrlTable = new ScrollPane();
        // Add the Table View
        TableView<String[]> tblEdgeInformation ;
        tblEdgeInformation = new TableView<>();
        tblEdgeInformation.setEditable(true);
        tblEdgeInformation.setMaxWidth(800);
        
        Map<String, Boolean> mapAttributesNames = GraphAPI.getEdgeAttributeNamesWithType(pintProjectID, pintGraphID, pintTimeFrameIndex) ;
        Set<String> lstAttributesNames = mapAttributesNames.keySet();
        
        String [][] arrarrstrMetrics = new String[psetUIEdge.size()][lstAttributesNames.size()+1];
        List<Integer> lstEdgeIDs = new LinkedList<>();
        
        //below list will contain info on all the rows which are edited in tableview
        List<String[]> editedRows = new ArrayList<>();
        
        // Initialize the first column for ID        
        int intRowNumber = 0;
        for (UIEdge currentUIEdge : psetUIEdge) {
            int intCurrentID = currentUIEdge.getID() ;
            arrarrstrMetrics[intRowNumber][0] = String.valueOf(intCurrentID) ; 
            lstEdgeIDs.add(intCurrentID) ;
            
            for (int i=1; i<arrarrstrMetrics[0].length; i++) {
                arrarrstrMetrics[intRowNumber][i] = "" ;
            }
            intRowNumber++ ;
        }
        
        ObservableList<String[]> obvData = FXCollections.observableArrayList();
        obvData.addAll(Arrays.asList(arrarrstrMetrics));
        
        // Create a table column for EdgeIDs
        TableColumn tclmEdgeID = new TableColumn("*"+LangConfig.GENERAL_EDGEID);
        tclmEdgeID.setVisible(true);
        tclmEdgeID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                return new SimpleStringProperty((p.getValue()[0]));
            }
        });
        tblEdgeInformation.getColumns().add(tclmEdgeID);
               
        scrlTable.setContent(tblEdgeInformation);
        scrlTable.setFitToHeight(true);
        scrlTable.setFitToWidth(true);
        
        int intIndex = 1;
        
        Set<String> UserAttributes = GraphAPI.getEdgeAttributeNamesWithType(pintProjectID, pintGraphID, pintTimeFrameIndex).keySet();
        
        // This is the place to write all the API invocation and assigning the values to the specific columns        
        for (String strAttributeName : lstAttributesNames) {
            
            String columnName;
            if(UserAttributes.contains(strAttributeName))
                columnName = strAttributeName;
            else
                columnName = "*"+strAttributeName;
            
            TableColumn tclmAttribute = new TableColumn(columnName);
            
            // For each attribute, extract a list of values for all the selected list of edges
            final int intColumnNumber = intIndex ;
            
            Map<Integer, String> mapResults = GraphAPI.getEdgeAttributeValues(pintProjectID, pintGraphID, pintTimeFrameIndex, strAttributeName, lstEdgeIDs) ;
                        
            for (int i = 0; i<lstEdgeIDs.size(); i++) {                            
                int intCurrentEdgeID = Integer.parseInt(arrarrstrMetrics[i][0]) ;
                arrarrstrMetrics[i][intColumnNumber] = mapResults.get(intCurrentEdgeID) ;
            }
            
            
            tclmAttribute.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[intColumnNumber]));
                }
            });
            
            //making user attributes editable  
            if(UserAttributes.contains(strAttributeName)){
                tclmAttribute.setCellFactory(TextFieldTableCell.forTableColumn());
                tclmAttribute.setOnEditCommit( new EventHandler<TableColumn.CellEditEvent<String[], String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<String[], String> event) {             
                        String[] name = event.getTableView().getItems().get(event.getTablePosition().getRow());
                        name[intColumnNumber]=event.getNewValue();
                        updateAttributeValueInLogic(event.getNewValue());
                    }
                    
                    //call to logic to update AttributeValue
                    private void updateAttributeValueInLogic(String strAttrValue) {
                        String[] row = new String[3];
//                        for(UIEdge uiEdge : psetUIEdge)
//                            edgeIDs.add(uiEdge.getID());
                        
                        // store the edgeID, attribute name and value for the edited row in tableview
                        String edgeID = tblEdgeInformation.getSelectionModel().getSelectedItem()[0];
                        row[0]= edgeID;
                        row[1]= strAttributeName;
                        row[2]= strAttrValue;
                        
                        System.out.println("adding changed attribute values to buffer...");
                        
                        //adding the current edited row to the list of edited rows.
                        editedRows.add(row);
                    }
                });
            }
            tblEdgeInformation.getColumns().add(tclmAttribute);
            tclmAttribute.setVisible(true);
            
            
            intIndex++;
        }
        tblEdgeInformation.setItems(obvData);
        
        
        // Add the OK button
        Button btnOK = new Button(LangConfig.GENERAL_OK);
        btnOK.setOnAction(e -> {
            //update the changed attribute values in logic
            GraphAPI.updateEdgeAttributeValue(pintProjectID, pintGraphID, pintTimeFrameIndex, editedRows);
            System.out.println("changed attribute values updated in logic...");
            
            //ProjectTracker to record change in graph
            ProjectStatusTracker.updateProjectModifiedStatus(pintProjectID, ProjectStatusTracker.attributeValueChanged);
                                    
            pController.updateStatusBar(true, StatusMsgsConfig.WINDOW_CLOSING);
            stgEdgeInformation.close();
            pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
        });
        
        btnOK.setCancelButton(false);        
        btnOK.setAlignment(Pos.CENTER);
        
        Button btnCancel = new Button(LangConfig.GENERAL_CANCEL);
        btnCancel.setOnAction(e -> { 
            System.out.println("changed attribute values NOT updated in logic...");
            pController.updateStatusBar(true, StatusMsgsConfig.WINDOW_CLOSING);
            stgEdgeInformation.close();
            pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
        });
  
        HBox hbox = new HBox(btnOK,btnCancel);
        hbox.setPadding(new Insets(0, 10, 0, 10));
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(40);
        VBox label = new VBox();
        label.setPadding(new Insets(0, 10, 0, 10));
        label.setAlignment(Pos.CENTER_LEFT);
        label.setSpacing(10);
        Label sysAttributeLabel = new Label("* - denotes system attributes, cannot be edited.");
        Label editAttributeLabel = new Label("  - press enter to save edited attribute value and click 'OK' button to commit.");
        label.getChildren().addAll(sysAttributeLabel,editAttributeLabel);
        VBox vbox = new VBox(tblEdgeInformation, hbox, label);                
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);
        
        Scene scnEdgeInformation = new Scene(vbox);
        scnEdgeInformation.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
                stgEdgeInformation.close();
                pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            }
        });
        
        stgEdgeInformation.initModality(Modality.APPLICATION_MODAL);
        stgEdgeInformation.setTitle(getTitle()); 
        stgEdgeInformation.setResizable(false);
        
        stgEdgeInformation.setScene(scnEdgeInformation);
        stgEdgeInformation.show();
        stgEdgeInformation.setAlwaysOnTop(true);
        
        stgEdgeInformation.setOnCloseRequest(event -> {
            event.consume();
            stgEdgeInformation.close();
            pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
        });
        
        // Adds the funcitonality to close the window on pressing the specific key
        KeyPressFunctionality.CloseWindowOnKeyPress(stgEdgeInformation, scnEdgeInformation, KeyCode.ESCAPE);
        
        pController.updateStatusBar(true, StatusMsgsConfig.Edge_INFORMATION);
    }
}