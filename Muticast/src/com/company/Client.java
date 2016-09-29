package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 28.09.2016.
 */
public class Client {
    final  String INET_ADDR = "224.0.0.3";
    int PORT = 8888;
    boolean Leader;
    String name;
    List<IpData> IpList = new ArrayList<IpData>();
    Client(int port, boolean IsLeader) throws Exception {
        PORT = port;
        Leader = IsLeader;
        main();
    }

    public void  main() throws Exception
    {
        Listener L1 = new Listener( IpList,PORT);
        L1.start();
        SendMulticast S1 = new SendMulticast(IpList,Integer.toString(PORT));
        S1.start();


        /*if(Leader) {
            DatagramSocket serverSocket = new DatagramSocket(9876);
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());
                System.out.println("RECEIVED: " + sentence);
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                String capitalizedSentence = sentence.toUpperCase();
                sendData = capitalizedSentence.getBytes();
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            }
        }else {
            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            String sentence = inFromUser.readLine();
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData());
            System.out.println("FROM SERVER:" + modifiedSentence);
            clientSocket.close();

        }
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

            // Create a new SendMulticast socket (that will allow other sockets/programs
            // to join it as well.
            try (MulticastSocket clientSocket = new MulticastSocket(PORT)){
                //Joint the SendMulticast group.
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
        }*/
    }
}


