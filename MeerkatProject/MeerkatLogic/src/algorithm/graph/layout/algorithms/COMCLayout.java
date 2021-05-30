package algorithm.graph.layout.algorithms;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.LinkedList;

import config.MeerkatSystem;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.impl.DynamicGraph;
import datastructure.core.graph.impl.Edge;
import datastructure.core.graph.impl.StaticGraph;
import datastructure.core.graph.impl.Vertex;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class Name      : COMCLayout
 * Created Date    : from the last versions of Meerkat
 * Description     : COMC is the layout that puts each community in 
 *                      a separate part of the scene
 * Version         : 2.0
 * @author         : Afra
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-03-16      Afra            Changed the constructor to get Parameters 
 *                                  in an array of strings. 
 * 
 * @author aabnar
 * @param <V> Type of vertices
 * @param <E> Type of edges
 */
public class COMCLayout<V extends IVertex, E extends IEdge<V>>
        extends Layout<V, E> {

    /**
     *
     */
    public static final String STR_NAME = "COMC Layout";

    HashMap<V, List<V>> hmpV2LstOfComs
            = new HashMap<>();
    
    private final Type type = Type.COMMUNITY;

    /**
     *  Method Name     : COMCLayout (Constructor)
     *  Created Date    : from previous versions of Meerkat
     *  Description     : 
     *  Version         : 2.0
     *  @author         : Talat
     * 
     * 
     * @param pIGraph
     *          an instance of IDynamicGraph
     * @param tf
     *          TimeFrame 
     * @param parameters
     *          String[]
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-03-16      Afra            Added the String[] parameters to include
     *                                  all parameters required for the algorithm.
     *  
     * 
    */
    public COMCLayout(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String[] parameters) {
        super(pIGraph, tf, parameters);
    }

    /**
     *
     */
    @Override
    public void run() {
        // Layout a graph with the communities represented as vertices.
        IDynamicGraph<V, E> representative = createRepresentativeGraph();

        
        Set<V> setNoComVertices = getVerticesWithoutCommunities();
        for (V vertex : setNoComVertices) {
            if(!running){
                break;
            }
            V outlierV = (V) new Vertex(1.0 / dynaGraph.getVertices(tf).size());
            representative.addVertex(outlierV, tf);
            hmpV2LstOfComs.put(outlierV, new LinkedList<>());
            hmpV2LstOfComs.get(outlierV).add(vertex);
        }
        
        

        String[] parameters = new String[1];
        parameters[0] = "COMC";
        if(!running){
                return;
        }
        Layout<V,E> layout = new CircleLayout<> (representative, tf, null);
        Thread th = new Thread(layout);
        th.start();
        try {
            th.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(COMCLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!running){
                return;
        }
//        for (V v : representative.getVertices(tf)) {
//            System.out.println("COMCLayout.run() : vid (x,y) = " + 
//                    v.getId() + "("+getX(v)+","+getY(v)+")");
//        }
        
        layout = new ModifiedFRLayout<>(representative, tf, parameters);
        layout.run();
        if(!running){
            return;
        }
        // Layout each vertex in the community based on the representative
        // graph.
        
//        System.out.println("COMCLayout.run(): number of communities = " + hmpV2LstOfComs.keySet().size() );
        
        double xmin = Double.MAX_VALUE;
        double xmax = Double.MIN_VALUE;

        double ymin = Double.MAX_VALUE;
        double ymax = Double.MIN_VALUE;

        Map<V,double[]> mapVMap = new HashMap<>();
        for (V com : hmpV2LstOfComs.keySet()) {
            if(!running){
                return;
            }
            // Determine the bounding circle from the representative graph.
            Point2D center;

            double x = Double.parseDouble(com.getSystemAttributer()
                    .getAttributeValue(MeerkatSystem.X, tf));

            double y = Double.parseDouble(com.getSystemAttributer()
                    .getAttributeValue(MeerkatSystem.Y, tf));
            
//            System.out.println("COMCLayout.run(): Represantative Graph: com " + com.getId() + " : (" + x + "," +y+")" );

            center = new Point2D.Double(x * getWidth(), y * getHeight());

            double radius = com.getWeight() * Math.min(getWidth(), getHeight());

            double theta = 0;

            double angleIncrement
                    = (2 * Math.PI) / hmpV2LstOfComs.get(com).size();

            
            for (V vMember : hmpV2LstOfComs.get(com)) {
                // Position each vertex in the community within the circle.
                Point2D pos = new Point2D.Double(center.getX() + radius
                        * Math.cos(theta), center.getY() + radius
                        * Math.sin(theta));
                
                double newLocation[] = new double[2];
                newLocation[0] = pos.getX() / getWidth();
                newLocation[1] = pos.getY() / getHeight();
                
                if (newLocation[0] < xmin) {
                    xmin = newLocation[0];
                } else if (newLocation[0] > xmax) {
                    xmax = newLocation[0];
                }
                
                if (newLocation[1] < ymin) {
                    ymin = newLocation[1];
                } else if (newLocation[1] > ymax) {
                    ymax = newLocation[1];
                }
                mapVMap.put(vMember, newLocation);
//                vMember.getSystemAttributer().addAttributeValue(
//                        MeerkatSystem.X, + "", new Date(), tf);
//                vMember.getSystemAttributer().addAttributeValue(
//                        MeerkatSystem.Y,  + "", new Date(), tf);

                theta += angleIncrement;
            }

        }
        
        // normalizing between 0 and 1
        double scale = Math.max(xmax - xmin, ymax - ymin);
        if (scale > 0) {
            for (V v : dynaGraph.getVertices(tf)) {
                if(!running){
                    return;
                }
                //checking if a vertex has a community, then normalize
                if(mapVMap.containsKey(v)){
                    double newX = (mapVMap.get(v)[0] - xmin) / scale;
                    double newY = (mapVMap.get(v)[1] - ymin) / scale;
                    setLocation(v, newX, newY);
                }
            }
        }
        
        blnDone = true;
    }

    private Set<V> getVerticesWithoutCommunities() {
        Set<V> lltNoCommunity = new HashSet<>();
        for (V v : dynaGraph.getVertices(tf)) {
            if (v.getSystemAttributer().getAttributeNames().contains(MeerkatSystem.COMMUNITY)) {
                continue;
            }
            lltNoCommunity.add(v);
        }
        return lltNoCommunity;
    }

    @Override
    public String toString() {
        return STR_NAME;
    }

    private IDynamicGraph<V, E> createRepresentativeGraph() {
        assert (dynaGraph != null);

        // Add the representative vertices.
        HashMap<String, LinkedList<V>> communities
                = new HashMap<>();

        for (V v : dynaGraph.getVertices(tf)) {

            if (v.getSystemAttributer().getAttributeNames()
                    .contains(MeerkatSystem.COMMUNITY)) {
                String comId
                        = v.getSystemAttributer().getAttributeValue(
                                MeerkatSystem.COMMUNITY,
                                tf);
                if (comId != null) {
                    if (!communities.keySet().contains(comId)) {
                        communities.put(comId, new LinkedList<>());
                    }
                    communities.get(comId).add(v);
                }
            }
        }

        IDynamicGraph<V, E> representative = new DynamicGraph<>(communities.size(), 0);
        representative.addGraph(tf, new StaticGraph<>(communities.size(),0));

        for (LinkedList<V> community : communities.values()) {

//            double avgRadius = getAverageRadius(community);
//
//            double circumference = avgRadius * community.size();

            // System.out.println("circumference is " + circumference);
            double cRadius = community.size() / (2 * dynaGraph.getVertices(tf).size() + 0.0);

            V comV = (V) new Vertex(cRadius);

            for (V v : community) {
                if (!hmpV2LstOfComs.containsKey(comV)) {
                    hmpV2LstOfComs.put(comV, new LinkedList<>());
                }

                hmpV2LstOfComs.get(comV).add(v);
            }
            

            representative.addVertex(comV, tf);
        }
        
        /* adding edges */
        for (int eid : dynaGraph.getGraph(tf).getAllEdgeIds()) {
            E e = dynaGraph.getEdge(eid);
            V v = e.getSource();
            V u = e.getDestination();
            if (hmpV2LstOfComs.containsKey(v) && hmpV2LstOfComs.containsKey(u)){
                for (V comV : hmpV2LstOfComs.get(v)) {
                    for (V comU : hmpV2LstOfComs.get(u)) {
                        E newE = (E) new Edge<>(comV, comU, false);
                        representative.addEdge(newE, tf);
                    }
                }
            }
        }

//        System.out.println("ComCLayout.createRepresentativeGraph() : " + 
//                representative.getVertices(tf).size() + "," + 
//                representative.getEdges(tf).size());
        return representative;
    }

    /**
     * This method returns the average cRadius of the vertices in a community.
     * Since the DEFAULT_VERTEX_RADIUS is a UI dependent, we would not define
     * that in back-end.
     *
     * @param pllCommunity - list of vertices belonging to the same community.
     * @return - average cRadius of the vertices.
     */
    private double getAverageRadius(LinkedList<V> pllCommunity) {
        double average = 0.0;
        for (V v : pllCommunity) {
            average += v.getWeight();
        }
        average /= pllCommunity.size();

        return average;
        // return average * VertexShapeTransformer.DEFAULT_VERTEX_RADIUS;
    }

    @Override
    public Type getType() {
        return type;
    }

    private double getWidth() {
        return 1000;
    }
    
    private double getHeight() {
        return 1000;
    }
    
}
