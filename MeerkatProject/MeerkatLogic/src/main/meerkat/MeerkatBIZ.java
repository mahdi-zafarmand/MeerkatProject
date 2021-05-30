package main.meerkat;

import config.MeerkatClassConfig;
import config.MeerkatSystem;
import datastructure.core.IDGenerator;
import datastructure.core.graph.classinterface.IDynamicGraph;
//import io.algorithmutility.EmotionModelReader;
//import io.algorithmutility.PolarityModelReader;
//import io.algorithmutility.PolarityWriter;

import io.utility.ConfigLoader;
import java.util.HashMap;
import java.util.Map;
import main.project.Project;

/**
 *  Class Name      : MeerkatBIZ
 *  Created Date    : 2015-07
 *  Description     : The MeerkatBIZ Instance that would contain information about all the projects and graphs
 *  Version         : 1.0
 *  @author         : Afra
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-11-02      Talat           Added the field mapNodeFeedback and methods 
 *                                  getFeedback(), addFeedback(), clearFeedback()
 *  2015-09-18      Talat           Renamed the class name (and its references) from MeerkatBIZ 
 *                                  to MeerkatBIZ to avoid confusion with the 
 *                                  other class of the Application "MeerkatBIZ"
 *  2015-08-12      Talat           Changed the method name from getProject to getProject
 *  2015-08-12      Talat           Changed the variable hmapProjects to hmapProjects
 *  2015-08-12      Talat           Used Diamond inference and access modifiers at possible places 
 * 
*/
public class MeerkatBIZ {
    
    private HashMap<Integer, Project> hmapProjects = new HashMap<>();
    IDGenerator projIdGen = new IDGenerator();
    
    /* A hashmap between the MsgNode IDs and the Feedback value 
    that is stored but not applied yet*/
    private Map<Integer, Double> mapNodeFeedback = new HashMap<>();
    
    /**
     *
     */
    public static MeerkatClassConfig meerkatConfig; 

    static MeerkatBIZ application = null; // Singleton instance

    private IDGenerator projectIdGen = new IDGenerator();

    /**
     * Updating some of the MeerkatSystem Variables set in UI
     * @param pmapConfig 
     * @since 2018-01-24
     * @author Talat/Abhi/Sankalp
     * 
     */
    public void updateConfig(Map<String, String> pmapConfig) {
        String strDefaultColorKey = "VERTEX_COLOR_DEFAULT";
        if (pmapConfig.containsKey(strDefaultColorKey)){
            MeerkatSystem.setDefaultVertexColor(pmapConfig.get(strDefaultColorKey));
        }
    }


    /**
     *
     */
    public enum SytemAttribute {

        /**
         *
         */
        SYS_BETWEENNESS,

        /**
         *
         */
        SYS_CLOSENESS,

        /**
         *
         */
        SYS_INDEGREE,

        /**
         *
         */
        SYS_OUTDEGREE,

        /**
         *
         */
        SYS_PAGERANK,

        /**
         *
         */
        SYS_HITS
    }

    private MeerkatBIZ() {
        // TODO: initialize bunch of application wide attributes.
        meerkatConfig = ConfigLoader.loadConfig(MeerkatSystem.PATH_TO_CONFIG_FILE);
        initializeEnvironmentSettings();
    }

    private void initializeEnvironmentSettings() {
        // TODO Auto-generated method stub
        String strOSName = System.getProperty("os.name");
        System.out.println("MeerkatBIZ.initializeEnvironment(): "+strOSName);

        System.out.println("MeerkatBIZ.initializeEnvironment(): "+
                "Available processors (cores): " + 
                        Runtime.getRuntime().availableProcessors());

        /* Total amount of free memory available to the JVM */
        System.out.println("MeerkatBIZ.initializeEnvironment(): "+
                "Free memory (bytes): " + 
            Runtime.getRuntime().freeMemory());

        /* This will return Long.MAX_VALUE if there is no preset limit */
        long maxMemory = Runtime.getRuntime().maxMemory();
    
        /* Maximum amount of memory the JVM will attempt to use */
        System.out.println("MeerkatBIZ.initializeEnvironment(): "+
                "Maximum memory (bytes): " + 
            (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));

        /* Total memory currently available to the JVM */
        System.out.println("MeerkatBIZ.initializeEnvironment(): "+
                "Total memory available to JVM (bytes): " + 
            Runtime.getRuntime().totalMemory());
        //Textual tasks Removed from version MeerkatFX1.0
        //will be included in future
        //loadTextualLibs();
    }
    
    private void loadTextualLibs(){
    /*
        //emotion
        EmotionModelReader.load(MeerkatSystem.getMiningEmotionDirectory(),
                MeerkatSystem.getMiningEmotionStopwordsPath(),
                MeerkatSystem.getMiningEmotionPunctuationsPath(),
                MeerkatSystem.getMiningEmotionEmoticonsPath());
        
        //polarity
        PolarityModelReader.load(MeerkatSystem.getDetectingPolarityNegatorsPath(),
                MeerkatSystem.getDetectingPolarityAdjFormsPath(),
                MeerkatSystem.getDetectingPolarityLexiconPath(),
                MeerkatSystem.getDetectingPolarityTaggerPath(),
                MeerkatSystem.getDetectingPolarityCelexPath(),
                MeerkatSystem.getDetectingPolarityAntonymsPath(),
                MeerkatSystem.getDetectingPolarityFeedbackPath());
        
        
      */  
        /* #DEBUG */
            // Remove the following lines - START
        //    TextualXMLReader.read("sample.xml");
            // END
    
    
    }

