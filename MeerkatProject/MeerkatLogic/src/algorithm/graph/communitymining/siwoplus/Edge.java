/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.communitymining.siwoplus;

/**
 *
 * @author mahdi
 */
public class Edge {
    
    public Node start;
    public Node end;
    public double weight;

    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
        this.weight = 1.0;
    }

    public Edge(Node start, Node end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
    
    public String toString() {
    	return start.toString() + " " + end.toString() + " " + weight;
    }
}
