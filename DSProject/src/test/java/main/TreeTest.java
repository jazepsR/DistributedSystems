/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import data.Tree;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.Parser;

/**
 *
 * @author Euaggelos
 */
public class TreeTest {
    
    private Tree tree, tree2, tree3;
    private InetAddress ip1, ip2;
    
    public TreeTest() {
        tree = new Tree();
        tree2 = new Tree();
        tree3 = new Tree();
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
        ip1 = Parser.strToInet("192.168.175.5");
        ip2 = Parser.strToInet("192.168.175.10");
        tree.addHost(ip1, 1);
        tree.addHost(ip2, 2);
        tree.addHost("192.168.175.15", 3);
        tree.addHost("192.168.175.20", 4);
        tree.addHost("192.168.175.30", 5);
        tree.addHost("192.168.175.30", 100);
        
        tree2.addHost("192.168.175.5", 1);
        tree2.addHost("192.168.175.10", 2);
        tree2.addHost("192.168.175.15", 3);
        tree2.addHost("192.168.175.20", 4);
        tree2.addHost("192.168.175.30", 5);
        tree2.addHost("192.168.175.30", 6);
        
        tree3.addHost("192.168.175.5", 1);
        tree3.addHost("192.168.175.10", 0);
        tree3.addHost("192.168.175.15", 0);
        tree3.addHost("192.168.175.20", 0);
        tree3.addHost("192.168.175.30", 0);
        tree3.addHost("192.168.175.30", 0);
        
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test if we can set a leader.
     */
    @Test
    public void testLeader1() {
        System.out.println("testing leader 1...");
        tree.setIPLeader(ip2);
        assertEquals(ip2, tree.getIPLeader());    
    }
    
    /**
     * Test if we can alter the leader.
     */
    @Test
    public void testLeader2() {
        System.out.println("testing leader 2...");
        tree.setIPLeader(ip1);
        assertEquals(ip1, tree.getIPLeader());    
    }
    
    /**
     * 
     */
    @Test
    public void testCounter1(){
        System.out.println("testing counter 1...");
        System.out.println(tree.getCounter(ip1));
        assertEquals(1, tree.getCounter(ip1));
    }
    
    /**
     * 
     */
    @Test
    public void testCounter2(){
        System.out.println("testing counter 2...");
        tree.changeCounter(ip1, 5);
        assertEquals(5, tree.getCounter(ip1));
        assertNotEquals(50, tree.getCounter(ip1));
    }
    
    /**
     * 
     */
    @Test
    public void testVectorClock(){
        System.out.println("testing vector clock...");
        ArrayList<Integer> vClock = tree.getVector();
        for(Integer i : vClock) {
            System.out.println(i);
        }
    }
    
    /**
     * 
     */
    @Test
    public void testGetHigherIps(){
        System.out.println("testing higher ips...");
        System.out.println(tree.getHigherIps(ip2));
    }
    
    @Test
    public void compareTrees(){
        System.out.println("testing compare trees...");
        ArrayList<Tree> treeList = new ArrayList<Tree>();
        treeList.add(tree);
        treeList.add(tree2);
        treeList.add(tree3);
        System.out.println(tree.getVector());
        System.out.println(tree2.getVector());
        System.out.println(treeList);
        Collections.sort(treeList, Tree.Comparators.cmp);
        System.out.println(treeList);
    }
    
}
