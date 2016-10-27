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
public class InBuffer extends Buffers {

    public InBuffer() {
        super();
    }

    public ChatDataUnit get(String UUID) {
        for (ChatDataUnit chatData : super.listOfChatMsgs) {
            if (chatData.getUUID().equals(UUID)) {
                return chatData;
            }
        }
        return null;
        
    }

    public boolean contains(String UUID) {
        for (ChatDataUnit chatData : listOfChatMsgs) {
            if (chatData.getUUID().equals(UUID)) {
                return true;
            }
        }
        return false;
    }

    public ChatDataUnit pop(String UUID) {
        ChatDataUnit tmpChatData = null;
        if (contains(UUID) == true) {
            tmpChatData = get(UUID);
            remove(UUID);
        }
        return tmpChatData;
    }
}
