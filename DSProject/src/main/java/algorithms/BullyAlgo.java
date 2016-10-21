/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import data.Tree;
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
    public boolean LostElection = false;
    private final Tree tree;
    
    public BullyAlgo(Tree tree){
        this.tree = tree;
    }
    
    public void run(String incomingId, String randomID, Tree tree){
        if (!compareIds(Parser.parseIp(incomingId), Parser.parseIp(randomID))){
            bullyThem();
        }else{
            Multicast mult = new Multicast();
            DataUnit data = new DataUnit(Config.ipAddress, MessageType.IAMHIGHER, tree);
            mult.SendMulticast(incomingId,data);
        }
    }
    
    public void run(InetAddress incomingInetId, InetAddress randomInetId, Tree tree){
        run(Parser.inetToStr(incomingInetId), Parser.inetToStr(randomInetId), tree);
    }
    
    public void run(InetAddress incomingInetId, String randomStringId, Tree tree){
        run(Parser.inetToStr(incomingInetId), randomStringId, tree);
    }
    
    public void run(String incomingStrId, InetAddress randomInetId, Tree tree){
        run(Parser.strToInet(incomingStrId), Parser.inetToStr(randomInetId), tree);
    }
    
    public boolean compareIds(Long incomingID, Long localID){
        return (incomingID < localID);
    }
    
    public void bullyThem() {
        ArrayList<InetAddress> higherIps = tree.getHigherIps(Config.ipAddress);
        Multicast mult = new Multicast();
        DataUnit data = new DataUnit(Config.ipAddress, MessageType.WANNABELEADER, tree);
        mult.SendMulticast(higherIps,data);
        System.out.println("bully other clients"+higherIps.toString());
        WaitTimer wt = new WaitTimer(10, this);
        wt.run();
    }

    public void BroadcastWin(){
        DataUnit data = new DataUnit(Config.ipAddress,MessageType.IAMLEADER, tree);
        Broadcast br = new Broadcast(data);
        br.run();
    }
}
