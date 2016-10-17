package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 29.09.2016.
 */
public class Listener extends Thread {
    DatagramSocket serverSocket = null;
    byte[] receiveData = new byte[1024];
    byte[] sendData = new byte[1024];
    List<IpData> IpList;
    int port;
    Listener(List<IpData> IpList,int port) {
        this.port = port;
        this.IpList = IpList;
        try {
            serverSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    public void run() {

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String sentence = new String(receivePacket.getData());
            System.out.println(Integer.toString(this.port)+" RECEIVED: " + sentence);
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            IpData recieverAddress = new IpData(IPAddress,port);
           /*if(!IpList.contains(recieverAddress)){
                IpList.add(recieverAddress);
            }*/
        }

    }
}
