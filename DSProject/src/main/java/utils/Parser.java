/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Euaggelos
 */
public class Parser {
    
    public static Long parseIp(String s){
        return Long.parseLong(s.replace(".", ""));
    }
    
}
