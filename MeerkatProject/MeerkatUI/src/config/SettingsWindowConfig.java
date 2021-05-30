/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *
 * @author Talat-AICML
 * EDIT HISTORY (most recent at the top)
 * Date            Author          Description
 * 2016-08-23      Abhi            Added static variables for Algorithm params tab
 */
public class SettingsWindowConfig {
    
    public static String SETTINGSWINDOW_TITLE  ;
    public static String SETTINGSWINDOW_UITABTITLE  ;    
    public static String SETTINGSWINDOW_ALGORITHMTABTITLE  ;       
        
    // Vertex Parameters
    public static String UITAB_VERTEXCOLORDEFAULT  ;
    public static String UITAB_VERTEXTCOLORSELECTED ;
    public static String UITAB_VERTEXSHAPEDEFAULT ;
    public static String UITAB_VERTEXSIZEDEFAULT ;
    public static String UITAB_VERTEXOPACITYDEFAULT ;
    public static String UITAB_VERTEXLABELFONTSIZE ;
    
    // Edge Parameters
    public static String UITAB_EDGECOLORPRIMARY ;
    public static String UITAB_EDGECOLORSECONDARY ;
    public static String UITAB_EDGECOLORTERTIARY ;
    public static String UITAB_EDGEOPACITY ;
    public static String UITAB_EDGESTROKEWIDTH ;
    
    // Canvas Parameters
    public static String UITAB_CANVASBGCOLORDEFAULT ;
    public static String UITAB_CANVASDRAGRECTCOLOR ;
    public static String UITAB_CANVASDRAGRECTOPACITY ;
    public static String UITAB_CANVASZOOMFACTOR ;
    
    
    // Algorithm Tab
    // k-means clustering
    public static String ALGOPARAMTAB_KMEANSCLUSTERCOUNT;
    // Same Attribute value
    public static String ALGOPARAMTAB_SAMEATTRIBUTEVALUE_MULTIPLEVALUES;
    // Fast Modularity
    public static String ALGOPARAMTAB_FASTMODULARITY_ALGORITHM;
    public static String ALGOPARAMTAB_FASTMODULARITY_WEIGHTED;
    // Local Top Leader
    public static String ALGOPARAMTAB_LOCALTOPLEADERS_CLUSTERCOUNT;
    public static String ALGOPARAMTAB_LOCALTOPLEADERS_OUTLIERPERCENTAGE;
    public static String ALGOPARAMTAB_LOCALTOPLEADERS_HUBPERCENTAGE;
    public static String ALGOPARAMTAB_LOCALTOPLEADERS_CENTERDISTANCE;
    // Local Community 
    public static String ALGOPARAMTAB_LOCALCOMMUNITY_ALGORITHM;
    public static String ALGOPARAMTAB_LOCALCOMMUNITY_OVERLAP;
    // RosvallInfoMap
    public static String ALGOPARAMTAB_ROSVALLINFOMAP_ATTEMPTS;
    public static String ALGOPARAMTAB_ROSVALLINFOMAP_ISDIRECTED;
    // RosvallInfoMod
    public static String ALGOPARAMTAB_ROSVALLINFOMOD_ATTEMPTS;
    // Dynamic Community Mining
    public static String ALGOPARAMTAB_DCMINING_SIMILARITYTHRESHOLD;
    public static String ALGOPARAMTAB_DCMINING_METRIC;
    public static String ALGOPARAMTAB_DCMINING_METHOD;
    public static String ALGOPARAMTAB_DCMINING_INSTABILITY;
    public static String ALGOPARAMTAB_DCMINING_HISTORY;
    public static String ALGOPARAMTAB_DCMINING_OVERLAP;
    public static String ALGOPARAMTAB_DCMINING_HUBS;

    
}
