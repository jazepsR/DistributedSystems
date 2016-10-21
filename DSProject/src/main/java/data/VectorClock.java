/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.net.InetAddress;
import utils.Parser;

/**
 *
 * @author Euaggelos
 */
public class VectorClock extends Tree{
    
    private InetAddress ipLeader;
        
    public VectorClock(){
        super();
    }    
    
        /**
     * @return the IPLeader
     */
    public InetAddress getIPLeader() {
        return ipLeader;
    }
    
        /**
     * @param ip
     */
    public void setIPLeader(InetAddress ip) {
        ipLeader = ip;
    }
    
    public void setIPLeader(String ip){
        setIPLeader(Parser.strToInet(ip));
    }
    
}
