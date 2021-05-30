/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.communitymining.dynamic;

import algorithm.graph.communitymining.dynamic.auxilaryDS.Region;
import algorithm.graph.communitymining.dynamic.auxilaryDS.Snapshot;
import algorithm.graph.communitymining.dynamic.local.LocalCommunityMining;
import algorithm.graph.communitymining.dynamic.measure.graphcriteria.Lmetric;
import algorithm.graph.communitymining.dynamic.mininginfo.LocalInfo;
import algorithm.graph.communitymining.dynamic.util.VertexComparator;
import algorithm.graph.connectivity.CommunityConnectedComponentsUtils;
import config.dynamiccommunitymining.Method;
import datastructure.Partitioning;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import javafx.beans.property.BooleanProperty;

/**
 *
 * @author aabnar
 */
public class LocalMining<V extends IVertex, E extends IEdge<V>> extends CommunityMining<V,E> {
    private LocalInfo miningInfo;

    public LocalMining(LocalInfo miningInfo, IDynamicGraph<V,E> pDynaGraph) {
        super();
        this.miningInfo = miningInfo;
        this.dynamicGraph = pDynaGraph;
    }
    
    @Override
    public Vector<Snapshot<V, E>> findCommunities(){
        return null;
    }
    
    @Override
    public Vector<Snapshot<V, E>> findCommunities(BooleanProperty isThreadRunningProperty) {
        try{
            for (int sId = 0; sId < dynamicGraph.getAllTimeFrames().size(); sId++) {
                System.out.println("LocalMining.findCommunities() : TimeFrame Index --> " +sId);
                //added to stop thread computation
                if(isThreadRunningProperty.getValue()==false){
                    return snapshots;
                }
                TimeFrame tf = dynamicGraph.getAllTimeFrames().get(sId);
                Map<V, String> mapVertexLabels = new HashMap<>();
                for (V v : dynamicGraph.getVertices(tf)) {
                    mapVertexLabels.put(v, v.getId()+"");
                }

                Map<E,Double> mapEdgeWeights = new HashMap<>();
                for (E e : dynamicGraph.getEdges(tf)) {
                    mapEdgeWeights.put(e, e.getWeight());
                }



    //            Map<V,Vector<V>> neighbors = new HashMap<>();
    //            for (V v : dynamicGraph.getGraph(tf).getAllVertices()) {
    //                Vector<V> vect = new Vector<>();
    //                vect.addAll(dynamicGraph.getGraph(tf).getNeighbors(v));
    //                neighbors.put(v, vect);
    //            } 
    //            snapshot.setVerticesNeighbours(neighbors);

                //snapshot.setEdgeWeights(graphReader.getEdgeWeights(sId));

                V start = null;
                if (miningInfo.getStartVertices() != null) {
                    start = dynamicGraph.getVertex(
                                Integer.parseInt(miningInfo.getStartVertices()[sId]));
                    if (!dynamicGraph.getVertices(tf).contains(start)) {
                            start = null;
                    }
                }
    //            System.out.println("LocalMining.findCommunities() : startVertex --> " + start);
                //added to stop thread computation
                if(isThreadRunningProperty.getValue()==false){
                    return snapshots;
                }
                List<Set<V>> connectedComponents = null;
                
                if (sId != 0 && miningInfo.getMethod().equals(Method.Direct)) {
                    List<Set<V>> previousCommunities = snapshots.get(sId - 1)
                                    .getPartitining().getCommunities();
                    //added to stop thread computation
                    if(isThreadRunningProperty.getValue()==false){
                        return snapshots;
                    }
                    System.out.println("LocalMining.findCommunities() line 103 reached ");
                    CommunityConnectedComponentsUtils<V, E> utils = 
                            new CommunityConnectedComponentsUtils<>();

                    
                    System.out.println("LocalMining.findCommunities() line 106 reached ");
                    connectedComponents = 
                            utils.extractAllCommunityWeakConnectedComponents(
                                    previousCommunities, dynamicGraph, tf);
                    System.out.println("LocalMining.findCommunities() line 110 reached ");
                    //added to stop thread computation
                    if(isThreadRunningProperty.getValue()==false){
                        return snapshots;
                    }
    //                System.out.println("LocalMining.findCommunities() : foundWeakluCCs");
                    if (miningInfo.getInstability() > 0)
                            insertingInstability(connectedComponents,
                                            dynamicGraph, tf);
                    //added to stop thread computation
                    if(isThreadRunningProperty.getValue()==false){
                        return snapshots;
                    }
                    connectedComponents = breakDownComponents(connectedComponents, snapshots.get(sId - 1));
                    System.out.println("LocalMining.findCommunities() line 124 reached ");
    //                System.out.println("LocalMining.findCommunities() : breakDownComponents DONE!");                
    //
                }

    //            System.out.println("LocalMining.findCommunities() : connectedComponents --> " + connectedComponents );
                Map<V,Double> vertex_labels =new HashMap<>();
                for (V v : dynamicGraph.getVertices(tf)) {
                    vertex_labels.put(v, v.getId() + 0.0);
                }
                System.out.println("LocalMining.findCommunities() line 136 reached ");
                Snapshot<V, E> snapshot = new Snapshot<>(sId,
                        tf.toString(), 
                        dynamicGraph, tf,
                        vertex_labels,
                        connectedComponents);

                LocalCommunityMining<V, E> localMining = 
                        new LocalCommunityMining<>(miningInfo, snapshot);
                //added to stop thread computation
                if(isThreadRunningProperty.getValue()==false){
                    return snapshots;
                }

                Partitioning<V> partitioning = localMining.findCommunities(start, isThreadRunningProperty);
                snapshot.setPartitining(partitioning);

    //            System.out.println("LocalMining.findCommunities() : snapshot --> " + snapshot.getPartitining());
                if (snapshots == null) {
                    snapshots = new Vector<>();
                }
                snapshots.add(snapshot);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
//        System.out.println("LocalMining.findCommunities() : DONE!");
        return snapshots;
    }

    private void insertingInstability(List<Set<V>> connectedComponents,
                             IDynamicGraph<V, E> graph, TimeFrame tf) {

        Random randomGenerator = new Random();
        randomGenerator.setSeed(123456789);
        boolean reArrange = false;
        Iterator<Set<V>> componentIterator = connectedComponents.iterator();
        while (componentIterator.hasNext()) {
            Set<V> component = componentIterator.next();
            if (component.size() > 1) {
                Iterator<V> vertexIterator = component.iterator();
                while (vertexIterator.hasNext()) {
                    vertexIterator.next();
                    double randomValue = randomGenerator.nextInt(1001) / 1000.0;
                    if (randomValue <= miningInfo.getInstability()) {
                        vertexIterator.remove();
                        reArrange = true;
                    }
                }
            }
        }

        if (reArrange) {
            CommunityConnectedComponentsUtils<V, E> utils = 
                    new CommunityConnectedComponentsUtils<>();

            connectedComponents = utils.extractAllCommunityWeakConnectedComponents(
                        connectedComponents, graph, tf);
        }
    }

    private List<Set<V>> breakDownComponents(List<Set<V>> connectedComponents,
            Snapshot psnapshot) {
        Lmetric<V, E> lmetric = new Lmetric<>(dynamicGraph, tf, false);
        boolean reArrange = false;
        for (Set<V> component : connectedComponents) {
            Map<V,Double> mapAllVertices = new HashMap<>();
            for (TimeFrame timeframe : dynamicGraph.getAllTimeFrames()) {
                for (V v : dynamicGraph.getVertices(timeframe)) {
                    mapAllVertices.put(v, v.getId() + 0.0);
                }
            }
            TreeSet<V> orderedVertices = 
                new TreeSet<>(new VertexComparator<V>(mapAllVertices));
            orderedVertices.addAll(component);
            
            Region<V,E> region = new Region<>(/*snapshot.getVerticesNeighbours(),
                    snapshot.getEdgeWeights()*/
                    psnapshot.getLabels(),
                    psnapshot.getGraph(), /*mapAllVertices,*/
                    psnapshot.getTimeFrame(),
                    orderedVertices, null);
            
            Iterator<V> vertexIterator = region.getVertices().iterator();
            while (vertexIterator.hasNext()) {
                V vertex = vertexIterator.next();
                @SuppressWarnings("unchecked")
                Region<V,E> communityBeforeMerge = (Region<V,E>) region.clone();
                communityBeforeMerge.removeVertex(vertex);
                if (lmetric.evaluateRegion(communityBeforeMerge) >= lmetric
                                .evaluateRegion(region)) {
                    component.remove(vertex);
                    region = communityBeforeMerge;
                    reArrange = true;
                }
            }
        }
        if (reArrange) {
            CommunityConnectedComponentsUtils<V, E> utils = 
                    new CommunityConnectedComponentsUtils<>();
            connectedComponents = 
                    utils.extractAllCommunityWeakConnectedComponents(
                            connectedComponents, psnapshot.getGraph(), 
                            psnapshot.getTimeFrame());
        }
        
        return connectedComponents;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(miningInfo.getMethod()).append(": ");
        stringBuilder.append(miningInfo.getMetric()).append("-metric");

        return stringBuilder.toString();
    }
}
