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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angelo
 */
public class Listen implements Runnable{
    // TODO add comments
    private final int port, packetSize;
    private DatagramSocket sock;
    private byte[] buffer, data;
    private DatagramPacket incoming;
    private DataUnit msg;
    private final MessageHandler mh;
    
    public Listen(){
        this.port = Config.port;
        this.packetSize = Config.packetSize;
        this.mh = new MessageHandler();
        
    }
    
    public void run(){
        sock = null;

        try {
            //creating a server socket, parameter is local port number
            sock = new DatagramSocket(this.port);                    

            //buffer to receive incoming data
            buffer = new byte[this.packetSize];
            incoming = new DatagramPacket(buffer, buffer.length);


            // TODO remove this once we are done with debugging
            echo("Server socket created. Waiting for incoming data...");

            while (true) {
                //receive data
                sock.receive(incoming);
                data = incoming.getData();
                
                //turn bytes back to java object
                msg = DataTranslator.bytesToObject(data);
                    
                // TODO remove after debugging
                echo(msg);
                
                mh.switchMsg(msg);
            }
        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }

    public static void echo(String msg) {
        System.out.println(msg);
    }
    
    public static void echo(DataUnit msg) {
        // TODO make it better
        System.out.println(msg);
    }
}