package data;

import java.net.InetAddress;
import java.util.HashMap;

/**
 * Created by User on 24.10.2016.
 */
public final class AckObject  {
    public HashMap<InetAddress,Boolean> AckHashMap = new HashMap<InetAddress, Boolean>();
    private String myKey;
    public AckObject(Tree tree, String myKey){
        AckHashMap = tree.getTreeForAck();
        this.myKey = myKey;

    }
    public void testAcks(){
        boolean checkSucceeded = true;
        for(boolean HasAck: AckHashMap.values()){
            if(HasAck==false){
                checkSucceeded = false;
                System.out.println("Ack check failed");
            }
        }
        if(checkSucceeded)
        {
            MessageLogger.AckLog.remove(myKey);
            System.out.println("Ack check succeeded!");
        }
    }
}
