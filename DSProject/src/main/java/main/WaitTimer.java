package main;

import algorithms.BullyAlgo;

/**
 * Created by User on 30.09.2016.
 */
public class WaitTimer implements Runnable{
    public int waitTime;
    private Thread t;
    public WaitTimer(int waitForSeconds){
        waitTime = waitForSeconds;

    }

    public void run() {
        try {
            t.sleep(waitTime*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (BullyAlgo.LostElection = false)
        {
            System.out.println("Won election");
           BullyAlgo.BroadcastWin();
        }else{
            System.out.println("Lost election");
        }
    }
}
