/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.general;

import datastructure.core.graph.classinterface.IVertex;
import datastructure.core.graph.impl.Vertex;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author talat
 */
public class DynamicArrayTest {
    
    /**
     * Test of increaseSize method, of class DynamicArray.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testIncreaseSize_int() {
        int initialSize = 300;
        int increaseBy = 400;
        DynamicArray instance = new DynamicArray(initialSize);
        instance.increaseSize(increaseBy);
        
        int result = instance.getMaxIndex() + 1;
        int expResult = initialSize + increaseBy;
        assertEquals(expResult, result);
    }

    /**
     * Test of increaseSize method, of class DynamicArray.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testIncreaseSize_0args() {        
        int initialSize = 300;
        DynamicArray instance = new DynamicArray(initialSize);
        instance.increaseSize();
        
        int result = instance.getMaxIndex() + 1;
        int expResult = DynamicArray.DEFAULT_SIZE + initialSize;
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class DynamicArray.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testAdd() {
        int initialSize = 300;
        DynamicArray instance = new DynamicArray(initialSize);
        
        int eid = 1;
        IVertex vtxCurrent = new Vertex();        
        instance.add(eid, vtxCurrent);
        
        int expResult = 1;
        int result = instance.size();
        
        assertEquals(expResult, result);
    }
    
    
    /**
     * Test of add method for 2 objects, of class DynamicArray.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testAdd_2Objects() {
        int initialSize = 300;
        DynamicArray instance = new DynamicArray(initialSize);
        
        int eid = 1;
        IVertex vtxCurrent = new Vertex();        
        instance.add(eid, vtxCurrent);
        
        eid = 2;
        vtxCurrent = new Vertex();        
        instance.add(eid, vtxCurrent);
        
        int expResult = 2;
        int result = instance.size();
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test of add method for 2 objects, of class DynamicArray.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testAdd_2Objects_sameID() {
        int initialSize = 300;
        DynamicArray instance = new DynamicArray(initialSize);
        
        int eid = 1;
        IVertex vtxCurrent = new Vertex();        
        instance.add(eid, vtxCurrent);
        
        eid = 1;
        vtxCurrent = new Vertex();        
        instance.add(eid, vtxCurrent);
        
        int expResult = 1;
        int result = instance.size();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of remove method, of class DynamicArray.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testRemove_int() {
        int initialSize = 300;        
        DynamicArray instance = new DynamicArray(initialSize);
        
        int eid = 1;        
        IVertex vtxCurrent = new Vertex();
        instance.add(eid, vtxCurrent);
        
        eid = 2;        
        vtxCurrent = new Vertex();
        instance.add(eid, vtxCurrent);
        
        instance.remove(1);
        
        int expResult = 1;
        int result = instance.size();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class DynamicArray.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testGet() {
        int initialSize = 300;        
        DynamicArray instance = new DynamicArray(initialSize);
        
        int eid = 1;        
        IVertex vtx1 = new Vertex();
        instance.add(eid, vtx1);
        
        IVertex result = (IVertex)instance.get(eid);
                
        assertEquals(vtx1, result);
    }

    /**
     * Test of getMaxIndex method, of class DynamicArray.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testGetMaxIndex_Empty() {
        int initialSize = 300;        
        DynamicArray instance = new DynamicArray(initialSize);
        
        int result = instance.getMaxIndex();
        int expResult = initialSize - 1;
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getMaxIndex method, of class DynamicArray.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testGetMaxIndex_Exists() {
        int initialSize = 300;
        DynamicArray instance = new DynamicArray(initialSize);
        
        int increaseBy = 1000;
        instance.increaseSize(increaseBy);
        
        int result = instance.getMaxIndex();
        int expResult = initialSize + increaseBy - 1;
        
        assertEquals(expResult, result);
    }


    /**
     * Test of size method, of class DynamicArray.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testSize() {
        int initialSize = 300;
        DynamicArray instance = new DynamicArray(initialSize);
        
        int eid = 1;
        IVertex vtxCurrent = new Vertex();        
        instance.add(eid, vtxCurrent);
        
        int expResult = 1;
        int result = instance.size();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of clear method, of class DynamicArray.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testClear() {
        int initialSize = 300;
        DynamicArray instance = new DynamicArray(initialSize);
        
        int eid = 1;
        IVertex vtxCurrent = new Vertex();        
        instance.add(eid, vtxCurrent);
        
        instance.clear();
        
        int expResult = 0;
        int result = instance.size();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getIds method, of class DynamicArray.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testGetIds() {
        int initialSize = 300;        
        DynamicArray instance = new DynamicArray(initialSize);
        
        int eid = 1;        
        IVertex vtxCurrent = new Vertex();
        instance.add(eid, vtxCurrent);
        
        eid = 2;        
        vtxCurrent = new Vertex();
        instance.add(eid, vtxCurrent);
        
        List<Integer> result = instance.getIds();
        List<Integer> expResult = new ArrayList<>();
        expResult.add(1);
        expResult.add(2);
                
        Assert.assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of getObjects method, of class DynamicArray.
     * @author Talat
     * @since 2018-02-01
     */
    @Test
    public void testGetObjects() {
        int initialSize = 300;        
        DynamicArray instance = new DynamicArray(initialSize);
        
        int eid1 = 1;        
        IVertex vtxCurrent1 = new Vertex();
        instance.add(eid1, vtxCurrent1);
        
        int eid2 = 2;        
        IVertex vtxCurrent2 = new Vertex();
        instance.add(eid2, vtxCurrent2);
        
        List<IVertex> result = (List<IVertex>)instance.getObjects();
        List<IVertex> expResult = new ArrayList<>();
        expResult.add(vtxCurrent1);
        expResult.add(vtxCurrent2);
                
        Assert.assertArrayEquals(expResult.toArray(), result.toArray());
    }    
}