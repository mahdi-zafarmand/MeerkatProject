/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph;

import algorithm.graph.layout.algorithms.Layout;
import algorithm.graph.layout.algorithms.RandomLayout;
import config.MeerkatSystem;
import datastructure.core.IDGenerator;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IStaticGraph;
import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.graph.impl.Edge;
import datastructure.core.graph.impl.StaticGraph;
import datastructure.core.graph.impl.Vertex;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections15.list.LazyList;

/**
 *
 * @author aabnar
 */
public class GraphUtil {
    /*******************************    METHODS     ****************************/
    /**
     *  Method Name     : getDensity
     *  Created Date    : 2015-09-01
     *  Description     : Returns the Density of the graph
     *  Version         : 1.0
     *  @author         : Talat
     * @param pIGraph
     * @param tf
     * 
     *  @return double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static double getDensity(IDynamicGraph pIGraph, TimeFrame tf) {
        return ((2 * pIGraph.getGraph(tf).getEdgeCount())/ 
                (pIGraph.getGraph(tf).getVertexCount() * (pIGraph.getGraph(tf).getVertexCount() - 1)));
    }
    
    
    /**
     *  Method Name     : getAvgNumberOfConnections
     *  Created Date    : 2015-09-01
     *  Description     : Returns the Average Number of Connections in the graph (Total Degree for each edge)
     *  Version         : 1.0
     *  @author         : Talat
     * @param pIGraph
     * @param tf
     * 
     *  @return double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static double getAvgNumberOfConnections(IDynamicGraph pIGraph, TimeFrame tf) {
        return pIGraph.getGraph(tf).getEdgeCount() / pIGraph.getGraph(tf).getVertexCount() * 2 ;
    }
    
    /**
     *  Method Name     : getAvgClusteringCoefficient
     *  Created Date    : 2015-09-01
     *  Description     : Returns the Average Clustering Co-efficient of the graph
     *  Version         : 1.0
     *  @author         : Talat
     *  References      : https://en.wikipedia.org/wiki/Clustering_coefficient
     * 
     *  @return double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static double getAvgClusteringCoefficient() {
        double avgCC = -1.0d;
        // TODO : Implementatation of Clustering Co0efficient 
        // Details can be found on : https://en.wikipedia.org/wiki/Clustering_coefficient
        return avgCC;
    }
    
    /**
     *  Method Name     : getAvgAssortativityCoefficient
     *  Created Date    : 2015-09-01
     *  Description     : Returns the Average Assortavity of the Graph
     *  Version         : 1.0
     *  @author         : Talat
     * @param pIGraph
     * @param tf
     * 
     *  @return double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static double getAvgAssortativityCoefficient(
                                    IDynamicGraph<IVertex,IEdge<IVertex>> pIGraph, TimeFrame tf) {

        HashMap<IVertex, Integer> hmapVertexDegrees = new HashMap<>();
        for (int vtxCurrentId : pIGraph.getGraph(tf).getAllVertexIds()) {
            IVertex vtxCurrent = pIGraph.getVertex(vtxCurrentId);
            hmapVertexDegrees.put(vtxCurrent, pIGraph.getDegree(vtxCurrent, tf));
        }
        
        double dblX = 0.0 ;
        double dblY = 0.0 ;
        double dblDensity = 0.0 ;
        for (int edgeCurrentId : pIGraph.getGraph(tf).getAllEdgeIds()){
            IEdge edgeCurrent = pIGraph.getEdge(edgeCurrentId);
            double dblSourceDegree = hmapVertexDegrees.get(edgeCurrent.getSource());
            double dblDestinationDegree = hmapVertexDegrees.get(edgeCurrent.getDestination());
            
            dblX += dblSourceDegree * dblDestinationDegree ;
            dblY += dblSourceDegree + dblDestinationDegree ;
            dblDensity += (dblSourceDegree * dblSourceDegree) + (dblDestinationDegree * dblDestinationDegree) ;
        }
        
        // Average the co-eficeint
        dblX /= pIGraph.getGraph(tf).getEdgeCount() ;
        dblDensity /= pIGraph.getGraph(tf).getEdgeCount() ;
        dblY = Math.pow(dblY / (2 * pIGraph.getGraph(tf).getEdgeCount()), 2.0) ;
        
        return (dblX - dblY) / (dblDensity - dblY) ;
    }
    
    /**
     *  Method Name     : getAvgShortestPathDistance
     *  Created Date    : 2015-09-01
     *  Description     : Returns the Average Shortest Path Distance
     *  Version         : 1.0
     *  @author         : Talat
     * @param pIGraph
     * @param tf
     * 
     *  @return double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static double getAvgShortestPathDistance(IDynamicGraph pIGraph, TimeFrame tf) {
        double avgSPD = -1.0d;
        //TODO
        return avgSPD;        
    }
    
    public static double getDiameter(Collection<Double> plstDistances) {
        double max = Double.MIN_VALUE;
        for (double dist : plstDistances) {
            if (dist > max) {
                max = dist;
            }
        }
        return max;
    }
    
    public static Set<IVertex> getNeighborAtLevelith(IDynamicGraph<IVertex,IEdge<IVertex>> pdynaGraph,
            IVertex v, TimeFrame tf, int pint_i_th) {
        int intLevelCounter = 0;
        Set<IVertex> lstneighbors = new HashSet<>();
        lstneighbors.addAll(pdynaGraph.getNeighbors(v, tf));
        intLevelCounter++;
        
        Set<IVertex> lstalter = new HashSet<>();
        
        while (intLevelCounter < pint_i_th) {
            
            for (IVertex neigh : lstneighbors) {
                List<IVertex> lstTemp = pdynaGraph.getNeighbors(neigh, tf);
                for (IVertex newNeigh : lstTemp) {
                    if (!lstneighbors.contains(newNeigh)) {
                        lstalter.add(newNeigh);
                    }
                }
            }
            intLevelCounter++;
            
            
            lstneighbors = lstalter;
            lstalter = new HashSet<>();
        }
        if (lstneighbors.contains(v)) {
            lstneighbors.remove(v);
        }
        return lstneighbors;
        
    }
    
    
    public static void extractASubGraph(int pintProjectID, int pintGraphID, int pintTimeFrameIndex, List<Integer> plistSelectedVerticesIds, List<Integer> plistSelectedEdgesIds) {
        
        //MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
    }

    public static void makeDynaGraphold(IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph, IDynamicGraph<IVertex, IEdge<IVertex>> dynaSubGraph, List<Integer> plistSelectedVerticesIds, List<Integer> plistSelectedEdgesIds) {
        
        /*
        For each time frame tf:
            get all vertices of dynagraph
            get all selected vertices and clone the list
            find intersection of these 2 - to get vertices_current_time_frame
            add vertices_current_time_frame to dynaSubgraph under time frame tf
        */
        System.out.println(" -------- Parent Graph title : -------"+ dynaGraph.getGraphTitle());
        for(TimeFrame tf : dynaGraph.getAllTimeFrames()){
            
            System.out.println("   GraphUtil: timeframe : "+ tf.getTimeFrameName());
            Map<Integer, IVertex> mapOldIdtoNewVertexCopy = new HashMap<>();
            List<Integer> listVerticesStatGraph = dynaGraph.getGraph(tf).getAllVertexIds();
            List<Integer> listVerticesSelected = new ArrayList<>();
            for(int i : plistSelectedVerticesIds){
                listVerticesSelected.add(i);
            }
            
            listVerticesSelected.retainAll(listVerticesStatGraph);
            
            IStaticGraph<IVertex,IEdge<IVertex>> istatgraph 
                = new StaticGraph<>(listVerticesSelected.size() , 0);
            TimeFrame tfCopy  = new TimeFrame(tf.getTimeFrameName());
            dynaSubGraph.addGraph(tfCopy, istatgraph);
            
            
            
            for(int v : listVerticesSelected){
                IVertex vold = dynaGraph.getVertex(v);
           
                System.out.println("old vertex id = " + vold.getId());
                Vertex vnew = new Vertex(vold, -1, tf, tfCopy);
                //System.out.println("Meerkat Logic...GraphUtil: " +vnew.toString());
                dynaSubGraph.addVertex(vnew, tfCopy);
                mapOldIdtoNewVertexCopy.put(v, vnew);
                
            }
            
            // process edges
            //System.out.println("plistSelectedEdgesIds size : " + plistSelectedEdgesIds.size());
            List<Integer> listEdgesStatGraph = dynaGraph.getGraph(tf).getAllEdgeIds();
            List<Integer> listEdgesSelected = new ArrayList<>();
            for(int i : plistSelectedEdgesIds){
                listEdgesSelected.add(i);
            }
            //System.out.println("listEdgesStatGraph size : " + listEdgesStatGraph.size());
            listEdgesSelected.retainAll(listEdgesStatGraph);
            //System.err.println("GraphUtil : listEdgesSelected size : " + listEdgesSelected.size());
            for(int e : listEdgesSelected){
                IEdge eold = dynaGraph.getEdge(e);
                int vertexFromId = eold.getSource().getId();
                int vertexToId = eold.getDestination().getId();
                // if both verttics present in the selected list of vertices, then add the edge to the statgraph
                if(listVerticesSelected.contains(vertexFromId) && listVerticesSelected.contains(vertexToId)){
                    // make a deep copy and add deep copy edge to statgraph 
                    IVertex vertexFrom = mapOldIdtoNewVertexCopy.get(vertexFromId);
                    IVertex vertexTo = mapOldIdtoNewVertexCopy.get(vertexToId);
                    Edge newEdge = new Edge(vertexFrom, vertexTo, eold, tf, tfCopy);
                    newEdge.setId(-1);
                    dynaSubGraph.addEdge(newEdge, tfCopy);
                }
            
            }
            

        }
        //System.out.println("GraphUtil.makeASubGraph() layout timeframes -----Doing layout system x----" + 
        //        dynaSubGraph.getAllVertices().get(0).getSystemAttributer().getAttributeValue(MeerkatSystem.X, dynaSubGraph.getAllTimeFrames().get(0)));
        for(TimeFrame tf : dynaSubGraph.getAllTimeFrames()){
            System.out.println("GraphUtil.makeASubGraph():: tf, noOfVertices = " + tf.getTimeFrameName() + ", " + dynaSubGraph.getVertices(tf).size());
        }
        
        
        try{
        if (!((IVertex)dynaSubGraph.getAllVertices().get(0)).getSystemAttributer()
                        .getAttributeNames().contains(MeerkatSystem.X)) {
                    System.out.println("GraphUtil.makeASubGraph() layout timeframes -----Doing layout ----");
                    for (TimeFrame tf : dynaSubGraph.getAllTimeFrames()) {
                        System.out.println("GraphUtil.makeASubGraph() layout timeframes --- doing layout --timeframe : "+tf.getTimeFrameName());
                        Layout layAlg = new RandomLayout(dynaSubGraph, tf, null);
                        Thread th = new Thread(layAlg);
                        th.run();
                        th.join();
                    }
                }
        } catch (Exception ex) {
            System.out.println("GraphUtil.makeASubGraph() layout timeframes: EXCEPTION: ");
            ex.printStackTrace();
        }
        
