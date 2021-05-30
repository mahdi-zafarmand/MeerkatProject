/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import datastructure.core.text.HNode;
import datastructure.core.text.MsgNode;
import datastructure.core.text.impl.TextualNetwork;
import io.text.reader.TextualXMLReader;

/**
 *
 * @author Afra
 */
public class TextualFileReaderTest {
    
    public static void main(String[] args) {
        TextualXMLReader txmlr = new TextualXMLReader();
        TextualNetwork n = txmlr.loadFile("../MeerkatUI/sample_emotion.textual");
        
        printNode(n.getRoot() , "");
    }
    
    static void printNode (HNode n , String space) {
        System.out.println(space + n.getTitle());
        if (n instanceof MsgNode) {
            System.out.println(space + ":" + ((MsgNode)n).getContent());
        }
                
        
        for (HNode child : n.getChildren()) {
            printNode(child, space + "\t");
        }
    }
}
