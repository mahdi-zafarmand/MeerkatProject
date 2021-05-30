/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *  Class Name      : LangConfig
 *  Created Date    : 2015-06-21
 *  Description     : Contains all the tags used to extract language specific parameters
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-08-23      Abhi            Added tags for text of remove Filter Button 
 *  2016-08-23      Abhi            Added tags for Algorithm params tab for Application Settings window
 *  2016-02-01      Talat           Added the Tags for Background Color Change of Canvas
 *  2016-01-18      Talat           Added the Tags for Tools Ribbon and Application Parameters
 *  2016-01-14      Talat           Moved the bunch of static fields for Error Messages from LangConfig class into ErrorMsgs class
 *  2016-01-04      Talat           Added the Tags for Graph Editing Tool
 *  2015-10-29      Talat           Added the Tags for Polarity, Emotion and Save
 *  2015-10-10      Talat           Added the Tags for Options
 * 
*/
public class LangConfig {
    
    /************** LANGUAGE SELECTION WINDOW *****************/
    public static String LANGSELECTWINDOWTITLE_TAG = "LangSelectWindowTitle";
    public static String LANGSELECTWINDOWLABEL_TAG = "LangSelectWindowLabel";
    public static String LANGSELECTWINDOWCHECKBOX_TAG = "LangSelectWindowCheckBox";
    public static String LANGSELECTWINDOWBUTTON_TAG = "LangSelectWindowSubmit";
    public static String LANGSELECTWINDOWICON_TAG = "LangSelectWindowIcon"; 
    
    
    /**************** WELCOME SCREEN WINDOW *****************/
    public static String WELCOMEWINDOWTITLE_TAG = "WelcomeScreenTitle";
    public static String WELCOMEWINDOWMESSAGE_TAG = "WelcomeScreenMessage";
    public static String WELCOMEWINDOWLOGO_TAG = "WelcomeScreenLogo";   
    
    
    /*************** EXIT CONFIRMATION DIALOG ***************/
    public static String EXITTITLE_TAG = "ExitTitle";
    public static String EXITMESSAGE_TAG = "ExitMessage";
    public static String EXITICONPATH_TAG = "ExitIconPath";
    
    /*************** EXIT CONFIRMATION DIALOG ***************/
    public static String PROJECTCLOSED_TAG = "ProjectClose" ;
    public static String PROJECTCLOSEDMESSAGE_TAG = "ProjectCloseMessage";
    public static String PROJECTCLOSEDNOTSAVEMESSAGE_TAG = "ProjectCloseAndNotSaveMessage";
    public static String PROJECTCLOSEDTITLE_TAG = "ExitMessage";
    public static String PROJECTCLOSEDICONPATH_TAG = "ExitIconPath";
    
    /*************** FEEDBACK CONFIRMATION DIALOG ***************/
    public static String FEEDBACKSELECTIONTITLE_TAG = "FeedbackSelectionTitle";
    public static String FEEDBACKSELECTIONMESSAGE_TAG = "FeedbackSelectionMessage";
    public static String FEEDBACKSELECTIONICONPATH_TAG = "FeedbackSelectionIconPath";
    
    /*************** NEW PROJECT WIZARD PARAMETERS *********************/
    public static String NEWPROJECTWIZARD_TAG = "NewProjectWizard";
    public static String CREATEPROJECTBUTTON_TAG = "CreateProject" ;
    public static String CANCEL_TAG = "Cancel";
    public static String NEWPROJECTPROMPTTEXT_TAG = "NewProjectPrompt";
    public static String NEWPROJECTLABEL_TAG = "NewProjectLabel";
    public static String NEWPROJECTFILEFOUNDERROR_TAG = "NewProjectErrorFileFound";
    
    // ************************ NEIGHBORHOOD DEGREE DIALOG ****************
    public static String NEIGHBORDEGREE_TITLE_TAG = "NeighborhoodDegreeTitle";
    public static String NEIGHBORDEGREE_ERROR_TAG = "NeighborhoodDegreeError" ;
    public static String NEIGHBORDEGREE_PROMPT_TAG = "NeighborhoodDegreePrompt" ;
        
