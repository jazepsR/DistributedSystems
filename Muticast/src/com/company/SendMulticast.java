package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;

/**
 * Created by User on 29.09.2016.
 */
public class SendMulticast extends Thread {
    List<IpData> IpList;
    BufferedReader inFromUser =
            new BufferedReader(new InputStreamReader(System.in));
    DatagramSocket clientSocket = null;
    InetAddress IPAddress = null;
    byte[] sendData = new byte[1024];
    byte[] receiveData = new byte[1024];
    String sentence = null;
    String name;
    SendMulticast(List<IpData> IpList,String name) throws UnknownHostException {
        this.name = name;
        this.IpList = IpList;
        IpList.add(new IpData(InetAddress.getByName("localhost"),8787));
        IpList.add(new IpData(InetAddress.getByName("localhost"),8888));
        IpList.add(new IpData(InetAddress.getByName("localhost"),9999));
        //IpList.add(new IpData(InetAddress.getByName("192.168.0.12"),9999));
        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            IPAddress = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        while(true) {


            try {
                sentence = inFromUser.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //sentence = name + " is sending";

            sendData = sentence.getBytes();
            for (IpData target:IpList
                 ) {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, target.ip, target.port);
                try {
                    clientSocket.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
