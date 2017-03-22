import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.event.*;

public class server {
    PrintWriter out;
    String receive, worked;

    StringTokenizer token;
    Set<Socket> hashset;

    public class ServerProcess implements Runnable {
        BufferedReader in;
        PrintWriter out;
        Socket socket;

        public ServerProcess(Socket clientSocket) throws IOException {
            try {
                socket = clientSocket;
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            } catch (Exception ex) {
                socket.close();
                ex.printStackTrace();
            }
        }

        public void run() {
            try {
                while (true) {
                    receive = in.readLine();
                    //System.out.println(receive);
                    token = new StringTokenizer(receive, "|");    //process the message
                    worked = token.nextToken();    //Returns the next token from this string tokenizer
                    if (worked.equals("user_name")) {
                        String name1 = token.nextToken();
                        hello(name1);
                    } else if (worked.equals("Message")) {
                        String mes = token.nextToken();    //get the message
                        String uer = token.nextToken();    //get the user
                        String messs = uer + " : " + mes;
                        chat(messs);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void hello(String name) {

        for (Socket s : hashset) {
            try {
                PrintWriter psend = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
                //String name = token.nextToken();	//get the user name
                psend.println("!!!" + " " + name + " is online now" + "!!!");
                psend.flush();
                System.out.println("!!!" + " " + name + " is online now");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void chat(String mess) {
        //String message;
        for (Socket s : hashset) {
            try {
                PrintWriter psed = new PrintWriter((s.getOutputStream()));
                psed.println(mess);
                psed.flush();
                System.out.println(mess);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void go() {
        try {
            ServerSocket serverSock = new ServerSocket(5000);		//1024-65535
            hashset = new HashSet<>();
            while (true) {
                Socket socket;
                socket = serverSock.accept();
                hashset.add(socket);
                Thread t = new Thread(new ServerProcess(socket));
                t.start();
                System.out.println("get a connection");
            }
        } catch (IOException E) {
            E.printStackTrace();
            System.out.println("fail to connect");
        }
    }

    public static void main(String[] args) {
        new server().go();
    }
}
