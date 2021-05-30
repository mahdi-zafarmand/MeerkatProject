/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import javafx.scene.control.CheckMenuItem;

/**
 *  Interface Name  : ICheckMenuItem
 *  Created Date    : 2016-01-28
 *  Description     : Every Check Menu item should implement this interface to support java reflection 
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
*/
public interface ICheckMenuItem {
    
    /**
     *  Method Name     : Click()
     *  Created Date    : 2015-07-xx
     *  Description     : 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     *  @param pMenuItem : CheckMenuItem
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author      Description
     * 
    */
     public void Click(AnalysisController pController, CheckMenuItem pMenuItem, Object pobjParameter) ;
}
