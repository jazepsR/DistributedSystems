/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.net.InetAddress;
import utils.Parser;

/**
 *
 * @author Angelo
 */
public class DataUnit implements Serializable{
    
    private final InetAddress ipAddress;
    private final MessageType msgType;
    protected final Tree tree;
    
    public DataUnit(String ip, MessageType msgType, Tree tree){
        this(Parser.strToInet(ip), msgType, tree);
    }
    
    public DataUnit(InetAddress ipAddress,  MessageType msgType, Tree tree){
        this.ipAddress = ipAddress;
        this.msgType = msgType;
        this.tree = tree;
    }
    
    public InetAddress getIpAddress(){
        return this.ipAddress;
    }

    public MessageType getMsgType(){
        return this.msgType;
    }
    
    public Tree getTree(){
        return this.tree;
    }

    @Override
    public String toString(){
        // TODO make it better
        System.out.println(ipAddress +" Type:"+ msgType.toString());
        return getClass().getSimpleName();
    }

}
