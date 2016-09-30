/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.*;

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
     * @param ip to add host
     * @param counter
     */
    public void addHost(String ip, int counter) {
        this.hmap.put(ip,counter);
    }
    
    /**
     * @param ip to delete host
     */
    public void deleteHost(String ip) {
        this.hmap.remove(ip);
    }
    
    /**
     * @param ip to increase counter
     * @param newCounter
     */
    public void changeCounter(String ip, int newCounter) {
        this.hmap.put(ip, newCounter);
    }
    
    /**
     * @param ip to get counter
     * @return Counter
     */
    public int getCounter(String ip) {
        return hmap.get(ip);
    }

    public List<Integer> getVectorClock(){
        List<Integer> VClock = new ArrayList<Integer>();
        for (Integer value : hmap.values()) {
            VClock.add(value);
        }
        return VClock;
    }
    
    public List<Long> getHigherIps(String ip){
        List<Long> higherIps = new ArrayList<Long>();
        Long hostIp = parseIp(ip);
        Long tmpIp;
        for (String s : hmap.keySet()){
            tmpIp = parseIp(s);
            if (tmpIp > hostIp)
                higherIps.add(tmpIp);
        }
        return higherIps;
    }
    
    private Long parseIp(String s){
        return Long.parseLong(s.replace(".", ""));
    }
    
}
