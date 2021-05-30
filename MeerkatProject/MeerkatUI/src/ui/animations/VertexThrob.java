/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.animations;

import globalstate.MeerkatUI;
import graphelements.UIVertex;
import java.util.Set;

/**
 *  Class Name      : VertexThrob
 *  Created Date    : 2016-07-08
 *  Description     : Vertex Throb
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class VertexThrob {
    
    /**
     *  Method Name     : ThrobSelectedVertices()
     *  Created Date    : 2016-07-08
     *  Description     : Throbs all the selected Vertices
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */

    public static void ThrobSelectedVertices() {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        Set<UIVertex> setSelectedVertices = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getSelectedVertices();
        
        for (UIVertex vtxCurrent : setSelectedVertices) {
            
        }
    }
}
