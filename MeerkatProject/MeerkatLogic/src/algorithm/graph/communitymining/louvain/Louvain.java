
package algorithm.graph.communitymining.louvain;


import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IGraph;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import static java.lang.System.exit;

/**
 *
 * @author Shiva Zamani
 * Date : 08/08/2017
 * Paper : Fast unfolding of communities in large networks, http://iopscience.iop.org/article/10.1088/1742-5468/2008/10/P10008/meta
 * Other links : 
 */

public class Louvain {
    
    static int __PASS_MAX = -1;
    static double __MIN = 0.0000001;
    
    
    public static Graph induced_graph(HashMap<Integer, Integer> partition, Graph graph) {
        Graph ret = new Graph();
        for(int comId:partition.values()){
            ret.add_node(comId);
        }
        int node1, node2, com1, com2;
        double edge_weight, w_prec;
        ArrayList<Edge> OldEdgeList=graph.edge_iter();
        
        for(Edge E:OldEdgeList){
            edge_weight = E.weight;
            node1 = E.start.id;
            node2 = E.end.id;
            com1 = partition.get(node1);
            com2 = partition.get(node2);
            w_prec = ret.get_edge_data(com1, com2);
            ret.add_edge(ret.getNode(com1), ret.getNode(com2), w_prec + edge_weight);
        }
        
        return ret;
    }

    public static HashMap<Integer,Double> __neighcom(Node node, Graph graph, Status status) {
        HashMap<Integer,Double> weights = new HashMap<>();
        double datas, edge_weight, W;
        int neighborcom;
        Set<Integer> neighbors= node.neighbors().keySet();
        for(int neighbor: neighbors){
            datas = node.neighbors().get(neighbor);
            if(neighbor!= node.id){
                edge_weight = datas;
                neighborcom = status.node2com.get(neighbor);
                W=0;
                if(weights.containsKey(neighborcom)){
                    W = weights.get(neighborcom);
                }
                weights.put(neighborcom, W + edge_weight);
            }
        
        }
        return weights;
    }

    public static void __remove(int node, int com, double weight, Status status) {
        double degree = status.degrees.get(com)- status.gdegrees.get(node);
        status.degrees.put(com, degree);
        double internal = status.internals.get(com) - weight - status.loops.get(node);
        status.internals.put(com, internal);
        status.node2com.put(node, -1);
    }

    public static void __insert(int node, int com, double weight, Status status) {
        status.node2com.put(node, com);
        double degree = (status.degrees.get(com) + status.gdegrees.get(node));
        status.degrees.put(com, degree);
        double internal = status.internals.get(com) + weight + status.loops.get(node);
        status.internals.put(com, internal);
    }
    
    public static HashMap<Integer, Integer> __renumber(HashMap<Integer, Integer> dictionary) {
        int count = 0;
        HashMap<Integer, Integer> ret= new HashMap<>(dictionary);
        HashMap<Integer, Integer> new_values=new HashMap<>();
        int value, new_value;
        
        for(int key: dictionary.keySet()){
            value = dictionary.get(key);
            if(new_values.containsKey(value)){
                new_value = new_values.get(value);
            }
            else{
                new_value = -1;
            }
            if(new_value == -1){
                new_values.put(value, count);
                new_value = count;
                count += 1;
            } 
            ret.put(key, new_value);
        }
        return ret;
    }
    
