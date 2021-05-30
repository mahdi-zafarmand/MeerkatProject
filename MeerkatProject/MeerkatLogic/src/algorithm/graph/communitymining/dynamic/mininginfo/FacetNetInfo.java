/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.communitymining.dynamic.mininginfo;

import config.dynamiccommunitymining.DynamicCommunityMiningAlgorithmName;

/**
 *
 * @author aabnar
 */
public class FacetNetInfo extends MiningInfo{
    private String facetnetLocation;
    private String facetnetMFile;
    private double alpha;
    private int maxNbCluster;
    private Integer nbCluster;

    public FacetNetInfo(double similarityThreshold, String facetnetLocation,
                            String facetnetMFile, double alpha, 
                            int maxNbCluster, int nbCluster) {
        this.similarityThreshold = similarityThreshold;
        this.facetnetLocation = facetnetLocation;
        this.facetnetMFile = facetnetMFile;
        this.alpha = alpha;
        this.maxNbCluster = maxNbCluster;
        this.nbCluster = nbCluster;
    }

    public String getFacetnetLocation() {
        return facetnetLocation;
    }

    public void setFacetnetLocation(String facetnetLocation) {
        this.facetnetLocation = facetnetLocation;
    }

    public String getFacetnetMFile() {
        return facetnetMFile;
    }

    public void setFacetnetMFile(String facetnetMFile) {
        this.facetnetMFile = facetnetMFile;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public Integer getNbCluster() {
        return nbCluster;
    }

    public void setNbCluster(Integer nbCluster) {
        this.nbCluster = nbCluster;
    }

    public int getMaxNbCluster() {
        return maxNbCluster;
    }

    public void setMaxNbCluster(int maxNbCluster) {
        this.maxNbCluster = maxNbCluster;
    }

    public String getName() {
        return DynamicCommunityMiningAlgorithmName.FACETNET + "_a" + 
                                            alpha + "_n" + nbCluster;
    }
}
