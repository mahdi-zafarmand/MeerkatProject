/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.utility;

import config.MeerkatSystem;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Class Name      : GraphGenerator
 *  Created Date    : 2017-03-13
 *  Description     : class with method used in generating graphs.
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
*/
public class GraphGenerator extends Thread{
    
    private static final String EXEC_LOCATION
            = MeerkatSystem.getGraphGeneratorDirectory();
    
    private final String nodes;
    private final String edges;
    private final String community;
    private final String beta;
    private final String alpha;
    private final String gamma;
    private final String r;
    private final String q;
    private final String epsilon;
    private final String phi;
    private final String t;
    
    public GraphGenerator(String nodes, String edges, String community, String 
            beta, String alpha, String gamma, String r, String q,
            String phi, String epsilon, String t){
        this.nodes=nodes;
        this.edges=edges;
        this.community=community;
        this.beta=beta;
        this.alpha=alpha;
        this.gamma=gamma;
        this.r=r;
        this.q=q;
        this.phi=phi;
        this.epsilon=epsilon;
        this.t=t;
    }
    
    @Override
    public void run() {
        try {
            generateGraph();
        } catch (InterruptedException ex) {
            Logger.getLogger(GraphGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *  Method Name     : generateGraph()
     *  Created Date    : 2017-03-13
     *  Description     : method which calls the executable to generate graph
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public void generateGraph() throws InterruptedException
    {
        try {
            System.out.println("process started :"+ EXEC_LOCATION+
                    " -n "+this.nodes+" -m "+this.edges+ " -k "+this.community+" --beta "+this.beta+
                    " --alpha "+this.alpha+" --gamma "+this.gamma+ " --phi "+this.phi+" -r "+this.r+" -q "+this.q+" --epsilon "+this.epsilon+" -t "+this.t);
            ProcessBuilder pb = new ProcessBuilder(EXEC_LOCATION,"-n",nodes,"-m",edges,
                    "-k",community,"--beta",beta,"--alpha",alpha,"--gamma",gamma,"--phi",phi,"-r",r,"-q",q,"--epsilon",epsilon,"-t",t);
            Process p = pb.start();
            p.waitFor();
            
        } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException(
                "Graph could not be generated on this run. " +
                        "Please enter proper parameters and try again");
        }
    }
}