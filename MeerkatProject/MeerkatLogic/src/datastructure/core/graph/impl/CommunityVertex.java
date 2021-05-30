package datastructure.core.graph.impl;

import java.util.Set;
import datastructure.core.graph.classinterface.IVertex;

/**
 *
 * @author aabnar
 */
public class CommunityVertex extends Vertex {
    
    Set<IVertex> nodes;
    int size = 0;

    /**
     *
     */
    public static int maxSize = 1; // TODO: make it content dependent to avoid
                                    // messing multiple datasets, discussion
                                    // threads, etc...

    /**
     *
     * @param nodes
     * @param size
     */
    public CommunityVertex(Set<IVertex> nodes, int size) {
        this.size = size;
        this.nodes = nodes;
        if (size > maxSize)
                maxSize = size;
    }

    String toStringRes;

    /**
     *
     * @return
     */
    @Override
    public String toString() {// For the sake of saving time
            if (toStringRes == null)
                    toStringRes = nodes.toString();
            return toStringRes;
    }

    /**
     *
     * @return
     */
    public Set<IVertex> getNodesInACom() {
            return nodes;
    }

    /**
     *
     * @return
     */
    public int getSize() {
            return size;
    }

    /**
     *
     * @param size
     */
    public void setSize(int size) {
            this.size = size;
    }

}