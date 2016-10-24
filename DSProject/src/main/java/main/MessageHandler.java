/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package main;

import com.company.ChatMessage;
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
        if (type != MessageType.ACK)
            System.out.println(data.toString());
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
                System.out.println("Recieved IAMHIGHER");
                break;
            case DISCOVER:
                replyMsg = new DataUnit(Config.ipAddress, MessageType.DISCOVERRESPONSE, vClock);
                multicast.SendMulticast(data.getIpAddress(), replyMsg);
                vClock.addHost(data.getIpAddress(), vClock.getCounter(data.getIpAddress()) );
                vChat.addHost(data.getIpAddress(), vChat.getCounter(data.getIpAddress()) );
                //this.tree.addHost(data.getIpAddress(), data.getSequenceNr());
                break;
            case CHATMESSAGE:
                ChatDataUnit chatMsg = (ChatDataUnit) data;
                System.out.println(chatMsg.toString() + " Message:" + chatMsg.getMsg());
                int seqNumber = chatMsg.getSequenceNumber();
                int currentSeqNumber = vChat.getCounter(chatMsg.getIpAddress());
                System.out.println("RECIEVED:" + seqNumber + " CURRENT:" + currentSeqNumber);

                if (seqNumber == currentSeqNumber + 1) {
                    vChat.addHost(chatMsg.getIpAddress(), chatMsg.getSequenceNumber());
                    System.out.println("GOOD CHAT MSG: " + chatMsg.getMsg());

                    int BufKeyNumber = seqNumber + 1;
                    String BufferKey = chatMsg.getIpAddress().toString().substring(1)+":" +(BufKeyNumber);
                    while (MessageLogger.Buffer.containsKey(BufferKey)) {
                        ChatDataUnit MessageFormBuf = (ChatDataUnit)MessageLogger.Buffer.get(BufferKey);
                        System.out.println("CHAT MSG FROM BUFFER: " + MessageFormBuf.getMsg());
                        if(MessageFormBuf.getIpAddress().equals(Config.ipAddress)){
                            //int o = 0;
                        }
                        //this.switchMsg();
                        MessageLogger.Buffer.remove(BufferKey);
                        vChat.addHost(chatMsg.getIpAddress(), chatMsg.getSequenceNumber());
                        BufKeyNumber++;
                        BufferKey = chatMsg.getIpAddress()+":" +(BufKeyNumber);
                    }
                    replyMsg = new ChatDataUnit(Config.ipAddress, MessageType.ACK, vClock,data.getIpAddress().toString()+":" + ((ChatDataUnit) data).getSequenceNumber());
                    multicast.SendMulticast(data.getIpAddress(), replyMsg);
                } else {
                    MessageLogger.Buffer.put(chatMsg.getIpAddress().toString().substring(1)+":" +chatMsg.getSequenceNumber(),chatMsg);
                    //buff.addMsg(chatMsg);
                    for (int i = currentSeqNumber+1; i < seqNumber; i++) {
                        multicast.SendMulticast(data.getIpAddress(), new ChatDataUnit(Config.ipAddress, MessageType.NEGATIVEACK, vClock, Integer.toString(i)));
                    }
                }
                //buff.messageLog.add(chatMsg);

                break;
            case ACK:
                ChatDataUnit recieveData = (ChatDataUnit) data;
                MessageLogger.AckLog.get(recieveData.getMsg()).AckHashMap.put(recieveData.getIpAddress(),true);
                int a = 0;
                break;
            case NEGATIVEACK:
                System.out.println("NEGATIVE ACK!!!");
                ChatDataUnit recievedData = (ChatDataUnit) data;
                DataUnit message = MessageLogger.MessageLog.get(Config.ipAddress+":"+recievedData.getMsg());
                multicast.SendMulticast(data.getIpAddress(), message);
                int o =0 ;
                //Config.msgCounter--;
                break;
            case DISCOVERRESPONSE:
                vClock.addHost(data.getIpAddress(), vClock.getCounter(data.getIpAddress()) );
                vChat.addHost(data.getIpAddress(), vChat.getCounter(data.getIpAddress()) );
                //HashMap<InetAddress,Integer> aa = Tree.getHmap();
                int oo =0;
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
