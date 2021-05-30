/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *  Class Name      : GenerateGraphConfig
 *  Created Date    : 2017-06-09
 *  Description     : List of all the parameters needed for graph generation.
 *  Version         : 1.0
 *  @author         : sankalp
 * 
*/
public class GenerateGraphConfig {
    
    /**
     * ************* graph generator parameters *******************
     */
    public static final String beta = "0.8";
    /**
     *
     */
    public static final String alpha = "0.5";
    /**
     *
     */
    public static final String gamma = "0.5";
    /**
     *
     */
    public static final String epsilon = "0.0000001";
    /**
     *
     */
    public static final String r = "1";
    /**
     *
     */
    public static final String q = "0.5";
    /**
     *
     */
    public static final String phi = "1";
    /**
    *
    */
    public static final String t = "0";
    /**
    * ************* graph generator labels *******************
    */
    public static final String nodes = "Number of nodes (n)";
    /**
    *
    */
    public static final String edges = "Edges per node (m)";
    /**
    *
    */
    public static final String communities = "Number of communities (k)";
    /**
    *
    */
    public static final String beta_label = "Community structure strength (beta)";
    /**
    *
    */
    public static final String alpha_label = "Common neighbor strength (alpha)";
    /**
    *
    */
    public static final String gamma_label = "Degree similarity strength (gamma)" ;
    /**
    *
    */
    public static final String file_location = "Generated File Location" ;
    /**
    *
    */
    public static final String error_label = "nodes, edges or communities cannot be empty!";
    /**
    *
    */
    public static final String epsilon_label = "Probability of random/noisy edges (epsilon)";
    /**
    *
    */
    public static final String r_label = "Maximum community each node can belong to (r)";
    /**
    *
    */
    public static final String q_label = "Probability of node belonging to multiple communities (q)";
    /**
    *
    */
    public static final String phi_label = "Constant added to community sizes (phi)";
    /**
    *
    */
    public static final String t_label = "Probability of also connecting to the neighbors of a node each nodes connects to (t)";
    /**
    *
    */
    public static final String load_label = "Do you want to load the generated graph?";

    
}