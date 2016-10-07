package main;

import data.ChatDataUnit;
import data.DataUnit;
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

    }
    public void run() {
        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                text = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Placeholder code
            ChatDataUnit chatMessage = new ChatDataUnit(Config.ipAddress, MessageType.CHATMESSAGE, tree, text);
            multicast.SendMulticast(tree.getHigherIps("0.0.0.0"), chatMessage);
        }
    }
}
