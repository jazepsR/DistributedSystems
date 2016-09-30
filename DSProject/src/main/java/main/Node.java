/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

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
    private int macAddress;

    public Node(int macAddress, int port, int send) {
        this.iAmLeader = false;
        this.electionInProgress = false;
        this.macAddress = macAddress;
        this.port = port;
        this.send = send;
    }

    @Override
    public void run() {
        System.out.println("in thread");
        if (send == 1) {
            new Thread(new Listen()).start();
        } else {
            Send c = new Broadcast();
            c.run();
        }
    }
}
