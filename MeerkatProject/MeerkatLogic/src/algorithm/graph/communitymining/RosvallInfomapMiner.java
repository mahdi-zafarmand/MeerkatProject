package algorithm.graph.communitymining;

import config.CommunityMiningParameters;
import io.algorithmutility.RosvallInfomapReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import config.MeerkatSystem;
import config.Parameter;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import io.graph.writer.algorithm.RosvallWriter;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author aabnar
 * paper: Maps of random walks on complex networks
reveal community structure. Not sure if it is the exact implementation of the paper.
 * @param <V>
 * @param <E>
 */
public class RosvallInfomapMiner<V extends IVertex, E extends IEdge<V>> 
                                                        extends Miner<V,E> {

    private String execLocation = 
            MeerkatSystem.getRosvallInfomapMinerDirectory(false);
    
    private static final int DEFAULT_NUMBER_OF_ATTEMPTS = 2;
    private static final boolean DEFAULT_DIRECTED = false;

    /* Algorithm Parameters */

    private boolean directed = DEFAULT_DIRECTED;
    private int numAttempts = DEFAULT_NUMBER_OF_ATTEMPTS;

    /**
     * Constructor
     * Version: 2.0
     * @param pIGraph
     *          dynamic graph (not null)
     * @param tf
     *          time frame (not null)
     * @param pstrParameters
     *          Array of String containing required parameters for the algorithm:
     *          - pstrParameters[i] = the ith parameter including param name and value --> param_name:value
     * 
     * EDIT HISTORY
     * DATE         Author      Description
     * 2016 Apr 20  Afra        Changed 2D array of parameter to 1D array (each String includes both param name and value)
     * 2016 Apr 19  Afra        all parameters are given in an array of String.
     */
    public RosvallInfomapMiner(IDynamicGraph<V, E> pIGraph, 
            TimeFrame tf, 
            String[] pstrParameters) {
        super(pIGraph, tf);
        
        List<Parameter> lstParam = CommunityMiningParameters.getParameters(this.getClass().getSimpleName());
        if (pstrParameters != null && pstrParameters.length > 0 ) {
            for (String strP : pstrParameters) {
                for (Parameter p : lstParam) {
                    if (strP.startsWith(p.key)) {
                        String value = strP.substring(p.key.length() + 1).trim();
                        if (p.key.equals(CommunityMiningParameters.ROSVALLINFOMAP_DIRECTED)) {
                            this.directed = Boolean.parseBoolean(value);
                        } else if (p.key.equals(CommunityMiningParameters.ROSVALLINFOMAP_NUMBEROFATTEMPTS)) {
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
        execLocation = MeerkatSystem.getRosvallInfomapMinerDirectory(directed);
        // Convert the graph into pairs format.
        List<V> lstVertices = dynaGraph.getVertices(tf);
        RosvallWriter.RosvallWriter(dynaGraph, tf, lstVertices, "temp");

        // Pass the data into the executable.
        try {
                execute("temp.net");
        } catch (IOException e) {
                throw new RuntimeException(e);
        }
        if(!running){
                return;
        }
        // Retrieve the communities from the output text file.
        RosvallInfomapReader<V, E> rsvComReader = new RosvallInfomapReader<>();
        
        HashMap<String, List<Integer>> lstTempComs = 
                rsvComReader.readCommunities(dynaGraph.getGraph(tf), "temp.map");
        
        for (String com : lstTempComs.keySet()) {
            hmpCommunities.put(com, new LinkedList<>());
            for (int vindex : lstTempComs.get(com)) {
//                System.err.println(com + " : index = " + vindex + " , id = " +
//                        lstVertices.get(vindex).getId());
                hmpCommunities.get(com).add(lstVertices.get(vindex).getId());
            }
        }
        
        //cleanup
        //moving temp.map to /temp folder.
        File tempFile = new File("temp.map");
        if(tempFile.exists())
            tempFile.renameTo(new File(System.getProperty("user.dir")+"/temp/"+"temp.map"));
        // Delete the temporary files.
        (new File("temp.net")).delete();
//        (new File("temp.map")).delete();
        (new File("temp.clu")).delete();
        (new File("temp_map.net")).delete();
        (new File("temp_map.vec")).delete();
        (new File("temp.tree")).delete();

    }

    private void execute(String fileName) throws IOException {
        Random rand = new Random();
        Process process = null;
        String curDir = System.getProperty("user.dir");
        int randomSeed = rand.nextInt(Integer.MAX_VALUE) + 1;
        try {
            ProcessBuilder builder = new ProcessBuilder(execLocation,
                            String.valueOf(randomSeed), fileName,
                            String.valueOf(numAttempts));
            
            //generating all files in the /temp directory.
            File flRedirectedOutput = new File(curDir+"/temp/"+"rosvallinfomap_redirect");   
            if(!flRedirectedOutput.exists())
                flRedirectedOutput.mkdirs();
                      
            builder.redirectOutput(flRedirectedOutput);            
            flRedirectedOutput.delete() ;            
            
            process = builder.start();

            process.waitFor();
        } catch (InterruptedException e) {
            process.destroy();
            System.out.println("RosvallInfomapMiner.execute() - Mining Interrupted!");
            //throw new RuntimeException(e);
        }catch( Exception ex){
            ex.printStackTrace();
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
        return "Rosvall Infomap";
    }

    /**
     *
     * @return
     */
//    @Override
//    public synchronized boolean updateDataStructure() {
//        
//        HashMap<Integer,String> hmpVtoCom = 
//                        new HashMap<>();
//        for (String comId : hmpCommunities.keySet()) {
//            for (int vId : hmpCommunities.get(comId)) {
//                if(!hmpVtoCom.containsKey(vId)) {
//                    hmpVtoCom.put(vId, comId);
//                } else {
//                    hmpVtoCom.put(vId, hmpVtoCom.get(vId) + "," + comId);
//                }
//            }
//        }
//        
//        return true;
//    }

}
