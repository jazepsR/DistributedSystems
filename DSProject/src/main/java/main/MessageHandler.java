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
import data.MessageLogger;
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
        //Config.SentMsg++;
        System.out.println(data.toString());
        //tree.getReliability().put(data.getIpAddress(),data.getSequenceNr());
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
                DataUnit msg = new DataUnit(Config.ipAddress, MessageType.DISCOVERRESPONSE, this.tree,Config.SentMsg);
                ArrayList<InetAddress> target = new ArrayList<InetAddress>();
                target.add(data.getIpAddress());
                multicast.SendMulticast(target, msg);
                InetAddress ipAdrr = data.getIpAddress();
                this.tree.addHost(ipAdrr, data.getSequenceNr());
                this.tree.addHost(ipAdrr, 0);
                break;
            case CHATMESSAGE:
                ChatDataUnit ChatMsg = (ChatDataUnit)data;
                System.out.println(ChatMsg.toString()+" Message:" + ChatMsg.getMsg());
                node.messageLog.add(ChatMsg);
                break;
            case ACK:
                break;
            case NEGATIVEACK:
                System.out.println("NEGATIVE ACK!!!");
                ArrayList<InetAddress> targetAdr = new ArrayList<InetAddress>();
                targetAdr.add(data.getIpAddress());
                ChatDataUnit recievedData = (ChatDataUnit)data;
                DataUnit message = MessageLogger.MessageLog.get(Integer.parseInt(recievedData.getMsg()));
                multicast.SendMulticast(targetAdr,message );
                Config.SentMsg--;
                break;
            case DISCOVERRESPONSE:
                InetAddress ipAdr = data.getIpAddress();  
                this.tree.addHost(ipAdr, data.getSequenceNr());
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
        if(type==MessageType.CHATMESSAGE){
            DataUnit msg = new DataUnit(Config.ipAddress, MessageType.ACK, this.tree,Config.SentMsg);
            ArrayList<InetAddress> target = new ArrayList<InetAddress>();
            target.add(data.getIpAddress());
            multicast.SendMulticast(target, msg);
        }
    }

}
