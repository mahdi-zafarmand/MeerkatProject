/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;


/**
 *  Class Name      : CanvasContextConfig
 *  Created Date    : 2016-06-23
 *  Description     : The Text that are to be displayed on the Canvas Context Options
 *                      (Right click on a node)
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
*/
public class CanvasContextConfig {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String CANVASCTX_PINVERTICES ;
    private static String CANVASCTX_UNPINVERTICES ;
    private static String CANVASCTX_SHOWHIDEMINIMAP ;
    private static String CANVASCTX_CHANGEBGCOLOR ;
    private static String CANVASCTX_CHANGEBGIMAGE ;
    private static String CANVASCTX_CLEARBG ; 
    private static String CANVASCTX_EXTRACTSUBGRAPH ;      
    private static String CANVASCTX_REMOVENODE ;
    private static String CANVASCTX_REMOVEEDGE ;
    private static String CANVASCTX_VERTEXINFO ;
    private static String CANVASCTX_LINKPREDICTION ;
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    public static String getPinVerticesText() {
        return CANVASCTX_PINVERTICES;
    }
    public static String getUnpinVerticesText() {
        return CANVASCTX_UNPINVERTICES;
    }
    public static String getShowHideMinimapText() {
        return CANVASCTX_SHOWHIDEMINIMAP;
    }
    public static String getChangeBGColorText() {
        return CANVASCTX_CHANGEBGCOLOR;
    }
    public static String getChangeBGImageText() {
        return CANVASCTX_CHANGEBGIMAGE;
    }
    public static String getClearBGImageText() {
        return CANVASCTX_CLEARBG;
    }
    public static String getExtratcSubGraphText() {
        return CANVASCTX_EXTRACTSUBGRAPH;
    }
    public static String getLinkPredictionText() {
        return CANVASCTX_LINKPREDICTION;
    }
    public static String getNodeRemoveText() {
        return CANVASCTX_REMOVENODE;
    }
    public static String getEdgeRemoveText() {
        return CANVASCTX_REMOVEEDGE;
    }
    public static String getVertexInformationText() {
        return CANVASCTX_VERTEXINFO;
    }
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : Instantiate
     *  Created Date    : 2016-06-23
     *  Description     : Instantiates the text required by the CanvasContext Options
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrPinVertices : String
     *  @param pstrUnpinVertices : String
     *  @param pstrShowHideMiniMap : String
     *  @param pstrChangeBGColor : String
     *  @param pstrChangeBGImage : String
     *  @param pstrClearBG : String
     *  @param pstrExtractSubgraph : String
     *  @param pstrLinkPrediction : String
     *  @param pstrRemoveNode : String
     *  @param pstrRemoveEdge : String
     *  @param pstrVertexInfo : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public static void Instantiate(
              String pstrPinVertices
            , String pstrUnpinVertices
            , String pstrShowHideMiniMap
            , String pstrChangeBGColor
            , String pstrChangeBGImage
            , String pstrClearBG
            , String pstrExtractSubgraph
            , String pstrLinkPrediction
            , String pstrRemoveNode
            , String pstrRemoveEdge
            , String pstrVertexInfo
    ) {
        CANVASCTX_PINVERTICES = pstrPinVertices ;
        CANVASCTX_UNPINVERTICES = pstrUnpinVertices ;
        CANVASCTX_SHOWHIDEMINIMAP = pstrShowHideMiniMap ;
        CANVASCTX_CHANGEBGCOLOR = pstrChangeBGColor ;
        CANVASCTX_CHANGEBGIMAGE = pstrChangeBGImage ;
        CANVASCTX_CLEARBG = pstrClearBG ; 
        CANVASCTX_EXTRACTSUBGRAPH = pstrExtractSubgraph;
        CANVASCTX_LINKPREDICTION = pstrLinkPrediction;
        CANVASCTX_REMOVENODE = pstrRemoveNode;
        CANVASCTX_REMOVEEDGE = pstrRemoveEdge;
        CANVASCTX_VERTEXINFO = pstrVertexInfo;
    }
    
}
