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


/**
 *
 * @author Angelo
 */
public class Server implements Runnable{
    // TODO rename the class from Server to Listen
    // TODO add comments
    // TODO movee the values to a config file
    private int port = 7777;
    private int packetSize = 65536;
    
    public Server(){

    }
    
    public void run(){
        DatagramSocket sock = null;

        try {
            //1. creating a server socket, parameter is local port number
            sock = new DatagramSocket(this.port);                    

            //buffer to receive incoming data
            byte[] buffer = new byte[this.packetSize];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

            //2. Wait for an incoming data
            // TODO remove this once we are done with debugging
            echo("Server socket created. Waiting for incoming data...");

            while (true) {
                sock.receive(incoming);
                byte[] data = incoming.getData();
                
                DataUnit msg = DataTranslator.bytesToObject(data);

                echo(msg);
                
                // TODO add the message handler here.
            }
        } catch (IOException e) {
            System.err.println("IOException ..." + e);
        }
    }

    public static void echo(String msg) {
        System.out.println(msg);
    }
    
    public static void echo(DataUnit msg) {
        // TODO make it better
        System.out.println(msg);
        System.out.println(msg.getCounter());
    }
}
    

