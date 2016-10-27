/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import utils.Parser;

/**
 *
 * @author Euaggelos
 */
public abstract class Buffers {
    
    // THIS IS ONLY FOR VECTOR CLOCK SORTING

    protected ArrayList<ChatDataUnit> listOfChatMsgs;

    public Buffers() {
        listOfChatMsgs = new ArrayList();
    }

    public void addMsg(ChatDataUnit msg) {
        listOfChatMsgs.add(msg);
    }
    
    public boolean contains(int i, InetAddress ip){
        for(ChatDataUnit data : listOfChatMsgs){
            if(data.getSequenceNumber() == i && data.getIpAddress().equals(ip))
                return true;
        }
        return false;
    }
    

    public boolean containsSeq(int i, String ip){
        return Buffers.this.contains(i, Parser.strToInet(ip));
    }
    
    public ChatDataUnit getOnSeq(int i){
        for(ChatDataUnit data : listOfChatMsgs){
            if(data.getSequenceNumber() == i)
                return data;
        }
        return null;
    }
    
    public void remove(int i){
        ListIterator<ChatDataUnit> iter = listOfChatMsgs.listIterator();
        while(iter.hasNext()){
            if(iter.next().getSequenceNumber() == i){
                iter.remove();
            }
        }
    }
    
    public void remove(ChatDataUnit chatData){
        listOfChatMsgs.remove(chatData);
    }
    
    public void remove(String UUID){
        ListIterator<ChatDataUnit> iter = listOfChatMsgs.listIterator();
        while(iter.hasNext()){
            if(iter.next().getUUID().equals(UUID)){
                iter.remove();
            }
        }
    }
    
    public void addMsgs(ArrayList<ChatDataUnit> lst){
        for(ChatDataUnit data : lst){
            addMsg(data);
        }
    }
    
    public ArrayList<ChatDataUnit> getMsgs(){
        return this.listOfChatMsgs;
    }
    
    public void sortBuffer(){
        Collections.sort(listOfChatMsgs, ChatDataUnit.Comparators.cmp);
    }
}
