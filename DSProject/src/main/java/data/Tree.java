/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import utils.Parser;

/**
 *
 * @author roberto
 */

public abstract class Tree implements Serializable, Comparable<Tree>{
    
    private final HashMap<InetAddress, Integer> vectorHmap;
    
    public Tree(){
        vectorHmap = new HashMap<InetAddress, Integer>(); 
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
        return vectorHmap;
    }
    
    /**
     * @param ip to add host
     * @param counter
     */
    public void addHost(InetAddress ip, int counter) {
        if(vectorHmap.get(ip) == null)
            vectorHmap.put(ip, 0);

        else
            vectorHmap.put(ip,counter);
    }
    
    public void addHost(String ip, int counter){
        addHost(Parser.strToInet(ip), counter);
    }
    
    /**
     * @param ip to delete host
     */
    public void deleteHost(InetAddress ip) {
        vectorHmap.remove(ip);
    }
    
    /**
     * @param ip
     * @param newCounter
     */
    public void changeCounter(InetAddress ip, int newCounter) {
        vectorHmap.put(ip, newCounter);
    }
    
    public void changeCounter(String ip, int newCounter){
        changeCounter(Parser.strToInet(ip), newCounter);
    }
    
    /**
     * @param ip to get counter
     * @return Counter
     */
    public int getCounter(InetAddress ip) {
        try {
            return vectorHmap.get(ip);
        }catch (Exception e){
            return 0;
        }
    }
    
    public int getCounter(String ip){
        return getCounter(Parser.strToInet(ip));
    }

    public ArrayList<Integer> getVector(){
        ArrayList<Integer> vector = new ArrayList<Integer>();
        for (Integer value : vectorHmap.values()) {
            vector.add(value);
        }
        return vector;
    }
    
    public ArrayList<InetAddress> getHigherIps(String ip){
        ArrayList<InetAddress> higherIps = new ArrayList<InetAddress>();
        Long hostIp = Parser.parseIp(ip);
        Long tmpIp;
        for (InetAddress s : vectorHmap.keySet()){
            tmpIp = Parser.parseIp(s);
            if (tmpIp > hostIp)
                higherIps.add(s);
        }
        return higherIps;
    }
    

    
    public ArrayList<InetAddress> getHigherIps(InetAddress ip){
        return getHigherIps(Parser.inetToStr(ip));
    }
    
    public ArrayList<InetAddress> getAllIps(){
        return new ArrayList<InetAddress>( vectorHmap.keySet());
    }

    public int compareTo(Tree tree) {
        ArrayList<Integer> v1 = this.getVector();
        ArrayList<Integer> v2 = tree.getVector();
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
        return getVector().toString();
    }
    
    public static class Comparators{
        public static Comparator<Tree> cmp = new Comparator<Tree>(){
            public int compare(Tree o1, Tree o2) {
                return o1.compareTo(o2);
            }
        };
    }
}
