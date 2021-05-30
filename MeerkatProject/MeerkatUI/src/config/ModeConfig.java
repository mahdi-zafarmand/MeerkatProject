/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *
 * @author talat
 * 
 * @since 2018-05-27
 */
public class ModeConfig {
 
    public static String MODES_TAG = "Modes" ;
    
    public static String SELECT_MODE_TAG = "SelectMode" ;
    public static String SELECT_MODE ;
    
    public static String VERTEXADD_MODE_TAG = "VertexAddMode";
    public static String VERTEXADD_MODE ;
    
    public static String VERTEXDELETE_MODE_TAG = "VertexDeleteMode";
    public static String VERTEXDELETE_MODE ;
    
    public static String VERTEXADD_MULTIFRAME_MODE_TAG = "VertexMultiFrameAddMode";
    public static String VERTEXADD_MULTIFRAME_MODE ;
    
    public static String EDGEADD_MODE_TAG = "EdgeAddMode" ;
    public static String EDGEADD_MODE ;
    
    public static String EDGEDELETE_MODE_TAG = "EdgeDeleteMode" ;
    public static String EDGEDELETE_MODE ;
    
    public static String SHORTESTPATH_MODE_TAG = "ShortestPathMode" ;
    public static String SHORTESTPATH_MODE ;
    
    // The following enums are used for setting the comparisons
    public static enum ModeTypes {
        SELECT,
        VERTEXADD,
        VERTEXMULTIFRAMEADD,
        VERTEXDELETE,
        EDGEADD,
        EDGEDELETE,
        SHORTESTPATH
    }
}
