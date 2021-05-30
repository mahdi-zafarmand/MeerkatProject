/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm.graph.evolutionanalysis;

import java.util.HashMap;


/**
 * This class represents a transition that occur between two survival
 * communities. The first community that is mostly earlier in time (the left
 * hand side) is called the source community. The second community that is
 * mostly later in time (the right hand side) is called the result community.
 * 
 * @author takaffol
 */
public class Transition {

	public enum TransitionName {

		SHRINK, EXPAND, COMPACT, DIFFUSE, PERSIST, LEADER, NONE

	}
}