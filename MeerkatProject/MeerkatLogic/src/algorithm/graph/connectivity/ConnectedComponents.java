package algorithm.graph.connectivity;

import datastructure.core.TimeFrame;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import datastructure.core.graph.classinterface.*;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public class ConnectedComponents<V extends IVertex, E extends IEdge<V>> {

    IDynamicGraph<V,E> dynagraph;
    TimeFrame tf; 
    
    Set<V> setVertices;

    public ConnectedComponents(IDynamicGraph<V,E> pIGraph, 
            TimeFrame tf, 
            Set<V> psetVertices) {
        dynagraph = pIGraph;
        this.tf = tf;
        this.setVertices = psetVertices;
    }

    /**
     *
     * @return
     */
    public List<Set<V>> computeWeakComponents(BooleanProperty isThreadRunningProperty) {
        return computeComponents(false,isThreadRunningProperty);
    }
    
    /**
     *
     * @return
     */
    public List<Set<V>> computeStrongComponents(BooleanProperty isThreadRunningProperty) {
        return computeComponents(true, isThreadRunningProperty);
    }
    
    /**
     *
     * @return
     */
    public List<Set<V>> getWeakComponentAsSet(BooleanProperty isThreadRunningProperty) {
        List<Set<V>> lstComponents = computeWeakComponents(isThreadRunningProperty);
        return lstComponents;
        //return getVertexSet(lstComponents);
    }
    public List<Set<V>> getWeakComponentAsSet() {
        BooleanProperty isThreadRunningProperty = new SimpleBooleanProperty(true);
        List<Set<V>> lstComponents = computeWeakComponents(isThreadRunningProperty);
        return lstComponents;
        //return getVertexSet(lstComponents);
    }
    
    /**
     *
     * @return
     */
    public List<Set<V>> getStrongComponentAsSet(BooleanProperty isThreadRunningProperty) {
        List<Set<V>> lstComponents = computeStrongComponents(isThreadRunningProperty);
        return lstComponents;
//        return getVertexSet(lstComponents);
    }
    public List<Set<V>> getStrongComponentAsSet() {
        BooleanProperty isThreadRunningProperty = new SimpleBooleanProperty(true);
        List<Set<V>> lstComponents = computeStrongComponents(isThreadRunningProperty);
        return lstComponents;
//        return getVertexSet(lstComponents);
    }
    
    private Set<Set<V>> getVertexSet(LinkedList<IStaticGraph<V,E>> plstComponents) {
        Set<Set<V>> setCompSet = new HashSet<>();
        for (IGraph<V,E> g : plstComponents) {
            Set<V> set = new HashSet<>();
            for (int vid : g.getAllVertexIds()) {
                set.add(dynagraph.getVertex(vid));
            }
            setCompSet.add(set);
        }
        
        return setCompSet;
    }
    
    /**
     * Computes connected components (Weak or Strong connectivity is
     * determined by the boolean parameter). and returns each component 
     * as an IGraph.
     * @param blnStrongConnectivity
     * @return 
     */
    public List<Set<V>> computeComponents(
                                        boolean blnStrongConnectivity, BooleanProperty isThreadRunningProperty) {
        HashSet<V> hstUnvisitedVertex = new HashSet<>();
        List<Set<V>> lltAllConnectecComponents = new LinkedList<>();

        if (setVertices != null) {
            hstUnvisitedVertex.addAll(setVertices);
        } else {
            hstUnvisitedVertex.addAll(dynagraph.getVertices(tf));
        }

        while (!hstUnvisitedVertex.isEmpty()) {
            if(isThreadRunningProperty.getValue()==false){
                return lltAllConnectecComponents;
            }
            V currentVertex = hstUnvisitedVertex.iterator().next();
            
//            System.out.println("ConnectedComponents.computeComponenets() : currentVertex --> " + currentVertex.getId());
            if (setVertices!= null && !setVertices.contains(currentVertex)) {
                
                continue;
            }
            if(hstUnvisitedVertex.contains(currentVertex)) {
                
                Set<V> connectedVertices = null;
                if (blnStrongConnectivity) {
                    connectedVertices = strongDFS(currentVertex, new HashSet<>(), isThreadRunningProperty);
//                    System.out.println(connectedVertices);
                } else {
                    connectedVertices = weakDFS(currentVertex, isThreadRunningProperty/*, new HashSet<>()*/);
//                    System.out.println(connectedVertices);
                }

                for (V v : connectedVertices) {
                    hstUnvisitedVertex.remove(v);
//                    System.out.println("ConnectedComponents.computeComponenets() : removed V --> " + v.getId());

                }
                
                lltAllConnectecComponents.add(connectedVertices);
            }
        }
//        System.out.println("ConnectedComponents.computeComponenets() : componenets size  --> " + lltAllConnectecComponents.size());

        return lltAllConnectecComponents;
    }

    /**
     * considers one way path is the some edges (or whole graph)
     * are (is) directed.
     * @param pVertex
     * @return 
     */
    private Set<V> weakDFS(V pVertex, BooleanProperty isThreadRunningProperty) {
        HashSet<V> lstConnectedVs = new HashSet<>();
        List<V> hstExplore = new LinkedList<>();
        
        lstConnectedVs.add(pVertex);

        hstExplore.add(pVertex);
        
        while(hstExplore.size() > 0) {
            if(isThreadRunningProperty.getValue()==false){
                return lstConnectedVs;
            }
            V vtxCurrent = hstExplore.remove(0);
//            System.out.println("ConnectedComponents.weakDFS() : current V --> " + vtxCurrent.getId());

//            System.out.println("ConnectedComponents.weakDFS() : currentVId --> " + vtxCurrent.getId());
            for (V v : dynagraph.getNeighbors(vtxCurrent, tf)) {
//                System.out.println("ConnectedComponents.weakDFS() : Neighbor(v) --> " + v.getId());

                if (setVertices != null && !setVertices.contains(v)) {
                    continue;
                }
                if (!lstConnectedVs.contains(v)) {
                    lstConnectedVs.add(v);
                    hstExplore.add(v);
                }
            }
        }
//        for (E e : dynagraph.getOutgoingEdges(pVertex,tf)) {
//            if (pstVisistedVertices.contains(e.getDestination())) {
//                continue;
//            }
//            if (e.isDirected()) {
//                if(!lstConnectedVs.contains(e.getDestination())) {
//                    lstConnectedVs.add(e.getDestination());
//                    pstVisistedVertices.add(e.getDestination());
//                    lstConnectedVs.addAll(weakDFS(e.getDestination(), pstVisistedVertices));
//                }
//            } else {
//                if (!lstConnectedVs.contains(e.getSource())) {
//                    lstConnectedVs.add(e.getSource());
//                    pstVisistedVertices.add(e.getSource());
//                    lstConnectedVs.addAll(weakDFS(e.getSource(),pstVisistedVertices));
//                }
//            }
//        }

        return lstConnectedVs;
    }
    
    /**
     * considers two way path in case of directed edges.
     * @param pVertex
     * @return 
     */
    private Set<V> strongDFS(V pVertex, Set<V> pstVisitedVertices, BooleanProperty isThreadRunningProperty) {
        HashSet<V> lstConnectedVs = new HashSet<>();

        lstConnectedVs.add(pVertex);

        lstConnectedVs.add(pVertex);
        pstVisitedVertices.add(pVertex);
        
        if(isThreadRunningProperty.getValue()==false){
                return lstConnectedVs;
        }
        for (E e : dynagraph.getOutgoingEdges(pVertex, tf)) {
            if (pstVisitedVertices.contains(e.getDestination())) {
                continue;
            }
            if (e.isDirected()) {
                if (dynagraph.getIncomingNeighbors(pVertex, tf)
                        .contains(e.getDestination())) {
                    lstConnectedVs.add(e.getDestination());
                    lstConnectedVs
                        .addAll(strongDFS(e.getDestination(),pstVisitedVertices, isThreadRunningProperty));
                }
            } else {
                if (!lstConnectedVs
                        .contains(e.getDestination())) {
                    lstConnectedVs
                        .add(e.getDestination());
                    pstVisitedVertices.add(e.getDestination());
                    lstConnectedVs
                        .addAll(strongDFS(e.getDestination(),pstVisitedVertices, isThreadRunningProperty));
                }
            }
        }
        return lstConnectedVs;
    }
}
