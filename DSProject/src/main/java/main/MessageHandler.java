/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package main;

import algorithms.BullyAlgo;
import data.DataUnit;
import data.MessageType;

/**
 *
 * @author roberto
 */
public class MessageHandler {
    Multicast multicast;
    Broadcast broadcast;
    MessageHandler(){
        multicast= new Multicast();
        broadcast = new Broadcast();
    }

    public static void switchMsg (DataUnit data){
        
        MessageType type = data.getMsgType();
       
        switch (type) {
            
            case HEARTBEAT: 
                     break;
            case IAMLEADER:  
                     break;
            case WANNABELEADER:
                //TODO find local IP
                //BullyAlgo.compareIds(data.getIpAddress(),)

                // TODO multicast to people with higher ip
                     break;
            case IAMHIGHER:
                BullyAlgo.LostElection =true;
            case DISCOVER:
                Mu
                // TODO add the ip to the list of ips
                // TODO send the reply (ip) back to the poerson who broadcast-ed
                break;
            default: break;
                
        }
    }
    
}
