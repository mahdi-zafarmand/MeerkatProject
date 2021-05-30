/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package config;

import algorithm.util.Numbers;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aabnar
 */
public class FilteringOperators {

    /**
     *
     */
    public static final String LESSTHAN = "<";

    /**
     *
     */
    public static final String LEQ = "<=";

    /**
     *
     */
    public static final String GREATERTHAN = ">";

    /**
     *
     */
    public static final String GEQ = ">=";

    /**
     *
     */
    public static final String EQUAL = "=";

    /**
     *
     */
    public static final String NOTEQ = "!=";

    /**
     *
     */
    public static final String CONTAINS = "contains";

    /**
     *
     */
    public static final String STARTSWITH = "startswith";

    /**
     *
     */
    public static final String ENDSWITH = "endswith";

    /**
     *
     */
    public static final String EMPTY = "empty";

    /**
     *
     */
    public static final String NOTEMPTY = "notempty";
    
    
    /**
     * MethodName: Compare
     * Description: Compares two operands by a given operator. This order 
     * of operands is important. The way it is treated is as: Operand1 Operator Operand2.
     * @param pstrOperator
     *             String operator that should be one of the static Strings in FilteringOperators class. (not null)
     * @param pstrOperand1
     *              String the first operand value. (not null)
     * @param pstrOperand2
     *              String the second operand value. (not null)
     * @return 
     *      return true if the comparative operator is true.
     */
    public static boolean compare(String pstrOperator, 
            String pstrOperand1, 
            String pstrOperand2) {
        
        
        
        if (pstrOperand1 == null && !pstrOperator.equals(EMPTY)) {
            return false;
        }
        if (pstrOperand1 == null && pstrOperator.equals(EMPTY)) {
            return true;
        }
        if (pstrOperand2 == null) {
            pstrOperand2 = "";
        }
        
        pstrOperand1 = pstrOperand1.toLowerCase().trim();
        pstrOperand2 = pstrOperand2.toLowerCase().trim();
        
        boolean blnResult = false;
        
        NumberFormat nf = NumberFormat.getInstance();
        Number n1;
        Number n2;
        
        if(Numbers.isNumber(pstrOperand1) && Numbers.isNumber(pstrOperand2)) {
            try{
                n1 = nf.parse(pstrOperand1);
                n2 = nf.parse(pstrOperand2);
                switch(pstrOperator) {
                    case LESSTHAN:
                        blnResult = n1.doubleValue() < n2.doubleValue();
                        break;
                    case LEQ:
                        blnResult = n1.doubleValue() <= n2.doubleValue();
                        break;
                    case GREATERTHAN:
                        blnResult = n1.doubleValue() > n2.doubleValue();
                        break;
                    case GEQ:
                        blnResult = n1.doubleValue() >= n2.doubleValue();
                        break;
                    case EQUAL:
                        blnResult = (n1.doubleValue() == n2.doubleValue());
                        break;
                    case NOTEQ:
                        blnResult = (n1.doubleValue() != n2.doubleValue());
                        break;
                }
            } catch (ParseException ex) {
                Logger.getLogger(FilteringOperators.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        } else {
            // Attribute is not a number.
            switch(pstrOperator) {
                case EQUAL:
                    if (pstrOperand1.isEmpty()) {
                        blnResult = false;
                    } else {
                        blnResult = pstrOperand1.equals(pstrOperand2);
                    }
                    break;
                case NOTEQ:
                    blnResult = ! pstrOperand1.equals(pstrOperand2);
                    break;
                case EMPTY:
                    blnResult = pstrOperand1.isEmpty();
                    break;
                case NOTEMPTY:
                    blnResult = !pstrOperand1.isEmpty();
                    break;
                case CONTAINS:
                    if (pstrOperand2.isEmpty() || pstrOperand1.isEmpty()) {
                        blnResult = false;
                    } else {
                        blnResult = pstrOperand1.contains(pstrOperand2);
                    }
                    break;
                case STARTSWITH:
                    if (pstrOperand2.isEmpty()) {
                        blnResult=false;
                    } else {
                        blnResult = pstrOperand1.startsWith(pstrOperand2);
                    }
                    break;
                case ENDSWITH:
                    if (pstrOperand2.isEmpty()) {
                        blnResult = false;
                    } else {
                        blnResult = pstrOperand1.endsWith(pstrOperand2);
                    }
                    break;
            }
        }
        
        return blnResult;
    }
}
