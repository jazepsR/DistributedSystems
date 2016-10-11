/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package main;

import algorithms.BullyAlgo;
import com.company.ChatMessage;
import data.ChatDataUnit;
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
    private final Node node;

    MessageHandler(Tree tree, BullyAlgo bAlgo, Node node) {
        this.tree = tree;
        this.bAlgo = bAlgo;
        this.node = node;
        multicast = new Multicast();
    }

    public void switchMsg(DataUnit data) {

        MessageType type = data.getMsgType();

        System.out.println(data.toString());
        tree.getReliability().put(data.getIpAddress(),data.getSequenceNr());
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

                DataUnit msg = new DataUnit(Config.ipAddress, MessageType.DISCOVERRESPONSE, this.tree,Config.SentMsg);
                ArrayList<InetAddress> target = new ArrayList<InetAddress>();
                target.add(data.getIpAddress());
                multicast.SendMulticast(target, msg);
                InetAddress ipAdrr = data.getIpAddress();
                this.tree.addHost(ipAdrr, 0);
                break;
            case CHATMESSAGE:
                ChatDataUnit aa = (ChatDataUnit)data;
                System.out.println(aa.getMsg());
                node.messageLog.add(aa);
                break;
            case ACK:
                break;
            case NEGATIVEACK:
                System.out.println(data.toString());
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
        if(type!=MessageType.ACK){
            DataUnit msg = new DataUnit(Config.ipAddress, MessageType.ACK, this.tree,Config.SentMsg);
            ArrayList<InetAddress> target = new ArrayList<InetAddress>();
            target.add(data.getIpAddress());
            multicast.SendMulticast(target, msg);
        }
    }

}
