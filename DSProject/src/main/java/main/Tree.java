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
    
    private static InetAddress ipLeader;
    private static HashMap<InetAddress, Integer> hmap = new HashMap<InetAddress, Integer>();

    /**
     * @return the IPLeader
     */
    public InetAddress getIPLeader() {
        return ipLeader;
    }

    /**
     * @param ip
     */
    public static void setIPLeader(InetAddress ip) {
        ipLeader = ip;
    }

    /**
     * @return the hmap
     */
    public static HashMap<InetAddress, Integer> getHmap() {
        return hmap;
    }
    
    /**
     * @param ip to add host
     * @param counter
     */
    public static void addHost(InetAddress ip, int counter) {
        hmap.put(ip,counter);
    }
    
    /**
     * @param ip to delete host
     */
    public void deleteHost(InetAddress ip) {
        hmap.remove(ip);
    }
    
    /**
     * @param ip
     * @param newCounter
     */
    public void changeCounter(InetAddress ip, int newCounter) {
        hmap.put(ip, newCounter);
    }
    
    /**
     * @param ip to get counter
     * @return Counter
     */
    public int getCounter(InetAddress ip) {
        return hmap.get(ip);
    }

    public List<Integer> getVectorClock(){
        List<Integer> VClock = new ArrayList<Integer>();
        for (Integer value : hmap.values()) {
            VClock.add(value);
        }
        return VClock;
    }
    
    public static ArrayList<InetAddress> getHigherIps(String ip){
        ArrayList<InetAddress> higherIps = new ArrayList<InetAddress>();
        Long hostIp = Parser.parseIp(ip);
        Long tmpIp;
        for (InetAddress s : hmap.keySet()){
            tmpIp = Parser.parseIp(s);
            if (tmpIp > hostIp)
                higherIps.add(s);
        }
        return higherIps;
    }
    
    public static ArrayList<InetAddress> getHigherIps(InetAddress ip){
        return getHigherIps(Parser.inetToStr(ip));
    }
}
