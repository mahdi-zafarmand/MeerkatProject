package main.project;

import algorithm.graph.layout.algorithms.Layout;
import algorithm.graph.layout.algorithms.RandomLayout;
import config.MeerkatSystem;
import datastructure.core.IDGenerator;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.text.HNode;
import datastructure.core.text.classinterface.ITextualNetwork;
import datastructure.core.text.impl.TextualNetwork;
import io.DataReader;
import io.communities.writer.CommunityWriter;
import io.serialize.ProjectSerialization;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.FileUtils;

/**
 *  Class Name      : Project
 *  Created Date    : 2015-07
 *  Description     : Stores all the details regarding a project
 *  Version         : 1.0
 *  @author         : Afra Abnar
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-03-01      Talat           Changed the mapTextNodes from Map<Integer, HNode> to Map<Integer, ITextualNetwork>
 *  2016-03-01      Talat           Adding fields, strProjectName, lngLastSavedTime, lststrRawDataFiles and their getters and setters
 *  2016-03-01      Talat           Added the access modifiers for the fields
 *  2015-08-27      Talat           Added the getter getGraph()
 *  2015-08-12      Talat           The HashMap hmapMessages is changed to a List of HashMaps
 *  2015-10-09      Afra            Revert the above change. Access the list of
 *                                  messages in a thread tree by root node id.
 *                                  (changed List of HashMap to 
 *                                  HashMap of HashMap)
*/
public class Project{

    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    private final int intProjectId;
    private String strProjectName ;
    private String strProjectDirectory ;
    private String strProjectIconPath ;
    
    private long lngLastSavedTime ;
    
    private List<String> lststrRawDataFiles = new ArrayList<>();
    
    /* Variables to store Input files */    
    protected String strProjectFilePath;

    /* Variables to store Output files */
    protected final String strGraphFilePath = MeerkatSystem.OUTPUT_DIRECTORY +"graph";

    protected final String strThreadTreeFilePath = 
            MeerkatSystem.OUTPUT_DIRECTORY+"textual";
    
    // A Project contains a list of Graphs by mapping their IDs with the Graphs
    private final Map<Integer, IDynamicGraph<IVertex, IEdge<IVertex>>> 
            mapGraphs = new HashMap<>(); // A Map of all the Graphs in the Project
    
    private final Map<Integer, IDynamicGraph<IVertex, IEdge<IVertex>>> 
            mapGraphsMPRJFile = new HashMap<>(); // A Map of all the Graphs present in mprj file

    private final Map<Integer, ITextualNetwork> mapTextualNetwork = 
            new HashMap<>() ;
    
    private final Map<Integer, ITextualNetwork> mapTextualNetworkMPRJFile = 
            new HashMap<>() ;  // A Map of all the textual networks present in mprj file
    
    private final IDGenerator graphIDGenerator = new IDGenerator();
    private final IDGenerator textualIDGenerator = new IDGenerator();
    

    
    /**
     *
     * @param pintProjId
     * @param pstrProjectName
     * @param pstrProjectDirectory
     */
    public Project(int pintProjId, String pstrProjectName, String pstrProjectDirectory) {
        this.intProjectId = pintProjId;
        this.strProjectName = pstrProjectName ;
        this.strProjectDirectory = pstrProjectDirectory ;
        
        File flProjDir = new File(pstrProjectDirectory);
        flProjDir.mkdir();
        
        System.out.println("Project.constructor - project directory path " + 
                flProjDir.getAbsolutePath() );
        
    }

    public Project(int pintProjId) {
        this.intProjectId = pintProjId;
    }

    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    public Set<Integer> getAllGraphIDs () {
        return mapGraphs.keySet() ;
    }
    
    

    public Set<Integer> getAllTextNetworkIDs () {
        return mapTextualNetwork.keySet() ;
    }
    
    public IDynamicGraph<IVertex, IEdge<IVertex>> getGraph(int pintGraphID) {
        return mapGraphs.get(pintGraphID);
    }

    public int getId(){
        return this.intProjectId;
    }
 
    public int getGraphCount() {
        return this.mapGraphs.size();
    }

