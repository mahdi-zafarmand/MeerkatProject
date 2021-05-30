/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate;


import accordiontab.AccordionTabContents;
import accordiontab.AccordionTabValues;
import ca.aicml.meerkat.api.GraphAPI;
import config.AppConfig;
import config.GraphConfig.GraphType;
import config.LangConfig;
import config.ModeConfig.ModeTypes;
import config.SceneConfig;
import globalstate.UIComponents.DCSlider;
import globalstate.UIComponents.GraphCanvasMode;
import globalstate.UIComponents.GraphCanvasModeFactory;
import graphelements.UIVertex;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import threadtree.IThreadTree;
import threadtree.MeerkatTreeItem;
import threadtree.TreeItemConfig.TreeItemType;
import ui.dialogwindow.InfoDialog;
import globalstate.UIComponents.PanAndZoomPane;
import globalstate.UIComponents.Minimap;
import globalstate.UIComponents.MinimapDelegator;
import globalstate.UIComponents.ModeInformation;
import globalstate.UIComponents.SceneGestures4;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.Event;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.BoxBlur;
import ui.utilities.DeleteGraph;
import ui.utilities.RenameGraph;


/**
 *  Class Name      : GraphTab
 *  Created Date    : 2015-07-21
 *  Description     : UI related Elements for a GraphTab
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-05-25      Talat           Adding minimap show/hide
 *  2016-02-26      Talat           Added fields for strVertexLabelAttr, strVertexToolTipAttr, strEdgeAttr
 *  2016-02-03      Talat           Changed intParentProjectID to intProjectID
 *  2015-01-28      Talat           Removing old and unwanted code. Check previously dated version for old code
 *  2015-01-28      Talat           Added the field blnShowToolTip
 *  2015-10-22      Talat           Constructor GraphTab() :
 *                                  Adding Code related to making the Canvas in 
 *                                  JavaFX and removing the D3 related code
 * 
*/

class DragContext2 {

            double mouseAnchorX;
            double mouseAnchorY;

            double translateAnchorX;
            double translateAnchorY;

}
public class GraphTab {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    private int intProjectID;         // Every Graph has a parent Project
    private int intGraphID;                 // ID used to identify a graph. Will change from one instance of the applicaiton to another
    private String strGraphTabTitle;           // Used to display on the tab and while saving the graph
    private String strGraphFilePath;           // The path of the file where this graph would be saved
    private String strRawDataFilePath;         // Each graph, may or may not be derived from a Raw Data File
    private String strDisplayGraphFilePath ;        
    private String strGraphOutput;             // The place where the Output for this graph is saved for it to be displayed
    
    
    private boolean blnIsVisualizationEnabled ;
    private BooleanProperty blnprpIsClickedOnCanvas  ;
    // SelectionModel selectionModel = new SelectionModel();
    
    /* UI RELATED COMPONENTS */
    private Tab uitabGraph;
    
    
    private ScrollPane scrlCanvas ;
    private StackPane stkCanvasMinimapHolder ; // Holder for the GraphCanvas and Minimap
    //private Parent scrlZoomPane ; // The actual pane on which the graph is displayed as Zoom Pane
    
    //private final AnchorPane anchZoomPane  = new AnchorPane(); // One which has the groupl elements of a graph
    private PanAndZoomPane anchZoomPane = new PanAndZoomPane();
    private PanAndZoomPane[] arrZoomPane;
    
    
    private StackPane stkMiniMap ; // Holder for the minimap
    private Shape shpMiniMapMasking ;
    
    //private Slider sldrTimeFrame ;
    private DCSlider sldrDCSlider;
    
    //private int intCurrentTimeFrameIndex = 0;
    private SimpleIntegerProperty intCurrentTimeFrameIndexProperty = new SimpleIntegerProperty(0);
    
    
    private int intTotalTimeFrames = 0;
    private String [] arrstrTimeFrames ;
    private MeerkatTreeItem<String> treeGraph;
    final double SCALE_DELTA = SceneConfig.CANVAS_SCROLL_SCALEFACTOR;
    
    private String strBackgroundColor = SceneConfig.CANVAS_BACKGROUND_COLOR ;
    
    // TimeframeIndex --> graphCanvas
    private Map<Integer,GraphCanvas> graphCanvas;
    
    private double dblMousePointX ;
    private double dblMousePointY ;
    
    private boolean blnShowToolTip ; // A status to show the tooltip or not on hovering
    private boolean blnIsMinimapHidden ; // Status to show if the Map is hidden or not
    private boolean blnIsModeInfoHidden ; // Status to show if the Mode Information is hidden or not
    private boolean blnShowVertexLabel ; // Boolean to set the visibility status of Vertex Labels in the current graph
    private boolean blnShowEdgeLabel ;// Boolean to set the visibility status of Edge Labels in the current graph
    
    private boolean blnArePredictedEdgesShown;
    private boolean blnAreEdgesShown;;
    
    private String strVertexLabelAttr = "" ;
    private String strVertexTooltipAttr = "" ;
    private String strEdgeLabelAttr = "" ;
    
    private String strBackgroundImage ;
    
    /* mapping from timeframe index to AccordionTabValues */
    private Map<Integer,AccordionTabValues>  accordionValues;
    
    /* USER SELECTED UI ATTRIBUTES */
    private boolean blnFishEyeLens ;
    private boolean blnMagnifyLens ;
    
    
        
    double dblSceneSourceX, dblSceneSourceY;
    double dblTranslateX, dblTranslateY;
    double dblInternalOffsetX, dblInternalOffsetY ;
    double dblSceneDestinationX, dblSceneDestinationY ;
    
    // Dynamic Graph can have multiple time frames and the index would be changed accordingly. For Static Graph, this value is always 0
    private double dblOverallScaleFactor = 1 ;
    
    final DragContext dragContext = new DragContext();
    final MouseContext mouseContext = new MouseContext();
    
    private DragContext2 nodeDragContext = new DragContext2();
   
    private boolean DCMiningStatus = false;
    // map of Timeframe -> Status (true, false)
    private Map<Integer, Boolean> comMiningStatus;
    private Map<Integer, Boolean> linkPredictionStatus;
    
    private Minimap minimap;
    private ModeInformation modeInfo ;
    private MinimapDelegator minimapDeligator;// = new MinimapDelegator(minimap, anchZoomPane, scrlCanvas);
    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(1.0d);
    private final DoubleProperty deltaY = new SimpleDoubleProperty(0.0d);
    
    /*
    mapGlobalCommunityColor maintains a map for this graph's community names to color. The invariant is that
    if with in a graph, a community with same name, irrespective of time frames, will have same color.
    Once the list of colors is assigned, the list is cycled again to assign colors.
    Earlier, the logic was to maintain this Map as global for entire Meerkat application.
    */
    //private Map<String, Color> mapGlobalCommunityColor = new HashMap<>();
    private SimpleIntegerProperty intNextColorIndexProperty = new SimpleIntegerProperty(0);
    
    /*
    GraphCanvasMode factory - get an object from this factory to set the graphCanvas mode interaction methods, its cursor mode etc
    */
    private GraphCanvasModeFactory graphCanvasModeFactory = new GraphCanvasModeFactory();
    private SceneGestures4 sceneGestures;
    private GraphCanvasMode currentGraphCanvasMode;
    
    private StackPane stkpaneMiningProgress = new StackPane();
    
    private Button btnPreviousTimeFrame ;
    private Button btnNextTimeFrame ;
    private HBox timeFrameChangeButtonBox;
    //to check if the toolTip for all vertices has been created.
    private boolean toolTipAdded = false;
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
//    public Map<String, Color> getMapGlobalCommunityColor(){
//        return mapGlobalCommunityColor;
//    }
    public SimpleIntegerProperty getIntNextColorIndexProperty(){
        return intNextColorIndexProperty;
    }
    public int getIntNextColorIndex(){
        return intNextColorIndexProperty.get();
    }
    public void IncrementIntNextColorIndex(){
        intNextColorIndexProperty.set(intNextColorIndexProperty.get()+1);
    }
    public void setIntNextColorIndex(int index){
        intNextColorIndexProperty.set(0);
    }
    
    public MinimapDelegator getMinimapDelegator(){
        return minimapDeligator;
    }
    
    public boolean getIsVisualizationEnabled() {
        return blnIsVisualizationEnabled ;
    }
    public void setIsVisualizationEnabled(boolean pblnValue) {
        this.blnIsVisualizationEnabled = pblnValue ;
    }
    
