/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm.graph.communitymining.dynamic.auxilaryDS;

import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.collections15.keyvalue.MultiKey;

/**
 * 
 * This class represents the data structure for region which is used in local
 * community mining
 */

public class Region<V extends IVertex, E extends IEdge<V>> implements Cloneable{

    private Map<V, Double> vertices_label;

    private Set<V> assigendVertices;

    private Set<V> regionVertices;
    private Set<V> boundaryVertices;
    private Set<V> shellVertices;

    //private Map<V, Vector<V>> verticesNeighbours;
    //private MultiKeyMap<V, Double> edgeWeights;

    private double region2RegionWeight;
    private double boundary2RegionWeight;
    private double boundary2ShellWeight;

    private IDynamicGraph<V,E> igraph; 
    private TimeFrame tf;
    
    public Region(/*Map<V, Vector<V>> verticesNeighbours,
                    MultiKeyMap<V, Double> edgeWeights,*/ Map<V, Double> vertices_label,
                    IDynamicGraph<V,E> pIgraph,
                    TimeFrame tf,
                    TreeSet<V> vertices, Set<V> assignedVertices) {

            this.vertices_label = vertices_label;
            this.assigendVertices = assignedVertices;
            //this.verticesNeighbours = verticesNeighbours;
            //this.edgeWeights = edgeWeights;

            this.igraph = pIgraph;
            this.tf = tf;
            regionVertices = new HashSet<>();
            boundaryVertices = new HashSet<>();
            shellVertices = new HashSet<>();

            configureAreasAndUpdateWeights(vertices);
    }