    public String getProjectName() {
        return this.strProjectName ;
    }

    public long getSavedTime() {
        return this.lngLastSavedTime ;
    }

    public void setSavedTime(long pdtSavedTime) {
        this.lngLastSavedTime = pdtSavedTime ;
    }    

    public void setCurrentAsSavedTime() {
        this.lngLastSavedTime = System.currentTimeMillis();
    }

    public String getProjectDirectory() {
        return this.strProjectDirectory ;
    }

    public void setProjectDirectory(String pstrProjectDirectory) {
        this.strProjectDirectory = pstrProjectDirectory ;
    }

    public void setProjectName(String pstrProjectName) {
        this.strProjectName = pstrProjectName;
    }
    public List<String> getRawDataFiles() {
        return this.lststrRawDataFiles ;
    }

    public void setRawDataFiles(List<String> plststrRawDataFile) {
        this.lststrRawDataFiles = plststrRawDataFile ;
    }

    public String getProjectIconPath () {
        return this.strProjectIconPath ;
    }

    public void setProjectIconPath (String pstrProjectIconPath) {
        this.strProjectIconPath = pstrProjectIconPath;
    }

    public ITextualNetwork getTextualNetwork(int pTextualNetworkId) {
        return mapTextualNetwork.get(pTextualNetworkId);
    }
    
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    /**
     *  Method Name     : loadFile
     *  Created Date    : 2015-07-16
     *  Description     : Loads the network (by the user action of LoadNetwork) 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrReaderID: String - The ID that would be mapped with the class
     *  @param pstrPath2FileName: String - The input File Path
     *  @param pintGraphType : int (0 for Graph files, 1 for textual files)
     *  @return int: the graphID 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-10-19      Afra            Change DataRead load method to 
     *                                  loadGraph & loadThreaTree and decides on
     *                                  which one to call in this method 
     *                                  (loadFile) based on the value of
     *                                  parameter "pintGraphType".
     *  2015-10-13      Talat           Added the parameter pintGraphType since the package that is to be 
     *                                  invoked using reflection are not the same (The readers are not located 
     *                                  in the same package)
     *  July 20, 2015	aabnar		added implementation for loadFile. It will pass the laoding to DataReader
     *  				and depending on the file extension. The return however, is an Object.
     *  				It contains an HNode if the input is a textual file and IGraph if the input
     *  				is a graph file (.meerkat, .graphml, .csv, .pajek, ...)
     *  				The method will return the MeerkatId of the IGraph or the MeerkatId of the 
     *  				root of the message thread tree.
     *  				in case of exceptions or when the loaded file in niether a igraph, nor 
     *  				an HNode, method will return -1.
     * 
     *  
     * 
    */
    public int loadFile(
            String pstrReaderID, 
            String pstrPath2FileName, 
            int pintGraphType) {
        
        try {

            // Load the Graph File and store the graph file in an object
            switch (pintGraphType) {
                case 0:
                    IDynamicGraph<IVertex,IEdge<IVertex>> igraph =
                            DataReader.loadGraph(pstrReaderID, 
                                    pstrPath2FileName);
                    
                    if (igraph == null) {
                        System.out.println("Project.addGraph(): Graph is null");
                        return -201;
                    } else {
                        addGraph(igraph);
                        igraph.calculateCommunityColorsOnLoading();
                        if (!((IVertex)igraph.getAllVertices().get(0))
                                .getSystemAttributer()
                                .getAttributeNames()
                                .contains(MeerkatSystem.X)) {
                            for (TimeFrame tf : igraph.getAllTimeFrames()) {
                                Layout layAlg = 
                                        new RandomLayout(igraph, tf, null);
                                Thread th = new Thread(layAlg);
                                th.run();
                                th.join();
                            }
                        }

                        igraph.setGraphFile(pstrPath2FileName);
                        return igraph.getId();
                        //System.out.println(" ===== MeerkatLogic: Project.loadFile(): random layout done for the graph in logic");
                    }
                case 1:
                    TextualNetwork TNW = DataReader.loadThreadTree(pstrReaderID,
                            pstrPath2FileName);
                    addTextualNetwork(TNW);
                    return TNW.getId();
                default:
                    System.out.println("Project.loadFile(): "
                            + "The graph does not belong to any instance");
                    
                    return -1 ;
            }
        } catch (Exception ex) {
            System.out.println("EXCEPTION: Project.loadFile()");
            ex.printStackTrace();
            return -3001 ;           
        }
    }
    /**
     *  Method Name     : loadFile
     *  Created Date    : 2017-08-10
     *  Description     : Loads the network (by the user action of LoadNetwork), the one not in mprj file
     *  Version         : 1.0
     *  @author         : Abhi
     * 
     *  @param pstrReaderID: String - The ID that would be mapped with the class
     *  @param pstrPath2FileName: String - The input File Path
     *  @param pintGraphType : int (0 for Graph files, 1 for textual files)
     *  @return int: the graphID 
     */
    public int loadFileFromOutsideMPRJ(String pstrReaderID, String pstrPath2FileName, int pintGraphType){
            
        
            int graphId =  this.loadFile(pstrReaderID, pstrPath2FileName, pintGraphType);
            
            //return if the graph has some load error.
            if(graphId==-201)
                return graphId;
            
            
            Path pathFileOnDisk = Paths.get(pstrPath2FileName);
            
            String fileName = pathFileOnDisk.getFileName().toString();
            
            
            
            String strGraphTitle = "GraphTitle";
            
            if (fileName.contains(".")) {
                strGraphTitle = fileName.substring(0, fileName.lastIndexOf(".")).trim();
                System.out.println("Project.loadFileOutsideMPRJ  :: " + strGraphTitle + " >> filename = " + fileName);
                //extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            }
        
        
            //put fileName as "Name.meerkat".Right now all files being saved as .meerkat
            //put filePath = "projects/projectname/Name.meerkat"
            String pstrPath2FileNameMeerkat = this.getProjectDirectory() + strGraphTitle + ".meerkat";
            
            this.getGraph(graphId).setGraphFile(pstrPath2FileNameMeerkat);
            this.getGraph(graphId).setGraphTitle(strGraphTitle);
            
            
            return graphId;
    }
    
    /**
     *  Method Name     : addGraph
     *  Created Date    : 2015 Jul/Aug
     *  Description     : 
     *  Version         : 1.0
     *  @author         : Afra
     * 
     *  @param pIGraph : IGraph
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016 Apr 26     Afra            make it public and changed the return value from void to int (graph id).
     *                                  it is public because in terms of extracting graphs from selected vertices/edges we need to add the extracted graph to the project. 
     * @return  
     *                                  
     * 
    */
    public int addGraph(IDynamicGraph<IVertex,IEdge<IVertex>> pIGraph) {
        try {
            if (pIGraph == null) {
                System.out.println("Project.addGraph(): Graph is null ");
            }
            pIGraph.setId(graphIDGenerator.getNextAvailableID());
            mapGraphs.put(pIGraph.getId(), pIGraph);
            
            return pIGraph.getId();
        } catch (Exception ex) {
            System.out.println("Project.addGraph(): EXCEPTION: ");
            ex.printStackTrace();
        }
        return -1;
    }
    /**
    *  Method Name     : addMsgThread
    *  Created Date    : 2015-07
    *  Description     : Adding the Message Thread 
    *  Version         : 1.0
    *  @author         : Afra Abnar
    * 
    *  @param pRoot : HNode
    * 
    *  EDIT HISTORY (most recent at the top)
    *  Date            Author          Description
    *  2016-12-14       Afra            Saving textualnetwork to the map rather
    *                                   than the root HNode.
    *  2015-08-12      Talat           The hmpMessages is now added to the list of Messages
    * 
   */
    private void addTextualNetwork(TextualNetwork ptextualNetwork) {
        ptextualNetwork.setId(textualIDGenerator.getNextAvailableID());
        mapTextualNetwork.put(ptextualNetwork.getId(), ptextualNetwork);
    }
    /**
     *  Method Name     : updateMPRJMaps()
     *  Created Date    : 
     *  Description     : Saves the current project
     *  Version         : 1.0
     *  @author         : put all entries of mapGraphs and mapTextualNetworks in mprjMaps
     */
    public void fillMPRJMaps(){
        for(int graphId : mapGraphs.keySet()){
            mapGraphsMPRJFile.put(graphId, mapGraphs.get(graphId));
        }
        for(int textualNetworkId : mapTextualNetwork.keySet()){
            mapTextualNetworkMPRJFile.put(textualNetworkId, mapTextualNetwork.get(textualNetworkId));
        }
    }
    public void clearMPRJMaps(){
        
            mapGraphsMPRJFile.clear();
        
            mapTextualNetworkMPRJFile.clear();
        
    }
    
    /**
     *  Method Name     : Save()
     *  Created Date    : 2016-03-03
     *  Description     : Saves the current project
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrProjectExtension : String
     *  @param pstrProjectDelimiter : String
     *  @param pstrGraphDelimiter : String
     *  @return int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 2016-12-21       Afra            updating the lastSavedTime in this method
     *                                  as well.
     *  
     * 
    */        
    public int Save(String pstrProjectExtension, String pstrProjectDelimiter, String pstrGraphDelimiter) {
        
        // #DEBUG
        /*
        System.out.println("Project.Save(): Project Parameters"
                + "\n\tDirectory: " + this.getProjectDirectory()
                + "\n\tProject Name: " + this.getProjectName()
                + "\n\tProject Extension: " + pstrProjectExtension
                + "\n\tID: " + this.getId()
                + "\n\tAll Graph IDs: " + this.getAllGraphIDs()
                + "\n\tText Network IDs: " + this.getAllTextNetworkIDs()
                + "\n\tRawFiles: " + getRawDataFiles()
                + "\n\tProject Delimiter: " + pstrProjectDelimiter
                + "\n\tGraphDelimiter: " + pstrGraphDelimiter
        );
*/
        int intErrorCode = ProjectSerialization.Write(
                  this.getProjectDirectory()
                , this.getProjectName()
                , pstrProjectExtension
                , this.getId()
                
                , this.getAllGraphIDs()
                , this.getAllTextNetworkIDs()
                
                , getRawDataFiles()
                , pstrProjectDelimiter
                , pstrGraphDelimiter
        );
        // update MPRJ maps with latest list of graphs and textual networks
        clearMPRJMaps();
        fillMPRJMaps();
        lngLastSavedTime = System.currentTimeMillis();
        return intErrorCode ;
    }
    
    /**
     *  Method Name     : Save()
     *  Created Date    : 2017-04-12
     *  Description     : Saves the current project, writes graphs according to new location of vertices
     *  Version         : 1.0
     *  @author         : Abhi
     * 
     *  @param pmapProjectsAllVerticesLocation : Map<Integer, Map<Integer, Map<Integer, Double[]>>>
     *  @param pstrProjectExtension : String
     *  @param pstrProjectDelimiter : String
     *  @param pstrGraphDelimiter : String
     *  @return int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
     *  
     * 
    */        
    public int Save(Map<Integer, Map<Integer, Map<Integer, Double[]>>> pmapProjectsAllVerticesLocation, String pstrProjectExtension, String pstrProjectDelimiter, String pstrGraphDelimiter) {
        
        // #DEBUG
        /*
        System.out.println("Project.Save(): Project Parameters"
                + "\n\tDirectory: " + this.getProjectDirectory()
                + "\n\tProject Name: " + this.getProjectName()
                + "\n\tProject Extension: " + pstrProjectExtension
                + "\n\tID: " + this.getId()
                + "\n\tAll Graph IDs: " + this.getAllGraphIDs()
                + "\n\tText Network IDs: " + this.getAllTextNetworkIDs()
                + "\n\tRawFiles: " + getRawDataFiles()
                + "\n\tProject Delimiter: " + pstrProjectDelimiter
                + "\n\tGraphDelimiter: " + pstrGraphDelimiter
        );
*/      
        // Update Each Dynamic graph in the method parameter
        System.out.println("Project.Save(): To Update graphs SysX,SysY");
        for(Integer dynaGraphId : pmapProjectsAllVerticesLocation.keySet()){
        
            Map<Integer, Map<Integer, Double[]>> mapGraphTimeFramesVerticesLocation = pmapProjectsAllVerticesLocation.get(dynaGraphId);
            mapGraphs.get(dynaGraphId).updateVertexLocations(mapGraphTimeFramesVerticesLocation);
            
        }
        
        int intErrorCode = ProjectSerialization.Write(
                  this.getProjectDirectory()
                , this.getProjectName()
                , pstrProjectExtension
                , this.getId()
                
                , this.getAllGraphIDs()
                , this.getAllTextNetworkIDs()
                
                , getRawDataFiles()
                , pstrProjectDelimiter
                , pstrGraphDelimiter
        );
        
        // update MPRJ maps with latest list of graphs and textual networks
        clearMPRJMaps();
        fillMPRJMaps();
        
        lngLastSavedTime = System.currentTimeMillis();
        return intErrorCode ;
    }
    
    /**
     *  Method Name     : saveCommunity()
     *  Created Date    : 2017-07-05
     *  Description     : Saves the community to a file on the disk
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param intGraphID : int
     *  @param communities : Set<String>
     *  @param lstintSelectedVertexIDs : List<Integer>
     *  @param edgeSourceAndTarget : List<Integer[]>
     *  @param filePath : String

     *  @return int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
     *  
     * 
    */        
    public int saveCommunity(int intGraphID, Set<String> communities, List<Integer> lstintSelectedVertexIDs, 
            List<Integer[]> edgeSourceAndTarget, String filePath) {
        
            int intErrorCode = CommunityWriter.Write(intGraphID, communities, this.getProjectDirectory(), lstintSelectedVertexIDs, edgeSourceAndTarget, filePath);
            
        return intErrorCode ;
    }
    
    public String deleteGraph(int pintGraphId
            , String pstrProjectExtension
            , String pstrProjectDelimiter
            , String pstrGraphDelimiter){
        

        String messageReturned = "";
        // if graphFile is in mprj file - then modify mprj file and delete the graphFile on disk
        // otherwise - remove the graph from mapGraphs only
        
        //1. get graph file and delete graphfile
        if(mapGraphsMPRJFile.containsKey(pintGraphId)){
            
            messageReturned = messageReturned + deleteGraphAndFile(pintGraphId, pstrProjectExtension, pstrProjectDelimiter, pstrGraphDelimiter);
        
        }else{
            
            String messageRemoved = "Graph File : "+ mapGraphs.get(pintGraphId).getGraphTitle() + " Removed from project";
            mapGraphs.remove(pintGraphId);
            messageReturned = messageReturned + messageRemoved;
        }
        
       return messageReturned;
    }
    /**
     * Description : Delete GraphFile and delete its entry from maps if this file is in .mprj file of this project
     * @param pintGraphId
     * @param pstrProjectExtension
     * @param pstrProjectDelimiter
     * @param pstrGraphDelimiter
     * @return 
     */
    private String deleteGraphAndFile(int pintGraphId
            , String pstrProjectExtension
            , String pstrProjectDelimiter
            , String pstrGraphDelimiter){
    
        
            String messageReturned = "";
            String graphFilePath = mapGraphs.get(pintGraphId).getGraphFile();
            String graphTitle = mapGraphs.get(pintGraphId).getGraphTitle();
            Boolean fileDeletionResult = false;
            try{
                    File file = new File(graphFilePath);

                    if(file.exists()){
                            fileDeletionResult = file.delete();
                            if(fileDeletionResult){
                                System.out.println("MeerkatLogic Project.java deleteGraphFile" + file.getName() + " is deleted!");
                                messageReturned = messageReturned + "Graph: "+ graphTitle + " deleted" +
                                        "\n File: " + graphFilePath + " deleted" ;
                            }else{
                                messageReturned = messageReturned + "File: " + graphFilePath + " could not be deleted";
                            }
                    }else{
                            System.out.println("MeerkatLogic Project.java deleteGraphFile -- File does not exist.");
                            messageReturned = messageReturned + "File: " + graphFilePath + " does not exist";
                    }

            }catch(Exception e){

                    e.printStackTrace();
                    messageReturned = messageReturned + e.toString();

            }

            //2. remove graph from mapGraphs and mapGraphsMPRJFile
            mapGraphs.remove(pintGraphId);
            mapGraphsMPRJFile.remove(pintGraphId);

            //3. get mprj file - rewrite it
            int intErrorCode = ProjectSerialization.WriteMPRJFileOnly(
                      this.getProjectDirectory()
                    , this.getProjectName()
                    , pstrProjectExtension
                    , this.getId()

                    , this.getAllGraphIDs()
                    , this.getAllTextNetworkIDs()

                    , getRawDataFiles()
                    , pstrProjectDelimiter
                    , pstrGraphDelimiter
            );
            
            
        
            return messageReturned;
        
    }
    
    private void removeGraph(int pintGraphId){
        
        mapGraphs.remove(pintGraphId);
    
    }
    
    public Boolean containsGraphName(String pstrGraphname){
        for(int graphId : mapGraphs.keySet()){
            if(mapGraphs.get(graphId).getGraphTitle().equalsIgnoreCase(pstrGraphname)){
                return true;
            }
        }
        return false;
    }
    
    public Boolean checkIfGraphInMPRJFile(int graphId){
    
        if(this.mapGraphsMPRJFile.containsKey(graphId)){
            return true;
        }
        return false;
    
    }
    
    public Boolean renameGraphFile(int pintGraphId, String newTitle){
            String messageReturned = "";
            String graphFilePath = mapGraphs.get(pintGraphId).getGraphFile();
            String graphTitle = mapGraphs.get(pintGraphId).getGraphTitle();
            
            try{
                    
                
                    Path sourcePath = Paths.get(graphFilePath);

                    Path directoryPath = sourcePath.getParent();
                    String fileName = sourcePath.getFileName().toString();



                    String title = newTitle;
                    String extension = "";
                    if (fileName.contains(".")) {
                     extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                    }
                    System.out.println("Project.renameGraphFileOnDisk : directoryPath of graph = " + directoryPath.toString());
                    String newFilePath = directoryPath.toString() + File.separator + title + extension;

                    
                    
                    
                    Path destinationPath = Paths.get(newFilePath);
                    
                    
                    Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

                    
            }catch(Exception e){
                e.printStackTrace();
                return false;

                
            }
        return true;
    }
    
    public void writeMPRJFile(String pstrProjectExtension
            , String pstrProjectDelimiter
            , String pstrGraphDelimiter){
        
        
                    int intErrorCode = ProjectSerialization.WriteMPRJFileOnly(
                      this.getProjectDirectory()
                    , this.getProjectName()
                    , pstrProjectExtension
                    , this.getId()

                    , this.getAllGraphIDs()
                    , this.getAllTextNetworkIDs()

                    , getRawDataFiles()
                    , pstrProjectDelimiter
                    , pstrGraphDelimiter
            );
    
    }
    
    public void deleteProject(String pstrProjectExtension){
        //delete its graph files
        //delete its mprj file
        String projectDirectoryPath = this.getProjectDirectory();
        System.out.println("MeerkatLogic.Project. deleteproject() Deleting projetFolder  " + projectDirectoryPath);
        try{ 
        FileUtils.forceDelete(new File(projectDirectoryPath));
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("MeerkatLogic.Project. deleteproject() error in deleting projectFolder + " + projectDirectoryPath);
        }
        Path projectFolderPath = Paths.get(this.getProjectDirectory());

        Path mprjDirectoryPath = projectFolderPath.getParent();
        
        String strRootDirectory = this.getProjectDirectory() + "../";
            
        // mprj file
        //String mprjFile = strRootDirectory + this.getProjectName() + pstrProjectExtension;
        
        String mprjFile = mprjDirectoryPath.getFileName().toString() + File.separator +this.getProjectName() + pstrProjectExtension;

        System.out.println("MeerkatLogic.Project. deleteproject() Deleting mprj file  " + mprjFile);
        try{ 
        FileUtils.forceDelete(new File(mprjFile));
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("MeerkatLogic.Project. deleteproject() error in deleting mprj file + " + mprjFile);
        }
        
        
    
    }
}
