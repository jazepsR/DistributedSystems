/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;


import java.net.Socket;


/**
 *
 * @author Euaggelos
 */
public class Node extends Thread {
    
    // TODO add boolean for leader 2) add boolean for election 3) add boolean into the dataunit
    private int macAddress;
    private Socket socket;
    private final int port;
    private int send;

    public Node(int macAddress, int port, int send) {
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
            Send c = new Send();
            c.run();
        }
    }



}
