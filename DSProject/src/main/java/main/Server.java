/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import data.DataTranslator;
import data.DataUnit;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angelo
 */
public class Server implements Runnable{
    
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
            echo("Server socket created. Waiting for incoming data...");

            while (true) {
                sock.receive(incoming);
                byte[] data = incoming.getData();
                
                DataUnit o = DataTranslator.bytesToObject(data);

                echo(o);
                
                //ENTER HERE THE ALGORITHM THAT FIGURES OUT WHAT TO DO WITH THE MESSAGE
            }
        } catch (IOException e) {
            System.err.println("IOException ..." + e);
        }
    }

    public static void echo(String msg) {
        System.out.println(msg);
    }
    
    public static void echo(Object msg) {
        System.out.println(msg);
    }
    
    
}
    

