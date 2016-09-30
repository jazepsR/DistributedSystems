/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import algorithms.BullyAlgo;
import data.DataUnit;
import data.MessageType;
import static data.MessageType.DISCOVER;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Euaggelos
 */
public class Node extends Thread {
        
    private boolean iAmLeader;
    private boolean electionInProgress;
    private final int port;
    private final String ipAddress;
    
    // TODO to be removed at some point
    private int send;
   

    public Node( int port, int send) {
        this.ipAddress = Config.ipAddress;
        this.iAmLeader = false;
        this.electionInProgress = false;
        
        this.port = port;
        this.send = send;
    }

    @Override
    public void run()
    {
        System.out.println("in thread");
        if (send == 1) {
            Send b=null;
            try {
                b = new Broadcast(new DataUnit(this.ipAddress,MessageType.DISCOVER, 0));
            } catch (UnknownHostException ex) {
                Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            b.run();
            WaitTimer timer= new WaitTimer(5);
            timer.run();
            
            new Thread(new Listen()).start();
            
            if (Tree.getHigherIps(this.ipAddress).isEmpty()){
                this.iAmLeader=true;
                BullyAlgo.BroadcastWin();
            }
            
           
        }
        else {
            if(send==2)
            {
            Send c = null;
            try {
                c = new Multicast();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
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
           