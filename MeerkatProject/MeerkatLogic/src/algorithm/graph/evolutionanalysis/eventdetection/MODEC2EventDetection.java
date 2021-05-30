/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.graph.evolutionanalysis.eventdetection;

import algorithm.graph.communitymining.dynamic.auxilaryDS.MetaCommunity;
import algorithm.graph.evolutionanalysis.Event;
import algorithm.graph.evolutionanalysis.EventSnapshot;
import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IVertex;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * The events and transitions between communities and individuals is detected by
 * this class.
 *
 * The formula and methods used here is based on the following paper:
 *
 * Takaffoli et al,"Tracking changes in dynamic information networks", CASON
 * 2011.
 *
 * @author takaffol
 *
 */
public class MODEC2EventDetection<V extends IVertex, E extends IEdge<V>> extends EventDetection<V, E> {

    double k = 1;

    public MODEC2EventDetection(IDynamicGraph<V, E> pdynaGraph, Map<String, MetaCommunity<V>> pmpMetaCommunities/*,
     double identityThreshold,
     List<Matrix> matrices*/) {
//        super(pdynaGraph, identityThreshold, matrices);
        super(pdynaGraph, pmpMetaCommunities);
    }

    @Override
    public void runEventDetection() {
        detectingEvents();
//        detecingTransitions();
        detectingIndividualEvents();
    }

