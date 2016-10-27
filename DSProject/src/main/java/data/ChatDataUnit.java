/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.Comparator;
import main.Config;

/**
 *
 * @author Angelo
 */
public class ChatDataUnit extends DataUnit implements Comparable<ChatDataUnit>{
    
    private final String msg;
    private final int sequenceNumber;
    private final String UUID;

    
    public ChatDataUnit(String ip, MessageType msgType, Tree tree, String msg){
        super(ip, msgType, tree);
        this.sequenceNumber = Config.msgCounter;
        this.msg = msg;
        this.UUID = ip+":" +(this.sequenceNumber);
    }
    
    public String getUUID(){
        return this.UUID;
    }
    
    public String getMsg(){
        return this.msg;
    }
    
    public int getSequenceNumber(){
        return sequenceNumber;
    }
    
    @Override
    public String toString(){
        System.out.println();
        return super.tree.toString()  + " Counter: " + Integer.toString(sequenceNumber);
    }

    public int compareTo(ChatDataUnit incomingMsg) {
        Tree inTree = incomingMsg.getTree();
        Tree thisTree = this.getTree();
        
        return inTree.compareTo(thisTree);
    }
    
    public static class Comparators{
        public static Comparator<ChatDataUnit> cmp = new Comparator<ChatDataUnit>() {
            public int compare(ChatDataUnit o1, ChatDataUnit o2) {
                return o1.compareTo(o2);
            }
        };
    }
    
}
