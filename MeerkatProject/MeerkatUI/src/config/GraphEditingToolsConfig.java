/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *  Class Name      : GraphEditingToolsConfig
 *  Created Date    : 2016-01-04
 *  Description     : The Configuration for the Graph Editing Config
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class GraphEditingToolsConfig {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String HEADER_LABEL ;
    
    private static String ADDEDGE_TOOLTIP ;
    private static String ADDEDGE_IMAGEURL ;
    
    private static String ADDVERTEX_TOOLTIP ;
    private static String ADDVERTEX_IMAGEURL ;
    
    private static String ADDVERTEX_MULTIPLETIMEFRAMES_TOOLTIP ;
    private static String ADDVERTEX_MULTIPLETIMEFRAMES_IMAGEURL ;
    
    private static String SHORTESTPATH_TOOLTIP ;
    private static String SHORTESTPATH_IMAGEURL;

    private static String DELETEVERTEX_TOOLTIP ;
    private static String DELETEVERTEX_IMAGEURL ;
    
    private static String DELETEEDGE_TOOLTIP ;
    private static String DELETEEDGE_IMAGEURL ;
    
    private static String SELECT_TOOLTIP ;
    private static String SELECT_IMAGEURL ;
    
    private static String SELECTMULTI_TOOLTIP ;
    private static String SELECTMULTI_IMAGEURL ;
    
    private static String EDGESIZE_TOOLTIP ;
    private static String EDGESIZE_IMAGEURL ;
    
    private static String VERTEXSIZE_TOOLTIP ;
    private static String VERTEXSIZE_IMAGEURL ;
    
    private static String VERTEXCOLOR_TOOLTIP ;
    private static String VERTEXCOLOR_IMAGEURL ;
    
    private static String EDGECOLOR_TOOLTIP ;
    private static String EDGECOLOR_IMAGEURL ;
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public static String getHeader() {
        return HEADER_LABEL;
    }
    public static String getAddEdgeToolTip () {
        return ADDEDGE_TOOLTIP ;
    }
    public static String getAddEdgeImageURL () {
        return ADDEDGE_IMAGEURL ;
    }
    public static String getAddVertexToolTip () {
        return ADDVERTEX_TOOLTIP ;
    }
    public static String getAddVertexImageURL () {
        return ADDVERTEX_IMAGEURL ;
    }
    
    public static String getAddVertexMultipleTimeFramesToolTip () {
        return ADDVERTEX_MULTIPLETIMEFRAMES_TOOLTIP ;
    }
    public static String getAddVertexMultipleTimeFramesImageURL () {
        return ADDVERTEX_MULTIPLETIMEFRAMES_IMAGEURL ;
    }
    
    public static String getShortestPathToolTip() {
        return SHORTESTPATH_TOOLTIP;
    }
    public static String getShortestPathImageURL() {
        return SHORTESTPATH_IMAGEURL;
    }
    
    public static String getDeleteVertexToolTip() {
        return DELETEVERTEX_TOOLTIP ;
    }
    public static String getDeleteVertexImageURL () {
        return DELETEVERTEX_IMAGEURL;
    }
    
    public static String getDeleteEdgeToolTip() {
        return DELETEEDGE_TOOLTIP ;
    }
    public static String getDeleteEdgeImageURL () {
        return DELETEEDGE_IMAGEURL;
    }
    
    public static String getSelectToolTip () {
        return SELECT_TOOLTIP;
    }
    public static String getSelectImageURL () {
        return SELECT_IMAGEURL ;
    }
    public static String getSelectMultiToolTip() {
        return SELECTMULTI_TOOLTIP;
    }
    public static String getSelectMultiImageURL () {
        return SELECTMULTI_IMAGEURL;
    }
    public static String getEdgeImageURL () {
        return EDGESIZE_IMAGEURL ;
    }
    public static String getVertexImageURL () {
        return VERTEXSIZE_IMAGEURL ;
    }
    public static String getEdgeImageTooltip () {
        return EDGESIZE_TOOLTIP ;
    }
    public static String getVertexImageTooltip () {
        return VERTEXSIZE_TOOLTIP ;
    }
    public static String getVertexColorImageTooltip () {
        return VERTEXCOLOR_TOOLTIP ;
    }
    public static String getVertexColorImageURL () {
        return VERTEXCOLOR_IMAGEURL ;
    }
    public static String getEdgeColorImageTooltip () {
        return EDGECOLOR_TOOLTIP ;
    }
    public static String getEdgeColorImageURL () {
        return EDGECOLOR_IMAGEURL ;
    }

    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    
    /**
     * Instantiates the fields of Graph Editing Tools after reading from the XML
     * @param pstrHeader
     * @param pstrAddEdgeToolTip
     * @param pstrAddEdgeImageURL
     * @param pstrAddVertexToolTip
     * @param pstrAddVertexImageURL
     * @param pstrAddVertexMultipleTimeFramesToolTip
     * @param pstrAddVertexMultipleTimeFramesImageURL
     * @param pstrShortestPathToolTip
     * @param pstrShortestPathImageURL
     * @param pstrDeleteVertexToolTip
     * @param pstrDeleteVertexImageURL
     * @param pstrDeleteEdgeToolTip
     * @param pstrDeleteEdgeImageURL
     * @param pstrSelectToolTip
     * @param pstrSelectImageURL
     * @param pstrSelectMultiToolTip
     * @param pstrSelectMultiImageURL
     * @param pstrEdgeSizeImageURL
     * @param pstrEdgeImageToolTip
     * @param pstrVertexSizeImageURL
     * @param pstrVertexImageToolTip
     * @param pstrVertexColorImageURL
     * @param pstrVertexColorToolTip
     * @param pstrEdgeColorImageURL
     * @param pstrEdgeColorToolTip 
     * 
     * @author Talat
     * @since 2016-01-04
     */
    public static void Instantiate(
              String pstrHeader
            , String pstrAddEdgeToolTip
            , String pstrAddEdgeImageURL
            , String pstrAddVertexToolTip
            , String pstrAddVertexImageURL
            , String pstrAddVertexMultipleTimeFramesToolTip
            , String pstrAddVertexMultipleTimeFramesImageURL
            , String pstrShortestPathToolTip
            , String pstrShortestPathImageURL
            , String pstrDeleteVertexToolTip
            , String pstrDeleteVertexImageURL
            , String pstrDeleteEdgeToolTip
            , String pstrDeleteEdgeImageURL
            , String pstrSelectToolTip
            , String pstrSelectImageURL
            , String pstrSelectMultiToolTip
            , String pstrSelectMultiImageURL
            , String pstrEdgeSizeImageURL
            , String pstrEdgeImageToolTip
            , String pstrVertexSizeImageURL
            , String pstrVertexImageToolTip
            , String pstrVertexColorImageURL
            , String pstrVertexColorToolTip
            , String pstrEdgeColorImageURL
            , String pstrEdgeColorToolTip
    ) {
        HEADER_LABEL = pstrHeader;
        
        ADDVERTEX_TOOLTIP = pstrAddVertexToolTip ;
        ADDVERTEX_IMAGEURL = pstrAddVertexImageURL ;
        
        ADDVERTEX_MULTIPLETIMEFRAMES_TOOLTIP = pstrAddVertexMultipleTimeFramesToolTip ;
        ADDVERTEX_MULTIPLETIMEFRAMES_IMAGEURL = pstrAddVertexMultipleTimeFramesImageURL ;
        
        SHORTESTPATH_TOOLTIP = pstrShortestPathToolTip;
        SHORTESTPATH_IMAGEURL = pstrShortestPathImageURL;
        
        ADDEDGE_TOOLTIP = pstrAddEdgeToolTip ;
        ADDEDGE_IMAGEURL = pstrAddEdgeImageURL ;
        
        DELETEVERTEX_TOOLTIP = pstrDeleteVertexToolTip ;
        DELETEVERTEX_IMAGEURL = pstrDeleteVertexImageURL ;
        
        DELETEEDGE_TOOLTIP = pstrDeleteEdgeToolTip ;
        DELETEEDGE_IMAGEURL = pstrDeleteEdgeImageURL ;
        
        SELECT_TOOLTIP = pstrSelectToolTip;
        SELECT_IMAGEURL= pstrSelectImageURL;
        
        SELECTMULTI_TOOLTIP = pstrSelectMultiToolTip ;
        SELECTMULTI_IMAGEURL = pstrSelectMultiImageURL ;
        
        EDGESIZE_TOOLTIP  = pstrEdgeImageToolTip ;
        EDGESIZE_IMAGEURL = pstrEdgeSizeImageURL ;
    
        VERTEXSIZE_TOOLTIP = pstrVertexImageToolTip ;
        VERTEXSIZE_IMAGEURL = pstrVertexSizeImageURL ;
        
        VERTEXCOLOR_TOOLTIP = pstrVertexColorToolTip ;
        VERTEXCOLOR_IMAGEURL = pstrVertexColorImageURL ;
        
        EDGECOLOR_TOOLTIP = pstrEdgeColorToolTip;
        EDGECOLOR_IMAGEURL = pstrEdgeColorImageURL;
        
    }
    
}
