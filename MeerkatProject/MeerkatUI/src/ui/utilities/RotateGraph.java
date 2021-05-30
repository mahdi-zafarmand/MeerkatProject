/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.utilities;

import analysisscreen.AnalysisController;
import globalstate.MeerkatUI;

/**
 *
 * @author AICML Administrator
 */
public class RotateGraph {

    public static void rotateGraph(AnalysisController controller, int intProjectID, int intGraphID, int currentTimeFrameIndex, String direction) {
        
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        UIInstance.getActiveProjectTab().getActiveGraphTab().rotateGraphCanvas(currentTimeFrameIndex, direction);
        
    }
    
    
}
