/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import data.Tree;
import algorithms.BullyAlgo;
import data.Buffer;
import data.ChatDataUnit;
import data.DataUnit;
import data.MessageType;
import data.VectorChat;
import data.VectorClock;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.Parser;


/**
 *
 * @author Euaggelos
 */
public class Node extends Thread {
        
    private boolean iAmLeader;
    private boolean electionInProgress;
    private final int port;
    private final InetAddress ipAddress;
    private final BullyAlgo bullyAlgo;
    public List<ChatDataUnit> messageLog;
    // TODO to be removed at some point
    private int send;
    private final VectorClock vectorClock;
    private final VectorChat vectorChat;
    private Buffer buffer;
   

    public Node( int port, int send) {
        messageLog = new ArrayList<ChatDataUnit>();
        this.ipAddress = Parser.strToInet(Config.ipAddress);
        this.iAmLeader = false;
        this.electionInProgress = false;
        this.vectorClock = new VectorClock();
        this.vectorChat = new VectorChat();
        this.bullyAlgo = new BullyAlgo(this.vectorClock);
        this.port = port;
        this.send = send;
        this.buffer = new Buffer();
        
    }

    @Override
    public void run(){
        new Thread(new Listen(bullyAlgo, vectorClock, vectorChat, this, buffer)).start();
        new Thread(new InputHandler(this.vectorChat)).start();
        // TODO add bully in a loop 
        // TODO remove the tree from the DataUnit
        // TODO de-couple the wait timer
        // 
        System.out.println("in thread");
        if (send == 1) {
            Broadcast b = new Broadcast(new DataUnit(this.ipAddress, MessageType.DISCOVER, vectorClock));
            b.run();
            
           /* try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
            }*/
           try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
                }
            if (vectorClock.getHigherIps(this.ipAddress).isEmpty()){
                System.out.println("imLeader");
                this.iAmLeader=true;
                this.bullyAlgo.BroadcastWin();
            }else{
                System.out.println("I am not the leader");
            }
            
            // test become a leader
            while(true){
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
                }
                bullyAlgo.bullyThem();
            }
        }
        else {
            if(send==2)
            {
            Send c = null;
            c = new Multicast();
            c.run();
        }
        else
            {
                Send c = new Broadcast();
                c.run();
            }
        }
    }
}
           