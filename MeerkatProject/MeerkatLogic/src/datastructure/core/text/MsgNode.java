package datastructure.core.text;

import java.util.Date;

/**
 * Classname: MsgNode (Message Node)
 * Version: 1
 * Created Date: July 13, 2015
 * @author: aabnar
 * Copyright: @meerkat
 * Description:
 * MsgNode contains information about a Hierarchical node that has some
 * text attached to them.
 * @author aabnar
 *
 * @param <A>
 * 		Type of the Author/Creator of the Node
 * @param <L>
 * 
 * * -------------------------------------------
 * EDIT HISTORY (latest on top)
 * Date             Author      Description
 * 2015-10-04       Talat       Changed the access modifiers to protected
 * July 13,2015     aabnar	created
 */
public class MsgNode<A,L> extends HNode{

    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */

    /**
     *
     */
    
    
    protected Date dtTime;

    /**
     *
     */
    protected L location;

    /**
     *
     */
    protected A author;

    /**
     *
     */
    protected String content;
	
        
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */

    /**
     *
     * @param pintId
     * @param pstrTitle
     * @param pParent
     */
    
    public MsgNode(int pintId, String pstrTitle, HNode pParent, String pcontent) {
        super(pintId, pstrTitle, pParent);
        this.content = pcontent;
        
    }

//    /**
//     *
//     * @param pintId
//     * @param pNode
//     * @param pParent
//     */
//    public MsgNode(int pintId, HNode pNode, HNode pParent) {
//        super (pintId, pNode.getTitle(), pParent);
//    }

    /**
     *
     * @param pintId
     * @param pstrTitle
     * @param pParent
     * @param pDate
     * @param pLocation
     * @param pAuthor
     */
    public MsgNode(int pintId, 
            String pstrTitle, 
            HNode pParent, 
            Date pDate, 
            L pLocation, 
            A pAuthor,
            String pcontent) {
        this(pintId, pstrTitle, pParent, pcontent);
        this.dtTime = pDate;
        this.location = pLocation;
        this.author = pAuthor;
    }
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */

    /**
     *
     * @param pDate
     */
    

    public void setDate(Date pDate) {
        this.dtTime = pDate;
    }

    /**
     *
     * @return
     */
    public Date getDate () {
        return dtTime;
    }

    /**
     *
     * @param pLocation
     */
    public void setLocation(L pLocation) {
        this.location = pLocation;
    }

    /**
     *
     * @return
     */
    public L getLocation () {
        return location;
    }

    /**
     *
     * @param pAuthor
     */
    public void setAuthor (A pAuthor) {
        this.author = pAuthor;
    }

    /**
     *
     * @return
     */
    public A getAuthor () {
        return author;
    }

    /**
     *
     * @param pContent
     */
    public void setContent (String pContent) {
        this.content = pContent;
    }

    /**
     *
     * @return
     */
    public String getContent () {
        return content;
    }
}
