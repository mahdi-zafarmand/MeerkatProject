package config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author aabnar
 */
public class MeerkatSystem {
    /* OS properties */

    private static final String OS_BIT = System.getProperty("os.arch").equals(
            "amd64") ? "64" : "32";

    private static final String OS_TYPE
            = System.getProperty("os.name").toLowerCase().contains("window")
                    ? ".exe" : (
            System.getProperty("os.name").toLowerCase().contains("mac")
            ? "_osx" : (
            System.getProperty("os.name").toLowerCase().contains("linux")
            ? "" : ""));

    /* System Attribute Names
     * ------------------------
     * All System Attributes should start with "SYS:"
     * and the keyword SYS should not be allowed to be used as 
     * a part of attribute names in data files. (reserve for Meerkat use)
     * */

    /**
     *
     */
    
    public static final String FILE_ID = "File_ID";
    public static final String SYSTEMATTRIBUTE = "SYS:";
    public static final String MEERKAT_ID = SYSTEMATTRIBUTE + "MEERKATID";
    public static final String SOURCE = SYSTEMATTRIBUTE + "SOURCE";
    public static final String DESTINATION = SYSTEMATTRIBUTE + "DESTINATION";
    public static final String COLOR = SYSTEMATTRIBUTE + "COLOR";
    public static final String DIRECTED = SYSTEMATTRIBUTE + "DIRECTED";
    public static final String PREDICTED = SYSTEMATTRIBUTE + "PREDICTED";
    public static final String COMMUNITY = SYSTEMATTRIBUTE + "COMMUNITY";
    public static final String TYPE = SYSTEMATTRIBUTE + "TYPE";
    public static final String TYPEICONURL = SYSTEMATTRIBUTE + "TYPEICONURL";
    public static final String ISPREDICTED = SYSTEMATTRIBUTE + "PREDICTED";
    public static final String LAYOUT = SYSTEMATTRIBUTE + "LAYOUT";
    public static final String X = SYSTEMATTRIBUTE + "X";
    public static final String Y = SYSTEMATTRIBUTE + "Y";
    public static final String Z = SYSTEMATTRIBUTE + "Z";
    public static final String INDEGREE = SYSTEMATTRIBUTE + "METRIC:INDEGREE";
    public static final String OUTDEGREE = SYSTEMATTRIBUTE + "METRIC:OUTDEGREE";
    public static final String BETWEENNESS = SYSTEMATTRIBUTE + "METRIC:BETWEENNESS";
    public static final String PAGERANK = SYSTEMATTRIBUTE + "METRIC:PAGERANK";
    public static final String HUB = SYSTEMATTRIBUTE + "METRIC:HUB";
    public static final String AUTHORITY = SYSTEMATTRIBUTE + "METRIC:AUTHORITY";
    public static final String CLOSENESS = SYSTEMATTRIBUTE + "METRIC:CLOSENESS";
    public static final String SENTIMENTSCORE = SYSTEMATTRIBUTE + "SENTIMENTSCORE";
    public static final String POLARITY = SYSTEMATTRIBUTE + "POLARITY";
    public static final String EMOTION = SYSTEMATTRIBUTE + "EMOTION";
    public static final String WEAKCONNECTEDCOMPONENT = SYSTEMATTRIBUTE + "WEAKCONNECTEDCOMPONENT";
    public static final String STRONGCONNECTEDCOMPONENT = SYSTEMATTRIBUTE + "STRONGCONNECTEDCOMPONENT";
    
    public static final String DELIMITER_CSV = ",";

    
    public static final Set<String> SystemAttributes = new HashSet<>();
    
    static {
        SystemAttributes.add(FILE_ID);
        SystemAttributes.add(MEERKAT_ID);
        SystemAttributes.add(SOURCE);
        SystemAttributes.add(DESTINATION);
        SystemAttributes.add(AUTHORITY);
        SystemAttributes.add(BETWEENNESS);
        SystemAttributes.add(CLOSENESS);
        SystemAttributes.add(COLOR);
        SystemAttributes.add(TYPE);
        SystemAttributes.add(TYPEICONURL);
        SystemAttributes.add(ISPREDICTED);
        SystemAttributes.add(COMMUNITY);
        SystemAttributes.add(EMOTION);
        SystemAttributes.add(HUB);
        SystemAttributes.add(INDEGREE);
        SystemAttributes.add(LAYOUT);
        SystemAttributes.add(MEERKAT_ID);
        SystemAttributes.add(OUTDEGREE);
        SystemAttributes.add(PAGERANK);
        SystemAttributes.add(POLARITY);
        SystemAttributes.add(SENTIMENTSCORE);
        SystemAttributes.add(STRONGCONNECTEDCOMPONENT);        
        SystemAttributes.add(WEAKCONNECTEDCOMPONENT);
        SystemAttributes.add(X);
        SystemAttributes.add(Y);
        SystemAttributes.add(Z);
    }
    
