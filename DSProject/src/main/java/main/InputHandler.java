package main;

import data.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by User on 07.10.2016.
 */
public class InputHandler implements Runnable {

    String text;
    Tree vector;
    Multicast multicast;

    InputHandler(VectorChat vector) {
        this.vector = vector;
        multicast = new Multicast();
    }

    public void run() {
        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                text = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ///ChatDataUnit chatMessage2 = new ChatDataUnit(Config.ipAddress, MessageType.CHATMESSAGE, vector, "TESTING BUFFER");
            //MessageLogger.MessageLog.put(Config.ipAddress+":" +Config.msgCounter, chatMessage2);
            //Config.msgCounter++;

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
            // REMEMBER THIS 
            MessageLogger.MessageLog.put(Config.ipAddress+":" +Config.msgCounter , chatMessage);
            //For testing
            // SHOULDNT THIS INCREASE MORE?
            Config.msgCounter++;

            int o = 0;

        }
    }
}
