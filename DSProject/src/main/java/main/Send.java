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
    protected final int port;
    protected int counter = 0;
    protected DatagramSocket socket;
    protected byte[] dataBytes;
    protected DatagramPacket udpPacket;
    protected DataUnit dataObject;


    public Send() {
        this.port = Config.port;
    }

    public void run() {
        
    }

    public static void echo(String msg) {
        System.out.println(msg);
    }
    
    // TODO this has to be moved away from here once debugging has finished
    protected DataUnit broadcastMessage(Tree tree) {
        return new DataUnit("sampleAddressFROM CLIENT",  MessageType.DISCOVER, tree,Config.SentMsg);
    }
    
    protected void increaseCounter(){
        counter += 1;
    }

}