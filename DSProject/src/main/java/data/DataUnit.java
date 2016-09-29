/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;

/**
 *
 * @author Angelo
 */
public class DataUnit implements Serializable{
    
    private final String ipAddress;
    private final String macAddress;
    private final MessageType msgType;
    private final int counter;
    
    public DataUnit(String ipAddress, String macAddress, MessageType msgType, int counter){
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.msgType = msgType;
        this.counter = counter;
    }
    
    public String getIpAddress(){
        return this.ipAddress;
    }
    
    public String getMacAddress(){
        return this.macAddress;
    }
    
    public MessageType getMsgType(){
        return this.msgType;
    }
    
    public int getCounter(){
        return this.counter;
    }
    
    @Override
    public String toString(){
        // TODO make it better
        System.out.println(macAddress);
        return getClass().getSimpleName();
    }
    
}
