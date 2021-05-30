/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *  Class Name      : StatusMsgsConfig
 *  Created Date    : 2016-01-14
 *  Description     : Stores a list of Status Msgs that would be displayed in the status bar
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-01-28      Talat           Added the Tags for VertexLabels and Tooltips
 *  2016-01-18      Talat           Added the Tags for Tools Ribbon, Application Parameters and About Meerkat
 *  2016-01-15      Talat           Added the Tags for Snapshots
 *  2016-01-14      Talat           Added the Tags for Graph Editing Tools 
 * 
*/
public class StatusMsgsConfig {
    
    public static String STATUSMSGS_TAG = "StatusMessages";
    
    // OPERATION TAGS
    public static String OPERATION_CANCEL_TAG = "OperationCancelled" ;
    public static String OPERATION_CANCEL ;
    
    public static String OPERATION_FAILED_TAG = "OperationFailed" ;
    public static String OPERATION_FAILED ;
    
    // WINDOW OPEN
    public static String WINDOW_OPENED_TAG = "WindowOpened" ;
    public static String WINDOW_OPENED ;
    
    public static String WINDOW_OPENING_TAG = "WindowOpening" ;
    public static String WINDOW_OPENING ;
    
    // WINDOW CLOSE
    public static String WINDOW_CLOSED_TAG = "WindowClosed" ;
    public static String WINDOW_CLOSED ;
    
    public static String WINDOW_CLOSING_TAG = "WindowClosing" ;
    public static String WINDOW_CLOSING ;
    
    // SAVING CHANGES
    public static String CHANGES_SAVING_TAG = "ChangesSaving";
    public static String CHANGES_SAVING ;
    
    public static String CHANGES_SAVED_TAG = "ChangesSaved";
    public static String CHANGES_SAVED ;
    
    // DISCARDING CHANGES
    public static String CHANGES_DISCARDING_TAG = "ChangesDiscarding";
    public static String CHANGES_DISCARDING ;
    
    public static String CHANGES_DISCARDED_TAG = "ChangesDiscard";
    public static String CHANGES_DISCARDED ;
    
    // APPLICATION TAGS
    public static String APPLICATION_OPENED_TAG = "ApplicationOpen" ;
    public static String APPLICATION_OPENED ;
    
    public static String APPLICATION_OPENING_TAG = "ApplicationOpening" ;
    public static String APPLICATION_OPENING ;
    
    public static String APPLICATION_CLOSING_TAG = "ApplicationClosing" ;
    public static String APPLICATION_CLOSING ;
    
    // WAITING TAGS
    public static String WAITING_USERINPUT_TAG = "WaitingUserInput" ;
    public static String WAITING_USERINPUT ;
    
    // PROJECT TAGS
    public static String PROJECT_OPENED_TAG = "ProjectOpened";
    public static String PROJECT_OPENED ;
    
    public static String PROJECT_OPENING_TAG = "ProjectOpening";
    public static String PROJECT_OPENING ;
    
    public static String PROJECT_CLOSED_TAG = "ProjectClosed" ;
    public static String PROJECT_CLOSED ;
    
    public static String PROJECT_CLOSING_TAG = "ProjectClosing" ;
    public static String PROJECT_CLOSING ;
    
    public static String PROJECT_ADDED_TAG = "ProjectAdded" ;
    public static String PROJECT_ADDED ;
    
    public static String PROJECT_ADDING_TAG = "ProjectAdding" ;
    public static String PROJECT_ADDING ;    
    
    public static String PROJECT_SAVED_TAG = "ProjectSaved" ;
    public static String PROJECT_SAVED ;
    
    public static String PROJECT_SAVING_TAG = "ProjectSaving" ;
    public static String PROJECT_SAVING ;
    
    // GRAPH TAGS
    public static String GRAPH_OPENED_TAG = "GraphOpened" ;
    public static String GRAPH_OPENED ;
    
