/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.core;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test Class for TimeFrame Test
 * @author talat
 * @since 2018-02-01
 */
public class TimeFrameTest {
    
    public TimeFrameTest() {
    }
    
    /**
     * Test of toString method, of class TimeFrame.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testToString() {
        TimeFrame tf = new TimeFrame("Test_TimeFrame");
        String expResult = "Test_TimeFrame";
        String result = tf.toString();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getTimeFrameName method, of class TimeFrame.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testGetTimeFrameName() {
        TimeFrame tf = new TimeFrame("Test_TimeFrame");
        String expResult = "Test_TimeFrame";                
        String result = tf.getTimeFrameName();
        assertEquals(expResult, result);
    }
    
}
