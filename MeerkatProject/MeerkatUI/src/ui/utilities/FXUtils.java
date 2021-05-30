/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.utilities;

import javafx.scene.paint.Color;

/**
 *  Class Name      : FXUtils
 *  Created Date    : 2016-02-01
 *  Description     : Class containing all the FX  Utilities
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class FXUtils
{
 
    /**
     *  Method Name     : toRGBCode()
     *  Created Date    : 2016-02-01
     *  Description     : Converts the scne Color to Hexadecimal value
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pColor : Color
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static String ColorToHex(Color pColor)
    {
        return String.format( "#%02X%02X%02X",
            (int)( pColor.getRed() * 255 ),
            (int)( pColor.getGreen() * 255 ),
            (int)( pColor.getBlue() * 255 ) );
    }
    
    
    /**
     *  Method Name     : FormatDouble()
     *  Created Date    : 2016-10-07
     *  Description     : Converts the double to an integer
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pdblValue : double
     *  @return String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static String FormatDouble(double pdblValue) {
        if(pdblValue == (long) pdblValue) {
            return String.format("%d",(long)pdblValue);
        } else {
            return String.format("%.2f",pdblValue);
        }
    }
            
            
}