/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.toolsribbon;

import analysisscreen.AnalysisController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 *  Class Name      : ContextMenu
 *  Created Date    : 2016-01-18
 *  Description     : The COntext Menu on the Tools Ribbon shown in the Analysis Screen
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class ToolsRibbonContextMenu {
    
    private static String strHideMenu ;
    private static boolean blnIsShown = true ;
    private ContextMenu cmToolsRibbon ;
    private MenuItem menuHideTool ;
    private static ToolsRibbonContextMenu trcmInstance ;
    
    public static String getHideMenu () {
        return strHideMenu ;
    }
    
    public static void setHideMenu(String pstrHideMenu){
        strHideMenu = pstrHideMenu ;
    }
    
    public static boolean getIsShown (){
        return blnIsShown ;
    }
    
    public static void setIsShown(boolean pblnIsShown) {
        blnIsShown = pblnIsShown ;
    }
    
    
    
    
    private ToolsRibbonContextMenu(AnalysisController pController) {
        
        cmToolsRibbon = new ContextMenu();
        
        /* ADDING THE CONTEXT MENU ITEM */        
        menuHideTool = new MenuItem(getHideMenu());
        menuHideTool.setOnAction((ActionEvent t) -> {
            System.out.println("ToolsRibbonContextMenu.ToolsRibbonContextMenu(): Clicked to hide the tools ribbon");
            pController.hideToolsRibbon();
        });        

        cmToolsRibbon.getItems().add(menuHideTool);
    }
    
    
     public static ToolsRibbonContextMenu getInstance(AnalysisController pController) {
        if (trcmInstance == null) {
            trcmInstance = new ToolsRibbonContextMenu(pController);
        } 
        return trcmInstance;
    }
    
     
    public void Show (Node pNode, double pdblX, double pdblY) {
        cmToolsRibbon.show(pNode, pdblX, pdblY);
    }
    
    
    public void Hide() {
        cmToolsRibbon.hide();
    }
}