    /**
     * Community Colors List
     */
    private static List<String> COLORS_COMMUNITIES = Arrays.asList(
                  "#FFB300" // Vivid Yellow
                , "#803E75" // Strong Purple
                , "#FF6800" // Vivid Orange
                , "#A6BDD7" // Very Light Blue
                , "#C10020" // Vivid Red
                , "#CEA262" // Grayish Yellow
                , "#817066" // Medium Gray
                // The following don't work well for people with defective color vision
                , "#007D34" // Vivid Green
                , "#F6768E" // Strong Purplish Pink
                , "#00538A" // Strong Blue
                , "#FF7A5C" // Strong Yellowish Pink
                , "#53377A" // Strong Violet
                , "#FF8E00" // Vivid Orange Yellow
                , "#B32851" // Strong Purplish Red
                , "#F4C800" // Vivid Greenish Yellow
                , "#7F180D" // Strong Reddish Brown
                , "#93AA00" // Vivid Yellowish Green
                , "#593315" // Deep Yellowish Brown
                , "#F13A13" // Vivid Reddish Orange
                , "#232C16" // Dark Olive Green
            );
    
    /* defaults */

    /**
     *
     */
    
    public static final String DEFAULT_TIMEFRAME = "DEFAULT_TIMEFRAME";

    /* Tags that would be used in the config */
    public static final String APPLICATION_ROOT_TAG = "app";
    public static final String CLASS_TAG = "class";
    public static final String ID_ATTRIBUTE_TAG = "id";
    public static final String MEERKAT_TAG = "meerkat";
    public static final String LAYOUTS_TAG = "layouts";
    public static final String COMMUNITYMININGS_TAG = "communityminings";
    public static final String SHORTESTPATH_TAG = "shortestpaths";
    public static final String LINKPREDICTION_TAG = "linkpredictions";
    public static final String METRICS_TAG = "metrics";
    public static final String GRAPHS_TAG = "graphs";
    public static final String READERS_TAG = "readers";
    public static final String WRITERS_TAG = "writers";
    public static final String TEXTUALNODE_TAG = "Node";
    public static final String TEXTUALNODE_CHILDREN_TAG = "";
    public static final String TEXTUALNODE_TITLE_ATTR = "title";
    public static final String TEXTUALNODE_ATTRIB_TAG = "attributes";

    // ************* Emotion and Polarity parameters *******************
     
    public static final int minSentencesReq = 400;
    public static final double minSupport = 0.00008;
    public static final double minConfidence = 0.6;
    public static final double P = 0.3;   //top P% of rules will be selected
    public static double probabThreshold = 0.5; // the threshold to decide if the word is positive or stronly positive (the same threshold for negative and strongly negative)
    public static final double minScore = 0.8; // the threshold to decide if the word is neutral or polar
    public static final double threshold = 0.1;
    