    public double getOverallScaledFactor() {
        return this.dblOverallScaleFactor ;
    }
    public boolean IsMinimapHidden() {
        return this.blnIsMinimapHidden;
    }
    public void setIsMinimapHidden(boolean pblnValue) {
        this.blnIsMinimapHidden = pblnValue ;
    }
    
    public boolean getAreEdgesShown() {
        return this.blnAreEdgesShown ;
    }
    public void setAreEdgesShown(boolean pblnValue) {
        this.blnAreEdgesShown = pblnValue;
    }
    
    public boolean getArePredictedEdgesShown() {
        return this.blnArePredictedEdgesShown;
    }
    public void setArePredictedEdgesShown(boolean pblnValue) {
        this.blnArePredictedEdgesShown = pblnValue; 
    }
    
    public boolean IsVertexLabelShown() {
        return blnShowVertexLabel ;
    }
    public void setIsVertexLabelShown(boolean pblnValue) {
        this.blnShowVertexLabel = pblnValue ;
    }
    
    public boolean IsEdgeLabelShown() {
        return blnShowEdgeLabel ;
    }
    public void setIsEdgeLabelShown(boolean pblnValue) {
        this.blnShowEdgeLabel = pblnValue ;
    }
    
    public boolean IsVertexTooltipShown() {
        return blnShowToolTip ;
    }
    public void setIsVertexTooltipShown(boolean pblnValue) {
        this.blnShowToolTip = pblnValue ;
    }
    
    public void setTimeFrameIndex (int pintTimeFrameIndex) {
        this.intCurrentTimeFrameIndexProperty.setValue( pintTimeFrameIndex );
    }
    public int getTimeFrameIndex() {
        return this.intCurrentTimeFrameIndexProperty.getValue() ;
    }
    
    
    public int getTotalTimeFrames() {
        return this.intTotalTimeFrames ;
    }
    private void setTimeFrames() {
        arrstrTimeFrames = GraphAPI.getTimeFrameNames(intProjectID, intGraphID);
        
        intTotalTimeFrames = arrstrTimeFrames.length ;
        
        //System.out.println("arrstrTimeFrames length = " + intTotalTimeFrames + ", " + arrstrTimeFrames);
        /*
        for(String tfName : arrstrTimeFrames){
            System.out.println("\t\t\t\t\t timeframe name = " + tfName);
        }
         */
    }
    
    public String[] getTimeFrames() {
        return arrstrTimeFrames;
        
    }
    
    public DCSlider getSlider() {
        //return sldrTimeFrame;
        return sldrDCSlider;
    }
    
    // GraphID
    public int getGraphID() {
        return this.intGraphID;
    }
    private void setGraphID(int pintGraphID) {        
        this.intGraphID = pintGraphID;
        this.strDisplayGraphFilePath = AppConfig.DIR_DISPLAYGRAPH_HTML+"graph"+this.intGraphID+AppConfig.EXTENSION_DISPLAY_OUTPUT;
    }
    private String getGraphIDAsString() {
        return Integer.toString(intGraphID);
    }    
    
    // ParentProjectID
    public void setProjectID (int pintProjectID) {
        this.intProjectID = pintProjectID;
    }
    
    public int getProjectID () {
        return this.intProjectID;
    }
    
    // GraphTabTitle
    public String getGraphTabTitle () {
        return this.strGraphTabTitle;
    }
    private void setGraphTabTitle (String pstrIconPath) {
        this.strGraphTabTitle = pstrIconPath;
    }
        
    // GraphOutputFile
    public String getGraphOutputFile () {
        return this.strGraphOutput;
    }
    
    public void setGraphOutputFile (String pstrFileOutput) {
        this.strGraphOutput = pstrFileOutput;
    }
    
    // Graph File
    public String getGraphFile() {
        return this.strGraphFilePath;
    }
    
    // RawDataFile
    public String getRawDataFile() {
        return this.strRawDataFilePath;
    }
    
    // UI Related Components
    public Point2D getMousePointerPosition(){
        return new Point2D(dblMousePointX, dblMousePointY);
    }
 
    
    // UITab
    public Tab getUITab() {
        return uitabGraph;
    }
    
    // Get the Drawable Pane
    public Node getDrawingPane() {
        return (Node)anchZoomPane;
    }
    
    // TreeGraph
    public MeerkatTreeItem<String> getTreeItemGraph() {
        return this.treeGraph;
    }
        
    public AccordionTabValues getAccordionTabValues(){
        return this.accordionValues.get(intCurrentTimeFrameIndexProperty.getValue());
    }
    
    //method returns AccordianTabValues Object for the given TimeFrame
     public AccordionTabValues getAccordionTabValues(int TimeFrameIndex){
        return this.accordionValues.get(TimeFrameIndex);
    }

    public String getColorString() {        
        if (this.strBackgroundColor.isEmpty()) {
            this.strBackgroundColor = SceneConfig.CANVAS_BACKGROUND_TRANSPARENT ;
        }        
        return this.strBackgroundColor ;
    }
    
    
    // Graph Canvas for current time frame
    public GraphCanvas getGraphCanvas () {
        return this.graphCanvas.get(intCurrentTimeFrameIndexProperty.getValue());
    }
    public GraphCanvas getGraphCanvas (int pintTimeFrame) {
        return this.graphCanvas.get(pintTimeFrame);
    }
    // Graph Canvas map
    public Map<Integer, GraphCanvas> getGraphCanvasMap () {
        return this.graphCanvas;
    }
    // get PanAndZoomPane for current time frame
    public PanAndZoomPane getZoomPane(){
        return this.arrZoomPane[intCurrentTimeFrameIndexProperty.getValue()];
    }
    private PanAndZoomPane[] getArrZoomPane(){
        return this.arrZoomPane;
    }
    
    public Double getZoomPaneClickX(){
        return this.arrZoomPane[intCurrentTimeFrameIndexProperty.getValue()].getMouseClickX();
    }
    public Double getZoomPaneClickY(){
        return this.arrZoomPane[intCurrentTimeFrameIndexProperty.getValue()].getMouseClickY();
    }
    
    
    // Fisheye Lens
    public boolean IsFisheyeLens () {
        return this.blnFishEyeLens;
    }
    public void setFisheyeLens(boolean pblnFishEyeLensSelected) {
        this.blnFishEyeLens = pblnFishEyeLensSelected;
    }
    
    // Magnify Lens
    public boolean IsMagnifyLens() {
        return this.blnMagnifyLens;
    }
    public void setMagnifyLens(boolean pblnMagnifyLensSelected) {
        this.blnMagnifyLens = pblnMagnifyLensSelected;
    }
    
    public String getVertexLabelAttr() {
        return this.strVertexLabelAttr ;
    }
    public void setVertexLabelAttr(String pstrValue) {
        this.strVertexLabelAttr = pstrValue ;
    }
    
    public String getVertexTooltipAttr () {
        return this.strVertexTooltipAttr ;
    }
    public void setVertexTooltipAttr(String pstrValue) {
        this.strVertexTooltipAttr = pstrValue ;
    }
    
    public String getEdgeLabelAttr() {
        return this.strEdgeLabelAttr ;
    }
    public void setEdgeLabelAttr(String pstrValue) {
        this.strEdgeLabelAttr = pstrValue ;
    }
    
    public void setDCommMiningStatus(Boolean status){
        this.DCMiningStatus=status;
    }
    public boolean getDCommMiningStatus(){
        return this.DCMiningStatus;
    }
    
    public void setComMiningStatus(Boolean status, Integer tf){
        this.comMiningStatus.put(tf, status);
    }
    public boolean getCommMiningStatus(Integer tf){
        return this.comMiningStatus.get(tf);
    }
    
    public void setLinkPredictionStatus(Boolean status, Integer tf) {
        this.linkPredictionStatus.put(tf, status);
    }
    public boolean getLinkPredictionStatus(Integer tf) {
        return this.linkPredictionStatus.get(tf);
    }
    
    public void renameGraphTab(String pstrGraphTitle){
        //rename Tab
        setGraphTabTitle(pstrGraphTitle);
        this.getUITab().setText(getGraphTabTitle());
        
        //rename TreeViewItem
        this.treeGraph.setTreeItemValue(getGraphTabTitle());
        
    }
    
    public GraphCanvasMode getCurrentGraphCanvasMode(){
        return currentGraphCanvasMode;
    }
    