    /**
     * This method detect the events between communities. Here five events are
     * define for communities.
     *
     * Form: A community forms at snapshot i if there is not community match for
     * it in previous time. Simply the first community instance of each meta
     * community is detected as form.
     *
     * Dissolve: A community dissolves at snapshot i if there is no community
     * match for it in the next snapshot. The last community instance of each
     * meta community is not the only dissolve in a meta community. So the
     * communities without any survival are marked by dissolve.
     *
     * Survive: A community survives at snapshot i if there is a community match
     * for it in any of the next snapshots. Simply the match discovered by
     * matching algorithm determines survival
     *
     * Split: A community C at snapshot i splits to a set of communities D at
     * snapshot j > i if at least k proportion of the members of the communities
     * in D are from community C . Also in order to prevent the case where most
     * of the members of C leave the dynaGraph, the mutual members of the union
     * of the communities in D with C should be greater than k proportion of C :
     *
     *
     * Merge: A set of communities D at snapshot i merge to C at snapshot j > i
     * if C contains at least k proportion of the members from each community in
     * D . Also to prevent the case where most of the members of C did not exist
     * before, the mutual members of the union of all communities in D with C
     * should be greater than k proportion of C :
     */
    @Override
    protected void detectingEvents() {
        System.out.println("MODEC2EventDetection detectingEvents() :");
        /* examining every meta community to set form, dissolve, and survive
         * events
         */
        for (int tfIndex = 0;
                tfIndex < dynaGraph.getAllTimeFrames().size();
                tfIndex++) {

            for (String comId : mapEventSnapshots.get(tfIndex).getCommunityIds()) {
                if (metaCommunities.get(comId).getStartTimeIndex() == tfIndex) {
                    // first instance always marked by form
                    catchFormEvent(comId, tfIndex);
                } // indicate a survival
                /**
                 * Since matching algorithm finds matched in backward direction:
                 * 1) one community can survive to more than one communities 2)
                 * two or more communities cannot survive to one community.
                 */
                else if (metaCommunities.get(comId).getStartTimeIndex() < tfIndex && 
                        metaCommunities.get(comId).getOrderedTimeFrameIndexes().indexOf(tfIndex)!=0) {
                    catchSurvive(comId,
                            metaCommunities.get(comId).getOrderedTimeFrameIndexes().get(
                                    metaCommunities.get(comId).getOrderedTimeFrameIndexes().indexOf(tfIndex) - 1),
                            tfIndex);
                }

                // indicate dissolve
                if (metaCommunities.get(comId).getEndTimeIndex() == tfIndex) {
                    catchDissolve(comId, tfIndex);
                }

                // examining future snapshots to detect split
                EventSnapshot<V, E> evSnapshot = mapEventSnapshots.get(tfIndex);

                for (int tfIndexNext = tfIndex + 1;
                        tfIndexNext < dynaGraph.getAllTimeFrames().size();
                        tfIndexNext++) {

                    EventSnapshot evNextSnapshot = mapEventSnapshots.get(tfIndexNext);

                    Set<String> setSplitCommunityIds
                            = findSplits(evSnapshot, evNextSnapshot, comId);

                    catchSplits(comId, tfIndex, tfIndexNext, setSplitCommunityIds);

                }
                // examining every snapshot to set merge
                for (int tfIndexPrev = tfIndex - 1; tfIndexPrev >= 0; tfIndexPrev--) {

                    TimeFrame prevTf
                            = dynaGraph.getAllTimeFrames().get(tfIndexPrev);

                    EventSnapshot evPrevSnapshot = mapEventSnapshots.get(tfIndexPrev);

                    Set<String> setMergeCommunityIds
                            = findMerges(evSnapshot, evPrevSnapshot, comId);

                    catchMerges(comId, tfIndexPrev, tfIndex, setMergeCommunityIds);
                }
            }
        }
    }
      /**
     *  Method Name     : detectingIndividualEvents()
     *  Created Date    : 2017-04-20
     *  Description     : detects all individual events
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
    */
    @Override
    protected void detectingIndividualEvents() {
        System.out.println("MODEC2EventDetection detectingIndividualEvents() :");
        for (int tfIndex = 0;
                tfIndex < dynaGraph.getAllTimeFrames().size();
                tfIndex++) {
            
                List<V> listOfVertices = new ArrayList<>();
                EventSnapshot eventSnapShot = mapEventSnapshots.get(tfIndex);
                Collection<List<V>> vertices = eventSnapShot.getCommunities();
                
                for(List<V> vertexList : vertices){
                    for(V v: vertexList){
                        listOfVertices.add(v);                        
                    }
                }

                for (V v : listOfVertices) {
                    
                    //get all the communities of this vertex (supports overlap communities)
                    ArrayList<String> VertexComm = eventSnapShot.getVertexCommunity(v);

                        // when the vertex is not the member of this snapshot
                        if (VertexComm.isEmpty())
                                continue;

                        // vertices that go away and come back will not register as
                        // appeared.
                        boolean isAppeared = true;
                        for(int tf = tfIndex-1; tf>=0; tf--){
                            if(mapEventSnapshots.get(tf).hasVertex(v)){
                                isAppeared = false;
                                break;         
                            }
                        }

                        // By definition, there are no appeared vertices on
                        // the first timeframe.
                        
                        if(isAppeared && tfIndex!=0)
                            eventSnapShot.addAppearIndividual(v,VertexComm);

                        // vertices that go away and come back will not register as
                        // disappeared.
                        boolean isDisappeared = true;
                        for (int tf = tfIndex + 1; tf < dynaGraph.getAllTimeFrames().size(); tf++) {
                            if(mapEventSnapshots.get(tf).hasVertex(v)){
                                isDisappeared = false;
                                break;         
                            }
                        }

                        // By definition, there are no disappeared vertices on
                        // the last timeframe.
                        if(isDisappeared && tfIndex!=dynaGraph.getAllTimeFrames().size() -1)
                            eventSnapShot.addDisappearIndividual(v, VertexComm);

                        // By definition, there are no join vertices on
                        // the first timeframe. Also the vertices that 'appear' are not
                        // considered as join.
                        if (!isAppeared && tfIndex != 0) {
                                // vertices that go away from the community and come back to it
                                // later are not considered as join.

                                HashMap<String, Boolean> isJoin = new HashMap<String, Boolean>();

                                for (String curComm : VertexComm)
                                        isJoin.put(curComm, true);

                                for (int i = tfIndex - 1; i >= 0; i--) {
                                        ArrayList<String> preComm = mapEventSnapshots.get(i).getVertexCommunity(v);
                                        for (String preCIdx : preComm) {
                                            if(VertexComm.contains(preCIdx)){
                                                isJoin.put(preCIdx, false);
                                                
                                                if (!isJoin.containsValue(true)) {
                                                    i = -1;
                                                    break;
                                                }      
                                            }

                                        }
                                }

                                ArrayList<String> selectedComm = new ArrayList<>();
                                for (String curCommId : VertexComm)
                                        if (isJoin.get(curCommId).equals(true))
                                            selectedComm.add(curCommId);
                                
                                if(selectedComm.size()>0)
                                    eventSnapShot.addJoinIndividual(v, selectedComm);

                        }   
                        
                        // By definition, there are no left vertices on
                        // the last timeframe. Also the vertices that 'disappear' are not
                        // considered as left.
                        if (!isDisappeared && tfIndex != dynaGraph.getAllTimeFrames().size() -1) {
                                // vertices that go away from the community and come back to it
                                // later are not considered as leave.

                                HashMap<String, Boolean> isLeave = new HashMap<>();
                                
                                for (Object curComm : eventSnapShot.getCommunityIds())
                                        isLeave.put((String) curComm, true);

                                for (int i = tfIndex + 1; i < dynaGraph.getAllTimeFrames().size(); i++) {
                                    ArrayList<String> nextComm = mapEventSnapshots.get(i).getVertexCommunity(v);

                                        for (String nextCom : nextComm) {
                                            if(VertexComm.contains(nextCom)){
                                                isLeave.put(nextCom, false);
                                                if (!isLeave.containsValue(true)) {
                                                    i = -1;
                                                    break;
                                                }      
                                            }

                                        }
                                }

                                ArrayList<String> selectedComm = new ArrayList<>();
                                for (String curCommId : VertexComm)
                                        if (isLeave.get(curCommId).equals(true))
                                            selectedComm.add(curCommId);
                                
                                if(selectedComm.size()>0)
                                    eventSnapShot.addLeftIndividual(v, selectedComm);

                        }
                }

        }

    }

