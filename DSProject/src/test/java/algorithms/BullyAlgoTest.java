/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Angelo
 */
public class BullyAlgoTest {
    
    public BullyAlgoTest() {
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
     * Test of run method, of class BullyAlgo.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        int incomingID = 0;
        BullyAlgo.run(10, 100);

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareIds method, of class BullyAlgo.
     */
    @Test
    public void testCompareIds() {
        System.out.println("compareIds");
<<<<<<< HEAD
//        int incomingID = 0;
//        int localID = 12;
//        BullyAlgo instance = new BullyAlgo(12);
//        boolean expResult = true;
            boolean result = BullyAlgo.compareIds(10, 1);
            System.out.print(result);
//        
//        incomingID = 50;
//        expResult = false;
//        result = instance.compareIds(incomingID, localID);
//        assertEquals(expResult, result);

    }

    /**
     * Test of bullyThem method, of class BullyAlgo.
     */
    @Test
    public void testBullyThem() {
        System.out.println("bullyThem");
        BullyAlgo instance = null;
        instance.bullyThem();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
}
