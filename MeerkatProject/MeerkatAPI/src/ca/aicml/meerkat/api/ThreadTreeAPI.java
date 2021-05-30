/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api;

import datastructure.core.text.HNode;
import datastructure.core.text.MsgNode;
import datastructure.core.text.classinterface.ITextualNetwork;
import datastructure.core.text.impl.TextualNetwork;
import java.util.LinkedList;
import java.util.List;
import main.meerkat.MeerkatBIZ;
import main.project.Project;

/**
 *  Class Name      : ThreadTreeAPI
 *  Created Date    : 2015-07-28
 *  Description     : ThreadTree
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class ThreadTreeAPI {
    
    
    /**
     *  Method Name     : getThreadTree
     *  Created Date    : 2015-07-28
     *  Description     : Returns a List of String for a Textual Project that is to be displayed on the Thread Tree of UI
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     * @param pintTextualNetworkId
     *  @return lstEntries: List<String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */    
    public static List<String> getThreadTree (int pintProjectID, 
            int pintTextualNetworkId) {
        MeerkatBIZ mktappBusiness = MeerkatBIZ.getMeerkatApplication();
        ITextualNetwork tnetwork = mktappBusiness.getProject(pintProjectID)
                .getTextualNetwork(pintTextualNetworkId);
        
        List<String> threadtree = getDFS(tnetwork.getRoot(),0);
            
        return threadtree;
    }    
    
    private static List<String> getDFS(HNode phndMsg,int depth) {
        List<String> lstTempTitle =new LinkedList<>();
        lstTempTitle.add(depth + ":" 
                + phndMsg.getId() + ":" 
                + phndMsg.getTitle());
        for (HNode child : phndMsg.getChildren()) {
            lstTempTitle.addAll(getDFS(child, depth + 1));
        }
        
        return lstTempTitle;
    }
    
    public static String getMsgContent(int pintProjectID, 
            int pintTextualNetworkId,
            int pintMsgNodeID){
         MeerkatBIZ mktBizInstance = MeerkatBIZ.getMeerkatApplication();
        Project currentProject = mktBizInstance.getProject(pintProjectID);
         
        String content = currentProject.getTextualNetwork(pintTextualNetworkId)
                .getContent(pintMsgNodeID);
        
        System.out.println("ThreadTeeAPI.getMSgContent() : content = " +
                content);

        return content;
    }
   
}
