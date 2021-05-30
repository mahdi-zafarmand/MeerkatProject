package algorithm.graph.communitymining;

import config.CommunityMiningParameters;
import io.algorithmutility.LocalCommunityReader;
import io.graph.writer.WeightedPairsWriter;

import java.io.File;
import java.io.IOException;

import config.MeerkatSystem;
import config.Parameter;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public class LocalCommunityMiner<V extends IVertex, E extends IEdge<V>> 
                                                        extends Miner<V,E> {

    /**
     *
     */
    public static String STR_NAME = "Local Community Mining";
    
    /**
     *
     */
    public enum AlgorithmType {

        /**
         *
         */
        M,

        /**
         *
         */
        L,

        /**
         *
         */
        R
    }

    private final String EXEC_LOCATION = 
                    MeerkatSystem.getLocalCommunityMiningDirectory();

    private final String DIR = "";
    private final String NAME = "temp";

    /**
     *
     */
    public static final AlgorithmType DEFAULT_TYPE = AlgorithmType.M;

    /**
     *
     */
    public static final boolean DEFAULT_OVERLAP = false;
    
    /* Algorithm Parameters */
    private AlgorithmType algorithmType = DEFAULT_TYPE;
    
    private boolean overlap = DEFAULT_OVERLAP;

    /**
     * Constructor
     * Version: 2.0
     * Author: 
     * @param pIGraph
     *          dynamic graph (not null)
     * @param tf
     *          time frame (not null)
     * @param pstrParameters
     *          Array of string containing parameters for the algorithm:
     *          - pstrParameters[i] = the ith parameter name and value --> Parametername:value
     * 
     * EDIT HISTORY
     * Date             Author              Description
     * 2016 Apr 20      Afra                Changed 2D array of parameter to 1D array (param name and value both in same string)
     * 2016 April 19    Afra                Changed different parameters of the algorithm to a single array of all parameters.
     * 
     */
    public LocalCommunityMiner(IDynamicGraph<V,E> pIGraph, 
            TimeFrame tf, 
            String[] pstrParameters) {
        
        super(pIGraph, tf);
        
        List<Parameter> lstParam = CommunityMiningParameters.getParameters(this.getClass().getSimpleName());
        
        if (pstrParameters != null) {
            for (String strP : pstrParameters) {
                for (Parameter p : lstParam) {
                    if (strP.startsWith(p.key)) {
                        String value = strP.substring(p.key.length()+1).trim();
                        if (p.key.equals(CommunityMiningParameters.LOCALCM_ALGORITHMTYPE)) {
                            if (value.equals(CommunityMiningParameters.LOCALCM_ALGTYPE_M)) {
                                this.algorithmType = AlgorithmType.M;
                            } else if (value.equals(CommunityMiningParameters.LOCALCM_ALGTYPE_L)) {
                                this.algorithmType = AlgorithmType.L;
                            } else if (value.equals(CommunityMiningParameters.LOCALCM_ALGTYPE_R)) {
                                this.algorithmType = AlgorithmType.R;
                            }
                        } else if (p.key.equals(CommunityMiningParameters.LOCALCM_OVERLAP)) {
                            this.overlap = Boolean.parseBoolean(value);
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     *
     */
    @Override
    public void run() {
        mineGraph();
        if(!running){
                return;
        }
        updateDataStructure();
        blnDone = true;
    }

    /**
     *
     */
    public void mineGraph() {
        // Convert the graph into pairs format.
        WeightedPairsWriter<V,E> writer = new WeightedPairsWriter<>();

        String strFullFilePath = writer.write(dynaGraph, tf, DIR + NAME);

        // Pass the data into the executable.
        execute(strFullFilePath, 
                dynaGraph.getVertices(tf).get(0).getUserAttributer()
                        .getAttributeValue(MeerkatSystem.FILE_ID, tf)/*getMinimumVertexWithEdgeID(dynaGraph.getGraph(tf))*/);
        if(!running){
                return;
        }
        // Retrieve the communities from the output text file.
        LocalCommunityReader<V, E> lcrCommunityReader = 
                                    new LocalCommunityReader<>();
        
        String resultFilePath = System.getProperty("user.dir")+"/temp/";
        hmpCommunities = lcrCommunityReader.readCommunities(dynaGraph, tf, resultFilePath+DIR + NAME + ".result");

        // Delete the temporary files.
//        (new File(strFullFilePath)).delete();
//        (new File(DIR + NAME + ".result")).delete();

    }

//    private int getMinimumVertexWithEdgeID(IGraph<V,E> graph) {
//        int minID = Integer.MAX_VALUE;
//        for (int eid : graph.getAllEdgeIds()) {
//            IEdge e = dynaGraph.getEdge(eid);
//            IVertex vs = e.getSource();
//            int vsID = vs.getId();
//            if (vsID < minID) {
//                minID = vsID;
//            }
//            IVertex vd = e.getDestination();
//            int vdID = vd.getId();
//            if (vdID < minID) {
//                minID = vdID;
//            }
//        }
//        return minID;
//    }

    private void execute(String filename, String startNodeID) {
        Process process = null;
        String curDir = System.getProperty("user.dir");
        try {
            //ProcessBuilder builder = new ProcessBuilder(EXEC_LOCATION,
            ProcessBuilder builder = new ProcessBuilder(EXEC_LOCATION,
                filename, algorithmType.toString(), overlap ? "1" : "0", "-1",
                startNodeID+""/*, " > " + " temp.result"*/);
            
            System.out.println(EXEC_LOCATION + " " +
                filename + " " + algorithmType + "  " +
                    (overlap ? "1" : "0") + " " + "-1" + " " +
                startNodeID+"");
            
            ////generating all files in the /temp directory.
            File tempDir = new File (curDir+"/temp");
            if(!tempDir.exists())
                tempDir.mkdirs();
            
            builder.redirectOutput(new File(tempDir+"/"+DIR + NAME + ".result"));
            process = builder.start();
            
            process.waitFor();
            
        } catch (InterruptedException e) {
            process.destroy();
            System.out.println("LocalCommunityMiner.execute() - Mining Interrupted!");
//            e.printStackTrace();
//            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public boolean requiresParameters() {
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return STR_NAME;
    }
	
}
