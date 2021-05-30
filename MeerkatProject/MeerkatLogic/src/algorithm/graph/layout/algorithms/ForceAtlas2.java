/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.layout.algorithms;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author aabnar
 */
public class ForceAtlas2<V extends IVertex, E extends IEdge<V>> extends Layout<V, E> {

    Map<V, Double> mapSize = new HashMap<>();
    
    /* v1 --> v_neigh --> (fx,fy) */
    Map<V, double[]> mapForce_t = new HashMap<>();
    Map<V, double[]> mapForce_prev_t = new HashMap<>();

    double dbl_s_prev_t_G;

    double dbl_k_gravity = 1;
    double dbl_k_speed = 0.1;
    double dbl_k_repulsion = 0.1;
    double dbl_tao = 0.01;
    
    double lamda = 0.0001;

    public ForceAtlas2(IDynamicGraph<V, E> pdynaGraph, 
            TimeFrame tf, 
            String[] parameters) {
        
        super(pdynaGraph, tf, parameters);
        this.type = Type.STANDARD;
        
        for (V v : dynaGraph.getVertices(tf)) {
            mapSize.put(v, 0.0);
        }

    }

    public void run() {
        
        blnDone = false;
        
//        lamda = Math.min(lamda , 1.0 / dynaGraph.getVertices(tf).size());

        for (V v : dynaGraph.getVertices(tf)) {
            mapForce_prev_t.put(v, new double[2]);
        }
        
        int intNumberOfIterations = 0;
        
        while (true && running) {
            intNumberOfIterations++;
            for (V v : dynaGraph.getVertices(tf)) {
                double[] vdbl_Force_v = new double[2];
                vdbl_Force_v[0] = 0.0;
                vdbl_Force_v[1] = 0.0;

                double dblX = Double.parseDouble(v.getSystemAttributer()
                    .getAttributeValue(MeerkatSystem.X, tf));
                double dblY = Double.parseDouble(v.getSystemAttributer()
                    .getAttributeValue(MeerkatSystem.Y, tf));
                double dbl_f_n_gravity = calculateFGravity(v, dblX, dblY);

                for (V u : dynaGraph.getNeighbors(v, tf)) {
                    if(!running)
                        return;
                    if (v.equals(u)) {
                        continue;
                    }
                    double dblX_neigh = 
                            Double.parseDouble(u.getSystemAttributer()
                            .getAttributeValue(MeerkatSystem.X, tf));
                    double dblY_neigh = 
                            Double.parseDouble(u.getSystemAttributer()
                            .getAttributeValue(MeerkatSystem.Y, tf));
                    double distance = 
                            getDistance(dblX, dblY, dblX_neigh, dblY_neigh);

                    distance = distance - mapSize.get(v) - mapSize.get(u);

                    double dbl_f_attraction = 0;
                    dbl_f_attraction = calculateFAttraction(v, u, distance);
                    double dbl_f_repulsion = 
                            calculateFRepulsion(v, u, distance);

                    Vector<Double> force = computeForce(dbl_f_attraction, 
                            dbl_f_repulsion, 
                            dbl_f_n_gravity, 
                            dblX, 
                            dblY, 
                            dblX_neigh, 
                            dblY_neigh);

                    vdbl_Force_v[0] += force.get(0);
                    vdbl_Force_v[1] += force.get(1);
                }

                mapForce_t.put(v, vdbl_Force_v);
//                System.out.println("ForceAtlas2: force for vid (" +
//                        v.getId() +
//                        ") is : (" + 
//                        mapForce_t.get(v)[0] + 
//                        "," + 
//                        mapForce_t.get(v)[1]+")");
            }

            double dbl_traction_G = computeTractionforGraph();

            double dbl_swg_G = computeSWGforGraph();

            double dbl_speed_G = computeSpeedforGraph(dbl_traction_G, dbl_swg_G);


            double dbl_total_dislocation = 0.0;
            
            Map<V,double[]> lstPoints = new HashMap<>();
            double minX = Double.MAX_VALUE;
            double maxX = Double.MIN_VALUE;
            
            double minY = Double.MAX_VALUE;
            double maxY = Double.MIN_VALUE; 
            
            for (V v : dynaGraph.getVertices(tf)) {
                if(!running)
                    return;
                double swg_n = Math.sqrt(Math.pow(mapForce_t.get(v)[0] - 
                        mapForce_prev_t.get(v)[0], 2.0) +
                        Math.pow(mapForce_t.get(v)[1] - 
                                mapForce_prev_t.get(v)[1], 2.0));

                double dbl_speed_v = dbl_k_speed * dbl_speed_G / 
                        (1 + dbl_speed_G * Math.sqrt(dbl_swg_G));
                
//                System.out.println("ForceAtlas2.run() speed(v0 parameters : " + dbl_k_speed + " , " + 
//                        dbl_speed_G + " , " + dbl_swg_G );

                Vector<Double> vecDislocation = new Vector<>();
                vecDislocation.add(mapForce_t.get(v)[0] * dbl_speed_v);
                vecDislocation.add(mapForce_t.get(v)[1] * dbl_speed_v);

//                System.out.println("ForceAtlas2.run() force v * speed v = " + mapForce_t.get(v)[0] + " * " + dbl_speed_v);
                double dblX = Double.parseDouble(v.getSystemAttributer()
                    .getAttributeValue(MeerkatSystem.X, tf));
                double dblY = Double.parseDouble(v.getSystemAttributer()
                    .getAttributeValue(MeerkatSystem.Y, tf));

                double[] dblNew = new double[2];
                dblNew[0] = dblX + vecDislocation.get(0);
                dblNew[1] = dblY + vecDislocation.get(1);

                
                
                
                
                lstPoints.put(v, dblNew);
                
                if (dblNew[0] < minX) {
                    minX = dblNew[0];
                } else if (dblNew[0] > maxX) {
                    maxX = dblNew[0];
                }
                
                if (dblNew[1] < minY) {
                    minY = dblNew[1];
                } else if (dblNew[1] > maxY) {
                    maxY = dblNew[1];
                }
            }
            
            for (V v : dynaGraph.getVertices(tf)) {
                if(!running)
                    return;
                double dblOldX = Double.parseDouble(v.getSystemAttributer()
                    .getAttributeValue(MeerkatSystem.X, tf));
                double dblOldY = Double.parseDouble(v.getSystemAttributer()
                    .getAttributeValue(MeerkatSystem.Y, tf));
                
                double x = (lstPoints.get(v)[0] - minX ) / (maxX - minX);
                double y = (lstPoints.get(v)[1] - minY) / (maxY - minY);
                
                
                v.getSystemAttributer().addAttributeValue(
                        MeerkatSystem.X, x+"", new Date(), tf);
                v.getSystemAttributer().addAttributeValue(
                        MeerkatSystem.Y, y +"", new Date(), tf);
                
                dbl_total_dislocation = Math.max(dbl_total_dislocation,Math.sqrt(
                        (x - dblOldX) * (x - dblOldX) + 
                                (y - dblOldY) * (y - dblOldY)));
                
                
//                System.out.println (x + " : " + dblOldX);
//                System.out.println (y + " : " + dblOldY);

            }
            

            System.out.println("ForceAtlas2.run() : total displacement : " + dbl_total_dislocation);
            if (dbl_total_dislocation < lamda) {
                System.out.println("ForceAtlas2.run() : total displacement : " + dbl_total_dislocation);
                System.out.println("ForceAtlas2.run() : Number of iterations : " + intNumberOfIterations);
                break;
            } else {
                for (V v : mapForce_t.keySet()) {
                    mapForce_prev_t.put(v, mapForce_t.get(v));
                }
            }
        }
        
        
        updateDataStructure();
        blnDone = true;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Type getType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private double getDistance(double pdblX1, double pdblY1, 
            double pdblX2, double pdblY2) {
        double distanceSQ = (pdblX1 - pdblX2) * (pdblX1 - pdblX2)
                + (pdblY1 - pdblY2) * (pdblY1 - pdblY2);

        double distance = Math.sqrt(distanceSQ) * 100 + 1;

        return distance;
    }

    private double calculateFGravity(V v, double dblX, double dblY) {
        
        double dbl_f = dbl_k_gravity
                * (dynaGraph.getDegree(v, tf) + 1)
                / getDistance(dblX, dblY, 50, 50);

        return dbl_f;
    }

    private double calculateFAttraction(V v, V neigh, double distance) {
        Collection<V> n1 = dynaGraph.getNeighbors(v, tf);
        Collection<V> n2 = dynaGraph.getNeighbors(neigh, tf);
        
        List<V> intersection = new LinkedList<>();
        for (V vertex : n1) {
            if (n2.contains(vertex)) {
                intersection.add(vertex);
            }
        }
        double dbl_f = 0;
        if (distance > 10) {
            dbl_f = distance ;
//                    *  
//                    (dynaGraph.getDegree(v, tf) + 1)
//                    * (dynaGraph.getDegree(neigh, tf) + 1) * 
//                    intersection.size() / n1.size();
        } else {
            dbl_f = 0;
        }
        
        return dbl_f;
    }

    private double calculateFRepulsion(V v, V neigh, double distance) {
        Collection<V> n1 = dynaGraph.getNeighbors(v, tf);
        Collection<V> n2 = dynaGraph.getNeighbors(neigh, tf);
        
        List<V> intersection = new LinkedList<>();
        for (V vertex : n1) {
            if (n2.contains(vertex)) {
                intersection.add(vertex);
            }
        }
        
        double ratio = 1.0;
        if (n1.size() > 0 && intersection.size() / n1.size() < 0.5) {
            ratio += intersection.size();
        } 
        
        double dbl_f = 0;
        if (distance > 40) {
            dbl_f = dbl_k_repulsion
//                    * (dynaGraph.getDegree(v, tf) + 1)
//                    * (dynaGraph.getDegree(neigh, tf) + 1)
                    / distance;
        } else if (distance < 40) {
            dbl_f = dbl_k_repulsion
                    * (dynaGraph.getDegree(v, tf) + 1)
                    * (dynaGraph.getDegree(neigh, tf) + 1);
        }
        return dbl_f;
    }

    private Vector<Double> computeForce(double dbl_f_attraction, 
            double dbl_f_repulsion, 
            double dbl_f_n_gravity, 
            double dblX, 
            double dblY, 
            double dblX_neigh, 
            double dblY_neigh) {
        
        double distance = getDistance(dblX, dblY, dblX_neigh, dblY_neigh);
        
        double dbl_fa_x = 
                -1 * (dblX - dblX_neigh) / distance * dbl_f_attraction;
        double dbl_fa_y = 
                -1 * (dblY - dblY_neigh) / distance * dbl_f_attraction;
        
        double dbl_fr_x = (dblX - dblX_neigh) / distance * dbl_f_repulsion;
        double dbl_fr_y = (dblY - dblY_neigh) / distance * dbl_f_repulsion;
        
        double distanceToCenter = getDistance(dblX, dblY, 0.5, 0.5);
        double dbl_fg_x = (0.5 - dblX) / distanceToCenter * dbl_f_n_gravity;
        double dbl_fg_y = (0.5 - dblY) / distanceToCenter * dbl_f_n_gravity;
        
        
        double dbl_f_x = dbl_fa_x + dbl_fr_x + dbl_fg_x;
        double dbl_f_y = dbl_fa_y + dbl_fr_y + dbl_fg_y;
        
        Vector<Double> force = new Vector<>();
        force.add(dbl_f_x);
        force.add(dbl_f_y);
        
        return force;
    }

    private double computeTractionforGraph() {
        double dbl_traction = 0;
        
        for (V v : dynaGraph.getVertices(tf)) {
            dbl_traction += (dynaGraph.getDegree(v, tf) + 1) *
                    computeTractionforVertex(v);
        }
        
        return dbl_traction;
    }
    
    
    private double computeTractionforVertex(V v) {
        double dbl_trac_v = 0;
        double x = mapForce_t.get(v)[0] + mapForce_prev_t.get(v)[0];
        double y = mapForce_t.get(v)[1] + mapForce_prev_t.get(v)[1];
        dbl_trac_v = Math.sqrt(x*x + y*y) / 2;
        
        return dbl_trac_v;
    }

    private double computeSWGforGraph() {
        double dbl_swg_G = 0.0;
        for (V v : dynaGraph.getVertices(tf)) {
            dbl_swg_G += (dynaGraph.getDegree(v, tf) + 1) *
                    computeSWGforVertex(v);
        }
        
        return dbl_swg_G;
    }

    private double computeSWGforVertex(V v) {
        double dbl_swg_v = 0;
        double x = mapForce_t.get(v)[0] - mapForce_prev_t.get(v)[0];
        double y = mapForce_t.get(v)[1] - mapForce_prev_t.get(v)[1];
        
        dbl_swg_v = Math.sqrt(x*x + y*y);
        
        return dbl_swg_v;
    }

    private double computeSpeedforGraph(double dbl_traction_G, 
                                        double dbl_swg_G) {
        
        double dbl_speed_G;
        if (dbl_s_prev_t_G > 0) {
            dbl_speed_G = Math.min(dbl_tao * dbl_traction_G 
                / dbl_swg_G, 1.5 * dbl_s_prev_t_G);
        } else {
            dbl_speed_G = dbl_tao * dbl_traction_G  / dbl_swg_G;
        }
        
        return dbl_speed_G;
    }

    private double computeTotalDeltaForces() {
        double dbl_delta_force = 0.0;
        
        for (V v : mapForce_t.keySet()) {
            double x = mapForce_t.get(v)[0] - mapForce_prev_t.get(v)[0];
            double y = mapForce_t.get(v)[1] - mapForce_prev_t.get(v)[1];
            
            dbl_delta_force += Math.sqrt(x*x + y*y);
        }
        
        return dbl_delta_force;
    }
 
}