    /**************** GENERAL TAGS AND MESSAGES *****************/
    public static String GENERAL_OK_TAG = "Ok";
    public static String GENERAL_OK ;
    public static String GENERAL_APPLY_TAG = "Apply";
    public static String GENERAL_APPLY ;
    public static String GENERAL_CANCEL_TAG = "Cancel";
    public static String GENERAL_CANCEL ;
    public static String GENERAL_CLOSE_TAG = "Close";
    public static String GENERAL_CLOSE ;
    public static String GENERAL_ERROR_TAG = "Error";
    public static String GENERAL_ERROR ;
    public static String GENERAL_EXPORT_TAG = "Export";
    public static String GENERAL_EXPORT ;
    public static String GENERAL_SAVE_TAG = "Save";
    public static String GENERAL_SAVE ;
    public static String GENERAL_SAVEALL_TAG = "SaveAll";
    public static String GENERAL_SAVEALL ;
    public static String GENERAL_DONTSAVE_TAG = "DontSave";
    public static String GENERAL_DONTSAVE ;
    public static String GENERAL_TRUE_TAG = "True";
    public static String GENERAL_TRUE ;
    public static String GENERAL_FALSE_TAG = "False";
    public static String GENERAL_FALSE ;
    public static String GENERAL_RUN_TAG = "Run";
    public static String GENERAL_RUN;
    public static String GENERAL_START_TAG = "Start";
    public static String GENERAL_START ;
    public static String GENERAL_STOP_TAG = "Stop";
    public static String GENERAL_STOP ;
    public static String GENERAL_YES_TAG = "Yes";
    public static String GENERAL_YES ;
    public static String GENERAL_NO_TAG = "No";
    public static String GENERAL_NO ;    
    public static String GENERAL_AND_TAG = "And";
    public static String GENERAL_AND ;
    public static String GENERAL_COMPUTE_TAG = "Compute";
    public static String GENERAL_COMPUTE ;
    public static String GENERAL_INFORMATION_TAG = "Information";
    public static String GENERAL_INFORMATION ;
    
    
    public static String GENERAL_ORPHANSINGULAR_TAG = "OrphanSingular";
    public static String GENERAL_ORPHANSINGULAR ;
    public static String GENERAL_ORPHANPLURAL_TAG = "OrphanPlural";
    public static String GENERAL_ORPHANPLURAL ;
    
    public static String GENERAL_EDGESINGULAR_TAG = "EdgeSingular" ;
    public static String GENERAL_EDGESINGULAR ;
    public static String GENERAL_EDGEPLURAL_TAG = "EdgePlural" ;
    public static String GENERAL_EDGEPLURAL ;
    public static String GENERAL_EDGEID_TAG = "EdgeID" ;
    public static String GENERAL_EDGEID ;
    
    public static String GENERAL_VERTEXSINGULAR_TAG = "VertexSingular" ;
    public static String GENERAL_VERTEXSINGULAR ;
    public static String GENERAL_VERTEXPLURAL_TAG = "VertexPlural" ;
    public static String GENERAL_VERTEXPLURAL ;
    public static String GENERAL_VERTEXID_TAG = "VertexID" ;
    public static String GENERAL_VERTEXID ;
        
    public static String GENERAL_COMMUNITYSINGULAR_TAG = "CommunitySingular" ;
    public static String GENERAL_COMMUNITYSINGULAR ;
    public static String GENERAL_COMMUNITYPLURAL_TAG = "CommunityPlural" ;
    public static String GENERAL_COMMUNITYPLURAL ;
    public static String GENERAL_COMMUNITYID_TAG = "CommunityID" ;
    public static String GENERAL_COMMUNITYID ;
    public static String GENERAL_COMMUNITYSUB_TAG = "SubCommunity" ;
    public static String GENERAL_COMMUNITYSUB ;
    public static String GENERAL_FILTER_TAG = "Filter" ;
    public static String GENERAL_FILTER ;
    
    public static String GENERAL_ENABLEVISUALIZATION_TAG = "EnableVisualization" ;
    public static String GENERAL_ENABLEVISUALIZATION ;
    public static String GENERAL_DISABLEVISUALIZATION_TAG = "DisbleVisualization" ;
    public static String GENERAL_DISABLEVISUALIZATION ;
    
    public static String GENERAL_AREYOUSURE_TAG = "AreYouSureVertex" ;
    public static String GENERAL_AREYOUSURE ;
    
    public static String GENERAL_AREYOUSURE_EDGE_TAG = "AreYouSureEdge" ;
    public static String GENERAL_AREYOUSURE_EDGE ;
       
    /**************** INFO MESSAGES *******************/
    public static String INFO_PROJECTSAVED_TAG = "ProjectSaved";
    public static String INFO_PROJECTSAVED ;
    
    public static String INFO_GRAPHEXPORTED_TAG = "GraphExported" ;
    public static String INFO_GRAPHEXPORTED ;
    
    public static String INFO_SNAPSHOTSAVED_TAG = "SnapshotSaved" ;
    public static String INFO_SNAPSHOTSAVED ;
    
    public static String INFO_MININGRESULTSCLEARED_TAG = "MiningResultsCleared" ;
    public static String INFO_MININGRESULTSCLEARED ;
    
    public static String INFO_LINKPREDICTIONRESULTSCLEARED_TAG = "LinkPredictionResultsCleared";
    public static String INFO_LINKPREDICTIONRESULTSCLEARED ;
    
    public static String INFO_SELECTVERTEX_TAG = "SelectVertex" ;
    public static String INFO_SELECTVERTEX ;
    
    public static String INFO_SELECTEDGE_TAG = "SelectEdge" ;
    public static String INFO_SELECTEDGE ;
    
