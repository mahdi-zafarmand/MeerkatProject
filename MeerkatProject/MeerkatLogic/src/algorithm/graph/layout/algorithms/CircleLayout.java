/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.layout.algorithms;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;

import datastructure.core.graph.classinterface.*;
import java.util.Date;

/**
 * Class Name : CircleLayout
 * Created Date : From previous version of Meerkat
 * Description : Lays out vertices on a circle in the 1 by 1 square
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
public class CircleLayout<V extends IVertex, E extends IEdge<V>> extends
        Layout<V, E> {

    /**
     *
     */
    public static final String STR_NAME = "Circle Layout";

    /**
     * Method Name : CircleLayout (Constructor) 
     * Created Date : From previous version of Meerkat 
     * Description : Version : 2.0
     *
     * @author : Afra
     *
     * @param pIGraph
     * @param tf
     * @param parameters
     *
     * EDIT HISTORY (most recent at the top) 
     * Date         Author  Description 
     * 2016-03-16   Afra    Added String[] parameters to the set of parameters.
     *                      For now there is not parameter for circle layout.
     *                      It is there for having same signature with other
     *                      layout algorithms (because of reflection usages)
     *
     */
    public CircleLayout(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String[] parameters) {
        super(pIGraph, tf, parameters);
        type = Type.STANDARD;
    }

    /**
     *
     */
    @Override
    public void run() {

        double dblNumberOfFakeVertices
                = computeNumberOfVerticesWithRespectToTheirWeight();

//        double nodeRadi = Math.PI / dblNumberOfFakeVertices;
        double dblR = 0.4;

        double dblAngelPerVRadian = 2 * Math.PI / dblNumberOfFakeVertices;
        double dblTotalAngelRadian = 0.0;

        System.out.println("CircleLayout.run() : Number of fake vertices = " + dblNumberOfFakeVertices);
        Date dtWriteTime = new Date();
        for (V v : dynaGraph.getVertices(tf)) {
            
            if(!running){
                break;
            }
            
            double angelRadian = dblAngelPerVRadian * v.getWeight();

            /*
             * while computing location of the vertices on a circle layout,
             * we assume that the width and height of the canvas is 2 * R,
             * and the center of the layout is located at (x=R, y=R). 
             */
            double centerAngel = dblTotalAngelRadian + angelRadian / 2;
            double x = dblR * Math.sin(centerAngel) + dblR;
            double y = dblR * Math.cos(centerAngel) + dblR;

            // System.out.println("CircleLayout.run(): " + centerAngel);
            v.getSystemAttributer().addAttributeValue(MeerkatSystem.X, x + "", dtWriteTime, tf);
            v.getSystemAttributer().addAttributeValue(MeerkatSystem.Y, y + "", dtWriteTime, tf);
            
            dblTotalAngelRadian += angelRadian;
        }
        blnDone = true;
    }

    /* Computing radius of the circle based on numberoffakevertices.
     * Premiter of the circle layout should be capable of fitting all vertices and
     * not hiding some under some others. 
     * Thus: 2.pi.R = numberoffakevertices * r_fixed * 2 */
    private double computeRadiusCircleLayout(int intNumVertices,
            double dblVerticeRadius) {
        return (intNumVertices * dblVerticeRadius / Math.PI);
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