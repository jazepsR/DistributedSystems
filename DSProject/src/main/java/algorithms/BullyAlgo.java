/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.util.List;
import main.Config;
import main.Tree;
import main.WaitTimer;
import utils.Parser;

/**
 *
 * @author Angelo
 */
public class BullyAlgo{
    
    
    /**
     * This is the main bully algorithm. After the comparison between
     * the incoming id and the local id the method either proceeds to
     * the next step or does nothing.
     * @param incomingID 
     */
    public static  boolean LostElection = false;
    public static void run(String incomingId, String randomID){
        if (!compareIds(Parser.parseIp(incomingId), Parser.parseIp(randomID))){
            bullyThem();
        }
    }
    
    public static boolean compareIds(Long incomingID, Long localID){
        return (incomingID < localID);
    }
    
    public static void bullyThem(){
        List<String> higherIps = Tree.getHigherIps(Config.ipAddress);
        
        // TODO multicast to everyone with higher ip
        // TODO 1) start the timer 2) assube everybody is dead 3) if receive reply turn to alive
        System.out.println("bully other clients");
        WaitTimer wt = new WaitTimer(10);
        wt.run();
    }

    public static void BroadcastWin(){
        // TODO broadcast winning the election
    }
}
