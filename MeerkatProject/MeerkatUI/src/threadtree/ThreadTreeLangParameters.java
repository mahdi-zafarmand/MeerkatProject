/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadtree;

/**
 *  Class Name      : ThreadTreeParameters
 *  Created Date    : 2015-07-xx
 *  Description     : Stores the default identifiers for the default GraphFormImplementation
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class ThreadTreeLangParameters {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strProjectLabel ; // This would be displayed as the root
    
    // Regular Graph
    private static String strVertex ;
    private static String strEdge ;
    
    // Twitter Graph
    private static String strUser;
    private static String strTopic;
    private static String strHashtags;
    
    // Textual Graph
    private static String strAuthor;
    private static String strTerm;
    private static String strTermCloud;
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    // public static String getRootLabel() {return ThreadTreeParameters.strRootLabel;} // Remove
        
    public static String getProjectLabel() {
        return ThreadTreeLangParameters.strProjectLabel;
    }
    
    public static String getVertexLabel() {
        return ThreadTreeLangParameters.strVertex;
    }
    
    public static String getEdgeLabel() {
        return ThreadTreeLangParameters.strEdge;
    }
    
    public static String getUserLabel() {
        return ThreadTreeLangParameters.strUser;
    }
    
    public static String getTopicLabel() {
        return ThreadTreeLangParameters.strTopic;
    }
    
    public static String getHashtagLabel() {
        return ThreadTreeLangParameters.strHashtags;
    }
    
    public static String getAuthorLabel() {
        return ThreadTreeLangParameters.strAuthor;
    }
    
    public static String getTermLabel() {
        return ThreadTreeLangParameters.strTerm;
    }
    
    public static String getTermCloudLabel() {
        return ThreadTreeLangParameters.strTermCloud;
    }
        
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    public ThreadTreeLangParameters(String pstrProjectLabel, 
            String pstrVertexLabel, String pstrEdgeLabel, 
            String pstrUser, String pstrTopic, String pstrHashtags,
            String pstrAuthor, String pstrTerm, String pstrTermCloud) {
        
        // ThreadTreeParameters.strRootLabel = pstrRootLabel;
        ThreadTreeLangParameters.strProjectLabel = pstrProjectLabel ;
        
        ThreadTreeLangParameters.strVertex = pstrVertexLabel ;
        ThreadTreeLangParameters.strEdge = pstrEdgeLabel ;
        
        ThreadTreeLangParameters.strUser = pstrUser;
        ThreadTreeLangParameters.strTopic = pstrTopic;
        ThreadTreeLangParameters.strHashtags = pstrHashtags;
        
        ThreadTreeLangParameters.strAuthor = pstrAuthor;
        ThreadTreeLangParameters.strTerm = pstrTerm;
        ThreadTreeLangParameters.strTermCloud = pstrTermCloud;               
    }
}

