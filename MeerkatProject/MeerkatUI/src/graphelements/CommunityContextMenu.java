/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphelements;

import accordiontab.CommunitiesTreeItem;
import ca.aicml.meerkat.api.GraphAPI;
import config.CommunityContextConfig;
import config.StatusMsgsConfig;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *  Class Name      : CommunityContextMenu 
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
public class CommunityContextMenu {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static ContextMenu cmNode;
    private static CommunityContextMenu comcmInstance = null; // To use singleton pattern
    
    private CommunitiesTreeItem communitiesTreeItemSelected;
    private Color colorSelectedTreeItem;
    private ColorPicker clrPicker;

    private MenuItem menuPinVertices ;
    private MenuItem menuUnpinVertices ;
    private MenuItem menuExtractGraph ;
    //private MenuItem menuColorPicker;
    private Menu menuColorPicker;
    private MenuItem menuItemColorPicker;
    private MenuItem menuSaveCommunity ;
    
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    
    public static CommunityContextMenu getInstance() {
        if (comcmInstance == null) {
            comcmInstance = new CommunityContextMenu();
        } 
        return comcmInstance;
    }
    
    public static CommunityContextMenu getInstance(CommunitiesTreeItem communitiesTreeItem) {
        if (comcmInstance == null) {
            comcmInstance = new CommunityContextMenu(communitiesTreeItem);
        }
        return comcmInstance;
    }
    
    
    public void setSelectedTreeItem(CommunitiesTreeItem treeItemSelected, Color colorSelectedTreeItem){
        communitiesTreeItemSelected = treeItemSelected;
        setColorSelectedTreeItem(colorSelectedTreeItem);
    }
    private void setColorSelectedTreeItem(Color colortreeItemSelected){
        colorSelectedTreeItem = colortreeItemSelected;
        clrPicker.setValue(colorSelectedTreeItem);
    }
    

    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    private CommunityContextMenu() {
    
    }
    
    
    private CommunityContextMenu(CommunitiesTreeItem communitiesTreeItem) {
    
        communitiesTreeItemSelected = communitiesTreeItem;
        clrPicker = new ColorPicker();
        cmNode = new ContextMenu();
        
        
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex() ;
        
        /* ADDING THE CONTEXT MENU ITEM */
        menuPinVertices = new MenuItem(CommunityContextConfig.getPinVerticesText());
        menuPinVertices.setOnAction(event -> {
            event.consume();
            UIInstance.getController().updateStatusBar(true, StatusMsgsConfig.VERTEXPINNING_ENABLING);
//            UIInstance.getActiveProjectTab().getActiveGraphTab().pinVertexToCanvas();
            for (UIVertex vtx : UIInstance.getActiveProjectTab()
                    .getActiveGraphTab().getGraphCanvas().getSelectedVertices()) {
                vtx.getVertexHolder().pinVertexToCanvas();
            }
            UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.VERTEXPINNING_ENABLED);
        });
        
