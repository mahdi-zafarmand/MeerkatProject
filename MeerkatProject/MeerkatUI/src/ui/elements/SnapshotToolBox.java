/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.elements;

import analysisscreen.AnalysisController;
import config.GraphEditingToolsConfig;
import config.SnapshotToolboxConfig;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import toolbox.snapshot.ApplicationSnapshot;
import toolbox.snapshot.CurrentProjectAllGraphs;
import toolbox.snapshot.FullGraphSnapshot;
import toolbox.snapshot.VisibleGraphArea;
import ui.dialogwindow.SnapshotOptionsDialog;

/**
 *  Class Name      : SnapshotToolBox
 *  Created Date    : 2016-01-07
 *  Description     : Displays the Snapshot Tool Box for editing the graph
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class SnapshotToolBox {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private ToggleImageButton tbCompleteApp ;
    private ToggleImageButton tbAllProjects ;
    private ToggleImageButton tbCurrentProject ;
    private ToggleImageButton tbEntireGraph ;
    private ToggleImageButton tbVisibleGraphPart ;
   
    private final ToggleGroup grpSnapshotTools ;
    
    private final Label lblSnapshotTools ;
    private final HBox hboxSnapshotTools ;
    private final VBox vboxSnapshotTools ;
    
    private static SnapshotToolBox instance;
    
    private static AnalysisController pAnalysisController ;
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public VBox getSnapshotToolsWithLabel () {
        return vboxSnapshotTools;
    }
    
    public HBox getSnapshotTools() {
        return hboxSnapshotTools;
    }   
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */

    /**
     *  Constructor Name: SnapshotToolBox()
     *  Created Date    : 2016-01-04
     *  Description     : Constructor for SnapshotToolBoox - Initiates all the components and their behaviour within the editing tool box
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */

    private SnapshotToolBox() {
        
        grpSnapshotTools = new ToggleGroup();        
                        
        // Adding the toggle buttons to the toggle group
        tbCompleteApp = new ToggleImageButton(SnapshotToolboxConfig.getAppSnapshot_imageURL());
//        Image imgCompleteApp = new Image(SnapshotToolboxConfig.getAppSnapshot_imageURL());
//        tbAddEdge = new ToggleImageButton("A", new ImageView(imgAddEdge));
//        tbCompleteApp = new ToggleImageButton(new ImageView(imgCompleteApp));
        tbCompleteApp.setTooltip(new Tooltip(SnapshotToolboxConfig.getAppSnapshot_tooltip()));
        tbCompleteApp.setToggleGroup(grpSnapshotTools);
        tbCompleteApp.setOnAction(a -> {
            ApplicationSnapshot.SaveAsPNG(pAnalysisController);
            System.out.println("SnapshotToolBox.SnapshotToolBox(): Snapshot of the complete application");
        });
                
        tbAllProjects = new ToggleImageButton(SnapshotToolboxConfig.getAllOpenGraphsAllProjects_ImageURL());
//        Image intAllProjects = new Image(SnapshotToolboxConfig.getAllOpenGraphsAllProjects_ImageURL());
//        tbAddVertex = new ToggleImageButton("B", new ImageView(imgAddVertex));
//        tbAllProjects = new ToggleImageButton(new ImageView(intAllProjects));
        tbAllProjects.setTooltip(new Tooltip(SnapshotToolboxConfig.getAllOpenGraphsAllProjects_ToolTip()));
        tbAllProjects.setToggleGroup(grpSnapshotTools);
        tbAllProjects.setOnAction(a -> {
            // Display a window that would display all the activities
            // AllProjectsAllGraphs.SaveAsPNG(pAnalysisController);
            SnapshotOptionsDialog.Display();
            System.out.println("SnapshotToolBox.SnapshotToolBox(): All the graphs and all the projects");
        });
        
        tbCurrentProject = new ToggleImageButton(SnapshotToolboxConfig.getAllOpenGraphActiveProject_ImageURL());
//        Image imgCurrentProject = new Image(SnapshotToolboxConfig.getAllOpenGraphActiveProject_ImageURL());
//        tbDelete = new ToggleImageButton("C", new ImageView(imgDelete));
//        tbCurrentProject = new ToggleImageButton(new ImageView(imgCurrentProject));
        tbCurrentProject.setTooltip(new Tooltip(SnapshotToolboxConfig.getAllOpenGraphsActiveProject_ToolTip()));
        tbCurrentProject.setToggleGroup(grpSnapshotTools);
        tbCurrentProject.setOnAction(a -> {
            CurrentProjectAllGraphs.SaveAsPNG(pAnalysisController);
            System.out.println("SnapshotToolBox.SnapshotToolBox(): Current Project All Graphs");
        });
         
        tbEntireGraph = new ToggleImageButton(SnapshotToolboxConfig.getCurrentGraphComplete_ImageURL());
//        Image imgEntireGraph = new Image(SnapshotToolboxConfig.getCurrentGraphComplete_ImageURL());
//        tbSelect = new ToggleImageButton("D", new ImageView(imgSelect));
//        tbEntireGraph = new ToggleImageButton(new ImageView(imgEntireGraph));
        tbEntireGraph.setTooltip(new Tooltip(SnapshotToolboxConfig.getCurrentGraphComplete_ToolTip()));
        tbEntireGraph.setToggleGroup(grpSnapshotTools);
        tbEntireGraph.setOnAction(a -> {
            System.out.println("SnapshotToolBox.SnapshotToolBox(): Complete graph");
            FullGraphSnapshot.SaveAsPNG(pAnalysisController);
        });
        
        tbVisibleGraphPart = new ToggleImageButton(SnapshotToolboxConfig.getCurrentGraphViewOnly_ImageURL());
//        Image imgVisibleGraphPart = new Image(SnapshotToolboxConfig.getCurrentGraphViewOnly_ImageURL());
//        tbSelectMulti = new ToggleImageButton("E", new ImageView(imgMultiSelect));
//        tbVisibleGraphPart = new ToggleImageButton(new ImageView(imgVisibleGraphPart));
        tbVisibleGraphPart.setTooltip(new Tooltip(SnapshotToolboxConfig.getCurrentGraphViewOnly_ToolTip()));
        tbVisibleGraphPart.setToggleGroup(grpSnapshotTools);
        tbVisibleGraphPart.setOnAction(a -> {
            VisibleGraphArea.SaveAsPNG(pAnalysisController);
            System.out.println("SnapshotToolBox.SnapshotToolBox(): Visible Part of the Graph Only");
        });
        
        // hboxSnapshotTools = new HBox(tbCompleteApp, tbAllProjects, tbCurrentProject, tbEntireGraph, tbVisibleGraphPart);
        hboxSnapshotTools = new HBox(tbCompleteApp, tbAllProjects);
        hboxSnapshotTools.setAlignment(Pos.CENTER);
        hboxSnapshotTools.setSpacing(2);
        hboxSnapshotTools.setPadding(new Insets(0, 0, 0, 0));
        hboxSnapshotTools.setStyle(
                          "-fx-padding: 0;"
                        + "-fx-border-style: solid outside;"
                        + "-fx-border-width: 1;"
                        + "-fx-border-insets: 0;"
                        + "-fx-border-radius: 0;"
                        + "-fx-border-style: dashed;"
                        + "-fx-border-color: #AAAAAA;"
        );
        
        
        // Initializing the label to be displayed at the top of the editing tool box
        lblSnapshotTools = new Label();
        
        // Adding the label and the toggle group of buttons in a VBox to be displayed on the tool ribbon
        vboxSnapshotTools = new VBox(lblSnapshotTools, hboxSnapshotTools);
        vboxSnapshotTools.setAlignment(Pos.CENTER);
        vboxSnapshotTools.setStyle(
                          "-fx-padding: 2;"
                        + "-fx-border-style: solid outside;"
                        + "-fx-border-width: 2;"
                        + "-fx-border-insets: 1;"
                        + "-fx-border-radius: 1;"
                        + "-fx-border-style: dashed;"
                        + "-fx-border-color: black;"
        );
    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : activateSnapshotToolBox()
     *  Created Date    : 2016-01-07
     *  Description     : Activates / Deactivates all the toggle buttons in the toggle group
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pblnActiveState : boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void activateSnapshotToolBox(boolean pblnActiveState) {
                
        for (Toggle t : grpSnapshotTools.getToggles()) {  
            if (t instanceof ToggleImageButton) {  
                ((ToggleImageButton) t).setDisable(!pblnActiveState);  
            }  
        } 
    }
    
    
    /**
     *  Method Name     : getInstance()
     *  Created Date    : 2016-01-07
     *  Description     : Returns the only instance of SnapshotToolBox Object
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return SnapshotToolBox
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static SnapshotToolBox getInstance() {
        if (instance == null) {
            instance = new SnapshotToolBox();
        }
        return instance;
    }
    
    
    
    /**
     *  Method Name     : getInstance()
     *  Created Date    : 2016-01-07
     *  Description     : Returns the only instance of SnapshotToolBox Object
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     *  @return SnapshotToolBox
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static SnapshotToolBox getInstance(AnalysisController pController) {
        pAnalysisController = pController;
        if (instance == null) {
            instance = new SnapshotToolBox();
        }
        return instance;
    }
}
