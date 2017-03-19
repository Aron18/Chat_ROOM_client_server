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
			String receive,worked;
			StringTokenizer	token;
			socket = client;

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));	//get the message
			out = new PrintWriter(new BufferedReader(new OutputStreamWriter(socket.getOutputStream())));	//send the message

			public void run(){
				try{
					while(true){
						receive = in.readline();
						token = new StringTokenizer(receive,"|");	//process the message
						worked = token.nextToken();	//choose the first to start and loop on
						if(worked.equals("user_name")){
							hello();
						}
						else if (worked.equals("Message")){
							chat();
						}
					}
					}cathc(IOException e){
						ex.printStackTrace();
					}
				}
			}
		}
	}
	public static void mian(String[] args){
		new server().go();
	}
}