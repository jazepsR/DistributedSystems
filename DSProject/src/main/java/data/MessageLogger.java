package data;

import java.net.InetAddress;
import java.util.HashMap;

/**
 * Created by User on 07.10.2016.
 */
public class MessageLogger {

    //Key in form ip + ":" + counter
    public static HashMap<String,DataUnit> MessageLog = new HashMap<String, DataUnit>(); // FOR OUTGOING RELIABILITY
    public static HashMap<String,DataUnit> Buffer = new HashMap<String, DataUnit>(); // INCOMING RELIABILITY

    //First string is ip of sender + ":" + sender counter. "Second key is ip of processor who sent the ACK
    public static HashMap<String,AckObject> AckLog = new HashMap<String, AckObject>(); //FOR TRACKING RECIEVED ACK MESSAGES
}
