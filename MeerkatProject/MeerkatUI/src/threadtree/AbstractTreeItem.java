/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadtree;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Talat-AICML
 */
public abstract class AbstractTreeItem<V> extends TreeItem<V>{
    public abstract ContextMenu getMenu();
    
}