    public static String GRAPH_OPENING_TAG = "GraphOpening" ;
    public static String GRAPH_OPENING ;
    
    public static String GRAPH_CLOSED_TAG = "GraphClosed" ;
    public static String GRAPH_CLOSED ;
    
    public static String GRAPH_CLOSING_TAG = "GraphClosing" ;
    public static String GRAPH_CLOSING ;
    
    public static String GRAPH_ADDED_TAG = "GraphAdded" ;
    public static String GRAPH_ADDED ;
    
    public static String GRAPH_ADDING_TAG = "GraphAdding" ;
    public static String GRAPH_ADDING ;
    
    public static String GRAPH_SAVED_TAG = "GraphSaved" ;
    public static String GRAPH_SAVED ;
    
    public static String GRAPH_SAVING_TAG = "GraphSaving" ;
    public static String GRAPH_SAVING ;
    
    public static String GRAPH_EXPORTING_TAG = "GraphExporting" ;
    public static String GRAPH_EXPORTING ;
    
    public static String GRAPH_EXPORTED_TAG = "GraphExported" ;
    public static String GRAPH_EXPORTED ;
    
    // LAYOUT MESSAGES
    public static String LAYOUT_RUNNING_TAG = "LayoutRunning" ;
    public static String LAYOUT_RUNNING ;
    
    public static String LAYOUT_COMPLETED_TAG = "LayoutCompleted" ;
    public static String LAYOUT_COMPLETED ;
    
    public static String LAYOUT_STOPPED_TAG = "LayoutStopped" ;
    public static String LAYOUT_STOPPED ;
    
    public static String LAYOUT_CANCELLED_TAG = "LayoutCancelled" ;
    public static String LAYOUT_CANCELLED ;
    
    // GRAPH TAGS
    public static String SNAPSHOT_APPLICATION_TAG = "SnapshotApplication" ;
    public static String SNAPSHOT_APPLICATION ;
    
    public static String SNAPSHOT_ALLPROJECTSALLGRAPHS_TAG = "SnapshotAllProjects" ;
    public static String SNAPSHOT_ALLPROJECTSALLGRAPHS ;
    
    public static String SNAPSHOT_CURRENTPROJECTALLGRAPH_TAG = "SnapshotAllGraphs" ;
    public static String SNAPSHOT_CURRENTPROJECTALLGRAPH ;
    
    public static String SNAPSHOT_CURRENTGRAPH_TAG = "SnapshotCurrentGraph" ;
    public static String SNAPSHOT_CURRENTGRAPH ;
    
    public static String SNAPSHOT_CURRENTVIEW_TAG = "SanpshotCurrentView" ;
    public static String SNAPSHOT_CURRENTVIEW ;
    
    
    // EDITING TOOLS
    public static String EDITING_VERTEXADDED_TAG = "EditingVertexAdded" ;
    public static String EDITING_VERTEXADDED ;
    
    public static String EDITING_VERTICESADDED_TAG = "EditingVerticesAdded" ;
    public static String EDITING_VERTICESADDED ;
    
    public static String EDITING_VERTEXDELETED_TAG = "EditingVertexDeleted" ;
    public static String EDITING_VERTEXDELETED ;
    
    public static String EDITING_VERTICESDELETED_TAG = "EditingVerticesDeleted" ;
    public static String EDITING_VERTICESDELETED ;
    
    public static String EDITING_VERTEXSELECTED_TAG = "EditingVertexSelected" ;
    public static String EDITING_VERTEXSELECTED ;
    
    public static String EDITING_VERTICESSELECTED_TAG = "EditingVerticesSelected" ;
    public static String EDITING_VERTICESSELECTED ;
    
    public static String EDITING_EDGEADDED_TAG = "EditingEdgeAdded" ;
    public static String EDITING_EDGEADDED ;
    
    public static String EDITING_EDGESADDED_TAG = "EditingEdgesAdded" ;
    public static String EDITING_EDGESADDED ;
    