    private void catchFormEvent(String comId, int timeframeindex) {
        Event formEvent = new Event(Event.EventName.FORM);

        formEvent.setSourceCommunityIds(comId);
        formEvent.setSourceCommunityTimeFrameIndex(timeframeindex);

        formEvent.setResultCommunityIds(comId);
        formEvent.setResultCommunityTimeFrameIndex(timeframeindex);

        metaCommunities.get(comId).addEvent(formEvent);
        mapEventSnapshots.get(timeframeindex).addFormEvent(formEvent);
    }

    private void catchSurvive(String comId,
            int pintsourcetimeframeindex,
            int pintresulttimeframeindex) {
        Event surviveEvent = new Event(Event.EventName.SURVIVE);

        surviveEvent.setSourceCommunityIds(comId);
        surviveEvent.setSourceCommunityTimeFrameIndex(pintsourcetimeframeindex);

        surviveEvent.setResultCommunityIds(comId);
        surviveEvent.setResultCommunityTimeFrameIndex(pintresulttimeframeindex);

        metaCommunities.get(comId).addEvent(surviveEvent);
        mapEventSnapshots.get(pintresulttimeframeindex)
                .addSurviveEvent(surviveEvent);
    }

    private void catchDissolve(String comId, int timeframeindex) {
        Event dissolveEvent = new Event(Event.EventName.DISSOLVE);

        dissolveEvent.setSourceCommunityIds(comId);
        dissolveEvent.setSourceCommunityTimeFrameIndex(timeframeindex);

        dissolveEvent.setResultCommunityIds(comId);
        dissolveEvent.setResultCommunityTimeFrameIndex(timeframeindex);

        metaCommunities.get(comId).addEvent(dissolveEvent);
        mapEventSnapshots.get(timeframeindex).addDissolveEvent(dissolveEvent);
    }

    private void catchSplits(String comId,
            int pintSourceTFIndex,
            int pintResultTFIndex,
            Set<String> setSplitCommunityIds) {

        if (setSplitCommunityIds == null || setSplitCommunityIds.isEmpty()) {
            return;
        }
        Event splitEvent = new Event(Event.EventName.SPLIT);
        splitEvent.setSourceCommunityTimeFrameIndex(pintSourceTFIndex);
        splitEvent.setSourceCommunityIds(comId);

        splitEvent.setResultCommunityTimeFrameIndex(pintResultTFIndex);
        splitEvent.setResultCommunityIds(setSplitCommunityIds);

        metaCommunities.get(comId).addEvent(splitEvent);
        mapEventSnapshots.get(pintResultTFIndex).addSplitEvent(splitEvent);
    }

