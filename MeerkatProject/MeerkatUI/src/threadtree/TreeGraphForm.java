/*
 * To change this license header, choose License Headers in ProjectTab Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadtree;

import ca.aicml.meerkat.api.GraphAPI;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import threadtree.TreeItemConfig.TreeItemType;

/**
 *  Class Name      : TextualFormImplementation
 *  Created Date    : 2015-06-26
 *  Description     : Responsible to convert complete graph into TreeItems in terms of Vertices and edges
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/

public class TreeGraphForm implements IThreadTree {
    
    /**
     *  Method Name     : addProject
     *  @author         : Talat
     *  Created Date    : 2015-06-26
     *  Description     : Adds the project to the Root - to be displayed on the Thread Tree View
     *  Version         : 1.0
     * 
     *  @param ptreeRoot: TreeItem<String>
     *  @param pintProjectID : int
     *  @return TreeItem<String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *   
     * 
    */
    @Override
    public MeerkatTreeItem<String> addProject(MeerkatTreeItem<String> ptreeRoot, int pintProjectID) {
                
        /*
            1) Add the name of the project as a child to the root
            2) Check if there are any graph available
                a) If there are no graphs available, just leave it
                b) Else Add Nodes and Edges in a loop
        */ 
        String strDelimiter = ",";
        MeerkatUI applicationState = MeerkatUI.getUIInstance();
        
        /* For each ProjectTab, the Tab title by default would be a Project Name */
        MeerkatTreeItem<String> treeProject = new MeerkatTreeItem(applicationState.getProject(pintProjectID).getProjectName(), TreeItemType.PROJECTTITLE, pintProjectID);
        // System.out.println("TreeGraphForm.addProject(): ProjectName "+applicationState.getProject(pintProjectID).getProjectName());
        
        for (GraphTab currentGraph : applicationState.getActiveProjectTab().getAllGraphTabs().values()) {
            MeerkatTreeItem<String> treeGraph = new MeerkatTreeItem(currentGraph.getGraphTabTitle(), TreeItemType.GRAPHTITLE, currentGraph.getGraphID());
            
            /* Add the list of vertices to the TreeItems */
            MeerkatTreeItem<String> treeNodes = new MeerkatTreeItem(ThreadTreeLangParameters.getVertexLabel(), TreeItemType.VERTEXTITLE, -1);
            for (Integer intCurrentVertexID : GraphAPI.getAllVertexIds(pintProjectID, currentGraph.getGraphID(), currentGraph.getTimeFrameIndex())) {
                MeerkatTreeItem<String> item = new MeerkatTreeItem (Integer.toString(intCurrentVertexID), TreeItemType.VERTICES, intCurrentVertexID);
                treeNodes.getChildren().add(item);
            }
            treeGraph.getChildren().add(treeNodes);
            
            
            /* Add the list of edges to the TreeItems */
            MeerkatTreeItem<String> treeEdges = new MeerkatTreeItem<>(ThreadTreeLangParameters.getEdgeLabel(), TreeItemType.EDGETITLE, -1);
            for (String strCurrentID : GraphAPI.getAllEdgesAsNodeIds(pintProjectID, currentGraph.getGraphID(), currentGraph.getTimeFrameIndex(), strDelimiter)) {
                String[] arrstrVertices = strCurrentID.split(strDelimiter);
                int intEdgeID = Integer.parseInt(arrstrVertices[0]);
                MeerkatTreeItem<String> item = new MeerkatTreeItem<> (arrstrVertices[1]+"-"+arrstrVertices[2], TreeItemType.EDGES, intEdgeID);
                treeEdges.getChildren().add(item);
            }
            treeGraph.getChildren().add(treeEdges);
            
            treeProject.getChildren().add(treeGraph);            
        }
        ptreeRoot.getChildren().add(treeProject);    
        
        // System.out.println("TreeGraphForm.addProject(): "+ptreeRoot.getChildren().get(0).getValue()); // #Debug
        
        return ptreeRoot;
    }
    
    /**
     *  Method Name     : addGraph
     *  @author         : Talat
     *  Created Date    : 2015-08-09
     *  Description     : Generates the treeItems for a graph that are to be displayed in the ThreadTree View
     *  Version         : 1.0
     * 
     *  @param ptreeGraph : TreeItem<String>
     *  @param pintProjectID: int
     *  @param pintGraphID : int
     *  @return TreeItem<String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-02-25      Talat           Instead of receiving the array as a string, receive it as an array and then prepare the output 
     *                                  to be shown in the tree
     * 
    */
    @Override
    public MeerkatTreeItem<String> addGraph(MeerkatTreeItem<String> ptreeGraph, int pintProjectID, int pintGraphID) {
        
        String strDelimiter = ",";        
            
        /* Add the list of vertices to the TreeItems */
        MeerkatTreeItem<String> treeNodes = new MeerkatTreeItem<>(ThreadTreeLangParameters.getVertexLabel(), TreeItemType.VERTEXTITLE, -1);
        
        for (Integer intCurrentVertexID : GraphAPI.getAllVertexIds(pintProjectID, pintGraphID)) {
            MeerkatTreeItem<String> item = new MeerkatTreeItem<> (Integer.toString(intCurrentVertexID), TreeItemType.VERTICES, intCurrentVertexID);
            treeNodes.getChildren().add(item);            
        }
        ptreeGraph.getChildren().add(treeNodes);

        /* Add the list of edges to the TreeItems */
        MeerkatTreeItem<String> treeEdges = new MeerkatTreeItem<>(ThreadTreeLangParameters.getEdgeLabel(), TreeItemType.EDGETITLE, -1);
        for (int [] arrintEdge : GraphAPI.getAllEdgesAsNodeIds(pintProjectID, pintGraphID, strDelimiter)) {
            // String[] arrstrVertices = strCurrentID.split(strDelimiter);
            if (arrintEdge != null) {                
                MeerkatTreeItem<String> item = new MeerkatTreeItem<> (arrintEdge[1]+"-"+arrintEdge[2], TreeItemType.EDGES, arrintEdge[0]);
                treeEdges.getChildren().add(item);
            }
        }
        ptreeGraph.getChildren().add(treeEdges);

        return ptreeGraph;
    }
    
}
