/*
 * To change this license header, choose License Headers in ProjectTab Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysisscreen;

import accordiontab.AccordionTabContents;
import analysismenubar.MenuBarUtilities;
import config.SceneConfig;
import config.StatusMsgsConfig;
import datastructure.GraphElement;
import events.EventAnalyzerDialog;
import ui.elements.EditingToolBox;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import globalstate.ProjectTab;
import globalstate.UIComponents.KeyEventListener;
import io.parser.D3Json;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import static javafx.scene.layout.HBox.setHgrow;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import meerkat.UIUtilities;
import threadtree.MeerkatTreeItem;
import threadtree.TextFieldTreeCellImpl;
import threadtree.ThreadTree;
import threadtree.ThreadTreeLangParameters;
import threadtree.TreeItemConfig.TreeItemType;
import ui.dialogwindow.EventAnalyzer;
import ui.elements.ColorToolBox;
import ui.elements.IconToolBox;
import ui.elements.SizeToolBox;
import ui.elements.SnapshotToolBox;
import ui.toolsribbon.ToolsRibbonContextMenu;

/**
 *  Class Name      : AnalysisController
 *  Created Date    : 2015-07-21
 *  Description     : The main Controller that controls the complete UI
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-03-31      Talat           Removed wvHistogram,wvLineGraph, weHistogram,weLineGraph initiateLineGraph() and initiateHistogram(). Check previously dated versions for code and functionality
 *  2016-03-29      Talat           Adding a listener to initiateThreadTreeView() to have two way communications with Graph/Project Tabs
 *  2016-01-13      Talat           Adding the @FXML variable gridStatus to hold all the Status Bar elements
 *  2016-01-04      Talat           Adding the @FXML variable hboxToolRibbon to hold all the tools in the ribbon below the Menu Bar
 *  2015-07-28      Talat           Replacing the initiateMenuBar and moving the functionality to analysismenubar
 *  2015-07-22      Talat           Removed Setters and Getters of FishEyeLens and Magnify Lens. These attributes are now added as
 *                                  part of the GraphTab from where the state could be retrieved
*/
public class AnalysisController implements Initializable {
    
    // #TOBEREMOVED
    /* ------------ START --------------*/
    // @FXML protected WebView wvHistogram ;
    // @FXML protected WebView wvLineGraph ;    
    // private WebEngine weHistogram ;
    // private WebEngine weLineGraph ;
    
    // # TO BE REMOVED - ONLY FOR EASE OF TESTING
    // -------- START
    // GLOBAL PARAMETERS
    protected String strCurrentTabID ;
    public double dblRadius = 5 ; 
    @FXML
    private SplitPane splitTreeCanvasAccordion;
    @FXML
    private TreeView<?> treeThread;
    @FXML
    private SplitPane splitCanvasAccordion;
    @FXML
    private AnchorPane anchCanvas;
    @FXML
    private AnchorPane anchEvents;
    public double getRadius(){return this.dblRadius;}
    // -------- END
    
    /* ------------ END- -------------*/

    
    /* *************************************************************** */
    /* *******************     FXML VARIABLES     ******************** */
    /* *************************************************************** */
    
    // COMPLETE APPLICATION
    @FXML protected VBox vboxApplication;
    
    @FXML protected AnchorPane anchDCMining ;
    @FXML protected VBox vboxDCMining ;
    
    // MENUBAR
    @FXML 
    protected MenuBar menuAnalysis;        
    
    // TOOL RIBBON
    @FXML
    protected HBox hboxToolRibbon;
    
    // STATUS BAR
    @FXML protected AnchorPane anchStatusBar ;
    @FXML protected GridPane gridStatus ;
    
    // Tabs in the Main Graph
    @FXML 
    protected TabPane tabpaneProject;
    
    @FXML 
    protected TitledPane      accDetails
                            , accLayouts
                            , accFilters
                            , accStatistics
                            , accCommunities ;
    
    @FXML 
    protected Accordion accOptionsPane;
    
    @FXML 
    protected AnchorPane anchThreadView;
    
    @FXML 
    protected SplitPane splitParent;
    
    /* Thread Tree */
    protected TreeView treeviewThread; // TreeView that displays the hierarchy
    // TreeItem<String> treeitemsRoot; // TreeRoot that contains the TreeItems to be displayed
    
    // Panel for Community Mining
    
    // Dynamic Representation of Graph
    
    // Main Anchor Panes
    @FXML protected AnchorPane anchDCSliderButton ;
    @FXML protected AnchorPane anchEventsButtons ;
    /* These buttons moved to GraphTab. Now each Graphtab object has its own Time Frame ChangeButtons
    *  no more common buttons for the entire application
    @FXML protected Button btnPreviousTimeFrame ;
    @FXML protected Button btnNextTimeFrame ;
    */
    // Buttons for Dynamic Community Mining
    @FXML protected Button btnEvents ;
    @FXML protected Button btnFormed ;
    @FXML protected Button btnAppeared ;
    @FXML protected Button btnJoined ;
    @FXML protected Button btnDisappeared ;
    @FXML protected Button btnLeft ;
    @FXML protected Button btnSurvived ;
    @FXML protected Button btnDissolved ;
    @FXML protected Button btnSplit ;
    @FXML protected Button btnMerged ;
    
    @FXML protected HBox hboxDCSlider ;
        
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    /* Removed for MeerkatFXv1.0
    private Label lblStatus ;
    private ProgressBar pbarStatus ;
    */
    private ThreadTreeLangParameters treeParameters ; // Parameters that would be useful in language dependent display           
    private MeerkatUI UIInstance ;
    private String strCurrentOutputFile ;
    private enum Event {
        NONE, FORM, DISSOLVE, MERGE, SPLIT, SURVIVE,
        APPEAR, DISAPPEAR, JOIN, LEFT
    }
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    public VBox getApplicationNode() {
        return vboxApplication;
    }
    public Stage getPrimaryStage() {
        return (Stage)splitParent.getScene().getWindow();
    }
    
    public TreeView getTreeView() {
        return this.treeviewThread;
    }
    public TabPane getProjectTabPane () {
        return this.tabpaneProject;
    }
    public Accordion getAccordion() {
        return this.accOptionsPane;
    }
    public TitledPane getDetails() {
        return this.accDetails;
    }
    public TitledPane getCommunities () {
        return this.accCommunities ;
    }
    public TitledPane getFilters () {
        return this.accFilters ;
    }
    public TitledPane getStatistics () {
        return this.accStatistics ;
    }
    public TitledPane getLayouts () {
        return this.accLayouts ;
    }
    private void setStatus (String pstrStatusLabel) {
        //lblStatus.setText(pstrStatusLabel);
    }
    
