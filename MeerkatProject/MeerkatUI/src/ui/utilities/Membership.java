/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.utilities;

/**
 *  Class Name      : Membership
 *  Created Date    : 2016-07-07
 *  Description     : Membership class that see if one structure contains other
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class Membership {
    
    /**
     *  Method Name     : lineWithinRectangle()
     *  Created Date    : 2016-07-07
     *  Description     : Checks if a line is contained within a rectangle
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pdblLineStartX : double
     *  @param pdblLineStartY : double
     *  @param pdblLineEndX : double
     *  @param pdblLineEndY : double
     *  @param pdblRectStartX : double
     *  @param pdblRectStartY : double
     *  @param pdblRectEndX : double
     *  @param pdblRectEndY : double
     *  @return boolean
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */

    public static boolean lineWithinRectangle(
              double pdblLineStartX
            , double pdblLineStartY
            , double pdblLineEndX
            , double pdblLineEndY
            , double pdblRectStartX
            , double pdblRectStartY
            , double pdblRectEndX
            , double pdblRectEndY
    ) {
        boolean blnIsContained = false ;
        
        
        if (pdblRectStartX >= pdblLineStartX 
                && pdblRectStartY <= pdblLineStartY
                && pdblRectEndX <= pdblLineEndX
                && pdblRectEndY >= pdblLineEndY) {
            blnIsContained = true ;
        } else if (pdblRectStartX >= pdblLineEndX 
                && pdblRectStartY <= pdblLineEndY
                && pdblRectEndX <= pdblLineStartX
                && pdblRectEndY >= pdblLineStartY) {
            blnIsContained = true ;
        } else if (pdblRectEndX >= pdblLineStartX
                && pdblRectEndY <= pdblLineStartY
                && pdblRectStartY >= pdblLineEndY
                && pdblRectStartX <= pdblLineEndX) {
            blnIsContained = true ;
        } else if (pdblRectEndX >= pdblLineEndX
                && pdblRectEndY <= pdblLineEndY
                && pdblRectStartX <= pdblLineStartX
                && pdblRectStartY >= pdblLineStartY) {
            blnIsContained = true ;
        }
        
        return blnIsContained;
    }
    
}
