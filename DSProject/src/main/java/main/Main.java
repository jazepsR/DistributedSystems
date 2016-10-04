/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Euaggelos
 */
public class Main {
    
    public static void main(String [] args){
        System.out.println("test");
        getIpAddress();
        Thread thread1 = new Node(20015, 1);
        //Thread thread2 = new Node(155, 20000, 0);
        //Thread thread3 = new Node(155, 20030, 2);
        
        thread1.start();
        //thread2.start();
        //thread3.start();
        
    }
    
    private static void getIpAddress(){
        String ip;
<<<<<<< HEAD
        try {
            ip = Inet4Address.getLocalHost().getHostAddress();
            Config.ipAddress = "192.168.0.103";
            //int o= 0;
=======

       // try {
            //ip = Inet4Address.getLocalHost().getHostAddress();
            Config.ipAddress = "192.168.1.102";

>>>>>>> parent of 58ff53f... testing
            //Config.ipAddress = "192.168.173.230";
        } catch (UnknownHostException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}