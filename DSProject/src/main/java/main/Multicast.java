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
import java.util.ArrayList;

import data.MessageType;
import utils.Parser;

/**
 *
 * @author Angelo
 */
public class Multicast extends Send {

    public Multicast() {
        super();
        socket = null;
        //IpList.add(InetAddress.getByName("192.168.173.1"));
        //IpList.add(InetAddress.getByName("192.168.173.107"));

    }

    public void SendMulticast(InetAddress ip, DataUnit message) {
        try {
            socket = new DatagramSocket();
            // TODO move this line in a field and the ip address in the config
            dataBytes = DataTranslator.objectToBytes(message);
            udpPacket = new DatagramPacket(dataBytes, dataBytes.length, ip, port);
            //send the packet
            socket.send(udpPacket);
        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
        if(message.getMsgType() == MessageType.CHATMESSAGE){Config.msgCounter++;}
    }

    public void SendMulticast(ArrayList<InetAddress> ipList, DataUnit message) {
        for (InetAddress ip
                : ipList) {
            SendMulticast(ip, message);
        }
        // MOVED THIS TO THE MAIN SENDMULTICAST ABOVE
        //if(message.getMsgType() == MessageType.CHATMESSAGE){Config.msgCounter++;}
    }
    
    public void SendMulticast(String ipAddress, DataUnit message){
        SendMulticast(Parser.strToInet(ipAddress), message);
        // MOVED THIS TO THE MAIN SENDMULTICAST ABOVE
        //if(message.getMsgType() == MessageType.CHATMESSAGE){Config.msgCounter++;}
    }
}
