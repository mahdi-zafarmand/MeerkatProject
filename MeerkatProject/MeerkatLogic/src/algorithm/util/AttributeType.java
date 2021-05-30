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
public class AttributeType {

    /**
     *
     */
    public static enum AttType{

        /**
         *
         */
        STRING,

        /**
         *
         */
        INTEGER,

        /**
         *
         */
        DOUBLE
    }
    
    /**
     *
     * @param pstrAttValue
     * @return
     */
    public static AttType getType(String pstrAttValue) {
        try {
            Integer.parseInt(pstrAttValue);
            return AttType.INTEGER;
        } catch (NumberFormatException e1) {
            try{
                Double.parseDouble(pstrAttValue);
                return AttType.DOUBLE;
            } catch (NumberFormatException e2) {
                return AttType.STRING;
            }
        }
    }
}
