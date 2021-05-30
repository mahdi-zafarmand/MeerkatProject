/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meerkat;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author aicml_adm
 */
public class UIUtilities {
    public static void StretchToParent(Node pNode) {
        // Code for stretching the anchor
        AnchorPane.setLeftAnchor(pNode, 0d);
        AnchorPane.setRightAnchor(pNode, 0d);
        AnchorPane.setTopAnchor(pNode, 0d);
        AnchorPane.setBottomAnchor(pNode, 0d);
    }
    
    public static void StretchToParent(Control pControl) {
        // Code for stretching the anchor
        AnchorPane.setLeftAnchor(pControl, 0d);
        AnchorPane.setRightAnchor(pControl, 0d);
        AnchorPane.setTopAnchor(pControl, 0d);
        AnchorPane.setBottomAnchor(pControl, 0d);
    }
}
