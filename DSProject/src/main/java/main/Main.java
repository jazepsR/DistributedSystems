/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package main;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Euaggelos
 */
public class Main {
    
    public static void main(String [] args) throws UnknownHostException, SocketException{
        
        getIpAddress();
        System.out.println("myIP0: "+Config.ipAddress);
        
        Thread thread1 = new Node(20015, 1);
        //Thread thread2 = new Node(155, 20000, 0);
        //Thread thread3 = new Node(155, 20030, 2);
        
        thread1.start();
        //thread2.start();
        //thread3.start();
        
    }
    
    
    public static void getIpAddress() {
        Enumeration<NetworkInterface> n = null;
        try {
            n = NetworkInterface.getNetworkInterfaces();
            for (; n.hasMoreElements();){
                NetworkInterface e = n.nextElement();
                Enumeration<InetAddress> a = e.getInetAddresses();
                for (; a.hasMoreElements();){
                    InetAddress addr = a.nextElement();
                    String ind = addr.getHostAddress();
                    if(ind.contains("192.168.") || ind.contains("10.") || ind.contains("172.16.") || ind.contains("172.17.") || ind.contains("172.18.") || ind.contains("172.19.") || ind.contains("172.20.") || ind.contains("172.21.") || ind.contains("172.22.") || ind.contains("172.23.") || ind.contains("172.24.") || ind.contains("172.25.") || ind.contains("172.26.") || ind.contains("172.27.") || ind.contains("172.28.") || ind.contains("172.29.") || ind.contains("172.30.") || ind.contains("172.31."))
                        Config.ipAddress=ind;
                }
            }
        } catch (SocketException ex) {
            
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}