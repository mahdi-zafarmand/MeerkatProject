/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;


/**
 *  Class Name      : CommunitiesContextConfig
 *  Created Date    : 2016-07-07
 *  Description     : The config for the Communities Context Menu (the root Communities Node)
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class CommunitiesContextConfig {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String SAVECOMMUNITIES ;
    private static String SAVEASNEWGRAPH ;
    private static String COMMUNITYMINING_ALGORITHMS ;
    private static String CLEARMININGRESULTS ;
    
    private static String ALG_KMEANS ;
    private static String ALG_FASTMODULARITY ;
    private static String ALG_SAMEATTRIBUTEVALUE ;
    private static String ALG_LOCALT ;
    private static String ALG_LOCALTOP ;
    private static String ALG_LOCALCOMMUNITY ;
    private static String ALG_ROSVALLINFOMAP ;
    private static String ALG_ROSVALLINFOMOD ;
    private static String ALG_DYNAMICCOMMUNITY ;

    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public static String getSaveCommunitiesText() {
        return SAVECOMMUNITIES ;
    }
    public static String getSaveAsNewGraphText() {
        return SAVEASNEWGRAPH ;
    }
    public static String getCommunityAlgorithmsText() {
        return COMMUNITYMINING_ALGORITHMS ;
    }
    public static String getClearMiningResultsText() {
        return CLEARMININGRESULTS ;
    }
    
    public static String getAlgoKMeansText() {
        return ALG_KMEANS ;
    }
    public static String getAlgoFastModularityText() {
        return ALG_FASTMODULARITY ;
    }
    public static String getAlgoSameAttributeValueText() {
        return ALG_SAMEATTRIBUTEVALUE ;
    }
    public static String getAlgoLocalTText() {
        return ALG_LOCALT ;
    }
    public static String getAlgoLocalCommunityText() {
        return ALG_LOCALCOMMUNITY ;
    }
    public static String getAlgoLocalTopText() {
        return ALG_LOCALTOP ;
    }
    public static String getAlgoRosvallInfomapText() {
        return ALG_ROSVALLINFOMAP ;
    }
    public static String getAlgoRosvallInfomodText() {
        return ALG_ROSVALLINFOMOD ;
    }
    public static String getAlgoDynamicCommunityText() {
        return ALG_DYNAMICCOMMUNITY ;
    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    /**
     *  Method Name     : Instantiate()
     *  Created Date    : 2016-07-08
     *  Description     : Instantiates the context menu for Communities
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrSaveCommunities : String
     *  @param pstrSaveAsNewGraph : String
     *  @param pstrCommunityMiningAlgorithms : String
     *  @param pstrClearMiningResults : String
     *  @param pstrKMeans : String
     *  @param pstrFastModularity : String
     *  @param pstrSameAttributeValue : String
     *  @param pstrLocalT : String
     *  @param pstrLocalTop : String
     *  @param pstrLocalCommunity : String
     *  @param pstrRosvallInfomap : String
     *  @param pstrRosvallInfomod : String
     *  @param pstrDynamicCommunity : String
     * 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Instantiate(
              String pstrSaveCommunities
            , String pstrSaveAsNewGraph
            , String pstrCommunityMiningAlgorithms
            , String pstrClearMiningResults

            , String pstrKMeans
            , String pstrFastModularity
            , String pstrSameAttributeValue
            , String pstrLocalT
            , String pstrLocalTop
            , String pstrLocalCommunity
            , String pstrRosvallInfomap
            , String pstrRosvallInfomod
            , String pstrDynamicCommunity) {
        
        SAVECOMMUNITIES = pstrSaveCommunities ;
        SAVEASNEWGRAPH = pstrSaveAsNewGraph ;
        COMMUNITYMINING_ALGORITHMS = pstrCommunityMiningAlgorithms ;
        CLEARMININGRESULTS = pstrClearMiningResults ;

        ALG_KMEANS = pstrKMeans ;
        ALG_FASTMODULARITY = pstrFastModularity ;
        ALG_SAMEATTRIBUTEVALUE = pstrSameAttributeValue ;
        ALG_LOCALT = pstrLocalT ;
        ALG_LOCALTOP = pstrLocalTop ;
        ALG_LOCALCOMMUNITY = pstrLocalCommunity ;
        ALG_ROSVALLINFOMAP = pstrRosvallInfomap ;
        ALG_ROSVALLINFOMOD = pstrRosvallInfomod ;
        ALG_DYNAMICCOMMUNITY = pstrDynamicCommunity ;
    }
    
}
