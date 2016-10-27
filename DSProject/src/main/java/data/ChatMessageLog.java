/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;

/**
 *
 * @author Euaggelos
 */
public class ChatMessageLog extends Buffers {
    
    public ChatMessageLog(){
        super();
        
    }
    
    public void replace(ArrayList<ChatDataUnit> newList){
        listOfChatMsgs = newList;
    }
    
}
