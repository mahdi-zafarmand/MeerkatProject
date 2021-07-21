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
 *
 * @author mahdi
 */
public class TextualAPI {
    public static boolean stopAlgorithms(int pintProjectID, int pintGraphID) {
        boolean blnValue = true ;        
        return blnValue ;
    }
    
    public static HNode getTextualNode(int pintProjectID, int pintMsgNodeID, int pintTextualNetworkId) {
        MeerkatBIZ meerkat = MeerkatBIZ.getMeerkatApplication();
        Project prj = meerkat.getProject(pintProjectID);
        return prj.getTextualNetwork(pintTextualNetworkId).getMsgNode(pintMsgNodeID);
    }
    
    public String getTextContent(int pintProjectID, int pintTexualNetworkId, int pintMsgId) {
        String lststrTextContent = null;
        try {            
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
            lststrTextContent = BIZInstance.getProject(pintProjectID)
                    .getTextualNetwork(pintTexualNetworkId).getContent(pintMsgId);
        } catch (Exception ex) {
            System.out.println("TextualAPI.getTextContent(): EXCEPTION") ;
            ex.printStackTrace();
        }
        return lststrTextContent;
    }
    
    public String getTextContentWihtDate(int pintProjectID, int pintTextualNetworkId, int pintMsgId) {
        StringBuilder strTextContent = new StringBuilder();
        try {            
            MeerkatBIZ BIZInstance = MeerkatBIZ.getMeerkatApplication();
            HNode msg = BIZInstance.getProject(pintProjectID)
                    .getTextualNetwork(pintTextualNetworkId).getMsgNode(pintMsgId);
            
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
