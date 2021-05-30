package io.algorithmutility;

import io.graph.reader.GraphReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class RosvallInfomodReader<V extends IVertex, E extends IEdge<V>> {

    /**
     *
     * @param filename
     * @return
     */
    public HashMap<String, List<Integer>> extractPartition(String filename) {
        HashMap<String, List<Integer>> communities
                = new HashMap<>();

        // System.out.println("RosvallInfomodReader.extractPartiotion() : fileName --> " + filename);
        try {
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));

            BufferedReader br = new BufferedReader(reader, GraphReader.BUFFER_SIZE);

            String s = br.readLine();
            boolean beginParsing = false;

            while (s != null) {
                // System.out.println("RosvallIndomodReader.extractPartition(): " + s);
                if (s.contains("nodes")) {
                    beginParsing = true;
                } else if (beginParsing) {
                    if (s.trim().isEmpty()) {
                        s = br.readLine();
                        continue;
                    }
                    String communityID = s.split(" ")[0];
                    String vertexID = s.split(" ")[1].replace("\"", "");

                    if (!communities.containsKey(communityID)) {
                        communities.put(communityID, new LinkedList<>());
                    }
                    communities.get(communityID).add(Integer.parseInt(vertexID) - 1);
                }
                s = br.readLine();
            }

            br.close();
            reader.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return communities;
    }
}
