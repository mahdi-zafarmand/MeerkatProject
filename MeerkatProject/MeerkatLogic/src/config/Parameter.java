/*Licenced at Meerkat@ualberta*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package config;

/**
 * ClassName: Parameter
 * Description: Contains information for parameters of an algorithm 
 * including parameter name (not null) and possible constant values 
 * in some cases.
 * 
 * Version: 1.0
 * Author: Afra
 * 
 * @author aabnar
 */
public class Parameter {

    /**
     * Not null. Name of the Parameter.
     */
    public String key;
    
    /**
     * Possible constant values for the parameter.
     */
    public String[] values;
    
    
    //TOOD: Type of the parameter might be useful to add as well.
    
    /**
     * Constructor
     * Version: 1.0
     * Author: Afra
     * 
     * @param k
     *      String : Name of the parameter.
     * @param values 
     *      String[] : Possible constant values of the parameter.
     */
    public Parameter(String k, String[] values) {
        this.key = k;
        this.values = values;
    }
}