    public static String EDITING_EDGEDELETED_TAG = "EditingEdgeDeleted" ;
    public static String EDITING_EDGEDELETED ;
    
    public static String EDITING_EDGESDELETED_TAG = "EditingEdgesDeleted" ;
    public static String EDITING_EDGESDELETED ;
    
    public static String EDITING_EDGESELECTED_TAG = "EditingEdgeSelected" ;
    public static String EDITING_EDGESELECTED ;
        
    public static String EDITING_EDGESSELECTED_TAG = "EditingEdgesSelected" ;
    public static String EDITING_EDGESSELECTED ;
        
    public static String EDITING_SELECTIONTOOLSELECTED_TAG = "EditingSelectionToolSelected" ;
    public static String EDITING_SELECTIONTOOLSELECTED ;
    
    
    // TOOLS RIBBON   
    public static String TOOLSRIBBON_HIDE_TAG = "HideToolsRibbon" ;
    public static String TOOLSRIBBON_HIDE ;
    
    public static String TOOLSRIBBON_SHOW_TAG = "ShowToolsRibbon" ;
    public static String TOOLSRIBBON_SHOW ;
    
    public static String TOOLSRIBBON_WANTTOHIDE_TAG = "WantToHideToolsRibbon" ;
    public static String TOOLSRIBBON_WANTTOHIDE ;
    
    
    // APPLICATION PARAMETERS
    public static String APPPARAMETERS_SHOW_TAG = "ShowApplicationParameters" ;
    public static String APPPARAMETERS_SHOW ;
    
    public static String APPPARAMETERS_CLOSED_TAG = "ClosedApplicationParameters" ;
    public static String APPPARAMETERS_CLOSED ;
    
    // DOCUMENTATION 
    public static String DOCUMENTATION_SHOW_TAG = "ShowDocumentation" ;
    public static String DOCUMENTATION_SHOW ;
    
    public static String DOCUMENTATION_CLOSED_TAG = "ClosedDocumentation" ;
    public static String DOCUMENTATION_CLOSED ;
    
    
    // ABOUT
    public static String ABOUT_SHOW_TAG = "ShowAbout" ;
    public static String ABOUT_SHOW ;
    
    public static String ABOUT_CLOSED_TAG = "ClosedAbout" ;
    public static String ABOUT_CLOSED ;
    
    // SETTINGS WINDOW
    public static String SETTINGS_SHOW_TAG = "ShowSettings" ;
    public static String SETTINGS_SHOW ;
    
    public static String SETTINGS_CLOSED_TAG = "ClosedSettings" ;
    public static String SETTINGS_CLOSED ;
    
    public static String SETTINGS_APPLIED_TAG = "AppliedSettings" ;
    public static String SETTINGS_APPLIED ;
    
    public static String SETTINGS_APPLIEDCLOSED_TAG = "AppliedClosedSettings" ;
    public static String SETTINGS_APPLIEDCLOSED ;
    
    public static String SETTINGS_CANCELLED_TAG = "CancelledSettings" ;
    public static String SETTINGS_CANCELLED ;
    
    // VERTEX LABEL
    public static String VERTEXLABEL_ENABLED_TAG = "VertexLabelEnabled" ;
    public static String VERTEXLABEL_ENABLED ;
    
    public static String VERTEXLABEL_ENABLING_TAG = "VertexTooltipEnabling" ;
    public static String VERTEXLABEL_ENABLING ;
    
    public static String VERTEXLABEL_DISABLED_TAG = "VertexLabelDisabled" ;
    public static String VERTEXLABEL_DISABLED ;
    
    public static String VERTEXLABEL_DISABLING_TAG = "VertexLabelDisabling" ;
    public static String VERTEXLABEL_DISABLING ;
    
    // VERTEX TOOLTIP
    public static String VERTEXTOOLTIP_ENABLED_TAG = "VertexTooltipEnabled" ;
    public static String VERTEXTOOLTIP_ENABLED ;
    
