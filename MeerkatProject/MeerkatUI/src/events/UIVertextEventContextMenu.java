/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 *  Class Name      : UIVertextEventContextMenu
 *  Created Date    : 2017-04-27
 *  Description     : Contains logic to show the context menu for vertex time line.
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
*/
public class UIVertextEventContextMenu {
    private static ContextMenu cmNode;
    private static MenuItem vertexTimeline;
    private static UIVertexEvent uiVertex ;
    
    private static UIVertextEventContextMenu vcmInstance = null; // singleton
    
    private UIVertextEventContextMenu(UIVertexEvent pvtxHolder) {
        UIVertextEventContextMenu.uiVertex = pvtxHolder ;
        
        cmNode = new ContextMenu();
        vertexTimeline = new MenuItem("View Vertex Timeline");
        vertexTimeline.setOnAction( event -> {
            VertexTimeline.createVertexTimeline(UIVertextEventContextMenu.uiVertex);
        });
        cmNode.getItems().add(vertexTimeline);
    }
    
    /**
     *  Method Name     : getInstance()
     *  Created Date    : 2017-04-27
     *  Description     : constructs and returns an instance for this class.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public static UIVertextEventContextMenu getInstance(UIVertexEvent pvtxHolder) {
        UIVertextEventContextMenu.uiVertex = pvtxHolder;
        
        if (vcmInstance == null) {
            vcmInstance = new UIVertextEventContextMenu(pvtxHolder);
        }
        return vcmInstance;
    }
    
    /**
     *  Method Name     : Show()
     *  Created Date    : 2017-04-27
     *  Description     : shows the context menu at specified coordinates
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public void Show (Node pNode, double pdblX, double pdblY) {
        UIVertextEventContextMenu.cmNode.show(pNode, pdblX, pdblY);
    }
}
