/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *  Class Name      : VertexContextConfig
 *  Created Date    : 2015-10-30
 *  Description     : The Text that are to be displayed on the VertexContext Options
 *                      (Right click on a node)
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-06-01      Talat           Vertex Pinning/Unpinning & Label Size are added to the Context Menu
 *  2016-05-10      Talat           Vertex Delete is added to the context menu
 * 
*/
public class VertexContextConfig {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String VERTEX_INFO ;
    private static String VERTEX_NEIGHBOR ;
    private static String VERTEX_STYLE ;
    private static String VERTEX_SHAPE ;
    private static String VERTEX_SIZE ;
    private static String VERTEX_LABELSIZE ;
    private static String VERTEX_COLOR ;
    private static String VERTEX_DELETE ;
    private static String VERTEX_PIN ;
    private static String VERTEX_UNPIN ;
    private static String VERTEX_EXTRACT ;
    

    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    /**
     * @return VERTEX_INFO
     */
    public static String getVertexInfoText() {
        return VERTEX_INFO;
    }

    /**
     * @return VERTEX_NEIGHBOR
     */
    public static String getVertexNeighborText() {
        return VERTEX_NEIGHBOR;
    }

    /**
     * @return VERTEX_SHAPE
     */
    public static String getVertexShapeText() {
        return VERTEX_SHAPE;
    }
    
    /**
     * @return VERTEX_SIZE
     */
    public static String getVertexSizeText() {
        return VERTEX_SIZE;
    }
    
    /**
     * @return VERTEX_LABELSIZE
     */
    public static String getVertexLabelSizeText() {
        return VERTEX_LABELSIZE;
    }

    /**
     * @return VERTEX_STYLE
     */
    public static String getVertexStyleText() {
        return VERTEX_STYLE;
    }

    /**
     * @return VERTEX_COLOR
     */
    public static String getVertexColorText() {
        return VERTEX_COLOR;
    }
    
    
    /**
     * @return VERTEX_DELETE
     */
    public static String getVertexDeleteText() {
        return VERTEX_DELETE;
    }
    
    
    /**
     * @return VERTEX_PIN
     */
    public static String getVertexPinText() {
        return VERTEX_PIN;
    }
    
    /**
     * @return VERTEX_UNPIN
     */
    public static String getVertexUnpinText() {
        return VERTEX_UNPIN;
    }
    
    /**
     * @return VERTEX_UNPIN
     */
    public static String getVertexExtractText() {
        return VERTEX_EXTRACT;
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : Instantiate
     *  Created Date    : 2015-10-30
     *  Description     : Instantiates the text required by the NodeContext Options
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrVertexInfoText : String
     *  @param pstrVertexNeighborText : String
     *  @param pstrVertexStyleText : String
     *  @param pstrVertexShapeText : String
     *  @param pstrVertexSizeText : String
     *  @param pstrVertexLabelSizeText : String
     *  @param pstrVertexColorText : String
     *  @param pstrVertexDeleteText : String
     *  @param pstrVertexPinText : String
     *  @param pstrVertexUnpinText : String
     *  @param pstrVertexExtract : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-06-01      Talat           Added parameters pstrVertexLabelSizeText, pstrVertexPinText, pstrVertexUnpinText
     * 
    */
    public static void Instantiate(
              String pstrVertexInfoText
            , String pstrVertexNeighborText
            , String pstrVertexStyleText
            , String pstrVertexShapeText
            , String pstrVertexSizeText
            , String pstrVertexLabelSizeText
            , String pstrVertexColorText
            , String pstrVertexDeleteText
            , String pstrVertexPinText
            , String pstrVertexUnpinText
            , String pstrVertexExtract
    ) {
        VERTEX_INFO = pstrVertexInfoText;
        VERTEX_NEIGHBOR = pstrVertexNeighborText;
        VERTEX_STYLE = pstrVertexStyleText;
        VERTEX_SHAPE = pstrVertexShapeText;
        VERTEX_SIZE = pstrVertexSizeText;
        VERTEX_LABELSIZE = pstrVertexLabelSizeText ;
        VERTEX_COLOR = pstrVertexColorText;
        VERTEX_DELETE = pstrVertexDeleteText ;
        VERTEX_PIN = pstrVertexPinText ;
        VERTEX_UNPIN = pstrVertexUnpinText ;
        VERTEX_EXTRACT = pstrVertexExtract ;
    }
    
}
