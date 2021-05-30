/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.analysis.GraphMetricAPI;
import config.LangConfig;
import globalstate.MeerkatUI;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *  Class Name      : StatisticsValues
 *  Created Date    : 2015-09-25
 *  Description     : The values per graph that are to be stored in the Statistics Pane
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class StatisticsValues {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private MetricElements mtrElements ; // Mapping between the text and the ID
    
    private String [][] arrarrstrMetrics ;
    private boolean [] arrblnWasRunBefore ;
    
    // private List<Integer> lstVertexIDs ;
        
    // private List<String> lststrColumnHeaders ;
    // UI Elements
    private VBox vboxContainer ;
    private ScrollPane scrlTable ; // Holder of Table
    private TableView<String[]> tblStatistics ;
    private GridPane gridMetrics ;
    ObservableList<String[]> obvData ;
    
    private Integer intSelectedID ;
        
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */

    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    /**
     *  Constructor Name: StatisticsValues()
     *  Created Date    : 2015-09-28
     *  Description     : Intiates the Statistics Values
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pintTimeFrameIndex : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public StatisticsValues (int pintProjectID, int pintGraphID, int pintTimeFrameIndex) {
        
        mtrElements = MetricElements.getInstance();
        tblStatistics = new TableView<>();
        scrlTable = new ScrollPane();
        gridMetrics = new GridPane() ;
        intSelectedID = new Integer(-1) ;
        
        int intVerticesCount = GraphAPI.getVertexCount(pintProjectID, pintGraphID, pintTimeFrameIndex) ;
        int intMetricsElements = this.mtrElements.getMetricsIDTextMapping().size() ;
        
        // Initialize the arrays 
        arrblnWasRunBefore = new boolean[intMetricsElements+1] ;        
        arrarrstrMetrics = new String [intVerticesCount][intMetricsElements+1] ;
        
        // Initialize the first column
        List<Integer> lstVertexIDs = GraphAPI.getAllVertexIds(pintProjectID, pintGraphID, pintTimeFrameIndex);
        int intRowNumber = 0;
        for (int intVertexID : lstVertexIDs) {
            arrarrstrMetrics[intRowNumber][0] = String.valueOf(intVertexID) ;            
            for (int i=1; i<arrarrstrMetrics[0].length; i++) {
                arrarrstrMetrics[intRowNumber][i] = null ;
            }
            intRowNumber++ ;
        }
        
        obvData = FXCollections.observableArrayList();
        obvData.addAll(Arrays.asList(arrarrstrMetrics));
        
        // Create a table column for VertexIDs
        TableColumn tclmVertexID = new TableColumn(LangConfig.GENERAL_VERTEXID);
        tclmVertexID.setVisible(true);
        tclmVertexID.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                return new SimpleStringProperty((p.getValue()[0]));
            }
        });
        tclmVertexID.setComparator(Comparator.comparing(vertexID -> {  
                return Integer.parseInt((String) vertexID);
        }));
        
        tblStatistics.getColumns().add(tclmVertexID);
        tblStatistics.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                System.out.println("StatisticsValue(): "+newValue);
                
                //Check whether item is selected and set value of selected item to Label
                
                if(tblStatistics.getSelectionModel().getSelectedItem() != null) 
                {    
                   intSelectedID = Integer.parseInt(tblStatistics.getSelectionModel().getSelectedItem()[0]);
                   // Select the Item in Graph
                   MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
                   UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().selectVertex(intSelectedID.intValue());
                   
                   
                   //update the selected vertex in minimap
                    MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getMinimapDelegator().updateMinimap();
                    MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getMinimapDelegator().changeMinimapMasking();
                }
            }
        });
        ////
        
      tblStatistics.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override 
            public void handle(MouseEvent event) {
                if (event.isSecondaryButtonDown()) {
                    //System.out.println("StatisticsValues.statisticsValues() : row = " + tblStatistics.getSelectionModel().getSelectedItem()[0]);                   
                    // Display the Context Menu on right click
                    if(tblStatistics.getSelectionModel().getSelectedItem() != null) 
                    {    
                        intSelectedID = Integer.parseInt(tblStatistics.getSelectionModel().getSelectedItem()[0]);
                        // Select the Item in Graph and display Vertex context Menu
                        MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
                        
                        UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getSelectVertex(intSelectedID).getVertexHolder().displayVertexContextMenu(event.getScreenX(), event.getScreenY());
                        
                    }
                    
                }
            }
        });
        ////
                
        scrlTable.setContent(tblStatistics);
        scrlTable.setFitToHeight(true);
        scrlTable.setFitToWidth(true);        
        
        gridMetrics.setVgap(4);
        gridMetrics.setPadding(new Insets(5, 5, 5, 5));
        
        int intIndex = 1;
                
        // This is the place to write all the API invocation and assigning the values to the specific columns
        
        for (String strKey : mtrElements.getMetricsIDTextMapping().keySet()) {            
            String strMetricID = mtrElements.getMetricID(strKey);
            String [] arrstrMetricParameter = new String [] {mtrElements.getMetricParameter(strMetricID)};
            final int intColumnNumber = intIndex ;
            TableColumn tclmMeasure = new TableColumn(strKey);
            arrblnWasRunBefore[intColumnNumber] = false ;
            
            tclmMeasure.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[intColumnNumber]));
                }
            });
            tblStatistics.getColumns().add(tclmMeasure);
            tclmMeasure.setVisible(false);
            
            CheckBox chbCurrentItem = new CheckBox(strKey);
            chbCurrentItem.setDisable(false);
            chbCurrentItem.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if (newValue) { // If the Checkbox is checked                                        
                    // System.out.println("StatisticsValue.StatisticsValue(): Was x run?: "+arrblnWasRunBefore[intColumnNumber]);
                    if (!arrblnWasRunBefore[intColumnNumber]) {
                        
                        // Invoke the API
                        GraphMetricAPI.computeCentrality(pintProjectID, pintGraphID, pintTimeFrameIndex, strMetricID, arrstrMetricParameter) ;
                        while (!GraphMetricAPI.isDone(pintProjectID, pintGraphID, pintTimeFrameIndex, strMetricID, arrstrMetricParameter)) {
                            // Do Nothing
                            // System.out.println("StatisticsValue.StatisticsValue(): Inside while loop - Column Number: "+intColumnNumber);
                        }                        
                        Map<Integer, Double> mapResults = GraphMetricAPI.getVertexMetricScores(pintProjectID, pintGraphID, pintTimeFrameIndex, strMetricID, arrstrMetricParameter) ;
                        
                        for (int i = 0; i<intVerticesCount; i++) {                            
                            int intCurrentVertexID = Integer.parseInt(arrarrstrMetrics[i][0]);
                            arrarrstrMetrics[i][intColumnNumber] = String.valueOf(mapResults.get(intCurrentVertexID)) ;
                            // System.out.println("StatisticsValue.StatisticsValue(): Vertex: "+intCurrentVertexID+"\tValue: "+mapResults.get(intCurrentVertexID));
                        }
                        
                        arrblnWasRunBefore[intColumnNumber] = true ;
                        // Set it to true
                    } else {
                        // Check for change in graph 
                        boolean blnDidGraphChange = false ; // Invoke the API here
                        if (blnDidGraphChange) {
                            // Update the array with new values // Invoke the API
                            GraphMetricAPI.computeCentrality(pintProjectID, pintGraphID, pintTimeFrameIndex, strMetricID, arrstrMetricParameter) ;
                            while (!GraphMetricAPI.isDone(pintProjectID, pintGraphID, pintTimeFrameIndex, strMetricID, arrstrMetricParameter)) {
                                // Do Nothing
                            }                        
                            Map<Integer, Double> mapResults = GraphMetricAPI.getVertexMetricScores(pintProjectID, pintGraphID, pintTimeFrameIndex, strMetricID, arrstrMetricParameter) ;

                            for (int i = 0; i<intVerticesCount; i++) {
                                int intCurrentVertexID = Integer.parseInt(arrarrstrMetrics[i][0]) ;
                                arrarrstrMetrics[i][intColumnNumber] = String.valueOf(mapResults.get(intCurrentVertexID)) ;
                            }
                        }                        
                    }
                    tclmMeasure.setVisible(true);
                } else {
                    // System.out.println("StatisticsContent.updatePane(): Old Value is: "+oldValue);
                    tclmMeasure.setVisible(false);
                }
            });            
            gridMetrics.add(chbCurrentItem, (intIndex+1)%2, (intIndex-1)/2);
            
            intIndex++;
        }
        tblStatistics.setItems(obvData);
        
        vboxContainer = new VBox(scrlTable, gridMetrics);
        vboxContainer.setSpacing(20);
        vboxContainer.setPadding(new Insets(10, 10, 10, 10));
        
    }   
    
    public Node getStatValuesContainer () {
        return this.vboxContainer ;
    }
    
    /**
     *  Method Name     : updateStatisticsValues()
     *  Created Date    : 2016-02-
     *  Description     : Updates the Statistics Values for a graph given in a time index
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pintTimeFrameIndex : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */

    public void updateStatisticsValues(int pintProjectID, int pintGraphID, int pintTimeFrameIndex) {
        
    }
    
    
    /**
     *  Method Name     : focusARow()
     *  Created Date    : 2016-05-11
     *  Description     : Focusses a row of the table view in the statistics pane
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintVertexID : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void focusARow(int pintVertexID) {
        try {
            int intIndex = -1;
            for (String [] dblRow : obvData) {
                intIndex ++ ;
                if (dblRow[0].equals(String.valueOf(pintVertexID))) {                
                    // Higlight the row

    //                tblStatistics.requestFocus();
                    // tblStatistics.getSelectionModel().select(intIndex);
    //                tblStatistics.getFocusModel().focus(intIndex);
                }
            }
        } catch (Exception ex) {
            System.out.println("StatisticsValue.focusARow(): EXCEPTION");
        }
    }
}