    public HBox getTimeFrameChangeButtonBox(){
        return this.timeFrameChangeButtonBox;
    }
    
    
       
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    
    /**
     *  Constructor Name: GraphTab()
     *  Created Date    : 2015-07-21
     *  Description     : Constructor to initiate the components of a GraphTab
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pGraphType : GraphType (enum)
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public GraphTab(int pintProjectID, int pintGraphID, GraphType pGraphType) {
        setProjectID(pintProjectID);
        setGraphID(pintGraphID);    
        // setGraphType(pGraphType);
        // setGraphTabTitle("New Graph ("+this.intGraphID+")");
        setGraphTabTitle(GraphAPI.getGraphTitle(pintProjectID, pintGraphID));
        // New Edit : Set the total time frames
        setTimeFrames();
        setTimeFrameIndex(0);
        accordionValues = new HashMap<>();
        
        initiateUIComponents();
    }
    
    
    /**
     *  Constructor Name: GraphTab()
     *  Created Date    : 2015-07-21
     *  Description     : Constructor to initiate the components of a GraphTab
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pGraphType : GraphType (enum)
     *  @param pstrGraphTitle : String
     *  @param pstrGraphFilePath : String
     *  @param pstrGraphRawDataFilePath : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public GraphTab(int pintProjectID, 
            int pintGraphID, 
            GraphType pGraphType, 
            String pstrGraphTitle,
            String pstrGraphFilePath, 
            String pstrGraphRawDataFilePath) {
        
        setProjectID(pintProjectID);
        setGraphID(pintGraphID);
        setGraphTabTitle(pstrGraphTitle);    
        this.strGraphFilePath = pstrGraphFilePath;
        this.strRawDataFilePath = pstrGraphRawDataFilePath;
        
        // Set the total time frames
        setTimeFrames();
        setTimeFrameIndex(0);
        accordionValues = new HashMap<>();
        
        initiateUIComponents();
        
        // #Debug
        System.out.println("GraphTab.GraphTab(): Total Time Frames = "+this.intTotalTimeFrames);
        // #EndDebug
    }
    
    public GraphTab(int pintProjectID, 
            int pintGraphID, 
            int pCurrentTimeFrameIndex,
            GraphType pGraphType, 
            String pstrGraphTitle,
            String pstrGraphFilePath, 
            String pstrGraphRawDataFilePath) {
        
        setProjectID(pintProjectID);
        setGraphID(pintGraphID);
        setGraphTabTitle(pstrGraphTitle);    
        this.strGraphFilePath = pstrGraphFilePath;
        this.strRawDataFilePath = pstrGraphRawDataFilePath;
        
        // Set the total time frames
        setTimeFrames();

        // Set the MapCommunityColor
        // this.mapGlobalCommunityColor = pMapGlobalCommunityColor;
        
        
        accordionValues = new HashMap<>();
        
        initiateUIComponents(pCurrentTimeFrameIndex);
        
        // #Debug
        System.out.println();
        System.out.println("\t\t\t\t\tGraphTab.GraphTab(): Total Time Frames = "+this.intTotalTimeFrames + ", currentTimeFrameIndex = " +  pCurrentTimeFrameIndex);
        // #EndDebug
        
    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
        
    
     private void initiateUIComponents(int pcurrentTimeFrameIndex) {
         //this.intCurrentTimeFrameIndex = pcurrentTimeFrameIndex;
         this.intCurrentTimeFrameIndexProperty.setValue(pcurrentTimeFrameIndex);
         initiateUIComponents();
     }
    
    /**
     *  Method Name     : initiateUIComponents
     *  Created Date    : 2015-08-04
     *  Description     : Initiates the UI Components related to a graph
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
     /* old method code
    private void initiateUIComponents() {
        
        blnShowVertexLabel = SceneConfig.VERTEX_LABEL_VISIBLE_DEFAULT ;
        blnShowToolTip = SceneConfig.VERTEX_TOOLTIP_VISIBLE_DEFAULT ;
        blnprpIsClickedOnCanvas = new SimpleBooleanProperty(false);
        blnIsVisualizationEnabled = true ;
        sldrDCSlider = new DCSlider(intCurrentTimeFrameIndexProperty, arrstrTimeFrames, this);
        //sldrTimeFrame = new Slider();
        System.out.println("GraphTab.initiateUIComponents(): ProjectID: "+intProjectID+"\tGraphID: "+intGraphID);
        
        treeGraph = new MeerkatTreeItem(TreeItemType.GRAPHTITLE, intGraphID); // Initializing the tree items in the graphtab

        try {
            MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
            
            // Assign the dimensions of the TabPanes in case if they have not been set yet
            if (SceneConfig.GRAPHCANVAS_WIDTH == 0 || 
                    SceneConfig.GRAPHCANVAS_WIDTH == 0) {                
                SceneConfig.GRAPHCANVAS_WIDTH = UIInstance.getController()
                        .getProjectTabPane().getWidth();
                SceneConfig.GRAPHCANVAS_HEIGHT = UIInstance.getController()
                        .getProjectTabPane().getHeight();                
                // System.out.println("GraphTab.GraphTab(): Canvas Width: "+SceneConfig.GRAPHCANVAS_WIDTH+"\tHeight: "+SceneConfig.GRAPHCANVAS_HEIGHT);
            }
            
            // Creating a Canvas
            graphCanvas = new HashMap<>();
            for (int i = 0 ; i < arrstrTimeFrames.length; i++) {
                graphCanvas.put(i, new GraphCanvas(intProjectID, 
                        intGraphID, 
                        i, 
                        this.strVertexLabelAttr, 
                        this.strVertexTooltipAttr, 
                        this.strEdgeLabelAttr));
            }
           
            // Create the Children to be attached to the Stack Pane
            
            // Child 1 - Creating a zoom pane
            //scrlZoomPane = createZoomPane(getGraphCanvas()); // A scroll pane is returned by createZoomPane
            scrlZoomPane = createZoomPane_exp2(getGraphCanvas()); // A scroll pane is returned by createZoomPane
            // Child 2 - Initializing the MiniMap
            //stkMiniMap = new StackPane();
            
            // #COMMENTED: Trying to add the minimap after the graph is displayed
            // DIDNT WORK
            // Add the Minimap so that the zoom pane can now be released to get it attached on the actual canvas
            //stkMiniMap = addMiniMap(scrlZoomPane);
            //addLayerToMiniMap();              
            
            
            // Creating an stack pane to attach it to the UITab
            stkCanvasMinimapHolder = new StackPane();
            
            
            // Now add the zoom pane to the Minimap Holder
            stkCanvasMinimapHolder.getChildren().add(scrlZoomPane);
            StackPane.setAlignment(scrlZoomPane, Pos.CENTER);
                        
            // Adding the Minimap to the MiniMapHolder StackPane
            //stkCanvasMinimapHolder.getChildren().add(stkMiniMap);
            //StackPane.setAlignment(stkMiniMap, Pos.TOP_LEFT);
            //StackPane.setMargin(stkMiniMap, new Insets(5, 0, 0, 5));
            minimap.updateMiniMap();
            stkCanvasMinimapHolder.getChildren().add(minimap.getStkPaneMinimap());
              StackPane.setAlignment(minimap.getStkPaneMinimap(), Pos.TOP_LEFT);
              StackPane.setMargin(minimap.getStkPaneMinimap(), new Insets(5, 0, 0, 5));    
            // #Debug
            // System.out.println("GraphTab.initiateUIComponents(): ID = "+this.intGraphID);

            blnShowToolTip = AppConfig.IsTooltipEnabled ;

            this.uitabGraph = new Tab(getGraphIDAsString());        
            this.uitabGraph.setId(getGraphIDAsString());
            this.uitabGraph.setText(getGraphTabTitle());
            this.uitabGraph.setContent(this.stkCanvasMinimapHolder);
            // this.uitabGraph.setContent(this.zoomPane);
            this.uitabGraph.setClosable(true);   

            // Initiate the AccordionValues for all timeframe
            int intkeepTF = intCurrentTimeFrameIndexProperty.getValue();
            for (int i = 0 ; i < arrstrTimeFrames.length; i++) {
                intCurrentTimeFrameIndexProperty.setValue(i);
                updateAccordionValuesOnLoading();
            }
            intCurrentTimeFrameIndexProperty.setValue(intkeepTF);

            // Initiate the Tree related stuff
            updateTree();
            
            if (intTotalTimeFrames > 1) {
                sldrDCSlider.updateDCSlider() ;
            }            
            
            UIInstance.UpdateUI();
        } catch (Exception ex) {
            System.out.println("GraphTab.initiateUIComponents(): EXCEPTION: ");
            ex.printStackTrace(); ;
        }
    
    }
    */
    
