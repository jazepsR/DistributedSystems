/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import data.Tree;
import algorithms.BullyAlgo;
import data.*;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angelo
 */
public class Listen implements Runnable {

    // TODO add comments
    private final int port, packetSize;
    private DatagramSocket sock;
    private byte[] buffer, data;
    private DatagramPacket incoming;
    private DataUnit msg;
    private final MessageHandler mh;
    private final Multicast multicast;
    private final VectorChat vChat;
    private final VectorClock vClock;

    public Listen(BullyAlgo bAlgo, VectorClock vClock, VectorChat vChat, Node node, Buffer buff) {
        multicast = new Multicast();
        this.port = Config.port;
        this.packetSize = Config.packetSize;
        this.vClock = vClock;
        this.vChat = vChat;
        this.mh = new MessageHandler(bAlgo, vClock, vChat, buff);
    }

    public void run() {
        sock = null;

        try {
            //creating a server socket, parameter is local port number
            sock = new DatagramSocket(this.port);
        } catch (SocketException ex) {
            Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
        }

        //buffer to receive incoming data
        buffer = new byte[this.packetSize];
        incoming = new DatagramPacket(buffer, buffer.length);

        // TODO remove this once we are done with debugging
        echo("Server socket created. Waiting for incoming data...");

        while (true) {
            try {
                //receive data
                sock.receive(incoming);
            } catch (IOException ex) {
                Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
            }
            data = incoming.getData();

            //turn bytes back to java object
            msg = DataTranslator.bytesToObject(data);
            //System.out.println(msg.toString());

            //Relibalilty check
            if (msg.getMsgType() != MessageType.CHATMESSAGE) {
                mh.switchMsg(msg);
            }

            //int seqNR = msg.getSequenceNr(); // WE DO NEED THE SEQ NUMBER
            //int currentSeqNr = reliability.get(msg.getIpAddress());
            //System.out.println("RECIEVED:" + seqNR + " CURRENT:" + currentSeqNr);
            //if (msg.getMsgType() == MessageType.CHATMESSAGE) {
                //if (seqNR == currentSeqNr + 1) {
                    //reliability.put(msg.getIpAddress(), msg.getSequenceNr());
                    //mh.switchMsg(msg);
                    //System.out.println("GOOD CHAT MSG");
                    //int BufferKey = seqNR + 1;
//                    while (MessageLogger.Buffer.containsKey(BufferKey)) {
//                        mh.switchMsg(MessageLogger.Buffer.get(BufferKey));
//                        MessageLogger.Buffer.remove(BufferKey);
//                        reliability.put(msg.getIpAddress(), msg.getSequenceNr());
//                        BufferKey++;
//                    }
//                } else {
//                    MessageLogger.Buffer.put(seqNR, msg);
//                    for (int i = currentSeqNr; i < seqNR; i++) {
//                        ChatDataUnit msg = new ChatDataUnit(Config.ipAddress, MessageType.NEGATIVEACK, this.tree, Integer.toString(i));
//                        multicast.SendMulticast(msg.getIpAddress(), msg);
//                    }

//                }
//            }

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
