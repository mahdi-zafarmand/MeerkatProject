
package algorithm.graph.communitymining.louvain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Shiva Zamani
 */
public class Node {
    public int id;
    public HashMap<Integer,Double> connections;

    public Node(int id) {
        this.id = id;
        connections=new HashMap<Integer,Double>();
    }
    
    public HashMap<Integer, Double> neighbors(){
        HashMap<Integer, Double> neighbors = new HashMap<Integer, Double>();
        Set<Integer> keys = connections.keySet();
        for(int E:keys){
            if(E!= id && !neighbors.containsKey(E)){
                neighbors.put(E, connections.get(E));
            }
        }
        return neighbors;
    }
    
    
}
