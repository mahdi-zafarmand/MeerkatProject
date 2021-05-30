/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import algorithm.graph.communitymining.AttributeValueMiner;
import algorithm.graph.communitymining.FastModularity;
import algorithm.graph.communitymining.KMeansMiner;
import algorithm.graph.communitymining.LocalCommunityMiner;
import algorithm.graph.communitymining.LocalT;
import algorithm.graph.communitymining.LocalTopLeader;
import algorithm.graph.communitymining.RosvallInfomapMiner;
import algorithm.graph.communitymining.RosvallInfomodMiner;
import config.CommunityMiningParameters;
import config.Parameter;
import java.util.List;

/**
 *
 * @author aabnar
 */
public class CommunityMiningParametersTest {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        printClassParametersAndValues(AttributeValueMiner.class);
        printClassParametersAndValues(FastModularity.class);
        printClassParametersAndValues(KMeansMiner.class);
        printClassParametersAndValues(LocalCommunityMiner.class);
        printClassParametersAndValues(LocalT.class);
        printClassParametersAndValues(LocalTopLeader.class);
        printClassParametersAndValues(RosvallInfomapMiner.class);
        printClassParametersAndValues(RosvallInfomodMiner.class);
    }

    private static void printClassParametersAndValues(Class pclassName) {
        List<Parameter> lstParams
                = CommunityMiningParameters.getParameters(pclassName.getSimpleName());
//        System.out.println(CommunityMiningParameters.mapClassParameters);
        System.out.println("Class: " + pclassName.getSimpleName());
        for (Parameter p : lstParams) {
            System.out.print(p.key);
            if (p.values != null) {
                System.out.print(" : ");
                for (String s : p.values) {
                    System.out.print(s + " , ");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }
}