    private void initiateUIComponents() {
        
        blnShowVertexLabel = SceneConfig.VERTEX_LABEL_VISIBLE_DEFAULT ;
        blnShowToolTip = SceneConfig.VERTEX_TOOLTIP_VISIBLE_DEFAULT ;
        blnprpIsClickedOnCanvas = new SimpleBooleanProperty(false);
        blnIsVisualizationEnabled = true ;
        sldrDCSlider = new DCSlider(intCurrentTimeFrameIndexProperty, arrstrTimeFrames, this);
        
        System.out.println("GraphTab.initiateUIComponents(): ProjectID: "+intProjectID+"\tGraphID: "+intGraphID);

        try {
            MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
            
            // Assign the dimensions of the TabPanes in case if they have not been set yet
            if (SceneConfig.GRAPHCANVAS_WIDTH == 0 || 
                    SceneConfig.GRAPHCANVAS_WIDTH == 0) {                
                SceneConfig.GRAPHCANVAS_WIDTH = UIInstance.getController()
                        .getProjectTabPane().getWidth();
                SceneConfig.GRAPHCANVAS_HEIGHT = UIInstance.getController()
                        .getProjectTabPane().getHeight();                
            }
            
            // Setting the community mining status as false for all timeframes initially.
            comMiningStatus = new HashMap<>();
            for (int i = 0 ; i < arrstrTimeFrames.length; i++) {
                setComMiningStatus(Boolean.FALSE, i);
            }
            
            // Setting the link prediction status as false for all timeframes initially
            linkPredictionStatus = new HashMap<>();
            for (int i = 0 ; i < arrstrTimeFrames.length; i++) {
                setLinkPredictionStatus(Boolean.FALSE, i);
            }
            
            // Creating a Canvas Map, one entry for each time frame
            graphCanvas = new HashMap<>();
            for (int i = 0 ; i < arrstrTimeFrames.length; i++) {
                System.out.println("\t\t\t----------------------\t\t\t Plotting in GraphCanvas TimeFrame no = " + i);
                graphCanvas.put(i, new GraphCanvas(intProjectID, 
                        intGraphID, 
                        i, 
                        this.strVertexLabelAttr, 
                        this.strVertexTooltipAttr, 
                        this.strEdgeLabelAttr));
            }
            setArePredictedEdgesShown(false);
            setAreEdgesShown(true);
            //create all zoomPanes here. one corresponding to each time frame
            arrZoomPane = new PanAndZoomPane[arrstrTimeFrames.length];
            for (int i = 0 ; i < arrstrTimeFrames.length; i++) {
                arrZoomPane[i] = new PanAndZoomPane();
                arrZoomPane[i].getChildren().add(getGraphCanvas(i));
            }
           //initialize minimpap, minimapDeligator, scrlCanvas 
           minimap = new Minimap(getArrZoomPane(), intCurrentTimeFrameIndexProperty);
           
           scrlCanvas = new ScrollPane();
           minimapDeligator = new MinimapDelegator(minimap, getArrZoomPane(), scrlCanvas, intCurrentTimeFrameIndexProperty);
           
           // Initialize Mode Information
           modeInfo = new ModeInformation();
           
            // Create the Children to be attached to the Stack Pane. This stackPane has scrlCanvas and minimap
            
            // Child 1 - scrollpane
            // scrollPane - attach all listeners to it, initialize minimap, minimpapDelegator. ScrollPane will have one zoomPane for each tf
            // i.e. activate the zoompane for current time frame
            scrlCanvas.setContent(getZoomPane());
            createScrollPaneBehaviour(); 
            // Creating an stack pane to attach it to the UITab
            
            
            stkCanvasMinimapHolder = new StackPane();// Child 2 MiniMap
            
            
            
            // Now add the scrlPane - zoom pane to the Minimap Holder
            stkCanvasMinimapHolder.getChildren().add(scrlCanvas);
            StackPane.setAlignment(scrlCanvas, Pos.CENTER);
                        
            // Adding the Minimap to the MiniMapHolder StackPane
            minimapDeligator.updateMinimap();
            minimapDeligator.changeMinimapMasking();
            stkCanvasMinimapHolder.getChildren().add(minimap.getStkPaneMinimap());
            StackPane.setAlignment(minimap.getStkPaneMinimap(), Pos.TOP_LEFT);
            StackPane.setMargin(minimap.getStkPaneMinimap(), new Insets(5, 0, 0, 5));
            
            // Adding the Mode Information Stack
            stkCanvasMinimapHolder.getChildren().add(modeInfo.getStackModeInformation());
            StackPane.setAlignment(modeInfo.getStackModeInformation(), Pos.BOTTOM_LEFT);
            StackPane.setMargin(modeInfo.getStackModeInformation(), new Insets(5, 0, 0, 5));

            blnShowToolTip = AppConfig.IsTooltipEnabled ;

            this.uitabGraph = new Tab(getGraphIDAsString());        
            this.uitabGraph.setId(getGraphIDAsString());
            this.uitabGraph.setText(getGraphTabTitle());
            this.uitabGraph.setContent(this.stkCanvasMinimapHolder);
            
            /* Make the graph tab unclosable. The graph tab can be closed by right click on its tab or tree view or when its project is closed  */
            //this.uitabGraph.setClosable(true);   

            // Initiate the AccordionValues for all timeframe
            int intkeepTF = intCurrentTimeFrameIndexProperty.getValue();
            for (int i = 0 ; i < arrstrTimeFrames.length; i++) {
                intCurrentTimeFrameIndexProperty.setValue(i);
                updateAccordionValuesOnLoading();
            }
            intCurrentTimeFrameIndexProperty.setValue(intkeepTF);
            //update canvas for communities here for the intkeepTF time frame because this time frame will be shown
            //in canvas first. If graph has community info (either in Graph input file or its Parent graph from which it has been extracted)
            //, then that must be shown on canvas. For other time frames, when changing time frames - updateCanvasWithCommunities is called in the listener.
            updateCanvasWithCommunitiesOnLoading();
            // Initiate the Tree related stuff
            updateTree();
            // set context menu of the tab same as that of its TreeItem
            this.uitabGraph.setContextMenu(new GraphTabContextMenu(this).getGraphTabContextMenu());
            if (intTotalTimeFrames > 1) {
                sldrDCSlider.updateDCSlider() ;
            }            
            
            UIInstance.UpdateUI();
            
            this.addTimeFrameChangeButtons();
        } catch (Exception ex) {
            System.out.println("GraphTab.initiateUIComponents(): EXCEPTION: ");
            ex.printStackTrace(); ;
        }
    
    }
    
    public ModeInformation getModeInformationUI(){
        return this.modeInfo;
    }
    
    private void addTimeFrameChangeButtons(){
        //add these buttons only if noOfTimeFrames of this graphTab more than 1
        timeFrameChangeButtonBox = new HBox();
        btnPreviousTimeFrame = new Button("<<");
        btnNextTimeFrame = new Button(">>");
        if(this.getTotalTimeFrames()>1){
            timeFrameChangeButtonBox = new HBox();
            //create buttons
            //attach listeners
            //add these buttons on the UI in analysis controller

            

            btnPreviousTimeFrame.setOnAction( e -> {
                this.getSlider().moveToPreviousTimeFrame();
            });

            
            btnNextTimeFrame.setOnAction( e -> {
                this.getSlider().moveToNextTimeFrame();
            });
            timeFrameChangeButtonBox.setSpacing(10);
            timeFrameChangeButtonBox.setPadding(new Insets(10,10,10,10));
            timeFrameChangeButtonBox.setAlignment(Pos.CENTER);
            timeFrameChangeButtonBox.getChildren().add(btnPreviousTimeFrame);
            timeFrameChangeButtonBox.getChildren().add(btnNextTimeFrame);
        }
    
    }
    