    public static String VERTEXTOOLTIP_ENABLING_TAG = "VertexTooltipEnabling" ;
    public static String VERTEXTOOLTIP_ENABLING ;
    
    public static String VERTEXTOOLTIP_DISABLED_TAG = "VertexTooltipDisabled" ;
    public static String VERTEXTOOLTIP_DISABLED ;
    
    public static String VERTEXTOOLTIP_DISABLING_TAG = "VertexTooltipDisabling" ;
    public static String VERTEXTOOLTIP_DISABLING ;
    
    // VERTEX PINNING
    public static String VERTEXPINNING_ENABLED_TAG = "VertexPinningEnabled" ;
    public static String VERTEXPINNING_ENABLED ;
    
    public static String VERTEXPINNING_ENABLING_TAG = "VertexPinningEnabling" ;
    public static String VERTEXPINNING_ENABLING ;
    
    public static String VERTEXPINNING_DISABLED_TAG = "VertexPinningDisabled" ;
    public static String VERTEXPINNING_DISABLED ;
    
    public static String VERTEXPINNING_DISABLING_TAG = "VertexPinningDisabling" ;
    public static String VERTEXPINNING_DISABLING ;
    
    // Vertex Deleting
    public static String VERTEX_DELETED_TAG = "VertexDeleted" ;
    public static String VERTEX_DELETED ;
    
    public static String VERTEX_DELETING_TAG = "VertexDeleting" ;
    public static String VERTEX_DELETING ;
    
    public static String VERTEX_INFORMATION_TAG = "VertexInformation" ;
    public static String VERTEX_INFORMATION ;
    
    public static String VERTEX_NEIGHBORHOOD_TAG = "VertexNeighborhood" ;
    public static String VERTEXL_NEIGHBORHOOD ;
    
    public static String MINING_RESULTSCLEARED_TAG = "MiningResultsCleared" ;
    public static String MINING_RESULTSCLEARED ;
    
    public static String MINING_RESULTSCLEARING_TAG = "MiningResultsClearing" ;
    public static String MINING_RESULTSCLEARING ;
    
    public static String MINING_RESULTSCOMPUTED_TAG = "MiningResultsComputed" ;
    public static String MINING_RESULTSCOMPUTED ;
    
    public static String MINING_RESULTSCOMPUTING_TAG = "MiningResultsComputing" ;
    public static String MINING_RESULTSCOMPUTING ;
    
    // LINK PREDICTION
    public static String LINKPREDICTION_RESULTSCLEARED_TAG = "LinkPredictionResultsCleared" ;
    public static String LINKPREDICTION_RESULTSCLEARED ;
    
    public static String LINKPREDICTION_RESULTSCLEARING_TAG = "LinkPredictionResultsClearing" ;
    public static String LINKPREDICTION_RESULTSCLEARING ;
    
    public static String LINKPREDICTION_RESULTSCOMPUTED_TAG = "LinkPredictionResultsComputed" ;
    public static String LINKPREDICTION_RESULTSCOMPUTED ;
    
    public static String LINKPREDICTION_RESULTSCOMPUTING_TAG = "LinkPredictionResultsComputing" ;
    public static String LINKPREDICTION_RESULTSCOMPUTING ;
    
    // SHORTEST PATH
    public static String SHORTESTPATH_COMPUTING_TAG = "ShortestPathComputing";
    public static String SHORTESTPATH_COMPUTING ;
    
    public static String SHORTESTPATH_COMPUTED_TAG = "ShortestPathComputed";
    public static String SHORTESTPATH_COMPUTED ;
    
    public static String SHORTESTPATH_RESULTSCLEARED_TAG = "ShortestPathResultsCleared" ;
    public static String SHORTESTPATH_RESULTSCLEARED ;
    