    public static String INFO_SETTINGS_TAG = "Settings" ;
    public static String INFO_SETTINGS ;
    
    public static String INFO_MININGRESULTSCOMPUTED_TAG = "MiningResultsComputed" ;
    public static String INFO_MININGRESULTSCOMPUTED ;
    
    public static String INFO_CANVAS_VISUALALREADYENABLED_TAG = "InfoVisualAlreadyEnabled" ;
    public static String INFO_CANVAS_VISUALALREADYENABLED ;
    
    public static String INFO_CANVAS_VISUALALREADYDISABLED_TAG = "InfoVisualAlreadyDisabled" ;
    public static String INFO_CANVAS_VISUALALREADYDISABLED ;
    
    public static String INFO_NODENEIGHBORHOOD_WARNING_TAG = "SelectAtLeastOneVertex" ;
    public static String INFO_NODENEIGHBORHOOD_WARNING ;
        
    /******************* LABELS **********************/
    // #DEPRECATED
    public static String LABEL_FILTER_VERTEXATTRIBUTE_TAG = "LabelVertexAttribute";
    public static String LABEL_FILTER_EDGEATTRIBUTE_TAG = "LabelEdgeAttribute";
    // #END-DEPRECATED
    
    /******************  BUTTON TEXT *****************/
    public static String BTNTXT_FILTER_VERTEXATTRIBUTE_TAG = "ButtonAddVertexAttribute";
    public static String BTNTXT_FILTER_EDGEATTRIBUTE_TAG = "ButtonAddEdgeAttribute";
    public static String BTNTXT_FILTER_REMOVE_VERTEXATTRIBUTE_TAG = "ButtonRemoveVertexAttribute";
    public static String BTNTXT_FILTER_REMOVE_EDGEATTRIBUTE_TAG = "ButtonRemoveEdgeAttribute";
    public static String BTNTXT_APPLYFILTER_ATTRTAG = "ButtonApplyFilter" ;
    
    /******************  OPTIONS *****************/
    public static String OPTIONS_TAG = "Options" ;
    
    public static String OPTIONS_LOADPROJECT_TAG = "LoadProject" ;
    public static String OPTIONS_LOADPROJECT ;    
    public static String OPTIONS_NEWPROJECT_TAG = "NewProject" ;
    public static String OPTIONS_NEWPROJECT ;    
    public static String OPTIONS_REMOVEPROJECT_TAG = "RemoveProject" ;
    public static String OPTIONS_REMOVEPROJECT ;
    public static String OPTIONS_RENAMEPROJECT_TAG = "RenameProject" ;
    public static String OPTIONS_RENAMEPROJECT ;
    
    public static String OPTIONS_LOADGRAPH_TAG = "LoadGraph" ;
    public static String OPTIONS_LOADGRAPH ;    
    public static String OPTIONS_NEWGRAPH_TAG = "NewGraph" ;
    public static String OPTIONS_NEWGRAPH ;    
    public static String OPTIONS_REMOVEGRAPH_TAG = "RemoveGraph" ;
    public static String OPTIONS_REMOVEGRAPH ;
    public static String OPTIONS_RENAMEGRAPH_TAG = "RenameGraph" ;
    public static String OPTIONS_RENAMEGRAPH ;
    
    public static String OPTIONS_LOADTEXTUALGRAPH_TAG = "LoadTextualGraph" ;
    public static String OPTIONS_LOADTEXTUALGRAPH ;    
    public static String OPTIONS_NEWTEXTUALGRAPH_TAG = "NewTextualGraph" ;
    public static String OPTIONS_NEWTEXTUALGRAPH ;    
    public static String OPTIONS_REMOVETEXTUALGRAPH_TAG = "RemoveTextualGraph" ;
    public static String OPTIONS_REMOVETEXTUALGRAPH ;
    public static String OPTIONS_RENAMETEXTUALGRAPH_TAG = "RenameTextualGraph" ;
    public static String OPTIONS_RENAMETEXTUALGRAPH ;
    
    public static String OPTIONS_ADDNODE_TAG = "AddNode" ;
    public static String OPTIONS_ADDNODE ;    
    public static String OPTIONS_NEWNODE_TAG = "NewNode" ;
    public static String OPTIONS_NEWNODE ;    
    public static String OPTIONS_REMOVENODE_TAG = "RemoveNode" ;
    public static String OPTIONS_REMOVENODE ;
    
    public static String OPTIONS_ADDEDGE_TAG = "AddEdge" ;
    public static String OPTIONS_ADDEDGE ;    
    public static String OPTIONS_NEWEDGE_TAG = "NewEdge" ;
    public static String OPTIONS_NEWEDGE ;    
    public static String OPTIONS_REMOVEEDGE_TAG = "RemoveEdge" ;
    public static String OPTIONS_REMOVEEDGE ;
    
    public static String OPTIONS_GENERATEGRAPH_TAG = "GenerateGraph" ;
    public static String OPTIONS_GENERATEGRAPH ;
    
