/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import ca.aicml.meerkat.api.analysis.GraphDetails;
import globalstate.MeerkatUI;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polygon;
import ui.utilities.FXUtils;

/**
 *  Class Name      : DetailsContent
 *  Created Date    : 2015-06-26
 *  Description     : Contents to be displayed on the DetailsContent Pane
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-09-09      Talat           Renamed the class name from Details to DetailsContent
 *  2015-09-01      Talat           Implements ITitledPaneContents
 * 
*/
public class DetailsContent implements ITitledPaneContents {
    
    // VARIABLES TO STORE THE DISPLAY ELEMENTS
    private String strTitle ;
    
    private String strEdgeCount ;
    private String strVertexCount ;
    private String strDensity ;
    private String strAvgConnections ;
    private String strAvgCoefficient ;
    private String strAvgAssortavity ;
    private String strAvgShortestPath;
    
    private Button btnDensity ;
    private Button btnAvgConnections ;
    private Button btnAvgCoefficient ;
    private Button btnAvgAssortavity ;
    private Button btnAvgShortestPath;
//    private String style = 
//               "-fx-min-width: 10px; " +
//                "-fx-min-height: 10px; " +
//                "-fx-max-width: 10px; " +
//                "-fx-max-height: 10px;"+
//            "-fx-background-color: grey;";
    
    private String style = 
            "-fx-background-color: "
            + "linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),"
            + "linear-gradient(#020b02, #3a3a3a),"
            + "linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),"
            + "linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),"
            + "linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);"
            + "-fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);"
            + "-fx-min-width: 5px; "
            + "-fx-min-height: 5px; " 
            + "-fx-scale-x: 0.5; "
            + "-fx-scale-y: 0.5; ";

               
    /* These variables are never used
    private Label lblEdgeCount ;
    private Label lblVertexCount ;
    private Label lblDensity ;
    private Label lblAvgConnections ;
    private Label lblAvgCoefficient ;
    private Label lblAvgAssortavity ;
    private Label lblAvgShortestPath;
    
    private Label lblEdgeCountValue ;
    private Label lblVertexCountValue ;
    private Label lblDensityValue ;
    private Label lblAvgConnectionsValue ;
    private Label lblAvgCoefficientValue ;
    private Label lblAvgAssortavityValue ;
    private Label lblAvgShortestPathValue ;
    */   
    MeerkatUI UIInstance ;
    GridPane grid = new GridPane();
    
    
    public String getTitle(){
        return this.strTitle;
    }
    public String getEdgeCountText(){
        return this.strEdgeCount;
    }
    
    public String getVertexCountText(){
        return this.strVertexCount;
    }
    public String getDensityText(){
        return this.strDensity;
    }
    public String getAvgConnectionsText(){
        return this.strAvgConnections;
    }
    public String getAvgCoefficientText(){
        return this.strAvgCoefficient;
    }
    public String getAvgAssortavityText(){
        return this.strAvgAssortavity;
    }
    public String getAvgShortestPathText(){
        return this.strAvgShortestPath;
    }
    
    
    
    /********************************* CONSTRUCTOR *******************************/
     
    /**
     *  Constructor Name: DetailsContent()
     *  Created Date    : 2016-
     *  Description     : 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrTitle
     *  @param pstrVertexCount
     *  @param pstrEdgeCount
     *  @param pstrDensity
     *  @param pstrAvgConnections
     *  @param pstrAvgCoefficient
     *  @param pstrAvgAssortavity
     *  @param pstrAvgShortestPath
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public DetailsContent(
              String pstrTitle
            , String pstrVertexCount
            , String pstrEdgeCount
            , String pstrDensity
            , String pstrAvgConnections
            , String pstrAvgCoefficient
            , String pstrAvgAssortavity
            , String pstrAvgShortestPath
    ){
        
        UIInstance = MeerkatUI.getUIInstance();
        
        this.strTitle = pstrTitle;
        this.strVertexCount = pstrVertexCount ;
        this.strEdgeCount = pstrEdgeCount ;
        this.strDensity = pstrDensity ;
        this.strAvgConnections = pstrAvgConnections ;
        this.strAvgCoefficient = pstrAvgCoefficient;
        this.strAvgAssortavity = pstrAvgAssortavity;
        this.strAvgShortestPath = pstrAvgShortestPath;        
        
        /*
        grid.add(new Label(this.getVertexCountText()+"\t"), 0, 0);        
        grid.add(new Label(""), 1, 0);
                
        grid.add(new Label(this.getEdgeCountText()+"\t"), 0, 1);        
        grid.add(new Label(""), 1, 1);
        
        grid.add(new Label(this.getDensityText()+"\t"), 0, 2);
        grid.add(new Label(""), 1, 2);
        grid.add(btnDensity, 2, 2);
        
        grid.add(new Label(this.getAvgConnectionsText()+"\t"), 0, 3);
        grid.add(new Label(""), 1, 3);
        grid.add(btnAvgConnections, 2, 3);
        
        grid.add(new Label(this.getAvgCoefficientText()+"\t"), 0, 4);
        grid.add(new Label(""), 1, 4);
        grid.add(btnAvgCoefficient, 2, 4);
        
        grid.add(new Label(this.getAvgAssortavityText()+"\t"), 0, 5);
        grid.add(new Label(""), 1, 5);
        grid.add(btnAvgAssortavity, 2, 5);
        
        grid.add(new Label(this.getAvgShortestPathText()+"\t"), 0, 6);
        grid.add(new Label(""), 1, 6);
    */
        Polygon polyButton = new Polygon(new double[]{
        0.0, 0.0,
        2.0, 3.0,
        0.0, 6.0});
        
