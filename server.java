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
		BufferedReader in;
		PrintWriter out;

		public ServerProcess(Socket client){
			socket = client;

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));	//get the message
			out = new PrintWriter(new BufferedReader(new OutputStreamWriter(socket.getOutputStream())));	//send the message

			public void run(){
				try{
					while(true){
						;
					}
				}cathc(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
	public static void mian(String[] args){
		new server().go();
	}
}