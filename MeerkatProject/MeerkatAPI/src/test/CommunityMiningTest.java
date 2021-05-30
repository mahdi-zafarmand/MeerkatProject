/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.LoadingAPI;
import ca.aicml.meerkat.api.MeerkatAPI;
import ca.aicml.meerkat.api.analysis.CommunityMiningAPI;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *
 * @author maple
 */
public class CommunityMiningTest {
    
    public static void main (String args[]){
        String strbase = new File("").getAbsolutePath();
        String fileName = strbase.concat("/../MeerkatUI/projects/layouttest/Graph (0).meerkat");
        
        String[] cmAlgName = {"sameattribute","fastmodularity", "kmeans",
        "localcommunity", "localt", "localtopleaders", "rosvallinfomap",
        "rosvallinfomod", "dcmining"};
        
        MeerkatAPI.initializeMeerkat(null, null);
        int pId = LoadingAPI.CreateProject("project1", strbase.concat("./projects."));
        int gId = LoadingAPI.LoadGraphFile(pId, ".meerkat", fileName);
        
//        for (int i = 0 ; i < GraphAPI.getTimeFrameNames(pId, gId).length ; i++){
//            for (String cmName : cmAlgName) {
                int i = -1;
                String cmName = cmAlgName[8];
                System.out.println("MeerkatAPI.CommunityMiningTest: CM Alg Name : " 
                        + cmName);
                CommunityMiningAPI.runCommunityMiningAlgorithm(pId, gId, i, cmName, null);
            
                while (!CommunityMiningAPI.isDone(pId, gId, i));

                Map<String, List<Integer>> communities = 
                        CommunityMiningAPI.getResults(pId, gId, i);

                for (String strCom : communities.keySet()) {
                    for (int vid : communities.get(strCom)) {
                        System.out.print (vid + ", ");
                    }
                    System.out.println();
                }
//            }
//        }
                
    }
}
