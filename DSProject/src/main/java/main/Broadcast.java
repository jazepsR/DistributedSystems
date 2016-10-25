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

/**
 *
 * @author Angelo
 */
public class Broadcast extends Send{

    public Broadcast(){
        super();
    }
    public Broadcast(DataUnit data) {
        super();
        dataObject = data;
    }

    @Override
    public void run() {
        socket = null;

        try {
            socket = new DatagramSocket();
                
            String BroadcastAddr =  Config.ipAddress.substring(0,Config.ipAddress.lastIndexOf('.'))+".255";
            InetAddress host = InetAddress.getByName(BroadcastAddr);


            //increase counter for the package you want to send
            increaseCounter();
            //translate java object to bytes
            dataBytes = DataTranslator.objectToBytes(dataObject);

            //create the UDP packet
            udpPacket = new DatagramPacket(dataBytes, dataBytes.length,host, port);
            //send the packet

            socket.send(udpPacket);

        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }

    
}