    public static String OPTIONS_ADDSUBTHREADMENU_TAG = "AddSubThread";
    public static String OPTIONS_ADDSUBTHREADMENU ;
    
    public static String OPTIONS_ADDCONTENT_TAG = "AddContent" ;
    public static String OPTIONS_ADDCONTENT ;
    
    public static String OPTIONS_EMOTION_TAG = "EmotionAnalysis" ;
    public static String OPTIONS_EMOTION ;
    
    public static String OPTIONS_POLARITY_TAG = "PolarityAnalysis" ;
    public static String OPTIONS_POLARITY ;
    
    public static String OPTIONS_WORDCLOUD_TAG = "WordCloud" ;
    public static String OPTIONS_WORDCLOUD ;
    
    public static String OPTIONS_TOPICMODELLING_TAG = "TopicModelling" ;
    public static String OPTIONS_TOPICMODELLING ;
    
    /* ******************   POLARITY    ******************* */
    public static String POLARITY_CONTENT_TAG = "PolarityContent";
    public static String POLARITY_TITLE_TAG = "Title";
    public static String POLARITY_FEEDBACK_TAG = "Feedback";    
    public static String POLARITY_SENTENCE_TAG = "SentencePolarity" ;
    public static String POLARITY_WORD_TAG = "WordPolarity";
    public static String POLARITY_STRONGPOSITIVE_TAG = "StronglyPositive";
    public static String POLARITY_POSITIVE_TAG = "Positive";
    public static String POLARITY_NEUTRAL_TAG = "Neutral";
    public static String POLARITY_NEGATIVE_TAG = "Negative";
    public static String POLARITY_STRONGNEGATIVE_TAG = "StronglyNegative";
    public static String POLARITY_TOTAL_TAG = "TotalPolarity";       
    
    /* ******************   EMOTION     ******************* */
    public static String EMOTION_CONTENT_TAG = "EmotionContent";
    public static String EMOTION_TITLE_TAG = "Title";
    public static String EMOTION_SURPRISE_TAG = "Surprise";
    public static String EMOTION_ANGER_TAG = "Anger";
    public static String EMOTION_SADNESS_TAG = "Sadness";
    public static String EMOTION_THANK_TAG = "Thank";
    public static String EMOTION_FEAR_TAG = "Fear";    
    public static String EMOTION_JOY_TAG = "Joy";
    public static String EMOTION_LOVE_TAG = "Love";
    public static String EMOTION_DISGUST_TAG = "Disgust";
    public static String EMOTION_GUILT_TAG = "Guilt";
    
    /* ******************   NODE CONTEXT     ******************* */
    public static String NODECTX_MENU_TAG = "NodeContextMenu";
    public static String NODECTX_INFO_TAG = "NodeInfo";
    public static String NODECTX_NEIGHBOR_TAG = "NodeNeighbor";
    public static String NODECTX_STYLE_TAG = "Style";
    public static String NODECTX_SHAPE_TAG = "Shape";
    public static String NODECTX_SIZE_TAG = "Size";
    public static String NODECTX_LABELSIZE_TAG = "LabelSize";
    public static String NODECTX_COLOR_TAG = "Color";
    public static String NODECTX_DELETE_TAG = "NodeDelete";
    public static String NODECTX_PIN_TAG = "NodePin";
    public static String NODECTX_UNPIN_TAG = "NodeUnpin";
    public static String NODECTX_EXTRACT_TAG = "NodeExtract" ;
    
    /* ******************   EDGE CONTEXT     ******************* */
    public static String EDGECTX_MENU_TAG = "EdgeContextMenu";
    public static String EDGECTX_INFO_TAG = "EdgeInfo";
    public static String EDGECTX_STYLE_TAG = "Style";
    public static String EDGECTX_LINE_TAG = "LineStyle";
    public static String EDGECTX_WIDTH_TAG = "Width";
    public static String EDGECTX_COLOR_TAG = "Color";
    public static String EDGECTX_DELETE_TAG = "Delete";
    
    
    /* ******************   CANVAS CONTEXT     ******************* */
    public static String CANVASCTX_MENU_TAG = "CanvasContextMenu" ;
    public static String CANVASCTX_PINVERTICES_TAG = "PinVertices" ;
    public static String CANVASCTX_UNPINVERTICES_TAG = "UnpinVertices" ;
    public static String CANVASCTX_SHOWHIDEMINIMAP_TAG = "ShowHideMinimap" ;
    public static String CANVASCTX_CHANGEBGCOLOR_TAG = "ChangeBGColor" ;
    public static String CANVASCTX_CHANGEBGIMAGE_TAG = "ChangeBGImage" ;
    public static String CANVASCTX_CLEARBG_TAG = "ClearBGImage" ;
    public static String CANVASCTX_EXTRACTSUBGRAPH_TAG = "ExtractSubGraph" ;
    public static String CANVASCTX_LINKPREDICTION_TAG = "LinkPredictionMenu" ;
    public static String CANVASCTX_REMOVENODE_TAG = "RemoveNode" ;
    public static String CANVASCTX_REMOVEEDGE_TAG = "RemoveEdge" ;
    public static String CANVASCTX_VERTEXINFO_TAG = "VertexInfo" ;
    
