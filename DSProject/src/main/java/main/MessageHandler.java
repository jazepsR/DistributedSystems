/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package main;

import data.DataUnit;

/**
 *
 * @author roberto
 */
public class MessageHandler {
    
    private final DataUnit data;
    
    public MessageHandler (DataUnit data) {
        this.data=data;
    }
    
    public void switchMsg (){
       
        switch (this.data.getMsgType()) {
            
            case HEARTBEAT: 
                     break;
            case IAMLEADER:  
                     break;
            case WANNABELEADER: 
                     break;
            case DISCOVER: 
                     break;
            default: break;
                
        }
    }
    
}
