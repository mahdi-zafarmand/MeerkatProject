/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *  Class Name      : GraphConfig
 *  Created Date    : 2015-08-06
 *  Description     : To store the type of the graph and other graphic items
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-03-03      Talat           Added NOSUCHGRAPH for default
 * 
*/
public class GraphConfig {
    
    public enum GraphType {
        GRAPH,
        TEXTUALGRAPH,
        TWITTERGRAPH,
        NOSUCHGRAPH
    }
    
    public static int NEIGHBORHOOD_DEGREE_MIN = 1;
    public static int NEIGHBORHOOD_DEGREE_MAX = 100;
    public static int NEIGHBORHOOD_DEGREE_DEFAULT = 1;
    public static int NEIGHBORHOOD_DEGREE_STEP = 1 ;
}
