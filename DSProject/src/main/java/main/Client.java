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
    private final int port;
    private int counter = 0;
    private DatagramSocket sock;
    private byte[] data;
    private DatagramPacket dp;

    public Client() {
        this.port = Config.port;
    }

    public void run() {
        sock = null;

        try {
            sock = new DatagramSocket();
                
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