    /**
     *  Method Name     : enableVisualization()
     *  Created Date    : 2016-08-08
     *  Description     : Enables the Visualization of the Canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void enableVisualization() {
            
        // If the visualization is already enabled, show an Information Dialog
        if(blnIsVisualizationEnabled) {
            InfoDialog.Display(LangConfig.INFO_CANVAS_VISUALALREADYENABLED, SceneConfig.INFO_TIMEOUT);
        } else {
            uitabGraph.setContent(this.stkCanvasMinimapHolder);
        }
        this.setIsVisualizationEnabled(true);
    }
    
    /**
     *  Method Name     : disableVisualization()
     *  Created Date    : 2016-08-08
     *  Description     : Disables the Visualization of the Canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void disableVisualization() {
        // If the visualization is already disabled, show an Information Dialog
        if(!blnIsVisualizationEnabled) {
            InfoDialog.Display(LangConfig.INFO_CANVAS_VISUALALREADYDISABLED, SceneConfig.INFO_TIMEOUT);
        } else {            
            Button btnEnableVisualization = new Button();
            btnEnableVisualization.setText(LangConfig.GENERAL_ENABLEVISUALIZATION);
            btnEnableVisualization.setOnAction((e) -> {
                enableVisualization();
            });
            StackPane stkEnableVisualization = new StackPane(btnEnableVisualization);
            StackPane.setAlignment(stkEnableVisualization, Pos.CENTER);
            uitabGraph.setContent(stkEnableVisualization);
        }
        this.setIsVisualizationEnabled(false);
    }
    
    public void changeGraphCanvas(int pintProjectID, 
            int pintGraphID, 
            int pintCurrentTimeFrameIndex) {

        
        // Create the UI instance to get the handles to all the UI components
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        // Update the Details pane         
        AccordionTabContents accordionTab = 
                AccordionTabContents.getAccordionTabInstance();
        accordionTab.updateAccordionTabs(
                  UIInstance.getController().getAccordion()
                , UIInstance.getController().getDetails()
                , UIInstance.getController().getLayouts()
                , UIInstance.getController().getFilters()
                , UIInstance.getController().getStatistics()
                , UIInstance.getController().getCommunities()
                , this.intProjectID
                , intGraphID
                , this.intCurrentTimeFrameIndexProperty.getValue()
                , this.getAccordionTabValues());
        
        updateCanvasWithCommunitiesOnLoading();
        
        // Updating the zoom pane
        updateZoomPane(getGraphCanvas()); // A scroll pane is returned by createZoomPane
        
        //minimap.updateMiniMap();
        minimapDeligator.updateMinimap();
        minimapDeligator.changeMinimapMasking();
        
        UIInstance.UpdateUI();
    }
    
    /**
     *  Method Name     : removeTreeItem
     *  Created Date    : 2015-09-10
     *  Description     : Removes the tree view for the specific graph whose tab is closed
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void removeTreeItem() {
        /*
        Meerkat UIInstance = Meerkat.getMeerkatApplication();        
        TreeItem treeitemClosedItem = (TreeItem)UIInstance.getController().getTreeView().getSelectionModel().getSelectedItem();        
        boolean blnRemove = treeitemClosedItem.getParent().getChildren().remove(treeitemClosedItem);
        */
        // The following code snippet is the faster and easier way to remove the treeitem of a graph from the treeview and equal to the above commented code snippet
        treeGraph.getParent().getChildren().remove(treeGraph);
        
    }
    
    /**
     *  Method Name     : updateTree()
     *  Created Date    : 2015-08-04
     *  Description     : Updates the UI based tree with the elements in the graph
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void updateTree() {
        treeGraph = new MeerkatTreeItem(getGraphTabTitle(), TreeItemType.GRAPHTITLE, intGraphID);
        /**
         * Graph Tree not displayed any more
         */
        IThreadTree treeImplementation = null;    
