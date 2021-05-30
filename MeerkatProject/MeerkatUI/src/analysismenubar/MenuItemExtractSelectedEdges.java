/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import globalstate.MeerkatUI;
import graphelements.ExtractSubGraph;
import graphelements.UIEdge;
import graphelements.UIVertex;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.control.MenuItem;
import ui.dialogwindow.InfoDialog;

/**
 *
 * @author sankalp
 */
/**
 *  Class Name      : MenuItemExtractSelectedEdges
 *  Created Date    : 2017-07-24
 *  Description     : Functionalities when the user chooses to extract selected edges from the menu
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class MenuItemExtractSelectedEdges extends MenuItemGeneric implements IMenuItem {

    /**
     *  Constructor Name: MenuItemExtractSelectedEdges()
     *  Created Date    : 2016-07-24
     *  Description     : Calls the super constructor to initialize the values
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pstrMenuItemDisplay : String
     *  @param pstrMenuItemClass : String
     *  @param pstrMenuItemIconPath : String
     *  
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public MenuItemExtractSelectedEdges(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
       /**
     *  Method Name     : Click
     *  Created Date    : 2017-07-24
     *  Description     : call the method to extract selected edges.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pController: AnalysisController
     *  @param pMenuItem : MenuItem
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * @param pobjParameter
     * 
    */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
        int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
        int currentTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
        HashSet<UIVertex> setSelectedVertices = new HashSet<>();
        
        Set<UIEdge> setSelectedEdges = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getSelectedEdges();

        // only selecting those vertices to extract which are tied to the edges
        for(UIEdge e : setSelectedEdges){
            int sourceVrtx = e.getSourceVertexID();
            int destiVrtx = e.getDestinationVertexID();
            
            UIVertex srcVertex = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas(currentTimeFrameIndex).getVertices().get(sourceVrtx);
            UIVertex destiVertex = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas(currentTimeFrameIndex).getVertices().get(destiVrtx);
            
            setSelectedVertices.add(srcVertex);
            setSelectedVertices.add(destiVertex);
        }
        
        String[] timeFrames = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrames();
        
        if(setSelectedVertices.size()>0)
            ExtractSubGraph.extractASubgraph(intProjectID, intGraphID, currentTimeFrameIndex,timeFrames, setSelectedVertices, setSelectedEdges);
        else
            InfoDialog.Display("Please select some edges to extract!", 3);
    }
}
