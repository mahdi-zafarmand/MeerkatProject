package algorithm.graph.communitymining.louvain;

import java.util.ArrayList;
import static java.util.Arrays.sort;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Shiva Zamani
 */
public class Graph {
    List<Node> nodes;
    //TODO speed it up. by passing the no of vertices. pass that in arraylist constructor
    public Graph() {
        this.nodes = new ArrayList<>();
    }
    
    //TODO speed it up. This is O(n) right now. do it in O(1)
    public Node add_node(int id){
        for(Node n:nodes){
            if(n.id==id)
                return n;
        }
        Node n=new Node(id);
        nodes.add(n);
        return n;
    }
    
    public void add_edge(Node n1, Node n2){
        //check if the edge exists
        if(n1.connections.containsKey(n2.id)){
            return;
        }
        
        n1.connections.put(n2.id,1.0);
        if(n2!=n1){
            n2.connections.put(n1.id,1.0);
        }
    }
    
    public void add_edge(Node n1, Node n2, double weight ){

 
        n1.connections.put(n2.id, weight);
        if(n2!=n1){
            n2.connections.put(n1.id, weight);
        }
        
    }
    
    public void print_Edges(){
        for (Node n:nodes){
            for(int e:n.connections.keySet()){
                System.out.println(n.id+" "+e+" "+n.connections.get(e));
            }
        }
    }
    
    public ArrayList<Edge> edge_iter(){
        /*
        Set<Edge> EdgeSet = new TreeSet<Edge>();
        for(Node node:nodes){
            for(Edge e: node.connections){
                EdgeSet.add(e);
            }
        }
        ArrayList<Edge> Edges = new ArrayList(EdgeSet);
        */
        ArrayList<Edge> Edges = new ArrayList<>();
        
        for(Node node1:nodes){
            Edge newE;
            for(int node2:node1.connections.keySet()){
                if(node1.id <= node2){
                    newE= new Edge(node1, getNode(node2), node1.connections.get(node2) );
                    Edges.add(newE);
                }
            }
        }
        
        return Edges;
    }
    
    public Node getNode(int id){
        for(Node n: nodes){
            if(n.id==id)
                return n;
        }
        return null;
    }
    
    public Graph copy(){
        Graph gcopy=new Graph();
        for(Node n:nodes){
            gcopy.add_node(n.id);
        }
        for(Node n:nodes){
            gcopy.getNode(n.id).connections=new HashMap<>(n.connections);
        }
        return gcopy;
    }
    
    public double size(){
        double total_weight=0,weight;
        for(Node n:nodes){
            for(int e:n.connections.keySet()){
                weight=n.connections.get(e);
                if(n.id != e){
                    total_weight+= weight!=0 ? (weight) : 1;
                }
                else{
                    total_weight+= 2 *(weight!=0 ? (weight) : 1);
                }
            }
        }
        total_weight/=2;
        return total_weight;
    }
    
    public double degree(Node n){
        double total_degree=0, weight;
        for(int e:n.connections.keySet()){
            weight = weight=n.connections.get(e);
            if(n.id!= e){
                total_degree+=weight!=0 ? (weight) : 1;
            }
            else{
                total_degree+= 2 *(weight!=0 ? (weight) : 1);
            }
        }
        return total_degree;
    }
    
    public double get_edge_data(int id1, int id2){
        Node node1;
        node1 = getNode(id1);
        if(node1.connections.containsKey(id2)){
            return node1.connections.get(id2);
        }
        return 0;
    }
    
    public List<Node> sorted(){
        List<Node> sortedNodes=new ArrayList<>();
        int [] nodeIds=new int[nodes.size()];
        HashMap<Integer,Node> id2node=new HashMap<>();
        int i=0;
        for(Node n:nodes){
            nodeIds[i]=n.id;
            id2node.put(n.id, n);
            i++;
        }
        sort(nodeIds);
        for(int j=0;j<nodeIds.length;j++){
            sortedNodes.add(id2node.get(nodeIds[j]));
        }
        return sortedNodes;
    }
    
    public int number_of_edges(){
        int num_edges=0;
        for(Node n:nodes){
            num_edges+= n.connections.size();
        }
        num_edges = num_edges / 2;
        
        return num_edges;
    }
    
}
