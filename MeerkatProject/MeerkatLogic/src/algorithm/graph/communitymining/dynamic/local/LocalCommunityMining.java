/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm.graph.communitymining.dynamic.local;

import algorithm.graph.communitymining.dynamic.auxilaryDS.Region;
import algorithm.graph.communitymining.dynamic.auxilaryDS.Snapshot;
import algorithm.graph.communitymining.dynamic.measure.graphcriteria.Lmetric;
import algorithm.graph.communitymining.dynamic.mininginfo.LocalInfo;
import algorithm.graph.communitymining.dynamic.util.SetComparator;
import algorithm.graph.communitymining.dynamic.util.VertexComparator;
import algorithm.graph.connectivity.CommunityConnectedComponentsUtils;
import datastructure.Partitioning;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import javafx.beans.property.BooleanProperty;

/**
 * 
 * "Local Community Identification".
 * 
 * @param <V>
 * @param <E>
 */
public class LocalCommunityMining<V extends IVertex, E extends IEdge<V>> {

    private final LocalInfo miningInfo;
    private final RegionIdentification<V, E> regionIdentification;
    private final Snapshot<V, E> snapshot;
    // it's called dummy graph, since we have to remove the vertices from
    // this graph to proceed.
    private List<V> lstVertex;
    
    public LocalCommunityMining(LocalInfo miningInfo, 
            Snapshot<V, E> snapshot) {
        this.miningInfo = miningInfo;
        this.snapshot = snapshot;
        this.regionIdentification = new RegionIdentification<>(snapshot);
    }

    public Partitioning<V> findCommunities(V startVertex, BooleanProperty isThreadRunningProperty) {
        lstVertex = snapshot.getGraph().getVertices(snapshot.getTimeFrame());
        
        
        List<Set<V>> connectedComponents = null;
        if (snapshot.getConnectedComponents() != null) {
            connectedComponents = snapshot.getConnectedComponents();
        }
        
        
        Partitioning<V> partitioning = new Partitioning<>();

        Collection<V> potentailSingletones = new HashSet<>();

        if (startVertex == null)
            startVertex = getHighestVertex(snapshot.getGraph(), snapshot.getTimeFrame(),
                            lstVertex, null);

        while (startVertex != null) {
            //added to stop thread computation
            if(isThreadRunningProperty.getValue()==false){
                    return partitioning;
            }
            Set<V> connectedComponent = null;

            // find out the next available connected component
            if (connectedComponents != null && connectedComponents.size() > 0) {
                connectedComponent = findComponent(connectedComponents,
                                startVertex);
            }
            
            if (connectedComponent == null) {
                connectedComponent = new HashSet<>();
                connectedComponent.add(startVertex);
            }
//            System.out.println("LocalCommunityMining.findCommunities() : connectedComponents --> \n" + connectedComponent);
//            System.out.println("LocalCommunityMining.findCommunities() : connectedComponents --> " + connectedComponents);

            Region<V,E> region = regionIdentification.identifyRegion(miningInfo,
                                                        connectedComponent);
            
//            System.out.println("LocalCommunityMining.findCommunities() : Region result (vertex set size) --> " + region.getVertices().size());
//            System.out.println("LocalCommunityMining.findCommunities() : regions are identified! ");
//            System.out.println("LocalCommunityMining.findCommunities() : isAcceptable(region, startVertex) --> " + isAcceptable(region, startVertex));
            
            if (isAcceptable(region, startVertex)) {
                partitioning.addCluster(new HashSet<>(region.getVertices()));
                snapshot.addAssignedVertices(new HashSet<>(region.getVertices()));

                if (!miningInfo.getOverlap()) {

                    for (V vertex : region.getVertices()) {
                        // Prevent overlap while the algorithm is running -
                        // remove Vertices that have been placed in a community
                        lstVertex.remove(vertex);
                    }

                    // based on the new dummygaph, some of the connected
                    // components may become disconnected components, so the
                    // connectivity of the components
                    // should be checked again
                    if (connectedComponents != null) {
                        CommunityConnectedComponentsUtils<V, E> utils = 
                                new CommunityConnectedComponentsUtils<>();
                        connectedComponents = utils
                                        .extractAllCommunityWeakConnectedComponents(
                                                        connectedComponents, 
                                                snapshot.getGraph(), 
                                                snapshot.getTimeFrame());
                    }
                }
            } else {
                // add the start vertex to the possible singletones
                potentailSingletones.add(startVertex);
            }

            startVertex = getHighestVertex(snapshot.getGraph(), 
                    snapshot.getTimeFrame(),
                    lstVertex, 
                    potentailSingletones);
//            System.out.println("LocalCommunityMining.findCommunities() : startVertex --> " + startVertex);
        }
        // add the singleton communities
        for (V vertex : potentailSingletones) {
                Set<V> vertices = new HashSet<>();
                vertices.add(vertex);
                partitioning.addCluster(vertices);
        }
//        System.out.println("LocalCommunityMining.findCommunities() : potentialSingletones --> " + potentailSingletones);

        
        //TODO: Commented out since it was taking a lot of time.
        if (!miningInfo.getHub()) {
                mergedCommunities(partitioning, true);
        }
//        System.out.println("LocalCommunityMining.findCommunities() : mergedCommunities Done!");

//        System.out.println("LocalCommunityMining.findCommunities() : partitions --> " + partitioning.getStatistics());
        
        return partitioning;
    }

