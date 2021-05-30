/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *  Class Name      : AppSettingsConfig
 *  Created Date    : 2016-07-15
 *  Description     : The configuration of the Application Settings
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-08-23      Abhi            Added tags for Algorithm Params tab in Application settings window
 * 
*/

public class AppSettingsConfig {

    public static String SETTINGS_ROOT_TAG = "Settings";
    
    
    public static String UITAB_TAG = "UI" ;   
    public static String ALGOPARAMTAB_TAG = "AlgorithmParameters" ;
    
        
    // Vertex Parameters
    public static String UITAB_VERTEXCOLORDEFAULT_TAG = "VertexColorDefault" ;
    // public static String UITAB_VERTEXCOLORDEFAULT = "VertexColorDefault" ;
    
    public static String UITAB_VERTEXTCOLORSELECTED_TAG = "VertexColorSelected" ;
    // public static String UITAB_VERTEXTCOLORSELECTED = "VertexColorSelected" ;
        
    public static String UITAB_VERTEXSHAPEDEFAULT_TAG = "VertexShapeDefault" ;
    // public static String UITAB_VERTEXSHAPEDEFAULT = "VertexShapeDefault" ;
    
    public static String UITAB_VERTEXSIZEDEFAULT_TAG = "VertexSizeDefault" ;
    // public static String UITAB_VERTEXSIZEDEFAULT = "VertexSizeDefault" ;
    
    public static String UITAB_VERTEXOPACITYDEFAULT_TAG = "VertexOpacityDefault" ;
    // public static String UITAB_VERTEXOPACITYDEFAULT = "VertexOpacityDefault" ;
    
    public static String UITAB_VERTEXLABLEFONTSIZE_TAG = "VertexLabelFontSizeDefault" ;
    // public static String UITAB_VERTEXLABELFONTSIZE = "VertexLabelFontSizeDefault" ;
    
    
            
    // Edge Parameters
    public static String UITAB_EDGECOLORPRIMARY_TAG = "EdgeColorPrimary" ;
    // public static String UITAB_EDGECOLORPRIMARY = "EdgeColorPrimary" ;
    
    public static String UITAB_EDGECOLORSECONDARY_TAG = "EdgeColorSecondary" ;
    // public static String UITAB_EDGECOLORSECONDARY = "EdgeColorSecondary" ;
    
    public static String UITAB_EDGECOLORTERTIARY_TAG = "EdgeColorTertiary" ;
    // public static String UITAB_EDGECOLORTERTIARY = "EdgeColorTertiary" ;
    
    public static String UITAB_EDGEOPACITY_TAG = "EdgeOpacity" ;
    // public static String UITAB_EDGEOPACITY = "EdgeOpacity" ;
    
    public static String UITAB_EDGESTROKEWIDTH_TAG = "EdgeStrokeWidth" ;
    // public static String UITAB_EDGESTROKEWIDTH = "EdgeStrokeWidth" ;
    
    
    // Canvas Parameters
    public static String UITAB_CANVASBGCOLLORDEFAULT_TAG = "CanvasBackgroundColorDefault" ;
    // public static String UITAB_CANVASBGCOLLORDEFAULT = "CanvasBackgroundColorDefault" ;
        
    public static String UITAB_CANVASDRAGRECTCOLOR_TAG = "CanvasDragRectColor" ;
    // public static String UITAB_CANVASDRAGRECTCOLOR = "CanvasDragRectColor" ;
        
    public static String UITAB_CANVASDRAGRECTOPACITY_TAG = "CanvasDragRectOpacity" ;
    // public static String UITAB_CANVASDRAGRECTOPACITY = "CanvasDragRectOpacity" ;
    
    public static String UITAB_CANVASZOOMFACTOR_TAG = "CanvasZoomFactor" ;
    // public static String UITAB_CANVASZOOMFACTOR = "CanvasZoomFactor" ;
    
    
    // Algorithm Tab
    // K Means Cluster Count
    public static String ALGOPARAMTAB_KMEANSCLUSTERCOUNT_TAG = "KMeansDefaultClusters" ;
     // Same Attribute value
    public static String ALGOPARAMTAB_SAMEATTRIBUTEVALUE_MULTIPLEVALUES_TAG = "SameAttributeMultipleValues";
    // Fast Modularity
    public static String ALGOPARAMTAB_FASTMODULARITY_ALGORITHM_TAG = "FastModularityAlgorithm";
    public static String ALGOPARAMTAB_FASTMODULARITY_WEIGHTED_TAG = "FastModularityWeighted";
    // Local Top Leader
    public static String ALGOPARAMTAB_LOCALTOPLEADERS_CLUSTERCOUNT_TAG = "LocalTopLeadersDefaultClusters";
    public static String ALGOPARAMTAB_LOCALTOPLEADERS_OUTLIERPERCENTAGE_TAG = "LocalTopLeadersDefaultOutlierPercentage";
    public static String ALGOPARAMTAB_LOCALTOPLEADERS_HUBPERCENTAGE_TAG = "LocalTopLeadersDefaultHubPercentage";
    public static String ALGOPARAMTAB_LOCALTOPLEADERS_CENTERDISTANCE_TAG = "LocalTopLeadersDefaultCenterDistance";
    // Local Community 
    public static String ALGOPARAMTAB_LOCALCOMMUNITY_ALGORITHM_TAG = "LocalCommunityAlgorithm";
    public static String ALGOPARAMTAB_LOCALCOMMUNITY_OVERLAP_TAG = "LocalCommunityOverlap";
    // RosvallInfoMap
    public static String ALGOPARAMTAB_ROSVALLINFOMAP_ATTEMPTS_TAG = "RosvallInfoMapNoOfAttempts";
    public static String ALGOPARAMTAB_ROSVALLINFOMAP_ISDIRECTED_TAG = "RosvallInfoMapIsDirected";   
    // RosvallInfoMod
    public static String ALGOPARAMTAB_ROSVALLINFOMOD_ATTEMPTS_TAG = "RosvallInfoModNoOfAttempts";
    // Dynamic Community Mining
    public static String ALGOPARAMTAB_DCMINING_SIMILARITYTHRESHOLD_TAG = "DCMiningSimilarityThreshold";
    public static String ALGOPARAMTAB_DCMINING_METRIC_TAG = "DCMiningMetric";
    public static String ALGOPARAMTAB_DCMINING_METHOD_TAG = "DCMiningMethod";
    public static String ALGOPARAMTAB_DCMINING_INSTABILITY_TAG = "DCMiningInstability";
    public static String ALGOPARAMTAB_DCMINING_HISTORY_TAG = "DCMiningHistory";
    public static String ALGOPARAMTAB_DCMINING_OVERLAP_TAG = "DCMiningOverlap";
    public static String ALGOPARAMTAB_DCMINING_HUBS_TAG = "DCMiningHubs";
   
}
