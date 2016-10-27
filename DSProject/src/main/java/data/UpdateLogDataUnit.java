/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;

/**
 *
 * @author Angelo
 */
public class UpdateLogDataUnit extends DataUnit{
    
    private final ArrayList<ChatDataUnit> listOfMessages;
    
    public UpdateLogDataUnit(String ip, MessageType msgType, Tree tree, ArrayList<ChatDataUnit> msgs) {
        super(ip, msgType, tree);
        listOfMessages = msgs;
    }
    
    public ArrayList<ChatDataUnit> getMessages(){
        return listOfMessages;
    }
    
}
