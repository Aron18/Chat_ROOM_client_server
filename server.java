import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.event.*;

public class server{
	
	public void go(){
		ServerSocket serverSock = new ServerSocket(5000);
		try{
			while(true){
				Socket socket = serverSock.accept();
				Thread t = new Thread(new ServerProcess(socket));
				t.start();
				System.out.println("got a connection");
			}
		}catch(Exception ex){
				System.out.println("can't not find a connection");
				ex.printStackTrace();
			}
	}

	public class ServerProcess implements Runnable{
		Socket socket;
		public BufferedReader in;
		public PrintWriter out;
		String receive,worked;
		StringTokenizer	token;

		public ServerProcess(Socket client){
			try{
				socket = client;
				InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(new BufferedWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))));
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}

		public void run(){
			try{
				while(true){
					receive = in.readline();
					token = new StringTokenizer(receive,"|");	//process the message
					worked = token.nextToken();	//Returns the next token from this string tokenizer
					if(worked.equals("user_name")){
						hello();
					}
					else if (worked.equals("Message")){
						chat();
					}
				}
			}catch(IOException ex){
				ex.printStackTrace();
				}
			}

		public void hello(){
			String name = token.nextToken();	//get the user name
			sednAll(name + " is online");
			System.out.println(name + " is online");
		}

		public void chat(){
			String message = token.nextToken();	//get the message
			String name = token.nextToken();	//get the speaker
			String mes = name + " : " + message;
			sendAll(mes);
			}

		public void sednAll(String messages){
			out.println(messages);
			out.flush();
			System.out.println(messages);
			}
		}	//end the ServerProcess

	
	public static void mian(String[] args){
		new server().go();
	}
}
	