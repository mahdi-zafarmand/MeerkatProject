package algorithm.graph.communitymining;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.BidiMap;

import config.MeerkatSystem;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import config.CommunityMiningParameters;
import config.Parameter;
import datastructure.Partitioning;
import datastructure.core.DynamicAttributable.SysDynamicAttributer.SystemDynamicAttributer;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.*;
import java.util.List;
import java.util.Vector;


// TODO: THIS CLASS NEEDS THOROUGH REVIEW AND CLEANING.

/**
 * Justin made this algorithm. Since it uses JUNG classes, it is run client
 * side via mechanisms other than those set up for calling external executables
 * for mining.
 * 
 * Except for the Graph class, I have removed JUNG usage from this. If we do
 * need it to be an external executable, we need to replace that usage.
 * Until then, it will be client side only, and called differently than the
 * other algorithms.
 * 
 *  Version         : 1
 *  @author        : Justin
 *  paper : Using Triads to Identify Local Community
Structure in Social Networks. https://webdocs.cs.ualberta.ca/~zaiane/postscript/Triad-ASONAM14.pdf
Not sure if it is the exact implementation of the paper.
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-10-19      Afra            updated the code to fit standards for the
 *                                  repository based on the need for superclass
 *                                  "GraphAlgorithm".
 * 
 * @param <V>
 * @param <E>
 */
public class LocalT<V extends IVertex,E extends IEdge<V>> extends Miner<V,E> {

    private static final int DEFAULT_COMMUNITY_NUMBER = 3;
    
    /* Algorithm Parameters */
    V startNode = null;
    int numCommunities = DEFAULT_COMMUNITY_NUMBER;

    private HashSet<V> allRealVertices;
    private Vector<Set<V>> vctCommunities = new Vector<>(); 
	
