/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Euaggelos
 */
public class Main {
    
    public static void main(String [] args){
        System.out.println("test");
        Thread thread1 = new Client(15, 20015, 1);
        Thread thread2 = new Client(155, 20000, 0);
        
        thread1.start();
        thread2.start();
    }
    
}
