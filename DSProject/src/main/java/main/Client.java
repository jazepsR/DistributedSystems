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
    // TODO add comments
    // TODO move the fixed variables to a config file
    // TODO rename the class from client to send
    private int port = 7777;
    private int packetSize = 65536;
    private int counter = 0;

    public Client() {

    }

    public void run() {
        DatagramSocket sock = null;
        String s;

        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

        try {
            sock = new DatagramSocket();

            InetAddress host = InetAddress.getByName("192.168.173.255");

            while (true) {
                
                increaseCounter();
                
                byte[] data = DataTranslator.objectToBytes(broadcastMessage());

                DatagramPacket dp = new DatagramPacket(data, data.length, host, port);
                
                sock.send(dp);
            }
        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }

    public static void echo(String msg) {
        System.out.println(msg);
    }
    
    // TODO this has to be moved away from here once debugging has finished
    private DataUnit broadcastMessage() {
        return new DataUnit("sampleAddressFROM CLIENT", "sampleMac FROM CLIENT", MessageType.DISCOVER, this.counter);

    }
    
    private void increaseCounter(){
        this.counter += 1;
    }

}
