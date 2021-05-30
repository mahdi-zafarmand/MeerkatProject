package io.algorithmutility;

import datastructure.core.TimeFrame;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import datastructure.core.graph.classinterface.*;
import java.util.LinkedList;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public class LocalCommunityReader<V extends IVertex, E extends IEdge<V>> {

    /**
     *
     */
    public LocalCommunityReader() {

    }

    /**
     *
     * @param pIGraph
     * @param filename
     * @return
     */
    public HashMap<String, List<Integer>> readCommunities(IDynamicGraph<V,E> pIGraph, 
            TimeFrame tf,String filename) {
        HashMap<String, List<Integer>> hmpVertexCommunity = new HashMap<>();

        try {
            InputStreamReader reader = new InputStreamReader(
                            new FileInputStream(filename));

            BufferedReader br = new BufferedReader(reader);

            int commNum = 0;
            String s = br.readLine();
            while (s != null) {
                s = s.trim();
                if (s.isEmpty()) {
                    s = br.readLine();
                    continue;
                }
                String[] tokens = s.split("\\s+");

                String commName = commNum + "";
                hmpVertexCommunity.put(commName, new LinkedList<>());
                for (String vertexID : tokens) {
                    int intVId = Integer.parseInt(vertexID);
                    V v = pIGraph.getVertex(intVId);
                    
                    if (v != null) {
                        hmpVertexCommunity.get(commName).add(intVId);
                    }
                }
                commNum++;
                s = br.readLine();
            } 
            
            br.close();
            
        } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
        } catch (NumberFormatException | IOException e) {
                throw new RuntimeException(e);
        }
        return hmpVertexCommunity;
    }

}
