/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import globalstate.GraphCanvas;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import graphelements.VertexHolder;
import ui.elements.SizeToolBox;

/**
 *
 * @author AICML Administrator
 */
public class AddEdgeDialog {
       
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strTitle;
    private static String strMessage ;  
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */    
    private static String getTitle() {
        return strTitle;
    }
    private static String getMessage() {
        return strMessage;
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 
     *  Description     : Sets the parameters of the Vertex Add Confirmation Dialog Box
     *  Version         : 1.0
     *  @author         : 
     * 
     *  @param pstrTitle : String
     *  @param pstrMessage : String
     *  @param pstrIconPath : String    
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters(String pstrTitle, String pstrMessage, String pstrIconPath) {
        strTitle = pstrTitle;
        strMessage = pstrMessage;
    }
    

    /**
     * 
     * @param vertexHolderFrom
     * @param vertexHolderTo
     * @param pController 
     */
    public static void Display(VertexHolder vertexHolderFrom, VertexHolder vertexHolderTo, AnalysisController pController) {
        System.out.println(" \t\t\t\t\t\t\t\t-- ADDEDGEDIALOG.display() -- VertexPair = v1,v2 = " + vertexHolderFrom + ", " + vertexHolderTo);
        System.out.println(" \t\t\t\t\t\t\t\t-- ADDEDGEDIALOG.display() -- VertexPair = v1,v2 = " + vertexHolderFrom.getID() + ", " + vertexHolderTo.getID());
        
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int intProjectId = UIInstance.getActiveProjectTab().getProjectID();
        GraphTab graphTab = UIInstance.getActiveProjectTab().getActiveGraphTab();
        int intGraphId = graphTab.getGraphID();
        int intTimeFrameIndex = graphTab.getTimeFrameIndex();
        
        //check if an edge exists between source and destination in this time frame
        //if yes - don't add edge - display a notice
        //else - add edge
        if(!GraphAPI.edgeExists(intProjectId, intGraphId, intTimeFrameIndex, vertexHolderFrom.getID(), vertexHolderTo.getID())){
            //add edge in logic throuh api
            int newEdgeId = GraphAPI.addEdge(intProjectId, intGraphId, intTimeFrameIndex, vertexHolderFrom.getID(), vertexHolderTo.getID());
            //add edge in ui
            GraphCanvas graphCanvas = graphTab.getGraphCanvas();
            graphCanvas.updateCanvasAfterAddEdge(intProjectId, intGraphId, intTimeFrameIndex, 
                    newEdgeId,vertexHolderFrom.getID(), vertexHolderTo.getID());
        }else{
            //display a notice of error
            ErrorDialog.Display("Edge already exists");
        }
        
        //update Accordion Tabs
        //update accordion tab values
                graphTab.updateAccordionValues();
                
                //update the status of the project after vertex is added
                ProjectStatusTracker.updateProjectModifiedStatus(UIInstance.getActiveProjectID(), ProjectStatusTracker.eventEdgeAdded);
                
                //update UI after vertices/edges have been removed.
                UIInstance.UpdateUI();
                
                //update SizeToolBox
                SizeToolBox.getInstance().disableSizeToolbox();    
        

        
        
    }
}
