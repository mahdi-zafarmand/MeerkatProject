/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.layout.algorithms;

import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.map.LazyMap;

/**
 * Width and Height as well as boundingRadius size are all important 
 * for the algorithm to create good results. And they all should depend on the 
 * number of vertices in the graph.
 * 
 * @author aabnar
 * @param <V>
 * @param <E> 
 */
public class ModifiedFRLayout<V extends IVertex, E extends IEdge<V>> extends Layout<V, E> {

    private Point2D center = null;
    private double boundingRadius = 0;
    private Variant variant = null;

    public enum Variant {

        COMA, COMB, COMC;
    }

    private double forceConstant;
    private double temperature;
    private double maxTemperature;
    private int currentIteration = 0;
    private final int mMaxIterations = 1000;
    private final Map<V, FRVertexData> frVertexData = LazyMap.decorate(
            new HashMap<V, FRVertexData>(), new Factory<FRVertexData>() {
                @Override
                public FRVertexData create() {
                    return new FRVertexData();
                }
            });
    private final double attraction_multiplier = 0.75;
    private double attraction_constant;
    private final double repulsion_multiplier = 0.75;
    private double repulsion_constant;
    private final double EPSILON = 0.000001D;
    
    Map<V,double[]> xydata;
    
    int width = 1;
    int height = 1;
    
    
    Collection<V> colEffectedVertices;
    Collection<E> colEffectedEdges;
    /**
     * 
     * @param pdynagraph
     * @param tf
     * @param parameters 
     *      Variant : COMA COMB COMC
     *      BoudingRadius;
     */
    public ModifiedFRLayout(IDynamicGraph<V, E> pdynagraph, TimeFrame tf, String[] parameters) {
        super(pdynagraph, tf, parameters);
        type = Type.STANDARD;
        variant = Variant.COMB;
        
        
        
        System.out.println("MFR: width and height = " + width + " , "  + height);
        
        colEffectedVertices = dynaGraph.getVertices(tf);
        colEffectedEdges = dynaGraph.getEdges(tf);
        
        boundingRadius = colEffectedVertices.size() * 2.0;
                //Math.pow(10, Math.ceil(Math.log10(dynaGraph.getVertices(tf).size())-1));
        width = height = (int) boundingRadius;
    }
    
    public ModifiedFRLayout(IDynamicGraph<V, E> pdynagraph, 
            Collection<V> psetVertices, 
            TimeFrame tf, 
            String[] parameters) {
        
        super(pdynagraph, tf, parameters);
        
        colEffectedVertices = psetVertices;
        colEffectedEdges = new HashSet<>();
        for (E e : pdynagraph.getEdges(tf)) {
            if (colEffectedVertices.contains(e.getSource())&&
                    colEffectedVertices.contains(e.getDestination())) {
                colEffectedEdges.add(e);
            }
        }
        
        boundingRadius =  colEffectedVertices.size() * 2.0;
        width = height = (int) boundingRadius;
    }

//    public ModifiedFRLayout(Point2D center, double boundingRadius) {
//        
//        this.center = center;
//        this.boundingRadius = boundingRadius;
//    }
//
//    public ModifiedFRLayout(Variant variant) {
//        this.variant = variant;
//    }
//
//    public ModifiedFRLayout(IGraph graph, Variant variant) {
//        this.graph = graph;
//        this.variant = variant;
//    }
    public void run() {
//        if (variant != Variant.COMB) {
//            double allWeights = 1.0;
//            for (V v : dynaGraph.getVertices(tf)) {
//                allWeights += v.getWeight() * getRadius();
//            }
////            layout.setInitializer(new RandomLocationTransformer<IVertex>(
////                    new Dimension((int) allWeights / 2, (int) allWeights / 2)));
//        } else {
////            layout.setInitializer(new Transformer<IVertex, Point2D>() {
////                @Override
////                public Point2D transform(IVertex vertex) {
////                    return vertex.getLocation();
////                }
////            });
//        }
        // layout.setInitializer(new RandomLocationTransformer<IVertex>(
        // new Dimension(600, 600)));

        initialize();
        while (!done() && running) {
            step();
        }
    }

    public void initialize() {
        forceConstant = Math.sqrt(colEffectedVertices.size()
                + colEffectedEdges.size());
        temperature = colEffectedVertices.size() + colEffectedEdges.size();
        maxTemperature = temperature;
        attraction_constant = forceConstant * attraction_multiplier;
        repulsion_constant = forceConstant * repulsion_multiplier;
        
        center = new Point2D.Double(0.5 * getWidth(), 0.5 * getHeight());
        
        int n = colEffectedVertices.size();
        xydata = new HashMap<>(n);
        
        for (V v : colEffectedVertices) {
            double[] xy = {getX(v) * getWidth(), getY(v) * getHeight()};
            xydata.put(v, xy);
        }
        
    }

