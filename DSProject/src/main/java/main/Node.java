/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import data.Tree;
import algorithms.BullyAlgo;
import data.ChatDataUnit;
import data.DataUnit;
import data.MessageType;
import data.VectorChat;
import data.VectorClock;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

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
    }

    @Override
    public void run(){
        new Thread(new Listen(this.vectorClock, bullyAlgo,this)).start();
        new Thread(new InputHandler(this.vectorChat)).start();
        // TODO add bully in a loop 
        // TODO remove the tree from the DataUnit
        // TODO de-couple the wait timer
        // 
        System.out.println("in thread");
        if (send == 1) {
            Broadcast b = new Broadcast(new DataUnit(this.ipAddress, MessageType.DISCOVER, vectorClock));
            b.run();
            WaitTimer timer = new WaitTimer(5, bullyAlgo);
            timer.run();
            if (vectorClock.getHigherIps(this.ipAddress).isEmpty()){
                System.out.println("imLeader");
                this.iAmLeader=true;
                System.out.println("I am leader");
                this.bullyAlgo.BroadcastWin();
            }else{
                System.out.println("I am not the leader");
            }
            
            // test become a leader
            WaitTimer timer2= new WaitTimer(15,bullyAlgo);
            timer2.run();
            bullyAlgo.bullyThem();
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
           