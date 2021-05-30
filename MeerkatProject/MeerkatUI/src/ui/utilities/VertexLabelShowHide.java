/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.utilities;

import analysisscreen.AnalysisController;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;

/**
 *  Class Name      : VertexLabelShowHide
 *  Created Date    : 2016-05-25
 *  Description     : Shows the Vertex Label or Not
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/

public class VertexLabelShowHide {
    
    /**
     *  Method Name     : Execute()
     *  Created Date    : 2016-05-25
     *  Description     : Shows / Hides the label of the Vertex
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Execute(AnalysisController pController) {
        
        // Update the Graph's Label Visibility
        MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
        boolean blnIsVertexLabelShown = UIInstance.getActiveProjectTab().getActiveGraphTab().IsVertexLabelShown();
        UIInstance.getActiveProjectTab().getActiveGraphTab().setLabelVisibility(blnIsVertexLabelShown);
        
        // Update the Status Bar
        if (blnIsVertexLabelShown){
            pController.updateStatusBar(false, StatusMsgsConfig.VERTEXLABEL_ENABLED);
        } else {
            pController.updateStatusBar(false, StatusMsgsConfig.VERTEXLABEL_DISABLED);
        }
    }
}
