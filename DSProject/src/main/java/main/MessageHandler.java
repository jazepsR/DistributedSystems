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
    private final VectorClock vClock;
    private final InBuffer buff;
    private final ChatMessageLog msgLog;

    MessageHandler(BullyAlgo bAlgo, VectorClock vClock, VectorChat vChat, InBuffer buff, ChatMessageLog msgLog) {
        this.bAlgo = bAlgo;
        this.vClock = vClock;
        this.vChat = vChat;
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
                UpdateLogDataUnit log = new UpdateLogDataUnit(Config.ipAddress,MessageType.MSGLOG,vClock,msgLog.getMsgs());
                multicast.SendMulticast(data.getIpAddress(), log);
                //this.tree.addHost(data.getIpAddress(), data.getSequenceNr());
                break;
            case CHATMESSAGE:
                msgLog.sortBuffer();
                ChatDataUnit chatMsg = (ChatDataUnit) data;
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
                    replyMsg = new ChatDataUnit(Config.ipAddress, MessageType.ACK, vClock, chatMsg.getIpAddress().toString()+":" + chatMsg.getSequenceNumber());
                    multicast.SendMulticast(chatMsg.getIpAddress(), replyMsg);
                    node.addAllMsg(node.chatLog.getMsgs());
                } else {
                    buff.addMsg(chatMsg);
                    buff.sortBuffer();
                    for (int i = currentSeqNumber+1; i < seqNumber; i++) {
                        multicast.SendMulticast(chatMsg.getIpAddress(), new ChatDataUnit(Config.ipAddress, MessageType.NEGATIVEACK, vClock, Integer.toString(i)));
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
                break;
                
            case MSGLOG:
                if( !("/"+Config.ipAddress).equals(data.getIpAddress().toString())) {
                    UpdateLogDataUnit updateData = (UpdateLogDataUnit) data;
                    ArrayList<ChatDataUnit> newList = updateData.getMessages();
                    msgLog.replace(newList);
                    node.addAllMsg(node.chatLog.getMsgs());
                }
                break;
            default:
                break;

        }
    }

}
