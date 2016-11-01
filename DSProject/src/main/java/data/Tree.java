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
    
    public final HashMap<InetAddress, Integer> vectorHmap;
    
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
     * @param ip to delete host
     */
    public void deleteHigherIps(InetAddress ip) {
        System.out.println("cmdso");
        
        ArrayList<InetAddress> h= getHigherIps(ip);
        System.out.println("ip"+h.indexOf(0));
        for(int i=0;i<h.size();i++){
             System.out.println("ip"+h.indexOf(i));
            vectorHmap.remove( h.indexOf(i));
        }
       
    }

    public HashMap<InetAddress,Boolean> getTreeForAck(){
        HashMap<InetAddress,Boolean> hmapForAck = new HashMap<InetAddress, Boolean>();
        for(InetAddress ip: vectorHmap.keySet()){
            hmapForAck.put(ip,false);
        }
        return hmapForAck;

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
        Integer counter = vectorHmap.get(ip);
        if (counter == null)
            return 0;
        return counter;
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
        System.out.println("higherIps"+higherIps.toString());
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
            int v1val=0;
            int v2val=0;
            try{
                v1val = v1.get(i);
            }catch (Exception e){
                v1val = 0;
            }
            try{
                v2val = v2.get(i);
            }catch (Exception e){
                v2val = 0;
            }


            if(v1val> v2val)
                smallerFound = true;
            if(v1val < v2val)
                biggerFound = true;
        }
        
        if(smallerFound == true & biggerFound == true)
            return 0;
        else if(smallerFound == true & biggerFound == false)
            return -1;
        else if(smallerFound == false & biggerFound == true)
            return 1;
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
