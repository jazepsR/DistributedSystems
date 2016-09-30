/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.net.InetAddress;
import java.util.*;
import utils.Parser;

/**
 *
 * @author roberto
 */

public class Tree {
    
    private static String IPLeader;
    private static HashMap<String, Integer> hmap = new HashMap<String, Integer>();

    public Tree(){
        
    }

    /**
     * @return the IPLeader
     */
    public String getIPLeader() {
        return IPLeader;
    }

    /**

     */
    public static void setIPLeader(String IpLeader) {
        IPLeader = IpLeader;
    }

    /**
     * @return the hmap
     */
    public static HashMap<String, Integer> getHmap() {
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
    public static void addHost(String ip, int counter) {
        hmap.put(ip,counter);
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
    
    public static List<InetAddress> getHigherIps(String ip){
        List<InetAddress> higherIps = new ArrayList<InetAddress>();
        Long hostIp = Parser.parseIp(ip);
        Long tmpIp;
        for (String s : hmap.keySet()){
            tmpIp = Parser.parseIp(s);
            if (tmpIp > hostIp)
                higherIps.add(Parser.strToInet(s));
        }
        return higherIps;
    }
}
