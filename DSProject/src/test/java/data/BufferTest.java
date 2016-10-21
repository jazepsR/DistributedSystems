/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Euaggelos
 */
public class BufferTest {
    
    private Buffer b;
    private Tree tree1;
    private Tree tree2;
    private ChatDataUnit data1;
    private ChatDataUnit data2;
    
    public BufferTest() {
        b = new Buffer();
        tree1 = new Tree();
        tree2 = new Tree();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        System.out.println("Setting up...");
        tree1.addHost("127.0.0.1", 5);
        tree1.addHost("127.0.0.2", 9);
        tree2.addHost("127.0.1.5", 5);
        tree2.addHost("127.0.1.55", 10);
        data1 = new ChatDataUnit("127.0.0.0", MessageType.DISCOVER, tree1, "asdf");
        data2 = new ChatDataUnit("127.0.0.0", MessageType.DISCOVER, tree2, "asdf2");
        b.addMsg(data1);
        b.addMsg(data2);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetMsgs(){
        System.out.println(b.getMsgs());
    }
    
    @Test
    public void testCmpr(){
        System.out.println("Testing compare");
        b.cmpr();
        testGetMsgs();
    }
    
}
