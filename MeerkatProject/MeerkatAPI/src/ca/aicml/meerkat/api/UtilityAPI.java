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
 *  Class Name      : UtilityAPI
 *  Created Date    : 2017-03-13
 *  Description     : API containing some utility functions
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
*/
public class UtilityAPI {
    
    private static GraphGenerator G;
    
    /**
     *  Method Name     : generateGraph()
     *  Created Date    : 2017-03-13
     *  Description     : spawns a new thread to generate graph
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
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
    
    /**
     * Gets only the file name without the extension and without the path
     * @param pstrFileName
     * @return String without an extension
     */
    public static String getFileNameWithoutExtension(String pstrFileName){
        return Utilities.getFileNameWithoutExtension(pstrFileName);
    }
    
    /**
     * 
     * @return List<String>
     * @since 2018-02-07
     * @author Sankalp/Talat
     */
    public static List<String> getAllIcons(){
        return IconReader.getAllIcons();
    }
}