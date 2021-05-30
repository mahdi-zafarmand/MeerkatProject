/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.communitymining.dynamic;

import algorithm.graph.communitymining.dynamic.auxilaryDS.EvolvingCommunity;
import algorithm.graph.communitymining.dynamic.auxilaryDS.Snapshot;
import algorithm.graph.communitymining.dynamic.matcher.Matcher;
import algorithm.graph.communitymining.dynamic.matcher.OptimalBipartiteMatcher;
import algorithm.graph.communitymining.dynamic.mininginfo.FacetNetInfo;
import algorithm.graph.communitymining.dynamic.mininginfo.LocalInfo;
import config.dynamiccommunitymining.DynamicCommunityMiningAlgorithmName;
import config.dynamiccommunitymining.Method;
import config.dynamiccommunitymining.Metric;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;

public class DynamicCommunityMining<V extends IVertex, E extends IEdge<V>> {

    IDynamicGraph<V, E> dynaGraph;
    double identityThreshold;
    
    public enum Framework {
        ASUR, GREENE, PALLA, MODEC, MODEC2
    }
    
    Framework framework;

    public DynamicCommunityMining(IDynamicGraph<V, E> pDynaGraph, 
            Framework pframework, double pdblThreshold) {
        this.dynaGraph = pDynaGraph;
        this.framework = pframework;
        this.identityThreshold = pdblThreshold;
    }

