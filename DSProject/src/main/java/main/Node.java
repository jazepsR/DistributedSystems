/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import data.MessageType;
import java.net.UnknownHostException;

/**
 *
 * @author Euaggelos
 */
public class Node extends Thread {
        
    private boolean iAmLeader;
    private boolean electionInProgress;
    private final int port;
    
    // TODO to be removed at some point
    private int send;
    

    public Node(int port, int send) {
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
             Send b = new Broadcast(new DataUnit(new Config, String macAddress, DISCOVER, 0));
             b.run();
            // timeout 5 sec
            new Thread(new Listen()).start();
            
            // listen all the msg 
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
