/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core.text.impl;

import datastructure.core.IDGenerator;
import datastructure.core.text.HNode;
import datastructure.core.text.MsgNode;
import datastructure.core.text.classinterface.ITextualNetwork;
import java.util.HashMap;

/**
 *  Class Name      : TextualNetwork
 *  Created Date    : 2016-03-01
 *  Description     : Wrapper for Textual Networks
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class TextualNetwork implements ITextualNetwork {
    private IDGenerator idgen = new IDGenerator();

    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private int intID ;
    private String strTitle ;
    private String strFileName ;
    private HNode hndRoot ;
    
    private HashMap<Integer,HNode> hmapMessages = 
            new HashMap<>();
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */

    /**
     *
     * @param pstrTitle
     * @param pstrFileName
     */
    
    public TextualNetwork(String pstrTitle, String pstrFileName) {

        this.strTitle = pstrTitle ;        
        this.strFileName = pstrFileName ;
        System.out.println("TextualNetwork.TextualNetwork(): Title: "+
                pstrFileName+
                " FileName: "+
                pstrFileName);
    }
    
    
    /* *************************************************************** */
    /* ****************     OVER-RIDDEN  METHODS     ***************** */
    /* *************************************************************** */

    /**
     *
     * @param pintId
     */
    
    @Override
    public void setId(int pintId) {
        this.intID = pintId ;
    }

    /**
     *
     * @return
     */
    @Override
    public int getId() {
        return this.intID ;
    }

    /**
     *
     * @return
     */
    @Override
    public String getTitle() {
        return this.strTitle ;
    }

    /**
     *
     * @param pstrTitle
     */
    @Override
    public void setTitle(String pstrTitle) {
        this.strTitle = pstrTitle ;
    }

    /**
     *
     * @return
     */
    @Override
    public String getFile() {
        return this.strFileName ;
    }

    /**
     *
     * @param pstrFile
     */
    @Override
    public void setFile(String pstrFile) {
        this.strFileName = pstrFile ;
    }

    /**
     *
     * @param pHNode
     */
    @Override
    public void setRoot(HNode pHNode) {
        this.hndRoot = pHNode ;
    }

    /**
     *
     * @return
     */
    @Override
    public HNode getRoot() {
        return this.hndRoot ;
    }

    @Override
    public HNode createHNode(String strTitle, 
            HNode phnParentHNode, 
            String strContext) {
        MsgNode n = new MsgNode(idgen.getNextAvailableID(), strTitle, 
                phnParentHNode, strContext);
        hmapMessages.put(n.getId(), n);
        return n;
    }

    @Override
    public HNode createHNode(String strTitle, HNode phnParentHNode) {
        HNode n = 
                new HNode(idgen.getNextAvailableID(), strTitle, phnParentHNode);
        hmapMessages.put(n.getId(), n);
        if (phnParentHNode == null) {
            this.hndRoot = n;
        }
        
        return n;
    }
    
    @Override
    public HNode getMsgNode(int pintNodeId) {
        return hmapMessages.get(pintNodeId);
    }
    
    @Override
    public String getContent(int pintNodeId) {
        HNode msg = hmapMessages.get(pintNodeId);
        
        StringBuilder strContentbld = new StringBuilder("");
        if (msg instanceof MsgNode) {
            strContentbld.append(((MsgNode) msg).getContent()).append("\n");
        } else {
            for (HNode child : msg.getChildren()) {
                strContentbld.append(getContent(child.getId()));
            }
        }
              
        return strContentbld.toString();
    }
}