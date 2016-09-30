/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import data.DataUnit;
import data.MessageType;
import main.*;
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
            try {
                bullyThem();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static boolean compareIds(Long incomingID, Long localID){
        return (incomingID < localID);
    }
    
    public static void bullyThem() throws UnknownHostException {
        ArrayList<InetAddress> higherIps = Tree.getHigherIps(Config.ipAddress);

        Multicast mult = new Multicast();
        DataUnit data = new DataUnit(Config.ipAddress, MessageType.WANNABELEADER,1);
        mult.SendMulticast(higherIps,data);
        System.out.println("bully other clients");
        WaitTimer wt = new WaitTimer(10);
        wt.run();
    }

    public static void BroadcastWin(){
        // TODO broadcast winning the election
        DataUnit data = new DataUnit(Config.ipAddress,MessageType.IAMLEADER,2);
        Broadcast.run(data);

    }
}
