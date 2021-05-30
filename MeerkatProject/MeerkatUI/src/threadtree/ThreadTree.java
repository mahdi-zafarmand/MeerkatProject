/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadtree;

import config.AppConfig;
import config.GraphConfig.GraphType;
import globalstate.ProjectTab;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TreeItem;
import threadtree.TreeItemConfig.TreeItemType;

/**
 *  Class Name      : ThreadTree
 *  Created Date    : 2015-07-28
 *  Description     : List of Projects that are to be displayed on the screen. Uses singleton pattern
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class ThreadTree {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    private MeerkatTreeItem<String> treeitemsRoot;
    private ThreadTreeLangParameters treeParameters ; // Parameters that would be useful in language dependent display    
    
    // private BidiMap <Integer, MeerkatTreeItem<String>> bdIDTreeMap ;
    private Map<MeerkatTreeItem, Integer> mapTreeMap ;
    
    private static ThreadTree threadTree = null; // The only instance that is created for this class - Singleton pattern
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public int getID(MeerkatTreeItem<String> pTreeItem) {
        return mapTreeMap.get(pTreeItem);
    }
    
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    
    /**
     *  Method Name     : ThreadTree (Constructor)
     *  Created Date    : 2015-07-27
     *  Description     : A ThreadTree constructor that parses the initial ThreadTree parameters (language dependent ones)
     *                  : and adds the project label to the root
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private ThreadTree () {
        /* Extract the ThreadTree parameters information from the Language XML */
        treeParameters = io.parser.ThreadTree.Parse(AppConfig.XML_LANGUAGE_FILE);
        
        // bdIDTreeMap = new DualHashBidiMap<>();
        mapTreeMap = new HashMap<>();
        
        // Add them to the root
        treeitemsRoot = new MeerkatTreeItem<>(ThreadTreeLangParameters.getProjectLabel(), TreeItemType.ROOTNODE, -1);
                
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : getTreeInstance
     *  Created Date    : 2015-07-27
     *  Description     : To get the single instance of the ThreadTree. Instantiates it if it has not been done yet
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return ThreadTree
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static ThreadTree getTreeInstance () {
        if (threadTree == null) {
            threadTree = new ThreadTree();
        }
        return threadTree;
    }
    
    /**
     *  Method Name     : getTreeItems()
     *  Created Date    : 2015-07-27
     *  Description     : Returns a list of All the TreeItems in the ThreadTree
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @return TreeItem<String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public MeerkatTreeItem<String> getTreeItems() {
        return this.treeitemsRoot;
    }
    
    /**
     *  Method Name     : addProject
     *  Created Date    : 2015-07-30
     *  Description     : Adds a project to the list of TreeItems that are be viewed in TreeView
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pProjectTab : ProjectTab
     *  @return TreeItem<String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    /* TOBEREMOVED*/
    public MeerkatTreeItem<String> addProject(ProjectTab pProjectTab, int intDummy) {
        /*
            Currently it would support onlly three forms
            1) Regular Graph files
            2) Textual Networks
        */
        GraphType enmProjectType = null;
                
        // enmProjectType = 2; // IMPORTANT : Remove this 
        IThreadTree treeImplementation = null;    
        switch(enmProjectType) {
            case GRAPH :
                treeImplementation = new TreeGraphForm();
                break;
            case TEXTUALGRAPH:
                treeImplementation = new TreeTextualForm();
                break;               
        }        
        return treeImplementation.addProject(treeitemsRoot, pProjectTab.getProjectID());        
    }
    // #END REMOVE
    
    /**
     *  Method Name     : addProject
     *  Created Date    : 2015-07-30
     *  Description     : Adds a project to the list of TreeItems that are be viewed in TreeView
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pProjectTab : ProjectTab
     *  @return TreeItem<String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-08-10      Talat           The type of graph is now checked in GraphTab and not in Project. Therefore, at this place
     *                                  only the contents of treeProject in ProjectTab are added
    */
    public MeerkatTreeItem<String> addProject(ProjectTab pProjectTab) {
        treeitemsRoot.getChildren().add(pProjectTab.getTreeItemProject());
        return treeitemsRoot;
    }
    
    /**
     *  Method Name     : removeProject
     *  Created Date    : 2015-07-31
     *  Description     : Removes a project from the list of TreeItems that is to be viewed in TreeView
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pProjectTab : ProjectTab
     *  @return TreeItem<String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public MeerkatTreeItem<String> removeProject(ProjectTab pProjectTab) {
        String strProjectName = pProjectTab.getProjectName();
        
        for(TreeItem<String> treeitemProject: treeitemsRoot.getChildren()){
            if(treeitemProject.getValue().equalsIgnoreCase(strProjectName)){
                // Remove the project
                treeitemsRoot.getChildren().remove(treeitemProject);
                break;
            } 
        }
        return treeitemsRoot;
    }
    
        
    /**
     *  Method Name     : addDualHashItem()
     *  Created Date    : 2015-10-14
     *  Description     : Adds an Item to the DualHashMap with the ID as the Key and TreeItem as the Value
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pTreeItem : MeerkatTreeItem<String>
     *  @param pintID : Integer
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    /*
    public void addDualHashItem (Integer pintID, MeerkatTreeItem<String> ptreeItem) {
        this.bdIDTreeMap.put(pintID, ptreeItem);
    }
    */
    
    public void addItem (MeerkatTreeItem<String> pTreeItem, Integer pintID) {
        this.mapTreeMap.put(pTreeItem, pintID);
    }
    
}
