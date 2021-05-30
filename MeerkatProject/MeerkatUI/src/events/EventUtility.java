/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import ca.aicml.meerkat.api.GraphAPI;
import config.LangConfig;
import config.SceneConfig;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *  Class Name      : EventUtility
 *  Created Date    : 2017-03-25
 *  Description     : Contains all utility methods for event construction.
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2017-03-25      @sankalp         N/A
*/


public class EventUtility {
    
    /**
     *  Method Name     : generateTableView()
     *  Created Date    : 2017-03-25
     *  Description     : Generates vertex table for each event.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    
    public static TableView generateTableView(Map<String , Map<Integer, String>> mapResults, Set<String> userAttributes, 
        Map<Integer, UIVertexEvent> vertexMap){

        TableView<String[]> tblVertexInformation = new TableView<>();
        String [][] arrarrstrMetrics = new String[vertexMap.size()][mapResults.size()+1];

        // Initialize the first column for vertexID.      
        int intRowNumber = 0;
        for (Integer currentVertex : vertexMap.keySet()) {
            int intCurrentID = currentVertex;
            arrarrstrMetrics[intRowNumber][0] = String.valueOf(intCurrentID);

            for (int i=1; i<arrarrstrMetrics[0].length; i++) {
                arrarrstrMetrics[intRowNumber][i] = "" ;
            }
            intRowNumber++ ;
        }

        // create an observable list which would contain table rows with vertex information.
        ObservableList<String[]> obvData = FXCollections.observableArrayList();
        obvData.addAll(Arrays.asList(arrarrstrMetrics));

        // Create a table column for VertexIDs
        TableColumn tclmVertexID = new TableColumn(LangConfig.GENERAL_VERTEXID);
        tclmVertexID.setVisible(true);

        // binding the first value of string array (row) to vertex column.
        tclmVertexID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                return new SimpleStringProperty((p.getValue()[0]));
            }
        });

        // add vertex column to the table.
        tblVertexInformation.getColumns().add(tclmVertexID);

        int intIndex = 1;

        // creating other table columns and assigning values to them for different rows.    
        for (String strAttributeName : mapResults.keySet()) {

            //excluding system attributes (only SYS:X & SYS:Y for now) as table columns.
            if(!strAttributeName.equals(GraphAPI.getMeerkatSystemXAttribute()) && !strAttributeName.equals(GraphAPI.getMeerkatSystemYAttribute())){

                TableColumn tclmAttribute = new TableColumn(strAttributeName);

                final int intColumnNumber = intIndex ;

                // populating the column values for every vertex.
                for (int i = 0; i<vertexMap.keySet().size(); i++) {                            
                    int intCurrentVertexID = Integer.parseInt(arrarrstrMetrics[i][0]) ;
                    arrarrstrMetrics[i][intColumnNumber] = mapResults.get(strAttributeName).get(intCurrentVertexID);
                }

                tclmAttribute.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                        return new SimpleStringProperty((p.getValue()[intColumnNumber]));
                    }
                });

                //setting only the vertex user Attributes as editable in tableview.
                if(userAttributes.contains(strAttributeName)){                    
                    tclmAttribute.setCellFactory(TextFieldTableCell.forTableColumn());

                    tclmAttribute.setOnEditCommit( new EventHandler<TableColumn.CellEditEvent<String[], String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<String[], String> event) {             
                            String[] name = event.getTableView().getItems().get(event.getTablePosition().getRow());
                            name[intColumnNumber]=event.getNewValue();                   
                        }
                    });
                }

                tblVertexInformation.getColumns().add(tclmAttribute);
                tclmAttribute.setVisible(true);

                intIndex++;
            }
        }

        //listener to scale & throb vertex when the corresponding table row is selected.
        tblVertexInformation.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String[]> obs, String[] oldSelection, String[] newSelection) -> {
            Integer vertexID = Integer.parseInt(newSelection[0]);
            EventUtility.VertexThrob(SceneConfig.VERTEX_TIMEPERTHROB_MILLIS, SceneConfig.VERTEX_THROB_COUNT, vertexMap.get(vertexID).getVertexShape());
        });

        tblVertexInformation.setItems(obvData);

        return tblVertexInformation;
    }
    
    /**
     *  Method Name     : VertexThrob()
     *  Created Date    : 2017-03-25
     *  Description     : throbs the vertex (circle) on the pane.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    
    public static void VertexThrob(int pintTimePerThrobMills, int pintThrobCount, Circle circle ) {
        EventUtility.VertexScale(SceneConfig.VERTEX_THROB_SCALE, SceneConfig.VERTEX_TIMESCALE_MILLIS, circle);
        FadeTransition fadeStart = new FadeTransition(Duration.millis(pintTimePerThrobMills), circle);
        fadeStart.setFromValue(0);
        fadeStart.setToValue(1);        
        fadeStart.setAutoReverse(true);
        fadeStart.setCycleCount(pintThrobCount);
        fadeStart.play();
    }
    
    /**
     *  Method Name     : VertexScale()
     *  Created Date    : 2017-03-25
     *  Description     : scales the vertex (circle) on the pane.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    
    public static void VertexScale(int pintThrobScale, int pintTimeThrobScaleCycle,  Circle circle){
        ScaleTransition st = new ScaleTransition(Duration.millis(pintTimeThrobScaleCycle), circle);
        st.setByX(pintThrobScale);
        st.setByY(pintThrobScale);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
    }
        
    /**
     *  Method Name     : focusTableRow()
     *  Created Date    : 2017-03-25
     *  Description     : focuses the selected table row.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    
    public static void focusTableRow(TableView table, int rowIndex){
        table.requestFocus();
        table.getSelectionModel().select(rowIndex);
        table.getFocusModel().focus(rowIndex); 
        table.scrollTo(rowIndex);
    }  
}
