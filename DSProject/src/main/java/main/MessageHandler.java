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
import java.util.ArrayList;


/**
 *
 * @author roberto
 */
public class MessageHandler {

    public static Multicast multicast;
    public static Broadcast broadcast;

    MessageHandler() {

    }

    public void switchMsg(DataUnit data) {

        MessageType type = data.getMsgType();
        //System.out.println(data.toString());
        switch (type) {

            case HEARTBEAT:
                break;
            case IAMLEADER:
                Tree.setIPLeader(data.getIpAddress());
                break;
            case WANNABELEADER:
                BullyAlgo.run(data.getIpAddress(), Config.ipAddress);
                break;
            case IAMHIGHER:
                BullyAlgo.LostElection = true;
                break;
            case DISCOVER:
                //System.out.println("Recieved ")
                multicast = new Multicast();
                DataUnit msg = new DataUnit(Config.ipAddress, MessageType.DISCOVERRESPONSE);
                ArrayList<InetAddress> target = new ArrayList<InetAddress>();

                target.add(data.getIpAddress());

                multicast.SendMulticast(target, msg);
                break;
            case DISCOVERRESPONSE:
               
                InetAddress ipAdr = data.getIpAddress();  
                Tree.addHost(ipAdr, 0);
//               TODO this should be removed? i really do not understand this code.
//                HashMap<String, Integer> hMap = Tree.getHmap();
//                if (hMap.containsKey(ipAdr)) {
//                    Tree.addHost(ipAdr, 0);
//                }
                break;
            default:
                break;

        }
    }

}
