
package algorithm.graph.communitymining.louvain;

import java.util.List;

/**
 *
 * @author Shiva Zamani
 */
public class Edge implements Comparable{
    public Node start;
    public Node end;
    public double weight;

    public Edge(Node start, Node end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public int compareTo(Object o) {
        if(this==o){
            return 1;
        }
        return -1;
    }
    
}