    public static double __modularity(Status status) {
        double links, result, in_degree, degree;
        links = status.total_weight;
        result = 0;
        HashSet<Integer> Unique_communities = new HashSet<Integer> (status.node2com.values());
        for(int community: Unique_communities){
            in_degree = 0;
            if(status.internals.containsKey(community)){
                in_degree = status.internals.get(community);
            }
            degree = 0;
            if(status.degrees.containsKey(community)){
                degree = status.degrees.get(community);
            }
            
            if(links > 0){
                result += (in_degree / links) - (Math.pow((degree / (2 * links)), 2));
            }
        }    
        return result;
    }
    
    
    public static void __one_level(Graph graph, Status status, BooleanProperty isThreadRunningProperty) {
        boolean modified = true;
        int nb_pass_done = 0;
        double cur_mod,new_mod, degc_totw, best_increase, dnc, incr, gdeg, com_w;
        HashMap<Integer,Double> neigh_communities;
        cur_mod = __modularity(status);
        new_mod = cur_mod;
        int com_node, best_com;
        // sort to test with python code 
        //List<Node> GraphNodes = graph.sorted();
        List<Node> GraphNodes =graph.nodes;
                
        while (modified && nb_pass_done != __PASS_MAX){
            cur_mod = new_mod;
            modified = false;
            nb_pass_done ++;
            
            for(Node node: GraphNodes){
                //put thread stopping condition here
                if(isThreadRunningProperty.getValue()==false){
                    return;
                }
                com_node = status.node2com.get(node.id);
                gdeg=0;
                if(status.gdegrees.containsKey(node.id)){
                    gdeg = status.gdegrees.get(node.id);
                }
                
                degc_totw = gdeg / (status.total_weight * 2);
                /*
                */
                neigh_communities = __neighcom(node, graph, status);
                com_w = 0;
                if(neigh_communities.containsKey(com_node)){
                    com_w = neigh_communities.get(com_node);
                }
                __remove(node.id, com_node,com_w, status);
                best_com = com_node;
                best_increase = 0;
                //sort neigh communities
                /*
                ArrayList<Integer> intALKeys = new ArrayList<Integer>(neigh_communities.keySet());
                int [] neigh_communitiesArr= new int[intALKeys.size()];
                for (int i=0; i < intALKeys.size(); i++){
                    neigh_communitiesArr[i] = intALKeys.get(i);
                }
                
                sort(neigh_communitiesArr);
                */
                Set<Integer> NeighComs = neigh_communities.keySet();
                for(int com: NeighComs){
                    dnc = neigh_communities.get(com);
                    incr = dnc - status.degrees.get(com) * degc_totw;
                    if(incr > best_increase){
                        best_increase = incr;
                        best_com = com;
                    }
                       
                }
                com_w = 0;
                if(neigh_communities.containsKey(best_com)){
                    com_w = neigh_communities.get(best_com);
                }
                __insert(node.id, best_com, com_w, status); 
                if(best_com != com_node){
                    modified = true;
                } 
            }
            new_mod = __modularity(status);
            if(new_mod - cur_mod < __MIN){
                break;
            }
        }        
    }
    
        
    public static HashMap<Integer, Integer> partition_at_level(ArrayList<HashMap<Integer, Integer>> dendo, int level) {
        HashMap<Integer,Integer> partition = new HashMap<>(dendo.get(0));
        int community;
        for (int i = 1; i <= level ; i++) {
            for(int node: partition.keySet()){
                community = partition.get(node);
                partition.put(node, dendo.get(i).get(community));
            }
        }
        return partition;
    }
    
    public static ArrayList<HashMap<Integer, Integer>> generate_dendrogram(Graph net, BooleanProperty isThreadRunningProperty) {
        ArrayList<HashMap<Integer, Integer>> status_list = new ArrayList<>();
        
        //special case, when there is no link, the best partition is everyone in its community
        if(net.number_of_edges()==0){
            HashMap<Integer,Integer> partition = new HashMap<>();
            for (Node node: net.nodes){
                partition.put(node.id, node.id);
            }
            status_list.add(partition);
            return status_list;
        }
        
        Graph current_graph = net.copy();
        Status status;
        status = new Status();
        status.init(net);
       
        __one_level(current_graph, status, isThreadRunningProperty);
        //TODO put thread stopping condition here
        if(isThreadRunningProperty.getValue()==false){
                    return new ArrayList<>();
        }
        double new_mod,mod;
        new_mod = __modularity(status);
        HashMap<Integer,Integer> partition = new HashMap<>();
        partition = __renumber(status.node2com);
        status_list.add(partition);
        mod = new_mod;
        current_graph = induced_graph(partition, current_graph);
        
        status.init(current_graph);
        //TODO put thread flag in while loop condition
        if(isThreadRunningProperty.getValue()==false){
                    return new ArrayList<>();
        }
        while(isThreadRunningProperty.getValue()){
            
            __one_level(current_graph, status, isThreadRunningProperty);
            new_mod = __modularity(status);
            if(new_mod - mod < __MIN){
                break;
            }
            partition = __renumber(status.node2com);
            status_list.add(partition);
            mod = new_mod;
            current_graph = induced_graph(partition, current_graph);
            /*
            for(Node n:current_graph.nodes){
                for(Edge e:n.connections){
                    System.out.println(e.start.id+" "+e.end.id+" "+e.weight);
                }
            }
            
            System.out.println("priiiiiiiiiiiiiiiiiiiinting edges");
            current_graph.print_Edges();
            */
            status.init(current_graph);
        
        }
        /*
        for (int l =0; l<status_list.size();l++){
            System.out.println(status_list.get(l));
            System.out.println("-------------------------------------");
        }
        */
        return status_list;
    }
    
