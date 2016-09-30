/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package main;

import algorithms.BullyAlgo;
import data.DataUnit;
import data.MessageType;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author roberto
 */
public class MessageHandler {
    public static Multicast multicast;
    public static Broadcast broadcast;
    MessageHandler() throws UnknownHostException {
        multicast= new Multicast();
        broadcast = new Broadcast();
    }

    public static void switchMsg (DataUnit data) throws UnknownHostException {
        
        MessageType type = data.getMsgType();
       
        switch (type) {
            
            case HEARTBEAT: 
                     break;
            case IAMLEADER:
                Tree.setIPLeader(data.getIpAddress());
                     break;
            case WANNABELEADER:
                BullyAlgo.run(data.getIpAddress(),Config.ipAddress);
                     break;
            case IAMHIGHER:
                BullyAlgo.LostElection =true;
                break;
            case DISCOVER:
                DataUnit msg = new DataUnit(Config.ipAddress,MessageType.DISCOVERRESPONSE,1);
                ArrayList<InetAddress> target = new ArrayList<InetAddress>();
                target.add(InetAddress.getByName(data.getIpAddress()));
                multicast.SendMulticast(target,msg);
                break;
            case DISCOVERRESPONSE:
                String ipAdr = data.getIpAddress();
                HashMap<String, Integer> hMap = Tree.getHmap();
                if (hMap.containsKey(ipAdr)){ Tree.addHost(ipAdr,data.getCounter());}
            default: break;
                
        }
    }
    
}