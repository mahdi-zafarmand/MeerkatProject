/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate;

/**
 *
 * @author AICML Administrator
 */
public class ProjectStatusTracker {
    /**
     * List of events after which status of a project will change
     */
    public static String eventVertexMoved = "vertexMoved";
    public static String eventSubGraphAdded = "subGraphAdded";
    public static String eventCommunityMiningDone = "communityMiningDone";
    public static String eventLinkPredictionDone = "linkPredictionDone";
    public static String eventClearedCommunityMining = "clearedCommunityMining";
    public static String eventLayoutDone = "layoutDone";
    public static String eventProjectSaved = "projectSaved";
    public static String eventNewGraphLoaded = "newGraphLoaded";
    public static String eventGraphDeleted = "graphDeleted";
    public static String eventVertexRemoved = "removeVertices";
    public static String eventEdgeRemoved = "removeEdges";
    public static String eventGraphRenamed = "graphRenamed";
    public static String eventVertexAdded = "vertexAdded";
    public static String eventEdgeAdded = "edgeAdded";
    public static String attributeValueChanged = "attrValueChange";
    public static String IconAddedRemoved = "iconAddedRemoved";
    /**
     *  
     * @param pintProjectId
     * @param pstringMessage
     */
    public static void updateProjectModifiedStatus(int pintProjectId, String pstringMessage){
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        switch(pstringMessage){
            case "vertexMoved" :
                
                UIInstance.getProject(pintProjectId).setProjectModifiedStatus(Boolean.TRUE);
                break;
            case "subGraphAdded" :
                
                UIInstance.getProject(pintProjectId).setProjectModifiedStatus(Boolean.TRUE);
                break;
                
            case "communityMiningDone" :
                
                UIInstance.getProject(pintProjectId).setProjectModifiedStatus(Boolean.TRUE);
                break;
                
            case "clearedCommunityMining" :
                
                UIInstance.getProject(pintProjectId).setProjectModifiedStatus(Boolean.TRUE);
                break;
                
            case "layoutDone" :
                
                UIInstance.getProject(pintProjectId).setProjectModifiedStatus(Boolean.TRUE);
                break;
                
            case "projectSaved" :
                
                UIInstance.getProject(pintProjectId).setProjectModifiedStatus(Boolean.FALSE);
                break; 
                        
            case "newGraphLoaded" :
                
                UIInstance.getProject(pintProjectId).setProjectModifiedStatus(Boolean.TRUE);
                break;
                        
            case "graphDeleted" :
                
                UIInstance.getProject(pintProjectId).setProjectModifiedStatus(Boolean.TRUE);
                break;
                
            case "removeVertices" :
                
                UIInstance.getProject(pintProjectId).setProjectModifiedStatus(Boolean.TRUE);
                break;
                
            case "removeEdges" :
                
                UIInstance.getProject(pintProjectId).setProjectModifiedStatus(Boolean.TRUE);
                break;
                
            case "graphRenamed" :
                
                UIInstance.getProject(pintProjectId).setProjectModifiedStatus(Boolean.TRUE);
                break;
                
            case "vertexAdded" :
                
                UIInstance.getProject(pintProjectId).setProjectModifiedStatus(Boolean.TRUE);
                break;
                
            case "edgeAdded" :
                
                UIInstance.getProject(pintProjectId).setProjectModifiedStatus(Boolean.TRUE);
                break;
                
            case "attrValueChange" :
                
                UIInstance.getProject(pintProjectId).setProjectModifiedStatus(Boolean.TRUE);
                break;
                
            case "iconAddedRemoved" :
                
                UIInstance.getProject(pintProjectId).setProjectModifiedStatus(Boolean.TRUE);
                break;
                
            default :
        }
    }
}

