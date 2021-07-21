/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aicml.meerkat.api;

import datastructure.core.text.HNode;
import datastructure.core.text.classinterface.ITextualNetwork;
import java.util.LinkedList;
import java.util.List;
import main.meerkat.MeerkatBIZ;
import main.project.Project;

/**
 *
 * @author mahdi
 */
public class ThreadTreeAPI {
    public static List<String> getThreadTree (int pintProjectID, int pintTextualNetworkId) {
        MeerkatBIZ mktappBusiness = MeerkatBIZ.getMeerkatApplication();
        ITextualNetwork tnetwork = mktappBusiness.getProject(pintProjectID)
                .getTextualNetwork(pintTextualNetworkId);
        
        List<String> threadtree = getDFS(tnetwork.getRoot(),0);
        return threadtree;
    }
    
    private static List<String> getDFS(HNode phndMsg,int depth) {
        List<String> lstTempTitle =new LinkedList<>();
        lstTempTitle.add(depth + ":" + phndMsg.getId() + ":" + phndMsg.getTitle());
        phndMsg.getChildren().forEach((child) -> {
            lstTempTitle.addAll(getDFS(child, depth + 1));
        });
        return lstTempTitle;
    }
    
    public static String getMsgContent(int pintProjectID, int pintTextualNetworkId, int pintMsgNodeID) {
        MeerkatBIZ mktBizInstance = MeerkatBIZ.getMeerkatApplication();
        Project currentProject = mktBizInstance.getProject(pintProjectID);
        String content = currentProject.getTextualNetwork(pintTextualNetworkId).getContent(pintMsgNodeID);
        System.out.println("ThreadTeeAPI.getMSgContent() : content = " + content);
        return content;
    }
}