    /* ******************   COMMUNITY CONTEXT     ******************* */
    public static String CMNTCTX_MENU_TAG = "CommunityContextMenu" ;
    public static String CMNTCTX_PINVERTICES_TAG = "PinVertices" ;
    public static String CMNTCTX_UNPINVERTICES_TAG = "UnpinVertice" ;
    public static String CMNTCTX_EXTRACTGRAPH_TAG = "ExtractGraph" ;
    public static String CMNTCTX_SAVECOMMUNITY_TAG=  "SaveCommunity" ;
    
    /* ******************   COMMUNITIES CONTEXT     ******************* */
    public static String CMNTSCTX_MENU_TAG = "CommunitiesContextMenu" ;
    public static String CMNTSCTX_SAVECOMMUNITIES_TAG = "SaveCommunities" ;
    public static String CMNTSCTX_EXTRACTGRAPH_TAG = "ExtractGraph" ;
    public static String CMNTSCTX_MININGALGORITHM_TAG = "MiningAlgorithms" ;
    public static String CMNTSCTX_CLEARMININGRESULTS_TAG = "ClearMiningResults" ;
    public static String CMNTSCTX_KMEANS_TAG = "KMeansAlgorithm" ;
    public static String CMNTSCTX_FASTMODULARITY_TAG = "FastModularityAlgorithm" ;
    public static String CMNTSCTX_SAMEATTRIBUTEVALUE_TAG = "SameAttributeValueAlgorithm" ;
    public static String CMNTSCTX_LOCALT_TAG = "LocalTAlgorithm" ;
    public static String CMNTSCTX_LOCALTOP_TAG = "LocalTopAlgorithm" ;
    public static String CMNTSCTX_LOCALCOMMUNITY_TAG = "LocalCommunityAlgorithm" ;
    public static String CMNTSCTX_ROSVALLINFOMAP_TAG = "RosvallInfomapAlgorithm" ;
    public static String CMNTSCTX_ROSVALLINFOMOD_TAG = "RosvallInfomodAlgorithm" ;
    public static String CMNTSCTX_DYNAMICCOMMUNITY_TAG = "DynamicCommunityAlgorithm" ;
    
    /* ******************   GRAPH EDITING TOOLS    ******************* */
    public static String GRAPHEDITINGTOOLS_TAG = "GraphEditingTools";
    
    public static String GRAPHEDITINGTOOLS_HEADER = "Header";
    
    public static String ADDEDGE_TOOLTIP_TAG = "AddEdgeToolTip" ;
    public static String ADDEDGE_IMAGEURL_TAG = "AddEdgeImageURL";
    
    public static String ADDVERTEX_TOOLTIP_TAG = "AddVertexToolTip";
    public static String ADDVERTEX_IMAGEURL_TAG = "AddVertexImageURL";
    
    public static String ADDVERTEXMULTIPLETIMEFRAMES_TOOLTIP_TAG = "AddVertexMultipleTimeFramesToolTip";
    public static String ADDVERTEXMULTIPLETIMEFRAMES_IMAGEURL_TAG = "AddVertexMultipleTimeFramesImageURL";
    
    public static String DELETEVERTEX_TOOLTIP_TAG = "DeleteVertexToolTip";
    public static String DELETEVERTEX_IMAGEURL_TAG = "DeleteVertexImageURL";
    
    public static String DELETEEDGE_TOOLTIP_TAG = "DeleteEdgeToolTip";
    public static String DELETEEDGE_IMAGEURL_TAG = "DeleteEdgeImageURL";
    
    public static String SELECT_TOOLTIP_TAG = "SelectToolTip";
    public static String SELECT_IMAGEURL_TAG = "SelectImageURL";
    
    public static String SHORTESTPATH_TOOLTIP_TAG = "ShortestPathToolTip";
    public static String SHORTESTPATH_IMAGEURL_TAG = "ShortestPathImageURL";
    
    public static String SELECTMULTI_TOOLTIP_TAG = "SelectMultiToolTip";
    public static String SELECTMULTI_IMAGEURL_TAG = "SelectMultiImageURL";
    
    public static String EDGESIZE_IMAGEURL_TAG = "EdgeImageURL";
    public static String EDGESIZE_TOOLTIP_TAG = "EdgeTooltipURL";
    
    public static String VERTEXSIZE_IMAGEURL_TAG = "VertexImageURL";
    public static String VERTEXSIZE_TOOLTIP_TAG = "VertexTooltipURL";
    
    public static String VERTEXCOLOR_IMAGEURL_TAG = "VertexColorImageURL";
    public static String VERTEXCOLOR_TOOLTIP_TAG = "VertexColorTooltipURL";
    
