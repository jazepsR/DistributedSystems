/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author roberto
 */

public class Tree {
    
    private String IPLeader;
    private HashMap<String, Integer> hmap = new HashMap<String, Integer>();

    public Tree(){
        
    }

    /**
     * @return the IPLeader
     */
    public String getIPLeader() {
        return IPLeader;
    }

    /**
     * @param IPLeader the IPLeader to set
     */
    public void setIPLeader(String IPLeader) {
        this.IPLeader = IPLeader;
    }

    /**
     * @return the hmap
     */
    public HashMap<String, Integer> getHmap() {
        return hmap;
    }

    /**
     * @param hmap the hmap to set
     */
    public void setHmap(HashMap<String, Integer> hmap) {
        this.hmap = hmap;
    }
    
    /**
     * @param IP to add host
     */
    public void addHost(String IP, int counter) {
        this.hmap.put(IP,counter);
    }
    
    /**
     * @param IP to delete host
     */
    public void deleteHost(String IP) {
        this.hmap.remove(IP);
    }
    
    /**
     * @param IP to increse counter
     */
    public void changeCounter(String IP, int newCounter) {
        this.hmap.put(IP, newCounter);
    }
    
    /**
     * @param IP to get counter
     * @return Counter
     */
    public int getCounter(String IP) {
        return hmap.get(IP);
    }
    
}
