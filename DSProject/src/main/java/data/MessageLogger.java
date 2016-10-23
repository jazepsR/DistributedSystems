package data;

import java.util.HashMap;

/**
 * Created by User on 07.10.2016.
 */
public class MessageLogger {

    //Key in form ip + ":" + counter
    public static HashMap<String,DataUnit> MessageLog = new HashMap<String, DataUnit>(); // FOR OUTGOING RELIABILITY
    public static HashMap<String,DataUnit> Buffer = new HashMap<String, DataUnit>(); // INCOMING RELIABILITY
}
