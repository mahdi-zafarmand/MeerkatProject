/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *  Class Name      : ErrorMsgs
 *  Created Date    : 2016-01-14
 *  Description     : Set of Error Messages
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-01-14      Talat           Moved the bunch of static fields for Error Messages from LangConfig class into ErrorMsgs class
 * 
*/
public class ErrorMsgsConfig {
    
    public static String ERRORMSG_TAG = "ErrorMessages" ;
    
    public static String GENERAL_AND_TAG = "And" ;
    public static String GENERAL_AND = "and" ;
    
    /**************** ERROR MESSAGES *******************/
    public static String ERROR_ATLEASTONEPROJECT_TAG = "ErrorAtLeastOneProject";
    public static String ERROR_ATLEASTONEPROJECT ;
    
    public static String ERROR_CORRUPTEDFILE_TAG = "ErrorCorruptedProjectFile";
    public static String ERROR_CORRUPTEDFILE ;
    
    public static String ERROR_WRITINGGRAPHFILE_TAG = "ErrorUnableToWriteGraphFile";
    public static String ERROR_WRITINGGRAPHFILE ;
    
    public static String ERROR_NOPROJECTSTOSAVE_TAG = "ErrorNoProjectsToSave";
    public static String ERROR_NOPROJECTSTOSAVE ;
    
    public static String ERROR_PROJECTALREADYOPEN_TAG = "ErrorProjectAlreadyOpen";
    public static String ERROR_PROJECTALREADYOPEN ;
    
    public static String ERROR_GRAPHALREADYOPEN_TAG = "ErrorGraphAlreadyOpen";
    public static String ERROR_GRAPHALREADYOPEN ;
    
    public static String ERROR_GRAPHLOAD_TAG = "ErrorGraphLoad";
    public static String ERROR_GRAPHLOAD ;
    
    public static String ERROR_SAVECOMMUNITY_TAG = "ErrorSaveCommunity";
    public static String ERROR_SAVECOMMUNITY ;
    
    public static String ERROR_NUMERICVALUEONLY_TAG = "ErrorNumericValueOnly";
    public static String ERROR_NUMERICVALUEONLY ;
    
    public static String ERROR_SMALLERLEFTVALUE_TAG = "SmallerLeftValue";
    public static String ERROR_SMALLERLEFTVALUE ;
    
    public static String ERROR_SNAPSHOTFAILED_TAG = "ErrorSnapshotFailed" ;
    public static String ERROR_SNAPSHOTFAILED ;
    
    public static String ERROR_SAMELATLONGATTR_TAG = "ErrorSameLattitudeLongitudeAttr" ;
    public static String ERROR_SAMELATLONGATTR ;
    
    public static String ERROR_BOTHINPUT_TAG = "ErrorSelectBothInput" ;
    public static String ERROR_BOTHINPUT ; 
    
    public static String PROJECTSERIALIZE_TEXTUALGRAPH_TAG = "" ;
    public static String PROJECTSERIALIZE_TEXTUALGRAPH = "Error in Wrting to Textual Graph" ;
    
    public static String PROJECTSERIALIZE_REGULARGRAPH_TAG = "" ;
    public static String PROJECTSERIALIZE_REGULARGRAPH = "Error in Wrting to Regular Graph" ;
    
    public static String PROJECTSERIALIZE_WRITINGPROJECT_TAG = "" ;
    public static String PROJECTSERIALIZE_WRITINGPROJECT = "Error in writing Project File" ;
    
    public static String PROJECTSERIALIZE_GENERALERROR_TAG = "" ;
    public static String PROJECTSERIALIZE_GENERALERROR = "Error in ProjectSerialization" ;
    
    public static String PROJECTDESERIALIZE_GENERALERROR_TAG = "" ;
    public static String PROJECTDESERIALIZE_GENERALERROR = "Error in ProjectDeserialization" ;
    
    public static String PROJECTDESERIALIZE_MALFORMEDPROJECTLINE_TAG = "" ;
    public static String PROJECTDESERIALIZE_MALFORMEDPROJECTLINE = "Project line in Project File is malformed" ;
    
    public static String PROJECTDESERIALIZE_NOVALUEFORKEY_TAG = "" ;
    public static String PROJECTDESERIALIZE_NOVALUEFORKEY = "Key does not have a corrsponding value" ;
    
    public static String COMMUNITYMINING_NUMBEROFATTEMPTSBETWEEN_TAG = "" ;
    public static String COMMUNITYMINING_NUMBEROFATTEMPTSBETWEEN = "Attempts should be between\n" ;
    
    public static String COMMUNITYMINING_SELECTALGORITHM_TAG = "" ;
    public static String COMMUNITYMINING_SELECTALGORITHM = "Please select atleast one algorithm" ;
    
    public static String COMMUNITYMINING_SELECTATTRIBUTE_TAG = "" ;
    public static String COMMUNITYMINING_SELECTATTRIBUTE = "Please select atleast one attribute" ;
    
    public static String COMMUNITYMINING_NUMBERCLUSTERSBETWEEN_TAG = "" ;
    public static String COMMUNITYMINING_NUMBERCLUSTERSBETWEEN = "Number of Clusters should be between\n" ;
    
    public static String COMMUNITYMINING_SIMILARITYTHRESHOLDBETWEEN_TAG = "" ;
    public static String COMMUNITYMINING_SIMILARITYTHRESHOLDBETWEEN = "Similarity Threshold should be between\n" ;
    
    public static String COMMUNITYMINING_SELECTMETRIC_TAG = "" ;
    public static String COMMUNITYMINING_SELECTMETRIC = "Select at least one metric" ;
    
    public static String COMMUNITYMINING_SELECTMETHOD_TAG = "" ;
    public static String COMMUNITYMINING_SELECTMETHOD = "Select at least one method" ;
    
    public static String COMMUNITYMINING_INSTABILITYBETWEEN_TAG = "" ;
    public static String COMMUNITYMINING_INSTABILITYBETWEEN = "Instability should be between\n" ;
    
    public static String COMMUNITYMINING_HISTORYBETWEEN_TAG = "" ;
    public static String COMMUNITYMINING_HISTORYBETWEEN = "History should be between\n" ;
    
    public static String COMMUNITYMINING_OUTLIERPERCENTAGEBETWEEN_TAG = "" ;
    public static String COMMUNITYMINING_OUTLIERPERCENTAGEBETWEEN = "Outlier Percentage should be between\n" ;
    
    public static String COMMUNITYMINING_HUBPERCENTAGEBETWEEN_TAG = "" ;
    public static String COMMUNITYMINING_HUBPERCENTAGEBETWEEN = "Hub Percentage should be between\n" ;
    
    public static String COMMUNITYMINING_CENTERDISTANCEBETWEEN_TAG = "" ;
    public static String COMMUNITYMINING_CENTERDISTANCEBETWEEN = "Center Distance should be between\n" ;
    
    public static String COMMUNITYMINING_SEPARATOREMPTY_TAG = "" ;
    public static String COMMUNITYMINING_SEPRATOREMPTY = "Separator cannot be empty" ;
    
    
}
