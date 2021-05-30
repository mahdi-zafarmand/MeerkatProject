/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph;

import algorithm.graph.shortestpath.DijkstraShortestPath;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.HashMap;

/**
 *  Class Name      : GeneralStatistics
 *  Created Date    : 2015-09-29
 *  Description     : Static class containing all static methods that
 *                      compute statics on an igraph
 *  Version         : 1.0
 * @author aabnar
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-09-29      aabnar          added: computeAvgClusteringCoefficient.
 */

public class GeneralStatistics {
    
    /**
     * Computes avgCC by the following formula.
     * avgCC = 1/n (sum(cc_i))
     * cc_i = ( 2 * e_i) / (k_i (k_i - 1 )) for undirected networks.
     * cc_i =  e_i / (k_i (k_i - 1 )) for directed networks.
     * 
     * where e_i : number of edges between i's neighbors.
     *       k_i : number of node i's neighbors.
     * 
     * @param pIgraph
     * @param tf
     * @return
     *       avg clustering coefficient : double
     */
    public static double computeAvgClusteringCoefficient(IDynamicGraph<IVertex,IEdge<IVertex>> pDynaGraph, TimeFrame tf) {        
        double avgCC = 0.0;
        
        for (IVertex v : pDynaGraph.getVertices(tf)) {
            double dblLocalCC = 0;
            double dblConnectedNeighbors = 0;
            double dblPossibleTriads = 0;
            for (IVertex u1 : pDynaGraph.getNeighbors(v, tf)) {
                for (IVertex u2 : pDynaGraph.getNeighbors(v, tf)) {
                    if (u1.equals(u2)) {
                        continue;
                    }
                    if (pDynaGraph.findEdge(u1, u2, tf) != null) {
                        dblConnectedNeighbors++;
                    }
                }
            }
            int intNSize = pDynaGraph.getNeighbors(v, tf).size();
            dblPossibleTriads = intNSize * (intNSize - 1);
            
            if(dblPossibleTriads>0)
                dblLocalCC = dblConnectedNeighbors / dblPossibleTriads;
            
            avgCC += dblLocalCC;
        }
        
        avgCC /= pDynaGraph.getVertices(tf).size();
        
        return avgCC;
    }
    
    /**
     * Method Name: computeTransitivity
     * Created Date: March 31, 2016
     * Description: computes graph transitivity or in other words global clustering coefficient (unweighted version)
     * Version: 1.0
     * Author: Afra
     * 
     * @param pDynaGraph
     *          IDynamicGraph
     * @param tf
     *          TimeFrame
     * @return 
     *          double
     */
    public static double computeTransitivity(IDynamicGraph<IVertex,IEdge<IVertex>> pDynaGraph, TimeFrame tf) {
        double dblClosedTriads = 0;
        double dblAllTriads = 0;
        for (IVertex v : pDynaGraph.getVertices(tf)) {
            double dblConnectedNeighbors = 0;
            for (IVertex u1 : pDynaGraph.getNeighbors(v, tf)) {
                for (IVertex u2 : pDynaGraph.getNeighbors(v, tf)) {
                    if (u1.equals(u2)) {
                        continue;
                    }
                    IEdge e = pDynaGraph.findEdge(u1, u2,tf);
                    
                    if (e != null) {
                        dblConnectedNeighbors++;
                    }
                }
            }
            dblClosedTriads += dblConnectedNeighbors;
            dblAllTriads += pDynaGraph.getNeighbors(v, tf).size();
        }
        
        double dblTransitivity = dblClosedTriads / dblAllTriads;
        
        return dblTransitivity;
    }
    
