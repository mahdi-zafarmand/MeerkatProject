/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.communitymining.dynamic;

import algorithm.graph.communitymining.dynamic.auxilaryDS.Snapshot;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.Vector;
import javafx.beans.property.BooleanProperty;

/**
 *
 * @author aabnar
 */
public abstract class CommunityMining<V extends IVertex, E extends IEdge<V>> {
    protected Vector<Snapshot<V, E>> snapshots;
    //DynamicGraphReader<V, E> graphReader;
    
    IDynamicGraph<V, E> dynamicGraph;
    TimeFrame tf;

    public abstract Vector<Snapshot<V, E>> findCommunities();
    public abstract Vector<Snapshot<V, E>> findCommunities(BooleanProperty isThreadRunningProperty);

    public Vector<Snapshot<V, E>> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(Vector<Snapshot<V, E>> snapshots) {
        this.snapshots = snapshots;
    }
}
