package io.text.reader;

import datastructure.core.IDGenerator;
import datastructure.core.text.HNode;
import datastructure.core.text.MsgNode;
import datastructure.core.text.impl.TextualNetwork;
import io.utility.Utilities;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aabnar
 */
public class ForumReader extends MsgReader {

    
    boolean anonymize = true;

    String USERS = "*STUDENTS", FORUM = "*FORUM", DISCUSSION = "*DISCUSSION",
            MESSAGE = "*MESSAGE", END = "*END", COURSE = "*COURSE",
            DATEFORMAT = "*DATE_FORMAT", BY = "by", AT = "at",
            INRESPONSETO = "inResponseTo";

    public DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    private Map<String, String> hmpInputFileIdToAuthor = new HashMap<>();

    private Map<String, HNode> hmpInputFileIdToMsg = new HashMap<>();

    IDGenerator hnodeIdGen = new IDGenerator();
    IDGenerator msgnodeIdGen = new IDGenerator();

    /**
     * Method Name : loadFile() Created Date : 2015-07-xx Description : Loading
     * of the Forum given an input Version : 1.0
     *
     * @author : Afra
     *
     * @param pstrFileName : String
     * @return HNode
     *
     * EDIT HISTORY (most recent at the top) Date Author Description 2015-10-13
     * Talat Changed the name from load() to loadFile() 2015-08-25 Talat
     * Converting code to code standards 2015-08-25 Talat Adding Exceptions and
     * initialization of various objects
     */
    @Override
    public TextualNetwork loadFile(String pstrFileName) {

        InputStreamReader reader = null;

        try {
            HNode course = null;
            TextualNetwork n = new TextualNetwork(
                    Utilities.getFileNameWithoutExtension(pstrFileName),
                    pstrFileName);
            reader = new InputStreamReader(new FileInputStream(pstrFileName));
            try (BufferedReader brInputStream
                    = new BufferedReader(reader, MsgReader.BUFFER_SIZE)) {
                String strCurrentLine;
                HNode hndForum = null;
                HNode hndDiscussion = null;
                while ((strCurrentLine = brInputStream.readLine()) != null) {
                    if (strCurrentLine.startsWith(DATEFORMAT, 0)) {
                        strCurrentLine = brInputStream.readLine();
                        formatter = new SimpleDateFormat(strCurrentLine);

                    } else if (strCurrentLine.startsWith(COURSE, 0)) {
                        strCurrentLine = brInputStream.readLine();
                        course = n.createHNode(strCurrentLine, null);
                    } else if (strCurrentLine.startsWith(USERS, 0)) {
                        while ((strCurrentLine = brInputStream.readLine()) != null
                                && !strCurrentLine.equals(END)) {
                            String[] sp = strCurrentLine.split("\\,+");
                            String person = sp[1];
                            hmpInputFileIdToAuthor.put(sp[0], person);
                        }
                    } else if (strCurrentLine.startsWith(FORUM, 0)) {
                        strCurrentLine = brInputStream.readLine();
                        hndForum = n.createHNode(strCurrentLine, course);
                        // hndRoot.addChild(hndForum);

                    } else if (strCurrentLine.startsWith(DISCUSSION, 0)) {
                        strCurrentLine = brInputStream.readLine();
                        hndDiscussion = n.createHNode(strCurrentLine, hndForum);
                        // hndForum.addChild(hndDiscussion);
                        hmpInputFileIdToMsg = new HashMap<>();

                    } else if (strCurrentLine.startsWith(MESSAGE, 0)) {

                        String strFileID;
                        String athMsgAuthor = null;
                        Date dtMsgDate = null;
                        String strParentID = null;
                        String strTitle;
                        String strContent;

                        strFileID = strCurrentLine.substring(MESSAGE.length()).trim();

                        strCurrentLine = brInputStream.readLine();
                        if (strCurrentLine != null && strCurrentLine.startsWith(BY)) {
                            athMsgAuthor = hmpInputFileIdToAuthor.get(Integer.parseInt(strCurrentLine
                                    .substring(BY.length()).trim()));
                        } else {
                            System.err.println(
                                    "Messages should have an sender indicated by keword "
                                    + "\"by\", please refer to documentations for more info.");
                        }

                        strCurrentLine = brInputStream.readLine();

                        if (strCurrentLine != null && strCurrentLine.startsWith(AT)) {
                            try {
                                dtMsgDate
                                        = formatter.parse(strCurrentLine.substring(AT.length()).trim());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.err.println(
                                    "Messages should have an sender indicated by keword "
                                    + "\"by\", please refer to documentations for more info.");
                        }

                        strCurrentLine = brInputStream.readLine();
                        if (strCurrentLine != null && strCurrentLine.startsWith(INRESPONSETO)) {
                            strParentID
                                    = strCurrentLine.substring(INRESPONSETO.length()).trim();

                            strCurrentLine = brInputStream.readLine();
                        }

                        strCurrentLine = brInputStream.readLine();
                        strTitle = strCurrentLine;

                        strContent = "";
                        while ((strCurrentLine = brInputStream.readLine()) != null
                                && !strCurrentLine.equals(END)) {
                            strContent = strContent.concat("\n" + strCurrentLine);
                        }

                        HNode message = n.createHNode(
                                strTitle,
                                hmpInputFileIdToMsg.get(strParentID),
                                strContent);

                        hmpInputFileIdToMsg.put(strFileID, message);
                        // hmpInputFileIdToMsg.get(strParentID).addChild(message);

                        ((MsgNode) message).setDate(dtMsgDate);
                        ((MsgNode) message).setAuthor(athMsgAuthor);

                    }
                }
            }
            return n;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ForumReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ForumReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(ForumReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
