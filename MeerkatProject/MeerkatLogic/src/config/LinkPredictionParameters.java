/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import algorithm.graph.linkprediction.LocalNaiveBayesIndexLinkPredictor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AICML Administrator
 */

/**
 * ClassName: LinkPredictionParameters
 * Description: Configuration class for Link Prediction Algorithm's` 
 * parameters (and in case specific values, their values)
 * 
 * @author AICML Administrator
 */
public class LinkPredictionParameters {
    
    // LocalNaiveBayesIndexLinkPredictor
    public static final String LOCALNAIVEBAYESINDEX_TOPNEDGES = "Top K Edges";
    public static final String LOCALNAIVEBAYESINDEX_TOPMETRIC = "Top Metric";
    public static final int DEFAULT_NO_OF_TOPKEDGES = 10;
    public static final double DEFAULT_METRIC_SCORE = 1;
    
    
    /**
     * This map contains the mapping between linkPrediction algorithm
     * class name to the list of all input parameters for the algorithm.
     */
    private static Map<String, List<Parameter>> mapClassParameters = new HashMap<>();

    static {
        
        // LocalNaiveBayesIndexLinkPredictor
        List<Parameter>lstParams= new LinkedList<>();
        lstParams.add(new Parameter(LOCALNAIVEBAYESINDEX_TOPNEDGES,null));
        mapClassParameters.put(LocalNaiveBayesIndexLinkPredictor.class.getSimpleName(), lstParams);
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