    public StringBuffer performCommunityMining(Properties properties, BooleanProperty isThreadRunningProperty) {
        StringBuffer miningResults = new StringBuffer();
        try {
            Vector<Snapshot<V, E>> snapshots = runCommunityMiningAlgorithm(properties, isThreadRunningProperty);

            // Matching communities
            
            if (this.framework == Framework.ASUR) {
                // eventDetector = new AsurEventDetection(identityThreshold,
                // matrices, network);
            } else if (this.framework == Framework.GREENE) {
                // eventDetector = new GreeneEventDetection(identityThreshold,
                // matrices, network);
            } else if (this.framework == Framework.PALLA) {
                // TD add meta to Palla
                // eventDetector = new PallaEventDetection(identityThreshold,
                // matrices, network);
            } else if (this.framework == Framework.MODEC) {
                // eventDetector = new MODECEventDetection(identityThreshold,
                // matrices, network);
            } else {
                // MODEC2EventDetection
                communityMatching(snapshots, identityThreshold);
            }
            
            
//          Map<String, MetaCommunity<V>> metaCommunities = settingMetaCommunity(snapshots);
            //adding this condition to stop thread
                if(isThreadRunningProperty.getValue()==false){
                    return miningResults;
                }
            miningResults = new StringBuffer();
            for (int i = 0 ; i < dynaGraph.getAllTimeFrames().size(); i++) {
                //adding this condition to stop thread
                if(isThreadRunningProperty.getValue()==false){
                    return miningResults;
                }
                for (EvolvingCommunity<V> com : snapshots.get(i).getCommunities()) {
                    miningResults.append(com.getId()).append(":");
                    for (V v  : com.getVertices()) {
                        miningResults.append(v.getId()).append(",");
                    }
                    miningResults.deleteCharAt(miningResults.length() - 1);
                    miningResults.append("@");
                }
                miningResults.append("\n");
            }
            System.out.println("Mining Results : " + miningResults);
//            System.out.println("DynamicCommunityMining.performCommunityMining() : DONE!");

            return miningResults;
        //} catch (IOException | IllegalArgumentException ex) {
        //    Logger.getLogger(DynamicCommunityMining.class.getName()).log(Level.SEVERE, null, ex);
        //    System.out.println("DynamicCommunityMining.performeCommunityMining() : exception");
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param pIGraph
     * @param datasetAsIS
     * @param properties
     * @return
     * @throws IOException
     * @throws IllegalArgumentException
     */
    private Vector<Snapshot<V, E>> runCommunityMiningAlgorithm(
            Properties properties, BooleanProperty isThreadRunningProperty){// throws IOException, IllegalArgumentException {
        System.out.println("DynamicCommuityMining.runCommunityMiningAlgorithm at the begining");
        try{
            if (!properties.containsKey(DynamicCommunityMiningProperties.algorithm)) {
                throw new IllegalArgumentException("Option algorithm is required.");
            }
            String algorithm = properties.get(DynamicCommunityMiningProperties.algorithm)
                    .toString();

            if (algorithm.equals(DynamicCommunityMiningAlgorithmName.FACETNET.toString())) {

                FacetNetInfo facetNetInfo = createFacetNetInfo(properties);

                FacetNet<V, E> facetNet = new FacetNet<>(facetNetInfo, dynaGraph);
                return facetNet.findCommunities();
            } else if (algorithm.equals(DynamicCommunityMiningAlgorithmName.LOCAL.toString())) {

                LocalInfo localInfo = createLocalInfo(properties);
                LocalMining<V, E> local = new LocalMining<>(localInfo, dynaGraph);
                Vector<Snapshot<V, E>> communities = local.findCommunities(isThreadRunningProperty);
    //            System.out.println("DynamicCommunityMining.runCommunityMiningAlgorithm() : DONE!");
                return communities;
            } else {
                throw new IllegalArgumentException(
                        "Not a valid algorithm - "
                        + "Algorithm shoudl be any of the following: "
                        + DynamicCommunityMiningAlgorithmName.values());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private FacetNetInfo createFacetNetInfo(Properties properties)
            throws IllegalArgumentException {
        double similarityThreshold = new Double(getProperty(
                DynamicCommunityMiningAlgorithmName.FACETNET.toString(),
                DynamicCommunityMiningProperties.similarityThreshold, properties)
                .toString());

        String facetnetLocation
                = getProperty(DynamicCommunityMiningAlgorithmName.FACETNET.toString(),
                        DynamicCommunityMiningProperties.facetnetLocation, properties)
                .toString();

        String facetnetMFile
                = getProperty(DynamicCommunityMiningAlgorithmName.FACETNET.toString(),
                        DynamicCommunityMiningProperties.facetnetMFile,
                        properties)
                .toString();

        double alpha
                = new Double(getProperty(
                                DynamicCommunityMiningAlgorithmName.FACETNET.toString(),
                                DynamicCommunityMiningProperties.alpha, properties)
                        .toString());

        int maxNbCluster = new Integer(getProperty(
                DynamicCommunityMiningAlgorithmName.FACETNET.toString(),
                DynamicCommunityMiningProperties.maxNbCluster, properties).toString());

        Integer nbCluster = null;
        if (getOptionalProperty(DynamicCommunityMiningProperties.nbCluster, properties) != null) {
            nbCluster = new Integer(getProperty(DynamicCommunityMiningAlgorithmName.FACETNET.toString(),
                    DynamicCommunityMiningProperties.nbCluster, properties).toString());
        }

        FacetNetInfo facetNetInfo = new FacetNetInfo(similarityThreshold,
                facetnetLocation, facetnetMFile, alpha, maxNbCluster, nbCluster);
        properties.put(DynamicCommunityMiningProperties.algName,
                facetNetInfo.getName());
        return facetNetInfo;

    }

    private LocalInfo createLocalInfo(Properties properties)
            throws IllegalArgumentException {
        double similarityThreshold = 0.5;
        if (getOptionalProperty(DynamicCommunityMiningProperties.similarityThreshold,
                properties) != null) {
            similarityThreshold = new Double(getOptionalProperty(
                    DynamicCommunityMiningProperties.similarityThreshold, properties)
                    .toString());
        }

        Metric metric = Metric.valueOf(getProperty(DynamicCommunityMiningAlgorithmName.LOCAL.toString(),
                DynamicCommunityMiningProperties.metric, properties).toString());

        Method method = Method.valueOf(getProperty(DynamicCommunityMiningAlgorithmName.LOCAL.toString(),
                DynamicCommunityMiningProperties.method, properties).toString());

        Boolean overlap = Boolean.valueOf(getProperty(DynamicCommunityMiningAlgorithmName.LOCAL.toString(),
                DynamicCommunityMiningProperties.overlap, properties).toString());

        Boolean hub = Boolean.valueOf(getProperty(DynamicCommunityMiningAlgorithmName.LOCAL.toString(),
                DynamicCommunityMiningProperties.hub, properties).toString());

        int history = new Integer(getProperty(DynamicCommunityMiningAlgorithmName.LOCAL.toString(),
                DynamicCommunityMiningProperties.history, properties).toString());

        double instability = new Double(getProperty(DynamicCommunityMiningAlgorithmName.LOCAL.toString(),
                DynamicCommunityMiningProperties.instability, properties).toString());

        String[] startVertices = null;

        if (getOptionalProperty(DynamicCommunityMiningProperties.startVertices,
                properties) != null) {
            startVertices = getOptionalProperty(
                    DynamicCommunityMiningProperties.startVertices, properties)
                    .toString().split(",");
        }

        if (startVertices != null
                && startVertices.length != dynaGraph.getAllTimeFrames().size()) {
            throw new IllegalArgumentException(
                    "Invalid startvertices provided. The size of the array has to be equal to the number of snapshots.");
        }

        LocalInfo localInfo = new LocalInfo(similarityThreshold, metric,
                method, overlap, hub, history, instability, startVertices);
        properties.put(DynamicCommunityMiningProperties.algName, localInfo.getName());
        return localInfo;

    }

    protected Object getProperty(String algorithm, String property, Properties properties) {
        if (!properties.containsKey(property)) {
            System.out.println(properties);
            throw new IllegalArgumentException("Option " + property
                    + " is required for " + algorithm);
        }
        return properties.get(property);
    }

    protected Object getOptionalProperty(String property, Properties properties) {
        return properties.get(property);
    }

    /**
     * This method find the matches (previous similar community) for all the
     * communities in the whole observation time
     *
     * @param matrices
     */
    private void communityMatching(Vector<Snapshot<V, E>> snapshots, double identityThreshold) {
        Matcher matcher = null;

        matcher = new OptimalBipartiteMatcher(snapshots, identityThreshold);

        // the match of each community is set by calling this function
        matcher.getMatching();

    }

}
