/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package main;

import data.Tree;
import algorithms.BullyAlgo;
import data.Buffer;
import data.ChatDataUnit;
import data.DataUnit;
import data.MessageLogger;
import data.MessageType;
import data.VectorChat;
import data.VectorClock;

/**
 *
 * @author roberto
 */
public class MessageHandler {

    private static Multicast multicast;
    private static Broadcast broadcast;
    private final BullyAlgo bAlgo;
    private DataUnit replyMsg;
    private final VectorChat vChat;
    private final VectorClock vClock;
    private final Buffer buff;

    MessageHandler(BullyAlgo bAlgo, VectorClock vClock, VectorChat vChat, Buffer buff) {
        this.bAlgo = bAlgo;
        this.vClock = vClock;
        this.vChat = vChat;
        this.buff = buff;
        multicast = new Multicast();
    }

    public void switchMsg(DataUnit data) {

        MessageType type = data.getMsgType();
        //Config.msgCounter++;
        //System.out.println(data.toString());
        //tree.getReliability().put(data.getIpAddress(),data.getSequenceNr());
        switch (type) {

            case HEARTBEAT:
                break;
            case IAMLEADER:
                vClock.setIPLeader(data.getIpAddress());
                break;
            case WANNABELEADER:
                bAlgo.run(data.getIpAddress(), Config.ipAddress, vClock);
                break;
            case IAMHIGHER:
                bAlgo.LostElection = true;
                System.out.println("no leader");
                break;
            case DISCOVER:
                replyMsg = new DataUnit(Config.ipAddress, MessageType.DISCOVERRESPONSE, vClock);
                multicast.SendMulticast(data.getIpAddress(), replyMsg);
                //this.tree.addHost(data.getIpAddress(), data.getSequenceNr());
                break;
            case CHATMESSAGE:
                ChatDataUnit chatMsg = (ChatDataUnit) data;
                System.out.println(chatMsg.toString() + " Message:" + chatMsg.getMsg());
                int seqNumber = chatMsg.getSequenceNumber();
                int currentSeqNumber = vChat.getCounter(chatMsg.getIpAddress());
                //System.out.println("RECIEVED:" + seqNumber + " CURRENT:" + currentSeqNumber);
                if (seqNumber == currentSeqNumber + 1) {
                    vChat.addHost(chatMsg.getIpAddress(), chatMsg.getSequenceNumber());
                    //System.out.println("GOOD CHAT MSG");
                    int BufferKey = seqNumber + 1;
                    while (buff.contains(BufferKey)) {
                        this.switchMsg(buff.get(BufferKey));
                        buff.remove(BufferKey);
                        vChat.addHost(chatMsg.getIpAddress(), chatMsg.getSequenceNumber());
                        BufferKey++;
                    }
                    replyMsg = new DataUnit(Config.ipAddress, MessageType.ACK, vClock);
                    multicast.SendMulticast(data.getIpAddress(), replyMsg);
                } else {
                    buff.addMsg(chatMsg);
                    for (int i = currentSeqNumber+1; i < seqNumber; i++) {
                        multicast.SendMulticast(Config.ipAddress, new ChatDataUnit(Config.ipAddress, MessageType.NEGATIVEACK, vClock, Integer.toString(i)));
                    }
                }
                //buff.messageLog.add(chatMsg);

                break;
            case ACK:
                break;
            case NEGATIVEACK:
                //System.out.println("NEGATIVE ACK!!!");
                ChatDataUnit recievedData = (ChatDataUnit) data;
                DataUnit message = MessageLogger.MessageLog.get(Integer.parseInt(recievedData.getMsg()));
                multicast.SendMulticast(data.getIpAddress(), message);
                Config.msgCounter--;
                break;
            case DISCOVERRESPONSE:
                vClock.addHost(data.getIpAddress(), vClock.getCounter(data.getIpAddress()) + 1);
                vChat.addHost(data.getIpAddress(), vChat.getCounter(data.getIpAddress()) + 1);
                //HashMap<InetAddress,Integer> aa = Tree.getHmap();
                int o =0;
//               TODO this should be removed? i really do not understand this code.
//                HashMap<String, Integer> hMap = Tree.getHmap();
//                if (hMap.containsKey(ipAdr)) {
//                    Tree.addHost(ipAdr, 0);
//                }
                break;
            default:
                break;

        }
        // MOVED THIS ABOVE
//        if(type==MessageType.CHATMESSAGE){
//            DataUnit msg = new DataUnit(Config.ipAddress, MessageType.ACK, this.tree,Config.msgCounter);
//            ArrayList<InetAddress> target = new ArrayList<InetAddress>();
//            target.add(data.getIpAddress());
//            multicast.SendMulticast(target, msg);
//        }
    }

}
