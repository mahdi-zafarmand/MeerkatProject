/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author talat
 */
public class UtilitiesTest {
    
    public UtilitiesTest() {
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
     * Test of getFileNameWithExtension method, of class Utilities for a simple
     * path
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileNameWithExtension_1path() {
        String pstrFileName = "sample/myfile.meerkat";
        String expResult = "myfile.meerkat";
        String result = Utilities.getFileNameWithExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFileNameWithExtension method, of class Utilities with only 
     * file name
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileNameWithExtension_0path() {
        String pstrFileName = "myfile.meerkat";
        String expResult = "myfile.meerkat";
        String result = Utilities.getFileNameWithExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFileNameWithExtension method, of class Utilities for a deep
     * path
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileNameWithExtension_deepPath() {
        String pstrFileName = "sampleA/sampleB/sampleC/myfile.meerkat";
        String expResult = "myfile.meerkat";
        String result = Utilities.getFileNameWithExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFileNameWithExtension method, of class Utilities for a file 
     * without any extension
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileNameWithExtension_noext() {
        String pstrFileName = "sample/myfile";
        String expResult = "myfile";
        String result = Utilities.getFileNameWithExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFileNameWithExtension method, of class Utilities with a bad path
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileNameWithExtension_badPath() {
        String pstrFileName = "sample.test/myfile.meerkat";
        String expResult = "myfile.meerkat";
        String result = Utilities.getFileNameWithExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFileNameWithExtension method, of class Utilities with a 
     * file name with multiple period
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileNameWithExtension_badFileName() {
        String pstrFileName = "sample/myfile.meerkat.json";
        String expResult = "myfile.meerkat.json";
        String result = Utilities.getFileNameWithExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFileNameWithExtension method, of class Utilities with empty String
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileNameWithExtension_emptyFileName() {
        String pstrFileName = "";
        String expResult = "";
        String result = Utilities.getFileNameWithExtension(pstrFileName);
        assertEquals(expResult, result);
    }

    /**
     * Test of getFileExtension method, of class Utilities, for a normal path
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileExtension_1path() {
        String pstrFileName = "sample/myfile.meerkat";
        String expResult = ".meerkat";
        String result = Utilities.getFileExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFileExtension method, of class Utilities, with only file name
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileExtension_0path() {
        String pstrFileName = "myfile.meerkat";
        String expResult = ".meerkat";
        String result = Utilities.getFileExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFileExtension method, of class Utilities, with only deep path
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileExtension_deepPath() {
        String pstrFileName = "sampleA/sampleB/myfile.meerkat";
        String expResult = ".meerkat";
        String result = Utilities.getFileExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFileExtension method, of class Utilities, with no extension
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileExtension_noExt() {
        String pstrFileName = "myfile";
        String expResult = "";
        String result = Utilities.getFileExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFileExtension method, of class Utilities, with empty string
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileExtension_emptyPath() {
        String pstrFileName = "";
        String expResult = "";
        String result = Utilities.getFileExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFileExtension method, of class Utilities, with multiple periods 
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileExtension_multiExt() {
        String pstrFileName = "myfile.meerkat.json";
        String expResult = ".json";
        String result = Utilities.getFileExtension(pstrFileName);
        assertEquals(expResult, result);
    }

    /**
     * Test of getFileNameWithoutExtension method, of class Utilities, for a normal path
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileNameWithoutExtension_1path() {
        String pstrFileName = "sample/myfile.meerkat";
        String expResult = "myfile";
        String result = Utilities.getFileNameWithoutExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFileNameWithoutExtension method, of class Utilities, with only file name
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileNameWithoutExtension_0path() {
        String pstrFileName = "myfile.meerkat";
        String expResult = "myfile";
        String result = Utilities.getFileNameWithoutExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFileNameWithoutExtension method, of class Utilities, with only deep path
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileNameWithoutExtension_deepPath() {
        String pstrFileName = "sampleA/sampleB/myfile.meerkat";
        String expResult = "myfile";
        String result = Utilities.getFileNameWithoutExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFileNameWithoutExtension method, of class Utilities, with no extension
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileNameWithoutExtension_noExt() {
        String pstrFileName = "myfile";
        String expResult = "myfile";
        String result = Utilities.getFileNameWithoutExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFileNameWithoutExtension method, of class Utilities, with empty string
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileNameWithoutExtension_emptyPath() {
        String pstrFileName = "";
        String expResult = "";
        String result = Utilities.getFileNameWithoutExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFileNameWithoutExtension method, of class Utilities, with multiple periods 
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testGetFileNameWithoutExtension_multiExt() {
        String pstrFileName = "myfile.meerkat.json";
        String expResult = "myfile.meerkat";
        String result = Utilities.getFileNameWithoutExtension(pstrFileName);
        assertEquals(expResult, result);
    }
    

    /**
     * Test of createDirectory method, of class Utilities. create a non-existing
     * directory
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testCreateDirectory_notExisting() {
        String pstrPath = "sample";
        boolean replaceExisting = false;        
        boolean result = Utilities.createDirectory(pstrPath, replaceExisting);
        
        File f = new File(pstrPath);
        System.out.println(f.exists() + "\t" + f.isDirectory());
        boolean expResult = f.exists() && f.isDirectory();
        f.delete();
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test of createDirectory method, of class Utilities. When the parameter 
     * replaceExisting is sent as false
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testCreateDirectory_existing_noReplace() {
        
        
        try {
            String pstrPath = "sample";
            boolean replaceExisting = false;
            Path path = Paths.get(pstrPath);            
            File f = new File(pstrPath);
            f.mkdir();
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            long expResult = attr.creationTime().to(TimeUnit.MILLISECONDS);
            
            Thread.sleep(1000);
            Utilities.createDirectory(pstrPath, replaceExisting);
            
            attr = Files.readAttributes(path, BasicFileAttributes.class);
            long result = attr.creationTime().to(TimeUnit.MILLISECONDS);
            
            f.delete();
        
            assertEquals(expResult, result);
            
        } catch (IOException ex){
            System.out.println("EXCEPTION in UtilitiesTest");
        } catch (InterruptedException ex){
            System.out.println("EXCEPTION in UtilitiesTest");
        }  
    }
    
    
    /**
     * Test of createDirectory method, of class Utilities. When the parameter 
     * replaceExisting is sent as true
     * @author Talat
     * @since 2018-02-07
     */
    @Test
    public void testCreateDirectory_existing_replace() {
        
        
        try {
            String pstrPath = "sample";
            boolean replaceExisting = true;
            Path path = Paths.get(pstrPath);            
            File f = new File(pstrPath);
            f.mkdir();
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            long expResult = attr.creationTime().to(TimeUnit.MILLISECONDS);
            
            Thread.sleep(1000);
            Utilities.createDirectory(pstrPath, replaceExisting);
                        
            attr = Files.readAttributes(path, BasicFileAttributes.class);
            long result = attr.creationTime().to(TimeUnit.MILLISECONDS);
            
            f.delete();
        
            assertNotEquals(expResult, result);
            
        } catch (IOException ex){
            System.out.println("EXCEPTION in UtilitiesTest");
        } catch (InterruptedException ex){
            System.out.println("EXCEPTION in UtilitiesTest");
        }
    }
}
