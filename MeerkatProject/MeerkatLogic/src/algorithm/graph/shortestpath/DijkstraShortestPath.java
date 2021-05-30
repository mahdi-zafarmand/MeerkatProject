package algorithm.graph.shortestpath;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.ArrayList;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public class DijkstraShortestPath<V extends IVertex, E extends IEdge<V>> extends ShortestPathAbstract
        implements ShortestPath<V, E> {

    /**
     *
     */
    protected IDynamicGraph<V, E> igraph;
    TimeFrame timeFrame;

    HashMap<V, Map<V, Double>> hmpAllShortestPaths
            = new HashMap<>();
    
    double diameter = Double.MIN_VALUE;

    /**
     *
     * @param pIGraph
     * @param tf
     */
    public DijkstraShortestPath(IDynamicGraph<V, E> pIGraph, TimeFrame tf) {
        super(pIGraph, tf);
        igraph = pIGraph;
        timeFrame = tf;
    }

    /**
     *
     * @param s
     * @param d
     * @return
     */
    @Override
    public double computeUnWeightedShortestPath(V s, V d) {

        HashSet<V> visitedVertices = new HashSet<>();

        HashMap<V, Integer> currentVertices = new HashMap<>();
        currentVertices.put(s, 0);
        while (!currentVertices.isEmpty()) {
            V current = getMinimumValueInteger(currentVertices);
            for (V v : igraph.getNeighbors(current, tf)) {
                if (!currentVertices.containsKey(v) && !visitedVertices.contains(v)) {
                    currentVertices.put(v, currentVertices.get(current) + 1);
                }
            }
            visitedVertices.add(current);

            if (currentVertices.containsKey(d)) {
                break;
            }
            currentVertices.remove(current);
            visitedVertices.add(current);
        }

        if (currentVertices.containsKey(d)) {
            return currentVertices.get(d);
        } else {
            return igraph.getGraph(tf).getVertexCount() + 1.0;
        }
    }

    /**
     *
     * @param source
     * @param destination
     * @return
     */
    @Override
    public double computeWeightedShortestPath(V source, V destination) {
        diameter = Double.MIN_VALUE;
        HashSet<V> visitedVertices = new HashSet<>();

        HashMap<V, Double> currentVertices = new HashMap<>();
        currentVertices.put(source, 0.0);
        int intPrevSize = 0;
        while (currentVertices.size() > intPrevSize) {
            intPrevSize = currentVertices.size();
//            System.out.println("DijkstraShortestPath.computeWeightedShortestPath : currentVertices map: \n" + currentVertices);
            V current = getMinimumValueDouble(currentVertices, visitedVertices);
            for (E e : igraph.getOutgoingEdges(current, tf)) {
                V neighbor = e.getDestination();
                if (!currentVertices.containsKey(neighbor)
                        && !visitedVertices.contains(neighbor)) {
                    currentVertices.put(e.getDestination(),
                            currentVertices.get(current) + e.getWeight());
                }
            }
            visitedVertices.add(current);

            if (currentVertices.containsKey(destination)) {
                break;
            }
        }

        if (currentVertices.containsKey(destination)) {
            shortestPathSrcToDest = currentVertices.get(destination);
            System.out.println("DijkstraShortestPath.computeWeightedShortestPath "+ shortestPathSrcToDest);
            return shortestPathSrcToDest;
        } else {
            return Double.MAX_VALUE;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public HashMap<V,Map<V,Double>> computeAllPairsSPWeighted() {
        diameter = Double.MIN_VALUE;
        for (V source : igraph.getVertices(tf)) {
            Map<V,Double> mapAllPairsFromSource = 
                    computeAllPairsSPWeighted(source);
            
            hmpAllShortestPaths.put(source, mapAllPairsFromSource);
            
            for (double dist : mapAllPairsFromSource.values()) {
                if (dist > diameter) {
                    diameter = dist;
                }
            }
        }
        
        return hmpAllShortestPaths;
    }

    /**
     *
     */
    @Override
    public HashMap<V, Map<V, Double>> computeAllPairsSPUnweighted() {
        diameter = Double.MIN_VALUE;
        for (V source : igraph.getVertices(tf)) {
            Map<V,Double> mapAllPairsFromSource = 
                    computeAllPairsSPUnWeighted(source);
            
            hmpAllShortestPaths.put(source, mapAllPairsFromSource);
            
            for (double dist : mapAllPairsFromSource.values()) {
                if (dist > diameter) {
                    diameter = dist;
                }
            }
        }
        
        return hmpAllShortestPaths;
    }

    /**
     * BFS from source
     * @param source
     * @return
     */
    @Override
    public HashMap<V, Double> computeAllPairsSPUnWeighted(V source) {
        HashMap<V, Double> hmpDistances = new HashMap<>();
        
        List<V> lstToExpand = new LinkedList<>();
        lstToExpand.add(source);
        hmpDistances.put(source, 0.0);
        
        Set<V> setVisited = new HashSet<>();
        while(!lstToExpand.isEmpty()) {
            V currentV = lstToExpand.get(0);
            double newDistance = hmpDistances.get(currentV) + 1;
            for (V neigh : igraph.getNeighbors(currentV, tf)) {
                if (!setVisited.contains(neigh) && 
                        !hmpDistances.containsKey(neigh)) {
                    lstToExpand.add(neigh);
                    hmpDistances.put(neigh, newDistance);
                }
            }
            lstToExpand.remove(0);
            setVisited.add(currentV);
        }
        return hmpDistances;
    }

    /**
     * BFS from source
     * @param source
     * @return
     */
    @Override
    public HashMap<V, Double> computeAllPairsSPWeighted(V source) {
        HashMap<V, Double> hmpDistances = new HashMap<>();

        List<V> lstToExpand = new LinkedList<>();
        lstToExpand.add(source);
        hmpDistances.put(source, 0.0);

        Set<V> setVisited = new HashSet<>();
        
        while (!lstToExpand.isEmpty()){
            V currentV = lstToExpand.get(0);
            double currentDistance = hmpDistances.get(currentV);
            for (V neigh : igraph.getNeighbors(currentV, tf)) {
                double newDistance = currentDistance + neigh.getWeight();
                if (hmpDistances.containsKey(neigh)) {
                    if (hmpDistances.get(neigh) > newDistance ) {
                        hmpDistances.put(neigh, newDistance);
                    }
                }
                if (!setVisited.contains(neigh) && !hmpDistances.containsKey(neigh)) {
                    lstToExpand.add(neigh);
                    hmpDistances.put(neigh, newDistance);
                }
            }
            lstToExpand.remove(currentV);
            setVisited.add(currentV);
        }
        
        hmpDistances.remove(source);
        return hmpDistances;
    }

    private V getMinimumValueInteger(HashMap<V, Integer> map) {
        int minValue = Integer.MAX_VALUE;
        V minVertex = null;
        for (V v : map.keySet()) {
            if (minValue > map.get(v)) {
                minValue = map.get(v);
                minVertex = v;
            }
        }
        return minVertex;
    }

    private V getMinimumValueDouble(HashMap<V, Double> map, HashSet<V> visitedVertices) {
        double minValue = Double.MAX_VALUE;
        V minVertex = null;
        for (V v : map.keySet()) {
            if (!visitedVertices.contains(v) && minValue > map.get(v)) {
                minValue = map.get(v);
                minVertex = v;
            }
        }
        return minVertex;
    }
    
    private IVertex getMinimumValueVertex(HashMap<IVertex, Double> map, HashSet<IVertex> visitedVertices) {
        double minValue = Double.MAX_VALUE;
        IVertex minVertex = null;
        for (IVertex v : map.keySet()) {
            if (!visitedVertices.contains(v) && minValue > map.get(v)) {
                minValue = map.get(v);
                minVertex = v;
            }
        }
        return minVertex;
    }

    /**
     *
     * @param source
     * @param destination
     * @return
     */
    @Override
    public double getShortestPath(V source, V destination) {
        //System.out.println(hmpAllShortestPaths.get(source));
        if (!hmpAllShortestPaths.containsKey(source) ||
                !hmpAllShortestPaths.get(source).containsKey(destination)) {
            return Double.MAX_VALUE;
        }
        return hmpAllShortestPaths.get(source).get(destination);
    }

    @Override
    public double getDiameter() {
        return diameter;
    }

    @Override
    public void writeToFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void computeWeightedShortestPathEdges(IVertex source, IVertex destination) {
        

        HashMap<IVertex, IVertex> edgeMap = new HashMap<>();
        HashSet<IVertex> visitedVertices = new HashSet<>();
        
        HashMap<IVertex, Double> currentVertices = new HashMap<>();
        currentVertices.put(source, 0.0);
        int intPrevSize = 0;
        int currentSizeCount = 1;
        
        while (currentSizeCount > intPrevSize) {
            intPrevSize = currentSizeCount;
            
            IVertex current = getMinimumValueVertex(currentVertices, visitedVertices);
            for (E e : igraph.getOutgoingEdges((V) current, tf)) {
                V neighbor = e.getOtherEndPoint((V)current);
                currentSizeCount+=1;
                if (!currentVertices.containsKey(neighbor)
                        && !visitedVertices.contains(neighbor)) {
                    currentVertices.put(e.getOtherEndPoint((V)current),
                            currentVertices.get(current) + e.getWeight());
                    edgeMap.put(neighbor, current);
                }
            }
            visitedVertices.add(current);

            if (currentVertices.containsKey(destination)) {
                break;
            }
        }

        if (currentVertices.containsKey(destination)) {           
            shortestPathSrcToDest = currentVertices.get(destination);
            List<Integer> edgeIDs = new ArrayList<>();
            
            while(source!=destination){
                V src = (V) edgeMap.get(destination);
                edgeIDs.add(igraph.findEdge(src, (V) destination, timeFrame).getId());
                destination = src;
            }
            
            listEdgesPredicted = edgeIDs;
            System.out.println("DijkstraShortestPath.computeWeightedShortestPathEdges "+ shortestPathSrcToDest);
        }
    }
}
