/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import ca.aicml.meerkat.api.MeerkatAPI;
import ca.aicml.meerkat.api.ProjectAPI;

/**
 *
 * @author aabnar
 */
public class testBetweenness {
    
    public static void main(String[] args) {
        MeerkatAPI.loadMeerkat(false, false);
        MeerkatAPI.initializeMeerkat(null, null);
        int projectId = ProjectAPI.newProject(null);
//        int graphId = GraphLoader.loadGraph(projectId, "MeerkatReader",
//          "/cshome/aabnar/workspace/MeerkatLogic/resources/files/test.meerkat");
        
//        GraphMetric.computeBetweennessCentrality(projectId, graphId);
    }
}
