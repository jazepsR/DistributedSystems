/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import data.DataTranslator;
import data.DataUnit;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Angelo
 */
public class Broadcast extends Send{
    private DataUnit data;    
    
    public Broadcast(){
        super();
    }
     public Broadcast(DataUnit data) throws UnknownHostException {
        super();
        this.data= data;
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
                byte[] dataBytes = DataTranslator.objectToBytes(this.data);
                //create the UDP packet
                dp = new DatagramPacket(dataBytes, dataBytes.length, host, port);
                //send the packet
                socket.send(dp);
            }
        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }
    
}
