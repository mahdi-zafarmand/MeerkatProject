/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphelements;

import accordiontab.CommunitiesTreeItem;
import javafx.scene.control.TreeCell;
import javafx.scene.layout.HBox;


/**
 *  Class Name      : CommunitiesTreeCellImpl
 *  Created Date    : 2016-07-08
 *  Description     : Tree Cell Implementation of the Communities Tree
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public final class CommunitiesTreeCellImpl extends TreeCell<HBox> {

    @Override
    public void updateItem(HBox item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setGraphic(item);
            CommunitiesTreeItem<HBox> cmTreeItem = 
                    (CommunitiesTreeItem<HBox>)getTreeItem();
            // A specific community has been selected
            if (cmTreeItem.getVertexIDs() != null) {
                setContextMenu(cmTreeItem.getCommunityMenu());
            } else {
                //Removing this context Menu - Did not find any use of it 
                //setContextMenu(cmTreeItem.getCommunitiesMenu());
            }
        }
    }
}