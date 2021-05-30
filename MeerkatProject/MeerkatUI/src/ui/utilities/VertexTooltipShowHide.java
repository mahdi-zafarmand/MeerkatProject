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
 *  Class Name      : VertexTooltipShowHide
 *  Created Date    : 2016-05-27
 *  Description     : Shows the Vertex Tooltip or Not
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/

public class VertexTooltipShowHide {
    
    /**
     *  Method Name     : Execute()
     *  Created Date    : 2016-05-27
     *  Description     : Shows / Hides the Tooltip of the Vertex
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
        
        // Update the Graph's Tooltip Visibility
        MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
        boolean blnIsVertexTooltipShown = UIInstance.getActiveProjectTab().getActiveGraphTab().IsVertexTooltipShown();
        System.out.println("VertexToolTipShowHide(): Tool tip selection");
        UIInstance.getActiveProjectTab().getActiveGraphTab().setTooltipVisibility(blnIsVertexTooltipShown);
        
        // Update the Status Bar
        if (blnIsVertexTooltipShown){
            pController.updateStatusBar(false, StatusMsgsConfig.VERTEXTOOLTIP_ENABLED);
        } else {
            pController.updateStatusBar(false, StatusMsgsConfig.VERTEXTOOLTIP_DISABLED);
        }
    }
}