    private void catchMerges(String comId,
            int pintSourcetfIndex,
            int pintResulttfIndex,
            Set<String> setMergeCommunityIds) {

        if (setMergeCommunityIds == null || setMergeCommunityIds.isEmpty()) {
            return;
        }
        
        Event mergeEvent = new Event(Event.EventName.MERGE);

        mergeEvent.setSourceCommunityTimeFrameIndex(pintSourcetfIndex);
        mergeEvent.setSourceCommunityIds(setMergeCommunityIds);

        mergeEvent.setResultCommunityTimeFrameIndex(pintResulttfIndex);
        mergeEvent.setResultCommunityIds(comId);

        // TD how can I add item to result community sequence
        for (String sourceComId : setMergeCommunityIds) {
            metaCommunities.get(sourceComId).addEvent(mergeEvent);
        }
        mapEventSnapshots.get(pintResulttfIndex).addMergeEvent(mergeEvent);
    }

    private Set<String> findSplits(EventSnapshot<V, E> evSnapshot,
            EventSnapshot<V, E> evNextSnapshot, String comId) {

        Set<String> possibleNextCommunity_Ids = new HashSet<>();

        List<V> lstComVertices = evSnapshot.getCommunityVertices(comId);

        for (String strNextComId : evNextSnapshot.getCommunityIds()) {
            List<V> potentialCom = evNextSnapshot.getCommunityVertices(strNextComId);

            int intIntersection = 0;
            for (V v : lstComVertices) {
                if (potentialCom.contains(v)) {
                    intIntersection++;
                }
            }

            if (intIntersection >= k * potentialCom.size()) {
                possibleNextCommunity_Ids.add(strNextComId);
            }
        }

        boolean splitFlag = false;

        Set<V> union = new HashSet<>();

        if (possibleNextCommunity_Ids.size() > 1) {
            for (String com : possibleNextCommunity_Ids) {
                List<V> nextPotentialCom = evNextSnapshot.getCommunityVertices(com);

                union.addAll(nextPotentialCom);
            }

            int intIntersectionWithAll_size = 0;
            for (V v : lstComVertices) {
                if (union.contains(v)) {
                    intIntersectionWithAll_size++;
                }
            }
            if (intIntersectionWithAll_size >= k * lstComVertices.size()) {
                splitFlag = true;
            }
        }
        
        for (String str : possibleNextCommunity_Ids) {
            //System.out.println("MODEC2.findsplits() : " + str);
        }
        if (!splitFlag) {
            possibleNextCommunity_Ids = null;
        }

        if (possibleNextCommunity_Ids != null) {
            /**
             * Two cases of split is not accepted:
             *
             * 1) The split community is detected as split to a similar set of
             * communities (at least two similar communities) in earlier time
             *
             * 2) The same set of community (at least two similar communities)
             * is the result of any other split from the same meta community in
             * earlier time
             */
            if (isLegalSplit(comId,
                    possibleNextCommunity_Ids,
                    evSnapshot.getTimeFrameIndex())) {

                return possibleNextCommunity_Ids;
            }
        }
        return null;
    }

    // detect previously saved similar split
    private boolean isLegalSplit(String communityId,
            Collection<String> splitComIds,
            int pintSourceTimeFrameIndex) {

        for (int tfindex : metaCommunities.get(communityId).getOrderedTimeFrameIndexes()) {
            if (tfindex >= pintSourceTimeFrameIndex) {
                continue;
            }
            // check the previously saved split for this specific community
            for (Event ev : mapEventSnapshots.get(tfindex).getSplits()) {
                if (ev.getSourceCommunityIds().contains(communityId)) {
                    List<String> preResultIds = new LinkedList<>();
                    preResultIds.addAll(ev.getResultCommunityIds());

                    ArrayList<String> intersection = new ArrayList<>();
                    intersection.addAll(splitComIds);
                    intersection.retainAll(preResultIds);

                    // this community split to the similar set of community
                    // previously
                    double dblIntersectionRation
                            = (intersection.size() + 0.0) / splitComIds.size();
                    if (dblIntersectionRation > 0.5) {
                        return false;
                    }
                }
            }

            // check the previously saved split for communities with the same ID as
            // split community
            EventSnapshot<V, E> otherEvSnapshots = mapEventSnapshots.get(tfindex);
            for (Event splitEv : otherEvSnapshots.getSplits()) {
                List<String> preResultIds = splitEv.getResultCommunityIds();

                ArrayList<String> intersection = new ArrayList<>();
                intersection.addAll(splitComIds);
                intersection.retainAll(preResultIds);
                /* a community with the same ID split to the similar set of
                 * community previously
                 */
                if (intersection.size() >= 2
                        && splitEv.getResultCommunityTimeFrameIndex()
                        == pintSourceTimeFrameIndex) {
                    return false;
                }
            }
        }

        return true;
    }

