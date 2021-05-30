/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import ca.aicml.meerkat.api.analysis.LayoutAPI;
import config.LangConfig;
import config.LayoutConfig;
import config.LayoutGroupConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import layout.LayoutElements;
import layout.LayoutGroup;
import layout.LayoutSet;

/**
 *  Class Name      : LayoutValues
 *  Created Date    : 2016-06-02
 *  Description     : The Values specific to a graph for the Layouts
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/

public class LayoutValues {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    private String strTitle;
    // private List<LayoutGroup> lstlytGroup;

    private Button btnRun;
    private Button btnStop;
    private GridPane grid;
    private String strSelectedLayoutID;
    private LayoutGroup lgSelectedLayoutGroup ;
    private LayoutElements lytSelectedLayout ;
    private String[] arrstrParameters;
    private boolean blnIsBusyWaiting = true;
    
    private Task task;
    
    VBox vboxContainer ;


    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */

    /*
    public List<LayoutGroup> getAllLayouts() {
        return this.lstlytGroup;
    }
    */
    
    public VBox getLayoutContainer() {
        return this.vboxContainer;
    }

    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    /**
     *  Constructor Name: LayoutValues()
     *  Created Date    : 2016-06-03
     *  Description     : Constructor to initialize the components
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
    */
    public LayoutValues (int pintProjectID, int pintGraphID, int pintTimeFrameIndex) {
         
        this.initializeComponents();
        
        // LayoutContent pLayout = (LayoutContent) pobjContent;        
        grid.setVgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5));
        int intRowPosition = 0; /* The row number in which a particular element is to be added */

        final ToggleGroup layoutToggle = new ToggleGroup();

        /* 
         For each of the layout group (such as Standard, Metric Layouts), 
         add the group along with its elements  to the Accordion Tab
         */
        
        LayoutSet lytSet = LayoutSet.getInstance();        
        for (LayoutGroup currentGroup : lytSet.getAllLayouts()) {
            grid.add(new Label(currentGroup.getTitle()), 0, intRowPosition);
            intRowPosition++;
            
            /* For each of the element in the layout group, display it on the screen and set the onSelect property */
            for (LayoutElements currentElement : currentGroup.getAllLayoutElements()) {

                RadioButton rbLayout = new RadioButton(currentElement.getText());
                rbLayout.setOnAction(e -> { // Action to be done on selecting the radio button                
                    try {
                        lgSelectedLayoutGroup = currentGroup;
                        lytSelectedLayout = currentElement ;
                        strSelectedLayoutID = currentElement.getID(); // strDisplayText is the Key and strLayoutID is the value
                        arrstrParameters[0] = currentElement.getParameter();                        

                        // Enable the run button
                        btnRun.setDisable(false);
                    } catch (SecurityException | IllegalArgumentException ex) {
                        System.out.println("LayoutValues.LayoutValues(): EXCEPTION ");
                        ex.printStackTrace();
                    }
                });

                rbLayout.setToggleGroup(layoutToggle);
                grid.add(rbLayout, 0, intRowPosition);
                intRowPosition++;
            }
        }

        btnRun.setDisable(false);
        btnStop.setDisable(true);

        HBox hboxRun = new HBox(btnRun, btnStop);
        hboxRun.setSpacing(30);
        hboxRun.setAlignment(Pos.CENTER);

        // Events to happen on pressing the RUN Button
        btnRun.setOnAction((ActionEvent e) -> {
            
            // if there is a class existing
            
            // -----------
            if (lytSelectedLayout.getLayoutClass() != null && !lytSelectedLayout.getLayoutClass().isEmpty()) {
                String strClassName = "layout."+lytSelectedLayout.getLayoutClass();
                Class clsMenuItem = null ;
                try {
                    clsMenuItem = Class.forName(strClassName);
                    // Constructor constructor = clsMenuItem.getConstructor(String.class, String.class, String.class);
                    // Object objMenuItem = constructor.newInstance(strParam_Display, strParam_Class, strParam_IconPath);

                    // Call the Click Method
                    Class[] paramTab = new Class[5];
                    paramTab[0] = Integer.class;
                    paramTab[1] = Integer.class;
                    paramTab[2] = Integer.class;
                    paramTab[3] = String.class;
                    paramTab[4] = LayoutValues.class ;

                    Method methodDisplay = clsMenuItem.getMethod("Display", paramTab);
                    methodDisplay.invoke(null, pintProjectID, pintGraphID, pintTimeFrameIndex, strSelectedLayoutID, this);
                    
                    System.out.println("LayoutValues(): After getParameters");
                } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | 
                        IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    System.out.println("LayoutValues.LayoutValues(): EXCEPTION") ;
                    ex.printStackTrace();
                }
            } else {            
                runLayoutAlgorithm(pintProjectID, pintGraphID, pintTimeFrameIndex, arrstrParameters) ;
            }
            
            //Update Project Status
            ProjectStatusTracker.updateProjectModifiedStatus(pintProjectID, ProjectStatusTracker.eventLayoutDone);

            
        });

        // Events to happen on pressing the STOP Button
        btnStop.setOnAction(e -> {
            task.cancel();
            LayoutAPI.stopAlgorithm(pintProjectID, pintGraphID, pintTimeFrameIndex);
            System.out.println("\t\t\t\t ----------------- LayoutValues() constructor task status after pressing stop button = " + task.getState());
            grid.setDisable(false); // Free the Panel with all the layouts
            btnRun.setDisable(false);
            btnStop.setDisable(true);           
        });

        /* Adding the Accordion a Scroll Pane to allow scrolling when the number of layouts go beyond the space */
        ScrollPane scroll = new ScrollPane();
        // scroll.setPrefHeight(paccParentAccordionPane.getHeight());
        // scroll.prefWidth(paccParentAccordionPane.getWidth());
        scroll.setContent(grid);

        vboxContainer = new VBox(scroll, hboxRun);
        vboxContainer.setSpacing(10);
    }


    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : runLayoutAlgorithm()
     *  Created Date    : 2016-07-14
     *  Description     : Runs the Layout Algorithm
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
    public void runLayoutAlgorithm(int pintProjectID, int pintGraphID, int pintTimeFrameIndex, String [] parrParameters) {
        
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            System.out.println("LayoutValues: Layout Group Type: "+lgSelectedLayoutGroup.getTitle());
            // Pinning of Vertices
            if (lgSelectedLayoutGroup.getLayoutGroupType() == LayoutGroupConfig.LayoutGroupType.METRIC) {
                UIInstance.getActiveProjectTab().getActiveGraphTab().pinVertexToCanvas() ;
                
            } else {
                UIInstance.getActiveProjectTab().getActiveGraphTab().unpinVertexToCanvas() ;
            }             
            
            // Call the API to load the implementation of the specific LayoutContentString [] arrstrAdditionalParameters = new String() {c}
            LayoutAPI.runLayout(pintProjectID, pintGraphID, pintTimeFrameIndex,
                    strSelectedLayoutID, parrParameters);

            try {

                grid.setDisable(true); // Free the Panel with all the layouts
                btnRun.setDisable(true);
                btnStop.setDisable(false);

                // Update the Canvas                
                UIInstance.getController().updateStatusBar(true, StatusMsgsConfig.LAYOUT_RUNNING);
                
                // Do the Busy Waiting
                task = new Task<Void>() {
                    @Override
                    public Void call() throws Exception {
                        while (!this.isCancelled() && !LayoutAPI.isDone(pintProjectID, pintGraphID,
                                pintTimeFrameIndex, strSelectedLayoutID, null)) {
                            System.out.println("LayoutContent: Task Calling...");
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (LayoutAPI.isDone(pintProjectID, pintGraphID,
                                pintTimeFrameIndex, strSelectedLayoutID, null)) {
                                        return;
                                        
                                    }
                                    UIInstance.getProject(pintProjectID)
                                        .getGraphTab(pintGraphID).updateCanvas();

                                    System.err.println("LayoutContent: "
                                            + "Updating Canvas in a loop");
                                }
                            });
                            
                            
                            Thread.sleep(getTimeToUpdateCanvas(pintProjectID, pintGraphID, pintTimeFrameIndex));
                        }
                        
                        Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                        UIInstance.getProject(pintProjectID)
                                        .getGraphTab(pintGraphID).updateCanvas();
                        System.err.println("LayoutContent.btnRun(): " + 
                                new Date() + ": Algorithm Completed");
                        grid.setDisable(false); // Free the Panel with all the layouts
                        btnRun.setDisable(false);
                        btnStop.setDisable(true);
                                }});
                        return null;
                    }
                };
                Thread th = new Thread(task);
                th.setDaemon(true);
                th.start();

                UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.LAYOUT_COMPLETED);
            } catch (Exception ex) {
                UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.LAYOUT_STOPPED);
                UIInstance.getProject(pintProjectID)
                                        .getGraphTab(pintGraphID).updateCanvas();
                grid.setDisable(false); // Free the Panel with all the layouts
                btnRun.setDisable(false);
                btnStop.setDisable(true);
            }
    }
    public int getTimeToUpdateCanvas(int pintProjectID, int pintGraphID, int pintTimeFrameIndex) {
        
                            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
                            int noOfVertices = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas(pintTimeFrameIndex).getNoOfVertices();
                            int noOfEdges = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas(pintTimeFrameIndex).getNoOfEdges();
                            int timeToUpdateCanvasUpdateInMillis = 0;
                            if(noOfVertices < LayoutConfig.crticalNoOfVerticesOnCanvas && noOfEdges < LayoutConfig.crticalNoOfEdgesOnCanvas){
                                timeToUpdateCanvasUpdateInMillis = LayoutConfig.timeToUpdateCanvasUpdateInMillis_SmallCanvasSize;
                            }else{
                                timeToUpdateCanvasUpdateInMillis = LayoutConfig.timeToUpdateCanvasUpdateInMillis_BigCanvasSize;
                            }
                            return timeToUpdateCanvasUpdateInMillis;
    
    }
    public void initializeComponents() {    
        
        this.arrstrParameters = new String[2];
        
        this.grid = new GridPane();        
        this.btnStop = new Button(LangConfig.GENERAL_STOP);
        this.btnRun = new Button(LangConfig.GENERAL_RUN);
    }
    
    /**
     *  Method Name     : updateLayoutValues()
     *  Created Date    : 2016-06-03
     *  Description     : Updates the Layout Values
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
    public void updateLayoutValues(int pintProjectID, int pintGraphID, int pintTimeFrameIndex) {
        // DO something here
    }
    
}
