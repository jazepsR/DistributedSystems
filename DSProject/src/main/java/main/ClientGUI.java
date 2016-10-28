package main;

/**
 * Created by User on 20.09.2016.
 */
import data.VectorChat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/*
 * The Client with its GUI
 */
public class ClientGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    // will first hold "Username:", later on "Enter message"
    private JTextField tf;
    // to Logout and get the list of the users
    private JButton login, logout, whoIsIn;

    // if it is for connection
    private final VectorChat vectorChat;
    
    private JTextArea chat, event;

    // Constructor connection receiving a socket number
    ClientGUI(VectorChat vectorChat) {
        super("Chat Client");
        this.vectorChat = vectorChat;
        // The NorthPanel with:
        JPanel northPanel = new JPanel(new GridLayout(3, 1));
        // the server name anmd the port number
        JPanel serverAndPort = new JPanel(new GridLayout(1, 5, 1, 3));


        String s = getIpAddress();

        serverAndPort.add(new JLabel("Address:  " + s));

        northPanel.add(serverAndPort);


        tf = new JTextField("");
        tf.setBackground(Color.WHITE);
        //tf.addActionListener(action);
        northPanel.add(tf);
        add(northPanel, BorderLayout.NORTH);

        // the event and chat room
        JPanel center = new JPanel(new GridLayout(2, 1));
        chat = new JTextArea(80, 80);

        chat.setEditable(false);
        appendChat("Chat room.\n");
                center.add(new JScrollPane(chat));
        event = new JTextArea(80, 80);
        event.setEditable(false);
        appendEvent("Events log.\n");
        center.add(new JScrollPane(event));
        add(center);

        // the 3 buttons
        login = new JButton("Login");
        login.addActionListener(this);
        logout = new JButton("Logout");
        logout.addActionListener(this);
        logout.setEnabled(false);		// you have to login before being able to logout
        whoIsIn = new JButton("Who is in");
        whoIsIn.addActionListener(this);
        whoIsIn.setEnabled(false);		// you have to login before being able to Who is in

        JPanel southPanel = new JPanel();
        southPanel.add(login);
        southPanel.add(logout);
        southPanel.add(whoIsIn);
        add(southPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);
        tf.requestFocus();

    }

    // called by the Client to append text in the TextArea
     void appendChat(String str) {
        chat.append(str);
        chat.setCaretPosition(chat.getText().length() - 1);
    }
    
     void appendEvent(String str) {
        event.append(str);
        event.setCaretPosition(chat.getText().length() - 1);

    }

    public void clearText(JTextArea area) {
        area.setText("");
    }
    
    Action action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String tmpText = tf.getText();
            tf.setText("");
            appendChat(tmpText + "\n");
            SendMsg s=new SendMsg(vectorChat);
            s.send(tmpText);

            // TODO SAVE TEXT
        }
    };

    /*
    * Button or JTextField clicked
     */
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        // if it is the Logout button
        if (o == logout) {
            // client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
            System.exit(0);
        }
        // if it the who is in button
        if (o == whoIsIn) {
            //client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));
            return;
        }

        if (o == login) {

//            Thread thread1 = new Node(20015, 1, this);
//            thread1.start();
            // disable login button
            login.setEnabled(false);
            // enable the 2 buttons
            logout.setEnabled(true);
            //whoIsIn.setEnabled(true);
           
        }

    }

    public String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) networkInterfaces
                        .nextElement();
                Enumeration<InetAddress> nias = ni.getInetAddresses();
                while (nias.hasMoreElements()) {
                    InetAddress ia = (InetAddress) nias.nextElement();
                    if (!ia.isLinkLocalAddress()
                            && !ia.isLoopbackAddress()
                            && ia instanceof Inet4Address) {
                        ip = ia.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, e);
        }
        return ip;
    }
}
