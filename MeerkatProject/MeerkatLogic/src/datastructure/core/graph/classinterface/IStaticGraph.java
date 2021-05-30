/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.graph.classinterface;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 * 
 * *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2018-01-18      Talat           Added updateVertexColors, updateEdgeColors 
 */
public interface IStaticGraph<V extends IVertex,E extends IEdge<V>> extends IGraph<V,E> {
    
}