    public static String EDGECOLOR_IMAGEURL_TAG = "EdgeColorImageURL";
    public static String EDGECOLOR_TOOLTIP_TAG = "EdgeColorTooltipURL";
    
    
    /* ******************   SNASPSHOT TOOLS    ******************* */
    public static String SNAPSHOTTOOLS_TAG = "SnapshotTools";
    
    public static String SNAPSHOTTOOLS_HEADER = "Header";    
    public static String ALLPROJECTS_LABEL_TAG = "AllProjectsLabel" ;
            
    public static String APPSNAPSHOT_TOOLTIP_TAG = "AppSnapshotToolTip" ;
    public static String APPSNAPSHOT_IMAGEURL_TAG = "AppSnapshotImageURL";
    
    public static String ALLGRAPHONEPROJECT_TOOLTIP_TAG = "AllOpenGraphsActiveProjectToolTip";
    public static String ALLGRAPHONEPROJECT_IMAGEURL_TAG = "AllOpenGraphActiveProjectImageURL";
    
    public static String ALLGRAPHALLPROJECTS_TOOLTIP_TAG = "AllOpenGraphsAllProjectsToolTip";
    public static String ALLGRAPHALLPROJECTS_IMAGEURL_TAG = "AllOpenGraphsAllProjectsImageURL";
    
    public static String CURRENTGRAPHCOMPLETE_TOOLTIP_TAG = "CurrentGraphCompleteToolTip";
    public static String CURRENTGRAPHCOMPLETE_IMAGEURL_TAG = "CurrentGraphCompleteImageURL";
    
    public static String CURRENTGRAPHVISIBLE_TOOLTIP_TAG = "CurrentGraphViewOnlyToolTip";
    public static String CURRENTGRAPHVISIBLE_IMAGEURL_TAG = "CurrentGraphViewOnlyImageURL";
    
    /* ******************   ADD EDGE BASED ON ATTRIBUTE DIALOG BOX   ******************* */
    public static String ADDEDGEATTRIBUTE_TAG = "AddEdgeAttribute" ;
    public static String ADDEDGEATTRIBUTE_HEADER_TAG = "Header";
    public static String ADDEDGEATTRIBUTE_INFO_TAG = "Information";
    public static String ADDEDGEATTRIBUTE_IMAGEURL_TAG = "ImageURL" ;
    public static String ADDEDGEATTRIBUTE_ATTRIBUTE_TAG = "Attribute" ;
    public static String ADDEDGEATTRIBUTE_EDGETYPE_TAG = "EdgeType" ;
    public static String ADDEDGEATTRIBUTE_ATTRIBUTENAME_TAG = "AttributeName" ;
    public static String ADDEDGEATTRIBUTE_ATTRIBUTEVALUE_TAG = "AttributeValue" ;
    public static String ADDEDGEATTRIBUTE_MULTIPLEVALUES_TAG = "MultipleValues" ;
    public static String ADDEDGEATTRIBUTE_SEPARATOR_TAG = "Separator" ;
    
    
    /* ******************   ADD EDGE BASED ON ATTRIBUTE SIMILARITY DIALOG BOX   ******************* */
    public static String ADDEDGEATTRSIMILAIRTY_TAG = "AddEdgeAttributeSimilairty";
    public static String ADDEDGEATTRSIMILAIRTY_HEADER_TAG = "Header";
    public static String ADDEDGEATTRSIMILAIRTY_INFO_TAG = "Information";
    public static String ADDEDGEATTRSIMILAIRTY_IMAGEURL_TAG = "ImageURL" ;
    public static String ADDEDGEATTRSIMILAIRTY_ATTRIBUTE_TAG = "Attribute" ;
    public static String ADDEDGEATTRSIMILAIRTY_SIMILARITY_TAG = "Similarity" ;
    public static String ADDEDGEATTRSIMILAIRTY_THRESHOLD_TAG = "Threshold" ;
    public static String ADDEDGEATTRSIMILARITY_THRESHOLDERROR_TAG = "ThresholdError" ;
    
    
    /* ******************   TOOLS RIBBON CONTEXT MENU   ******************* */
    public static String TOOLSRIBBONMENU_TAG = "ToolsRibbonContextMenu" ;
    public static String TOOLSRIBBONMENU_HIDE_TAG = "HideRibbon" ;
    
    // ******************   VERTEX LABEL SELECT ATTRIBUTE DIALOG     *****************
    public static String VERTEXLABEL_TAG = "VertexLabelSelectAttribute";
    public static String VERTEXLABEL_TITLE_TAG = "Title" ;
    public static String VERTEXLABEL_TITLE ;
    public static String VERTEXLABEL_MSG_TAG = "Message" ;    
    public static String VERTEXLABEL_MSG ;
    
