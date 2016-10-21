/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author Euaggelos
 */
public class Buffer {
    
    // THIS IS ONLY FOR VECTOR CLOCK SORTING

    private final ArrayList<ChatDataUnit> listOfChatMsgs;

    public Buffer() {
        listOfChatMsgs = new ArrayList();
    }

    public void addMsg(ChatDataUnit msg) {
        listOfChatMsgs.add(msg);
    }
    
    public boolean contains(int i){
        for(ChatDataUnit data : listOfChatMsgs){
            if(data.getSequenceNumber() == i)
                return true;
        }
        return false;
    }
    
    public ChatDataUnit get(int i){
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
