/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import algorithms.BullyAlgo;
import data.InBuffer;
import data.ChatMessageLog;
import data.ChatDataUnit;
import data.DataUnit;
import data.MessageType;
import data.VectorChat;
import data.VectorClock;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private SimpleDateFormat sdf;
     private ClientGUI cg;
    private final InetAddress ipAddress;
    private final BullyAlgo bullyAlgo;
    public List<ChatDataUnit> messageLog;
    // TODO to be removed at some point
    private int send;
    private VectorClock vectorClock;
    private final VectorChat vectorChat;
    private InBuffer buffer;
    public ChatMessageLog chatLog;
    private ClientGUI gui;
   
    public Node( int port, int send, ClientGUI cg) {
        this(port, send);
        this.cg =cg;
        
    }
    
    public Node( int port, int send) {
        sdf = new SimpleDateFormat("HH:mm:ss"); 
        messageLog = new ArrayList<ChatDataUnit>();
        this.ipAddress = Parser.strToInet(Config.ipAddress);
        this.iAmLeader = false;
        this.electionInProgress = false;
        this.vectorClock = new VectorClock();
        this.vectorChat = new VectorChat();
        this.bullyAlgo = new BullyAlgo(this.vectorChat);
        this.port = port;
        this.send = send;
        this.buffer = new InBuffer();
        this.chatLog = new ChatMessageLog();
    }
    
    public void startGui(){
        this.gui=new ClientGUI(this.vectorChat, vectorClock, chatLog);
    }

    @Override
    public void run(){
        new Thread(new Listen(bullyAlgo, vectorChat, vectorClock, this, buffer, chatLog)).start();
        //new Thread(new InputHandler(this.vectorChat,this.vectorClock)).start();
        //new ClientGUI();
        System.out.println("Connected");
//        displayEvent("Connected");
//        displayChat("test");
        if (send == 1) {
            Broadcast b = new Broadcast(new DataUnit(this.ipAddress, MessageType.DISCOVER, vectorChat));
            b.run();

           try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
                }
            if (vectorChat.getHigherIps(this.ipAddress).isEmpty()){
                //displayEvent("imLeader");
                System.out.println("imLeader");
                this.iAmLeader=true;
                this.bullyAlgo.BroadcastWin();
            }else{
                 //displayEvent("I am not the leader");
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
    
public void displayEvent(String msg) {
            String time = sdf.format(new Date()) + " " + msg;
            if(gui == null)
                System.out.println(time);
            else
                gui.appendEvent(time + "\n");
        }
    
     public void displayChat(String msg) {
            String time = sdf.format(new Date()) + " " + msg;
            if(gui == null)
                System.out.println(time);
            else
                gui.appendChat(time + "\n");
        }
     
     public void addAllMsg(ArrayList<ChatDataUnit> array) {
         
         gui.clearChat();
         for (int i=0; i<array.size();i++)
                        if(gui != null){
                            gui.appendChat( array.get(i).getMsg() + "\n");
                        }
        }
}
           