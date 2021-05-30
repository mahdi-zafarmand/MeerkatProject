/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm.graph.communitymining.dynamic.measure.graphcriteria;

import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.Set;
import java.util.Vector;


/**
 * <pre> Q(undirected)= 1/2m Sum(i,j){Aij - (K(i)-K(j))/2m } Delta(i,j) </pre> 
 * where m is number of links, K(i) is degree of node i: # of links for node i  
 * <p> Delta(i,j) is one if i and j are in the same cluster, otherwise 0
 * <p> Aij is 1 for un-weighted networks: can be any number for weighted networks
 */

public class Modularity<V extends IVertex, E extends IEdge<V>> 
                                    extends RelativeCommunityCriteria<V,E> {

    public Modularity(IDynamicGraph<V, E> graph, TimeFrame tf) {
        super(graph, tf);
    }

    //TODO: make it weighted !!!! edge count = sum W, size = sum W, 1 = W 

    /**
     *
     * @param communities
     * @return
     */
    @Override
    public double evaluateCommunities(Vector<Set<V>> communities){

        double modularity = 0;
        double m; // edges_within_cluster, edges_between_cluster

        m = SumWeight;//graph.getEdgeCount(); //I think the edge count is more accurate; check it for weighted 

        for (Set<V> cluster : communities) {
            for (V v1 : dynaGraph.getVertices(tf)) {
                for (V v2 : dynaGraph.getNeighbors(v1,tf)) {
                    if (cluster.contains(v2)) {
                        E e = dynaGraph.findEdge(v1, v2, tf);
                        modularity += e.getWeight();
                    }
                    double di = 0, dj = 0; 
                    for (V v : dynaGraph.getNeighbors(v1,tf)) {//graph.getNeighbors(v1).size()
                        E e = dynaGraph.findEdge(v1,v,tf);
                        di+= e.getWeight();
                    }
                    for (V v : dynaGraph.getNeighbors(v2,tf)) {//graph.getNeighbors(v2).size()
                        E e = dynaGraph.findEdge(v2,v,tf);
                        dj+= e.getWeight();
                    }
                    modularity -= di*dj / (2 * m);
                }
            }
        }

        modularity /= (2 * m);

        return modularity;
    }

    public double evaluateCommunitiesAlt(Vector<Set<V>> communities){
        double modularity = 0;
        double Max , E=0; 

        Max = 2 *  dynaGraph.getEdges(tf).size(); //Original formula

        for (Set<V> cluster : communities) {

            for (V v1 : cluster) {
                for (V v2 : cluster) {
                    double pij = 0, pi=0,pj=0;

                    for (V k : dynaGraph.getNeighbors(v1,tf)) pi++;	pi/=Max;
                    for (V k : dynaGraph.getNeighbors(v2,tf)) pj++;	pj/=Max;

                    if (dynaGraph.getNeighbors(v1,tf).contains(v2))
                        pij = 1/(Max);

                    modularity += pij;
                    E += pi*pj ;				
                }
            }
        }
        modularity  = modularity - E  ;// (modularity - E );  Original formula
        return modularity;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return "Modularity";
    }

    public String getName() {
        return "Q" ;
    }

}
