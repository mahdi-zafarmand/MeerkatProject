/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.parser;

import datastructure.GraphElement;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class Name      : D3Json
 *  Created Date    : 2015-07-23
 *  Description     : The output for the D3 is sent in the form of a .json file 
 *                  : This file could be parsed to retrieve the Nodes and Edges
 *                  : and thir corresponding attributes
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class D3Json {
    
    private static int BUFFER_SIZE = 99999999;  // number of characters ~= 95MB
    
    /**
     *  Method Name     : Parse()
     *  Created Date    : 2015-07-23
     *  Description     : Parses the JSON file and returns a list of Graph Elements
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrJSONFilePath: String
     *  @return List<GraphElement>
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static List<GraphElement> Parse (String pstrJSONFilePath) {
        List<GraphElement> lstGraphElements ;
        try {
            lstGraphElements = new ArrayList<>();
            InputStreamReader reader = new InputStreamReader(new FileInputStream(pstrJSONFilePath));        	
            BufferedReader br = new BufferedReader(reader, BUFFER_SIZE);
            String strCurrentLine = new String();
            int intElementType = -1;
            int intElementID = -1;
            int intEdgeID = -1;
            
            while ((strCurrentLine = br.readLine()) != null) {              
                strCurrentLine = strCurrentLine.replaceAll("\"", "");
                int intStartIndex = strCurrentLine.indexOf("{")+1; 
                int intEndIndex = strCurrentLine.lastIndexOf("}");
                if (intEndIndex > intStartIndex) {
                    String strAttributes = strCurrentLine.substring(intStartIndex, intEndIndex).trim();
                    
                    if (!strAttributes.isEmpty()) {
                        List<AbstractMap.SimpleEntry> lstkvpAttributes = new ArrayList<>();
                        String [] arrstrParsed = strAttributes.split(",");
                        for (String strParsed : arrstrParsed) {
                            String[] arrstrKVP = strParsed.split(":");
                            if (arrstrKVP[0].equalsIgnoreCase("name")) {
                                intElementType = 0;
                                intElementID = Integer.parseInt(arrstrKVP[1]);
                                continue;
                            } else if (arrstrKVP[0].equalsIgnoreCase("source")) {
                                intElementType = 1;
                                intEdgeID++;
                                intElementID = intEdgeID;
                                lstkvpAttributes.add(new AbstractMap.SimpleEntry(arrstrKVP[0], arrstrKVP[1]));
                            } else {
                                lstkvpAttributes.add(new AbstractMap.SimpleEntry(arrstrKVP[0], arrstrKVP[1]));
                            }
                        }
                        lstGraphElements.add(new GraphElement(intElementType, intElementID, lstkvpAttributes));                        
                    }
                }
            }
            return lstGraphElements;
        } catch (IOException ex) {
            ex.printStackTrace();;
        }
        return null;
    }    
}
