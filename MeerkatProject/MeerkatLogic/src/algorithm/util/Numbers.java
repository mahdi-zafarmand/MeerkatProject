/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm.util;

/**
 *
 * @author aabnar
 */
public class Numbers {

    /**
     *
     * @param pstrValue
     * @return
     */
    public static boolean isNumber(String pstrValue) {
        boolean result = false;
        
        result = pstrValue.matches("[-]?[0-9]*[.]?[0-9]+");
        
        return result;
    }
}
