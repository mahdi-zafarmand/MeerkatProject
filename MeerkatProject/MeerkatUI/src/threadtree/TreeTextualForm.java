/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadtree;

import ca.aicml.meerkat.api.ThreadTreeAPI;
import globalstate.MeerkatUI;
import java.util.List;
import threadtree.TreeItemConfig.TreeItemType;

/**
 *  Class Name      : TextualFormImplementation
 *  Created Date    : 2015-07-28
 *  Description     : Implementation of the Textual Network for displaying in ThreadTree
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class TreeTextualForm implements IThreadTree {
    
    static int intIteratorIndex = 0;

    /**
     *  Method Name     : addProject
     *  @author         : Talat
     *  Created Date    : 2015-06-26
     *  Description     : Adds the project to the Root - to be displayed on the Thread Tree View
     *  Version         : 1.0
     * 
     *  @param ptreeRoot : TreeItem<String>
     *  @param pintProjectID : int
     *  @return TreeItem<String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *   
     * 
    */
    @Override
    public MeerkatTreeItem<String> addProject(MeerkatTreeItem<String> ptreeRoot, 
            int pintProjectID) {
                
        MeerkatUI applicationState = MeerkatUI.getUIInstance();
        
        // int intGraphID = applicationState.getActiveProject().getActiveGraphTab().getGraphID();
        
        /* For each ProjectTab, the ProjectTab by default would be a ProjectTab Name or the File Name */
        MeerkatTreeItem<String> treeProject = new MeerkatTreeItem<>(applicationState.getActiveProjectTab().getProjectName(), TreeItemType.PROJECTTITLE, pintProjectID);
        // System.out.println("TreeTextualForm.ThreadTree(): ProjectName "+applicationState.getActiveProject().getProjectName());
        
        /* List of Entries for the ThreadTree */ 
        List<String> lstEntries = ThreadTreeAPI.getThreadTree(pintProjectID, 0);
        
        // Adding the content of MsgNodes in the ThreadView
        addTreeItem(-1, lstEntries, treeProject);
        
        ptreeRoot.getChildren().add(treeProject);    
        
        return ptreeRoot;
    }
    
    /**
     *  Method Name     : addGraph
     *  @author         : Talat
     *  Created Date    : 2015-08-09
     *  Description     : Generates the treeItems for a graph that are to be displayed in the ThreadTree View
     *  Version         : 1.0
     * 
     *  @param ptreeGraph : TreeItem<String>
     *  @param pintProjectID : int
     *  @param pintRootMsgID : int
     *  @return TreeItem<String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-10-13      Talat           Changed the implementation 
     * 
    */
    @Override
    public MeerkatTreeItem<String> addGraph (MeerkatTreeItem<String> ptreeGraph, 
            int pintProjectID, 
            int pintRootMsgID) {
        
        try {
            /* List of Entries for the ThreadTree */ 
            List<String> lstEntries = ThreadTreeAPI.getThreadTree(pintProjectID, pintRootMsgID);

            // Adding the content of MsgNodes in the ThreadView
            addTreeItem(-1, lstEntries, ptreeGraph);

            // ptreeGraph.getChildren().add(ptreeGraph);
        } catch (Exception ex) {
            System.out.println("EXCEPTION in TreeeTextualForm.addGraph(): ");
            ex.printStackTrace();
        }
        
        return ptreeGraph;
    }
    
    
    /**
     *  Method Name     : addTreeItem
     *  Created Date    : 2015-07-30
     *  Description     : A recursive function that builds the hierarchy to be displayed on the ThreadTree
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintPreviousDepth : int
     *  @param plststrContent : List<String>
     *  @param ptreeitemParent : TreeItem<String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void addTreeItem (int pintPreviousDepth, 
            List<String> plststrContent, 
            MeerkatTreeItem<String> ptreeitemParent) {        
        
        /* Continue the processing until the iterator reaches the end of the list of string */
        while (intIteratorIndex < plststrContent.size()) {            
            
            /* Divide the current string into the depth (given in the string) and content */
            /*
                Example format of strings that are in the list
                    "0:Message 0.1"
                    "1:Message 1.1"
                    "2:Message 2.1"
            */
            
            
            String strCurrentLine = plststrContent.get(intIteratorIndex);        
              System.out.println("TreeTextualForm.addTreeItem(): CurrentLine "
                      + strCurrentLine);
            
            int intDepthDelimiter = strCurrentLine.indexOf(":");
            int intCurrentDepth = Integer.parseInt(
                    strCurrentLine.substring(0, intDepthDelimiter));            
            strCurrentLine = strCurrentLine
                    .substring(intDepthDelimiter+1, strCurrentLine.length());
            
            int intIDDelimiter = strCurrentLine.indexOf(":");
            int intID = Integer.parseInt(
                    strCurrentLine.substring(0, intIDDelimiter));
            strCurrentLine = strCurrentLine
                    .substring(intIDDelimiter+1, strCurrentLine.length());
            
            /* Add the content of the string to a tree item */
            MeerkatTreeItem<String> treeItem = new MeerkatTreeItem<>(
                    strCurrentLine, TreeItemType.TEXTCONTENT, intID);
            
            if (intCurrentDepth > pintPreviousDepth) {
                /* Recurse when the depth of parent is less than that of the Child */
                intIteratorIndex++; // Incrementing the iterator so that it processes the next String in the list
                ptreeitemParent.getChildren().add(treeItem); // Add the current treeitem to the Parent
                
                // Adding the ID and the Tree Instance to the Map
                ThreadTree treeInstance = ThreadTree.getTreeInstance();
                // System.out.println("TreeTextualForm.addTreeItem(): Adding Item "+intID+" for "+treeItem);
                // treeInstance.addDualHashItem(intID, treeItem);
                treeInstance.addItem(treeItem, intID);
                
                addTreeItem(intCurrentDepth, plststrContent, treeItem);  // Recurse to find more children, if any
            } else {
                return ; // Just return the control to the parent recursive function to add further treeitems, if any
            } 
        }
    }    
}