    // ******************   VERTEX TOOLTIP SELECT ATTRIBUTE DIALOG     *****************
    public static String VERTEXTOOLTIP_TAG = "VertexTooltipSelectAttribute";
    public static String VERTEXTOOLTIP_TITLE_TAG = "Title" ;
    public static String VERTEXTOOLTIP_TITLE ;
    public static String VERTEXTOOLTIP_MSG_TAG = "Message" ;    
    public static String VERTEXTOOLTIP_MSG ;
    
    
    /* ******************   FILTER OPERATORS   ******************* */
    public static String FILTEROPERATORS_TAG = "FilterOperators" ;
    public static String FILTEROPERATORS_OPERATOR_TAG = "Operator" ;
    public static String FILTEROPERATORS_ATTR_VALUE = "value" ;
    public static String FILTEROPERATORS_ATTR_TEXT = "text" ;
    public static String FILTEROPERATORS_ATTR_TYPE = "type" ;
    public static String FILTEROPERATORS_VALUE_NUMERIC = "numeric" ;
    public static String FILTEROPERATORS_VALUE_NONNUMERIC = "nonnumeric" ;
    
    
    /* ******************   APPLICATION PARAMETERS   ******************* */
    public static String APPPARAMETERS_TAG = "ApplicationParameters" ;
    public static String APPPARAMETERS_OS_TAG = "OS" ;
    public static String APPPARAMETERS_PROCESSORS_TAG = "Processors" ;
    public static String APPPARAMETERS_TOTALMEMORY_TAG = "TotalMemory" ;
    public static String APPPARAMETERS_JVMVERSION_TAG = "JVMVersion" ;
    public static String APPPARAMETERS_TOTALJVMMEMORY_TAG = "TotalJVMMemory" ;
    public static String APPPARAMETERS_USEDJVMMEMORY_TAG = "UsedJVMMemory" ;
    public static String APPPARAMETERS_FREEJVMMEMORY_TAG = "FreeJVMMemory" ;
    
    /* ******************   ABOUT MEERKAT   ******************* */
    public static String ABOUTMEERKAT_TAG = "AboutMeerkat" ;
    
    /* ******************   DOCUMENTATION MEERKAT   ******************* */
    public static String DOCUMENTATIONMEERKAT_TAG = "DocumentationMeerkat" ;
    
    
    /* ******************   BACKGROUND COLOR CHANGE   ******************* */
    public static String BGCOLORCHANGE_TAG = "BackgroundColorChange" ;
    public static String BGCOLORCHANGE_TITLE_TAG = "Title" ;
    public static String BGCOLORCHANGE_INFO_TAG = "Information" ;
    public static String BGCOLORCHANGE_ERROR_TAG = "Error" ;
    
    /* *****************    VERTEX SHAPES ********************** */
    public static String VERTEXSHAPES_TAG = "VertexShapes" ;
    public static String VERTEXSHAPES_SQUARE_TAG = "Square" ;
    public static String VERTEXSHAPES_SQUARE ;
    public static String VERTEXSHAPES_RECTANGLE_TAG = "Rectangle" ;
    public static String VERTEXSHAPES_RECTANGLE ;
    public static String VERTEXSHAPES_ELLIPSE_TAG = "Ellipse" ;
    public static String VERTEXSHAPES_ELLIPSE  ;
    public static String VERTEXSHAPES_CIRCLE_TAG = "Circle" ;
    public static String VERTEXSHAPES_CIRCLE ;    
    public static String VERTEXSHAPES_SPHERE_TAG = "Sphere" ;
    public static String VERTEXSHAPES_SPHERE ;
    public static String VERTEXSHAPES_CUBOID_TAG = "Cuboid" ;
    public static String VERTEXSHAPES_CUBOID ;
    public static String VERTEXSHAPES_CUBE_TAG = "Cube" ;
    public static String VERTEXSHAPES_CUBE ;
    
    /* *****************    LINK PREDICTION     ********************** */
    public static String LINKPREDICTION_TAG = "LinkPrediction";
    public static String LINKPREDICTION_TITLE_TAG = "Title";
    public static String LINKPREDICTION_TOPN_TAG = "TopN";
    public static String LINKPREDICTION_METRIC_TAG = "Metric";
    public static String LINKPREDICTION_TOPN_ERROR_TAG = "TopNError";
    public static String LINKPREDICTION_METRIC_ERROR_TAG = "MetricError";
    public static String LINKPREDICTION_TOPN_TOOLTIP_TAG = "TopNEditorTooltip";
    public static String LINKPREDICTION_METRIC_TOOLTIP_TAG = "MetricEditorTooltip";
    
    /* *****************    SETTINGS WINDOW     ********************** */
    
    //-- ui tab params --//
    public static String SETTINGSWINDOW_TAG = "SettingsWindow" ;
            
    public static String SETTINGSWINDOW_TITLE_TAG = "Title" ;    
    public static String SETTINGSWINDOW_UITABTITLE_TAG = "UITabTitle" ;
    public static String SETTINGSWINDOW_ALGORITHMTABTITLE_TAG = "AlgorithmParametersTabTitle" ;
    
