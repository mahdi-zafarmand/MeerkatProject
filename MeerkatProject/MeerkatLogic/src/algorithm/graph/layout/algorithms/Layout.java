package algorithm.graph.layout.algorithms;

import algorithm.GraphAlgorithm;
import config.MeerkatSystem;
import datastructure.core.DynamicAttributable.SysDynamicAttributer.SystemDynamicAttributer;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.awt.geom.Point2D;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public abstract class Layout<V extends IVertex, E extends IEdge<V>>
        extends GraphAlgorithm<V,E> {
    
    enum Type {
        METRIC, COMMUNITY, STANDARD
    }
    
    Type type;
    
    String strLayoutName;
    
    boolean update = false;

    /**
     * Method Name : Layout (Constructor)
     * Created Date : From previous Meerkat versions
     * Description : The superclass for all Layouts that include shared
     *                  methods and attributes between all layouts= algorithms.
     * Version : 1.0
     *
     * @author : Afra
     *
     * @param pIGraph
     * @param tf
     * @param parameters 
     *
     * EDIT HISTORY (most recent at the top) 
     * Date         Author      Description 
     * 2015-07-17   Talat       TODO list
     *
     */
    public Layout(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String[] parameters) {
        super(pIGraph, tf);
    }
    
    /**
     *
     * @return
     */
    public Map<Integer, Double[]> getResults() {
        Map<Integer, Double[]> scores = new HashMap<>();
        
        for (V v : dynaGraph.getVertices(tf)) {
            Double[] position = new Double[2];
            position[0] = getX(v);
            position[1] = getY(v);
            scores.put(v.getId(), position);
        }
        return scores;
    }

    /*
     * Computing number of vertices while considering their weights as well.
     * Thus, vertices with more weight would be counted as several vertices.
     */

    /**
     *
     * @return
     */
    
    protected double computeNumberOfVerticesWithRespectToTheirWeight() {
        double intNumberOfFakeVertices = 0;
        for (V v : dynaGraph.getVertices(tf)) {
            if(!running){
                break;
            }
            intNumberOfFakeVertices += v.getWeight();
        }
        return intNumberOfFakeVertices;
    }

    /**
     *
     * @param positions
     */
    public void addLocationToAttributeList(HashMap<IVertex, Point2D> positions) {
        Date dtLastDate = new Date();
        for (IVertex v : positions.keySet()) {
            setLocation(v, positions.get(v).getX(), positions.get(v).getY());
        }
    }

    /**
     *
     * @return layout name
     */
    @Override
    public abstract String toString();

    /**
     *
     * @return type of the layout
     */
    public abstract Type getType();

    /**
     * Method Name : Created Date : Description : Version :
     *
     * @author : Talat
     *
     * @param
     * @param
     * @return
     *
     * EDIT HISTORY (most recent at the top) Date Author Description
     *
     *
     */
    @Override
    public synchronized boolean updateDataStructure() {
        /*
         * Update the date structure only when the location (X and Y) attribute is not out-dated.
         * Meaning when it was not added yet or the time is older than dtLastUpdate. 
         */
        if (!(SystemDynamicAttributer.hmpAttTime.containsKey(MeerkatSystem.X)
                && (SystemDynamicAttributer.hmpAttTime.get(MeerkatSystem.X)
                        .get(tf)
                        .compareTo(dtCallTime) > 0))) {

            // initially dynaGraph was fetching allVertices which resulted in an error, changing it
            // to fetch only the vertices of current timeframe fixes the error.
            for (V v : dynaGraph.getVertices(tf)) {
                v.getSystemAttributer().addAttributeValue(
                        MeerkatSystem.X, 
                        getX(v) + "", 
                        dtCallTime,
                        tf);
                v.getSystemAttributer().addAttributeValue(
                        MeerkatSystem.Y, 
                        getY(v) + "", 
                        dtCallTime,
                        tf);
            }
            return true;
        }
        return false;
    }
    
    /**
     *
     */
    @Override
    public void writeToFile() {
        
    }
    
    /**
     *
     * @param v
     * @param x
     * @param y
     */
    protected void setLocation(IVertex v , double x, double y) {
        Date dt = new Date();
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            x = 0.5;
        }
        if (Double.isNaN(y) || Double.isInfinite(y)) {
            y = 0.5;
        }
        v.getSystemAttributer().addAttributeValue(MeerkatSystem.X, x +"", dt, tf);
        v.getSystemAttributer().addAttributeValue(MeerkatSystem.Y, y + "", dt, tf);
        
    }
    
    /**
     *
     * @param v
     * @return
     */
    protected double getX(IVertex v) {
        if (v.getSystemAttributer().getAttributeNames().contains(MeerkatSystem.X)) {
            return Double.parseDouble(v.getSystemAttributer().getAttributeValue(MeerkatSystem.X, tf));
        } else {
            return 0.5;
        }
    }
    
    /**
     *
     * @param v
     * @return
     */
    protected double getY(IVertex v) {
        if (v.getSystemAttributer().getAttributeNames().contains(MeerkatSystem.X)) {
            return Double.parseDouble(v.getSystemAttributer().getAttributeValue(MeerkatSystem.Y, tf));
        } else {
            return 0.5;
        }
    }

    public void appliedUpdates(){
        this.update = false;
    }
    
    public boolean isUpdated() {
        return this.update;
    }
}
