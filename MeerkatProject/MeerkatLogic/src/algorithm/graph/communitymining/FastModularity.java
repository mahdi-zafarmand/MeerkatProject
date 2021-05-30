package algorithm.graph.communitymining;

import io.algorithmutility.FastModularityOutputReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import config.MeerkatSystem;
import algorithm.graph.connectivity.ConnectedComponents;
import config.CommunityMiningParameters;
import config.Parameter;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import io.graph.writer.algorithm.FastModularityWriter;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Set;
import org.apache.commons.io.FileUtils;

/**
 * ClassName: FastModularity
 * Description: runs the fast modularity algorithm on the each connected component of the graph.The input should be in .pairs and vertex ids should be sequential ids starting from 1.
 * @author aabnar
 * paper : Finding community structure in very large networks, https://arxiv.org/pdf/cond-mat/0408187.pdf
 * not sure, if it is the exact implementation of this paper
 * @param <V>
 * @param <E>
 */
public class FastModularity<V extends IVertex, E extends IEdge<V>>
        extends Miner<V, E> {

    /**
     *
     */
    public static final String STR_NAME = "Fast Modularity Community Mining";

    private final String EXEC_LOCATION
            = MeerkatSystem.getFastModularityCommunityMiningDirectory();
    

    private static final String DEFAULT_ALG_TYPE = 
            CommunityMiningParameters.FASTMODULARITY_ALGTYPE_FASTMODULARITY;
    
    private static final boolean DEFAULT_WEIGHTED = true;

    /*
     * Algorithm Parameters:
     * - Weighted
     * - Algorithm Type ( Fast Modularity / Min Max Modularity )
     */
    private boolean weighted = DEFAULT_WEIGHTED;

    /**
     * Fast Modularity has two algorithm variants: fast modularity and maximum
     * minimum modularity.
     */
    private String algorithmType = DEFAULT_ALG_TYPE;

    

    /**
     * Constructor
     * Version: 2.0
     * @param pIGraph
     *          dynamic graph (not null)
     * @param tf
     *          time frame (not null)
     * @param pstrParameters
     *          Array of String containing all required parameters.
     *          - pstrParameters[i] = the ith parameter and value --> parametername:value
     * 
     * EDIT HISTORY
     * Date         Author      Description
     * 2016 Apr 20  Afra        changed the format of parameters to a one dimensional array instead of 2D.
     * 2016 Apr 19  Afra        all parameters are given in an array of String.
     * 
     */
    public FastModularity(final IDynamicGraph<V, E> pIGraph,
            TimeFrame tf,
            String[] pstrParameters) {
        
        super(pIGraph, tf);

        List<Parameter> lstParam = CommunityMiningParameters
                .getParameters(this.getClass().getSimpleName());
        
        if (pstrParameters != null) {
            for (String strP : pstrParameters) {
                for (Parameter p : lstParam) {
                    if (strP.startsWith(p.key)) {
                        String value = strP.substring(p.key.length()+1).trim();
                        if (p.key.equals(CommunityMiningParameters.FASTMODULARITY_ALGORITHMTYPE)) {
                            if (value.equals(CommunityMiningParameters.FASTMODULARITY_ALGTYPE_FASTMODULARITY)) {
                                this.algorithmType = value;
                            } else if (value.equals(CommunityMiningParameters.FASTMODULARITY_ALGTYPE_MAXMINMODULARITY)) {
                                this.algorithmType = value;
                            }
                        } else if (p.key.equals(CommunityMiningParameters.FASTMODULARITY_WEIGHTED)) {
                            this.weighted = Boolean.parseBoolean(value);
                        }
                        break;
                    }
                }
            }
        }
    }
    /*
     * (non-Javadoc)
     * 
     * @see
     * ca.aicml.meerkat.plugins.mining.IMinerPlugin#mine(ca.aicml.meerkat.model
     * .INetwork)
     */

    /**
     *
     */
    @Override
    public void run() {


        ConnectedComponents<V, E> ccAlgorithm
                = new ConnectedComponents<>(dynaGraph, tf, null);
        if(!running){
                return;
        }
        List<Set<V>> lstWComponents
                = ccAlgorithm.computeWeakComponents(this.isThreadRunningProperty);
        if(!running){
                return;
        }
        if (lstWComponents.size() > 1) {
            System.err.println("The graph is not connected!"
                    + " Thus, the mining algorithm is being run on each weakly "
                    + "connected component separately.");
        }

        int startComId = 0;
        for (Set<V> comp : lstWComponents) {
            // Mine only the current graph;
            if(!running){
                
                return;
            }
            List<V> lstComp = new LinkedList<>();
            lstComp.addAll(comp);
            if (comp != null && lstComp.size() > 3) {
                startComId = mineGraph(dynaGraph, lstComp,startComId);
                startComId++;
            }
        }
        updateDataStructure();
        blnDone = true;
    }

    /**
     * A mapping of the Fast Modularity identifiers to the vertex's original
     * identifiers. This is necessary to ensure that the executable's input
     * files begin at identifier 1 and are consecutive.
     */
    private HashMap<Integer, Integer> fastModIdToRegularId
            = new HashMap<>();

    /**
     * Mine a single graph (within a network) for its communities using Fast
     * Modularity.
     *
     * @param graph - The graph to be mined for communities.
     * @return - A list of communities that make up the groupings within this
     * graph.
     */
    private int mineGraph(IDynamicGraph<V, E> pdynagraph, List<V> plstVertices, int pintStartComId) {

        // Create the file that will be passed into the executable.
        String strPath2FileName = "temp";
        
        // get the current directory
        String curDir = System.getProperty("user.dir");
        
        // takes a while to write for very large graphs (large vertices/edges)
        String strFullFilePath = 
                FastModularityWriter.write(pdynagraph, tf, plstVertices, weighted, strPath2FileName);

        // Call the executable for the first run.
        //create a file /temp/algo_name.exe 
        /*InputStream in = this.getClass().getClassLoader().getResourceAsStream(EXEC_LOCATION);
        //File file = new File("temp/fastmodularity.exe");
        File tempFile = null;
        try {
            tempFile = File.createTempFile("fastmodularity", ".exe");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        tempFile.deleteOnExit();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(tempFile);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            IOUtils.copy(in, out);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        tempFile.setExecutable(true);
        try {
            Thread.currentThread().wait(20);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        */
        Process process = null;
        
        try {
            //ProcessBuilder builder = new ProcessBuilder(getTempExecFIle("fastmodularity.exe",  EXEC_LOCATION).getAbsolutePath(), "-alg",
            //        algorithmType == CommunityMiningParameters.FASTMODULARITY_ALGTYPE_FASTMODULARITY ? "fastmod" : "maxminmod",
            //        "-f", strFullFilePath, "-l", "firstrun");
            System.out.println("meekat logic : algorithm.grpah.communityMining.FastModularity.java : " + strFullFilePath);
            ProcessBuilder builder = new ProcessBuilder(EXEC_LOCATION, "-alg",
                    algorithmType == CommunityMiningParameters.FASTMODULARITY_ALGTYPE_FASTMODULARITY ? "fastmod" : "maxminmod",
                    "-f", strFullFilePath, "-l", "firstrun");
            
            //generating all files in the /temp directory.
            File flRedirectedOutput = new File(curDir+"/temp/"+"fastmodularity_redirect");
//            InputStream flRedirectedOutput = FastModularity.class.getClassLoader().getResourceAsStream(curDir+"/temp/"+"fastmodularity_redirect");
            
            if(!flRedirectedOutput.exists())
                flRedirectedOutput.mkdirs();
            
            builder.redirectOutput(flRedirectedOutput);            
            flRedirectedOutput.delete() ;            
            
            process = builder.start();
            
            process.waitFor();
        } catch (InterruptedException e) {
            process.destroy();
            System.out.println("FastModularity.mineGraph() -  Process Destroyed!");
            return -1;
            //throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Fast Modularity could not be run on this graph. "
                    + "This error is display if the graph is disconnected or "
                    + "collapsed. Please try a different algorithm");
        }
        
        if(!this.running)
            return -1;

        // Call the executable for the second run.
        try {
            String step = FastModularityOutputReader.getStepValue(
                    "temp-fc_firstrun.info");
            ProcessBuilder builder = new ProcessBuilder(EXEC_LOCATION,
                    "-alg",
                    (algorithmType == CommunityMiningParameters.FASTMODULARITY_ALGTYPE_FASTMODULARITY)
                            ? "fastmod" : "maxminmod",
                    "-f",
                    strFullFilePath,
                    "-l",
                    "secondrun",
                    "-c",
                    step);
            
            //generating all files in the /temp directory.
            File flRedirectedOutput = new File(curDir+"/temp/"+"fastmodularity_redirect");
            if(!flRedirectedOutput.exists())
                flRedirectedOutput.mkdirs();
            
            builder.redirectOutput(flRedirectedOutput);            
            flRedirectedOutput.delete() ;            
            
            process = builder.start();
            
            process.waitFor();
            
        } catch (InterruptedException e) {
            process.destroy();
            System.out.println("FastModularity.mineGraph() -  Process Destroyed!");
            return -1;
            //throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Fast Modularity could not be run on this graph. This error is"
                    + " display if the graph is disconnected or collapsed. "
                    + "Please try a different algorithm");
        }

        List<List<Integer>> lstCommunities = FastModularityOutputReader.parseCommunities(
                "temp-fc_secondrun.groups");
        
        int intComIndex = 0;
        for (List<Integer> com : lstCommunities) {
            hmpCommunities.put(intComIndex + "", new LinkedList<>());
            for (int vindex : com) {
                hmpCommunities.get(intComIndex+"").add(plstVertices.get(vindex -1).getId());
            }
            intComIndex++;
        }

        // Remove temporary files.
        cleanup();
        
        return intComIndex;

    }
    
    private void cleanup(){
        (new File("temp-fc_firstrun.info")).delete();
        (new File("temp-fc_firstrun.joins")).delete();
        (new File("temp-fc_secondrun.info")).delete();
        (new File("temp-fc_secondrun.joins")).delete();
        (new File("temp-fc_secondrun.groups")).delete();
        (new File("temp-fc_secondrun.hist")).delete();
        (new File("temp-fc_secondrun.wpairs")).delete();
        (new File("temp.pairs")).delete();
    }

    /*
     * (non-Javadoc)
     * 
     * @see ca.aicml.meerkat.plugins.mining.IMinerPlugin#requiresParameters()
     */

    /**
     *
     * @return
     */
    
    public boolean requiresParameters() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */

    /**
     *
     * @return
     */
    
    @Override
    public String toString() {
        return STR_NAME;
    }

}
