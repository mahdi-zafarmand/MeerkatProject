/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.serialize;

import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sankalp
 */
public class ProjectSerializationTest {
    
    public ProjectSerializationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Write method, of class ProjectSerialization.
     */
    @Test
    public void testWrite() {
        System.out.println("Write");
        String pstrProjectDirectory = "";
        String pstrProjectName = "";
        String pstrProjectExtension = "";
        int pintProjectID = 0;
        Set<Integer> psetGraphIDs = null;
        Set<Integer> psetTextualIDs = null;
        List<String> plststrInputRawFiles = null;
        String pstrProjectFileDelimiter = "";
        String pstrGraphListDelimiter = "";
        int expResult = 0;
        int result = ProjectSerialization.Write(pstrProjectDirectory, pstrProjectName, pstrProjectExtension, pintProjectID, psetGraphIDs, psetTextualIDs, plststrInputRawFiles, pstrProjectFileDelimiter, pstrGraphListDelimiter);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of WriteMPRJFileOnly method, of class ProjectSerialization.
     */
    @Test
    public void testWriteMPRJFileOnly() {
        System.out.println("WriteMPRJFileOnly");
        String pstrProjectDirectory = "";
        String pstrProjectName = "";
        String pstrProjectExtension = "";
        int pintProjectID = 0;
        Set<Integer> psetGraphIDs = null;
        Set<Integer> psetTextualIDs = null;
        List<String> plststrInputRawFiles = null;
        String pstrProjectFileDelimiter = "";
        String pstrGraphListDelimiter = "";
        int expResult = 0;
        int result = ProjectSerialization.WriteMPRJFileOnly(pstrProjectDirectory, pstrProjectName, pstrProjectExtension, pintProjectID, psetGraphIDs, psetTextualIDs, plststrInputRawFiles, pstrProjectFileDelimiter, pstrGraphListDelimiter);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