    private Set<V> findComponent(List<Set<V>> connectedComponents, V vertex) {
        for (Set<V> component : connectedComponents)
            if (component.contains(vertex))
                return component;
        return null;
    }

    private boolean isAcceptable(Region<V,E> region, V startVertex) {

//        System.out.println("LocalCommunityMining.isAcceptable() : started");
        // we don't consider singleton as a community at first
        if (region == null || region.getVertices().size() <= 1)
                return false;

        if (!region.containsVertex(startVertex))
                return false;

        // sometimes after the examination phase the region become
        // disconnected
        CommunityConnectedComponentsUtils<V, E> utils = new CommunityConnectedComponentsUtils<>();
        
//        System.out.println("LocalCommunityMining.isAcceptable() : stating Calling communityconnectedComponentsUtils");   
        boolean isConnected = utils.isConnected(region.getVertices(), snapshot.getGraph(), snapshot.getTimeFrame());
//        System.out.println("LocalCommunityMining.isAcceptable() : ended");
        
//        System.out.println("LocalCommunityMining.isAcceptable() : isConnected --> " + isConnected);
        return isConnected;
    }

    private V getHighestVertex(IDynamicGraph<V, E> graph, TimeFrame tf, Collection<V> available,
                    Collection<V> potentialSingletones) {
        
//        System.out.println("LocalCommunityMining.getHighestVertex() : started");
        double maxDegree = -1;
        V maxVertex = null;

        for (V vertex : graph.getVertices(tf)) {
            if (available.contains(vertex)
                            && (potentialSingletones == null || !potentialSingletones
                                            .contains(vertex))) {
                double degree = graph.getNeighbors(vertex, tf).size();
                if (degree > maxDegree) {
                    maxDegree = degree;
                    maxVertex = vertex;
                }
                // make it consistent
                else if (degree == maxDegree) {
                    Map<V,Double> vertex_labels = new HashMap<>();
                    for (V v : graph.getVertices(tf)) {
                        vertex_labels.put(v, graph.getNeighbors(v, tf).size() + 0.0);
                    }
                    VertexComparator<V> comparator = new VertexComparator<>(
                                    vertex_labels);

                    if (comparator.compare(vertex, maxVertex) > 0) {
                        maxDegree = degree;
                        maxVertex = vertex;
                    }
                }
            }
        }
        
//        System.out.println("LocalCommunityMining.getHighestVertex() : maxVertex Id --> " + maxVertex.getId());
//        System.out.println("LocalCommunityMining.getHighestVertex() : ended");

        return maxVertex;
    }

    private void mergedCommunities(Partitioning<V> partitioning,
                    boolean singleton) {
        boolean mergeable = true;
        TreeSet<Set<V>> sizeSorted = new TreeSet<>(new SetComparator<>());
        sizeSorted.addAll(partitioning.getCommunities());

        while (true) {
            mergeable = false;

            Iterator<Set<V>> iterator = sizeSorted.iterator();
            Set<V> cluster = null;
            Set<V> match = null;
            while (iterator.hasNext()) {
                cluster = iterator.next();
                if (!singleton || (cluster.size() == 1)) {
                    // merged communities if possible
                    match = findBestMatch(partitioning, cluster);
                    if (match != null) {
                        mergeable = true;
                        break;
                    }
                }
//                System.out.println("Iterator has NEXT");

            }
            if (mergeable) {
                partitioning.getCommunities().remove(cluster);
                partitioning.getCommunities().remove(match);
                match.addAll(cluster);
                partitioning.getCommunities().add(match);

                // I couldn't remove from sizeSorted
                sizeSorted = new TreeSet<>(new SetComparator<>());
                sizeSorted.addAll(partitioning.getCommunities());

            } else {
                break;
            }
//            System.out.println("TRUE WHILE");
        }
    }

    /**
     * This Method takes a lot of time
     * @param partitioning
     * @param vertices
     * @return 
     */
    private Set<V> findBestMatch(Partitioning<V> partitioning, Set<V> vertices) {

        Set<V> match = null;
        CommunityConnectedComponentsUtils<V, E> utils = new CommunityConnectedComponentsUtils<>();

        Lmetric<V, E> lmetric = new Lmetric<>(snapshot.getGraph(), snapshot.getTimeFrame(), true);
        double maxModularity = lmetric.evaluateCommunity(vertices);
        double totalModularity = lmetric
                        .evaluateCommunities(partitioning.getCommunities());

        for (Set<V> cluster : partitioning.getCommunities()) {
            if (!cluster.equals(vertices)) {
                Set<V> mergedCluster = new HashSet<>(cluster);
                mergedCluster.addAll(vertices);

                if (utils.isConnected(mergedCluster, snapshot.getGraph(), snapshot.getTimeFrame())) {

                    double mergedModularity = lmetric
                                    .evaluateCommunity(mergedCluster);
                    Vector<Set<V>> mergedPartitioning = new Vector<>();
                    mergedPartitioning.addAll(partitioning.getCommunities());
                    mergedPartitioning.remove(cluster);
                    mergedPartitioning.remove(vertices);
                    mergedPartitioning.add(mergedCluster);
                    double totalMergedModularity = lmetric
                                    .evaluateCommunities(mergedPartitioning);

                    if (mergedModularity > maxModularity
                                    && totalMergedModularity >= totalModularity) {
                        maxModularity = mergedModularity;
                        match = cluster;
                    }
                }
            }
        }
        return match;
    }
}