    /**
     * Constructor
     * Version: 2.0
     * @param pIGraph
     *          Dynamic Graph (not null)
     * @param tf
     *          Time Frame (not null)
     * @param pstrParameters
     *          array of String of all required parameters:
     *          - pstrParameters[i] = the ith parameter and value --> param_name:value
     * 
     * EDIT HISTORY 
     * Date         Author      Description
     * 2106 Apr 20  Afra        changed 2D array of parameters to 1D array (param name and value in same string)
     * 2016 Apr 19  Afra        all parameters are given in an array of string.
     */
    public LocalT(IDynamicGraph<V,E> pIGraph, TimeFrame tf, 
            String[] pstrParameters) {
        
        super(pIGraph, tf);
        
        List<Parameter> lstParam = CommunityMiningParameters.getParameters(this.getClass().getSimpleName());
        
        if (pstrParameters != null && pstrParameters.length > 0) {
            for (String strP : pstrParameters) {
                for (Parameter p : lstParam)  {
                    if (strP.startsWith(p.key)) {
                        String value = strP.substring(p.key.length() + 1).trim();
                        if (p.key.equals(CommunityMiningParameters.LOCALT_STARTNODEID)) {
                            int startNodeId = Integer.parseInt(value);
                            this.startNode = dynaGraph.getVertex(startNodeId);
                        } else if (p.key.equals(CommunityMiningParameters.LOCALT_NUMBEROFCM)) {
                            this.numCommunities = Integer.parseInt(value);
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
        Partitioning<V> partitions; 
        partitions = localMethod();
        vctCommunities = partitions.getCommunities();
        int comIndex = 0;
        for (Set<V> commSet : vctCommunities) {
            if(!running){
                return;
            }
            String comName = comIndex + "";
            hmpCommunities.put(comName, new LinkedList<>());
            
            for (V v : commSet) {
                hmpCommunities.get(comName).add(v.getId());
            }
            comIndex++;
        }
        if(!running){
                return;
        }
        updateDataStructure();
        blnDone = true;
    }

    /**
     *
     * @param pVStart
     */
    public void setStartVertex(V pVStart) {
        this.startNode = pVStart;
    }

    /**
     *
     * @param pIntNumOfComs
     */
    public void setNumberOfCommunities(int pIntNumOfComs) {
        this.numCommunities = pIntNumOfComs;
    }

    /**
     *
     * @return
     */
    public Partitioning<V> localMethod() {

        int totalNumberComs = 0;

        Partitioning<V> result = new Partitioning<>();

        LinkedList<V> vertsToExplore = new LinkedList<>();
        HashSet<V> unseenVerts = new HashSet<>();

        // initially dynaGraph was fetching allVertices which resulted in an error, changing it
        // to fetch only the vertices of current timeframe fixes the error.
        for (V vert : dynaGraph.getVertices(tf)) {
            vertsToExplore.add(vert);
            unseenVerts.add(vert);
        }

        /* This Indexer class was originally from JUNG, but I made a copy in
        * this package to allow this algorithm to be encapsulated into an
        * executable.
        */
        this.allRealVertices = new HashSet<>(vertsToExplore);
        BidiMap<V, Integer> indexer = Indexer.<V> create(allRealVertices); 
        // Need fake verts filtered out.

        // Copied necessary methods from GraphMatrixOperations to this class.
        SparseDoubleMatrix2D grphMatrix = graphToSparseMatrix(dynaGraph, tf);

        Collections.shuffle(vertsToExplore);

        HashSet<V> prevCommunity = new HashSet<>();
        while (vertsToExplore.size() > 0) {
            if(!running){
                return result;
            }

            V startVert = vertsToExplore.peekFirst();

            HashSet<V> unsortedNeighbours = new HashSet<>();
            for (V vert : dynaGraph.getNeighbors(startVert, tf)) {
                if (vertsToExplore.contains(vert)) {
                    unsortedNeighbours.add(vert);
                }

                for (V secondNeighbour : dynaGraph.getNeighbors(vert, tf)) {
                    if (vertsToExplore.contains(secondNeighbour)) {
                        unsortedNeighbours.add(secondNeighbour);
                    }
                }
            }
            unsortedNeighbours.add(startVert);

            Vector<V> neighbours = new Vector<>(unsortedNeighbours);

            Collections.sort(neighbours, (V v1, V v2) -> {
                return -1
                        * new Integer(dynaGraph.getDegree(v1, tf))
                                .compareTo(dynaGraph.getDegree(v2, tf));
            });
            startVert = neighbours.firstElement();

            if (startNode != null) {
                startVert = startNode;
                startNode = null;
            }

            HashSet<V> shell = new HashSet<>();
            HashSet<V> community = new HashSet<>();

            for (V neighbour : dynaGraph.getNeighbors(startVert, tf)) {
                if (vertsToExplore.contains(neighbour)) {
                        shell.add(neighbour);
                }
            }

            community.add(startVert);

            Double maxScore = null;
            Double maxInternalTri = null;
            Double minExternalTri = null;
            double prevMax = 0;
            V maxNode = null;

            Double prevExternalTriadic = null;
            Double prevInternalTriadic = null;

            Vector<Double> scoreVect = new Vector<>();
            Double runningMean = null;
            double runningVar = 0;

            HashMap<V, Double> shellToIntScore = new HashMap<>();
            HashMap<V, Double> shellToExtScore = new HashMap<>();

            while (true) {

                if (shell.isEmpty()) {
                    break;
                }

                Double selectedExternalTriadic = 0.0;
                Double selectedInternalTriadic = 0.0;

                for (V shellNode : shell) {
                    double numInternalTri = 0;

                    if (prevInternalTriadic == null)
                        numInternalTri = nodalInternalTriadic(shellNode,
                                        community, grphMatrix, indexer);
                    else {
                        Double nodeScore = shellToIntScore.get(shellNode);
                        if (nodeScore == null) {
                            nodeScore = nodalInternalTriadic(shellNode,
                                            community, grphMatrix, indexer);
                            shellToIntScore.put(shellNode, nodeScore);
                        }

                        numInternalTri = prevInternalTriadic + nodeScore;
                    }

                    double numExternalTri = 0;
                    if (prevExternalTriadic == null)
                        numExternalTri = nodalExternalTriadic(shellNode,
                                        community, grphMatrix, indexer);
                    else {
                        Double nodeScore = shellToExtScore.get(shellNode);
                        if (nodeScore == null) {
                            nodeScore = nodalExternalDiffTriadic(shellNode,
                                            community, grphMatrix, indexer);
                            shellToExtScore.put(shellNode, nodeScore);
                        }

                        numExternalTri = prevExternalTriadic + nodeScore;
                    }

                    double differential = numInternalTri - numExternalTri;
                    if (differential < 0)
                        differential = 0;

                    double score = (numInternalTri) * differential;

                    if (maxScore == null || score > maxScore) {
                        maxScore = score;
                        maxInternalTri = numInternalTri;
                        minExternalTri = numExternalTri;
                        maxNode = shellNode;
                        selectedExternalTriadic = numExternalTri;
                        selectedInternalTriadic = numInternalTri;
                    } else if (score == maxScore) { // tie breaker based on
                                                    // internal triadics
                        if (numExternalTri < minExternalTri) {
                            maxScore = score;
                            minExternalTri = numExternalTri;
                            maxNode = shellNode;
                            selectedExternalTriadic = numExternalTri;
                            selectedInternalTriadic = numInternalTri;
                        }
                    }
                }

                if (maxScore < prevMax) {
                    break;
                }

                scoreVect.add(maxScore);
                prevMax = Math.max(maxScore, prevMax);
                maxScore = -0.1;
                community.add(maxNode);

                prevExternalTriadic = selectedExternalTriadic;
                prevInternalTriadic = selectedInternalTriadic;
                shell.remove(maxNode);

                for (V neighbour : dynaGraph.getNeighbors(maxNode, tf)) {
                        shellToExtScore.remove(neighbour);
                        shellToIntScore.remove(neighbour);

                        if (community.contains(neighbour)
                                        || !vertsToExplore.contains(neighbour))
                                continue;
                        shell.add(neighbour);
                }
            }

            if (prevCommunity.equals(community)) {
                for (V vert : community) {
                    double nodalComExternal = nodalExternalTriadic(vert,
                                    community, grphMatrix, indexer);
                    double nodalComInternal = nodalInternalTriadic(vert,
                                    community, grphMatrix, indexer);

                    if (nodalComExternal > nodalComInternal) {
                        result.addHub(vert);
                    } else {
                        result.addOutlier(vert);
                    }

                    vertsToExplore.remove(vert);

                }
                community.clear();
            }

            prevCommunity = new HashSet<>(community);
            if (community.size() > 0) {
                HashSet<V> comVerts = new HashSet<>(community);

                Vector<Double> numTriads = new Vector<>();
                double maxNum = 0;
                double avg = 0;
                double minVal = 999999;
                double nonZeros = 0;
                for (V vert : comVerts) {
                    double nodalComInternal = nodalInternalTriadic(vert,
                                    community, grphMatrix, indexer);

                    numTriads.add(nodalComInternal);
                    maxNum = Math.max(nodalComInternal, maxNum);
                    avg += nodalComInternal;
                    if (nodalComInternal == 0 || nodalComInternal < minVal)
                        minVal = nodalComInternal;

                    if (nodalComInternal > 0)
                        nonZeros++;
                }

                avg = (avg) / (comVerts.size());

                double variance = 0.0;
                for (Double dbl : numTriads) {
                    variance += (dbl - avg) * (dbl - avg);
                }
                variance = variance / (comVerts.size());
                double standardDev = Math.sqrt(variance);

                for (V vert : comVerts) {

                    double nodalComExternal = nodalExternalTriadic(vert,
                                    community, grphMatrix, indexer);
                    double nodalComInternal = nodalInternalTriadic(vert,
                                    community, grphMatrix, indexer);

                    community.remove(vert);

                    if (variance == 0 && avg == 0) {
                        community.clear();
                        break;
                    }

                    boolean outlier = false;
                    boolean hub = false;

                    double outlierThreshold = Math.floor(avg - standardDev);

                    if (nodalComExternal > nodalComInternal
                                    && nodalComInternal > 0) {
                        hub = true;

                        if (!result.isHub(vert))
                            result.addHub(vert);

                        while (vertsToExplore.remove(vert) == true);
                        // We remove a potentially crucial part of this
                        // community, dissolve it and check if it reforms.
                        community.clear();
                        break;
                    }

                    else if (nodalComInternal < outlierThreshold
                                    && nodalComExternal == 0) {
                        outlier = true;
                        result.addOutlier(vert);
                        vertsToExplore.remove(vert);
                    }

                    if (!outlier && !hub) {
                        if (nodalComExternal <= nodalComInternal) {
                            community.add(vert);
                        }
                    }
                }

            }

            if (!community.isEmpty()) {
                totalNumberComs++;
                result.addCluster(community);
                if (totalNumberComs >= numCommunities && numCommunities > 0) {
                    return result;
                }

                for (V vert : community) {
                    if (result.isHub(vert)) {
                        result.removeHub(vert);
                    }
                    while (vertsToExplore.remove(vert) == true);
                }

                for (V hub : result.getHubs()) {
                    vertsToExplore.add(hub);
                }
            }
        }

        return result;
    }

    /**
     * Copied from the JUNG GraphMatrixOperations class, to allow this to be
     * encapsulated in an executable.
     * 
     * Returns an unweighted (0-1) adjacency matrix based on the specified
     * graph.
     * 
     * @param g
     *            the graph to convert to a matrix
     * @param tf
     * @return 
     */
    public SparseDoubleMatrix2D graphToSparseMatrix(IDynamicGraph<V, E> g, TimeFrame tf) {
            return graphToSparseMatrix(g, tf,  null);
    }

    /**
     * Copied from the JUNG GraphMatrixOperations class, to allow this to be
     * encapsulated in an executable.
     * 
     * Returns a SparseDoubleMatrix2D whose entries represent the edge weights
     * for the
     * edges in <code>g</code>, as specified by <code>nev</code>.
     * 
     * <p>
     * The <code>(i,j)</code> entry of the matrix returned will be equal to the
     * sum of the weights of the edges connecting the vertex with index
     * <code>i</code> to <code>j</code>.
     * 
     * <p>
     * If <code>nev</code> is <code>null</code>, then a constant edge weight of
     * 1 is used.
     * 
     * @param g
     * @param tf
     * @param nev
     * @return 
     */
    public SparseDoubleMatrix2D graphToSparseMatrix(IDynamicGraph<V, E> g,
            TimeFrame tf,
                    Map<E, Number> nev) {
        
            if (nev == null) {
                    // Dealt with null when setting doubleValue...see below...
                    // nev = new ConstantMap<E,Number>(1);
            }
            int numVertices = g.getVertexCount();
            SparseDoubleMatrix2D matrix = new SparseDoubleMatrix2D(numVertices,
                            numVertices);

            // BidiMap<V,Integer> indexer = Indexer.<V>create(g.getVertices());
            BidiMap<V, Integer> indexer = Indexer
                            .<V> create(allRealVertices);
            int i = 0;

            // for(V v : g.getVertices()){
            for (V v : allRealVertices) {
                    for (E e : g.getOutgoingEdges(v, tf)) {
                            V w = e.getDestination();

                            int j = indexer.get(w);
                            double doubleValue;
                            if (nev == null) {
                                    doubleValue = 1.0;
                            } else {
                                    doubleValue = nev.get(e).doubleValue();
                            }
                            matrix.set(i, j, matrix.getQuick(i, j) + doubleValue);
                    }
                    i++;
            }
            return matrix;
    }

    /**
     *
     * @param vert
     * @param community
     * @param grphMatrix
     * @param indexer
     * @return
     */
    public double nodalExternalTriadic(V vert,
                    HashSet<V> community, SparseDoubleMatrix2D grphMatrix,
                    BidiMap<V, Integer> indexer) {
            double numTriadic;

            double cv = 0;

            Collection<V> col = dynaGraph.getNeighbors(vert, tf);

            for (V n1 : col) {
                    if (community.contains(n1))
                            continue;
                    for (V n2 : col) {
                            if (community.contains(n2))
                                    continue;
                            if (n1 == n2 || n1 == vert || n2 == vert)
                                    continue;
                            int indexn1 = indexer.get(n1);
                            int indexn2 = indexer.get(n2);

                            double value = grphMatrix.getQuick(indexn1, indexn2);
                            if (value > 0) {
                                    cv++;
                            }
                    }
            }

            numTriadic = cv / 2;
            return numTriadic;

    }

    /**
     *
     * @param vert
     * @param community
     * @param grphMatrix
     * @param indexer
     * @return
     */
    public double nodalExternalDiffTriadic(V vert,
                    HashSet<V> community, SparseDoubleMatrix2D grphMatrix,
                    BidiMap<V, Integer> indexer) {
        double numTriadic;

        double cv = 0;

        Collection<V> neighbours = dynaGraph.getNeighbors(vert, tf);
        for (V n1 : neighbours) {

            boolean contains1 = community.contains(n1);
            if (contains1) {
                continue;
            }

            for (V n2 : neighbours) {
                if (n1 == n2) {
                    continue;
                }

                int indexn1 = indexer.get(n1);
                int indexn2 = indexer.get(n2);

                double value = grphMatrix.getQuick(indexn1, indexn2);

                if (value <= 0) {
                    continue;
                }

                if (community.contains(n2)) {
                    cv--;
                    cv--;
                } else {
                    cv++;
                }
            }
        }

        numTriadic = cv / 2;
        return numTriadic;
    }

    /**
     *
     * @param vert
     * @param community
     * @param grphMatrix
     * @param indexer
     * @return
     */
    public double nodalInternalTriadic(V vert,
                    HashSet<V> community, SparseDoubleMatrix2D grphMatrix,
                    BidiMap<V, Integer> indexer) {
        double numTriadic = 0;

        double cv = 0;
        double temp = 0.0;

        Collection<V> col = dynaGraph.getNeighbors(vert, tf);

        for (V n1 : col) {

            if (!community.contains(n1)){
                continue;
            }
            
            for (V n2 : col) {
                if (!community.contains(n2)) {
                    continue;
                }

                if (n1 == n2 || n1 == vert || n2 == vert) {
                    continue;
                }
                int indexn1 = indexer.get(n1);
                int indexn2 = indexer.get(n2);

                double value = grphMatrix.getQuick(indexn1, indexn2);
                if (value > 0) {
                    cv++;
                }
            }
        }

        numTriadic = ((cv + temp) / 2.0);
        return numTriadic;
    }
	
    /**
     *
     * @return
     */
//    @Override
//    public synchronized boolean updateDataStructure() {
//        /*
//         * Update the date structure only when the COMMUNITY attribute is not out-dated.
//         * Meaning when it was not added yet or the time is older than dtLastUpdate. 
//         */
//        if (! (SystemDynamicAttributer.hmpAttTime.containsKey(MeerkatSystem.COMMUNITY) && 
//            (SystemDynamicAttributer.hmpAttTime.get(MeerkatSystem.COMMUNITY)
//                    .get(tf).after(dtCallTime)))) {
//
//            int communityCount = 0;
//            for(Set<V> com : vctCommunities) {
//                for (V v: com) {
//                    v.getSystemAttributer().addAttributeValue(
//                            MeerkatSystem.COMMUNITY,
//                            communityCount+"",
//                            dtCallTime,
//                            tf);
//                }
//                communityCount++;
//            }
//        }
//        return true;
//    }

    /**
     *
     */
    @Override
    public void writeToFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
