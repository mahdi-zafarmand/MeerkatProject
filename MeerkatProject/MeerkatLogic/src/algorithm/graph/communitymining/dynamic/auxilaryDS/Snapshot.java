/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.communitymining.dynamic.auxilaryDS;

import algorithm.graph.communitymining.dynamic.util.CommunityComparator;
import datastructure.Partitioning;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public class Snapshot<V extends IVertex, E extends IEdge<V>> {
    private final int id;
    private final String name;
    private final IDynamicGraph<V, E> graph;
    private final TimeFrame tf;
    private Partitioning<V> partitining;
    private final ArrayList<EvolvingCommunity<V>> communities;
    private final List<Set<V>> connectedComponents;
    private Set<V> assignedVertices;
    private final Map<V,Double> vertex_labels;

    public Snapshot(int id, String name, IDynamicGraph<V,E> pDynaGraph, TimeFrame tf,
            Map<V,Double> pmpVLabels, List<Set<V>> lstCComp) {
        this.id = id;
        this.name = name;
        this.graph = pDynaGraph;
        this.tf = tf;
        this.communities = new ArrayList<>();
        this.assignedVertices = new HashSet<>();
        this.connectedComponents = lstCComp;
        this.vertex_labels = pmpVLabels;
    }

    public int getId() {
        return id;
    }

    public IDynamicGraph<V, E> getGraph() {
        return graph;
    }
    
    public TimeFrame getTimeFrame() {
        return this.tf;
    }
    
    public Map<V,Double> getLabels() {
        return this.vertex_labels;
    }

    public Partitioning<V> getPartitining() {
        return partitining;
    }

    public void setPartitining(Partitioning<V> partitining) {
        this.partitining = partitining;

        
//        Map<V,String> vertex_labels = new HashMap<>();
//        for (V v : graph.getVertices(tf)) {
//            vertex_labels.put(v, v.getId() + "");
//        }
        int counter = 0;
        TreeSet<Set<V>> orderedCommunities = new TreeSet<>(
                        new CommunityComparator<>(vertex_labels));
        orderedCommunities.addAll(partitining.getCommunities());

        for (Set<V> c : orderedCommunities) {
                EvolvingCommunity<V> community = new EvolvingCommunity<>(c,
                                counter++, id);
                communities.add(community);
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String res = "[";
        res += " Graph(" + graph + ") \n Communities( " + partitining + ")]";
        return res;
    }

    public List<EvolvingCommunity<V>> getCommunities() {
        return communities;
    }

    public int getNumberOfGroups() {
        return communities.size();
    }

    public Set<V> getAssignedVertices() {
        return assignedVertices;
    }
    
    public List<Set<V>> getConnectedComponents() {
        return this.connectedComponents;
    }

    public void setAssignedVertices(Set<V> assignedVertices) {
        this.assignedVertices = assignedVertices;
    }

    public void addAssignedVertex(V vertex) {
        this.assignedVertices.add(vertex);
    }

    public void addAssignedVertices(Set<V> vertices) {
        this.assignedVertices.addAll(vertices);
    }
    
    public double computeSimilarity(Set<V> com1, Set<V> com2, double threshold) {
        int common = 0;
        for (V v : com1) {
            if (com2.contains(v)) {
                common++;
            }
        }
        
        double sim = common / Math.max(com1.size(), com2.size());
        
        if (sim >= threshold) {
            return sim;
        } else {
            return 0;
        }
    }
}