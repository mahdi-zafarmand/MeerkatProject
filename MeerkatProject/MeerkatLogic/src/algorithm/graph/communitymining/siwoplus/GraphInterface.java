/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.communitymining.siwoplus;

import java.util.ArrayList;

/**
 *
 * @author mahdi
 */
public interface GraphInterface {
    Node addNode(Node node);
    Node addNode(int nodeId);
    Node getNode(int nodeId);
    ArrayList<Node> nodes();
    int getNumberOfNodes();

    boolean hasEdge(Node node1, Node node2);
    boolean hasEdge(int nodeId1, int nodeId2);
    void addEdge(Node node1, Node node2);
    void addEdge(int nodeId1, int nodeId2);
    void addEdge(Node node1, Node node2, double weight);
    void addEdge(int nodeId1, int nodeId2, double weight);
    ArrayList<Edge> edges();	
    int getNumberOfEdges();

    Graph copy();	
    double size();	
    double degree(Node node);
    double degree(int nodeId);
    double edgeWeight(Node node1, Node node2);
    double edgeWeight(int nodeId1, int nodeId2); 
}
