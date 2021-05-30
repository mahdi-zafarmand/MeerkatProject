/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import algorithm.util.Numbers;

/**
 *
 * @author aabnar
 */
public class NumberRecignitionTest {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        boolean isNumber = false;
        
        String test = "1";        
        isNumber = Numbers.isNumber(test);
        System.out.println(test + " is Number : " + isNumber);
        
        test = "1234567890";
        isNumber = Numbers.isNumber(test);
        System.out.println(test + " is Number : " + isNumber);
        
        test = "123a453";
        isNumber = Numbers.isNumber(test);
        System.out.println(test + " is Number : " + isNumber);
        
        test = "a";
        isNumber = Numbers.isNumber(test);
        System.out.println(test + " is Number : " + isNumber);
        
        test = "afra";
        isNumber = Numbers.isNumber(test);
        System.out.println(test + " is Number : " + isNumber);
        
        test = "1234567890a";
        isNumber = Numbers.isNumber(test);
        System.out.println(test + " is Number : " + isNumber);
        
        test = "1234+9353";
        isNumber = Numbers.isNumber(test);
        System.out.println(test + " is Number : " + isNumber);
        
        test = "123$#65";
        isNumber = Numbers.isNumber(test);
        System.out.println(test + " is Number : " + isNumber);
        
        test = "12.345";
        isNumber = Numbers.isNumber(test);
        System.out.println(test + " is Number : " + isNumber);
        
        test = ".12345";
        isNumber = Numbers.isNumber(test);
        System.out.println(test + " is Number : " + isNumber);
        
        test = "1.245a";
        isNumber = Numbers.isNumber(test);
        System.out.println(test + " is Number : " + isNumber);
        
        test = "a.b.d";
        isNumber = Numbers.isNumber(test);
        System.out.println(test + " is Number : " + isNumber);
        
        test = "a.12345";
        isNumber = Numbers.isNumber(test);
        System.out.println(test + " is Number : " + isNumber);
        
        test = "-1000";
        isNumber = Numbers.isNumber(test);
        System.out.println(test + " is Number : " + isNumber);
        
        test = "-1.000923";
        isNumber = Numbers.isNumber(test);
        System.out.println(test + " is Number : " + isNumber);
    }
}
