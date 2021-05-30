package datastructure.core.graph.classinterface;

import datastructure.core.DynamicAttributable;

/**
 *
 * @author aabnar
 */
public interface IGraphElement extends DynamicAttributable {

    /**
     *
     * @return
     */
    public int getId ();

    /**
     *
     * @param pintId
     */
    public void setId (int pintId);
    
    /**
     *
     * @return
     */
    public double getWeight();

    /**
     *
     * @param pdblWeight
     */
    public void setWeight(double pdblWeight);
}