    public HBox getToolsRibbon () {
        return hboxToolRibbon ;
    }
    
    public MenuBar getMenuBar() {
        return this.menuAnalysis ;
    }
    

    
    /* *************************************************************** */
    /* ****************     OVER-RIDDEN METHODS     ****************** */
    /* *************************************************************** */

    /**
     *  Method Name     : initialize
     *  Created Date    : 2015-07-22
     *  Description     : Overridden method - Initializes the FXML components. Any update is to be done here
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param url: URL
     *  @param rb: ResourceBundle
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-01-13      Talat           Calling the method to initiate the Status Bar
     *  2016-01-04      Talat           Calling the method to initiate the Tools Ribbon
     * 
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {     
                
        // Initiate a Meerkat Application Instance
        UIInstance = MeerkatUI.getUIInstance();
        UIInstance.setController(this);
        
        // SceneConfig.VERTEX_LABEL_VISIBLE = SceneConfig.VERTEX_LABEL_VISIBLE_DEFAULT ;
                
        initiateMenuBar(); // Initialize the Menu Bar of the Analysis Screen
        
        initiateToolsRibbon(); // Initiates the Tools Ribbon just below the Menu Bar
        
        /* Initializing the Project Tab Pane - Main canvas area to display the graphs */        
        initiateProjectTabPane(); // Initialize the Tabbed Pane for the Project - While initializing, its not required to have any projects
        
        /* Initializing the Thread Tree View */
        initiateThreadTreeView(); 
        
        /* Initializing the Accordion Tabs - Options Pane on the right */
        initiateAccordionTabs();
        
        initiateStatusBar();
        
        initiateDCMiningPanel() ;
        
        
        // Later to be moved somewhere else
        // #START
        // initiateHistogram();
        // initiateLineGraph();
        // #END
        
