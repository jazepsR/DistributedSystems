/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package main;


import algorithms.BullyAlgo;
import data.InBuffer;
import data.ChatDataUnit;
import data.ChatMessageLog;
import data.DataUnit;
import data.MessageLogger;
import data.MessageType;
import data.UpdateLogDataUnit;
import data.VectorChat;
import data.VectorClock;
import utils.Parser;

import java.net.InetAddress;
import java.util.ArrayList;

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
    private final InBuffer buff;
    private final ChatMessageLog msgLog;
    private VectorClock vClock;

    MessageHandler(BullyAlgo bAlgo, VectorChat vChat, VectorClock vClock, InBuffer buff, ChatMessageLog msgLog) {
        this.bAlgo = bAlgo;
        this.vChat = vChat;
        this.vClock = vClock;
        this.buff = buff;
        this.msgLog = msgLog;
        multicast = new Multicast();
    }

    public void switchMsg(DataUnit data, Node node) {

        MessageType type = data.getMsgType();
        //Config.msgCounter++;
        if (type != MessageType.ACK)
            System.out.println(data.toString());
        //tree.getReliability().put(data.getIpAddress(),data.getSequenceNr());
        
        switch (type) {

            case HEARTBEAT:
                break;
            case IAMLEADER:
                vChat.setIPLeader(data.getIpAddress());
                break;
            case WANNABELEADER:
                bAlgo.run(data.getIpAddress(), Config.ipAddress, vChat);
                break;
            case IAMHIGHER:
                bAlgo.LostElection = true;
                System.out.println("Recieved IAMHIGHER");
                break;
            case DISCOVER:
                int number = Math.max(vChat.getCounter(data.getIpAddress()),data.getTree().getCounter(data.getIpAddress()));
                vChat.addHost(data.getIpAddress(), number );
                replyMsg = new DataUnit(Config.ipAddress, MessageType.DISCOVERRESPONSE, vChat);
                multicast.SendMulticast(data.getIpAddress(), replyMsg);
                if(vClock.vectorHmap.get(data.getIpAddress())==null){
                    vClock.addHost(data.getIpAddress(),0);
                }
                UpdateLogDataUnit log = new UpdateLogDataUnit(Config.ipAddress,MessageType.MSGLOG,vClock,msgLog.getMsgs());
                multicast.SendMulticast(data.getIpAddress(), log);

                //this.tree.addHost(data.getIpAddress(), data.getSequenceNr());
                break;
            case DISCOVERRESPONSE:
                if( !("/"+Config.ipAddress).equals(data.getIpAddress().toString())) {
                    int oo =0;
                }
                int num  = data.getTree().getCounter(data.getIpAddress());
                vChat.addHost(data.getIpAddress(), num);

                Config.msgCounter = Math.max(Config.msgCounter, data.getTree().getCounter(Config.ipAddress));
                //HashMap<InetAddress,Integer> aa = Tree.getHmap();

                break;
            case CHATMESSAGE:
                if(vClock.vectorHmap.get(data.getIpAddress())== null){
                    vClock.addHost(data.getIpAddress(),data.getTree().getCounter(data.getIpAddress()));
            }
                msgLog.sortBuffer();
                ChatDataUnit chatMsg = (ChatDataUnit) data;
                if( !("/"+Config.ipAddress).equals(data.getIpAddress().toString())) {
                    int myCounter = vClock.getCounter(Config.ipAddress);
                    for (InetAddress key:vClock.getHmap().keySet()
                         ) {
                        int ip1 = vClock.getCounter(key);
                        int ip2 = data.getTree().getCounter(key);
                        vClock.addHost(key,Math.max(ip1,ip2));
                    }
                    //vClock.addHost(data.getIpAddress(),data.getTree().getCounter(data.getIpAddress()));
                    vClock.addHost(Config.ipAddress,myCounter+1);
                }
                //node.displayChat(data.getTree().getVector()+" "+chatMsg.getMsg());
          
                /*if(chatMsg.getTree().compareTo(vChat)!=0){ // need to check the order
                    //wait until compare=0 and save the previous message
                }else*/
                System.out.println(chatMsg.toString() + " Message:" + chatMsg.getMsg());
                int seqNumber = chatMsg.getSequenceNumber();
                int currentSeqNumber = vChat.getCounter(chatMsg.getIpAddress());
                System.out.println("RECIEVED:" + seqNumber + " CURRENT:" + currentSeqNumber);

                if (seqNumber == currentSeqNumber + 1) {
                    vChat.addHost(chatMsg.getIpAddress(), chatMsg.getSequenceNumber());
                    System.out.println("GOOD CHAT MSG: " + chatMsg.getMsg());

                    int bufKeyNumber = seqNumber + 1;
                    String bufferKey = chatMsg.getUUID();//getIpAddress().toString().substring(1)+":" +(bufKeyNumber);
                    buff.addMsg(chatMsg);
                    while (buff.contains(bufferKey)) {
                        ChatDataUnit messageFormBuf = buff.get(bufferKey);
                        System.out.println("CHAT MSG FROM BUFFER: " + messageFormBuf.getMsg());
                        
                        msgLog.addMsg(buff.pop(bufferKey));
                        vChat.addHost(chatMsg.getIpAddress(), chatMsg.getSequenceNumber());
                        bufKeyNumber++;
                        bufferKey = chatMsg.getIpAddress()+":" +(bufKeyNumber);
                    }
                    replyMsg = new ChatDataUnit(Config.ipAddress, MessageType.ACK, vChat, chatMsg.getIpAddress().toString()+":" + chatMsg.getSequenceNumber());
                    multicast.SendMulticast(chatMsg.getIpAddress(), replyMsg);
                    node.addAllMsg(node.chatLog.getMsgs());
                } else {
                    buff.addMsg(chatMsg);
                    buff.sortBuffer();
                    for (int i = currentSeqNumber+1; i < seqNumber; i++) {
                        multicast.SendMulticast(chatMsg.getIpAddress(), new ChatDataUnit(Config.ipAddress, MessageType.NEGATIVEACK, vChat, Integer.toString(i)));
                    }
                }
                //buff.messageLog.add(chatMsg);

                break;
            case ACK:
                ChatDataUnit recieveData = (ChatDataUnit) data;
                System.out.println(recieveData.getIpAddress().toString());
                try{
                    MessageLogger.AckLog.get(recieveData.getMsg()).AckHashMap.put(recieveData.getIpAddress(),true);
                }catch (Exception e){
                    int oo =0 ;
                }
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

                
            case MSGLOG:
                if( !("/"+Config.ipAddress).equals(data.getIpAddress().toString())) {
                    UpdateLogDataUnit updateData = (UpdateLogDataUnit) data;
                    ArrayList<ChatDataUnit> newList = updateData.getMessages();
                    msgLog.replace(newList);
                    node.addAllMsg(node.chatLog.getMsgs());
                    vClock.addHost(data.getIpAddress(),data.getTree().getCounter(data.getIpAddress()));
                    vClock.addHost(Config.ipAddress,data.getTree().getCounter(Config.ipAddress));
                }

                break;
            default:
                break;

        }
    }

}
