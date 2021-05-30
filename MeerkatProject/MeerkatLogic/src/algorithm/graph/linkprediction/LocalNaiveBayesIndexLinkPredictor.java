/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.linkprediction;



import config.LinkPredictionParameters;
import config.MeerkatSystem;
import config.Parameter;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author AICML Administrator
 * paper : Chi's thesis section 2.2.4 LNB algorithm implementation, +1 is added to the rw
 * to avoid the negative values for S (S-score)
 * @param <V>
 * @param <E>
 */
public class LocalNaiveBayesIndexLinkPredictor<V extends IVertex, E extends IEdge<V>>
        extends LinkPredictor<V, E>{
    
    private Map<IVertex, Double> mapRw;
    private double s;
    private DecimalFormat df = new DecimalFormat("#.###########");
    
    private int noOfTopNEdges = LinkPredictionParameters.DEFAULT_NO_OF_TOPKEDGES;
    private double minMetricScore = LinkPredictionParameters.DEFAULT_METRIC_SCORE;

    
    public LocalNaiveBayesIndexLinkPredictor(IDynamicGraph<V, E> pDynaGraph, TimeFrame tf, String[] pstrParameters){
        super(pDynaGraph, tf);
        
        //parse params here
        List<Parameter> lstParam = LinkPredictionParameters
                .getParameters(this.getClass().getSimpleName());
       

        if (pstrParameters != null && pstrParameters.length > 0) {
            for (String strP : pstrParameters) {
                System.out.println(strP);

                for (Parameter p : lstParam) {
                    if (strP.startsWith(p.key)) {
                        String value = strP.substring(p.key.length() + 1).trim();
                        if (p.key.equals(LinkPredictionParameters.LOCALNAIVEBAYESINDEX_TOPNEDGES)) {
                            this.noOfTopNEdges = Integer.parseInt(value);
                            this.minMetricScore = -1.0 ;
                        } else if (p.key.equals(LinkPredictionParameters.LOCALNAIVEBAYESINDEX_TOPMETRIC)) {
                            this.minMetricScore = Double.parseDouble(value);
                            this.noOfTopNEdges = -1 ;
                        }
                        break;
                    }
                }
            }
        }
        
        
    }
    
    
    @Override
    public void run(){
        
        predictNewLinks();
        
        updateDataStructure();
        
        blnDone = true;
    
    }
    
    
    private void calculateMapRwMetric(){
        
        mapRw = new HashMap<>();
        
        for(V v : dynaGraph.getVertices(tf)){
            List<V> listNeighbors = dynaGraph.getNeighbors(v, tf);
            //can not create a generic array
            IVertex[] neighborsArray = listNeighbors.toArray(new IVertex[listNeighbors.size()]);
            int connected = 0;
            int disconnected = 0;
            for(int i = 0; i < neighborsArray.length-1; i++){
                for(int j = 1; j < neighborsArray.length; j++){
                    if(dynaGraph.findEdge((V)neighborsArray[i], (V)neighborsArray[j], tf)!=null){
                        connected = connected + 1;
                    }else{
                        disconnected = disconnected + 1;           
                    }
                }
            }
            double Rw = ((double)(connected+1))/(disconnected+1) + 1; // So that there are no negative values
            Rw = Double.parseDouble(df.format(Rw));
            mapRw.put(v, Rw);
            //System.out.println("####################### " + v.getId() + " -> "+mapRw.get(v) + " ,,,, " +
            //        connected + " : " + disconnected );
        }
        
    }
    
    private double calculateSScore(V u, V v){
            
        Set<V> listCommonNeighbors = dynaGraph.getCommonNeighbors(u, v, tf);
        double SScore = 0;
        for(V vertex : listCommonNeighbors){
            SScore = SScore +  Double.parseDouble(df.format(Math.log10(s))) + Double.parseDouble(df.format(Math.log10(mapRw.get(vertex))));
        }
        
        return SScore;
        
    }
    
    public void predictNewLinks(){
        
        this.calculateSandMapRw(); 
        
        List<IVertex[]> edgePairsPredicted = new ArrayList<>(noOfTopNEdges);
        TreeMap<Double, IVertex[]> mapScoreEdgepairs = new TreeMap<>();
        
        Set<int[]> edgeNonExistingPairs = dynaGraph.getNonExistingEdges(tf);
        
        EdgeSScorePair[] edgeSScorePairsArray = new EdgeSScorePair[edgeNonExistingPairs.size()];
        
        
        
        //System.out.println("----------------  edgeNonExistingPairs size = " + edgeNonExistingPairs.size());
        int i = 0;
        for(int[] edgePair : edgeNonExistingPairs){
            
            V u = dynaGraph.getVertex(edgePair[0]);
            V v = dynaGraph.getVertex(edgePair[1]);
            double s_score = this.calculateSScore(u, v);
            //System.out.println("################################## link prediction ---- u,v -> s  = " +
            //       edgePair[0] + ", " + edgePair[1] + " -> " + s_score); 
            //mapScoreEdgepairs.put(s_score, new IVertex[]{u,v});  
            edgeSScorePairsArray[i] = new EdgeSScorePair(new IVertex[]{u,v}, s_score);
            i++;
        }
        //sorting in descending order
        Arrays.sort(edgeSScorePairsArray);
        // System.out.println("All Non existing Edges score -- file id's and score");
        for(EdgeSScorePair pair : edgeSScorePairsArray){
            String fileIdv1 = pair.edgePair[0].getUserAttributer().getAttributeValue(MeerkatSystem.FILE_ID, tf);
            String fileIdv2 = pair.edgePair[1].getUserAttributer().getAttributeValue(MeerkatSystem.FILE_ID, tf);
            // System.out.println(pair.edgePair[0].getId() + "    " + pair.edgePair[1].getId() + "    " + pair.s_score);
            
        }
        
        //add edges in listEdgesPredicted
        if (this.noOfTopNEdges > -1) {
            int noOfEdges = 0;
            // System.out.println("Top N Edges: " + this.noOfTopNEdges);
            for(EdgeSScorePair edgeScorePair : edgeSScorePairsArray){
                if(noOfEdges < noOfTopNEdges){
                    int id1 = edgeScorePair.edgePair[0].getId();
                    int id2 = edgeScorePair.edgePair[1].getId();
                    // System.out.println("\t" + id1 + "\t" + id2 + "\t" + edgeScorePair.s_score);
                    listEdgesPredicted.add(new int[]{id1, id2});
                    noOfEdges++;
                } else {
                    break;
                }    
            }
        } else if (this.minMetricScore > -1.0) {
            // System.out.println("Metric Score: " + this.minMetricScore);
            for(EdgeSScorePair edgeScorePair : edgeSScorePairsArray){
                if(edgeScorePair.s_score > this.minMetricScore){
                    int id1 = edgeScorePair.edgePair[0].getId();
                    int id2 = edgeScorePair.edgePair[1].getId();
                    // System.out.println("\t" + id1 + "\t" + id2 + "\t" + edgeScorePair.s_score);
                    listEdgesPredicted.add(new int[]{id1, id2});
                }
            }
        }
    }
    
    private void calculateSandMapRw(){
    
        int noOfVertices = dynaGraph.getVertexCount(tf);
        double M = noOfVertices*(noOfVertices-1)/2;
        double MTranspose = dynaGraph.getEdges(tf).size();
        
        s = (M/MTranspose)-1;
        // System.out.println("############################# s score = " + s);
        this.calculateMapRwMetric();
    
    }
    
    
}

class EdgeSScorePair implements Comparable{
        IVertex[] edgePair;
        double s_score;

        public EdgeSScorePair(IVertex[] edgePair, double s_score){
            this.edgePair = edgePair;
            this.s_score = s_score;
        }
        
        @Override
        //Sorting in descending order
        public int compareTo(Object o) {
            EdgeSScorePair other = (EdgeSScorePair) o;
            if(this.s_score > other.s_score){
                return -1;
            }else if (this.s_score < other.s_score){
                return 1;
            }else{
                return 0;
            }
        }
}
