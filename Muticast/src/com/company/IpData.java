package com.company;

import java.net.InetAddress;

/**
 * Created by User on 29.09.2016.
 */
public class IpData {
    public InetAddress ip;
    int port;
    IpData(InetAddress IP,int port){
        ip= IP;
        this.port = port;
    }
}