        menuUnpinVertices = new MenuItem(CommunityContextConfig.getUnpinVerticesText());
        menuUnpinVertices.setOnAction(event -> {
            event.consume();
            UIInstance.getController().updateStatusBar(true, StatusMsgsConfig.VERTEXPINNING_DISABLING);
//            UIInstance.getActiveProjectTab().getActiveGraphTab().unpinVertexToCanvas();
            for (UIVertex vtx : UIInstance.getActiveProjectTab()
                    .getActiveGraphTab().getGraphCanvas().getSelectedVertices()) {
                vtx.getVertexHolder().unpinVertexToCanvas();
            }
            UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.VERTEXPINNING_DISABLED);
        });
        
        menuExtractGraph = new MenuItem(CommunityContextConfig.getExtractGraphText());
        menuExtractGraph.setOnAction(event -> {
            
            event.consume();
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
            //TODO get these edges based on communityobject - not on setzofselected in graph canvas
            Set<UIVertex> setSelectedVertices = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getSelectedVertices();
            Set<UIEdge> setSelectedEdges = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getSelectedEdges();
            System.out.println("CommunityContextMenu.communityContextMenu() extractCommunity: activeGraphTabTitle Parent :: " + UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphTabTitle() + 
                        ", its graphid = " + intGraphID);
            String[] timeFrames = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrames();
            int currentTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            if(setSelectedVertices.size() > 0 || setSelectedEdges.size() > 0){
                ExtractSubGraph.extractASubgraph(intProjectID, intGraphID, currentTimeFrameIndex,timeFrames, setSelectedVertices, setSelectedEdges);
            }
        });
        
        
        
        // Change color of vertices of this community
        menuColorPicker = new Menu("Change color of commuity");
        menuItemColorPicker = new MenuItem(null, clrPicker);
        menuColorPicker.getItems().add(menuItemColorPicker);
        //menuColorPicker = new MenuItem(null, clrPicker);
        menuItemColorPicker.setOnAction((ActionEvent t) -> {
            System.out.println("CommunityContextMenu.CommunityContextMenu(): Change of color invoked for community");
            Color newColor = clrPicker.getValue();
            System.out.println("CommunityContextMenu.CommunityContextMenu(): commuity = " + this.communitiesTreeItemSelected.getCommunityID());
            
            //change color of this row in TreeView
            this.communitiesTreeItemSelected.changeColorOfCommunity(newColor);
            //vertices and edges of this community have been selected in CommunitiesValues.updateCommunitiesOnLoading and  updateCommunities listeners  
            
            //change color of vertices of this community
            //change color of edges of this community     
            GraphTab currentGraph = UIInstance.getActiveProjectTab().getActiveGraphTab();
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
            
            Set setSelectedVertexIDs = this.communitiesTreeItemSelected.getVertexIDs();
            currentGraph.getGraphCanvas().updateColorVertices(setSelectedVertexIDs, newColor);      
            currentGraph.getGraphCanvas().updateColorEdgesAmongVertices(this.communitiesTreeItemSelected.getVertexIDs(), newColor);
            
            //upadate vertex and edge - sys:color in logic
            GraphAPI.updateVertexColor(intProjectID, intGraphID, intTimeFrameIndex, newColor.toString(), new ArrayList<Integer>(){{addAll(setSelectedVertexIDs);}});
            GraphAPI.updateEdgeColor(intProjectID, intGraphID, intTimeFrameIndex, newColor.toString(), new ArrayList<Integer>(){{addAll(setSelectedVertexIDs);}});
            
            //update community color in the logic here
            GraphAPI.setCommunityColor(intProjectID, intGraphID, intTimeFrameIndex, this.communitiesTreeItemSelected.getCommunityID(), newColor.toString());
            
            
            //update mapCommuityColor in CommunitiesValues

            //TODO update these methods and their calls
            currentGraph.getAccordionTabValues().getCommunitiesValues().updateCommunityColorMap(this.communitiesTreeItemSelected.getCommunityID(), newColor);
            

            //Update Table tblVertexCommunity in CommunitiesValues
            currentGraph.getAccordionTabValues().getCommunitiesValues().updateTableVertexCommunityColors(this.communitiesTreeItemSelected.getVertexIDs(), newColor);
            //TODO - what happens if graph extracted now - check those cases
        });        
        
        
        
        
        
        menuSaveCommunity = new MenuItem(CommunityContextConfig.getSaveCommunityText());
        menuSaveCommunity.setOnAction(event -> {
            event.consume();
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
            Set<UIVertex> setSelectedVertices = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getSelectedVertices();
            Set<UIEdge> setSelectedEdges = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getSelectedEdges();
            
            int intcurrentTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
        
            FileChooser filechooser = new FileChooser();
            filechooser.setInitialDirectory((new File(System.getProperty("user.dir"))));
            filechooser.setTitle("choose file name");
            filechooser.setInitialFileName("community.json");
            File file = filechooser.showSaveDialog(dialog);

            String filePath;

            if(file!= null){
                filePath = file.getAbsolutePath();
            }else{
                filePath=null;
            }
            
            if(filePath!=null)
                SaveCommunity.saveCommunity(intProjectID, intGraphID, intcurrentTimeFrameIndex, setSelectedVertices, setSelectedEdges, filePath);
            
        });
        

        cmNode.getItems().addAll(
                  menuPinVertices
                , menuUnpinVertices
                , menuExtractGraph
                , menuColorPicker
                , menuSaveCommunity
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
     *  Description     : Shows the context menu for the Community
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
     *  Description     : Returns the Context Menu for a Community in the Community Thread Tree
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
