package main;

import algorithms.BullyAlgo;
import data.Tree;
import java.net.InetAddress;
import utils.Parser;

/**
 * Created by User on 30.09.2016.
 */
public class WaitTimer implements Runnable{
    public int waitTime;
    private Thread t;
    private BullyAlgo bAlgo;
    private Node node;
    
    public WaitTimer(int waitForSeconds, BullyAlgo bAlgo){
        waitTime = waitForSeconds;
        this.bAlgo = bAlgo;
    }
    
    public WaitTimer(int waitForSeconds, BullyAlgo bAlgom, Node node){
        waitTime = waitForSeconds;
        this.bAlgo = bAlgo;
        this.node=node;
    }

    public void run() {
        
        try {
            t.sleep(waitTime*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (bAlgo.LostElection == false)
        {
            Tree tree=bAlgo.getTree();
         // tree.deleteHigherIps(Parser.strToInet(Config.ipAddress));
            System.out.println("Won election");
            node.displayEvent("Connected");
           bAlgo.BroadcastWin();
           
        } else{
             System.out.println("no leader 2");
        }
        
        
       bAlgo.LostElection = false;
                
    }
}