    /**
     *
     * @param pDynaGraph
     * @param tf
     * @return
     */
    public static double computeAssortativity(
            IDynamicGraph<IVertex,IEdge<IVertex>> pDynaGraph, TimeFrame tf) {
        
        double dblAssort = 0;
        
        int[] arrSourceD = new int[pDynaGraph.getEdges(tf).size()];
        int[] arrDestD = new int[pDynaGraph.getEdges(tf).size()];
        
        int eCount = 0;
        for (IEdge<IVertex> e : pDynaGraph.getEdges(tf)) {
            IVertex Vst = e.getSource();
            IVertex Ven = e.getDestination();
            
            int dv = pDynaGraph.getNeighbors(Vst, tf).size();
            int du = pDynaGraph.getNeighbors(Ven, tf).size();
            
            arrSourceD[eCount] = dv;
            arrDestD[eCount] = du;
            
            
        }
        
        System.out.println(arrSourceD.length);
        System.out.println(arrDestD);
        
        dblAssort = computePearsonCorrelation(arrSourceD, arrDestD);
              
        return dblAssort;
    }

    private static double computePearsonCorrelation(int[] arrintX, int[] arrintY) {
        HashMap<Integer, Integer> XYDist = new HashMap<>();
        
        for (int i = 0 ; i < arrintX.length ; i++) {
            int mult = arrintX[i] * arrintY[i];
            if (!XYDist.containsKey(mult)) {
                XYDist.put(mult, 0);
            }
            XYDist.put(mult, XYDist.get(mult) + 1);
        }
        
        double dblPearson = 0.0;
        
        double meanX = 0.0;
        
        double meanY = 0.0;
        
        for (int i = 0 ; i < arrintX.length ; i++) {
            meanX += arrintX[i];
            meanY += arrintY[i];
        }
        
        meanX /= arrintX.length * 1.0;
        meanY /= arrintY.length * 1.0;

        double EXY = 0;
            
        for (int mult : XYDist.keySet()) {
            EXY += mult * XYDist.get(mult) * 1.0 / XYDist.size();
            
            
        }
        
        double varianceX = 0;
        double varianceY = 0;    
        for (int i = 0 ; i < arrintX.length ; i++) {
            varianceX += Math.pow(arrintX[i] - meanX, 2);
            varianceY += Math.pow(arrintY[i] - meanY, 2);
        }
        varianceX = Math.sqrt(varianceX);
        varianceY = Math.sqrt(varianceY);
        
        System.out.println(EXY + " , " + meanX +  meanY + " , " + varianceX + " , " + varianceY);
        dblPearson = (EXY - meanX * meanY) / (varianceX * varianceY);
        
        
        return dblPearson;
    }
    
    /**
     *
     * @param pDynaGraph
     * @param tf
     * @return
     */
    public static double computeAvgNumberOfConnections(IDynamicGraph<IVertex,IEdge<IVertex>> pDynaGraph, TimeFrame tf) {
        double dblAvgCon = 0;
        
        for (IVertex v : pDynaGraph.getVertices(tf)) {
            dblAvgCon += pDynaGraph.getNeighbors(v, tf).size();
        }
        
        dblAvgCon /= pDynaGraph.getVertices(tf).size();
        
        return dblAvgCon;
    }
    
    /**
     *
     * @param pDynaGraph
     * @param tf
     * @return
     */
    public static double computeAvgShortestPath(IDynamicGraph<IVertex,IEdge<IVertex>> pDynaGraph, TimeFrame tf) {
        double dblAvgSP = 0;
        DijkstraShortestPath<IVertex,IEdge<IVertex>> djkstraAlg = new DijkstraShortestPath<>(pDynaGraph,tf);
        djkstraAlg.computeAllPairsSPUnweighted();
        
        int intCountE = 0;
        for (IVertex v : pDynaGraph.getVertices(tf)) {
            for (IVertex u : pDynaGraph.getVertices(tf)) {
                if (v.equals(u)) {
                    continue;
                }
                dblAvgSP += djkstraAlg.getShortestPath(v, u);
                intCountE++;
            }
        }
        
        dblAvgSP /= intCountE;
        
        return dblAvgSP;
    }
}
