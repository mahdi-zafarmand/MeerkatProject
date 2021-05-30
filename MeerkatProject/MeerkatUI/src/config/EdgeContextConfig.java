/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *  Class Name      : EdgeContextConfig
 *  Created Date    : 2015-10-30
 *  Description     : The Text that are to be displayed on the EdgeContext Options
 *                      (Right click on a node)
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class EdgeContextConfig {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String EDGE_INFO ;    
    private static String EDGE_STYLE ;
    private static String EDGE_LINESTYLE ;
    private static String EDGE_WIDTH ;
    private static String EDGE_COLOR ;
    private static String EDGE_DELETE ;

    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    /**
     * @return the EDGE_INFO
     */
    public static String getEdgeInfoText() {
        return EDGE_INFO;
    }

    /**
     * @return the EDGE_STYLE
     */
    public static String getEdgeStyleText() {
        return EDGE_STYLE;
    }

    /**
     * @return the EDGE_LINESTYLE
     */
    public static String getEdgeLineStyleText() {
        return EDGE_LINESTYLE;
    }

    /**
     * @return the EDGE_WIDTH
     */
    public static String getEdgeWidthText() {
        return EDGE_WIDTH;
    }

    /**
     * @return the EDGE_COLOR
     */
    public static String getEdgeColorText() {
        return EDGE_COLOR;
    }
    
    /**
     * @return the EDGE_DELETE
     */
    public static String getEdgeDeleteText() {
        return EDGE_DELETE;
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : Instantiate
     *  Created Date    : 2015-10-30
     *  Description     : Instantiates the text required by the EdgeContext Options
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrEdgeInfoText : String
     *  @param pstrEdgeStyleText : String
     *  @param pstrEdgeLineStyleText : String
     *  @param pstrEdgeSizeText : String
     *  @param pstrEdgeColorText : String
     *  @param pstrEdgeDeleteText : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Instantiate(
              String pstrEdgeInfoText
            , String pstrEdgeStyleText
            , String pstrEdgeLineStyleText
            , String pstrEdgeSizeText
            , String pstrEdgeColorText
            , String pstrEdgeDeleteText
    ) {
        EDGE_INFO = pstrEdgeInfoText;
        EDGE_STYLE = pstrEdgeStyleText;
        EDGE_LINESTYLE = pstrEdgeLineStyleText ;
        EDGE_WIDTH = pstrEdgeSizeText;
        EDGE_COLOR = pstrEdgeColorText;
        EDGE_DELETE = pstrEdgeDeleteText;
    }

}
