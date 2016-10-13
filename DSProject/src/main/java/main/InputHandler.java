package main;

import data.ChatDataUnit;
import data.DataUnit;
import data.MessageLogger;
import data.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by User on 07.10.2016.
 */
public class InputHandler implements Runnable {
    String text;
    Tree tree;
    Multicast multicast;
    InputHandler(Tree tree){
        this.tree = tree;
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

            ChatDataUnit chatMessage2 = new ChatDataUnit(Config.ipAddress, MessageType.CHATMESSAGE, tree, "TESTING BUFFER");
            MessageLogger.MessageLog.put(Config.SentMsg,chatMessage2);
            Config.SentMsg++;

            ChatDataUnit chatMessage = new ChatDataUnit(Config.ipAddress, MessageType.CHATMESSAGE, tree, text);
            multicast.SendMulticast(tree.getHigherIps("0.0.0.0"), chatMessage);
            MessageLogger.MessageLog.put(Config.SentMsg-1,chatMessage);
            //For testing
            int o = 0;

        }
    }
}