    public void reset() {
        initialize();
    }

    public void step() {
        currentIteration++;

        /**
         * Calculate repulsion
         */

        for (V v1 : colEffectedVertices) {
            calcRepulsion(v1);
        }
        if(!running)
            return;

        /**
         * Calculate attraction
         */
        for (E e : colEffectedEdges) {

            calcAttraction(e);
        }
        if(!running)
            return;

        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        
        for (V v : colEffectedVertices) {
//                    if (isLocked(v)) {
//                        continue;
//                    }
            calcPositions(v);
            
            double[] xy = xydata.get(v);
            if (xy[0] < minX ) {
                minX = xy[0];
            } else if (xy[0] > maxX) {
                maxX = xy[0];
            }
            
            if (xy[1] < minY) {
                minY = xy[1];
            } else if (xy[1] > maxY) {
                maxY = xy[1];
            }
        }
        if(!running)
            return;
        
        // Normalizing between 0 and 1
        double diffX = maxX - minX;
        double diffY = maxY - minY;
        
        for (V v : xydata.keySet()) {
            double[] xy = xydata.get(v);
            double newX = xy[0];
            double newY = xy[1];
            if (diffX < diffY && diffY > 0) {
                double alpha = (minX + maxX - diffY) / 2 ;
                newX = (xy[0] - alpha) / diffY ; 
                newY = (xy[1] - minY) / diffY ;
            } else if (diffX > 0) {
                double alpha = (minY + maxY - diffX) / 2 ;
                newY = (xy[1] - alpha) / diffX ; 
                newX = (xy[0] - minX) / diffX ;
            }
            
            if (Double.isNaN(newX) || Double.isInfinite(newX)) {
                System.err.println("FOUND INFINITY");
                newX = Math.random() * getWidth();
            }
            if (Double.isNaN(newY) || Double.isInfinite(newY)) {
                System.err.println("FOUND INFINITY");                
                newY = Math.random() * getHeight();
            }
            xy[0] = newX * getWidth();
            xy[1] = newY * getHeight();
            xydata.put(v, xy);
            setLocation(v, newX, newY);
        }
        
        cool();
    }

    public boolean done() {
        return currentIteration > mMaxIterations;
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
        Point2D p1 = new Point2D.Double(xydata.get(v1)[0] , xydata.get(v1)[1]);
        Point2D p2 = new Point2D.Double(xydata.get(v2)[0] , xydata.get(v2)[1]);
//        if (p1 == null || p2 == null) {
//            return;
//        }
        double xDelta = p1.getX() - p2.getX();
        double yDelta = p1.getY() - p2.getY();

        // double deltaLength = Math.max(EPSILON,
        // Math.sqrt((xDelta * xDelta) + (yDelta * yDelta))
        // - (v1.getWeigher().getWeight() + v2.getWeigher()
        // .getWeight())
        // * VertexShapeTransformer.DEFAULT_VERTEX_RADIUS);
        double deltaLength = Math.max(EPSILON,
                Math.sqrt((xDelta * xDelta) + (yDelta * yDelta))
                - (v1.getWeight() + v2.getWeight()));

        double noOverlapDistance = v1.getWeight() + v2.getWeight();
        double force = 0;
        if (deltaLength > noOverlapDistance) {
            force = (deltaLength * deltaLength) / attraction_constant;   
        }
        

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
    }

