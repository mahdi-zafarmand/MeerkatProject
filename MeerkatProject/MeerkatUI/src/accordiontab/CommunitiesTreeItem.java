/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import graphelements.CommunitiesContextMenu;
import graphelements.CommunityContextMenu;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *  Class Name      : CommunitiesTreeItem
 *  Created Date    : 2016-05-12
 *  Description     : The Treeitem to be displayed on the Communities Panel
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class CommunitiesTreeItem<V> extends TreeItem<V>{
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private Set<Integer> setVertexIDs ;
    private String strCommunityID ;
    private V hboxCommunity;
    
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */   
    public Set<Integer> getVertexIDs () {
        return this.setVertexIDs ;
    }
    public void setID (Set<Integer> plstVertexIDs) {
        this.setVertexIDs = plstVertexIDs ;
    }
    
    public String getCommunityID () {
        return this.strCommunityID ;
    }
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
        
    public CommunitiesTreeItem(V pVContent, String pstrCommunityID, Set<Integer> psetVertexIDs) {
        super(pVContent);
        this.hboxCommunity = pVContent;
        this.strCommunityID = pstrCommunityID ;
        this.setVertexIDs = new HashSet<>();
        this.setVertexIDs = psetVertexIDs;
    }  
    
    public CommunitiesTreeItem(V pVContent) {
        super(pVContent);
        this.strCommunityID = null;        
        this.setVertexIDs = null;
    }  
    
    public ContextMenu getCommunityMenu(){
        
        CommunityContextMenu cmMenu = CommunityContextMenu.getInstance(this);
        //CommunityContextMenu cmMenu = new CommunityContextMenu(this);
        return cmMenu.getMenu();
    }
    
    /*
    Updates the selectedTreeItem field to currently selected TreeItem in CommunityContextMenu class which is a singelton. It 
    */
    public void setSelectedTreeItemInContextmenu(Color colorSelectedTreeItem){
        
        CommunityContextMenu.getInstance(this).setSelectedTreeItem(this, colorSelectedTreeItem);
        
    }
    
    public ContextMenu getCommunitiesMenu(){
        CommunitiesContextMenu cmMenu = CommunitiesContextMenu.getInstance();
        return cmMenu.getMenu();
    }
    
    public void changeColorOfCommunity(Color clrCommunityColor){
    /*
                Rectangle r = new Rectangle();
                r.setWidth(20);
                r.setHeight(10);
                r.setFill(clrCommunityColor);
                Group grpCurrentRectangle = new Group(r);
                
                
                Text txtChild = new Text(strCommunityID 
                        + "(" + 0 + " " + LangConfig.GENERAL_VERTEXPLURAL + ")"
                        + "[" + 0 + " " + LangConfig.GENERAL_COMMUNITYSUB + "]");
                        //+ "(" + intCommunityVertexCount + " " + LangConfig.GENERAL_VERTEXPLURAL + ")"
                        //+ "[" + intSubCommunities + " " + LangConfig.GENERAL_COMMUNITYSUB + "]");
                HBox hboxCurrentCommunity = new HBox(grpCurrentRectangle, txtChild);
                
                
                hboxCurrentCommunity.setSpacing(5);
                hboxCurrentCommunity.setPadding(new Insets(2,2,2,2));
                
                
                this.setValue((V)hboxCurrentCommunity);
                
    */            
                Group grpCurrentRectangler = (Group)((HBox)this.hboxCommunity).getChildren().get(0);
                Rectangle rectColor = (Rectangle)grpCurrentRectangler.getChildren().get(0);
                //TODO make this rectangle as a field in this class and then do this change
                rectColor.setFill(clrCommunityColor);
    }
    

}