    public static HashMap<Integer, Integer> best_partition(Graph net, BooleanProperty isThreadRunningProperty){
        
        // dendo: list of partitions
        ArrayList<HashMap<Integer,Integer>> dendo = new ArrayList<>();
        HashMap<Integer, Integer> partition = new HashMap<>();
        dendo = generate_dendrogram(net, isThreadRunningProperty);
        if(isThreadRunningProperty.getValue()==false){
                    return new HashMap<>();
        }
        partition=partition_at_level(dendo, dendo.size() - 1);
        
        /*
        for(Node n:net.sorted()){
            System.out.println(n.id+" "+partition.get(n.id));
        }
        */
        
        return partition;
    
    }

    public static Graph loadDataset(String DatasetPath){
        Graph net =new Graph();
        //read the nodes and edges
        try {    
            BufferedReader br = new BufferedReader(new FileReader(DatasetPath+".edgeList"));
            String line = br.readLine();
            int v1,v2;
            Node n1,n2;
            
            while (line != null) {
                v1=Integer.parseInt(line.split(" ")[0]);
                v2=Integer.parseInt(line.split(" ")[1]);
                
                //add nodes to graph
                n1=net.add_node(v1);
                n2=net.add_node(v2);
                net.add_edge(n1, n2);
                
                line = br.readLine();
            }
            br.close();
            
        } catch (Exception ex) {
            Logger.getLogger(Louvain.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error: file "  + DatasetPath + ".edgeList not found! ");
            exit(-1);
        }
        
        return net;
    }
    /**
     * @author Abhi on 08/08/2017
     * @param dynaGraph
     * @param tf
     * @param isThreadRunningProperty
     * @return 
     */
    public static HashMap<Integer, Integer> runLouvain(IDynamicGraph dynaGraph, TimeFrame tf, BooleanProperty isThreadRunningProperty) {
        
        /*
        1. convert graph format
        */
        Graph louvainGraph = new Graph();
        IGraph meerkatIGraph = dynaGraph.getGraph(tf);
        
        
        //add all nodes
        Map<Integer, Node> mapNodes = new HashMap<>();
        for( Object vid : meerkatIGraph.getAllVertexIds()){
            Node newNode = louvainGraph.add_node((Integer)vid);
            mapNodes.put((Integer)vid, newNode);   
        }
        // add all edges
        for( Object vid : meerkatIGraph.getAllVertexIds()){
            //get all edges of a vertex
             List<IEdge> listEdges = dynaGraph.getEdges(meerkatIGraph.getVertex((Integer)vid), tf);
             for(IEdge edge : listEdges){
                 int vertexFrom = edge.getSource().getId();
                 int vertexTo = edge.getDestination().getId();
                 Node sourceNode = mapNodes.get(vertexFrom);
                 Node destinationNode = mapNodes.get(vertexTo);
                 if(sourceNode!=null && destinationNode!=null){
                     louvainGraph.add_edge(sourceNode, destinationNode);
                 }   
             }
        }
        
        if(isThreadRunningProperty.getValue()==false){
                    return new HashMap<>();
        }
        
        /*
        2. Run louvain on converted graph
        */

               
        return best_partition(louvainGraph, isThreadRunningProperty);
    }
}
