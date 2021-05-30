/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *
 * @author aicml_adm
 */
public class EmotionConfig {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String EMOTION_TITLE ;
    private static String EMOTION_SURPRISE ;
    private static String EMOTION_ANGER ;
    private static String EMOTION_SADNESS ;
    private static String EMOTION_THANK ;
    private static String EMOTION_FEAR ;
    private static String EMOTION_JOY ;
    private static String EMOTION_LOVE ;
    private static String EMOTION_DISGUST ;
    private static String EMOTION_GUILT ;
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    /**
     * @return the EMOTION_SURPRISE
     */
    public static String getSurpriseText() {
        return EMOTION_SURPRISE;
    }

    /**
     * @return the EMOTION_ANGER
     */
    public static String getAngerText() {
        return EMOTION_ANGER;
    }

    /**
     * @return the EMOTION_SADNESS
     */
    public static String getSadnessText() {
        return EMOTION_SADNESS;
    }

    /**
     * @return the EMOTION_THANK
     */
    public static String getThankText() {
        return EMOTION_THANK;
    }

    /**
     * @return the EMOTION_FEAR
     */
    public static String getFearText() {
        return EMOTION_FEAR;
    }

    /**
     * @return the EMOTION_JOY
     */
    public static String getJoyText() {
        return EMOTION_JOY;
    }

    /**
     * @return the EMOTION_LOVE
     */
    public static String getLoveText() {
        return EMOTION_LOVE;
    }

    /**
     * @return the EMOTION_DISGUST
     */
    public static String getDisgustText() {
        return EMOTION_DISGUST;
    }

    /**
     * @return the EMOTION_GUILT
     */
    public static String getGuiltText() {
        return EMOTION_GUILT;
    }
    
    /**
     * @return the EMOTION_TITLE
     */
    public static String getTitle() {
        return EMOTION_TITLE;
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    /**
     *  Method Name     : Instantiate
     *  Created Date    : 2015-10-29
     *  Description     : Instantiates the text required by the Emotion Screen
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrTitle : String
     *  @param pstrSurpise : String
     *  @param pstrAnger : String
     *  @param pstrSadness : String
     *  @param pstrThank : String
     *  @param pstrFear : String
     *  @param pstrJoy : String
     *  @param pstrLove : String
     *  @param pstrDisgust : String
     *  @param pstrGuilt : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Instantiate(
              String pstrTitle
            , String pstrSurpise       
            , String pstrAnger
            , String pstrSadness
            , String pstrThank
            , String pstrFear
            , String pstrJoy
            , String pstrLove
            , String pstrDisgust
            , String pstrGuilt
    ) {
        EMOTION_TITLE = pstrTitle ;
        EMOTION_SURPRISE = pstrSurpise;
        EMOTION_ANGER = pstrAnger;
        EMOTION_SADNESS = pstrSadness;
        EMOTION_THANK = pstrThank;
        EMOTION_FEAR = pstrFear;
        EMOTION_JOY = pstrJoy;
        EMOTION_LOVE = pstrLove;
        EMOTION_DISGUST = pstrDisgust;
        EMOTION_GUILT = pstrGuilt;
    }

}