        updateStatusBar(false, StatusMsgsConfig.APPLICATION_OPENED);        
        
    }    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    
    /**
     *  Method Name     : initiateStatusBar()
     *  Created Date    : 2016-01-13
     *  Description     : Initializes the components in the status bar
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void initiateStatusBar() {
        /* Removed for MeerkatFXv1.0
        lblStatus = new Label(StatusMsgsConfig.APPLICATION_OPENING);
        pbarStatus = new ProgressBar();
        
        ColumnConstraints clmImageInfo1 = new ColumnConstraints(100);
        clmImageInfo1.setPercentWidth(50);
        ColumnConstraints clmImageInfo2 = new ColumnConstraints();
        clmImageInfo2.setPercentWidth(50);
        
        gridStatus.getColumnConstraints().addAll(clmImageInfo1, clmImageInfo2); 
        gridStatus.setPadding(new Insets(0, 0, 0, 0));
        // gridStatus.setHgap(10);
        
        GridPane.setHalignment(pbarStatus, HPos.LEFT);
        gridStatus.add(pbarStatus, 0, 0);
        
        GridPane.setHalignment(lblStatus, HPos.RIGHT);
        gridStatus.add(lblStatus, 1, 0);
        
        hboxToolRibbon.setSpacing(20);
        hboxToolRibbon.setPadding(new Insets(0, 3, 0, 3));
        */
        
    }
    
    
    /**
     *  Method Name     : initiateDCMiningPanel()
     *  Created Date    : 2016-04-01
     *  Description     : Initiates the Mining panel
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void initiateDCMiningPanel () {
        
        SceneConfig.initialize();
        
        vboxDCMining.setMinHeight(SceneConfig.LOWERPANEL_HEIGHT.get());
        vboxDCMining.setMaxHeight(SceneConfig.LOWERPANEL_HEIGHT.get());
        
        Slider sldrTimeFrame = new Slider(0, 100, 0);
        sldrTimeFrame.setOrientation(Orientation.HORIZONTAL);
        setHgrow(sldrTimeFrame, Priority.ALWAYS);
        
        hboxDCSlider.getChildren().add(sldrTimeFrame);
        hboxDCSlider.setPadding(new Insets(5, 10, 5, 10));        
        hboxDCSlider.setVisible(true);
        
        /*
        btnPreviousTimeFrame.setDisable(true);
        btnPreviousTimeFrame.setOnAction( e -> {
            UIInstance.getActiveProjectTab().getActiveGraphTab().getSlider().moveToPreviousTimeFrame();
        });
        
        btnNextTimeFrame.setDisable(true);
        btnNextTimeFrame.setOnAction( e -> {
            UIInstance.getActiveProjectTab().getActiveGraphTab().getSlider().moveToNextTimeFrame();
        });
        */    
        btnEvents.setDisable(true);
        btnEvents.setOnAction(e -> {
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID() ;
            int intTimeFrame = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            EventAnalyzer.DisplayEventDialog(this, intProjectID, intGraphID, intTimeFrame);
        });
        
        btnFormed.setDisable(true);
        btnFormed.setOnAction(e -> {
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID() ;
            int intTimeFrame = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            EventAnalyzer.DisplayFormedDialog(this, intProjectID, intGraphID, intTimeFrame);
        });
        
        btnSurvived.setDisable(true);
        btnSurvived.setOnAction(e -> {
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID() ;
            int intTimeFrame = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            EventAnalyzer.DisplaySurvivedDialog(this, intProjectID, intGraphID, intTimeFrame);
        });
        
        btnDissolved.setDisable(true);
        btnDissolved.setOnAction(e -> {
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID() ;
            int intTimeFrame = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            EventAnalyzer.DisplayDissolvedDialog(this, intProjectID, intGraphID, intTimeFrame);
        });
        
        btnSplit.setDisable(true);
        btnSplit.setOnAction(e -> {
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID() ;
            int intTimeFrame = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            EventAnalyzer.DisplaySplitDialog(this, intProjectID, intGraphID, intTimeFrame);  
        });
        
        btnMerged.setDisable(true);
        btnMerged.setOnAction(e -> {
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID() ;
            int intTimeFrame = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            EventAnalyzer.DisplayMergeDialog(this, intProjectID, intGraphID, intTimeFrame);
        });
            
        btnAppeared.setDisable(true);
        btnAppeared.setOnAction(e -> {
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID() ;
            int intTimeFrame = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            EventAnalyzer.DisplayAppearDialog(this, intProjectID, intGraphID, intTimeFrame);
        });
        
        btnJoined.setDisable(true);
        btnJoined.setOnAction(e -> {
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID() ;
            int intTimeFrame = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            EventAnalyzer.DisplayJoinDialog(this, intProjectID, intGraphID, intTimeFrame);
        });
        
        btnDisappeared.setDisable(true);
        btnDisappeared.setOnAction(e -> {
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID() ;
            int intTimeFrame = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            EventAnalyzer.DisplayDisappearDialog(this, intProjectID, intGraphID, intTimeFrame);
        });
         
        btnLeft.setDisable(true);
        btnLeft.setOnAction(e -> {
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID() ;
            int intTimeFrame = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            EventAnalyzer.DisplayLeftDialog(this, intProjectID, intGraphID, intTimeFrame);
        });
    }
    
    /**
     *  Method Name     : enableDCPanel()
     *  Created Date    : 2016-06-29
     *  Description     : Enables the Dynamic Community Panel
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
    private void showAndEnableDCPanel(int pintProjectID, int pintGraphID, int pintTimeFrameIndex, boolean DCMiningStatus) {
              
        // Remove the Status bar so that the ordering of the children on the VBox Application is maintained
        vboxApplication.getChildren().remove(anchStatusBar);
        
        // Now Add both the DCMining Panel and Status Bar Panel
        if (!vboxApplication.getChildren().contains(anchDCMining)){
            vboxApplication.getChildren().add(anchDCMining);
        }        
        if (!vboxApplication.getChildren().contains(anchStatusBar)){
            vboxApplication.getChildren().add(anchStatusBar);
        }
        
        // Set the height properties of the DC Panels
        SceneConfig.DCPANEL_SLIDER_HEIGHT.set(SceneConfig.DCPANEL_SLIDER_MAXHEIGHT);
        SceneConfig.DCPANEL_BUTTONS_HEIGHT.set(SceneConfig.DCPANEL_BUTTONS_MAXHEIGHT);
        
        // Set the Height of the DC Panels
        anchDCSliderButton.setMinHeight(SceneConfig.DCPANEL_SLIDER_HEIGHT.get());
        anchEventsButtons.setMinHeight(SceneConfig.DCPANEL_BUTTONS_HEIGHT.get());
        
        anchDCSliderButton.setMaxHeight(SceneConfig.DCPANEL_SLIDER_HEIGHT.get());
        anchEventsButtons.setMaxHeight(SceneConfig.DCPANEL_BUTTONS_HEIGHT.get());
        
        // Set the slider and button panel to be visible
        anchDCSliderButton.setVisible(true);
        anchEventsButtons.setVisible(true);
        
        // Enable all the elements DC Panels
        vboxDCMining.setDisable(false);
        hboxDCSlider.setDisable(false);
        /*
        btnPreviousTimeFrame.setDisable(false);
        btnNextTimeFrame.setDisable(false);
        */
                
        if(DCMiningStatus){
            //Initialize Event Buttons
            EventAnalyzer.init(pintProjectID, pintGraphID, pintTimeFrameIndex);
            ArrayList<Map<String, Boolean>> EventStatus = EventAnalyzerDialog.getEventStatus();
            ValidateEventButtons(EventStatus);
            btnEvents.setDisable(false);       
        }else{
            disableDCEventPanel();
        }
        // Clear already existing sliders
        // Clear alredy existing TimeFrameChange Buttons
        hboxDCSlider.getChildren().clear();
        anchEvents.getChildren().clear();
        
        
        // Assign the GraphTabs Slider and add it to the child
        ProjectTab prjCurrent = UIInstance.getProject(pintProjectID) ;
        
        // Check if the Current Project returned is not null - This is to avoid 
        // the initial tab change event that the UI invokes while creating tabs 
        // before the project tab is added to the map in MeerkatUI
        if (prjCurrent != null) {
            GraphTab grphCurrent = prjCurrent.getGraphTab(pintGraphID) ;
            if (grphCurrent != null) {                
                Slider sldrCurrent = grphCurrent.getSlider();        
                hboxDCSlider.getChildren().add(sldrCurrent);
                hboxDCSlider.setDisable(false);
                //add TimeFrame change buttons on screen 
                anchEvents.getChildren().add(grphCurrent.getTimeFrameChangeButtonBox());
                
            }            
        }
        
        
        // Update the Slider
        /*
        hboxDCSlider.getChildren().clear();
        hboxDCSlider.getChildren().add(UIInstance.getProject(pintProjectID).getGraphTab(pintGraphID).getSlider());            
        hboxDCSlider.setDisable(false);

        if (pintTimeFrameIndex == (pinTotalTimeFrames-1)) {
            btnNextTimeFrame.setDisable(true);
            btnPreviousTimeFrame.setDisable(false);

        } else if (pintTimeFrameIndex == 0) {
            btnPreviousTimeFrame.setDisable(true);
            btnNextTimeFrame.setDisable(false);
        }
        */
    }
    
    /**
     *  Method Name     : ValidateEventButtons()
     *  Created Date    : 2017-04-20
     *  Description     : enables or disables event buttons.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    private void ValidateEventButtons(ArrayList<Map<String, Boolean>> EventStatus){
        
        //TODO remove if else and replace with something concise
        int currentTimeFrame =  UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
        if(EventStatus.get(currentTimeFrame).containsKey(Event.FORM.toString()))
            btnFormed.setDisable(false);
        else
            btnFormed.setDisable(true);
        if(EventStatus.get(currentTimeFrame).containsKey(Event.DISSOLVE.toString()))
            btnDissolved.setDisable(false);
        else
            btnDissolved.setDisable(true);
        if(EventStatus.get(currentTimeFrame).containsKey(Event.SURVIVE.toString()))
            btnSurvived.setDisable(false);
        else
            btnSurvived.setDisable(true);
        if(EventStatus.get(currentTimeFrame).containsKey(Event.MERGE.toString()))
            btnMerged.setDisable(false);
        else
            btnMerged.setDisable(true);
        if(EventStatus.get(currentTimeFrame).containsKey(Event.SPLIT.toString()))
            btnSplit.setDisable(false);
        else
            btnSplit.setDisable(true);
        if(EventStatus.get(currentTimeFrame).containsKey(Event.APPEAR.toString()))
            btnAppeared.setDisable(false);
        else
            btnAppeared.setDisable(true);
        if(EventStatus.get(currentTimeFrame).containsKey(Event.DISAPPEAR.toString()))
            btnDisappeared.setDisable(false);
        else
            btnDisappeared.setDisable(true);
        if(EventStatus.get(currentTimeFrame).containsKey(Event.JOIN.toString()))
            btnJoined.setDisable(false);
        else
            btnJoined.setDisable(true);
        if(EventStatus.get(currentTimeFrame).containsKey(Event.LEFT.toString()))
            btnLeft.setDisable(false);
        else
            btnLeft.setDisable(true);
    }
    
    /**
     *  Method Name     : hideDCPanel()
     *  Created Date    : 2016-06-29
     *  Description     : Hides the Dynamic Community Panel
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void hideDCPanel() {
        
        /*
        btnEvents.setDisable(false);
        btnFormed.setDisable(false);
        btnAppeared.setDisable(false);
        btnJoined.setDisable(false);
        btnDisappeared.setDisable(false);
        btnLeft.setDisable(false);
        btnSurvived.setDisable(false);
        btnDissolved.setDisable(false);
        btnSplit.setDisable(false);
        btnMerged.setDisable(false);
        
        // Set the height properties of the DC Panels
        SceneConfig.DCPANEL_BUTTONS_HEIGHT.set(0);
        SceneConfig.DCPANEL_SLIDER_HEIGHT.set(0);
        
        // Set the Height of the DC Panels
        anchDCSliderButton.setMinHeight(SceneConfig.DCPANEL_SLIDER_HEIGHT.get());
        anchEventsButtons.setMinHeight(SceneConfig.DCPANEL_BUTTONS_HEIGHT.get());
        
        anchDCSliderButton.setMaxHeight(SceneConfig.DCPANEL_SLIDER_HEIGHT.get());
        anchEventsButtons.setMaxHeight(SceneConfig.DCPANEL_BUTTONS_HEIGHT.get());
        
        // anchDCSliderButton.setVisible(false);
        // anchEventsButtons.setVisible(false);
        */
        
        // Remove the DC Mining Panel
        vboxApplication.getChildren().remove(anchDCMining);
          
    }
    
    /**
     *  Method Name     : disableDCPanel()
     *  Created Date    : 2016-06-29
     *  Description     : Disables the Dynamic Community Panel
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void disableDCPanel() {
        
        // Disable all the elements DC Panels
        vboxDCMining.setDisable(true);
        hboxDCSlider.setDisable(true);
        /*
        btnPreviousTimeFrame.setDisable(true);
        btnNextTimeFrame.setDisable(true);
        */
        btnEvents.setDisable(true);
        btnFormed.setDisable(true);
        btnAppeared.setDisable(true);
        btnJoined.setDisable(true);
        btnDisappeared.setDisable(true);
        btnLeft.setDisable(true);
        btnSurvived.setDisable(true);
        btnDissolved.setDisable(true);
        btnSplit.setDisable(true);
        btnMerged.setDisable(true);  
    }
    
    /**
     *  Method Name     : disableDCEventPanel()
     *  Created Date    : 2017-04-12
     *  Description     : Disables the Event buttons on the Dynamic Community Panel
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void disableDCEventPanel() {
        
        // Disable all the event buttons on the DC Panel
        btnEvents.setDisable(true);
        btnFormed.setDisable(true);
        btnAppeared.setDisable(true);
        btnJoined.setDisable(true);
        btnDisappeared.setDisable(true);
        btnLeft.setDisable(true);
        btnSurvived.setDisable(true);
        btnDissolved.setDisable(true);
        btnSplit.setDisable(true);
        btnMerged.setDisable(true);  
    }
    
    /**
     *  Method Name     : updateDCMiningPanel()
     *  Created Date    : 2016-06-29
     *  Description     : Updates the DC Panel based on the time frames of the available graphs
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pintTimeFrameIndex : int
     *  @param pinTotalTimeFrames : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateDCMiningPanel(int pintProjectID, int pintGraphID, int pintTimeFrameIndex, int pinTotalTimeFrames, boolean DCMiningStatus) {        
        if (pinTotalTimeFrames > 1) {
            showAndEnableDCPanel(pintProjectID, pintGraphID, pintTimeFrameIndex, DCMiningStatus);
        } else {
            hideDCPanel();
        }
    }
    
    /**
     *  Method Name     : updateDCMiningPanel()
     *  Created Date    : 2016-06-29
     *  Description     : Updates the DC Panel based on the time frames of the available graphs
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    
    public void updateDCMiningPanel() {
        if (UIInstance.getProjectTabCount() <= 0) {
            disableDCPanel();
        }else if(UIInstance.getActiveProjectTab().getGraphCount()>0){
            int pintProjectID = UIInstance.getActiveProjectID();
            int pintGraphID = UIInstance.getActiveProjectTab().getActiveGraphID();
            int pintTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            boolean DCMiningStatus = UIInstance.getActiveProjectTab().getActiveGraphTab().getDCommMiningStatus();
            showAndEnableDCPanel(pintProjectID, pintGraphID, pintTimeFrameIndex, DCMiningStatus);
        }
    }
    
    
    /**
     *  Method Name     : initiateMenuBar()
     *  Created Date    : 2015-07-28
     *  Description     : MenuBar components that are to be displayed on the Screen are initialized here
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void initiateMenuBar() {
        
        menuAnalysis.getMenus().clear(); ;
        
        /* Initializing the Menu Bar */
        menuAnalysis.getMenus().addAll(MenuBarUtilities.initiateMenuBar(this));
        /*
        // #DEBUG for MenuBar
        for (Menu optionMenu : lstMenu) {
            System.out.println(optionMenu.getText());
            for (MenuItem item : optionMenu.getItems()) {
                    System.out.println("\t"+item.getText());
            }
        }
        */
    }
    
    /**
     *  Method Name     : initiateToolsRibbon()
     *  Created Date    : 2016-01-04
     *  Description     : Initiates the Tools Ribbon below the MenuBar
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void initiateToolsRibbon() {
        
        hboxToolRibbon.setSpacing(0);
        hboxToolRibbon.setPadding(new Insets(0, 0, 0, 0));
        hboxToolRibbon.setOnMousePressed((MouseEvent mouseEvent) -> {
            ToolsRibbonContextMenu trcmInstance = ToolsRibbonContextMenu.getInstance(this);
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {                
                trcmInstance.Show(hboxToolRibbon, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                updateStatusBar(true, StatusMsgsConfig.TOOLSRIBBON_WANTTOHIDE);
            } else {
                trcmInstance.Hide();
                updateStatusBar(false, StatusMsgsConfig.OPERATION_CANCEL);
            }
        });
        
        // Add the Editing Tool Box
        EditingToolBox editToolBoxInstance = EditingToolBox.getInstance(this);
        hboxToolRibbon.getChildren().add(editToolBoxInstance.getEditingTools());
        editToolBoxInstance.activateEditingToolBox(false);
        
        Separator sepVert1 = new Separator();
        sepVert1.setOrientation(Orientation.VERTICAL);
        sepVert1.setValignment(VPos.CENTER);
        sepVert1.setPrefHeight(20);
        hboxToolRibbon.getChildren().add(sepVert1);
       
        // Adding the Snapshot Tool
        SnapshotToolBox snapshotToolBoxInstance = SnapshotToolBox.getInstance(this);
        hboxToolRibbon.getChildren().add(snapshotToolBoxInstance.getSnapshotTools());
        snapshotToolBoxInstance.activateSnapshotToolBox(false);
        
        
        Separator sepVert2 = new Separator();
        sepVert2.setOrientation(Orientation.VERTICAL);
        sepVert2.setValignment(VPos.CENTER);
        sepVert2.setPrefHeight(20);
        hboxToolRibbon.getChildren().add(sepVert2);
        
        
        // Adding the Vertex/Edge Size Tool
        SizeToolBox sizeToolBoxInstance = SizeToolBox.getInstance(this);
        hboxToolRibbon.getChildren().add(sizeToolBoxInstance.getSizeTools());
        
        Separator sepVert3 = new Separator();
        sepVert3.setOrientation(Orientation.VERTICAL);
        sepVert3.setValignment(VPos.CENTER);
        sepVert3.setPrefHeight(20);
        hboxToolRibbon.getChildren().add(sepVert3);
        
        // Adding the Vertex/Edge color Tool
        ColorToolBox colorToolBoxInstance = ColorToolBox.getInstance(this);
        hboxToolRibbon.getChildren().add(colorToolBoxInstance.getColorTools());
        
        Separator sepVert4 = new Separator();
        sepVert4.setOrientation(Orientation.VERTICAL);
        sepVert4.setValignment(VPos.CENTER);
        sepVert4.setPrefHeight(20);
        hboxToolRibbon.getChildren().add(sepVert4);
        
        IconToolBox iconToolBoxInstance = IconToolBox.getInstance(this);

        iconToolBoxInstance.disableIconToolbox();
        hboxToolRibbon.getChildren().add(iconToolBoxInstance.getIconTools());

    }
    
    public void showToolsRibbon () {
        getToolsRibbon().setManaged(true);
        ToolsRibbonContextMenu.setIsShown(true); 
        updateStatusBar(false, StatusMsgsConfig.TOOLSRIBBON_SHOW);
    }
    
    public void hideToolsRibbon () {
        getToolsRibbon().setManaged(false);
        ToolsRibbonContextMenu.setIsShown(false); 
        updateStatusBar(false, StatusMsgsConfig.TOOLSRIBBON_HIDE);
    }
    
    /**
     *  Method Name     : initiateProjectTabPane
     *  Created Date    : 2015-07-28
     *  Description     : The components of the visualization pane are initialized
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void initiateProjectTabPane() {
        UIUtilities.StretchToParent(tabpaneProject);
        addTabChangeListeners();
    }
    
    /**
     *  Method Name     : initiateThreadTreeView
     *  Created Date    : 2015-07-28
     *  Description     : Initialization of the thread tree components
     *  ERRORNOTE       : this listener has an error which is not fixed.
     *                    However, the error does not impact the normal functioning of
     *                    Meerkat application.
     *  ErrorDescription: When you select a project in treeview and then try selecting a graph of
     *                    ANOTHER project in the treeview, both listeners (onTabChange & TreeView)
     *                    are called multiple times and it throws a java.lang.IndexOutOfBoundsException
     *                    exception. Also, both listeners are called multiple times (twice or thrice) in any normal
     *                    meerkat execution. However, this is expected since there is a circular dependency.
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void initiateThreadTreeView () {        
        /* Add the thread tree items to the tree view after initializing it */
        treeviewThread = new TreeView<>(ThreadTree.getTreeInstance().getTreeItems());
        
        // Adding a listener to the Thread Tree
        treeviewThread.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            MeerkatTreeItem<String> selectedItem = (MeerkatTreeItem<String>) newValue;
            // System.out.println("AnalysisController ENTRY TreeView Listener");
            if (selectedItem != null && oldValue != null) {
                
                int intID = selectedItem.getID();
                int intProjectID = -1 ;
                int intGraphID  = -1 ;
                int intVertexID = -1 ;
                int intEdgeID = -1 ;
                int intTextTabID = -1 ;
                int intTextContentID = -1 ;
                TreeItemType itemType = selectedItem.getTreeItemType();
                
                // MeerkatUI UIInstance = MeerkatUI.getUIInstance();
                
                // System.out.println("AnalysisController.initiateThreadTreeView(): Selected Text : " + selectedItem.getValue()+"\n\t"+selectedItem.getTreeItemType()+"\n\tSelected Item: "+selectedItem);
                
                switch (itemType) {
                    case ROOTNODE:
                        // Do nothing in this case
                        break ;
                    case PROJECTTITLE :
                        intProjectID = intID ;
                        intGraphID = UIInstance.getProject(intProjectID).getActiveGraphID();
                        // System.out.println("From AnalysisController.initiateThreadTreeView() Switch Case GraphID = " + intGraphID + ", from project = " + UIInstance.getProject(intProjectID).getProjectName());
                        break ;
                    case GRAPHTITLE :
                        intGraphID = intID ;
                        // get the Parent
                        MeerkatTreeItem parentTreeItem = (MeerkatTreeItem)selectedItem.getParent();
                        if (parentTreeItem != null) {
                            intProjectID = parentTreeItem.getID();
                        }                     
                        break ;
                    case VERTEXTITLE :
                        MeerkatTreeItem vtxTitleparentGraph = (MeerkatTreeItem)selectedItem.getParent();
                        intGraphID = vtxTitleparentGraph.getID();
                        
                        MeerkatTreeItem vtxTitleparentProject = (MeerkatTreeItem)vtxTitleparentGraph.getParent() ;
                        intProjectID = vtxTitleparentProject.getID() ;
                        
                        break ;
                    case VERTICES :
                        MeerkatTreeItem vtxparentGraph = (MeerkatTreeItem)(MeerkatTreeItem)selectedItem.getParent().getParent();
                        intGraphID = vtxparentGraph.getID();
                        
                        MeerkatTreeItem vtxparentProject = (MeerkatTreeItem)vtxparentGraph.getParent() ;
                        intProjectID = vtxparentProject.getID() ;
                        break ;
                    case EDGETITLE :
                        MeerkatTreeItem edgeTitleparentGraph = (MeerkatTreeItem)selectedItem.getParent();
                        intGraphID = edgeTitleparentGraph.getID();
                        
                        MeerkatTreeItem edgeTitleparentProject = (MeerkatTreeItem)edgeTitleparentGraph.getParent() ;
                        intProjectID = edgeTitleparentProject.getID() ;
                        break ;
                    case EDGES :
                        MeerkatTreeItem edgeparentGraph = (MeerkatTreeItem)(MeerkatTreeItem)selectedItem.getParent().getParent();
                        intGraphID = edgeparentGraph.getID();
                        
                        MeerkatTreeItem edgeparentProject = (MeerkatTreeItem)edgeparentGraph.getParent() ;
                        intProjectID = edgeparentProject.getID() ;
                        break ;
                    case TEXTTITLE :
                        MeerkatTreeItem textTreeItem = (MeerkatTreeItem)selectedItem.getParent();
                        intTextTabID = textTreeItem.getID();
                        
                        MeerkatTreeItem textProjectTreeItem = (MeerkatTreeItem)textTreeItem.getParent();
                        intProjectID = textProjectTreeItem.getID();
                        break ;
                    case TEXTCONTENT :
                        
                        break ;
                }
                
                if (intProjectID > -1) {
                    UIInstance.setActiveProject(intProjectID);
                }
                ProjectTab currentProjectTab = UIInstance.getActiveProjectTab();
                
                // Show the corresponding Project and Graph   
                if (currentProjectTab != null) {                    
                    tabpaneProject.getSelectionModel().select(currentProjectTab.getProjectUITab());                     
                    // Select the ProjectID from the graph
                    if (intGraphID > -1 && intProjectID > -1) {
                        // System.out.println("From AnalysisController.initiateThreadTreeView() Calling setActiveGraphID with param = " + intGraphID + ", from project = " + UIInstance.getProject(intProjectID).getProjectName());
                        UIInstance.getProject(intProjectID).setActiveGraphID(intGraphID);                    
                    }
                    // System.out.println("AnalysisController.initiateThreadTreeView(): Selected ProjectID: "+intProjectID+"\tGraphID: "+intGraphID);
                    GraphTab currentGraphTab = currentProjectTab.getActiveGraphTab();
                    if (currentGraphTab != null) {
                        // System.out.println("From AnalysisController.initiateThreadTreeView(): ActiveGraphTab = not null = with id = " + currentGraphTab.getGraphID());
                        currentProjectTab.getGraphTabPane().getSelectionModel().select(currentGraphTab.getUITab());
                    }
                }
            }
        });
        /* Update the anchor */
        anchThreadView.getChildren().add(treeviewThread);    
        
        //treeviewThread.setEditable(true);
        treeviewThread.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
            @Override
            public TreeCell<String> call(TreeView<String> p) {
                return new TextFieldTreeCellImpl();
            }
        });
        
        UIUtilities.StretchToParent(treeviewThread);
        // System.out.println("AnalysisController EXIT TreeView Listener");
    } 
    
    /**
     *  Method Name     : initiateAccordionTabs
     *  Created Date    : 2015-07-28
     *  Description     : Initialize the Accordion Tabs
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void initiateAccordionTabs() {
        AccordionTabContents accordionTab = AccordionTabContents.getAccordionTabInstance();

        accordionTab.initiateAccordionTabs(accOptionsPane, accDetails, accLayouts, accFilters, accStatistics, accCommunities);        
    }

     /**
     *  Method Name     : addProject
     *  Created Date    : 2015-07-29
     *  Description     : Adds a project and updates the components in the AnalysisScreen
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pProjectTab : ProjectTab
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */    
    public void addProject(ProjectTab pProjectTab){
        // Add the Project to the TreeView
        treeviewThread.setRoot(ThreadTree.getTreeInstance().addProject(pProjectTab));            
        
        // Open all the graphs under the project tab in the drawing pane (canvas)
        tabpaneProject.getTabs().add(pProjectTab.getProjectUITab());
        tabpaneProject.getSelectionModel().select(pProjectTab.getProjectUITab());  
        tabpaneProject.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS); 
    }
    
    /**
     *  Method Name     : removeProject
     *  Created Date    : 2015-07-31
     *  Description     : Removes a project from the components
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pProjectTab : ProjectTab
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void removeProject(ProjectTab pProjectTab) {
        // Remove from the Thread Tree
        treeviewThread.setRoot(ThreadTree.getTreeInstance().removeProject(pProjectTab));
        
        // Remove the tab from the view area
    }
    
    
        /**
     *  Method Name     : initiateTabbedPane
     *  Created Date    : 2015-08-29
     *  Description     : Initiating the Listener Properties for the TabPane
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void addTabChangeListeners() {                
        // System.out.println("ProjectTab.initateTabbedPane(): Invoked");
        getProjectTabPane().getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Tab>() {
                @Override
                public void changed(ObservableValue<? extends Tab> ov, Tab tabSource, Tab tabDestination) {    
                    if (tabDestination != null) {
                        OnTabChange(tabDestination);
                    }
                }
            }
        );
        
        /*
        // Above function as Lambda Expression
        tabpaneGraph.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> ov, Tab tabSource, Tab tabDestination) -> {
            OnTabChange(tabDestination);
        });
        */
    }
    
    /**
     *  Method Name     : OnTabChange
     *  Created Date    : 2015-08-29
     *  Description     : Set of Actions to be taken when the tab has been changed by the user
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param ptabDestination : Tab
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-09-09      Talat           Code to make changes to the Accordion Tab when the Projects are changed
     * 
    */
    private void OnTabChange(Tab ptabDestination) {
        // ProjectID and the corresponding Tab ID are the same
        // Therefore, A ProjectTab can retrieved using the FX UI Tab
        int intProjectID = Integer.parseInt(ptabDestination.getId());
        
        // set activeProjectId in MeerkatUI to be ptabDestination's id
        UIInstance.setActiveProject(intProjectID);

        // Update the Thread Tree View
        getTreeView().getSelectionModel().select(UIInstance.getAllProject().get(intProjectID).getTreeItemProject());
        boolean DCMiningStatus = false;
        if(UIInstance.getActiveProjectTab().getGraphCount()>0)
            DCMiningStatus = UIInstance.getActiveProjectTab().getActiveGraphTab().getDCommMiningStatus();
        /*
        // Update the Accordion Tab
            1) Check if the project consists any graphs
            2) If present, get the active graph ID
            3) Update the Accordion Tabs
        */
        // Meerkat UIInstance = Meerkat.getMeerkatApplication();
        if (UIInstance.getProject(intProjectID).getGraphCount() > 0) {
            // System.out.println("AnalysisController.ontabChange() : destination project id = " + intProjectID + " ::: " + UIInstance.getProject(intProjectID) +" ::::: destination project name = " +UIInstance.getProject(intProjectID).getProjectName());
            // System.out.println("AnalysisController.ontabChange() : destination project id = " + intProjectID + " ::: " + UIInstance.getProject(intProjectID) +" ::::: " +UIInstance.getProject(intProjectID).getActiveGraphID());
            int intGraphID = UIInstance.getProject(intProjectID).getActiveGraphTab().getGraphID();
            // System.out.println("AnalysisController.ontabChange() : destination project id = " + intProjectID + ", activeGraphId = " + intGraphID);
            //System.out.println("AnalysisController.ontabChange() : ");
            int intTimeFrameIndex = UIInstance.getProject(intProjectID).getGraphTab(intGraphID).getTimeFrameIndex();
            int intTotalTimeFrames = UIInstance.getProject(intProjectID).getGraphTab(intGraphID).getTotalTimeFrames() ;
            
            AccordionTabContents accordionTab = AccordionTabContents.getAccordionTabInstance();
            accordionTab.updateAccordionTabs(
                  UIInstance.getController().getAccordion()
                , UIInstance.getController().getDetails()
                , UIInstance.getController().getLayouts()
                , UIInstance.getController().getFilters()
                , UIInstance.getController().getStatistics()
                , UIInstance.getController().getCommunities()
                , intProjectID
                , intGraphID
                , intTimeFrameIndex
                , UIInstance.getProject(intProjectID).getGraphTab(intGraphID)
                        .getAccordionTabValues());
            
            updateDCMiningPanel(intProjectID, intGraphID, intTimeFrameIndex, intTotalTimeFrames, DCMiningStatus) ;
        }
    }
    
    
    
    
    public void updateProjectUI(ProjectTab pProjectTab) {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
        UIInstance.UpdateUI();
        
        /* 
            Components to be updated in the project 
            1) Adding to the Thread tree View            
            2) Updating the Canvas
            3) Updating the Options Pane
        */
        
        /*
        System.out.println(treeitemsRoot.getValue()+"\t");
        
        for (TreeItem<String> currentItem : treeitemsRoot.getChildren()) {
            System.out.println(currentItem.getValue());
            if (currentItem.getValue().equalsIgnoreCase(pProjectTab.getProjectName())) {
                treeitemsRoot.getChildren().remove(currentItem);
                treeitemsRoot.getChildren().add(pProjectTab.getTreeItemProject());
                break;
            }
        }
        treeviewThread.setRoot(treeitemsRoot);
        */
        
    }    
    
        
    // DEPRECATED
    /**
     *  Method Name     : addNewProjectAsTab()
     *  Created Date    : 2015-07-21
     *  Description     : To add a tab for a Project to the existing TabPane
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pProjectTab: ProjectTab
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void addProjectAsTab(ProjectTab pProjectTab) {        
        
        TabPane tabpaneGraph = null;        
        // tabpaneGraph = pProjectTab.getGraphTabPane();        
        
        /* 
            SEQUENCE OF STEPS 
            1) Create a tab for the project
            2) Add an anchor pane to the tab
            3) Add a GraphTabPane of TabbedPane type - This would be available in the GraphTab DataStructure itself
            4) Add a tab (graph) for each available graph            
            5) Draw the Graph on Canvas if required
        */
        AnchorPane ancGraphPane = new AnchorPane(); // Anchor Pane that would hold the Graph Tabbed Pane        
        ancGraphPane.getChildren().add(null); // Making the GraphPane of the project to be the child of the AnchorPane
        UIUtilities.StretchToParent(tabpaneGraph);
        
        Tab currentProjectTab = new Tab();
        currentProjectTab.setId(Integer.toString(pProjectTab.getProjectID()));
        currentProjectTab.setText(pProjectTab.getProjectName());
        currentProjectTab.setContent(ancGraphPane);     
        currentProjectTab.setClosable(true);        
                        
        tabpaneProject.getTabs().add(currentProjectTab);
        tabpaneProject.getSelectionModel().select(currentProjectTab);  
        tabpaneProject.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS); 
        
        
        /* If there exists any graph in a project, add the graphs to the graph's Tabbed Pane */
        pProjectTab.getAllGraphTabs().values().stream().forEach((currentGraph) -> {            
            addNewGraphAsTab(tabpaneGraph, currentGraph);
        });
        
        // Just for Demo
        addNewGraphAsTab(tabpaneGraph, pProjectTab.getActiveGraphTab());
        
        /* 
            Once the New Project is created, update other structures in the Analysis Window
            1) Accordion Tabs - All Five Tabs Details, Filters, Communities, Layouts and Statistics
            2) Update the ThreadTree View with the current Project
        */
        // this.populateComponents(pProjectTab);
        
        /* ONLY FOR TESTING PURPOSE */
        String strOutputPath = new File("").getAbsolutePath()+"/output1.json";
        // System.out.println("AnalysisController.addNewProjectsAsTab(): Graph Output File: "+strOutputPath);
        List<GraphElement> lstElements = D3Json.Parse(strOutputPath);

        /* 
        // Debug to check the output
        lstElements.stream().forEach((element) -> {
            System.out.println(element.getElementType()+"\t"+element.getMeerkatID()+"\tAttributes: "+element.getAttributes().size());
        });
        */
    }
    
    
    
    public void initiateGraphTabPane(TabPane ptabpaneGraph) {
        UIUtilities.StretchToParent(ptabpaneGraph);
        ptabpaneGraph.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends Tab> ov, Tab tabSource, Tab tabDestination) -> {
                    //updateOnGraphTabChange(tabDestination);
        });
    }
    
    public void addGraphTabToProjectTab (ProjectTab pProjectTab) {
        
        /*
            Since this is the first graph that is created for the project that is sent as the parameter, 
            a tab is to be added to the tabpaneProject and the Graph appended to it
        */
        

        
        /* Add the tab */
        
    }
    
    
    /*
    public void updateOnGraphTabChange(Tab ptabDestination) {
        strCurrentTabID = ptabDestination.getId();
        populateAccordionTabs();
        // populateThreadTreeView(UIInstance.getActiveProject().getProjectName());
    }
    
    public void updateOnProjectTabChange(Tab ptabDestination) {
        strCurrentTabID = ptabDestination.getId();
        populateAccordionTabs();
        // populateThreadTreeView(UIInstance.getActiveProject().getProjectName());
    }
    
    public void populateComponents(GraphTab pGraphTab) {
        populateAccordionTabs();
        // populateThreadTreeView(UIInstance.getActiveProject().getProjectName());
        addProjectToThreadTree(UIInstance.getActiveProject().getProjectID());
        System.out.println("AnalysisController.populateComponents(): "+UIInstance.getActiveProject().getProjectName());
    }
    
    public void populateComponents(ProjectTab pProjectTab) {
        populateAccordionTabs();
        addProjectToThreadTree(pProjectTab.getProjectID());
        System.out.println("AnalysisController.populateComponents(): "+UIInstance.getActiveProject().getProjectName());
    }
    */
    
    
    

    
    /**
     *  Method Name     : addNewGraphAsTab()
     *  Created Date    : 2015-07-21
     *  Description     : To add a tab for a Graph to the existing TabPane
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pGraphTab: GraphTab
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void addNewGraphAsTab(TabPane pTabPaneGraph, GraphTab pGraphTab) {
        /* Add the webview and engine for this tab */
        WebView wvCurrent = null;
        // = UIInstance.getActiveProjectTab().getActiveGraphTab().getWebView();
        WebEngine weCurrent = null ;
        // = UIInstance.getActiveProjectTab().getActiveGraphTab().getWebEngine();
        
        AnchorPane ancGraph = new AnchorPane(); // This anchor would hold the WebView
        ancGraph.widthProperty().addListener(new ChangeListener<Number>() {
            @Override 
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {                
                if (Math.abs(oldSceneWidth.doubleValue() - newSceneWidth.doubleValue()) > 25.0) {
                    // updateMainGraph(getActiveWebEngine());
                }
            }
        });
        ancGraph.heightProperty().addListener(new ChangeListener<Number>() {
            @Override 
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                if (Math.abs(oldSceneHeight.doubleValue() - newSceneHeight.doubleValue()) > 25.0) {
                    // updateMainGraph(getActiveWebEngine());
                }                
            }
        });
        
        ancGraph.getChildren().add(wvCurrent);
        UIUtilities.StretchToParent(wvCurrent);
        
        displayMainGraph(weCurrent);
        
        Tab currentTab = new Tab();
        currentTab.setId(Integer.toString(pGraphTab.getGraphID()));
        currentTab.setText(pGraphTab.getGraphTabTitle());
        currentTab.setContent(ancGraph);     
        currentTab.setClosable(true);
                        
        pTabPaneGraph.getTabs().add(currentTab);
        pTabPaneGraph.getSelectionModel().select(currentTab);  
        pTabPaneGraph.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);        
    }
    
    
    /**
     *  Method Name     : displayMainGraph()
     *  Created Date    : 2015-07-22
     *  Description     : Displays the MainGraph in the canvas area using JavaScript
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pweMainGraph: WebEngine - WebEngine that displays the graph 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void displayMainGraph(WebEngine pweMainGraph) {
        String strCallingMethod = "AnalysisController.displayMainGraph(): ";
        /*
        final URL urlLoadMainGraph = getClass().getResource("html/MainGraph.html");        
        pweMainGraph.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState == State.SUCCEEDED) {
                
                // #Debug                
                System.out.println(strCallingMethod+"Width and Height of the WebView: " 
                        + UIInstance.getActiveProject().getActiveGraphTab().getWebView().getWidth()
                        + " & "
                        + UIInstance.getActiveProject().getActiveGraphTab().getWebView().getHeight()
                    );
                
                // String strJsonFile = "../data/test.meerkat.json";
                pweMainGraph.executeScript(" doEverything(' " 
                        + UIInstance.getActiveProject().getActiveGraphTab().getGraphOutputFile() + " ', "
                        + UIInstance.getActiveProject().getActiveGraphTab().getWebView().getWidth() + ","
                        + UIInstance.getActiveProject().getActiveGraphTab().getWebView().getHeight() + ","
                        + UIInstance.getActiveProject().getActiveGraphTab().IsFisheyeLens() + ") ");
            }
        });
        pweMainGraph.load(urlLoadMainGraph.toExternalForm());        
        */
    }   
    
    public void updateMainGraph(WebEngine weMainGraph) {
        // /setCurrentDataFile(pstrDataFile + ".json");
        /*
        final URL urlLoadMainGraph = getClass().getResource("html/MainGraph.html");
        weMainGraph.executeScript(" doEverything(' "
                        + UIInstance.getActiveProject().getActiveGraphTab().getGraphOutputFile() + " ', "
                        + UIInstance.getActiveProject().getActiveGraphTab().getWebView().getWidth() + ","
                        + UIInstance.getActiveProject().getActiveGraphTab().getWebView().getHeight() + ","
                        + UIInstance.getActiveProject().getActiveGraphTab().IsFisheyeLens() + ") ");
        weMainGraph.load(urlLoadMainGraph.toExternalForm());     
        */
    }
            
    /* IMPORTANT NOTE: Soon to be removed - using only for testing purpose */
    /*
    public void initiateHistogram () {
        weHistogram = wvHistogram.getEngine();
        final URL urlLoadHistogram = getClass().getResource("html/Histogram.html");
        weHistogram.load(urlLoadHistogram.toExternalForm());
    }
    */
   
    /* IMPORTANT NOTE: Soon to be removed - using only for testing purpose */
    /*
    public void initiateLineGraph () {
        weLineGraph = wvLineGraph.getEngine();
        final URL urlLoadLineGraph = getClass().getResource("html/LineGraph.html");
        weLineGraph.load(urlLoadLineGraph.toExternalForm());
    } 
    */
    
    
    /**
     *  Method Name     : addProjectToThreadTree()
     *  Created Date    : 2015-07-22
     *  Description     : Adds the Project to the TreeView by requesting the information from the BIZ
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID: int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void addProjectToThreadTree(int pintProjectID) {
        
        // treeitemsRoot = ThreadTree.addProject(UIInstance.getActiveProject().getProjectType());
        // Update the treeview with the file that is loaded
        // treeviewThread = threadtree.ThreadTree.initiateThreadTreeView(treeviewThread, pstrProjectName);
        
        UIUtilities.StretchToParent(treeviewThread);
    }
    
    /**
     *  Method Name     : runProgressBar()
     *  Created Date    : 2016-01-13
     *  Description     : If the parameter is true, the progress bar is animated, else it just fills up
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pblnIsRunning: boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
     * 
    */
    private void runProgressBar(boolean pblnIsRunning) {
        /*
        if (!pblnIsRunning) {
            pbarStatus.setProgress(0);
        } else {
            pbarStatus.setProgress(-1.0);
        }
        */
    }
    
    
    /**
     *  Method Name     : updateStatusBar()
     *  Created Date    : 2016-01-14
     *  Description     : Updates both the status label and the progress bar
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pblnIsRunning: boolean
     *  @param pstrStatusText : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void updateStatusBar(boolean pblnIsRunning, String pstrStatusText) {
        setStatus(pstrStatusText);
        runProgressBar(pblnIsRunning);        
    }
    
    /**
     *  Method Name     : ActivateKeyEventListener()
     *  Created Date    : 2017-07-24
     *  Description     : activates all the KeyEvent listener
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
    */
    
    public void ActivateKeyEventListener(){
        KeyEventListener keyListener = new KeyEventListener(this);
    }
    
}
