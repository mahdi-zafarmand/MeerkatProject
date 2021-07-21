/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api;

import io.utility.GraphGenerator;
import io.utility.IconReader;
import io.utility.Utilities;
import java.util.List;

/**
 *
 * @author mahdi
 */
public class UtilityAPI {
    private static GraphGenerator G;

    public static void generateGraph(String nodes, String edges, String community, 
            String beta, String alpha, String gamma, String r, String q,
            String phi, String epsilon, String t) throws InterruptedException{
        G = new GraphGenerator(nodes, edges, community, beta, alpha, gamma, r, q,
        phi, epsilon, t);
        G.start();
        G.join();
    }
    
    public static boolean isDone(){
        return !G.isAlive();
    }
    
    public static String getFileNameWithoutExtension(String pstrFileName){
        return Utilities.getFileNameWithoutExtension(pstrFileName);
    }
    
    public static List<String> getAllIcons(){
        return IconReader.getAllIcons();
    }
}
