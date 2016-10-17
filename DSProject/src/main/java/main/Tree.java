/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.*;
import utils.Parser;

/**
 *
 * @author roberto
 */

public class Tree implements Serializable, Comparable<Tree>{
    
    private InetAddress ipLeader;
    private HashMap<InetAddress, Integer> hmap = new HashMap<InetAddress, Integer>();
     private HashMap<InetAddress, Integer> reliability = new HashMap<InetAddress, Integer>();

    /**
     * @return the IPLeader
     */
    public InetAddress getIPLeader() {
        return ipLeader;
    }

    /**
     * @param ip
     */
    public void setIPLeader(InetAddress ip) {
        ipLeader = ip;
    }
    
    public void setIPLeader(String ip){
        setIPLeader(Parser.strToInet(ip));
    }
    
    public void increaseCounter(InetAddress ip){
        changeCounter(ip, getCounter(ip) + 1);
    }
    
    public void increaseCounter(String ip){
        changeCounter(ip, getCounter(ip) + 1);
    }

    /**
     * 
     * @return reliability
     */
    public HashMap<InetAddress, Integer> getHmap(){
        return reliability;
    }
    
    /**
     * @param ip to add host
     * @param counter
     */
    public void addHost(InetAddress ip, int counter) {
        hmap.put(ip,counter);
        reliability.put(ip,0);
    }
    
    public void addHost(String ip, int counter){
        addHost(Parser.strToInet(ip), counter);
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
    
    public void changeCounter(String ip, int newCounter){
        changeCounter(Parser.strToInet(ip), newCounter);
    }
    
    /**
     * @param ip to get counter
     * @return Counter
     */
    public int getCounter(InetAddress ip) {
        return hmap.get(ip);
    }
    
    public int getCounter(String ip){
        return getCounter(Parser.strToInet(ip));
    }

    public ArrayList<Integer> getVectorClock(){
        ArrayList<Integer> VClock = new ArrayList<Integer>();
        for (Integer value : hmap.values()) {
            VClock.add(value);
        }
        return VClock;
    }
    
    public ArrayList<InetAddress> getHigherIps(String ip){
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
    
    public ArrayList<InetAddress> getHigherIps(InetAddress ip){
        return getHigherIps(Parser.inetToStr(ip));
    }

    public int compareTo(Tree tree) {
        ArrayList<Integer> v1 = this.getVectorClock();
        ArrayList<Integer> v2 = tree.getVectorClock();
        boolean smallerFound = false, biggerFound = false;
        
        for(int i = 0; i < v1.size(); i++){
            if(v1.get(i) > v2.get(i))
                smallerFound = true;
            if(v1.get(i) < v2.get(i))
                biggerFound = true;
        }
        
        if(smallerFound == true & biggerFound == true)
            return 0;
        else if(smallerFound == true & biggerFound == false)
            return 1;
        else if(smallerFound == false & biggerFound == true)
            return -1;
        return 0;
    }
    
    @Override
    public String toString(){
        return getVectorClock().toString();
    }
    
    public static class Comparators{
        public static Comparator<Tree> cmp = new Comparator<Tree>(){
            public int compare(Tree o1, Tree o2) {
                return o1.compareTo(o2);
            }
        };
    }
}
