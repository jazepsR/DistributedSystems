
package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        //Server server = new Server(14);
        //Client client1 = new Client("servert",14,"Jekabs");
        //Client client2 = new Client("server",14,"Antons");
        ServerGUI sgui = new ServerGUI(1111);
        ClientGUI cgui1 = new ClientGUI("localhost",1111);
        ClientGUI cgui2 = new ClientGUI("localhost",1111);
    }
}