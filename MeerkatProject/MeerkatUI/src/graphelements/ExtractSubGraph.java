/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphelements;

import ca.aicml.meerkat.api.GraphAPI;
import config.GraphConfig.GraphType;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author panwar
 */
//TODO change filename, do code clean up and put this file in an appropriate place
public class ExtractSubGraph {

    public static void extractASubgraph(int pintProjectID, int pintGraphID, int pcurrentTimeFrameIndex ,String[] ptimeFrames, Set<UIVertex> psetSelectedVertices, Set<UIEdge> psetSelectedEdges) {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        List<Integer> lstintSelectedVertexIDs = new ArrayList<>();        
        for (UIVertex uiVertex : psetSelectedVertices) {
                lstintSelectedVertexIDs.add(uiVertex.getVertexHolder().getID()) ;
        }
        
        List<Integer> lstintSelectedEdgeIDs = new ArrayList<>();        
        for (UIEdge uiEdge : psetSelectedEdges) {
                lstintSelectedEdgeIDs.add(uiEdge.getID()) ;
        }
        
        
        // TODO implement Graph and TimeFrame modified status
        // if timeframe not modified, then don't update coordinates in logic 
        // update coordinates of all vertives in this tf in logic because it might have been changed
        //TODO Update location of all time frames in logic because other time frames may have been modified
        Map<Integer, Double[]> mapVertexUILocationsTF = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getVerticesCoordinatesAbsolute();
        GraphAPI.updateVertexLocationsInLogic(pintProjectID, pintGraphID,pcurrentTimeFrameIndex, mapVertexUILocationsTF);
        
        
        
        // get new graph, new projectID from GraphAPI
        System.out.println("ExtractSubGraph.extractsubgraph : noOFSelectedVertices = " + lstintSelectedVertexIDs.size() + " noOfSelectedEdges = " + lstintSelectedEdgeIDs.size());
        int subGraphId = GraphAPI.extractSubGraph(pintProjectID, pintGraphID, ptimeFrames, lstintSelectedVertexIDs, lstintSelectedEdgeIDs);
        
        
        //calculate mapping of pcurrentTimeFrameIndex in subgraph w.r.t parent graph
        String[] parentGraphTimeFrameNames = GraphAPI.getTimeFrameNames(pintProjectID, pintGraphID);
        String[] subGraphTimeFrameNames = GraphAPI.getTimeFrameNames(pintProjectID, subGraphId);
        String currentTimeFrameNameParentGraph = parentGraphTimeFrameNames[pcurrentTimeFrameIndex];
        int currentTimeFrameIndexSubGraph = 0;
        int timeFrameIndexSubGraph = 0;
        for(String subGraphTimeFrameName : subGraphTimeFrameNames){
        
            if(subGraphTimeFrameName.equals(currentTimeFrameNameParentGraph)){
                currentTimeFrameIndexSubGraph = timeFrameIndexSubGraph;
                break;
            }
            timeFrameIndexSubGraph++;
        }
        
        
        
        //GraphTab subGraphTab = new GraphTab(pintProjectID, subGraphId, GraphType.GRAPH, GraphAPI.getGraphTitle(pintProjectID, subGraphId), "", "" );
        GraphTab subGraphTab = new GraphTab(pintProjectID, subGraphId, currentTimeFrameIndexSubGraph, GraphType.GRAPH, GraphAPI.getGraphTitle(pintProjectID, subGraphId), "", "");
        
        
        UIInstance.getActiveProjectTab().addGraphTab(subGraphTab);
        
        //Update Project status
        ProjectStatusTracker.updateProjectModifiedStatus(UIInstance.getActiveProjectID(), ProjectStatusTracker.eventSubGraphAdded);

                
        //Update Controller message
        /*
                pController.updateStatusBar(true, StatusMsgsConfig.WAITING_USERINPUT);

        */
    }
    
}
