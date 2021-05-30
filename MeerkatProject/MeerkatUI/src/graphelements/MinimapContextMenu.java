/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphelements;

import globalstate.MeerkatUI;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;

/**
 *  Class Name      : MinimapContextMenu
 *  Created Date    : 2016-05-25
 *  Description     : Context Menu on the Minimap using a singleton pattern
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class MinimapContextMenu {
    
    private StackPane stkMinimap ;
    
    private static ContextMenu cmMiniMap;
    private static MinimapContextMenu mcmInstance ;
    
    private MenuItem menuHideMinimap ;
    private MenuItem menuDocking ;
    
    private boolean blnIsDocked = true;
    
    /**
     *  Constructor Name: MinimapContextMenu()
     *  Created Date    : 2016-05-25
     *  Description     : Private constructor initializing the Context Menu for the Minimap
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pMinimap : StackPane
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private MinimapContextMenu(StackPane pMinimap) {
     
        stkMinimap = pMinimap;
        
        cmMiniMap = new ContextMenu();
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        menuHideMinimap = new MenuItem("Hide Menu Item") ;
        menuHideMinimap.setOnAction((e) -> {
            UIInstance.getActiveProjectTab().getActiveGraphTab().hideMinimap();
        });
        menuDocking = new MenuItem("Undock Minimap");
        menuDocking.setOnAction((e) -> {
            if (blnIsDocked) {
                menuDocking.setText("Undock Minimap");
                blnIsDocked = false ;
            } else {
                menuDocking.setText("Dock Minimap");
                blnIsDocked = true ;
            }
        });
        
        cmMiniMap.getItems().addAll(menuHideMinimap, menuDocking);
    }
    
    /**
     *  Method Name     : getInstance()
     *  Created Date    : 2016-05-25
     *  Description     : Retrieves the instance of the Minimap Context Menu
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pMinimap : StackPane
     *  @return MinimapContextMenu
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static MinimapContextMenu getInstance(StackPane pMinimap) {
        if (mcmInstance == null) {
            mcmInstance = new MinimapContextMenu(pMinimap);
        } 
        return mcmInstance;
    }
    
    /**
     *  Method Name     : Show()
     *  Created Date    : 2016-05-25
     *  Description     : Displays the Context Menu
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pNode : Node
     *  @param pdblX : double
     *  @param pdblY : double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void Show (Node pNode, double pdblX, double pdblY) {
        this.cmMiniMap.show(pNode, pdblX, pdblY);
    }
}
