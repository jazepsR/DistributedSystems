/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import data.*;

/**
 *
 * @author roberto
 */
public class SendMsg {
   
    Tree vector;
    Multicast multicast;
    VectorClock vClock;
    ChatMessageLog msgLog;

    SendMsg(VectorChat vector, ChatMessageLog msgLog) {
        this.vClock = vClock;
        this.vector = vector;
        multicast = new Multicast();
        this.msgLog = msgLog;
    }
    
    public void send(String text){
        Config.msgCounter++;
        vector.changeCounter(Config.ipAddress,Config.msgCounter);
        System.out.println("text: "+text);
         ChatDataUnit chatMessage = new ChatDataUnit(Config.ipAddress, MessageType.CHATMESSAGE, vector, text);
            //Checking Ack buffer
            final AckObject ack = new AckObject(vector,"/"+Config.ipAddress+":" +(Config.msgCounter));
            MessageLogger.AckLog.put("/"+Config.ipAddress+":" +(Config.msgCounter),ack);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            ack.testAcks();
                        }
                    },
                    500
            );
            multicast.SendMulticast(vector.getAllIps(), chatMessage);
            //MessageLogger.MessageLog.put(Config.ipAddress+":" +Config.msgCounter , chatMessage);
            msgLog.addMsg(chatMessage);
            //For testing

            //ChatDataUnit chatMessage = new ChatDataUnit(Config.ipAddress, MessageType.CHATMESSAGE, vector, text);
           // multicast.SendMulticast(vector.getAllIps(), chatMessage);
            //MessageLogger.MessageLog.put(Config.msgCounter , chatMessage);

//            vClock.changeCounter(Config.ipAddress,Config.msgCounter);

    }
}