/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import ca.aicml.meerkat.api.FilterAPI;
import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *  Class Name      : FiltersContent
 *  Created Date    : 2015-09-29
 *  Description     : Contents to be displayed on the Filter Pane
 *  Version         : 2.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-08-23      Abhi            Modified calls to FilterAPI's getFilteredVertices() method to individually pass the vertexFilterValues and EdgeFilterValues
 *                                  to make edge attribute filtering work as well.
 * 
*/
public class FilterContent implements ITitledPaneContents {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private String strTitle ;
    
    private GridPane grid ;    
    private TabPane tabpaneGraph;  // Part of the AnchorPane    
    private Button btnApplyFilter ;
    private String strApplyFilterText ;
    
    private AnchorPane anchor ;
    
    // Vertex Tab UI
    private Tab tabVertex ; 
    private VBox vboxVertexFilterSubContainer ;
    private VBox vboxVertexFilterContainer ;
    private Button btnAddVertexFilter ;
    private String strAddVertexButtonText ;
    private String strRemoveVertexButtonText ;
    private ScrollPane scrlVertexFilter ;
    private static String stringNoOfVertexFilters = "";
    
    // Edge Tab UI    
    private Tab tabEdge ; 
    private VBox vboxEdgeFilterSubContainer ;
    private VBox vboxEdgeFilterContainer ;
    private Button btnAddEdgeFilter ;
    private String strAddEdgeButtonText ;
    private String strRemoveEdgeButtonText ;
    private ScrollPane scrlEdgeFilter ;
    
    private static BooleanProperty blnprpFilterButtonDisable = new SimpleBooleanProperty(true);
    private static BooleanProperty blnprpAddVertexFilterButtonDisable = new SimpleBooleanProperty(false);
    private static BooleanProperty blnprpAddEdgeFilterButtonDisable = new SimpleBooleanProperty(true);
        
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    // Title
    private String getTitle(){
        return this.strTitle;
    }
    private void setTitle(String pstrTitle){
        this.strTitle = pstrTitle;
    }
    private void setAddEdgeButtonText(String pstrText) {
        this.strAddEdgeButtonText = pstrText;
    }
    private String getAddEdgeButtonText() {
        return this.strAddEdgeButtonText;
    }
    
    private void setRemoveEdgeButtonText(String pstrText) {
        this.strRemoveEdgeButtonText = pstrText;
    }
    private String getRemoveEdgeButtonText() {
        return this.strRemoveEdgeButtonText;
    }
    
    private void setAddVertexButtonText(String pstrText) {
        this.strAddVertexButtonText = pstrText ;
    }
    private String getAddVertexButtonText() {
        return this.strAddVertexButtonText;
    }
    
    private void setRemoveVertexButtonText(String pstrText) {
        this.strRemoveVertexButtonText = pstrText ;
    }
    private String getRemoveVertexButtonText() {
        return this.strRemoveVertexButtonText;
    }
    
