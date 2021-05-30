package algorithm.graph.communitymining;

import algorithm.Clustering.KMedoidClustering;
import algorithm.util.AttributeType;
import config.CommunityMiningParameters;
import java.util.HashMap;
import java.util.Map;

import config.MeerkatSystem;
import config.Parameter;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public class KMeansMiner<V extends IVertex, E extends IEdge<V>> 
                                                extends Miner<V,E> {

    /**
     *
     */
    public static final String STR_NAME = "K-Means Clustering";

    /**
     *
     */
    public static final int DEFAULT_CLUSTERS = 3;
    /* Algorithm Parameters */
    private String attribute;
    private int numClusters = DEFAULT_CLUSTERS;

    /**
     * Constructor
     * Version: 2.0
     * @param pIGraph
     *          dynamic graph (not null)
     * @param tf
     *          time frame (not null)
     * @param pstrParameters
     *          String array of parameters: 
     *          - pstrParameters[i] = the ithe param name and value --> parametername:value
     * 
     * EDIT HISTORY
     * DATE             Author              Description
     * 2106 Apr 20      Afra                Changed the 2D array of parameters to 1D (both param name and value in one string)
     * 2016 April 19    Afra                Changed the separated parameters to one single array of parameters.
     */
    public KMeansMiner(IDynamicGraph<V,E> pIGraph, TimeFrame tf, String[] pstrParameters) {
        super(pIGraph, tf);
        
        List<Parameter> lstParam = CommunityMiningParameters.getParameters(this.getClass().getSimpleName());
        
        if (pstrParameters != null) {
            for (String strP : pstrParameters) {
                for (Parameter p : lstParam) {
                    if (strP.startsWith(p.key)) {
                        String value = strP.substring(p.key.length()+1).trim();
                        if (p.key.equals(CommunityMiningParameters.KMEANS_ATTRIBUTE)) {
                            this.attribute = value;
                        } else if (p.key.equals(CommunityMiningParameters.KMEANS_NUMBEROFCLUSTERS)) {
                            this.numClusters = Integer.parseInt(value);
                            if (this.numClusters < 1) {
                                this.numClusters = DEFAULT_CLUSTERS;
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
    
    /**
     *
     */
    @Override
    public void run() {
        mineGraph();
        if(!running){
                return;
        }
        System.out.println("KMeansMiner.run(): algorithm is completed! updating datastructures...");
        updateDataStructure();
        System.out.println("KMeansMines.run(): datastructure is updated!");
        this.blnDone = true;
    }

    /**
     *
     */
    public void mineGraph() {

        Map<V,Map<V,Double>> mapDistances = computeDistances();
        
        KMedoidClustering<V> kmmAlgorithm = 
                new KMedoidClustering<>(mapDistances);
        // Mine for clusters using the k-means algorithm.
        
        
        List<Set<V>> clusters = kmmAlgorithm.cluster(numClusters, this.isThreadRunningProperty);

        // Convert the clusters to communities.
        int commIndex = 0;
        for (Set<V> cluster : clusters) {
            if(!running){
                return;
            }
            String commName = commIndex + "";
            hmpCommunities.put(commName, new LinkedList<>() );
            for (V v : cluster) {
                if(!running){
                    return;
                }
                if (!(v instanceof IGraph)) {
                    hmpCommunities.get(commName)
                            .add(v.getId());
                }
            }
            commIndex++;
        }
    }

    private Map<V,Map<V,Double>> computeDistances () {
        // Give all vertices a location based on the attribute being compared.
        Map<V, Map<V,Double>> locations = new HashMap<>();
        for (V v : dynaGraph.getVertices(tf)) {
            if(!running){
                break;
            }
            Map<V,Double> location = new HashMap<>();
            locations.put(v, location);
            for (V vi : dynaGraph.getVertices(tf)) {
                if(!running){
                    break;
                }
                locations.get(v).put(vi, distance(v, vi, attribute));
            }
        }
        return locations;
    }
    
    private double distance(IVertex v1, IVertex v2, String attrId) {
        double distance = 0.0;
        
        String a1 = "";
        String a2 = "";
        if (MeerkatSystem.isSystemAttribute(attrId)) {
            
            if (v1.getSystemAttributer().getAttributeNames().contains(attrId) 
                && v2.getSystemAttributer().getAttributeNames().contains(attrId)){

                a1 = v1.getSystemAttributer().getAttributeValue(attrId,tf);
                a2 = v2.getSystemAttributer().getAttributeValue(attrId,tf);
            }
        } else if (v1.getUserAttributer().getAttributeNames().contains(attrId) 
                && v2.getUserAttributer().getAttributeNames().contains(attrId)){

                a1 = v1.getUserAttributer().getAttributeValue(attrId,tf);
                a2 = v2.getUserAttributer().getAttributeValue(attrId,tf);
        }
        // Both attributes are quantitative.
        AttributeType.AttType atpA1 = AttributeType.getType(a1);
        AttributeType.AttType atpA2 = AttributeType.getType(a2);

        if (atpA1 == AttributeType.AttType.INTEGER && 
                atpA2 == AttributeType.AttType.INTEGER) {
            distance = Math.abs(Integer.valueOf(a1) - Integer.valueOf(a2));
        } else if (atpA1 == AttributeType.AttType.DOUBLE && 
                atpA2 == AttributeType.AttType.INTEGER) {
            distance = Math.abs(Double.valueOf(a1) - Integer.valueOf(a2));
        } else if (atpA1 == AttributeType.AttType.DOUBLE && 
                atpA2 == AttributeType.AttType.DOUBLE) {
            distance = Math.abs(Double.valueOf(a1) - Double.valueOf(a2));
        } else if (atpA1 == AttributeType.AttType.INTEGER && 
                atpA2 == AttributeType.AttType.DOUBLE) {
            distance = Math.abs(Integer.valueOf(a1) - Double.valueOf(a2));
        } else {
            distance = levenshtein(a1, a2);    
        }
        
        return distance;
    }

    private int levenshtein(String s1, String s2) {
        int[][] distance = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            distance[i][0] = i;
        }
        for (int j = 1; j <= s2.length(); j++) {
                distance[0][j] = j;
        }
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (Math.min(i, j) == 0) {
                        distance[i][j] = Math.max(i, j);
                } else {
                    distance[i][j] = 
                        Math.min(Math.min(distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1), distance[i - 1][j - 1]
                        + ((s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1));
                }
            }
        }
            return distance[s1.length()][s2.length()];
    }

    /**
     *
     * @return
     */
    public boolean requiresParameters() {
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return STR_NAME;
    }
}