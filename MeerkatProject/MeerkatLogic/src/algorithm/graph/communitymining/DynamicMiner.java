package algorithm.graph.communitymining;

import algorithm.graph.communitymining.dynamic.DynamicCommunityMining;
import algorithm.Algorithm;
import config.DynamicCommunityMiningParameters;
import config.MeerkatSystem;
import config.dynamiccommunitymining.DynamicCommunityMiningAlgorithmName;
import config.dynamiccommunitymining.Method;
import config.dynamiccommunitymining.Metric;
import datastructure.core.TimeFrame;
import java.util.Properties;

import datastructure.core.graph.classinterface.*;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author 
 * paper : Community Evolution Mining in Dynamic Social Networks, http://www.sciencedirect.com/science/article/pii/S1877042811013784
 * not sure, if it is the exact implementation of this paper.
 * @param <V>
 * @param <E> 
 */
public class DynamicMiner<V extends IVertex, E extends IEdge<V>> 
                                                extends Miner{

    private DynamicCommunityMiningAlgorithmName alg = 
                                DynamicCommunityMiningAlgorithmName.LOCAL;

    private final IDynamicGraph<V,E> dynaGraph;
    
    /* tf --> comId --> list of vertices */
    private final Map<TimeFrame,Map<String, List<V>>> mapTFCommunities;
    private double simThreshold = 0.5;
    private Metric metric = Metric.M;
    private Method method = Method.Direct;
    private boolean hasOverlap = false;
    private boolean hasHub = false;
    private double instability = 0.2;
    private int history = 1;
    
    Properties properties ;
            
    public DynamicMiner(IDynamicGraph<V,E> pDynaGraph, String[] parameters) {
        super(pDynaGraph, pDynaGraph.getAllTimeFrames().get(0));
        this.dynaGraph = pDynaGraph;
        this.mapTFCommunities = new HashMap<>();
        
        /* parse parameters */
        if (parameters != null) {
            for (String par : parameters) {
                String[] pair = par.split(":");
                String key = pair[0];
                String value = pair[1];
                switch(key){
                    case DynamicCommunityMiningParameters.ALGORITHM:
                        if (value.toLowerCase().contains("local")) {
                            alg = DynamicCommunityMiningAlgorithmName.LOCAL;
                        } else if (value.toLowerCase().contains("facetnet")) {
                            alg = DynamicCommunityMiningAlgorithmName.FACETNET;
                        }
                        break;
                    case DynamicCommunityMiningParameters.SIMILARITY_THRESHOLD:
                        simThreshold = Double.parseDouble(value);
                        break;
                    case DynamicCommunityMiningParameters.METRIC:
                        if (value.toLowerCase().contains("l")) {
                            metric = Metric.L;
                        } else if (value.toLowerCase().contains("m")) {
                            metric = Metric.M;
                        } else if (value.toLowerCase().contains("r")) {
                            metric = Metric.R;
                        }
                        break;
                    case DynamicCommunityMiningParameters.METHOD:
                        if (value.toLowerCase().contains("direct")) {
                            method = Method.Direct;
                        } else if (value.toLowerCase().contains("independent")) {
                            method = Method.Independent;
                        } else if (value.toLowerCase().contains("spectrum")) {
                            method = Method.Spectrum;
                        }
                        break;
                    case DynamicCommunityMiningParameters.OVERLAP:
                        if (value.toLowerCase().contains("false")) {
                            hasOverlap = false;
                        } else if (value.toLowerCase().contains("true")) {
                            hasOverlap = true;
                        }
                        break;
                    case DynamicCommunityMiningParameters.HUB:
                        if (value.toLowerCase().contains("false")) {
                            hasHub = false;
                        } else if (value.toLowerCase().contains("true")) {
                            hasHub = true;
                        }
                        break;
                    case DynamicCommunityMiningParameters.INSTABILITY:
                        instability = Double.parseDouble(value);
                        break;
                    case DynamicCommunityMiningParameters.HISTORY:
                        history = Integer.parseInt(value);
                        break;
                }
            }
        }
        
        
        /* setting up properties based on parameters / default value */
        System.out.println("Setting properties for Dynamic Community Mining");
        properties = new Properties();
        properties.put("algorithm", alg.toString().toUpperCase());
        properties.put("similarityThreshold", simThreshold);
        properties.put("metric", metric.toString().toUpperCase());
        properties.put("method", method.toString());
        properties.put("overlap", hasOverlap);
        properties.put("hub", hasHub);
        properties.put("instability", instability);
        properties.put("history", history);
        // The following are required for facetnet.
        properties.put("facetnetLocation", "0");
        properties.put("facetnetMFile", "0");
        properties.put("alpha", "0");
        properties.put("maxNbCluster", "0");
    }

    public void run() {
        DynamicCommunityMining<V,E> dynamicCommunityMining =
                        new DynamicCommunityMining<>(dynaGraph,null,0.3);

        

        StringBuffer results;
        try {
            System.out.println("DynamicMiner.run() : starts performing community mining");
            
            results = 
                dynamicCommunityMining.performCommunityMining(properties, this.isThreadRunningProperty);
            //adding this condition to stop thread
            if(!running){
                return;
            }
            System.out.println("DynamicMiner.run() : results are ready --> " + results);
            int graphNum = 0;
            String[] graphs = results.toString().split("\n");
            for (String graph : graphs) {
                //adding this condition to stop thread
                if(!running){
                    return;
                }
                Map<String,List<V>> mapCommunityVertices = new HashMap<>();
                String comName;
                String[] communities = graph.split("@");
                for (String community : communities) {
                    String strCurCom = community.split(":")[0];
                    String strVertesList = community.split(":")[1];
                    String[] vertices = strVertesList.split(",");
                    LinkedList<V> llVertices = new LinkedList<>();
                    for (String vertex : vertices) {
                        V v = dynaGraph.getVertex(Integer.parseInt(vertex.trim()));
                        llVertices.add(v);
                    }
                    mapCommunityVertices.put(strCurCom, llVertices);
                    mapTFCommunities.put(dynaGraph.getAllTimeFrames().get(graphNum), mapCommunityVertices);
                }
                graphNum++;
            }
            for (TimeFrame tf : mapTFCommunities.keySet()) {
                System.out.println("DynamicMiner.run() : timeframe -- >" + tf.toString());
                for (String comName : mapTFCommunities.get(tf).keySet()) {
                    System.out.print("DynamicMiner.run() : " + comName + " : ");
                    for (V v : mapTFCommunities.get(tf).get(comName)) {
                        System.out.print(v.getId() + " , ");
                    }
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //adding this condition to stop thread
            if(!running){
                return;
            }
        updateDataStructure();
        
        blnDone = true;
    }

    public boolean requiresParameters() {
        return true;
    }

    @Override
    public boolean updateDataStructure() {
        
        //System.out.println("DynamicMiner.updateDataStructure(): ");
        for (TimeFrame tf : mapTFCommunities.keySet()) {
            //adding this condition to stop thread
            if(!running){
                break;
            }
            
            //calculate color of communities here and update them in data strucutre
            dynaGraph.calculateCommunityColors(mapTFCommunities.get(tf).keySet(), tf);
            
            //System.out.println("DynamicMiner.updateDataStructure(): " + tf.toString());
            
            //update sys:com and sys:color of vertices that belong to communties
            for (String com : mapTFCommunities.get(tf).keySet()) {
                String strcolorCommuity = dynaGraph.getCommunityColor(com, tf);
                for (V v : mapTFCommunities.get(tf).get(com)) {
                    //a vertex can belong to more than one community
                    v.getSystemAttributer().appendAttributeValue(
                            MeerkatSystem.COMMUNITY, 
                            com, 
                            new Date(),
                            tf);
                    v.getSystemAttributer().appendAttributeValue(
                            MeerkatSystem.COLOR, 
                            strcolorCommuity, 
                            new Date(),
                            tf);
                    
//                    System.out.println("DynamicMiner.updateDataStructure() : " +
//                            v.getId() + " : " + 
//                            v.getSystemAttributer().getAttributeValue(
//                                    MeerkatSystem.COMMUNITY, tf));
                }
            }
        }
        
        return true;
    }
    
    public Map<TimeFrame,Map<String,List<V>>> getResults() {
        return mapTFCommunities;
    }
    
    @Override
    public String toString() {
        return "Dynamic Mining";
    }
}