/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Euaggelos
 */
public class DataTranslator {
    
    public static byte[] objectToBytes(DataUnit data){
        
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {   
            out = new ObjectOutputStream(byteOut);
            out.writeObject(data);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(DataTranslator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return byteOut.toByteArray();
    }
    
    public static DataUnit bytesToObject(){
        return new DataUnit("localhost", "asdf", MessageType.DISCOVER);
    }
    
}
