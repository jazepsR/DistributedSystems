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
    
    public WaitTimer(int waitForSeconds, BullyAlgo bAlgo){
        waitTime = waitForSeconds;
        this.bAlgo = bAlgo;
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
           bAlgo.BroadcastWin();
           
        } else{
             System.out.println("no leader 2");
        }
        
        
       bAlgo.LostElection = false;
                
    }
}
