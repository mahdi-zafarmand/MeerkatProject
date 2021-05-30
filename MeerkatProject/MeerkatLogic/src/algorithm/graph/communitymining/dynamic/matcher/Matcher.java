/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm.graph.communitymining.dynamic.matcher;

import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;


public interface Matcher<V extends IVertex, E extends IEdge<V>> {

	public void getMatching();
}