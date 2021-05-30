/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

/**
 *
 * @author aabnar
 */
public class CoordinateConversionTest {

    
    public static void main(String[] args) {
        double val = getDecimal ("5° 8' 49.29'' S");
        
        System.out.println("CoordinateConversionTest: " + val);
    }
    
    private static double getDecimal(String attributeValue) {
        StringBuilder degree = new StringBuilder();
        StringBuilder minute = new StringBuilder();
        StringBuilder second = new StringBuilder();
        
        int count = 0;
        boolean prevDigit = false;
        for (char c : attributeValue.toCharArray()) {
            if (Character.isDigit(c) || (count == 2 && c == '.')) {
                prevDigit = true;
                if (count == 0) {
                    degree.append(c);
                } else if (count ==1 ) {
                    minute.append(c);
                } else if(count ==2) {
                    second.append(c);
                }
            } else {
                if (prevDigit) {
                    count++;
                }
                prevDigit = false;
            }
        }
        
        double decimalVal = Double.parseDouble(degree.toString()) +
                Double.parseDouble(minute.toString())/60 +
                Double.parseDouble(second.toString())/3600;

        if (attributeValue.contains("W") || attributeValue.contains("S") || attributeValue.startsWith("-")) {
            decimalVal *= -1;
        }
        
        return decimalVal;
        
    }
}