    private Set<String> findMerges(EventSnapshot<V, E> evSnapshot,
            EventSnapshot<V, E> evPrevSnapshot, String pstrComId) {

        Set<String> possiblePreGroups = new HashSet<>();

        List<V> lstCurrentCom = evSnapshot.getCommunityVertices(pstrComId);

        for (String strPrevComId : evPrevSnapshot.getCommunityIds()) {
            Set<V> setPrevCom = new HashSet<>();
            setPrevCom.addAll(evPrevSnapshot.getCommunityVertices(strPrevComId));

            List<V> lstIntersectionComs = new LinkedList<>();
            for (V v : lstCurrentCom) {
                if (setPrevCom.contains(v)) {
                    lstIntersectionComs.add(v);
                }
            }

            if (lstIntersectionComs.size() >= k
                    * setPrevCom.size()) {
                possiblePreGroups.add(strPrevComId);
            }
        }

        boolean mergeFlag = false;

        Set<V> setUnionMerges = new HashSet<>();

        if (possiblePreGroups.size() > 1) {
            for (String strPrevComId : possiblePreGroups) {
                List<V> lstPrevCom = evPrevSnapshot.getCommunityVertices(strPrevComId);
                setUnionMerges.addAll(lstPrevCom);
            }

            List<V> lstIntersect = new LinkedList<>();
            for (V v : lstCurrentCom) {
                if (setUnionMerges.contains(v)) {
                    lstIntersect.add(v);
                }
            }
            if (lstIntersect.size() >= k * lstCurrentCom.size()) {
                mergeFlag = true;
            }
        }

        if (mergeFlag) {
            /**
             * Two cases of merge is not accepted:
             *
             * 1) The merged community is the result of a merge from similar set
             * of communities (at least two similar communities) in earlier time
             *
             * 2) The same set of community (at least two similar communities)
             * are merged into the same meta community in earlier time
             */
            if (isLegalMerge(pstrComId, possiblePreGroups, evSnapshot.getTimeFrameIndex())) {
                return possiblePreGroups;
            }
        }
        return null;
    }

    // detect previously saved similar merge
    private boolean isLegalMerge(String comId,
            Collection<String> plstPrevComIds,
            int pintComTfIndex) {

        for (int intTFIndex : mapEventSnapshots.keySet()) {
            if (intTFIndex > pintComTfIndex) {
                continue;
            }
            // check the previously saved resulting merge for this specific community
            List<String> lstPreSourceIds = new LinkedList<>();

            for (Event evMerge : mapEventSnapshots.get(intTFIndex).getMerges()) {
                if (evMerge.getResultCommunityIds().contains(comId)) {
                    lstPreSourceIds.addAll(evMerge.getSourceCommunityIds());
                }

                ArrayList<String> intersection = new ArrayList<>();
                intersection.addAll(plstPrevComIds);
                intersection.retainAll(lstPreSourceIds);

                /* this community is the result of merge from similar set of
                 * community previously
                 */
                if (intersection.size() > 0.8 * plstPrevComIds.size()) {
                    return false;
                }
            }

            /* check the previously saved resulting merge for communities with the
             * same ID as merged community
             */
            EventSnapshot<V, E> eventSnap = mapEventSnapshots.get(intTFIndex);

            for (Event merge : eventSnap.getMerges()) {
                if (merge.getResultCommunityIds().contains(comId)) {
                    ArrayList<String> preSourceIds = new ArrayList<>();
                    for (String sourceGIdx : merge.getSourceCommunityIds()) {
                        preSourceIds.add(sourceGIdx);
                    }

                    ArrayList<String> intersection = new ArrayList<>();
                    intersection.addAll(plstPrevComIds);
                    intersection.retainAll(preSourceIds);
                    // a community with the same ID is the result of similar
                    // merge

                    if (intersection.size() >= 2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
