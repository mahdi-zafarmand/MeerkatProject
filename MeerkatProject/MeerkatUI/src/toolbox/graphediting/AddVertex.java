/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.graphediting;

import analysisscreen.AnalysisController;
import globalstate.MeerkatUI;

/**
 *  Class Name      : AddVertex
 *  Created Date    : 2016-01-15
 *  Description     : Adds a Vertex to the screen
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class AddVertex {
    
    public static void Click(AnalysisController pController) {
        
        // Identify the position of the mouse pointer in the scene
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        UIInstance.getActiveProjectTab().getActiveGraphTab().getMousePointerPosition() ;
        
        // Add the Vertex
        
        // Invoke the Logic layer
        
        // Display the vertex on the screen
    }
}