    // ************* Graph Generator Parameters *******************     
    public static final String beta = "0.8";
    public static final String alpha = "0.5";
    public static final String gamma = "0.5";
    public static final String epsilon = "0.0000001";
    public static final String r = "1";
    public static final String q = "0.5";
    public static final String phi = "1";
    public static final String t = "0";

    
    /* Path to executable algorithms on board*/
    public static String PROJECT_RESOURCES_DIR = "resources/";
    public static String PROJECT_ICONS_DIR = "icons/";
    public static String PATH_TO_CONFIG_FILE = OS_TYPE.equals(".exe")? "conf\\bizconf.xml" :"conf/bizconf.xml";
    private static String DEFAULT_MINING_DIRECTORY =  OS_TYPE.equals(".exe")? "resources\\mining\\" : "resources/mining/";
    private static String DEFAULT_GRAPH_DIRECTORY =  OS_TYPE.equals(".exe")? "resources\\graphGenerator\\" : "resources/graphGenerator/";
    private static String MINING_DIRECTORY_LOCAL_COMMUNITY
            = DEFAULT_MINING_DIRECTORY + ( OS_TYPE.equals(".exe")? "local_community\\LocalCommunity" : "local_community/LocalCommunity");
    private static String MINING_DIRECTORY_TOP_LEADER
            = DEFAULT_MINING_DIRECTORY + (OS_TYPE.equals(".exe")? "local_top_leaders\\TopLeaders.jar" : "local_top_leaders/TopLeaders.jar");
    private static String MINING_DIRECTORY_FAST_MODULARITY
            = DEFAULT_MINING_DIRECTORY + ( OS_TYPE.equals(".exe")? "fast_modularity\\FastCommunityMH" : "fast_modularity/FastCommunityMH");
    private static String MINING_DIRECTORY_ROSVALLINFOMAP
            = DEFAULT_MINING_DIRECTORY + (OS_TYPE.equals(".exe")? "rosvall_infomap\\infomap" : "rosvall_infomap/infomap");
    private static String MINING_DIRECTORY_ROSVALLINFOMOD
            = DEFAULT_MINING_DIRECTORY + (OS_TYPE.equals(".exe") ? "rosvall_infomod\\infomod" :"rosvall_infomod/infomod");
    private static final String MINING_EMOTION_DIRECTORY
            = DEFAULT_MINING_DIRECTORY + (OS_TYPE.equals(".exe") ? "emotion_mining\\" :"emotion_mining/");
    private static final String MINING_EMOTION_PYTHON_DIRECTORY
            = MINING_EMOTION_DIRECTORY + "segment.py";
    private static final String MINING_EMOTION_STOPWORDS_PATH
            = MINING_EMOTION_DIRECTORY + "stopwords";
    private static final String MINING_EMOTION_PUNCTUATIONS_PATH
            = MINING_EMOTION_DIRECTORY + "punctuations";
    private static final String MINING_EMOTION_EMOTICONS_PATH
            = MINING_EMOTION_DIRECTORY + "emoticons";
    private static final String DETECTING_POLARITY_DIRECTORY
            = DEFAULT_MINING_DIRECTORY + (OS_TYPE.equals(".exe") ? "polarity_detection\\" :"polarity_detection/");
    private static final String DETECTING_POLARITY_NEGATORS_PATH
            = DETECTING_POLARITY_DIRECTORY + "negators";
    private static final String DETECTING_POLARITY_ADJ_FORMS_PATH
            = DETECTING_POLARITY_DIRECTORY + "adj_forms";
    private static final String DETECTING_POLARITY_LEXICON_PATH
            = DETECTING_POLARITY_DIRECTORY + "MPQA_test";
    private static final String DETECTING_POLARITY_TAGGER_PATH
            = DETECTING_POLARITY_DIRECTORY + "english-left3words-distsim.tagger";
    private static final String DETECTING_POLARITY_CELEX_PATH
            = DETECTING_POLARITY_DIRECTORY + "eml.cd";
    private static final String DETECTING_POLARITY_ANTONYMS_PATH
            = DETECTING_POLARITY_DIRECTORY + "antonyms";
    private static final String DETECTING_POLARITY_FEEDBACK_PATH
            = DETECTING_POLARITY_DIRECTORY + "feedbacks";
    private static String GRAPH_GENERATOR
            = DEFAULT_GRAPH_DIRECTORY + ( OS_TYPE.equals(".exe")? "FARZ" : "FARZ");

    /* Path to generated output files for UI usage*/
    public static String OUTPUT_DIRECTORY = "";
    public static String AUTHENTICATION_FILE = ".authentication";
    
    private static final String EXTRACTSUBGRAPHTITLEPREFIX = "Untitled";
    private static final String GRAPHNAME_PROJECTNAME_REGEX = "[A-Za-z0-9]+[A-Za-z0-9_-]*";    
    private static String VERTEX_COLOR_DEFAULT = "#334DB3" ;
    private static String EDGE_COLOR_DEFAULT = "#999999" ;
    
    
    public MeerkatSystem() {

    }
    
    
    public static String getExtractSubGraphTitlePrefix(){
        return EXTRACTSUBGRAPHTITLEPREFIX;
    }
    
    public static String getGraphNameProjectNameRegex(){
        return GRAPHNAME_PROJECTNAME_REGEX;
    }
    /**
     *
     * @return
     */
    public static String getLocalCommunityMiningDirectory() {
        return MINING_DIRECTORY_LOCAL_COMMUNITY + OS_BIT + OS_TYPE;
    }

