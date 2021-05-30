/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api;

import datastructure.core.text.HNode;
import datastructure.core.text.MsgNode;
import main.meerkat.MeerkatBIZ;
import main.project.Project;

/**
 *  Class Name      : TextualAPI
 *  Created Date    : 2016-06-20
 *  Description     : API related to to Textual Graph
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/

public class TextualAPI {
    
    /**
     *  Method Name     : stopAlgorithms()
     *  Created Date    : 2016-06-20
     *  Description     : Stops all the algorithms of the graphs
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @return boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static boolean stopAlgorithms(int pintProjectID, int pintGraphID) {
        
        boolean blnValue = true ;
        
        return blnValue ;
    }

    public static HNode getTextualNode(int pintProjectID, 
            int pintMsgNodeID, 
            int pintTextualNetworkId) {
        MeerkatBIZ meerkat = MeerkatBIZ.getMeerkatApplication();
        Project prj = meerkat.getProject(pintProjectID);
        return prj.getTextualNetwork(pintTextualNetworkId)
                .getMsgNode(pintMsgNodeID);
    }
    
    /**
     *  Method Name     : getTextContent()
     *  Created Date    : 2016-08-16
     *  Description     : Extracts the Text Content of the selected item as a list of Strings
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     * @param pintTexualNetworkId
     * @param pintMsgId
     *  @return List<String>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-12-21      Afra            change the return type from list<String>
     *                                  to String.
     *  2016-12-21      Afra            change the input parameter from rootMsgId
     *                                  to : textualNetwokrId, msgId
     * 
    */
    public String getTextContent(int pintProjectID, 
            int pintTexualNetworkId,
            int pintMsgId) {
        String lststrTextContent = null;
        try {            
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
            lststrTextContent = BIZInstance.getProject(pintProjectID)
                    .getTextualNetwork(pintTexualNetworkId)
                    .getContent(pintMsgId);
        } catch (Exception ex) {
            System.out.println("TextualAPI.getTextContent(): EXCEPTION") ;
            ex.printStackTrace();
        }
        return lststrTextContent;
    }

    /**
     *  Method Name     : getTextContentWihtDate()
     *  Created Date    : 2016-08-16
     *  Description     : Extracts the Text Content of the selected item as a list of Strings along with the date
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     * @param pintTextualNetworkId
     * @param pintMsgId
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-12-21      Afra            Changed return type from list<String>
     *                                  to String.
     *                                  Change input parameter from rootId to :
     *                                  textualNetworkId , msgId.
     * 
    */
    public String getTextContentWihtDate(int pintProjectID, 
            int pintTextualNetworkId, 
            int pintMsgId) {
        StringBuilder strTextContent = new StringBuilder();
        try {            
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
            HNode msg = BIZInstance.getProject(pintProjectID)
                    .getTextualNetwork(pintTextualNetworkId)
                    .getMsgNode(pintMsgId);
            
            if (msg instanceof MsgNode) {
                strTextContent.append(((MsgNode) msg).getContent());
            }
            strTextContent.append(":").append(msg.getDateString());
        } catch (Exception ex) {
            System.out.println("TextualAPI.getTextContent(): EXCEPTION");
            ex.printStackTrace();
        }
        return strTextContent.toString();
    }
}
