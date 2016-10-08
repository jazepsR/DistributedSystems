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

    private static Multicast multicast;
    private static Broadcast broadcast;
    private final Tree tree;
    private final BullyAlgo bAlgo;

    MessageHandler(Tree tree, BullyAlgo bAlgo) {
        this.tree = tree;
        this.bAlgo = bAlgo;
    }

    public void switchMsg(DataUnit data) {

        MessageType type = data.getMsgType();

        System.out.println(data.toString());
        switch (type) {

            case HEARTBEAT:
                break;
            case IAMLEADER:
                tree.setIPLeader(data.getIpAddress());
                break;
            case WANNABELEADER:
                bAlgo.run(data.getIpAddress(), Config.ipAddress, tree);
                break;
            case IAMHIGHER:
                bAlgo.LostElection = true;
                 System.out.println("no leader");
                break;
            case DISCOVER:
                //System.out.println("Recieved ")
                multicast = new Multicast();
                DataUnit msg = new DataUnit(Config.ipAddress, MessageType.DISCOVERRESPONSE, this.tree);
                ArrayList<InetAddress> target = new ArrayList<InetAddress>();
                target.add(data.getIpAddress());
                multicast.SendMulticast(target, msg);
                InetAddress ipAdrr = data.getIpAddress();
                this.tree.addHost(ipAdrr, 0);
                break;
            case DISCOVERRESPONSE:
                InetAddress ipAdr = data.getIpAddress();  
                this.tree.addHost(ipAdr, 0);
                
                //HashMap<InetAddress,Integer> aa = Tree.getHmap();
                //int o =0;
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
