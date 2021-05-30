/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.util.Arrays;
import java.util.List;

/**
 *  Class Name      : CommunityMiningConfig
 *  Created Date    : 2016-04-08
 *  Description     : A config file for the Community Mining
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-08-22      Abhi            Added Static variables to keep track of Algortihm params
 *  
 * 
*/

public class CommunityMiningConfig {
    
    /* *********    DYNAMIC COMMUNITY MINING    *********** */
    public static double DCMINING_SIMILARITYTHRESHOLD_MIN = 0.0 ;
    public static double DCMINING_SIMILARITYTHRESHOLD_MAX = 1.0 ;
    public static double DCMINING_SIMILARITYTHRESHOLD_DEFAULT = 0.2 ;
    public static double DCMINING_SIMILARITYTHRESHOLD_STEP = 0.01 ;
    
    public static List<String> DCMINING_METRIC = Arrays.asList("L","M","R") ;
    public static String DCMINING_METRIC_DEFAULT = "L" ;
    public static List<String> DCMINING_METHOD = Arrays.asList("Direct", "Spectrum", "Independent") ;
    public static String DCMINING_METHOD_DEFAULT = "Direct" ;
    
    public static Boolean DCMINING_OVERLAP_DEFAULT = false ;
    public static Boolean DCMINING_HUBS_DEFAULT = false ;
    
    public static double DCMINING_INSTABILITY_MIN = 0.0 ;
    public static double DCMINING_INSTABILITY_MAX = 1.0 ;
    public static double DCMINING_INSTABILITY_DEFAULT = 0.0 ;
    public static double DCMINING_INSTABILITY_STEP = 0.01 ;
    
    public static double DCMINING_HISTORY_MIN = 0.0 ;
    public static double DCMINING_HISTORY_MAX = 1.0 ;
    public static double DCMINING_HISTORY_DEFAULT = 0.0 ;
    public static double DCMINING_HISTORY_STEP = 0.01 ;
    
    /* *********    FAST MODULARITY MINING    *********** */
    public static List<String> FM_METRIC = Arrays.asList("Fast Modularity", "Max-Min Modularity") ;
    public static String FM_METRIC_DEFAULT = "Fast Modularity";
    public static Boolean FM_WEIGHTED = false;
    
    /* *********    LOCAL COMMUNITY MINING    *********** */
    public static List<String> LOCALCOMMUNITY_TYPE = Arrays.asList("L","M","R") ;
    public static String LOCALCOMMUNITY_TYPE_DEFAULT = "L";
    public static Boolean LOCALCOMMUNITY_OVERLAP = false;
    
    /* *********    LOCAL TOP LEADERS MINING    *********** */
    public static int LOCALTOPLEADERS_CLUSTERCOUNT_MIN = 2 ;
    public static int LOCALTOPLEADERS_CLUSTERCOUNT_MAX = 32 ;
    public static int LOCALTOPLEADERS_CLUSTERCOUNT_DEFAULT = 2 ;
    public static int LOCALTOPLEADERS_CLUSTERCOUNT_STEP = 1 ;
    
    public static double LOCALTOPLEADERS_OUTLIERPERCENTAGE_MIN = 0 ;
    public static double LOCALTOPLEADERS_OUTLIERPERCENTAGE_MAX = 100 ;
    public static double LOCALTOPLEADERS_OUTLIERPERCENTAGE_DEFAULT = 0 ;
    public static double LOCALTOPLEADERS_OUTLIERPERCENTAGE_STEP = 0.5 ;
    
    public static double LOCALTOPLEADERS_HUBPERCENTAGE_MIN = 0 ;
    public static double LOCALTOPLEADERS_HUBPERCENTAGE_MAX = 100 ;
    public static double LOCALTOPLEADERS_HUBPERCENTAGE_DEFAULT = 0 ;
    public static double LOCALTOPLEADERS_HUBPERCENTAGE_STEP = 0.5 ;
    
    public static double LOCALTOPLEADERS_CENTERDISTANCE_MIN = 0 ;
    public static double LOCALTOPLEADERS_CENTERDISTANCE_MAX = 100 ;
    public static double LOCALTOPLEADERS_CENTERDISTANCE_DEFAULT = 0 ;
    public static double LOCALTOPLEADERS_CENTERDISTANCE_STEP = 0.5 ;
        
    /* *********    ROSVALL INFOMAP MINING    *********** */
    public static int ROSVALLINFOMAP_ATTEMPTS_MIN = 0 ;
    public static int ROSVALLINFOMAP_ATTEMPTS_MAX = 100 ;
    public static int ROSVALLINFOMAP_ATTEMPTS_DEFAULT = 10 ;
    public static int ROSVALLINFOMAP_ATTEMPTS_STEP = 1 ;
    public static Boolean ROSVALLINFOMAP_ISDIRECTED = false;
    
    /* *********    ROSVALL INFOMOD MINING    *********** */
    public static int ROSVALLINFOMOD_ATTEMPTS_MIN = 0 ;
    public static int ROSVALLINFOMOD_ATTEMPTS_MAX = 100 ;
    public static int ROSVALLINFOMOD_ATTEMPTS_DEFAULT = 10 ;
    public static int ROSVALLINFOMOD_ATTEMPTS_STEP = 1 ;
    
