/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import data.DataUnit;
import data.MessageType;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author Angelo
 */
public abstract class Send {
    // TODO add comments
    protected static int port;
    protected static int counter = 0;
    protected static DatagramSocket socket;
    protected static byte[] data;
    protected static DatagramPacket dp;

    public Send() {
        this.port = Config.port;
    }

    public void run() {
        
    }

    public static void echo(String msg) {
        System.out.println(msg);
    }
    
    // TODO this has to be moved away from here once debugging has finished
    protected DataUnit broadcastMessage() {
        return new DataUnit("sampleAddressFROM CLIENT",  MessageType.DISCOVER, counter);

    }
    
    protected static void increaseCounter(){
        counter += 1;
    }

}