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
public class BullyAlgo extends Algo{
    
    public BullyAlgo(int id){
        super(id);
    }
    
    /**
     * This is the main bully algorithm. After the comparison between
     * the incoming id and the local id the method either proceeds to
     * the next step or does nothing.
     * @param incomingID 
     */
    public void run(int incomingID){
        if (!compareIds(incomingID, super.getID())){
            bullyThem();
        }
    }
    
    
    public boolean compareIds(int incomingID, int localID){
        return (incomingID < localID);
    }
    
    public void bullyThem(){
        System.out.println("bully other clients");
    }
}
