/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.layout.algorithms;

import datastructure.core.TimeFrame;
import java.awt.geom.Point2D;
import java.util.Random;

import datastructure.core.graph.classinterface.*;

/**
 * Class Name : RandomLayout
 * Created Date : From previous version of Meerkat
 * Description : Lays out vertices randomly in the 1 by 1 square.
 * Version : 2.0
 *
 * @author : Afra
 *
 * EDIT HISTORY (most recent at the top) 
 * Date         Author      Description 
 * 2016-03-16   Afra        Added String[] parameters to the set of parameters of the constructor.
 * 
 * @param <V> type of vertices
 * @param <E> type of edges
 */
public class RandomLayout<V extends IVertex, E extends IEdge<V>>
        extends Layout<V, E> {

    /**
     *
     */
    public static String STR_NAME = "Random Layout";

    /**
     *
     * @param pIGraph
     * @param tf
     * @param parameters
     */
    public RandomLayout(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String[] parameters) {
        super(pIGraph, tf, parameters);
        type = Type.STANDARD;
    }

    /**
     *
     */
    @Override
    public void run() {
        double size = computeNumberOfVerticesWithRespectToTheirWeight();

        Random random = new Random();
        // initially dynaGraph was fetching allVertices which resulted in an error, changing it
        // to fetch only the vertices of current timeframe fixes the error.
        for (IVertex vertex : dynaGraph.getVertices(tf)) {
            if(!running){
                break;
            }
            Point2D coordinate = new Point2D.Double(random.nextDouble(),
                    random.nextDouble());
            setLocation(vertex, coordinate.getX(), coordinate.getY());
        }
        blnDone = true;
    }

    @Override
    public String toString() {
        return STR_NAME;
    }

    @Override
    public Type getType() {
        return type;
    }

}
