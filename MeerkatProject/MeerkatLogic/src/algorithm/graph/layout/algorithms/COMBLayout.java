/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm.graph.layout.algorithms;


import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IStaticGraph;
import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.graph.impl.DynamicGraph;
import datastructure.core.graph.impl.Edge;
import datastructure.core.graph.impl.StaticGraph;
import datastructure.core.graph.impl.Vertex;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class COMBLayout<V extends IVertex, E extends IEdge<V>> extends Layout<V,E>{

    HashMap<V, List<V>> hmpV2LstOfComs = new HashMap<>();
        
    public COMBLayout(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String[] parameters) {
        super(pIGraph, tf, parameters);
    }


    @Override
    public Type getType() {
        return type;
    }

    public void run() {
        blnDone = false;
        if(!running){
                return;
        }
        
        
        Map<V, double[]> mapVertexLocations = new HashMap<>(dynaGraph.getVertices(tf).size());
        // Layout a graph with the communities represented as vertices.
        IDynamicGraph<V,E> representative = createRepresentativeGraph();
        Set<V> setNoComVertices = getVerticesWithoutCommunities();
        for (V vertex : setNoComVertices) {
            if(!running){
                break;
            }
            V outlierV = (V) new Vertex(1.0 / dynaGraph.getVertices(tf).size());
            representative.addVertex(outlierV, tf);
            hmpV2LstOfComs.put(outlierV, new LinkedList<>());
            hmpV2LstOfComs.get(outlierV).add(vertex);
        }
        
        Layout<V,E> layout = new CircleLayout<>(representative, tf, null);
        Thread th = new Thread(layout);
        th.start();
        try {
            th.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(COMBLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        layout = new ModifiedFRLayout<>(representative, tf , null);
        th = new Thread(layout);
        th.start();
        try {
            th.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(COMBLayout.class.getName()).log(Level.SEVERE, null, ex);
        }

        double xmin = Double.MAX_VALUE;
        double xmax = Double.MIN_VALUE;
        double ymin = Double.MAX_VALUE;
        double ymax = Double.MIN_VALUE;
        
        // Layout each vertex in the community based on the representative
        // graph.
        System.out.println("ComBLayout.run() : running FRLayout within each community...");
        for (V rep : representative.getVertices(tf)) {
            
            if(!running){
                break;
            }
            
//            System.out.println("COMBLayout.run() : community size: " + hmpV2LstOfComs.get(rep).size());                       
            double xc = getX(rep) * getWidth();
            double yc = getY(rep) * getHeight();
            
            if (Double.isNaN(yc) || Double.isNaN(xc)) {
                System.out.println("COMBLayout.run() : representative graph has problem");
                
            }
            
            double radius = rep.getWeight() * Math.min(getWidth(), getHeight());

            // Run the layout algorithm on the community.
            if (hmpV2LstOfComs.get(rep).size() > 1) {
                layout = new ModifiedFRLayout(dynaGraph, hmpV2LstOfComs.get(rep), tf, null);
                th = new Thread(layout);
                th.start();
                try {
                    th.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(COMBLayout.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            for (V v : hmpV2LstOfComs.get(rep)) {
                double vx = getX(v);
                double vy = getY(v);

                double[] newLocation = new double[2];
                newLocation[0] = (vx * 2 * radius + (xc - radius) ) / getWidth();
                newLocation[1] = (vy * 2 * radius + (yc - radius) ) / getHeight();

                mapVertexLocations.put(v, newLocation);
                setLocation(v, newLocation[0], newLocation[1]);
                if (newLocation[0] < xmin) {
                    xmin = newLocation[0];
                } else if (newLocation[0] > xmax) {
                    xmax = newLocation[0];
                }

                if(newLocation[1] < ymin) {
                    ymin = newLocation[1];
                } else if (newLocation[1] > ymax) {
                    ymax = newLocation[1];
                }
            }
            
            
            for (V v : setNoComVertices) {
                double vx = getX(v);
                double vy = getY(v);
                
                if (vx < xmin) {
                    xmin = vx;
                } else if (vx > xmax) {
                    xmax = vx;
                }
                
                if(vy < ymin) {
                    ymin = vy;
                } else if (vy > ymax) {
                    ymax = vy;
                }
            }
        }
        if(!running)
            return;
        
        // Normalizing between 0 and 1;
        
        double scale = Math.max(xmax - xmin, ymax - ymin);
        if (scale > 0) {
            for (V v : dynaGraph.getVertices(tf)) {
                double[] locate = mapVertexLocations.get(v);

                System.out.println("ComBLayout.run() : locate = " + locate[0] + "," + locate[1]);
                setLocation(v, (locate[0] - xmin) / scale, (locate[1]-ymin) / scale);
            }
        }
        
        updateDataStructure();
        blnDone = true;
    }

    @Override
    public String toString() {
        return type.toString();
    }


    private IDynamicGraph<V, E> createRepresentativeGraph() {
        
        
        
        assert (dynaGraph != null);

        // Add the representative vertices.
        HashMap<String, LinkedList<V>> communities
                = new HashMap<>();

        for (V v : dynaGraph.getVertices(tf)) {

            if (v.getSystemAttributer().getAttributeNames()
                    .contains(MeerkatSystem.COMMUNITY)) {
                String comId
                        = v.getSystemAttributer().getAttributeValue(
                                MeerkatSystem.COMMUNITY,
                                tf);
                if (comId != null) {
                    if (!communities.keySet().contains(comId)) {
                        communities.put(comId, new LinkedList<>());
                    }
                    communities.get(comId).add(v);
                }
            }
        }

        IDynamicGraph<V, E> representative = new DynamicGraph<>(communities.size(), 0);
        representative.addGraph(tf, new StaticGraph<>(communities.size(),0));

        for (LinkedList<V> community : communities.values()) {
            if(!running){
                break;
            }
//            double avgRadius = getAverageRadius(community);
//
//            double circumference = avgRadius * community.size();

            // System.out.println("circumference is " + circumference);
            double cRadius = community.size() / (2 * dynaGraph.getVertices(tf).size() + 0.0);

            V comV = (V) new Vertex(cRadius);

            hmpV2LstOfComs.put(comV, community);

            representative.addVertex(comV, tf);
        }
        
        /* adding edges */
        for (int eid : dynaGraph.getGraph(tf).getAllEdgeIds()) {
            if(!running){
                break;
            }
            E e = dynaGraph.getEdge(eid);
            V v = e.getSource();
            V u = e.getDestination();
            if (hmpV2LstOfComs.containsKey(v) && hmpV2LstOfComs.containsKey(u)){
                for (V comV : hmpV2LstOfComs.get(v)) {
                    for (V comU : hmpV2LstOfComs.get(u)) {
                        E newE = (E) new Edge<>(comV, comU, false);
                        representative.addEdge(newE, tf);
                    }
                }
            }
        }

//        System.out.println("ComCLayout.createRepresentativeGraph() : " + 
//                representative.getVertices(tf).size() + "," + 
//                representative.getEdges(tf).size());
        return representative;
    }
   
    private Set<V> getVerticesWithoutCommunities() {
        Set<V> lltNoCommunity = new HashSet<>();
        for (V v : dynaGraph.getVertices(tf)) {
            if(!running){
                break;
            }
            if (v.getSystemAttributer().getAttributeNames().contains(MeerkatSystem.COMMUNITY)) {
                continue;
            }
            lltNoCommunity.add(v);
        }
        return lltNoCommunity;
    }
    
    private double getWidth() {
        return 1000;
    }
    
    private double getHeight() {
        return 1000;
    }
}