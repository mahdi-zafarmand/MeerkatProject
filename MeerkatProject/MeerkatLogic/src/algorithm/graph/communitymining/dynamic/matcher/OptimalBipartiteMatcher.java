/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.communitymining.dynamic.matcher;

import algorithm.graph.communitymining.dynamic.auxilaryDS.EvolvingCommunity;
import algorithm.graph.communitymining.dynamic.auxilaryDS.Snapshot;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class implements optimal bipartite matching algorithm.
 *
 * Here, for each snapshot, a graph containing all the groups at that snapshot
 * and also all the other groups at previous snapshots is built. The graphs at
 * the current snapshot are connected to previous groups if their similarity is
 * more than threshold.
 *
 * The aggregate graph is built for each snapshot separately. The first snapshot
 * (i.e. t=0) does not have any past. So there wouldn't any match for the
 * communities at this time.
 *
 * For the second snapshot, a graph containing all the group at second snapshot
 * and groups at first snapshots is built. The groups at 2nd snapshot are
 * connected to groups at 1st snapshot if their similarity is more than
 * identityThreshold. Then the optimal bipartite matching find the optimal
 * matches for groups at 2nd snapshots to groups at 1st snapshot.
 *
 *
 * Same procedure repeated for 3rd, 4th, .... snapshots. The only difference is
 * that at the i-th iteration if there are unmatched groups regarding to the
 * groups at i-1 snapshots, a new graph based on the unmatched groups at groups
 * at i-2 is built and so on.
 *
 * @author takaffol
 *
 */
public class OptimalBipartiteMatcher<V extends IVertex, E extends IEdge<V>> implements Matcher<V,E>{

    private final List<Snapshot<V,E>> snapshots;
    private final double identityThreshold;

    public OptimalBipartiteMatcher(List<Snapshot<V,E>> snapshots,
            double identityThreshold) {
        this.snapshots = snapshots;
        this.identityThreshold = identityThreshold;
    }

    @Override
    public void getMatching() {
        for (int curSnapIndex = 0; curSnapIndex < snapshots.size(); curSnapIndex++) {
            Snapshot<V,E> curSnapshot = snapshots.get(curSnapIndex);
            
//            System.out.println("OptimalBipartiteMatcher.getMatching(): snapshot index : # communities --> " + curSnapIndex +" : "+ curSnapshot.getCommunities().size());
            // Storing the unmatched groups
            ArrayList<EvolvingCommunity<V>> unmatchedGroups = new ArrayList<>();
            unmatchedGroups.addAll(curSnapshot.getCommunities());

            // Two communities can not match to communities with unique IDs
            // (i.e. unique meta communities)
            ArrayList<String> matchedIds = new ArrayList<>();

            int preSnapIndex = curSnapIndex - 1;

            while (preSnapIndex >= 0 && unmatchedGroups.size() > 0) {
                Snapshot<V,E> preSnapshot = snapshots.get(preSnapIndex);

                // Storing the left side in bipartite
                ArrayList<EvolvingCommunity<V>> preCommunities = new ArrayList<>();
                for (EvolvingCommunity<V> preComm : preSnapshot.getCommunities()) 
                // make sure the id (i.e. meta community) has not
                // selected yet
                {
                    if (!matchedIds.contains(preComm.getId())) {
                        preCommunities.add(preComm);
                    }
                }

                MWBMatchingAlgorithm matcher = new MWBMatchingAlgorithm(
                        preCommunities.size(), unmatchedGroups.size());

                for (int i = 0; i < preCommunities.size(); i++) {
                    for (int j = 0; j < unmatchedGroups.size(); j++) {
//                        double similarity = 0 ;

                        
                        double similarity = preSnapshot.computeSimilarity(
                                unmatchedGroups.get(j).getVertices()
                                        , preCommunities.get(i).getVertices(), 
                                        identityThreshold);
                        
                        if (similarity > 0) {
                            matcher.setWeight(i, j, similarity);
//                            System.out.println("Matching["+i+"] = " + j + 
//                                " (similarity = " + similarity + ")");
                        } /*
                         * For the edges that are not exist
                         * Double.NEGATIVE_INFINITY has to be set as weight
                         */ else {
                            matcher.setWeight(i, j, Double.NEGATIVE_INFINITY);
                        }
                        

                    }
                }

                // contains the optimal matching of each left side node. If a
                // left side node does not have any matching -1 is return
                int[] matching = matcher.getMatching();

                /*
                 * I can not remove from unmatchedGroups list in the below
                 * because I need the previous indices
                 */
                ArrayList<EvolvingCommunity<V>> newUnmatchedGroups = new ArrayList<>();
                newUnmatchedGroups.addAll(unmatchedGroups);

                for (int i = 0; i < preCommunities.size(); i++) {
//                    System.out.println("OptimalBipartiteMatche.getMatching()"
//                            + " --> matching["+i+"]= " + matching[i]);
                    if (matching[i] != -1) {

                        EvolvingCommunity<V> oldCommunity = preCommunities.get(i);
                        EvolvingCommunity<V> newCommunity = unmatchedGroups.get(matching[i]);
                        
                        matchedIds.add(oldCommunity.getId());
                        // set the matching
                        newCommunity.addBackwardMatch(oldCommunity);
                        oldCommunity.addForwardMatch(newCommunity);
                        newUnmatchedGroups.remove(newCommunity);
//                        System.out.println("OptimalBipartiteMatcher.getMatching() --> "
//                                + "timeframe : com id --> " + curSnapIndex +
//                                " : " + oldCommunity.getId());
                        newCommunity.setId(oldCommunity.getId());
                    }
                }
                unmatchedGroups.clear();
                unmatchedGroups.addAll(newUnmatchedGroups);

                preSnapIndex--;
            }

            for (EvolvingCommunity community : unmatchedGroups) {
//                System.out.println("OptimalBipartiteMatcher.getMatching() --> timeframe : new id --> " + curSnapIndex + ":" +
//                        ("C" + community.getIndex() + "T" + curSnapIndex));
                community
                        .setId("C" + community.getIndex() + "T" + curSnapIndex);
            }
        }

    }
}
