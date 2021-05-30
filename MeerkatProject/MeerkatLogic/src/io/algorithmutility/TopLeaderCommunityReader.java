package io.algorithmutility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import datastructure.core.graph.classinterface.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ClassName : TopLeaderCommunityReader
 * @author aabnar
 * @param <V>
 * @param <E>
 * 
 * Edit History:
 * DATE         AUTHOR          DESCRIPTION
 * 13 Oct 2016  Afra            Added the mapping between FILE_ID to id in the 
 *                              method which reads communities from file.
 */
public class TopLeaderCommunityReader<V extends IVertex, E extends IEdge<V>> {

    enum parseState{
        community,
        hub,
        outlier
    }
    
    Set<Integer> setHubs;
    Set<Integer> setOutliers;
    parseState state;
    
    /**
     *
     */
    public TopLeaderCommunityReader() {

    }

    /**
     *
     * MethodName : readCommunities
     * Description: read community results from LocalTopLeader CM Alg.
     *              since .wpairs file reads nodes based on their FILE_ID, 
     *              we need the mapping from FILE_ID to internal id to store
     *              the right vertex id.
     * @param pFileName
     * @param maptoId
     * @return map of community name to list of vertices in that community
     */
    public HashMap<String, List<Integer>> readCommunities(String pFileName,
                                    Map<String, Integer> maptoId) {

        HashMap<String, List<Integer>> hmpCommunityMapping = new HashMap<>();

//        System.out.println("CommunityReader.readCommunities() : filename --> " + pFileName);
        
        try {
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(pFileName));

            BufferedReader br = new BufferedReader(reader);

            int commNum = 0;
            String s = br.readLine();
            while (s != null) {
                // Declaration of a new community.
                if (s.trim().isEmpty()) {
                    s = br.readLine();
                    continue;
                }
                if (s.toLowerCase().contains("community")) {
                    state = parseState.community;
                    commNum++;
                    hmpCommunityMapping.put(commNum + "",
                            new LinkedList<>());
                } else if (s.toLowerCase().contains("hub")) {
                    state = parseState.hub;
                    if(setHubs == null) {
                        setHubs = new HashSet<>();
                    }
                } else if (s.toLowerCase().contains("outlier")) {
                    state = parseState.outlier;
                    if (setOutliers == null) {
                        setOutliers = new HashSet<>();
                    }
                } // Adding to vertex to current community.
                else {
                    switch (state) {
                        case community:
                            hmpCommunityMapping.get(commNum + "")
                                    .add(maptoId.get(s));
                            break;
                        case hub:
                            setHubs.add(maptoId.get(s));
                            break;
                        case outlier:
                            setOutliers.add(maptoId.get(s));
                            break;
                    }
                }
                
                s = br.readLine();
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hmpCommunityMapping;
    }

    public Set<Integer> getHubs() {
        return setHubs;
    }

    public Set<Integer> getOutliers() {
        return setOutliers;
    }
}
