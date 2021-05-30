/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.general;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Talat
 */
public class PairTest {
    
    /**
     * Test of set method, of class Pair.
     * @author Talat
     * @since 2018-02-08
     */
    @Test
    public void testSet_key() {
        String key = "Key";
        String value = "Value";
        Pair instance = new Pair(key, value);
        instance.set(key, value);
        String result = (String)instance.getKey();
        String expResult = key;
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test of set method, of class Pair.
     * @author Talat
     * @since 2018-02-08
     */
    @Test
    public void testSet_Value() {
        String key = "Key";
        String value = "Value";
        Pair instance = new Pair(key, value);
        instance.set(key, value);
        String result = (String)instance.getValue();
        String expResult = value;
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getKey method, of class Pair.
     * @author Talat
     * @since 2018-02-08
     */
    @Test
    public void testGetKey() {
        String key = "Key";
        String value = "Value";
        Pair instance = new Pair(key, value);
        instance.set(key, value);
        String result = (String)instance.getKey();
        String expResult = key;
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class Pair.
     * @author Talat
     * @since 2018-02-08
     */
    @Test
    public void testGetValue() {
        String key = "Key";
        String value = "Value";
        Pair instance = new Pair(key, value);
        instance.set(key, value);
        String result = (String)instance.getValue();
        String expResult = value;
        
        assertEquals(expResult, result);
    }
}
