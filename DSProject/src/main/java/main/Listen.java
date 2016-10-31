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
    private final Node node;

    public Listen(BullyAlgo bAlgo, VectorClock vClock, VectorChat vChat, Node node, InBuffer buff, ChatMessageLog chatLog) {
        multicast = new Multicast();
        this.port = Config.port;
        this.packetSize = Config.packetSize;
        this.vClock = vClock;
        this.vChat = vChat;
        this.node=node;
        this.mh = new MessageHandler(bAlgo, vClock, vChat, buff, chatLog);
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
            node.displayEvent(msg.getMsgType().toString()+" "+msg.getTree().getVector());
            mh.switchMsg(msg, node);
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