    /* *********    SAME VALUE ATTRIBUTE MINING    *********** */
    public static String SAMEVALUEATTR_SEPERATOR = "," ;
    public static Boolean SAMEVALUEATTR_MULTIPLEVALUES = false; ;
    
    /* *********    KMEANS CLUSTERING MINING    *********** */
    public static int KMEANS_CLUSTERCOUNT_MIN = 2 ;
    public static int KMEANS_CLUSTERCOUNT_MAX = 32 ;
    public static int KMEANS_CLUSTERCOUNT_DEFAULT = 2 ;
    public static int KMEANS_CLUSTERCOUNT_STEP = 1 ;
    
    
    /* **************************************************************************** */
    /* *******************  TAGS REQUIRED FOR PARSING THE INPUT FILE ************** */
    /* **************************************************************************** */
    
    public static String COMMUNITYMINING_TAG = "CommunityMining";
    
    public static String DCMINING_TAG = "DynamicCommunityMining" ;
    public static String DCMINING_TITLE_TAG = "Title" ;
    public static String DCMINING_SIMILARITY_TAG = "SimilarityThreshold" ;
    public static String DCMINING_METRIC_TAG = "Metric" ;
    public static String DCMINING_METHOD_TAG = "Method" ;
    public static String DCMINING_OVERLAP_TAG = "Overlap" ;
    public static String DCMINING_HUBS_TAG = "Hubs" ;
    public static String DCMINING_INSTABILITY_TAG = "Instability" ;
    public static String DCMINING_HISTORY_TAG = "History" ;
    
    public static String FASTMODULARITY_TAG = "FastModularity" ;
    public static String FASTMODULARITY_TITLE_TAG = "Title" ;
    public static String FASTMODULARITY_NOTE_TAG = "Note" ;
    public static String FASTMODULARITY_ALGORITHM_TAG = "AlgorithmType" ;
    public static String FASTMODULARITY_WEIGHTED_TAG = "Weighted" ;
    
    public static String Louvain_TAG = "Louvain" ;
    public static String LOUVAIN_TITLE_TAG = "Title" ;
    
    
    public static String KMEANS_TAG = "KMeansClustering" ;
    public static String KMEANS_TITLE_TAG = "Title" ;
    public static String KMEANS_CLUSTERCOUNT_TAG = "NumberOfClusters" ;
    public static String KMEANS_ATTRIBUTE_TAG = "AttributeID" ;
    
    public static String LOCALCOMMUNITY_TAG = "LocalCommunity" ;
    public static String LOCALCOMMUNITY_TITLE_TAG = "Title" ;
    public static String LOCALCOMMUNITY_ALGORITHM_TAG = "AlgorithmType" ;
    public static String LOCALCOMMUNITY_OVERLAP_TAG = "Overlap" ;
    
    public static String LOCALTCOMMUNITY_TAG = "LocalT" ;
    public static String LOCALTCOMMUNITY_TITLE_TAG = "Title" ;
    
    public static String LOCALTOPLEADERS_TAG = "LocalTopLeaders" ;
    public static String LOCALTOPLEADERS_TITLE_TAG = "Title" ;
    public static String LOCALTOPLEADERS_NUMBEROFCLUSTERS_TAG = "NumberOfClusters" ;
    public static String LOCALTOPLEADERS_OUTLIERPERCENT_TAG = "OutlierPercentage" ;
    public static String LOCALTOPLEADERS_HUBPERCENT_TAG = "HubPercentage" ;
    public static String LOCALTOPLEADERS_CENTERDISTANCE_TAG = "CenterDistance" ;
        
    public static String ROSVALLINFOMAP_TAG = "RosvallInfomap" ;
    public static String ROSVALLINFOMAP_TITLE_TAG = "Title" ;
    public static String ROSVALLINFOMAP_NUMBEROFATTEMPTS_TAG = "NumberOfAttempts" ;
    public static String ROSVALLINFOMAP_DIRECTED_TAG = "Directed" ;
    
    public static String ROSVALLINFOMOD_TAG = "RosvallInfomod" ;
    public static String ROSVALLINFOMOD_TITLE_TAG = "Title" ;
    public static String ROSVALLINFOMOD_NUMBEROFATTEMPTS_TAG = "NumberOfAttempts" ;
    
    public static String SAMEATTRIBUTEVALUE_TAG = "SameAttributeValue" ;
    public static String SAMEATTRIBUTEVALUE_TITLE_TAG = "Title" ;
    public static String SAMEATTRIBUTEVALUE_MESSAGE_TAG = "Message" ;
    public static String SAMEATTRIBUTEVALUE_ATTRIBUTE_TAG = "AttributeID" ;
    public static String SAMEATTRIBUTEVALUE_MULTIPLEVALUES_TAG = "MultipleValules" ;
    public static String SAMEATTRIBUTEVALUE_SEPERATOR_TAG = "Separator" ;
        
}
