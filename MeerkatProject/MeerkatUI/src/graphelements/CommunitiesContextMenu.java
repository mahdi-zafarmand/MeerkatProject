/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphelements;

import communitymining.dialogwindow.DynamicCommunityMining;
import communitymining.dialogwindow.FastModularity;
import communitymining.dialogwindow.KMeansClustering;
import communitymining.dialogwindow.LocalCommunity;
import communitymining.dialogwindow.LocalT;
import communitymining.dialogwindow.LocalTopLeaders;
import communitymining.dialogwindow.RosvallInfomap;
import communitymining.dialogwindow.RosvallInfomod;
import communitymining.dialogwindow.SameAttributeValue;
import config.CanvasContextConfig;
import config.CommunitiesContextConfig;
import globalstate.MeerkatUI;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 *  Class Name      : CommunitiesContextMenu 
 *  Created Date    : 2016-07-07
 *  Description     : The Context Menu for the specific community
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class CommunitiesContextMenu {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static ContextMenu cmNode;
    private static CommunitiesContextMenu comcmInstance = null; // To use singleton pattern

    private MenuItem menuSaveAllCommunities ;
    private MenuItem menuSaveNewGraph ;
    private MenuItem menuClearMining ;
    
    private Menu menuAlgorithms ;
    private MenuItem menuAlgKMeans;
    private MenuItem menuAlgSAV ;
    private MenuItem menuAlgFMod;
    private MenuItem menuAlgLocalT ;
    private MenuItem menuAlgLocalTop ;
    private MenuItem menuAlgLocalComm ;
    private MenuItem menuAlgRosvallInfomap ;
    private MenuItem menuAlgRosvallInfomod ;
    private MenuItem menuAlgDynamicCommunity ;
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public static CommunitiesContextMenu getInstance() {
        if (comcmInstance == null) {
            comcmInstance = new CommunitiesContextMenu();
        } 
        return comcmInstance;
    }

    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    private CommunitiesContextMenu() {
        
        cmNode = new ContextMenu();
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
        int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
        int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex() ;
        
        /* ADDING THE CONTEXT MENU ITEM */
        menuSaveAllCommunities = new MenuItem(CommunitiesContextConfig.getSaveCommunitiesText());
        menuSaveAllCommunities.setOnAction(event -> {
            event.consume();
        });
        
        menuSaveNewGraph = new MenuItem(CommunitiesContextConfig.getSaveAsNewGraphText());
        menuSaveNewGraph.setOnAction(event -> {
            event.consume();
        });
        
        menuClearMining = new MenuItem(CommunitiesContextConfig.getClearMiningResultsText());
        menuClearMining.setOnAction(event -> {
            event.consume();
        });
        
        menuAlgKMeans = new MenuItem(CommunitiesContextConfig.getAlgoKMeansText());
        menuAlgKMeans.setOnAction(event -> {
            event.consume();
            KMeansClustering.Display(UIInstance.getController(), null); 
        });
        
        menuAlgSAV = new MenuItem(CommunitiesContextConfig.getAlgoSameAttributeValueText());
        menuAlgSAV.setOnAction(event -> {
            event.consume();
            SameAttributeValue.Display(UIInstance.getController(), null);   
        });
        
        menuAlgFMod = new MenuItem(CommunitiesContextConfig.getAlgoFastModularityText());
        menuAlgFMod.setOnAction(event -> {
            event.consume();
            // Display the Parameters dialog box
            FastModularity.Display(UIInstance.getController(), null);   
        });
        
        menuAlgLocalT = new MenuItem(CommunitiesContextConfig.getAlgoLocalTText());
        menuAlgLocalT.setOnAction(event -> {
            event.consume();
            LocalT.Display(UIInstance.getController(), null);
        });
        
        menuAlgLocalTop = new MenuItem(CommunitiesContextConfig.getAlgoLocalTopText());
        menuAlgLocalTop.setOnAction(event -> {
            event.consume();
            LocalTopLeaders.Display(UIInstance.getController(), null);  
        });
        
        menuAlgLocalComm = new MenuItem(CommunitiesContextConfig.getAlgoLocalCommunityText());
        menuAlgLocalComm.setOnAction(event -> {
            event.consume();
            LocalCommunity.Display(UIInstance.getController(), null);     
        });
        
        menuAlgRosvallInfomap = new MenuItem(CommunitiesContextConfig.getAlgoRosvallInfomapText());
        menuAlgRosvallInfomap.setOnAction(event -> {
            event.consume();
            RosvallInfomap.Display(UIInstance.getController(), null);
        });
        
        menuAlgRosvallInfomod = new MenuItem(CommunitiesContextConfig.getAlgoRosvallInfomodText());
        menuAlgRosvallInfomod.setOnAction(event -> {
            event.consume();
            RosvallInfomod.Display(UIInstance.getController(), null);      
        });
        
        menuAlgDynamicCommunity = new MenuItem(CommunitiesContextConfig.getAlgoDynamicCommunityText());
        menuAlgDynamicCommunity.setOnAction(event -> {
            event.consume();
            DynamicCommunityMining.Display(UIInstance.getController(), null);       
        });
        
        
        menuAlgorithms = new Menu(CommunitiesContextConfig.getCommunityAlgorithmsText());
        menuAlgorithms.getItems().add(menuAlgKMeans) ;
        menuAlgorithms.getItems().add(menuAlgSAV) ;
        menuAlgorithms.getItems().add(menuAlgFMod) ;
        menuAlgorithms.getItems().add(menuAlgLocalT) ;
        menuAlgorithms.getItems().add(menuAlgLocalTop) ;
        menuAlgorithms.getItems().add(menuAlgLocalComm) ;
        menuAlgorithms.getItems().add(menuAlgRosvallInfomap) ;
        menuAlgorithms.getItems().add(menuAlgRosvallInfomod) ;
        menuAlgorithms.getItems().add(menuAlgDynamicCommunity) ;
        

        cmNode.getItems().addAll(
                  menuSaveAllCommunities
                , menuSaveNewGraph
                , menuClearMining
                , menuAlgorithms
            );        
    }
    

    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : Show()
     *  Created Date    : 2016-07-07
     *  Description     : Shows the context menu for the specific Community
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pNode : Node
     *  @param pdblX : double
     *  @param pdblY : double
     * 
     *  EDIT HISTORY (most recent at the top)20
     *  Date            Author          Description
     *  
     * 
    */
    public void Show (Node pNode, double pdblX, double pdblY) {
        this.cmNode.show(pNode, pdblX, pdblY);
    }
    
    /**
     *  Method Name     : Hide()
     *  Created Date    : 2016-07-07
     *  Description     : Shows the context menu for the Canvas
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)20
     *  Date            Author          Description
     *  
     * 
    */
    public void Hide () {
        this.cmNode.hide();
    }
    
    /**
     *  Method Name     : getMenu()
     *  Created Date    : 2016-07-08
     *  Description     : Returns the Context Menu for the root node of Communities in the Community Thread Tree
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return ContextMenu
     * 
     *  EDIT HISTORY (most recent at the top)20
     *  Date            Author          Description
     *  
     * 
    */
    public ContextMenu getMenu() {
        return cmNode ;
    }
}
