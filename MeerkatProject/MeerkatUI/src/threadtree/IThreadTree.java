/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadtree;

import javafx.scene.control.TreeItem;

/**
 *  Interface Name  : IThreadTree
 *  Created Date    : 2015-08-08
 *  Description     : The interface with all the function declarations
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description *  
 * 
*/
public interface IThreadTree {
    public MeerkatTreeItem<String> addProject(MeerkatTreeItem<String> ptreeRoot, 
            int pintProjectID);
    
    public MeerkatTreeItem<String> addGraph (MeerkatTreeItem<String> ptreeGraph, 
            int pintProjectID, int pintGraphID);
}
