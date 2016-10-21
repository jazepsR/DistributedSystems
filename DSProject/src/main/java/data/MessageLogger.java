package data;

import java.util.HashMap;

/**
 * Created by User on 07.10.2016.
 */
public class MessageLogger {
    public static HashMap<Integer,DataUnit> MessageLog = new HashMap<Integer, DataUnit>(); // FOR OUTGOING RELIABILITY
    public static HashMap<Integer,DataUnit> Buffer = new HashMap<Integer, DataUnit>(); // INCOMING RELIABILITY
}
