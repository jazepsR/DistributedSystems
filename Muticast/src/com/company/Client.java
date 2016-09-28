package com.company;
import java.io.IOException;
import java.net.*;

/**
 * Created by User on 28.09.2016.
 */
public class Client {
    final  String INET_ADDR = "224.0.0.3";
    int PORT = 8888;
    boolean Leader;
    String name;
    Client(String Name, boolean IsLeader) throws InterruptedException, UnknownHostException {
        name = Name;
        Leader = IsLeader;
        main();
    }


    public void main() throws UnknownHostException, InterruptedException {
        // Get the address that we are going to connect to.
        InetAddress addr = InetAddress.getByName(INET_ADDR);





        // Open a new DatagramSocket, which will be used to send the data.
        if (Leader) {
            try (DatagramSocket serverSocket = new DatagramSocket()) {
                for (int i = 0; i < 100; i++) {
                    String msg = "Sent message no " + i;

                    // Create a packet that will contain the data
                    // (in the form of bytes) and send it.
                    DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
                            msg.getBytes().length, addr, PORT);
                    serverSocket.send(msgPacket);

                    System.out.println("Server sent packet with msg: " + msg);
                    Thread.sleep(500);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
            // Get the address that we are going to connect to.
            InetAddress address = InetAddress.getByName(INET_ADDR);

            // Create a buffer of bytes, which will be used to store
            // the incoming bytes containing the information from the server.
            // Since the message is small here, 256 bytes should be enough.
            byte[] buf = new byte[256];

            // Create a new Multicast socket (that will allow other sockets/programs
            // to join it as well.
            try (MulticastSocket clientSocket = new MulticastSocket(PORT)){
                //Joint the Multicast group.
                clientSocket.joinGroup(address);

                while (true) {
                    // Receive the information and print it.
                    DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                    clientSocket.receive(msgPacket);

                    String msg = new String(buf, 0, buf.length);
                    System.out.println(name+" received msg: " + msg);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}


