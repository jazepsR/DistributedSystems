/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import data.DataTranslator;
import data.DataUnit;
import data.MessageType;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Angelo
 */
public class Multicast extends Send {
    public Multicast() throws UnknownHostException {
        super();

        //IpList.add(InetAddress.getByName("192.168.173.1"));
        //IpList.add(InetAddress.getByName("192.168.173.107"));

    }

    public void SendMulticast(ArrayList<InetAddress> IpList,DataUnit message) {
        socket = null;
        try {
            socket = new DatagramSocket();
            // TODO move this line in a field and the ip address in the config
            data = DataTranslator.objectToBytes(message);
                for (InetAddress ip:
                     IpList) {
                    dp = new DatagramPacket(data, data.length, ip, port);
                    //send the packet
                    socket.send(dp);

                }


                //create the UDP packet


        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }
}