    private void setApplyFilterButtonText(String pstrText) {
        this.strApplyFilterText = pstrText ;
    }
    private String getAplyFilterButtonText() {
        return this.strApplyFilterText;
    }
    
    
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    /**
     *  Constructor Name: FilterContent
     *  Created Date    : 2015-09-29
     *  Description     : The constructor for the filter content initializing sone of the UI elements
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrTitle : String
     *  @param pstrVertexAddText : String
     *  @param pstrEdgeAddText : String
     *  @param pstrApplyFilterText : String
     *  @param pstrVertexRemoveText : String
     *  @param pstrEdgeRemoveText : String
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-08-23      Abhi            Added params to enable passing on the text values for removing filter buttons in FilterValues class
     *  2016-08-23      Abhi            Modified call to FilterAPI's getFilteredVertices() method to individually pass the vertexFilterValues and EdgeFilterValues 
     *  
     * 
    */
    public FilterContent(String pstrTitle, String pstrVertexAddText, String pstrEdgeAddText, String pstrVertexRemoveText, String pstrEdgeRemoveText, String pstrApplyFilterText) {        
        
        setTitle(pstrTitle);
        
        setApplyFilterButtonText(pstrApplyFilterText);
        setAddVertexButtonText(pstrVertexAddText);
        setAddEdgeButtonText(pstrEdgeAddText);
        
        setRemoveVertexButtonText(pstrVertexRemoveText);
        setRemoveEdgeButtonText(pstrEdgeRemoveText);
        
                
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        /*        
        this.btnAddVertexFilter = new Button(getAddVertexButtonText());
        //this.btnAddVertexFilter.disableProperty().bind(blnprpAddVertexFilterButtonDisable);
        btnAddVertexFilter.setOnAction((ActionEvent e) -> {
            
            UIInstance.getController().updateStatusBar(true, StatusMsgsConfig.FILTERS_ADDING);
            
            if (UIInstance.getActiveProjectTab() != null && UIInstance.getActiveProjectTab().getActiveGraphTab() != null) {
                UIInstance.getActiveProjectTab().getActiveGraphTab().getAccordionTabValues()
                        .getFiltersValues().addVertexAttributeFilterBox(getRemoveVertexButtonText(), btnAddVertexFilter);
                updateVertexFilterContainer();
            }
            
            UIInstance.getController().updateStatusBar(true, StatusMsgsConfig.FILTERS_ADDED);
        });
        */
        
        /*
        this.btnAddEdgeFilter = new Button(getAddEdgeButtonText());
        //this.btnAddEdgeFilter.disableProperty().bind(blnprpAddEdgeFilterButtonDisable);
        btnAddEdgeFilter.setOnAction((ActionEvent e) -> {
            UIInstance.getController().updateStatusBar(true, StatusMsgsConfig.FILTERS_ADDING);
            
            if (UIInstance.getActiveProjectTab() != null && UIInstance.getActiveProjectTab().getActiveGraphTab() != null) {
                UIInstance.getActiveProjectTab().getActiveGraphTab().getAccordionTabValues()
                        .getFiltersValues().addEdgeAttributeFilterBox(getRemoveEdgeButtonText(), btnAddEdgeFilter);                
                updateEdgeFilterContainer();
            }
            UIInstance.getController().updateStatusBar(true, StatusMsgsConfig.FILTERS_ADDED);
        });  
        */
        this.vboxVertexFilterSubContainer = new VBox();
        this.vboxVertexFilterSubContainer.setSpacing(10);
        this.vboxVertexFilterSubContainer.setPadding(new Insets(5,5,5,5));
        
        this.vboxEdgeFilterSubContainer = new VBox();
        this.vboxEdgeFilterSubContainer.setSpacing(10);
        this.vboxEdgeFilterSubContainer.setPadding(new Insets(5,5,5,5));
        
        //this.vboxVertexFilterContainer = new VBox(vboxVertexFilterSubContainer, btnAddVertexFilter);
        this.vboxVertexFilterContainer = new VBox(vboxVertexFilterSubContainer);
        //this.vboxVertexFilterContainer.setSpacing(10);
        //this.vboxVertexFilterContainer.setPadding(new Insets(5,5,5,5));
        
        //this.vboxEdgeFilterContainer = new VBox(vboxEdgeFilterSubContainer, btnAddEdgeFilter);
        this.vboxEdgeFilterContainer = new VBox(vboxEdgeFilterSubContainer);
        //this.vboxEdgeFilterContainer.setSpacing(10);
        //this.vboxEdgeFilterContainer.setPadding(new Insets(5,5,5,5));
        
        this.scrlVertexFilter = new ScrollPane(this.vboxVertexFilterContainer);
        this.tabVertex = new Tab(LangConfig.GENERAL_VERTEXPLURAL);
        this.tabVertex.setClosable(false);  
        this.tabVertex.setContent(this.scrlVertexFilter);
        
        this.scrlEdgeFilter = new ScrollPane(this.vboxEdgeFilterContainer);
        this.tabEdge = new Tab(LangConfig.GENERAL_EDGEPLURAL);
        this.tabEdge.setClosable(false);  
        this.tabEdge.setContent(this.scrlEdgeFilter);     
        
        this.tabpaneGraph = new TabPane(this.tabVertex, this.tabEdge);
                
        this.grid = new GridPane();
                
        this.btnApplyFilter = new Button(getAplyFilterButtonText());
        this.btnApplyFilter.disableProperty().bind(blnprpFilterButtonDisable);
        btnApplyFilter.setOnAction((ActionEvent e) -> {            
            
            UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.FILTERS_APPLYING);

            // Invoke a method in API that sends the request   
            int intProjectID = UIInstance.getActiveProjectID();
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID();
            int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            //String [][] arrarrstrFilters = UIInstance.getActiveProjectTab().getActiveGraphTab().getAccordionTabValues().getFiltersValues().getFilters();
            String [][] arrarrstrVertexFilters = 
                    UIInstance.getActiveProjectTab().getActiveGraphTab()
                            .getAccordionTabValues()
                            .getFiltersValues().getVertexFiltersValues();
            String [][] arrarrstrEdgeFilters = 
                    UIInstance.getActiveProjectTab().getActiveGraphTab()
                            .getAccordionTabValues()
                            .getFiltersValues().getEdgeFiltersValues();
            
            for (String [] arrstrCurrentFilter : arrarrstrVertexFilters) {
                System.out.println("FilterVertexContent.initialize(): "+ 
                        arrstrCurrentFilter[0]+"\t"+arrstrCurrentFilter[1]+
                        "\t"+arrstrCurrentFilter[2]);
            }
            for (String [] arrstrCurrentFilter : arrarrstrEdgeFilters) {
                System.out.println("FilterEdgeContent.initialize(): "+
                        arrstrCurrentFilter[0]+"\t"+arrstrCurrentFilter[1]+
                        "\t"+arrstrCurrentFilter[2]);
            }
            
            Set<Integer> setFilterVertexIDs = FilterAPI.getFilteredVertices(intProjectID, intGraphID, intTimeFrameIndex, arrarrstrVertexFilters, arrarrstrEdgeFilters);
            System.out.println("FilterContent: "+setFilterVertexIDs);
            UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().clearSelectedVertexList();
            UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().selectVertex(setFilterVertexIDs);
            
            //TODO -> check how the "AND" logic on filters work if both vertex and edge filters are selected.
            // Also, on vertex filters, should we extract both vertex and corresponding edges?
            if(arrarrstrEdgeFilters.length>0){
                Set<Integer> setFilterEdgeIDs = FilterAPI.getFilteredEdges(intProjectID, intGraphID, intTimeFrameIndex, arrarrstrEdgeFilters);
                UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().selectEdge(setFilterEdgeIDs);
            }
            
            //update the selected community in minimap
            MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getMinimapDelegator().updateMinimap();
            MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getMinimapDelegator().changeMinimapMasking();
            
            UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.FILTERS_APPLIED);
        });
        
    }
    
    public void updateVertexTabPaneNoOfFilters(int pintNoOfVertexFilters){
        
        /*
        String stNoOfFiltersText = "";
        if(pintNoOfVertexFilters > 0){
            stNoOfFiltersText =  " ("+ pintNoOfVertexFilters + ")";
        }
        stringNoOfVertexFilters = stNoOfFiltersText;
        tabVertex.setText(LangConfig.GENERAL_VERTEXPLURAL + stringNoOfVertexFilters);
        */
    }
    
    /**
     *  Method Name     : initialize()
     *  Created Date    : 2015-09-29
     *  Description     : Overridden method to initialize the UI container components
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param paccParentAccordionPane : Accordion
     *  @param paccFilters : TitledPane
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    @Override
    public void initialize(Accordion paccParentAccordionPane, TitledPane paccFilters) {

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setFillWidth(true);
        columnConstraints.setHgrow(Priority.ALWAYS);
        this.grid.getColumnConstraints().add(columnConstraints);

        // grid.setStyle("-fx-background-color:red");
        grid.add(this.tabpaneGraph, 0, 0);
        grid.add(this.btnApplyFilter, 0, 1);
        grid.setDisable(true);
        
        anchor = new AnchorPane();
        anchor.setPrefHeight(paccParentAccordionPane.getHeight());
        anchor.prefWidth(paccParentAccordionPane.getWidth());
        anchor.getChildren().add(this.grid);
        
        paccFilters.setText(this.getTitle());
        paccFilters.setContent(grid);
    }

    /**
     *  Method Name     : updatePane
     *  Created Date    : 2015-09-29
     *  Description     : Updating the pane when there is a change in the Graph/Project Tab
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param paccParentAccordionPane: Accordion
     *  @param paccTitledPane: TitledPane
     *  @param pintProjectID : int - Project which contains the graph
     *  @param pintGraphID: int - Graph whose parameters are to be reflected in the FiltersContent Pane
     *  @param pobjContent : Object - Any object that contains the dependent information
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-08-23      Abhi            Modified to show the vertex filter pane first by default 
     *  2016-09-12      Abhi            Modified to call new versions of updateEdgeFilterContainer2() methods
     *  
     * 
    */
    @Override
    public void updatePane(Accordion paccParentAccordionPane, TitledPane paccTitledPane, int pintProjectID, int pintGraphID, int pintTimeFrameIndex, Object pobjContent) {
        FilterValues fltValues = (FilterValues)pobjContent;    
        grid.setDisable(false);
        // show the vertex tab by default
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        //updateVertexTabPaneNoOfFilters(UIInstance.getActiveProjectTab().getActiveGraphTab().getAccordionTabValues().getFiltersValues().getNoOfVertexFilters());
        this.updateEdgeFilterContainer2();
        this.updateVertexFilterContainer2();  
        //System.out.println("FilterContent. updatePane on graph tab switch");
    }
    
    
    /**
     *  Method Name     : updateVertexFilterContainer
     *  Created Date    : 2015-09-29
     *  Description     : Updates the Vertex Filter Container that contains the 
     *                      list of Attribute Filter Boxes and redraws the UI (Filter Pane)
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void updateVertexFilterContainer() {
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        if (UIInstance.getActiveProjectTab() != null && UIInstance.getActiveProjectTab().getActiveGraphTab() != null) {
            this.vboxVertexFilterContainer.getChildren().clear();        
            this.vboxVertexFilterContainer.getChildren().addAll(
                    UIInstance.getActiveProjectTab().getActiveGraphTab()
                            .getAccordionTabValues()
                            .getFiltersValues().getVertexBox()
                , btnAddVertexFilter);
                        
            this.scrlVertexFilter.setContent(this.vboxVertexFilterContainer);
            tabpaneGraph.getTabs().removeAll(this.tabVertex, this.tabEdge);
            tabpaneGraph.getTabs().addAll(this.tabVertex, this.tabEdge);
            
            // Need to clear the grid too 
            grid.getChildren().clear();;
            grid.add(this.tabpaneGraph, 0, 0);
            grid.add(this.btnApplyFilter, 0, 1);
            
        }
    }
    /**
     *  Method Name     : updateVertexFilterContainer
     *  Created Date    : 2016-09-12
     *  Description     : Updates the Vertex Filter Container that contains the 
     *                      list of Attribute Filter Boxes and Add filter button(from FilterVaues class)
     *                      and redraws the UI (Filter Pane)
     *  Version         : 1.0
     *  @author         : Abhi
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateVertexFilterContainer2() {
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        if (UIInstance.getActiveProjectTab() != null && UIInstance.getActiveProjectTab().getActiveGraphTab() != null) {
            this.vboxVertexFilterContainer.getChildren().clear();        
            this.vboxVertexFilterContainer.getChildren().addAll(UIInstance.getActiveProjectTab().getActiveGraphTab().getAccordionTabValues().getFiltersValues().getVertexBox()
                    );
                        
            this.scrlVertexFilter.setContent(this.vboxVertexFilterContainer);
            tabpaneGraph.getTabs().removeAll(this.tabVertex, this.tabEdge);
            tabpaneGraph.getTabs().addAll(this.tabVertex, this.tabEdge);
            
            // Need to clear the grid too 
            grid.getChildren().clear();;
            grid.add(this.tabpaneGraph, 0, 0);
            grid.add(this.btnApplyFilter, 0, 1);
            
        }
    }
    

    /**
     *  Method Name     : updateEdgeFilterContainer
     *  Created Date    : 2015-09-29
     *  Description     : Updates the Vertex Filter Container that contains the 
     *                      list of Attribute Filter Boxes and redraws the UI (Filter Pane)
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-08-23      Abhi            Modified to show the edge pane after repainting - if a filter was added to edge pane
     *  
     * 
    */
    private void updateEdgeFilterContainer() {
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        if (UIInstance.getActiveProjectTab() != null && UIInstance.getActiveProjectTab().getActiveGraphTab() != null) {            
            this.vboxEdgeFilterContainer.getChildren().clear();
            this.vboxEdgeFilterContainer.getChildren().addAll(UIInstance.getActiveProjectTab().getActiveGraphTab().getAccordionTabValues().getFiltersValues().getEdgeBox()
                , btnAddEdgeFilter);
            
            this.scrlEdgeFilter.setContent(this.vboxEdgeFilterContainer);
            tabpaneGraph.getTabs().removeAll(this.tabVertex, this.tabEdge);
            tabpaneGraph.getTabs().addAll(this.tabVertex, this.tabEdge);
            // select the edge pane to be shown
            tabpaneGraph.getSelectionModel().select(this.tabEdge);

            grid.getChildren().clear();;
            grid.add(this.tabpaneGraph, 0, 0);
            grid.add(this.btnApplyFilter, 0, 1);
            
        }
    }
    public void updateEdgeFilterContainer2() {
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        if (UIInstance.getActiveProjectTab() != null && UIInstance.getActiveProjectTab().getActiveGraphTab() != null) {            
            this.vboxEdgeFilterContainer.getChildren().clear();
            this.vboxEdgeFilterContainer.getChildren().addAll(UIInstance.getActiveProjectTab().getActiveGraphTab().getAccordionTabValues().getFiltersValues().getEdgeBox()
                );
            
            this.scrlEdgeFilter.setContent(this.vboxEdgeFilterContainer);
            tabpaneGraph.getTabs().removeAll(this.tabVertex, this.tabEdge);
            tabpaneGraph.getTabs().addAll(this.tabVertex, this.tabEdge);
            // select the edge pane to be shown
            tabpaneGraph.getSelectionModel().select(this.tabEdge);

            grid.getChildren().clear();;
            grid.add(this.tabpaneGraph, 0, 0);
            grid.add(this.btnApplyFilter, 0, 1);
            
        }
    }
    
    /**
     *  Method Name     : enableFilterButton()
     *  Created Date    : 2016-05-16
     *  Description     : Enables the filter button by setting the property to which the button's diable property is bound
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void enableFilterButton() {
        blnprpFilterButtonDisable.set(false);
    }
    
    /**
     *  Method Name     : disableFilterButton()
     *  Created Date    : 2016-05-16
     *  Description     : Enables the filter button by setting the property to which the button's diable property is bound
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void disableFilterButton() {
        blnprpFilterButtonDisable.set(true);
    }
    /**
     *  Method Name     : enableAddVertexFilterButton()
     *  Created Date    : 2016-09-07
     *  Description     : Enables the Add Vertex Filter button by setting the property to which the button's disable property is bound
     *  Version         : 1.0
     *  @author         : Abhi
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void enableAddVertexFilterButton() {
        blnprpAddVertexFilterButtonDisable.set(false);
    }
    public static void disableAddVertexFilterButton() {
        blnprpAddVertexFilterButtonDisable.set(true);
    }
    public static void enableAddEdgeFilterButton() {
        blnprpAddVertexFilterButtonDisable.set(false);
    }
    public static void disableAddEdgeFilterButton() {
        blnprpAddVertexFilterButtonDisable.set(true);
    }
}
