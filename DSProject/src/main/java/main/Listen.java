/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import algorithms.BullyAlgo;
import data.ChatDataUnit;
import data.DataTranslator;
import data.DataUnit;
import data.MessageType;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
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
    private final Tree tree;
    private final HashMap<InetAddress, Integer> reliability;
    private final Multicast multicast;

    public Listen(Tree tree, BullyAlgo bAlgo, Node node){
        multicast = new Multicast();
        this.port = Config.port;
        this.packetSize = Config.packetSize;
        this.tree = tree;
        this.mh = new MessageHandler(tree, bAlgo, node);
        reliability = tree.getReliability();
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
                msg =  DataTranslator.bytesToObject(data);


                    //Relibalilty check
                    if(!reliability.containsKey(msg.getIpAddress()) && msg.getIpAddress().toString() != "/"+Config.ipAddress){
                        mh.switchMsg(msg);
                    }else {
                        int seqNR = msg.getSequenceNr();
                        int currentSeqNr = reliability.get(msg.getIpAddress());
                        if ( seqNR == currentSeqNr+ 1) {
                            reliability.put(msg.getIpAddress(),msg.getSequenceNr());
                            mh.switchMsg(msg);

                        } else {
                            for(int i = currentSeqNr;i<seqNR;i++) {
                                System.out.println(msg.toString());
                                ChatDataUnit msg = new ChatDataUnit(Config.ipAddress, MessageType.NEGATIVEACK, this.tree, Integer.toString(i));
                                ArrayList<InetAddress> target = new ArrayList<InetAddress>();
                                target.add(msg.getIpAddress());
                                multicast.SendMulticast(target, msg);
                            }
                        }
                    }



                // TODO remove after debugging
                //echo(msg);
                

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