    @SuppressWarnings("unchecked")
    public void configureAreasAndUpdateWeights(TreeSet<V> vertices) {
        this.regionVertices.addAll(vertices);
        Set<MultiKey<V>> visitedEdge = new HashSet<>();

        for (V vertex : vertices) {
            double current_in = 0;
            double current_out = 0;

            // discover the new shell vertices
            boolean isBoundary = false;
            List<V> neighbors = igraph.getNeighbors(vertex,tf);
            if (neighbors != null) {
                for (V adjV : neighbors) {
                    if (assigendVertices == null
                                    || !assigendVertices.contains(adjV)) {
                        
                        E e = igraph.findEdge(vertex, adjV, tf);
                        if (!regionVertices.contains(adjV)) {
//                            E e = igraph.findEdge(vertex, adjV, tf);
//                            System.out.println(e);
                            current_out += e.getWeight();
                            if (!isBoundary) {
                                isBoundary = true;
                            }
                            if (!shellVertices.contains(adjV)) {
                                shellVertices.add(adjV);
                            }
                        } else {
                            if (!visitedEdge.contains(new MultiKey<>(vertex,
                                            adjV))) {
                                
                                current_in += e.getWeight();
                                visitedEdge.add(new MultiKey<>(vertex, adjV));
                                visitedEdge.add(new MultiKey<>(adjV, vertex));
                            }

                            // make sure the adjV remains in boundary node
                            boolean isAdjBoundary = false;
                            if (boundaryVertices.contains(adjV)) {
                                for (V adjAdjV : igraph.getNeighbors(adjV,tf)) {
                                    if (!regionVertices.contains(adjAdjV)) {
                                        isAdjBoundary = true;
                                        break;
                                    }
                                }
                                // if the adjeV doesn't have connection to
                                // outside
                                // remove it from boundary
                                if (!isAdjBoundary) {
                                    boundaryVertices.remove(adjV);
                                }
                            }
                        }
                    }
                }
            }

            region2RegionWeight += current_in;
            boundary2ShellWeight += current_out;

            // add it to boundary if it has connection to outside
            if (isBoundary) {
                boundaryVertices.add(vertex);
                boundary2RegionWeight += current_in;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void addVertex(V vertex) {
        this.regionVertices.add(vertex);

        double current_in = 0;
        double current_out = 0;
        // discover the new shell vertices
        boolean isBoundary = false;
        if (igraph.getNeighbors(vertex,tf) != null) {
            for (V adjV : igraph.getNeighbors(vertex,tf)) {
                if (assigendVertices == null
                                || !assigendVertices.contains(adjV)) {
                    if (!regionVertices.contains(adjV)) {
                        E e = igraph.findEdge(vertex, adjV, tf);
                        current_out += e.getWeight();
                        if (!isBoundary) {
                            isBoundary = true;
                        }
                        if (!shellVertices.contains(adjV)) {
                            shellVertices.add(adjV);
                        }

                    } else {
                        E e = igraph.findEdge(vertex, adjV, tf);
                        current_in += e.getWeight();
                        // make sure the adjV remains in boundary node
                        boolean isAdjBoundary = false;
                        if (boundaryVertices.contains(adjV)) {
                            for (V adjAdjV : igraph.getNeighbors(adjV,tf)) {
                                if (!regionVertices.contains(adjAdjV)) {
                                    isAdjBoundary = true;
                                    break;
                                }
                            }
                            // if the adjeV doesn't have connection to outside
                            // remove it from boundary
                            if (!isAdjBoundary) {
                                boundaryVertices.remove(adjV);
                            }
                        }
                    }
                }
            }
        }
        region2RegionWeight += current_in;
        boundary2ShellWeight += (current_out - current_in);

        // add it to boundary if it has Es to outside
        if (isBoundary) {
                boundaryVertices.add(vertex);
                boundary2RegionWeight += current_in;
        }
        // remove it from shell if it was a shell
        if (shellVertices.contains(vertex)) {
                shellVertices.remove(vertex);
        }

    }

    @SuppressWarnings("unchecked")
    public void removeVertex(V vertex) {
        this.regionVertices.remove(vertex);

        double current_in = 0;
        double current_out = 0;

        // find boundary nodes
        boolean isShell = false;
        if (igraph.getNeighbors(vertex,tf) != null) {
            for (V adjV : igraph.getNeighbors(vertex,tf)) {
                if (regionVertices.contains(adjV)) {
                    // since this adjV has connection to outside world,
                    // it is a boundary
                    if (!boundaryVertices.contains(adjV))
                            boundaryVertices.add(adjV);
                    if (!isShell)
                            isShell = true;

                    E e = igraph.findEdge(vertex, adjV, tf);
                    current_in += e.getWeight();
                } else {
                    E e = igraph.findEdge(vertex, adjV, tf);
                    current_out += e.getWeight();
                }
                // make sure the adjV remains in shell node
                boolean isAdjShell = false;
                if (shellVertices.contains(adjV)) {
                    for (V adjAdjV : igraph.getNeighbors(adjV,tf)) {
                        if (regionVertices.contains(adjAdjV)) {
                            isAdjShell = true;
                            break;
                        }
                    }
                    // if the adjeV doesn't have connection to inside
                    // remove it
                    if (!isAdjShell) {
                        shellVertices.remove(adjV);
                    }
                }
            }
        }
        region2RegionWeight -= current_in;
        boundary2ShellWeight -= (current_out - current_in);

        // if it has connection to the community, keep it as shell
        if (isShell)
            shellVertices.add(vertex);

        // if it was boundary node, remove it from boundary nodes
        if (boundaryVertices.contains(vertex)) {
            boundaryVertices.remove(vertex);
            boundary2RegionWeight -= current_in;
        }

    }

    public boolean containsVertex(V V) {
        return regionVertices.contains(V);
    }

    public int size() {
        return regionVertices.size();
    }

    public boolean isEmpty() {
        if (this.regionVertices.isEmpty())
            return true;

        return false;
    }

    public Set<V> getVertices() {
        return regionVertices;
    }

    public Set<V> getShellVertices() {
        return shellVertices;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("[");
        for (V v : regionVertices)
                string.append(v.getId()).append(",");
        // remove extra ,
        string.deleteCharAt(string.length()-1);
        string.append("]");
        return string.toString();
    }

    public double getRegion2RegionWeight() {
        return region2RegionWeight;
    }

    public double getBoundary2RegionWeight() {
        return boundary2RegionWeight;
    }

    public double getBoundary2ShellWeight() {
        return boundary2ShellWeight;
    }

    public int getRegionSize() {
        return regionVertices.size();
    }

    public int getBoundarySize() {
        return boundaryVertices.size();
    }

    public int getShellSize() {
        return shellVertices.size();
    }
    
    public IDynamicGraph<V,E> getGraph() {
        return igraph;
    }
    
    public TimeFrame getTimeFrame() {
        return tf;
    }

    public Map<V,Double> getLables() {
        return vertices_label;
    }
    
    @Override
    public Object clone() {
        try {
            @SuppressWarnings("unchecked")
            Region<V,E> cloned = (Region<V,E>) super.clone();
            cloned.igraph = igraph;
            cloned.tf = tf;
            cloned.assigendVertices = assigendVertices;

            cloned.regionVertices = new HashSet<>(regionVertices);
            cloned.boundaryVertices = new HashSet<>(boundaryVertices);
            cloned.shellVertices = new HashSet<>(shellVertices);

            cloned.region2RegionWeight = region2RegionWeight;
            cloned.boundary2ShellWeight = boundary2ShellWeight;
            cloned.boundary2RegionWeight = boundary2RegionWeight;

            return cloned;
        } catch (CloneNotSupportedException e) {
            System.out.println(e);
            return null;
        }
    }
}
