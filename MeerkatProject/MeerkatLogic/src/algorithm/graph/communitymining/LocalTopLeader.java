package algorithm.graph.communitymining;

import config.CommunityMiningParameters;
import io.algorithmutility.TopLeaderCommunityReader;
import io.graph.writer.WeightedPairsWriter;

import java.io.File;
import java.io.IOException;

import config.MeerkatSystem;
import config.Parameter;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author meerkat!
 * paper : Top Leaders Community Detection Approach in Information Networks
* Not sure if it is the exact implementation of the paper
 * @param <V>
 * @param <E>
 *
 * -------- runs local top leader algorithm to find communities. the parameters
 * required (set to default if not given) for the algorithm to run are as
 * follows:
 *
 * - number of clusters - outlier percentage - hub percentage
 *
 */
public class LocalTopLeader<V extends IVertex, E extends IEdge<V>> extends Miner<V, E> {

    /**
     *
     */
    public static String STR_NAME = "Local Top Leaders";

    private final String EXEC_LOCATION
            = MeerkatSystem.getTopLeaderCommunityMiningDirectory();

    private static final int DEFAULT_CLUSTER_NUMBER = 2;
    private static final double DEFAULT_OUTLIER_PERCENT = 0.0;
    private static final double DEFAULT_HUB_PERCENT = 0.0;
    private static final double DEFAULT_CENTERS = 0.0;

    private final String DIR = "";
    private final String NAME = "temp";
    private final String TYPE = ".wpairs";

    /**
     * Algorithm Parameters
     */
    private int numClusters = DEFAULT_CLUSTER_NUMBER;
    private double percentageOutliers = DEFAULT_OUTLIER_PERCENT;
    private double percentageHubs = DEFAULT_HUB_PERCENT;
    private double centers = DEFAULT_CENTERS;

    /**
     * Cosntructor Version: 2.0
     *
     * @param pIGraph dynamic graph (not null)
     * @param tf time frame (not null)
     * @param pstrParameters Array of String containing parameters of the
     * algorithm - pstrParameter[i] = the ith parameter including parameter name
     * and value --> param_name:value
     *
     * EDIT HISTORY Date Author Description 2016 Apr 20 Afra Changed 2D array of
     * parameters to 1D array (each String contains both param name and value)
     * 2016 April 19 Afra all parameters of the algorithm are sent as a single
     * String array in the constructor.
     */
    public LocalTopLeader(IDynamicGraph<V, E> pIGraph,
            TimeFrame tf,
            String[] pstrParameters) {

        super(pIGraph, tf);

        List<Parameter> lstParam = CommunityMiningParameters.getParameters(this.getClass().getSimpleName());

        if (pstrParameters != null && pstrParameters.length > 0) {
            for (String strP : pstrParameters) {
                for (Parameter p : lstParam) {
                    if (strP.startsWith(p.key)) {
                        String value = strP.substring(p.key.length() + 1).trim();
                        if (p.key.equals(CommunityMiningParameters.LOCALTOPLEADERS_NUMBEROFCLUSTERS)) {
                            this.numClusters = Integer.parseInt(value);
                            break;
                        } else if (p.key.equals(CommunityMiningParameters.LOCALTOPLEADERS_OUTLIERPERCENTAGE)) {
                            this.percentageOutliers = Double.parseDouble(value);
                            System.out.println(value + " vs. " + percentageOutliers);
                            break;
                        } else if (p.key.equals(CommunityMiningParameters.LOCALTOPLEADERS_HUBPERCENTAGE)) {
                            this.percentageHubs = Double.parseDouble(value);
                            System.out.println(value + " vs. " + percentageHubs);
                            break;
                        } else if (p.key.equals(CommunityMiningParameters.LOCALTOPLEADERS_CENTERS)) {
                            this.centers = Double.parseDouble(value);
                            System.out.println(value + " vs. " + centers);
                            break;
                        }
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

        // Mapping between FILE_ID and internal id.
        Map<String, Integer> mapFID_InternalId= new HashMap<>();
        for (V v : dynaGraph.getVertices(tf)) {
            mapFID_InternalId.put(v.getUserAttributer()
                    .getAttributeValue(MeerkatSystem.FILE_ID, tf), v.getId());
        }
        // writes to wpais file base of FILE_ID
        WeightedPairsWriter<V, E> writer = new WeightedPairsWriter<>();
        String strFullFilePath = writer.write(dynaGraph, tf, DIR + NAME);

        // Pass the data into the executable.
        execute(strFullFilePath);
        if(!running){
                return;
        }
        System.out.println(strFullFilePath);

        // Retrieve the communities from the output text file.
        String communityFileName = strFullFilePath + ".clusters";
        TopLeaderCommunityReader<V, E> comReader = new TopLeaderCommunityReader<>();
        hmpCommunities = comReader.readCommunities(communityFileName, mapFID_InternalId);
        Set<Integer> hubs = comReader.getHubs();
        Set<Integer> outliers = comReader.getOutliers();

        // Delete temporary files.
        (new File(DIR + NAME + TYPE)).delete();
        (new File(DIR + NAME + TYPE + ".clusters")).delete();
    }

    private void execute(String fileName) {
        Process process = null;
        String curDir = System.getProperty("user.dir");
        try {
            ProcessBuilder builder = new ProcessBuilder("java", "-jar", EXEC_LOCATION,
                    "-i", fileName, "-k", String.valueOf(numClusters), "-o",
                    percentageOutliers + "", "-h",
                    percentageHubs + "", "-c",
                    centers + "");
            
            System.out.println("java " + "-jar " + EXEC_LOCATION +
                    " -i " + fileName + " -k "+ String.valueOf(numClusters)+ " -o "+
                    percentageOutliers + " " + " -h "+
                    percentageHubs + " " + " -c "+
                    centers + " ");
            
            //generating all files in the /temp directory.
            File flRedirectedOutput = new File(curDir+"/temp/"+"localtopleader_redirect");   
            if(!flRedirectedOutput.exists())
                flRedirectedOutput.mkdirs();
            
            builder.redirectOutput(flRedirectedOutput);            
            flRedirectedOutput.delete() ;            
            
            process = builder.start();
            
            process.waitFor();

            System.out.println("LocalTopLeader.execute() : Execution Completed!");
        } catch (InterruptedException e) {
            process.destroy();
            System.out.println("==============----------==============  LocalTopLeader() : interrupted");
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
     * @param pdblHubPercentage
     */
    public void setParameterHubPercentage(double pdblHubPercentage) {
        this.percentageHubs = pdblHubPercentage;
    }

    /**
     *
     * @param pdblOutlierPercentage
     */
    public void setParameterOutlierPercentage(double pdblOutlierPercentage) {
        this.percentageOutliers = pdblOutlierPercentage;
    }

    /**
     *
     * @param pintNumOfClusters
     */
    public void setParameterNumberOfClusters(int pintNumOfClusters) {
        this.numClusters = pintNumOfClusters;
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