        //btnDensity = new Button(LangConfig.GENERAL_RUN);
        btnDensity = new Button();
        btnDensity.setShape(polyButton);
        btnDensity.toFront();
        btnDensity.setStyle(style);
        btnDensity.setOnAction((event) -> {
            double dblValue = GraphDetails.getDensity(
                      UIInstance.getActiveProjectID()
                    , UIInstance.getActiveProjectTab().getActiveGraphID()
                    , UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex());
            
            // clear and update the grid with the value
            grid.getChildren().remove(getNodeByRowColumnIndex(1, 2, grid));
            grid.add(new Label(FXUtils.FormatDouble(dblValue)+" "), 1, 2);
            
        });
        
        //btnAvgConnections = new Button(LangConfig.GENERAL_RUN);
        btnAvgConnections = new Button();
        btnAvgConnections.setShape(polyButton);
        btnAvgConnections.setStyle(style);
        btnAvgConnections.setOnAction((event) -> {
            double dblValue = GraphDetails.getAvgNumberOfConnections(
                      UIInstance.getActiveProjectID()
                    , UIInstance.getActiveProjectTab().getActiveGraphID(),
                    UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex());
            
            // clear and update the grid with the value
            grid.getChildren().remove(getNodeByRowColumnIndex(1, 3, grid));
            grid.add(new Label(FXUtils.FormatDouble(dblValue)), 1, 3);
        });
        
        //btnAvgCoefficient = new Button(LangConfig.GENERAL_RUN);
        btnAvgCoefficient = new Button();
        btnAvgCoefficient.setShape(polyButton);
        btnAvgCoefficient.setStyle(style);
        btnAvgCoefficient.setOnAction((event) -> {
            double dblValue = GraphDetails.getAvgClusteringCoefficient(
                      UIInstance.getActiveProjectID()
                    , UIInstance.getActiveProjectTab().getActiveGraphID(),
                    UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex());
            
            // clear and update the grid with the value
            grid.getChildren().remove(getNodeByRowColumnIndex(1, 4, grid));
            grid.add(new Label(FXUtils.FormatDouble(dblValue)), 1, 4);
        });
        
        //btnAvgAssortavity = new Button(LangConfig.GENERAL_RUN);
        btnAvgAssortavity = new Button();
        btnAvgAssortavity.setShape(polyButton);
        btnAvgAssortavity.setStyle(style);
        btnAvgAssortavity.setOnAction((event) -> {
            double dblValue = GraphDetails.getAverageAssortativity(
                      UIInstance.getActiveProjectID()
                    , UIInstance.getActiveProjectTab().getActiveGraphID(),
                    UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex());
            
            // clear and update the grid with the value
            grid.getChildren().remove(getNodeByRowColumnIndex(1, 5, grid));
            grid.add(new Label(FXUtils.FormatDouble(dblValue)), 1, 5);
        });
        
