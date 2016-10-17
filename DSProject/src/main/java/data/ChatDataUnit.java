/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import main.Config;
import main.Tree;

/**
 *
 * @author Angelo
 */
public class ChatDataUnit extends DataUnit{
    
    private final String msg;

    
    public ChatDataUnit(String ip, MessageType msgType, Tree tree, String msg){
        super(ip, msgType, tree, Config.SentMsg);
        this.msg = msg;
    }
    
    public String getMsg(){
        return this.msg;
    }
    
    /*@Override
    public String toString(){
        System.out.println();
        return this.msg + "---" + super.tree.toString();
    }*/
    
}
