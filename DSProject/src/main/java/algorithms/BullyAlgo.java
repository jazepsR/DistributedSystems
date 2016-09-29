/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

/**
 *
 * @author Angelo
 */
public class BullyAlgo{
    
    
    /**
     * This is the main bully algorithm. After the comparison between
     * the incoming id and the local id the method either proceeds to
     * the next step or does nothing.
     * @param incomingID 
     */
    public static void run(int incomingID, int randomID){
        if (!compareIds(incomingID, randomID)){
            bullyThem();
        }
    }
    
    public static boolean compareIds(int incomingID, int localID){
        return (incomingID < localID);
    }
    
    public static void bullyThem(){
        // TODO multicast to everyone with higher ip
        // TODO 1) start the timer 2) assube everybody is dead 3) if receive reply turn to alive
        System.out.println("bully other clients");
    }
}
