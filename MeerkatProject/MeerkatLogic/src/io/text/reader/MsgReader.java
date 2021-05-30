package io.text.reader;

import datastructure.core.text.HNode;
import datastructure.core.text.impl.TextualNetwork;
import io.DataReader;


/**
 *  Class Name      : MsgReader
 *  Created Date    : 2015-07-xx
 *  Description     : 
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2015-10-13      Talat           Extends DataReader
 *  2015-10-13      Talat           Changed from class to abstract class
 * 
*/
public abstract class MsgReader extends DataReader {

    /**
     *
     */
    public static int BUFFER_SIZE = 99999999;  // number of characters ~= 95MB

    /**
     *
     * @param strInputFilePath
     * @return
     */
    public abstract TextualNetwork loadFile(String strInputFilePath);

    
}
