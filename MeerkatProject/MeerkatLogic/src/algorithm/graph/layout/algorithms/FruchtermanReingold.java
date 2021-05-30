/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.layout.algorithms;

/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 *
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 */
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.awt.geom.Point2D;
import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.map.LazyMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements the Fruchterman-Reingold force-directed algorithm for node layout.
 *
 * <p>
 * Behavior is determined by the following settable parameters:
 * <ul>
 * <li/>attraction multiplier: how much edges try to keep their vertices
 * together
 * <li/>repulsion multiplier: how much vertices try to push each other apart
 * <li/>maximum iterations: how many iterations this algorithm will use before
 * stopping
 * </ul>
 * Each of the first two defaults to 0.75; the maximum number of iterations
 * defaults to 700.
 *
 * @see "Fruchterman and Reingold, 'Graph Drawing by Force-directed Placement'"
 * @see
 * "http://i11www.ilkd.uni-karlsruhe.de/teaching/SS_04/visualisierung/papers/fruchterman91graph.pdf"
 * @author Scott White, Yan-Biao Boey, Danyel Fisher
 */
public class FruchtermanReingold<V extends IVertex, E extends IEdge<V>>
        extends Layout<V, E> {

    private double forceConstant;

    private double temperature;

    private int currentIteration;

    private int mMaxIterations = 700;

    private Map<V, FRVertexData> frVertexData
            = LazyMap.decorate(new HashMap<V, FRVertexData>(), new Factory<FRVertexData>() {
                public FRVertexData create() {
                    return new FRVertexData();
                }
            });

    private double attraction_multiplier = 0.75;

    private double attraction_constant;

    private double repulsion_multiplier = 0.75;

    private double repulsion_constant;

    private double max_dimension;

    /**
     * Creates an instance for the specified graph.
     */
    public FruchtermanReingold(IDynamicGraph<V, E> g, TimeFrame tf, String[] parameters) {
        super(g, tf, parameters);
        
        max_dimension = Math.max(getHeight(), getWidth());
    }

    /**
     * Sets the attraction multiplier.
     */
    public void setAttractionMultiplier(double attraction) {
        this.attraction_multiplier = attraction;
    }

    /**
     * Sets the repulsion multiplier.
     */
    public void setRepulsionMultiplier(double repulsion) {
        this.repulsion_multiplier = repulsion;
    }

    public void reset() {
        doInit();
    }

    public void initialize() {
        doInit();
    }

    private void doInit() {
        if (dynaGraph != null) {
            currentIteration = 0;
            temperature = getWidth() / 10;

            forceConstant
                    = Math
                    .sqrt(getHeight()
                            * getWidth()
                            / dynaGraph.getVertices(tf).size());

            attraction_constant = attraction_multiplier * forceConstant;
            repulsion_constant = repulsion_multiplier * forceConstant;
        }
    }

    private double EPSILON = 0.000001D;

    public void run() {
        initialize();
        while (!done() && running) {
            step();
        }
        blnDone = true;
    }
    /**
     * Moves the iteration forward one notch, calculation attraction and
     * repulsion between vertices and edges and cooling the temperature.
     */
    public void step() {
        currentIteration++;

        /**
         * Calculate repulsion
         */
        while (true) {

            for (V v1 : dynaGraph.getVertices(tf)) {
                if(!running)
                    return;
                calcRepulsion(v1);
            }
            break;
        }

        /**
         * Calculate attraction
         */
        while (true) {
            for (E e : dynaGraph.getEdges(tf)) {
                if(!running)
                    return;
                calcAttraction(e);
            }
            break;
        }

        while (true) {
            for (V v : dynaGraph.getVertices(tf)) {
//                if (isLocked(v)) {
//                    continue;
//                }
                if(!running)
                    return;
                calcPositions(v);
            }
            break;
        }
        cool();
    }

    protected void calcPositions(V v) {
        FRVertexData fvd = getFRData(v);
        if (fvd == null) {
            return;
        }
        Point2D xyd = new Point2D.Double(getX(v) * getWidth(),getY(v) * getHeight());
        double deltaLength = Math.max(EPSILON, fvd.norm());

        double newXDisp = fvd.getX() / deltaLength
                * Math.min(deltaLength, temperature);

        if (Double.isNaN(newXDisp)) {
            throw new IllegalArgumentException(
                    "Unexpected mathematical result in FRLayout:calcPositions [xdisp]");
        }

        double newYDisp = fvd.getY() / deltaLength
                * Math.min(deltaLength, temperature);
        xyd.setLocation(xyd.getX() + newXDisp, xyd.getY() + newYDisp);

        double borderWidth = getWidth() / 50.0;
        double newXPos = xyd.getX();
        if (newXPos < borderWidth) {
            newXPos = borderWidth + Math.random() * borderWidth * 2.0;
        } else if (newXPos > (getWidth() - borderWidth)) {
            newXPos = getWidth() - borderWidth - Math.random()
                    * borderWidth * 2.0;
        }

        double newYPos = xyd.getY();
        if (newYPos < borderWidth) {
            newYPos = borderWidth + Math.random() * borderWidth * 2.0;
        } else if (newYPos > (getHeight() - borderWidth)) {
            newYPos = getHeight() - borderWidth
                    - Math.random() * borderWidth * 2.0;
        }

        xyd.setLocation(newXPos, newYPos);
        setLocation(v, newXPos / getWidth(), newYPos / getHeight());
    }

    protected void calcAttraction(E e) {
        V v1 = e.getSource();
        V v2 = e.getDestination();
//        boolean v1_locked = isLocked(v1);
//        boolean v2_locked = isLocked(v2);

//        if (v1_locked && v2_locked) {
//            // both locked, do nothing
//            return;
//        }
        Point2D p1 = new Point2D.Double(getX(v1) * getWidth(), getY(v1) * getHeight());
        Point2D p2 = new Point2D.Double(getX(v2) * getWidth(), getY(v2) * getHeight());
        
        double xDelta = p1.getX() - p2.getX();
        double yDelta = p1.getY() - p2.getY();

        double deltaLength = Math.max(EPSILON, Math.sqrt((xDelta * xDelta)
                + (yDelta * yDelta)));

        double force = (deltaLength * deltaLength) / attraction_constant;

        if (Double.isNaN(force)) {
            throw new IllegalArgumentException(
                    "Unexpected mathematical result in FRLayout:calcPositions [force]");
        }

        double dx = (xDelta / deltaLength) * force;
        double dy = (yDelta / deltaLength) * force;
//        if (v1_locked == false) {
            FRVertexData fvd1 = getFRData(v1);
            fvd1.offset(-dx, -dy);
//        }
//        if (v2_locked == false) {
            FRVertexData fvd2 = getFRData(v2);
            fvd2.offset(dx, dy);
//        }
            
//        setLocation(v1, fvd1.getX() / getWidth(), fvd1.getY() / getHeight());
//        setLocation(v2, fvd2.getX() / getWidth(), fvd2.getY() / getHeight());
        
    }

    protected void calcRepulsion(V v1) {
        FRVertexData fvd1 = getFRData(v1);
        if (fvd1 == null) {
            return;
        }
        fvd1.setLocation(0, 0);

        for (V v2 : dynaGraph.getVertices(tf)) {

//                if (isLocked(v2)) continue;
            if (v1 != v2) {
                Point2D p1 = new Point2D.Double(getX(v1) * getWidth(), getY(v1) * getHeight());
                Point2D p2 = new Point2D.Double(getX(v2) * getWidth(), getY(v2) * getHeight());
//                    if (p1 == null || p2 == null) {
//                        continue;
//                    }
                double xDelta = p1.getX() - p2.getX();
                double yDelta = p1.getY() - p2.getY();

                double deltaLength = Math.max(EPSILON, Math
                        .sqrt((xDelta * xDelta) + (yDelta * yDelta)));

                double force = (repulsion_constant * repulsion_constant) / deltaLength;

                if (Double.isNaN(force)) {
                    throw new RuntimeException(
                            "Unexpected mathematical result in FRLayout:calcPositions [repulsion]");
                }

                fvd1.offset((xDelta / deltaLength) * force,
                        (yDelta / deltaLength) * force);
//                setLocation(v1, fvd1.getX() / getWidth(), fvd1.getY() / getHeight());
                
            }
        }
    }

    private void cool() {
        temperature *= (1.0 - currentIteration / (double) mMaxIterations);
    }

    /**
     * Sets the maximum number of iterations.
     */
    public void setMaxIterations(int maxIterations) {
        mMaxIterations = maxIterations;
    }

    protected FRVertexData getFRData(V v) {
        return frVertexData.get(v);
    }

    /**
     * This one is an incremental visualization.
     */
    public boolean isIncremental() {
        return true;
    }

    /**
     * Returns true once the current iteration has passed the maximum count,
     * <tt>MAX_ITERATIONS</tt>.
     */
    public boolean done() {
        if (currentIteration > mMaxIterations || temperature < 1.0 / max_dimension) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Type getType() {
        return type;
    }

    private int getWidth() {
        return 600;
    }

    private int getHeight() {
        return 600;
    }

    protected static class FRVertexData extends Point2D.Double {

        protected void offset(double x, double y) {
            this.x += x;
            this.y += y;
        }

        protected double norm() {
            return Math.sqrt(x * x + y * y);
        }
    }
}
