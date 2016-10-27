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
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Euaggelos
 */
public class Main {
    
    public static void main(String [] args){
        
        getIpAddress();
        System.out.println("myIP0: "+Config.ipAddress);
        
        
        
        Node node = new Node(20015, 1);
        //Thread thread2 = new Node(155, 20000, 0);
        //Thread thread3 = new Node(155, 20030, 2);
        node.startGui();
        node.start();
        //thread2.start();
        //thread3.start();
        
        
        
    }
    
    
    public static void getIpAddress() {
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
                        .getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface ni = (NetworkInterface) networkInterfaces
                            .nextElement();
                    Enumeration<InetAddress> nias = ni.getInetAddresses();
                    while(nias.hasMoreElements()) {
                        InetAddress ia= (InetAddress) nias.nextElement();
                        if (!ia.isLinkLocalAddress() 
                         && !ia.isLoopbackAddress()
                         && ia instanceof Inet4Address) {
                            Config.ipAddress=ia.getHostAddress();
                        }
                    }
                }
            } catch (SocketException e) {
                  Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, e);
            }
            
        }
    
}