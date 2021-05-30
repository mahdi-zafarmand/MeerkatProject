/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package config;

import algorithm.graph.communitymining.AttributeValueMiner;
import algorithm.graph.communitymining.FastModularity;
import algorithm.graph.communitymining.KMeansMiner;
import algorithm.graph.communitymining.LocalCommunityMiner;
import algorithm.graph.communitymining.LocalT;
import algorithm.graph.communitymining.LocalTopLeader;
import algorithm.graph.communitymining.RosvallInfomapMiner;
import algorithm.graph.communitymining.RosvallInfomodMiner;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: CommunityMiningParameters
 * DEscription: Configuration class for Community Mining Algorithm's` 
 * parameters (and in case od specific values, their values)
 * Author: Afra
 * Version: 1.0
 * 
 * @author aabnar
 */
public class CommunityMiningParameters {
    
    // FAST MODULARITY
    public static final String FASTMODULARITY_WEIGHTED = "Weighted";
    public static final String FASTMODULARITY_ALGORITHMTYPE = "AlgorithmType";
    public static final String FASTMODULARITY_ALGTYPE_FASTMODULARITY= "Fast_Modularity";
    public static final String FASTMODULARITY_ALGTYPE_MAXMINMODULARITY = "MAX_MIN_Modularity";
       
    // SAME ATTRIBUTE VALUE
    public static final String ATTRIBUTEVALUEMINER_CHOSENATTRIBUTE = "Chosen Attribute";
    public static final String ATTRIBUTEVALUEMINER_MULTIPLEVALUE = "Multiple Value";
    public static final String ATTRIBUTEVALUEMINER_SEPARATOR = "Separator";
        
    // KMEANS
    public static final String KMEANS_ATTRIBUTE = "Attribute";
    public static final String KMEANS_NUMBEROFCLUSTERS = "Number of Clusters";
    
    // LOCALCM
    public static final String LOCALCM_OVERLAP = "Overlap";
    public static final String LOCALCM_ALGORITHMTYPE = "Algorithm Type";
    public static final String LOCALCM_ALGTYPE_M = "M";
    public static final String LOCALCM_ALGTYPE_L = "L";
    public static final String LOCALCM_ALGTYPE_R = "R";
        
    // LOCAL T
    public static final String LOCALT_STARTNODEID = "Start Node Id";
    public static final String LOCALT_NUMBEROFCM = "Number of Communities";
    
    // LOCAL TOP LEADEERS
    public static final String LOCALTOPLEADERS_NUMBEROFCLUSTERS = "Number of Clusters";
    public static final String LOCALTOPLEADERS_OUTLIERPERCENTAGE = "Oulier Percentage";
    public static final String LOCALTOPLEADERS_HUBPERCENTAGE = "Hub Percentage";
    public static final String LOCALTOPLEADERS_CENTERS = "Centers";
    
    // ROSVALL INFOMAP
    public static final String ROSVALLINFOMAP_DIRECTED = "Directed";
    public static final String ROSVALLINFOMAP_NUMBEROFATTEMPTS = "Number of Attempts";
       
    // ROSVALL INFOMOD
    public static final String ROSVALLINFOMOD_NUMBEROFATTEMPTS = "Number of Attempts";
    
    // DYNAMIC COMMUNITY MINING
    public static final String DCMINING_SIMILARITYTHRESHOLD = "Similarity Threshold" ;
    public static final String DCMINING_METRIC = "Metric" ;
    public static final String DCMINING_METHOD = "Method" ;
    public static final String DCMINING_OVERLAP = "Overlap" ;
    public static final String DCMINING_HUBS = "Hubs" ;
    public static final String DCMINING_INSTABILITY = "Instability" ;
    public static final String DCMINING_HISTORY = "History" ;

    /**
     * This map contains the mapping between community mining algorithm
     * class name to the list of all input parameters for the algorithm.
     */
    private static Map<String, List<Parameter>> mapClassParameters = new HashMap<>();
    
