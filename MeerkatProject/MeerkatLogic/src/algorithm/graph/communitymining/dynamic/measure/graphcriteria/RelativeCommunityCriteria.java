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
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public abstract class RelativeCommunityCriteria
                                        <V extends IVertex,E extends IEdge<V>> {
	protected IDynamicGraph<V, E> dynaGraph;
        protected TimeFrame tf;
        //protected Transformer<E, ? extends Number> weights;
	//protected Vector<Graph<V,E>> communities;
	protected double MaxWeight, SumWeight;
	
	protected boolean selfWeight = false; // (A(i,i) == MaxWeight )? 
	protected boolean normalizedWeight = false; // (A(i,i) == (A(i,i)/MaxWeight )?
	
	
	public RelativeCommunityCriteria (IDynamicGraph<V, E> graph,
                TimeFrame ptf){
		this(graph, ptf, false, false);
	}
	
	public RelativeCommunityCriteria(IDynamicGraph<V, E> graph,
                        TimeFrame ptf,
                        boolean selfWeight,
			boolean normalized) {
		super();
		this.dynaGraph = graph;
                this.tf = ptf;
		//this.weights = weights;
		this.selfWeight = selfWeight;
		this.normalizedWeight = normalized;
		
		
                MaxWeight = 0;
                SumWeight = 0;
                for (E e : graph.getAllEdges()) {
                    if(e!=null){
                        double w = e.getWeight();
                        if( w > MaxWeight) MaxWeight = w;
                        SumWeight += w;
                    }
                }
	}
	
	protected  Set<V> getUnionNeighbours (V source, V target) {
            Set<V> sNeigh = new HashSet<>(dynaGraph.getNeighbors(source, tf));
            Set<V> tNeigh = new HashSet<>(dynaGraph.getNeighbors(target, tf));
            sNeigh.addAll(tNeigh);
            sNeigh.add(source);
            sNeigh.add(target);
            return sNeigh;
	}
	
	protected abstract double evaluateCommunities(
                Vector<Set<V>> communities);
	
}
