/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.net.InetAddress;
import java.util.ArrayList;
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
     */
    public static  boolean LostElection = false;
    
    public static void run(String incomingId, String randomID){
        if (!compareIds(Parser.parseIp(incomingId), Parser.parseIp(randomID))){
            bullyThem();
        }
    }
    
    public static void run(InetAddress incomingInetId, InetAddress randomInetId){
        run(Parser.inetToStr(incomingInetId), Parser.inetToStr(randomInetId));
    }
    
    public static void run(InetAddress incomingInetId, String randomStringId){
        run(Parser.inetToStr(incomingInetId), randomStringId);
    }
    
    public static void run(String incomingStrId, InetAddress randomInetId){
        run(Parser.strToInet(incomingStrId), Parser.inetToStr(randomInetId));
    }
    
    public static boolean compareIds(Long incomingID, Long localID){
        return (incomingID < localID);
    }
    
    public static void bullyThem() {
        ArrayList<InetAddress> higherIps = Tree.getHigherIps(Config.ipAddress);

        Multicast mult = new Multicast();
        DataUnit data = new DataUnit(Config.ipAddress, MessageType.WANNABELEADER);
        mult.SendMulticast(higherIps,data);
        System.out.println("bully other clients"+higherIps.toString());
        WaitTimer wt = new WaitTimer(10);
        wt.run();
    }

    public static void BroadcastWin(){
        // TODO broadcast winning the election
        DataUnit data = new DataUnit(Config.ipAddress,MessageType.IAMLEADER);
        Broadcast br = new Broadcast(data);
        br.run();
    }
}
