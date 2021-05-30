/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

/**
 *  Interface Name  : ITitledPaneContents
 *  Created Date    : 2015-09-01
 *  Description     : The interface that defines the functionalities of the TitledPane contents
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public interface ITitledPaneContents {
    public void initialize(Accordion paccParentAccordionPane, TitledPane paccTitledPane) ;
    public void updatePane(Accordion paccParentAccordionPane, TitledPane paccTitledPane, int pintProjectID, int pintGraphID, int pintTimeFrameIndex, Object pobjContent) ;        
}
