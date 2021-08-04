/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communitymining.parameters;

/**
 *
 * @author mahdi
 */
public class SiwoPlusMiningParam {
    
    private static String strTitle ;
    
    public static String getTitle () {
        return strTitle;
    }
        
    public static void setParameters(String pstrTitle){
        strTitle = pstrTitle ;
    }
    
}
