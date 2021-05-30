package algorithm.graph.shortestpath;

import java.util.HashMap;

import datastructure.core.graph.classinterface.*;
import java.util.Map;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public interface ShortestPath<V extends IVertex, E extends IEdge<V>> {

    /**
     *
     * @param source
     * @param destination
     * @return
     */
    public double computeUnWeightedShortestPath(V source, V destination);
	
    /**
     *
     * @param source
     * @param destination
     * @return
     */
    public double computeWeightedShortestPath(V source, V destination);
	
    /**
     *
     * @return
     */
    public HashMap<V,Map<V,Double>> computeAllPairsSPWeighted();
	
    /**
     *
     */
    public HashMap<V, Map<V, Double>> computeAllPairsSPUnweighted();
	
    /**
     *
     * @param source
     * @return
     */
    public HashMap<V,Double> computeAllPairsSPUnWeighted(V source);
	
    /**
     *
     * @param source
     * @return
     */
    public HashMap<V,Double> computeAllPairsSPWeighted(V source);
	
    /**
     *
     * @param source
     * @param destination
     * @return
     */
    public double getShortestPath(V source, V destination);
    
    public double getDiameter();
}
