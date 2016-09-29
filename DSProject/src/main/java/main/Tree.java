/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;

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
    public void addHost(String IP) {
        this.hmap.put(IP,0);
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
    public void increaseCounter(String IP) {
        this.hmap.put(IP, this.hmap.get(IP) + 1);
    }
    
    /**
     * @param IP to get counter
     */
    public void getCounter(String IP) {
        this.hmap.get(IP);
    }
    
    
}
