package main;

import data.Tree;
import data.ChatDataUnit;
import data.DataUnit;
import data.MessageLogger;
import data.MessageType;
import data.VectorChat;

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

            ChatDataUnit chatMessage2 = new ChatDataUnit(Config.ipAddress, MessageType.CHATMESSAGE, vector, "TESTING BUFFER");
            MessageLogger.MessageLog.put(Config.msgCounter, chatMessage2);
            Config.msgCounter++;

            ChatDataUnit chatMessage = new ChatDataUnit(Config.ipAddress, MessageType.CHATMESSAGE, vector, text);
            multicast.SendMulticast(vector.getAllIps(), chatMessage);
            MessageLogger.MessageLog.put(Config.msgCounter - 1, chatMessage);
            //For testing

            //ChatDataUnit chatMessage = new ChatDataUnit(Config.ipAddress, MessageType.CHATMESSAGE, vector, text);
           // multicast.SendMulticast(vector.getAllIps(), chatMessage);
            //MessageLogger.MessageLog.put(Config.msgCounter , chatMessage);
            int o = 0;

        }
    }
}
