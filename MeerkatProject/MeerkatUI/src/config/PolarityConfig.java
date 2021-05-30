/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;


/**
 *  Class Name      : PolarityConfig
 *  Created Date    : 2015-10-29
 *  Description     : Configuration for the polarity
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class PolarityConfig {
    
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String POLARITY_FEEDBACK ;
    private static String POLARITY_TITLE ;
    private static String POLARITY_SENTENCE ;
    private static String POLARITY_WORD ;
    private static String POLARITY_STRONGPOSITIVE ;
    private static String POLARITY_POSITIVE ;
    private static String POLARITY_NEUTRAL ;
    private static String POLARITY_NEGATIVE ;
    private static String POLARITY_STRONGNEGATIVE ;
    private static String POLARITY_TOTAL ;
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    /**
     * @return the POLARITY_FEEDBACK
     */
    public static String getFeedbackText() {
        return POLARITY_FEEDBACK;
    }

    /**
     * @return the POLARITY_SENTENCE
     */
    public static String getSentenceText() {
        return POLARITY_SENTENCE;
    }

    /**
     * @return the POLARITY_WORD
     */
    public static String getWordText() {
        return POLARITY_WORD;
    }

    /**
     * @return the POLARITY_STRONGPOSITIVE
     */
    public static String getStrongPosText() {
        return POLARITY_STRONGPOSITIVE;
    }

    /**
     * @return the POLARITY_POSITIVE
     */
    public static String getPositiveText() {
        return POLARITY_POSITIVE;
    }

    /**
     * @return the POLARITY_NEUTRAL
     */
    public static String getNeutralText() {
        return POLARITY_NEUTRAL;
    }

    /**
     * @return the POLARITY_NEGATIVE
     */
    public static String getNegativeText() {
        return POLARITY_NEGATIVE;
    }

    /**
     * @return the POLARITY_STRONGNEGATIVE
     */
    public static String getStrongNegText() {
        return POLARITY_STRONGNEGATIVE;
    }

    /**
     * @return the POLARITY_TOTAL
     */
    public static String getTotalText() {
        return POLARITY_TOTAL;
    }
    
    /**
     * @return the POLARITY_TITLE
     */
    public static String getTitle() {
        return POLARITY_TITLE;
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : Instantiate
     *  Created Date    : 2015-10-29
     *  Description     : Instantiates the text required by the Polarity Screen
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrFeedback : String
     *  @param pstrTitle : String
     *  @param pstrSentence : String
     *  @param pstrWord : String
     *  @param pstrStrongPositive : String
     *  @param pstrPositive : String
     *  @param pstrNeutral : String
     *  @param pstrNegative : String
     *  @param pstrStrongNegative : String
     *  @param pstrTotal : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Instantiate(
              String pstrFeedback
            , String pstrTitle
            , String pstrSentence
            , String pstrWord
            , String pstrStrongPositive
            , String pstrPositive
            , String pstrNeutral
            , String pstrNegative
            , String pstrStrongNegative
            , String pstrTotal
             
    ) {
        POLARITY_FEEDBACK = pstrFeedback;
        POLARITY_TITLE = pstrTitle;
        POLARITY_SENTENCE = pstrSentence;
        POLARITY_WORD = pstrWord;
        POLARITY_STRONGPOSITIVE = pstrStrongPositive;
        POLARITY_POSITIVE = pstrPositive;
        POLARITY_NEUTRAL = pstrNeutral;
        POLARITY_NEGATIVE = pstrNegative;
        POLARITY_STRONGNEGATIVE = pstrStrongNegative;
        POLARITY_TOTAL = pstrTotal;
    }
}

