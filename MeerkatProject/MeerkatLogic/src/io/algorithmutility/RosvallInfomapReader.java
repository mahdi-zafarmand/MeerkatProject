package io.algorithmutility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import datastructure.core.graph.classinterface.*;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public class RosvallInfomapReader<V extends IVertex, E extends IEdge<V>> {

    /**
     *
     */
    public RosvallInfomapReader() {

    }
	
    /**
     *
     * @param pIGraph
     * @param pstrPath2FileName
     * @return
     */
    public HashMap<String,List<Integer>> readCommunities(
        IGraph<V,E> pIGraph, 
        String pstrPath2FileName) {

        HashMap<String, List<Integer>> comms = new HashMap<>();

        try (BufferedReader reader = 
                new BufferedReader(new FileReader(pstrPath2FileName))) {
           
            LinkedList<Integer> lltCurrentCommunity = new LinkedList<>();  
            // list of vertex MeerkatIds.

            String s;
            boolean beginParsing = false;
            while ((s = reader.readLine()) != null) {
                if (s.contains("*Nodes")) {
                    beginParsing = true;
                    continue;
                }
                if (s.contains("*Links")) {
                    beginParsing = false;
                    continue;
                }
                if (beginParsing) {
//                    System.err.println(s);
                    String communityID = s.split(":")[0];
                    String vertexID = s.split(" ")[1].replace("\"", "");

                    if (!comms.containsKey(communityID)) {
                        comms.put(communityID, new LinkedList<>());
                    } 
//                    lltCurrentCommunity =
//                            (LinkedList<Integer>) comms.get(communityID);


//                    System.err.println(communityID + " : " + (Integer.parseInt(vertexID) - 1));
                    comms.get(communityID).add(Integer.parseInt(vertexID) - 1);
                }
            }
             // list of vertex MeerkatIds.
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        return comms;
    }
}
