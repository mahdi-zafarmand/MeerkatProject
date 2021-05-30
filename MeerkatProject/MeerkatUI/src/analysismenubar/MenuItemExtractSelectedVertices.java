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
import java.util.Set;
import javafx.scene.control.MenuItem;
import ui.dialogwindow.InfoDialog;

/**
 *
 * @author sankalp
 */
/**
 *  Class Name      : MenuItemExtractSelectedVertices
 *  Created Date    : 2017-07-24
 *  Description     : Functionalities when the user chooses to extract selected vertices from the menu
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class MenuItemExtractSelectedVertices extends MenuItemGeneric implements IMenuItem {

    /**
     *  Constructor Name: MenuItemExtractSelectedVertices()
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
    public MenuItemExtractSelectedVertices(String pstrMenuItemDisplay, String pstrMenuItemClass, String pstrMenuItemIconPath) {
        super(pstrMenuItemDisplay, pstrMenuItemClass, pstrMenuItemIconPath);
    }
    
       /**
     *  Method Name     : Click
     *  Created Date    : 2017-07-24
     *  Description     : call the method to extract selected vertices.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pController: AnalysisController
     *  @param pMenuItem : MenuItem
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    @Override
    public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
        int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
        int currentTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
        
        Set<UIVertex> setSelectedVertices = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getSelectedVertices();
        Set<UIEdge> setSelectedEdges = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getSelectedEdges();
        String[] timeFrames = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrames();
        
        if(setSelectedVertices.size()>0)
            ExtractSubGraph.extractASubgraph(intProjectID, intGraphID, currentTimeFrameIndex,timeFrames, setSelectedVertices, setSelectedEdges);
        else
            InfoDialog.Display("Please select some vertices to extract!", 3);
    }
}
