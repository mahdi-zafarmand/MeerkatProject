package algorithm.graph.layout.algorithms;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import algorithm.graph.metric.IMetric;
import algorithm.graph.metric.InDegree;
import algorithm.graph.metric.MetricHandler;
import algorithm.util.Sort;
import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Class Name : Bullseye <P>
 * Created Date : From previous version of Meerkat <P>
 * Description : Lays out vertices (in the 1 by 1 square) on a concentric circles based on their score 
 * (with is computed by the metric algorithm) The higher the score, the more 
 * the vertex is closed to the center <P>
 * Version : 2.0 <P>
 *
 * @author : Afra <P>
 *
 * EDIT HISTORY (most recent at the top)  <P>
 * Date         Author      Description  <P>
 * 2016-03-16   Afra        Added String[] parameters to the set of parameters of the constructor. <P>
 * 
 * @param <V> type of vertices
 * @param <E> type of edges
 */
public class Bullseye<V extends IVertex, E extends IEdge<V>>
        extends Layout<V, E> {

    /**
     *
     */
    public static final String STR_NAME = "Byllseye Layout";
//    public static final Class DEFAILT_METRIC_ALGORITHM =
//            InDegree.class;

    /**
     *
     */
    public static double RADIUS = 0.5;

    /**
     *
     */
    public static double DIAMETER = 2 * RADIUS;

    /**
     *
     */
    public static int RING_SIZE = 50;

    private IMetric<V, E> imetric = null;

    HashMap<V, Point2D> positions;
    HashMap<V, Double> vertexMetricValues;
    
    private Thread thMetric;
    /**
     * Method Name : Bullseye (Constructor) <P>
     * Created Date : From previous version of Meerkat  <P>
     * Description : <P>
     * Version : 3.0 <P>
     *
     * @author : Afra 
     *
     * @param pIGraph
     * @param tf
     * @param parameters
     *          array of parameters for Bullseye layout, where parameter[0] = metric algorithm Id and the rest of the array include the parameters for that metric algorithm.
     *
     * EDIT HISTORY (most recent at the top) <P>
     * Date         Author  Description  <P>
     * 2016-03-23   Afra    using parameters to create the metricparameters for the metric algorithm to be called.
     *                      the first item in parameters is the parameter for bullseye which is Id of the metric algorithm,
     *                      while the rest of the items in the array are parameters for the metric algorithm to be called. <P>
     * 2016-03-16   Afra    Added String[] parameters to the set of parameters. The first
     *                      element in the array should contain the id of the Metric algorithm. if it
     *                      is null then, the default metric (InDegree) is computed. <P>
     *
     */
    public Bullseye(IDynamicGraph<V, E> pIGraph,
            TimeFrame tf,
            String[] parameters) {
        super(pIGraph, tf, parameters);
        
        setThreadRunningPropertyListener();
        
        String strMetricClassId = null;
        if (parameters != null && parameters[0] != null) {
            strMetricClassId = parameters[0];
        }
        
        String[] metricParameters = null;
        if (parameters.length > 1) {
            metricParameters = new String[parameters.length - 1];
            for (int i = 1 ; i < parameters.length ; i++) {
                metricParameters[i-1] = parameters[i];
            }
        }
        if (strMetricClassId != null) {
            this.imetric = MetricHandler
                    .getMetricAlgorithm(dynaGraph, tf, strMetricClassId, metricParameters);
        }
        
        //setting the metricAttribute of authority and hub here
        // since they're run via HITS.
        if(strMetricClassId.equals("Authority"))
            imetric.metricAttribute=MeerkatSystem.AUTHORITY;
        else if(strMetricClassId.equals("Hub"))
            imetric.metricAttribute=MeerkatSystem.HUB;
                    

        /*Default imetric is InDegree Centrality measure*/
        if (imetric == null) {
            imetric = InDegree.getAlgorithm(pIGraph, tf, null);
        }
    }
    private void setThreadRunningPropertyListener(){
        
        isThreadRunningProperty.addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,Boolean newValue){
                if(newValue == false){
                    // System.out.println("-------=====---- \t\t\t\t MeerkatLogic BullsEye() : setThreadRunningPropertyListener() StopThread called");
                    imetric.stopThread();
                    thMetric.interrupt();
                    //imetric.stopThread();

                }
            }
        });
        
        
    }
    /**
     *
     */
    @Override
    @SuppressWarnings("empty-statement")
    public void run() {
        
        HashMap<V, Double> scores;
        HashMap<V, Double> normalizedSortedScores;
        LinkedList<V> sortedVertices;

        V highScoreV;
        if(!running){
            return;
        }
        if (dynaGraph.getGraph(tf).getVertexCount() == 0) {
            return;
        }

        // The scores have to be calculated and normalized so that they will fit
        // on the current layout size, whatever that may be.
        thMetric = new Thread(imetric);
        thMetric.setDaemon(true);
        thMetric.start();

        //while (thMetric.isAlive());// || !imetric.isInterrupted());
        while (thMetric.isAlive() && !imetric.isDone()){
            // System.out.println("------- \t\t ---- \t\t\t\t MeerkatLogic BullsEye() --- thMetric thread is Alive : imetric.isDone = " + imetric.isDone());
        }
        // System.out.println("------- \t\t ---- \t\t\t\t MeerkatLogic BullsEye() --- thMetric thread isDone ===== TRUE");
        
        // System.out.println("Bullseye.run() - updating vertex 2D positions after metric algorithm is run...");
        scores = getMetricScores(imetric.metricAttribute);
        //scores = imetric.getScores();
        if(!running){
            return;
        }
        Sort<V> srtOperator = new Sort<>(scores);
        sortedVertices = srtOperator.callQuickSort();

        highScoreV = (V) sortedVertices.toArray()[sortedVertices.size() - 1];

        double maxScore = scores.get(highScoreV);
        if(!running){
            return;
        }
        normalizedSortedScores = normalize(scores, maxScore);

        /*int numRings = (int) Math.ceil(DIAMETER / RING_SIZE);*/
        HashSet<Double> distinctValues = new HashSet<>();
        distinctValues.addAll(normalizedSortedScores.values());
        int numRings = distinctValues.size();

        /*		List<Annotation<Shape>> rings = createRings(center.getLocation(),
         numRings);
         for (Annotation<Shape> ring : rings) {
         drawGraph.getAnnotater().addShapeAnnotation(ring);
         }
         */
        HashMap<IVertex, Point2D> positions = positionVertices(normalizedSortedScores, sortedVertices);

        addLocationToAttributeList(positions);

            
        /*List<Annotation<String>> labels = createLabels(center.getLocation(),
         numRings, maxScore, -1 * maxScore / numRings);
         for (Annotation<String> label : labels) {
         drawGraph.getAnnotater().addStringAnnotation(label);
         }*/
        blnDone = true;

    }

    /**
     * locates all nodes in the unit square (0,0) - (1,1)
     *
     * @param normalizedSortedScores
     * @param sortedVertices
     * @return - A mapping of vertices to their respective positions.
     */
    public HashMap<IVertex, Point2D> positionVertices(
            HashMap<V, Double> normalizedSortedScores,
            LinkedList<V> sortedVertices) {

        HashMap<IVertex, Point2D> vertexToPoint = new HashMap<>();

        if (normalizedSortedScores.isEmpty()) {
            return vertexToPoint;
        }

        // We want the highest scoring vertex in the middle.
        double centerX = 0.5;
        double centerY = 0.5;

        V highestScoreV = (V) sortedVertices.toArray()[sortedVertices.size() - 1];
        vertexToPoint.put(highestScoreV, new Point2D.Double(centerX, centerY));

        // All the other vertices should fall around the center based on their
        // score.
        double angle = 0.0;
        double step = Math.ceil(360.0 / normalizedSortedScores.keySet().size());
        int offset = 0; // Prevents the vertices from clumping together.

        for (V v : normalizedSortedScores.keySet()) {
            if (v.equals(highestScoreV)) {
                setLocation(highestScoreV, centerX, centerY);
                continue;
            }

            double distance = normalizedSortedScores.get(highestScoreV)
                    - normalizedSortedScores.get(v);

            distance *= RADIUS;

            double x = Math.cos(angle) * distance;
            x += centerX;

            double y = Math.sin(angle) * distance;
            y += centerY;

            vertexToPoint.put(v, new Point2D.Double(x, y));

            angle += step + offset % 360;
            offset += 1;
        }

        return vertexToPoint;
    }

    /**
     * Given a center, create a number of ring annotations that form a bullseye.
     *
     * @param center - The center from which to originate the rings.
     * @param numRings - The number of rings.
     * @return - A list of the ring annotations.
     *//*
     public List<Annotation<Shape>> createRings(Point2D center, int numRings) {
     List<Annotation<Shape>> rings = new ArrayList<Annotation<Shape>>();

     for (int i = 0, shade = 0; i < numRings; i++, shade++) {
     double x = center.getX();
     double y = center.getY();
     boolean isShaded = shade % 2 == 0;

     double innerRadius = i * RING_SIZE;
     double outerRadius = (i + 1) * RING_SIZE;

     Annotation<Shape> ring = createRingAnnotation(x, y, innerRadius,
     outerRadius, isShaded);
     rings.add(ring);
     }

     return rings;
     }

     */

    /**
     * Create a number of label annotations that are positioned alongside the
     * top of the rings.
     *
     * @param center - The center from which to originate the labels.
     * @param numLabels - The number of labels.
     * @param startValue - The numeric label to start with.
     * @param increment - The amount by which to increment each label.
     * @return - A list of string annotations that are the labels.
     *//*
     public List<Annotation<String>> createLabels(Point2D center, int numLabels,
     double startValue, double increment) {
     List<Annotation<String>> labels = new ArrayList<Annotation<String>>();

     for (int i = 0; i < numLabels; i++) {
     double x = center.getX();
     double y = center.getY();

     double outer = (i + 1) * RING_SIZE;
     double score = startValue + (i + 1) * increment;

     DecimalFormat format = new DecimalFormat("#.000");
     String text = format.format(score);

     Annotation<String> label = createStringAnnotation(
     x - 2.5 * text.length(), y - 0.5 * outer, text);
     labels.add(label);
     }

     return labels;
     }*/

    // TODO: This function is just a utility. It should be moved.
    /**
     * Create a number of label annotations that are positioned alongside the
     * top of the rings.
     *
     * @param values
     * @param max
     * @return - A list of string annotations that are the labels.
     */
    public HashMap<V, Double> normalize(HashMap<V, Double> values,
            double max) {
        HashMap<V, Double> normalized = new HashMap<>(
                values);
        for (V v : values.keySet()) {
            double normed = values.get(v) / max;
            normalized.put(v, normed);
        }
        return normalized;
    }

    /*private Annotation<String> createStringAnnotation(double x, double y,
     String label) {
     Annotation<String> annotation = new Annotation<String>(label,
     Annotation.Layer.UPPER, null, true, new Point2D.Double(x, y));
     return annotation;
     }

     private Annotation<Shape> createRingAnnotation(double x, double y,
     double innerRadius, double outerRadius, boolean shade) {

     Area donut = new Area(new Ellipse2D.Double(x - outerRadius / 2.0, y
     - outerRadius / 2.0, outerRadius, outerRadius));

     Area hole = new Area(new Ellipse2D.Double(x - innerRadius / 2.0, y
     - innerRadius / 2.0, innerRadius, innerRadius));
     donut.subtract(hole);

     Annotation<Shape> annotation = new Annotation<Shape>(donut,
     Annotation.Layer.LOWER, shade ? Color.gray.brighter()
     : GraphPanel.DEFAULT_BACKGROUND_COLOR, true,
     new Point2D.Double(0, 0));
     return annotation;
     }*/
    @Override
    public String toString() {
        return STR_NAME;
    }

    @Override
    public Type getType() {
        return type;
    }

    /**
     *
     * @return
     */
    @Override
    public Map<Integer, Double[]> getResults() {
        Map<Integer, Double[]> scores = new HashMap<>();

        for (V v : positions.keySet()) {
            Double[] position = new Double[2];
            position[0] = positions.get(v).getX();
            position[1] = positions.get(v).getY();

            scores.put(v.getId(), position);
        }

        return scores;
    }

    private HashMap<V, Double> getMetricScores(String metricAttribute) {
        vertexMetricValues = new HashMap<>();
        if(dynaGraph.getGraph(tf).getVertexNumericalAttributeNames().contains(metricAttribute)){

            for(V v : dynaGraph.getGraph(tf).getAllVertices()){
                vertexMetricValues.put(v, Double.parseDouble(v.getSystemAttributer().getAttributeValue(metricAttribute, tf)));
            }
        }
        
        return vertexMetricValues;
    }

}
