/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import algorithms.BullyAlgo;
import data.DataUnit;
import data.MessageType;
import java.net.InetAddress;
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
    
    // TODO to be removed at some point
    private int send;
   

    public Node( int port, int send) {
        this.ipAddress = Parser.strToInet(Config.ipAddress);
        this.iAmLeader = false;
        this.electionInProgress = false;
        
        this.port = port;
        this.send = send;
    }

    @Override
    public void run(){
        new Thread(new Listen()).start();
        System.out.println("in thread");
        if (send == 1) {
            Broadcast b = new Broadcast(new DataUnit(this.ipAddress,MessageType.DISCOVER));
            b.run();
            WaitTimer timer= new WaitTimer(5);
            timer.run();
            if (Tree.getHigherIps(this.ipAddress).isEmpty()){
                System.out.println("imLeader");
                this.iAmLeader=true;
                BullyAlgo.BroadcastWin();
            }
            
            // test become a leader
            /*WaitTimer timer2= new WaitTimer(15);
            timer2.run();
            BullyAlgo.bullyThem();*/
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
           