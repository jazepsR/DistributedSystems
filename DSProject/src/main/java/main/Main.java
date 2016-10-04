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

       /* String TestIP = "234.345.567";
        TestIP= TestIP.replace(".","");
        Double ipVal = Double.parseDouble(TestIP);
        System.out.println("test");*/

        System.out.println("test");

        getIpAddress();
        System.out.println("myIP0"+Config.ipAddress);
        Thread thread1 = new Node(20015, 1);
        //Thread thread2 = new Node(155, 20000, 0);
        //Thread thread3 = new Node(155, 20030, 2);
        
        thread1.start();
        //thread2.start();
        //thread3.start();
        
    }
    
    private static void getIpAddress(){
        String ip;

       // try {
            //ip = Inet4Address.getLocalHost().getHostAddress();
            Config.ipAddress = "192.168.1.103";

            //Config.ipAddress = "192.168.173.230";
       /* } catch (UnknownHostException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
    }
    
}