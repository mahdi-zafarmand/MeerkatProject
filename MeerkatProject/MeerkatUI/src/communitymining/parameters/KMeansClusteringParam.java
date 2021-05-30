/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communitymining.parameters;

/**
 *  Class Name      : FastModularityParam
 *  Created Date    : 2016-04-07
 *  Description     : Parameters for Fast Modularity Community Mining Algorithm
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class KMeansClusteringParam {
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strTitle ;
    private static String strClusterCountLabel ;
    private static String strAttributeLabel ;
    
    // Values
    private static int intNumberOfClusters ;
    private static String strAttribute ;


    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public static String getTitle() {
        return strTitle ;
    }
    public static String getClusterCountLabel() {
        return strClusterCountLabel ;
    }
    public static String getAttributeLabel() {
        return strAttributeLabel ;
    }
    
    public static int getNumberOfClusters () {
        return intNumberOfClusters ;
    }
    public static void setNumberOfClusters (int pintValue) {
        intNumberOfClusters = pintValue ;
    }
    
    public static void setAttribute(String pstrValue) {
        strAttribute = pstrValue ;
    }
    public static String getAttribute() {
        return strAttribute ;
    }

    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2016-04-07
     *  Description     : Sets the Parameters of the KMeans Clustering Algorithm
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrTitle : String
     *  @param pstrClusterCountLabel : String
     *  @param pstrAttributeLabel : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters(
              String pstrTitle
            , String pstrClusterCountLabel
            , String pstrAttributeLabel
    ) {
        strTitle = pstrTitle ;
        strClusterCountLabel = pstrClusterCountLabel ;
        strAttributeLabel = pstrAttributeLabel ;
    }
}
