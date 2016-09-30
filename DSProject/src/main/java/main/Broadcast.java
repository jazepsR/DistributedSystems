/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import data.DataTranslator;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Angelo
 */
public class Broadcast extends Send{
        
    public Broadcast(){
        super();
    }
    
    @Override
    public void run() {
        socket = null;

        try {
            socket = new DatagramSocket();
                
            // TODO move this line in a field and the ip address in the config
            InetAddress host = InetAddress.getByName("192.168.173.255");

            while (true) {
                //increase counter for the package you want to send
                increaseCounter();
                //translate java object to bytes
                data = DataTranslator.objectToBytes(broadcastMessage());
                //create the UDP packet
                dp = new DatagramPacket(data, data.length, host, port);
                //send the packet
                socket.send(dp);
            }
        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }
    
}