    protected void calcRepulsion(V v1) {
        FRVertexData fvd1 = getFRData(v1);
        if (fvd1 == null) {
            return;
        }
        fvd1.setLocation(0, 0);

        for (V v2 : colEffectedVertices) {

            // if (isLocked(v2)) continue;
            if (v1 != v2) {
                Point2D p1 = new Point2D.Double(xydata.get(v1)[0] , xydata.get(v1)[1]);
                Point2D p2 = new Point2D.Double(xydata.get(v2)[0] , xydata.get(v2)[1]);
//                    if (p1 == null || p2 == null) {
//                        continue;
//                    }
                double xDelta = p1.getX() - p2.getX();
                double yDelta = p1.getY() - p2.getY();
						//
                // double deltaLength = Math
                // .max(EPSILON,
                // Math.sqrt((xDelta * xDelta)
                // + (yDelta * yDelta))
                // - (v1.getWeigher().getWeight() + v2
                // .getWeigher()
                // .getWeight())
                // * VertexShapeTransformer.DEFAULT_VERTEX_RADIUS);
                double deltaLength = Math
                        .max(EPSILON,
                                Math.sqrt((xDelta * xDelta) + (yDelta * yDelta))
                                - (v1.getWeight())
                                + v2.getWeight());

                
                double force = (repulsion_constant * repulsion_constant)
                        / deltaLength;

//                if (variant == Variant.COMA) {
//                    ca.aicml.meerkat.model.Graph c = (ca.aicml.meerkat.model.Graph) g
//                            .getCommunityContainingVertex((IVertex) v1);
//                    if (c != null) {
//                        double e = c.getNumEdges();
//                        double v = c.getNumVertices();
//                        double density = e / (v * (v - 1));
//                        force = Math.pow(force, 1 + density * density);
//                    }
//                }

                if (Double.isNaN(force)) {
                    System.out.println("ModifiedFR.calcRepulsion() :  xDelta , yDelta");
                    System.out.println("                             " + xDelta + " , " + yDelta );
                    System.out.println("v1 : " + p1.getX() + " , " + p1.getY());
                    System.out.println("v2 : " + p2.getX() + " , " + p2.getY());
                    System.out.println("v1 is infinity : " + Double.isInfinite(p1.getX()) + " , " + Double.isInfinite(p1.getY()));
                    
                    
                    throw new RuntimeException(
                            "Unexpected mathematical result in FRLayout:calcPositions [repulsion]");
                }

                double xOffset = (xDelta / deltaLength) * force;
                double yOffset = (yDelta / deltaLength) * force;
                
                fvd1.offset(xOffset, yOffset);
            }
        }
    }

    protected synchronized void calcPositions(V v) {
        FRVertexData fvd = getFRData(v);
        if (fvd == null) {
            return;
        }
        Point2D xyd = new Point2D.Double(xydata.get(v)[0] , xydata.get(v)[1]);

        double deltaLength = Math.max(EPSILON, fvd.norm());

        double newXDisp = fvd.getX() / deltaLength
                * Math.min(deltaLength, temperature);

        if (Double.isNaN(newXDisp)) {
            System.out.println("ModifiedFR.calcPositions() : fvd.getX() , deltaLength , min(deltaLenghth, temperature)");
            System.out.println("                             " + fvd.getX() + " , " + deltaLength + " , " + Math.min(deltaLength, temperature));
            throw new IllegalArgumentException(
                    "Unexpected mathematical result in FRLayout:calcPositions [xdisp]");
        }

        double newYDisp = fvd.getY() / deltaLength
                * Math.min(deltaLength, temperature);
        
        
        if (Double.isNaN(newXDisp) || Double.isInfinite(newXDisp)) {
            System.err.println("FOUND INFINITY");
            newXDisp = 0;
        }
        if (Double.isNaN(newYDisp) || Double.isInfinite(newYDisp)) {
            System.err.println("FOUND INFINITY");                
            newYDisp = 0;
        }
        xyd.setLocation(xyd.getX() + newXDisp, xyd.getY() + newYDisp);
        double[] xy = {xyd.getX(),xyd.getY()};
        
        xydata.put(v, xy);
        setLocation(v, xyd.getX() / getWidth(), xyd.getY() / getHeight());
        if (variant == Variant.COMB
                && xyd.distance(center) > boundingRadius) {

            double angle = Math.atan2(center.getY() - xyd.getY(),
                    center.getX() - xyd.getX());
            Random random = new Random();
            double length = boundingRadius
                    * (1 - (random.nextDouble() * temperature / maxTemperature));
            double newX = Math.cos(angle) * length + center.getX();
            double newY = Math.sin(angle) * length + center.getY();
            
            if (Double.isNaN(newX) || Double.isInfinite(newX)) {
                System.err.println("FOUND INFINITY");
                newX = Math.random() * getWidth();
            }
            if (Double.isNaN(newY) || Double.isInfinite(newY)) {
                System.err.println("FOUND INFINITY");                
                newY = Math.random() * getHeight();
            }
            xyd.setLocation(newX, newY);
            double[] xy2 = {xyd.getX(),xyd.getY()};
            xydata.put(v, xy2);
            
            newX /= getWidth();
            newY /= getHeight();
            
            setLocation(v, newX, newY);
        }
        
        
    }

    private void cool() {
        temperature *= (1.0 - currentIteration / (double) mMaxIterations);
    }

    protected FRVertexData getFRData(V v) {
        return frVertexData.get(v);
    }
    
    private double getRadius() {
        return 10;
    }
    
    private double getWidth() {
        return width;
    }
    
    private double getHeight() {
        return height;
    }
    
    @Override
    public String toString() {
        return "Modified Fruchterman-Reingold";
    }

    @Override
    public Type getType() {
        return type;
    }
    
    @SuppressWarnings("serial")
    protected class FRVertexData extends Point2D.Double {

        protected void offset(double x, double y) {
            this.x += x;
            this.y += y;
        }

        protected double norm() {
            return Math.sqrt(x * x + y * y);
        }
    }
}