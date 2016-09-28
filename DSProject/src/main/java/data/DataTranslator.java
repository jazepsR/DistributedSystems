/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
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
    
    public static DataUnit bytesToObject(byte[] data){
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInput in = null;

        try {
            in = new ObjectInputStream(bis);
            DataUnit o = (DataUnit) in.readObject(); 
        } catch (IOException ex) {
            Logger.getLogger(DataTranslator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataTranslator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new DataUnit("localhost", "asdf", MessageType.DISCOVER);
    }
    
}
