/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author talat 
*/
public class IDGeneratorTest {
    
    public IDGeneratorTest() {
    }
    
    /**
     * Test of getNextAvailableID method, of class IDGenerator.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testGetNextAvailableID() {
        IDGenerator instance = new IDGenerator();
        int expResult = 0;
        int result = instance.getNextAvailableID();
        assertEquals(expResult, result);
    }

    /**
     * Test of addAvailableID method, of class IDGenerator.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testAddAvailableID() {
        int pintId = 10;
        IDGenerator instance = new IDGenerator();
        instance.addAvailableID(pintId);
        int result = instance.getNextAvailableID();
        int expResult = pintId ;
        assertEquals(expResult, result);
    }
    
}
