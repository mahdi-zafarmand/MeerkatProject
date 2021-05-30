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
import graphelements.UIVertex;
import java.util.ArrayList;
import java.util.Arrays;
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
 *  Class Name      : VertexInformationDialog
 *  Created Date    : 2016-05-10
 *  Description     : Vertex Information Dialog that is to be displayed
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class VertexInformationDialog {
    
    
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
     *  Created Date    : 2016-05-10
     *  Description     : Sets the parameters of the Vertex Delete Confirmation Dialog Box
     *  Version         : 1.0
     *  @author         : Talat
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
     *  Created Date    : 2016-05-10
     *  Description     : Closes the Vertex Delete Confirmation Dialog Box
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     *  @param psetUIVertex : List<UIVertex>
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pintTimeFrameIndex : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */

    public static void Display(
              AnalysisController pController
            , Set<UIVertex> psetUIVertex
            , int pintProjectID
            , int pintGraphID
            , int pintTimeFrameIndex ) {
              
        Stage stgVertexInformation = new Stage();
        
        ScrollPane scrlTable = new ScrollPane();
        // Add the Table View
        TableView<String[]> tblVertexInformation ;
        tblVertexInformation = new TableView<>();
        tblVertexInformation.setEditable(true);
        tblVertexInformation.setMaxWidth(800);
        
        List<String> lstAttributesNames = GraphAPI.getAllAttributesNames_Sorted(pintProjectID, pintGraphID, pintTimeFrameIndex) ;
        //below list will contain info on all the rows which are edited in tableview
        List<String[]> editedRows = new ArrayList<>();
        
        String [][] arrarrstrMetrics = new String[psetUIVertex.size()][lstAttributesNames.size()+1];
        List<Integer> lstVertexIDs = new LinkedList<>();
        // Initialize the first column for ID        
        int intRowNumber = 0;
        for (UIVertex currentUIVertex : psetUIVertex) {
            int intCurrentID = currentUIVertex.getVertexHolder().getID() ;
            arrarrstrMetrics[intRowNumber][0] = String.valueOf(intCurrentID) ; 
            lstVertexIDs.add(intCurrentID) ;
            
            for (int i=1; i<arrarrstrMetrics[0].length; i++) {
                arrarrstrMetrics[intRowNumber][i] = "" ;
            }
            intRowNumber++ ;
        }
        
        ObservableList<String[]> obvData = FXCollections.observableArrayList();
        obvData.addAll(Arrays.asList(arrarrstrMetrics));
        
        // Create a table column for VertexIDs
        TableColumn tclmVertexID = new TableColumn("*"+LangConfig.GENERAL_VERTEXID);
        tclmVertexID.setVisible(true);
        tclmVertexID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                return new SimpleStringProperty((p.getValue()[0]));
            }
        });
        tblVertexInformation.getColumns().add(tclmVertexID);
               
        scrlTable.setContent(tblVertexInformation);
        scrlTable.setFitToHeight(true);
        scrlTable.setFitToWidth(true);
        
        int intIndex = 1;
        
        Set<String> UserAttributes = GraphAPI.getAllUserAttributesNames(pintProjectID, pintGraphID, pintTimeFrameIndex);
        //TODO removing File_ID for now. Decide if we want to make it editable.
        UserAttributes.remove("File_ID");
        
        // This is the place to write all the API invocation and assigning the values to the specific columns        
        for (String strAttributeName : lstAttributesNames) {     
            String columnName;
            if(UserAttributes.contains(strAttributeName))
                columnName = strAttributeName;
            else
                columnName = "*"+strAttributeName;
            
            TableColumn tclmAttribute = new TableColumn(columnName);
            
            // For each attribute, extract a list of values for all the selected list of vertices
            final int intColumnNumber = intIndex ;
            
            Map<Integer, String> mapResults = GraphAPI.getVertexAttributeValues(pintProjectID, pintGraphID, pintTimeFrameIndex, strAttributeName, lstVertexIDs) ;
                        
            for (int i = 0; i<lstVertexIDs.size(); i++) {                            
                int intCurrentVertexID = Integer.parseInt(arrarrstrMetrics[i][0]) ;
                arrarrstrMetrics[i][intColumnNumber] = mapResults.get(intCurrentVertexID) ;
                // System.out.println("StatisticsValue.StatisticsValue(): Vertex: "+intCurrentVertexID+"\tValue: "+mapResults.get(intCurrentVertexID));
            }
            
            
            tclmAttribute.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    // System.out.println("StatisticsValue.StatisticsValue(); Column Number: "+intColumnNumber);
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
//                        for(UIVertex uivertex : psetUIVertex)
//                            vertexIDs.add(uivertex.getVertexHolder().getID());

                        // store the vertexID, attribute name and value for the edited row in tableview
                        String vertexID = tblVertexInformation.getSelectionModel().getSelectedItem()[0];
                        row[0]= vertexID;
                        row[1]= strAttributeName;
                        row[2]= strAttrValue;
                        
                        System.out.println("adding changed attribute values to buffer...");
                        
                        //adding the current edited row to the list of edited rows.
                        editedRows.add(row);
                    }
                });
            }
            tblVertexInformation.getColumns().add(tclmAttribute);
            tclmAttribute.setVisible(true);
            
            
            intIndex++;
        }
        tblVertexInformation.setItems(obvData);
        
        
        // Add the OK button
        Button btnOK = new Button(LangConfig.GENERAL_OK);
        btnOK.setOnAction(e -> { 
            //update the changed attribute values in logic
            GraphAPI.updateVertexAttributeValue(pintProjectID, pintGraphID, pintTimeFrameIndex, editedRows);
            System.out.println("changed attribute values updated in logic...");
            
            //ProjectTracker to record change in graph
            ProjectStatusTracker.updateProjectModifiedStatus(pintProjectID, ProjectStatusTracker.attributeValueChanged);
                        
            pController.updateStatusBar(true, StatusMsgsConfig.WINDOW_CLOSING);
            stgVertexInformation.close();
            pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
        });
        btnOK.setCancelButton(false);        
        btnOK.setAlignment(Pos.CENTER);
  
        Button btnCancel = new Button(LangConfig.GENERAL_CANCEL);
        btnCancel.setOnAction(e -> { 
            System.out.println("changed attribute values NOT updated in logic...");
            pController.updateStatusBar(true, StatusMsgsConfig.WINDOW_CLOSING);
            stgVertexInformation.close();
            pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
        });
        
        Button btnExtractInfo = new Button("Extract Info");
        btnExtractInfo.setOnAction(e -> { 
            
            pController.updateStatusBar(true, StatusMsgsConfig.WINDOW_CLOSING);
            stgVertexInformation.close();
            pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
        });
  
        HBox hbox = new HBox(btnOK, btnCancel);
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
        VBox vbox = new VBox(tblVertexInformation, hbox, label);                
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);
        
        Scene scnVertexInformation = new Scene(vbox);
        scnVertexInformation.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
                stgVertexInformation.close();
                pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            }
        });
        
        stgVertexInformation.initModality(Modality.APPLICATION_MODAL);
        stgVertexInformation.setTitle(getTitle()); 
        stgVertexInformation.setResizable(false);
        
        stgVertexInformation.setScene(scnVertexInformation);
        stgVertexInformation.show();
        stgVertexInformation.setAlwaysOnTop(true);
        
        stgVertexInformation.setOnCloseRequest(event -> {
            event.consume();
            stgVertexInformation.close();
            pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
        });
        
        // Adds the funcitonality to close the window on pressing the specific key
        KeyPressFunctionality.CloseWindowOnKeyPress(stgVertexInformation, scnVertexInformation, KeyCode.ESCAPE);
        
        pController.updateStatusBar(true, StatusMsgsConfig.VERTEX_INFORMATION);
    }
}