    /**
     *
     * @return
     */
    public static String getTopLeaderCommunityMiningDirectory() {
        return MINING_DIRECTORY_TOP_LEADER;
    }

    /**
     *
     * @return
     */
    public static String getFastModularityCommunityMiningDirectory() {
        System.out.println("OS TYPE: " + OS_TYPE);
        return MINING_DIRECTORY_FAST_MODULARITY + OS_BIT + OS_TYPE;
    }
    
    /**
     *
     * @return
     */
    public static String getGraphGeneratorDirectory() {
        return GRAPH_GENERATOR + OS_BIT + OS_TYPE;
    }
        
    /**
     *
     * @param pblnDirected
     * @return
     */
    public static String getRosvallInfomapMinerDirectory(boolean pblnDirected) {
        if (pblnDirected) {
            return MINING_DIRECTORY_ROSVALLINFOMAP + "_directed" + OS_BIT + OS_TYPE;
        } else {
            return MINING_DIRECTORY_ROSVALLINFOMAP + "_undirected" + OS_BIT + OS_TYPE;
        }
    }

    /**
     *
     * @return
     */
    public static String getRosvallInfomodMinerDirectory() {
        return MINING_DIRECTORY_ROSVALLINFOMOD + OS_BIT + OS_TYPE;
    }

    /**
     *
     * @return
     */
    public static String getMiningEmotionDirectory() {
        return MINING_EMOTION_DIRECTORY;
    }

    /**
     *
     * @return
     */
    public static String getMiningEmotionPythonDirectory() {
        return MINING_EMOTION_PYTHON_DIRECTORY;
    }

    /**
     *
     * @return
     */
    public static String getMiningEmotionStopwordsPath() {
        return MINING_EMOTION_STOPWORDS_PATH;
    }

    /**
     *
     * @return
     */
    public static String getMiningEmotionEmoticonsPath() {
        return MINING_EMOTION_EMOTICONS_PATH;
    }

    /**
     *
     * @return
     */
    public static String getMiningEmotionPunctuationsPath() {
        return MINING_EMOTION_PUNCTUATIONS_PATH;
    }

    /**
     *
     * @return
     */
    public static String getDetectingPolarityNegatorsPath() {
        return DETECTING_POLARITY_NEGATORS_PATH;
    }

    /**
     *
     * @return
     */
    public static String getDetectingPolarityAdjFormsPath() {
        return DETECTING_POLARITY_ADJ_FORMS_PATH;
    }

    /**
     *
     * @return
     */
    public static String getDetectingPolarityLexiconPath() {
        return DETECTING_POLARITY_LEXICON_PATH;
    }

    /**
     *
     * @return
     */
    public static String getDetectingPolarityTaggerPath() {
        return DETECTING_POLARITY_TAGGER_PATH;
    }

    /**
     *
     * @return
     */
    public static String getDetectingPolarityCelexPath() {
        return DETECTING_POLARITY_CELEX_PATH;
    }

    /**
     *
     * @return
     */
    public static String getDetectingPolarityAntonymsPath() {
        return DETECTING_POLARITY_ANTONYMS_PATH;
    }

    /**
     *
     * @return
     */
    public static String getDetectingPolarityFeedbackPath() {
        return DETECTING_POLARITY_FEEDBACK_PATH;
    }
    
    /**
     *
     * @return
     */
    public static String getDefaultVertexColor() {
        return VERTEX_COLOR_DEFAULT;
    }
    
    /**
     *
     * @param vColorString
     */
    public static void setDefaultVertexColor(String vColorString) {
        VERTEX_COLOR_DEFAULT = vColorString;
    }
    /**
     *
     * @return
     */
    public static String getDefaultEdgeColor() {
        return EDGE_COLOR_DEFAULT;
    }
    
    /**
     *
     * @param vColorString
     */
    public static void setDefaultEdgeColor(String vColorString) {
        EDGE_COLOR_DEFAULT = vColorString;
    }

    /**
     *
     * @param pstrAttrName
     * @return
     */
    public static boolean isSystemAttribute(String pstrAttrName) {
        if (SystemAttributes.contains(pstrAttrName)) {
            return true;
        } else {
            return false;
        }
    }    
    
    public static List<String> getValidColorsCommunities(){    
        return COLORS_COMMUNITIES;        
    }
}
