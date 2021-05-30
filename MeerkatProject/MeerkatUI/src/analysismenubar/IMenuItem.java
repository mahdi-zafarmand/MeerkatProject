/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysismenubar;

import analysisscreen.AnalysisController;
import javafx.scene.control.MenuItem;

/**
 *  Interface Name  : IMenuItem
 *  Created Date    : 2015-07-xx
 *  Description     : Every Menu item should implement this interface to support java reflection 
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-01-28      Talat           Changed the signature of Click()
 * 
*/
public interface IMenuItem {
    
    /**
     *  Method Name     : Click()
     *  Created Date    : 2015-07-xx
     *  Description     : 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     *  @param pMenuItem : MenuItem
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author      Description
     *  2016-01-28      Talat       Changed the parameter from boolean to MenuItem
     * 
    */
     public void Click(AnalysisController pController, MenuItem pMenuItem, Object pobjParameter) ;
}
