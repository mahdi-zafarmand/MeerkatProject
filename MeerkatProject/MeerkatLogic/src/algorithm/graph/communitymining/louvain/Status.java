
package algorithm.graph.communitymining.louvain;

import java.util.ArrayList;
import static java.util.Arrays.sort;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Shiva Zamani
 */
public class Status {

    HashMap<Integer, Integer> node2com;
    //degrees:dictionary. key: community value: sum of the edge weights connected to nodes inside a community.
    HashMap<Integer, Double> degrees; 
    //gdegrees:dictionary. key: node id value: sum of the edge weights connected to node with node id.
    HashMap<Integer, Double> gdegrees; 
    //internals:dictionary. key: community value: sum of the self loops in community.
    HashMap<Integer, Double> internals; 
    //loops:dictionary. key: node value: sum of the self loops of a node.
    HashMap<Integer, Double> loops; 
    double total_weight=0;
    
    //status init
    public Status() {
        node2com=new HashMap<>();
        degrees=new HashMap<>(); 
        gdegrees=new HashMap<>(); 
        internals=new HashMap<>(); 
        loops=new HashMap<>();
        total_weight=0;
    }
    public void init(Graph graph){
        node2com=new HashMap<>();
        degrees=new HashMap<>(); 
        gdegrees=new HashMap<>(); 
        internals=new HashMap<>(); 
        loops=new HashMap<>();
        total_weight=graph.size();
        
        int count=0;
        double deg,looptmp;
        total_weight=graph.size();
       
        for(Node n:graph.sorted()){
            node2com.put(n.id, count);
            deg=graph.degree(n);
            degrees.put(count, deg);
            gdegrees.put(n.id, deg);
            looptmp=graph.get_edge_data(n.id, n.id);
            loops.put(n.id, looptmp);
            internals.put(count, looptmp);
            count++;
        }
    }

    
}
