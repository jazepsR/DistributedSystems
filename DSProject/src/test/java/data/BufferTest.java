/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.net.InetAddress;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Euaggelos
 */
public class BufferTest {

    private final InBuffer buffer;
    private VectorChat vc;
    
    public BufferTest() {
        this.buffer = new InBuffer();
        this.vc = new VectorChat();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        vc.addHost("192.168.1.5", 5);
        vc.addHost("192.168.1.50", 80);
        ChatDataUnit chatData = new ChatDataUnit("192.168.1.5", MessageType.CHATMESSAGE, vc, "asdf");
        buffer.addMsg(chatData);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addMsg method, of class InBuffer.
     */
    @Test
    public void testAddMsg() {
        System.out.println("addMsg");
        ArrayList<ChatDataUnit> list = buffer.getMsgs();
        assertEquals(list.size(), 1);
    }

    /**
     * Test of contains method, of class InBuffer.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        assertEquals(buffer.containsSeq(1, "192.168.1.5"), true);
    }

    /**
     * Test of getOnSeq method, of class InBuffer.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int i = 0;
        InBuffer instance = new InBuffer();
        ChatDataUnit expResult = null;
        ChatDataUnit result = instance.getOnSeq(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class InBuffer.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        int i = 0;
        InBuffer instance = new InBuffer();
        instance.remove(i);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addMsgs method, of class InBuffer.
     */
    @Test
    public void testAddMsgs() {
        System.out.println("addMsgs");
        ArrayList<ChatDataUnit> lst = new ArrayList<ChatDataUnit>();
        ChatDataUnit chatData2 = new ChatDataUnit("192.168.1.5", MessageType.CHATMESSAGE, vc, "asdf2");
        ChatDataUnit chatData3 = new ChatDataUnit("192.168.1.50", MessageType.CHATMESSAGE, vc, "asdf3");
        lst.add(chatData3);
        lst.add(chatData2);
        buffer.addMsgs(lst);
        assertEquals(buffer.getMsgs().size(), 3);
    }

    /**
     * Test of getMsgs method, of class InBuffer.
     */
    @Test
    public void testGetMsgs() {
        System.out.println("getMsgs");
        ArrayList<ChatDataUnit> ch = buffer.getMsgs();
        for(ChatDataUnit i : ch){
            System.out.println(i);
        }
    }

    /**
     * Test of sortBuffer method, of class InBuffer.
     */
    @Test
    public void testSortBuffer() {
        System.out.println("sortBuffer");
        InBuffer instance = new InBuffer();
        instance.sortBuffer();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
