/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

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

            //communication loop
            while (true) {
                sock.receive(incoming);
                byte[] data = incoming.getData();
                
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                ObjectInput in = null;
                
                in = new ObjectInputStream(bis);
                Object o = in.readObject(); 
                
                
                
                
                //String s = new String(data, 0, incoming.getLength());

                //echo the details of incoming data - client ip : client port - client message
                //echo(incoming.getAddress().getHostAddress() + " : " + incoming.getPort() + " - " + s);
                echo(o);
                //s = "OK : " + s;
                //DatagramPacket dp = new DatagramPacket(s.getBytes(), s.getBytes().length, incoming.getAddress(), incoming.getPort());
                //sock.send(dp);
            }
        } catch (IOException e) {
            System.err.println("IOException " + e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //simple function to echo data to terminal
    public static void echo(String msg) {
        System.out.println(msg);
    }
    
    public static void echo(Object msg) {
        System.out.println("Server prints: this is the object");
        System.out.println(msg);
    }
    
    
}
    

