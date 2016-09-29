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
public abstract class Algo {
    
    private int id;
    
    public Algo(int id){
        this.id = id;
    }
    
    public int getID(){
        return this.id;
    }
    
}