    /**
     * Static Block
     * Description: This static block initializes the map with all 
     * community mining algorithms and their required input parameters
     * and possible values.
     * 
     * Version: 1.0
     * Author: Afra
     * 
     */
    static {
        //FastModularity
        List<Parameter> lstParams = new LinkedList<>();
        lstParams.add(new Parameter(FASTMODULARITY_WEIGHTED,null));
        String[] algType = {FASTMODULARITY_ALGTYPE_FASTMODULARITY, FASTMODULARITY_ALGTYPE_MAXMINMODULARITY};
        lstParams.add(new Parameter(FASTMODULARITY_ALGORITHMTYPE, algType));
        mapClassParameters.put(FastModularity.class.getSimpleName(), lstParams);
        
        //AttributeValueMiner
        lstParams = new LinkedList<>();
        lstParams.add(new Parameter(ATTRIBUTEVALUEMINER_CHOSENATTRIBUTE, null));
        lstParams.add(new Parameter(ATTRIBUTEVALUEMINER_MULTIPLEVALUE, null));
        lstParams.add(new Parameter(ATTRIBUTEVALUEMINER_SEPARATOR, null));
        mapClassParameters.put(AttributeValueMiner.class.getSimpleName(), lstParams);
        
        //KMeasnMiner
        lstParams= new LinkedList<>();
        lstParams.add(new Parameter(KMEANS_ATTRIBUTE,null));
        lstParams.add(new Parameter((KMEANS_NUMBEROFCLUSTERS), null));
        mapClassParameters.put(KMeansMiner.class.getSimpleName(), lstParams);
        
        //LocalCommunityMiner
        lstParams= new LinkedList<>();
        lstParams.add(new Parameter(LOCALCM_OVERLAP, null));
        String[] LCMAlgType = {LOCALCM_ALGTYPE_L,LOCALCM_ALGTYPE_M,LOCALCM_ALGTYPE_R};
        lstParams.add(new Parameter(LOCALCM_ALGORITHMTYPE,LCMAlgType));
        mapClassParameters.put(LocalCommunityMiner.class.getSimpleName(), lstParams);
        
        //LocalT
        lstParams= new LinkedList<>();
        lstParams.add(new Parameter(LOCALT_STARTNODEID, null));
        lstParams.add(new Parameter(LOCALT_NUMBEROFCM, null));
        mapClassParameters.put(LocalT.class.getSimpleName(), lstParams);
        
        //LocalTopLeader
        lstParams= new LinkedList<>();
        lstParams.add(new Parameter(LOCALTOPLEADERS_NUMBEROFCLUSTERS,null));
        lstParams.add(new Parameter(LOCALTOPLEADERS_OUTLIERPERCENTAGE,null));
        lstParams.add(new Parameter(LOCALTOPLEADERS_HUBPERCENTAGE,null));
        lstParams.add(new Parameter(LOCALTOPLEADERS_CENTERS,null));
        mapClassParameters.put(LocalTopLeader.class.getSimpleName(), lstParams);
        
        
        //RosvallInfomap
        lstParams= new LinkedList<>();
        lstParams.add(new Parameter(ROSVALLINFOMAP_DIRECTED,null));
        lstParams.add(new Parameter(ROSVALLINFOMAP_NUMBEROFATTEMPTS,null));
        mapClassParameters.put(RosvallInfomapMiner.class.getSimpleName(), lstParams);
        
        //RosvallInfomod
        lstParams= new LinkedList<>();
        lstParams.add(new Parameter(ROSVALLINFOMOD_NUMBEROFATTEMPTS,null));
        mapClassParameters.put(RosvallInfomodMiner.class.getSimpleName(), lstParams);
        
    }
    
    /**
     *
     * @param pstrClassName
     * @return
     */
    public static List<Parameter> getParameters(String pstrClassName) {
        return mapClassParameters.get(pstrClassName);
    }
}
