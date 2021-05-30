package algorithm.graph.metric;

import algorithm.GraphAlgorithm;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import java.util.HashMap;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public abstract class IMetric<V extends IVertex, E extends IEdge<V>>
        extends GraphAlgorithm<V, E> {
    public String metricAttribute;

    /**
     *
     * @param pIGraph
     * @param tf
     * @param parameters
     */
    protected IMetric(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String[] parameters) {
        super(pIGraph, tf);
    }

    /**
     *
     * @return
     */
    public abstract HashMap<V, Double> getScores();

//    public static IMetric getAlgorithm(String pstrClassName, IDynamicGraph pIGraph, TimeFrame tf, String[] parameters) {
//        
//        try {
//            Class clsMetric;
//            clsMetric = Class.forName(pstrClassName);
//
//            if (containsAlglortihm(clsMetric, pIGraph, tf)) {
//                IMetric btwAlg = (IMetric) GraphAlgorithm.getAlgorithm(clsMetric, pIGraph, tf);
//                btwAlg.parseParameters(parameters);
//                btwAlg.updateCallTime();
//                return (IMetric) GraphAlgorithm.getAlgorithm(clsMetric, pIGraph, tf);
//            } else {
//                Constructor constMetric = clsMetric.getConstructor(IDynamicGraph.class, TimeFrame.class, String[].class);
//                IMetric btwAlg = (IMetric) constMetric.newInstance(pIGraph, tf, parameters);
//                addAlgorithm(clsMetric, pIGraph, tf, btwAlg);
//                return btwAlg;
//            }
//        } catch (ClassNotFoundException | NoSuchMethodException 
//                | SecurityException | InstantiationException 
//                | IllegalAccessException | IllegalArgumentException 
//                | InvocationTargetException ex) {
//            Logger.getLogger(IMetric.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }

    /**
     *
     * @param parameters
     */
    
    protected abstract void parseParameters(String[] parameters);
    
    public abstract boolean isResultValid();
}
