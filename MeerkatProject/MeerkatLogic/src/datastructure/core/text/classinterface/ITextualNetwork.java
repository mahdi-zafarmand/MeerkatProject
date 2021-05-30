/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.text.classinterface;

import datastructure.core.text.HNode;

/**
 *  Interface Name  : ITextualNetwork
 *  Created Date    : 2016-03-01
 *  Description     : Wrapper to store the Textual Network
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public interface ITextualNetwork {
    
    /**
     *
     * @param pintId
     */
    public void setId(int pintId);

    /**
     *
     * @return
     */
    public int getId();

    /**
     *
     * @return
     */
    public String getTitle();

    /**
     *
     * @param pstrTitle
     */
    public void setTitle(String pstrTitle);

    /**
     *
     * @return
     */
    public String getFile();

    /**
     *
     * @param pstrFile
     */
    public void setFile(String pstrFile);

    /**
     *
     * @param pHNode
     */
    public void setRoot(HNode pHNode);

    /**
     *
     * @return
     */
    public HNode getRoot();
    
    public HNode getMsgNode(int pintNodeId);
            
    public String getContent(int pintMsgId);
    
    public HNode createHNode(String strTitle, 
            HNode phnParentHNode, 
            String strContext);
    
    public HNode createHNode(String strTitle, 
            HNode phnParentHNode);
}
