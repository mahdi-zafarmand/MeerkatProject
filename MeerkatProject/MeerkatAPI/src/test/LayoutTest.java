/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.LoadingAPI;
import ca.aicml.meerkat.api.MeerkatAPI;
import ca.aicml.meerkat.api.ProjectAPI;
import ca.aicml.meerkat.api.analysis.LayoutAPI;
import java.util.Map;

/**
 *
 * @author aabnar
 */
public class LayoutTest {
    
     public static void main(String[] args) {
        
        MeerkatAPI.loadMeerkat(false, false);
        MeerkatAPI.initializeMeerkat("D:\\", null);
        int projectId = ProjectAPI.newProject(null);
        int graphId = LoadingAPI.LoadGraphFile(projectId, "MeerkatReader",
                "C:\\Users\\Talat-AICML\\Documents\\NetBeansProjects\\MeerkatAPI\\resources\\files\\test.meerkat");
        
        
        runTestLayout(projectId, graphId);
        
//        int graphId = GraphLoader.loadGraph(projectId, "MeerkatReader",
//          "/cshome/aabnar/workspace/MeerkatLogic/resources/files/test.meerkat");
        
//        GraphMetric.computeBetweennessCentrality(projectId, graphId);
    }
     
     public static void runTestLayout(int projectId, int graphId) {
        Map<Integer, Double[]> positions = GraphAPI.getVertex2DPoistions(projectId, graphId, 0, 100, 100);
        
        for (Integer intCurrentIndex : positions.keySet()) {
            System.out.println("LayoutTest in API -- Vertex ID --> Position : " + intCurrentIndex + ": \t" 
                    + positions.get(intCurrentIndex)[0] + "\t" + positions.get(intCurrentIndex)[1]);
        } 
        
        LayoutAPI.runLayout(projectId, graphId, 0, "LayoutCircle", null);
        
        while(!LayoutAPI.isDone(projectId, graphId, 0, "LayoutCircle", null));
        
        System.out.println("Circle Layout Results ------------------->");
        
        Map<Integer, Double[]> positions2 = GraphAPI.getVertex2DPoistions(projectId, graphId, 0, 100, 100);
        for (Integer intCurrentIndex : positions2.keySet()) {
            System.out.println("LayoutTest in API -- Vertex ID --> Position : " + intCurrentIndex + ": \t" 
                + positions2.get(intCurrentIndex)[0] + "\t" + positions2.get(intCurrentIndex)[1]);
        } 
     }
}
