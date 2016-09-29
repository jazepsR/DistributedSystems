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
            new Thread(new Server()).start();
        } else {
            Client c = new Client();
            c.run();
        }
    }



}