//        switch(this.enmGraphType) {
//            case GRAPH :
//                treeImplementation = new TreeGraphForm();
//                break;
//            case TEXTUALGRAPH:
//                treeImplementation = new TreeTextualForm();
//                break;               
//        }
        //treeImplementation = new TreeGraphForm();
        //treeImplementation.addGraph(getTreeItemGraph(), intProjectID, getGraphID());
    }
    
    /**
     *  Method Name     : updateCanvas()
     *  Created Date    : 2015-09-01
     *  Description     : Updates the UI canvas - area where the graph is displayed
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrOutputFilePath : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateCanvas(String pstrOutputFilePath) {
        
        // String strCallingMethod = "GraphTab.updateCanvas(): ";
        // System.out.println(strCallingMethod+"JSON to be fetched: "+pstrOutputFilePath);
        String strInputHTMLFilePath = AppConfig.DIR_HTMLFILES+"MainGraph.html";
        
        final URL urlLoadMainGraph = this.getClass().getResource(strInputHTMLFilePath); 
    }
    

    
    /**
     *  Method Name     : updateAccordionValuesOnLoading()
     *  Created Date    : 2015-09-09
     *  Description     : Updates the Accordion Pane values that shows the details, statistics, communities, filters and layouts, on graph load.
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateAccordionValuesOnLoading() {
        AccordionTabValues actv = new AccordionTabValues(this.intProjectID, 
                    this.intGraphID, 
                    this.intCurrentTimeFrameIndexProperty.getValue());

        actv.updateAccordionTabValuesOnLoading(this.intProjectID,
                this.intGraphID, 
                this.intCurrentTimeFrameIndexProperty.getValue(), getGraphCanvas());//, getMapGlobalCommunityColor(), getIntNextColorIndexProperty());

        this.accordionValues.put(this.intCurrentTimeFrameIndexProperty.getValue(), actv);
    }
    
     /**
     *  Method Name     : updateAccordionValues()
     *  Created Date    : 2017-07-04
     *  Description     : Updates the Accordion Pane values and corresponding Tab contents that shows the details, statistics, communities, 
     *                    filters and layouts for current time frame.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateAccordionValues() {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        getAccordionTabValues(this.intCurrentTimeFrameIndexProperty.getValue()).
                updateAccordionTabValues(this.intProjectID, this.intGraphID, this.intCurrentTimeFrameIndexProperty.getValue());
        
        AccordionTabContents accordionTab = AccordionTabContents.getAccordionTabInstance();
                accordionTab.updateAccordionTabs(
                      UIInstance.getController().getAccordion()
                    , UIInstance.getController().getDetails()
                    , UIInstance.getController().getLayouts()
                    , UIInstance.getController().getFilters()
                    , UIInstance.getController().getStatistics()
                    , UIInstance.getController().getCommunities()
                    , this.intProjectID
                    , this.intGraphID
                    , this.intCurrentTimeFrameIndexProperty.getValue()
                    , UIInstance.getProject(this.intProjectID).getGraphTab(this.intGraphID)
                            .getAccordionTabValues());
    }
    
     public void updateAccordionValues(int timeFrameIndex) {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        getAccordionTabValues(timeFrameIndex).
                updateAccordionTabValues(this.intProjectID, this.intGraphID, timeFrameIndex);
        
        AccordionTabContents accordionTab = AccordionTabContents.getAccordionTabInstance();
                accordionTab.updateAccordionTabs(
                      UIInstance.getController().getAccordion()
                    , UIInstance.getController().getDetails()
                    , UIInstance.getController().getLayouts()
                    , UIInstance.getController().getFilters()
                    , UIInstance.getController().getStatistics()
                    , UIInstance.getController().getCommunities()
                    , this.intProjectID
                    , this.intGraphID
                    , timeFrameIndex
                    , UIInstance.getProject(this.intProjectID).getGraphTab(this.intGraphID)
                            .getAccordionTabValues());
    }
    
    
    /**
     *  Method Name     : updateAfterCommunityMining()
     *  Created Date    : 2016-04-29
     *  Description     : Updates the Community Mining results in the following places
     *                      1) Community Mining Panel in the Accordion Pane
     *                      2) Graph Canvas of the GraphTab showing the vertices and edges
     *                      3) method is called for each time frame after Community MIning is done.
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2017-04-20      @sankalp         added method call to update DCMiningPanel
     * 
    */
    public void updateAfterCommunityMining() {
        
        updateCommunitiesValuesAndPane();
        updateCanvasWithCommunitiesAfterMining();
        minimapDeligator.updateMinimap();
    }
    
    /**
     * Updates the UI components after the link prediction has been computed
     * @author Talat
     * @since 2018-04-08
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex 
     */
    public void updateAfterLinkPrediction(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        updateCanvasAfterLinkPrediction(pintProjectId, pintGraphId, pintTimeFrameIndex);
        minimapDeligator.updateMinimap();
    }
    
    /**
     * Updates the UI components after finding the shortest path
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param plstEdgeIds 
     * 
     * @author Talat
     * @since 2018-05-14
     */
    public void updateAfterShortestPath(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, List<Integer> plstEdgeIds) {
        updateCanvasAfterShortestPath(pintProjectId, pintGraphId, pintTimeFrameIndex, plstEdgeIds);
        minimapDeligator.updateMinimap();
    }    
    
    /**
     * Updates the UI components after clearing the shortest path
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param plstEdgeIds 
     * 
     * @author Talat
     * @since 2018-05-25
     */
    public void updateAfterClearingShortestPath(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, List<Integer> plstEdgeIds) {
        updateCanvasAfterClearingShortestPath(pintProjectId, pintGraphId, pintTimeFrameIndex, plstEdgeIds);
        minimapDeligator.updateMinimap();
    }
    
    /**
     *  Method Name     : updateDCPanelAfterCommunityMining()
     *  Created Date    : 2017-04-21
     *  Description     : This method would be called only when DCMining is done.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public void updateDCPanelAfterCommunityMining() {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        boolean DCMiningState = UIInstance.getActiveProjectTab().getActiveGraphTab().getDCommMiningStatus();
        UIInstance.getController().updateDCMiningPanel(intProjectID, intGraphID, 
                intCurrentTimeFrameIndexProperty.getValue(), intTotalTimeFrames, DCMiningState);
    }
    
    /**
     *  Method Name     : updateCommunitiesValuesAndPane()
     *  Created Date    : 2016-04-27
     *  Description     : This method would be called only when Communities algorithm is run 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    private void updateCommunitiesValuesAndPane() {
        
        // 1) Update the communities values
        if (this.accordionValues.keySet().contains(intCurrentTimeFrameIndexProperty.getValue())) {
                    this.accordionValues.get(intCurrentTimeFrameIndexProperty.getValue()).
                    updateAccordionTabValues(intProjectID, 
                    intGraphID, 
                    intCurrentTimeFrameIndexProperty.getValue());
        } else {
            updateAccordionValuesOnLoading();
            this.accordionValues.get(intCurrentTimeFrameIndexProperty.getValue()).
                    updateAccordionTabValues(intProjectID, 
                    intGraphID, 
                    intCurrentTimeFrameIndexProperty.getValue());
        }
        
        // 2) Update the Communities Pane
        //accordionValues.updateCommunitiesValues(this.intProjectID, this.intGraphID, this.intCurrentTimeFrameIndex);        
        AccordionTabContents accordionTab = 
                AccordionTabContents.getAccordionTabInstance();
        MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
        
        accordionTab.updateAccordionCommunitiesPane(
                  UIInstance.getController().getAccordion()
                , UIInstance.getController().getDetails()
                , UIInstance.getController().getLayouts()
                , UIInstance.getController().getFilters()
                , UIInstance.getController().getStatistics()
                , UIInstance.getController().getCommunities()
                , intProjectID
                , intGraphID
                , intCurrentTimeFrameIndexProperty.getValue()
                , getAccordionTabValues());
        
        //update DCMining Panel after community mining is done.
//        UIInstance.getController().updateDCMiningPanel(intProjectID, intGraphID, intCurrentTimeFrameIndexProperty.getValue(), intTotalTimeFrames, DCMiningState);
    }
    
    /**
     *  Method Name     : updateCanvasWithCommunitiesAfterMining()
     *  Created Date    : 2016-04-29
     *  Description     : Updates the Canvas with Communities updates the colors of the vertices 
     *                      with the colors of their respective communities after community mining is run
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void updateCanvasWithCommunitiesAfterMining() {
        this.graphCanvas.get(intCurrentTimeFrameIndexProperty.getValue())
                .updateVerticesColorCommunitiesAfterMining(
                  getAccordionTabValues()
                          .getCommunitiesValues().getCommunityVertexMapping()
                , getAccordionTabValues()
                        .getCommunitiesValues().getCommunityColors()
                
        );
        
        this.graphCanvas.get(intCurrentTimeFrameIndexProperty.getValue())
                .updateEdgesColorCommunitiesAfterMining(
                  getAccordionTabValues()
                          .getCommunitiesValues().getCommunityVertexMapping()
                , getAccordionTabValues()
                        .getCommunitiesValues().getCommunityColors()
        );        
    }
    
    /**
     * Updates the Graph Canvas after computing the Links (predicted)
     * by adding those links on the canvas
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex 
     * 
     * @author Talat
     * @since 2018-04-08
     */
    private void updateCanvasAfterLinkPrediction(int pintProjectId, int pintGraphId, int pintTimeFrameIndex) {
        
        Map<Integer, Integer[]> mapPredictedEdges = GraphAPI.getPredictedEdges(pintProjectId, pintGraphId, pintTimeFrameIndex);
    
        for (Integer intEdgeId : mapPredictedEdges.keySet()) {
            int intSourceVertexId = mapPredictedEdges.get(intEdgeId)[0];
            int intDestinationVertexId = mapPredictedEdges.get(intEdgeId)[1];
            this.graphCanvas.get(pintTimeFrameIndex).updateCanvasAfterAddEdge(
                    pintProjectId, pintGraphId, pintTimeFrameIndex, intEdgeId, 
                    intSourceVertexId, intDestinationVertexId);
        }
        setArePredictedEdgesShown(true);
    }
    
    /**
     * Updates the Canvas After Shortest Path has been computed
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param plstEdgeIds 
     * 
     * @author Talat
     * @since 2018-05-17
     */
    private void updateCanvasAfterShortestPath(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, List<Integer> plstEdgeIds){
        
        for (Integer edgeId : plstEdgeIds) {
            this.graphCanvas.get(pintTimeFrameIndex).updateCanvasAfterShortestPath(
                    pintProjectId, pintGraphId, pintTimeFrameIndex, edgeId);
        }
    }
    
    /**
     * Updates the canvas after clearing the shortest path
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param plstEdgeIds 
     * 
     * @author Talat
     * @since 2018-05-25
     */
    private void updateCanvasAfterClearingShortestPath(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, List<Integer> plstEdgeIds){
        
        for (Integer edgeId : plstEdgeIds) {
            this.graphCanvas.get(pintTimeFrameIndex).updateCanvasAfterClearingShortestPath(
                    pintProjectId, pintGraphId, pintTimeFrameIndex, edgeId);
        }
    }
    
    /**
     *  Method Name     : updateCanvasWithCommunitiesOnLoading()
     *  Created Date    : 2018-01-24
     *  Description     : Updates the Canvas with Communities updates the colors of the vertices 
     *                      with the colors of their respective communities on graph/project load
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void updateCanvasWithCommunitiesOnLoading() {
        int currentTF = intCurrentTimeFrameIndexProperty.getValue();
        this.graphCanvas.get(intCurrentTimeFrameIndexProperty.getValue())
                .updateVerticesColorCommunitiesOnLoading(
                  getAccordionTabValues()
                          .getCommunitiesValues().getCommunityVertexMapping()
                , getAccordionTabValues()
                        .getCommunitiesValues().getCommunityColors()
                
        );
        
        this.graphCanvas.get(intCurrentTimeFrameIndexProperty.getValue())
                .updateEdgesColorCommunitiesOnLoading(
                  getAccordionTabValues()
                          .getCommunitiesValues().getCommunityVertexMapping()
                , getAccordionTabValues()
                        .getCommunitiesValues().getCommunityColors()
        );        
    }
    
    /**
     *  Method Name     : clearCanvasFromCommunities()
     *  Created Date    : 2016-04-29
     *  Description     : Clears all the community mining results
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void clearCanvasFromCommunities() {
        
    }
    
    /**
     *  Method Name     : circleOnMousePressedEventHandler()
     *  Created Date    : 2015-10-27
     *  Description     : An Event Handler for a Mouse Press Event
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    EventHandler<MouseEvent> circleOnMousePressedEventHandler = 
        (MouseEvent t) -> {
            dblSceneSourceX = t.getSceneX();
            dblSceneSourceY = t.getSceneY() ;
            dblTranslateX = ((Circle)(t.getSource())).getTranslateX();
            dblTranslateY = ((Circle)(t.getSource())).getTranslateY();
            
            // System.out.println("GraphTab.circleOnMousePressedEventHandler(): MousePress: ("+dblSceneSourceX+","+dblSceneSourceY+")"+"\t"+"("+dblTranslateX+","+dblTranslateY+")");
    };
     
    
    /**
     *  Method Name     : circleOnMouseDraggedEventHandler
     *  Created Date    : 2015-10-27
     *  Description     : An Event Handler for a Mouse Drag Event
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = 
        (MouseEvent t) -> {
            double offsetX = t.getSceneX() - dblSceneSourceX;
            double offsetY = t.getSceneY() - dblSceneSourceY;
            double newTranslateX = dblTranslateX + offsetX;
            double newTranslateY = dblTranslateY + offsetY;
            
            // System.out.println("GraphTab.circleOnMouseDraggedEventHandler(): MouseDrag: ("+offsetX+","+offsetY+")"+"\t"+"("+newTranslateX+","+newTranslateY+")");
             
            ((Circle)(t.getSource())).setTranslateX(newTranslateX);
            ((Circle)(t.getSource())).setTranslateY(newTranslateY);
    };
    
    
    // #TO-DO Write the Visible Area
    /**
     *  Method Name     : getVisibleArea()
     *  Created Date    : 
     *  Description     : 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return Node
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public Node getVisibleArea () {
        return null;
    }
    
    
    
    public void updateZoomPane(final GraphCanvas pGraphElements) {
        
        
        scrlCanvas.setContent(getZoomPane());
        /*previous versions of code in this method
        //anchZoomPane.getChildren().add(pGraphElements);
        //Rectangle rectLowerTop = new Rectangle(0,0,0,0);
        //Rectangle rectRightBottom = new Rectangle(SceneConfig.GRAPHCANVAS_WIDTH, SceneConfig.GRAPHCANVAS_WIDTH, 0,0);
        //anchZoomPane.getChildren().addAll(rectLowerTop, rectRightBottom);
        //modified code here after
        //anchZoomPane.getChildren().clear();
        //anchZoomPane.setOnMousePressed(null);
        //anchZoomPane.setOnMouseReleased(null);
        // anchZoomPane.setOnMouseDragged(null);
        // anchZoomPane.setOnMouseMoved(null);
        // createZoomPane_exp(pGraphElements);
        *
        */ 
        
        
    }
   
    private void createScrollPaneBehaviour() {
        final double SCALE_DELTA = SceneConfig.CANVAS_SCROLL_SCALEFACTOR;

        ////minimap = new Minimap(pGraphElements, scrlCanvas, anchZoomPane);
        // NOTE: Use -fx-background instead of -fx-background-color to make the scrollpane paint the complete area (including the non-visible area) to the assigned color
        scrlCanvas.setStyle("-fx-background: "+this.getColorString()+"; ");
        scrlCanvas.setFitToHeight(true);
        scrlCanvas.setFitToWidth(true);
        
        scrlCanvas.viewportBoundsProperty().addListener((ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) -> {
            //TODO see if this line is needed or not
            //anchZoomPane.setMinSize(newValue.getWidth(), newValue.getHeight());
            // System.out.println("GraphTab. scrlCanvas.viewportBoundsProperty() change");
            minimapDeligator.changeMinimapMasking();
        });

        //SceneGestures sceneGestures = new SceneGestures(anchZoomPane, this.getGraphCanvasMap(), intCurrentTimeFrameIndexProperty, minimapDeligator);
        //SceneGestures2 sceneGestures = new SceneGestures2(anchZoomPane, this.getGraphCanvasMap(), intCurrentTimeFrameIndexProperty, minimapDeligator, pGraphElements);
        //SceneGestures3 sceneGestures = new SceneGestures3(anchZoomPane, this.getGraphCanvasMap(), intCurrentTimeFrameIndexProperty, minimapDeligator, pGraphElements);
        sceneGestures = new SceneGestures4(getArrZoomPane(), this.getGraphCanvasMap(), intCurrentTimeFrameIndexProperty, minimapDeligator);
        
        setGraphCanvasMode(ModeTypes.SELECT);
    }

    
    public void setGraphCanvasMode(ModeTypes canvasMode){
        //remove all event filters before setting up a mode
        try{
            scrlCanvas.removeEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
            scrlCanvas.removeEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
            scrlCanvas.removeEventFilter( MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
            scrlCanvas.removeEventFilter( MouseEvent.MOUSE_RELEASED, sceneGestures.getOnMouseReleasedEventHandler());
            scrlCanvas.removeEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
            
        } catch(Exception e){
            System.out.println("Exception in setting GrahCanvasMode listeners");
        }
        //clear all selections from graphCanvas - all time frames before switching a mode
        for (int i = 0 ; i < arrstrTimeFrames.length; i++) {
                getGraphCanvas(i).clearSelectedVertexList();
                getGraphCanvas(i).clearSelectedEdgeList();
        }
        
        //set mode and activate it by adding the appropriate listeners
        currentGraphCanvasMode = this.graphCanvasModeFactory.getGraphCanvasMode(canvasMode);
        currentGraphCanvasMode.activateGraphCanvasMode(scrlCanvas, sceneGestures, modeInfo);
        
    }
    
    public void rotateGraphCanvas(int pintCurrentTimeFrameIndex, String direction){
    
        if(direction.equals("left")){
            scrlCanvas.rotateProperty().set(scrlCanvas.rotateProperty().getValue()-90);
            //this.getZoomPane().rotateProperty().set(this.getZoomPane().rotateProperty().getValue()-90);
            
        }else{
            scrlCanvas.rotateProperty().set(scrlCanvas.rotateProperty().getValue()+90);
            //this.getZoomPane().rotateProperty().set(this.getZoomPane().rotateProperty().getValue()+90);
        }
    }
    
    
    /**
     *  Method Name     : setLabelVisibility()
     *  Created Date    : 2016-01-28
     *  Description     : Sets the Vertex Labels Visibility
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pblnVisibility : boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public void setLabelVisibility(boolean pblnVisibility){
        
        for (UIVertex vtx : getGraphCanvas().getVertices().values()) {
            if (vtx != null) {
                vtx.getVertexHolder().setLabelVisibility(pblnVisibility);                
            }
        }
    }
    
    /**
     *  Method Name     : setTooltipVisibility()
     *  Created Date    : 2016-01-28
     *  Description     : Create the Tool tip object & sets the Tool tip Label's Visibility 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pblnVisibility : boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2017-08-30      @sankalp        added logic to create Tool tip for all 
     *                                  vertices for the first time.
     * 
    */
    public void setTooltipVisibility(boolean pblnVisibility){
        System.out.println("GraphTab(): Tool tip selection");
        //creating tooltip objects
        if(!toolTipAdded){
            for (UIVertex vtx : getGraphCanvas().getVertices().values()) {
                if (vtx != null) {
                    vtx.getVertexHolder().createTooltip();
                }
            }
            toolTipAdded=true;
        }
        
        for (UIVertex vtx : getGraphCanvas().getVertices().values()) {
            if (vtx != null) {
                vtx.getVertexHolder().setTooltipVisibility(pblnVisibility);
            }
        }
        
    }
    
    /**
     *  Method Name     : increaseVertexLabel()
     *  Created Date    : 2016-05-26
     *  Description     : Increases the size of the Vertex Label
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public void increaseVertexLabel(){      
        
        for (UIVertex vtx : getGraphCanvas().getVertices().values()) {
            if (vtx != null) {
                vtx.getVertexHolder().increaseVertexLabelSize();
            }
        }
        
        //updateMiniMap();
        minimapDeligator.updateMinimap();
    }
    
    /**
     *  Method Name     : decreaseVertexLabel()
     *  Created Date    : 2016-05-26
     *  Description     : Decreases the size of the Vertex Label
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public void decreaseVertexLabel(){      
        
        for (UIVertex vtx : getGraphCanvas().getVertices().values()) {
            if (vtx != null) {
                vtx.getVertexHolder().decreaseVertexLabelSize();
            }
        }
        
        //updateMiniMap();
        minimapDeligator.updateMinimap();
    }
    
    
    /**
     *  Method Name     : pinVertexToCanvas()
     *  Created Date    : 2016-05-26
     *  Description     : Pins the Vertex to the Context
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public void pinVertexToCanvas(){ 
        for (UIVertex vtx : getGraphCanvas().getVertices().values()) {
            if (vtx != null) {
                vtx.getVertexHolder().pinVertexToCanvas();
            }
        }
    }
    
    
    /**
     *  Method Name     : unpinVertexToCanvas()
     *  Created Date    : 2016-05-26
     *  Description     : Pins the Vertex to the Context
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public void unpinVertexToCanvas(){ 
        for (UIVertex vtx : getGraphCanvas().getVertices().values()) {
            if (vtx != null) {
                vtx.getVertexHolder().unpinVertexToCanvas();
            }
        }
    }
    
    
    
    /**
     *  Method Name     : changeColor()
     *  Created Date    : 2016-02-01
     *  Description     : Changes the background color of the current tab
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrColor : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void changeBGColor(String pstrColor) {
        removeBackgroundImage();
        this.strBackgroundColor = pstrColor ;
        scrlCanvas.setStyle("-fx-background: "+this.getColorString()+"; ");   
        
        //updateMiniMap();
        minimapDeligator.updateMinimap();
    }
    
    /**
     *  Method Name     : removeBackgroundImage()
     *  Created Date    : 2016-07-14
     *  Description     : Removes the background color of the current tab
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void removeBackgroundImage(){
        scrlCanvas.setBackground(Background.EMPTY);
    }
    
    /**
     *  Method Name     : changeBackgroundImage()
     *  Created Date    : 2016-05-27
     *  Description     : Changes the background image of the current tab
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrImageURL : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void changeBackgroundImage(String pstrImageURL) {
        // changeBGTransparent();
        System.out.println("GraphTab.changeBackgroundImage(): ImageURL: "+pstrImageURL);
        try {
            //pstrImageURL = this.getClass().getResource(pstrImageURL).toExternalForm();
            BackgroundImage myBI = new BackgroundImage(new Image("file:///"+pstrImageURL,SceneConfig.GRAPHCANVAS_WIDTH,SceneConfig.GRAPHCANVAS_HEIGHT,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
            getZoomPane().setBackground(new Background(myBI));
            //updateMiniMap();
            minimapDeligator.updateMinimap();
        } catch (Exception ex) {
            System.out.println("GraphTab.changeBackgroundImage(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
    /**
     *  Method Name     : changeBGTransparent()
     *  Created Date    : 2016-07-14
     *  Description     : Changes the background color of the current tab to transparent
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void changeBGTransparent() {
        this.strBackgroundColor = SceneConfig.CANVAS_BACKGROUND_TRANSPARENT ;
        scrlCanvas.setStyle("-fx-background: "+this.getColorString()+"; ");        
    }
    
    /**
     *  Method Name     : updateCanvas()
     *  Created Date    : 2016-03-11
     *  Description     : Updates the Graph on canvas based on the new positions
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateCanvas() {
        
        this.getGraphCanvas().updateCanvas(this.intProjectID, 
                this.intGraphID, 
                this.intCurrentTimeFrameIndexProperty.getValue());
        //this.updateMiniMap();
        this.minimapDeligator.updateMinimap();
    }    
    
    public double getMouseX() {
        return mouseContext.mouseX ;
    }
    public void setMouseX(double pdblValue) {
        mouseContext.mouseX = pdblValue ;
        getGraphCanvas().setMouseX(pdblValue);
    } 
    
    public double getMouseY(){
        return mouseContext.mouseY ;
    }
    public void setMouseY(double pdblValue) {
        mouseContext.mouseY = pdblValue ;
        getGraphCanvas().setMouseY(pdblValue);
    } 

    public void updateComMiningStatus() {
        this.setComMiningStatus(Boolean.TRUE, this.getTimeFrameIndex());
    }
    
    public void updateLinkPredictionStatus() {
        this.setLinkPredictionStatus(Boolean.TRUE, this.getTimeFrameIndex());
    }
    
    private final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
    }
    
    private final class MouseContext {
        public double mouseX ;
        public double mouseY ;
    }
    
    public void showMinimap() {
        this.minimap.getStkPaneMinimap().setVisible(true);
        this.blnIsMinimapHidden = false ;
        /*
        this.stkMiniMap.setVisible(true);
        this.blnIsMinimapHidden = false ;
        */
    }
    
    public void showModeInformaiton() {
        this.modeInfo.getStackModeInformation().setVisible(true);
        this.blnIsModeInfoHidden = false ;
    }
    
    public void hideMinimap() {
        this.minimap.getStkPaneMinimap().setVisible(false);
        this.blnIsMinimapHidden = true ;
        /*
        this.stkMiniMap.setVisible(false);
        this.blnIsMinimapHidden = true ;
        */
    }
    
    public void hideModeInformaiton() {
        this.modeInfo.getStackModeInformation().setVisible(false);
        this.blnIsModeInfoHidden = true ;
    }
    
    /**
     * Shows the Edges on the canvas
     * 
     * @author Talat
     * @since 2018-04-14
     */
    public void showEdges() {
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
        this.graphCanvas.get(intTimeFrameIndex).showNonPredictedEdgesInUI(intTimeFrameIndex);
        setAreEdgesShown(true);
        minimapDeligator.updateMinimap();
    }
    
    /**
     * Hides the Edges from canvas
     * 
     * @author Talat
     * @since 2018-04-14
     */
    public void hideEdges() {
     MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
        this.graphCanvas.get(intTimeFrameIndex).hideNonPredictedEdgesInUI(intTimeFrameIndex);   
        setAreEdgesShown(false);
        minimapDeligator.updateMinimap();
    }
    
    /**
     * Shows the Predicted Edges on the canvas
     * 
     * @author Talat
     * @since 2018-04-14
     */
    public void showPredictedEdges() {
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
        this.graphCanvas.get(intTimeFrameIndex).showPredictedEdgesInUI(intTimeFrameIndex);
        setArePredictedEdgesShown(true);
        minimapDeligator.updateMinimap();
    }
    
    /**
     * Hides the Predicted Edges from canvas
     * 
     * @author Talat
     * @since 2018-04-14
     */
    public void hidePredictedEdges() {
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
        this.graphCanvas.get(intTimeFrameIndex).hidePredictedEdgesInUI(intTimeFrameIndex);
        setArePredictedEdgesShown(false);
        minimapDeligator.updateMinimap();
    }
    
    public void clearLinkPrediction(){
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
        this.graphCanvas.get(intTimeFrameIndex).clearLinkPredictionResultsUI(intTimeFrameIndex);
    }
    
    
    
    public void addProgressIndicatorAndDisableSlider(ProgressIndicator progressIndicator, Button stopButton){
        scrlCanvas.setEffect(new BoxBlur());
        
        stkpaneMiningProgress = new StackPane();
        
        progressIndicator.setStyle(" -fx-progress-color: grey;");
        // changing size without css
        progressIndicator.setMaxWidth(200);
        progressIndicator.setMaxHeight(200);

        
        stkpaneMiningProgress.getChildren().add(progressIndicator);
        stkpaneMiningProgress.getChildren().add(stopButton);
        stkCanvasMinimapHolder.getChildren().add(stkpaneMiningProgress);
        //Time Frames can not be changed while mining is running
        disableDCSlider();
        disableTimeFrameChangeButtons();
        
    }
    private void disableTimeFrameChangeButtons(){
        this.btnNextTimeFrame.setDisable(true);
        this.btnPreviousTimeFrame.setDisable(true);
    }
    
    private void enableTimeFrameChangeButtons(){
        this.btnNextTimeFrame.setDisable(false);
        this.btnPreviousTimeFrame.setDisable(false);
    }
    private void disableDCSlider(){
        sldrDCSlider.setDisable(true);
    }
    private void enableDCSlider(){
        sldrDCSlider.setDisable(false);
    }
    
    public void removeProgressIndicatorAndDisableSlider(){
        stkCanvasMinimapHolder.getChildren().remove(stkpaneMiningProgress);
        scrlCanvas.setEffect(null);
        //enable the dc slider once mining is done
        enableDCSlider();
        enableTimeFrameChangeButtons();
    }
    
    
    
    
    
    
    
}


class GraphTabContextMenu{

    private ContextMenu contextMenu;
    private MenuItem removeGraphMenuItem;
    private MenuItem renameGraphMenuItem;
    
    private GraphTab graphTab;
    
    
    public ContextMenu getGraphTabContextMenu(){
    
        return contextMenu;
    }
    
    public GraphTabContextMenu(GraphTab graphTab){
    
        this.contextMenu = new ContextMenu();
        this.graphTab = graphTab;
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        // REMOVE A GRAPH
        removeGraphMenuItem = new MenuItem(LangConfig.OPTIONS_REMOVEGRAPH);
        removeGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                //DeleteGraph.delete(UIInstance.getController());
                DeleteGraph.delete(UIInstance.getController(), graphTab);
                //UIInstance.getActiveProjectTab().deleteActiveGraph();
            }
        });
        
        // RENAME A GRAPH
        renameGraphMenuItem = new MenuItem(LangConfig.OPTIONS_RENAMEGRAPH);
        renameGraphMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                RenameGraph.rename(UIInstance.getController(), graphTab);
            }
        });
        
        
        contextMenu.getItems().add(renameGraphMenuItem);
        contextMenu.getItems().add(removeGraphMenuItem);
        
    }   
}