    public static String SHORTESTPATH_RESULTSCLEARING_TAG = "ShortestPathResultsClearing" ;
    public static String SHORTESTPATH_RESULTSCLEARING ;
    
    
    //Edge Information
    public static String Edge_INFORMATION_TAG = "EdgeInformation" ;
    public static String Edge_INFORMATION ;
    
    public static String Edge_DELETE_TAG = "EdgeDelete" ;
    public static String Edge_DELETE ;
    
    // FILTERS 
    public static String FILTERS_APPLIED_TAG = "FiltersApplied" ;
    public static String FILTERS_APPLIED ;
    
    public static String FILTERS_APPLYING_TAG = "FiltersApplying" ;
    public static String FILTERS_APPLYING ;
    
    public static String FILTERS_ADDED_TAG = "FiltersAdded" ;
    public static String FILTERS_ADDED ;
    
    public static String FILTERS_ADDING_TAG = "FiltersAdding" ;
    public static String FILTERS_ADDING ;
    
    public static String FILTERS_REMOVED_TAG = "FilterRemoved" ;
    public static String FILTERS_REMOVED ;
    
    public static String FILTERS_REMOVING_TAG = "FiltersRemoving" ;
    public static String FILTERS_REMOVING ;
    
    // CANVAS BACKGROUND COLOR CHANGE
    public static String CANVAS_BGCOLOR_CHANGING_TAG = "BGColorChanging" ;
    public static String CANVAS_BGCOLOR_CHANGING ;
    
    public static String CANVAS_BGCOLOR_CHANGED_TAG = "BGColorChanged" ;
    public static String CANVAS_BGCOLOR_CHANGED ;
    
    // CANVAS BACKGROUND IMAGE CHANGE
    public static String CANVAS_BGIMAGE_CHANGING_TAG = "BGImageChanging" ;
    public static String CANVAS_BGIMAGE_CHANGING ;
    
    public static String CANVAS_BGIMAGE_CHANGED_TAG = "BGImageChanged" ;
    public static String CANVAS_BGIMAGE_CHANGED ;
    
    // MINIMAP SHOW / HIDE
    public static String CANVAS_SHOWMINIMAP_TAG = "ShowMinimap";
    public static String CANVAS_SHOWMINIMAP ;
    
    public static String CANVAS_HIDEMINIMAP_TAG = "HideMiniMap" ;
    public static String CANVAS_HIDEMINIMAP ;
    
    // EDGES SHOW/HIDE
    public static String CANVAS_SHOWEDGES_TAG = "ShowEdges" ;
    public static String CANVAS_SHOWEDGES ;
    
    public static String CANVAS_HIDEEDGES_TAG = "HideEdges" ;
    public static String CANVAS_HIDEEDGES ;
    
    // PREDICTED EDGES SHOW/HIDE
    public static String CANVAS_SHOWPREDICTEDEDGES_TAG = "ShowPredictedEdges" ;
    public static String CANVAS_SHOWPREDICTEDEDGES ;
    
    public static String CANVAS_HIDEPREDICTEDEDGES_TAG = "HidePredictedEdges" ;
    public static String CANVAS_HIDEPREDICTEDEDGES ;
    
    // ENABLING / DISABLING VISUALIZATION
    public static String CANVAS_VISUALIZATION_ENABLED_TAG = "EnableVisualization" ;
    public static String CANVAS_VISUALIZATION_ENABLED ;
    
    public static String CANVAS_VISUALIZATION_DISABLED_TAG = "DisableVisualization" ;
    public static String CANVAS_VISUALIZATION_DISABLED ;
    
    //STATUS UPDATE AFTER GRAPH EXPORT
    public static String WINDOW_CLEARED_TAG = "WindowCleared" ;
    public static String WINDOW_CLEARED ;
    
    public static String EXPORT_CANCELLED_TAG = "ExportCancelled" ;
    public static String EXPORT_CANCELLED ;
    
    public static String EXPORTING_GRAPH_TAG = "ExportingGraph" ;
    public static String EXPORTING_GRAPH ;
}
