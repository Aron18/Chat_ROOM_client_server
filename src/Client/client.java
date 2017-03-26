package Client;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class client {
    String name, username;
    JTextField usrname;
    JFrame frame1;    //login panel
    JFrame frame2;    //chat panel
    JFrame frame3;  //chat log
    BufferedReader in;
    PrintWriter out;
    Socket socket;
    JButton loginButtion;
    JButton sendButton;
    JButton readlog;
    JTextArea inputarea, chatpanel;
    JTextArea showP;

    private void setUpNetworking() {
        try {
            socket = new Socket("127.0.0.1", 5000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

            Thread t = new Thread(new inputListener(socket));
            t.start();
            System.out.println("networking established");

        } catch (IOException ex) {
            System.out.println("Fail");
                ex.printStackTrace();
        }
    }

    public void login() {
        setUpNetworking();
        frame1 = new JFrame("login in panel");
        JPanel logininPanel = new JPanel();
        JLabel label1 = new JLabel("UR NAME");
        usrname = new JTextField(20);
        loginButtion = new JButton("login");
        JScrollPane qScroller = new JScrollPane(usrname);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        loginButtion.addActionListener(new loginButtionListener());
        logininPanel.add(qScroller);
        logininPanel.add(label1);
        logininPanel.add(usrname);
        logininPanel.add(loginButtion);


        frame1.getContentPane().add(BorderLayout.CENTER, logininPanel);
        frame1.setSize(400, 500);
        frame1.setVisible(true);    //show the login panel
    }

    public void chatPane() {
        frame2 = new JFrame("Chat Room");
        JPanel chatP = new JPanel();
        chatpanel = new JTextArea(10, 30);    //show the chat history
        inputarea = new JTextArea(5, 20);    //the area to input the message
        sendButton = new JButton("Send");
        readlog = new JButton("Chatlog");
        readlog.addActionListener(new readlogListener());
        sendButton.addActionListener(new sendButtonListener());
        chatpanel.setLineWrap(true);    //change lines
        chatpanel.setEditable(false);    //don't allow to edit

        chatP.add(chatpanel);
        chatP.add(inputarea);
        chatP.add(sendButton);
        chatP.add(readlog);

        frame2.getContentPane().add(BorderLayout.CENTER, chatP);
        frame2.setSize(400, 600);
        frame2.setVisible(false);
    }

    public void chatLogpane(){
        frame3 = new JFrame("log");
        JPanel P = new JPanel();
        showP = new JTextArea(60,30);
        showP.setLineWrap(true);
        showP.setEditable(false);
        frame3.add(showP);
        frame3.setSize(400,600);
    }

    public static void main(String[] args) {

        client Client = new client();
        Client.login();
        Client.chatPane();
        Client.chatLogpane();
    }
    public class readlogListener implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            Object obj = ev.getSource();
            try{
                if(obj.equals(readlog)){
                    frame3.setVisible(true);
                    showP.append(FileUtil.readText("clog.txt"));
                    //System.out.println(FileUtil.readText("clog.txt"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public class loginButtionListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            Object obj = ev.getSource();    //choose a button to react
            try {
                if (obj.equals(loginButtion)) {
                    System.out.println("test");
                    if (usrname.getText().length() > 0) {
                        name = "user_name|" + usrname.getText();
                        username = usrname.getText();
                        System.out.println(name);
                        out.println(name);
                        out.flush();
                    }
                    frame1.setVisible(false);
                    frame2.setVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("U R offline");
            }
        }
    }

    public class sendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            Object obj = ev.getSource();
            String message;
            try {
                if (obj.equals(sendButton)) {
                    if (inputarea.getText().length() > 0) {
                        //System.out.println(inputarea.getText());
                        message = "Message|" + inputarea.getText() + "|" + username;
                        // System.out.println(message);
                        out.println(message);
                        out.flush();
                        inputarea.setText("");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                try{
                    socket.close();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                System.out.println("User is offline");
            }
        }
    }

    public class inputListener implements Runnable {
        Socket socket;
        BufferedReader reader;

        public inputListener(Socket socket) throws IOException {
            this.socket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        public void run() {
            String mea = "";
            while (true) {
                try {
                    mea = reader.readLine();
                    if (mea != null) {
                        System.out.println(mea);
                        chatpanel.append(mea + "\n");
                        FileUtil.writeText("clog.txt",mea);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
