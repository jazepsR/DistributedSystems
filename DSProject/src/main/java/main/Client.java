/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import data.DataTranslator;
import data.DataUnit;
import data.MessageType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Angelo
 */
public class Client {

    private int port = 7777;
    private int packetSize = 65536;

    public Client() {

    }

    public void run() {
        DatagramSocket sock = null;
        String s;

        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

        try {
            sock = new DatagramSocket();

            InetAddress host = InetAddress.getByName("localhost");

            while (true) {
                //take input and send the packet
                echo("Enter message to send : ");
                //s = (String) cin.readLine();
                
                byte[] data = DataTranslator.objectToBytes(new DataUnit("testing packet", "testing packet", MessageType.DISCOVER));
                
                //byte[] b = s2.getBytes();


                DatagramPacket dp = new DatagramPacket(data, data.length, host, port);
                sock.send(dp);

                //now receive reply
                //buffer to receive incoming data
                byte[] buffer = new byte[65536];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                sock.receive(reply);

                byte[] data2 = reply.getData();
                s = new String(data2, 0, reply.getLength());

                //echo the details of incoming data - client ip : client port - client message
                echo(reply.getAddress().getHostAddress() + " : " + reply.getPort() + " - " + s);
            }
        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }

    public static void echo(String msg) {
        System.out.println(msg);
    }
    
    private DataUnit broadcastMessage() {
        return new DataUnit("sampleAddressFROM CLIENT", "sampleMac FROM CLIENT", MessageType.DISCOVER);

    }

}