    /**
     *
     * @return
     */
    public static MeerkatBIZ getMeerkatApplication () {
        if (application == null) {
            application = new MeerkatBIZ();
        }
        return application;
    }
    
    // Start - Feedback setters and getters

    /**
     *
     * @return
     */
        public Map<Integer, Double> getFeedback() {
        return this.mapNodeFeedback;
    }
    
    /**
     *
     * @param pintNodeID
     * @param pdblFeedback
     */
    public void addFeedback (Integer pintNodeID, Double pdblFeedback) {
        this.mapNodeFeedback.put(pintNodeID, pdblFeedback);
    }
    
    /**
     *
     */
    public void clearFeedback () {
        this.mapNodeFeedback.clear();
    }
    // End - Feedback setters and getters

    /**
     *
     * @param pstrPath2OutputFiles
     */
    public void setOutputDirectory(String pstrPath2OutputFiles) {
        MeerkatSystem.OUTPUT_DIRECTORY = pstrPath2OutputFiles;
    }

    /**
     *
     * @param pintMeerkatProjectID
     * @return
     */
    public Project getProject(int pintMeerkatProjectID) {
        // #Debug
        // System.out.println("MeerkatBIZ.getProject(): Fetching Project with Project ID  "+pintMeerkatProjectID);
        // Project currentProject = hmapProjects.get(pintMeerkatProjectID);
        // System.out.println("MeerkatBIZ.getProject(): Number of Graphs in project "+pintMeerkatProjectID+" is "+currentProject.getGraphCount());
        // #EndDebug
        return hmapProjects.get(pintMeerkatProjectID);
    }

    /**
     *
     */
    public void Debug_ListProject() {
        System.out.println("MeerkatBIZ.Debug_ListProject() : The following "
                + "lines will list all the projects that have been created");
        for (Project prjCurrent : this.hmapProjects.values()) {
            System.out.println("\tMeerkatBIZ.Debug_ListProject() : "
                    + "Project ID "+prjCurrent.getId()+"\tNo. of graphs"+prjCurrent.getAllGraphIDs().size());
            
            System.out.println(prjCurrent.getAllGraphIDs());
        }
    }

    /**
     *
     * @return
     */
    public int createNewProject() {
        Project prjNewP = new Project(projectIdGen.getNextAvailableID());
        hmapProjects.put(prjNewP.getId(), prjNewP);

        return prjNewP.getId();
    }
    
    /**
     *
     * @param pstrProjectName
     * @param pstrProjectDirectory
     * @return
     */
    public int createNewProject(String pstrProjectName, String pstrProjectDirectory) {
        Project prjNewP = new Project(projectIdGen.getNextAvailableID(), pstrProjectName, pstrProjectDirectory);
        hmapProjects.put(prjNewP.getId(), prjNewP);

        return prjNewP.getId();
    }

    /**
     *
     */
    public void closeProject() {
        
    }
    /**
     *  Method Name     : getGraph
     *  Created Date    : 2017-04-27
     *  Description     : Removes the project with given projectID
     *  Version         : 1.0
     *  @author         : Abhi
     * 
     *  @param pintProjectId : int
     *  @return 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void closeProject(int pintProjectId){
        if(this.hmapProjects.containsKey(pintProjectId)){
            this.hmapProjects.remove(pintProjectId);
        }
    }
    
    
    /**
     *  Method Name     : getGraph
     *  Created Date    : 2015-09-01
     *  Description     : Returns the graph by iterating through all the
     *                      projects and finding the graph using the graphID
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintGraphID : int
     *  @return IGraph
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public IDynamicGraph getGraph(int pintGraphID) {
        IDynamicGraph graphReturn ;
        for (Project prjCurrent : this.hmapProjects.values()) {
            graphReturn = prjCurrent.getGraph(pintGraphID) ;
            if (graphReturn != null) {
                return graphReturn;
            }
        }
        return null;
    }
    
    /**
     * calls appropriate function to save the lexicon (used for polarity mining)
     */
    public void closeApplication(){
        //PolarityWriter.saveLexicon(MeerkatSystem.getDetectingPolarityLexiconPath());
    
}
    
    
    public String getExtractSubGraphTitlePrefix(){
    
        return MeerkatSystem.getExtractSubGraphTitlePrefix();
    }
    
    public String getGraphNameProjectNameRegex(){
    
        return MeerkatSystem.getGraphNameProjectNameRegex();
    }
    
    public void deleteProject(int ProjectId, String pstrProjectExtension) {
        //delete its graph files
        //delete its mprj file
        this.getProject(ProjectId).deleteProject(pstrProjectExtension);
        
    }
	
}
