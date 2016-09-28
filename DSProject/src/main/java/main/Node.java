/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import data.DataUnit;
import data.MessageType;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        System.out.println(macAddress);
        if (send == 1) {
            new Thread(new Server()).start();
        } else {
            Client c = new Client();
            c.run();
        }
    }



}
