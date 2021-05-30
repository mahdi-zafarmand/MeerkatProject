/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TreeItem;
import meerkat.Utilities;
import threadtree.IThreadTree;
import threadtree.MeerkatTreeItem;
import threadtree.TreeItemConfig.TreeItemType;
import threadtree.TreeTextualForm;

/**
 *  Class Name      : TextualTab
 *  Created Date    : 2015-10-14
 *  Description     : The Tab that maintains the properties of the Textual Tab
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class TextualTab {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */    
    private int intParentProjectID;         // Every Graph has a parent Project
    private int intTextTabID;               // Also the RootMsgID // ID used to identify a TextualNW. Will change from one instance of the applicaiton to another
    private String strFilePath ;
    private String strTitle ;            // Also the Title
    private Map<Integer, GraphTab> mapGraphs ;
    
    // UI Component
    private MeerkatTreeItem<String> treeGraph; // The UI element that is to be displayed in the TreeView
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    // TreeGraph
    public MeerkatTreeItem<String> getTreeItemGraph() {
        return this.treeGraph;
    }
    
    public int getID () {
        return this.intTextTabID;
    }
    
    public String getFilePath() {
        return this.strFilePath;
    }
    
    public Map<Integer, GraphTab> getAllGraphs() {
        return this.mapGraphs;
    }
    
    public String getTitle() {
        return this.strTitle;
    }
    
    public void setFilePath(String pstrFilePath) {
        this.strFilePath = pstrFilePath;
    }
    
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    private TextualTab() {
        mapGraphs = new HashMap<>();
        treeGraph = new MeerkatTreeItem(TreeItemType.TEXTTITLE, intTextTabID);
    }
    
    public TextualTab(int pintParentProjectID, int pintTextTabID, String pstrFilePath) {
        this();
        this.intParentProjectID = pintParentProjectID;
        this.intTextTabID = pintTextTabID;
        this.strFilePath = pstrFilePath ;
        this.strTitle = Utilities.getFileNameWithExtention(pstrFilePath);
        // System.out.println("TextualTab.TextualTab(): FileName with extension = "+strTitle);
        initiateUIComponents();
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        UIInstance.UpdateUI();
    }
        
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    /**
     *  Method Name     : updateTree()
     *  Created Date    : 2015-10-14
     *  Description     : Updates the UI based tree with the elements in the text
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void updateTree() {
        treeGraph = new MeerkatTreeItem(this.strTitle, TreeItemType.TEXTTITLE, intTextTabID);
        IThreadTree treeImplementation = new TreeTextualForm();
        treeImplementation.addGraph(getTreeItemGraph(), intParentProjectID, intTextTabID);
    }
    
    /**
     *  Method Name     : initiateUIComponents()
     *  Created Date    : 2015-10-14
     *  Description     : Updates all the UI components whenever there is a change in the Emotion Analysis Part
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void initiateUIComponents() {
                
        // Initiate the AccordionValues 
        // updateAccordionValues();
        
        // Initiate the Tree related stuff
        updateTree();                                                                                                                                                                                                                                                                                                                                                                                                                                                       
    }
}
