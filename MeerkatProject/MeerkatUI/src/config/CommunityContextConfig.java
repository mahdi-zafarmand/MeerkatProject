/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *  Class Name      : CommunityContextConfig
 *  Created Date    : 2016-07-07
 *  Description     : The config for the Community Context Menu
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class CommunityContextConfig {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String PINVERTICES ;
    private static String UNPINVERTICES ;
    private static String EXTRACTGRAPH ;
    private static String SAVECOMMUNITY ;

    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public static String getPinVerticesText() {
        return PINVERTICES ;
    }
    
    public static String getUnpinVerticesText() {
        return UNPINVERTICES ;
    }
    
    public static String getExtractGraphText() {
        return EXTRACTGRAPH ;
    }
    
    public static String getSaveCommunityText() {
        return SAVECOMMUNITY ;
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : Instantiate()
     *  Created Date    : 2016-07-08
     *  Description     : Instantiates the context menu for Community
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrPinVertices : String
     *  @param pstrUnpinVertices : String
     *  @param pstrExtractGraph : String
     *  @param pstrSaveCommunity : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Instantiate(
              String pstrPinVertices
            , String pstrUnpinVertices
            , String pstrExtractGraph
            , String pstrSaveCommunity
    ) {
        PINVERTICES = pstrPinVertices;
        UNPINVERTICES = pstrUnpinVertices ;
        EXTRACTGRAPH = pstrExtractGraph ;
        SAVECOMMUNITY = pstrSaveCommunity ;
    }
    
}
