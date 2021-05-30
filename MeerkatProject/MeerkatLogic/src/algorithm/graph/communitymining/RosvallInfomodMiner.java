package algorithm.graph.communitymining;

import config.CommunityMiningParameters;
import io.algorithmutility.RosvallInfomodReader;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import config.MeerkatSystem;
import config.Parameter;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import io.graph.writer.algorithm.RosvallWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author aabnar
 * paper : An information-theoretic framework for resolving community structure in
complex networks. 
* Not sure if it is the exact implementation.
 * @param <V>
 * @param <E>
 */
public class RosvallInfomodMiner<V extends IVertex, E extends IEdge<V>>
        extends Miner<V, E> {

    /**
     *
     */
    public static final String STR_NAME = "Rosvall Infomod";

    private final String EXEC_LOCATION
            = MeerkatSystem.getRosvallInfomodMinerDirectory();

    private static final int DEFAULT_NUMBER_OF_ATTEMPTS = 2;

    private final String DIR = "";
    private final String NAME = "temp";

    /* Algorithm Parameter */
    private int numAttempts = DEFAULT_NUMBER_OF_ATTEMPTS;

    /**
     * Constructor Version: 2.0
     *
     * @param pIGraph dynamic graph (not null)
     * @param tf time frame (not null)
     * @param pstrParameters Array of String of required parameters: -
     * pstrParameters[i] = the ith parameter with its value --> param_name:value
     *
     * EDIT HISTORY Date Author Description 2016 Apr 20 Afra Changed 2D array of
     * parameters to 1D array (String includes both parameter name and its
     * value) 2016 Apr 19 Afra all parameters to the algorithm are put in an
     * array of Strings.
     */
    public RosvallInfomodMiner(IDynamicGraph<V, E> pIGraph,
            TimeFrame tf,
            String[] pstrParameters) {

        super(pIGraph, tf);
        
        System.out.println("RosvallInfomodMiner.Constructor() : parameters = " );
        

        List<Parameter> lstParam = CommunityMiningParameters
                .getParameters(this.getClass().getSimpleName());

        if (pstrParameters != null && pstrParameters.length > 0) {
            for (String strP : pstrParameters) {
                System.out.println(strP);

                for (Parameter p : lstParam) {
                    if (strP.startsWith(p.key)) {
                        String value = strP.substring(p.key.length() + 1).trim();
                        if (p.key.equals(CommunityMiningParameters.ROSVALLINFOMOD_NUMBEROFATTEMPTS)) {
                            System.out.println("RosvallInfomod.Constructor() : Found the Parameter");
                            this.numAttempts = Integer.parseInt(value);
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
        List<V> lstVertices = dynaGraph.getVertices(tf);
        
        String strFullFilePath = RosvallWriter.RosvallWriter(dynaGraph, tf, lstVertices, DIR + NAME);

        System.out.println("RosvallInfomodMinge.mineGraph() : FullFilePath = " + strFullFilePath);
        // Pass the data into the executable.                
        execute(strFullFilePath);
        if(!running){
                return;
        }
        // Retrieve the communities from the output text file.
        RosvallInfomodReader<V, E> outputReader
                = new RosvallInfomodReader<>();

        HashMap<String,List<Integer>> mapTempResults = 
                outputReader.extractPartition(DIR + NAME + ".mod");
        if(!running){
                return;
        }
        for (String com : mapTempResults.keySet()) {
            hmpCommunities.put(com, new LinkedList<>());
            for (int vid : mapTempResults.get(com)) {
//                System.out.println("RosvallInfomodMiner.mineGraph() : vid vs. lst size = " + vid + "," + lstVertices.size());
                hmpCommunities.get(com).add(lstVertices.get(vid).getId());
            }
        }
        
        // Delete the temporary files.
        (new File(strFullFilePath)).delete();
        (new File(DIR + NAME + ".clu")).delete();
        (new File(DIR + NAME + ".mod")).delete();
    }

    private void execute(String fileName) {
        Random rand = new Random();
        Process process = null;
        String curDir = System.getProperty("user.dir");
        int randomSeed = rand.nextInt(Integer.MAX_VALUE) + 1;
        try {            
            ProcessBuilder processBuilder = new ProcessBuilder(EXEC_LOCATION,
                    String.valueOf(randomSeed), fileName,
                    String.valueOf(numAttempts)) ;
            
            ////generating all files in the /temp directory.
            File tempDir = new File (curDir+"/temp");
            if(!tempDir.exists())
                tempDir.mkdirs();
            
            File flRedirectedOutput = new File(tempDir+"/"+"rosvallinfomod_redirect");
          
            processBuilder.redirectOutput(flRedirectedOutput);            
//            flRedirectedOutput.delete() ;            
            
            process = processBuilder.start(); ;
            
            System.out.println(EXEC_LOCATION);
            
            System.out.println("RosvallInfomod.execute() : " + 
                    EXEC_LOCATION + " " +
                    randomSeed + " " +
                    fileName + " " +
                    numAttempts);
            process.waitFor();
            
            // System.out.println("Arch: "+System.getProperty("os.arch"));
        } catch (InterruptedException e) {
            process.destroy();
            System.out.println("==============----------==============  RosvallInfomodMiner() : interrupted! ");
//            e.printStackTrace();
//            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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