        //btnAvgShortestPath = new Button(LangConfig.GENERAL_RUN);
        btnAvgShortestPath = new Button();
        btnAvgShortestPath.setShape(polyButton);
        btnAvgShortestPath.setStyle(style);
        btnAvgShortestPath.setOnAction((event) -> {
            double dblValue = GraphDetails.getAvgShortestPath(
                      UIInstance.getActiveProjectID()
                    , UIInstance.getActiveProjectTab().getActiveGraphID(),
                    UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex());
            
            // clear and update the grid with the value
            grid.getChildren().remove(getNodeByRowColumnIndex(1, 6, grid));
            grid.add(new Label(FXUtils.FormatDouble(dblValue)), 1, 6);
        });
    }    
    
    
    /************************************ METHODS ********************************/
    /**
     *  Method Name     : initialize
     *  Created Date    : 2015-06-26
     *  Description     : Initializes the UI components that are to be displayed on the DetailsContent Tab in the Analysis Screen
     *  Version         : 1.0
     *  @author         : Talat
     * 
     * @param paccParentAccordionPane: Accordion
     * @param paccTitledPane: TitledPane
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-09-13      Talat           Added the buttons for each measure except number of nodes and edges
     *  2015-09-01      Talat           Changed initiateDetailsTab() to the overridden method initialize()
     *  2015-07-20      Talat           Removed the parameter IGraph and replaced the calling of IGraph methods with the GraphAPI calling
     * 
    */
    @Override
    public void initialize (Accordion paccParentAccordionPane, TitledPane paccTitledPane) {
                
        
        grid.setVgap(4);
        grid.setPadding(new Insets(5, 20, 5, 5));
        
        ColumnConstraints clmcnCol1 = new ColumnConstraints();
        //clmcnCol1.setPercentWidth(70);
        clmcnCol1.setHalignment(HPos.LEFT);
        
        ColumnConstraints clmcnCol2 = new ColumnConstraints();
        //clmcnCol2.setPercentWidth(15);
        clmcnCol2.setHalignment(HPos.LEFT);
        
        ColumnConstraints clmcnCol3 = new ColumnConstraints();
        //clmcnCol3.setPercentWidth(15);
        clmcnCol3.setHalignment(HPos.RIGHT);
        
        grid.getColumnConstraints().addAll(clmcnCol1, clmcnCol2, clmcnCol3);
        
        /* These variables are never used
        lblEdgeCount= new Label(this.getVertexCountText()) ;
        lblVertexCount = new Label(this.getEdgeCountText()) ;
        lblDensity = new Label(this.getDensityText()) ;
        lblAvgConnections = new Label(this.getAvgConnectionsText()) ;
        lblAvgCoefficient = new Label(this.getAvgCoefficientText()) ;
        lblAvgAssortavity = new Label(this.getAvgCoefficientText()) ;
        lblAvgShortestPath = new Label(this.getAvgShortestPathText()) ;

        lblEdgeCountValue = new Label() ;
        lblVertexCountValue = new Label() ;
        lblDensityValue = new Label() ;
        lblAvgConnectionsValue = new Label() ;
        lblAvgCoefficientValue = new Label() ;
        lblAvgAssortavityValue = new Label() ;
        lblAvgShortestPathValue = new Label() ;
        */
        grid.add(new Label(this.getVertexCountText()+"\t"), 0, 0);        
        grid.add(new Label(""), 1, 0);
                
        grid.add(new Label(this.getEdgeCountText()+"\t"), 0, 1);        
        grid.add(new Label(""), 1, 1);
        
        grid.add(new Label(this.getDensityText()+"\t"), 0, 2);
        //grid.add(new Label(""), 1, 2);
        grid.add(btnDensity, 2, 2);
        
        grid.add(new Label(this.getAvgConnectionsText()+"\t"), 0, 3);
        //grid.add(new Label(""), 1, 3);
        grid.add(btnAvgConnections, 2, 3);
        
        grid.add(new Label(this.getAvgCoefficientText()+"\t"), 0, 4);
        //grid.add(new Label(""), 1, 4);
        grid.add(btnAvgCoefficient, 2, 4);
        
        grid.add(new Label(this.getAvgAssortavityText()+"\t"), 0, 5);
        //grid.add(new Label(""), 1, 5);
        grid.add(btnAvgAssortavity, 2, 5);
        
        grid.add(new Label(this.getAvgShortestPathText()+"\t"), 0, 6);
        //grid.add(new Label(""), 1, 6);
        grid.add(btnAvgShortestPath, 2, 6);
        
        //disable all the UI elements on initialization
        grid.getChildren().forEach((node) -> {
            node.setDisable(true);
        });
        
        ScrollPane scroll = new ScrollPane();
        scroll.setPrefHeight(paccParentAccordionPane.getHeight());
        scroll.prefWidth(paccParentAccordionPane.getWidth());
        scroll.setContent(grid);
        
        paccTitledPane.setText(this.getTitle());
        paccTitledPane.setContent(scroll);
        paccTitledPane.setExpanded(true);
    }
    
    
    /* METHODS */
    /**
     *  Method Name     : updatePane
     *  Created Date    : 2015-06-26
     *  Description     : Initializes the UI components that are to be displayed on the DetailsContent Tab in the Analysis Screen
     *  Version         : 1.0
     *  @author         : Talat
     * 
     * @param paccParentAccordionPane: Accordion
     * @param paccTitledPane: TitledPane
     * @param pintProjectID : int - Project which contains the graph
     * @param pintGraphID: int - Graph whose parameters are to be reflected in the DetailsContent Pane
     * @param pobjContent : Object - Any object that contains the dependent information
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-09-10      Talat           The Grid is cleared and the labels are rewritten to avoid the visible text overlapping in the pane
     *  2015-09-01      Talat           Changed from updateDetailsTab() to overridden method updatePane
     *  2015-07-20      Talat           Removed the parameter IGraph and replaced the calling of IGraph methods with the GraphAPI calling
     * 
    */
    @Override
    public void updatePane(Accordion paccParentAccordionPane, TitledPane paccTitledPane, int pintProjectID, int pintGraphID, int pintTimeFrameIndex, Object pobjContent) {

        // Cast the object to the DetailsValues
        DetailsValues pValues = (DetailsValues)pobjContent;
        
        // Get the existing Grid from the Scroll Panes
        GridPane grid = (GridPane)((ScrollPane)paccTitledPane.getContent()).getContent();
        
        // Clear the Grid according to what is required
        grid.getChildren().clear();
        
        // Add the labels accordingly
        grid.add(new Label(this.getVertexCountText()+"\t"), 0, 0);        
        grid.add(new Label(Integer.toString(pValues.getVertexCount())), 1, 0);
        
        grid.add(new Label(this.getEdgeCountText()+"\t"), 0, 1);        
        grid.add(new Label(Integer.toString(pValues.getEdgeCount())), 1, 1);
        
        grid.add(new Label(this.getDensityText()+"\t"), 0, 2);
        // grid.add(new Label(Double.toString(pValues.getDensity())), 1, 2);
        grid.add(btnDensity, 2, 2);
        
        grid.add(new Label(this.getAvgConnectionsText()+"\t"), 0, 3);
        // grid.add(new Label(Double.toString(pValues.getAvgConnections())), 1, 3);
        grid.add(btnAvgConnections, 2, 3);
        
        grid.add(new Label(this.getAvgCoefficientText()+"\t"), 0, 4);
        // grid.add(new Label(Double.toString(pValues.getAvgAssortavity())), 1, 4);
        grid.add(btnAvgCoefficient, 2, 4);
        
        grid.add(new Label(this.getAvgAssortavityText()+"\t"), 0, 5);
        // grid.add(new Label(Double.toString(pValues.getAvgCoefficient())), 1, 5);
        grid.add(btnAvgAssortavity, 2, 5);
        
        grid.add(new Label(this.getAvgShortestPathText()+"\t"), 0, 6);
        // grid.add(new Label(Double.toString(pValues.getAvgShortestPath())), 1, 6);
        grid.add(btnAvgShortestPath, 2, 6);
        
        //enable the details UI elements.
        grid.getChildren().forEach((node) -> {
            node.setDisable(false);
        });
                
        // Add the contents of the Grid
        /*
        grid.add(new Label(Integer.toString(pValues.getVertexCount())), 1, 0);
        grid.add(new Label(Integer.toString(pValues.getEdgeCount())), 1, 1);
        grid.add(new Label(Double.toString(pValues.getDensity())), 1, 2);
        grid.add(new Label(Double.toString(pValues.getAvgConnections())), 1, 3);
        grid.add(new Label(Double.toString(pValues.getAvgAssortavity())), 1, 4);
        grid.add(new Label(Double.toString(pValues.getAvgCoefficient())), 1, 5);
        grid.add(new Label(Double.toString(pValues.getAvgShortestPath())), 1, 6);
        */
    }
    
    /**
     *  Method Name     : getNodeByRowColumnIndex
     *  Created Date    : 2017-07-19
     *  Description     : gets the child node of the GridPane corresponding to a particular row & column.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param column : int
     *  @param row  : int
     *  @param gridPane : int
     *  @return : Node
     * 
    */
    
    public Node getNodeByRowColumnIndex (final int column, final int row, GridPane gridPane) {
        Node resultNode = null;
        
        for (Node node : gridPane.getChildren()) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                resultNode = node;
                break;
            }
        }

        return resultNode;
    }
}
