/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.communitymining.dynamic;

import algorithm.graph.communitymining.dynamic.auxilaryDS.Snapshot;
import algorithm.graph.communitymining.dynamic.measure.graphcriteria.Modularity;
import algorithm.graph.communitymining.dynamic.mininginfo.FacetNetInfo;
import datastructure.Partitioning;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;

/**
 *
 * @author aabnar
 */
public class FacetNet<V extends IVertex, E extends IEdge<V>> 
                                            extends CommunityMining<V, E> {

    private FacetNetInfo miningInfo;

    public FacetNet(FacetNetInfo miningInfo, IDynamicGraph<V,E> pIGraph) {
        super();
        this.miningInfo = miningInfo;
        this.dynamicGraph = pIGraph;
    }
    @Override
    public Vector<Snapshot<V, E>> findCommunities(BooleanProperty isThreadRunningProperty) {
        return null;
    }
    @Override
    public Vector<Snapshot<V, E>> findCommunities() {

        // save the ordering
        List<V> allVs = dynamicGraph.getAllVertices();

        // determine number of clusters
        if (miningInfo.getNbCluster() == null) {
            determineNbCluster(allVs);
        }

        // create W_Cube
        double[][][] W = createNormalizedW(dynamicGraph);

        // call facetnet
        double[] memberships = callFacetNet(W, miningInfo.getNbCluster(),
                        miningInfo.getAlpha());

        // extract communities
        Vector<Partitioning<V>> partitionings = extractCommunities(
                        miningInfo.getNbCluster(), allVs,
                        memberships);

        
        for (TimeFrame sId : dynamicGraph.getAllTimeFrames()) {
            Map<E,Double> edgeWeights = new HashMap<>();
            for (E e : dynamicGraph.getEdges(sId)) {
                edgeWeights.put(e, e.getWeight());
            }
            
            Map<V,Double> vertexLabel = new HashMap<>();
            for (V v : dynamicGraph.getVertices(sId)) {
                vertexLabel.put(v, v.getId()+ 0.0);
            }
            
            int snapshotIndex = dynamicGraph.getAllTimeFrames().indexOf(sId);
            Snapshot<V, E> snapshot = new Snapshot<>(
                    snapshotIndex,
                    dynamicGraph.getGraph(tf).getId() + "",
                    dynamicGraph, 
                    tf,
                    vertexLabel,
                    null);

            snapshot.setPartitining(partitionings.get(snapshotIndex));

            if (snapshots == null) {
                snapshots = new Vector<>();
            }
            snapshots.add(snapshot);
        }
        return snapshots;
    }

    private double[] callFacetNet(double[][][] W, int nbCluster, double alpha) {
        MatlabProxyFactoryOptions options = new MatlabProxyFactoryOptions.Builder()
                        .setUsePreviouslyControlledSession(true).setHidden(true)
                        .setMatlabLocation(null).build();

        MatlabProxyFactory factory = new MatlabProxyFactory(options);
        MatlabProxy proxy;
        double[] memberships = null;

        try {
            proxy = factory.getProxy();
            proxy.eval("cd('" + miningInfo.getFacetnetLocation() + "')");

            memberships = ((double[]) proxy.returningFeval(
                    miningInfo.getFacetnetMFile(), 1, W, nbCluster, 1 - alpha)[0]);
            proxy.disconnect();

        } catch (MatlabConnectionException e) {
        
        } catch (MatlabInvocationException ex) {
            Logger.getLogger(FacetNet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return memberships;
    }

    private void determineNbCluster(List<V> nodes) {
//        GraphAggregator<V, E> aggregator = new GraphAggregator<>();
//        IStaticGraph<V, E> aggregateGraph = 
//                aggregator.aggregateGraph(dynamicGraph);

        double maxModularity = -1;
        int optimalNbCluster = -1;

        for (int n = 1; n < nodes.size() && n < miningInfo.getMaxNbCluster(); n++) {

//            Vector<IStaticGraph<V, E>> graphs = new Vector<>();
//            graphs.add(aggregateGraph);

            // create W_Cube
            double[][][] W = createNormalizedW(dynamicGraph);

            // call facetnet
            double[] memberships = callFacetNet(W, n, miningInfo.getAlpha());

            Modularity<V, E> modularity = new Modularity<>(dynamicGraph, tf);
            
            Vector<Partitioning<V>> partitionings = extractCommunities(
                              n, nodes, memberships);
            // the vector size is 1, since we only have one aggregate graph
            double mod = modularity.evaluateCommunities(partitionings.get(0)
                              .getCommunities());
            if (mod > maxModularity) {
                maxModularity = mod;
                // sometimes it is less than m
                optimalNbCluster = partitionings.get(0).getCommunities().size();
            }

        }
        miningInfo.setNbCluster(optimalNbCluster);
    }

    private Vector<Partitioning<V>> extractCommunities(
                    int nbCluster, List<V> nodes,
                    double[] memberships) {

        Vector<Partitioning<V>> partitionings = new Vector<>();
        for (int sId = 0; sId < dynamicGraph.getAllTimeFrames().size(); sId++) {
            Vector<Set<V>> clusters = new Vector<>(nbCluster);
            for (int c = 0; c < nbCluster; c++) {
                clusters.add(new HashSet<>());
            }

            for (V v : (dynamicGraph.getVertices(dynamicGraph.getAllTimeFrames().get(sId)))) {
                int vIndex = nodes.indexOf(v);
                int cIndex = (int) memberships[sId * nodes.size() + vIndex] - 1;
                clusters.get(cIndex).add(v);
            }

            Partitioning<V> partitioning = new Partitioning<>();
            for (int c = 0; c < nbCluster; c++) {
                if (clusters.get(c).size() > 0) {
                    partitioning.addCluster(clusters.get(c));
                }
            }
            partitionings.add(partitioning);
        }

        return partitionings;
    }

    private double[][][] createNormalizedW(IDynamicGraph<V,E> pDynaGraph) {

        int maxNodeSize = 0;
        for (TimeFrame t : pDynaGraph.getAllTimeFrames()) {
            int curGraphNodeSize = pDynaGraph.getVertices(t).size();
            if ( curGraphNodeSize > maxNodeSize) {
                maxNodeSize = curGraphNodeSize;
            }
        }
        // create W_Cube
        double[][][] W = new double[pDynaGraph.getAllTimeFrames().size()]
                                    [maxNodeSize][maxNodeSize];

        // maxWeight
        double maxWeight = maxWeight(pDynaGraph);

        int t_index = 0;
        for (TimeFrame t : pDynaGraph.getAllTimeFrames()) {
            List<V> lstAllVertices = pDynaGraph.getVertices(t);
            for (int i = 0; i < lstAllVertices.size(); i++) {
                for (int j = 0; j < lstAllVertices.size(); j++) {

                    E e = pDynaGraph.findEdge(lstAllVertices.get(i), 
                                            lstAllVertices.get(j), t);
                    if (e == null) {
                        W[t_index][i][j] = 0.0;
                    } else {
                        W[t_index][i][j] += 
                            e.getWeight() / maxWeight;
                    }
                }
            }
            t_index++;
        }

        return W;
    }

    private double maxWeight(IDynamicGraph<V,E> pDynamicGraph) {

        double maxWeight = 0;
        for (TimeFrame t : pDynamicGraph.getAllTimeFrames()) {
            for (E e : pDynamicGraph.getEdges(t)) {
                double w = e.getWeight();
                if (w > maxWeight) {
                    maxWeight = w;
                }
            }
        }

        return maxWeight;
    }

    @Override
    public String toString() {
        return "FacetNet";
    }
}

