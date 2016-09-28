/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import data.DataUnit;
import data.MessageType;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutput out = null;
                out = new ObjectOutputStream(bos);   
                out.writeObject(broadcastMessage());
                out.flush();
                byte[] yourBytes = bos.toByteArray();
                
                //byte[] b = s2.getBytes();

                DatagramPacket dp = new DatagramPacket(yourBytes, yourBytes.length, host, port);
                sock.send(dp);

                //now receive reply
                //buffer to receive incoming data
                byte[] buffer = new byte[65536];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                sock.receive(reply);

                byte[] data = reply.getData();
                s = new String(data, 0, reply.getLength());

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