    public static String UITAB_VERTEXCOLORDEFAULT_TAG = "VertexColorDefault" ;
    public static String UITAB_VERTEXTCOLORSELECTED_TAG = "VertexColorSelected" ;
    public static String UITAB_VERTEXSHAPEDEFAULT_TAG = "VertexShapeDefault" ;
    public static String UITAB_VERTEXSIZEDEFAULT_TAG = "VertexSizeDefault" ;
    public static String UITAB_VERTEXOPACITYDEFAULT_TAG = "VertexOpacityDefault" ;
    public static String UITAB_VERTEXLABELFONTSIZE_TAG = "VertexLabelFontSizeDefault" ;
    
    public static String UITAB_EDGECOLORPRIMARY_TAG = "EdgeColorPrimary" ;
    public static String UITAB_EDGECOLORSECONDARY_TAG = "EdgeColorSecondary" ;
    public static String UITAB_EDGECOLORTERTIARY_TAG = "EdgeColorTertiary" ;
    public static String UITAB_EDGEOPACITY_TAG = "EdgeOpacity" ;
    public static String UITAB_EDGESTROKEWIDTH_TAG = "EdgeStrokeWidth" ;
    
    public static String UITAB_CANVASBGCOLORDEFAULT_TAG = "CanvasBackgroundColorDefault" ;
    public static String UITAB_CANVASDRAGRECTCOLOR_TAG = "CanvasDragRectColor" ;    
    public static String UITAB_CANVASDRAGRECTOPACITY_TAG = "CanvasDragRectOpacity" ;    
    public static String UITAB_CANVASZOOMFACTOR_TAG = "CanvasZoomFactor" ;
    
    // -- Algorithms Params Tab -- //
    // k-means clustering
    public static String ALGOPARAMTAB_KMEANSCLUSTERCOUNT_TAG = "KMeansDefaultClusters";
    // Same Attribute value
    public static String ALGOPARAMTAB_SAMEATTRIBUTEVALUE_MULTIPLEVALUES_TAG = "SameAttributeMultipleValues";
    // Fast Modularity
    public static String ALGOPARAMTAB_FASTMODULARITY_ALGORITHM_TAG = "FastModularityAlgorithm";
    public static String ALGOPARAMTAB_FASTMODULARITY_WEIGHTED_TAG = "FastModularityWeighted";
    // Local Top Leader
    public static String ALGOPARAMTAB_LOCALTOPLEADERS_CLUSTERCOUNT_TAG = "LocalTopLeadersDefaultClusters";
    public static String ALGOPARAMTAB_LOCALTOPLEADERS_OUTLIERPERCENTAGE_TAG = "LocalTopLeadersDefaultOutlierPercentage";
    public static String ALGOPARAMTAB_LOCALTOPLEADERS_HUBPERCENTAGE_TAG = "LocalTopLeadersDefaultHubPercentage";
    public static String ALGOPARAMTAB_LOCALTOPLEADERS_CENTERDISTANCE_TAG = "LocalTopLeadersDefaultCenterDistance";
    // Local Community 
    public static String ALGOPARAMTAB_LOCALCOMMUNITY_ALGORITHM_TAG = "LocalCommunityAlgorithm"; 
    public static String ALGOPARAMTAB_LOCALCOMMUNITY_OVERLAP_TAG = "LocalCommunityOverlap"; 
    // RosvallInfoMap
    public static String ALGOPARAMTAB_ROSVALLINFOMAP_ATTEMPTS_TAG = "RosvallInfoMapNoOfAttempts";  
    public static String ALGOPARAMTAB_ROSVALLINFOMAP_ISDIRECTED_TAG = "RosvallInfoMapIsDirected";   
    // RosvallInfoMod
    public static String ALGOPARAMTAB_ROSVALLINFOMOD_ATTEMPTS_TAG = "RosvallInfoModNoOfAttempts";
    // Dynamic Community Mining
    public static String ALGOPARAMTAB_DCMINING_SIMILARITYTHRESHOLD_TAG = "DCMiningSimilarityThreshold";
    public static String ALGOPARAMTAB_DCMINING_METRIC_TAG = "DCMiningMetric";
    public static String ALGOPARAMTAB_DCMINING_METHOD_TAG = "DCMiningMethod";
    public static String ALGOPARAMTAB_DCMINING_INSTABILITY_TAG = "DCMiningInstability";
    public static String ALGOPARAMTAB_DCMINING_HISTORY_TAG = "DCMiningHistory";
    public static String ALGOPARAMTAB_DCMINING_OVERLAP_TAG = "DCMiningOverlap";
    public static String ALGOPARAMTAB_DCMINING_HUBS_TAG = "DCMiningHubs";
    
    
    
    
    
    private static String strProjectTitle;
    public static void setProjectTitle(String pstrProjectTitle){
        LangConfig.strProjectTitle=pstrProjectTitle;
    }
    public static String getProjectTitle(){
        return LangConfig.strProjectTitle;
    }    
    
}
