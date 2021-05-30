/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import ca.aicml.meerkat.api.LoadingAPI;
import ca.aicml.meerkat.api.MeerkatAPI;
import ca.aicml.meerkat.api.ProjectAPI;
import ca.aicml.meerkat.api.analysis.GraphMetricAPI;
import java.util.Map;

/**
 *
 * @author aabnar
 */
public class MetricTest {
    
    public static void main(String[] args) {
        MeerkatAPI.loadMeerkat(false, false);
        MeerkatAPI.initializeMeerkat("/cshome/aabnar/workspace/meekatTestRunOutputs", null);
        int projectId = ProjectAPI.newProject(null);
        int graphId = LoadingAPI.LoadGraphFile(projectId, "MeerkatReader", 
                 "/cshome/aabnar/workspace/MeerkatLogicMVP/resources/files/test.meerkat");
        
        GraphMetricAPI.computeCentrality(projectId, graphId, 0, "Betweenness", null);
        
        while (!GraphMetricAPI.isDone(projectId, graphId, 0, "Betweenness", null));
        
        Map<Integer,Double> hmpScores = GraphMetricAPI.getScores(projectId, graphId, 0, "Betweenness", null);
        for (int i : hmpScores.keySet()) {
            System.out.println("MetricTest in API -- Vertex ID --> Betweenness Score : " + i + ": \t" + hmpScores.get(i) );
        } 
        
        GraphMetricAPI.computeCentrality(projectId, graphId, 0, "Authority", null);
        
        while (!GraphMetricAPI.isDone(projectId, graphId, 0, "Authority",null));
        
        hmpScores = GraphMetricAPI.getScores(projectId, graphId, 0, "Authority",null);
        for (int i : hmpScores.keySet()) {
            System.out.println("MetricTest in API -- Vertex ID --> Authority Score : " + i + ": \t" + hmpScores.get(i) );
        } 
        
        GraphMetricAPI.computeCentrality(projectId, graphId, 0, "Hub", null);
        
        while (!GraphMetricAPI.isDone(projectId, graphId, 0, "Hub", null));
        
        hmpScores = GraphMetricAPI.getScores(projectId, graphId, 0, "Hub", null);
        for (int i : hmpScores.keySet()) {
            System.out.println("MetricTest in API -- Vertex ID --> Hub Score : " + i + ": \t" + hmpScores.get(i) );
        } 
    }
}