        // set graph title and filename and filepaths
                     
        //getGraph(igraph.getId()).setGraphFile(pstrPath2FileName); // Set the file name
        //getGraph(igraph.getId()).setGraphTitle("Graph ("+igraph.getId()+")");
        //lstintGraphIDs.add(intGraphID);
        //dynaSubGraph.setGraphFile(pstrPath2FileName); // Set the file name
        //System.out.println("GraphUtil.makeASubGraph() : filename"+ dynaGraph.getGraphFile() );
        
        dynaSubGraph.setGraphTitle("Graph ("+dynaSubGraph.getId()+")");
                
        System.out.println("GraphUtil.makeASubGraph():: subGraphID "+dynaSubGraph.getId() + " timeframes : " + dynaSubGraph.getAllTimeFrames().size());
            
        
        
        
        /*
        //add vertices and edges to sub graph
        List<TimeFrame> allTimeFrames = dynaGraph.getAllTimeFrames();
        for(int vertexId :  plistSelectedVerticesIds){
            Vertex v= new Vertex(dynaGraph.getVertex(vertexId), 1, allTimeFrames);
            v.setId(vertexId);
            v.setWeight(dynaGraph.getVertex(vertexId).getWeight());
            IVertex vd = dynaGraph.getVertex(vertexId);
            
            //dynaSubGraph.addVertex(v, allTimeFrames);
            //new Vertex(dynaGraph.getVertex(vertexId), vertexId, pintTimeFrameIndex);
        */
    }
    public static void makeDynaGraph(IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph, IDynamicGraph<IVertex, IEdge<IVertex>> dynaSubGraph, List<Integer> plistSelectedVerticesIds, List<Integer> plistSelectedEdgesIds) {
        
        /*
        For each time frame tf:
            get all vertices of dynagraph
            get all selected vertices and clone the list
            find intersection of these 2 - to get vertices_current_time_frame
            add (deep copy)vertices_current_time_frame to dynaSubgraph under time frame tf
        */
        System.out.println(" -------- GraphUtil.makeDynaGraph() Extract SubGraph - Parent Graph title : -------"+ dynaGraph.getGraphTitle());
        //TODO - put size of these 2 maps in their constructor - it will speed up the process
        Map<Integer, Integer> mapOldIdtoNewVertexID = new HashMap<Integer, Integer>();
        Map<Integer, Integer> mapOldIdtoNewEdgeID = new HashMap<Integer, Integer>();
        
        for(TimeFrame tf : dynaGraph.getAllTimeFrames()){
            
            
            Map<Integer, IVertex> mapOldIdtoNewVertexCopy = new HashMap<Integer, IVertex>();
            List<Integer> listVerticesStatGraph = dynaGraph.getGraph(tf).getAllVertexIds();
            List<Integer> listVerticesSelected = new ArrayList<Integer>();
            for(int i : plistSelectedVerticesIds){
                listVerticesSelected.add(i);
            }
            
            listVerticesSelected.retainAll(listVerticesStatGraph);
            
            //TODO - put edge count as well in constructor of Static graph
            //System.out.println("   GraphUtil: timeframe : "+ tf.getTimeFrameName() + ", #listVerticesSelected = " + listVerticesSelected + ", listVerticesStatGraph" + listVerticesStatGraph);
            //DO not add a time frame where there is no vertex to be added
            //Skip this kind of time frame and go to the next one
            if(listVerticesSelected.size()>0){
                    
                System.out.println("   GraphUtil: timeframe : "+ tf.getTimeFrameName() + ", #listVerticesSelected = " + listVerticesSelected.size());
                //make new timeframe, static graph and add it to dynasubgraph - do deep copy
                IStaticGraph<IVertex,IEdge<IVertex>> istatgraph 
                    = new StaticGraph<>(listVerticesSelected.size() , 0);
                TimeFrame tfCopy  = new TimeFrame(tf.getTimeFrameName());
                dynaSubGraph.addGraph(tfCopy, istatgraph);
                
                //copy the community color map of this timeframe - do deep copy
                Map<String, String>mapCommunityColorMapdynaGraph = dynaGraph.getMapCommunityColors(tf);
                dynaSubGraph.setCommunityColorMap(tfCopy, mapCommunityColorMapdynaGraph);
                
                for(int v : listVerticesSelected){
                    IVertex vold = dynaGraph.getVertex(v);

                    IVertex vnew = null;

                    if(mapOldIdtoNewVertexID.get(v) != null){

                        int newID = mapOldIdtoNewVertexID.get(vold.getId());
                        vnew = dynaSubGraph.getVertex(newID);
                        vnew.updateAttributes(vold, tf, tfCopy);
                        dynaSubGraph.addVertex(vnew, tfCopy);


                    }else{

                        vnew = new Vertex(vold, -1, tf, tfCopy);
                        dynaSubGraph.addVertex(vnew, tfCopy);
                        mapOldIdtoNewVertexID.put(v, vnew.getId());

                    }

                    mapOldIdtoNewVertexCopy.put(v, vnew);
                }

                // process edges
                //System.out.println("plistSelectedEdgesIds size : " + plistSelectedEdgesIds.size());
                List<Integer> listEdgesStatGraph = dynaGraph.getGraph(tf).getAllEdgeIds();
                List<Integer> listEdgesSelected = new ArrayList<>();
                for(int i : plistSelectedEdgesIds){
                    listEdgesSelected.add(i);
                }
                //System.out.println("listEdgesStatGraph size : " + listEdgesStatGraph.size());
                listEdgesSelected.retainAll(listEdgesStatGraph);
                //System.err.println("GraphUtil : listEdgesSelected size : " + listEdgesSelected.size());

                for(int e : listEdgesSelected){
                    IEdge eold = dynaGraph.getEdge(e);
                    int vertexFromId = eold.getSource().getId();
                    int vertexToId = eold.getDestination().getId();
                    // if both verttics present in the selected list of vertices, then add the edge to the statgraph
                    if(listVerticesSelected.contains(vertexFromId) && listVerticesSelected.contains(vertexToId)){
                        // make a deep copy and add deep copy edge to statgraph 

                        IEdge enew = null;
                        if(mapOldIdtoNewEdgeID.get(eold.getId()) != null){

                            int newID = mapOldIdtoNewEdgeID.get(eold.getId());
                            enew = dynaSubGraph.getEdge(newID);
                            enew.updateAttributes(eold, tf, tfCopy);
                            dynaSubGraph.addEdge(enew, tfCopy);

                        }else{
                            //need dynasubgraph vertex here to make deep copy of edge
                            IVertex vertexFrom = mapOldIdtoNewVertexCopy.get(vertexFromId);
                            IVertex vertexTo = mapOldIdtoNewVertexCopy.get(vertexToId);
                            enew = new Edge(vertexFrom, vertexTo, eold, tf, tfCopy);
                            enew.setId(-1);
                            dynaSubGraph.addEdge(enew, tfCopy);
                            mapOldIdtoNewEdgeID.put(eold.getId(), enew.getId());

                        }

                    }

                }
            }
            
         
 
        }

        try{
        //if (!((IVertex)dynaSubGraph.getAllVertices().get(0)).getSystemAttributer()
        //                .getAttributeNames().contains(MeerkatSystem.X)) {
                    System.out.println("GraphUtil.makeASubGraph() layout timeframes -----Doing layout ----");
                    for (TimeFrame tf : dynaSubGraph.getAllTimeFrames()) {
                        if(dynaSubGraph.getVertexCount(tf)>0){
                            if(dynaSubGraph.getAnyVertex(tf).getSystemAttributer().getAttributeValue(MeerkatSystem.X, tf)==null){
                                System.out.println("GraphUtil.makeASubGraph() layout timeframes --- doing layout --timeframe : "+tf.getTimeFrameName());
                                Layout layAlg = new RandomLayout(dynaSubGraph, tf, null);
                                Thread th = new Thread(layAlg);
                                th.run();
                                th.join();
                            }
                        }
                    }
        //        }
        } catch (Exception ex) {
            System.out.println("GraphUtil.makeASubGraph() layout timeframes: EXCEPTION: ");
            ex.printStackTrace();
        }
        
        //Copy the GloablCommunityColor maps here of the dynaSubGraph
        Map<String, String>mapGloablCommunityColordynaGraph = dynaGraph.getMapGloablCommunityColor();
        dynaSubGraph.setGlobalCommunityColorMap(mapGloablCommunityColordynaGraph);
        
        
    }

    public static int createAndAddNewVertex(IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph, TimeFrame tf, double pdblX, double pdblY) {
        
        //create vertex
        Vertex vnew = new Vertex();
        
        
        //set its properties
        
        //set file id
        //File id of new vertex  = maximum{FileId of other vertices} + 1;
        //if no vertex present - then it becomes the first vertex - then its file_id = 1;
        int maxFileId = dynaGraph.getMaxFileId();
        //if no vertex in graph - then getMaxEdgeId return -1.
        if(maxFileId == -1){
            maxFileId = 1;
        }
        
        Integer fileIdVNew = maxFileId + 1;
        vnew.getUserAttributer().addAttribute(MeerkatSystem.FILE_ID, fileIdVNew.toString(), tf);
        
        //set its MeerkatSystem.X and Y
        if(pdblX >= 0 && pdblX < 1 && pdblY >=0 && pdblY < 1){
            vnew.getSystemAttributer().addAttributeValue(MeerkatSystem.X, pdblX+"", new Date(), tf);
            vnew.getSystemAttributer().addAttributeValue(MeerkatSystem.Y, pdblY+"", new Date(), tf);
        }else{
            vnew.getSystemAttributer().addAttributeValue(MeerkatSystem.X, 0.01+"", new Date(), tf);
            vnew.getSystemAttributer().addAttributeValue(MeerkatSystem.Y, 0.01+"", new Date(), tf);
        }
        
        
        //add this vertex to the dynamic graph
        dynaGraph.addVertex(vnew, tf);
        
        System.out.println(" \t\t\t\t\t GraphUtil.createNewVertex newVertex meerkatId : " + vnew.getId() + " : FileId = " + vnew.getUserAttributer().getAttributeValue(MeerkatSystem.FILE_ID, tf));
        //Debug - to print list of vertices in this time frame
        
        //System.out.println(" \t\t\t\t\t GraphUtil.createNewVertex vertexListInThisTimeFrame tf = " + tf.getTimeFrameName() + " : " + dynaGraph.getGraph(tf).getAllVertexIds());
        return vnew.getId();

    }
    
    public static int createAndAddNewVertexMultipleTimeFrames(IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph, List<TimeFrame> plistTimeFrames, double pdblX, double pdblY) {
        
        //create vertex
        Vertex vnew = new Vertex();
        
        
        //set its properties
        
        //set file id
        //File id of new vertex  = maximum{FileId of other vertices} + 1;
        //if no vertex present - then it becomes the first vertex - then its file_id = 1;
        int maxFileId = dynaGraph.getMaxFileId();
        //if no vertex in graph - then getMaxEdgeId return -1.
        if(maxFileId == -1){
            maxFileId = 1;
        }
        
        Integer fileIdVNew = maxFileId + 1;
        
        // add vertex to multiple times frames
        
        for(TimeFrame tf : plistTimeFrames){
        
            vnew.getUserAttributer().addAttribute(MeerkatSystem.FILE_ID, fileIdVNew.toString(), tf);

            //set its MeerkatSystem.X and Y
            if(pdblX >= 0 && pdblX < 1 && pdblY >=0 && pdblY < 1){
                vnew.getSystemAttributer().addAttributeValue(MeerkatSystem.X, pdblX+"", new Date(), tf);
                vnew.getSystemAttributer().addAttributeValue(MeerkatSystem.Y, pdblY+"", new Date(), tf);
            }else{
                vnew.getSystemAttributer().addAttributeValue(MeerkatSystem.X, 0.01+"", new Date(), tf);
                vnew.getSystemAttributer().addAttributeValue(MeerkatSystem.Y, 0.01+"", new Date(), tf);
            }
        
        
        
            //add this vertex to the dynamic graph
            dynaGraph.addVertex(vnew, tf);
            
        }

        System.out.println(" \t\t\t\t\t GraphUtil.createNewVertexMultipleTimeFrames newVertex meerkatId : " + vnew.getId() + " : FileId (from 0th timeframe selected)= " + vnew.getUserAttributer().getAttributeValue(MeerkatSystem.FILE_ID, plistTimeFrames.get(0)));
        //Debug - to print list of vertices in this time frame
        
        //System.out.println(" \t\t\t\t\t GraphUtil.createNewVertex vertexListInThisTimeFrame tf = " + tf.getTimeFrameName() + " : " + dynaGraph.getGraph(tf).getAllVertexIds());
        return vnew.getId();

    }
    
    

    private static IVertex createNewVertex(IDynamicGraph<IVertex,IEdge<IVertex>> dynaGraph, TimeFrame tf, double pdblX, double pdblY) {
        //create vertex
        Vertex vnew = new Vertex();
        //set its properties
        
        //set file id
        int maxFileId = -1;
        for(IVertex v : dynaGraph.getAllVertices()){
            //get this value for any time frame
            Object key = v.getUserAttributer().getAttributeValues(MeerkatSystem.FILE_ID).keySet().stream().findFirst().get();
            String fileId = v.getUserAttributer().getAttributeValues(MeerkatSystem.FILE_ID).get(key);
            if(fileId!=null){
                int intFileId = Integer.parseInt(fileId);
                if(intFileId >= maxFileId){
                    maxFileId = intFileId;
                }
            }
        }
        
        Integer fileIdVNew = maxFileId + 1;
        vnew.getUserAttributer().addAttribute(MeerkatSystem.FILE_ID, fileIdVNew.toString(), tf);
        //System.out.println("----------------------------------------------------------------------GraphUtil.createNewVertex" + vnew.getId());
        // add the vertex to dyna graph
        dynaGraph.addVertex(vnew, tf);
        //System.out.println("----------------------------------------------------------------------GraphUtil.createNewVertex" + vnew.getId());
        
        return vnew;
    }

    public static int createAndAddNewEdge(IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph, TimeFrame tf, int pintSourceVertexID, int pintDestinationVertexID) {
        double weight = 1.0;
        IVertex vertexFrom = dynaGraph.getVertex(pintSourceVertexID);
        IVertex vertexTo = dynaGraph.getVertex(pintDestinationVertexID);
        //check if edge(vfrom, vto) exists or not
        IEdge iedge = dynaGraph.getEdgeIfExists(pintSourceVertexID, pintDestinationVertexID);
        //if it exists, then just add it to dynagraph
        if(iedge!=null){
            System.out.println("GraphUtil.createAndAddNewEdge() Before adding to new TimeFrame - old edge found between vertices (edgeid -> v1,v2) = " + iedge.getId() + "->" + pintSourceVertexID + ", " + pintDestinationVertexID);
            dynaGraph.addEdge(iedge, tf);
            System.out.println("GraphUtil.createAndAddNewEdge() After adding to new TimeFrame - old edge found between vertices (edgeid -> v1,v2) = " + iedge.getId() + "->" + vertexFrom.getId() + ", " + vertexTo.getId());

        }else{
            //if not, create a new edge with -1 edgeid and add it to dynagraph
            iedge = new Edge<>(vertexFrom,
                        vertexTo,
                        false,
                        weight);
            //when new edge (i.e. a GraphElement object) created, its id is set to -1 by default
            dynaGraph.addEdge(iedge, tf);
        }
        
        return iedge.getId();
    
    }
    
    public static boolean edgeExists(IDynamicGraph<IVertex, IEdge<IVertex>> dynaGraph, TimeFrame tf, int pintSourceVertexID, int pintDestinationVertexID){
    
        IVertex vertexFrom = dynaGraph.getVertex(pintSourceVertexID);
        IVertex vertexTo = dynaGraph.getVertex(pintDestinationVertexID);
        IEdge iedge = dynaGraph.findEdge(vertexFrom, vertexTo, tf);
        if(iedge!=null){
            return true;
        }else{
            return false;
        }

    }
}
