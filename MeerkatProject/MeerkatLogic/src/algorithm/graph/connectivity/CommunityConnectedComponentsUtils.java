/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm.graph.connectivity;

import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CommunityConnectedComponentsUtils<V extends IVertex, E extends IEdge<V>> {

    
    @SuppressWarnings("empty-statement")
    public List<Set<V>> extractCommunityWeakConnectedComponents
                        (Set<V> previousCommunity, IDynamicGraph<V, E> currentGraph, TimeFrame tf) {
//        Vector<Set<V>> connectedComponents = null;
//
//        VertexPredicate<V> vertexPrecicate =
//                new VertexPredicate<>(previousCommunity);
//
//        VertexPredicateFilter<V, E> graphfilter = 
//                new VertexPredicateFilter<>(vertexPrecicate);
//
//        IStaticGraph<V, E> currentComponent = graphfilter.transform(currentGraph);
//        if (currentComponent.getVertexCount() > 1) {
            WeaklyConnectedComponent<V, E> clusterer = 
                    new WeaklyConnectedComponent<>(currentGraph, tf, previousCommunity);
            clusterer.start();
            
            while (!clusterer.isDone());
            
            List<Set<V>> components = clusterer.getConnectedComponents();

            Iterator<Set<V>> iter = components.iterator();
            while (iter.hasNext()) {
                if (iter.next().size() == 1) {
                    iter.remove();
                }
            }
//        }

        return components;
    }

    @SuppressWarnings("empty-statement")
    public List<Set<V>> extractAllCommunityWeakConnectedComponents(
            List<Set<V>> previousCommunities, 
            IDynamicGraph<V, E> currentGraph, 
            TimeFrame tf) {
        List<Set<V>> connectedComponents = new LinkedList<>();

        HashMap<Set<V>, Integer> levels = new HashMap<>();
        for (Set<V> preCommunity : previousCommunities) {
//            VertexPredicate<V> vertexPrecicate = 
//                    new VertexPredicate<>(preCommunity);
//
//            VertexPredicateFilter<V, E> graphfilter = 
//                    new VertexPredicateFilter<>(vertexPrecicate);
//            
//            IStaticGraph<V, E> currentComponent = graphfilter.transform(currentGraph);
//            if (currentComponent.getVertexCount() > 1) {
                WeaklyConnectedComponent<V, E> clusterer = 
                        new WeaklyConnectedComponent<>(currentGraph, tf, preCommunity);
                
                Thread th = new Thread(clusterer);
                th.start();
                
                while (th.isAlive());
                
                List<Set<V>> components = clusterer.getConnectedComponents();

                Iterator<Set<V>> iter = components.iterator();
                while (iter.hasNext()) {
                    if (iter.next().size() == 1) {
                        iter.remove();
                    }
                }

                for (Set<V> component : components) {
                    levels.put(component, components.size());
                    insertCommunitySortedBySize(connectedComponents, levels, component);
                }

//            }
        }
        return connectedComponents;
    }

    private void insertCommunitySortedBySize(List<Set<V>> connectedComponents,
                    HashMap<Set<V>, Integer> levels, Set<V> component) {

        int index = 0;
        int level = levels.get(component);

        for (; index < connectedComponents.size(); index++) {
            if (levels.get(connectedComponents.get(index)) > level) {
                break;
            } else if (levels.get(connectedComponents.get(index)) == level) {
                if (connectedComponents.get(index).size() < component.size()) {
                    break;
                }
            }
        }

        connectedComponents.add(index, component);

    }

//    public boolean isConnected(Collection<V> component, IStaticGraph<V, E> graph) {
//
//        VertexPredicate<V> vertexPrecicate = new VertexPredicate<>(component);
//
//        VertexPredicateFilter<V, E> graphfilter = 
//                new VertexPredicateFilter<>(vertexPrecicate);
//        IStaticGraph<V, E> currentComponent = graphfilter.transform(graph);
//
//        return isConnected(currentComponent);
//    }

    public boolean isConnected(Set<V> cluster, IDynamicGraph<V, E> graph, TimeFrame tf) {
        WeaklyConnectedComponent<V, E> clusterer = 
                new WeaklyConnectedComponent<>(graph, tf, cluster);
        
        Thread th = new Thread(clusterer);
        th.start();
        
        while (th.isAlive());
        
        List<Set<V>> components = clusterer.getConnectedComponents();
//        System.out.println("CommunityConnectedComponentsUtils.isConnected() : lst of components size --> " + components.size());
        
        return components.size() == 1;
    }
}
